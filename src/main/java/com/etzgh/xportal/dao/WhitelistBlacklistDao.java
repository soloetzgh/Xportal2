/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etzgh.xportal.dao;

import com.etzgh.xportal.model.ApiPostData;
import com.etzgh.xportal.model.NODES;
import com.etzgh.xportal.model.NlaLiquidationReport;
import com.etzgh.xportal.model.WhitelistBlacklistReport;
import com.etzgh.xportal.utility.Config;
import com.etzgh.xportal.utility.DbHibernate;
import com.etzgh.xportal.utility.GeneralUtility;
import com.etzgh.xportal.utility.HashNumber;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.json.JSONObject;

/**
 *
 * @author yaw.owusu-koranteng
 */
public class WhitelistBlacklistDao {

    private static final AppDao appDao = new AppDao();
    private static final GeneralUtility utility = new GeneralUtility();
    private static final Gson gson = new Gson();
    private static final Logger log = Logger.getLogger(WhitelistBlacklistDao.class.getName());
    private static Map<String, String> banks = new HashMap<>();
    private static final Config config = new Config();

    public static void main(String[] args) {
        String r = "{\"merchant\":\"\",\"product\":\"\",\"accountNumber\":\"\",\"service\":\"search\",\"action\":\"\",\"apiSecret\":\"\",\"apiCode\":\"\",\"userCode\":\"[2000000000000060]|905,[2500000000000050]|77\",\"admin\":\"\",\"type_desc\":\"\",\"role_id\":\"\",\"type_id\":\"[4],[5],[6],[7],[20],[24],[39],[40]\",\"searchKey\":\"02RS42603E77662\",\"userName\":\"\",\"ClientId\":\"\",\"user_id\":\"10500000000000754\",\"pageNumber\":1,\"rowsPerPage\":1,\"trans_code\":\"\",\"bank_code\":\"905\",\"subscriberId\":\"\",\"trans_status\":\"\",\"bank\":\"\",\"biller\":\"\",\"transId\":\"\",\"bankid\":\"\",\"card_num\":\"\",\"payType\":\"\",\"wcMerchant\":\"\",\"lineType\":\"\",\"trans_channel\":\"\",\"terminal_id\":\"\",\"card_account\":\"\",\"cop_issuercode\":\"\",\"cop_role_id\":\"\",\"cop_company_id\":\"\",\"mobile_no\":\"\",\"phone\":\"\",\"type\":\"\",\"amount\":\"\",\"amount2\":\"\",\"refundStatus\":\"\",\"partialReversal\":\"\",\"reversalReason\":\"\",\"ipAddress\":\"0:0:0:0:0:0:0:1\",\"userData\":\"\",\"appId\":[],\"company\":\"\",\"options\":{}}";
        //new TmcDao().getTmcTransactions(r);
        new WhitelistBlacklistDao().getTransaction(r);
    }

    public static Map<String, String> convertListAfterJava8(List<NODES> list) {
        Map<String, String> map = list.stream().collect(Collectors.toMap(NODES::getId, node -> node.getName()));
        return map;
    }

    //MAPPING ISOCODES FOR TMC
    protected String mapResponse(String code, String resp) {

        String RespMsg = "";
        resp = resp.substring(0, 3);
        if (code == null || resp == null) {
            return "No response";
        }
        if (code.equals("58") && (resp.equals("686") || resp.equals("844") || resp.equals("863") || resp.equals("285"))) {
            return "MobileMoney Debit Successful";
        }

        if (code.equals("00")) {
            return "SUCCESSFUL";
        }
        if (code.equals("-1")) {
            return "TIME OUT";
        }
        if (code.equals("55")) {
            return "INCORRECT PIN";
        }
        if (code.equals("51")) {
            return "INSUFFICIENT FUNDS";
        }
        if (code.equals("91")) {
            return "Issuer or switch inoperative";
        }
        if (code.equals("34")) {
            return "Suspected fraud, pick-up";
        }
        if (code.equals("58")) {
            return "Transaction not permitted on terminal";
        }

        return "FAILED";
    }

    static {
        banks = convertListAfterJava8(appDao.getBankNodes("000"));
    }

    public List<WhitelistBlacklistReport> getTransaction(String jsonString) {

        log.log(Level.INFO, "Whitelist Blacklist trans request received >> {0}", jsonString);
        Gson j = new Gson();

        ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);
        //String start_dt = apiData.getStartDate();
        String startDate = apiData.getStartDate();
        //System.out.println("Start Date:"+start_dt);
//        String end_dt = apiData.getEndDate();
        String endDate = apiData.getEndDate();
        // System.out.println("End Date:"+end_dt);
        String reference = apiData.getUniqueTransId();
        String user = apiData.getSubscriberId();
        String user_code = apiData.getUserCode();
        String type_id = apiData.getType_id();
        String searchKey = apiData.getSearchKey();
        final String service = apiData.getService();

        String ticketNumber = apiData.getTicketNumber();
        String phoneNumber = apiData.getMobile_no();
        String nlaReference = apiData.getNlaReference();
        String etzReference = apiData.getEtzReference();
        String newCustomerId = apiData.getNewCustomerId();

        String account = apiData.getAccount();
        String action = apiData.getAction();

        List<WhitelistBlacklistReport> records = new ArrayList<>();

//        log.log(Level.INFO, "USER CODE >>{0}", user_code);
        String userRoles = "";

