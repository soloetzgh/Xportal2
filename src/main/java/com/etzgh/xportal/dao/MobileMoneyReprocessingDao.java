/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etzgh.xportal.dao;

import com.etzgh.xportal.model.ApiPostData;
import com.etzgh.xportal.model.MobileMoneyReprocess;
import com.etzgh.xportal.model.NODES;
import com.etzgh.xportal.utility.AutoRequest;
import com.etzgh.xportal.utility.Config;
import com.etzgh.xportal.utility.DbHibernate;
import com.etzgh.xportal.utility.DoHTTPRequest;
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
import org.apache.log4j.Logger;
import java.util.stream.Collectors;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author yaw.owusu-koranteng
 */
public class MobileMoneyReprocessingDao {

//    private static final AppDao appDao = new AppDao();
    private static final Gson gson = new Gson();
    private static final Logger log = Logger.getLogger(MobileMoneyReprocessingDao.class.getName());
    private static Map<String, String> banks = new HashMap<>();
    private static final Config config = new Config();
    private static final String ERROR = "ERROR";

    public static void main(String[] args) {
        String r = "{\"merchant\":\"\",\"product\":\"\",\"accountNumber\":\"\",\"service\":\"search\",\"action\":\"\",\"apiSecret\":\"\",\"apiCode\":\"\",\"userCode\":\"[2000000000000060]|905,[2500000000000050]|77\",\"admin\":\"\",\"type_desc\":\"\",\"role_id\":\"\",\"type_id\":\"[4],[5],[6],[7],[20],[24],[39],[40]\",\"searchKey\":\"02RS42603E77662\",\"userName\":\"\",\"ClientId\":\"\",\"user_id\":\"10500000000000754\",\"pageNumber\":1,\"rowsPerPage\":1,\"trans_code\":\"\",\"bank_code\":\"905\",\"subscriberId\":\"\",\"trans_status\":\"\",\"bank\":\"\",\"biller\":\"\",\"transId\":\"\",\"bankid\":\"\",\"card_num\":\"\",\"payType\":\"\",\"wcMerchant\":\"\",\"lineType\":\"\",\"trans_channel\":\"\",\"terminal_id\":\"\",\"card_account\":\"\",\"cop_issuercode\":\"\",\"cop_role_id\":\"\",\"cop_company_id\":\"\",\"mobile_no\":\"\",\"phone\":\"\",\"type\":\"\",\"amount\":\"\",\"amount2\":\"\",\"refundStatus\":\"\",\"partialReversal\":\"\",\"reversalReason\":\"\",\"ipAddress\":\"0:0:0:0:0:0:0:1\",\"userData\":\"\",\"appId\":[],\"company\":\"\",\"options\":{}}";

        new MobileMoneyReprocessingDao().getUploadedData(r);
    }

