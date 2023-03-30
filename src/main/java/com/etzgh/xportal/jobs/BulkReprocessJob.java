package com.etzgh.xportal.jobs;

import com.etzgh.xportal.controller.PortalSettings;
import com.etzgh.xportal.dao.ReprocessDao;
import com.etzgh.xportal.model.BulkUploadLog;
import com.etzgh.xportal.utility.AutoRequest;
import com.etzgh.xportal.utility.Config;
import com.etzgh.xportal.utility.SuperHttpClient;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.UnableToInterruptJobException;

/**
 *
 * @author sunkwa-arthur
 */
public class BulkReprocessJob implements InterruptableJob {

    private static final Logger log = Logger.getLogger(BulkReprocessJob.class.getName());
    private final ExecutorService executor = Executors.newCachedThreadPool();

    private static final Config config = new Config();
    private static final String ATM_REVERSAL_URL;
    private static final List<String> SKIP_BANK_SC;

    static {
        ATM_REVERSAL_URL = config.getProperty("ATM_REVERSAL_URL");
        String sbsc = config.getProperty("SKIP_BANK_SC").trim();
        if (!sbsc.isEmpty()) {
            SKIP_BANK_SC = Arrays.asList(sbsc.split(","));
        } else {
            SKIP_BANK_SC = new ArrayList<>();
        }

    }

    @Override
    public void execute(JobExecutionContext context)
            throws JobExecutionException {
        long start = System.currentTimeMillis();
        try {
            List<BulkUploadLog> bulkList = new ReprocessDao().getPendingProcess();

            if (!bulkList.isEmpty()) {
                log.info("BULK PROCESS PROCESSING: " + bulkList.size());

                List<Callable<String>> callables = new ArrayList<>();

                for (BulkUploadLog process : bulkList) {
                    Callable<String> task = () -> {
                        long st = System.currentTimeMillis();
                        log.info("{} => execution started in new thread " + process.getReference());
                        log.info("{} => " + process.toString());
                        String res = "05";

                        //check reversal
                        BulkUploadLog p = checkReversalStatus(process);

                        if (p.getStatus().equalsIgnoreCase("NOT_REVERSED")) {

                            if (p.getReference().startsWith("02") && !p.getReference().startsWith("02MPAY")) {
                                //check transaction status from bank
                                if (!SKIP_BANK_SC.contains(p.getDestination().substring(0, 3))) {
                                    String[] bankResp = new AutoRequest().statusCheck(p.getReference(), p.getDestination(), "02");
                                    switch (bankResp[2]) {
                                        case "00":
                                            //update transaction to successful
                                            new ReprocessDao().updateProcessStatus(process, res, "", "");
                                            break;
                                        case "06":
                                            //update transaction to final status

                                            break;
                                        case "14":
                                            //invalid account... reverse transaction
                                            break;
                                       
                                        case "":
                                            break;
                                        default:
                                            break;
                                    }
                                } else {
                                    //SKIPPING STATUS CHECK
                                }
                            } else {
                                //update database
                            }
                        }
                        //Check status
//                        if (process.getReference().startsWith("04")) {
//                            try {
//                                int respCode = new DoHTTPRequest().get(ATM_REVERSAL_URL + process.getTransid());
//                                log.info("Reprocess Message from Autoswitch for : " + process.getReference() + " -> " + respCode);
//                                if (respCode == 200) {
//                                    res = "0";
//                                }
//                            } catch (Exception e) {
//                                log.error("URL: " + ATM_REVERSAL_URL, e);
//                            }
//                        } else {
//                            String[] message = new AutoRequest().doAutoSwitchReversal(e_reversal.getCard_num(), "2040", process.getReference(), e_reversal.getMerchant_code(), "XPortalRvsl:" + process.getReference(), process.getReference(), e_reversal.getTrans_amount().doubleValue(), e_reversal.getChannelid(), e_reversal.getSrcname());
//                            log.info("Reversal Message from Autoswitch for : " + process.getReference() + " -> " + Arrays.toString(message));
//                            res = "0";
//                        }
                        int update = new ReprocessDao().updateProcessStatus(process, (res.equals("0") ? "1" : res), "", "");
                        log.info("{} => execution completed :: TAT {} " + process.getReference() + " - " + (System.currentTimeMillis() - st) + " :: update status :: " + ((update > 0) ? "YES" : "NO"));
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

                log.info("done bulk reprocess job. TAT {} ms. Number of txns processed is {}" + (System.currentTimeMillis() - start) + " " + bulkList.size());

            }
        } catch (Exception es) {

            log.error("error", es);
        }
    }

    public BulkUploadLog checkReversalStatus(BulkUploadLog bulk) {
        final String TRANSQUERY_ENDPOINT = StringUtils.substringBeforeLast(new PortalSettings().getSetting("transquery_endpoint"), ":");
        String url = TRANSQUERY_ENDPOINT + "?reference=" + bulk.getReference() + "&transtype=REVERSAL";
        log.info("CALLING: " + url);
        String scResponse = SuperHttpClient.doGet(url);
        log.info("RESPONSE: " + bulk.getReference() + " - " + scResponse);

        if (scResponse != null && !scResponse.trim().isEmpty()) {
            JSONObject sc = new JSONObject(scResponse);
            String resp = sc.optString("status", "31");
            switch (resp) {
                case "31":
                    bulk.setStatus("NOT_FOUND");
                    break;
                case "06":
                    bulk.setStatus("REVERSED");
                    break;
                case "00":
                    bulk.setStatus("NOT_REVERSED");
                    break;
                default:
                    bulk.setStatus("SC_ERROR");
                    break;
            }
        } else {
            bulk.setStatus("SC_ERROR");
        }
        return bulk;

    }

    @Override
    public void interrupt() throws UnableToInterruptJobException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
