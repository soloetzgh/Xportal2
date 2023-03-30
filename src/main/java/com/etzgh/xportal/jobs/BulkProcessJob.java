package com.etzgh.xportal.jobs;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.etz.vasgate.dao.VasgateDAO;
import com.etz.vasgate.dao.impl.VasgateDAOImpl;
import com.etzgh.xportal.dao.ReprocessDao;
import com.etzgh.xportal.model.BulkUpload;
import com.etzgh.xportal.model.BulkUploadLog;
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
public class BulkProcessJob implements InterruptableJob {

    private static final Logger log = Logger.getLogger(BulkProcessJob.class.getName());
    private final ExecutorService executor = Executors.newCachedThreadPool();

    @Override
    public void execute(JobExecutionContext context)
            throws JobExecutionException {
        log.info("Bulk Request Job");
        long start = System.currentTimeMillis();
        try {
            List<BulkUploadLog> bulkList = new ReprocessDao().getPendingProcess();/* new ReprocessDao().getPendingBulkProcessRequests(); */

            if (!bulkList.isEmpty()) {
                log.info("BULK REQUEST PROCESSING: " + bulkList.size());

                List<Callable<String>> callables = new ArrayList<>();

                for (BulkUploadLog bu : bulkList) {
                    Callable<String> task = () -> {
                        long st = System.currentTimeMillis();
                        log.info("{} => execution started in new thread " + bu.getBatch_id() + " - " + bu.getReference());
                        String res = "05";
                        String resMsg = "About to process to debit card";

                        //process transaction
                        switch (bu.getTransaction_type().toUpperCase()) {
                            case "AIRTIME":
                            case "BUNDLE":
                            case "BILL":
                                //vasgate process
                                String Response = "";
                                VasgateDAO vasDao = new VasgateDAOImpl();
                                if (bu.getTransaction_type().toUpperCase().equals("BILL") || bu.getTransaction_type().toUpperCase().equals("BUNDLE")) {
                                    Response = vasDao.payBill(bu.getDestination_type(), bu.getDestination(), bu.getAmount(), bu.getReference(), "");

                                } else if (bu.getTransaction_type().toUpperCase().equals("AIRTIME")) {
                                    Response = vasDao.doTopup(bu.getDestination_type(), bu.getDestination(), bu.getAmount(), bu.getReference());
                                }

//                                if (Response != null && !Response.isEmpty()) {
//                                    //got response
//                                    JSONObject resp = new JSONObject(Response);
//                                    if (resp.optString("error").equals("0")) {
//
//                                    } else {
//                                        //transaction failed
//                                    }
//                                } else {
//                                    //invalid response
//
//                                }
                                break;
                            default:
                                res = "06";
                                resMsg = bu.getTransaction_type() + " not setup";
                                log.info(bu.getBatch_id() + " - " + bu.getReference() + " - " + bu.getTransaction_type() + " not setup");

                                break;

                        }

                        log.info("{} => execution completed :: TAT {} " + bu.getBatch_id() + " - " + bu.getReference() + " - " + (System.currentTimeMillis() - st));
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

                log.info("done bulk process job. TAT {} ms. Number of txns processed is {}" + (System.currentTimeMillis() - start) + " " + bulkList.size());

            }
        } catch (Exception es) {
//            log.info("ChangePasswordJob Error");
            log.error("error", es);
        }
    }

    @Override
    public void interrupt() throws UnableToInterruptJobException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
