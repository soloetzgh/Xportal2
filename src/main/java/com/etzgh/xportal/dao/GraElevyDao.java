/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etzgh.xportal.dao;

import com.etzgh.xportal.model.ApiPostData;
import com.etzgh.xportal.model.GraElevyReport;
import com.etzgh.xportal.model.NODES;
import com.etzgh.xportal.model.NlaLiquidationReport;
import com.etzgh.xportal.utility.Config;
import com.etzgh.xportal.utility.DbHibernate;
import com.etzgh.xportal.utility.DoHTTPRequest;
import com.etzgh.xportal.utility.GeneralUtility;
import com.etzgh.xportal.utility.HashNumber;
import com.etzgh.xportal.utility.SuperHttpClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author yaw.owusu-koranteng
 */
public class GraElevyDao {
    
    
    private static final AppDao appDao = new AppDao();
    private static final GeneralUtility utility = new GeneralUtility();
    private static final Gson gson = new Gson();
    private static final Logger log = Logger.getLogger(TmcDao.class.getName());
    private static Map<String, String> banks = new HashMap<>();
    private static final Config config = new Config();

    public static void main(String[] args) {
        String r = "{\"merchant\":\"\",\"product\":\"\",\"accountNumber\":\"\",\"service\":\"search\",\"action\":\"\",\"apiSecret\":\"\",\"apiCode\":\"\",\"userCode\":\"[2000000000000060]|905,[2500000000000050]|77\",\"admin\":\"\",\"type_desc\":\"\",\"role_id\":\"\",\"type_id\":\"[4],[5],[6],[7],[20],[24],[39],[40]\",\"searchKey\":\"02RS42603E77662\",\"userName\":\"\",\"ClientId\":\"\",\"user_id\":\"10500000000000754\",\"pageNumber\":1,\"rowsPerPage\":1,\"trans_code\":\"\",\"bank_code\":\"905\",\"subscriberId\":\"\",\"trans_status\":\"\",\"bank\":\"\",\"biller\":\"\",\"transId\":\"\",\"bankid\":\"\",\"card_num\":\"\",\"payType\":\"\",\"wcMerchant\":\"\",\"lineType\":\"\",\"trans_channel\":\"\",\"terminal_id\":\"\",\"card_account\":\"\",\"cop_issuercode\":\"\",\"cop_role_id\":\"\",\"cop_company_id\":\"\",\"mobile_no\":\"\",\"phone\":\"\",\"type\":\"\",\"amount\":\"\",\"amount2\":\"\",\"refundStatus\":\"\",\"partialReversal\":\"\",\"reversalReason\":\"\",\"ipAddress\":\"0:0:0:0:0:0:0:1\",\"userData\":\"\",\"appId\":[],\"company\":\"\",\"options\":{}}";
        // new TmcDao().getTmcTransactions(r);
        new NlaLiquidationReportDao().getNlaLiquidationReport(r);
    }

    public static Map<String, String> convertListAfterJava8(List<NODES> list) {
        Map<String, String> map = list.stream().collect(Collectors.toMap(NODES::getId, node -> node.getName()));
        return map;
    }

