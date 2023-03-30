package com.etzgh.xportal.dao;

import com.etzgh.xportal.controller.PortalSettings;
import com.etzgh.xportal.model.ApiPostData;
import com.etzgh.xportal.model.AuditTrail;
import com.etzgh.xportal.model.ECARDHOLDER;
import com.etzgh.xportal.model.E_CMS;
import com.etzgh.xportal.model.NODES;
import com.etzgh.xportal.utility.Alert;
import com.etzgh.xportal.utility.Config;
import com.etzgh.xportal.utility.CryptographerMin;
import com.etzgh.xportal.utility.DbHibernate;
import com.etzgh.xportal.utility.GeneralUtility;
import static com.etzgh.xportal.utility.GeneralUtility.stripDecimalZeros;
import com.etzgh.xportal.utility.HashNumber;
import com.etzgh.xportal.utility.PinGenerator;
import com.google.gson.Gson;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.persistence.NoResultException;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.json.JSONObject;

public class CmsDao {

    Query q;
    private static final GeneralUtility utility = new GeneralUtility();
    private static final PortalSettings portalSettings = new PortalSettings();
    private static final List<String> etzAppIds;
    private static final Map<String, String> bankDB = new HashMap<>();
    private static final Map<String, String> channelList = new HashMap<>();
    private static final Map<String, String> appIdList = new HashMap<>();
    private static final Config config = new Config();
    private static final List<String> ussdGateBanks;
    private static final Logger log = Logger.getLogger(CmsDao.class.getName());

    static {
        etzAppIds = Arrays.asList(StringUtils.substringBeforeLast(portalSettings.getSetting("etz_appids"), ":").split(","));

        bankDB.put("004", "GCB");
        bankDB.put("021", "BOA");
        bankDB.put("920", "ABII");
        bankDB.put("905", "BESTPOINT");
        bankDB.put("005", "NIB");
        bankDB.put("700920", "40.17MYSQL");
        configSetup();
        ussdGateBanks = Arrays.asList(config.getProperty("USSDGATE_APPS"));

    }

    public static void main(String[] args) {

        String r = "{\"merchant\":\"\",\"product\":\"\",\"accountNumber\":\"\",\"service\":\"\",\"action\":\"addaccount\",\"apiSecret\":\"\",\"apiCode\":\"\",\"userCode\":\"[1],[17]|0060000290:0067620000000010,[2000000000000048]|00000000000000000002:0069900596470004,[2000000000000049],[2000000000000053]|ALL,[2000000000000054],[2000000000000060]|000,[2000000000000062]|ALL,[2000000000000063]|KNUST,[2000000000000064]|ALL,[2500000000000049]|2,[2500000000000050]|ALL,[2500000000000053]|3,[29],[50]|2,[71]|0230010002,[91]|ALL\",\"admin\":\"\",\"cardSchemeNumbers\":\"\",\"type_desc\":\"\",\"role_id\":\"\",\"type_id\":\"[0],[1],[2],[3],[4],[5],[6],[7],[8],[9],[10],[11],[20],[21],[24],[25],[26],[27],[28],[30],[31],[33],[35],[37],[39],[40],[41],[43],[44]\",\"searchKey\":\"\",\"userName\":\"Eugene.Arthur\",\"ClientId\":\"\",\"user_id\":\"9000000000000490\",\"pageNumber\":1,\"rowsPerPage\":1,\"uniqueTransId\":\"\",\"trans_code\":\"\",\"transType\":\"\",\"bank_code\":\"000\",\"subscriberId\":\"\",\"trans_status\":\"\",\"channel\":\"\",\"bank\":\"\",\"biller\":\"\",\"transId\":\"\",\"bankid\":\"\",\"from_source\":\"\",\"card_num\":\"0068860077370006\",\"optionType\":\"rerer\",\"payType\":\"\",\"wcMerchant\":\"\",\"destination\":\"\",\"lineType\":\"\",\"trans_channel\":\"\",\"pan\":\"\",\"terminal_id\":\"\",\"card_account\":\"\",\"cop_issuercode\":\"\",\"cop_role_id\":\"\",\"cop_company_id\":\"\",\"mobile_no\":\"\",\"phone\":\"\",\"type\":\"\",\"amount\":\"\",\"amount2\":\"\",\"refundStatus\":\"\",\"partialReversal\":\"\",\"reversalReason\":\"\",\"ipAddress\":\"0:0:0:0:0:0:0:1\",\"userData\":\"\",\"appId\":[\"2\"],\"company\":\"\",\"options\":{\"branchcode\":\"001\",\"firstname\":\"EUGENE KOFI\",\"lastname\":\"SUNKWA-ARTHUR\"}}";

        log.info("RES:: " + new CmsDao().modifyCardAccount(r));
        System.exit(0);
    }

    public List<ECARDHOLDER> getCardDetails(String jsonString) {

        log.info("card details view request received >> " + jsonString);
        Gson j = new Gson();
        ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);
        List<ECARDHOLDER> recordsList = new ArrayList<>();

        String card_num = apiData.getCard_num();
        String source_frm = apiData.getFrom_source();
        String ucode = apiData.getUserCode();
        String type_id = apiData.getType_id();
        String query = "";
        String bankCode = apiData.getBank_code();
        log.info("Card Num -> " + card_num);
        log.info("type -> " + type_id);
        String userRoles = "";

        List<String> cardList = new ArrayList<>();

        if (source_frm == null) {
            source_frm = "";
        }
        if (card_num == null) {
            card_num = "";
        }

