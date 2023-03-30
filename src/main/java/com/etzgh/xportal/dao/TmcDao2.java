package com.etzgh.xportal.dao;

/**
 *
 * @author sunkwa-arthur
 */
import com.etzgh.xportal.model.ApiPostData;
import com.etzgh.xportal.model.NODES;
import com.etzgh.xportal.model.TMC;
import com.etzgh.xportal.utility.DbHibernate;
import com.etzgh.xportal.utility.HashNumber;
import com.google.gson.Gson;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.apache.log4j.Logger;

public class TmcDao2 {

    // private static final GeneralUtility utility = new GeneralUtility();
    private static final Map<String, String> banks;

    private static final Logger log = Logger.getLogger(TmcDao2.class.getName());

    public static void main(String[] args) {
        String r = "{\"merchant\":\"\",\"product\":\"\",\"accountNumber\":\"\",\"service\":\"search\",\"action\":\"\",\"apiSecret\":\"\",\"apiCode\":\"\",\"userCode\":\"[2000000000000060]|905,[2500000000000050]|77\",\"admin\":\"\",\"type_desc\":\"\",\"role_id\":\"\",\"type_id\":\"[4],[5],[6],[7],[20],[24],[39],[40]\",\"searchKey\":\"02RS42603E77662\",\"userName\":\"\",\"ClientId\":\"\",\"user_id\":\"10500000000000754\",\"pageNumber\":1,\"rowsPerPage\":1,\"trans_code\":\"\",\"bank_code\":\"905\",\"subscriberId\":\"\",\"trans_status\":\"\",\"bank\":\"\",\"biller\":\"\",\"transId\":\"\",\"bankid\":\"\",\"card_num\":\"\",\"payType\":\"\",\"wcMerchant\":\"\",\"lineType\":\"\",\"trans_channel\":\"\",\"terminal_id\":\"\",\"card_account\":\"\",\"cop_issuercode\":\"\",\"cop_role_id\":\"\",\"cop_company_id\":\"\",\"mobile_no\":\"\",\"phone\":\"\",\"type\":\"\",\"amount\":\"\",\"amount2\":\"\",\"refundStatus\":\"\",\"partialReversal\":\"\",\"reversalReason\":\"\",\"ipAddress\":\"0:0:0:0:0:0:0:1\",\"userData\":\"\",\"appId\":[],\"company\":\"\",\"options\":{}}";
        new TmcDao2().getTmcTransactions(r);
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

                inconList = new AppDao().getInconId(bankCode);

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

//        System.out.println("SOURCE: " + tmcSourceList);
//        System.out.println("TARGET: " + tmcTargetList);
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
                + " response_code as response,  terminal_id, stan, card_acc_name, fee, trans_seq, src_node, target_node, response_time,settlefee from "
                + (!dbType.equals("old") ? " ecarddb2.e_tmcrequest " : " ecarddb..e_tmcrequest ") + " where 1=1 "
                + (service.equalsIgnoreCase("search") ? " AND trans_data = :searchKey "
                : "  and trans_date BETWEEN :start_dt and :end_dt ")
                + (!pan.trim().isEmpty() ? " and pan like :pan " : "")
                + (!trans_data.trim().isEmpty() ? " and trans_data = :trans_data " : "")
                + (!channel.equals("ALL") ? "" + channel + "" : "")
                + (!transtype.equals("ALL") ? "" + transtype + "" : "")
                + (!tmcSourceList.isEmpty() ? " and src_node in (:tmcSource) " : "")
                + (!tmcSourceList.isEmpty() ? " and src_node in (:tmcSource) " : "")
                + (!tmcTargetList.isEmpty() ? " and target_node in (:tmctarget)  " : "")
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
//        System.out.println("SIZE: " + recordsList.size());

        return recordsList;
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