        if (account == null) {
            account = "";
        } else {
            account = account.trim();
        }
        if (action == null) {
            action = "";
        }
        if (user == null) {
            user = "";
        } else {
            user = user.trim();
        }

//        if (ticketNumber == null) {
//            ticketNumber = "";
//        }
//
//        if (phoneNumber == null) {
//            phoneNumber = "";
//        }
//
//        if (nlaReference == null) {
//            nlaReference = "";
//        }
//
//        if (etzReference == null) {
//            etzReference = "";
//        }
//        if (service.equalsIgnoreCase("updatesearch") && searchKey.trim().isEmpty()) {
//            return records;
//        }
//        if (service.equalsIgnoreCase("updatesearch")) {
//            reference = searchKey.trim();
//        }
        //29012018
//        bank = user_code.equals("000") ? bank : scheme;
//        if (!type_id.isEmpty()) {
////            System.out.println("Type: " + type_id);
//            if (type_id.contains("[44]")) {
//            } else {
//                return records;
//            }
//        } else {
//            return records;
//        }
        System.out.println("SEARCH: " + service);
//        String query = "select reference, ticket_number, code, response, amount_won, amount, fee, fg_error status, fg_referrence, fg_message, bank_code, phone_number, created from etz_nla.nla_record where 1=1 "
//                                //+ " and created between :start_dt and :end_dt  "
//                //+ (service.equalsIgnoreCase("nlaliquidationreport") ? "  AND reference = :reference " : " and created between :start_dt  and :end_dt ")
//                //+ (!user.isEmpty() ? " and update_by like  :user " : "")
//                 + (!ticketNumber.isEmpty() ? " and ticket_number = :ticket_number " : "")
//                 + (!phoneNumber.isEmpty() ? " and phone_number = :phone_number " : "")
//                 + (!nlaReference.isEmpty() ? " and reference = :reference " : "")
//                 + (!etzReference.isEmpty() ? " and fg_referrence = :etzReference " : "")
//                 + " and created between :start_dt and :end_dt  "
//                + " order by created desc";

        String query = "select account, reason, action, authorizer, date_blacklisted, date_whitelisted  from telcodb.aml_blacklist where 1=1 "
                //+ " and created between :start_dt and :end_dt  "
                //+ (service.equalsIgnoreCase("nlaliquidationreport") ? "  AND reference = :reference " : " and created between :start_dt  and :end_dt ")
                //+ (!user.isEmpty() ? " and update_by like  :user " : "")
                //+ (!ticketNumber.isEmpty() ? " and ticket_number = :ticket_number " : "")
                //+ (!phoneNumber.isEmpty() ? " and phone_number = :phone_number " : "")
                //+ (!nlaReference.isEmpty() ? " and reference = :reference " : "")
                + (!account.isEmpty() ? " and account = :account " : "");
        // + (!action.isEmpty() ? " and action = :action " : "")
        //+ (!action.equalsIgnoreCase("ALL") ? " and action = :action " : "")
//                + " and date_blacklisted between :start_dt and :end_dt"
        // // + " and date_whitelisted between :start_dt and :end_dt"
        // + " order by date_blacklisted desc";
        // // + " order by date_whitelisted desc";

        System.out.println("Query >> " + query);
        //Session session = DbHibernate.getSession("40.9MYSQL");
        Session session = DbHibernate.getSession("VASREPROCESS");

//        System.out.println("STARTING ");
        try {

            Query q = session.createNativeQuery(query);

//            q.setParameter("start_dt", (Object) startDate);
//            q.setParameter("end_dt", (Object) endDate);
//            q.setParameter("start_dt", (Object) start_dt);
//            q.setParameter("end_dt", (Object) end_dt);
            //q.setParameter("ticket_number", (Object) ticketNumber);
//            q.setParameter("phoneNumber", (Object) phoneNumber);
//            q.setParameter("nlaReference", (Object) nlaReference);
//            q.setParameter("etzReference", (Object) etzReference);
//            if (!ticketNumber.equals("")) {
//                q.setParameter("ticket_number", (Object) ticketNumber);
//            }
//            
//                        if (!phoneNumber.equals("")) {
//                q.setParameter("phone_number", (Object) phoneNumber);
//            }
//                        
//                        if (!nlaReference.equals("")) {
//                q.setParameter("reference", (Object) nlaReference);
//            }
            if (!account.equals("")) {
                q.setParameter("account", (Object) account);
            }

//                        if (!action.equals("")) {
//                q.setParameter("action", (Object) action);
//            }
//            if (!service.equalsIgnoreCase("nlaliquidationreport")) {
//                q.setParameter("start_dt", (Object) start_dt)
//                        .setParameter("end_dt", (Object) end_dt);
//            } else {
//                q.setParameter("reference", (Object) reference);
//            }
            if (!user.isEmpty()) {
                q.setParameter("user", "%" + user + "%");
            }

            List<Object[]> resp = q.getResultList();
//            System.out.println("DONE");

            for (Object[] record : resp) {
                WhitelistBlacklistReport wbr = new WhitelistBlacklistReport();
                //String bankCode = "";
                //String network = "";

                try {

                    //nlr.setTransid(checkNull(record[0]).trim());
//                    nlr.setSubscriber_id(checkNull(record[1]));
//                    nlr.setUnique_transid(checkNull(record[2]));
//                    nlr.setNewCustomerId(newCustomerId);
//                    nlr.setResponse_date(checkNull(record[3]));
                    wbr.setAccount(checkNull(record[0]).trim());
                    wbr.setReason(checkNull(record[1]));
                    wbr.setAction(checkNull(record[2]));
                    wbr.setAuthorizer(checkNull(record[3]));
                    wbr.setDate_blacklisted(checkNull(record[4]));
                    wbr.setDate_whitelisted(checkNull(record[5]));

                } catch (Exception e) {
                    e.printStackTrace();
                }

                records.add(wbr);

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        System.out.println("RE: " + records);

        return records;
    }

//    public List<NlaLiquidationReport> callInitiateReversal(String jsonString) {
    public String callInsertData(String jsonString) {

        log.log(Level.INFO, "Insert into whitelist blacklist started received >> {0}", jsonString);
        Gson j = new Gson();

        ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);
        String start_dt = apiData.getStartDate();
        //System.out.println("Start Date:"+start_dt);
        String end_dt = apiData.getEndDate();
        // System.out.println("End Date:"+end_dt);
        String username = apiData.getUserName();
        String reference = apiData.getUniqueTransId();
        String user = apiData.getSubscriberId();
        String user_code = apiData.getUserCode();
        String type_id = apiData.getType_id();
        String searchKey = apiData.getSearchKey();
        final String service = apiData.getService();

        String ticketNumber = apiData.getTicketNumber();
        String phoneNumber = apiData.getMobile_no();
        String nlaReference = apiData.getNlaReference();
        String etzReference = apiData.getEtzReference();

        String account = apiData.getAccount();
        String reason = apiData.getReason();
        String action = apiData.getAction();
        String accountType = apiData.getAccountType();

        String data = apiData.getData();
        List<NlaLiquidationReport> recordsList = gson.fromJson(data, new TypeToken<List<NlaLiquidationReport>>() {
        }.getType());

        //String transId = recordsList.get
//        log.log(Level.INFO, "NLA data reversal request received >> {0}", recordsList);
//
//        String transid = recordsList.get(0).getTransid();
//        String subscriberId = recordsList.get(0).getSubscriber_id();
//        String uniqueTransId = recordsList.get(0).getUnique_transid();
//        String responseDate = recordsList.get(0).getResponse_date();
//        String newCustomerId = recordsList.get(0).getNewCustomerId();
//
//        System.out.println("transid" + transid);
//        System.out.println("subscriberId" + subscriberId);
//        System.out.println("uniqueTransId" + uniqueTransId);
//        System.out.println("responseDate" + responseDate);
//        System.out.println("newCustomerId" + newCustomerId);
        System.out.println("account" + account);
        System.out.println("reason" + reason);
        System.out.println("action" + action);
        System.out.println("username" + username);
        System.out.println("accountType" + accountType);

        if (accountType.equals("MOMO")) {
            account = editPhoneNumber(account);
        }

        System.out.println("account" + account);
        System.out.println("reason" + reason);
        System.out.println("action" + action);
        System.out.println("username" + username);
        System.out.println("accountType" + accountType);

        String respData = insertData(account, reason, action, username);

        return respData;
    }

