/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etzgh.xportal.dao;

import com.etz.vasgate.models.VasgateRequest;
import com.etz.vasgate.util.Encryption;
import com.etz.vasgate.util.NewEncryption;
import static com.etzgh.xportal.dao.NlaLiquidationReportDao.insertNlaReprocessedData;
import static com.etzgh.xportal.dao.NlaLiquidationReportDao.updateReversalData;
import com.etzgh.xportal.model.ApiPostData;
import com.etzgh.xportal.model.MomoUpdate;
import com.etzgh.xportal.model.NODES;
import com.etzgh.xportal.model.NlaLiquidationReport;
import com.etzgh.xportal.model.TMC;
import com.etzgh.xportal.model.TmcUpdate;
import com.etzgh.xportal.model.VasgateReprocess;
import com.etzgh.xportal.utility.Config;
import com.etzgh.xportal.utility.DbHibernate;
import com.etzgh.xportal.utility.DoHTTPRequest;
import com.etzgh.xportal.utility.GeneralUtility;
import com.etzgh.xportal.utility.HashNumber;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import java.util.stream.Collectors;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.json.JSONObject;

/**
 *
 * @author yaw.owusu-koranteng
 */
public class VasgateReprocessDao {

    private static final AppDao appDao = new AppDao();
    private static final GeneralUtility utility = new GeneralUtility();
    private static final Gson gson = new Gson();
    private static final Logger log = Logger.getLogger(VasgateReprocessDao.class.getName());
    private static Map<String, String> banks = new HashMap<>();

    private static final Config config = new Config();

    public static final String CLIENT = "XPORTAL";
    public static final String ERROR = "ERROR";

    private final static Config prop = new Config();
    private static String vas_url = "";
    private static String vas_user = "";
    private static String vas_pass = "";

    VasgateReprocess vr = new VasgateReprocess();

