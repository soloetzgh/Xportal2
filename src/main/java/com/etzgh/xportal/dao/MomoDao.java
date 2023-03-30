package com.etzgh.xportal.dao;

import com.etzgh.xportal.model.ApiPostData;
import com.etzgh.xportal.model.AuditTrail;
import com.etzgh.xportal.model.Bank;
import com.etzgh.xportal.model.MobileMoney;
import com.etzgh.xportal.model.MobileMoneyUpdate;
import com.etzgh.xportal.model.MomoUpdate;
import com.etzgh.xportal.utility.Config;
import com.etzgh.xportal.utility.DbHibernate;
import com.etzgh.xportal.utility.DoHTTPRequest;
import com.etzgh.xportal.utility.GeneralUtility;
import com.etzgh.xportal.utility.HashNumber;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class MomoDao {

    private static final GeneralUtility utility = new GeneralUtility();
    private static final Gson gson = new Gson();
    private static final Logger log = Logger.getLogger(MomoDao.class.getName());
    private static final Map<String, String> banks;
    protected static final Map<String, String> terminals;
    protected static final Map<String, String> merchantCodes;
    private static final String justpayUrl;
    private static final String mpayUrl;

    public static void main(String[] args) {
        String json = "{\"startDate\":\"2021-08-13 15:00\",\"endDate\":\"2021-08-13 23:59\",\"merchant\":\"\",\"product\":\"\",\"accountNumber\":\"\",\"status\":\"ALL\",\"service\":\"transactions\",\"action\":\"\",\"apiSecret\":\"\",\"apiCode\":\"\",\"userCode\":\"[1],[17]|0060000290:0067620000000010,[2000000000000048]|00000000000000000002:0069900596470004,[2000000000000049],[2000000000000053]|ALL,[2000000000000054],[2000000000000060]|000,[2000000000000062]|ALL,[2000000000000063]|KNUST,[2000000000000064]|ALL,[22],[2500000000000048]|3,[2500000000000049]|2,[2500000000000050]|ALL,[2500000000000053]|3,[29],[51]|2,[71]|0230010002,[91]|ALL\",\"admin\":\"\",\"type_desc\":\"\",\"role_id\":\"\",\"type_id\":\"[0],[1],[2],[3],[4],[5],[6],[7],[8],[9],[10],[11],[20],[21],[24],[25],[26],[27],[28],[30],[31],[33],[34],[39],[40],[41],[43],[44]\",\"userName\":\"Eugene.Arthur\",\"ClientId\":\"\",\"user_id\":\"9000000000000490\",\"pageNumber\":1,\"rowsPerPage\":1,\"uniqueTransId\":\"\",\"trans_code\":\"\",\"transType\":\"D\",\"bank_code\":\"ALL\",\"subscriberId\":\"\",\"trans_status\":\"\",\"roleList\":[],\"channel\":\"09\",\"bank\":\"\",\"biller\":\"\",\"transId\":\"\",\"bankid\":\"\",\"from_source\":\"\",\"card_num\":\"\",\"optionType\":\"ALL\",\"payType\":\"\",\"wcMerchant\":\"\",\"destination\":\"\",\"lineType\":\"ALL\",\"trans_channel\":\"\",\"pan\":\"\",\"terminal_id\":\"\",\"card_account\":\"\",\"cop_issuercode\":\"\",\"cop_role_id\":\"\",\"cop_company_id\":\"\",\"mobile_no\":\"\",\"phone\":\"\",\"type\":\"\",\"amount\":\"\",\"amount2\":\"\",\"refundStatus\":\"\",\"partialReversal\":\"\",\"reversalReason\":\"\",\"ipAddress\":\"0:0:0:0:0:0:0:1\",\"userData\":\"\",\"appId\":[],\"company\":\"\",\"origin\":\"\",\"options\":{}}";
        new MomoDao().getMomoTransactions(json);
        System.exit(0);
    }

    static {
        banks = convertListAfterJava8(new AppDao().getBanks());
        terminals = getTerminals();
        merchantCodes = getMerchantCodes();
        justpayUrl = new Config().getProperty("JUSTPAY_URL");
        mpayUrl = new Config().getProperty("MPAY_URL");
    }

    public List<MobileMoney> getMomoTransactions(String jsonString) {

        log.info("Mobile Money trx request received >>  " + jsonString);
        Gson j = new Gson();

        ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);
        String start_dt = apiData.getStartDate();
        String end_dt = apiData.getEndDate();
        String bank = apiData.getBank_code();

        String reference = apiData.getUniqueTransId();
        String msisdn = apiData.getFrom_source();
        String network = apiData.getLineType();
        String paymenttype = apiData.getTransType();
        String mode = apiData.getChannel();
        String status = apiData.getStatus();
        String alert = apiData.getOptionType();

        String user_code = apiData.getUserCode();

        String type_id = apiData.getType_id();
        final String searchKey = apiData.getSearchKey();
        final String service = apiData.getService();
        List<String> roleList = new ArrayList<>();

        List<MobileMoney> records = new ArrayList<>();

        String userRoles = "";

        if (msisdn == null) {
            msisdn = "";
        }

        if (!msisdn.isEmpty() && msisdn.startsWith("0") && msisdn.length() == 10) {
            msisdn = "233" + msisdn.substring(1);
        }

        if (alert == null || alert.trim().isEmpty()) {
            alert = "ALL";
        }
        if (paymenttype == null || paymenttype.trim().isEmpty()) {
            paymenttype = "ALL";
        }
        if (network == null || network.trim().isEmpty()) {
            network = "ALL";
        }
        if (mode == null || mode.trim().isEmpty()) {
            mode = "ALL";
        }
        if (bank == null || bank.trim().isEmpty() || bank.trim().equals("000")) {
            bank = "ALL";
        }

        if (service.equalsIgnoreCase("search") && searchKey != null && searchKey.trim().isEmpty()) {
            return records;
        }

        if (service.equalsIgnoreCase("search")) {
            reference = searchKey;
        }
        if (reference == null) {
            reference = "";
        }

        if (!type_id.isEmpty()) {

            if (type_id.contains("[0]") || type_id.contains("[6]")) {

            } else if (type_id.contains("[5]")) {
                userRoles = utility.getRoleParameters("[91]", user_code);

                roleList = Arrays.asList(userRoles.split("~"));
                if (network.equalsIgnoreCase("ALL")) {

                } else {
                    if (roleList.contains(network)) {
                        roleList = new ArrayList<>(roleList);
                        roleList.add(network);
                    }
                }
            } else {
                return records;
            }
        } else {
            return records;
        }

        String query = "select distinct(a.reference) , a.client , a.msisdn , (case when a.paymenttype='D' then 'DEBIT' when a.paymenttype = 'C' then 'CREDIT' else a.paymenttype end) as paymenttype, a.amount , a.trnxdate , (case when left(a.reference,5) = '01ESA' then 'WEBCONNECT' when  left(a.reference,2) in ('02','09') then 'TRANSFER' else  ifnull(UPPER(b.mainoptions),'') end) mainoptions, (case when a.respcode ='00' then 'SUCCESSFUL' when a.respcode in ('58','66') then 'PENDING' when a.respcode ='06' then 'FAILED' else a.respcode end) respcode, a.respcode as respcode2, a.cardno, a.cardno as cardno2, "
                + "a.cardno as code, (case when left(a.channel,2) in ('09') then 'FUNDGATE' when left(a.channel,2) = '03' then 'POS' WHEN left(a.channel,2) = '01' then 'WEBCONNECT' WHEN left(a.channel,2) = '07' then 'JUSTPAY' WHEN left(a.channel,2) = '02' then 'MOBILE' WHEN left(a.channel,2) = '08' then 'CORPORATEPAY' WHEN left(a.channel,2) = '04' then 'ATM' else left(a.channel, 2) end) channel, ifnull(a.clientid,''), (case when a.respcode in('0','00') then 'SUCCESSFUL' else ifnull(a.narration,'') end) narration,a.switchcode , "
                + "(case when a.flag='0' then 'COMPLETED' when a.flag='7' then 'REVERSING' when a.flag='8' then 'REVERSED' else ifnull(a.flag, '') end) flag, ifnull(a.clientcode,'') clientcode  from telcodb.mobilemoney a left join telcodb.just_pay b on a.reference = b.reference where 1=1 "
                + (service.equalsIgnoreCase("search") ? "  AND (a.reference = :reference or a.clientid = :reference) " : " and a.trnxdate between :start_dt  and :end_dt ")
                + (!reference.trim().isEmpty() ? " and (a.reference = :reference or a.clientid = :reference) " : "")
                + (!msisdn.trim().isEmpty() ? " and a.msisdn = :msisdn " : "")
                + (!status.equalsIgnoreCase("ALL") ? (!status.equalsIgnoreCase("successful") ? (!status.equalsIgnoreCase("pending") ? (!status.equalsIgnoreCase("failed")
                ? (status.equalsIgnoreCase("682") ? " and a.clientcode = '682' " : "") + "" : " and a.respcode not in ('00','58') and a.clientcode not in ('682') ") : " and a.respcode not in ('58') ") : " and a.respcode = '00' ") : "")
                + (!network.equalsIgnoreCase("ALL") ? " and a.client = :network " : "")
                + (!alert.equalsIgnoreCase("ALL") ? " and a.flag = :alert " : "")
                + (!paymenttype.equalsIgnoreCase("ALL") ? " and a.paymenttype = :paymenttype  " : "")
                + (!mode.equalsIgnoreCase("ALL") ? " and a.channel = :mode " : "")
                + (!bank.equalsIgnoreCase("ALL") ? " and left(a.cardno,3) = :bank " : "")
                + " order by a.trnxdate desc";

        Session session = DbHibernate.getSession("40.9MYSQL");

        try {

            Query q = session.createNativeQuery(query);

            if (!service.equalsIgnoreCase("search")) {
                q.setParameter("start_dt", (Object) start_dt)
                        .setParameter("end_dt", (Object) end_dt);
            } else {
                q.setParameter("reference", (Object) reference.trim());
            }

            if (!msisdn.trim().isEmpty()) {
                q.setParameter("msisdn", msisdn.trim());
            }

            if (!bank.equalsIgnoreCase("ALL")) {
                q.setParameter("bank", bank);
            }
            if (!mode.equalsIgnoreCase("ALL")) {
                q.setParameter("mode", mode);
            }
            if (!paymenttype.equalsIgnoreCase("ALL")) {
                q.setParameter("paymenttype", paymenttype);
            }
            if (!network.equalsIgnoreCase("ALL")) {
                q.setParameter("network", network);
            }
            if (!alert.equalsIgnoreCase("ALL")) {
                q.setParameter("alert", network);
            }
            if (!reference.trim().isEmpty()) {
                q.setParameter("reference", reference.trim());
            }

            List<Object[]> resp = q.getResultList();

            for (Object[] record : resp) {
                MobileMoney momo = new MobileMoney();
                try {
                    momo.setReference(checkNull(record[0]));
                    momo.setClient(checkNull(record[1]));
                    momo.setMsisdn(checkNull(record[2]));
                    momo.setPaymenttype(checkNull(record[3]));
                    momo.setAmount(BigDecimal.valueOf(Double.valueOf(checkNull(record[4]))));
                    momo.setTrnxdate(checkNull(record[5]));
                    momo.setMainoptions(checkNull(record[6]));
                    momo.setRespcode(checkNull(record[7]));
                    momo.setRespcode2(checkNull(record[8]));
                    momo.setCardno(hashAccountNumber(checkNull(record[9]), type_id));
                    momo.setBank(mapBank(checkNull(record[0]), checkNull(record[10]), checkNull(record[3])));
                    momo.setCode(checkNull(record[11]));
                    momo.setChannel(checkNull(record[12]));
                    momo.setClientid(checkNull(record[13]));
                    momo.setNarration(checkNull(record[14]));
                    momo.setSwitchcode(checkNull(record[15]));
                    momo.setFlag(checkNull(record[16]));
                    momo.setClientcode(checkNull(record[17]));

                } catch (Exception e) {
                    log.error("error", e);
                }
                records.add(momo);
            }
        } catch (Exception e) {
            log.error("error", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return records;
    }

    public List<MobileMoneyUpdate> getMomoUpdateLog(String jsonString) {

        log.info("Mobile Money update log request received >>  " + jsonString);
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

        List<MobileMoneyUpdate> records = new ArrayList<>();

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
        log.info("SEARCH: " + service);
        String query = "select reference, original_clientid, updated_clientid, original_respcode, updated_respcode, original_narration, updated_narration, update_by, update_date from telcodb.mobilemoney_updatelog where 1=1 "
                + (service.equalsIgnoreCase("updatesearch") ? "  AND reference = :reference " : " and update_date between :start_dt  and :end_dt ")
                + (!user.isEmpty() ? " and update_by like  :user " : "")
                + " order by update_date desc";

        Session session = DbHibernate.getSession("40.9MYSQL");

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
                MobileMoneyUpdate momo = new MobileMoneyUpdate();
                try {
                    momo.setReference(checkNull(record[0]));
                    momo.setOriginal_clientid(checkNull(record[1]));
                    momo.setUpdated_clientid(checkNull(record[2]));
                    momo.setOriginal_respcode(checkNull(record[3]));
                    momo.setUpdated_respcode(checkNull(record[4]));
                    momo.setOriginal_narration(checkNull(record[5]));
                    momo.setUpdated_narration(checkNull(record[6]));
                    momo.setUpdate_by(checkNull(record[7]));
                    momo.setUpdate_date(checkNull(record[8]));

                } catch (Exception e) {
                    log.error("error", e);
                }
                records.add(momo);
            }

        } catch (Exception e) {
            log.error("error", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return records;
    }

    public List<MobileMoney> updateMomoStatus(String jsonString) {

        log.info("Mobile Money Update request received >>  " + jsonString);
        Gson j = new Gson();

        ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);
        String data = apiData.getUniqueTransId();
        String status = apiData.getStatus();

        String user_code = apiData.getUserCode();
        String type_id = apiData.getType_id();
        String username = apiData.getUserName();

        String userId = apiData.getUser_id();

        String ipAddress = apiData.getIpAddress();

        List<MobileMoney> records = new ArrayList<>();

        List<String> roleList = new ArrayList<>();
        List<Object[]> resp = new ArrayList<>();
        List<String> references = new ArrayList<>();

        String userRoles = "";

        if (!type_id.isEmpty()) {

            if (type_id.contains("[0]")) {

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
        String respcode = "";
        String fgRespcode = "";

        switch (status) {
            case "successful":
                respcode = "00";
                fgRespcode = "0";
                narration = "[MANUAL]SUCCESSFUL";
                break;

            case "failed":
                respcode = "06";
                fgRespcode = "06";
                narration = "[MANUAL]FAILED";
                break;
            case "locked":
                respcode = "06";
                fgRespcode = "06";
                narration = "[MANUAL]Transaction failed. Account is locked";
                break;
            case "limit_exceeded":
                respcode = "06";
                fgRespcode = "06";
                narration = "[MANUAL]Transaction failed. Limit Exceeded";
                break;
            case "not_registered":
                respcode = "06";
                fgRespcode = "06";
                narration = "[MANUAL]Transaction failed. Not registered for Mobilemoney";
                break;
            case "reprocess":
                respcode = "06";
                fgRespcode = "06";
                narration = "[MANUAL]Transaction failed. Kindly reprocess";
                break;
            default:
                break;
        }

        List<MomoUpdate> recordsList = gson.fromJson(data, new TypeToken<List<MomoUpdate>>() {
        }.getType());

        final String _respcode = respcode;
        final String _fgRespcode = fgRespcode;
        final String _narration = narration;
        final String _status = status.substring(0, 1).toUpperCase() + status.substring(1);

        recordsList.stream().forEach(f -> {
            if (f.getReference() != null && !f.getReference().trim().isEmpty()) {
                references.add(f.getReference().trim());

                updateMomo(f.getReference().trim(), f.getClientid().trim(), _narration, _respcode, _fgRespcode, _status, username, userId, ipAddress);
            }
        });

        if (!references.isEmpty()) {

            String query = "select a.reference , a.client , a.msisdn , (case when a.paymenttype='D' then 'DEBIT' when a.paymenttype = 'C' then 'CREDIT' else a.paymenttype end) as paymenttype, a.amount , a.trnxdate , (case when left(a.reference,5) = '01ESA' then 'WEBCONNECT' when  left(a.reference,2) in ('02','09') then 'TRANSFER' else  ifnull(UPPER(b.mainoptions),'') end) mainoptions, (case when a.respcode ='00' then 'SUCCESSFUL' when a.respcode in ('58','66') then 'PENDING' when a.respcode ='06' then 'FAILED' else a.respcode end) respcode, a.respcode as respcode2, a.cardno, a.cardno as cardno2, "
                    + "a.cardno as code, (case when left(a.channel,2) in ('09','89') then 'FUNDGATE' when left(a.channel,2) = '03' then 'POS' WHEN left(a.channel,2) = '01' then 'WEBCONNECT' WHEN left(a.channel,2) = '07' then 'JUSTPAY' WHEN left(a.channel,2) = '02' then 'MOBILE' WHEN left(a.channel,2) = '08' then 'CORPORATEPAY' WHEN left(a.channel,2) = '04' then 'ATM' else left(a.channel, 2) end) channel, ifnull(a.clientid,''), (case when a.respcode in('0','00') then 'SUCCESSFUL' else ifnull(a.narration,'') end) narration,a.switchcode , "
                    + "(case when a.flag='0' then 'COMPLETED' when a.flag='7' then 'REVERSING' when a.flag='8' then 'REVERSED' else ifnull(a.flag, '') end) flag, ifnull(a.clientcode,'') clientcode  from telcodb.mobilemoney a left join telcodb.just_pay b on a.reference = b.reference where 1=1 "
                    + "  AND a.reference in (:reference)  order by a.trnxdate desc";

            Session session = DbHibernate.getSession("40.9MYSQL");

            try {

                Query q = session.createNativeQuery(query);

                q.setParameter("reference", references);

                resp = q.getResultList();

            } catch (Exception e) {
                log.error("error", e);
            } finally {
                if (session != null) {
                    session.close();
                }
            }
        }

        for (Object[] record : resp) {
            MobileMoney momo = new MobileMoney();
            try {
                momo.setReference(checkNull(record[0]));
                momo.setClient(checkNull(record[1]));
                momo.setMsisdn(checkNull(record[2]));
                momo.setPaymenttype(checkNull(record[3]));
                momo.setAmount(BigDecimal.valueOf(Double.valueOf(checkNull(record[4]))));
                momo.setTrnxdate(checkNull(record[5]));
                momo.setMainoptions(checkNull(record[6]));
                momo.setRespcode(checkNull(record[7]));
                momo.setRespcode2(checkNull(record[8]));
                momo.setCardno(hashAccountNumber(checkNull(record[9]), type_id));
                momo.setBank(banks.getOrDefault(checkNull(record[10]), "n/a"));
                momo.setCode(checkNull(record[11]));
                momo.setChannel(checkNull(record[12]));
                momo.setClientid(checkNull(record[13]));
                momo.setNarration(checkNull(record[14]));
                momo.setSwitchcode(checkNull(record[15]));
                momo.setFlag(checkNull(record[16]));
                momo.setClientcode(checkNull(record[17]));

            } catch (Exception e) {
                log.error("error", e);
            }
            records.add(momo);
        }

        return records;
    }

    public static boolean updateMomo(String reference, String clientId, String narration, String respcode, String fgRespcode, String status, String username, String userId, String ipAddress) {
        boolean result = false;
        boolean rollback = true;

        String logQry = "INSERT INTO telcodb.mobilemoney_updatelog(reference,original_clientid, updated_clientid, original_respcode, updated_respcode, original_narration, updated_narration, update_by, update_by_id) (select reference reference, clientid original_clientid, :clientid updated_clientid, respcode original_respcode, :respcode updated_respcode, narration original_narration, :updated_narration, :username update_by, :userid update_by_id from telcodb.mobilemoney where reference = :reference)";
        String momoqry = "UPDATE telcodb.mobilemoney set clientid = :clientid, respcode =:respcode, narration = concat(substring(ifnull(narration,''),1,80), :narration), processdate = now() where reference = :reference ";
        String fgqry = "SELECT etzref, respcode from fgdb.fundgate_response where etzref =:reference ";
        String mpqry = "SELECT reference, trnx_status from ussd_db.mpay_report where reference =:reference";

        Transaction tx = null;
        Session usession = DbHibernate.getSession("40.9MYSQLW");

        try {
            tx = usession.beginTransaction();

            Query q = usession.createNativeQuery(logQry);
            q.setParameter("clientid", clientId);
            q.setParameter("respcode", respcode);
            q.setParameter("username", username);
            q.setParameter("userid", userId);
            q.setParameter("reference", reference);
            q.setParameter("updated_narration", narration);

            int h = q.executeUpdate();

            if (h > 0) {

                q = usession.createNativeQuery(momoqry);
                q.setParameter("clientid", clientId);
                q.setParameter("respcode", respcode);
                q.setParameter("narration", narration);
                q.setParameter("reference", reference);

                int i = q.executeUpdate();
                log.info("I: " + i);

                if (i > 0) {

                    if (reference.toUpperCase().startsWith("09FG")) {
                        q = usession.createNativeQuery(fgqry).setParameter("reference", reference);

                        List<Object[]> res = q.getResultList();
                        String rec = "";
                        for (Object[] record : res) {
                            rec = record[0].toString();
                            log.info("RES:: " + rec);
                        }

                        String sql1 = "";
                        if (!rec.isEmpty()) {
                            sql1 = "update fgdb.fundgate_response set respMessage =concat(:respMessage,' - ',respMessage) ,respcode =:respcode where etzref =:reference ";
                        } else {
                            sql1 = "insert into fgdb.fundgate_response(action, terminal, respMessage, clientRef, respcode,  etzref, created) (select action, terminal, :respMessage respMessage, clientRef, :respcode respcode, :reference etzref, created from fgdb.fundgate_request where etzref=:reference)";
                        }
                        q = usession.createNativeQuery(sql1);

                        q.setParameter("respcode", fgRespcode);
                        q.setParameter("respMessage", fgRespcode + "::Transaction " + status + (status.equalsIgnoreCase("successful") ? " Ref: " + reference + "|[" + clientId + "]" : ""));
                        q.setParameter("reference", reference);

                        int j = q.executeUpdate();

                        rollback = !(j > 0);

                    } else if (reference.toUpperCase().startsWith("02MPAY")) {

                        Thread thread = new Thread(() -> {
                            String mUrl = mpayUrl.replaceAll("#status#", (fgRespcode.equals("0") || fgRespcode.equals("00")) ? "00" : "06");
                            mUrl = mUrl.replace("#reference#", reference);
                            mUrl = mUrl.replace("#transMsg#", fgRespcode + "::Transaction" + status);
                            log.info("CALLING MPAY: " + mUrl);
                            String mpayResp = DoHTTPRequest.get2(mUrl);

                            log.info("MPAY RESPONSE: " + mpayResp);
                        });
                        thread.start();
                        rollback = false;

                    } else if (reference.toUpperCase().startsWith("JUSTX")) {

                        Thread thread = new Thread(() -> {
                            String jUrl = justpayUrl.replaceAll("#status#", (fgRespcode.equals("0") || fgRespcode.equals("00")) ? "1" : "06");
                            jUrl = jUrl.replace("#reference#", reference);
                            jUrl = jUrl.replace("#transMsg#", status);
                            log.info("CALLING JUSTPAY: " + jUrl);
                            String jResp = DoHTTPRequest.get2(jUrl);

                            log.info("JUSTPAY RESPONSE: " + jResp);
                        });
                        thread.start();
                        rollback = false;

                    } else {
                        log.info("NO UPDATE AVAILABLE FOR: " + reference);
                        rollback = false;
                    }
                }
            }

            if (!rollback) {

                tx.commit();
                result = true;
                new Thread(() -> {
                    AuditTrail audit = new AuditTrail(userId, "Mobilemoney update for " + reference + " to clientId: " + clientId + " and responseCode: " + respcode + " and narration: " + narration, "update", null, "Mobilemoney Status Update", ipAddress);
                    new AuditDao().insertIntoAuditTrail(audit);
                }).start();

            } else if (tx.isActive()) {
                try {
                    tx.rollback();
                } catch (Exception er) {
                    log.error("error", er);
                }
            }

        } catch (Exception et) {
            log.error("error", et);
            if (tx != null) {
                try {
                    tx.rollback();
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
        } finally {
            if (usession != null) {
                usession.close();
            }
        }

        return result;
    }

    public List<MobileMoney> getMobileMoneyStatusCheck(String jsonString) {

        log.info("Mobile Money Status Check request received >>  " + jsonString);
        Gson j = new Gson();

        ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);
        String data = apiData.getUniqueTransId();
        String status = apiData.getStatus();

        String user_code = apiData.getUserCode();
        String type_id = apiData.getType_id();
        String username = apiData.getUserName();

        String userId = apiData.getUser_id();

        String ipAddress = apiData.getIpAddress();

        List<MobileMoney> records = new ArrayList<>();

        List<String> roleList = new ArrayList<>();
        List<Object[]> resp = new ArrayList<>();
        List<String> references = new ArrayList<>();

        String userRoles = "";

        if (!type_id.isEmpty()) {

            if (type_id.contains("[0]")) {

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
        String respcode = "";
        String fgRespcode = "";

        switch (status) {
            case "successful":
                respcode = "00";
                fgRespcode = "0";
                narration = "[MANUAL]SUCCESSFUL";
                break;

            case "failed":
                respcode = "06";
                fgRespcode = "06";
                narration = "[MANUAL]FAILED";
                break;
            case "locked":
                respcode = "06";
                fgRespcode = "06";
                narration = "[MANUAL]Transaction failed. Account is locked";
                break;
            case "limit_exceeded":
                respcode = "06";
                fgRespcode = "06";
                narration = "[MANUAL]Transaction failed. Limit Exceeded";
                break;
            case "not_registered":
                respcode = "06";
                fgRespcode = "06";
                narration = "[MANUAL]Transaction failed. Not registered for Mobilemoney";
                break;
            case "reprocess":
                respcode = "06";
                fgRespcode = "06";
                narration = "[MANUAL]Transaction failed. Kindly reprocess";
                break;
            default:
                break;
        }

        List<MomoUpdate> recordsList = gson.fromJson(data, new TypeToken<List<MomoUpdate>>() {
        }.getType());

//        final String _respcode = respcode;
//        final String _fgRespcode = fgRespcode;
//        final String _narration = narration;
//        final String _status = status.substring(0, 1).toUpperCase() + status.substring(1);
        recordsList.stream().forEach(f -> {
            if (f.getReference() != null && !f.getReference().trim().isEmpty()) {
                references.add(f.getReference().trim());

                //updateMomo(f.getReference().trim(), f.getClientid().trim(), _narration, _respcode, _fgRespcode, _status, username, userId, ipAddress);
            }
        });

        System.out.println("References" + references);

        if (!references.isEmpty()) {

//            String query = "select a.reference , a.client , a.msisdn , (case when a.paymenttype='D' then 'DEBIT' when a.paymenttype = 'C' then 'CREDIT' else a.paymenttype end) as paymenttype, a.amount , a.trnxdate , (case when left(a.reference,5) = '01ESA' then 'WEBCONNECT' when  left(a.reference,2) in ('02','09') then 'TRANSFER' else  ifnull(UPPER(b.mainoptions),'') end) mainoptions, (case when a.respcode ='00' then 'SUCCESSFUL' when a.respcode in ('58','66') then 'PENDING' when a.respcode ='06' then 'FAILED' else a.respcode end) respcode, a.respcode as respcode2, a.cardno, a.cardno as cardno2, "
//                    + "a.cardno as code, (case when left(a.channel,2) in ('09','89') then 'FUNDGATE' when left(a.channel,2) = '03' then 'POS' WHEN left(a.channel,2) = '01' then 'WEBCONNECT' WHEN left(a.channel,2) = '07' then 'JUSTPAY' WHEN left(a.channel,2) = '02' then 'MOBILE' WHEN left(a.channel,2) = '08' then 'CORPORATEPAY' WHEN left(a.channel,2) = '04' then 'ATM' else left(a.channel, 2) end) channel, ifnull(a.clientid,''), (case when a.respcode in('0','00') then 'SUCCESSFUL' else ifnull(a.narration,'') end) narration,a.switchcode , "
//                    + "(case when a.flag='0' then 'COMPLETED' when a.flag='7' then 'REVERSING' when a.flag='8' then 'REVERSED' else ifnull(a.flag, '') end) flag, ifnull(a.clientcode,'') clientcode  from telcodb.mobilemoney a left join telcodb.just_pay b on a.reference = b.reference where 1=1 "
//                    + "  AND a.reference in (:reference)  order by a.trnxdate desc";
            String query = "select reference , client , msisdn , cardno, paymenttype, amount, trnxdate, respcode, clientcode, clientid, channel, mac, narration, processdate, switchcode, flag, descr, verified_status, verify_resp, reverse_status, toreverse_flag, token, db_tat, txn_tat, process_attempt, ext1, ext2, ext3"
                    + " from telcodb.mobilemoney where 1=1 "
                    + "  AND reference in (:reference)  order by trnxdate desc";

            Session session = DbHibernate.getSession("40.9MYSQL");
            //Session session = DbHibernate.getSession("VASREPROCESS");

            try {

                Query q = session.createNativeQuery(query);

                q.setParameter("reference", references);

                resp = q.getResultList();

            } catch (Exception e) {
                log.error("error", e);
            } finally {
                if (session != null) {
                    session.close();
                }
            }
        }

        for (Object[] record : resp) {
            MobileMoney momo = new MobileMoney();
            try {
                momo.setReference(checkNull(record[0]));
                momo.setClient(checkNull(record[1]));
                momo.setMsisdn(checkNull(record[2]));
                momo.setCardno(checkNull(record[3]));
                momo.setPaymenttype(checkNull(record[4]));
                momo.setAmount(BigDecimal.valueOf(Double.valueOf(checkNull(record[5]))));
                momo.setTrnxdate(checkNull(record[6]));
                momo.setRespcode(checkNull(record[7]));
                momo.setClientcode(checkNull(record[8]));
                momo.setClientid(checkNull(record[9]));
                momo.setChannel(checkNull(record[10]));
                momo.setMac(checkNull(record[11]));
                momo.setNarration(checkNull(record[12]));
                momo.setProcessdate(checkNull(record[13]));
                momo.setSwitchcode(checkNull(record[14]));
                momo.setFlag(checkNull(record[15]));
                momo.setDescr(checkNull(record[16]));
                momo.setVerified_status(checkNull(record[17]));
                momo.setVerify_resp(checkNull(record[18]));
                momo.setReverse_status(checkNull(record[19]));
                momo.setToreverse_flag(checkNull(record[20]));
                momo.setToken(checkNull(record[21]));
                momo.setDb_tat(checkNull(record[22]));
                momo.setTxn_tat(checkNull(record[23]));
                momo.setProcess_attempt(checkNull(record[24]));
                momo.setExt1(checkNull(record[25]));
                momo.setExt2(checkNull(record[26]));
                momo.setExt3(checkNull(record[27]));

            } catch (Exception e) {
                log.error("error", e);
            }
            records.add(momo);
        }

        return records;
    }

    protected String checkNull(Object Data) {
        if (Data != null && !Data.equals("")) {
            return Data.toString();
        }
        return "";
    }

//    public boolean testBank(String userCode) {
//        ArrayList<String> banks = new ArrayList<>();
//        banks.add("025");
//        banks.add("003");
//        banks.add("905");
//        banks.add("021");
//        banks.add("029");
//        banks.add("004");
//        banks.add("006");
//        banks.add("012");
//
//        return banks.contains(userCode);
//    }
    public static Map<String, String> getTerminals() {

        Session session = DbHibernate.getSession("40.9MYSQL");
        List<Object[]> cardrecords = new ArrayList<>();
        Map<String, String> map = new HashMap<>();

        try {
            String query = "select card_num, merchant_name from fgdb.cop_fundgate_info order by merchant_name";

            Query q = session.createNativeQuery(query);

            cardrecords = q.getResultList();

            for (Object[] record : cardrecords) {
                map.put(record[0].toString(), record[1].toString());
            }

        } catch (Exception ek) {
            log.error(ek.getMessage(), ek);
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return map;
    }

    public static Map<String, String> getMerchantCodes() {

        Session session = DbHibernate.getSession("40.4SYBASE");
        List<Object[]> cardrecords = new ArrayList<>();
        Map<String, String> map = new HashMap<>();

        try {
            String query = "select default_card, alias from ecarddb..e_b2bmerchant where default_card is not null";

            Query q = session.createNativeQuery(query);

            cardrecords = q.getResultList();

            for (Object[] record : cardrecords) {
                map.put(record[0].toString(), record[1].toString());
            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return map;
    }

    protected String hashAccountNumber(String accNo, String userType) {
        if (!userType.contains("[6]") && accNo != null) {
            int start = accNo.length() / 2;
            HashNumber hn = new HashNumber();

            return hn.hashStringValue(accNo, start / 2, start / 2);
        }
        return accNo;
    }

    protected String mapBank(String ref, String bank, String paymentType) {

        if (ref.toUpperCase().startsWith("09FG") && paymentType.equalsIgnoreCase("CREDIT")) {
            return terminals.getOrDefault(bank, "n/a");
        }
        if (ref.toUpperCase().startsWith("09FG") && paymentType.equalsIgnoreCase("DEBIT")) {
            return merchantCodes.getOrDefault(bank, "n/a");
        }
        return banks.getOrDefault(bank.substring(0, 3), "n/a");

    }

    public static Map<String, String> convertListAfterJava8(List<Bank> list) {
        Map<String, String> map = list.stream().collect(Collectors.toMap(Bank::getIssuer_code, Bank::getIssuer_name));
        return map;
    }

}
