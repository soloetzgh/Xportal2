package com.etzgh.xportal.dao;

import com.etzgh.xportal.controller.PortalSettings;
import com.etzgh.xportal.model.AccountRequest;
import com.etzgh.xportal.model.ApiPostData;
import com.etzgh.xportal.model.AuditTrail;
import com.etzgh.xportal.model.BankApplication;
import com.etzgh.xportal.model.BankNewsItem;
import com.etzgh.xportal.model.CardAccounts;
import com.etzgh.xportal.model.CustomerFeedback;
import com.etzgh.xportal.model.ECARDHOLDER;
import com.etzgh.xportal.model.FraudReport;
import com.etzgh.xportal.model.MobileActivateLog;
import com.etzgh.xportal.model.MobileAppTransaction;
import com.etzgh.xportal.model.MobileAppUser;
import com.etzgh.xportal.model.MobileAppUserLog;
import com.etzgh.xportal.model.NODES;
import com.etzgh.xportal.utility.Alert;
import com.etzgh.xportal.utility.AutoRequest;
import com.etzgh.xportal.utility.Config;
import com.etzgh.xportal.utility.CryptographerMin;
import com.etzgh.xportal.utility.DbHibernate;
import com.etzgh.xportal.utility.DoHTTPRequest;
import com.etzgh.xportal.utility.PasswordGenerator;
import com.etzgh.xportal.utility.PinGenerator;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.persistence.NoResultException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.json.JSONObject;

public class MobileAppDao {

    List<ECARDHOLDER> records = new ArrayList<>();
    private static final Logger log = Logger.getLogger(MobileAppDao.class.getName());
    private static final Config config = new Config();
    private static final PortalSettings portalSettings = new PortalSettings();
    private static final Map<String, String> bankDB = new HashMap<>();
    private static final Map<String, String> bankName = new HashMap<>();
    private static final List<String> ussdGateBanks;
    private static final HashMap<String, String> bankAppIdList = new HashMap<>();
    private static final HashMap<String, String> appIdBankList = new HashMap<>();

    private static final HashMap<String, String> bankList = new HashMap<>();
    protected static final List<NODES> transCatList;
    private static final String bankApplicationImagePath;

    protected static final HashMap<String, String> BANK_NAME_MAP;
    protected static final HashMap<String, String> MOBILEAPP_STATUS;

    private static final String mobgateIV;
    private static final String mobgateKEY;
    private static final String ussdGateIV;
    private static final String ussdGateKEY;

    public static void main(String[] args) {
//        String json = "{\"merchant\":\"\",\"product\":\"\",\"accountNumber\":\"\",\"service\":\"modifyAccount\",\"action\":\"hotlist\",\"apiSecret\":\"\",\"userCode\":\"[1],[17]|0060000290:0067620000000010,[2000000000000048],[2000000000000049]|? string:? string:? string:? string:SCB:6AGAS ? ? ? ?,[2000000000000053]|DSTV,[2000000000000054]|ADSL~TELESOL,[2000000000000060]|000,[2000000000000062]|ALL,[2000000000000063]|KNUST,[2000000000000064]|ALL,[22],[2500000000000049]|2,[2500000000000050]|91,[2500000000000053]|1,[2500000000000056]|3,[2500000000000058]|1,[2500000000000059]|1,[29],[50]|2,[51]|2,[71],[91]|ALL\",\"admin\":\"\",\"cardSchemeNumbers\":\"\",\"type_desc\":\"\",\"role_id\":\"2\",\"type_id\":\"[0],[1],[2],[3],[4],[5],[6],[7],[8],[9],[10],[11],[20],[21],[24],[25],[26],[27],[28],[30],[31],[33],[35],[37],[39],[40],[41],[43],[44],[54],[55],[57],[58],[59],[60],[61]\",\"userName\":\"Eugene.Arthur\",\"ClientId\":\"6nJWrGZw30\",\"user_id\":\"9000000000000490\",\"pageNumber\":1,\"rowsPerPage\":1,\"trans_code\":\"\",\"bank_code\":\"\",\"trans_status\":\"\",\"roleList\":[\"2\"],\"channel\":\"91\",\"bank\":\"\",\"biller\":\"\",\"transId\":\"\",\"bankid\":\"\",\"from_source\":\"\",\"card_num\":\"\",\"optionType\":\"Test\",\"wcMerchant\":\"\",\"destination\":\"\",\"lineType\":\"91\",\"trans_channel\":\"\",\"pan\":\"\",\"terminal_id\":\"\",\"card_account\":\"\",\"cop_issuercode\":\"\",\"cop_role_id\":\"\",\"cop_company_id\":\"\",\"mobile_no\":\"233542023469\",\"phone\":\"\",\"type\":\"\",\"amount\":\"\",\"amount2\":\"\",\"refundStatus\":\"\",\"partialReversal\":\"\",\"reversalReason\":\"\",\"ipAddress\":\"10.0.1.91\",\"userData\":\"\",\"appId\":[\"91\"],\"company\":\"\",\"origin\":\"\",\"original_respcode\":\"\",\"updated_respcode\":\"\",\"initiated_by\":\"\",\"initiated_date\":\"\",\"authorised_by\":\"\",\"authorised_date\":\"\",\"originalReference\":\"\",\"client\":\"\",\"vastype\":\"\",\"account\":\"\",\"data\":\"\",\"otherInfo\":\"\",\"branch\":\"ALL\",\"ticketNumber\":\"\",\"nlaReference\":\"\",\"etzReference\":\"\",\"newCustomerId\":\"\",\"reason\":\"\",\"date_whitelisted\":\"\",\"date_blacklisted\":\"\",\"accountType\":\"\",\"clientRef\":\"\",\"options\":{}}";
        String json = "{\"startDate\":\"2023-01-18 00:00\",\"endDate\":\"2023-01-19 23:59\",\"merchant\":\"\",\"product\":\"\",\"accountNumber\":\"\",\"status\":\"ALL\",\"service\":\"searchLog\",\"action\":\"all\",\"apiSecret\":\"\",\"userCode\":\"[2000000000000060]|005,[2500000000000050]|91,[2500000000000056]|2,[2500000000000057],[2500000000000058]|2,[2500000000000059]|2,[51]|3\",\"admin\":\"\",\"cardSchemeNumbers\":\"\",\"type_desc\":\"\",\"role_id\":\"3\",\"type_id\":\"[4],[5],[6],[7],[20],[24],[39],[40],[54],[55],[57],[58],[59],[60],[61],[62],[63],[66]\",\"userName\":\"ernest.nunoo\",\"ClientId\":\"\",\"user_id\":\"10500000000000774\",\"pageNumber\":1,\"rowsPerPage\":1,\"trans_code\":\"initiated_date\",\"bank_code\":\"005\",\"trans_status\":\"\",\"roleList\":[],\"bank\":\"\",\"biller\":\"\",\"transId\":\"\",\"bankid\":\"\",\"from_source\":\"\",\"optionType\":\"\",\"wcMerchant\":\"\",\"destination\":\"\",\"lineType\":\"\",\"trans_channel\":\"\",\"pan\":\"\",\"terminal_id\":\"\",\"card_account\":\"\",\"cop_issuercode\":\"\",\"cop_role_id\":\"\",\"cop_company_id\":\"\",\"mobile_no\":\"233241954093\",\"phone\":\"\",\"type\":\"\",\"amount\":\"\",\"amount2\":\"\",\"refundStatus\":\"\",\"partialReversal\":\"\",\"reversalReason\":\"\",\"ipAddress\":\"172.18.10.113\",\"userData\":\"\",\"appId\":[\"91\"],\"company\":\"\",\"origin\":\"\",\"original_respcode\":\"\",\"updated_respcode\":\"\",\"initiated_by\":\"\",\"initiated_date\":\"\",\"authorised_by\":\"\",\"authorised_date\":\"\",\"originalReference\":\"\",\"client\":\"\",\"vastype\":\"\",\"account\":\"\",\"data\":\"\",\"otherInfo\":\"\",\"branch\":\"\",\"ticketNumber\":\"\",\"nlaReference\":\"\",\"etzReference\":\"\",\"newCustomerId\":\"\",\"reason\":\"\",\"date_whitelisted\":\"\",\"date_blacklisted\":\"\",\"accountType\":\"\",\"clientRef\":\"\",\"destinationAccount\":\"\",\"destinationBank\":\"\",\"narration\":\"\",\"sourceCard\":\"\",\"transactionReference\":\"\",\"pin\":\"\",\"network\":\"\",\"reference\":\"\",\"extReference\":\"\",\"destacct\":\"\",\"transNumber\":\"\",\"email\":\"\",\"idNumber\":\"\",\"can\":\"\",\"transactionStatus\":\"\",\"transactions\":\"\",\"options\":{}}";
        log.info("RESP: " + new MobileAppDao().getMobileAppActivateLog(json));

    }

    static {
        bankDB.put("004", "GCB");
        bankDB.put("021", "BOA");
        bankDB.put("920", "ABII");
        bankDB.put("905", "BESTPOINT");
        bankDB.put("005", "NIB");

        bankName.put("004", "GCB");
        bankName.put("021", "BOA");
        bankName.put("920", "ABII");
        bankName.put("905", "BEST");
        bankName.put("005", "NIB");
        List<String> u = new ArrayList<>();
        if (!config.getProperty("USSDGATE_APPS").trim().isEmpty()) {
            u = Arrays.asList(config.getProperty("USSDGATE_APPS").trim().split("\\s*,\\s*"));
        }
        ussdGateBanks = u;
        getAppIdBank();
        getBanks();

        transCatList = getTransactionCategories();
        bankApplicationImagePath = config.getProperty("BANK_APPLICATION_IMAGE_PATH");
        mobgateIV = config.getProperty("MOBGATE_IV");
        mobgateKEY = config.getProperty("MOBGATE_KEY");
        ussdGateKEY = config.getProperty("USSDGATE_KEY");
        ussdGateIV = config.getProperty("USSDGATE_IV");

        String[] bank_names_props = config.getProperty("BANK_NAMES").split("\\|");
        BANK_NAME_MAP = new HashMap<>();
        for (String bank_prop : bank_names_props) {
            String[] a = bank_prop.split("=");
            BANK_NAME_MAP.put(a[0].trim(), a[1].trim());
        }

        MOBILEAPP_STATUS = new HashMap<>();
        MOBILEAPP_STATUS.put("inactive", "0");
        MOBILEAPP_STATUS.put("active", "1");
        MOBILEAPP_STATUS.put("pending_activation", "2");
        MOBILEAPP_STATUS.put("blocked", "3");
        MOBILEAPP_STATUS.put("change_pin", "4");
        MOBILEAPP_STATUS.put("password_tries_exceeded", "5");

    }

    public List<NODES> getCatList() {
        return transCatList;
    }

    public List<String> useNMOtpList() {
        return Arrays.asList(StringUtils.substringBeforeLast(portalSettings.getSetting("nm_otp_banks"), ":").split(","));
    }

    public static void getAppIdBank() {
        String qry = "";

        List<Object[]> resp = new ArrayList<>();
        qry = "SELECT id, bank_code FROM mobiledb.m_mobileapp_properties";

        Session session = DbHibernate.getSession("MOBILEDBMYSQL");
        try {
            Query q = session.createNativeQuery(qry);

            resp = q.getResultList();

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }

        for (final Object[] record : resp) {
            try {
                bankAppIdList.put(record[0].toString(), record[1].toString());
                appIdBankList.put(record[1].toString(), record[0].toString());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }

        }

    }

    public List<ECARDHOLDER> getMobileAppActivate(String jsonString) {

        log.info("mobileApp  request received >> " + jsonString);
        Gson j = new Gson();
        ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);
        String usercode = apiData.getUserCode();
        String phone = apiData.getMobile_no() == null ? "" : apiData.getMobile_no().trim();
        String start_dt = apiData.getStartDate();
        String end_dt = apiData.getEndDate();
        String type = apiData.getTransType();
        String status = apiData.getStatus();
        List<String> appId = apiData.getAppId();
        String service = apiData.getService();
        String qry = "";

        if (type == null) {
            type = "";
        }

        if (!phone.isEmpty() && phone.startsWith("0") && phone.length() == 10) {
            phone = "233" + phone.substring(1);
        }
        String mobileno = phone;

        boolean newUssdGate = CollectionUtils.containsAny(ussdGateBanks, appId);
        if (newUssdGate) {
            qry = "select id, mobile_no, '' account, u_sess, if(active = 0, 'false', 'true') active, created, modified, app_name, card_number, app_id, bank_code \n"
                    + "from mobiledb.mobile_profile where 1=1 and profile_type in (1,3) "
                    + ((type.equalsIgnoreCase("status") || type.isEmpty()) ? ""
                    : (type.equals("created") ? " and created between :start_dt and :end_dt "
                    : " and modified between :start_dt and :end_dt "))
                    + (!appId.contains("ALL") ? " and app_id in(:appId) " : "")
                    + (!mobileno.isEmpty() ? " and mobile_no = :mobileno " : "")
                    + (!status.equalsIgnoreCase("all") ? " and active = :status " : "")
                    + (type.equals("created") ? " order by created desc " : " order by modified desc");
        } else {
            qry = "select b.id, c.dest_mobile, b.alias account,b.request_byip, if(b.active = 0, 'false', 'true') active,b.created, b.modified, d.appid appname, b.card_number, d.id app_id, d.bank_code \n"
                    + "from mobiledb.M_mobile_subscriber a,mobiledb.m_mobile_subscriber_card b, mobiledb.m_mobile_devices c, mobiledb.m_mobileapp_properties d \n"
                    + "where a.id=b.subscriber_id and c.src_mobile=a.mobile_no and a.device_id = c.id and d.id = a.appid "
                    + ((type.equalsIgnoreCase("status") || type.isEmpty()) ? ""
                    : (type.equals("created") ? " and a.created between :start_dt and :end_dt "
                    : " and b.modified between :start_dt and :end_dt "))
                    + (!appId.contains("ALL") ? " and a.appid in(:appId) " : "")
                    + (!mobileno.isEmpty() ? " and c.dest_mobile = :mobileno " : "")
                    + (!status.equalsIgnoreCase("all") ? " and b.active = :status " : "")
                    + (type.equals("created") ? " order by a.created desc " : " order by b.modified desc");
        }

        Session session = DbHibernate.getSession("MOBILEDBMYSQL");
        List<Object[]> resp = new ArrayList<>();
        try {
            log.info("QUERY: " + qry);
            Query q = session.createNativeQuery(qry);

            if (!(type.equalsIgnoreCase("status") || type.isEmpty())) {
                q.setParameter("start_dt", start_dt)
                        .setParameter("end_dt", end_dt);
            }
            if (qry.contains(":start_dt ")) {
                q.setParameter("start_dt", start_dt)
                        .setParameter("end_dt", end_dt);
            }
            if (!mobileno.isEmpty()) {
                q.setParameter("mobileno", mobileno);
            }
            if (!status.equalsIgnoreCase("all")) {
                q.setParameter("status", status.equalsIgnoreCase("ACTIVE") ? 1 : 0);
            }

            if (!appId.contains("ALL")) {
                q.setParameter("appId", appId);
            }
            resp = q.getResultList();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }

        records = new ArrayList<>();

        resp.stream().map((Object[] record) -> {
            ECARDHOLDER ma = new ECARDHOLDER();
            String mobile_no = checkNull(record[1]);

            try {
                ma.setClient_ID(CryptographerMin.encodeId(checkNullNumber(record[0])));
                ma.setPhone(checkNull(record[1]));
                ma.setAppName(checkNull(record[7]));

                ma.setAppId(checkNull(record[9]));
                ma.setIssuer_code(checkNull(record[10]));

//                if (!mobile_no.isEmpty() && newUssdGate && service.equals("search")) {
//                    try {
//
//                        String acc = new AutoRequest().getAPL(mobile_no,
//                                bankAppIdList.getOrDefault(record[9].toString(), ""));
//
//                        String card = checkNull(record[8]);
//
//                        card = CryptographerMin.cryptPan(card, 2);
//
//                        HashMap<String, String> map = new HashMap<>();
//                        String[] acct = acc.split("[|]");
//                        for (String acct1 : acct) {
//                            String[] first = acct1.split("~", -1);
//                            String cardNum = first[0];
//                            String maskedAccount = first[1];
//
//                            map.put(cardNum, String.format("%s", maskedAccount));
//                        }
//                        ma.setCard_num(map.getOrDefault(card, ""));
//                    } catch (Exception e) {
//                        log.error(e.getMessage(), e);
//                    }
//                } else {
//                    ma.setCard_num(checkNull(record[2]));
//                }
                ma.setCard_num(checkNull(record[8]));

                ma.setFirstname(checkNull(record[3]));

                ma.setCreated(checkNull(record[5]));
                ma.setModified(checkNull(record[6]));
                ma.setActive(checkNull(record[4]));
            } catch (Exception ex) {
                log.error(ex.getMessage(), ex);
            }
            return ma;
        }).forEachOrdered((ma) -> {
            records.add(ma);
        });