    public static void main(String[] args) {
        String r = "{\"merchant\":\"\",\"product\":\"\",\"accountNumber\":\"\",\"service\":\"search\",\"action\":\"\",\"apiSecret\":\"\",\"apiCode\":\"\",\"userCode\":\"[2000000000000060]|905,[2500000000000050]|77\",\"admin\":\"\",\"type_desc\":\"\",\"role_id\":\"\",\"type_id\":\"[4],[5],[6],[7],[20],[24],[39],[40]\",\"searchKey\":\"02RS42603E77662\",\"userName\":\"\",\"ClientId\":\"\",\"user_id\":\"10500000000000754\",\"pageNumber\":1,\"rowsPerPage\":1,\"trans_code\":\"\",\"bank_code\":\"905\",\"subscriberId\":\"\",\"trans_status\":\"\",\"bank\":\"\",\"biller\":\"\",\"transId\":\"\",\"bankid\":\"\",\"card_num\":\"\",\"payType\":\"\",\"wcMerchant\":\"\",\"lineType\":\"\",\"trans_channel\":\"\",\"terminal_id\":\"\",\"card_account\":\"\",\"cop_issuercode\":\"\",\"cop_role_id\":\"\",\"cop_company_id\":\"\",\"mobile_no\":\"\",\"phone\":\"\",\"type\":\"\",\"amount\":\"\",\"amount2\":\"\",\"refundStatus\":\"\",\"partialReversal\":\"\",\"reversalReason\":\"\",\"ipAddress\":\"0:0:0:0:0:0:0:1\",\"userData\":\"\",\"appId\":[],\"company\":\"\",\"options\":{}}";
        new TmcDao().getTmcTransactions(r);
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

    public List<TMC> getVasgateReprocessTransactions(String jsonString) {

        System.out.println("TMC trx request received >> " + jsonString);
        Gson j = new Gson();
        ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);

        String beginDate = apiData.getStartDate();
        String endDate = apiData.getEndDate();
        String trans_data = apiData.getUniqueTransId();
        String pan = apiData.getPan();
        String tmcSource = apiData.getFrom_source();
        String tmctarget = apiData.getDestination();
        String trans_status = apiData.getStatus();
        String transtype = apiData.getTransType();
        String channel = apiData.getChannel();
        String dbType = apiData.getOptionType();
        String userCode = apiData.getUserCode();
        String type_id = apiData.getType_id();
        String bankCode = apiData.getBank_code();
        String searchKey = apiData.getSearchKey();
        String service = apiData.getService();
        List<TMC> recordsList = new ArrayList<>();
        List<String> tmcSourceList = new ArrayList<>();
        List<String> tmcTargetList = new ArrayList<>();
        List<String> inconList = new ArrayList<>();
        Boolean hashAcc = true;

        String query = "";

        System.out.println(tmctarget);

        if (pan == null) {
            pan = "";
        }

        int panLength = pan.length();

        if (panLength >= 6) {
            pan = pan.substring(0, 5);
        } else if (panLength >= 3) {
            pan = pan.substring(0, panLength);
        }

        if (trans_data == null) {
            trans_data = "";
        }
        if (channel == null) {
            channel = "";
        }
        if (transtype == null) {
            transtype = "";
        }
        if (tmctarget == null || tmctarget.trim().isEmpty()) {
            tmctarget = "ALL";
        }
        if (tmcSource == null || tmcSource.trim().isEmpty()) {
            tmcSource = "ALL";
        }

        if (searchKey == null) {
            searchKey = "";
        }
        if (service.equalsIgnoreCase("search") && searchKey.trim().isEmpty()) {
            return recordsList;
        }

        String userRoles = "";
        String inconIds = "";

        if (!type_id.isEmpty()) {
            if (type_id.contains("[0]")) {
                hashAcc = false;
                System.out.println("Targer: " + tmctarget);
                if (!tmctarget.equalsIgnoreCase("all") && !tmctarget.trim().isEmpty()) {
                    tmcTargetList.add(tmctarget.trim());
                }
                System.out.println("Sou: " + tmcSource);
                if (!tmcSource.equalsIgnoreCase("all") && !tmcSource.trim().isEmpty()) {
                    tmcSourceList.add(tmcSource.trim());
                }
            } else if (type_id.contains("[6]")) {
                hashAcc = false;

                System.out.println("bank: " + bankCode);

                inconList = appDao.getInconId(bankCode);

                System.out.println("INCON : " + inconList + " " + tmcSource + " - " + inconList.contains(tmcSource));
                if (inconList.isEmpty() || (!tmcSource.equalsIgnoreCase("all") && !inconList.contains(tmcSource))) {
                    System.out.println("Not Allowed");
                    return recordsList;
                }
                if (inconList.isEmpty() || (!tmctarget.equalsIgnoreCase("all") && !inconList.contains(tmctarget))) {
                    System.out.println("Not Allowed");
                    return recordsList;
                }
                if (tmctarget.equalsIgnoreCase("all")) {
                    tmcTargetList = inconList;
                } else {
                    tmcTargetList.add(tmctarget);
                }
                if (tmcSource.equalsIgnoreCase("all")) {
                    tmcSourceList = inconList;
                } else {
                    tmcSourceList.add(tmcSource);
                }
            } else {
                return recordsList;
            }
        } else {
            return recordsList;
        }

        System.out.println("SOURCE: " + tmcSourceList);
        System.out.println("TARGET: " + tmcTargetList);

        switch (channel) {
            case "ATM":
                channel = "and left(trans_data,2) not in ('02','09','ju', 'us', '08','01') ";
                break;
            case "FUNDGATE":
                channel = "and left(trans_data,2) = '09' ";
                break;
            case "JUSTPAY":
                channel = " and left(trans_data,2) = 'ju' ";
                break;
            case "MOBILE":
                channel = " and left(trans_data,2) in ('02', 'us', 'ju') ";
                break;
            default:
                channel = "";
                break;
        }

        switch (transtype) {
            case "FundsTransfer":
                transtype = " and left(pro_code,2) = '42' ";
                break;
            case "Payments":
                transtype = " and left(pro_code,2) in ('53', '13') ";
                break;
            case "Withdrawals":
                transtype = " and left(pro_code,2) in ('01') ";
                break;
            default:
                break;
        }

        query = "select mti, trans_data, acct_id1, acct_id2, pro_code as transtype, trans_date, pro_code, pan, amount, track2, response_code,"
                + " response_code as response,  terminal_id, stan, card_acc_name, fee, reference, src_node, target_node, response_time from "
                + (!dbType.equals("old") ? " ecarddb.e_tmcrequest " : " ecarddb..e_tmcrequest ") + " where 1=1 "
                + (service.equalsIgnoreCase("search") ? " AND trans_data = :searchKey " : "  and trans_date BETWEEN :start_dt and :end_dt ")
                + (!pan.trim().isEmpty() ? " and pan like :pan " : "")
                + (!trans_data.trim().isEmpty() ? " and trans_data = :trans_data " : "")
                + (!channel.equals("ALL") ? "" + channel + "" : "")
                + (!transtype.equals("ALL") ? "" + transtype + "" : "")
                + (!tmcSourceList.isEmpty() ? " and src_node in (:tmcSource) " : "")
                + (!tmcSourceList.isEmpty() ? " and src_node in (:tmcSource) " : "")
                + (!tmcTargetList.isEmpty() ? " and target_node in (:tmctarget) " : "")
                + ((trans_status.equals("REVERSAL") ? " and MTI = '0420' " : "")
                + ((trans_status.equals("AMBIGUOUS") ? " and (response_code in ('-1', '91', null, '', '68'))" : ""))
                + ((trans_status.equals("FAIL") ? " and response_code <> '00'" : "")))
                + (trans_status.equals("SUCCESS") ? " and response_code = '00'" : "")
                + " order by trans_date desc";

        System.out.println("Query >> " + query);
        Session session = null;
        Query q;
        List<Object[]> records = new ArrayList<>();
        try {

            session = DbHibernate.getSession("NY");

            q = session.createNativeQuery(query);
            if (!service.equalsIgnoreCase("search")) {
                q.setParameter("start_dt", beginDate)
                        .setParameter("end_dt", endDate);
            } else {
                q.setParameter("searchKey", searchKey.trim());
            }
            if (!tmcSourceList.isEmpty()) {
                q.setParameter("tmcSource", tmcSourceList);
            }
            if (!tmcTargetList.isEmpty()) {
                q.setParameter("tmctarget", tmcTargetList);
            }
            if (!pan.trim().isEmpty()) {
                q.setParameter("pan", pan + "%");
            }
            if (!trans_data.trim().isEmpty()) {
                q.setParameter("trans_data", trans_data);
            }

            records = q.getResultList();

        } catch (Exception e) {
            log.error(ERROR, e);
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
        for (Object[] record : records) {
            TMC tmc = new TMC();
            try {
                tmc.setMti(checkNull(record[0]));
                tmc.setTrans_data(checkNull(record[1]));
                tmc.setSource_account(hashAccount(checkNull(record[2]), hashAcc));
                tmc.setDestination_account(hashAccount(checkNull(record[3]), hashAcc));

                tmc.setTranstype(mapTranstype(checkNull(record[6])));

                tmc.setTrans_date(convertDate(checkNull(record[5])));
                tmc.setPro_code(checkNull(record[6]));
                tmc.setPan(checkNull(record[7]));
                tmc.setAmount(BigDecimal.valueOf(Double.valueOf(checkNull(record[8]))));

                tmc.setResponse_code(checkNull(record[10]));
                tmc.setResponse(mapResponse(checkNull(record[10]), checkNull(record[7])));

                tmc.setTerminal_id(checkNull(record[12]));
                tmc.setStan(checkNull(record[13]));
                tmc.setCard_acc_name(checkNull(record[14]));
                tmc.setFee(BigDecimal.valueOf(Double.valueOf(checkNull(record[15]))));
                tmc.setReference(checkNull(record[16]));

                tmc.setSrc_node(banks.getOrDefault(checkNull(record[17]), ""));
                tmc.setTarget_node(banks.getOrDefault(checkNull(record[18]), ""));
                tmc.setResponse_time(convertDate(checkNull(record[19])));
            } catch (Exception et) {
                log.error(ERROR, et);
            }
            recordsList.add(tmc);
        }

        return recordsList;
    }

    public List<VasgateReprocess> reprocessVasgate(String jsonString) {

        log.info("Vasgate Reprocess status request received >> {0}" + jsonString);
        Gson j = new Gson();

        ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);
        String data = apiData.getUniqueTransId();

        List<VasgateReprocess> records = new ArrayList<>();

        List<String> roleList = new ArrayList<>();
        List<Object[]> resp = new ArrayList<>();
        List<String> references = new ArrayList<>();

        String userRoles = "";

        Boolean hashAcc = true;

        List<TmcUpdate> recordsList = gson.fromJson(data, new TypeToken<List<TmcUpdate>>() {
        }.getType());

        recordsList.stream().forEach(f -> {
            if (f.getReference() != null && !f.getReference().trim().isEmpty()) {
                references.add(f.getReference().trim());
                System.out.println("References from excel file" + f.getReference());

                System.out.println("Calling check status");

                try {

                    String response = checkStatus(f.getReference());
                    System.out.println("Ending check status");

                    System.out.println("Response Received" + response);

                    VasgateReprocess vr = new VasgateReprocess();

                    if (response != null || !response.trim().isEmpty()) {

                        ApiPostData apiDataResponse = j.fromJson(response, ApiPostData.class);

                        String status = apiDataResponse.getStatus();
                        String account = apiDataResponse.getAccount();
                        String client = apiDataResponse.getClient();
                        String vastype = apiDataResponse.getVastype();
                        String amount = apiDataResponse.getAmount();
                        String mobile = apiDataResponse.getAccount();
                        String otherInfo = apiDataResponse.getOtherInfo();

                        System.out.println("Status" + status);

                        System.out.println("account" + account);
                        System.out.println("client" + client);
                        System.out.println("vastype" + vastype);
                        System.out.println("amount" + amount);
                        System.out.println("mobile" + mobile);
                        System.out.println("otherInfo" + otherInfo);

                        vr.setReference(f.getReference());
                        vr.setStatus(status);
                        vr.setAccount(account);
                        vr.setClient(client);
                        vr.setVastype(vastype);
                        vr.setAmount(amount);
                        vr.setMobile(mobile);
                        vr.setNarration(otherInfo);

                    }

                    records.add(vr);

                } catch (Exception e) {
                    log.error(ERROR, e);
                }

            }
        });

        return records;
    }