    public static Map<String, String> convertListAfterJava8(List<NODES> list) {
        Map<String, String> map = list.stream().collect(Collectors.toMap(NODES::getId, node -> node.getName()));
        return map;
    }

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
        banks = convertListAfterJava8(new AppDao().getBankNodes("000"));
    }

    public List<MobileMoneyReprocess> getUploadedData(String jsonString) {

        log.info("Mobile Money Reprocess status request received >> {0}" + jsonString);
        Gson j = new Gson();

        ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);
        String data = apiData.getUniqueTransId();

        List<MobileMoneyReprocess> records = new ArrayList<>();

        List<String> roleList = new ArrayList<>();
        List<Object[]> respFromTable = new ArrayList<>();
        List<String> references = new ArrayList<>();

        String userRoles = "";

        Boolean hashAcc = true;

        List<MobileMoneyReprocess> recordsList = gson.fromJson(data, new TypeToken<List<MobileMoneyReprocess>>() {
        }.getType());

        recordsList.stream().forEach(f -> {
            if (f.getReference() != null && !f.getReference().trim().isEmpty()) {
                references.add(f.getReference().trim());
                System.out.println("References from excel file" + f.getReference());

                System.out.println("Calling mobile money table");

            }
        });

        System.out.println("References received" + references);

        if (!references.isEmpty()) {
            respFromTable = callMobileMoneyTable(references);

            for (Object[] record : respFromTable) {

                MobileMoneyReprocess mmr = new MobileMoneyReprocess();
                try {

                    System.out.println("reference" + record[0]);
                    System.out.println("paymenttype" + record[1]);
                    System.out.println("flag" + record[2]);
                    System.out.println("switchcode" + record[3]);
                    System.out.println("Card no" + record[4]);
                    System.out.println("channel" + record[5]);

                    mmr.setReference(checkNull(record[0]));
                    mmr.setPaymentType(checkNull(record[1]));
                    mmr.setFlag(checkNull(record[2]));
                    mmr.setSwitchCode(checkNull(record[3]));
                    mmr.setCardno(checkNull(record[4]));
                    mmr.setChannel(checkNull(record[5]));

                } catch (Exception e) {
                    log.error(ERROR, e);
                }

                records.add(mmr);

            }
        }

        return records;
    }

    public static List<Object[]> callMobileMoneyTable(List<String> references) {

        List<Object[]> respo = new ArrayList<>();

        System.out.println("Call mobile money table with references" + references);

        String query = "select reference, paymenttype, flag, switchcode, cardno, channel from telcodb.mobilemoney where 1=1"
                + " AND paymenttype = 'D' AND reference in (:reference)"
                + " order by paymenttype desc";

        System.out.println("Query >> " + query);
        Session session = DbHibernate.getSession("VASREPROCESS");

        try {

            Query q = session.createNativeQuery(query);

            System.out.println("Query q>> " + q.getQueryString());
            System.out.println("Query reference>> " + references);

            q.setParameter("reference", references);

            respo = q.getResultList();

            int result = respo.size();
            System.out.println("Rows affected: " + result);

        } catch (Exception e) {
            log.error(ERROR, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }

        System.out.println("RE: " + respo);

        return respo;
    }

    public List<MobileMoneyReprocess> callAutoSwitch(String jsonString) {

        log.info("AutoSwitch call request received >> {0}" + jsonString);
        Gson j = new Gson();

        ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);

        String data = apiData.getData();

        List<MobileMoneyReprocess> records = new ArrayList<>();

        // List<String> roleList = new ArrayList<>();
        // List<Object[]> resp = new ArrayList<>();
        List<String> references = new ArrayList<>();

        List<MobileMoneyReprocess> recordsList = gson.fromJson(data, new TypeToken<List<MobileMoneyReprocess>>() {
        }.getType());

        recordsList.stream().forEach(f -> {
            if (f.getReference() != null && !f.getReference().trim().isEmpty()) {
                references.add(f.getReference().trim());
                System.out.println("References from excel file for autoswitch" + f.getReference());

                System.out.println("Calling check status");

                try {
                    System.out.println("Starting check status from autoswitch");

                    String cardNumber = "";

                    switch (f.getCardno().substring(0, 3)) {
                        case "021":
                            cardNumber = "021";
                            break;

                        case "005":
                            cardNumber = "005";
                            break;

                        case "920":
                            cardNumber = "920";
                            break;

                        case "905":
                            cardNumber = "905";
                            break;

                        default:
                            break;

                    }

                    String autoResposeStatus = "";
                    if (cardNumber.equals("")) {
                        String[] autoresponse = new AutoRequest().statusCheck(f.getReference(), cardNumber,
                                f.getChannel());
                        System.out.println("Response from autoswitch" + autoresponse);

                        System.out.println("End status check");

                        System.out.println("Ending check status");

                        System.out.println("Response Received from autoswitch status check" + autoresponse);

                        autoResposeStatus = autoresponse[0];

                    } else {

                    }

                    MobileMoneyReprocess mmr = new MobileMoneyReprocess();

                    if (autoResposeStatus != null || !autoResposeStatus.trim().isEmpty()) {

                        ApiPostData apiDataResponse = j.fromJson(jsonString, ApiPostData.class);

                        String status = apiDataResponse.getStatus();
                        String account = apiDataResponse.getAccount();
                        String client = apiDataResponse.getClient();
                        String vastype = apiDataResponse.getVastype();
                        String amount = apiDataResponse.getAmount();
                        String mobile = apiDataResponse.getAccount();
                        String otherInfo = apiDataResponse.getOtherInfo();

                        System.out.println("Status" + status);

                        if (autoResposeStatus.equals("00")) {

                            mmr.setReference(f.getReference());
                            mmr.setStatus(status);
                            mmr.setOtherInfo(otherInfo);
                            mmr.setAmount(amount);

                            System.out.println("account" + account);
                            System.out.println("client" + client);
                            System.out.println("vastype" + vastype);
                            System.out.println("amount" + amount);
                            System.out.println("mobile" + mobile);
                            System.out.println("otherInfo" + otherInfo);

                            records.add(mmr);

                        }

                    }

                } catch (Exception e) {
                    log.error(ERROR, e);
                }

            }
        });

        return records;
    }

    public List<MobileMoneyReprocess> callTransqueryApi(String jsonString) {

        log.info("Transquery API request received >> {0}" + jsonString);
        Gson j = new Gson();

        ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);

        String data = apiData.getData();

        List<MobileMoneyReprocess> records = new ArrayList<>();

        List<String> roleList = new ArrayList<>();
        List<Object[]> resp = new ArrayList<>();
        List<String> references = new ArrayList<>();

        String userRoles = "";

        Boolean hashAcc = true;

        List<MobileMoneyReprocess> recordsList = gson.fromJson(data, new TypeToken<List<MobileMoneyReprocess>>() {
        }.getType());

        recordsList.stream().forEach(f -> {
            if (f.getReference() != null && !f.getReference().trim().isEmpty()) {
                references.add(f.getReference().trim());
                System.out.println("References from excel file for trasquery" + f.getReference());

                System.out.println("Calling check status");

                try {
                    System.out.println("Starting check status");

                    StringBuilder str = new StringBuilder();

                    String preCheckTransFirst = config.getProperty("preCheckTransFirst");
                    String preCheckTransSecond = config.getProperty("preCheckTransSecond");

                    str.append(preCheckTransFirst);
                    str.append(f.getReference());
                    str.append(preCheckTransSecond);

                    System.out.println("String builder url: ");

                    String responseData = new DoHTTPRequest().get2(str.toString());

                    System.out.println("Response from transquery" + responseData);

                    System.out.println("End status check");

                    System.out.println("Ending check status");

                    System.out.println("Response Received from status check" + responseData);

                    MobileMoneyReprocess mmr = new MobileMoneyReprocess();

                    if (responseData != null || !responseData.trim().isEmpty()) {

                        ApiPostData apiDataResponse = j.fromJson(responseData, ApiPostData.class);

                        String status = apiDataResponse.getStatus();
                        String account = apiDataResponse.getAccount();
                        String client = apiDataResponse.getClient();
                        String vastype = apiDataResponse.getVastype();
                        String amount = apiDataResponse.getAmount();
                        String mobile = apiDataResponse.getAccount();
                        String otherInfo = apiDataResponse.getOtherInfo();

                        System.out.println("Status" + status);

                        if (status.equals("00")) {

                            mmr.setReference(f.getReference());
                            mmr.setStatus(status);
                            mmr.setOtherInfo(otherInfo);
                            mmr.setAmount(amount);

                            System.out.println("account" + account);
                            System.out.println("client" + client);
                            System.out.println("vastype" + vastype);
                            System.out.println("amount" + amount);
                            System.out.println("mobile" + mobile);
                            System.out.println("otherInfo" + otherInfo);

                            records.add(mmr);

                        }

                    }

                } catch (Exception e) {
                    log.error(ERROR, e);
                }

            }
        });

        return records;
    }

    public List<MobileMoneyReprocess> callReprocessingTable(String jsonString) {
        System.out.println("Reprocess Table Call Started");

        log.info("Reprocess table data request received >> {0}" + jsonString);
        Gson j = new Gson();

        ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);

        String data = apiData.getData();
        String username = apiData.getUserName();
        System.out.println("Data from table" + data);

        List<MobileMoneyReprocess> records = new ArrayList<>();

        List<String> roleList = new ArrayList<>();
        List<Object[]> resp = new ArrayList<>();
        List<String> references = new ArrayList<>();

        String userRoles = "";

        Boolean hashAcc = true;

        List<MobileMoneyReprocess> recordsList = gson.fromJson(data, new TypeToken<List<MobileMoneyReprocess>>() {
        }.getType());

        recordsList.stream().forEach(f -> {
            if (f.getReference() != null && !f.getReference().trim().isEmpty()) {

                System.out.println("Response Received from table" + data);

                String reference = f.getReference();
                String status = f.getStatus();
                String otherInfo = f.getOtherInfo();
                String amount = f.getAmount();

                System.out.println("reference" + f.getReference());
                System.out.println("status" + status);
                System.out.println("amount" + amount);
                System.out.println("otherInfo" + otherInfo);

                insertReprocessedData(f.getReference().trim(), f.getReference().trim() + "REP", username, amount,
                        status, otherInfo);

            }
        });

        System.out.println("Printing Records" + records);

        return records;
    }

    public static boolean insertReprocessedData(String originalReference, String updatedReference, String initiatedBy,
            String amount, String status, String otherInfo) {

        System.out.println("Start insert data into reprocess table");
        boolean result = false;

        List<Object[]> resp = new ArrayList<>();

        String Qry = "INSERT IGNORE INTO telcodb.mobilemoney_reprocess_log(original_reference, updated_reference, initiated_by, initiated_date, amount, status, other_info)Values(:original_reference, :updated_reference, :initiated_by, now() , :amount ,:status, :otherInfo)";

        Transaction tx = null;

        Session session = DbHibernate.getSession("40.9MYSQLW");

        try {
            tx = session.beginTransaction();

            Query q = session.createNativeQuery(Qry);

            q.setParameter("original_reference", originalReference);
            q.setParameter("updated_reference", updatedReference);
            q.setParameter("initiated_by", initiatedBy);

            q.setParameter("status", status);
            q.setParameter("amount", amount);
            q.setParameter("otherInfo", otherInfo);

            int h = q.executeUpdate();
            tx.commit();

            System.out.println("H: " + h);

            result = true;

        } catch (Exception et) {
            log.error(ERROR, et);
            if (tx != null) {
                try {
                    tx.rollback();
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return result;
    }

    public List<MobileMoneyReprocess> getMobileMoneyReprocessingLogReport(String jsonString) {

        log.info("Mobile Money Reprocess request received >> {0}" + jsonString);
        Gson j = new Gson();

        ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);
        String start_dt = apiData.getStartDate();

        String end_dt = apiData.getEndDate();

        String reference = apiData.getUniqueTransId();
        String user = apiData.getSubscriberId();
        String user_code = apiData.getUserCode();
        String type_id = apiData.getType_id();
        String searchKey = apiData.getSearchKey();
        final String service = apiData.getService();

        String originalReference = apiData.getOriginalReference();

        List<MobileMoneyReprocess> records = new ArrayList<>();

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

        if (originalReference == null) {
            originalReference = "";
        }

        if (service.equalsIgnoreCase("updatesearch")) {
            reference = searchKey.trim();
        }

        System.out.println("SEARCH: " + service);

        String query = "select original_reference, updated_reference, initiated_by, initiated_date, authorised_by, authorised_date, status, amount,other_info from telcodb.mobilemoney_reprocess_log where 1=1 "
                + (!originalReference.isEmpty() ? " and original_reference = :originalReference " : "")
                + " and initiated_date between :start_dt and :end_dt  "
                + " order by initiated_date desc";

        Session session = DbHibernate.getSession("VASREPROCESS");

        try {

            Query q = session.createNativeQuery(query);

            q.setParameter("start_dt", (Object) start_dt);
            q.setParameter("end_dt", (Object) end_dt);

            if (!originalReference.equals("")) {
                q.setParameter("originalReference", (Object) originalReference);
            }

            if (!user.isEmpty()) {
                q.setParameter("user", "%" + user + "%");
            }

            List<Object[]> resp = q.getResultList();

            for (Object[] record : resp) {
                MobileMoneyReprocess mmr = new MobileMoneyReprocess();
                String bankCode = "";
                String network = "";

                try {

                    mmr.setOriginalReference(checkNull(record[0]));
                    mmr.setUpdatedReference(checkNull(record[1]));
                    mmr.setInitiatedBy(checkNull(record[2]));
                    mmr.setInitiatedDate(checkNull(record[3]));
                    mmr.setAuthorisedBy(checkNull(record[4]));
                    mmr.setAuthorisedDate(checkNull(record[5]));
                    mmr.setStatus(checkNull(record[6]));
                    mmr.setAmount(checkNull(record[7]));
                    mmr.setOtherInfo(checkNull(record[8]));

                } catch (Exception e) {
                    log.error(ERROR, e);
                }

                records.add(mmr);

            }

        } catch (Exception e) {
            log.error(ERROR, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        System.out.println("RE: " + records);

        return records;
    }

    public List<MobileMoneyReprocess> callMobileMoneyUpdateTable(String jsonString) {
        System.out.println("Reprocess Table Call Started");

        log.info("Reprocess table data request received >> {0}" + jsonString);
        Gson j = new Gson();

        ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);

        String data = apiData.getData();
        String username = apiData.getUserName();
        System.out.println("Data for updating table" + data);

        List<MobileMoneyReprocess> records = new ArrayList<>();

        List<String> roleList = new ArrayList<>();
        List<Object[]> resp = new ArrayList<>();
        List<String> references = new ArrayList<>();

        String userRoles = "";

        Boolean hashAcc = true;

        List<MobileMoneyReprocess> recordsList = gson.fromJson(data, new TypeToken<List<MobileMoneyReprocess>>() {
        }.getType());

        recordsList.stream().forEach(f -> {

            if (f.getOriginalReference() != null && !f.getOriginalReference().trim().isEmpty()) {
                references.add(f.getOriginalReference().trim());

                System.out.println("Response Received from table for update" + data);

                String reference = f.getOriginalReference();
                String status = f.getStatus();
                String otherInfo = f.getOtherInfo();
                String amount = f.getAmount();

                System.out.println("reference" + f.getOriginalReference());
                System.out.println("status" + status);
                System.out.println("amount" + amount);
                System.out.println("otherInfo" + otherInfo);
                System.out.println("username" + username);

            }
        });

        System.out.println("Printing References for update" + references);

        updateMobileMoneyTable(references);
        updateMobileMoneyReprocessLogTable(references, username);

        System.out.println("Printing Records" + records);

        return records;
    }

    public static boolean updateMobileMoneyTable(List<String> references) {

        System.out.println("Start update data into reprocess table");
        boolean result = false;

        List<Object[]> resp = new ArrayList<>();

        String updateqry = "UPDATE telcodb.mobilemoney set FLAG =null,switchcode = null, reference = CONCAT(reference , 'REP') where reference in (:reference)";

        Transaction tx = null;

        Session session = DbHibernate.getSession("VASREPROCESS");

        try {
            tx = session.beginTransaction();

            Query q = session.createNativeQuery(updateqry);

            q.setParameter("reference", references);

            int h = q.executeUpdate();
            tx.commit();
            result = true;

            System.out.println("H: " + h);

        } catch (Exception et) {
            log.error(ERROR, et);
            if (tx != null) {
                try {
                    tx.rollback();
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return result;
    }

    public static boolean updateMobileMoneyReprocessLogTable(List<String> references, String username) {

        System.out.println("Start update data into reprocess log table");
        boolean result = false;
        boolean rollback = false;

        List<Object[]> resp = new ArrayList<>();

        String qry = "UPDATE telcodb.mobilemoney_reprocess_log set authorised_by =:authorised_by, authorised_date = now() where original_reference in (:reference)";

        Transaction tx = null;

        Session session = DbHibernate.getSession("VASREPROCESS");

        try {
            tx = session.beginTransaction();

            Query q = session.createNativeQuery(qry);

            q.setParameter("reference", references);

            q.setParameter("authorised_by", username);

            int h = q.executeUpdate();
            tx.commit();
            result = true;

            System.out.println("H: " + h);

        } catch (Exception et) {
            log.error(ERROR, et);
            if (tx != null) {
                try {
                    tx.rollback();
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return result;
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

            accNo = hn.hashStringValue(accNo, start / 2, start / 2);
        }
        return accNo;
    }

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
