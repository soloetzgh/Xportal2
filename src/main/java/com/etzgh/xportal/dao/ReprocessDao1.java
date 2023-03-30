//package com.etzgh.xportal.dao;
//
//import com.etz.vasgate.dao.VasgateDAO;
//import com.etz.vasgate.dao.impl.VasgateDAOImpl;
//import com.etz.vasgate.util.Encryption;
//import com.etzgh.xportal.controller.PortalSettings;
//import com.etzgh.xportal.model.ApiPostData;
//import com.etzgh.xportal.model.AuditTrail;
//import com.etzgh.xportal.model.BulkUpload;
//import com.etzgh.xportal.model.Reprocess;
//import com.etzgh.xportal.model.ReprocessLog;
//import com.etzgh.xportal.model.TMC;
//import com.etzgh.xportal.model.TransStatus;
//import com.etzgh.xportal.utility.Config;
//import com.etzgh.xportal.utility.DbHibernate;
//import com.etzgh.xportal.utility.PasswordGenerator;
//import com.etzgh.xportal.utility.XRandom;
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//import java.math.BigDecimal;
//import java.sql.PreparedStatement;
//import java.sql.Timestamp;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.DoubleSummaryStatistics;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Set;
//import java.util.stream.Collectors;
//import javax.persistence.NoResultException;
//import org.apache.commons.lang.StringUtils;
//import org.apache.log4j.Logger;
//import org.hibernate.Session;
//import org.hibernate.Transaction;
//import org.hibernate.query.Query;
//import org.json.JSONObject;
//
///**
// *
// * @author sunkwa-arthur
// */
//public class ReprocessDao1 {
//
//    private static final Gson gson = new Gson();
//    private static final Config config = new Config();
//    private final PortalSettings portalSettings = new PortalSettings();
//
//    private static final String path = config.getProperty("APPLICATION_API_PATH");
//    private static final int batchSize;
//    private static final int transactionPerBatch;
//    private static final String secretKey = config.getProperty("SECRET_KEY");
//    public static final String[] airtime = {"mtn", "airtel", "tigo", "vodafone", "glo"};
//    public static final List<String> topuplist = Arrays.asList(airtime);
//
//    public static HashMap<String, String> merchantCodeMap = new HashMap<String, String>();
//
//    public static String merchantCodes = "";
//
//    public static String CLIENT = "XPORTAL";
//
//    private static final Logger log = Logger.getLogger(ReprocessDao1.class.getName());
//
//    MerchantCodes mc = new MerchantCodes();
//
//    static {
//        String b = config.getProperty("UPLOAD_INSERT_BATCHSIZE");
//        String c = config.getProperty("UPLOAD_INSERT_BATCHSIZE");
//
//        batchSize = Integer.valueOf(b.isEmpty() ? "100" : b);
//        transactionPerBatch = Integer.valueOf(c.isEmpty() ? "100" : c);
//    }
//
//    public ReprocessDao1() {
//
//    }
//
//    public static boolean updateMomo(String reference, String clientId, String narration, String respcode, String fgRespcode, String status, String username, String userId, String ipAddress) {
//        boolean result = false;
//        boolean rollback = true;
//
//        String logQry = "INSERT INTO ecarddb.e_reprocess_log(batch_id,reference,product, account, amount, other_info, transaction_type, transaction_date, date_initiated, initiated_by,status) (select reference reference, clientid original_clientid, :clientid updated_clientid, respcode original_respcode, :respcode updated_respcode, narration original_narration, :updated_narration, :username update_by, :userid update_by_id from telcodb.mobilemoney where reference = :reference)";
//        String momoqry = "UPDATE telcodb.mobilemoney set clientid = :clientid, respcode =:respcode, narration = concat(substring(ifnull(narration,''),1,80), :narration) where reference = :reference ";
//        String fgqry = "SELECT etzref, respcode from fgdb.fundgate_response where etzref =:reference ";
//
//        Transaction tx = null;
//        Session usession = DbHibernate.getSession("40.9MYSQLW");
//
//        try {
//            tx = usession.beginTransaction();
//
//            Query q = usession.createNativeQuery(logQry);
//            q.setParameter("clientid", clientId);
//            q.setParameter("respcode", respcode);
//            q.setParameter("username", username);
//            q.setParameter("userid", userId);
//            q.setParameter("reference", reference);
//            q.setParameter("updated_narration", narration);
//
//            int h = q.executeUpdate();
//
//            if (h > 0) {
//
//                q = usession.createNativeQuery(momoqry);
//                q.setParameter("clientid", clientId);
//                q.setParameter("respcode", respcode);
//                q.setParameter("narration", narration);
//                q.setParameter("reference", reference);
//
//                int i = q.executeUpdate();
//                if (i > 0) {
//
//                    if (reference.toUpperCase().startsWith("09FG")) {
//                        q = usession.createNativeQuery(fgqry).setParameter("reference", reference);
//
//                        List<Object[]> res = q.getResultList();
//                        String rec = "";
//                        for (Object[] record : res) {
//                            rec = record[0].toString();
//                            System.out.println("RES:: " + rec);
//                        }
//
//                        String sql1 = "";
//                        if (!rec.isEmpty()) {
//                            sql1 = "update fgdb.fundgate_response set respMessage = concat(:respMessage,' - ',respMessage) ,respcode =:respcode where etzref =:reference ";
//                        } else {
//                            sql1 = "insert into fgdb.fundgate_response(action, terminal, respMessage, clientRef, respcode,  etzref, created) (select action, terminal, :respMessage respMessage, clientRef, :respcode respcode, :reference etzref, created from fgdb.fundgate_request where etzref=:reference)";
//                        }
//                        q = usession.createNativeQuery(sql1);
//
//                        q.setParameter("respcode", fgRespcode);
//                        q.setParameter("respMessage", fgRespcode + "::Transaction " + status + (status.equalsIgnoreCase("successful") ? " Ref: " + reference + "|[" + clientId + "]" : ""));
//                        q.setParameter("reference", reference);
//
//                        int j = q.executeUpdate();
//                        if (j > 0) {
//                            rollback = false;
//                        }
//                    } else {
//                        rollback = false;
//                    }
//                }
//            }
//
//            if (!rollback) {
//
//                tx.commit();
//                result = true;
//                new Thread(() -> {
//                    AuditTrail audit = new AuditTrail(userId, "Mobilemoney update for " + reference + " to clientId: " + clientId + " and responseCode: " + respcode + " and narration: " + narration, "update", null, "Mobilemoney Status Update", ipAddress);
//                    new AuditDao().insertIntoAuditTrail(audit);
//                }).start();
//
//            } else if (tx.isActive()) {
//                try {
//                    tx.rollback();
//                } catch (Exception er) {
//                    log.error("error", er);
//                }
//            }
//
//        } catch (Exception et) {
//            et.printStackTrace();
//            if (tx != null) {
//                try {
//                    tx.rollback();
//                } catch (Exception e) {
//                    log.error(e.getMessage(), e);
//                }
//            }
//        } finally {
//            if (usession != null) {
//                usession.close();
//            }
//        }
//
//        return result;
//    }
//
//    public String initiateReprocessing(String rawJsonData, String username, String status) {
//
//        String result = "";
//
//        try {
//            long start = System.currentTimeMillis();
//            log.info("START:: " + start);
//            List<BulkUpload> bulkUploadList = new Gson().fromJson(rawJsonData, new TypeToken<List<BulkUpload>>() {
//            }.getType());
//            final String batchId = PasswordGenerator.generateUuid();
//
//            boolean loggedRequest = logBatchRequest(batchId, username, bulkUploadList);
//            if (loggedRequest) {
//                log.info("REQUEST LOGGED SUCCESSFULLY");
//            } else {
//                log.info("An Error Occured logging request");
//            }
//            log.info("TAT:: " + (System.currentTimeMillis() - start));
//
//        } catch (Exception rt) {
//            log.error("error: ", rt);
//        }
//
//        return result;
//    }
//
//    public boolean logBatchRequest(String batchId, String username, List<BulkUpload> bulkUploadList) {
//        boolean logged = false;
//        Session session = DbHibernate.getSession("40.15MYSQL");
//        final Transaction txn;
//        try {
//
//            txn = session.beginTransaction();
//            DoubleSummaryStatistics bulkUploadStatistics = bulkUploadList.stream()
//                    .collect(Collectors.summarizingDouble(BulkUpload::getAmount));
//
//            Double amount = bulkUploadStatistics.getSum();
//            int total = (int) bulkUploadStatistics.getCount();
//
//            String qry = "insert into bulk_process_request(batch_id, initiated_by,date_initiated, total, amount, status) values (:batchId, :username, now(), :total, :amount, :status)";
//
//            Query q = session.createNativeQuery(qry);
//            q.setParameter("batchId", batchId);
//            q.setParameter("username", username);
//            q.setParameter("total", total);
//            q.setParameter("amount", amount);
//            q.setParameter("status", "PENDING");
//
//            int i = q.executeUpdate();
//
//            String qry2 = "insert into bulk_process_log(batch_id,reference, destination_type,account,amount,extra_info, transaction_date, transaction_type, transaction_check) "
//                    + " values (?,?,?,?,?,?, now(),?, sha2(UPPER(concat(CAST(? AS DECIMAL(10,2)),?,?,?, 0,?)),512))";
//
//            if (i > 0) {
//                session.doWork(connection -> {
//                    try (PreparedStatement pstmt = connection.prepareStatement(qry2)) {
//                        int j = 1;
//                        for (BulkUpload bu : bulkUploadList) {
//                            pstmt.setString(1, batchId);
//                            pstmt.setString(2, bu.getReference());
//                            pstmt.setString(3, bu.getDestination_type());
//                            pstmt.setString(4, bu.getDestination());
//                            pstmt.setDouble(5, bu.getAmount());
//                            pstmt.setString(6, bu.getExtra_info());
//                            pstmt.setString(7, bu.getTransaction_type());
//
//                            pstmt.setDouble(8, bu.getAmount());
//                            pstmt.setString(9, bu.getReference());
//                            pstmt.setString(10, bu.getDestination());
//                            pstmt.setString(11, bu.getDestination_type());
//                            pstmt.setString(12, secretKey);
//
//                            pstmt.addBatch();
//
//                            if (j % batchSize == 0) {
//                                pstmt.executeBatch();
//                            }
//                            j++;
//                        }
//                        pstmt.executeBatch();
//                    } catch (Exception e) {
//                        log.error("error", e);
//                        txn.rollback();
//                    }
//                });
//            }
//
//            txn.commit();
//            logged = true;
//        } catch (Exception er) {
//            log.error("error", er);
//        } finally {
//            if (session != null) {
//                session.close();
//            }
//        }
//
//        return logged;
//    }
//
//    public boolean authorizeBatchRequest(String batchId, String username, String action) {
//        boolean updated = false;
//        Session session = DbHibernate.getSession("40.15MYSQL");
//        final Transaction txn;
//        try {
//
//            txn = session.beginTransaction();
//            String qry = "update bulk_process_request set status = :status, date_authorized = now(), authorized_by = :username  where batch_id = :batchId and status = 'PENDING';";
//
//            Query q = session.createNativeQuery(qry);
//            q.setParameter("batchId", batchId);
//            q.setParameter("username", username);
//            q.setParameter("status", action.equalsIgnoreCase("AUTHORIZE") ? "AUTHORIZED" : "DENIED");
//
//            int i = q.executeUpdate();
//
//            txn.commit();
//            updated = i > 0;
//        } catch (Exception er) {
//            log.error("error", er);
//        } finally {
//            if (session != null) {
//                session.close();
//            }
//        }
//
//        return updated;
//    }
//
//    public List<BulkUpload> getInitiatedBulkRequests(String jsonString) {
//        log.info("process BulkRequests trx request received >> " + jsonString);
//        Gson j = new Gson();
//        ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);
//
//        String startDate = apiData.getStartDate();
//        String endDate = apiData.getEndDate();
//        String batchId = apiData.getSearchKey();
//        String service = apiData.getService();
//        String status = apiData.getStatus();
//
//        String query = "";
//
//        List<BulkUpload> records = new ArrayList<>();
//
//        if (service.equalsIgnoreCase("searchIniRequests") && StringUtils.isBlank(batchId)) {
//            return records;
//        }
//        query = "select batch_id, amount, total, initiated_by, date_initiated, status from  bulk_process_request where 1=1 "
//                + (service.equalsIgnoreCase("searchIniRequests") ? " and  batch_id = :batch_id " : " and date_Initiated between :start_dt and :end_dt ")
//                + (!(StringUtils.isBlank(status) || status.equalsIgnoreCase("ALL")) ? " and status = :status " : "")
//                + (!StringUtils.isBlank(batchId) ? " and batch_id = :batch_id " : "");
//
//        Session session = DbHibernate.getSession("40.15MYSQL");
//        try {
//
//            Query q = session.createNativeQuery(query);
//
//            if (service.equalsIgnoreCase("searchIniRequests")) {
//                q.setParameter("batch_id", batchId);
//            } else {
//                q.setParameter("start_dt", startDate);
//                q.setParameter("end_dt", endDate);
//            }
//            records = new ArrayList<>();
//            List<Object[]> resp = q.getResultList();
//
//            for (Object[] record : resp) {
//                BulkUpload b = new BulkUpload();
//                try {
//                    b.setBatch_id(checkNull(record[0]));
//                    b.setAmount(checkNullDouble(record[1]));
//                    b.setTotal(checkNullNum(record[2]));
//                    b.setInitiated_by(checkNull(record[3]));
//                    b.setDate_initiated(checkNull(record[4]));
//                    b.setStatus(checkNull(record[5]));
//                } catch (Exception e) {
//                    log.error(e.getMessage(), e);
//                }
//                records.add(b);
//
//            }
//        } catch (Exception e) {
//            log.error("error", e);
//        } finally {
//            if (session != null) {
//                session.close();
//            }
//        }
//        return records;
//    }
//
//    public List<BulkUpload> getPendingBulkProcessRequests() {
//
//        String query = "";
//
//        List<BulkUpload> records = new ArrayList<>();
//
//        Session session = DbHibernate.getSession("40.15MYSQL");
//        Transaction tx = null;
//        try {
//
//            query = "select batch_id, authorized_by from bulk_process_request where 1=1 "
//                    + "and status = 'AUTHORIZED' and process_status is null "
//                    + "and authorized_date > date_sub(now(), interval 12 HOUR) order by authorized_date asc";
//            tx = session.beginTransaction();
//            Query q = session.createNativeQuery(query);
//
//            records = new ArrayList<>();
//            List<Object[]> resp = q.getResultList();
//            List<String> batchList = new ArrayList<>();
//
//            for (Object[] record : resp) {
//                BulkUpload b = new BulkUpload();
//                try {
//                    b.setBatch_id(checkNull(record[0]));
//                    if (!b.getBatch_id().equalsIgnoreCase("null")) {
//                        batchList.add(b.getBatch_id());
//                    }
//                    b.setAuthorized_by(checkNull(record[1]));
//                } catch (Exception et) {
//                    log.error("", et);
//                }
//                records.add(b);
//            }
//
//            if (!records.isEmpty()) {
//
//                query = "select batch_id, amount, reference, destination_type, account, extra_info, transaction_type,transaction_check, "
//                        + " status, sha2(UPPER(concat(CAST(amount AS DECIMAL(10,2)),reference,account,destination_type, 0,:secret)),512) mac "
//                        + " from  bulk_process_request where 1=1 and batch_id in (:batch_id) and status is null "
//                        + " order by id asc limit :transactionsPerBatch";
//
//                records = new ArrayList<>();
//                q = session.createNativeQuery(query);
//                q.setParameter("batch_id", batchList);
//                q.setParameter("secret", secretKey);
//                q.setParameter("transactionsPerBatch", transactionPerBatch);
//                resp = q.getResultList();
//
//                for (Object[] record : resp) {
//                    BulkUpload b = new BulkUpload();
//                    try {
//                        b.setBatch_id(checkNull(record[0]));
//                        b.setAmount(checkNullDouble(record[1]));
//                        b.setReference(checkNull(record[2]));
//                        b.setDestination_type(checkNull(record[3]));
//                        b.setDestination(checkNull(record[4]));
//                        b.setExtra_info(checkNull(record[5]));
//                        b.setTransaction_type(checkNull(record[6]));
//                        b.setTransaction_check(checkNull(record[7]));
//                        b.setStatus(checkNull(record[8]));
//                        b.setMac(checkNull(record[9]));
//                    } catch (Exception r) {
//                        r.printStackTrace();
//                    }
//                    records.add(b);
//                }
//
//                query = "update bulk_process_request set process_status = 'PROCESSING' where process_status is null and status ='AUTHORIZED' and batch_id in (:batch)";
//                q = session.createNativeQuery(query);
//                q.setParameter("batch_id", batchList);
//
//                int i = q.executeUpdate();
//                if (i > 0) {
//                    query = "update bulk_process_log set status = '58' where status is null and batch_id in (:batch)";
//                    q = session.createNativeQuery(query);
//                    q.setParameter("batch_id", batchList);
//
//                    int k = q.executeUpdate();
//
//                    if (k == 0) {
//                        records = new ArrayList<>();
//                    }
//                }
//            }
//            tx.commit();
//        } catch (Exception e) {
//            log.error("error", e);
//            if (tx != null) {
//                tx.rollback();
//            }
//        } finally {
//            if (session != null) {
//                session.close();
//            }
//        }
//        return records;
//    }
//
//    public boolean authorizeBatchRequest(BulkUpload bu) {
//        boolean updated = false;
//        Session session = DbHibernate.getSession("40.15MYSQL");
//        final Transaction txn;
//        try {
//
//            txn = session.beginTransaction();
//            String qry = "update bulk_process_log set status = :status, reversed = :reversed, reversal_code = :reversal_code  where reference = :reference;";
//
//            Query q = session.createNativeQuery(qry);
//            q.setParameter("batchId", bu.getBatch_id());
//            q.setParameter("username", bu.getStatus());
//            q.setParameter("reversed", bu.getReference());
//
//            int i = q.executeUpdate();
//
//            txn.commit();
//            updated = i > 0;
//        } catch (Exception er) {
//            log.error("error", er);
//        } finally {
//            if (session != null) {
//                session.close();
//            }
//        }
//
//        return updated;
//    }
//
//    protected int checkNullNum(Object Data) {
//        if (Data != null && !Data.toString().trim().isEmpty()) {
//            return Integer.valueOf(Data.toString());
//        }
//        return 0;
//    }
//
//    protected double checkNullDouble(Object Data) {
//        if (Data != null && !Data.toString().trim().isEmpty()) {
//            return Double.valueOf(Data.toString());
//        }
//        return 0.00;
//    }
//
//    public String authorizeReprocessing(String reference, String userName, String action) {
//        String Response = "";
//        String result = "";
//        JSONObject transaction = new JSONObject(reference);
//        JSONObject verifiedtransaction = new JSONObject();
//
//        log.info("=======Trans=======");
//        log.info(transaction.toString());
//        String unique_transid = transaction.getString("unique_transid");
//        String dest_account = transaction.getString("dest_account");
//        String amount = transaction.getString("amount");
//        String transtype = transaction.getString("transtype");
//        String provider = transaction.getString("provider");
//        String mac = transaction.getString("mac");
//        String otherinfo = "";
//        if (transaction.has("otherinfo")) {
//            otherinfo = transaction.getString("otherinfo");
//        }
//
//        boolean realTrans = verifyMac(dest_account, unique_transid, BigDecimal.valueOf(Double.valueOf(amount)), otherinfo, mac);
//
//        if (action.equalsIgnoreCase("authorize") && realTrans) {
//
//            int successResult = getSuccess(unique_transid, "bill");
//            switch (successResult) {
//                case 0:
//                    switch (transtype.toUpperCase()) {
//                        case "BILL":
//                        case "AIRTIME":
//                            verifiedtransaction.put("unique_transid", unique_transid);
//                            verifiedtransaction.put("provider", provider);
//                            verifiedtransaction.put("amount", amount);
//                            verifiedtransaction.put("dest_account", dest_account);
//                            verifiedtransaction.put("transtype", transtype);
//                            verifiedtransaction.put("otherinfo", otherinfo);
//
//                            log.info("Processing : " + verifiedtransaction.toString());
//                            if (unique_transid.endsWith("rep")) {
//
//                                log.info(unique_transid + " second attempt");
//                                Response = "ERROR:2ndAttempt";
//
//                            } else {
//
//                                Response = doReprocess(verifiedtransaction, userName);
//                            }
//
//                            if (Response.equals("SUCCESS")) {
//                                result = "SUCCESS:AUTHORIZED";
//                            } else {
//                                log.info("REPROCESS BILL ERROR ::: " + Response);
//                                result = Response;
//                            }
//                            break;
//                        default:
//                            result = "ERROR:INVALID TRANSTYPE";
//                            break;
//                    }
//                    break;
//                case 1:
//                    String result2 = "";
//                    try {
//                        Session session = DbHibernate.getSession("40.15MYSQL");
//                        Transaction userTxn7 = null;
//                        try {
//                            userTxn7 = session.beginTransaction();
//                            String qry2 = "update ecarddb.e_reprocess set `settled` = '0', `status`= 'authorized', `date_authorized`= now(), `authorized_by`= :username where `reference` =:reference and `status` ='pending' and `settled`='1'";
//                            Query q = session.createNativeQuery(qry2);
//                            q.setParameter("reference", unique_transid);
//                            q.setParameter("username", userName);
//                            q.executeUpdate();
//                            userTxn7.commit();
//                            result2 = "UPDATED";
//                        } catch (Exception ex) {
//                            result2 = ex.getMessage();
//                            log.info("REPROCESS ERROR ::: " + ex);
//                        } finally {
//                            if (session != null) {
//                                session.close();
//                            }
//                        }
//
//                    } catch (Exception excep) {
//                        log.info("REPROCESS ERROR UPDATING TO SUCCESS: " + excep);
//                    }
//                    result = "ERROR:ALREADY PROCESSED:" + result2;
//                    break;
//                case 2:
//                    result = "ERROR:CANT REPROCESS TRANSTYPE";
//                    break;
//                case 3:
//                    result = "ERROR:INVALID REFERENCE";
//                    break;
//                default:
//                    result = "ERROR:AN ERROR OCCURED";
//                    break;
//            }
//
//        } else if (action.equalsIgnoreCase("deny")) {
//
//            Session session = DbHibernate.getSession("40.15MYSQL");
//            Transaction userTxn6 = null;
//            try {
//                userTxn6 = session.beginTransaction();
//                String qry = "UPDATE `ecarddb`.`e_reprocess` SET  status ='denied', date_authorized = now(), authorized_by = :username where reference = :reference and status='pending'";
//                Query q = session.createNativeQuery(qry);
//                log.info("Update: " + qry);
//                q.setParameter("reference", unique_transid);
//                q.setParameter("username", userName);
//                int i = q.executeUpdate();
//                userTxn6.commit();
//                log.info("Update RESULT: " + i);
//
//                if (i == 0) {
//                    result = "ERROR:NO RECORD";
//                } else {
//                    result = "SUCCESS:DENIED";
//                }
//            } catch (Exception e) {
//                result = "ERROR:" + e.getMessage();
//            } finally {
//                if (session != null) {
//                    session.close();
//                }
//            }
//        } else {
//            result = "ERROR:INVALID ACTION";
//        }
//        return result;
//    }
//
//    public String doReprocess(JSONObject reprocessReq, String username) {
//        String result = "";
//        String Response = "";
//
//        JSONObject trans = reprocessReq;
//
//        log.info("REQUEST: " + trans.toString());
//        Session session = DbHibernate.getSession("40.15MYSQL");
//        Transaction userTxn3 = null;
//        try {
//
//            userTxn3 = session.beginTransaction();
//
//            String qry = "update ecarddb.e_reprocess set `settled` = '1' where `reference` =:reference and `status` ='pending' and `settled` is null";
//            Query q = session.createNativeQuery(qry);
//
//            q.setParameter("reference", trans.getString("unique_transid"));
//            int i = q.executeUpdate();
//            userTxn3.commit();
//            if (i > 0) {
//                log.info("Before post: " + trans);
//                VasgateDAO vasDao = new VasgateDAOImpl();
//                if (trans.getString("transtype").equalsIgnoreCase("bill")) {
//                    Response = vasDao.payBill(trans.getString("provider"), trans.getString("dest_account").trim(), Double.valueOf(trans.getString("amount")), trans.getString("unique_transid"), trans.optString("otherinfo", ""));
//
//                } else if (trans.getString("transtype").equalsIgnoreCase("airtime")) {
//                    Response = vasDao.doTopup(trans.getString("provider"), trans.getString("dest_account").trim(), Double.valueOf(trans.getString("amount")), trans.getString("unique_transid"));
//                }
//                log.info("RESPONSE : " + Response);
//                JSONObject vasResp = new JSONObject(Response);
//                if (vasResp.has("response") && vasResp.has("vasresponse")) {
//
//                    JSONObject vasResponse = new JSONObject(vasResp.getString("vasresponse"));
//                    String status = vasResp.getString("response");
//                    Transaction userTxn4 = null;
//                    try {
//                        userTxn4 = session.beginTransaction();
//                        String qry2 = "update ecarddb.e_reprocess set `settled` = :settled, `status`= 'authorized', `rsp_code` = :resp_code, `rsp_message` = :resp_msg, `date_authorized`= now(), `authorized_by`= :username where `reference` =:reference and `status` ='pending' and `settled`='1'";
//                        q = session.createNativeQuery(qry2);
//                        q.setParameter("reference", trans.getString("unique_transid"));
//                        q.setParameter("username", username);
//                        q.setParameter("resp_code", vasResponse.getString("error"));
//                        q.setParameter("resp_msg", vasResponse.optString("fault", ""));
//                        q.setParameter("settled", (status.equalsIgnoreCase("00") || status.equalsIgnoreCase("0")) ? "0" : "X");
//                        int j = q.executeUpdate();
//                        userTxn4.commit();
//                        result = (status.equalsIgnoreCase("00") || status.equalsIgnoreCase("0")) ? "SUCCESS" : "ERROR:" + vasResponse.optString("otherinfo");
//                    } catch (Exception ex) {
//                        result = "ERROR:" + ex.getMessage();
//                        log.info("REPROCESS ERROR ::: " + ex);
//                    } finally {
//                        if (session != null) {
//                            session.close();
//                        }
//                    }
//
//                } else {
//                    result = "ERROR:VASGATE ERROR";
//                }
//            } else {
//                result = "ERROR:NO RECORD FOUND";
//                log.info("NO RECORD FOUND");
//            }
//        } catch (Exception ex) {
//            result = "ERROR:" + ex.getMessage();
//            log.info("DOREPROCESS ERROR ::: " + ex);
//        }
//
//        return result;
//    }
//
//    public String createVasRequest(JSONObject req, String type) {
//        JSONObject vasRequest = new JSONObject();
//        if (type.equalsIgnoreCase("process")) {
//            vasRequest.put("reference", req.getString("unique_transid"));
//            vasRequest.put("product", req.getString("provider"));
//            vasRequest.put("amount", req.getString("amount"));
//            vasRequest.put("otherinfo", req.getString("otherinfo"));
//            vasRequest.put("account", req.getString("dest_account"));
//
//        } else {
//            vasRequest.put("reference", new XRandom().generateUniqueId());
//            vasRequest.put("product", req.getString("provider"));
//            vasRequest.put("amount", req.getString("amount"));
//            vasRequest.put("account", req.getString("dest_account"));
//            vasRequest.put("otherinfo", req.getString("otherinfo"));
//        }
//        return vasRequest.toString();
//    }
//
//    public int handleSQLDuplicateErrorAndNoResultFound(String reference) {
//        Session session = DbHibernate.getSession("40.15MYSQL");
//
//        try {
//
//            String qry = "select * from ecarddb.e_reprocess where reference like :reference and status='pending' or (status='authorized' and settled = '0' and reference like :reference)";
//
//            Query q = session.createNativeQuery(qry);
//            q.setParameter("reference", reference + "%");
//
//            Object[] record = (Object[]) q.getSingleResult();
//
//            if (record != null) {
//                log.info("Pending Request or awaiting authorization");
//                return 1;
//            }
//        } catch (NoResultException ex) {
//            log.info("no result");
//            return 0;
//        } catch (Exception ex) {
//            log.info("error: " + ex);
//            if (ex.getMessage().contains("more than one elements")) {
//
//                return 3;
//            }
//            return 2;
//        } finally {
//            if (session != null) {
//                session.close();
//            }
//        }
//        log.info("no result");
//        return 0;
//
//    }
//
//    public String logReprocess(JSONObject trans, String userName, String appName, String transType) {
//
//        String result = "INITIATED";
//        log.info("Trans: " + trans.toString());
//        Session session = DbHibernate.getSession("40.15MYSQL");
//        Transaction userTxn = null;
//        try {
//            userTxn = session.beginTransaction();
//            String qry = "INSERT INTO `ecarddb`.`e_reprocess` (`reference`,`product`,`account`,`amount`,`otherinfo`,`trans_type`,"
//                    + "`date_initiated`,`initiated_by`,`status`, `trans_date`) select :reference,:merchant,:account,:amount,:otherinfo,:transtype, "
//                    + "now(),:username,'pending',:trans_date WHERE NOT EXISTS (SELECT * FROM `ecarddb`.`e_reprocess` WHERE `reference` = :reference AND "
//                    + "`settled` in('','0','1'))";
//            Query q = session.createNativeQuery(qry);
//
//            q.setParameter("reference", trans.getString("unique_transid"));
//            q.setParameter("merchant", trans.getString("provider"));
//            q.setParameter("amount", trans.getString("amount"));
//            q.setParameter("account", trans.getString("dest_account"));
//            q.setParameter("username", userName);
//            q.setParameter("transtype", transType);
//            q.setParameter("trans_date", trans.getString("trans_date"));
//            q.setParameter("otherinfo", trans.getString("otherinfo"));
//            int i = q.executeUpdate();
//            userTxn.commit();
//
//            log.info("INSERT RESULT: " + i);
//
//            if (i == 0) {
//                result = "DUPLICATE";
//            }
//        } catch (Exception e) {
//            log.info("Error: " + e);
//            if (e.getMessage().contains("ConstraintViolationException")) {
//                result = "DUPLICATE";
//            } else {
//                result = "FAILED";
//
//            }
//        } finally {
//            if (session != null) {
//                session.close();
//            }
//        }
//        return result;
//    }
//
//    public List<Reprocess> getFailedTransactionsForReprocess(String jsonString) throws ParseException {
//        List<Reprocess> newrecordsList = new ArrayList<>();
//        List<Reprocess> recordsList = new ArrayList<>();
//        List<TMC> tmcrecordsList = new ArrayList<>();
//        List<ReprocessLog> reprocessrecordsList = new ArrayList<>();
//        log.info("Reprocess request received >> " + jsonString);
//        Gson j = new Gson();
//
//        if (merchantCodes.isEmpty()) {
//
//            merchantCodes = mc.getMerchantCodesString();
//            merchantCodeMap = mc.getMerchantCodes();
//
//        }
//
//        ApiPostData apidata = j.fromJson(jsonString, ApiPostData.class
//        );
//        String start_dt = apidata.getStartDate();
//        String end_dt = apidata.getEndDate();
//        String unique_transid = apidata.getUniqueTransId();
//        String destination = apidata.getDestination();
//        String source = apidata.getFrom_source();
//        String transtype = apidata.getTransType();
//        if (unique_transid == null) {
//            unique_transid = "";
//        }
//        String query = "";
//        try {
//
//            query = " select trim(account) account, auto_respcode, transid, trans_date, trans_amount, merchant_code, trans_descr from (select (case "
//                    + "when SUBSTRING_INDEX(a.trans_descr,':',1) in ('TOPUP', 'BILL') then SUBSTRING_INDEX(a.trans_descr,':',-1) \n"
//                    + "when SUBSTRING_INDEX(a.trans_descr,':',1) in ('REFID') then SUBSTRING_INDEX(a.trans_descr,'Payment for ',-1)\n"
//                    + "else '' end) account, b.response_code auto_respcode, a.transid, a.trans_date, a.trans_amount, a.merchant_code, a.trans_descr \n"
//                    + "from ecarddb.e_requestlog a left join ecarddb.e_tmcrequest b on  a.transid = b.trans_data \n"
//                    + "where (right(b.target,3) = 'CBA' or target in('MTNMOBILEMONEY')) and b.response_code in('00', '58') and "
//                    + " not exists(select mti from ecarddb.e_tmcrequest where mti ='0420' and trans_data = a.transid) "
//                    + (!unique_transid.isEmpty() ? " and a.transid =:unique_transid " : "")
//                    + " and a.merchant_code in (" + merchantCodes + ",'0067510000010000') and a.trans_date between :start_dt and :end_dt ) as t1";
//
//            if (!query.isEmpty()) {
//
//                recordsList = new ArrayList<>();
//                Session session = DbHibernate.getSession("40.15MYSQL");
//                List<Object[]> records = new ArrayList<>();
//                try {
//                    Query q = session.createNativeQuery(query)
//                            .setParameter("start_dt", start_dt)
//                            .setParameter("end_dt", end_dt);
//                    if (!unique_transid.isEmpty()) {
//                        q.setParameter("unique_transid", unique_transid);
//                    }
//                    records = q.getResultList();
//                } catch (Exception e) {
//                    log.error("error", e);
//                } finally {
//                    if (session != null) {
//                        session.close();
//                    }
//                }
//
//                for (Object[] record : records) {
//                    Reprocess rep = new Reprocess();
//                    try {
//                        rep.setDest_account(checkNull(record[0]));
//                        rep.setResponse_code(checkNull(record[1]));
//                        rep.setUnique_transid(checkNull(record[2]));
//                        rep.setTrans_date(((Timestamp) record[3]).toString());
//                        rep.setAmount(BigDecimal.valueOf(Double.valueOf(checkNull(record[4]))));
//
//                        rep.setProvider(getMerchant(checkNull(record[5])));
//                        rep.setTranstype(topuplist.contains(rep.getProvider().toLowerCase()) ? "airtime" : "bill");
//                        rep.setOtherinfo(checkNull(record[6]));
//                        rep.setMac(generateMac(rep.getDest_account(), rep.getUnique_transid(), rep.getAmount(), rep.getOtherinfo()));
//                    } catch (Exception r) {
//                        r.printStackTrace();
//                    }
//                    recordsList.add(rep);
//                }
//
//                String sql_inNew = recordsList.stream().filter(trans -> !trans.getUnique_transid().endsWith("rep"))
//                        .map(f -> "'" + f.getUnique_transid() + "'").collect((Collectors.joining(",")));
//
//                reprocessrecordsList = getLogged(sql_inNew);
//                if (!reprocessrecordsList.isEmpty()) {
//                    Set<String> refs1 = reprocessrecordsList.stream().map(ReprocessLog::getReference)
//                            .collect(Collectors.toSet());
//                    log.info("Reft: " + refs1.toString());
//
//                    String PendingDebit = recordsList.stream().filter(trans -> trans.getResponse_code().equals("58"))
//                            .map(f -> "'" + f.getUnique_transid() + "'")
//                            .collect(Collectors.joining(","));
//
//                    if (!PendingDebit.isEmpty()) {
//
//                        Session session1 = DbHibernate.getSession("40.15MYSQL");
//                        List<Object[]> records1 = new ArrayList<>();
//                        String qry = "Select a.reference, a.respcode from telcodb.mobilemoney a left  where respcode not in ('0','00') and reference in (" + PendingDebit + ") ";
//
//                        try {
//                            Query q = session1.createNativeQuery(qry);
//                            records1 = q.getResultList();
//                        } catch (Exception er) {
//                            log.error("error", er);
//                        } finally {
//                            if (session1 != null) {
//                                session1.close();
//                            }
//                        }
//                        List<TransStatus> transStatus = new ArrayList<>();
//                        records1.stream().filter((record) -> (record[0] != null && record[1] != null)).map((record) -> {
//                            TransStatus ts = new TransStatus();
//                            ts.setReference(record[0].toString());
//                            ts.setStatus(record[1].toString());
//                            return ts;
//                        }).forEachOrdered((ts) -> {
//                            transStatus.add(ts);
//                        });
//
//                        Set<String> refs = transStatus.stream().map(TransStatus::getReference).collect(Collectors.toSet());
//                        recordsList = recordsList.stream().filter(trans -> !refs.contains(trans.getUnique_transid()))
//                                .collect(Collectors.toList());
//                    }
//
//                    String vasdbRefs = recordsList.stream().map(f -> "'" + f.getUnique_transid() + "'")
//                            .collect(Collectors.joining(","));
//                    List<Object[]> records2 = new ArrayList<>();
//                    List<TransStatus> vasTrans = new ArrayList<>();
//                    if (!vasdbRefs.isEmpty()) {
//                        String qry2 = "select unique_transid, response_code trans_status from vasdb2.t_provider_log where unique_transid in (" + vasdbRefs + ") union \n"
//                                + "select unique_transid, trans_status  from vasdb2.t_paytrans where unique_transid in (" + vasdbRefs + ")";
//                        Session session1 = DbHibernate.getSession("40.15MYSQL");
//
//                        try {
//                            Query q = session1.createNativeQuery(qry2);
//                            records2 = q.getResultList();
//
//                            for (Object[] record : records2) {
//                                try {
//                                    if (record[0] != null && record[1] != null) {
//                                        TransStatus ts = new TransStatus();
//                                        ts.setReference(record[0].toString());
//                                        ts.setStatus(record[1].toString());
//                                        vasTrans.add(ts);
//                                    }
//                                } catch (Exception e) {
//                                    log.error(e.getMessage(), e);
//                                }
//                            }
//                        } catch (Exception er) {
//                            log.error("error", er);
//                        } finally {
//                            if (session1 != null) {
//                                session1.close();
//                            }
//                        }
//                    }
//
//                    Set<String> refs2 = vasTrans.stream().map(TransStatus::getReference).collect(Collectors.toSet());
//                    recordsList = recordsList.stream().filter(trans -> !refs2.contains(trans.getUnique_transid()))
//                            .collect(Collectors.toList());
//
//                    newrecordsList = recordsList.stream()
//                            .filter(
//                                    trans -> !refs1.contains((trans.getUnique_transid().endsWith("rep") ? trans.getUnique_transid()
//                                            : trans.getUnique_transid() + "rep")))
//                            .collect(Collectors.toList());
//
//                }
//            }
//        } catch (Exception ex) {
//            log.info("GET TRANS TO REPROCESS ERROR ::: " + ex);
//            ex.printStackTrace();
//        }
//
//        return newrecordsList;
//    }
//
//    public List<ReprocessLog> getInitiatedTransactions(String jsonString) throws ParseException {
//        List<ReprocessLog> newrecordsList = new ArrayList<>();
//        List<ReprocessLog> recordsList = new ArrayList<>();
//        log.info("Getstart_dt Initiated Reprocess request received >> " + jsonString);
//        Gson j = new Gson();
//        ApiPostData apidata = j.fromJson(jsonString, ApiPostData.class
//        );
//        String start_dt = apidata.getStartDate();
//        String end_dt = apidata.getEndDate();
//        String unique_transid = apidata.getUniqueTransId();
//        String mobile = apidata.getFrom_source();
//        String transtype = apidata.getTransType();
//
//        if (transtype == null) {
//            transtype = "";
//        }
//        if (unique_transid == null) {
//            unique_transid = "";
//        }
//        if (mobile == null) {
//            mobile = "";
//        }
//
//        String query = "select amount, reference, account, product, trans_date, initiated_by, date_initiated, status, trans_type, "
//                + "otherinfo, settled from ecarddb.e_reprocess where settled is null and status ='pending' and date_initiated between :start_dt  and :end_dt  "
//                + (!unique_transid.isEmpty() ? " and reference = :unique_transid " : "")
//                + (!transtype.equals("ALL") && !transtype.isEmpty() ? " and trans_type = :transtype " : "")
//                + (!mobile.isEmpty() ? " and account = :mobile " : "")
//                + " order by date_initiated desc";
//
//        recordsList = new ArrayList<>();
//        Session session = DbHibernate.getSession("40.15MYSQL");
//        List<Object[]> logrecords = new ArrayList<>();
//        try {
//            Query q = session.createNativeQuery(query).setParameter("start_dt", start_dt)
//                    .setParameter("end_dt", end_dt);
//            if (!unique_transid.isEmpty()) {
//                q.setParameter("unique_transid", unique_transid);
//            }
//            if (!transtype.equals("ALL") && !transtype.isEmpty()) {
//                q.setParameter("transtype", transtype);
//            }
//            if (!mobile.isEmpty()) {
//                q.setParameter("mobile", mobile);
//            }
//            logrecords = q.getResultList();
//        } catch (Exception e) {
//            log.error("error", e);
//        } finally {
//            if (session != null) {
//                session.close();
//            }
//        }
//
//        String references = "";
//        for (Object[] record : logrecords) {
//            ReprocessLog repLog = new ReprocessLog();
//            try {
//                repLog.setAmount(BigDecimal.valueOf(Double.valueOf(checkNull(record[0]))));
//                repLog.setReference(checkNull(record[1]));
//                repLog.setAccount(checkNull(record[2]));
//                repLog.setProduct(checkNull(record[3]));
//                repLog.setTrans_date(convertDate(checkNull(record[4])));
//                repLog.setInitiated_by(checkNull(record[5]));
//                repLog.setDate_initiated(convertDate(checkNull(record[6])));
//                repLog.setStatus(checkNull(record[7]));
//                repLog.setTrans_type(checkNull(record[8]));
//                repLog.setOtherinfo(checkNull(record[9]));
//                repLog.setSettled(checkNull(record[10]));
//                repLog.setMac(generateMac(repLog.getAccount(), repLog.getReference(), repLog.getAmount(), repLog.getOtherinfo()));
//            } catch (Exception e) {
//                log.error(e.getMessage(), e);
//            }
//            recordsList.add(repLog);
//        }
//
//        return recordsList;
//    }
//
//    public List<ReprocessLog> getReprocessedTransactions(String jsonString) throws ParseException {
//        List<ReprocessLog> newrecordsList = new ArrayList<>();
//        List<ReprocessLog> recordsList = new ArrayList<>();
//        log.info("Get Reprocessed Trans request received >> " + jsonString);
//        Gson j = new Gson();
//        ApiPostData apidata = j.fromJson(jsonString, ApiPostData.class
//        );
//        String start_dt = apidata.getStartDate();
//        String end_dt = apidata.getEndDate();
//        String unique_transid = apidata.getUniqueTransId();
//        String mobile = apidata.getFrom_source();
//        String transtype = apidata.getTransType();
//        if (transtype == null) {
//            transtype = "";
//        }
//
//        String query = "select amount, reference, account, product, trans_date, initiated_by, date_initiated, status, trans_type, "
//                + "otherinfo, settled from ecarddb.e_reprocess where settled is null and status not in('pending') and date_initiated between '"
//                + start_dt + "' and '" + end_dt + "' "
//                + (unique_transid != null && !unique_transid.equals("") ? " and reference = :unique_transid " : "")
//                + (!transtype.equals("ALL") && !transtype.equals("") ? " and trans_type = :transtype " : "")
//                + (mobile != null && !mobile.equals("") ? " and account = :mobile " : "")
//                + " order by date_initiated desc";
//
//        recordsList = new ArrayList<>();
//        Session session = DbHibernate.getSession("40.15MYSQL");
//        List<Object[]> logrecords = new ArrayList<>();
//        try {
//            Query q = session.createNativeQuery(query);
//
//            if (mobile != null && !mobile.equals("")) {
//                q.setParameter("mobile", mobile);
//            }
//
//            if (!transtype.equals("ALL") && !transtype.equals("")) {
//                q.setParameter("transtype", transtype);
//            }
//            if (unique_transid != null && !unique_transid.equals("")) {
//                q.setParameter("unique_transid", unique_transid);
//            }
//            logrecords = q.getResultList();
//
//        } catch (Exception e) {
//            log.error("error", e);
//        } finally {
//            if (session != null) {
//                session.close();
//            }
//        }
//        String references = "";
//        for (Object[] record : logrecords) {
//            ReprocessLog repLog = new ReprocessLog();
//            repLog.setAmount(BigDecimal.valueOf(Double.valueOf(checkNull(record[0]))));
//            repLog.setReference(checkNull(record[1]));
//            repLog.setAccount(checkNull(record[2]));
//            repLog.setProduct(checkNull(record[3]));
//            repLog.setTrans_date(convertDate(checkNull(record[4])));
//            repLog.setInitiated_by(checkNull(record[5]));
//            repLog.setDate_initiated(convertDate(checkNull(record[6])));
//            repLog.setStatus(checkNull(record[7]));
//            repLog.setTrans_type(checkNull(record[8]));
//            repLog.setOtherinfo(checkNull(record[9]));
//            repLog.setSettled(checkNull(record[10]));
//            recordsList.add(repLog);
//        }
//
//        return recordsList;
//    }
//
//    protected String checkNull(Object data) {
//
//        if (data != null && !data.equals("")) {
//            return data.toString();
//        }
//        return "NULL";
//    }
//
//    protected Date convertDate(String data) throws ParseException {
//        if (!data.equals("NULL")) {
//            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(data);
//            return date;
//        }
//        return null;
//    }
//
//    public Boolean getRev(String references) {
//        Boolean result = false;
//        Session session = DbHibernate.getSession("40.15MYSQL");
//        List<TMC> tmcrecordsList = new ArrayList<>();
//        try {
//            Object[] idsArr = references.split(",");
//            if (idsArr.length > 0) {
//                String qry = "select trans_data, mti, response_code from ecarddb.e_tmcrequest where mti= '0420' and src_node <> '62' and response_code in ('00','0') and trans_data in ("
//                        + references + ")";
//
//                Query q = session.createNativeQuery(qry);
//                tmcrecordsList = new ArrayList<>();
//                List<Object[]> tmcrecords = q.getResultList();
//                for (Object[] record : tmcrecords) {
//                    TMC tmc = new TMC();
//                    tmc.setTrans_data(checkNull(record[0]));
//                    tmc.setMti(checkNull(record[1]));
//                    tmc.setResponse_code(checkNull(record[2]));
//                    tmcrecordsList.add(tmc);
//                }
//                result = true;
//            }
//        } catch (Exception ex) {
//            result = false;
//            log.info("REPROCESS ERROR :: " + ex);
//        } finally {
//            if (session != null) {
//                session.close();
//            }
//        }
//        return result;
//    }
//
//    public int getSuccess(String reference, String type) {
//        int result = 1;
//        try {
//            log.info("Ref: " + reference + " " + type);
//            if (!reference.isEmpty()) {
//                String qry = "";
//
//                if (type.equalsIgnoreCase("bill")) {
//                    qry = "select unique_transid, trans_status from vasdb2.t_paytrans where trans_status in('0','00') and unique_transid like :reference";
//                } else if (type.equalsIgnoreCase("airtime")) {
//                    qry = "select unique_transid, response_code from vasdb2.t_provider_log where response_code in('0','00') and unique_transid like :reference";
//                }
//                if (!qry.isEmpty()) {
//                    Session session = DbHibernate.getSession("40.9MYSQL");
//                    try {
//                        Query q = session.createNativeQuery(qry);
//                        q.setParameter("reference", reference + "%");
//                        Object[] record = (Object[]) q.getSingleResult();
//
//                        if (record != null) {
//                            log.info("Successful transaction Exists");
//                            result = 1;
//                        }
//                    } catch (Exception e) {
//                        log.error("error", e);
//                    } finally {
//                        if (session != null) {
//                            session.close();
//                        }
//                    }
//
//                } else {
//                    result = 0;
//                }
//            } else {
//                result = 3;
//            }
//        } catch (NoResultException ex) {
//            log.info("no result");
//            result = 0;
//        } catch (Exception ex) {
//            log.info("REPROCESS ERROR ::: " + ex);
//            result = 3;
//        }
//        return result;
//    }
//
//    public List<ReprocessLog> getLogged(String references) {
//        log.info("Refs: " + references);
//
//        List<ReprocessLog> reprocessrecordsList = new ArrayList<>();
//        List<Object[]> reprocessrecords = new ArrayList<>();
//        try {
//            Object[] idsArr = references.split(",");
//            if (idsArr.length > 0 && !references.isEmpty()) {
//                String qry = "select reference, status from `ecarddb`.`e_reprocess` where reference in (" + references + ") and ((status ='authorized' and settled='0') or (status='pending'))";
//                log.info("Check: " + qry);
//                Session session = DbHibernate.getSession("40.15MYSQL");
//                try {
//                    Query q = session.createNativeQuery(qry);
//
//                    reprocessrecords = q.getResultList();
//                } catch (Exception y) {
//                    y.printStackTrace();
//                } finally {
//                    if (session != null) {
//                        session.close();
//                    }
//                }
//
//                for (Object[] record : reprocessrecords) {
//                    ReprocessLog repLog = new ReprocessLog();
//                    try {
//
//                        repLog.setReference(checkNull(record[0]));
//                        repLog.setStatus(checkNull(record[1]));
//                    } catch (Exception r) {
//                        r.printStackTrace();
//                    }
//                    reprocessrecordsList.add(repLog);
//                }
//
//            }
//        } catch (Exception ex) {
//            log.info("REPROCESS ERROR :: " + ex);
//        }
//        return reprocessrecordsList;
//    }
//
//    public String getMerchant(String merchantCode) {
//        return merchantCodeMap.getOrDefault(merchantCode, "");
//
//    }
//
//    public String generateMac(String destacct, String reference, BigDecimal amount, String otherInfo) {
//        String mac = Encryption.getMD5(reference + "" + amount + "" + destacct + otherInfo + CLIENT);
//
//        return mac;
//    }
//
//    public boolean verifyMac(String destacct, String reference, BigDecimal amount, String otherInfo, String mac) {
//        return Encryption.getMD5(reference + "" + amount + "" + destacct + otherInfo + CLIENT).equalsIgnoreCase(mac);
//    }
//}