    public List<VasgateReprocess> callVasgateTable(String jsonString) {

        log.info("Vasgate call api data request received >> {0}" + jsonString);
        Gson j = new Gson();

        ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);

        String data = apiData.getData();
        System.out.println("Data from api:" + data);

        List<VasgateReprocess> records = new ArrayList<>();

        List<String> roleList = new ArrayList<>();
        List<Object[]> resp = new ArrayList<>();
        List<String> references = new ArrayList<>();

        String userRoles = "";

        Boolean hashAcc = true;

        List<VasgateReprocess> recordsList = gson.fromJson(data, new TypeToken<List<VasgateReprocess>>() {
        }.getType());

        recordsList.stream().forEach(f -> {
            if (f.getReference() != null && !f.getReference().trim().isEmpty()) {

                System.out.println("References from excel file" + f.getReference());

                System.out.println("Response Received" + data);
                System.out.println("Response Received first four" + f.getReference().substring(0, 4).equals("09FG"));

                String status = f.getStatus();

                String account = "";
                String client = "";
                String vastype = "";
                String amount = "";
                String mobile = "";

                if (status.equals("00")) {

                    if (f.getReference().substring(0, 4).equals("just")) {

                        references.add(f.getReference().trim());

                    } else if (f.getReference().substring(0, 4).equals("09FG")) {

                        System.out.println("Fundgate reference add started");

                        references.add(f.getReference().trim());

                    } else if (f.getReference().substring(0, 2).equals("02")) {

                        System.out.println("Bank reference add started");

                        references.add(f.getReference().trim());

                    }

                }

            }
        });

        System.out.println("References received" + references);

        if (references.get(0).substring(0, 4).equals("just")) {

            List<Object[]> respo = callJustPayTable(references);

            for (Object[] record : respo) {

                VasgateReprocess vrs = new VasgateReprocess();
                try {

                    System.out.println("reference" + record[0]);
                    System.out.println("mainoptions" + record[1]);
                    System.out.println("amount" + record[2]);
                    System.out.println("vastype" + record[3]);
                    System.out.println("provider" + record[4]);
                    System.out.println("destacct" + record[5]);
                    System.out.println("mmno" + record[6]);
                    System.out.println("mmnocode" + record[7]);

                    vrs.setReference(checkNull(record[0]));
                    vrs.setStatus("00");
                    vrs.setAccount(checkNull(record[5]));
                    vrs.setClient("JustPay");
                    vrs.setVastype(checkNull(record[3]));
                    vrs.setAmount(checkNull(record[2]));
                    vrs.setMobile(checkNull(record[6]));
                    vrs.setTranstype(checkNull(record[1]));
                    vrs.setMmnocode(checkNull(record[7]));

                } catch (Exception e) {
                    log.error(ERROR, e);
                }

                records.add(vrs);

            }

        } else if (references.get(0).substring(0, 4).equals("09FG")) {

            List<Object[]> respo = callFundgateTable(references);

            for (Object[] record : respo) {

                VasgateReprocess vrs = new VasgateReprocess();
                try {

                    System.out.println("reference" + record[1]);

                    System.out.println("amount" + record[3]);
                    System.out.println("vastype" + record[2]);

                    System.out.println("destacct" + record[4]);
                    System.out.println("action" + record[0]);
                    System.out.println("terminal" + record[5]);

//                    vrs.setReference(checkNull(record[0]));
//                    vrs.setStatus("00");
//                    vrs.setAccount(checkNull(record[3]));
//                    vrs.setClient("Fundgate");
//                    vrs.setVastype(checkNull(record[2]));
//                    vrs.setAmount(checkNull(record[1]));
                    if (checkNull(record[0]).equals("VT")) {
                        vrs.setTranstype("airtime");
                    }
//                    else {
//                        vasRequest.setMerchant(vastype);
//                    }

                    vrs.setReference(checkNull(record[1]));
                    vrs.setStatus("00");
                    vrs.setAccount(checkNull(record[4]));
                    vrs.setClient("Fundgate");
                    vrs.setVastype(checkNull(record[2]));
                    vrs.setAmount(checkNull(record[3]));
                    vrs.setMobile(checkNull(record[4]));
                    //vrs.setTranstype(checkNull(record[1]));
                    vrs.setMmnocode(checkNull(record[5]).substring(0, 4));

                } catch (Exception e) {
                    log.error(ERROR, e);
                }

                records.add(vrs);

            }

        } else if (references.get(0).substring(0, 2).equals("02")) {

            List<Object[]> respo = callMobileTable(references);

            for (Object[] record : respo) {

                VasgateReprocess vrs = new VasgateReprocess();
                try {

                    System.out.println("reference" + record[0]);
                    System.out.println("amount" + record[3]);
                    System.out.println("vastype" + record[1]);
                    System.out.println("transtype" + record[2]);
                    System.out.println("destacct" + record[5]);
                    System.out.println("mobile" + record[4]);

                    vrs.setReference(checkNull(record[0]));
                    vrs.setStatus("00");
                    vrs.setAccount(checkNull(record[5]));
                    vrs.setClient("XPORTAL");
                    vrs.setVastype(checkNull(record[1]));
                    vrs.setAmount(checkNull(record[3]));
                    vrs.setMobile(checkNull(record[4]));
                    vrs.setTranstype(checkNull(record[2]));

                } catch (Exception e) {
                    log.error(ERROR, e);
                }

                records.add(vrs);

            }

        }