        return records;
    }

    public List<MobileActivateLog> getMobileAppActivateLog(String jsonString) {

        log.info("getMobileAppActivateLog request received >> " + jsonString);
        Gson j = new Gson();
        ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);
        String usercode = apiData.getUserCode();
        String mobileno = apiData.getMobile_no();
        String start_dt = apiData.getStartDate();
        String end_dt = apiData.getEndDate();
        String type = apiData.getTransType();
        String status = apiData.getStatus();
        String action = apiData.getAction();
        String typeId = apiData.getType_id();
        String sort_by = apiData.getTrans_code();
        String branch = apiData.getBranch();
        String service = apiData.getService();
        String searchKey = apiData.getSearchKey();

        List<String> appId = apiData.getAppId();
        List<MobileActivateLog> mobileLog = new ArrayList<>();
        String qry = "";

        if (type == null) {
            type = "";
        }
        if (mobileno == null) {
            mobileno = "";
        }
        if (action == null) {
            action = "";
        }

        if (sort_by == null) {
            sort_by = "initiated_date";
        }

        if (!mobileno.isEmpty() && mobileno.startsWith("0") && mobileno.length() == 10) {
            mobileno = "233" + mobileno.substring(1);
        }

        if (service.equals("searchLogWithPhone")) {
            if (!searchKey.isEmpty()) {
                if (searchKey.startsWith("0") && searchKey.length() == 10) {
                    mobileno = "233" + searchKey.substring(1);
                } else {
                    mobileno = searchKey;
                }
            } else {
                return mobileLog;
            }
        }

        qry = "SELECT a.phone, b.appId, a.account, a.alias,  a.action, a.initiated_by, a.initiated_date, a.status, a.authorized_by, a.authorized_date, b.id, a.reason, a.account_change, a.branch_code FROM mobiledb.account_activity_log a "
                + "LEFT JOIN mobiledb.m_mobileapp_properties b on a.appid = b.id  where 1=1 "
                + (status.isEmpty() || status.equalsIgnoreCase("all") ? "" : " and a.status = :status ")
                + (service.equals("searchLogWithPhone") ? "" : (!sort_by.equalsIgnoreCase("initiated_date") ? " and a.authorized_date between :start_dt and :end_dt "
                : " and a.initiated_date between :start_dt and :end_dt "))
                + (!mobileno.isEmpty() ? " and a.phone = :mobileno " : "")
                + (action.isEmpty() || action.equalsIgnoreCase("all") ? "" : " and a.action = :action ")
                + (!appId.contains("ALL") ? " and a.appId in (:appId) " : "")
                + (!(branch.equalsIgnoreCase("ALL") || branch.isEmpty()) ? " and a.branch_code in (:branch) " : "")
                + (!sort_by.equalsIgnoreCase("initiated_date") ? " order by a.authorized_date desc"
                : " order by a.initiated_date desc");

        Session session = DbHibernate.getSession("MOBILEDBMYSQL");
        List<Object[]> resp = new ArrayList<>();
        try {
            Query q = session.createNativeQuery(qry);

            if (!service.equals("searchLogWithPhone")) {
                q.setParameter("start_dt", start_dt)
                        .setParameter("end_dt", end_dt);
            }

            if (!mobileno.isEmpty()) {
                q.setParameter("mobileno", mobileno);
            }
            if (!(action.isEmpty() || action.equalsIgnoreCase("all"))) {
                q.setParameter("action", action);
            }
            if (!(status.isEmpty() || status.equalsIgnoreCase("all"))) {
                q.setParameter("status", status);
            }
            if (!appId.contains("ALL")) {

                q.setParameter("appId", appId);
            }
            if (!(branch.equalsIgnoreCase("ALL") || branch.isEmpty())) {
                q.setParameter("branch", branch);
            }
//            log.info("QUERY ::: " + qry);
            resp = q.getResultList();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }

        for (Object[] record : resp) {
            MobileActivateLog ma = new MobileActivateLog();
            try {
                ma.setMobile_number(checkNull(record[0]));
                ma.setApp_name(checkNull(record[1]));
                ma.setApp_id(checkNull(record[10]));
                ma.setAccount_id(CryptographerMin.encodeId(checkNullNumber(record[2])));
                ma.setAccount(checkNull(record[3]));
                ma.setBranch(checkNull(record[13]));
                ma.setAction(checkNull(record[4]));
                ma.setAccount_change(checkNull(record[12]));
                ma.setReason(checkNull(record[11]));
                ma.setInitiated_by(checkNull(record[5]));
                ma.setInitiated_date(checkNull(record[6]));
                ma.setStatus(checkNull(record[7]));
                ma.setAuthorized_by(checkNull(record[8]));
                ma.setAuthorized_date(checkNull(record[9]));
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            mobileLog.add(ma);
        }
        return mobileLog;
    }

    public List<MobileAppUserLog> getMobileAppUserLog(String jsonString) throws ParseException {

        log.info("getMobileAppUserLog request received >> " + jsonString);
        Gson j = new Gson();
        ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);
        String usercode = apiData.getUserCode();
        String mobileno = apiData.getMobile_no();
        String start_dt = apiData.getStartDate();
        String end_dt = apiData.getEndDate();
        String type = apiData.getTransType();
        String status = apiData.getStatus();
        String action = apiData.getAction();
        String bankCode = apiData.getBank_code();
        String branch = apiData.getBranch();

        String typeId = apiData.getType_id();
        String sort_by = apiData.getTrans_code();
        List<String> appId = apiData.getAppId();
        List<MobileAppUserLog> mobileLog = new ArrayList<>();
        String qry = "";

        if (type == null) {
            type = "";
        }
        if (mobileno == null) {
            mobileno = "";
        }
        if (action == null) {
            action = "";
        }

        if (sort_by == null) {
            sort_by = "initiated_date";
        }

        if (!mobileno.isEmpty() && mobileno.startsWith("0") && mobileno.length() == 10) {
            mobileno = "233" + mobileno.substring(1);
        }

        qry = "select  a.bank_code, a.phone, a.account, a.action, a.initiated_by, a.initiated_date, a.reason, a.status, a.authorized_by, a.authorized_date, b.id  FROM mobiledb.mobileapp_activity_log a left join mobiledb.m_mobileapp_properties b on a.bank_code = b.bank_code where 1=1 "
                + (status.isEmpty() || status.equalsIgnoreCase("all") ? "" : " and a.status = :status ")
                + (!branch.equalsIgnoreCase("ALL") ? " and a.branch_code in (:branch) " : "")
                + (!sort_by.equalsIgnoreCase("initiated_date") ? " and a.authorized_date between :start_dt and :end_dt "
                : " and a.initiated_date between :start_dt and :end_dt ")
                + (!mobileno.isEmpty() ? " and a.phone = :mobileno " : "")
                + (action.isEmpty() || action.equalsIgnoreCase("all") ? "" : " and a.action = :action ")
                + (!(bankCode.isEmpty() || bankCode.equalsIgnoreCase("ALL")) ? " and a.bank_code in(:bankCode) " : "")
                + (!sort_by.equalsIgnoreCase("initiated_date") ? " order by a.authorized_date desc"
                : " order by a.initiated_date desc");

        Session session = DbHibernate.getSession("MOBILEDBMYSQL");

        List<Object[]> resp = new ArrayList<>();
        try {
            Query q = session.createNativeQuery(qry);

            q.setParameter("start_dt", start_dt)
                    .setParameter("end_dt", end_dt);

            if (!mobileno.isEmpty()) {
                q.setParameter("mobileno", mobileno);
            }
            if (!(action.isEmpty() || action.equalsIgnoreCase("all"))) {
                q.setParameter("action", action);
            }
            if (!(status.isEmpty() || status.equalsIgnoreCase("all"))) {
                q.setParameter("status", status);
            }
            if (!bankCode.equalsIgnoreCase("ALL")) {
                q.setParameter("bankCode", bankCode);
            }
            if (!branch.equalsIgnoreCase("ALL")) {
                q.setParameter("branch", branch);
            }

            resp = q.getResultList();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }

        for (Object[] record : resp) {
            MobileAppUserLog ma = new MobileAppUserLog();
            try {
                ma.setMobileNo(checkNull(record[1]));
                String b = checkNull(record[0]);
                ma.setBank(bankList.getOrDefault(b, b));
                ma.setBankCode(b);
                ma.setCustomerId(checkNull(record[2]));
                ma.setAction(checkNull(record[3]));
                ma.setInitiatedBy(checkNull(record[4]));
                ma.setInitiatedDate(checkNull(record[5]));
                ma.setStatus(checkNull(record[7]));
                ma.setAuthorizedBy(checkNull(record[8]));
                ma.setAuthorizedDate(checkNull(record[9]));
                ma.setAppId(checkNull(record[10]));

            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            mobileLog.add(ma);
        }
        return mobileLog;
    }

    public List<AccountRequest> getMobileAppAccountRequestsLog(String jsonString) throws ParseException {

        log.info("mobileApp Account request request received >> " + jsonString);
        Gson j = new Gson();
        ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);

        String mobileno = apiData.getMobile_no();
        String start_dt = apiData.getStartDate();
        String end_dt = apiData.getEndDate();
        String type = apiData.getTransType();
        String status = apiData.getStatus();
        String action = apiData.getAction();
        String bankCode = apiData.getBank_code();
        String branch = apiData.getBranch();

        String sort_by = apiData.getTrans_code();

        List<AccountRequest> accountRequestLog = new ArrayList<>();
        String qry = "";

        if (type == null) {
            type = "";
        }
        if (mobileno == null) {
            mobileno = "";
        }
        if (action == null) {
            action = "";
        }
        if (bankCode == null) {
            bankCode = "";
        }

        if (sort_by == null) {
            sort_by = "initiated_date";
        }

        if (!mobileno.isEmpty() && mobileno.startsWith("0") && mobileno.length() == 10) {
            mobileno = "233" + mobileno.substring(1);
        }

        qry = "select reference, msisdn, bankCode, account, type, extraInfo, status, createdAt, processedBy, processedAt from mobiledb.mobgate_bankrequests where 1=1 "
                + (bankCode.isEmpty() || bankCode.equalsIgnoreCase("000") ? "" : " and bankCode = :bankCode ")
                + (!branch.equalsIgnoreCase("ALL") ? " and branchCode in (:branch) " : "")
                + (status.isEmpty() || status.equalsIgnoreCase("all") ? "" : " and status = :status ")
                + (!sort_by.equalsIgnoreCase("initiated_date") ? " and processedAt between :start_dt and :end_dt "
                : " and createdAt between :start_dt and :end_dt ")
                + (!mobileno.isEmpty() ? " and msisdn = :msisdn " : "")
                + (action.isEmpty() || action.equalsIgnoreCase("all") ? "" : " and type = :action ")
                + (!sort_by.equalsIgnoreCase("initiated_date") ? " order by processedAt desc"
                : " order by createdAt desc");

        Session session = DbHibernate.getSession("MOBILEDBMYSQL");
        List<Object[]> resp = new ArrayList<>();
        try {
            Query q = session.createNativeQuery(qry);

            q.setParameter("start_dt", start_dt)
                    .setParameter("end_dt", end_dt);

            if (!(bankCode.isEmpty() || bankCode.equalsIgnoreCase("000"))) {
                q.setParameter("bankCode", bankCode);
            }
            if (!mobileno.isEmpty()) {
                q.setParameter("msisdn", mobileno);
            }
            if (!(action.isEmpty() || action.equalsIgnoreCase("all"))) {
                q.setParameter("action", action);
            }
            if (!(status.isEmpty() || status.equalsIgnoreCase("all"))) {
                q.setParameter("status", status);
            }
            if (!branch.equalsIgnoreCase("ALL")) {
                q.setParameter("branch", branch);
            }

            resp = q.getResultList();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }

        for (Object[] record : resp) {
            AccountRequest ar = new AccountRequest();
            try {
                ar.setReference(checkNull(record[0]));
                ar.setMsisdn(checkNull(record[1]));

                String bank = checkNull(record[2]);
                ar.setBank(bankList.getOrDefault(bank, bank));
                ar.setBankCode(bank);
                ar.setAccountNumber(checkNull(record[3]));
                ar.setRequestType(checkNull(record[4]));
                ar.setRequestInfo(checkNull(record[5]));
                ar.setStatus(checkNull(record[6]));
                ar.setCreatedAt(checkNull(record[7]));
                ar.setProcessedBy(checkNull(record[8]));
                ar.setProcessedAt(checkNull(record[9]));
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            accountRequestLog.add(ar);
        }
        return accountRequestLog;
    }

    public List<CustomerFeedback> getMobileAppFeedbackLog(String jsonString) throws ParseException {

        log.info("mobileApp Feedback request received >> " + jsonString);
        Gson j = new Gson();
        ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);

        String mobileno = apiData.getMobile_no();
        String start_dt = apiData.getStartDate();
        String end_dt = apiData.getEndDate();
        String type = apiData.getTransType();
        String status = apiData.getStatus();
        String action = apiData.getAction();
        String bankCode = apiData.getBank_code();
        String typeId = apiData.getType_id();

        String sort_by = apiData.getTrans_code();

        List<CustomerFeedback> feedbackLog = new ArrayList<>();
        String qry = "";

        if (type == null) {
            type = "";
        }
        if (mobileno == null) {
            mobileno = "";
        }
        if (action == null) {
            action = "";
        }
        if (bankCode == null) {
            bankCode = "";
        }

        if (sort_by == null) {
            sort_by = "initiated_date";
        }

        if (!mobileno.isEmpty() && mobileno.startsWith("0") && mobileno.length() == 10) {
            mobileno = "233" + mobileno.substring(1);
        }

        qry = "select reference, msisdn, bankCode, feedback, createdAt, account, name from mobiledb.mobgate_feedback where 1=1 "
                + (bankCode.isEmpty() || bankCode.equalsIgnoreCase("ALL") ? "" : " and bankCode = :bankCode ")
                + (!mobileno.isEmpty() ? " and msisdn = :msisdn " : "")
                + " and createdAt between :start_dt and :end_dt order by createdAt desc ";

        Session session = DbHibernate.getSession("MOBILEDBMYSQL");
        List<Object[]> resp = new ArrayList<>();
        try {
            Query q = session.createNativeQuery(qry);
            log.info("QUERY: " + qry);

            q.setParameter("start_dt", start_dt)
                    .setParameter("end_dt", end_dt);

            if (!(bankCode.isEmpty() || bankCode.equalsIgnoreCase("ALL"))) {
                q.setParameter("bankCode", bankCode);
            }
            if (!mobileno.isEmpty()) {
                q.setParameter("msisdn", mobileno);
            }

            resp = q.getResultList();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }

        for (Object[] record : resp) {
            CustomerFeedback cf = new CustomerFeedback();
            try {
                cf.setReference(checkNull(record[0]));
                cf.setPhoneNumber(checkNull(record[1]));
                cf.setCustomerName(checkNull(record[6]));
                cf.setAccountNumber(checkNull(record[5]));
                String b = checkNull(record[2]);
                if (typeId.contains("[0]")) {
                    cf.setBank(bankList.getOrDefault(b, b));
                }
                cf.setFeedback(checkNull(record[3]));
                cf.setFeedbackDate(checkNull(record[4]));
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            feedbackLog.add(cf);
        }
        return feedbackLog;
    }

    public List<MobileAppUser> getMobileAppUsers(String jsonString) throws ParseException {

        log.info("mobileApp Users request received >> " + jsonString);
        Gson j = new Gson();
        ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);

        String mobileno = apiData.getMobile_no();
        String start_dt = apiData.getStartDate();
        String end_dt = apiData.getEndDate();
        String type = apiData.getTransType();
        String status = apiData.getStatus();
        String action = apiData.getAction();
        String bankCode = apiData.getBank_code();
        String searchKey = apiData.getSearchKey();
        String service = apiData.getService();

        String sort_by = apiData.getTrans_code();

        List<MobileAppUser> maUserLog = new ArrayList<>();
        String qry = "";

        if (type == null) {
            type = "";
        }
        if (mobileno == null) {
            mobileno = "";
        }
        if (action == null) {
            action = "";
        }
        if (bankCode == null) {
            bankCode = "";
        }

        if (sort_by == null) {
            sort_by = "created_at";
        }
        if (status == null) {
            status = "ALL";
        } else {
            status = MOBILEAPP_STATUS.getOrDefault(status.toLowerCase(), "ALL");
        }

        if (!mobileno.isEmpty() && mobileno.startsWith("0") && mobileno.length() == 10) {
            mobileno = "233" + mobileno.substring(1);
        }
        if (service.equals("mobileAppUsersSearch")) {
            if (!searchKey.isEmpty()) {
                if (searchKey.startsWith("0") && searchKey.length() == 10) {
                    mobileno = "233" + searchKey.substring(1);
                } else {
                    mobileno = searchKey;
                }
            } else {
                return maUserLog;
            }
        }

        switch (sort_by) {
            case "created_at":
                sort_by = " and a.created between :start_dt and :end_dt ";
                break;
            case "modified_at":
                sort_by = " and a.modified between :start_dt and :end_dt ";
                break;
            case "last_login":
                sort_by = " and last_login_time between :start_dt and :end_dt ";
                break;
            case "last_activity":
                sort_by = " and last_activity_time between :start_dt and :end_dt ";
                break;
            default:
                sort_by = " and a.created between :start_dt and :end_dt ";
                break;
        }

        qry = "SELECT a.customer_id, a.mobile_no, a.email, a.bank_code, (case when a.status = 1 then 'ACTIVE' when a.status = 2 then 'PENDING_ACTIVATION' when a.status = 3 then 'BLOCKED' when a.status = 4 then 'CHANGE_PIN' when a.status = 5 then 'PASSWORD_TRIES_EXCEED' else 'INACTIVE' end) status, a.created, a.modified, a.last_activity_time, a.last_login_time, b.id FROM mobiledb.mobgate_profile a left join mobiledb.m_mobileapp_properties b on a.bank_code = b.bank_code where 1=1 "
                + (bankCode.isEmpty() || bankCode.equalsIgnoreCase("ALL") ? "" : " and a.bank_code = :bankCode ")
                + (status.equalsIgnoreCase("all") ? "" : " and a.status = :status ")
                + (!mobileno.isEmpty() ? " and a.mobile_no = :msisdn " : "")
                + (!service.equals("mobileAppUsersSearch") ? sort_by : "")
                + " order by a.created desc ";

        Session session = DbHibernate.getSession("MOBILEDBMYSQL");
        List<Object[]> resp = new ArrayList<>();
        try {
            Query q = session.createNativeQuery(qry);

            if (!service.equals("mobileAppUsersSearch")) {
                q.setParameter("start_dt", start_dt)
                        .setParameter("end_dt", end_dt);
            }

            if (!(bankCode.isEmpty() || bankCode.equalsIgnoreCase("ALL"))) {
                q.setParameter("bankCode", bankCode);
            }
            if (!mobileno.isEmpty()) {
                q.setParameter("msisdn", mobileno);
            }
            if (!status.equalsIgnoreCase("ALL")) {
                q.setParameter("status", status);
            }

            resp = q.getResultList();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }

        for (Object[] record : resp) {
            MobileAppUser ma = new MobileAppUser();
            try {
                ma.setCustomerId(checkNull(record[0]));
                ma.setMobileNo(checkNull(record[1]));
                ma.setEmail(checkNull(record[2]));
                String b = checkNull(record[3]);
                ma.setBankCode(b);
                ma.setBank(bankList.getOrDefault(b, b));
                ma.setStatus(checkNull(record[4]));
                ma.setDateCreated(checkNull(record[5]));
                ma.setDateModified(checkNull(record[6]));
                ma.setLastActivityTime(checkNull(record[7]));
                ma.setLastLoginTime(checkNull(record[8]));
                ma.setAppId(checkNull(record[9]));

            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            maUserLog.add(ma);
        }
        return maUserLog;
    }

    public List<MobileAppTransaction> getMobileAppTransactions(String jsonString) throws ParseException {

        log.info("mobileApp Transactions request received >> " + jsonString);
        Gson j = new Gson();
        ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);

        String mobileno = apiData.getMobile_no();
        String start_dt = apiData.getStartDate();
        String end_dt = apiData.getEndDate();
        String type = apiData.getTransType();

        String status = apiData.getStatus();
        String action = apiData.getAction();
        String bankCode = apiData.getBank_code();

        String trans_code = apiData.getTrans_code();
        String service = apiData.getService();
        String searchKey = apiData.getSearchKey();
        String uniqueTransid = apiData.getUniqueTransId();

        String typeId = apiData.getType_id();
        List<MobileAppTransaction> transactionLog = new ArrayList<>();
        String qry = "";

        if (type == null) {
            type = "";
        }
        if (mobileno == null) {
            mobileno = "";
        }
        if (action == null) {
            action = "";
        }
        if (uniqueTransid == null) {
            uniqueTransid = "";
        }
        if (bankCode == null) {
            bankCode = "";
        }

        if (trans_code == null) {
            trans_code = "";
        }
        if (service.equalsIgnoreCase("mobileAppTransactionsSearch") && searchKey.trim().isEmpty()) {
            return transactionLog;
        }
        if (!mobileno.isEmpty() && mobileno.startsWith("0") && mobileno.length() == 10) {
            mobileno = "233" + mobileno.substring(1);
        }

        qry = "select reference, a.bankCode, sourceBankCode, source, msisdn, (case when txnTypeId in(5,8) then vasaccount when a.vasType ='qrpayment' then terminalId else target end)  target, "
                + " a.vasType, vasaccount,vasCode, a.gipCode, beneficiaryName, amount, b.pageTitle txnTypeId, c.description bankId, fees, total, status, statusMessage, "
                + " a.createdAt, a.statusCode from mobiledb.mobgate_transactions a left join mobiledb.mobgate_menulist b on a.txnTypeId = b.id left join mobiledb.mobgate_menulist_items c on a.bankId = c.id where 1=1 "
                + (!service.equalsIgnoreCase("mobileAppTransactionsSearch")
                ? " and a.createdAt between :start_dt and :end_dt "
                : " and a.reference = :searchKey ")
                + (!uniqueTransid.isEmpty() ? " and a.reference = :uniqueTransid " : "")
                + (bankCode.isEmpty() || bankCode.equalsIgnoreCase("ALL") ? "" : " and a.bankCode = :bankCode ")
                + (status.isEmpty() || status.equalsIgnoreCase("all") ? "" : " and a.status = :status ")
                + ((trans_code.isEmpty() || trans_code.equalsIgnoreCase("all")) ? "" : " and a.bankId = :bankId ")
                + ((type.isEmpty() || type.equalsIgnoreCase("all")) ? "" : " and a.txnTypeId = :txnTypeId ")
                + (!mobileno.isEmpty() ? " and a.msisdn = :msisdn " : "")
                + "order by a.createdAt desc ";

        Session session = DbHibernate.getSession("MOBILEDBMYSQL");
        List<Object[]> resp = new ArrayList<>();
        try {
            Query q = session.createNativeQuery(qry);

            if (!(status.isEmpty() || status.equalsIgnoreCase("all"))) {
                q.setParameter("status", status);
            }

            if (!service.equalsIgnoreCase("mobileAppTransactionsSearch")) {
                q.setParameter("start_dt", (Object) start_dt)
                        .setParameter("end_dt", (Object) end_dt);
            } else {
                q.setParameter("searchKey", (Object) searchKey.trim());
            }
            if (!(bankCode.isEmpty() || bankCode.equalsIgnoreCase("ALL"))) {
                q.setParameter("bankCode", bankCode);
            }
            if (!mobileno.isEmpty()) {
                q.setParameter("msisdn", mobileno);
            }
            if (!uniqueTransid.isEmpty()) {
                q.setParameter("uniqueTransid", uniqueTransid);
            }
            if (!(trans_code.isEmpty() || trans_code.equalsIgnoreCase("all"))) {
                q.setParameter("bankId", trans_code);
            }
            if (!(type.isEmpty() || type.equalsIgnoreCase("all"))) {
                q.setParameter("txnTypeId", type);
            }

            resp = q.getResultList();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }

        for (Object[] record : resp) {
            MobileAppTransaction mat = new MobileAppTransaction();
            try {
                mat.setReference(checkNull(record[0]));
                String b = checkNull(record[1]);
                if (typeId.contains("[0]")) {
                    mat.setBank(bankList.getOrDefault(b, b));
                }
                String c = checkNull(record[2]);
                mat.setSource(bankList.getOrDefault(c, c));

                mat.setSourceNumber(checkNull(record[3]));
                mat.setCustomerNumber(checkNull(record[4]));
                mat.setDestinationNumber(checkNull(record[5]));
                mat.setBeneficiaryName(checkNull(record[10]));

                mat.setAmount(checkNull(record[11]));
                mat.setTransactionCategory(checkNull(record[12]));
                mat.setTransactionType(checkNull(record[13]));
                mat.setFees(checkNull(record[14]));
                mat.setTotal(checkNull(record[15]));
                mat.setStatusCode(checkNull(record[19]));
                mat.setStatus(checkNull(record[16]));
                mat.setStatusMessage(checkNull(record[17]));
                mat.setTransactionDate(checkNull(record[18]));

            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            transactionLog.add(mat);
        }
        return transactionLog;
    }

    public List<FraudReport> getMobileAppFraudReport(String jsonString) throws ParseException {

        log.info("mobileApp FraudReport request received >> " + jsonString);
        Gson j = new Gson();
        ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);

        String mobileno = apiData.getMobile_no();
        String start_dt = apiData.getStartDate();
        String end_dt = apiData.getEndDate();
        String type = apiData.getTransType();
        String status = apiData.getStatus();
        String action = apiData.getAction();
        String bankCode = apiData.getBank_code();

        String sort_by = apiData.getTrans_code();

        List<FraudReport> fraudReportLog = new ArrayList<>();
        String qry = "";

        if (type == null) {
            type = "";
        }
        if (mobileno == null) {
            mobileno = "";
        }
        if (action == null) {
            action = "";
        }
        if (bankCode == null) {
            bankCode = "";
        }

        if (sort_by == null) {
            sort_by = "initiated_date";
        }

        if (!mobileno.isEmpty() && mobileno.startsWith("0") && mobileno.length() == 10) {
            mobileno = "233" + mobileno.substring(1);
        }

        qry = "select reference, msisdn, bankCode, report, createdAt, name, account from mobiledb.mobgate_fraudreport where 1=1 "
                + (bankCode.isEmpty() || bankCode.equalsIgnoreCase("ALL") ? "" : " and bankCode = :bankCode ")
                + (!mobileno.isEmpty() ? " and msisdn = :msisdn " : "")
                + " and createdAt between :start_dt and :end_dt order by createdAt desc ";

        Session session = DbHibernate.getSession("MOBILEDBMYSQL");
        List<Object[]> resp = new ArrayList<>();
        try {
            Query q = session.createNativeQuery(qry);
            log.info("QUERY: " + qry);

            q.setParameter("start_dt", start_dt)
                    .setParameter("end_dt", end_dt);

            if (!(bankCode.isEmpty() || bankCode.equalsIgnoreCase("ALL"))) {
                q.setParameter("bankCode", bankCode);
            }
            if (!mobileno.isEmpty()) {
                q.setParameter("msisdn", mobileno);
            }

            resp = q.getResultList();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }

        for (Object[] record : resp) {
            FraudReport fr = new FraudReport();
            try {
                fr.setReference(checkNull(record[0]));
                fr.setPhoneNumber(checkNull(record[1]));
                String b = checkNull(record[2]);
                fr.setBank(bankList.getOrDefault(b, b));
                fr.setCustomerName(checkNull(record[5]));
                fr.setAccountNumber(checkNull(record[6]));
                fr.setReport(checkNull(record[3]));
                fr.setReportDate(checkNull(record[4]));
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            fraudReportLog.add(fr);
        }
        return fraudReportLog;
    }

    public List<BankApplication> getBankApplicationsLog(String jsonString) throws ParseException {

        log.info("bankApplication request received >> " + jsonString);
        Gson j = new Gson();
        ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);

        String mobileno = apiData.getMobile_no();
        String start_dt = apiData.getStartDate();
        String end_dt = apiData.getEndDate();
        String type = apiData.getTransType();
        String status = apiData.getStatus();
        String action = apiData.getAction();
        String bankCode = apiData.getBank_code();
        String branch = apiData.getBranch();
        String sort_by = apiData.getTrans_code();

        List<BankApplication> bankApplicationLog = new ArrayList<>();
        String qry = "";

        if (type == null) {
            type = "";
        }
        if (mobileno == null) {
            mobileno = "";
        }
        if (action == null) {
            action = "";
        }
        if (bankCode == null) {
            bankCode = "";
        }

        if (sort_by == null) {
            sort_by = "initiated_date";
        }

        if (!mobileno.isEmpty() && mobileno.startsWith("0") && mobileno.length() == 10) {
            mobileno = "233" + mobileno.substring(1);
        }

        qry = "select phoneNumber, bankCode, firstName, lastName, otherName, dateOfBirth, gender, homeAddress, ghPostAddress,idType,idNumber, "
                + "idImage, status, accountType, requestDate, reference, processedBy, processedDate, email, branch from mobiledb.mobgate_bankapplications where 1=1 "
                + (bankCode.isEmpty() || bankCode.equalsIgnoreCase("ALL") ? "" : " and bankCode in (:bankCode) ")
                //                + (!branch.equalsIgnoreCase("ALL") ? " and branchCode in (:branch) " : "")
                + (status.isEmpty() || status.equalsIgnoreCase("all") ? "" : " and status = :status ")
                + (!mobileno.isEmpty() ? " and phoneNumber = :msisdn " : "")
                + " and requestDate between :start_dt and :end_dt "
                + " order by requestDate desc";

        Session session = DbHibernate.getSession("MOBILEDBMYSQL");
        List<Object[]> resp = new ArrayList<>();
        try {
            Query q = session.createNativeQuery(qry);

            if (!status.equalsIgnoreCase("all")) {
                q.setParameter("status", status);
            }

            q.setParameter("start_dt", start_dt)
                    .setParameter("end_dt", end_dt);

            if (!(bankCode.isEmpty() || bankCode.equalsIgnoreCase("ALL"))) {
                q.setParameter("bankCode", bankCode);
            }
            if (!mobileno.isEmpty()) {
                q.setParameter("msisdn", mobileno);
            }
//            if (!branch.equalsIgnoreCase("ALL")) {
//                q.setParameter("branch", branch);
//            }

            resp = q.getResultList();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }

        for (Object[] record : resp) {
            BankApplication ba = new BankApplication();
            try {
                ba.setReference(checkNull(record[15]));
                ba.setPhoneNumber(checkNull(record[0]));
                String b = checkNull(record[1]);
                ba.setBank(bankList.getOrDefault(b, b));
                ba.setBankCode(b);
                ba.setFirstName(checkNull(record[2]));
                ba.setLastName(checkNull(record[3]));
                ba.setOtherNames(checkNull(record[4]));
                ba.setEmail(checkNull(record[18]));
                ba.setClosestBranch(checkNull(record[19]));
                ba.setDob(checkNull(record[5]));
                ba.setGender(checkNull(record[6]));
                ba.setHomeAddress(checkNull(record[7]));
                ba.setGhPostAddress(checkNull(record[8]));
                ba.setIdType(checkNull(record[9]));
                ba.setIdNumber(checkNull(record[10]));
                ba.setIdImage(checkNull(record[11]));
                ba.setStatus(checkNull(record[12]));
                ba.setAccountType(checkNull(record[13]));
                ba.setRequestDate(checkNull(record[14]));
                ba.setProcessedBy(checkNull(record[16]));

                ba.setProcessedDate(checkNull(record[17]));

            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            bankApplicationLog.add(ba);
        }
        return bankApplicationLog;
    }

    public List<BankNewsItem> getNewsItems(String jsonString) throws ParseException {

        log.info("newsItems request received >> " + jsonString);
        Gson j = new Gson();
        ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);

        String start_dt = apiData.getStartDate();
        String end_dt = apiData.getEndDate();

        String status = apiData.getStatus();
        String bankCode = apiData.getBank_code();

        String typeId = apiData.getType_id();
        String trans_code = apiData.getTrans_code();
        List<BankNewsItem> newsItems = new ArrayList<>();

        String qry = "";

        if (bankCode == null) {
            bankCode = "";
        }

        if (trans_code == null) {
            trans_code = "";
        }

        qry = "select id, bank_code, title, message, image, priority, (case when status = 1 then 'ACTIVE' else 'INACTIVE' end) status, created FROM mobiledb.mobgate_newsitems where 1=1 "
                + (!(start_dt == null || end_dt == null) ? " and created between :start_dt and :end_dt " : "")
                + (bankCode.isEmpty() || bankCode.equalsIgnoreCase("ALL") ? "" : " and bank_code = :bank_code ")
                + (status.isEmpty() || status.equalsIgnoreCase("all") ? "" : " and a.status = :status ")
                + ((trans_code.isEmpty() || trans_code.equalsIgnoreCase("all")) ? "" : " and priority = :priority ")
                + "order by created desc ";

        Session session = DbHibernate.getSession("MOBILEDBMYSQL");
        List<Object[]> resp = new ArrayList<>();
        try {
            Query q = session.createNativeQuery(qry);

            if (!(status.isEmpty() || status.equalsIgnoreCase("all"))) {
                q.setParameter("status", status);
            }

            if (!(start_dt == null || end_dt == null)) {
                q.setParameter("start_dt", (Object) start_dt)
                        .setParameter("end_dt", (Object) end_dt);
            }
            if (!(bankCode.isEmpty() || bankCode.equalsIgnoreCase("ALL"))) {
                q.setParameter("bank_code", bankCode);
            }

            if (!(trans_code.isEmpty() || trans_code.equalsIgnoreCase("all"))) {
                q.setParameter("priority", trans_code.trim());
            }

            resp = q.getResultList();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }

        for (Object[] record : resp) {
            BankNewsItem bni = new BankNewsItem();
            try {
                bni.setId(checkNull(record[0]));
                String b = checkNull(record[1]);
                if (typeId.contains("[0]")) {
                    bni.setBank(bankList.getOrDefault(b, b));
                }

                bni.setTitle(checkNull(record[2]));
                bni.setMessage(checkNull(record[3]));
                bni.setImage(checkNull(record[4]));
                bni.setPriority(checkNull(record[5]));

                bni.setStatus(checkNull(record[6]));
                bni.setCreated(checkNull(record[7]));

            } catch (Exception e) {
                log.error("error: ", e);
            }
            newsItems.add(bni);
        }
        return newsItems;
    }

    public String getBankApplicationImage(String jsonString) throws ParseException {

        log.info("bankApplicationImage request received >> " + jsonString);
        Gson j = new Gson();
        ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);

        String reference = apiData.getUniqueTransId();
        String bankCode = apiData.getBank_code();
        String branch = apiData.getBranch();
        String qry = "";

        if (reference == null) {
            reference = "";
        }

        if (reference.trim().isEmpty()) {
            return "";
        }

        String filePath = "";

        qry = "select idImage, reference, processedBy, processedDate from mobiledb.mobgate_bankapplications where 1=1 "
                + " and bankCode in (:bankCode) "
                //                + (!branch.equalsIgnoreCase("ALL") ? " and branchCode in (:branch) " : "")
                + " and reference = :reference "
                + " order by requestDate desc";

        Session session = DbHibernate.getSession("MOBILEDBMYSQL");
        List<Object[]> resp = new ArrayList<>();
        try {
            Query q = session.createNativeQuery(qry);

            q.setParameter("bankCode", bankCode).setParameter("reference", reference);
//            if (!branch.equalsIgnoreCase("ALL")) {
//                q.setParameter("branch", branch);
//            }

            resp = q.getResultList();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }

        for (Object[] record : resp) {
            try {
                filePath = checkNull(record[0]);
                if (!(filePath.equalsIgnoreCase("null") || filePath.trim().isEmpty())) {
                    filePath = bankApplicationImagePath + filePath;

                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        return filePath;
    }

    public List<NODES> getMobileAppIdList(List<String> appId) throws ParseException {

        List<NODES> appIdList = new ArrayList<>();
        String qry = "";

        qry = "SELECT appid name, id FROM mobiledb.m_mobileapp_properties where 1=1 "
                + (!appId.contains("ALL") ? " and id in (:appId) " : "")
                + " order by name asc;";

        Session session = DbHibernate.getSession("MOBILEDBMYSQL");
        try {
            Query q = session.createNativeQuery(qry, NODES.class);

            if (!appId.contains("ALL")) {
                q.setParameter("appId", appId);
            }

            appIdList = q.getResultList();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return appIdList;
    }

    public String modifyCardAccount(String jsonString) {
        log.info("modifyCardAccountrequest received >> " + jsonString);
        Gson j = new Gson();
        ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);
        String action = apiData.getAction();
        String choice = apiData.getStatus() == null ? "" : apiData.getStatus();
        String bankCode = apiData.getBank_code().isEmpty() ? bankAppIdList.getOrDefault(apiData.getLineType(), "")
                : apiData.getBank_code();
        log.info("BAnk: " + bankCode + " -- " + apiData.getLineType());

        String reason = apiData.getOptionType();
        String id = apiData.getClientId();
        apiData.setBank_code(bankCode);
        String account_id = String.valueOf(CryptographerMin.decodeId(id));
        apiData.setClientId(account_id);

        String phone = apiData.getMobile_no();
        String newPhone = apiData.getSubscriberId();

        if (phone == null) {
            phone = "";
        }
        if (newPhone == null) {
            newPhone = "";
        }

        JSONObject resp = new JSONObject();
        String roleList = apiData.getRole_id();
        resp.put("code", "01");
        resp.put("message", choice + " " + action + " failed");

        if (phone.trim().startsWith("0") && phone.trim().length() == 10) {
            phone = "233" + phone.trim().substring(1);
            apiData.setMobile_no(phone);
        }
        if (newPhone.trim().startsWith("0") && newPhone.trim().length() == 10) {
            newPhone = "233" + newPhone.trim().substring(1);
            apiData.setSubscriberId(newPhone);
        }

        switch (action) {
            case "hotlist":
            case "dehotlist":
            case "pinreset":
            case "activate":
            case "deactivate":
            case "enhancement":
            case "number_modification":

                List<String> mcList = new ArrayList<>();
                switch (action) {
                    case "hotlist":
                    case "dehotlist":
                        mcList = Arrays.asList(StringUtils
                                .substringBeforeLast(portalSettings.getSetting("hdaccount_mc"), ":").split(","));
                        break;
                    case "activate":
                    case "deactivate":
                        mcList = Arrays.asList(StringUtils
                                .substringBeforeLast(portalSettings.getSetting("adaccount_mc"), ":").split(","));
                        break;
                    case "number_modification":
                        mcList = Arrays.asList(StringUtils
                                .substringBeforeLast(portalSettings.getSetting("nmaccount_mc"), ":").split(","));

                        break;

                    case "pinreset":
                    case "enhancement":
                        mcList = Arrays.asList(StringUtils
                                .substringBeforeLast(portalSettings.getSetting(action + "_mc"), ":").split(","));
                        break;
                    default:
                        break;
                }

                if (mcList.contains(bankCode) && choice.trim().isEmpty() && !roleList.contains("2")) {
                    resp.put("message", "Maker checker activity. Initiator Required");

                } else {

                    if (roleList.contains("2")) {

                        int logRecord = handleSQLDuplicateErrorAndNoResultFound(phone, action, reason, bankCode,
                                account_id);

                        switch (logRecord) {
                            case 0:

                                resp = processAccountModification(apiData);
                                break;
                            case 1:
                                resp.put("message", "Request exists OR awaiting authorization");
                                break;
                            default:
                                resp.put("message", "An Error Occured");
                                break;
                        }

                    } else if (roleList.contains("1") || roleList.contains("3")) {

                        resp = processAccountModification(apiData);
                    } else {
                        resp.put("message", "Authorization Required");
                    }
                }
                break;

            default:
                break;
        }
        log.info("MES::: " + resp.toString());

        return resp.toString();
    }

    public String modifyMobileAppUser(String jsonString) {
        log.info("modifyMobileAppUser received >> " + jsonString);
        Gson j = new Gson();
        ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);
        String action = apiData.getAction();
        String choice = apiData.getStatus();
        String bankCode = apiData.getBank_code();
        String reason = apiData.getOptionType();
        String id = apiData.getClientId();
        String account_id = id;
        apiData.setClientId(account_id);

        String phone = apiData.getMobile_no();

        JSONObject resp = new JSONObject();
        List<String> roleList = apiData.getRoleList();
        resp.put("code", "01");
        resp.put("message", choice + " " + action + " failed");
        List<String> mcList = new ArrayList<>();
        List<String> spList = new ArrayList<>();

        switch (action) {
            case "activate":
            case "deactivate":
            case "block":
            case "unblock":
            case "resetPassword":
            case "resetProfile":
            case "clearProfile":
            case "resetPin":

                switch (action) {
                    case "activate":
                    case "deactivate":
                        mcList = Arrays.asList(StringUtils
                                .substringBeforeLast(portalSettings.getSetting("admobileapp_mc"), ":").split(","));
                        break;
                    case "block":
                    case "unblock":
                        mcList = Arrays.asList(StringUtils
                                .substringBeforeLast(portalSettings.getSetting("bumobileapp_mc"), ":").split(","));
                        break;
                    case "resetPassword":
                        mcList = Arrays.asList(StringUtils
                                .substringBeforeLast(portalSettings.getSetting("prmobileapp_mc"), ":").split(","));
                        break;
                    case "resetPin":
                        mcList = Arrays.asList(StringUtils
                                .substringBeforeLast(portalSettings.getSetting("pinrmobileapp_mc"), ":").split(","));
                        spList = Arrays.asList(
                                StringUtils.substringBeforeLast(portalSettings.getSetting("sp_accounts"), ":").split(","));
                        break;
                    case "resetProfile":
                        mcList = Arrays.asList(StringUtils
                                .substringBeforeLast(portalSettings.getSetting("rpmobileapp_mc"), ":").split(","));
                        break;
                    case "clearProfile":
                        mcList = Arrays.asList(StringUtils
                                .substringBeforeLast(portalSettings.getSetting("cpmobileapp_mc"), ":").split(","));
                        break;
                    default:
                        break;
                }

                if (mcList.contains(bankCode) && choice.trim().isEmpty() && !roleList.contains("2")) {
                    resp.put("message", "Maker checker activity. Initiator Required");
                } else {
                    if (roleList.contains("2")) {

                        int logRecord = handleMobileAppDuplicateErrorAndNoResultFound(phone, action, reason, bankCode, account_id, null);
                        apiData.setRole_id("2");

                        switch (logRecord) {
                            case 0:

                                resp = processMobileAppModification(apiData);
                                break;
                            case 1:
                                resp.put("message", "Request exists OR awaiting authorization");
                                break;
                            default:
                                resp.put("message", "An Error Occured");
                                break;
                        }
                    } else if (roleList.contains("1") || roleList.contains("3")) {
                        apiData.setRole_id("3");

                        resp = processMobileAppModification(apiData);

                    } else {
                        resp.put("message", "Authorization Required");
                    }
                }
                break;
            default:
                resp.put("message", "Invalid action: " + action);
                break;
        }
        log.info("MES::: " + resp.toString());

        return resp.toString();
    }

    public JSONObject processAccountModification(ApiPostData apiData) {
        List<String> mcList = new ArrayList<>();
        List<String> spList = new ArrayList<>();
        List<String> nm_with_sms = new ArrayList<>();

        String role = apiData.getRole_id();
        String action = apiData.getAction();
        String choice = apiData.getStatus();
        String bankCode = apiData.getBank_code();
        String branchCode = apiData.getBranch();

        String userId = apiData.getUser_id();
        String ipAddress = apiData.getIpAddress();
        String userName = apiData.getUserName();
        String reason = apiData.getOptionType();
        String id = apiData.getClientId();
        String phone = apiData.getMobile_no();
        String app = apiData.getLineType();
        String new_mobile = apiData.getSubscriberId();
        String enhancement_type = apiData.getPayType();
        String acc_change = apiData.getProduct();
        String otp = apiData.getApiCode();
        String alias = "";
        String app_name = "";
        String name = "";
        String accountBank = "";
        String accountNumber = "";
        String accountPhone = "";
        String card_num = "";
        String enc_card_num = "";
        HashMap<String, Object> options = apiData.getOptions();
        List<String> appId = apiData.getAppId();
        String qry = "";
        JSONObject resp = new JSONObject();
        resp.put("code", "01");
        resp.put("message", choice + " " + action + " failed");
        List<Object[]> results = new ArrayList<>();
        List<String> roleList = apiData.getRoleList();
        String type = "";
        String currentDate = "";
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            currentDate = sdf2.format(new Date());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        boolean rollback = true;
        boolean newUssdGate = CollectionUtils.containsAny(ussdGateBanks, appId);
        if (role.equals("2")) {
            type = "Initiated " + action + " request";
        } else {
            type = "Authorized " + action + " request";
        }

        switch (action) {
            case "hotlist":
            case "dehotlist":
                mcList = Arrays.asList(
                        StringUtils.substringBeforeLast(portalSettings.getSetting("hdaccount_mc"), ":").split(","));
                break;
            case "activate":
            case "deactivate":
                mcList = Arrays.asList(
                        StringUtils.substringBeforeLast(portalSettings.getSetting("adaccount_mc"), ":").split(","));
                break;
            case "number_modification":
                mcList = Arrays.asList(
                        StringUtils.substringBeforeLast(portalSettings.getSetting("nmaccount_mc"), ":").split(","));
                nm_with_sms = useNMOtpList();
                break;

            case "pinreset":
            case "enhancement":
                mcList = Arrays.asList(
                        StringUtils.substringBeforeLast(portalSettings.getSetting(action + "_mc"), ":").split(","));
                spList = Arrays.asList(
                        StringUtils.substringBeforeLast(portalSettings.getSetting("sp_accounts"), ":").split(","));
                break;
            default:
                break;
        }

        final String finalType = type;
        String sms_header = config.getProperty(bankCode.trim() + "_SMS");
        sms_header = sms_header.isEmpty() ? "Etranzact" : sms_header;
        Transaction tx = null;
        Transaction tx2 = null;

        Session session = DbHibernate.getSession("MOBILEDBMYSQL");
        Session bankSession = null;
        Query q;
        try {

            if (bankCode.equals("000")) {
                bankSession = DbHibernate.getSession("40.17MYSQL");
            } else {
                bankSession = DbHibernate.getSession(bankDB.getOrDefault(bankCode.trim(), null));
            }

            tx = session.beginTransaction();
            tx2 = bankSession.beginTransaction();
            switch (role) {
                case "1":
                case "2":
                case "3":
                    int i = 0;
                    String query = "";
                    String status = "";
                    int app_id = 0;
                    String acc = "";
                    if (newUssdGate) {
                        query = "select id, active, '' alias, app_id, app_name appId2, card_number, ifnull(u_sess, 'Customer'), bank_code  from mobiledb.mobile_profile where profile_type in (1,3) and id = :id and mobile_no = :phone "
                                + (!appId.contains("ALL") ? " and app_id in(:appId) " : "");
                    } else {
                        query = "select b.id, b.active, b.alias, a.appid appId, c.appid appId2, b.card_number, ifnull(b.request_byip,'Customer'), c.bank_code from mobiledb.m_mobile_subscriber a,"
                                + " mobiledb.m_mobile_subscriber_card b, mobiledb.m_mobileapp_properties c where a.id = b.subscriber_id "
                                + " and a.appid = c.id and a.mobile_no = :phone and b.id = :id "
                                + (!appId.contains("ALL") ? " and a.appid in(:appId) " : "");
                    }

                    q = session.createNativeQuery(query);
                    q.setParameter("phone", phone);
                    q.setParameter("id", id);
                    if (!appId.contains("ALL")) {
                        q.setParameter("appId", appId);
                    }

                    results = q.getResultList();
                    for (Object[] record : results) {
                        id = record[0].toString();
                        status = record[1].toString();
                        alias = record[2].toString();
                        app_id = Integer.parseInt(record[3].toString());
                        app_name = record[4].toString();
                        name = record[6].toString();
                        accountBank = record[7].toString();

                        if (newUssdGate) {
                            acc = new AutoRequest().getAPL(phone, bankAppIdList.getOrDefault(app, ""));
                        }
                        try {
                            enc_card_num = record[5].toString();
                            card_num = CryptographerMin.cryptPan(enc_card_num, 2);

                            HashMap<String, String> map = new HashMap<>();
                            String[] acct = acc.split("[|]");
                            for (String acct1 : acct) {
                                String[] first = acct1.split("~", -1);
                                String cardNum = first[0];
                                String maskedAcc = first[1];
                                accountNumber = first[2];

                                map.put(cardNum, String.format("%s", maskedAcc));
                            }
                            alias = map.getOrDefault(card_num, "");

                        } catch (Exception er) {
                            // Do Nothing
                        }
                    }

                    if (newUssdGate) {
                        query = "select  group_concat(card_number) from mobiledb.mobile_profile where profile_type in (1,3) and mobile_no = :phone "
                                + (!appId.contains("ALL") ? " and app_id in(:appId) " : "")
                                + " group by mobile_no";
                    } else {
                        query = "select group_concat(b.card_number) from mobiledb.m_mobile_subscriber a,"
                                + " mobiledb.m_mobile_subscriber_card b, mobiledb.m_mobileapp_properties c where a.id = b.subscriber_id "
                                + " and a.appid = c.id and a.mobile_no = :phone "
                                + (!appId.contains("ALL") ? " and a.appid in(:appId) " : "")
                                + " group by a.mobile_no";
                    }

                    q = session.createNativeQuery(query);
                    q.setParameter("phone", phone);

                    if (!appId.contains("ALL")) {
                        q.setParameter("appId", appId);
                    }

                    String accounts = (String) q.getSingleResult();

                    log.info("ID: " + id);
                    log.info("STATUS: " + status);
                    log.info("AppId: " + app_id);
                    log.info("Account: " + alias);
                    log.info("ACCOUNTS: " + accounts);

                    String account_change = "";
                    if (acc_change != null && !acc_change.trim().isEmpty()) {
                        account_change = acc_change;
                    } else {
                        switch (action.toLowerCase()) {
                            case "number_modification":
                                account_change = new_mobile;
                                break;
                            case "enhancement":
                                account_change = enhancement_type;
                                break;
                            default:
                                break;
                        }
                    }

                    if (!((role.equals("3") || role.equals("1"))
                            && (choice.equalsIgnoreCase("AUTHORIZE") || choice.equalsIgnoreCase("DENY")))) {

                        String qry2 = "insert into mobiledb.account_activity_log (initiated_by, bank_code, branch_code, phone, alias , account, action, app, initiated_date, appId, reason, status, account_change) "
                                + " select :username initiated_by, bank_code, :branch_code branch_code, :phone phone, :alias alias, :account account, :action action, :app app, now() initiated_date, :app_id appId, :reason reason, :status status, :account_change account_change "
                                + " from mobiledb.m_mobileapp_properties where id = :app_id";
                        q = session.createNativeQuery(qry2);
                        q.setParameter("action", action);

                        q.setParameter("account", id);
                        q.setParameter("reason", reason);
                        q.setParameter("app", "XPORTAL");
                        q.setParameter("alias", alias);
                        q.setParameter("branch_code", branchCode);

                        q.setParameter("phone", phone);

                        q.setParameter("account_change", account_change);

                        q.setParameter("status", "PENDING");
                        q.setParameter("username", userName);
                        q.setParameter("app_id", app_id);

                        i = q.executeUpdate();

                    } else {
                        i = 1;
                    }
                    if (i > 0) {
                        int c = 0;

                        if ((!mcList.contains(bankCode) && role.equals("2")) || ((role.equals("3") || role.equals("1"))
                                && (choice.equalsIgnoreCase("AUTHORIZE") || choice.equalsIgnoreCase("DENY")))) {

                            if (role.equals("2")) {
                                choice = "Authorize";
                            }

                            qry = "update mobiledb.account_activity_log set status= :choice, authorized_date = now(), authorized_by = :username where status='PENDING' and appId=:appId and account = :account and phone =:phone and action =:action"
                                    + (branchCode.equalsIgnoreCase("all") ? "" : " and branch_code = :branch");
                            q = session.createNativeQuery(qry);
                            q.setParameter("account", id);
                            q.setParameter("appId", app_id);
                            q.setParameter("phone", phone);
                            if (!branchCode.equalsIgnoreCase("all")) {
                                q.setParameter("branch", branchCode);
                            }
                            q.setParameter("username", userName);
                            q.setParameter("choice", choice.equalsIgnoreCase("AUTHORIZE") ? "APPROVED" : "DENIED");
                            q.setParameter("action", action);
                            int s = q.executeUpdate();
                            log.info("UPDATE AUTORIZATION ::: " + s);

                            if (s > 0 && choice.equalsIgnoreCase("AUTHORIZE")) {

                                if (action.equalsIgnoreCase("enhancement")) {

                                    String enhanceCard = "update e_cardholder set control_id = :type where (card_num = :cardnum  or card_account =:cardnum)";

                                    q = bankSession.createNativeQuery(enhanceCard)
                                            .setParameter("type", account_change);
                                    q.setParameter("cardnum", card_num);

                                    c = q.executeUpdate();

                                    log.info("ENHANCEMENT: " + c);

                                    rollback = c < 1;

                                    if (!rollback) {
                                        rollback = true;

                                        String smsResponse = new Alert().sendSMS(phone,
                                                createMsg(name, "", "", "enhancement_template", bankCode.trim()),
                                                sms_header);
                                        if (!smsResponse.isEmpty()) {
                                            if (!smsResponse.equalsIgnoreCase("Invalid")) {
                                                log.info("SMSR ::: " + smsResponse);
                                                JSONObject smsResult = new JSONObject(smsResponse);

                                                if (!smsResult.isEmpty()
                                                        && smsResult.optString("responseCode", "99").equals("00")) {

                                                    resp.put("code", "00");
                                                    resp.put("message", "Account Enhancement Successful. Sms Sent: 1");
                                                    rollback = false;
                                                } else {
                                                    resp.put("message", "Account Enhancement unsuccessful. Sms Sent: 0");
                                                }
                                            } else {
                                                resp.put("message", "Account Enhancement unsuccessful. Sms Sent: 0");
                                            }
                                        } else {
                                            resp.put("message", "Account Enhancement unsuccessful. Sms Sent: 0");
                                        }
                                    }
                                    if (!rollback) {
                                        resp.put("code", "00");
                                        resp.put("message", "Account Enhancement Successful");
                                    }
                                } else if (action.equalsIgnoreCase("pinreset")) {

                                    String pin = "";
                                    if (spList.contains(bankCode)) {

                                        List<String> accountNumbers = Arrays.asList(accounts.split(","));

                                        accountNumbers = accountNumbers.stream()
                                                .filter(f -> !(f == null || f.trim().isEmpty()))
                                                .map(card -> CryptographerMin.cryptPan(card, 2))
                                                .collect(Collectors.toList());

                                        JSONObject pinResp = PinGenerator.generatePin(accountNumbers);

                                        if (pinResp.getString("result").equalsIgnoreCase("success")) {

                                            List<CardAccounts> cardAccounts = new Gson().fromJson(
                                                    pinResp.get("accounts").toString(),
                                                    new TypeToken<List<CardAccounts>>() {
                                                    }.getType());

                                            int acc_size = cardAccounts.size();
                                            int modified = 0;
                                            for (CardAccounts cardacc : cardAccounts) {
                                                pin = cardacc.getPin();

                                                String resetPin = "update e_cardholder set modified = :date, change_pin = '1', x_offset= :offset, card_pin= :cardpin, user_hotlist ='0',pin_missed=0 "
                                                        + " where (card_num = :cardnum or card_account = :cardnum)";

                                                q = bankSession.createNativeQuery(resetPin);
                                                q.setParameter("offset", cardacc.getX_offset());
                                                q.setParameter("cardpin", cardacc.getCard_pin());
                                                q.setParameter("cardnum", cardacc.getCard_number());
                                                q.setParameter("date", currentDate);

                                                int d = q.executeUpdate();
                                                log.info("Pin reset : " + d);
                                                modified += d;
                                            }
                                            c = (modified == acc_size) ? 1 : 0;
                                        }
                                    } else {
//                                        log.info("Pin reset not implemented yet");
//                                        c = 1;

                                        JSONObject generatePinRequest = new JSONObject(
                                                PinGenerator.generatePin(card_num));

                                        if (generatePinRequest.getString("result").equals("success")) {
                                            pin = generatePinRequest.getString("pin");

                                            String resetPin = "update e_cardholder set modified = :date, change_pin = '1', x_offset= :offset, card_pin= :cardpin, user_hotlist ='0',pin_missed=0 "
                                                    + " where (card_num = :cardnum or card_account = :cardnum)";

                                            q = bankSession.createNativeQuery(resetPin);
                                            q.setParameter("offset", generatePinRequest.getString("x_offset"));
                                            q.setParameter("cardpin", generatePinRequest.getString("card_pin"));
                                            q.setParameter("cardnum", generatePinRequest.getString("cardNumber"));
                                            q.setParameter("date", currentDate);

                                            c = q.executeUpdate();
                                            log.info("Pin reset : " + c);
                                        }
                                    }

                                    if (c > 0) {

                                        String smsResponse = new Alert().sendSMS(phone,
                                                createMsg(name, pin, "",
                                                        (action.equalsIgnoreCase("pinreset") ? "pin_reset_template"
                                                        : "aa_sms_template"),
                                                        bankCode.trim()),
                                                sms_header);
                                        if (!smsResponse.isEmpty()) {
                                            if (!smsResponse.equalsIgnoreCase("Invalid")) {
                                                log.info("SMSR ::: " + smsResponse);
                                                JSONObject smsResult = new JSONObject(smsResponse);

                                                if (!smsResult.isEmpty()
                                                        && smsResult.optString("responseCode", "99").equals("00")) {

                                                    resp.put("code", "00");
                                                    resp.put("message",
                                                            action.equalsIgnoreCase("addaccount")
                                                            ? "Add account successful. Pin sent: 1"
                                                            : "Pin reset successful. Sent: 1");
                                                    rollback = false;
                                                } else {
                                                    resp.put("message",
                                                            action.equalsIgnoreCase("addaccount")
                                                            ? "Add account unsuccessful. Pin sent: 0"
                                                            : "Pin reset unsuccessful. Sent: 0");
                                                }
                                            } else {
                                                resp.put("message",
                                                        action.equalsIgnoreCase("addaccount")
                                                        ? "Add account unsuccessful. Pin sent: 0"
                                                        : "Pin reset unsuccessful. Sent: 0");
                                            }
                                        } else {
                                            resp.put("message",
                                                    action.equalsIgnoreCase("addaccount")
                                                    ? "Add account unsuccessful. Pin sent: 0"
                                                    : "Pin reset unsuccessful. Sent: 0");
                                        }
                                    }
                                } else if (action.equalsIgnoreCase("hotlist") || action.equalsIgnoreCase("dehotlist")) {
                                    String updateCardholder = "update e_cardholder SET user_hotlist = :action where (card_num = :cardnum or card_account = :cardnum)";
                                    q = bankSession.createNativeQuery(updateCardholder);
                                    q.setParameter("cardnum", card_num);
                                    q.setParameter("action", action.equalsIgnoreCase("hotlist") ? "1" : "0");

                                    int m = q.executeUpdate();

                                    rollback = !(m > 0);

                                    if (!rollback) {
                                        rollback = true;
                                        String smsResponse = new Alert().sendSMS(phone,
                                                createMsg(name, "", "", action + "_sms_template", bankCode.trim()),
                                                sms_header);
                                        if (!smsResponse.isEmpty()) {
                                            if (!smsResponse.equalsIgnoreCase("Invalid")) {
                                                log.info("SMSR ::: " + smsResponse);
                                                JSONObject smsResult = new JSONObject(smsResponse);

                                                if (!smsResult.isEmpty()
                                                        && smsResult.optString("responseCode", "99").equals("00")) {

                                                    resp.put("code", "00");
                                                    resp.put("message", "Account " + action + " successful. Sms sent: 1");
                                                    rollback = false;
                                                } else {
                                                    resp.put("message", "Account " + action + " unsuccessful. Sms sent: 0");
                                                }
                                            } else {
                                                resp.put("message", "Account " + action + " unsuccessful. Sms Sent: 0");
                                            }
                                        } else {
                                            resp.put("message", "Account " + action + " unsuccessful. Sms Sent: 0");
                                        }
                                    }

                                    resp.put("code", !rollback ? "00" : "01");
                                    resp.put("message", (!role.equals("2") ? (choice + " ") : "") + action
                                            + (!rollback ? " request successful." : " request failed."));
                                } else if (action.equalsIgnoreCase("number_modification")) {

                                    if (account_change == null) {
                                        account_change = "";
                                    }
                                    account_change = account_change.trim();

                                    int verificationFromBank = verifyNumberFromBank(accountBank, accountNumber,
                                            account_change);
                                    if (account_change.isEmpty()) {

                                        resp.put("code", "01");
                                        resp.put("message", "New phone number cannot be empty");

                                    } else if (account_change.length() > 12 || account_change.length() < 10) {
                                        resp.put("code", "01");
                                        resp.put("message", "New phone number is invalid");
                                    } else if (verificationFromBank == -1) {
                                        resp.put("code", "01");
                                        resp.put("message", "Could not get response from bank");
                                    } else if (verificationFromBank == 1) {
                                        resp.put("code", "01");
                                        resp.put("message", "Phone number not linked to bank account");
                                    } else {

                                        boolean verifyOtp = false;

                                        String mac = "";
                                        if (role.equals("2") && nm_with_sms.contains(accountBank)) {

                                            try {
                                                query = "select mac from mobiledb.number_modification_log where original_number = :phone and otp_status = 0 "
                                                        + "and new_number = :new_number and app_id = :app_id ";

                                                q = session.createNativeQuery(query);
                                                q.setParameter("phone", phone);
                                                q.setParameter("new_number", account_change);
                                                q.setParameter("app_id", app_id);

                                                mac = (String) q.getSingleResult();
                                            } catch (Exception e) {
                                                log.error(e.getMessage(), e);
                                            }

                                            verifyOtp = CryptographerMin
                                                    .compareSHA512(phone + app_id + new_mobile + otp, mac);
                                        } else {
                                            verifyOtp = true;
                                        }

                                        if (verifyOtp) {

                                            if (nm_with_sms.contains(accountBank)) {
                                                query = "update mobiledb.number_modification_log set otp_status = 1, used_date = now() where original_number = :phone and otp_status = 0 "
                                                        + "and new_number = :new_number and app_id = :app_id and mac = :mac ";

                                                q = session.createNativeQuery(query);
                                                q.setParameter("phone", phone);
                                                q.setParameter("new_number", account_change);
                                                q.setParameter("app_id", app_id);
                                                q.setParameter("mac", mac);

                                                q.executeUpdate();
                                            }

                                            int update_profile = 0;
                                            if (newUssdGate) {

                                                String updateMobileProfile = "update mobiledb.mobile_profile SET mobile_no = :phone, chana = :chana where id = :id and app_id =:appId";
                                                q = session.createNativeQuery(updateMobileProfile);
                                                q.setParameter("phone", account_change);
                                                q.setParameter("chana",
                                                        CryptographerMin.getSHA512(
                                                                account_change + "|" + app_id + "|" + enc_card_num,
                                                                status.equalsIgnoreCase("true")));
                                                q.setParameter("id", id);
                                                q.setParameter("appId", app_id);

                                                update_profile = q.executeUpdate();

                                            } else {

                                                update_profile = 0;
                                            }
                                            int m = 0;
                                            if (update_profile > 0) {

                                                String updateCardholder = "update e_cardholder SET phone = :phone where (card_num = :cardnum or card_account = :cardnum)";
                                                q = bankSession.createNativeQuery(updateCardholder);
                                                q.setParameter("cardnum", card_num);
                                                q.setParameter("phone", account_change);

                                                m = q.executeUpdate();
                                            }

                                            rollback = !(m > 0);

                                            if (!rollback) {
                                                rollback = true;
                                                String smsResponse = new Alert().sendSMS(account_change,
                                                        createMsg(name, "", "", "nm_sms_template", bankCode.trim()),
                                                        sms_header);
                                                if (!smsResponse.isEmpty()) {
                                                    if (!smsResponse.equalsIgnoreCase("Invalid")) {
                                                        log.info("SMSR ::: " + smsResponse);
                                                        JSONObject smsResult = new JSONObject(smsResponse);

                                                        if (!smsResult.isEmpty()
                                                                && smsResult.optString("responseCode", "99").equals("00")) {

                                                            resp.put("code", "00");
                                                            resp.put("message", "Number modification successful. Sms sent: 1");
                                                            rollback = false;
                                                        } else {
                                                            resp.put("message", "Number modification unsuccessful. Sms sent: 0");
                                                        }
                                                    } else {
                                                        resp.put("message", "Number modification unsuccessful. Sms Sent: 0");
                                                    }
                                                } else {
                                                    resp.put("message", "Number modification unsuccessful. Sms Sent: 0");
                                                }
                                            }
                                            resp.put("code", !rollback ? "00" : "01");
                                            resp.put("message", (!role.equals("2") ? (choice + " ") : "") + action
                                                    + (!rollback ? " request successful." : " request failed."));

                                        } else {

                                            resp.put("code", "01");
                                            resp.put("message",
                                                    (!role.equals("2") ? (choice + " ") : "") + action
                                                    + (!rollback ? " request successful." : " request failed.")
                                                    + " Invalid Otp");
                                        }
                                    }
                                } else if (action.equalsIgnoreCase("activate")
                                        || action.equalsIgnoreCase("deactivate")) {

                                    int update_profile = 0;

                                    if (newUssdGate) {
                                        qry = "update mobiledb.mobile_profile set active =:status, modified = now(), chana =:chana where id = :id";
                                    } else {
                                        qry = "update mobiledb.m_mobile_subscriber_card set active = :status, modified = now() where id = :id";
                                    }
                                    q = session.createNativeQuery(qry);
                                    q.setParameter("status", action.equalsIgnoreCase("activate") ? 1 : 0);
                                    q.setParameter("id", id);

                                    if (newUssdGate) {
                                        q.setParameter("chana",
                                                CryptographerMin.getSHA512(phone + "|" + app_id + "|" + enc_card_num,
                                                        action.equalsIgnoreCase("activate")));
                                    }
                                    update_profile = q.executeUpdate();

                                    if (update_profile > 0) {
                                        String actionType = action.equalsIgnoreCase("activate") ? "activation" : "deactivation";
                                        String smsResponse = new Alert().sendSMS(phone,
                                                createMsg(name, "", "", actionType + "_sms_template", bankCode.trim()),
                                                sms_header);
                                        if (!smsResponse.isEmpty()) {
                                            if (!smsResponse.equalsIgnoreCase("Invalid")) {
                                                log.info("SMSR ::: " + smsResponse);
                                                JSONObject smsResult = new JSONObject(smsResponse);

                                                if (!smsResult.isEmpty()
                                                        && smsResult.optString("responseCode", "99").equals("00")) {
                                                    rollback = false;
                                                    resp.put("code", "00");
                                                    resp.put("message", "Account " + actionType + " successful. Sms sent: 1");

                                                } else {
                                                    resp.put("message", "Account " + actionType + " unsuccessful. Sms sent: 0");
                                                }
                                            } else {
                                                resp.put("message",
                                                        action.equalsIgnoreCase("addaccount")
                                                        ? "Add account unsuccessful. Pin sent: 0"
                                                        : "Pin reset unsuccessful. Sent: 0");
                                            }
                                        } else {
                                            resp.put("message",
                                                    action.equalsIgnoreCase("addaccount")
                                                    ? "Add account unsuccessful. Pin sent: 0"
                                                    : "Pin reset unsuccessful. Sent: 0");
                                        }
                                    }

                                    resp.put("code", !rollback ? "00" : "01");
                                    resp.put("message", (!role.equals("2") ? (choice + " ") : "") + action
                                            + (!rollback ? " request successful." : " request failed."));
                                }
                            } else if (s > 0) {
                                resp.put("code", "00");
                                resp.put("message",
                                        (!role.equals("2") ? (choice + " ") : "") + action + " request successful");
                                rollback = false;
                            }
                        } else {
                            resp.put("code", "00");
                            resp.put("message",
                                    (!role.equals("2") ? (choice + " ") : "") + action + " request successful");
                            rollback = false;
                        }
                    }

                    if (rollback) {
                        try {
                            tx2.rollback();
                            tx.rollback();

                        } catch (Exception rt) {
                            log.error("", rt);
                        }
                    } else {
                        tx2.commit();
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
            log.error(ex.getMessage(), ex);
            String msg = ex.getMessage();
            log.info("message: " + msg);
            if (msg != null && msg.contains("ConstraintViolationException")) {
                log.info("Database Exception>> Duplicate Record");
                resp.put("message", "Request exists OR waiting authorization");
            } else {
                log.info("Database Exception>> an error occured");
                resp.put("message", "An error occured. Please try again");
            }

            try {
                if (tx2 != null) {
                    tx2.rollback();
                }
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
        }
        final String mobile = phone;
        final String maskedAccount = alias;

        if (role.equals("2") && mcList.contains(bankCode) && resp.optString("code", "06").equals("00")) {
            String msg = resp.getString("message");
            resp.put("message", msg.replaceAll(" successful", " submitted successfully"));
        }

        final String error = resp.getString("message");

        new Thread(() -> {
            AuditTrail audit = new AuditTrail(userId,
                    finalType + " for " + mobile + " - " + maskedAccount + " : " + error, "cms", null,
                    action.equalsIgnoreCase("addaccount") ? "add account with data " + options.toString() : action,
                    ipAddress);
            new AuditDao().insertIntoAuditTrail(audit);
        }).start();

        return resp;

    }

    public JSONObject processMobileAppModification(ApiPostData apiData) {
        List<String> mcList = new ArrayList<>();
        List<String> spList = new ArrayList<>();

        String role = apiData.getRole_id();
        String action = apiData.getAction();
        String choice = apiData.getStatus();
        String bankCode = apiData.getBank_code();
        String userId = apiData.getUser_id();
        String ipAddress = apiData.getIpAddress();
        String userName = apiData.getUserName();
        String reason = apiData.getOptionType();
        String id = apiData.getClientId();
        String phone = apiData.getMobile_no();
        String card_num = apiData.getAccount();

        String alias = "";

        HashMap<String, Object> options = apiData.getOptions();
        List<String> appId = apiData.getAppId();
        String qry = "";
        JSONObject resp = new JSONObject();
        resp.put("code", "01");
        resp.put("message", choice + " " + action + " failed");
        List<Object[]> results = new ArrayList<>();
        List<String> roleList = apiData.getRoleList();
        String type = "";
        boolean rollback = true;
        if (role.equals("2")) {
            type = "Initiated " + action + " request";
        } else {
            type = "Authorized " + action + " request";
        }

//        String actionMessage
        switch (action) {
            case "activate":
            case "deactivate":
                mcList = Arrays.asList(
                        StringUtils.substringBeforeLast(portalSettings.getSetting("admobileapp_mc"), ":").split(","));
                break;
            case "block":
            case "unblock":
                mcList = Arrays.asList(
                        StringUtils.substringBeforeLast(portalSettings.getSetting("bumobileapp_mc"), ":").split(","));
                break;
            case "resetPassword":
                mcList = Arrays.asList(
                        StringUtils.substringBeforeLast(portalSettings.getSetting("prmobileapp_mc"), ":").split(","));
                break;
            case "resetPin":
                mcList = Arrays.asList(
                        StringUtils.substringBeforeLast(portalSettings.getSetting("pinrmobileapp_mc"), ":").split(","));
                spList = Arrays.asList(
                        StringUtils.substringBeforeLast(portalSettings.getSetting("sp_accounts"), ":").split(","));
                break;
            case "resetProfile":
                mcList = Arrays.asList(
                        StringUtils.substringBeforeLast(portalSettings.getSetting("rpmobileapp_mc"), ":").split(","));
                break;
            case "clearProfile":
                mcList = Arrays.asList(
                        StringUtils.substringBeforeLast(portalSettings.getSetting("cpmobileapp_mc"), ":").split(","));
                break;
            default:
                break;
        }

        final String finalType = type;

        Transaction tx = null;
        Transaction tx2 = null;
        Session session = DbHibernate.getSession("MOBILEDBMYSQL");
        Session maSession = DbHibernate.getSession("MOBILEDBMYSQL");

        if (appId.contains("ALL")) {
            appId.clear();
            appId.add(appIdBankList.getOrDefault(bankCode, ""));
        }
        boolean newUssdGate = CollectionUtils.containsAny(ussdGateBanks, appId);
        String sms_header = config.getProperty(bankCode.trim() + "_SMS");
        sms_header = sms_header.isEmpty() ? "Etranzact" : sms_header;
        Query q;
        try {

            tx = session.beginTransaction();
            tx2 = maSession.beginTransaction();
            switch (role) {
                case "1":
                case "2":
                case "3":
                    int i = 0;

                    log.info("mcList: " + mcList + " -- " + role + " -- " + roleList + " -- " + choice.trim());
                    if (!mcList.contains(bankCode) && (role.equals("3") || role.equals("1")) && choice.trim().isEmpty()) {
                        choice = "AUTHORIZE";
                    }

                    if (!((role.equals("3") || role.equals("1"))
                            && (choice.equalsIgnoreCase("AUTHORIZE") || choice.equalsIgnoreCase("DENY")))) {

                        String qry2 = "insert into mobiledb.mobileapp_activity_log (initiated_by, bank_code, phone, account, action, app, initiated_date, reason, status) "
                                + " select :username initiated_by, :bank_code bank_code, :phone phone,  :account account, :action action, :app app, now() initiated_date, :reason reason, :status status "
                                + " from DUAL ";
                        q = session.createNativeQuery(qry2);
                        q.setParameter("action", action);

                        q.setParameter("account", id);
                        q.setParameter("reason", reason);
                        q.setParameter("app", "XPORTAL");
                        q.setParameter("bank_code", bankCode);
                        q.setParameter("phone", phone);
                        q.setParameter("status", "PENDING");
                        q.setParameter("username", userName);

                        i = q.executeUpdate();

                    } else {
                        i = 1;
                    }
                    if (i > 0) {
                        int c = 0;

                        if ((!mcList.contains(bankCode) && role.equals("2")) || ((role.equals("3") || role.equals("1"))
                                && (choice.equalsIgnoreCase("AUTHORIZE") || choice.equalsIgnoreCase("DENY")))) {

                            if (role.equals("2")) {
                                choice = "Authorize";
                            }

                            qry = "update mobiledb.mobileapp_activity_log set status= :choice, authorized_date = now(), authorized_by = :username, bank_code =:bankCode where status='PENDING' and account = :account and phone =:phone and action =:action ";
                            q = session.createNativeQuery(qry);
                            log.info("DDD: " + id + " : " + phone + " : " + userName + " : " + bankCode + " : " + choice
                                    + " : " + action);
                            q.setParameter("account", id);

                            q.setParameter("phone", phone);
                            q.setParameter("username", userName);
                            q.setParameter("bankCode", bankCode);

                            q.setParameter("choice", choice.equalsIgnoreCase("AUTHORIZE") ? "APPROVED" : "DENIED");
                            q.setParameter("action", action);
                            int s = q.executeUpdate();
                            log.info("UPDATE AUTHORIZATION ::: " + s);

                            if (s > 0 && choice.equalsIgnoreCase("AUTHORIZE")) {
                                String sql = "select id, pin_salt from mobiledb.mobgate_profile where customer_id = :account";
                                q = maSession.createNativeQuery(sql);
                                q.setParameter("account", id);
                                results = q.getResultList();
                                String accountNumber = "";
                                String name = "";

                                for (Object[] record : results) {
                                    accountNumber = checkNull(record[1]);

                                    accountNumber = CryptographerMin.AESCBCDecrypt(accountNumber,
                                            mobgateKEY, mobgateIV);

                                    if (name.trim().isEmpty()) {
                                        String tempName = getNameFromBank(bankCode, accountNumber);
                                        if (tempName != null && !tempName.trim().isEmpty()) {
                                            name = tempName.split(" ")[0];
                                        } else {
                                            name = "Customer";
                                        }
                                    }
                                }
                                if (action.equalsIgnoreCase("activate") || action.equalsIgnoreCase("deactivate")) {

                                    String activateQry = "update mobiledb.mobgate_profile set status = :status where customer_id = :account ";

                                    q = maSession.createNativeQuery(activateQry)
                                            .setParameter("account", id);
                                    q.setParameter("status", (action.equalsIgnoreCase("activate") ? 1 : 0));

                                    c = q.executeUpdate();
                                    String pin = "";
                                    String msg = action.equalsIgnoreCase("activate") ? " activation" : " deactivation";

                                    if (c > 0) {
                                        if (action.equalsIgnoreCase("activate")) {

                                            String query = "";
                                            if (newUssdGate) {
                                                query = "select id, active, '' alias, app_id, app_name appId2, card_number, ifnull(u_sess, 'Customer'), bank_code, profile_type  from mobiledb.mobile_profile where profile_type in (1,3) and mobile_no = :phone "
                                                        + (!appId.contains("ALL") ? " and app_id in(:appId) " : "")
                                                        + " limit 1";
                                            } else {
                                                query = "select b.id, b.active, b.alias, a.appid appId, c.appid appId2, b.card_number, ifnull(b.request_byip,'Customer'), c.bank_code, '1' profile_type from mobiledb.m_mobile_subscriber a,"
                                                        + " mobiledb.m_mobile_subscriber_card b, mobiledb.m_mobileapp_properties c where a.id = b.subscriber_id "
                                                        + " and a.appid = c.id and a.mobile_no = :phone "
                                                        + (!appId.contains("ALL") ? " and a.appid in(:appId) " : "")
                                                        + " limit 1";

                                            }

                                            q = maSession.createNativeQuery(query);
                                            q.setParameter("phone", phone);
                                            if (!appId.contains("ALL")) {
                                                q.setParameter("appId", appId);
                                            }

                                            results = q.getResultList();
                                            boolean hasUssd = false;
                                            for (Object[] record : results) {
                                                if (record[6].toString().trim().length() > 0) {
                                                    hasUssd = true;
                                                    if (name.isEmpty() || name.equals("Customer")) {
                                                        name = record[6].toString();
                                                    }
                                                    break;
                                                }
                                            }

                                            if (newUssdGate) {
                                                query = "select id, active, '' alias, app_id, app_name appId2, card_number, ifnull(u_sess, 'Customer'), bank_code, profile_type  from mobiledb.mobile_profile where profile_type in (2,3) and mobile_no = :phone "
                                                        + (!appId.contains("ALL") ? " and app_id in(:appId) " : "")
                                                        + " limit 1";
                                            } else {
                                                query = "select b.id, b.active, b.alias, a.appid appId, c.appid appId2, b.card_number, ifnull(b.request_byip,'Customer'), c.bank_code, '1' profile_type from mobiledb.m_mobile_subscriber a,"
                                                        + " mobiledb.m_mobile_subscriber_card b, mobiledb.m_mobileapp_properties c where a.id = b.subscriber_id "
                                                        + " and a.appid = c.id and a.mobile_no = :phone "
                                                        + (!appId.contains("ALL") ? " and a.appid in(:appId) " : "")
                                                        + " limit 1";

                                            }

                                            q = maSession.createNativeQuery(query);
                                            q.setParameter("phone", phone);
                                            if (!appId.contains("ALL")) {
                                                q.setParameter("appId", appId);
                                            }

                                            results = q.getResultList();
                                            boolean hasProfile = false;
                                            for (Object[] record : results) {
                                                if (record[6].toString().trim().length() > 0) {
                                                    hasProfile = true;
                                                    if (name.isEmpty() || name.equals("Customer")) {
                                                        name = record[6].toString();
                                                    }
                                                    break;
                                                }
                                            }

                                            if (!hasProfile || (!hasUssd && !bankCode.equals("005"))) {

                                                if (!accountNumber.equals("null")) {

                                                    String APP_NAME = BANK_NAME_MAP.get(bankCode);
                                                    if (!newUssdGate) {

                                                        String minss = new SimpleDateFormat("MMddhhmm")
                                                                .format(new Date());

                                                        String otp = Base64.getEncoder()
                                                                .encodeToString(phone.getBytes("UTF-8"));

                                                        String acctno = Base64.getEncoder()
                                                                .encodeToString(accountNumber.getBytes("UTF-8"));
                                                        String enewpin = bankCode;

                                                        String email = Base64.getEncoder().encodeToString(
                                                                "default@etranzact.com.gh".getBytes("UTF-8"));

                                                        String payload = String.format(
                                                                "?=ACCSYNC*%s %s %s %s %s %s %s %s", APP_NAME, otp,
                                                                acctno, enewpin, "000000", APP_NAME, minss, email);
                                                        log.info("URL Payload: " + payload);

                                                        String encode = URLEncoder.encode(payload, "UTF-8");
                                                        String MG_URL = StringUtils.substringBeforeLast(
                                                                portalSettings.getSetting("old_mg_url2"), ":");
                                                        String url = MG_URL + "?id=" + phone + "&msg=" + encode;
                                                        String pinRst = DoHTTPRequest.get2(url);

                                                        String now = new SimpleDateFormat("MMyyyy").format(new Date());
                                                        int yr = Integer.parseInt(now) + 3;
                                                        DecimalFormat padFormat = new DecimalFormat("000000");
                                                        String expiration = padFormat.format(yr);

                                                        log.info("doMGAccountCreation() request:: " + url);
                                                        log.info("doMGAccountCreation() response:: " + phone
                                                                + ":: response ::" + pinRst);
                                                        if (pinRst.toLowerCase().contains("successfully activated")) {
                                                            String mSql = "update mobiledb.m_mobile_subscriber_card set alias=:alias, expiration= :expiration,  modified = now(), active = 1, request_byip = :name  where subscriber_id = (select id from mobiledb.m_mobile_subscriber where mobile_no = :mobile_no and appid in(:appid))";

                                                            int j = maSession.createNativeQuery(mSql)
                                                                    .setParameter("alias",
                                                                            bankDB.get(bankCode) + "-" + accountNumber)
                                                                    .setParameter("mobile_no", phone)
                                                                    .setParameter("appid", appId)
                                                                    .setParameter("request_byip", name)
                                                                    .setParameter("expiration", expiration)
                                                                    .executeUpdate();

                                                            if (j > 0) {

                                                                String acc = new AutoRequest().getAPL(phone, bankCode);
                                                                String cardNumber = "";

                                                                String[] acct = acc.split("[|]");
                                                                for (String acct1 : acct) {
                                                                    String[] first = acct1.split("~", -1);

                                                                    if ((name.isEmpty() || name.equals("Customer")) && !first[3].trim().isEmpty()) {
                                                                        name = first[3];
                                                                    }
                                                                    if (first[2].equals(accountNumber)) {
                                                                        cardNumber = first[0];
                                                                        if ((name.isEmpty() || name.equals("Customer")) && !first[3].trim().isEmpty()) {
                                                                            name = first[3];
                                                                        }
                                                                        break;
                                                                    }
                                                                }

                                                                JSONObject generatePinRequest = new JSONObject(
                                                                        PinGenerator.generatePin(cardNumber));

                                                                if (generatePinRequest.getString("result")
                                                                        .equals("success")) {
                                                                    pin = generatePinRequest.getString("pin");

                                                                    Date date = new Date();
                                                                    SimpleDateFormat formatter = new SimpleDateFormat(
                                                                            "yyyy-MM-dd HH:mm:ss");
                                                                    String currentDate = formatter.format(date);
                                                                    Transaction tx3 = null;
                                                                    Session bankSession = null;
                                                                    try {
                                                                        if (bankCode.equals("000")) {
                                                                            bankSession = DbHibernate
                                                                                    .getSession("40.17MYSQL");
                                                                        } else {
                                                                            bankSession = DbHibernate.getSession(
                                                                                    bankDB.getOrDefault(bankCode.trim(),
                                                                                            null));
                                                                        }

                                                                        String resetPin = "update e_cardholder set modified = :date, change_pin = '0', x_offset= :offset, card_pin= :cardpin, user_hotlist ='0',pin_missed=0 "
                                                                                + " where (card_num = :cardnum or card_account = :cardnum)";

                                                                        tx3 = bankSession.beginTransaction();
                                                                        q = bankSession.createNativeQuery(resetPin)
                                                                                .setParameter("date", currentDate)
                                                                                .setParameter("offset",
                                                                                        generatePinRequest
                                                                                                .getString("x_offset"))
                                                                                .setParameter("cardpin",
                                                                                        generatePinRequest
                                                                                                .getString("card_pin"))
                                                                                .setParameter("cardnum",
                                                                                        generatePinRequest.getString(
                                                                                                "cardNumber"));

                                                                        c = q.executeUpdate();
                                                                        log.info("Pin reset: " + c);
                                                                        tx3.commit();
                                                                    } catch (Exception jl) {
                                                                        log.error("", jl);
                                                                    } finally {
                                                                        if (bankSession != null
                                                                                && bankSession.isOpen()) {
                                                                            bankSession.close();
                                                                        }
                                                                    }

                                                                    String activateProfile = "update mobiledb.m_mobile_subscriber_card set modified = now(), active = 1 where id =  (select id from (select b.id id from mobiledb.m_mobile_subscriber a "
                                                                            + "left join mobiledb.m_mobile_subscriber_card b on a.id = b.subscriber_id left join  mobiledb.m_mobileapp_properties c on c.id = a.appId "
                                                                            + "where a.mobile_no = :mobile_no and c.bank_code = :bank_code and card_number = :card_number) as t1) ";

                                                                    q = maSession.createNativeQuery(activateProfile)
                                                                            .setParameter("mobile_no", phone)
                                                                            .setParameter("bank_code", bankCode)
                                                                            .setParameter("card_number",
                                                                                    CryptographerMin
                                                                                            .cryptPan(cardNumber, 1));
                                                                    int hj = q.executeUpdate();
                                                                    log.info("ACTIVATE " + phone + " " + bankCode + " " + hj + "");

                                                                } else {
                                                                    resp.put("message",
                                                                            "Account " + msg + " unsuccessful. Pin generation failed");
                                                                }
                                                            } else {
                                                                resp.put("message", "Account " + msg + " unsuccessful");
                                                            }

                                                        } else {
                                                            resp.put("message",
                                                                    "Could not create account at Etranzact Intermediary");
                                                        }
                                                    } else {
                                                        pin = PinGenerator.generatePin();
                                                        String iv = RandomStringUtils.randomAlphanumeric(16);
                                                        String encPin = CryptographerMin.AESCBCEncrypt(pin, ussdGateKEY,
                                                                iv);
                                                        String ussdGateReq = "{\n"
                                                                + "    \"id\": 0,\n"
                                                                + "    \"msisdn\": \"" + phone + "\",\n"
                                                                + "    \"token\": \"ACCSYNCP\",\n"
                                                                + "    \"pCode\": \"" + iv + "\",\n"
                                                                + "    \"pin\": \"" + encPin + "\",\n"
                                                                + "    \"appName\": \"" + APP_NAME + "\",\n"
                                                                + "    \"profileType\": 2,\n"
                                                                + "    \"newUserAccount\": \"" + accountNumber + "\",\n"
                                                                + "    \"uSess\": \"" + name + "\"\n"
                                                                + "}";

                                                        String encRequest = "{\n"
                                                                + " \"id\": \"" + phone + "\",\n"
                                                                + " \"msg\": \""
                                                                + CryptographerMin.AESCBCEncrypt(ussdGateReq,
                                                                        ussdGateKEY, ussdGateIV)
                                                                + "\"\n"
                                                                + "}";
                                                        String USSDGATE_URL = StringUtils.substringBeforeLast(
                                                                portalSettings.getSetting("ussdgate_url"), ":");
                                                        String ugResponse = new DoHTTPRequest()
                                                                .post(USSDGATE_URL + "/process", encRequest);
                                                        log.info("USSD ACCOUNT CREATION :: " + phone + " -- "
                                                                + ugResponse);
                                                        JSONObject ugResp = new JSONObject(ugResponse);

                                                        if (ugResp.optString("error", "6").equals("0")) {
                                                            c = 1;
                                                        } else {
                                                            c = 0;
                                                        }
                                                    }

                                                    rollback = c < 1;
                                                } else {
                                                    resp.put("message", "Could not retrieve account number");
                                                }
                                            } else if (hasUssd && newUssdGate) {

                                                q = maSession.createNativeQuery("update mobiledb.mobile_profile set profile_type = 3, modified = now() where mobile_no = :mobile_no and bank_code = :bank_code")
                                                        .setParameter("mobile_no", phone)
                                                        .setParameter("bank_code", bankCode);
                                                int k = q.executeUpdate();
                                                rollback = (k == 0);
                                            } else {
                                                rollback = false;
                                            }
                                        } else {
                                            rollback = false;
                                        }

                                        if (!rollback && !pin.isEmpty()) {
                                            q = maSession.createNativeQuery("update mobiledb.mobgate_profile set status = 4, modified = now() where mobile_no = :mobile_no and bank_code = :bank_code")
                                                    .setParameter("mobile_no", phone)
                                                    .setParameter("bank_code", bankCode);
                                            int k = q.executeUpdate();
                                            rollback = (k == 0);
                                        }
                                        if (!rollback) {
                                            rollback = true;
                                            String template = "";
                                            if (pin.isEmpty()) {
                                                template = (action.equalsIgnoreCase("activate") ? "activation_sms_template" : "deactivation_sms_template");
                                            } else {
                                                template = "activation_pin_sms_template";

                                            }
                                            String smsResponse = new Alert().sendSMS(phone, createMsg(name, pin, "", template, bankCode.trim()), sms_header);

                                            if (!smsResponse.isEmpty()) {
                                                if (!smsResponse.equalsIgnoreCase("Invalid")) {
                                                    log.info("SMSResponse ::: " + smsResponse);
                                                    JSONObject smsResult = new JSONObject(smsResponse);

                                                    if (!smsResult.isEmpty() && smsResult.optString("responseCode", "99").equals("00")) {

                                                        resp.put("code", "00");
                                                        resp.put("message", msg + " successful. Sms Sent: 1");
                                                        rollback = false;
                                                    } else {
                                                        log.info("SMS ERROR: Did not get successful response for sms: " + phone);
                                                        resp.put("message", msg + " successful. Sms Sent: 0");
                                                    }
                                                } else {
                                                    log.info("SMS ERROR: Invalid phone number: " + phone);
                                                    resp.put("message", msg + " unsuccessful");
                                                }
                                            } else {
                                                resp.put("message",
                                                        "Account " + msg + " successful but Sms Sent: 0");
                                            }
                                        }
                                    }

                                    if (!rollback) {
                                        resp.put("code", "00");
                                        resp.put("message", "MobileApp "
                                                + msg
                                                + " Successful");
                                    }
                                } else if (action.equalsIgnoreCase("block") || action.equalsIgnoreCase("unblock")) {
                                    String enhanceCard = "update mobiledb.mobgate_profile set status = :status ";

                                    enhanceCard += action.equalsIgnoreCase("unblock") ? "  ,password_missed = 0 " : "";

                                    enhanceCard += " where customer_id =:account ";

                                    q = maSession.createNativeQuery(enhanceCard)
                                            .setParameter("account", id);
                                    q.setParameter("status", (action.equalsIgnoreCase("block") ? 3 : 1));

                                    c = q.executeUpdate();
                                    log.info("A: " + (action.equalsIgnoreCase("block") ? "BLOCKING" : "UNBLOCKING" + " -- " + c));

                                    rollback = c < 1;
                                    String msg = (action.equalsIgnoreCase("block") ? "Blocking" : "Unblocking");

                                    if (!rollback) {
                                        rollback = true;
                                        String smsResponse = new Alert().sendSMS(phone, createMsg(name, "", "", action.equalsIgnoreCase("block") ? "block_sms_template" : "unblock_sms_template", bankCode.trim()), sms_header);

                                        if (!smsResponse.isEmpty()) {
                                            if (!smsResponse.equalsIgnoreCase("Invalid")) {
                                                log.info("SMSResponse ::: " + smsResponse);
                                                JSONObject smsResult = new JSONObject(smsResponse);

                                                if (!smsResult.isEmpty() && smsResult.optString("responseCode", "99").equals("00")) {

                                                    resp.put("code", "00");
                                                    resp.put("message", msg + " successful. Sms Sent: 1");
                                                    rollback = false;
                                                } else {
                                                    log.info("SMS ERROR: Did not get successful response for sms: " + phone);
                                                    resp.put("message", msg + " successful. Sms Sent: 0");
                                                }
                                            } else {
                                                log.info("SMS ERROR: Invalid phone number: " + phone);
                                                resp.put("message", msg + " unsuccessful");
                                            }
                                        } else {
                                            resp.put("message",
                                                    "Account " + msg + " successful but Sms Sent: 0");
                                        }
                                    }
                                    if (!rollback) {
                                        resp.put("code", "00");
                                        resp.put("message",
                                                "MobileApp "
                                                + msg
                                                + " Successful");
                                    }
                                } else if (action.equalsIgnoreCase("resetPassword")) {

                                    String request = "{\n"
                                            + "    \"action\": \"resetpasswordadmin\",\n"
                                            + "    \"bankCode\": \"" + bankCode + "\",\n"
                                            + "    \"mobile\": \"" + phone + "\"\n"
                                            + "}";
                                    String url = StringUtils
                                            .substringBeforeLast(portalSettings.getSetting("m_auth_url"), ":");
                                    String authResp = new DoHTTPRequest().post(url, request);

                                    JSONObject aResp = new JSONObject(authResp);
                                    if (aResp.optString("error", "06").equals("0")) {
                                        rollback = false;

//                                        if (!rollback) {
//                                            rollback = true;
//                                            String msg = "Password Reset";
//                                            String smsResponse = new Alert().sendSMS(phone, createMsg(name, "", "", "password_reset_sms_template", bankCode.trim()), sms_header);
//
//                                            if (!smsResponse.isEmpty()) {
//                                                if (!smsResponse.equalsIgnoreCase("Invalid")) {
//                                                    log.info("SMSResponse ::: " + smsResponse);
//                                                    JSONObject smsResult = new JSONObject(smsResponse);
//
//                                                    if (!smsResult.isEmpty() && smsResult.optString("responseCode", "99").equals("00")) {
//
//                                                        resp.put("code", "00");
//                                                        resp.put("message", msg + " successful. Sms Sent: 1");
//                                                        rollback = false;
//                                                    } else {
//                                                        log.info("SMS ERROR: Did not get successful response for sms: " + phone);
//                                                        resp.put("message", msg + " successful. Sms Sent: 0");
//                                                    }
//                                                } else {
//                                                    log.info("SMS ERROR: Invalid phone number: " + phone);
//                                                    resp.put("message", msg + " unsuccessful");
//                                                }
//                                            } else {
//                                                resp.put("message",
//                                                        "Account " + msg + " successful but Sms Sent: 0");
//                                            }
//                                        }
                                        resp.put("code", "00");
                                        resp.put("message", "MobileApp Password Reset Successful");
                                    } else {
                                        resp.put("code", "01");
                                        resp.put("message", "MobileApp Password Reset Unsuccessful. "
                                                + aResp.optString("message", "No Response"));
                                    }

                                } else if (action.equalsIgnoreCase("resetProfile")) {

                                    String request = "{\n"
                                            + "    \"action\": \"RESETPROFILE\",\n"
                                            + "    \"bankCode\": \"" + bankCode + "\",\n"
                                            + "    \"mobile\": \"" + phone + "\"\n"
                                            + "}";
                                    String url = StringUtils
                                            .substringBeforeLast(portalSettings.getSetting("m_auth_url"), ":");
                                    String authResp = new DoHTTPRequest().post(url, request);

                                    JSONObject aResp = new JSONObject(authResp);
                                    if (aResp.optString("error", "06").equals("0")) {
                                        rollback = false;

//                                        if (!rollback) {
//                                            rollback = true;
//                                            String msg = "Profile Reset";
//                                            String smsResponse = new Alert().sendSMS(phone, createMsg(name, "", "", "profile_reset_sms_template", bankCode.trim()), sms_header);
//
//                                            if (!smsResponse.isEmpty()) {
//                                                if (!smsResponse.equalsIgnoreCase("Invalid")) {
//                                                    log.info("SMSResponse ::: " + smsResponse);
//                                                    JSONObject smsResult = new JSONObject(smsResponse);
//
//                                                    if (!smsResult.isEmpty() && smsResult.optString("responseCode", "99").equals("00")) {
//
//                                                        resp.put("code", "00");
//                                                        resp.put("message", msg + " successful. Sms Sent: 1");
//                                                        rollback = false;
//                                                    } else {
//                                                        log.info("SMS ERROR: Did not get successful response for sms: " + phone);
//                                                        resp.put("message", msg + " successful. Sms Sent: 0");
//                                                    }
//                                                } else {
//                                                    log.info("SMS ERROR: Invalid phone number: " + phone);
//                                                    resp.put("message", msg + " unsuccessful");
//                                                }
//                                            } else {
//                                                resp.put("message",
//                                                        "Account " + msg + " successful but Sms Sent: 0");
//                                            }
//                                        }
                                        resp.put("code", "00");
                                        resp.put("message", "MobileApp Reset Profile Successful");
                                    } else {
                                        resp.put("code", "01");
                                        resp.put("message", "MobileApp Reset Profile Unsuccessful. "
                                                + aResp.optString("message", "No Response"));
                                    }

                                } else if (action.equalsIgnoreCase("clearProfile")) {

                                    String request = "{\n"
                                            + "    \"action\": \"CLEARPROFILE\",\n"
                                            + "    \"bankCode\": \"" + bankCode + "\",\n"
                                            + "    \"mobile\": \"" + phone + "\"\n"
                                            + "}";
                                    String url = StringUtils
                                            .substringBeforeLast(portalSettings.getSetting("m_auth_url"), ":");
                                    String authResp = new DoHTTPRequest().post(url, request);

                                    JSONObject aResp = new JSONObject(authResp);
                                    if (aResp.optString("error", "06").equals("0")) {
                                        rollback = false;
//                                        if (!rollback) {
//                                            rollback = true;
//                                            String msg = "Clear Profile";
//                                            String smsResponse = new Alert().sendSMS(phone, createMsg(name, "", "", "clear_profile_sms_template", bankCode.trim()), sms_header);
//
//                                            if (!smsResponse.isEmpty()) {
//                                                if (!smsResponse.equalsIgnoreCase("Invalid")) {
//                                                    log.info("SMSResponse ::: " + smsResponse);
//                                                    JSONObject smsResult = new JSONObject(smsResponse);
//
//                                                    if (!smsResult.isEmpty() && smsResult.optString("responseCode", "99").equals("00")) {
//
//                                                        resp.put("code", "00");
//                                                        resp.put("message", msg + " successful. Sms Sent: 1");
//                                                        rollback = false;
//                                                    } else {
//                                                        log.info("SMS ERROR: Did not get successful response for sms: " + phone);
//                                                        resp.put("message", msg + " successful. Sms Sent: 0");
//                                                    }
//                                                } else {
//                                                    log.info("SMS ERROR: Invalid phone number: " + phone);
//                                                    resp.put("message", msg + " unsuccessful");
//                                                }
//                                            } else {
//                                                resp.put("message",
//                                                        "Account " + msg + " successful but Sms Sent: 0");
//                                            }
//                                        }
                                        resp.put("code", "00");
                                        resp.put("message", "MobileApp Clear Profile Successful");

                                    } else {
                                        resp.put("code", "01");
                                        resp.put("message", "MobileApp Clear Profile Unsuccessful. "
                                                + aResp.optString("message", "No Response"));
                                    }

                                } else if (action.equalsIgnoreCase("resetPin")) {
                                    String query = "";
                                    if (newUssdGate) {
                                        query = "select  group_concat(card_number) from mobiledb.mobile_profile where profile_type in (2,3) and  mobile_no = :phone "
                                                + (!appId.contains("ALL") ? " and app_id in(:appId) " : "")
                                                + " group by mobile_no";
                                    } else {
                                        query = "select group_concat(b.card_number) from mobiledb.m_mobile_subscriber a,"
                                                + " mobiledb.m_mobile_subscriber_card b, mobiledb.m_mobileapp_properties c where a.id = b.subscriber_id "
                                                + " and a.appid = c.id and a.mobile_no = :phone "
                                                + (!appId.contains("ALL") ? " and a.appid in(:appId) " : "")
                                                + " group by a.mobile_no";
                                    }

                                    q = session.createNativeQuery(query);
                                    q.setParameter("phone", phone);

                                    if (!appId.contains("ALL")) {
                                        q.setParameter("appId", appId);
                                    }

                                    String accounts = (String) q.getSingleResult();
                                    String pin = "";
                                    Session bankSession = null;
                                    Transaction tx3 = null;
                                    if (bankCode.equals("000")) {
                                        bankSession = DbHibernate
                                                .getSession("40.17MYSQL");
                                    } else {
                                        bankSession = DbHibernate.getSession(
                                                bankDB.getOrDefault(bankCode.trim(),
                                                        null));
                                    }
                                    SimpleDateFormat formatter = new SimpleDateFormat(
                                            "yyyy-MM-dd HH:mm:ss");
                                    String currentDate = formatter.format(new Date());

                                    List<String> accountNumbers = Arrays.asList(accounts.split(","));

                                    log.info("GOT HERE::: " + accountNumbers);
                                    try {
                                        tx3 = bankSession.beginTransaction();

                                        if (spList.contains(bankCode)) {

                                            accountNumbers = accountNumbers.stream()
                                                    .filter(f -> !(f == null || f.trim().isEmpty()))
                                                    .map(card -> CryptographerMin.cryptPan(card, 2))
                                                    .collect(Collectors.toList());

                                            JSONObject pinResp = PinGenerator.generatePin(accountNumbers);

                                            if (pinResp.getString("result").equalsIgnoreCase("success")) {

                                                List<CardAccounts> cardAccounts = new Gson().fromJson(
                                                        pinResp.get("accounts").toString(),
                                                        new TypeToken<List<CardAccounts>>() {
                                                        }.getType());

                                                int acc_size = cardAccounts.size();
                                                int modified = 0;
//                                            try {
//                                                tx3 = bankSession.beginTransaction();
                                                for (CardAccounts cardacc : cardAccounts) {
                                                    pin = cardacc.getPin();

                                                    String resetPin = "update e_cardholder set modified = :date, change_pin = '1', x_offset= :offset, card_pin= :cardpin, user_hotlist ='0',pin_missed=0 "
                                                            + " where (card_num = :cardnum or card_account = :cardnum)";

                                                    q = bankSession.createNativeQuery(resetPin);
                                                    q.setParameter("offset", cardacc.getX_offset());
                                                    q.setParameter("cardpin", cardacc.getCard_pin());
                                                    q.setParameter("cardnum", cardacc.getCard_number());
                                                    q.setParameter("date", currentDate);

                                                    int d = q.executeUpdate();
                                                    log.info("Pin reset : " + d);
                                                    modified += d;
                                                }
                                                log.info("GOT HERE::: " + accountNumbers);

                                                c = (modified == acc_size) ? 1 : 0;
                                                log.info("Pin reset: " + c);

                                            }
                                        } else {
                                            JSONObject generatePinRequest = new JSONObject(
                                                    PinGenerator.generatePin(card_num));

                                            if (generatePinRequest.getString("result").equals("success")) {
                                                pin = generatePinRequest.getString("pin");

                                                String resetPin = "update e_cardholder set modified = :date, change_pin = '1', x_offset= :offset, card_pin= :cardpin, user_hotlist ='0',pin_missed=0 "
                                                        + " where (card_num = :cardnum or card_account = :cardnum)";

                                                q = bankSession.createNativeQuery(resetPin);
                                                q.setParameter("offset", generatePinRequest.getString("x_offset"));
                                                q.setParameter("cardpin", generatePinRequest.getString("card_pin"));
                                                q.setParameter("cardnum", generatePinRequest.getString("cardNumber"));
                                                q.setParameter("date", currentDate);

                                                c = q.executeUpdate();
                                                log.info("Pin reset : " + c);
                                            }
                                        }
                                        tx3.commit();
                                    } catch (Exception jl) {
                                        log.error(jl.getMessage(), jl);
                                    } finally {
                                        if (bankSession != null
                                                && bankSession.isOpen()) {
                                            bankSession.close();
                                        }
                                    }
                                    log.info("GOT HERE::: " + c);

                                    if (c > 0) {

                                        //change profile status
                                        String change_pin = "";
                                        int g = 0;
//                                        if (newUssdGate) {
//                                            change_pin = "Update mobiledb.mobile_profile set active = 4 where card_number in (:card_number)";
//                                            g = session.createNativeQuery(change_pin).setParameter("card_number", accountNumbers).executeUpdate();
//                                        } else {
//                                            g = 1;
//                                        }
                                        change_pin = "update mobiledb.mobgate_profile set status = 4 where customer_id =:account";
                                        g = session.createNativeQuery(change_pin)
                                                .setParameter("account", id)
                                                .executeUpdate();

                                        if (g > 0) {
                                            sms_header = sms_header.isEmpty() ? "Etranzact" : sms_header;
                                            String smsResponse = new Alert().sendSMS(phone,
                                                    createMsg(name, pin, "", "pin_reset_template",
                                                            bankCode.trim()),
                                                    sms_header);
                                            if (!smsResponse.isEmpty()) {
                                                if (!smsResponse.equalsIgnoreCase("Invalid")) {
                                                    log.info("SMSR ::: " + smsResponse);
                                                    JSONObject smsResult = new JSONObject(smsResponse);

                                                    if (!smsResult.isEmpty()
                                                            && smsResult.optString("responseCode", "99").equals("00")) {

                                                        resp.put("code", "00");
                                                        resp.put("message",
                                                                action.equalsIgnoreCase("addaccount")
                                                                ? "Add account successful. Pin sent: 1"
                                                                : "Pin reset successful. Sent: 1");
                                                        rollback = false;
                                                    } else {
                                                        resp.put("message",
                                                                action.equalsIgnoreCase("addaccount")
                                                                ? "Add account unsuccessful. Pin sent: 0"
                                                                : "Pin reset unsuccessful. Sent: 0");
                                                    }
                                                } else {
                                                    resp.put("message",
                                                            action.equalsIgnoreCase("addaccount")
                                                            ? "Add account unsuccessful. Pin sent: 0"
                                                            : "Pin reset unsuccessful. Sent: 0");
                                                }
                                            } else {
                                                resp.put("message",
                                                        action.equalsIgnoreCase("addaccount")
                                                        ? "Add account unsuccessful. Pin sent: 0"
                                                        : "Pin reset unsuccessful. Sent: 0");
                                            }
                                        } else {
                                            resp.put("message",
                                                    action.equalsIgnoreCase("addaccount")
                                                    ? "Add account unsuccessful. Pin sent: 0"
                                                    : "Pin reset unsuccessful. Sent: 0");
                                        }
                                    }
                                }
                            } else if (s > 0) {
                                resp.put("code", "00");
                                resp.put("message",
                                        (!role.equals("2") ? (choice + " ") : "") + action + " request successful");
                                rollback = false;
                            }
                        } else {
                            resp.put("code", "00");
                            resp.put("message",
                                    (!role.equals("2") ? (choice + " ")
                                    : (!choice.toLowerCase().contains("authorize") ? "initiate " : "")) + action
                                    + " request successful");
                            rollback = false;
                        }
                    }

                    if (rollback) {
                        try {
                            tx.rollback();
                            tx2.rollback();

                        } catch (Exception rt) {
                            log.error("", rt);
                        }
                    } else {
                        tx.commit();
                        tx2.commit();

                    }
                    break;

                default:
                    resp.put("message", (!role.equals("2") ? (choice + " ") : "") + action + " Failed");
                    break;
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            String msg = ex.getMessage();
            log.info("message: " + msg);
            if (msg.contains("ConstraintViolationException")) {
                log.info("Database Exception>> Duplicate Record");
                resp.put("message", "Request exists OR waiting authorization");
            } else {
                log.info("Database Exception >> an error occured");
                resp.put("message", "An error occured. Please try again");
            }

            try {
                if (tx != null) {
                    tx.rollback();
                }
                if (tx2 != null) {
                    tx2.rollback();
                }

            } catch (Exception exc) {
                log.info("XPORTAL CMS ERROR >> COULD NOT ROLLBACK");
            }
        } finally {
            try {
                if (session != null && session.isOpen()) {
                    session.close();
                }
                if (maSession != null && maSession.isOpen()) {
                    maSession.close();
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        final String mobile = phone;

        if (role.equals("2") && mcList.contains(bankCode) && resp.optString("code", "06").equals("00")) {
            String msg = resp.getString("message");
            resp.put("message", msg.replaceAll(" successful", " submitted successfully"));
        }
        final String error = resp.getString("message");

        new Thread(
                () -> {
                    AuditTrail audit = new AuditTrail(userId, finalType + " for " + mobile + " : " + error, "mobileapp",
                            null, action, ipAddress);
                    new AuditDao().insertIntoAuditTrail(audit);
                }).start();

        return resp;

    }

    public JSONObject processBankApplicationRequest(ApiPostData apiData) {
        List<String> mcList = new ArrayList<>();

        String role = apiData.getRole_id();
        String action = apiData.getAction();
        String choice = apiData.getStatus();
        String bankCode = apiData.getBank_code();
        String userId = apiData.getUser_id();
        String ipAddress = apiData.getIpAddress();
        String userName = apiData.getUserName();
        String reason = apiData.getOptionType();
        String id = apiData.getClientId();
        String phone = apiData.getMobile_no();

        String alias = "";

        HashMap<String, Object> options = apiData.getOptions();
        List<String> appId = apiData.getAppId();
        String qry = "";
        JSONObject resp = new JSONObject();
        resp.put("code", "01");
        resp.put("message", choice + " " + action + " failed");
        List<Object[]> results = new ArrayList<>();
        List<String> roleList = apiData.getRoleList();

        role = "3";
        String type = "";
        boolean rollback = true;

        type = "Processed " + action + " request";

        log.info("List:: " + mcList + " role: " + role);
        final String finalType = type;

        Transaction tx = null;

        Session session = DbHibernate.getSession("MOBILEDBMYSQL");
        Query q;
        try {
            tx = session.beginTransaction();
            switch (role) {
                case "1":
                case "2":
                case "3":

                    if ((role.equals("2")) || ((role.equals("3") || role.equals("1"))
                            && (choice.equalsIgnoreCase("AUTHORIZE") || choice.equalsIgnoreCase("DENY")))) {

                        if (role.equals("2")) {
                            choice = "Authorize";
                        }

                        qry = "update mobiledb.mobgate_bankapplications set status= :choice, processedDate = now(), processedBy = :username where status='PENDING' and reference = :id and phoneNumber = :phone and bankCode =:bankCode";
                        q = session.createNativeQuery(qry);
                        q.setParameter("id", id);
                        q.setParameter("bankCode", bankCode);
                        q.setParameter("username", userName);
                        q.setParameter("phone", phone);

                        q.setParameter("choice", choice.equalsIgnoreCase("AUTHORIZE") ? "PROCESSED" : "DECLINED");
                        int s = q.executeUpdate();
                        log.info("UPDATE AUTHORIZATION ::: " + s);

                        rollback = s < 1;
                        if (!rollback) {
                            resp.put("code", "00");
                            resp.put("message",
                                    "Bank Application "
                                    + (choice.equalsIgnoreCase("AUTHORIZE") ? "Processed" : "Declined")
                                    + " Successfully");
                        }

                    } else {
                        resp.put("code", "00");
                        resp.put("message", (!role.equals("2") ? (choice + " ") : "") + action + " request successful");
                        rollback = false;
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
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            String msg = ex.getMessage();
            log.info("message: " + msg);
            if (msg.contains("ConstraintViolationException")) {
                log.info("Database Exception>> Duplicate Record");
                resp.put("message", "Request exists OR waiting authorization");
            } else {
                log.info("Database Exception>> an error occured");
                resp.put("message", "An error occured. Please try again");
            }

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

        }
        final String mobile = phone;
        final String maskedAccount = alias;
        if (role.equals("2") && mcList.contains(bankCode) && resp.optString("code", "06").equals("00")) {
            String msg = resp.getString("message");
            resp.put("message", msg.replaceAll(" successful", " submitted successfully"));
        }
        final String error = resp.getString("message");

        new Thread(() -> {
            AuditTrail audit = new AuditTrail(userId,
                    finalType + " for " + mobile + " - " + maskedAccount + " : " + error, "mobileapp", null,
                    action.equalsIgnoreCase("addaccount") ? "add account with data " + options.toString() : action,
                    ipAddress);
            new AuditDao().insertIntoAuditTrail(audit);
        }).start();

        return resp;

    }

    public JSONObject processAccountRequest(String jsonString) {
        List<String> mcList = new ArrayList<>();
        log.info("processAccountRequest received >> " + jsonString);
        Gson j = new Gson();
        ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);

        String role = apiData.getRole_id();
        String reference = apiData.getUniqueTransId();

        String action = apiData.getAction();
        String choice = apiData.getStatus();
        String bankCode = apiData.getBank_code();
        String userId = apiData.getUser_id();
        String ipAddress = apiData.getIpAddress();
        String userName = apiData.getUserName();
        String reason = apiData.getOptionType();
        String id = apiData.getClientId();
        String phone = apiData.getMobile_no();

        String alias = "";

        HashMap<String, Object> options = apiData.getOptions();
        List<String> appId = apiData.getAppId();
        String qry = "";
        JSONObject resp = new JSONObject();
        resp.put("code", "01");
        resp.put("message", choice + " " + action + " failed");
        List<Object[]> results = new ArrayList<>();
        List<String> roleList = apiData.getRoleList();
        if (appId.contains("ALL") || appId.isEmpty()) {
            appId.clear();
            appId.add(appIdBankList.getOrDefault(bankCode, ""));
        }

        boolean newUssdGate = CollectionUtils.containsAny(ussdGateBanks, appId);
        String type = "";
        boolean rollback = true;
        if (role.equals("2")) {
            type = "Initiated " + action + " request";
        } else {
            type = "Authorized " + action + " request";
        }

        switch (action) {
            case "addaccount":
                mcList = Arrays.asList(
                        StringUtils.substringBeforeLast(portalSettings.getSetting("addaccount_mc"), ":").split(","));
                break;
            default:
                break;
        }
        final String finalType = type;
//        if (mcList.contains(bankCode) && !choice.trim().isEmpty() && !role.equals("2")) {
//        if (mcList.contains(bankCode) && choice.trim().isEmpty() && (role.equals("1") || role.equals("3"))) {
//            resp.put("message", "Maker checker activity. Initiator Required");
//            
//        } else {
        if (mcList.contains(bankCode) && choice.trim().isEmpty() && !role.equals("2")) {
            resp.put("message", "Maker checker activity. Initiator Required");

        } else {

            Transaction tx = null;

            Session session = DbHibernate.getSession("MOBILEDBMYSQL");

            Query q;
            try {
                log.info("TO:: " + bankCode.trim() + " role: " + role);
                log.info("BANK:: " + bankDB.getOrDefault(bankCode.trim(), null));

                tx = session.beginTransaction();
                int proceed = 0;
                switch (role) {

                    case "1":
                    case "2":
                    case "3":
                        int i = 0;

                        if (action.equals("addaccount") && apiData.getRole_id().equals("2")) {

                            int logRecord = handleMobileAppDuplicateErrorAndNoResultFound(phone, action, reason,
                                    bankCode, id, session);

                            switch (logRecord) {
                                case 0:
                                    proceed = 1;
                                    break;
                                case 1:
                                    resp.put("message", "Request exists OR awaiting authorization");
                                    break;
                                default:
                                    resp.put("message", "An Error Occured");
                                    break;
                            }
                        } else {
                            proceed = 1;
                        }
                        log.info("PROCEED: " + proceed);

                        if (proceed == 1) {

                            if (action.equalsIgnoreCase("addaccount") && (!((role.equals("3") || role.equals("1"))
                                    && (choice.equalsIgnoreCase("AUTHORIZE") || choice.equalsIgnoreCase("DENY"))))) {

                                String qry2 = "insert ignore into mobiledb.mobileapp_activity_log (initiated_by, bank_code, phone , account, action, app, initiated_date, reason, status) "
                                        + " select :username initiated_by, :bank_code bank_code, :phone phone,  :account account, :action action, :app app, now() initiated_date, :reason reason, :status status "
                                        + " from DUAL";
                                q = session.createNativeQuery(qry2);
                                q.setParameter("action", action);

                                q.setParameter("account", id);
                                q.setParameter("reason", reason);
                                q.setParameter("app", "XPORTAL");
                                q.setParameter("bank_code", bankCode);
                                q.setParameter("phone", phone);

                                q.setParameter("status", "PENDING");
                                q.setParameter("username", userName);

                                i = q.executeUpdate();

                            } else {
                                i = 1;
                            }
                            log.info("GG: " + i);
                            if (i > 0) {
                                int c = 0;

                                if (action.equalsIgnoreCase("addaccount")) {
                                    int sh = 0;
                                    if (apiData.getRole_id().equals("2")) {
                                        qry = "update mobiledb.mobgate_bankrequests set status = 'AWAITING' where  bankCode =:bankCode and status='PENDING' and account = :account and msisdn =:phone and type =:action ";
                                        q = session.createNativeQuery(qry);
                                        log.info("DATA : " + id + " : " + phone + " : " + userName + " : " + bankCode
                                                + " : " + choice + " : " + action);
                                        q.setParameter("account", id);

                                        q.setParameter("phone", phone);

                                        q.setParameter("bankCode", bankCode);

                                        q.setParameter("action", action);
                                        sh = q.executeUpdate();
                                    } else {
                                        sh = 1;
                                    }

                                    if (sh > 0) {

                                        if ((!mcList.contains(bankCode) && role.equals("2"))
                                                || ((role.equals("3") || role.equals("1"))
                                                && (choice.equalsIgnoreCase("AUTHORIZE")
                                                || choice.equalsIgnoreCase("DENY")))) {

                                            if (role.equals("2")) {
                                                choice = "Authorize";
                                            }

                                            qry = "update mobiledb.mobileapp_activity_log set status = :choice, authorized_date = now(), authorized_by = :username where status='PENDING' and bank_code = :bankCode and account = :account and phone = :phone and action = :action ";
                                            q = session.createNativeQuery(qry);
                                            log.info("DATA2 : " + id + " : " + phone + " : " + userName + " : " + bankCode
                                                    + " : " + choice + " : " + action);
                                            q.setParameter("account", id);

                                            q.setParameter("phone", phone);
                                            q.setParameter("username", userName);
                                            q.setParameter("bankCode", bankCode);
                                            q.setParameter("choice", choice.equalsIgnoreCase("AUTHORIZE") ? "APPROVED" : "DENIED");
                                            q.setParameter("action", action);

                                            int s = q.executeUpdate();
                                            log.info("UPDATE AUTHORIZATION mobileapp_activity_log ::: " + s);

                                            s = 0;
//                                            qry = "update mobiledb.mobgate_bankrequests set status = 'AWAITING' where  bankCode =:bankCode and status='PENDING' and account = :account and msisdn =:phone and type =:action ";
                                            qry = "update mobiledb.mobgate_bankrequests set status= :choice, processedAt = now(), processedBy = :username, bankCode =:bankCode where status ='AWAITING' and account = :account and msisdn =:phone and type =:action ";// and reference =:reference";
                                            q = session.createNativeQuery(qry);

                                            q.setParameter("account", id);
                                            q.setParameter("phone", phone);
                                            q.setParameter("username", userName);
                                            q.setParameter("bankCode", bankCode);
//                                            q.setParameter("reference", reference);

                                            q.setParameter("choice",
                                                    choice.equalsIgnoreCase("AUTHORIZE") ? "APPROVED" : "DENIED");
                                            q.setParameter("action", action);
                                            s = q.executeUpdate();
                                            log.info("UPDATE AUTHORIZATION mobgate_bankrequests ::: " + s + " id:" + id + " reference: " + reference);

                                            if (s > 0) {
                                                resp.put("code", "00");
                                                resp.put("message", choice + " " + action + " request successful");
                                                rollback = false;
                                            }

                                            if (s > 0 && choice.equalsIgnoreCase("AUTHORIZE")) {

                                                int verificationFromBank = verifyNumberFromBank(bankCode, id, phone);
                                                if (phone.isEmpty()) {

                                                    resp.put("code", "01");
                                                    resp.put("message", "New phone number cannot be empty");

                                                } else if (phone.length() > 12 || phone.length() < 10) {
                                                    resp.put("code", "01");
                                                    resp.put("message", "New phone number is invalid");
                                                } else if (verificationFromBank == -1) {
                                                    resp.put("code", "01");
                                                    resp.put("message", "Could not get response from bank");
                                                } else if (verificationFromBank == 1) {
                                                    resp.put("code", "01");
                                                    resp.put("message", "Phone number not linked to bank account");
                                                } else {
                                                    String APP_NAME = BANK_NAME_MAP.get(bankCode);
                                                    if (newUssdGate) {

                                                        String accountInfo = new AutoRequest().getAPL(phone, bankCode);
                                                        String card = "";
                                                        if (!accountInfo.isEmpty()) {
                                                            String[] acct = accountInfo.split("[|]");
                                                            for (String acct1 : acct) {
                                                                String[] first = acct1.split("~", -1);

                                                                if (first[2].equals(id)) {
                                                                    card = first[0];
                                                                    break;
                                                                }
                                                            }
                                                        }
                                                        if (!card.isEmpty()) {
                                                            card = CryptographerMin.cryptPan(card, 1);
                                                        }

                                                        q = session.createNativeQuery("update mobiledb.mobile_profile set active = 1, chana = :chana, modified = now() where mobile_no = :mobile_no and card_number = :card_num and bank_code = :bank_code")
                                                                .setParameter("mobile_no", phone)
                                                                .setParameter("bank_code", bankCode)
                                                                .setParameter("chana", CryptographerMin.getSHA512(phone + "|" + appId.get(0) + "|" + card, true))
                                                                .setParameter("card_num", card);
                                                        int k = q.executeUpdate();
                                                        log.info("ACTIVATE PROFILE ::: " + k + " -- " + card + " == " + phone + " :" + bankCode);
                                                        rollback = (k == 0);
                                                    } else {
                                                        String payload = String.format("?=ACCAD*%s %s-%s %s", APP_NAME,
                                                                bankName.get(bankCode), id, id);
                                                        log.info("payload >> " + payload);
                                                        String encodepayload = URLEncoder.encode(payload);
                                                        log.info("Add Account payload >>>>  :: " + encodepayload);
                                                        String mgUrl = StringUtils.substringBeforeLast(
                                                                portalSettings.getSetting("old_mg_url"), ":");
                                                        String url = mgUrl + "?id=" + phone + "&msg=" + encodepayload;

                                                        String addAccresp = DoHTTPRequest.get2(url);
                                                        log.info(url + " Add Account Resp: " + addAccresp);

                                                        if (addAccresp != null && addAccresp.trim().toLowerCase().contains(" successful")) {
                                                            String pin = "";
                                                            String name = "Customer";
                                                            Transaction tx2 = null;
                                                            Session bankSession = null;
                                                            if (bankCode.equals("000")) {
                                                                bankSession = DbHibernate.getSession("40.17MYSQL");
                                                            } else {
                                                                bankSession = DbHibernate.getSession(bankDB.getOrDefault(bankCode.trim(), null));
                                                            }

                                                            tx2 = bankSession.beginTransaction();

                                                            String acc = new AutoRequest().getAPL(phone, bankCode);
                                                            String cardNumber = "";

                                                            String[] acct = acc.split("[|]");
                                                            for (String acct1 : acct) {
                                                                String[] first = acct1.split("~", -1);

                                                                if (!first[3].trim().isEmpty()) {
                                                                    name = first[3];
                                                                }
                                                                if (first[2].equals(id)) {
                                                                    cardNumber = first[0];
                                                                    if (!first[3].trim().isEmpty()) {
                                                                        name = first[3];
                                                                    }
                                                                    break;
                                                                }
                                                            }

                                                            if (cardNumber != null && !cardNumber.trim().isEmpty()) {

                                                                JSONObject generatePinRequest = new JSONObject(
                                                                        PinGenerator.generatePin(cardNumber));

                                                                if (generatePinRequest.getString("result")
                                                                        .equals("success")) {
                                                                    pin = generatePinRequest.getString("pin");

                                                                    Date date = new Date();
                                                                    SimpleDateFormat formatter = new SimpleDateFormat(
                                                                            "yyyy-MM-dd HH:mm:ss");
                                                                    String currentDate = formatter.format(date);

                                                                    String resetPin = "update e_cardholder set modified = :date, change_pin = '0', x_offset= :offset, card_pin= :cardpin, user_hotlist ='0', pin_missed=0 "
                                                                            + " where (card_num = :cardnum or card_account = :cardnum)";

                                                                    q = bankSession.createNativeQuery(resetPin)
                                                                            .setParameter("date", currentDate)
                                                                            .setParameter("offset", generatePinRequest.getString("x_offset"))
                                                                            .setParameter("cardpin", generatePinRequest.getString("card_pin"))
                                                                            .setParameter("cardnum", generatePinRequest.getString("cardNumber"));

                                                                    c = q.executeUpdate();
                                                                    log.info("Pin reset: " + c);
                                                                }
                                                            } else {
                                                                log.info("Account has not been created at bank");
                                                                resp.put("message", "An error occured finding account record at bank");
                                                            }
                                                            tx2.commit();

                                                            String activateProfile = "update mobiledb.m_mobile_subscriber_card set modified = now(), active = 1 where id =  (select id from (select b.id id from mobiledb.m_mobile_subscriber a "
                                                                    + "left join mobiledb.m_mobile_subscriber_card b on a.id = b.subscriber_id left join  mobiledb.m_mobileapp_properties c on c.id = a.appId "
                                                                    + "where a.mobile_no = :mobile_no and c.bank_code = :bank_code and card_number = :card_number) as t1) ";

                                                            q = session.createNativeQuery(activateProfile)
                                                                    .setParameter("mobile_no", phone)
                                                                    .setParameter("bank_code", bankCode)
                                                                    .setParameter("card_number", CryptographerMin.cryptPan(cardNumber, 1));
                                                            int hj = q.executeUpdate();
                                                            log.info("ACTIVATE " + phone + " " + bankCode + " " + hj + "");

                                                            if (c > 0) {
                                                                String sms_header = config
                                                                        .getProperty(bankCode.trim() + "_SMS");
                                                                sms_header = sms_header.isEmpty() ? "Etranzact"
                                                                        : sms_header;
                                                                String smsResponse = new Alert().sendSMS(phone,
                                                                        createMsg(name, pin, "",
                                                                                (action.equalsIgnoreCase("pinreset")
                                                                                ? "pin_reset_template"
                                                                                : "aa_sms_template"),
                                                                                bankCode.trim()),
                                                                        sms_header);
                                                                if (!smsResponse.isEmpty()) {
                                                                    if (!smsResponse.equalsIgnoreCase("Invalid")) {
                                                                        log.info("SMSResponse ::: " + smsResponse);
                                                                        JSONObject smsResult = new JSONObject(smsResponse);

                                                                        if (!smsResult.isEmpty()
                                                                                && smsResult.optString("responseCode", "99")
                                                                                        .equals("00")) {

                                                                            resp.put("code", "00");
                                                                            resp.put("message",
                                                                                    action.equalsIgnoreCase("addaccount")
                                                                                    ? "Add account successful. Pin sent: 1"
                                                                                    : "Pin reset successful. Sent: 1");
                                                                            rollback = false;
                                                                        } else {
                                                                            resp.put("message",
                                                                                    action.equalsIgnoreCase("addaccount")
                                                                                    ? "Add account unsuccessful. Pin sent: 0"
                                                                                    : "Pin reset unsuccessful. Sent: 0");
                                                                        }
                                                                    } else {
                                                                        resp.put("message",
                                                                                action.equalsIgnoreCase("addaccount")
                                                                                ? "Add account unsuccessful. Pin sent: 0"
                                                                                : "Pin reset unsuccessful. Sent: 0");
                                                                    }
                                                                } else {
                                                                    resp.put("message",
                                                                            action.equalsIgnoreCase("addaccount")
                                                                            ? "Add account unsuccessful. Pin sent: 0"
                                                                            : "Pin reset unsuccessful. Sent: 0");
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            } else if (s > 0) {
                                                resp.put("code", "00");
                                                resp.put("message", (!role.equals("2") ? (choice + " ") : "") + action
                                                        + " request successful");
                                                rollback = false;
                                            }
                                        } else {

                                            resp.put("code", "00");
                                            resp.put("message", (!role.equals("2") ? (choice + " ")
                                                    : (!choice.toLowerCase().contains("authorize") ? "initiate " : ""))
                                                    + action + " request successful");
                                            rollback = false;
                                        }
                                    }
                                } else {

                                    qry = "update mobiledb.mobgate_bankrequests set status= :choice, processedAt = now(), processedBy = :username, bankCode =:bankCode where status='PENDING' and account = :account and msisdn =:phone and type =:action and reference =:reference";
                                    q = session.createNativeQuery(qry);

                                    q.setParameter("account", id);

                                    q.setParameter("phone", phone);
                                    q.setParameter("username", userName);
                                    q.setParameter("bankCode", bankCode);
                                    q.setParameter("reference", reference);

                                    q.setParameter("choice",
                                            choice.equalsIgnoreCase("AUTHORIZE") ? "APPROVED" : "DENIED");
                                    q.setParameter("action", action);
                                    int s = q.executeUpdate();
                                    log.info("UPDATE AUTHORIZATION ::: " + s);

                                    if (s > 0) {
                                        resp.put("code", "00");
                                        resp.put("message", choice + " " + action + " request successful");
                                        rollback = false;
                                    }
                                }
                            }
                        } else {
                            rollback = false;
                        }

                        if (rollback) {
                            try {
                                tx.rollback();

                            } catch (Exception e) {
                                log.error(e.getMessage(), e);
                            }
                        } else {
                            tx.commit();
                        }
                        break;

                    default:
                        resp.put("message", choice + " " + action + " Failed");
                        break;
                }
            } catch (Exception ex) {
                log.error(ex.getMessage(), ex);
                String msg = ex.getMessage();
                log.info("message: " + msg);
                if (msg.contains("ConstraintViolationException")) {
                    log.info("Database Exception>> Duplicate Record");
                    resp.put("message", "Request exists OR waiting authorization");
                } else {
                    log.info("Database Exception>> an error occured");
                    resp.put("message", "An error occured. Please try again");
                }

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
            }
        }
        final String mobile = phone;

        if (role.equals("2") && mcList.contains(bankCode) && resp.optString("code", "06").equals("00")) {
            String msg = resp.getString("message");
            resp.put("message", msg.replaceAll(" successful", " submitted successfully"));
        }
        final String error = resp.getString("message");

        new Thread(() -> {
            AuditTrail audit = new AuditTrail(userId, finalType + " for " + mobile + " : " + error, "mobileapp", null,
                    action, ipAddress);
            new AuditDao().insertIntoAuditTrail(audit);
        }).start();

        return resp;

    }

    public JSONObject sendNumberModificationOtp(String jsonString) {
        log.info("sendNumberModificationOtp received >> " + jsonString);
        Gson j = new Gson();
        ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);

        String bankCode = apiData.getBank_code();
        String userId = apiData.getUser_id();
        String ipAddress = apiData.getIpAddress();

        String id = apiData.getClientId();
        String account_number = apiData.getAccountNumber();
        String account_id = String.valueOf(CryptographerMin.decodeId(id));

        String phone = apiData.getMobile_no();

        String new_mobile = apiData.getSubscriberId();

        String lineType = apiData.getLineType();
        String alias = "";
        String app_name = "";
        String name = "";

        List<String> appId = apiData.getAppId();
        String qry = "";
        JSONObject resp = new JSONObject();
        resp.put("code", "01");
        resp.put("message", "OTP failed");
        List<Object[]> results = new ArrayList<>();

        final String finalType = "Number Modification Otp Request ";

        if (new_mobile == null) {
            new_mobile = "";
        }
        new_mobile = new_mobile.trim();
        if (new_mobile.isEmpty()) {

            resp.put("code", "01");
            resp.put("message", "New phone number cannot be empty");

        } else if (new_mobile.length() > 12 || new_mobile.length() < 10) {
            resp.put("code", "01");
            resp.put("message", "New phone number is invalid");
        } else {

            Transaction tx = null;
            boolean newUssdGate = CollectionUtils.containsAny(ussdGateBanks, appId);
            Session session = DbHibernate.getSession("MOBILEDBMYSQL");
            boolean rollback = true;
            Query q;
            try {

                String otp = PasswordGenerator
                        .generateOtp(Integer.valueOf(portalSettings.getSetting("otp_length").split(":")[0]));

                tx = session.beginTransaction();

                int i = 0;
                String query = "";
                String status = "";
                int app_id = 0;
                String acc = "";

                if (newUssdGate) {
                    query = "select id, active, '' alias, app_id, app_name appId2, card_number, u_sess  from mobiledb.mobile_profile where id = :id and mobile_no = :phone "
                            + (!appId.contains("ALL") ? " and app_id in(:appId) " : "");
                } else {
                    query = "select a.id, b.active, b.alias, a.appid appId, c.appid appId2, b.card_number, b.request_byip from mobiledb.m_mobile_subscriber a,"
                            + " mobiledb.m_mobile_subscriber_card b, mobiledb.m_mobileapp_properties c where a.id = b.subscriber_id "
                            + " and a.appid = c.id and a.mobile_no = :phone and a.id = :id "
                            + (!appId.contains("ALL") ? " and a.appid in(:appId) " : "");
                }

                q = session.createNativeQuery(query);
                q.setParameter("phone", phone);
                q.setParameter("id", account_id);
                if (!appId.contains("ALL")) {
                    q.setParameter("appId", appId);
                }

                results = q.getResultList();
                for (Object[] record : results) {

                    app_id = Integer.parseInt(record[3].toString());

                    name = checkNull(record[6]).split(" ")[0];

                    query = "update mobiledb.number_modification_log set otp_status = 1 where original_number = :original_number and otp_status = 0 "
                            + " and app_id in(:app_id) ";

                    q = session.createNativeQuery(query);
                    q.setParameter("original_number", phone);
                    q.setParameter("app_id", app_id);

                    int y = q.executeUpdate();

                    int c = 0;

                    qry = "insert into mobiledb.number_modification_log(original_number, app_id, new_number, mac,otp_status) values (:original_number, :app_id, :new_number, :mac, :otp_status)";
                    q = session.createNativeQuery(qry);
                    q.setParameter("app_id", app_id);
                    q.setParameter("original_number", phone);
                    q.setParameter("new_number", new_mobile);
                    q.setParameter("otp_status", 0);
                    q.setParameter("mac", CryptographerMin.getSHA512(phone + app_id + new_mobile + otp));
                    int s = q.executeUpdate();

                    if (s > 0) {
                        String sms_header = config.getProperty(bankCode.trim() + "_SMS");
                        log.info("SMS: HEADER = " + sms_header);
                        sms_header = sms_header.isEmpty() ? "Etranzact" : sms_header;
                        log.info("SMS: " + sms_header);

                        String smsResponse = new Alert().sendSMS(phone,
                                createMsg(name, otp,
                                        (account_number == null || account_number.trim().isEmpty() ? ""
                                        : " for " + account_number) + " from " + phone + " to " + new_mobile,
                                        "nm_sms_template", bankCode.trim()),
                                sms_header);
                        if (!smsResponse.isEmpty()) {
                            if (!smsResponse.equalsIgnoreCase("Invalid")) {
                                log.info("SMSR ::: " + smsResponse);
                                JSONObject smsResult = new JSONObject(smsResponse);

                                if (!smsResult.isEmpty() && smsResult.optString("responseCode", "99").equals("00")) {

                                    resp.put("code", "00");
                                    resp.put("message", "Otp sent: 1");
                                    rollback = false;
                                } else {
                                    resp.put("message", "Could not send otp");
                                }
                            } else {
                                resp.put("message", "Invalid number. Could not send pin");
                            }
                        } else {
                            resp.put("message", "Could not send otp");
                        }
                    }

                    if (rollback) {
                        try {
                            tx.rollback();

                        } catch (Exception rt) {
                            log.error("", rt);
                        }
                    } else {
                        tx.commit();
                    }
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
            }
        }
        final String mobile = phone;
        final String maskedAccount = alias;
        final String error = resp.getString("message");

        new Thread(() -> {
            AuditTrail audit = new AuditTrail(userId,
                    finalType + " for " + mobile + " - " + maskedAccount + " : " + error, "cms", null,
                    "number modification otp", ipAddress);
            new AuditDao().insertIntoAuditTrail(audit);
        }).start();

        return resp;

    }

    public int handleSQLDuplicateErrorAndNoResultFound(String phone, String action, String reason, String bankCode,
            String account) {
        Session session = null;
        try {
            Query q;

            String qry = "select id, account, status from mobiledb.account_activity_log where phone =:phone and account =:account and status = 'pending' and bank_code = :bankCode limit 1";

            session = DbHibernate.getSession("MOBILEDBMYSQL");
            q = session.createNativeQuery(qry);
            q.setParameter("phone", phone);
            q.setParameter("account", account);
            q.setParameter("bankCode", bankCode);

            Object[] record = (Object[]) q.getSingleResult();

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

    public int handleMobileAppDuplicateErrorAndNoResultFound(String phone, String action, String reason,
            String bankCode, String account, Session oldSession) {
        Session session = null;

        try {
            Query q;
            if (oldSession != null) {
                session = oldSession;
            } else {
                session = DbHibernate.getSession("MOBILEDBMYSQL");
            }

            String qry = "";

            qry = "select id,phone, status from mobiledb.mobileapp_activity_log where phone =:phone and account = :account and status in ('PENDING') and bank_code = :bankCode "
                    + (action.equalsIgnoreCase("addaccount") ? " and action = :action " : "")
                    + " limit 1";

            q = session.createNativeQuery(qry);
            q.setParameter("phone", phone);
            q.setParameter("account", account);
            q.setParameter("bankCode", bankCode);
            if (action.equalsIgnoreCase("addaccount")) {
                q.setParameter("action", action);
            }

            Object[] record = (Object[]) q.getSingleResult();

            if (record != null) {
                log.info("Pending Request or awaiting authorization");
                return 1;
            }

        } catch (NoResultException ex) {
            return 0;
        } catch (Exception ex) {
            return 2;
        } finally {
            if (session != null && oldSession == null) {
                try {
                    session.close();
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
        return 0;
    }

    public String createMsg(String name, String pin, String extra, String type, String bankCode) {

        log.info("Fetching template: " + type + " for " + bankCode);
        String message = StringUtils.substringBeforeLast(portalSettings.getSetting(bankCode + "_" + type, ""), ":");
        if (message.isEmpty()) {
            log.info("Empty message : " + type + " for " + bankCode + " -- Defaulting to " + type);

            message = StringUtils.substringBeforeLast(portalSettings.getSetting(type, ""), ":");
        }
        log.info("Final Template: " + message);
        if (name != null) {
            name = name.replaceAll("\\s\\s", " ").trim();
        }
        message = message.replace("<NAME>", name);
        message = message.replace("<PIN>", pin);
        message = message.replace("<EXTRA>", extra);
        return message;
    }

    protected String checkNull(Object data) {

        if (data != null && !data.equals("")) {
            return data.toString().trim();
        }
        return "NULL";
    }

    protected int checkNullNumber(Object data) {

        if (data != null && !data.equals("")) {
            return Integer.parseInt(data.toString());
        }
        return 0;
    }

    protected Date convertDate(String date) {
        Date convertedDate = null;
        try {
            convertedDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(date);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return convertedDate;
    }

    public static int verifyNumberFromBank(String bankCode, String accountNumber, String phone) {
        int status = -1;
        log.info("VERIFYING :" + bankCode + " - " + accountNumber + " - " + phone);

        String[] res = new AutoRequest().validateAccount(bankCode + accountNumber);
        log.info("Phone verification from bank: " + Arrays.toString(res));
        if (res[0].equals("0")) {

            String linkedPhone = res[1].split("\\|", -1)[0];
            if (linkedPhone.length() == 10 && linkedPhone.startsWith("0")) {
                linkedPhone = "233" + linkedPhone.substring(1);
            } else if (linkedPhone.length() == 11 && linkedPhone.startsWith("00")) {
                linkedPhone = "233" + linkedPhone.substring(2);
            }
            status = linkedPhone.equals(phone) ? 2 : 1;
        }
        return status;
    }

    public static String getNameFromBank(String bankCode, String accountNumber) {
        String name = "";
        log.info("VERIFYING :" + bankCode + " - " + accountNumber);
        String account = "";
        if (bankCode.equals("004")) {
            account = "004999" + accountNumber;
        } else {
            account = bankCode + accountNumber;
        }
        String[] res = new AutoRequest().validateAccount(account);
        log.info("Account verification from bank: " + Arrays.toString(res));
        if (res[0].equals("0")) {

            name = res[1].split("\\|", -1)[1];
        }
        return name;
    }

//    public List<NODES> fetchAccounts(String phone, String bank) {
//        List<NODES> acc = new ArrayList<>();
//
//        try {
//            String accountInfo = new AutoRequest().getAPL(phone, bank);
//
//            String[] acct = accountInfo.split("[|]");
//            Map<String, String> accountMap = new HashMap<>();
//            for (String acct1 : acct) {
//                String[] first = acct1.split("~", -1);
//
//                accountMap.put(first[0], first[2]);
//            }
//            
//        } catch (Exception e) {
//
//        }
//        return acc;
//    }
//    public String getNameFromBank(ApiPostData apiData) {
//
//        String bankCode = apiData.getBank_code().isEmpty() ? bankAppIdList.getOrDefault(apiData.getLineType(), "")
//                : apiData.getBank_code();
//
//        String id = String.valueOf(CryptographerMin.decodeId(apiData.getClientId()));
//        String phone = apiData.getMobile_no();
//        String app = apiData.getLineType();
//        String alias = "";
//        String app_name = "";
//        String name = "";
//        String accountBank = "";
//        String accountNumber = "";
//        String accountPhone = "";
//        String card_num = "";
//        String enc_card_num = "";
//        List<String> appId = apiData.getAppId();
//        String qry = "";
//
//        List<Object[]> results = new ArrayList<>();
//
//        boolean newUssdGate = CollectionUtils.containsAny(ussdGateBanks, appId);
//
//        Session session = DbHibernate.getSession("MOBILEDBMYSQL");
//        Query q;
//        try {
//
//            String query = "";
//            if (newUssdGate) {
//                query = "select id, active, '' alias, app_id, app_name appId2, card_number, ifnull(u_sess, 'Customer'), bank_code  from mobiledb.mobile_profile where id = :id and mobile_no = :phone "
//                        + (!appId.contains("ALL") ? " and app_id in(:appId) " : "");
//            } else {
//                query = "select b.id, b.active, b.alias, a.appid appId, c.appid appId2, b.card_number, ifnull(b.request_byip,'Customer'), c.bank_code from mobiledb.m_mobile_subscriber a,"
//                        + " mobiledb.m_mobile_subscriber_card b, mobiledb.m_mobileapp_properties c where a.id = b.subscriber_id "
//                        + " and a.appid = c.id and a.mobile_no = :phone and b.id = :id "
//                        + (!appId.contains("ALL") ? " and a.appid in(:appId) " : "");
//            }
//
//            q = session.createNativeQuery(query);
//            q.setParameter("phone", phone);
//            q.setParameter("id", id);
//            if (!appId.contains("ALL")) {
//                q.setParameter("appId", appId);
//            }
//
//            results = q.getResultList();
//
//        } catch (Exception ex) {
//            log.error(ex.getMessage(), ex);
//
//        } finally {
//            try {
//                if (session != null && session.isOpen()) {
//                    session.close();
//                }
//            } catch (Exception e) {
//                log.error(e.getMessage(), e);
//            }
//
//        }
////        if (newUssdGate) {
//        String acc = new AutoRequest().getAPL(phone, bankAppIdList.getOrDefault(app, ""));
////        }
//        for (Object[] record : results) {
//            id = record[0].toString();
//            alias = record[2].toString();
//            accountBank = record[7].toString();
////            String acc = "";
//
//            try {
//                enc_card_num = record[5].toString();
//                card_num = CryptographerMin.cryptPan(enc_card_num, 2);
//
//                HashMap<String, String> map = new HashMap<>();
//                String[] acct = acc.split("[|]");
//                for (String acct1 : acct) {
//                    String[] first = acct1.split("~", -1);
//                    String cardNum = first[0];
//                    String maskedAcc = first[1];
//                    accountNumber = first[2];
//
//                    map.put(cardNum, String.format("%s", maskedAcc));
//                }
//                alias = map.getOrDefault(card_num, "");
//
//            } catch (Exception e) {
//                log.error(e.getMessage(), e);
//            }
//        }
//
//        name = getNameFromBank(bankCode, accountNumber);
//
//        return name;
//
//    }
    public String getNameFromBank(ApiPostData apiData) {

        String bankCode = apiData.getBank_code().isEmpty() ? bankAppIdList.getOrDefault(apiData.getLineType(), "")
                : apiData.getBank_code();

        String id = String.valueOf(CryptographerMin.decodeId(apiData.getClientId()));
        String phone = apiData.getMobile_no();
        String app = apiData.getLineType();
        String alias = "";
        String app_name = "";
        String name = "";
        String accountBank = "";
        String accountNumber = "";
        String accountPhone = "";
        String card_num = "";
        String enc_card_num = "";
        List<String> appId = apiData.getAppId();
        String qry = "";

        List<Object[]> results = new ArrayList<>();

        boolean newUssdGate = CollectionUtils.containsAny(ussdGateBanks, appId);

        Session session = DbHibernate.getSession("MOBILEDBMYSQL");
        Query q;
        try {

            String query = "";
            if (newUssdGate) {
                query = "select id, active, '' alias, app_id, app_name appId2, card_number, ifnull(u_sess, 'Customer'), bank_code  from mobiledb.mobile_profile where id = :id and mobile_no = :phone "
                        + (!appId.contains("ALL") ? " and app_id in(:appId) " : "");
            } else {
                query = "select b.id, b.active, b.alias, a.appid appId, c.appid appId2, b.card_number, ifnull(b.request_byip,'Customer'), c.bank_code from mobiledb.m_mobile_subscriber a,"
                        + " mobiledb.m_mobile_subscriber_card b, mobiledb.m_mobileapp_properties c where a.id = b.subscriber_id "
                        + " and a.appid = c.id and a.mobile_no = :phone and b.id = :id "
                        + (!appId.contains("ALL") ? " and a.appid in(:appId) " : "");
            }

            q = session.createNativeQuery(query);
            q.setParameter("phone", phone);
            q.setParameter("id", id);
            if (!appId.contains("ALL")) {
                q.setParameter("appId", appId);
            }

            results = q.getResultList();

        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);

        } finally {
            try {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }

        }

        for (Object[] record : results) {
//        if (newUssdGate) {
            String acc = new AutoRequest().getAPL(phone, bankAppIdList.getOrDefault(app, ""));
            String[] acct = acc.split("[|]");
            for (String acct1 : acct) {
                String[] first = acct1.split("~", -1);
                accountNumber = first[2];

                name = getNameFromBank(bankCode, accountNumber);
                if (!name.isEmpty()) {
                    break;
                }
            }
            break;
        }

        return name;

    }

    public List<NODES> getUserAccounts(ApiPostData apiData) {

        String bankCode = apiData.getBank_code().isEmpty() ? bankAppIdList.getOrDefault(apiData.getLineType(), "")
                : apiData.getBank_code();

        String phone = apiData.getMobile_no();
        String app = apiData.getLineType();
        String type = apiData.getTransType();
        String card_num = apiData.getCard_num();
        String alias = "";

        List<String> appId = apiData.getAppId();
        String qry = "";

        List<NODES> accountList = new ArrayList<>();

        boolean newUssdGate = CollectionUtils.containsAny(ussdGateBanks, appId);

        String accountInfo = new AutoRequest().getAPL(phone, bankCode);

        if (!accountInfo.isEmpty()) {
            String[] acct = accountInfo.split("[|]");
            Map<String, String> accountMap = new HashMap<>();
            for (String acct1 : acct) {
                String[] first = acct1.split("~", -1);

                accountMap.put(first[0], first[2]);
            }
            if (card_num != null && !card_num.isEmpty()) {
                NODES a = new NODES();
                String c = CryptographerMin.cryptPan(card_num, 2);
//                        log.info("Checking::: " + c);
                a.setId(accountMap.getOrDefault(c, "N/A"));
                a.setName(c);
                accountList.add(a);
            } else {

//        log.info("Accountmap: " + accountMap.toString());
                Session session = DbHibernate.getSession("MOBILEDBMYSQL");
                Query q;
                List<String> accountNumbers = new ArrayList<>();
                try {

                    String query = "";

                    if (newUssdGate) {
                        query = "select  group_concat(distinct card_number) from mobiledb.mobile_profile where mobile_no = :phone and profile_type in (:type,3) "
                                + (!appId.contains("ALL") ? " and app_id in(:appId) " : "")
                                + " group by mobile_no";
                    } else {
                        query = "select group_concat(distinct b.card_number) from mobiledb.m_mobile_subscriber a,"
                                + " mobiledb.m_mobile_subscriber_card b, mobiledb.m_mobileapp_properties c where a.id = b.subscriber_id "
                                + " and a.appid = c.id and a.mobile_no = :phone "
                                + (!appId.contains("ALL") ? " and a.appid in(:appId) " : "")
                                + " group by a.mobile_no";
                    }

                    System.out.println("D: " + appId);
                    q = session.createNativeQuery(query);

                    q.setParameter("phone", phone);

                    if (newUssdGate) {
                        q.setParameter("type", type);
                    }

                    if (!appId.contains("ALL")) {
                        q.setParameter("appId", appId);
                    }

                    String accounts = (String) q.getSingleResult();

//            log.info("Account: " + alias);
//            log.info("ACCOUNTS: " + accounts);
                    accountNumbers = Arrays.asList(accounts.split(","));

                    if (!accountNumbers.isEmpty()) {

                        accountNumbers.stream().forEach(card -> {
                            NODES a = new NODES();
                            try {
                                String c = CryptographerMin.cryptPan(card, 2);
//                        log.info("Checking::: " + c);
                                a.setId(accountMap.getOrDefault(c, "N/A"));
                                a.setName(c);
                                accountList.add(a);
                            } catch (Exception e) {
                                log.error("Error mapping to account", e);
                            }
                        });
                    }

                } catch (NoResultException ex) {
                    log.error("Accounts not found");
                } catch (Exception ex) {
                    log.error(ex.getMessage(), ex);

                } finally {
                    try {
                        if (session != null && session.isOpen()) {
                            session.close();
                        }
                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                    }

                }
            }
        } else if (accountInfo.isEmpty() && type.equals("2")) {
//            check in mobgate profile for encrypted account
            Session session = DbHibernate.getSession("MOBILEDBMYSQL");

            String sql = "select id, pin_salt from mobiledb.mobgate_profile where mobile_no = :mobile_no and bank_code = :bank_code";
            Query q = session.createNativeQuery(sql);

            q.setParameter("mobile_no", phone).setParameter("bank_code", bankCode);
            List<Object[]> results = q.getResultList();
            String accountNumber = "N/A";

            for (Object[] record : results) {
                accountNumber = checkNull(record[1]);
                accountNumber = CryptographerMin.AESCBCDecrypt(accountNumber,
                        mobgateKEY, mobgateIV);

            }
            NODES a = new NODES();
//                        log.info("Checking::: " + c);
            a.setId(accountNumber);
            a.setName(accountNumber);
            accountList.add(a);
        }

        return accountList;

    }

    public static void getBanks() {

        String qry = "";

        List<Object[]> resp = new ArrayList<>();
        qry = "select ifnull(issuer_acct, issuer_code), issuer_name from ecarddb.e_issuer order by issuer_name asc";

        Session session = DbHibernate.getSession("40.15MYSQL");
        try {
            Query q = session.createNativeQuery(qry);

            resp = q.getResultList();

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }

        for (final Object[] record : resp) {
            try {
                bankList.put(record[0].toString(), record[1].toString());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }

        }
        bankList.put("686", "MTN MobileMoney");
        bankList.put("863", "Vodafone Cash");
        bankList.put("844", "AirtelTigo Money");
        bankList.put("466", "GMoney");

    }

    public static List<NODES> getTransactionCategories() {
        List<NODES> catList = new ArrayList<>();
        String qry = "";

        List<Object[]> resp = new ArrayList<>();
        qry = "SELECT id, pageTitle alias FROM mobiledb.mobgate_menulist where id > 0 order by alias asc";

        Session session = DbHibernate.getSession("MOBILEDBMYSQL");
        try {
            Query q = session.createNativeQuery(qry);

            resp = q.getResultList();

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }

        for (final Object[] record : resp) {
            NODES cat = new NODES();
            try {

                cat.setId(record[0].toString());
                cat.setName(record[1].toString());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }

            catList.add(cat);
        }
        return catList;
    }

    public static List<NODES> getTransactionTypes(String menuId) {
        List<NODES> typeList = new ArrayList<>();

        String qry = "";

        List<Object[]> resp = new ArrayList<>();
        qry = "SELECT id, name FROM mobiledb.mobgate_menulist_items "
                + (menuId.equalsIgnoreCase("ALL") ? "" : " where menuId = :menuId ")
                + " order by name asc";

        Session session = DbHibernate.getSession("MOBILEDBMYSQL");
        try {
            Query q = session.createNativeQuery(qry);
            if (!menuId.equalsIgnoreCase("ALL")) {
                q.setParameter("menuId", menuId);
            }
            resp = q.getResultList();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }

        for (final Object[] record : resp) {
            NODES type = new NODES();
            try {

                type.setId(record[0].toString());
                type.setName(record[1].toString());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }

            typeList.add(type);
        }
        return typeList;
    }

}
