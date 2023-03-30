/**
 *
 * @author sunkwa-arthur
 */
package com.etzgh.xportal.dao;

import com.etzgh.xportal.controller.PortalSettings;
import com.etzgh.xportal.model.ApiPostData;
import com.etzgh.xportal.model.CardMasterReport;
import com.etzgh.xportal.model.CardTransReport;
import com.etzgh.xportal.model.CardTransactionsReport;
import com.etzgh.xportal.model.NlaLiquidationReport;
import com.etzgh.xportal.model.UssdCardTransactions;
import com.etzgh.xportal.utility.CryptographerMin;
import com.etzgh.xportal.utility.DbHibernate;
import com.etzgh.xportal.utility.FormatPhoneNumber;
import com.etzgh.xportal.utility.GeneralUtility;
import static com.etzgh.xportal.utility.GeneralUtility.stripDecimalZeros;
import com.etzgh.xportal.utility.HashNumber;
import com.google.gson.Gson;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class CardTransDao {

    private static final PortalSettings portalSettings = new PortalSettings();
    private static final List<String> etzAppIds = Arrays.asList(StringUtils.substringBeforeLast(portalSettings.getSetting("etz_appids"), ":").split(","));
    private static final GeneralUtility utility = new GeneralUtility();
    private static final Map<String, String> bankDB = new HashMap<>();
    private static final Map<String, String> channelList = new HashMap<>();
    private static final Map<String, String> transTypeList = new HashMap<>();

    private static final Map<String, String> appIdList = new HashMap<>();
    private static final Logger log = Logger.getLogger(CardTransDao.class.getName());

    static {

        bankDB.put("004", "GCBDB");
        bankDB.put("021", "BOADB");
        bankDB.put("920", "ABIIDB");
        bankDB.put("905", "BESTPOINTDB");
        bankDB.put("005", "NIBDB");
        bankDB.put("700920", "40.17MYSQL");
        transTypeList.put("I", "REVERSAL");
        transTypeList.put("T", "TRANSFER");
        transTypeList.put("L", "CARD LOAD");
        transTypeList.put("P", "PAYMENT");

        configSetup();

    }

    public static void main(String[] args) {

        String j = "{\"startDate\":\"2011-02-14 00:00\",\"endDate\":\"2021-02-14 23:59\",\"merchant\":\"\",\"product\":\"\",\"accountNumber\":\"\",\"status\":\"ALL\",\"service\":\"cardTransRpt\",\"action\":\"\",\"apiSecret\":\"\",\"apiCode\":\"\",\"userCode\":\"[1],[17]|0060000244:0067510000010000,[2000000000000048]|000,[2000000000000049]|SCB:6PHY,[2000000000000053]|GOTV,[2000000000000054]|GOTV~TELESOL,[2000000000000060]|000,[2000000000000062]|ALL,[2000000000000063]|KNUST,[2000000000000064]|SurflineTopup,[2500000000000049]|2,[2500000000000050]|38,[29],[71]|0060010112,[91]|ALL\",\"admin\":\"\",\"type_desc\":\"\",\"role_id\":\"\",\"type_id\":\"[0],[1],[2],[3],[4],[5],[6],[7],[8],[9],[10],[11],[21],[25],[26],[27],[28],[30],[31],[33],[41],[43]\",\"userName\":\"\",\"ClientId\":\"\",\"user_id\":\"9000000000000490\",\"pageNumber\":1,\"rowsPerPage\":1,\"trans_code\":\"\",\"transType\":\"\",\"subscriberId\":\"548343909\",\"trans_status\":\"\",\"channel\":\"\",\"bank\":\"\",\"biller\":\"\",\"transId\":\"\",\"bankid\":\"\",\"from_source\":\"\",\"card_num\":\"\",\"optionType\":\"\",\"payType\":\"\",\"wcMerchant\":\"\",\"destination\":\"\",\"lineType\":\"\",\"trans_channel\":\"\",\"pan\":\"\",\"terminal_id\":\"\",\"card_account\":\"\",\"cop_issuercode\":\"\",\"cop_role_id\":\"\",\"cop_company_id\":\"\",\"mobile_no\":\"\",\"phone\":\"\",\"type\":\"\",\"amount\":\"\",\"amount2\":\"\",\"refundStatus\":\"\",\"partialReversal\":\"\",\"reversalReason\":\"\",\"ipAddress\":\"\",\"userData\":\"\",\"appId\":[],\"options\":{}}";
        new CardTransDao().getCardTransReport(j);
    }

    public CardTransactionsReport getCardTransReport(String jsonString) {

        log.info("card trans report request received >> " + jsonString);
        Gson j = new Gson();
        ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);

        String start_dt = apiData.getStartDate();
        String end_dt = apiData.getEndDate();
        String str_mobile = apiData.getSubscriberId();
        String bankCode = "000";
        String type_id = apiData.getType_id();

        String cardnum = apiData.getCard_num();
        List<String> cardList = new ArrayList<>();
//        List<String> tempList = new ArrayList<>();

//        String uniqueTransId = apiData.getUniqueTransId();
        String ucode = apiData.getUserCode();

        String searchKey = apiData.getSearchKey();

        String userRoles = "";

        BigDecimal debit_total = BigDecimal.valueOf(0.0);
        BigDecimal credit_total = BigDecimal.valueOf(0.0);
        BigDecimal debit = BigDecimal.valueOf(0.0);
        BigDecimal credit = BigDecimal.valueOf(0.0);
        String qry = "";
        List<CardTransReport> recordsList = new ArrayList<>();
        CardTransactionsReport frecordsList = new CardTransactionsReport();
        if (searchKey == null) {
            searchKey = "";
        }

        if (str_mobile == null) {
            str_mobile = "";
        } else {
            str_mobile = str_mobile.trim();
        }
        if (cardnum == null) {
            cardnum = "";
        } else {
            cardnum = cardnum.trim();
        }

        if (!type_id.isEmpty()) {
            if (type_id.contains("[0]")) {
                if (!cardnum.trim().isEmpty()) {
                    cardList.add(cardnum.trim());
                }
            } else if (type_id.contains("[2]")) {
                userRoles = utility.getRoleParameters("[17]", ucode);
//                System.out.println("usrRoles: " + userRoles);

                cardList = Pattern.compile("~").splitAsStream(userRoles).map(f -> f.split(":")[1]).collect(Collectors.toList());
                if (cardnum.equalsIgnoreCase("ALL") || cardnum.trim().isEmpty()) {

                } else if (cardList.contains(cardnum)) {
                    cardList = new ArrayList<>(cardList);
                    cardList.add(cardnum);
                }
                if (cardList.isEmpty()) {
                    return frecordsList;
                }
            } else if (type_id.contains("[28]")) {
                userRoles = utility.getRoleParameters("[2000000000000048]", ucode);
//                System.out.println("usrRoles: " + userRoles);

                cardList = Pattern.compile("~").splitAsStream(userRoles).map(f -> f.split(":")[1]).collect(Collectors.toList());
                if (cardnum.equalsIgnoreCase("ALL") || !cardnum.trim().isEmpty()) {

                } else if (cardList.contains(cardnum.trim())) {
                    cardList = new ArrayList<>(cardList);
                    cardList.add(cardnum.trim());
                }
                if (cardList.isEmpty()) {
                    return frecordsList;
                }
            } else if (type_id.contains("[6]")) {
                bankCode = utility.getRoleParameters("[2000000000000060]", ucode);

                if (!bankCode.trim().isEmpty() && !cardnum.isEmpty()) {
                    cardList.add(cardnum.trim());
                }

            } else if (type_id.contains("[68]")) {
//                userRoles = utility.getRoleParameters("[2500000000000055]", ucode);
                bankCode = "700920";

            } else {
                return frecordsList;
            }
        } else {
            return frecordsList;
        }

        Query q;

        if (!bankCode.equals("700920") && (cardList.isEmpty() && str_mobile.isEmpty())) {
            return frecordsList;
        }
        Map<String, String> resultMap = new HashMap<>();

        if (bankCode.equals("700920")) {
            qry = "select card_num, card_account from ecarddb.e_cardholder where 1=1 and card_num like '700920%' and card_account is not null "
                    + (!str_mobile.isEmpty() ? " and card_account = :account " : "");

            Session session = DbHibernate.getSession("40.17MYSQL");
            List<Object[]> res = new ArrayList<>();
            try {
                q = session.createNativeQuery(qry);
                if (!str_mobile.isEmpty()) {
                    q.setParameter("account", str_mobile);
                }
//                if (!str_mobile.trim().isEmpty()) {
//                    q.setParameter("str_mobile", "%" + str_mobile.trim());
//                }
//                if (!cardList.isEmpty()) {
//                    q.setParameter("account", "%" + cardList.get(0));
//                }

                res = q.getResultList();

                resultMap = res.parallelStream().collect(
                        Collectors.toMap(bu -> bu[0].toString(), bu -> bu[1].toString()));

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
//            System.out.println("DATA: " + resultMap);

            cardList = new ArrayList<>(resultMap.keySet());

//            System.out.println("cards: " + cardList);
        } else if (!bankCode.equals("000")) {
            String appId = appIdList.getOrDefault(bankCode, "");

            if (!appId.trim().isEmpty()) {
                List<String> l = Arrays.asList(appId.trim().split(","));

                if (!l.isEmpty()) {

                    qry = "select card_number from (select distinct a.id, a.appid, c.card_number, a.mobile_no, c.active status from mobiledb.m_mobile_subscriber a \n"
                            + "left join mobiledb.m_mobile_devices b on a.device_id = b.id left join mobiledb.m_mobile_subscriber_card c on a.id = c.subscriber_id \n"
                            + " where 1=1 "
                            + (!str_mobile.trim().isEmpty() ? " and a.mobile_no like :str_mobile " : "")
                            + (!cardList.isEmpty() ? " and b.device_name like :account " : "")
                            + " and a.appid in(:appIdList) "
                            + ") as t1";

                    Session session = DbHibernate.getSession("MOBILEDBMYSQL");
                    List<String> res = new ArrayList<>();

                    try {
                        q = session.createNativeQuery(qry).setParameter("appIdList", l);
                        if (!str_mobile.trim().isEmpty()) {
                            q.setParameter("str_mobile", "%" + str_mobile.trim());
                        }
                        if (!cardList.isEmpty()) {
                            q.setParameter("account", "%" + cardList.get(0));
                        }

                        res = q.getResultList();

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
//                    System.out.println("DATA: " + res);

                    cardList = res.stream().filter(f -> !(f == null || f.trim().isEmpty()))
                            .map(s -> CryptographerMin.cryptPan(s, 2))
                            .collect(Collectors.toList());

//                    System.out.println("CARDNUMBER(S) REGISTERED WITH PHONE >>" + cardList);
                } else {
                    return frecordsList;
                }
            } else {
                return frecordsList;
            }
        } else if (bankCode.equals("000") && cardList.isEmpty() && !str_mobile.trim().isEmpty()) {

            qry = "select card_number from (select distinct a.id, a.appid, c.card_number, a.mobile_no, c.active status from mobiledb.m_mobile_subscriber a \n"
                    + "left join mobiledb.m_mobile_devices b on a.device_id = b.id left join mobiledb.m_mobile_subscriber_card c on a.id = c.subscriber_id \n"
                    + " where 1=1 "
                    + " and a.mobile_no like :str_mobile and a.appid in(:appIdList) "
                    + ") as t1";

            Session session = DbHibernate.getSession("MOBILEDBMYSQL");
            List<String> res = new ArrayList<>();

            try {
                q = session.createNativeQuery(qry).setParameter("appIdList", etzAppIds).setParameter("str_mobile", "%" + str_mobile.trim());

                res = q.getResultList();
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
//            System.out.println("DATA: " + res);

            cardList = res.stream().filter(f -> !(f == null || f.trim().isEmpty()))
                    .map(s -> CryptographerMin.cryptPan(s, 2))
                    .collect(Collectors.toList());

//            System.out.println("CARDNUMBER(S) REGISTERED WITH PHONE >>" + cardList);
        }

        if (cardList.size() > 0) {
            if (bankCode.equals("700920")) {
                qry = "select trans_no, unique_transid, card_num, merchant_code, f_name, trans_descr, '' channel_name, trans_date, trans_amount, online_balance,  cr_balance, dr_balance, channelid, trans_code from "
                        + "(select trans_no, unique_transid, card_num, merchant_code, '' f_name, "
                        + " trans_descr, trans_date, cr_balance, dr_balance, "
                        + "(case when card_num in (:cardnum) then -1*trans_amount else trans_amount end) trans_amount, (select sum(online_balance) from ecarddb.e_cardholder "
                        + " where card_num in (:cardnum)) online_balance, channelid, trans_code from  ecarddb.e_transaction where (card_num in(:cardnum) or merchant_code in(:cardnum)) "
                        + (!searchKey.isEmpty() ? " and TRANS_DESCR like :searchkey " : "")
                        + "and trans_date between :start_dt and :end_dt) as t1 order by trans_date  desc";
            } else {
                qry = "select trans_no, unique_transid, card_num, merchant_code, f_name, trans_descr, '' channel_name, trans_date, trans_amount, online_balance,  cr_balance, dr_balance, channelid, trans_code from "
                        + "(select trans_no, unique_transid, card_num, merchant_code, '' f_name, "
                        + " trans_descr, trans_date, cr_balance, dr_balance, "
                        + "(case when card_num in (:cardnum) then -1*trans_amount else trans_amount end) trans_amount, (select sum(online_balance) from ecarddb.e_cardholder "
                        + " where card_num in(:cardnum)) online_balance, channelid, trans_code from  ecarddb.e_transaction where (card_num in(:cardnum) or merchant_code in(:cardnum)) "
                        + (!searchKey.isEmpty() ? " and TRANS_DESCR like :searchkey " : "")
                        + "and trans_date between  :start_dt and  :end_dt) as t1 order by trans_date  desc";
            }

//            System.out.println("CardTransReports QUERY >>" + qry);
            Session session = null;
            List<Object[]> records = new ArrayList<>();
            try {
                if (bankCode.equals("000")) {
                    session = DbHibernate.getSession("40.17MYSQL");
                } else {
                    session = DbHibernate.getSession(bankDB.getOrDefault(bankCode.trim(), null));
                }
                if (session != null) {
                    q = session.createNativeQuery(qry)
                            .setParameter("start_dt", start_dt)
                            .setParameter("end_dt", end_dt)
                            .setParameter("cardnum", cardList);
                    if (!searchKey.isEmpty()) {
                        q.setParameter("searchkey", "%" + searchKey + "%");
                    }

                    records = q.getResultList();
                }
            } catch (Exception u) {
                log.error(u.getMessage(), u);
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
                CardTransReport ctr = new CardTransReport();
                try {

                    ctr.setTrans_no(checkNull(record[0]));
                    ctr.setUnique_transid(checkNull(record[1]));
                    if (bankCode.equals("700920")) {
                        String src = checkNull(record[2]);
                        ctr.setSource(resultMap.getOrDefault(src, src));
                        String dest = checkNull(record[3]);
                        ctr.setDestination(resultMap.getOrDefault(dest, dest));
                    } else {
                        ctr.setCard_num(checkNumberNull(record[2]));
                        ctr.setMerchant_code(checkNumberNull(record[3]));
                    }
                    ctr.setF_name(checkNull(record[4]));
                    ctr.setTrans_descr(checkNull(record[5]));
                    ctr.setChannel_name(channelList.getOrDefault(checkNull(record[12]), ""));
                    ctr.setTrans_type(transTypeList.getOrDefault(checkNull(record[13]), ""));

                    ctr.setTrans_date(checkNull(record[7]));

                    Double d = Double.valueOf(checkNull(record[8]));
                    if (d > 0) {
                        ctr.setCR(stripDecimalZeros(BigDecimal.valueOf(d)));
                        ctr.setDR(stripDecimalZeros(debit));
                        credit_total = credit_total.add(ctr.getCR());

                    } else {
                        String str = "" + d;
                        ctr.setDR(stripDecimalZeros(BigDecimal.valueOf(Double.valueOf(str.substring(str.lastIndexOf("-") + 1)))));
                        ctr.setCR(stripDecimalZeros(credit));
                        debit_total = debit_total.add(ctr.getDR());
                    }
                    ctr.setOnline_balance(stripDecimalZeros(BigDecimal.valueOf(Double.valueOf(checkNull(record[9])))));
                    if (ctr.getCR().compareTo(credit) > 0) {
                        ctr.setBalance(stripDecimalZeros(BigDecimal.valueOf(Double.valueOf(checkNull(record[10])))));
                    } else if (ctr.getDR().compareTo(debit) > 0) {
                        ctr.setBalance(stripDecimalZeros(BigDecimal.valueOf(Double.valueOf(checkNull(record[11])))));
                    }

                } catch (Exception er) {
                    log.error(er.getMessage(), er);
                }
                recordsList.add(ctr);
            }
            frecordsList.setCardTransactionsReport(recordsList);
            frecordsList.setTotalCreditAmount(credit_total);
            frecordsList.setTotalDebitAmount(debit_total);

            System.out.println("CREDIT: " + credit_total);
            System.out.println("DEBIT: " + debit_total);

            if (!bankCode.equals("700920")) {
                try {

                    session = DbHibernate.getSession("40.17MYSQL");
                    records = new ArrayList<>();
                    if (session != null) {
                        q = session.createNativeQuery("select concat(firstname,' ',lastname) name, card_num from ecarddb.e_cardholder where card_num in (:card_num) limit 1")
                                .setParameter("card_num", cardList);

                        records = q.getResultList();
                    }
                } catch (Exception u) {
                    log.error(u.getMessage(), u);
                } finally {
                    if (session != null) {
                        try {
                            session.close();
                        } catch (Exception e) {
                            log.error(e.getMessage(), e);
                        }
                    }
                    if (!records.isEmpty()) {
                        for (Object[] record : records) {
                            frecordsList.setCardName(checkNull(record[0]));
                            frecordsList.setCardNumber(checkNull(record[1]));
                        }
                    }
                }
            } else {
                frecordsList.setCardName("PBC ACCOUNT");
                frecordsList.setCardNumber("0000000000000000");
            }

        }
        return frecordsList;
    }

    public List<UssdCardTransactions> getUssdCardTransReport(final String jsonString) {
        System.out.println("ussd card trx request received >> " + jsonString);
        final Gson j = new Gson();
        final ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);

        final String start_dt = apiData.getStartDate();
        final String end_dt = apiData.getEndDate();
        String trans_status = apiData.getStatus();
        final String ucode = apiData.getUserCode();
        final String cardScheme = apiData.getCardSchemeNumbers();
        final String type_id = apiData.getType_id();
        String service = apiData.getService();
        String searchKey = apiData.getSearchKey();
        String uniqueTransId = apiData.getUniqueTransId();
        String mobile = apiData.getMobile_no();
        String transType = apiData.getTransType();

        String qry = "";
        List<UssdCardTransactions> recordsList = new ArrayList<>();

        if (type_id.contains("[0]")) {
        } else {
            return recordsList;
        }
        if (service == null) {
            service = "";
        }
        if (trans_status == null) {
            trans_status = "";
        }
        if (uniqueTransId == null) {
            uniqueTransId = "";
        } else {
            uniqueTransId = uniqueTransId.trim();
        }
        if (transType == null) {
            transType = "ALL";
        }
        if (searchKey == null) {
            searchKey = "";
        } else {
            searchKey = searchKey.trim();
        }
        if (mobile == null) {
            mobile = "";
        } else {
            mobile = new FormatPhoneNumber().formatNumber(mobile.trim());
        }

        if (service.equalsIgnoreCase("search") && searchKey.isEmpty()) {
            return recordsList;
        }

        qry = "SELECT reference, msisdn, mainoptions trans_type,destacct destination, (case when left(vastype,3) ='999' then 'GIP' when vastype ='686' then 'MTN MOMO' when vastype ='844' then 'TIGO CASH' when vastype ='863' then 'VODA CASH' else vastype end)  vas_type,amount, status_code, status_msg, value_code, value_msg, sms_sent, created_date FROM ussd_db.justpay_card "
                + " WHERE 1=1 "
                + (!service.equalsIgnoreCase("search") ? " and created_date between :start_dt and :end_dt " : " and reference = :searchKey ")
                + (trans_status.equalsIgnoreCase("ALL") || trans_status.trim().isEmpty() ? "" : (trans_status.equalsIgnoreCase("SUCCESSFUL") ? " and (status_code = 1 and value_code in ('0','00','000') )" : " and (status_code = '0' || value_code not in ('0','00') || value_code is null)"))
                + (!uniqueTransId.isEmpty() ? " and reference = :reference " : "")
                + (!mobile.isEmpty() ? " and msisdn = :mobile " : "")
                + (!transType.isEmpty() && !transType.equalsIgnoreCase("ALL") ? " and mainoptions = :transType " : "")
                + " order by created_date desc";

//        System.out.println("ussd QUERY >>" + qry);
        Session session = DbHibernate.getSession("USSDDBMYSQL");
        try {

            Query q = session.createNativeQuery(qry, UssdCardTransactions.class);

            if (!service.equalsIgnoreCase("search")) {
                q.setParameter("start_dt", (Object) start_dt)
                        .setParameter("end_dt", (Object) end_dt);
            } else {
                q.setParameter("searchKey", (Object) searchKey);
            }
            if (!uniqueTransId.isEmpty()) {
                q.setParameter("reference", (Object) uniqueTransId);
            }
            if (!mobile.isEmpty()) {
                q.setParameter("mobile", (Object) mobile);
            }
            if (!transType.trim().isEmpty() && !transType.equalsIgnoreCase("ALL")) {
                q.setParameter("transType", (Object) transType);
            }
            recordsList = q.getResultList();
        } catch (Exception er) {
            log.error(er.getMessage(), er);

        } finally {
            if (session != null) {
                session.close();
            }
        }

        return recordsList;
    }

    public List<CardMasterReport> getCardMasterTransaction(String jsonString) {

        log.info("Cardmaster trans request received >> {0}" + jsonString);
        Gson j = new Gson();

        ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);
        String start_dt = apiData.getStartDate();
        // System.out.println("Start Date:"+start_dt);
        String end_dt = apiData.getEndDate();
        // System.out.println("End Date:"+end_dt);
        //String reference = apiData.getUniqueTransId();
        String user = apiData.getSubscriberId();
        String user_code = apiData.getUserCode();
        String type_id = apiData.getType_id();
        String searchKey = apiData.getSearchKey();
        final String service = apiData.getService();

        String reference = apiData.getReference();
        String extReference = apiData.getExtReference();
        String destacct = apiData.getDestacct();
        String transNumber = apiData.getTransNumber();

        List<CardMasterReport> records = new ArrayList<>();

        // log.info( "USER CODE >>{0}", user_code);
        String userRoles = "";

        if (reference == null) {
            reference = "";
        } else {
            reference = reference.trim();
        }

        if (extReference == null) {
            extReference = "";
        } else {
            extReference = extReference.trim();
        }

        if (destacct == null) {
            destacct = "";
        } else {
            destacct = destacct.trim();
        }

        if (transNumber == null) {
            transNumber = "";
        } else {
            transNumber = transNumber.trim();
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
        if (service.equalsIgnoreCase("updatesearch")) {
            reference = searchKey.trim();
        }

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

        String query = "select reference, extReference, amount, vastype, destacct, description, transType, transNumber, debitStatus, debitMessage, valueStatus, valueMessage, transactionStatus, isReversed, createdAt, updatedAt from ussd_db.ghlink_transaction where 1=1 "
                // + " and created between :start_dt and :end_dt "
                // + (service.equalsIgnoreCase("nlaliquidationreport") ? " AND reference =
                // :reference " : " and created between :start_dt and :end_dt ")
                // + (!user.isEmpty() ? " and update_by like :user " : "")
                // + (!ticketNumber.isEmpty() ? " and ticket_number = :ticket_number " : "")
                // + (!phoneNumber.isEmpty() ? " and phone_number = :phone_number " : "")
                // + (!nlaReference.isEmpty() ? " and reference = :reference " : "")
                + (!reference.isEmpty() ? " and reference = :reference " : "")
                + (!extReference.isEmpty() ? " and extReference = :extReference " : "")
                + (!destacct.isEmpty() ? " and destacct = :destacct " : "")
                + (!transNumber.isEmpty() ? " and transNumber = :transNumber " : "")
                + " and createdAt between :start_dt and :end_dt "
                + " order by createdAt desc";

        //Session session = DbHibernate.getSession("VASREPROCESS");
        Session session = DbHibernate.getSession("USSDDBMYSQL");

        // System.out.println("STARTING ");
        try {

            Query q = session.createNativeQuery(query);

//             q.setParameter("start_dt", (Object) startDate);
//             q.setParameter("end_dt", (Object) endDate);
            q.setParameter("start_dt", (Object) start_dt);
            q.setParameter("end_dt", (Object) end_dt);
            // q.setParameter("ticket_number", (Object) ticketNumber);
            // q.setParameter("phoneNumber", (Object) phoneNumber);
            // q.setParameter("nlaReference", (Object) nlaReference);
            // q.setParameter("etzReference", (Object) etzReference);
            // if (!ticketNumber.equals("")) {
            // q.setParameter("ticket_number", (Object) ticketNumber);
            // }
            //
            // if (!phoneNumber.equals("")) {
            // q.setParameter("phone_number", (Object) phoneNumber);
            // }
            //
            // if (!nlaReference.equals("")) {
            // q.setParameter("reference", (Object) nlaReference);
            // }
            if (!reference.equals("")) {
                q.setParameter("reference", (Object) reference);
            }

            if (!extReference.equals("")) {
                q.setParameter("extReference", (Object) extReference);
            }

            if (!destacct.equals("")) {
                q.setParameter("destacct", (Object) destacct);
            }

            if (!transNumber.equals("")) {
                q.setParameter("transNumber", (Object) transNumber);
            }

            // if (!service.equalsIgnoreCase("nlaliquidationreport")) {
            // q.setParameter("start_dt", (Object) start_dt)
            // .setParameter("end_dt", (Object) end_dt);
            // } else {
            // q.setParameter("reference", (Object) reference);
            // }
//            if (!user.isEmpty()) {
//                q.setParameter("user", "%" + user + "%");
//            }
            List<Object[]> resp = q.getResultList();
            // System.out.println("DONE");

            for (Object[] record : resp) {
                CardMasterReport cmr = new CardMasterReport();
                // String bankCode = "";
                // String network = "";

                try {

                    cmr.setReference(checkNull(record[0]).trim());
                    cmr.setExtReference(checkNull(record[1]));
                    cmr.setAmount(checkNull(record[2]));
                    cmr.setVastype(checkNull(record[3]));
                    cmr.setDestacct(checkNull(record[4]));
                    cmr.setDescription(checkNull(record[5]));
                    cmr.setTransType(checkNull(record[6]));
                    cmr.setTransNumber(checkNull(record[7]));
                    cmr.setDebitStatus(checkNull(record[8]));
                    cmr.setDebitMessage(checkNull(record[9]));
                    cmr.setValueStatus(checkNull(record[10]));
                    cmr.setValueMessage(checkNull(record[11]));
                    cmr.setTransactionStatus(checkNull(record[12]));
                    cmr.setIsReversed(checkNull(record[13]));
                    cmr.setCreatedAt(checkNull(record[14]));
                    cmr.setUpdatedAt(checkNull(record[15]));

//                    cmr.setNewCustomerId(newCustomerId);
//                    cmr.setResponse_date(checkNull(record[3]));
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }

                records.add(cmr);

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

    public List<CardMasterReport> getCardMasterOnboarding(String jsonString) {

        log.info("Cardmaster onboarding request received >> {0}" + jsonString);
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

        String email = apiData.getEmail();
        String phone = apiData.getPhone();
        String idNumber = apiData.getIdNumber();
        String can = apiData.getCan();

        List<CardMasterReport> records = new ArrayList<>();

        // log.info( "USER CODE >>{0}", user_code);
        String userRoles = "";

//        if (reference == null) {
//            reference = "";
//        } else {
//            reference = reference.trim();
//        }
        if (email == null) {
            email = "";
        } else {
            email = email.trim();
        }

        if (phone == null) {
            phone = "";
        } else {
            phone = phone.trim();
        }

        if (idNumber == null) {
            idNumber = "";
        } else {
            idNumber = idNumber.trim();
        }

        if (can == null) {
            can = "";
        } else {
            can = can.trim();
        }

        if (searchKey == null) {
            searchKey = "";
        }
        if (user == null) {
            user = "";
        } else {
            user = user.trim();
        }

        // if (service.equalsIgnoreCase("updatesearch") && searchKey.trim().isEmpty()) {
        // return records;
        // }
        if (service.equalsIgnoreCase("updatesearch")) {
            reference = searchKey.trim();
        }

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

        String query = "select email, firstName, lastName, phone, idNumber, can, onboardStatus, response, created_at, updated_at from ussd_db.ghlink_onboarding where 1=1 "
                // + " and created between :start_dt and :end_dt "
                // + (service.equalsIgnoreCase("nlaliquidationreport") ? " AND reference =
                // :reference " : " and created between :start_dt and :end_dt ")
                // + (!user.isEmpty() ? " and update_by like :user " : "")
                // + (!ticketNumber.isEmpty() ? " and ticket_number = :ticket_number " : "")
                // + (!phoneNumber.isEmpty() ? " and phone_number = :phone_number " : "")
                // + (!nlaReference.isEmpty() ? " and reference = :reference " : "")
                + (!email.isEmpty() ? " and email = :email " : "")
                + (!phone.isEmpty() ? " and phone = :phone " : "")
                + (!idNumber.isEmpty() ? " and idNumber = :idNumber " : "")
                + (!can.isEmpty() ? " and can = :can " : "")
                + " and created_at between :start_dt and :end_dt "
                + " order by created_at desc";

        //Session session = DbHibernate.getSession("VASREPROCESS");
         Session session = DbHibernate.getSession("USSDDBMYSQL");

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
            //
            // if (!phoneNumber.equals("")) {
            // q.setParameter("phone_number", (Object) phoneNumber);
            // }
            //
            // if (!nlaReference.equals("")) {
            // q.setParameter("reference", (Object) nlaReference);
            // }
            if (!email.equals("")) {
                q.setParameter("email", (Object) email);
            }

            if (!phone.equals("")) {
                q.setParameter("phone", (Object) phone);
            }

            if (!idNumber.equals("")) {
                q.setParameter("idNumber", (Object) idNumber);
            }

            if (!can.equals("")) {
                q.setParameter("can", (Object) can);
            }

            // if (!service.equalsIgnoreCase("nlaliquidationreport")) {
            // q.setParameter("start_dt", (Object) start_dt)
            // .setParameter("end_dt", (Object) end_dt);
            // } else {
            // q.setParameter("reference", (Object) reference);
            // }
//            if (!user.isEmpty()) {
//                q.setParameter("user", "%" + user + "%");
//            }
            List<Object[]> resp = q.getResultList();
            // System.out.println("DONE");

            for (Object[] record : resp) {
                CardMasterReport cmr = new CardMasterReport();
                // String bankCode = "";
                // String network = "";

                try {

                    cmr.setEmail(checkNull(record[0]).trim());
                    cmr.setFirstName(checkNull(record[1]));
                    cmr.setLastName(checkNull(record[2]));
                    cmr.setPhone(checkNull(record[3]));
                    cmr.setIdNumber(checkNull(record[4]));
                    cmr.setCan(checkNull(record[5]));
                    cmr.setOnboardStatus(checkNull(record[6]));
                    cmr.setResponse(checkNull(record[7]));
                    cmr.setCreatedAt(checkNull(record[8]));
                    cmr.setUpdatedAt(checkNull(record[9]));

                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }

                records.add(cmr);

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

    protected static String checkNull(Object Data) {
        if (Data != null && !Data.toString().trim().isEmpty()) {
            return Data.toString().trim();
        }
        return "NULL";
    }

    protected String checkNumberNull(Object Data) {
        if (Data != null && !Data.equals("")) {
            if (Data.toString().startsWith("686") || Data.toString().startsWith("863")
                    || Data.toString().startsWith("844") || Data.toString().startsWith("285")) {
                String num = Data.toString().substring(3, Data.toString().length());
                if (num.length() >= 12) {
                    return num.substring(0, 4) + "XXXX" + num.substring(8);
                }
                return num;
            } else {
                return hashAccountNumber(Data.toString());
            }
        }
        return "NULL";
    }

    public String currencyFormat(BigDecimal n) {

        return NumberFormat.getNumberInstance().format(n);
    }

    protected String hashAccountNumber(String accNo) {
        if (accNo.length() == 16) {
            int start = accNo.length() / 2;
            HashNumber hn = new HashNumber();

            accNo = hn.hashAccountValue(accNo, start / 2, start / 2);
        }
        return accNo;
    }

    protected String hashPhoneNumber(String accNo) {
        if (accNo.length() == 12) {
            int start = accNo.length() / 2;
            HashNumber hn = new HashNumber();

            accNo = hn.hashAccountValue(accNo, start / 2, start / 2);
        }
        return accNo;
    }

    protected Date convertDate(String Data) {
        try {
            if (!Data.equals("NULL")) {

                Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(Data);

                return date;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public int getDateDiff(String start_date, String end_date) {

        int range = -1;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH);
            Date firstDate = sdf.parse(start_date);
            Date secondDate = sdf.parse(end_date);

            long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
            long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            range = (int) diff;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return range;
    }

    public static void configSetup() {
        Session session = null;
        List<Object[]> records = new ArrayList<>();
        try {
            session = DbHibernate.getSession("40.17MYSQL");
            String qry = "select channel_id, channel_name from ecarddb.e_channel";
            records = session.createNativeQuery(qry).getResultList();
        } catch (Exception u) {
            log.error(u.getMessage(), u);
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
        }

        if (!records.isEmpty()) {
            for (Object[] record : records) {
                channelList.put(checkNull(record[0]), checkNull(record[1]));
            }
        }
        System.out.println("Channel:: " + channelList);

        records = new ArrayList<>();

        try {
            session = DbHibernate.getSession("MOBILEDBMYSQL");
            String qry = "select bank_code, group_concat(id) id from mobiledb.m_mobileapp_properties group by bank_code";
            records = session.createNativeQuery(qry).getResultList();
        } catch (Exception u) {
            log.error(u.getMessage(), u);
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
        }

        if (!records.isEmpty()) {
            for (Object[] record : records) {
                appIdList.put(checkNull(record[0]), checkNull(record[1]));
            }
        }

        System.out.println("APP1:: " + appIdList);
    }

}