    // MAPPING ISOCODES FOR TMC
    protected String mapResponse(String code, String resp) {

        String RespMsg = "";
        resp = resp.substring(0, 3);
        if (code == null || resp == null) {
            return "No response";
        }
        if (code.equals("58")
                && (resp.equals("686") || resp.equals("844") || resp.equals("863") || resp.equals("285"))) {
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

        public List<GraElevyReport> getGraElevyTransaction(String jsonString) {

        log.info("Gra Elevy trans request received >> {0}" + jsonString);
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
        String transactionStatus = apiData.getTransactionStatus();
        
        String [] startDateTime = start_dt.split(" ");
        String startDate = startDateTime[0];
        System.out.println("startDate"+startDate);
        
        String [] endDateTime = end_dt.split(" ");
        String endDate = endDateTime[0];
        System.out.println("endDate"+endDate);

        List<GraElevyReport> records = new ArrayList<>();

        // log.info( "USER CODE >>{0}", user_code);
        String userRoles = "";

        if (reference == null) {
            reference = "";
        } else {
            reference = reference.trim();
        }
        
                if (transactionStatus == null) {
            transactionStatus = "";
        } else {
            transactionStatus = transactionStatus.trim();
        }
        
        if (searchKey == null) {
            searchKey = "";
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

        // if (service.equalsIgnoreCase("updatesearch") && searchKey.trim().isEmpty()) {
        // return records;
        // }
//        if (service.equalsIgnoreCase("updatesearch")) {
//            reference = searchKey.trim();
//        }

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

//        String query = "select payment_code, subscriber_id, unique_transid, response_date date from vasdb2.t_paytrans where 1=1 "
//                // + " and created between :start_dt and :end_dt "
//                // + (service.equalsIgnoreCase("nlaliquidationreport") ? " AND reference =
//                // :reference " : " and created between :start_dt and :end_dt ")
//                // + (!user.isEmpty() ? " and update_by like :user " : "")
//                // + (!ticketNumber.isEmpty() ? " and ticket_number = :ticket_number " : "")
//                // + (!phoneNumber.isEmpty() ? " and phone_number = :phone_number " : "")
//                // + (!nlaReference.isEmpty() ? " and reference = :reference " : "")
//                //+ (!etzReference.isEmpty() ? " and unique_transid = :etzReference " : "")
//                // + " and created between :start_dt and :end_dt "
//                + " order by response_date desc";

        //Session session = DbHibernate.getSession("40.9MYSQLW");
        // Session session = DbHibernate.getSession("VASREPROCESS");

        // System.out.println("STARTING ");
        try {

		String tloc = "cfg//ELEVY-TRUSTSTORE.jks";
		String kloc = "cfg//ETRANZACT-ELEVY-KEYSTORE-PROD.jks";

//		String tpass = "123456";
//		String kpass = "123456";
//                String limit = "1000";
                
                
                 String tpass = config.getProperty("grausername");
                 String kpass = config.getProperty("grapassword");
                 String limit = config.getProperty("gralimit");
                 

           String test = "https://api.elevygh.org/prod/v1.0/query/elevy?startDateTime="+startDate+"&endDateTime="+endDate+"&limit="+limit+"&transactionStatus="+transactionStatus+"";

                     //String test = "https://api.elevygh.org/prod/v1.0/query/elevy?startDateTime=2022-01-03&endDateTime=2022-09-04&limit=1000&transactionStatus=RESERVED";

           
                 String response = SuperHttpClient.doGetSSL(test, 60, 60, tloc, tpass, kloc, kpass);
                 System.out.println("Response from GRA call"+response);
                 
                  //GraElevyReport graData = j.fromJson(response, GraElevyReport.class);
                  
                 JSONObject jsonObj = new JSONObject(response);
                   

//JSONObject jsonObject = new JSONObject(response);

//        List<String> transactions = graData.getTransactions();
            JSONArray transactions = jsonObj.getJSONArray("transactions");

//String transactions = jsonObject.getString("transactions");
        
        System.out.println("transactions"+transactions);
        
//        for(int i=0; i<transactions.length(); i++){
//             
//            //String ser = transactions
//            
//        }

 List<GraElevyReport> recordsList = gson.fromJson(transactions.toString(), new TypeToken<List<GraElevyReport>>() {
        }.getType());

              //List<GraElevyReport> recordsList = null ;

 recordsList.stream().forEach(f -> {
            if (f.getClientTransactionID() != null && !f.getClientTransactionID().trim().isEmpty()) {
                //references.add(f.getReference().trim());
                System.out.println("Transaction Id" + f.getClientTransactionID());
               // System.out.println("Message from excel file" + f.getMessage());

                //System.out.println("Calling send sms");

                try {

                     GraElevyReport graElevyReport = new GraElevyReport();


                       graElevyReport.setElevyID(f.getElevyID());
                       graElevyReport.setClientTransactionID(f.getClientTransactionID());
                       graElevyReport.setServiceType(f.getServiceType());
                       graElevyReport.setCurrency(f.getCurrency());
                       graElevyReport.setTransferAmount(f.getTransferAmount());
                       graElevyReport.setTaxableAmount(f.getTaxableAmount());
                       graElevyReport.setElevyAmount(f.getElevyAmount());
                       graElevyReport.setElevyRefundAmount(f.getElevyRefundAmount());
                       graElevyReport.setTransactionStatus(f.getTransactionStatus());
                       graElevyReport.setTransactionStatusTimestamp(f.getTransactionStatusTimestamp());

                    records.add(graElevyReport);

                } catch (Exception e) {
//                    log.error(ERROR, e);
                          e.printStackTrace();
                }

            }
        });

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
//            if (session != null) {
//                session.close();
//            }
        }
        System.out.println("RE: " + records);

        return records;
    }

  



    // public List<TmcUpdate> getTmcUpdateLog(String jsonString) {
    //
    // log.info( "TMC update log request received >> {0}"+ jsonString);
    // Gson j = new Gson();
    //
    // ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);
    // String start_dt = apiData.getStartDate();
    // String end_dt = apiData.getEndDate();
    // String reference = apiData.getUniqueTransId();
    // String user = apiData.getSubscriberId();
    // String user_code = apiData.getUserCode();
    // String type_id = apiData.getType_id();
    // String searchKey = apiData.getSearchKey();
    // final String service = apiData.getService();
    //
    // List<TmcUpdate> records = new ArrayList<>();
    //
    //// log.info( "USER CODE >>{0}", user_code);
    // String userRoles = "";
    //
    // if (reference == null) {
    // reference = "";
    // } else {
    // reference = reference.trim();
    // }
    // if (searchKey == null) {
    // searchKey = "";
    // }
    // if (user == null) {
    // user = "";
    // } else {
    // user = user.trim();
    // }
    //
    // if (service.equalsIgnoreCase("updatesearch") && searchKey.trim().isEmpty()) {
    // return records;
    // }
    //
    // if (service.equalsIgnoreCase("updatesearch")) {
    // reference = searchKey.trim();
    // }
    //
    // //29012018
    //// bank = user_code.equals("000") ? bank : scheme;
    // if (!type_id.isEmpty()) {
    //// System.out.println("Type: " + type_id);
    // if (type_id.contains("[64]")) {
    // } else {
    // return records;
    // }
    // } else {
    // return records;
    // }
    // System.out.println("SEARCH: " + service);
    // String query = "select reference, original_respcode, updated_respcode,
    // initiated_by, initiated_date, authorised_by, authorised_date from
    // telcodb.tmc_updatelog where 1=1 "
    // // + " and a.trnxdate between :start_dt and :end_dt "
    // + (service.equalsIgnoreCase("updatesearch") ? " AND reference = :reference "
    // : " and authorised_date between :start_dt and :end_dt ")
    // + (!user.isEmpty() ? " and update_by like :user " : "")
    // + " order by update_date desc";
    //
    //// Session session = DbHibernate.getSession("40.9MYSQL");
    // Session session = DbHibernate.getSession("1.230MYSQL");
    //
    //// System.out.println("STARTING ");
    // try {
    //
    // Query q = session.createNativeQuery(query);
    //
    //// q.setParameter("beginDate", (Object) beginDate);
    //// q.setParameter("endDate", (Object) endDate);
    // if (!service.equalsIgnoreCase("updatesearch")) {
    // q.setParameter("start_dt", (Object) start_dt)
    // .setParameter("end_dt", (Object) end_dt);
    // } else {
    // q.setParameter("reference", (Object) reference);
    // }
    //
    // if (!user.isEmpty()) {
    // q.setParameter("user", "%" + user + "%");
    // }
    //
    // List<Object[]> resp = q.getResultList();
    //// System.out.println("DONE");
    //
    // for (Object[] record : resp) {
    // TmcUpdate tmc = new TmcUpdate();
    // try {
    // tmc.setReference(checkNull(record[0]));
    //// tmc.setOriginal_clientid(checkNull(record[1]));
    //// tmc.setUpdated_clientid(checkNull(record[2]));
    //// tmc.setOriginal_respcode(checkNull(record[3]));
    //// tmc.setUpdated_respcode(checkNull(record[4]));
    //// tmc.setOriginal_narration(checkNull(record[5]));
    //// tmc.setUpdated_narration(checkNull(record[6]));
    //// tmc.setUpdate_by(checkNull(record[7]));
    //// tmc.setUpdate_date(checkNull(record[8]));
    // tmc.setOriginal_respcode(checkNull(record[1]));
    // tmc.setUpdated_respcode(checkNull(record[2]));
    // tmc.setInitiated_by(checkNull(record[3]));
    // tmc.setInitiated_date(checkNull(record[4]));
    // tmc.setAuthorised_by(checkNull(record[5]));
    // tmc.setAuthorised_date(checkNull(record[6]));
    //
    //
    //
    // } catch (Exception e) {
    // log.error(e.getMessage(), e);
    // }
    //
    // records.add(tmc);
    //
    // }
    //
    // } catch (Exception e) {
    // log.error(e.getMessage(), e);
    // } finally {
    // if (session != null) {
    // session.close();
    // }
    // }
    // System.out.println("RE: " + records);
    //
    // return records;
    // }
    // @POST
    // @Path("trx/find")
    // @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    // public List<TMC> searchTmcTransactions(String jsonString) throws
    // ParseException {
    //
    // System.out.println("TMC search request received >> " + jsonString);
    // Gson j = new Gson();
    // ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);
    //
    // String trans_data = apiData.getSearchKey();
    // String userCode = apiData.getUserCode();
    // String type_id = apiData.getType_id();
    // String bankCode = apiData.getBank_code();
    // String query = "";
    //
    // if (trans_data == null) {
    // trans_data = "";
    // }
    //
    // if (trans_data.equals("")) {
    // return Collections.EMPTY_LIST;
    // }
    //
    // String inconIds = "";
    // System.out.println("User : " + userCode);
    // if (!type_id.isEmpty()) {
    // if (type_id.contains("[0]")) {
    // } else if (type_id.contains("[6]")) {
    // System.out.println("bank: " + bankCode);
    // inconIds = appDao.getInconId(bankCode);
    // } else {
    // return Collections.EMPTY_LIST;
    // }
    // } else {
    // return Collections.EMPTY_LIST;
    // }
    //
    // if (type_id.contains("[0]")) {
    // query = "select mti, trans_data, acct_id1, acct_id2, pro_code as transtype,
    // trans_date, pro_code, pan, amount, "
    // + "track2, response_code, response_code as response, terminal_id, stan,
    // card_acc_name, fee, reference, "
    // + "src_node, target_node, response_time from ecarddb.e_tmcrequest where
    // trans_data = :trans_data order by trans_date desc";
    // } else if (type_id.contains("[6]")) {
    // query = "select mti, trans_data, acct_id1, acct_id2, pro_code as transtype,
    // trans_date, pro_code, pan, amount, "
    // + "track2, response_code, response_code as response, terminal_id, stan,
    // card_acc_name, fee, reference, "
    // + "src_node, target_node, response_time from ecarddb.e_tmcrequest where
    // (src_node in (" + inconIds + ") or target_node in (" + inconIds + ")) "
    // + "and trans_data = :trans_data order by trans_date desc";
    // }
    //
    // System.out.println("Query >> " + query);
    // List<TMC> recordsList = new ArrayList<>();
    // q = em.createNativeQuery(query).setParameter("trans_data", trans_data);
    // List<Object[]> records = q.getResultList();
    //
    // for (Object[] record : records) {
    // TMC tmc = new TMC();
    //
    // tmc.setMti(checkNull(record[0]));
    // tmc.setTrans_data(checkNull(record[1]));
    // tmc.setSource_account(checkNull(record[2]));
    // tmc.setDestination_account(checkNull(record[3]));
    // tmc.setTranstype(checkNull(record[4]));
    // tmc.setTrans_date(convertDate(checkNull(record[5])));
    // tmc.setPro_code(checkNull(record[6]));
    // tmc.setPan(checkNull(record[7]));
    // tmc.setAmount(BigDecimal.valueOf(Double.valueOf(checkNull(record[8]))));
    // tmc.setTrack2(checkNull(record[9]));
    // tmc.setResponse_code(checkNull(record[10]));
    // tmc.setResponse(checkNull(record[11]));
    // tmc.setTerminal_id(checkNull(record[12]));
    // tmc.setStan(checkNull(record[13]));
    // tmc.setCard_acc_name(checkNull(record[14]));
    // tmc.setFee(BigDecimal.valueOf(Double.valueOf(checkNull(record[15]))));
    // tmc.setReference(checkNull(record[16]));
    // tmc.setSrc_node(checkNull(record[17]));
    // tmc.setTarget_node(checkNull(record[18]));
    // tmc.setResponse_time(convertDate(checkNull(record[19])));
    // recordsList.add(tmc);
    // }
    //// System.out.println("Records: " + recordsList);
    // return recordsList;
    // }
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
    // public Map<Integer,String> getNodes() {
    // String query = "select incon_id as id , incon_name as name from
    // ecarddb.e_tmcnode order by incon_name asc";
    //
    // Session session = DbHibernate.getSession("40.15MYSQL");
    // List<Object[]> noderecords = new ArrayList<>();
    // try {
    // Query q = session.createNativeQuery(query);
    // noderecords = q.getResultList();
    // } catch (Exception e) {
    //
    // } finally {
    // if (session != null) {
    // try {
    // session.close();
    // } catch (Exception r) {
    //
    // }
    // }
    // }
    // return noderecords;
    // }

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

    
}