    public String callUpdateData(String jsonString) {

        log.log(Level.INFO, "Update into whitelist blacklist started received >> {0}", jsonString);
        Gson j = new Gson();

        ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);
        String start_dt = apiData.getStartDate();
        //System.out.println("Start Date:"+start_dt);
        String end_dt = apiData.getEndDate();
        // System.out.println("End Date:"+end_dt);
        String username = apiData.getUserName();
        String reference = apiData.getUniqueTransId();
        String user = apiData.getSubscriberId();
        String user_code = apiData.getUserCode();
        String type_id = apiData.getType_id();
        String searchKey = apiData.getSearchKey();
        final String service = apiData.getService();

        String ticketNumber = apiData.getTicketNumber();
        String phoneNumber = apiData.getMobile_no();
        String nlaReference = apiData.getNlaReference();
        String etzReference = apiData.getEtzReference();

//        String account = apiData.getAccount();
//        String reason = apiData.getReason();
//        String action = apiData.getAction();
//        String accountType = apiData.getAccountType();
        String data = apiData.getData();

        System.out.println("data" + data);

        JSONObject json = new JSONObject(data);

//        List<WhitelistBlacklistReport> recordsList = gson.fromJson(data, new TypeToken<List<WhitelistBlacklistReport>>() {
//        }.getType());
        //String transId = recordsList.get
//        log.log(Level.INFO, "NLA data reversal request received >> {0}", recordsList);
//
//        String transid = recordsList.get(0).getTransid();
//        String subscriberId = recordsList.get(0).getSubscriber_id();
//        String uniqueTransId = recordsList.get(0).getUnique_transid();
//        String responseDate = recordsList.get(0).getResponse_date();
//        String newCustomerId = recordsList.get(0).getNewCustomerId();
//
//        System.out.println("transid" + transid);
//        System.out.println("subscriberId" + subscriberId);
//        System.out.println("uniqueTransId" + uniqueTransId);
//        System.out.println("responseDate" + responseDate);
//        System.out.println("newCustomerId" + newCustomerId);
//        String account = recordsList.get(0).getAccount();
//        String action = recordsList.get(0).getAction();
        String account = json.getString("account");
        String action = json.getString("action");

        System.out.println("account" + account);
//        System.out.println("reason" + reason);
        System.out.println("action" + action);
        System.out.println("username" + username);
        //System.out.println("accountType" + accountType);

//        if(accountType.equals("MOMO")){
//         account = editPhoneNumber(account);
//        }
//        System.out.println("account" + account);
//        //System.out.println("reason" + reason);
//        System.out.println("action" + action);
//        System.out.println("username" + username);
        //System.out.println("accountType" + accountType);
        String respData = updateData(account, action, username);
        //String respData = "";


