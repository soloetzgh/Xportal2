package com.etzgh.xportal.dao;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author sunkwa-arthur
 */
import com.etzgh.xportal.model.ApiPostData;
import com.etzgh.xportal.model.AuditTrail;
import com.etzgh.xportal.model.NODES;
import com.etzgh.xportal.model.TMC;
import com.etzgh.xportal.model.TmcUpdate;
import com.etzgh.xportal.utility.DbHibernate;
import com.etzgh.xportal.utility.GeneralUtility;
import com.etzgh.xportal.utility.HashNumber;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class TmcDao {

    private static final GeneralUtility utility = new GeneralUtility();
    private static final Gson gson = new Gson();
    private static final Logger log = Logger.getLogger(TmcDao.class.getName());
    private static final Map<String, String> banks;

    static {
        banks = convertListAfterJava8(new AppDao().getBankNodes("000"));
    }

    public static void main(String[] args) {
        String r = "{\"merchant\":\"\",\"product\":\"\",\"accountNumber\":\"\",\"service\":\"search\",\"action\":\"\",\"apiSecret\":\"\",\"apiCode\":\"\",\"userCode\":\"[2000000000000060]|905,[2500000000000050]|77\",\"admin\":\"\",\"type_desc\":\"\",\"role_id\":\"\",\"type_id\":\"[4],[5],[6],[7],[20],[24],[39],[40]\",\"searchKey\":\"02RS42603E77662\",\"userName\":\"\",\"ClientId\":\"\",\"user_id\":\"10500000000000754\",\"pageNumber\":1,\"rowsPerPage\":1,\"trans_code\":\"\",\"bank_code\":\"905\",\"subscriberId\":\"\",\"trans_status\":\"\",\"bank\":\"\",\"biller\":\"\",\"transId\":\"\",\"bankid\":\"\",\"card_num\":\"\",\"payType\":\"\",\"wcMerchant\":\"\",\"lineType\":\"\",\"trans_channel\":\"\",\"terminal_id\":\"\",\"card_account\":\"\",\"cop_issuercode\":\"\",\"cop_role_id\":\"\",\"cop_company_id\":\"\",\"mobile_no\":\"\",\"phone\":\"\",\"type\":\"\",\"amount\":\"\",\"amount2\":\"\",\"refundStatus\":\"\",\"partialReversal\":\"\",\"reversalReason\":\"\",\"ipAddress\":\"0:0:0:0:0:0:0:1\",\"userData\":\"\",\"appId\":[],\"company\":\"\",\"options\":{}}";
        new TmcDao().getTmcTransactions(r);
    }

    public static Map<String, String> convertListAfterJava8(List<NODES> list) {
        Map<String, String> map = list.stream().collect(Collectors.toMap(NODES::getId, node -> node.getName()));
        return map;
    }

    protected String mapResponse(String code, String resp) {

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

    public List<TMC> getTmcTransactions(String jsonString) {

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

        if (!type_id.isEmpty()) {
            if (type_id.contains("[0]")) {
                hashAcc = false;
//                System.out.println("Targer: " + tmctarget);
                if (!tmctarget.equalsIgnoreCase("all") && !tmctarget.trim().isEmpty()) {
                    tmcTargetList.add(tmctarget.trim());
                }
//                System.out.println("Sou: " + tmcSource);
                if (!tmcSource.equalsIgnoreCase("all") && !tmcSource.trim().isEmpty()) {
                    tmcSourceList.add(tmcSource.trim());
                }
            } else if (type_id.contains("[6]")) {
                hashAcc = false;

//                System.out.println("bank: " + bankCode);
                inconList = new AppDao().getInconId(bankCode);

//                System.out.println("INCON : " + inconList + " " + tmcSource + " - " + inconList.contains(tmcSource));
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
                + " response_code as response,  terminal_id, stan, card_acc_name, fee, trans_seq, src_node, target_node, response_time, settlefee from "
                + (!dbType.equals("old") ? " ecarddb.e_tmcrequest " : " ecarddb..e_tmcrequest ") + " where 1=1 "
                + (service.equalsIgnoreCase("search") ? " AND trans_data = :searchKey "
                : "  and trans_date BETWEEN :start_dt and :end_dt ")
                + (!pan.trim().isEmpty() ? " and pan like :pan " : "")
                + (!trans_data.trim().isEmpty() ? " and trans_data = :trans_data " : "")
                + (!channel.equals("ALL") ? "" + channel + "" : "")
                + (!transtype.equals("ALL") ? "" + transtype + "" : "")
                + (!tmcSourceList.isEmpty() ? " and src_node in (:tmcSource) " : "")
                + (!tmcSourceList.isEmpty() ? " and src_node in (:tmcSource) " : "")
                + (!tmcTargetList.isEmpty() ? " and target_node in (:tmctarget) " : "")
                + ((trans_status.equals("REVERSAL") ? " and MTI = '0420' " : "")
                + ((trans_status.equals("AMBIGUOUS") ? " and (response_code in ('-1', '91', null, '', '68'))"
                : ""))
                + ((trans_status.equals("FAIL") ? " and response_code <> '00'" : "")))
                + (trans_status.equals("SUCCESS") ? " and response_code = '00'" : "")
                + " order by trans_date desc";

//        System.out.println("Query >> " + query);
        Session session = null;
        Query q;
        List<Object[]> records = new ArrayList<>();
        try {
            session = DbHibernate.getSession(!dbType.equals("old") ? "40.15MYSQL" : "40.4SYBASE");

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
            log.error(e.getMessage(), e);
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
                tmc.setAmount(BigDecimal.valueOf(checkNullNumber(record[8])));

                tmc.setResponse_code(checkNull(record[10]));
                tmc.setResponse(mapResponse(checkNull(record[10]), checkNull(record[7])));

                tmc.setTerminal_id(checkNull(record[12]));
                tmc.setStan(checkNull(record[13]));
                tmc.setCard_acc_name(checkNull(record[14]));
                tmc.setFee(BigDecimal.valueOf(checkNullNumber(record[15])));
                tmc.setReference(checkNull(record[16]));

                tmc.setSrc_node(banks.getOrDefault(checkNull(record[17]), ""));
                tmc.setTarget_node(banks.getOrDefault(checkNull(record[18]), ""));
                tmc.setResponse_time(convertDate(checkNull(record[19])));
                tmc.setElevy(checkNullNumber(record[20]));
            } catch (Exception et) {
                log.error(et.getMessage(), et);
            }
            recordsList.add(tmc);
        }

        return recordsList;
    }

    public List<TmcUpdate> getTmcUpdateLog(String jsonString) {

        log.info("Tmc update log request received >> {0}" + jsonString);
        Gson j = new Gson();

        ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);
        String start_dt = apiData.getStartDate();
        String end_dt = apiData.getEndDate();
        String reference = apiData.getUniqueTransId();
        String user = apiData.getSubscriberId();

        String type_id = apiData.getType_id();
        String searchKey = apiData.getSearchKey();
        final String service = apiData.getService();

        List<TmcUpdate> records = new ArrayList<>();

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

        if (service.equalsIgnoreCase("updatesearch") && searchKey.trim().isEmpty()) {
            return records;
        }

        if (service.equalsIgnoreCase("updatesearch")) {
            reference = searchKey.trim();
        }

        if (!type_id.isEmpty()) {

            if (type_id.contains("[44]")) {
            } else {
                return records;
            }
        } else {
            return records;
        }
//        System.out.println("SEARCH: " + service);
        String query = "select reference, original_respcode, updated_respcode, initiated_by, initiated_date, authorised_by, authorised_date from ecarddb.tmc_updatelog where 1=1 "
                + (service.equalsIgnoreCase("updatesearch") ? "  AND reference = :reference "
                : " and initiated_date between :start_dt  and :end_dt ")
                + (!user.isEmpty() ? " and update_by like  :user " : "")
                + " order by initiated_date desc";

        Session session = DbHibernate.getSession("NY");

        try {

            Query q = session.createNativeQuery(query);

            if (!service.equalsIgnoreCase("updatesearch")) {
                q.setParameter("start_dt", (Object) start_dt)
                        .setParameter("end_dt", (Object) end_dt);
            } else {
                q.setParameter("reference", (Object) reference);
            }

            if (!user.isEmpty()) {
                q.setParameter("user", "%" + user + "%");
            }

            List<Object[]> resp = q.getResultList();

            for (Object[] record : resp) {
                TmcUpdate tmc = new TmcUpdate();
                try {
                    tmc.setReference(checkNull(record[0]));
                    tmc.setOriginal_respcode(checkNull(record[1]));
                    tmc.setUpdated_respcode(checkNull(record[2]));
                    tmc.setInitiated_by(checkNull(record[3]));
                    tmc.setInitiated_date(checkNull(record[4]));
                    tmc.setAuthorised_by(checkNull(record[5]));
                    tmc.setAuthorised_date(checkNull(record[6]));

                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }

                records.add(tmc);

            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
//        System.out.println("RE: " + records);

        return records;
    }

    public List<TMC> updateTmcStatus(String jsonString) {

        log.info("TMC Update status request received >> {0}" + jsonString);
        Gson j = new Gson();

        ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);
        String data = apiData.getUniqueTransId();

        String user_code = apiData.getUserCode();
        String type_id = apiData.getType_id();
        String username = apiData.getUserName();

        String userId = apiData.getUser_id();

        String ipAddress = apiData.getIpAddress();

        String original_respcode = apiData.getOriginal_respcode();
        String updated_respcode = apiData.getUpdated_respcode();
        String initiated_by = apiData.getInitiated_by();
        String initiated_date = apiData.getInitiated_date();
        String authorised_by = apiData.getAuthorised_by();
        String authorised_date = apiData.getAuthorised_date();

        List<TMC> records = new ArrayList<>();

        List<Object[]> resp = new ArrayList<>();
        List<String> references = new ArrayList<>();

        String userRoles = "";

        Boolean hashAcc = true;

        if (!type_id.isEmpty()) {

            if (type_id.contains("[0]")) {
                hashAcc = false;
                userRoles = utility.getRoleParameters("[2500000000000053]", user_code);
                if (userRoles.isEmpty()) {
                    return records;
                }
            } else {
                return records;
            }
        } else {
            return records;
        }

        String narration = "";
        String respcode = "00";

        List<TmcUpdate> recordsList = gson.fromJson(data, new TypeToken<List<TmcUpdate>>() {
        }.getType());

        final String _respcode = respcode;

        final String _narration = narration;

        recordsList.stream().forEach(f -> {
            if (f.getReference() != null && !f.getReference().trim().isEmpty()) {
                references.add(f.getReference().trim());
                System.out.println(f.getReference());

                updateTmc(f.getReference().trim(), f.getClientid().trim(), _narration, _respcode, username, userId,
                        ipAddress, original_respcode, updated_respcode, initiated_by, initiated_date, authorised_by,
                        authorised_date);

            }
        });

        if (!references.isEmpty()) {

            String query = "select mti, trans_data, acct_id1, acct_id2, pro_code as transtype, trans_date, pro_code, pan, amount, track2, response_code,"
                    + " response_code as response,  terminal_id, stan, card_acc_name, fee, reference, src_node, target_node, response_time from "
                    + "ecarddb.e_tmcrequest where 1=1 "
                    + " and response_code = '00'"
                    + "AND trans_data in (:reference) order by trans_date desc";

            Session session = DbHibernate.getSession("40.15MYSQL");

            try {

                Query q = session.createNativeQuery(query);

                q.setParameter("reference", references);

                resp = q.getResultList();

            } catch (Exception e) {
                log.error(e.getMessage(), e);
            } finally {
                if (session != null) {
                    session.close();
                }
            }
        }

        for (Object[] record : resp) {
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
                tmc.setAmount(BigDecimal.valueOf(checkNullNumber(record[8])));

                tmc.setResponse_code(checkNull(record[10]));
                tmc.setResponse(mapResponse(checkNull(record[10]), checkNull(record[7])));

                tmc.setTerminal_id(checkNull(record[12]));
                tmc.setStan(checkNull(record[13]));
                tmc.setCard_acc_name(checkNull(record[14]));
                tmc.setFee(BigDecimal.valueOf(checkNullNumber(record[15])));
                tmc.setReference(checkNull(record[16]));

                tmc.setSrc_node(banks.getOrDefault(checkNull(record[17]), ""));
                tmc.setTarget_node(banks.getOrDefault(checkNull(record[18]), ""));
                tmc.setResponse_time(convertDate(checkNull(record[19])));

            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            records.add(tmc);
        }

        return records;
    }

    public static boolean updateTmc(String reference, String clientId, String narration, String respcode,
            String username, String userId, String ipAddress, String original_respcode, String updated_respcode,
            String initiated_by, String initiated_date, String authorised_by, String authorised_date) {

        boolean result = false;
        boolean rollback = true;

        String logQry = "INSERT INTO ecarddb.tmc_updatelog(reference, original_respcode, updated_respcode, initiated_by, initiated_date, authorised_by, authorised_date) (select trans_data reference, response_code original_respcode, :respcode updated_respcode,:initiated_by initiated_by, now() initiated_date,:authorised_by authorised_by, now() authorised_date from ecarddb.e_tmcrequest where trans_data = :reference)";

        String tmcqry = "UPDATE ecarddb.e_tmcrequest set response_code =:respcode where trans_data = :reference ";

        Transaction tx = null;

        Session session = DbHibernate.getSession("NY");

        try {
            tx = session.beginTransaction();

            Query q = session.createNativeQuery(logQry);

            q.setParameter("respcode", respcode);
            q.setParameter("initiated_by", username);
            q.setParameter("authorised_by", username);

            q.setParameter("reference", reference);

            int h = q.executeUpdate();

            System.out.println("H: " + h);

            if (h > 0) {

                q = session.createNativeQuery(tmcqry);

                q.setParameter("respcode", "00");

                q.setParameter("reference", reference);

                int i = q.executeUpdate();
                rollback = false;
                System.out.println("I: " + i);

            }

            if (!rollback) {

                tx.commit();
                result = true;
                new Thread(() -> {
                    AuditTrail audit = new AuditTrail(userId,
                            "Tmc update for " + reference + " to clientId: " + clientId + " and responseCode: "
                            + respcode + " and narration: " + narration,
                            "update", null, "TMC Status Update", ipAddress);
                    new AuditDao().insertIntoAuditTrail(audit);
                }).start();

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

    protected double checkNullNumber(Object Data) {
        if (Data != null && !Data.equals("")) {
            return Double.valueOf(Data.toString());
        }
        return 0.0;
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