        System.out.println("Printing Records" + records);

        return records;
    }

    public String callInitiateVasReprocess(String jsonString) {

        log.info("Vas initiate reversal request received >>  " + jsonString);
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

        String data = apiData.getData();
        List<VasgateReprocess> recordsList = gson.fromJson(data, new TypeToken<List<VasgateReprocess>>() {
        }.getType());

        //String transId = recordsList.get
        log.info("Vas data reprocess request received >> " + recordsList);

        String respData = "";

        for (int i = 0; i < recordsList.size(); i++) {
            String originalReference = recordsList.get(i).getReference();
            String account = recordsList.get(i).getAccount();
            String client = recordsList.get(i).getClient();
            String vasType = recordsList.get(i).getVastype();
            String amount = recordsList.get(i).getAmount();
            String mobile = recordsList.get(i).getMobile();
            String transtype = recordsList.get(i).getTranstype();
            String mmnocode = recordsList.get(i).getMmnocode();

            System.out.println("originalReference" + originalReference);
            System.out.println("account" + account);
            System.out.println("client" + client);
            System.out.println("vasType" + vasType);
            System.out.println("amount" + amount);
            System.out.println("mobile" + mobile);
            System.out.println("transtype" + transtype);
            System.out.println("mmnocode" + mmnocode);

            respData = insertVasReprocessedData(originalReference, account, client, vasType, amount, mobile, transtype, mmnocode, username, "PENDING");

        }

        //System.out.println("Looping completed");
//        String referenceNew = recordsList.get(0).getReference();
//        String subscriberId = recordsList.get(0).getSubscriber_id();
//        String uniqueTransId = recordsList.get(0).getUnique_transid();
//        String responseDate = recordsList.get(0).getResponse_date();
//        String newCustomerId = recordsList.get(0).getNewCustomerId();
//        System.out.println("transid" + transid);
//        System.out.println("subscriberId" + subscriberId);
//        System.out.println("uniqueTransId" + uniqueTransId);
//        System.out.println("responseDate" + responseDate);
//        System.out.println("newCustomerId" + newCustomerId);
        //String respData = insertVasReprocessedData(transid, subscriberId, uniqueTransId, responseDate, newCustomerId, username, "PENDING");
//        String respData = "";
        return respData;
    }

    public static String insertVasReprocessedData(String originalReference, String account, String client, String vastype, String amount, String mobile, String transtype, String mmno, String username, String status) {

        System.out.println("Start insert data into vas reprocess table");
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
        String Qry = "INSERT IGNORE INTO vasdb2.vas_reprocess_log(original_reference,status, account, client, vastype, amount, mobile, transtype,mmno, initiated_by, initiated_date)Values(:original_reference, :status, :account, :client, :vastype, :amount, :mobile, :transtype, :mmno, :initiated_by, now())";

        //String Qry = "INSERT IGNORE INTO banks_direct.nla_reversal_log(initiated_by, initiated_date,trans_id, customer_id, etz_reference, new_customer_id, status)Values(:initiated_by, now() , :trans_id ,:customer_id, :etz_reference, :newCustomer_id, :status)";
        //String tmcqry = "UPDATE ecarddb.e_tmcrequest set respcode =:respcode, processdate = now() where reference = :reference ";
        //String tmcqry = "UPDATE ecarddb.e_tmcrequest set response_code =:respcode where trans_data = :reference ";
        //String fgqry = "SELECT etzref, respcode from fgdb.fundgate_response where etzref =:reference ";
        //String mpqry = "SELECT reference, trnx_status from ussd_db.mpay_report where reference =:reference ";
        Transaction tx = null;
        //Session usession = DbHibernate.getSession("40.9MYSQLW");
        //Session session = DbHibernate.getSession("1.230MYSQL");
        Session session = DbHibernate.getSession("40.9MYSQLW");
        //Session session = DbHibernate.getSession("30.19MYSQL");

        try {
            tx = session.beginTransaction();
            //log record
            Query q = session.createNativeQuery(Qry);
            q.setParameter("original_reference", originalReference);
            //q.setParameter("updated_reference", updatedReference);
            q.setParameter("status", status);
            q.setParameter("account", account);
            q.setParameter("client", client);
            q.setParameter("vastype", vastype);
            q.setParameter("amount", amount);
            q.setParameter("mobile", mobile);
            q.setParameter("transtype", transtype);
            q.setParameter("mmno", mmno);
            q.setParameter("initiated_by", username);

            //int h = q.executeUpdate();
            int h = q.executeUpdate();

            System.out.println("H: " + h);

            if (h > 0) {
                respData = "Initiate Successful";
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
                    log.error(er.getMessage(), er);
                }
            }

        } catch (Exception et) {
            log.error(et.getMessage(), et);
            if (tx != null) {
                try {
                    tx.rollback();
                } catch (Exception er) {
                    log.error(er.getMessage(), er);
                }
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return respData;
    }

    public List<VasgateReprocess> getVasReprocessTransactionsAuthorizeReport(String jsonString) {

        log.info("Vas trans authorize request received >> " + jsonString);
        Gson j = new Gson();

        ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);
        String start_dt = apiData.getStartDate();
        //System.out.println("Start Date:"+start_dt);
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
        String originalReference = apiData.getOriginalReference();
        //System.out.println("etzReference" + etzReference);

        List<VasgateReprocess> records = new ArrayList<>();

//        log.log(Level.INFO, "USER CODE >>{0}", user_code);
        String userRoles = "";

        if (originalReference == null) {
            originalReference = "";
        } else {
            originalReference = originalReference.trim();
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
//        if (originalReference == null) {
//            originalReference = "";
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

        String query = "select original_reference, updated_reference, status, account, client, vastype, amount,mobile, transtype, mmno, response_code, response_message, initiated_by, initiated_date, authorised_by, authorised_date from vasdb2.vas_reprocess_log where 1=1 "
                //+ " and created between :start_dt and :end_dt"
                //+ (service.equalsIgnoreCase("nlaliquidationreport") ? "  AND reference = :reference " : " and created between :start_dt  and :end_dt ")
                //+ (!user.isEmpty() ? " and update_by like  :user " : "")
                //                + (!ticketNumber.isEmpty() ? " and ticket_number = :ticket_number " : "")
                //                + (!phoneNumber.isEmpty() ? " and phone_number = :phone_number " : "")
                //                + (!nlaReference.isEmpty() ? " and reference = :reference " : "")
                + (!originalReference.isEmpty() ? " and original_reference = :originalReference " : "")
                + " and initiated_date between :start_dt and :end_dt  "
                + " order by initiated_date desc";

//        String query = "select initiated_by, initiated_date, authorised_by, authorised_date,trans_id, customer_id, etz_reference, new_customer_id, code, transaction_reference, reversal_reference,response, status  from banks_direct.nla_reversal_log where 1=1 "
//                //+ " and created between :start_dt and :end_dt"
//                //+ (service.equalsIgnoreCase("nlaliquidationreport") ? "  AND reference = :reference " : " and created between :start_dt  and :end_dt ")
//                //+ (!user.isEmpty() ? " and update_by like  :user " : "")
//                //                + (!ticketNumber.isEmpty() ? " and ticket_number = :ticket_number " : "")
//                //                + (!phoneNumber.isEmpty() ? " and phone_number = :phone_number " : "")
//                //                + (!nlaReference.isEmpty() ? " and reference = :reference " : "")
//                + (!etzReference.isEmpty() ? " and etz_reference = :etzReference " : "")
//                + " and initiated_date between :start_dt and :end_dt  "
//                + " order by initiated_date desc";
        System.out.println("Query >> " + query);
        Session session = DbHibernate.getSession("40.9MYSQLW");
        //Session session = DbHibernate.getSession("30.19MYSQL");
        //Session session = DbHibernate.getSession("NLA");

//        System.out.println("STARTING ");
        try {

            Query q = session.createNativeQuery(query);

//            q.setParameter("start_dt", (Object) startDate);
//            q.setParameter("end_dt", (Object) endDate);
            q.setParameter("start_dt", (Object) start_dt);
            q.setParameter("end_dt", (Object) end_dt);

            //q.setParameter("ticket_number", (Object) ticketNumber);
//            q.setParameter("phoneNumber", (Object) phoneNumber);
//            q.setParameter("nlaReference", (Object) nlaReference);
//            q.setParameter("etzReference", (Object) etzReference);
//            if (!ticketNumber.equals("")) {
//                q.setParameter("ticket_number", (Object) ticketNumber);
//            }
//            if (!phoneNumber.equals("")) {
//                q.setParameter("phone_number", (Object) phoneNumber);
//            }
//            
//            if (!nlaReference.equals("")) {
//                q.setParameter("reference", (Object) nlaReference);
//            }
            if (!originalReference.equals("")) {
                q.setParameter("originalReference", (Object) originalReference);
            }

//            if (!service.equalsIgnoreCase("nlaliquidationreport")) {
//                q.setParameter("start_dt", (Object) start_dt)
//                        .setParameter("end_dt", (Object) end_dt);
//            } else {
//                q.setParameter("reference", (Object) reference);
//            }
//            if (!user.isEmpty()) {
//                q.setParameter("user", "%" + user + "%");
//            }
            List<Object[]> resp = q.getResultList();
            System.out.println("Resp" + resp);
            System.out.println("DONE");

            for (Object[] record : resp) {
                VasgateReprocess vr = new VasgateReprocess();
                String bankCode = "";
                String network = "";

                try {

                    vr.setOriginal_reference(checkNull(record[0]));
                    vr.setUpdated_reference(checkNull(record[1]));
                    vr.setStatus(checkNull(record[2]));
                    vr.setAccount(checkNull(record[3]));
                    vr.setClient(checkNull(record[4]));
                    vr.setVastype(checkNull(record[5]));
                    vr.setAmount(checkNull(record[6]));
                    vr.setMobile(checkNull(record[7]));
                    vr.setTranstype(checkNull(record[8]));
                    vr.setMmno(checkNull(record[9]));
                    vr.setResponse_code(checkNull(record[10]));
                    vr.setResponse_message(checkNull(record[11]));
                    vr.setInitiated_by(checkNull(record[12]));
                    vr.setInitiated_date(checkNull(record[13]));
                    vr.setAuthorized_by(checkNull(record[14]));
                    vr.setAuthorized_date(checkNull(record[15]));

                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }

                records.add(vr);

            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        System.out.println("RE: " + records);

        return records;
    }

    public String callVasAuthorizeReprocess(String jsonString) {

        log.info("Vas authorize reprocess request received >> " + jsonString);
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
        //List<String> references = new ArrayList<>();

        String vasReprocessResponse = "";
        //String nlaResponse = "";
//          String vasReprocessResponse;

        String data = apiData.getData();
        log.info("Vas data received >> " + data);

//        List<String> dataList = Arrays.asList(data);
//
//        log.info("Vas data list received >> " + dataList.get(0));
//         ArrayList<String> strList = new ArrayList<String>(
//            Arrays.asList(data));
        //log.info("Vas data list received 1 >> " + strList);
        //List<String> dataListNew = dataList.get(0);
        List<String> recordsList = gson.fromJson(data, new TypeToken<List<String>>() {
        }.getType());

        log.info("Vas record list received >> " + recordsList);

        //String transId = recordsList.get
        //log.info("Vas data reversal request received >> " + recordsList);
//                List<VasgateReprocess> recordsList = gson.fromJson(data, new TypeToken<List<VasgateReprocess>>() {
//        }.getType());
//        final String _respcode = respcode;
//        final String _fgRespcode = fgRespcode;
//        final String _narration = narration;
//        final String _status = status.substring(0, 1).toUpperCase() + status.substring(1);
//        recordsList.stream().forEach(f -> {
//            if (f.getOriginal_reference() != null && !f.getOriginal_reference().trim().isEmpty()) {
//                references.add(f.getOriginal_reference().trim());
//
//                //updateMomo(f.getReference().trim(), f.getClientid().trim(), _narration, _respcode, _fgRespcode, _status, username, userId, ipAddress);
//            }
//        });
//        
//        System.out.println("References"+references);
        //recordsList.stream().forEach(f -> {
//            if (f.getReference() != null && !f.getReference().trim().isEmpty()) {
//
//            }
        for (int i = 0; i < recordsList.size(); i++) {

            //}
            String originalReference = "";
            String updatedReference = "";
            //String status = "";
            String account = "";
            String client = "";
            String vasType = "";
            String amount = "";
            String mobile = "";
            String transType = "";
            String mmno = "";
            String statusResponse = "";
//        String initiatedBy = "";
//        String initiatedDate = "";
//        String authorizedBy = "";
//        String authorizedDate = "";

            originalReference = recordsList.get(i);
            System.out.println("Original Reference received" + originalReference);
//           updatedReference = recordsList.get(i).getUpdated_reference();
//           status = recordsList.get(i).getStatus();
//           account = recordsList.get(i).getAccount();
//           client = recordsList.get(i).getClient();
//           vasType = recordsList.get(i).getVastype();
//           amount = recordsList.get(i).getAmount();
//           mobile = recordsList.get(i).getMobile();
//           transType = recordsList.get(i).getTranstype();
//           mmno = recordsList.get(i).getMmno();

            String query = "select original_reference,updated_reference,status, account,client, vastype, amount, mobile, transtype, mmno,  initiated_date  from vasdb2.vas_reprocess_log where 1=1 "
                    //+ " and created between :start_dt and :end_dt"
                    //+ (service.equalsIgnoreCase("nlaliquidationreport") ? "  AND reference = :reference " : " and created between :start_dt  and :end_dt ")
                    //+ (!user.isEmpty() ? " and update_by like  :user " : "")
                    //                + (!ticketNumber.isEmpty() ? " and ticket_number = :ticket_number " : "")
                    //                + (!phoneNumber.isEmpty() ? " and phone_number = :phone_number " : "")
                    //                + (!nlaReference.isEmpty() ? " and reference = :reference " : "")
                    + (!originalReference.isEmpty() ? " and original_reference = :original_reference" : "")
                    //+ " and initiated_date between :start_dt and :end_dt  "
                    + " order by initiated_date desc";

            Session session = DbHibernate.getSession("40.9MYSQLW");
            //Session session = DbHibernate.getSession("30.19MYSQL");
            System.out.println("Query >> " + query);

            try {

                Query q = session.createNativeQuery(query);

                System.out.println("Query q>> " + q.getQueryString());
                System.out.println("Query originalReference>> " + originalReference);

                q.setParameter("original_reference", originalReference);
                // q.setParameter("reference", StringUtils.join(references, ','));

                List<Object[]> respo = q.getResultList();

                int result = respo.size();
                System.out.println("Rows affected for status check: " + result);

                //String vasReprocessResponse = "";
                for (Object[] record : respo) {
//                NlaLiquidationReport nlr = new NlaLiquidationReport();
//                String bankCode = "";
//                String network = "";
                    // vr.setOriginal_reference(checkNull(record[0]));

                    try {

//                    String statusResponse = record[0].toString();
//                          updatedReference = record[1].toString();
//                   statusResponse = record[2].toString();
//                   account = record[3].toString();
//                   client = record[4].toString();
//                   vasType = record[5].toString();
//                   amount = record[6].toString();
//                   mobile = record[7].toString();
//                   transType = record[8].toString();
//                   mmno = record[9].toString();
                        updatedReference = checkNull(record[1]);
                        statusResponse = checkNull(record[2]);
                        account = checkNull(record[3]);
                        client = checkNull(record[4]);
                        vasType = checkNull(record[5]);
                        amount = checkNull(record[6]);
                        mobile = checkNull(record[7]);
                        transType = checkNull(record[8]);
                        mmno = checkNull(record[9]);

                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                    }

                    System.out.println("statusResponse" + statusResponse);

                    if (statusResponse.equals("AUTHORISED")) {
                        vasReprocessResponse = "ALREADY AUTHORISED";
                    } else if (statusResponse.equals("DENIED")) {
                        vasReprocessResponse = "ALREADY DENIED";
                    } else {

                        updateReprocessedData(originalReference, originalReference + "REP", "AUTHORISED", username, "05", "PENDING");

//                     String vasresponse = sendVasgateRequest(account, recordsList.get(i).getReference(), vasType, amount, mobile, transType, mmno);
//                                     JSONObject jsonObject = new JSONObject(vasresponse);
                        String vasresponse = sendVasgateRequest(account, originalReference, vasType, amount, mobile, transType, mmno, client);
                        JSONObject jsonObject = new JSONObject(vasresponse);

                        //JSONObject jsonObjectVas = new JSONObject(vasresponse);
                        String vasstatus = jsonObject.getString("error");
                        System.out.println("vasstatus" + vasstatus);

                        switch (vasstatus) {
                            case "00":
                                try {

//                        vr.setReference(f.getReference());
//                        vr.setStatus("00");
//                        vr.setAccount(account);
//                        vr.setClient("JustPay");
//                        vr.setVastype(vasType);
//                        vr.setAmount(amount);
//                        vr.setMobile(mobile);
//                        vr.setTranstype(transType);
                                    System.out.println("Vasgate Status" + vasstatus);

//                        System.out.println("account" + account);
//                        System.out.println("client" + client);
//                        System.out.println("vastype" + vasType);
//                        System.out.println("amount" + amount);
//                        System.out.println("mobile" + mobile);
                                    System.out.println("Reference to update" + originalReference);

//                        updateReprocessedData(recordsList.get(i).getReference(),recordsList.get(i).getReference()+"REP", "AUTHORISED", username, "00", "SUCCESS");
                                    updateReprocessedData(originalReference, originalReference + "REP", "AUTHORISED", username, "00", "SUCCESS");
                                    vasReprocessResponse = "OPERATION SUCCESSFUL";

                                } catch (Exception e) {
                                    log.error(ERROR, e);
                                }
                                //records.add(vr);
                                break;
                            case "06":
                                try {

//                        vr.setReference(f.getReference());
//                        vr.setStatus("06");
//                        vr.setAccount(account);
//                        vr.setClient("JustPay");
//                        vr.setVastype(vasType);
//                        vr.setAmount(amount);
//                        vr.setMobile(mobile);
//                        vr.setTranstype(transType);
                                    System.out.println("Vasgate Status" + vasstatus);

//                        System.out.println("account" + account);
//                        System.out.println("client" + client);
//                        System.out.println("vastype" + vasType);
//                        System.out.println("amount" + amount);
//                        System.out.println("mobile" + mobile);
                                    System.out.println("Reference to update" + originalReference);

//                        updateReprocessedData(recordsList.get(i).getReference(),recordsList.get(i).getReference()+"REP", "AUTHORISED", username, "06", "FAILED");
                                    updateReprocessedData(originalReference, originalReference + "REP", "AUTHORISED", username, "06", "FAILED");
                                    vasReprocessResponse = "OPERATION FAILED";
                                } catch (Exception e) {
                                    log.error(ERROR, e);
                                }
                                //records.add(vr);
                                break;
                            case "31":
                                try {

//                        vr.setReference(f.getReference());
//                        vr.setStatus("31");
//                        vr.setAccount(account);
//                        vr.setClient("JustPay");
//                        vr.setVastype(vasType);
//                        vr.setAmount(amount);
//                        vr.setMobile(mobile);
//                        vr.setTranstype(transType);
                                    System.out.println("Vasgate Status" + vasstatus);

//                        System.out.println("account" + account);
//                        System.out.println("client" + client);
//                        System.out.println("vastype" + vasType);
//                        System.out.println("amount" + amount);
//                        System.out.println("mobile" + mobile);
                                    System.out.println("Reference to update" + originalReference);

//                       updateReprocessedData(recordsList.get(i).getReference(),recordsList.get(i).getReference()+"REP", "AUTHORISED", username, "31", "FAILED");
                                    updateReprocessedData(originalReference, originalReference + "REP", "AUTHORISED", username, "31", "FAILED");
                                    vasReprocessResponse = "OPERATION FAILED";
                                } catch (Exception e) {
                                    log.error(ERROR, e);
                                }
                                //records.add(vr);
                                break;
                            default:
                                break;
                        }

                    }

                }

//                            System.out.println("Response from table 0" + respo.get(0));
//                            System.out.println("Response from table 1" + respo.get(1));
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            } finally {
                if (session != null) {
                    session.close();
                }
            }

            //});
        }

        //select ended
        //String respData = insertNlaReprocessedData(transid, subscriberId,uniqueTransId, responseDate, newCustomerId , username);
        return vasReprocessResponse;
        //return "";
    }

    public static String checkStatus(String s) {

        List<Object[]> resp = new ArrayList<>();

        StringBuilder str = new StringBuilder();

        String preCheckTransFirst = config.getProperty("preCheckFirst");
        String preCheckTransSecond = config.getProperty("preCheckSecond");

        str.append(preCheckTransFirst);
        str.append(s);
        str.append(preCheckTransSecond);

        System.out.println("String builder url: ");

        String response = DoHTTPRequest.get2(str.toString());

        System.out.println("Response from transquery" + response);

        System.out.println("End status check");

        return response;

    }

    static {
        try {
            final String vas_env = prop.getProperty("VAS_ENV");
            vas_url = prop.getProperty("VAS_" + vas_env.toUpperCase() + "_URL");
            vas_user = prop.getProperty("VAS_" + vas_env.toUpperCase() + "_USER");
            vas_pass = prop.getProperty("VAS_" + vas_env.toUpperCase() + "_PASS");
        } catch (Exception e) {
            log.error(ERROR, e);
        }
    }

    public static List<Object[]> callJustPayTable(List<String> references) {

        List<Object[]> respo = new ArrayList<>();

        System.out.println("Call vasgate table stated with references" + references);

        String query = "select reference, mainoptions, amount, vastype, provider, destacct, mmno, mmnocode from telcodb.just_pay where 1=1 "
                + "  AND reference in (:reference)"
                + " order by mainoptions desc";

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

    public static List<Object[]> callFundgateTable(List<String> references) {

        List<Object[]> respo = new ArrayList<>();

        System.out.println("Call fundgate table started with references" + references);

//        String query = "select action, etzRef, lineType, amount, destination, terminal from fgdb.fundgate_request where 1=1 "
//                + "  AND reference in (:reference)"
//                + " order by lineType desc";
        String query = "select action, etzRef, lineType, amount, destination, terminal from fgdb.fundgate_request where 1=1 "
                + "  AND etzRef in (:reference)"
                + " order by lineType desc";

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

    public static List<Object[]> callMobileTable(List<String> references) {

        List<Object[]> respo = new ArrayList<>();

        System.out.println("Call Mobile table stated with references" + references);

//        String query = "select reference, mainoptions, amount, linetype, provider, destination, mmno from fgdb.fundgate_request where 1=1 "
//                + "  AND reference in (:reference)"
//                + " order by mainoptions desc";
        String query = "select unique_transid, merchant_id, trans_type, trans_amount, mobile_no, subscriber_id from vasdb2.t_paytrans where 1=1 "
                + "  AND unique_transid in (:reference)"
                + " order by trans_type desc";

        //vasdb2.t_paytrans
        System.out.println("Query >> " + query);
//        Session session = DbHibernate.getSession("VASREPROCESSMOBILE");
        Session session = DbHibernate.getSession("40.9MYSQLW");

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

    public static boolean insertReprocessedData(String originalReference, String updatedReference, String initiatedBy, String authorisedBy, String status, String account, String client, String vastype, String amount, String mobile, String transtype) {

        System.out.println("Start insert data into reprocess table");
        boolean result = false;
//        boolean rollback = false;

        List<Object[]> resp = new ArrayList<>();

        String Qry = "INSERT INTO vasdb2.vas_reprocess_log(original_reference, updated_reference, initiated_by, initiated_date, authorised_by, authorised_date, status, account, client, vastype, amount, mobile, transtype)Values(:original_reference, :updated_reference, :initiated_by, now() , :authorised_by, now() , :status, :account, :client, :vastype, :amount, :mobile, :transtype)";

        Transaction tx = null;

        Session session = DbHibernate.getSession("40.9MYSQLW");

        try {
            tx = session.beginTransaction();

            Query q = session.createNativeQuery(Qry);

            q.setParameter("original_reference", originalReference);
            q.setParameter("updated_reference", updatedReference);
            q.setParameter("initiated_by", initiatedBy);

            q.setParameter("authorised_by", authorisedBy);

            q.setParameter("status", status);
            q.setParameter("account", account);
            q.setParameter("client", client);
            q.setParameter("vastype", vastype);
            q.setParameter("amount", amount);
            q.setParameter("mobile", mobile);
            q.setParameter("transtype", transtype);

            int h = q.executeUpdate();

            System.out.println("H: " + h);

            tx.commit();
            result = true;

        } catch (Exception et) {
            log.error(ERROR, et);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return result;
    }

    public static boolean updateReprocessedData(String originalReference, String updatedReference, String status, String username, String responseCode, String responseMessage) {

        System.out.println("Start insert data into reprocess table");
        boolean result = false;
//        boolean rollback = true;

        List<Object[]> resp = new ArrayList<>();

        String Qry = "UPDATE vasdb2.vas_reprocess_log set status =:status, authorised_by =:authorised_by, authorised_date = now(), updated_reference =:updated_reference, response_code =:response_code, response_message =:response_message where original_reference = :original_reference";

        Transaction tx = null;

        Session session = DbHibernate.getSession("40.9MYSQLW");

        try {
            tx = session.beginTransaction();

            Query q = session.createNativeQuery(Qry);

            q.setParameter("original_reference", originalReference);

            q.setParameter("status", status);
            q.setParameter("authorised_by", username);
            q.setParameter("updated_reference", updatedReference);
            q.setParameter("response_code", responseCode);
            q.setParameter("response_message", responseMessage);

            int h = q.executeUpdate();

            System.out.println("H: " + h);

            tx.commit();
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

    public static String sendVasgateRequest(String destAccount, String reference, String vastype, String amount, String mobile, String transtype, String mmnocode, String client) {

        System.out.println("Start vasgate call");
        String res = "";
        String sysmac = "";
        //Double updatedAmount = Double.parseDouble(amount);

        VasgateRequest vasRequest = new VasgateRequest();

//        String sysmac = Encryption.getMD5(vastype + reference + "REP" + destAccount + "0");
        if (vastype.equalsIgnoreCase("dstv")) {

            //String sysmac = getMD5(String.valueOf(reference.trim()) + amount + account.trim() + client);
//               sysmac = Encryption.getMD5(String.valueOf(reference.trim())+ "REP" + amount + destAccount.trim() + client);
            sysmac = Encryption.getMD5(String.valueOf(reference.trim()) + "REP" + Double.parseDouble(amount) + destAccount.trim() + client);

        } else {
            sysmac = Encryption.getMD5(vastype + reference + "REP" + destAccount + "0");
        }

        vasRequest.setApiId("xportal");
        vasRequest.setApiSecret("EAE87AA45B443279747E158C6FA5FD2C9DDD49B8BCB2726FEE89F76D679B88BD5599E3E59643EA233454C66");
        vasRequest.setAccount(destAccount);
        vasRequest.setAction("process");
        vasRequest.setProduct(vastype);
        vasRequest.setMac(sysmac);
//        vasRequest.setClient("JustPay");
        vasRequest.setClient(client);
        vasRequest.setReference(reference + "REP");
        vasRequest.setAlias(vastype);
        vasRequest.setAmount(Double.parseDouble(amount));

        vasRequest.setMobile(mobile);
        vasRequest.setMode("1");
        vasRequest.setBank(mmnocode);
//        vasRequest.setName("JustPay");
        vasRequest.setName(client);

        if (transtype.equals("airtime")) {
            vasRequest.setMerchant("VTU");
        } else {
            vasRequest.setMerchant(vastype);
        }

        vasRequest.setType("0");
        vasRequest.setType2("0");

//        Gson gson = new Gson();
        String requestData = gson.toJson(vasRequest);
        log.info("Vas Request \n" + requestData);

        try {

            String vasurl = config.getProperty("vasreprocessurl");

            //System.out.println("Encryption Results"+Encryption.encryptWithRSA(requestData));
            //System.out.println("Encryption Results"+NewEncryption.encryptWithRSA(requestData));
            res = new DoHTTPRequest().post(vasurl, NewEncryption.encryptWithRSA(requestData));

            // res = "";
//            log.info("Vasgate url:: " + vas_url);
            log.info("Vasgate url:: " + vasurl);

            log.info("VASGATE PROCESS RESP " + res);

            JSONObject jsonObject = new JSONObject(res);
            String error = jsonObject.optString("error", "");
            String fault = jsonObject.optString("fault", "");

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        System.out.println("End vasgate call");

        return res;
    }

    public static String md5(String value) {
        String macValue = "";
        try {
            MessageDigest mdEnc = MessageDigest.getInstance("MD5");
            mdEnc.update(value.getBytes(), 0, value.length());
            macValue = (new BigInteger(1, mdEnc.digest())).toString(16);
            int len = 32 - macValue.length();
            for (int i = 0; i < len; i++) {
                macValue = "0" + macValue;
            }
        } catch (Exception e) {

            macValue = "";
        }
        return macValue;
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
