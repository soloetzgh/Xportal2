package com.etzgh.xportal.jobs;

import com.etzgh.xportal.dao.ReversalDao;
import com.etzgh.xportal.model.E_Reversal;
import com.etzgh.xportal.utility.AutoRequest;
import com.etzgh.xportal.utility.Config;
import com.etzgh.xportal.utility.DoHTTPRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.apache.log4j.Logger;
import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.UnableToInterruptJobException;

/**
 *
 * @author sunkwa-arthur
 */
public class BulkReversalJob implements InterruptableJob {

    private static final Logger log = Logger.getLogger(BulkReversalJob.class.getName());
    private final ExecutorService executor = Executors.newCachedThreadPool();

    private static final Config config = new Config();
    private static final String ATM_REVERSAL_URL;

    static {
        ATM_REVERSAL_URL = config.getProperty("ATM_REVERSAL_URL");
    }

    @Override
    public void execute(JobExecutionContext context)
            throws JobExecutionException {
        long start = System.currentTimeMillis();
        try {
            List<E_Reversal> bulkList = new ReversalDao().getPendingReversals();

            if (!bulkList.isEmpty()) {
                log.info("BULK REVERSAL PROCESSING: " + bulkList.size());

                List<Callable<String>> callables = new ArrayList<>();

                for (E_Reversal e_reversal : bulkList) {
                    Callable<String> task = () -> {
                        long st = System.currentTimeMillis();
                        log.info("{} => execution started in new thread " + e_reversal.getTransid());
                        log.info("{} => " + e_reversal.toString());
                        String res = "05";
                        if (e_reversal.getTransid().startsWith("04")) {
                            try {
                                int respCode = new DoHTTPRequest().get(ATM_REVERSAL_URL + e_reversal.getTransid());
                                log.info("Reversal Message from Autoswitch for : " + e_reversal.getTransid() + " -> " + respCode);
                                if (respCode == 200) {
                                    res = "0";
                                }
                            } catch (Exception e) {
                                log.error("URL: " + ATM_REVERSAL_URL, e);
                            }
                        } else {
                            String[] message = new AutoRequest().doAutoSwitchReversal(e_reversal.getCard_num(), "2040", e_reversal.getTransid(), e_reversal.getMerchant_code(), "XPortalRvsl:" + e_reversal.getTransid(), e_reversal.getTransid(), e_reversal.getTrans_amount().doubleValue(), e_reversal.getChannelid(), e_reversal.getSrcname());
                            log.info("Reversal Message from Autoswitch for : " + e_reversal.getTransid() + " -> " + Arrays.toString(message));
                            res = "0";
                        }

                        int update = new ReversalDao().updateReversalStatus(e_reversal.getTransid(), (res.equals("0") ? "1" : res));
                        log.info("{} => execution completed :: TAT {} " + e_reversal.getTransid() + " - " + (System.currentTimeMillis() - st) + " :: update status :: " + ((update > 0) ? "YES" : "NO"));
                        return res;
                    };
                    callables.add(task);
                }
                List<Future<String>> futures = this.executor.invokeAll(callables);
                for (Future<String> future : futures) {
                    try {
                        log.info("Job completed :: status => {}" + future.get());
                    } catch (ExecutionException e) {
                        log.error("Error processing request", e);
                    }
                }

                log.info("done bulk reversal job. TAT {} ms. Number of txns processed is {}" + (System.currentTimeMillis() - start) + " " + bulkList.size());

            }
        } catch (Exception es) {

            log.error("error", es);
        }
    }

    @Override
    public void interrupt() throws UnableToInterruptJobException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
