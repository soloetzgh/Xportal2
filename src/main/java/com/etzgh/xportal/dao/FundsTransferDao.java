/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etzgh.xportal.dao;

import com.etzgh.xportal.cdi.UserSession;
import static com.etzgh.xportal.dao.NlaLiquidationReportDao.updateReversalData;
import com.etzgh.xportal.model.Account;
import com.etzgh.xportal.model.ApiPostData;
import com.etzgh.xportal.model.Bank;
import com.etzgh.xportal.model.FundsTransfer;
import com.etzgh.xportal.model.GIPTransaction;
import com.etzgh.xportal.model.NODES;
import com.etzgh.xportal.model.NlaLiquidationReport;
import com.etzgh.xportal.service.UserProfileService;
import com.etzgh.xportal.utility.AutoRequest;

import com.etzgh.xportal.utility.Config;
import com.etzgh.xportal.utility.DbHibernate;
import com.etzgh.xportal.utility.DoHTTPRequest;
import com.etzgh.xportal.utility.GeneralUtility;
import com.etzgh.xportal.utility.HashNumber;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.json.JSONObject;
//import org.apache.log4j.Logger;

/**
 *
 * @author yaw.owusu-koranteng
 */
public class FundsTransferDao {

    private static final AppDao appDao = new AppDao();
    private static final GeneralUtility utility = new GeneralUtility();
    private static final Gson gson = new Gson();
    private static final Logger log = Logger.getLogger(FundsTransferDao.class.getName());
    private static Map<String, String> banks = new HashMap<>();
    private static final Config config = new Config();

    private static final String ERROR = "ERROR";