        return respData;
    }

    public static String insertData(String account, String reason, String action, String username) {

        System.out.println("Start insert data into aml table");
        boolean result = false;
        boolean rollback = false;
        String respData = "";

        List<Object[]> resp = new ArrayList<>();

//        System.out.println("UPDATE:: " + reference + " -- " + clientId);
        //UPDATE REFERENCES 
        //String logQry = "INSERT INTO ecarddb.tmc_updatelog(reference, original_respcode, updated_respcode, initiated_by, initiated_date, authorisedp_by, authorised_date) (select reference reference, respcode original_respcode, :respcode updated_respcode,initiated_by, initiated_date, authorised_by, authorised_date from ecarddb.e_tmcrequest where reference = :reference)";
        //String logQry = "INSERT INTO ecarddb.tmc_updatelog(reference, original_respcode, updated_respcode, initiated_by, initiated_date, authorised_by, authorised_date) (select reference reference, response_code original_respcode, :respcode updated_respcode,:initiated_by initiated_by, now() initiated_date,:authorised_by authorised_by, now() authorised_date from ecarddb.e_tmcrequest where reference = :reference)";
        //String logQry = "INSERT INTO ecarddb.tmc_updatelog(reference, original_respcode, updated_respcode, initiated_by, initiated_date, authorised_by, authorised_date) (select reference reference, response_code original_respcode, :respcode updated_respcode,:initiated_by initiated_by, now() initiated_date,:authorised_by authorised_by, now() authorised_date from ecarddb.e_tmcrequest where trans_data = :reference)";
        //String Qry = "INSERT INTO ecarddb.vas_reprocess_log(original_reference, updated_reference, initiated_by, initiated_date, authorised_by, authorised_date, status, account, client, vastype, amount, mobile, transtype) (select :original_reference original_reference, :updated_reference updated_reference, :initiated_by initiated_by, now() initiated_date, :authorised_by authorised_by, now() authorised_date, :status status, :account account, :client client, :vastype vastype, :amount amount, :mobile mobile, :transtype transtype from ecarddb.vas_reprocess_log where original_reference = :original_reference)";
        //String Qry = "INSERT INTO vasdb2.vas_reprocess_log(original_reference, updated_reference, initiated_by, initiated_date, authorised_by, authorised_date, status, account, client, vastype, amount, mobile, transtype)Values(:original_reference, :updated_reference, :initiated_by, now() , :authorised_by, now() , :status, :account, :client, :vastype, :amount, :mobile, :transtype)";
        //String Qry = "INSERT IGNORE INTO telcodb.aml_blacklist(account, reason,action, authorizer, etz_reference, new_customer_id, status)Values(:initiated_by, now() , :trans_id ,:customer_id, :etz_reference, :newCustomer_id, :status)";
        String Qry;

        if (action.equals("BLACKLIST")) {
            Qry = "INSERT IGNORE INTO telcodb.aml_blacklist(account, reason, action, authorizer, date_blacklisted)Values(:account, :reason, :action, :authorizer,  now())";

        } else {
            Qry = "INSERT IGNORE INTO telcodb.aml_blacklist(account, reason, action, authorizer, date_whitelisted)Values(:account, :reason, :action, :authorizer,  now())";

        }

        //String Qry = "INSERT IGNORE INTO banks_direct.nla_reversal_log(initiated_by, initiated_date,trans_id, customer_id, etz_reference, new_customer_id, status)Values(:initiated_by, now() , :trans_id ,:customer_id, :etz_reference, :newCustomer_id, :status)";
        //String tmcqry = "UPDATE ecarddb.e_tmcrequest set respcode =:respcode, processdate = now() where reference = :reference ";
        //String tmcqry = "UPDATE ecarddb.e_tmcrequest set response_code =:respcode where trans_data = :reference ";
        //String fgqry = "SELECT etzref, respcode from fgdb.fundgate_response where etzref =:reference ";
        //String mpqry = "SELECT reference, trnx_status from ussd_db.mpay_report where reference =:reference ";
        Transaction tx = null;
        //Session usession = DbHibernate.getSession("40.9MYSQLW");
        //Session session = DbHibernate.getSession("1.230MYSQL");
        //Session session = DbHibernate.getSession("40.9MYSQLW");
        Session session = DbHibernate.getSession("40.9MYSQLW");

        try {
            tx = session.beginTransaction();
            //log record
            Query q = session.createNativeQuery(Qry);
            //q.setParameter("clientid", clientId);
            q.setParameter("account", account);
            q.setParameter("reason", reason);
            q.setParameter("action", action);
            q.setParameter("authorizer", username);
//            q.setParameter("date_blacklisted", newCustomerId);
//            q.setParameter("date_whitelisted", status);
//            q.setParameter("vastype", vastype);
//            q.setParameter("amount", amount);
//            q.setParameter("mobile", mobile);
//            q.setParameter("transtype", transtype);

            //int h = q.executeUpdate();
            int h = q.executeUpdate();

            System.out.println("H: " + h);

            if (h > 0) {
                respData = "Insert Successful";
            } else {
                respData = "Data already exist";
            }
//            System.out.println("Roll: "+ rollback);
//            if (h > 0) {
//                //update momo
//                //q = session.createNativeQuery(tmcqry);
//                //q.setParameter("clientid", clientId);
//                q.setParameter("respcode", "00");
//                //q.setParameter("narration", narration);
//                //q.setParameter("reference", reference);
//
//                int i = q.executeUpdate();
//                rollback = false;
//                System.out.println("I: " + i);
////                System.out.println("Roll: "+ rollback);
//
////                if (i > 0) {
////
////                    if (reference.toUpperCase().startsWith("09FG")) {
////                        q = session.createNativeQuery(fgqry).setParameter("reference", reference);
////
////                        List<Object[]> res = q.getResultList();
////                        String rec = "";
////                        for (Object[] record : res) {
////                            rec = record[0].toString();
////                            System.out.println("RES:: " + rec);
////                        }
////
////                        String sql1 = "";
////                        if (!rec.isEmpty()) {
////                            sql1 = "update fgdb.fundgate_response set respMessage =concat(:respMessage,' - ',respMessage) ,respcode =:respcode where etzref =:reference ";
////                        } else {
////                            sql1 = "insert into fgdb.fundgate_response(action, terminal, respMessage, clientRef, respcode,  etzref, created) (select action, terminal, :respMessage respMessage, clientRef, :respcode respcode, :reference etzref, created from fgdb.fundgate_request where etzref=:reference)";
////                        }
////                        q = usession.createNativeQuery(sql1);
////
////                        q.setParameter("respcode", fgRespcode);
////                        q.setParameter("respMessage", fgRespcode + "::Transaction " + status + (status.equalsIgnoreCase("successful") ? " Ref: " + reference + "|[" + clientId + "]" : ""));
////                        q.setParameter("reference", reference);
////
////                        int j = q.executeUpdate();
////
//////                        System.out.println("j: "+ j);
//////                        System.out.println("Roll: "+ rollback);
////                        if (j > 0) {
////                            rollback = false;
////                        }
//////                        System.out.println("Rollf: "+ rollback);
////
////                    } else if (reference.toUpperCase().startsWith("02MPAY")) {
////                        String sql1 = "";
////                        q = session.createNativeQuery(mpqry).setParameter("reference", reference);
////
////                        List<Object[]> res = q.getResultList();
////                        String rec = "";
////                        for (Object[] record : res) {
////                            rec = record[0].toString();
////                            System.out.println("RES:: " + rec);
////                        }
////
////                        if (!rec.isEmpty()) {
////                            sql1 = "update ussd_db.mpay_report set trnx_status_msg = concat(:respMessage,' - ',trnx_status_msg) ,trnx_status =:respcode where etzref =:reference ";
////
////                            q = session.createNativeQuery(sql1);
////
////                            q.setParameter("respcode", fgRespcode);
////                            q.setParameter("respMessage", fgRespcode + "::Transaction " + status);
////                            q.setParameter("reference", reference);
////
////                            int j = q.executeUpdate();
////
//////                        System.out.println("j: "+ j);
//////                        System.out.println("Roll: "+ rollback);
////                            if (j > 0) {
////                                rollback = false;
////                            }
//////                        System.out.println("Rollf: "+ rollback);
////                        }
////
////                    } else {
////                        rollback = false;
////                    }
////                }
//            }

            System.out.println("Roll: " + rollback);
            if (!rollback) {

                //insert into AuditTrail
                //log record
                tx.commit();
                result = true;
//                new Thread(() -> {
//                    AuditTrail audit = new AuditTrail(userId, "Tmc update for " + reference + " to clientId: " + clientId + " and responseCode: " + respcode + " and narration: " + narration, "update", null, "TMC Status Update", ipAddress);
//                    new AuditDao().insertIntoAuditTrail(audit);
//                }).start();

            } else if (tx.isActive()) {
                try {
                    tx.rollback();
                } catch (Exception er) {
                    er.printStackTrace();
                }
            }

        } catch (Exception et) {
            et.printStackTrace();
            if (tx != null) {
                try {
                    tx.rollback();
                } catch (Exception er) {

                }
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return respData;
    }


    public static String updateData(String account, String action, String username) {

        System.out.println("Start insert data into aml table");
        boolean result = false;
        boolean rollback = false;
        String respData = "";

        List<Object[]> resp = new ArrayList<>();

//        System.out.println("UPDATE:: " + reference + " -- " + clientId);
        //UPDATE REFERENCES 
        //String logQry = "INSERT INTO ecarddb.tmc_updatelog(reference, original_respcode, updated_respcode, initiated_by, initiated_date, authorisedp_by, authorised_date) (select reference reference, respcode original_respcode, :respcode updated_respcode,initiated_by, initiated_date, authorised_by, authorised_date from ecarddb.e_tmcrequest where reference = :reference)";
        //String logQry = "INSERT INTO ecarddb.tmc_updatelog(reference, original_respcode, updated_respcode, initiated_by, initiated_date, authorised_by, authorised_date) (select reference reference, response_code original_respcode, :respcode updated_respcode,:initiated_by initiated_by, now() initiated_date,:authorised_by authorised_by, now() authorised_date from ecarddb.e_tmcrequest where reference = :reference)";
        //String logQry = "INSERT INTO ecarddb.tmc_updatelog(reference, original_respcode, updated_respcode, initiated_by, initiated_date, authorised_by, authorised_date) (select reference reference, response_code original_respcode, :respcode updated_respcode,:initiated_by initiated_by, now() initiated_date,:authorised_by authorised_by, now() authorised_date from ecarddb.e_tmcrequest where trans_data = :reference)";
        //String Qry = "INSERT INTO ecarddb.vas_reprocess_log(original_reference, updated_reference, initiated_by, initiated_date, authorised_by, authorised_date, status, account, client, vastype, amount, mobile, transtype) (select :original_reference original_reference, :updated_reference updated_reference, :initiated_by initiated_by, now() initiated_date, :authorised_by authorised_by, now() authorised_date, :status status, :account account, :client client, :vastype vastype, :amount amount, :mobile mobile, :transtype transtype from ecarddb.vas_reprocess_log where original_reference = :original_reference)";
        //String Qry = "INSERT INTO vasdb2.vas_reprocess_log(original_reference, updated_reference, initiated_by, initiated_date, authorised_by, authorised_date, status, account, client, vastype, amount, mobile, transtype)Values(:original_reference, :updated_reference, :initiated_by, now() , :authorised_by, now() , :status, :account, :client, :vastype, :amount, :mobile, :transtype)";
        //String Qry = "INSERT IGNORE INTO telcodb.aml_blacklist(account, reason,action, authorizer, etz_reference, new_customer_id, status)Values(:initiated_by, now() , :trans_id ,:customer_id, :etz_reference, :newCustomer_id, :status)";
//         String Qry;
//         
//        if(action.equals("BLACKLIST")){
//              Qry = "INSERT IGNORE INTO telcodb.aml_blacklist(account, reason, action, authorizer, date_blacklisted)Values(:account, :reason, :action, :authorizer,  now())";
//
//        }else{
//            Qry = "INSERT IGNORE INTO telcodb.aml_blacklist(account, reason, action, authorizer, date_whitelisted)Values(:account, :reason, :action, :authorizer,  now())";
//
//        }
        String Qry = "UPDATE telcodb.aml_blacklist set action =:action  where account = :account ";

        //String Qry = "INSERT IGNORE INTO banks_direct.nla_reversal_log(initiated_by, initiated_date,trans_id, customer_id, etz_reference, new_customer_id, status)Values(:initiated_by, now() , :trans_id ,:customer_id, :etz_reference, :newCustomer_id, :status)";
        //String tmcqry = "UPDATE ecarddb.e_tmcrequest set respcode =:respcode, processdate = now() where reference = :reference ";
        //String tmcqry = "UPDATE ecarddb.e_tmcrequest set response_code =:respcode where trans_data = :reference ";
        //String fgqry = "SELECT etzref, respcode from fgdb.fundgate_response where etzref =:reference ";
        //String mpqry = "SELECT reference, trnx_status from ussd_db.mpay_report where reference =:reference ";
        Transaction tx = null;

        Session session = DbHibernate.getSession("40.9MYSQLW");

        try {
            tx = session.beginTransaction();
            //log record
            Query q = session.createNativeQuery(Qry);
            //q.setParameter("clientid", clientId);
            q.setParameter("account", account);
            //q.setParameter("reason", reason);
            q.setParameter("action", action);
            //q.setParameter("authorizer", username);
//            q.setParameter("date_blacklisted", newCustomerId);
//            q.setParameter("date_whitelisted", status);
//            q.setParameter("vastype", vastype);
//            q.setParameter("amount", amount);
//            q.setParameter("mobile", mobile);
//            q.setParameter("transtype", transtype);

            //int h = q.executeUpdate();
            int h = q.executeUpdate();

            System.out.println("H: " + h);

            if (h > 0) {
                respData = "Update Successful";
            } else {
                respData = "Update not successful";
            }
//            System.out.println("Roll: "+ rollback);
//            if (h > 0) {
//                //update momo
//                //q = session.createNativeQuery(tmcqry);
//                //q.setParameter("clientid", clientId);
//                q.setParameter("respcode", "00");
//                //q.setParameter("narration", narration);
//                //q.setParameter("reference", reference);
//
//                int i = q.executeUpdate();
//                rollback = false;
//                System.out.println("I: " + i);
////                System.out.println("Roll: "+ rollback);
//
////                if (i > 0) {
////
////                    if (reference.toUpperCase().startsWith("09FG")) {
////                        q = session.createNativeQuery(fgqry).setParameter("reference", reference);
////
////                        List<Object[]> res = q.getResultList();
////                        String rec = "";
////                        for (Object[] record : res) {
////                            rec = record[0].toString();
////                            System.out.println("RES:: " + rec);
////                        }
////
////                        String sql1 = "";
////                        if (!rec.isEmpty()) {
////                            sql1 = "update fgdb.fundgate_response set respMessage =concat(:respMessage,' - ',respMessage) ,respcode =:respcode where etzref =:reference ";
////                        } else {
////                            sql1 = "insert into fgdb.fundgate_response(action, terminal, respMessage, clientRef, respcode,  etzref, created) (select action, terminal, :respMessage respMessage, clientRef, :respcode respcode, :reference etzref, created from fgdb.fundgate_request where etzref=:reference)";
////                        }
////                        q = usession.createNativeQuery(sql1);
////
////                        q.setParameter("respcode", fgRespcode);
////                        q.setParameter("respMessage", fgRespcode + "::Transaction " + status + (status.equalsIgnoreCase("successful") ? " Ref: " + reference + "|[" + clientId + "]" : ""));
////                        q.setParameter("reference", reference);
////
////                        int j = q.executeUpdate();
////
//////                        System.out.println("j: "+ j);
//////                        System.out.println("Roll: "+ rollback);
////                        if (j > 0) {
////                            rollback = false;
////                        }
//////                        System.out.println("Rollf: "+ rollback);
////
////                    } else if (reference.toUpperCase().startsWith("02MPAY")) {
////                        String sql1 = "";
////                        q = session.createNativeQuery(mpqry).setParameter("reference", reference);
////
////                        List<Object[]> res = q.getResultList();
////                        String rec = "";
////                        for (Object[] record : res) {
////                            rec = record[0].toString();
////                            System.out.println("RES:: " + rec);
////                        }
////
////                        if (!rec.isEmpty()) {
////                            sql1 = "update ussd_db.mpay_report set trnx_status_msg = concat(:respMessage,' - ',trnx_status_msg) ,trnx_status =:respcode where etzref =:reference ";
////
////                            q = session.createNativeQuery(sql1);
////
////                            q.setParameter("respcode", fgRespcode);
////                            q.setParameter("respMessage", fgRespcode + "::Transaction " + status);
////                            q.setParameter("reference", reference);
////
////                            int j = q.executeUpdate();
////
//////                        System.out.println("j: "+ j);
//////                        System.out.println("Roll: "+ rollback);
////                            if (j > 0) {
////                                rollback = false;
////                            }
//////                        System.out.println("Rollf: "+ rollback);
////                        }
////
////                    } else {
////                        rollback = false;
////                    }
////                }
//            }

            System.out.println("Roll: " + rollback);
            if (!rollback) {

                //insert into AuditTrail
                //log record
                tx.commit();
                result = true;
//                new Thread(() -> {
//                    AuditTrail audit = new AuditTrail(userId, "Tmc update for " + reference + " to clientId: " + clientId + " and responseCode: " + respcode + " and narration: " + narration, "update", null, "TMC Status Update", ipAddress);
//                    new AuditDao().insertIntoAuditTrail(audit);
//                }).start();

            } else if (tx.isActive()) {
                try {
                    tx.rollback();
                } catch (Exception er) {
                    er.printStackTrace();
                }
            }

        } catch (Exception et) {
            et.printStackTrace();
            if (tx != null) {
                try {
                    tx.rollback();
                } catch (Exception er) {

                }
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return respData;
    }


//        public List<TmcUpdate> getTmcUpdateLog(String jsonString) {
//
//        log.log(Level.INFO, "TMC update log request received >> {0}", jsonString);
//        Gson j = new Gson();
//
//        ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);
//        String start_dt = apiData.getStartDate();
//        String end_dt = apiData.getEndDate();
//        String reference = apiData.getUniqueTransId();
//        String user = apiData.getSubscriberId();
//        String user_code = apiData.getUserCode();
//        String type_id = apiData.getType_id();
//        String searchKey = apiData.getSearchKey();
//        final String service = apiData.getService();
//
//        List<TmcUpdate> records = new ArrayList<>();
//
////        log.log(Level.INFO, "USER CODE >>{0}", user_code);
//        String userRoles = "";
//
//        if (reference == null) {
//            reference = "";
//        } else {
//            reference = reference.trim();
//        }
//        if (searchKey == null) {
//            searchKey = "";
//        }
//        if (user == null) {
//            user = "";
//        } else {
//            user = user.trim();
//        }
//
//        if (service.equalsIgnoreCase("updatesearch") && searchKey.trim().isEmpty()) {
//            return records;
//        }
//
//        if (service.equalsIgnoreCase("updatesearch")) {
//            reference = searchKey.trim();
//        }
//
//        //29012018
////        bank = user_code.equals("000") ? bank : scheme;
//        if (!type_id.isEmpty()) {
////            System.out.println("Type: " + type_id);
//            if (type_id.contains("[64]")) {
//            } else {
//                return records;
//            }
//        } else {
//            return records;
//        }
//        System.out.println("SEARCH: " + service);
//        String query = "select reference, original_respcode, updated_respcode, initiated_by, initiated_date, authorised_by, authorised_date from telcodb.tmc_updatelog where 1=1 "
//                //                + " and a.trnxdate between :start_dt and :end_dt  "
//                + (service.equalsIgnoreCase("updatesearch") ? "  AND reference = :reference " : " and authorised_date between :start_dt  and :end_dt ")
//                + (!user.isEmpty() ? " and update_by like  :user " : "")
//                + " order by update_date desc";
//
////        Session session = DbHibernate.getSession("40.9MYSQL");
//Session session = DbHibernate.getSession("1.230MYSQL");
//
////        System.out.println("STARTING ");
//        try {
//
//            Query q = session.createNativeQuery(query);
//
////            q.setParameter("beginDate", (Object) beginDate);
////            q.setParameter("endDate", (Object) endDate);
//            if (!service.equalsIgnoreCase("updatesearch")) {
//                q.setParameter("start_dt", (Object) start_dt)
//                        .setParameter("end_dt", (Object) end_dt);
//            } else {
//                q.setParameter("reference", (Object) reference);
//            }
//
//            if (!user.isEmpty()) {
//                q.setParameter("user", "%" + user + "%");
//            }
//
//            List<Object[]> resp = q.getResultList();
////            System.out.println("DONE");
//
//            for (Object[] record : resp) {
//                TmcUpdate tmc = new TmcUpdate();
//                try {
//                    tmc.setReference(checkNull(record[0]));
////                    tmc.setOriginal_clientid(checkNull(record[1]));
////                    tmc.setUpdated_clientid(checkNull(record[2]));
////                    tmc.setOriginal_respcode(checkNull(record[3]));
////                    tmc.setUpdated_respcode(checkNull(record[4]));
////                    tmc.setOriginal_narration(checkNull(record[5]));
////                    tmc.setUpdated_narration(checkNull(record[6]));
////                    tmc.setUpdate_by(checkNull(record[7]));
////                    tmc.setUpdate_date(checkNull(record[8]));
//                      tmc.setOriginal_respcode(checkNull(record[1]));
//                      tmc.setUpdated_respcode(checkNull(record[2]));
//                      tmc.setInitiated_by(checkNull(record[3]));
//                      tmc.setInitiated_date(checkNull(record[4]));
//                      tmc.setAuthorised_by(checkNull(record[5]));
//                      tmc.setAuthorised_date(checkNull(record[6]));
//                      
//                      
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//                records.add(tmc);
//
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (session != null) {
//                session.close();
//            }
//        }
//        System.out.println("RE: " + records);
//
//        return records;
//    }
//    @POST
//    @Path("trx/find")
//    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
//    public List<TMC> searchTmcTransactions(String jsonString) throws ParseException {
//
//        System.out.println("TMC search request received >> " + jsonString);
//        Gson j = new Gson();
//        ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);
//
//        String trans_data = apiData.getSearchKey();
//        String userCode = apiData.getUserCode();
//        String type_id = apiData.getType_id();
//        String bankCode = apiData.getBank_code();
//        String query = "";
//
//        if (trans_data == null) {
//            trans_data = "";
//        }
//
//        if (trans_data.equals("")) {
//            return Collections.EMPTY_LIST;
//        }
//
//        String inconIds = "";
//        System.out.println("User : " + userCode);
//        if (!type_id.isEmpty()) {
//            if (type_id.contains("[0]")) {
//            } else if (type_id.contains("[6]")) {
//                System.out.println("bank: " + bankCode);
//                inconIds = appDao.getInconId(bankCode);
//            } else {
//                return Collections.EMPTY_LIST;
//            }
//        } else {
//            return Collections.EMPTY_LIST;
//        }
//
//        if (type_id.contains("[0]")) {
//            query = "select mti, trans_data, acct_id1, acct_id2, pro_code as transtype, trans_date, pro_code, pan, amount, "
//                    + "track2, response_code, response_code as response,  terminal_id, stan, card_acc_name, fee, reference, "
//                    + "src_node, target_node, response_time from ecarddb.e_tmcrequest where trans_data = :trans_data order by trans_date desc";
//        } else if (type_id.contains("[6]")) {
//            query = "select mti, trans_data, acct_id1, acct_id2, pro_code as transtype, trans_date, pro_code, pan, amount, "
//                    + "track2, response_code, response_code as response,  terminal_id, stan, card_acc_name, fee, reference, "
//                    + "src_node, target_node, response_time from ecarddb.e_tmcrequest where (src_node in (" + inconIds + ") or target_node in (" + inconIds + ")) "
//                    + "and trans_data = :trans_data order by trans_date desc";
//        }
//
//        System.out.println("Query >> " + query);
//        List<TMC> recordsList = new ArrayList<>();
//        q = em.createNativeQuery(query).setParameter("trans_data", trans_data);
//        List<Object[]> records = q.getResultList();
//
//        for (Object[] record : records) {
//            TMC tmc = new TMC();
//
//            tmc.setMti(checkNull(record[0]));
//            tmc.setTrans_data(checkNull(record[1]));
//            tmc.setSource_account(checkNull(record[2]));
//            tmc.setDestination_account(checkNull(record[3]));
//            tmc.setTranstype(checkNull(record[4]));
//            tmc.setTrans_date(convertDate(checkNull(record[5])));
//            tmc.setPro_code(checkNull(record[6]));
//            tmc.setPan(checkNull(record[7]));
//            tmc.setAmount(BigDecimal.valueOf(Double.valueOf(checkNull(record[8]))));
//            tmc.setTrack2(checkNull(record[9]));
//            tmc.setResponse_code(checkNull(record[10]));
//            tmc.setResponse(checkNull(record[11]));
//            tmc.setTerminal_id(checkNull(record[12]));
//            tmc.setStan(checkNull(record[13]));
//            tmc.setCard_acc_name(checkNull(record[14]));
//            tmc.setFee(BigDecimal.valueOf(Double.valueOf(checkNull(record[15]))));
//            tmc.setReference(checkNull(record[16]));
//            tmc.setSrc_node(checkNull(record[17]));
//            tmc.setTarget_node(checkNull(record[18]));
//            tmc.setResponse_time(convertDate(checkNull(record[19])));
//            recordsList.add(tmc);
//        }
////            System.out.println("Records: " + recordsList);
//        return recordsList;
//    }
    protected String checkNull(Object Data) {
        if (Data != null && !Data.equals("")) {
            return Data.toString();
        }
        return "NULL";
    }

    protected Date convertDate(String Data) throws ParseException {
        if (!Data.equals("NULL")) {
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(Data);

            return date;
        }
        return null;
    }

    protected String hashAccount(String accNo, Boolean hash) {

        if (accNo == null || accNo.equalsIgnoreCase("null") || accNo.trim().isEmpty() || !hash) {
        } else {
            int start = accNo.length() / 2;
            HashNumber hn = new HashNumber();
            // if (accNo < )
            accNo = hn.hashStringValue(accNo, start / 2, start / 2);
        }
        return accNo;
    }
//
//    public Map<Integer,String> getNodes() {
//        String query = "select incon_id as id , incon_name as name from ecarddb.e_tmcnode order by incon_name asc";
//
//        Session session = DbHibernate.getSession("40.15MYSQL");
//        List<Object[]> noderecords = new ArrayList<>();
//        try {
//            Query q = session.createNativeQuery(query);
//            noderecords = q.getResultList();
//        } catch (Exception e) {
//
//        } finally {
//            if (session != null) {
//                try {
//                    session.close();
//                } catch (Exception r) {
//
//                }
//            }
//        }
//        return noderecords;
//    }

    public static String mapTranstype(String b) {
        String transtype = "";

        switch (b.substring(0, 2)) {

            case "00":
                transtype = "Purchase";
                break;
            case "01":
                transtype = "Cash Withdrawal";
                break;

            case "31":
                transtype = "Balance Enquiry";
                break;

            case "38":
                transtype = "Mini Statement";
                break;
            case "42":
                transtype = "Funds Transfer";
                break;

            case "53":
                transtype = "Payments";
                break;

            case "13":
                transtype = "Payments";
                break;

            case "92":
                transtype = "Pin Change";
                break;

            case "95":
                transtype = "Account Query";
                break;

            default:
                transtype = "";
                break;
        }
        return transtype;
    }

    public String editPhoneNumber(String phoneNumber) {

        String withPrefix = "";

        if (phoneNumber.startsWith("0")) {
            withPrefix = "233" + phoneNumber.substring(1);
        } else {
            withPrefix = phoneNumber;
        }

        return withPrefix;

    }

}