        if (!type_id.isEmpty()) {
            if (type_id.contains("[0]") || type_id.contains("[35]")) {
                if (!card_num.trim().isEmpty()) {
                    cardList.add(card_num.trim());
                }
            } else if (type_id.contains("[6]")) {
                bankCode = utility.getRoleParameters("[2000000000000060]", ucode);
                log.info("bank: " + bankCode);

                if (!bankCode.trim().isEmpty() && !card_num.trim().isEmpty()) {
                    cardList.add(card_num.trim());
                }
            } else if (type_id.contains("[68]")) {
                userRoles = utility.getRoleParameters("[2500000000000055]", ucode);
                bankCode = "700920";
                log.info("bank: " + bankCode);

                if (!source_frm.trim().isEmpty()) {
                    cardList.add(source_frm.trim());
                }
            } else {

                return recordsList;
            }
        } else {

            return recordsList;
        }
        if (!bankCode.equals("700920") && cardList.isEmpty() && source_frm.trim().isEmpty()) {

            return recordsList;
        }
        Map<String, String> accounts = new HashMap<>();
        if (bankCode.equals("700920")) {

            Session session = DbHibernate.getSession("40.9MYSQL");
            List<NODES> n = new ArrayList<>();
            try {
                String qry = "select idNo id, names name from telcodb.cocoa_profile where 1=1 and coy ='PBC' "
                        + (!cardList.isEmpty() ? " and idNo = :phone " : "");
                Query qy = session.createNativeQuery(qry, NODES.class);
                if (!cardList.isEmpty()) {
                    qy.setParameter("phone", cardList);
                }
                n = qy.getResultList();

                accounts = n.stream().collect(Collectors.toMap(s -> s.getId(), s -> s.getName()));

                cardList = new ArrayList<>(accounts.keySet());
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
            if (!n.isEmpty()) {

                List<String> numbers = n.parallelStream().map(f -> f.getId()).collect(Collectors.toList());
                Session session2 = DbHibernate.getSession("40.17MYSQL");
                try {
                    String qry = "select card_num id, '' name from ecarddb.e_cardholder where card_account in(:numbers) and card_num like '700920%'";
                    Query qe = session2.createNativeQuery(qry, NODES.class).setParameter("numbers", numbers);
                    n = qe.getResultList();

                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                } finally {
                    if (session2 != null) {
                        try {
                            session2.close();
                        } catch (Exception e) {
                            log.error(e.getMessage(), e);
                        }
                    }

                }
                cardList = n.parallelStream().map(f -> f.getId()).collect(Collectors.toList());
            }

        } else if (!bankCode.equals("000")) {
            log.info("OUT: " + bankCode);
            String appId = appIdList.getOrDefault(bankCode, "");

            if (!appId.trim().isEmpty()) {
                List<String> l = Arrays.asList(appId.trim().split(","));

                if (!l.isEmpty()) {
                    String qry = "select concat_ws('|',card_number, status) from (select distinct a.id, a.appid, c.card_number, a.mobile_no, c.active status from mobiledb.m_mobile_subscriber a \n"
                            + "left join mobiledb.m_mobile_devices b on a.device_id = b.id left join mobiledb.m_mobile_subscriber_card c on a.id = c.subscriber_id \n"
                            + " where 1=1 "
                            + (!source_frm.trim().isEmpty() ? " and a.mobile_no like :source_frm " : "")
                            + (!cardList.isEmpty() ? " and b.device_name like :account " : "")
                            + " and a.appid in(:appIdList) "
                            + ") as t1";

                    Session session = DbHibernate.getSession("MOBILEDBMYSQL");
                    List<String> res = new ArrayList<>();

                    try {
                        q = session.createNativeQuery(qry).setParameter("appIdList", l);
                        if (!source_frm.trim().isEmpty()) {
                            q.setParameter("source_frm", "%" + source_frm.trim());
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
                    log.info("DATA: " + res);

                    accounts = res.stream().filter(f -> !(f == null || f.trim().isEmpty())).distinct()
                            .collect(Collectors.toMap(s -> CryptographerMin.cryptPan(s.split("[|]")[0], 2), s -> s.split("[|]")[1]));

                    cardList = new ArrayList<>(accounts.keySet());
                    log.info("CARDNUMBER(S) REGISTERED WITH PHONE >>" + cardList);
                } else {

                    return recordsList;
                }
            } else {
                log.info("P");
                return recordsList;
            }
        } else if (bankCode.equals("000") && cardList.isEmpty() && !source_frm.trim().isEmpty()) {

            String qry = "select concat_ws('|',card_number, status)  from (select distinct a.id, a.appid, c.card_number, a.mobile_no, c.active status from mobiledb.m_mobile_subscriber a \n"
                    + "left join mobiledb.m_mobile_devices b on a.device_id = b.id left join mobiledb.m_mobile_subscriber_card c on a.id = c.subscriber_id \n"
                    + " where 1=1 and a.mobile_no like :source_frm  and a.appid in(:appIdList)) as t1";

            Session session = DbHibernate.getSession("MOBILEDBMYSQL");
            List<String> res = new ArrayList<>();

            try {
                q = session.createNativeQuery(qry)
                        .setParameter("appIdList", etzAppIds)
                        .setParameter("source_frm", "%" + source_frm.trim());

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
            log.info("DATA: " + res);

            accounts = res.stream().filter(f -> !(f == null || f.trim().isEmpty())).distinct()
                    .collect(Collectors.toMap(s -> CryptographerMin.cryptPan(s.split("[|]")[0], 2), s -> s.split("[|]")[1]));

            cardList = new ArrayList<>(accounts.keySet());
            log.info("CARDNUMBER(S) REGISTERED WITH PHONE >>" + cardList);
        }

        if (cardList.isEmpty()) {
            return recordsList;
        }
        try {
            query = "(select c.card_account card_num,c.card_account as card2,c.Lastname,c.Firstname,c.issuer_code,c.Email, c.Phone,c.Street,c.card_expiration, c.bound_work,"
                    + "c.bound_value,c.Birth_Date,c.Change_Pin,(case when c.user_hotlist <> '1' then '0' else c.user_hotlist end) User_Hotlist,(case when c.user_locked <> '1' then '0' else c.user_locked end) User_Locked,(case when c.pin_missed =''  then '0' else c.pin_missed end) Pin_Missed, c.Last_Used,c.Modified,c.Created,"
                    + "c.Online_Balance,c.PayFee,c.CASHWTHDW_LIMIT,c.Client_ID, c.city, c.fax, c.company, '' active, control_id,sub_code  "
                    + "from e_cardholder c where c.card_num in(:cardList) order by card_num asc) ";

            query += " union (select c.card_account card_num,c.card_account as card2,c.Lastname,c.Firstname,c.issuer_code,c.Email, c.Phone,c.Street,c.card_expiration, c.bound_work,"
                    + "c.bound_value,c.Birth_Date,c.Change_Pin,(case when c.user_hotlist <> '1' then '0' else c.user_hotlist end) User_Hotlist,(case when c.user_locked <> '1' then '0' else c.user_locked end) User_Locked,(case when c.pin_missed =''  then '0' else c.pin_missed end) Pin_Missed, c.Last_Used,c.Modified,c.Created,"
                    + "c.Online_Balance,c.PayFee,c.CASHWTHDW_LIMIT,c.Client_ID, c.city, c.fax, c.company, '' active, control_id,sub_code  "
                    + "from e_cardholder c where c.card_account in(:cardList) order by card_account asc) ";

            query = "select card_num, card2, lastname,Firstname,issuer_code,Email, Phone,Street,card_expiration, bound_work,"
                    + " bound_value, Birth_Date,Change_Pin,(case when user_hotlist <> '1' then '0' else user_hotlist end) User_Hotlist,(case when user_locked <> '1' then '0' else user_locked end) User_Locked,(case when pin_missed =''  then '0' else pin_missed end) Pin_Missed, Last_Used,Modified,Created,"
                    + " Online_Balance,PayFee,CASHWTHDW_LIMIT,Client_ID, city, fax, company, '' active, control_id, sub_code  from (" + query + ") as t1 ";

            Session session = null;
            List<Object[]> records = new ArrayList<>();
            try {
                if (bankCode.equals("000")) {
                    session = DbHibernate.getSession("40.17MYSQL");
                } else {
                    session = DbHibernate.getSession(bankDB.getOrDefault(bankCode.trim(), null));
                }
                if (session != null) {

                    q = session.createNativeQuery(query)
                            .setParameter("cardList", cardList);

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
                ECARDHOLDER ctr = new ECARDHOLDER();
                try {

                    ctr.setCard_num(checkNull(record[0]));
                    ctr.setCard2(checkNull(record[1]));

                    if (bankCode.equals("700920")) {
                        String name = accounts.getOrDefault(record[0].toString(), "N/A");

                        ctr.setFirstname(name);
                    } else {
                        ctr.setLastname(checkNull(record[2]));
                        ctr.setFirstname(checkNull(record[3]));
                    }
                    ctr.setIssuer_code(checkNull(record[4]));
                    ctr.setEmail(checkNull(record[5]));
                    ctr.setPhone(source_frm.trim().isEmpty() ? checkNull(record[6]) : source_frm);
                    ctr.setStreet(checkNull(record[7]));
                    ctr.setCard_expiration(checkNull(record[8]));
                    ctr.setBound_work(checkNull(record[9]));
                    ctr.setBound_value(checkNull(record[10]));
                    ctr.setBirth_Date(checkNull(record[11]));
                    ctr.setChange_Pin(checkNull(record[12]));
                    ctr.setUser_Hotlist(checkNull(record[13]));
                    ctr.setUser_Locked(checkNull(record[14]));
                    ctr.setPin_Missed(checkNull(record[15]));
                    ctr.setLast_Used(checkNull(record[16]));
                    ctr.setModified(checkNull(record[17]));
                    ctr.setCreated(checkNull(record[18]));
                    ctr.setOnline_Balance(stripDecimalZeros(BigDecimal.valueOf(Double.valueOf(checkNull(record[19])))));
                    ctr.setPayFee(checkNull(record[20]));
                    ctr.setCASHWTHDW_LIMIT(checkNull(record[21]));
                    ctr.setClient_ID(checkNull(record[22]));
                    ctr.setCity(checkNull(record[23]));
                    ctr.setFax(checkNull(record[24]));
                    ctr.setCompany(checkNull(record[25]));
                    ctr.setActive(accounts.getOrDefault(record[0].toString(), "0").equals("1") ? "active" : "inactive");
                    ctr.setControl_id(checkNull(record[27]));
                    ctr.setSub_code(checkNull(record[28]));

                } catch (Exception er) {
                    log.error(er.getMessage(), er);
                }
                recordsList.add(ctr);
            }

        } catch (NoResultException ex) {
            return recordsList;
        }

        log.info("ECARDHOLDER Records Select done: " + recordsList);
        return recordsList;
    }

    public String modifyCardAccount(String jsonString) {
        String resp = "";
        log.info("modify card account request received >> " + jsonString);
        Gson g = new Gson();
        ApiPostData apiData = g.fromJson(jsonString, ApiPostData.class);

        String card_num = apiData.getCard_num();
        String source_frm = apiData.getFrom_source();
        String username = apiData.getUserName();
        String requestId = apiData.getClientId();
        String ucode = apiData.getUserCode();
        final String user = apiData.getUser_id();
        String choice = apiData.getStatus();
        String type_id = apiData.getType_id();
        String query = "";
        String bankCode = apiData.getBank_code();
        List<String> roleList = apiData.getAppId();
        List<String> cardList = new ArrayList<>();
        String action = apiData.getAction();
        String reason = apiData.getOptionType();
        String ipAddress = apiData.getIpAddress();
        HashMap<String, Object> options = apiData.getOptions();
        JSONObject response = new JSONObject();
        response.put("code", "01");
        response.put("message", "An Error Occured");

        if (source_frm == null) {
            source_frm = "";
        }
        if (card_num == null) {
            card_num = "";
        }
        if (action == null) {
            action = "";
        }
        if (card_num.isEmpty()) {
            response.put("message", "Invalid Account Number");
        } else {
            cardList.add(card_num.trim());

            ECARDHOLDER card = new ECARDHOLDER();
            card.setCard_num(card_num);
            log.info("ROLES ::: " + roleList + " - " + action);
            switch (action) {
                case "hotlist":
                case "dehotlist":
                case "pinreset":
                case "addaccount":
                case "enhancement":
                    if (roleList.contains("2")) {

                        int logRecord = handleSQLDuplicateErrorAndNoResultFound(card.getCard_num(), action, reason, bankCode);

                        switch (logRecord) {
                            case 0:

                                response = cmsAction(card, username, "2", action, reason, bankCode, user, ipAddress, choice, options);
                                break;
                            case 1:
                                response.put("message", "Request exists OR awaiting authorization");
                                break;
                            default:
                                response.put("message", "An Error Occured");
                                break;
                        }
                    } else if (roleList.contains("1") || roleList.contains("3")) {

                        response = cmsAction(card, username, "3", action, reason, bankCode, user, ipAddress, choice, options);

                    } else {
                        response.put("message", "Authorization Required");
                    }
                    break;
                default:
                    break;
            }
        }

        return response.toString();
    }

    public JSONObject cmsAction(ECARDHOLDER card, String userName, String role, String action, String reason, String bankCode, String userId, String ipAddress, String choice, HashMap options) {
        String result = "";
        List<String> mcList = new ArrayList<>();
        Transaction tx = null;
        Session session = DbHibernate.getSession("40.17MYSQL");
        Session mdbSession = DbHibernate.getSession("MOBILEDBMYSQL");
        Session bankSession = null;
        String qry = "";
        JSONObject resp = new JSONObject();
        boolean rollback = true;
        resp.put("code", "01");
        resp.put("message", choice + " " + action + " failed");
        String type = "";
        if (role.equals("2")) {
            type = "Initiated " + action + " request";
        } else {
            type = "Authorized " + action + " request";
        }
        switch (action) {
            case "hotlist":
            case "dehotlist":
                mcList = Arrays.asList(StringUtils.substringBeforeLast(portalSettings.getSetting("hdaccount_mc"), ":").split(","));
                break;
            case "addaccount":
            case "pinreset":
            case "enhancement":
                mcList = Arrays.asList(StringUtils.substringBeforeLast(portalSettings.getSetting(action + "_mc"), ":").split(","));
                break;
            default:
                break;
        }

        log.info("List:: " + mcList + " role: " + role);
        final String finalType = type;

        try {
            if (bankCode.equals("000")) {
                bankSession = session;
            } else {
                bankSession = DbHibernate.getSession(bankDB.getOrDefault(bankCode.trim(), null));
            }

            tx = bankSession.beginTransaction();
            switch (role) {
                case "1":
                case "2":
                case "3":
                    int i = 0;

                    if (!((role.equals("3") || role.equals("1")) && (choice.equalsIgnoreCase("AUTHORIZE") || choice.equalsIgnoreCase("DENY")))) {
                        if (action.equalsIgnoreCase("addaccount")) {
                            qry = "insert into e_cmslog (card_num,firstname, lastname, street, city, phone, fax, sub_code , email, action, reason, status, date_initiated, initiated_by) (select card_num, :firstname firstname, :lastname lastname, street, city, phone, :fax fax, :sub_code sub_code, email ,:action as action, :reason as reason, :status as status, now() as date_initiated, :username as initiated_by from e_cardholder where card_account = :cardnum or card_num = :cardnum)";
                        } else {
                            qry = "insert into e_cmslog (card_num,firstname, lastname, street, city, phone, fax, email, action, reason, status, date_initiated, initiated_by) (select card_num, firstname, lastname, street, city, phone, fax, email ,:action as action, :reason as reason, :status as status, now() as date_initiated, :username as initiated_by from e_cardholder where card_account = :cardnum or card_num = :cardnum)";
                        }
                        q = bankSession.createNativeQuery(qry);
                        q.setParameter("cardnum", card.getCard_num());
                        q.setParameter("status", "PENDING");
                        q.setParameter("reason", reason);
                        q.setParameter("action", action);
                        q.setParameter("username", userName);

                        if (action.equalsIgnoreCase("addaccount")) {
                            q.setParameter("firstname", options.getOrDefault("firstname", "").toString());
                            q.setParameter("lastname", options.getOrDefault("lastname", "").toString());
                            q.setParameter("sub_code", options.getOrDefault("branchcode", "001").toString());
                            q.setParameter("fax", options.getOrDefault("fax", "").toString());
                        }

                        i = q.executeUpdate();
                        log.info("update i " + i);
                    } else {
                        i = 1;
                    }
                    if (i > 0) {
                        int c = 0;

                        if ((!mcList.contains(bankCode) && role.equals("2")) || ((role.equals("3") || role.equals("1")) && (choice.equalsIgnoreCase("AUTHORIZE") || choice.equalsIgnoreCase("DENY")))) {

                            if (role.equals("2")) {
                                choice = "Authorize";
                            }
                            String name = "";
                            String mobile = "";
                            String firstname = "";
                            String lastname = "";
                            String branchcode = "";
                            String fax = "";

                            String cardNumQry = "";
                            List<Object[]> rec = new ArrayList<>();
                            if (action.equalsIgnoreCase("addaccount")) {
                                cardNumQry = "select card_num, firstname, lastname, sub_code, fax from e_cmslog where (card_num = :card_num or card_account = :card_num) and status='PENDING' and action = 'addaccount' limit 1";
                            } else {
                                cardNumQry = "select card_num, firstname, lastname, sub_code, fax from e_cardholder where (card_num = :card_num or card_account = :card_num) limit 1";
                            }
                            q = bankSession.createNativeQuery(cardNumQry);
                            q.setParameter("card_num", card.getCard_num());

                            rec = q.getResultList();

                            for (Object[] record : rec) {
                                name = name.isEmpty() ? (checkNull(record[1]).isEmpty() ? checkNull(record[2]) : checkNull(record[1])) : name;
                                firstname = checkNull(record[1]);
                                lastname = checkNull(record[2]);
                                branchcode = checkNull(record[3]);
                                fax = checkNull(record[4]);
                            }

                            qry = "update e_cmslog set status= :choice, date_authorized = now(), authorized_by = :username where status='PENDING' and card_num = :cardnum and action =:action";
                            q = bankSession.createNativeQuery(qry);
                            q.setParameter("cardnum", card.getCard_num());
                            q.setParameter("username", userName);
                            q.setParameter("choice", choice.equalsIgnoreCase("AUTHORIZE") ? "APPROVED" : "DENIED");
                            q.setParameter("action", action);
                            int s = q.executeUpdate();
                            log.info("UPDATE AUTORIZATION ::: " + s);

                            if (s > 0 && choice.equalsIgnoreCase("AUTHORIZE")) {

                                if (action.equalsIgnoreCase("enhancement")) {
                                    String EnhanceType = "";
                                    if (reason.toLowerCase().contains("medium")) {
                                        EnhanceType = "013";
                                    } else if (reason.toLowerCase().contains("enhanced")) {
                                        EnhanceType = "014";
                                    } else {
                                        EnhanceType = "013";
                                    }
                                    String enhanceCard = "update e_cardholder set control_id = :type where (card_num = :cardnum  or card_account =:cardnum)";

                                    q = bankSession.createNativeQuery(enhanceCard)
                                            .setParameter("type", EnhanceType);
                                    q.setParameter("cardnum", card.getCard_num());
                                    log.info("QUERY TO ENHANCE >>" + enhanceCard);
                                    c = q.executeUpdate();

                                    rollback = c > 0;

                                } else if (action.equalsIgnoreCase("pinreset") || action.equalsIgnoreCase("addaccount")) {
                                    String encCard = CryptographerMin.cryptPan(card.getCard_num(), 1);

                                    String sQry = "select mobile_no, name  from (select distinct a.id, a.appid, c.card_number, c.request_byip name, a.mobile_no, c.active status from mobiledb.m_mobile_subscriber a \n"
                                            + " left join mobiledb.m_mobile_devices b on a.device_id = b.id left join mobiledb.m_mobile_subscriber_card c on a.id = c.subscriber_id \n"
                                            + " where c.card_number = :card_number limit 1) as t1";

                                    q = mdbSession.createNativeQuery(sQry)
                                            .setParameter("card_number", encCard);

                                    rec = q.getResultList();

                                    for (Object[] record : rec) {
                                        mobile = checkNull(record[0]);
                                        name = name.trim().isEmpty() ? "user" : checkNull(record[1]);
                                    }

                                    JSONObject generatePinRequest = new JSONObject(PinGenerator.generatePin(card.getCard_num()));

                                    String pin = "";
                                    int k = 0;
                                    if (generatePinRequest.get("result").equals("success")) {
                                        pin = generatePinRequest.getString("pin");

                                        String resetPin = "update e_cardholder set modified = now(), change_pin = '0', x_offset= :offset, card_pin= :cardpin, user_hotlist ='0',pin_missed=0 "
                                                + (action.equalsIgnoreCase("addaccount") ? " ,firstname = :firstname, lastname = :lastname, sub_code = :branchcode, fax = :fax " : "")
                                                + " where (card_num = :cardnum or card_account = :cardnum)";
                                        log.info("QUERY TO RESET: " + resetPin);
                                        q = bankSession.createNativeQuery(resetPin);
                                        q.setParameter("offset", generatePinRequest.getString("x_offset"));
                                        q.setParameter("cardpin", generatePinRequest.getString("card_pin"));
                                        q.setParameter("cardnum", generatePinRequest.getString("cardNumber"));

                                        if (action.equalsIgnoreCase("addaccount")) {

                                            q.setParameter("firstname", firstname);
                                            q.setParameter("lastname", lastname);
                                            q.setParameter("branchcode", branchcode);
                                            q.setParameter("fax", fax);
                                        }

                                        c = q.executeUpdate();
                                        log.info("Pin reset : " + c);
                                    }

                                    if (c > 0) {
                                        String sms_header = config.getProperty(bankCode.trim() + "_SMS");
                                        sms_header = sms_header.isEmpty() ? "Etranzact" : sms_header;
                                        String smsResponse = new Alert().sendSMS(mobile, createMsg(name, pin, action.equalsIgnoreCase("pinreset") ? "pin_reset_template" : "aa_sms_template"), sms_header);
                                        if (!smsResponse.isEmpty()) {
                                            if (!smsResponse.equalsIgnoreCase("Invalid")) {
                                                log.info("SMSR ::: " + smsResponse);
                                                JSONObject smsResult = new JSONObject(smsResponse);

                                                if (!smsResult.isEmpty() && smsResult.optString("responseCode", "99").equals("00")) {

                                                    resp.put("code", "00");
                                                    resp.put("message", action.equalsIgnoreCase("addaccount") ? "Add account successful. Pin sent: 1" : "Pin reset successful. Sent: 1");
                                                    rollback = false;
                                                } else {
                                                    resp.put("message", action.equalsIgnoreCase("addaccount") ? "Add account unsuccessful. Pin sent: 0" : "Pin reset unsuccessful. Sent: 0");
                                                }
                                            } else {
                                                resp.put("message", action.equalsIgnoreCase("addaccount") ? "Add account unsuccessful. Pin sent: 0" : "Pin reset unsuccessful. Sent: 0");
                                            }
                                        } else {
                                            resp.put("message", action.equalsIgnoreCase("addaccount") ? "Add account unsuccessful. Pin sent: 0" : "Pin reset unsuccessful. Sent: 0");
                                        }
                                    }
                                } else if (action.equalsIgnoreCase("hotlist") || action.equalsIgnoreCase("dehotlist")) {
                                    String updateCardholder = "update e_cardholder SET user_hotlist = :action where (card_num = :cardnum or card_account = :cardnum)";
                                    q = bankSession.createNativeQuery(updateCardholder);
                                    q.setParameter("cardnum", card.getCard_num());
                                    q.setParameter("action", action.equalsIgnoreCase("hotlist") ? "0" : "1");
                                    log.info("QUERY TO HOTLIST/DEHOTLIST CARD >>" + updateCardholder);
                                    int j = q.executeUpdate();

                                    rollback = !(j > 0);
                                    resp.put("code", !rollback ? "00" : "01");
                                    resp.put("message", (!role.equals("2") ? (choice + " ") : "") + action + (!rollback ? " request successful." : " request failed."));
                                }
                            } else if (s > 0) {
                                resp.put("code", "00");
                                resp.put("message", (!role.equals("2") ? (choice + " ") : "") + action + " request successful");
                                rollback = false;
                            }
                        } else {
                            resp.put("code", "00");
                            resp.put("message", (!role.equals("2") ? (choice + " ") : "") + action + " request successful");
                            rollback = false;
                        }
                    }

                    if (rollback) {
                        try {
                            tx.rollback();

                        } catch (Exception rt) {
                            log.error(rt.getMessage(), rt);
                        }
                    } else {
                        tx.commit();
                    }
                    break;
                default:
                    resp.put("message", (!role.equals("2") ? (choice + " ") : "") + action + " Failed");
                    break;
            }
        } catch (NoResultException n) {
            resp.put("message", "Account Not Found");
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
        } catch (Exception ex) {
            String msg = ex.getMessage();
            if (msg.contains("ConstraintViolationException")) {
                log.info("Database Exception>> Duplicate Record");
                resp.put("message", "Request exists OR waiting authorization");
            } else {
                log.info("Database Exception>> an error occured");
                resp.put("message", "An error occured. Please try again");
            }
            log.info("XPORTAL CMS ERROR >>> " + ex);
            log.error(ex.getMessage(), ex);
            try {
                if (tx != null) {
                    tx.rollback();
                }
            } catch (Exception exc) {
                log.info("XPORTAL CMS ERROR >> COULD NOT ROLLBACK");
            }
        } finally {
            try {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            try {
                if (bankSession != null && bankSession.isOpen()) {
                    bankSession.close();
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            try {
                if (mdbSession != null && mdbSession.isOpen()) {
                    mdbSession.close();
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }

        new Thread(() -> {
            AuditTrail audit = new AuditTrail(userId, finalType + " for " + card.getCard_num().trim(), "cms", null, action.equalsIgnoreCase("addaccount") ? "add account with data " + options.toString() : action, ipAddress);
            new AuditDao().insertIntoAuditTrail(audit);
        }).start();
        log.info("MES::: " + resp.toString());

        return resp;
    }

    public JSONObject cmsAutoAction(ECARDHOLDER card, String userName, String role, String action, String reason, String bankCode, String userId, String ipAddress, String choice, HashMap options) {
        String result = "";
        List<String> mcList = new ArrayList<>();
        Transaction tx = null;
        Session session = DbHibernate.getSession("40.17MYSQL");
        Session mbSession = DbHibernate.getSession("MOBILEDBMYSQL");
        Session bankSession = null;
        String qry = "";
        JSONObject resp = new JSONObject();
        boolean rollback = true;
        resp.put("code", "01");
        resp.put("message", choice + " " + action + " failed");
        String type = "";
        if (role.equals("2")) {
            type = "Initiated " + action + " request";
        } else {
            type = "Authorized " + action + " request";
        }
        switch (action) {
            case "hotlist":
            case "dehotlist":
                mcList = Arrays.asList(StringUtils.substringBeforeLast(portalSettings.getSetting("hdaccount_mc"), ":").split(","));
                break;
            case "addaccount":
            case "pinreset":
            case "enhancement":
                mcList = Arrays.asList(StringUtils.substringBeforeLast(portalSettings.getSetting(action + "_mc"), ":").split(","));
                break;
            default:
                break;
        }

        log.info("List:: " + mcList + " role: " + role);
        final String finalType = type;

        try {
            if (bankCode.equals("000")) {
                bankSession = session;
            } else {
                bankSession = DbHibernate.getSession(bankDB.getOrDefault(bankCode.trim(), null));
            }

            tx = bankSession.beginTransaction();
            switch (role) {
                case "1":
                case "2":
                case "3":
                    int i = 0;

                    if (!((role.equals("3") || role.equals("1")) && (choice.equalsIgnoreCase("AUTHORIZE") || choice.equalsIgnoreCase("DENY")))) {
                        if (action.equalsIgnoreCase("addaccount")) {
                            qry = "insert into e_cmslog (card_num,firstname, lastname, street, city, phone, fax, sub_code , email, action, reason, status, date_initiated, initiated_by) (select card_num, :firstname firstname, :lastname lastname, street, city, phone, :fax fax, :sub_code sub_code, email ,:action as action, :reason as reason, :status as status, now() as date_initiated, :username as initiated_by from e_cardholder where card_account = :cardnum or card_num = :cardnum)";
                        } else {
                            qry = "insert into e_cmslog (card_num,firstname, lastname, street, city, phone, fax, email, action, reason, status, date_initiated, initiated_by) (select card_num, firstname, lastname, street, city, phone, fax, email ,:action as action, :reason as reason, :status as status, now() as date_initiated, :username as initiated_by from e_cardholder where card_account = :cardnum or card_num = :cardnum)";
                        }
                        q = bankSession.createNativeQuery(qry);
                        q.setParameter("cardnum", card.getCard_num());
                        q.setParameter("status", "PENDING");
                        q.setParameter("reason", reason);
                        q.setParameter("action", action);
                        q.setParameter("username", userName);

                        if (action.equalsIgnoreCase("addaccount")) {
                            q.setParameter("firstname", options.getOrDefault("firstname", "").toString());
                            q.setParameter("lastname", options.getOrDefault("lastname", "").toString());
                            q.setParameter("sub_code", options.getOrDefault("branchcode", "001").toString());
                            q.setParameter("fax", options.getOrDefault("fax", "").toString());
                        }

                        i = q.executeUpdate();
                        log.info("update i " + i);
                    } else {
                        i = 1;
                    }
                    if (i > 0) {
                        int c = 0;

                        if ((!mcList.contains(bankCode) && role.equals("2")) || ((role.equals("3") || role.equals("1")) && (choice.equalsIgnoreCase("AUTHORIZE") || choice.equalsIgnoreCase("DENY")))) {

                            if (role.equals("2")) {
                                choice = "Authorize";
                            }
                            String name = "";
                            String mobile = "";
                            String firstname = "";
                            String lastname = "";
                            String branchcode = "";
                            String fax = "";

                            String cardNumQry = "";
                            List<Object[]> rec = new ArrayList<>();
                            if (action.equalsIgnoreCase("addaccount")) {
                                cardNumQry = "select card_num, firstname, lastname, sub_code, fax from e_cmslog where (card_num = :card_num or card_account = :card_num) and status='PENDING' and action = 'addaccount' limit 1";
                            } else {
                                cardNumQry = "select card_num, firstname, lastname, sub_code, fax from e_cardholder where (card_num = :card_num or card_account = :card_num) limit 1";
                            }
                            q = bankSession.createNativeQuery(cardNumQry);
                            q.setParameter("card_num", card.getCard_num());

                            rec = q.getResultList();

                            for (Object[] record : rec) {
                                name = name.isEmpty() ? (checkNull(record[1]).isEmpty() ? checkNull(record[2]) : checkNull(record[1])) : name;
                                firstname = checkNull(record[1]);
                                lastname = checkNull(record[2]);
                                branchcode = checkNull(record[3]);
                                fax = checkNull(record[4]);
                            }

                            qry = "update e_cmslog set status= :choice, date_authorized = now(), authorized_by = :username where status='PENDING' and card_num = :cardnum and action =:action";
                            q = bankSession.createNativeQuery(qry);
                            q.setParameter("cardnum", card.getCard_num());
                            q.setParameter("username", userName);
                            q.setParameter("choice", choice.equalsIgnoreCase("AUTHORIZE") ? "APPROVED" : "DENIED");
                            q.setParameter("action", action);
                            int s = q.executeUpdate();
                            log.info("UPDATE AUTORIZATION ::: " + s);

                            if (s > 0 && choice.equalsIgnoreCase("AUTHORIZE")) {

                                if (action.equalsIgnoreCase("enhancement")) {
                                    String EnhanceType = "";
                                    if (reason.toLowerCase().contains("medium")) {
                                        EnhanceType = "013";
                                    } else if (reason.toLowerCase().contains("enhanced")) {
                                        EnhanceType = "014";
                                    } else {
                                        EnhanceType = "013";
                                    }
                                    String enhanceCard = "update e_cardholder set control_id = :type where (card_num = :cardnum  or card_account =:cardnum)";

                                    q = bankSession.createNativeQuery(enhanceCard)
                                            .setParameter("type", EnhanceType);
                                    q.setParameter("cardnum", card.getCard_num());
                                    log.info("QUERY TO ENHANCE >>" + enhanceCard);
                                    c = q.executeUpdate();

                                    rollback = c > 0;

                                } else if (action.equalsIgnoreCase("pinreset") || action.equalsIgnoreCase("addaccount")) {
                                    String encCard = CryptographerMin.cryptPan(card.getCard_num(), 1);

                                    String sQry = "select mobile_no, name  from (select distinct a.id, a.appid, c.card_number, c.request_byip name, a.mobile_no, c.active status from mobiledb.m_mobile_subscriber a \n"
                                            + " left join mobiledb.m_mobile_devices b on a.device_id = b.id left join mobiledb.m_mobile_subscriber_card c on a.id = c.subscriber_id \n"
                                            + " where c.card_number = :card_number limit 1) as t1";

                                    q = mbSession.createNativeQuery(sQry)
                                            .setParameter("card_number", encCard);

                                    rec = q.getResultList();

                                    for (Object[] record : rec) {
                                        mobile = checkNull(record[0]);
                                        name = name.trim().isEmpty() ? "user" : checkNull(record[1]);
                                    }

                                    JSONObject generatePinRequest = new JSONObject(new PinGenerator().generatePin(card.getCard_num()));

                                    String pin = "";
                                    int k = 0;
                                    if (generatePinRequest.get("result").equals("success")) {
                                        pin = generatePinRequest.getString("pin");

                                        String resetPin = "update e_cardholder set modified = now(), change_pin = '0', x_offset= :offset, card_pin= :cardpin, user_hotlist ='0',pin_missed=0 "
                                                + (action.equalsIgnoreCase("addaccount") ? " ,firstname = :firstname, lastname = :lastname, sub_code = :branchcode, fax = :fax " : "")
                                                + " where (card_num = :cardnum or card_account = :cardnum)";
                                        log.info("QUERY TO RESET: " + resetPin);
                                        q = bankSession.createNativeQuery(resetPin);
                                        q.setParameter("offset", generatePinRequest.getString("x_offset"));
                                        q.setParameter("cardpin", generatePinRequest.getString("card_pin"));
                                        q.setParameter("cardnum", generatePinRequest.getString("cardNumber"));

                                        if (action.equalsIgnoreCase("addaccount")) {

                                            q.setParameter("firstname", firstname);
                                            q.setParameter("lastname", lastname);
                                            q.setParameter("branchcode", branchcode);
                                            q.setParameter("fax", fax);
                                        }

                                        c = q.executeUpdate();
                                        log.info("Pin reset : " + c);
                                    }

                                    if (c > 0) {
                                        String sms_header = config.getProperty(bankCode.trim() + "_SMS");
                                        sms_header = sms_header.isEmpty() ? "Etranzact" : sms_header;
                                        String smsResponse = new Alert().sendSMS(mobile, createMsg(name, pin, action.equalsIgnoreCase("pinreset") ? "pin_reset_template" : "aa_sms_template"), sms_header);
                                        if (!smsResponse.isEmpty()) {
                                            if (!smsResponse.equalsIgnoreCase("Invalid")) {
                                                log.info("SMSR ::: " + smsResponse);
                                                JSONObject smsResult = new JSONObject(smsResponse);

                                                if (!smsResult.isEmpty() && smsResult.optString("responseCode", "99").equals("00")) {

                                                    resp.put("code", "00");
                                                    resp.put("message", action.equalsIgnoreCase("addaccount") ? "Add account successful. Pin sent: 1" : "Pin reset successful. Sent: 1");
                                                    rollback = false;
                                                } else {
                                                    resp.put("message", action.equalsIgnoreCase("addaccount") ? "Add account unsuccessful. Pin sent: 0" : "Pin reset unsuccessful. Sent: 0");
                                                }
                                            } else {
                                                resp.put("message", action.equalsIgnoreCase("addaccount") ? "Add account unsuccessful. Pin sent: 0" : "Pin reset unsuccessful. Sent: 0");
                                            }
                                        } else {
                                            resp.put("message", action.equalsIgnoreCase("addaccount") ? "Add account unsuccessful. Pin sent: 0" : "Pin reset unsuccessful. Sent: 0");
                                        }
                                    }
                                } else if (action.equalsIgnoreCase("hotlist") || action.equalsIgnoreCase("dehotlist")) {
                                    String updateCardholder = "update e_cardholder SET user_hotlist = :action where (card_num = :cardnum or card_account = :cardnum)";
                                    q = bankSession.createNativeQuery(updateCardholder);
                                    q.setParameter("cardnum", card.getCard_num());
                                    q.setParameter("action", action.equalsIgnoreCase("hotlist") ? "0" : "1");
                                    log.info("QUERY TO HOTLIST/DEHOTLIST CARD >>" + updateCardholder);
                                    int j = q.executeUpdate();

                                    rollback = !(j > 0);
                                    resp.put("code", !rollback ? "00" : "01");
                                    resp.put("message", (!role.equals("2") ? (choice + " ") : "") + action + (!rollback ? " request successful." : " request failed."));
                                }
                            } else if (s > 0) {
                                resp.put("code", "00");
                                resp.put("message", (!role.equals("2") ? (choice + " ") : "") + action + " request successful");
                                rollback = false;
                            }
                        } else {
                            resp.put("code", "00");
                            resp.put("message", (!role.equals("2") ? (choice + " ") : "") + action + " request successful");
                            rollback = false;
                        }
                    }

                    if (rollback) {
                        try {
                            tx.rollback();

                        } catch (Exception rt) {
                            log.error(rt.getMessage(), rt);
                        }
                    } else {
                        tx.commit();
                    }
                    break;
                default:
                    resp.put("message", (!role.equals("2") ? (choice + " ") : "") + action + " Failed");
                    break;
            }
        } catch (NoResultException n) {
            resp.put("message", "Account Not Found");
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
        } catch (Exception ex) {
            String msg = ex.getMessage();
            if (msg.contains("ConstraintViolationException")) {
                log.info("Database Exception>> Duplicate Record");
                resp.put("message", "Request exists OR waiting authorization");
            } else {
                log.info("Database Exception>> an error occured");
                resp.put("message", "An error occured. Please try again");
            }
            log.info("XPORTAL CMS ERROR >>> " + ex);
            log.error(ex.getMessage(), ex);
            try {
                if (tx != null) {
                    tx.rollback();
                }
            } catch (Exception exc) {
                log.info("XPORTAL CMS ERROR >> COULD NOT ROLLBACK");
            }
        } finally {
            try {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            try {
                if (bankSession != null && bankSession.isOpen()) {
                    bankSession.close();
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            try {
                if (mbSession != null && mbSession.isOpen()) {
                    mbSession.close();
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }

        new Thread(() -> {
            AuditTrail audit = new AuditTrail(userId, finalType + " for " + card.getCard_num().trim(), "cms", null, action.equalsIgnoreCase("addaccount") ? "add account with data " + options.toString() : action, ipAddress);
            new AuditDao().insertIntoAuditTrail(audit);
        }).start();
        log.info("MES::: " + resp.toString());

        return resp;
    }

    public List<E_CMS> getCmsRequests(String jsonString) {

        log.info("CMS Requests request received >> " + jsonString);
        Gson j = new Gson();
        ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);

        String start_dt = apiData.getStartDate();
        String end_dt = apiData.getEndDate();
        String type = apiData.getAction();
        String cardnumber = apiData.getCard_num();
        String status = apiData.getStatus();
        String order = apiData.getOptionType();
        String bankCode = apiData.getBank_code();

        log.info("cardnumber: " + cardnumber);
        List<E_CMS> records = new ArrayList<>();
        if (status == null) {
            status = "";
        }
        if (cardnumber == null) {
            cardnumber = "";
        }
        if (type == null) {
            type = "";
        }

        Session session = null;

        try {
            if (bankCode.equals("000")) {
                session = DbHibernate.getSession("40.17MYSQL");
            } else {
                session = DbHibernate.getSession(bankDB.getOrDefault(bankCode.trim(), null));
            }

            String qry = "Select id, card_num, card_num card2, Lastname, Firstname, issuer_code, Email, Phone, Street, card_expiration, bound_work, bound_value, Birth_Date, Change_Pin, User_Hotlist, User_Locked, Pin_Missed,    Last_Used,  Modified,Created, Online_Balance, PayFee, CASHWTHDW_LIMIT, Client_ID, city, fax, company, action, reason, date_initiated,date_authorized, authorized_by, status, initiated_by from ecarddb.e_cmslog where 1=1"
                    + (!order.equalsIgnoreCase("date_authorized") ? " and date_initiated between :start_dt and :end_dt " : " and date_authorized between :start_dt and :end_dt ")
                    + (!type.isEmpty() && !type.equalsIgnoreCase("ALL") ? " and action = :type " : "")
                    + (!cardnumber.isEmpty() ? " and card_num = :cardnum " : "")
                    + (!status.isEmpty() && !status.equalsIgnoreCase("ALL") ? " and status = :status " : "")
                    + " order by date_initiated desc";

            q = session.createNativeQuery(qry, E_CMS.class);

            q.setParameter("start_dt", start_dt);
            q.setParameter("end_dt", end_dt);
            if (!cardnumber.equals("")) {
                q.setParameter("cardnum", cardnumber);
            }
            if (!type.isEmpty() && !type.equalsIgnoreCase("ALL")) {
                q.setParameter("type", type);
            }
            if (!status.isEmpty() && !status.equalsIgnoreCase("ALL")) {
                q.setParameter("status", status);
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

        return records;
    }

    public List<NODES> getSubissuers(String jsonString) {

        log.info("SubIssuer request received >> " + jsonString);
        Gson j = new Gson();
        ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);

        String bankCode = apiData.getBank_code();
        List<NODES> records = new ArrayList<>();

        Session session = null;

        try {
            if (bankCode.equals("000")) {
                session = DbHibernate.getSession("40.17MYSQL");
            } else {
                session = DbHibernate.getSession(bankDB.getOrDefault(bankCode.trim(), null));
            }

            String qry = "select sub_code id, sub_name name from e_subissuer where 1=1  and trim(sub_name) !='' "
                    + " order by sub_name asc";

            q = session.createNativeQuery(qry, NODES.class);

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

        return records;
    }

    public int handleSQLDuplicateErrorAndNoResultFound(String cardnum, String action, String reason, String bankCode) {
        Session session = null;
        try {

            String qry = "select id, card_num , card_num card2, Lastname ,Firstname, issuer_code, Email ,Phone, Street,card_expiration, bound_work ,bound_value,Birth_Date,Change_Pin, User_Hotlist, User_Locked , Pin_Missed, Last_Used , Modified , Created , Online_Balance ,PayFee,CASHWTHDW_LIMIT,Client_ID, city, fax,company ,action ,reason ,date_initiated, date_authorized,authorized_by, status from e_cmslog where card_num =:cardnum and status = 'pending' ";
            if (bankCode.equals("000")) {
                session = DbHibernate.getSession("40.17MYSQL");
            } else {
                session = DbHibernate.getSession(bankDB.getOrDefault(bankCode.trim(), null));
            }
            q = session.createNativeQuery(qry);
            q.setParameter("cardnum", cardnum);

            Object[] record = (Object[]) q.getSingleResult();

            if (reason.equalsIgnoreCase("new user")) {
                qry = "select fax, card_num from e_cardholder where (card_num =:cardnum or card_account =:cardnum) and (fax is null or fax = '')";
                q = session.createNativeQuery(qry);
                q.setParameter("cardnum", cardnum);

                Object[] record1 = (Object[]) q.getSingleResult();
                if (record1 == null) {
                    log.info("Card has been registered already");
                    return 3;
                }
            }

            if (record != null) {
                log.info("Pending Request or awaiting authorization");
                return 1;
            }

        } catch (NoResultException ex) {
            return 0;
        } catch (Exception ex) {
            return 2;
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
        return 0;
    }

    public int handleSQLDuplicateErrorAndNoResultFoundAuto(String cardnum, String action, String reason, String bankCode) {

        int result = 2;

        String qry = "select id, card_num from e_cmslog where card_num =:cardnum and status = 'pending' ";
        if (bankCode.equals("000")) {
            Session session = null;
            try {
                session = DbHibernate.getSession("40.17MYSQL");

                q = session.createNativeQuery(qry);
                q.setParameter("cardnum", cardnum);

                Object[] record = (Object[]) q.getSingleResult();

                if (record != null) {
                    log.info("Pending Request or awaiting authorization");
                    result = 1;
                }

            } catch (NoResultException ex) {
                result = 0;
            } catch (Exception ex) {
                result = 2;
            } finally {
                if (session != null) {
                    try {
                        session.close();
                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                    }
                }
            }
        } else {

        }

        return result;
    }

    protected static String checkNull(Object Data) {
        if (Data != null && !Data.equals("")) {
            return Data.toString().trim();
        }
        return "";
    }

    protected String checkNumberNull(String Data) {
        if (Data != null && !Data.equals("")) {
            if (Data.startsWith("686") || Data.startsWith("863")
                    || Data.startsWith("844") || Data.startsWith("285")) {
                return Data.substring(3, Data.length());
            } else {
                return hashAccountNumber(Data);
            }
        }
        return "NULL";
    }

    protected String hashAccountNumber(String accNo) {
        if (accNo.length() == 16) {
            int start = accNo.length() / 2;
            HashNumber hn = new HashNumber();

            accNo = hn.hashAccountValue(accNo, start / 2, start / 2);
        }
        return accNo;
    }

    public static void configSetup() {
        Session session = null;
        List<Object[]> records = new ArrayList<>();

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

        log.info("APP:: " + appIdList);
    }

    public String createMsg(String name, String pin, String type) {
        String message = StringUtils.substringBeforeLast(portalSettings.getSetting(type), ":");
        message = message.replace("<NAME>", name);
        message = message.replace("<PIN>", pin);
        return message;
    }

}
