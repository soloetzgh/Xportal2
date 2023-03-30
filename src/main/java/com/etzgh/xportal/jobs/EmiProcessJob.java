package com.etzgh.xportal.jobs;

import com.etzgh.elevy.Elevy;
import com.etzgh.xportal.dao.EmiDao;
import com.etzgh.xportal.model.EmiPspRequest;
import java.util.ArrayList;
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
public class EmiProcessJob implements InterruptableJob {

    private static final Logger log = Logger.getLogger(EmiProcessJob.class.getName());
    private final ExecutorService executor = Executors.newCachedThreadPool();

    @Override
    public void execute(JobExecutionContext context)
            throws JobExecutionException {
        long start = System.currentTimeMillis();
        try {
            EmiDao emi = new EmiDao();
            List<EmiPspRequest> bulkList = emi.getPendingEmiProcessRequests();

            if (!bulkList.isEmpty()) {
                log.info("EMI PROCESSING: " + bulkList.size());

                List<Callable<String>> callables = new ArrayList<>();

                for (EmiPspRequest req : bulkList) {
                    Callable<String> task = () -> {
                        long st = System.currentTimeMillis();
                        log.info("{} => execution started in new thread " + req.getRequestId());

                        String[] response = new Elevy().sendEmiData(req);

                        int update = emi.updateEmiProcessStatus(response);
                        log.info("{} => execution completed :: TAT {} " + req.getRequestId() + " - " + (System.currentTimeMillis() - st) + " :: update status :: " + ((update > 0) ? "YES" : "NO"));
                        return response[0];
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

                log.info("done with emiproccess job. TAT {} ms. Number of requests processed is {}" + (System.currentTimeMillis() - start) + " " + bulkList.size());

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