    public static void main(String[] args) {
        String r = "{\"merchant\":\"\",\"product\":\"\",\"accountNumber\":\"\",\"service\":\"search\",\"action\":\"\",\"apiSecret\":\"\",\"apiCode\":\"\",\"userCode\":\"[2000000000000060]|905,[2500000000000050]|77\",\"admin\":\"\",\"type_desc\":\"\",\"role_id\":\"\",\"type_id\":\"[4],[5],[6],[7],[20],[24],[39],[40]\",\"searchKey\":\"02RS42603E77662\",\"userName\":\"\",\"ClientId\":\"\",\"user_id\":\"10500000000000754\",\"pageNumber\":1,\"rowsPerPage\":1,\"trans_code\":\"\",\"bank_code\":\"905\",\"subscriberId\":\"\",\"trans_status\":\"\",\"bank\":\"\",\"biller\":\"\",\"transId\":\"\",\"bankid\":\"\",\"card_num\":\"\",\"payType\":\"\",\"wcMerchant\":\"\",\"lineType\":\"\",\"trans_channel\":\"\",\"terminal_id\":\"\",\"card_account\":\"\",\"cop_issuercode\":\"\",\"cop_role_id\":\"\",\"cop_company_id\":\"\",\"mobile_no\":\"\",\"phone\":\"\",\"type\":\"\",\"amount\":\"\",\"amount2\":\"\",\"refundStatus\":\"\",\"partialReversal\":\"\",\"reversalReason\":\"\",\"ipAddress\":\"0:0:0:0:0:0:0:1\",\"userData\":\"\",\"appId\":[],\"company\":\"\",\"options\":{}}";
        //new TmcDao().getTmcTransactions(r);
        new FundsTransferDao().getTransaction(r);
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

    public List<FundsTransfer> getTransaction(String jsonString) {

        log.log(Level.INFO, "Funds Transfer trans request received >> {0}", jsonString);
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

        List<FundsTransfer> records = new ArrayList<>();

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
                FundsTransfer ft = new FundsTransfer();
                //String bankCode = "";
                //String network = "";

                try {

                    //nlr.setTransid(checkNull(record[0]).trim());
//                    nlr.setSubscriber_id(checkNull(record[1]));
//                    nlr.setUnique_transid(checkNull(record[2]));
//                    nlr.setNewCustomerId(newCustomerId);
//                    nlr.setResponse_date(checkNull(record[3]));
//                    wbr.setAccount(checkNull(record[0]).trim());
//                    wbr.setReason(checkNull(record[1]));
//                    wbr.setAction(checkNull(record[2]));
//                    wbr.setAuthorizer(checkNull(record[3]));
//                    wbr.setDate_blacklisted(checkNull(record[4]));
//                    wbr.setDate_whitelisted(checkNull(record[5]));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                records.add(ft);

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

    public List<FundsTransfer> getFundsTransferTransaction(String jsonString) {

        log.info("NLA trans authorize request received >> {0}" + jsonString);
        Gson j = new Gson();

        ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);
        String start_dt = apiData.getStartDate();
        // System.out.println("Start Date:"+start_dt);
        String end_dt = apiData.getEndDate();
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
        //System.out.println("etzReference" + etzReference);

        String transactionReference = apiData.getTransactionReference();

        List<FundsTransfer> records = new ArrayList<>();

        // log.info( "USER CODE >>{0}", user_code);
        String userRoles = "";

        if (reference == null) {
            reference = "";
        } else {
            reference = reference.trim();
        }
        if (searchKey == null) {
            searchKey = "";
        }
        if (user == null) {
            user = "";
        } else {
            user = user.trim();
        }

        // if (ticketNumber == null) {
        // ticketNumber = "";
        // }
        //
        // if (phoneNumber == null) {
        // phoneNumber = "";
        // }
        //
        // if (nlaReference == null) {
        // nlaReference = "";
        // }
        if (etzReference == null) {
            etzReference = "";
        }

        // if (service.equalsIgnoreCase("updatesearch") && searchKey.trim().isEmpty()) {
        // return records;
        // }
        // if (service.equalsIgnoreCase("updatesearch")) {
        // reference = searchKey.trim();
        // }
        // 29012018
        // bank = user_code.equals("000") ? bank : scheme;
        // if (!type_id.isEmpty()) {
        //// System.out.println("Type: " + type_id);
        // if (type_id.contains("[44]")) {
        // } else {
        // return records;
        // }
        // } else {
        // return records;
        // }
        System.out.println("SEARCH: " + service);
        // String query = "select reference, ticket_number, code, response, amount_won,
        // amount, fee, fg_error status, fg_referrence, fg_message, bank_code,
        // phone_number, created from etz_nla.nla_record where 1=1 "
        // //+ " and created between :start_dt and :end_dt "
        // //+ (service.equalsIgnoreCase("nlaliquidationreport") ? " AND reference =
        // :reference " : " and created between :start_dt and :end_dt ")
        // //+ (!user.isEmpty() ? " and update_by like :user " : "")
        // + (!ticketNumber.isEmpty() ? " and ticket_number = :ticket_number " : "")
        // + (!phoneNumber.isEmpty() ? " and phone_number = :phone_number " : "")
        // + (!nlaReference.isEmpty() ? " and reference = :reference " : "")
        // + (!etzReference.isEmpty() ? " and fg_referrence = :etzReference " : "")
        // + " and created between :start_dt and :end_dt "
        // + " order by created desc";

        String query = "select transaction_reference, source_card, amount, destination_bank, network, bank, destination_account, narration, status, response_code, response_message, initiated_by, initiated_date, authorised_by, authorised_date  from banks_direct.fund_transfer_log where 1=1 "
                // + " and created between :start_dt and :end_dt"
                // + (service.equalsIgnoreCase("nlaliquidationreport") ? " AND reference =
                // :reference " : " and created between :start_dt and :end_dt ")
                // + (!user.isEmpty() ? " and update_by like :user " : "")
                // + (!ticketNumber.isEmpty() ? " and ticket_number = :ticket_number " : "")
                // + (!phoneNumber.isEmpty() ? " and phone_number = :phone_number " : "")
                // + (!nlaReference.isEmpty() ? " and reference = :reference " : "")
                + (!transactionReference.isEmpty() ? " and transaction_reference = :transaction_reference " : "")
                + " and initiated_date between :start_dt and :end_dt  "
                + " order by initiated_date desc";

        // String query = "select initiated_by, initiated_date, authorised_by,
        // authorised_date,trans_id, customer_id, etz_reference, new_customer_id, code,
        // transaction_reference, reversal_reference,response, status from
        // banks_direct.nla_reversal_log where 1=1 "
        // //+ " and created between :start_dt and :end_dt"
        // //+ (service.equalsIgnoreCase("nlaliquidationreport") ? " AND reference =
        // :reference " : " and created between :start_dt and :end_dt ")
        // //+ (!user.isEmpty() ? " and update_by like :user " : "")
        // // + (!ticketNumber.isEmpty() ? " and ticket_number = :ticket_number " : "")
        // // + (!phoneNumber.isEmpty() ? " and phone_number = :phone_number " : "")
        // // + (!nlaReference.isEmpty() ? " and reference = :reference " : "")
        // + (!etzReference.isEmpty() ? " and etz_reference = :etzReference " : "")
        // + " and initiated_date between :start_dt and :end_dt "
        // + " order by initiated_date desc";
        System.out.println("Query >> " + query);
        // Session session = DbHibernate.getSession("40.9MYSQLW");
        //Session session = DbHibernate.getSession("40.9MYSQL");
        Session session = DbHibernate.getSession("30.19MYSQL");

        // System.out.println("STARTING ");
        try {

            Query q = session.createNativeQuery(query);

            // q.setParameter("start_dt", (Object) startDate);
            // q.setParameter("end_dt", (Object) endDate);
            q.setParameter("start_dt", (Object) start_dt);
            q.setParameter("end_dt", (Object) end_dt);

            // q.setParameter("ticket_number", (Object) ticketNumber);
            // q.setParameter("phoneNumber", (Object) phoneNumber);
            // q.setParameter("nlaReference", (Object) nlaReference);
            // q.setParameter("etzReference", (Object) etzReference);
            // if (!ticketNumber.equals("")) {
            // q.setParameter("ticket_number", (Object) ticketNumber);
            // }
            // if (!phoneNumber.equals("")) {
            // q.setParameter("phone_number", (Object) phoneNumber);
            // }
            //
            // if (!nlaReference.equals("")) {
            // q.setParameter("reference", (Object) nlaReference);
            // }
            if (!transactionReference.equals("")) {
                q.setParameter("transaction_reference", (Object) transactionReference);
            }

            // if (!service.equalsIgnoreCase("nlaliquidationreport")) {
            // q.setParameter("start_dt", (Object) start_dt)
            // .setParameter("end_dt", (Object) end_dt);
            // } else {
            // q.setParameter("reference", (Object) reference);
            // }
            // if (!user.isEmpty()) {
            // q.setParameter("user", "%" + user + "%");
            // }
            List<Object[]> resp = q.getResultList();
            System.out.println("Resp" + resp);
            System.out.println("DONE");

            for (Object[] record : resp) {
                FundsTransfer ft = new FundsTransfer();
//                String bankCode = "";
//                String network = "";

                try {

                    ft.setTransaction_reference(checkNull(record[0]));
                    ft.setSource_card(checkNull(record[1]));
                    ft.setAmount(checkNull(record[2]));
                    ft.setDestination_bank(checkNull(record[3]));
                    ft.setNetwork(checkNull(record[4]));
                    ft.setBank(checkNull(record[5]));
                    ft.setDestination_account(checkNull(record[6]));
                    ft.setNarration(checkNull(record[7]));
                    ft.setStatus(checkNull(record[8]));
                    ft.setResponse_code(checkNull(record[9]));
                    ft.setResponse_message(checkNull(record[10]));
                    ft.setInitiated_by(checkNull(record[11]));
                    ft.setInitiated_date(checkNull(record[12]));
                    ft.setAuthorized_by(checkNull(record[13]));
                    ft.setAuthorized_date(checkNull(record[14]));

//                    ft.setInitiated_by(checkNull(record[0]));
//                    
//
//                    nlr.setInitiated_by(checkNull(record[0]));
//                    nlr.setInitiated_date(checkNull(record[1]));
//                    nlr.setAuthorised_by(checkNull(record[2]));
//                    nlr.setAuthorised_date(checkNull(record[3]));
//                    nlr.setTransid(checkNull(record[4]));
//                    nlr.setSubscriber_id(checkNull(record[5]));
//                    nlr.setUnique_transid(checkNull(record[6]));
//                    nlr.setNewCustomerId(checkNull(record[7]));
//                    nlr.setCode(checkNull(record[8]));
//                    nlr.setTransactionReference(checkNull(record[9]));
//                    nlr.setReversal_reference(checkNull(record[10]));
//                    nlr.setResponse(checkNull(record[11]));
//                    nlr.setStatus(checkNull(record[12]));
                } catch (Exception e) {
                    //log.error(e.getMessage(), e);
                    e.printStackTrace();

                }

                records.add(ft);

            }

        } catch (Exception e) {
            //log.error(e.getMessage(), e);
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        System.out.println("RE: " + records);

        return records;
    }

    public List<FundsTransfer> getFundsTransferTransactionAll(String jsonString) {

        log.info("NLA trans authorize request received >> {0}" + jsonString);
        Gson j = new Gson();

        ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);
        String start_dt = apiData.getStartDate();
        // System.out.println("Start Date:"+start_dt);
        String end_dt = apiData.getEndDate();
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
        //System.out.println("etzReference" + etzReference);

        String transactionReference = apiData.getTransactionReference();

        List<FundsTransfer> records = new ArrayList<>();

        // log.info( "USER CODE >>{0}", user_code);
        String userRoles = "";

        if (reference == null) {
            reference = "";
        } else {
            reference = reference.trim();
        }
        if (searchKey == null) {
            searchKey = "";
        }
        if (user == null) {
            user = "";
        } else {
            user = user.trim();
        }

        // if (ticketNumber == null) {
        // ticketNumber = "";
        // }
        //
        // if (phoneNumber == null) {
        // phoneNumber = "";
        // }
        //
        // if (nlaReference == null) {
        // nlaReference = "";
        // }
        if (etzReference == null) {
            etzReference = "";
        }

        // if (service.equalsIgnoreCase("updatesearch") && searchKey.trim().isEmpty()) {
        // return records;
        // }
        // if (service.equalsIgnoreCase("updatesearch")) {
        // reference = searchKey.trim();
        // }
        // 29012018
        // bank = user_code.equals("000") ? bank : scheme;
        // if (!type_id.isEmpty()) {
        //// System.out.println("Type: " + type_id);
        // if (type_id.contains("[44]")) {
        // } else {
        // return records;
        // }
        // } else {
        // return records;
        // }
        System.out.println("SEARCH: " + service);
        // String query = "select reference, ticket_number, code, response, amount_won,
        // amount, fee, fg_error status, fg_referrence, fg_message, bank_code,
        // phone_number, created from etz_nla.nla_record where 1=1 "
        // //+ " and created between :start_dt and :end_dt "
        // //+ (service.equalsIgnoreCase("nlaliquidationreport") ? " AND reference =
        // :reference " : " and created between :start_dt and :end_dt ")
        // //+ (!user.isEmpty() ? " and update_by like :user " : "")
        // + (!ticketNumber.isEmpty() ? " and ticket_number = :ticket_number " : "")
        // + (!phoneNumber.isEmpty() ? " and phone_number = :phone_number " : "")
        // + (!nlaReference.isEmpty() ? " and reference = :reference " : "")
        // + (!etzReference.isEmpty() ? " and fg_referrence = :etzReference " : "")
        // + " and created between :start_dt and :end_dt "
        // + " order by created desc";

        String query = "select transaction_reference, source_card, amount, destination_bank, network, bank, destination_account, narration, status, response_code, response_message, initiated_by, initiated_date, authorised_by, authorised_date  from banks_direct.fund_transfer_log where 1=1 "
                // + " and created between :start_dt and :end_dt"
                // + (service.equalsIgnoreCase("nlaliquidationreport") ? " AND reference =
                // :reference " : " and created between :start_dt and :end_dt ")
                // + (!user.isEmpty() ? " and update_by like :user " : "")
                // + (!ticketNumber.isEmpty() ? " and ticket_number = :ticket_number " : "")
                // + (!phoneNumber.isEmpty() ? " and phone_number = :phone_number " : "")
                // + (!nlaReference.isEmpty() ? " and reference = :reference " : "")
                //+ (!transactionReference.isEmpty() ? " and transaction_reference = :transaction_reference " : "")
                //+ " and initiated_date between :start_dt and :end_dt  "
                + " order by initiated_date desc";

        // String query = "select initiated_by, initiated_date, authorised_by,
        // authorised_date,trans_id, customer_id, etz_reference, new_customer_id, code,
        // transaction_reference, reversal_reference,response, status from
        // banks_direct.nla_reversal_log where 1=1 "
        // //+ " and created between :start_dt and :end_dt"
        // //+ (service.equalsIgnoreCase("nlaliquidationreport") ? " AND reference =
        // :reference " : " and created between :start_dt and :end_dt ")
        // //+ (!user.isEmpty() ? " and update_by like :user " : "")
        // // + (!ticketNumber.isEmpty() ? " and ticket_number = :ticket_number " : "")
        // // + (!phoneNumber.isEmpty() ? " and phone_number = :phone_number " : "")
        // // + (!nlaReference.isEmpty() ? " and reference = :reference " : "")
        // + (!etzReference.isEmpty() ? " and etz_reference = :etzReference " : "")
        // + " and initiated_date between :start_dt and :end_dt "
        // + " order by initiated_date desc";
        System.out.println("Query >> " + query);
        // Session session = DbHibernate.getSession("40.9MYSQLW");
        //Session session = DbHibernate.getSession("40.9MYSQL");
         Session session = DbHibernate.getSession("30.19MYSQL");

        // System.out.println("STARTING ");
        try {

            Query q = session.createNativeQuery(query);

            // q.setParameter("start_dt", (Object) startDate);
            // q.setParameter("end_dt", (Object) endDate);
//            q.setParameter("start_dt", (Object) start_dt);
//            q.setParameter("end_dt", (Object) end_dt);
            // q.setParameter("ticket_number", (Object) ticketNumber);
            // q.setParameter("phoneNumber", (Object) phoneNumber);
            // q.setParameter("nlaReference", (Object) nlaReference);
            // q.setParameter("etzReference", (Object) etzReference);
            // if (!ticketNumber.equals("")) {
            // q.setParameter("ticket_number", (Object) ticketNumber);
            // }
            // if (!phoneNumber.equals("")) {
            // q.setParameter("phone_number", (Object) phoneNumber);
            // }
            //
            // if (!nlaReference.equals("")) {
            // q.setParameter("reference", (Object) nlaReference);
            // }
//            if (!transactionReference.equals("")) {
//                q.setParameter("transaction_reference", (Object) transactionReference);
//            }
            // if (!service.equalsIgnoreCase("nlaliquidationreport")) {
            // q.setParameter("start_dt", (Object) start_dt)
            // .setParameter("end_dt", (Object) end_dt);
            // } else {
            // q.setParameter("reference", (Object) reference);
            // }
            // if (!user.isEmpty()) {
            // q.setParameter("user", "%" + user + "%");
            // }
            List<Object[]> resp = q.getResultList();
            System.out.println("Resp" + resp);
            System.out.println("DONE");

            for (Object[] record : resp) {
                FundsTransfer ft = new FundsTransfer();
//                String bankCode = "";
//                String network = "";

                try {

                    ft.setTransaction_reference(checkNull(record[0]));
                    ft.setSource_card(checkNull(record[1]));
                    ft.setAmount(checkNull(record[2]));
                    ft.setDestination_bank(checkNull(record[3]));
                    ft.setNetwork(checkNull(record[4]));
                    ft.setBank(checkNull(record[5]));
                    ft.setDestination_account(checkNull(record[6]));
                    ft.setNarration(checkNull(record[7]));
                    ft.setStatus(checkNull(record[8]));
                    ft.setResponse_code(checkNull(record[9]));
                    ft.setResponse_message(checkNull(record[10]));
                    ft.setInitiated_by(checkNull(record[11]));
                    ft.setInitiated_date(checkNull(record[12]));
                    ft.setAuthorized_by(checkNull(record[13]));
                    ft.setAuthorized_date(checkNull(record[14]));

//                    ft.setInitiated_by(checkNull(record[0]));
//                    
//
//                    nlr.setInitiated_by(checkNull(record[0]));
//                    nlr.setInitiated_date(checkNull(record[1]));
//                    nlr.setAuthorised_by(checkNull(record[2]));
//                    nlr.setAuthorised_date(checkNull(record[3]));
//                    nlr.setTransid(checkNull(record[4]));
//                    nlr.setSubscriber_id(checkNull(record[5]));
//                    nlr.setUnique_transid(checkNull(record[6]));
//                    nlr.setNewCustomerId(checkNull(record[7]));
//                    nlr.setCode(checkNull(record[8]));
//                    nlr.setTransactionReference(checkNull(record[9]));
//                    nlr.setReversal_reference(checkNull(record[10]));
//                    nlr.setResponse(checkNull(record[11]));
//                    nlr.setStatus(checkNull(record[12]));
                } catch (Exception e) {
                    //log.error(e.getMessage(), e);
                    e.printStackTrace();

                }

                records.add(ft);

            }

        } catch (Exception e) {
            //log.error(e.getMessage(), e);
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

        log.log(Level.INFO, "Insert into fundtransfer started received >> {0}", jsonString);
        Gson j = new Gson();

        String network = "";
        String bank = "";

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

        String sourceCard = apiData.getSourceCard();
        String destinationAccount = apiData.getDestinationAccount();
        String destinationBank = apiData.getDestinationBank();
        String amount = apiData.getAmount();
        String narration = apiData.getNarration();
        network = apiData.getNetwork();
        bank = apiData.getBank();

        if (network.equals("N/A")) {
            network = "";
        }

        if (bank.equals("N/A")) {
            bank = "";
        }

        String data = apiData.getData();
        List<FundsTransfer> recordsList = gson.fromJson(data, new TypeToken<List<FundsTransfer>>() {
        }.getType());

//        if (destinationBank.equals("ETRANZACTCARD")) {
//            network = "";
//        }
        if (destinationBank.equals("CARD")) {
            network = "";
        }

        String respData = insertData(sourceCard, destinationAccount, destinationBank, network, bank, amount, narration, username, "PENDING");

        return respData;
    }

//    public String callUpdateData(String jsonString) {
//
//        log.log(Level.INFO, "Update into whitelist blacklist started received >> {0}", jsonString);
//        Gson j = new Gson();
//
//        ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);
//        String start_dt = apiData.getStartDate();
//        //System.out.println("Start Date:"+start_dt);
//        String end_dt = apiData.getEndDate();
//        // System.out.println("End Date:"+end_dt);
//        String username = apiData.getUserName();
//        String reference = apiData.getUniqueTransId();
//        String user = apiData.getSubscriberId();
//        String user_code = apiData.getUserCode();
//        String type_id = apiData.getType_id();
//        String searchKey = apiData.getSearchKey();
//        final String service = apiData.getService();
//
//        String ticketNumber = apiData.getTicketNumber();
//        String phoneNumber = apiData.getMobile_no();
//        String nlaReference = apiData.getNlaReference();
//        String etzReference = apiData.getEtzReference();
//
////        String account = apiData.getAccount();
////        String reason = apiData.getReason();
////        String action = apiData.getAction();
////        String accountType = apiData.getAccountType();
//        String data = apiData.getData();
//
//        System.out.println("data" + data);
//
//        JSONObject json = new JSONObject(data);
//
////        List<WhitelistBlacklistReport> recordsList = gson.fromJson(data, new TypeToken<List<WhitelistBlacklistReport>>() {
////        }.getType());
//        //String transId = recordsList.get
////        log.log(Level.INFO, "NLA data reversal request received >> {0}", recordsList);
////
////        String transid = recordsList.get(0).getTransid();
////        String subscriberId = recordsList.get(0).getSubscriber_id();
////        String uniqueTransId = recordsList.get(0).getUnique_transid();
////        String responseDate = recordsList.get(0).getResponse_date();
////        String newCustomerId = recordsList.get(0).getNewCustomerId();
////
////        System.out.println("transid" + transid);
////        System.out.println("subscriberId" + subscriberId);
////        System.out.println("uniqueTransId" + uniqueTransId);
////        System.out.println("responseDate" + responseDate);
////        System.out.println("newCustomerId" + newCustomerId);
////        String account = recordsList.get(0).getAccount();
////        String action = recordsList.get(0).getAction();
//        String account = json.getString("account");
//        String action = json.getString("action");
//
//        System.out.println("account" + account);
////        System.out.println("reason" + reason);
//        System.out.println("action" + action);
//        System.out.println("username" + username);
//        //System.out.println("accountType" + accountType);
//
////        if(accountType.equals("MOMO")){
////         account = editPhoneNumber(account);
////        }
////        System.out.println("account" + account);
////        //System.out.println("reason" + reason);
////        System.out.println("action" + action);
////        System.out.println("username" + username);
//        //System.out.println("accountType" + accountType);
//        String respData = updateData(account, action, username);
//        //String respData = "";
//
//
//        return respData;
//    }
    public String callAuthorizeTransfer(String jsonString) {

        log.info("Funds transfer authorize request received >> {0}" + jsonString);
        Gson j = new Gson();

        ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);
        String start_dt = apiData.getStartDate();
        // System.out.println("Start Date:"+start_dt);
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
        String pin = apiData.getPin();

        //int code;
        //String transactionReference = "";
        //String reversalReference = "";
        String fundstransferResponse = "";
        String responseCode = "";
        String destinationBank = "";
        String destinationAccount = "";
        String network = "";

        String data = apiData.getData();
        List<FundsTransfer> recordsList = gson.fromJson(data, new TypeToken<List<FundsTransfer>>() {
        }.getType());

        // String transId = recordsList.get
        log.info("Funds transfer data request received >> {0}" + recordsList);

        String transactionReference = recordsList.get(0).getTransaction_reference();
        String sourceCard = recordsList.get(0).getSource_card();
        String amount = recordsList.get(0).getAmount();
//        String destinationBank = recordsList.get(0).getDestination_bank();
        destinationBank = recordsList.get(0).getDestination_bank();
        network = recordsList.get(0).getNetwork();
        destinationAccount = recordsList.get(0).getDestination_account();
        String narration = recordsList.get(0).getNarration();
        String status = recordsList.get(0).getStatus();
        String bankCode = recordsList.get(0).getBank();
//        int newAmount = Integer.parseInt(amount);
//        double newAmount = Double.parseDouble(amount);

        if (destinationBank.equals("MOBILEMONEY")) {
            if (network.equals("MTN")) {
                destinationAccount = "686" + destinationAccount;
            } else if (network.equals("VODAFONE")) {
                destinationAccount = "863" + destinationAccount;
            } else if (network.equals("TIGO")) {
                destinationAccount = "844" + destinationAccount;
            }
        } else if (destinationBank.equals("BANK")) {
            destinationAccount = "999" + bankCode + destinationAccount;
        }

        double newAmount = 0.00;

        try {
            newAmount = Double.parseDouble(amount);
        } catch (NumberFormatException e) {
            //Log it if needed
            //intVal = //default fallback value;
            e.printStackTrace();
        }

        System.out.println("transactionReference" + transactionReference);
        System.out.println("sourceCard" + sourceCard);
        System.out.println("amount" + amount);
        System.out.println("destinationBank" + destinationBank);
        System.out.println("destinationAccount" + destinationAccount);
        System.out.println("narration" + narration);
        System.out.println("status" + status);
        System.out.println("bank code" + bankCode);

        String expiry = "";

        // select started
        String query = "select status, transaction_reference,initiated_date  from banks_direct.fund_transfer_log where 1=1 "
                // + " and created between :start_dt and :end_dt"
                // + (service.equalsIgnoreCase("nlaliquidationreport") ? " AND reference =
                // :reference " : " and created between :start_dt and :end_dt ")
                // + (!user.isEmpty() ? " and update_by like :user " : "")
                // + (!ticketNumber.isEmpty() ? " and ticket_number = :ticket_number " : "")
                // + (!phoneNumber.isEmpty() ? " and phone_number = :phone_number " : "")
                // + (!nlaReference.isEmpty() ? " and reference = :reference " : "")
                + (!transactionReference.isEmpty() ? " and transaction_reference = :transaction_reference" : "")
                // + " and initiated_date between :start_dt and :end_dt "
                + " order by initiated_date desc";

        // Session session = DbHibernate.getSession("40.9MYSQLW");
        //Session session = DbHibernate.getSession("40.9MYSQL");
         Session session = DbHibernate.getSession("30.19MYSQL");
        System.out.println("Query >> " + query);
        // Session session = DbHibernate.getSession("VASREPROCESS");

        // System.out.println("STARTING ");
        // System.out.println("Query" + query);
        // List<Object[]> respo = null;
        try {

            Query q = session.createNativeQuery(query);

            System.out.println("Query q>> " + q.getQueryString());
            System.out.println("Query reference>> " + transactionReference);

            q.setParameter("transaction_reference", transactionReference);
            // q.setParameter("reference", StringUtils.join(references, ','));

            List<Object[]> respo = q.getResultList();

            int result = respo.size();
            System.out.println("Rows affected for status check: " + result);

            for (Object[] record : respo) {
                // NlaLiquidationReport nlr = new NlaLiquidationReport();
                // String bankCode = "";
                // String network = "";

                String statusResponse = record[0].toString();
                System.out.println("statusResponse" + statusResponse);

                if (statusResponse.equals("AUTHORIZED")) {
                    fundstransferResponse = "ALREADY AUTHORIZED";
                } else if (statusResponse.equals("DENIED")) {
                    fundstransferResponse = "ALREADY DENIED";
                } else {

                    updateData(transactionReference, "AUTHORIZED", "05", "PENDING", username);

//                    double amount, String ben, String source, String narration, String ref,
//            String channel, String pin, String expiry
                    try {
                        //System.out.println("Auto Switch request:"+new AutoRequest().fundsTransfer(newAmount,destinationAccount, sourceCard, narration, transactionReference, narration, pin));

                        String[] autoresponse = new AutoRequest().fundsTransfer(newAmount, destinationAccount, sourceCard, narration, transactionReference, "02", pin, bankCode);

                        // String resp = "";
                        responseCode = autoresponse[0];
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (responseCode.equals("0")) {
                        fundstransferResponse = "SUCCESSFUL";
                        updateData(transactionReference, "AUTHORIZED", "00", "SUCCESSFUL", username);
                    } else if (responseCode.equals("31")) {
                        fundstransferResponse = "PENDING";
                        updateData(transactionReference, "AUTHORIZED", "31", "PENDING", username);
                    } else {
                        fundstransferResponse = "FAILED";
                        updateData(transactionReference, "AUTHORIZED", "06", "FAILED", username);
                    }

//                    StringBuilder str = new StringBuilder();
//
//                    String preCheckFirst = config.getProperty("nlaAuthorizeFirst");
//                    String preCheckSecond = config.getProperty("nlaAuthorizeSecond");
//
//                    str.append(preCheckFirst);
//                    str.append(transid);
//                    str.append(preCheckSecond);
//                    str.append(newCustomerId);
//
//                    String respData = new DoHTTPRequest().get1(str.toString());
//
//                    System.out.println("Resp Data" + respData);
//
//                    JSONObject myObject = new JSONObject(respData);
//
//                    String responseCode = myObject.getString("respCode");
//
//                    if (responseCode.equals("00")) {
//
//                        // String responseMessage = myObject.getString("respMsg");
//                        JSONObject responseMessage = myObject.getJSONObject("respMsg");
//
//                        //JSONObject myResponseMessage = new JSONObject(responseMessage);
//                        // code = myResponseMessage.getInt("code");
//                        int code = responseMessage.getInt("code");
//                        transactionReference = responseMessage.getString("TransactionReference");
//                        reversalReference = responseMessage.getString("ReversalReference");
//                        fundstransferResponse = responseMessage.getString("response");
//                        updateReversalData(String.valueOf(code), transactionReference, reversalReference, fundstransferResponse, uniqueTransId, username, "AUTHORIZED");
//
//                    } else if (responseCode.equals("06")) {
//                        fundstransferResponse = myObject.getString("respMsg");
//                        System.out.println("Response from 06:" + fundstransferResponse);
//                        updateReversalData("", "", "", fundstransferResponse, uniqueTransId, username, "AUTHORIZED");
//                    }
                    //System.out.println("Response code generated" + responseCode);
                    System.out.println("Response message" + fundstransferResponse);

                }

                // try {
                //
                //// nlr.setInitiated_by(checkNull(record[0]));
                //// nlr.setInitiated_date(checkNull(record[1]));
                //// nlr.setAuthorised_by(checkNull(record[2]));
                //// nlr.setAuthorised_date(checkNull(record[3]));
                //// nlr.setTransid(checkNull(record[4]));
                //// nlr.setSubscriber_id(checkNull(record[5]));
                //// nlr.setUnique_transid(checkNull(record[6]));
                //// nlr.setNewCustomerId(checkNull(record[7]));
                //// nlr.setCode(checkNull(record[8]));
                //// nlr.setTransactionReference(checkNull(record[9]));
                //// nlr.setReversal_reference(checkNull(record[10]));
                //// nlr.setResponse(checkNull(record[11]));
                //// nlr.setStatus(checkNull(record[12]));
                //
                // } catch (Exception e) {
                // log.error(e.getMessage(), e);
                // }
                // records.add(nlr);
            }

            // System.out.println("Response from table 0" + respo.get(0));
            // System.out.println("Response from table 1" + respo.get(1));
        } catch (Exception e) {
            //log.error(e.getMessage(), e);
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        // select ended

        // String respData = insertNlaReprocessedData(transid,
        // subscriberId,uniqueTransId, responseDate, newCustomerId , username);
        return fundstransferResponse;
    }

    public static String insertData(String sourceCard, String destinationAccount, String destinationBank, String network, String bank, String amount, String narration, String username, String status) {

        System.out.println("Start insert data into funds transfer table");
        boolean result = false;
        boolean rollback = false;
        String respData = "";

        List<Object[]> resp = new ArrayList<>();

        // create instance of Random class
        Random rand = new Random();

        // Generate random integers in range 0 to 999
        //int rand_int1 = rand.nextInt(100000000);
//        System.out.println("UPDATE:: " + reference + " -- " + clientId);
        //UPDATE REFERENCES 
        //String logQry = "INSERT INTO ecarddb.tmc_updatelog(reference, original_respcode, updated_respcode, initiated_by, initiated_date, authorisedp_by, authorised_date) (select reference reference, respcode original_respcode, :respcode updated_respcode,initiated_by, initiated_date, authorised_by, authorised_date from ecarddb.e_tmcrequest where reference = :reference)";
        //String logQry = "INSERT INTO ecarddb.tmc_updatelog(reference, original_respcode, updated_respcode, initiated_by, initiated_date, authorised_by, authorised_date) (select reference reference, response_code original_respcode, :respcode updated_respcode,:initiated_by initiated_by, now() initiated_date,:authorised_by authorised_by, now() authorised_date from ecarddb.e_tmcrequest where reference = :reference)";
        //String logQry = "INSERT INTO ecarddb.tmc_updatelog(reference, original_respcode, updated_respcode, initiated_by, initiated_date, authorised_by, authorised_date) (select reference reference, response_code original_respcode, :respcode updated_respcode,:initiated_by initiated_by, now() initiated_date,:authorised_by authorised_by, now() authorised_date from ecarddb.e_tmcrequest where trans_data = :reference)";
        //String Qry = "INSERT INTO ecarddb.vas_reprocess_log(original_reference, updated_reference, initiated_by, initiated_date, authorised_by, authorised_date, status, account, client, vastype, amount, mobile, transtype) (select :original_reference original_reference, :updated_reference updated_reference, :initiated_by initiated_by, now() initiated_date, :authorised_by authorised_by, now() authorised_date, :status status, :account account, :client client, :vastype vastype, :amount amount, :mobile mobile, :transtype transtype from ecarddb.vas_reprocess_log where original_reference = :original_reference)";
        //String Qry = "INSERT INTO vasdb2.vas_reprocess_log(original_reference, updated_reference, initiated_by, initiated_date, authorised_by, authorised_date, status, account, client, vastype, amount, mobile, transtype)Values(:original_reference, :updated_reference, :initiated_by, now() , :authorised_by, now() , :status, :account, :client, :vastype, :amount, :mobile, :transtype)";
        //String Qry = "INSERT IGNORE INTO telcodb.aml_blacklist(account, reason,action, authorizer, etz_reference, new_customer_id, status)Values(:initiated_by, now() , :trans_id ,:customer_id, :etz_reference, :newCustomer_id, :status)";
//        String Qry ;
//
//        if (action.equals("BLACKLIST")) {
//            Qry = "INSERT IGNORE INTO telcodb.aml_blacklist(account, reason, action, authorizer, date_blacklisted)Values(:account, :reason, :action, :authorizer,  now())";
//
//        } else {
//            Qry = "INSERT IGNORE INTO telcodb.aml_blacklist(account, reason, action, authorizer, date_whitelisted)Values(:account, :reason, :action, :authorizer,  now())";
//
//        }
        String Qry = "INSERT IGNORE INTO banks_direct.fund_transfer_log(transaction_reference, source_card, amount, destination_bank, network, bank, destination_account, narration, status, initiated_by, initiated_date)Values(:transaction_reference, :source_card, :amount, :destination_bank, :network, :bank,  :destination_account, :narration, :status,  :initiated_by, now())";

        //String Qry = "INSERT IGNORE INTO banks_direct.nla_reversal_log(initiated_by, initiated_date,trans_id, customer_id, etz_reference, new_customer_id, status)Values(:initiated_by, now() , :trans_id ,:customer_id, :etz_reference, :newCustomer_id, :status)";
        //String tmcqry = "UPDATE ecarddb.e_tmcrequest set respcode =:respcode, processdate = now() where reference = :reference ";
        //String tmcqry = "UPDATE ecarddb.e_tmcrequest set response_code =:respcode where trans_data = :reference ";
        //String fgqry = "SELECT etzref, respcode from fgdb.fundgate_response where etzref =:reference ";
        //String mpqry = "SELECT reference, trnx_status from ussd_db.mpay_report where reference =:reference ";
        Transaction tx = null;

        //Session session = DbHibernate.getSession("40.9MYSQL");
         Session session = DbHibernate.getSession("30.19MYSQL");

        try {
            tx = session.beginTransaction();
            //log record
            Query q = session.createNativeQuery(Qry);
            //q.setParameter("clientid", clientId);
            q.setParameter("transaction_reference", "C2C" + getAlphaNumericString(17));
            q.setParameter("source_card", sourceCard);
            q.setParameter("amount", amount);
            q.setParameter("destination_bank", destinationBank);
            q.setParameter("destination_account", destinationAccount);
            q.setParameter("narration", narration);
            q.setParameter("status", status);
            q.setParameter("initiated_by", username);
            q.setParameter("network", network);
            q.setParameter("bank", bank);
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

    public static String updateData(String transactionReference, String status, String responseCode, String responseMessage, String username) {

        System.out.println("Start update data into fundstransfer table");
        boolean result = false;
        boolean rollback = false;
        String respData = "";

        List<Object[]> resp = new ArrayList<>();

        //String Qry = "UPDATE telcodb.aml_blacklist set action =:action  where account = :account ";
        String Qry = "UPDATE banks_direct.fund_transfer_log set status =:status,response_code=:response_code, response_message = :response_message, authorised_by =:authorised_by, authorised_date = now() where transaction_reference = :transaction_reference";

        Transaction tx = null;

        //Session session = DbHibernate.getSession("40.9MYSQL");
         Session session = DbHibernate.getSession("30.19MYSQL");

        try {
            tx = session.beginTransaction();
            //log record
            Query q = session.createNativeQuery(Qry);
            //q.setParameter("clientid", clientId);
            q.setParameter("transaction_reference", transactionReference);
            q.setParameter("response_code", responseCode);
            q.setParameter("response_message", responseMessage);
            q.setParameter("status", status);
            q.setParameter("authorised_by", username);
            //q.setParameter("reason", reason);
            //q.setParameter("action", action);

            //int h = q.executeUpdate();
            int h = q.executeUpdate();

            System.out.println("H: " + h);

            if (h > 0) {
                respData = "Update Successful";
            } else {
                respData = "Update not successful";
            }

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
    
        public List<Account> getSourceCompanyAccounts(String companyId) {

        System.out.println("Accounts fetch started");

        List<Account> accountrecs = new ArrayList<>();

        //Session session = DbHibernate.getSession("40.9MYSQL");
        Session session = DbHibernate.getSession("30.19MYSQL");
        try {
//            Query q = session.createNativeQuery(
//                    "select company_id, account_name  from banks_direct.accounts order by account_name asc");

//            Query q = session.createNativeQuery(
//                    "SELECT company_id, account_type, account_name FROM banks_direct.accounts WHERE company_id IN (SELECT company_id FROM banks_direct.company_table WHERE company_id = '1') order by account_name asc");
            Query q = session.createNativeQuery(
                    "SELECT company_id, account_type, account_name FROM banks_direct.accounts WHERE account_type = 'CARD' AND company_id IN (SELECT company_id FROM banks_direct.company_table WHERE company_id = :company_id) order by account_name asc");

//            q.setParameter("company_id", "1");
            q.setParameter("company_id", companyId);

            List<Object[]> accountrecords = (List<Object[]>) q.getResultList();

            for (Object[] record : accountrecords) {
                Account b = new Account();
                try {
                    b.setCompany_id(checkNull(record[0]));
                    b.setAccount_type(checkNull(record[1]));
                    b.setAccount_name(checkNull(record[2]));
                } catch (Exception et) {
//                    log.error(ERROR, et);
                    et.printStackTrace();
                }
                accountrecs.add(b);
            }
        } catch (Exception e) {
//            log.error(ERROR, e);
            e.printStackTrace();
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (Exception e) {
//                    log.error(ERROR, e);
                    e.printStackTrace();
                }
            }
        }

        System.out.println("Account records fetched" + accountrecs);

        return accountrecs;
    }
    
    public List<Account> getCompanyAccounts(String companyId) {

        System.out.println("Accounts fetch started");

        List<Account> accountrecs = new ArrayList<>();

        //Session session = DbHibernate.getSession("40.9MYSQL");
        Session session = DbHibernate.getSession("30.19MYSQL");
        try {
//            Query q = session.createNativeQuery(
//                    "select company_id, account_name  from banks_direct.accounts order by account_name asc");

//            Query q = session.createNativeQuery(
//                    "SELECT company_id, account_type, account_name FROM banks_direct.accounts WHERE company_id IN (SELECT company_id FROM banks_direct.company_table WHERE company_id = '1') order by account_name asc");
            Query q = session.createNativeQuery(
                    "SELECT company_id, account_type, account_name FROM banks_direct.accounts WHERE company_id IN (SELECT company_id FROM banks_direct.company_table WHERE company_id = :company_id) order by account_name asc");

//            q.setParameter("company_id", "1");
            q.setParameter("company_id", companyId);

            List<Object[]> accountrecords = (List<Object[]>) q.getResultList();

            for (Object[] record : accountrecords) {
                Account b = new Account();
                try {
                    b.setCompany_id(checkNull(record[0]));
                    b.setAccount_type(checkNull(record[1]));
                    b.setAccount_name(checkNull(record[2]));
                } catch (Exception et) {
//                    log.error(ERROR, et);
                    et.printStackTrace();
                }
                accountrecs.add(b);
            }
        } catch (Exception e) {
//            log.error(ERROR, e);
            e.printStackTrace();
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (Exception e) {
//                    log.error(ERROR, e);
                    e.printStackTrace();
                }
            }
        }

        System.out.println("Account records fetched" + accountrecs);

        return accountrecs;
    }

    public List<Account> getCardBalance(String companyId) {

        System.out.println("Card Balance fetch started");

        List<Account> accountrecs = new ArrayList<>();
        List<String> accountrec = new ArrayList<>();
        List<Object[]> resp = new ArrayList<>();
        List<Account> records = new ArrayList<>();

        //Session session = DbHibernate.getSession("40.9MYSQL");
        Session session = DbHibernate.getSession("30.19MYSQL");
        try {
//            Query q = session.createNativeQuery(
//                    "select company_id, account_name  from banks_direct.accounts order by account_name asc");

//            Query q = session.createNativeQuery(
//                    "SELECT company_id, account_type, account_name FROM banks_direct.accounts WHERE company_id IN (SELECT company_id FROM banks_direct.company_table WHERE company_id = '1') order by account_name asc");
            Query q = session.createNativeQuery(
                    "SELECT company_id, account_type, account_name FROM banks_direct.accounts WHERE account_type = 'CARD' AND company_id IN (SELECT company_id FROM banks_direct.company_table WHERE company_id = :company_id) order by account_name asc");

//            q.setParameter("company_id", "1");
            q.setParameter("company_id", companyId);
            List<Object[]> accountrecords = (List<Object[]>) q.getResultList();

            //System.out.println("Card records" + accountrecords);
            int result = accountrecords.size();
            System.out.println("Rows affected: " + result);

            //if(result>0){
            for (Object[] record : accountrecords) {
                Account b = new Account();

                accountrec.add(checkNull(record[2]));
            }

        } catch (Exception e) {
//            log.error(ERROR, e);
            e.printStackTrace();
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (Exception e) {
//                    log.error(ERROR, e);
                    e.printStackTrace();
                }
            }
        }

        System.out.println("Card records fetched" + accountrec);

        if (accountrec.size() > 0) {

            String query = "SELECT card_num, online_balance, created"
                    + " from ecarddb.e_cardholder where 1=1 "
                    + "  AND card_num  in (:accountrec) order by created desc";

//            Session cardsession = DbHibernate.getSession("VASREPROCESS");
            Session cardsession = DbHibernate.getSession("30.17MYSQL");
            try {

                org.hibernate.query.Query q = cardsession.createNativeQuery(query);

                q.setParameter("accountrec", accountrec);

                resp = q.getResultList();
                
                //start
                //List<Object[]> accountrecords = (List<Object[]>) q.getResultList();
            int results = resp.size();
            System.out.println("Rows affected for card balance: " + results);
                //end

            } catch (Exception e) {
                //log.error("error", e);
                e.printStackTrace();
            } finally {
                if (cardsession != null) {
                    cardsession.close();
                }
            }

            for (Object[] record : resp) {
                Account b = new Account();

                try {
//                    b.setCompany_id(checkNull(record[0]));
//                    b.setAccount_type(checkNull(record[1]));
                    b.setAccount_name(checkNull(record[0]));
                    b.setBalance(checkNull(record[1]));
                } catch (Exception et) {
//                    log.error(ERROR, et);
                    et.printStackTrace();
                }
                accountrecs.add(b);
            }

        }

        System.out.println("Card balance" + accountrecs);

        return accountrecs;
    }

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

    static String getAlphaNumericString(int n) {

        // chose a Character random from this String
//  String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
//         + "0123456789"
//         + "abcdefghijklmnopqrstuvxyz";
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int) (AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }

}
