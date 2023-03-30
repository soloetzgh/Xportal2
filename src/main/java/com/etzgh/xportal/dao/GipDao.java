package com.etzgh.xportal.dao;

import com.etzgh.xportal.model.ApiPostData;
import com.etzgh.xportal.model.GIPTransaction;
import com.etzgh.xportal.model.MomoUpdate;
import com.etzgh.xportal.model.VasBillTransaction;
import com.etzgh.xportal.utility.DbHibernate;
import com.etzgh.xportal.utility.GeneralUtility;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import org.hibernate.Session;


public class GipDao {

    Query q;

    private static final Logger log = Logger.getLogger(GipDao.class.getName());
    private static final Gson gson = new Gson();
     private static final GeneralUtility utility = new GeneralUtility();

    public static void main(String[] args) {
        String r = "{\"merchant\":\"\",\"product\":\"\",\"accountNumber\":\"\",\"service\":\"qrsearch\",\"action\":\"\",\"apiSecret\":\"\",\"apiCode\":\"\",\"userCode\":\"[22]\",\"admin\":\"\",\"type_desc\":\"\",\"role_id\":\"\",\"type_id\":\"[0]\",\"searchKey\":\"02QRETZK-0430840-H30E-473\",\"userName\":\"\",\"ClientId\":\"\",\"user_id\":\"10500000000000754\",\"pageNumber\":1,\"rowsPerPage\":1,\"trans_code\":\"\",\"transType\":\"\",\"bank_code\":\"\",\"subscriberId\":\"\",\"trans_status\":\"\",\"channel\":\"\",\"bank\":\"\",\"biller\":\"\",\"transId\":\"\",\"bankid\":\"\",\"from_source\":\"\",\"card_num\":\"\",\"optionType\":\"\",\"payType\":\"\",\"wcMerchant\":\"\",\"destination\":\"\",\"lineType\":\"\",\"trans_channel\":\"\",\"pan\":\"\",\"terminal_id\":\"\",\"card_account\":\"\",\"cop_issuercode\":\"\",\"cop_role_id\":\"\",\"cop_company_id\":\"\",\"mobile_no\":\"\",\"phone\":\"\",\"type\":\"\",\"amount\":\"\",\"amount2\":\"\",\"refundStatus\":\"\",\"partialReversal\":\"\",\"reversalReason\":\"\",\"ipAddress\":\"0:0:0:0:0:0:0:1\",\"userData\":\"\",\"appId\":[],\"company\":\"\",\"options\":{}}";
        new GipDao().getGhQRTransactions(r);
    }

    public List<GIPTransaction> getGipTransactions(final String jsonString) {
        System.out.println("gip trx request received >> " + jsonString);
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

        String qry = "";
        List<GIPTransaction> recordsList = new ArrayList<>();

        if (type_id.contains("[0]") || type_id.contains("[16]")) {
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
        }

        if (service.equalsIgnoreCase("search") && searchKey.trim().isEmpty()) {
            return recordsList;
        }

        qry = "SELECT etzRef, truncate(amount/100,2) amount, accounttodebit accountDebit, accounttocredit accountSrc, actcode, nametocredit, created, sessionid  FROM telcodb.`giptransaction` "
                + " WHERE 1=1 "
                + (!service.equalsIgnoreCase("search") ? " and created between :start_dt and :end_dt " : " and etzRef = :searchKey ")
                + (trans_status.equalsIgnoreCase("ALL") || trans_status.trim().isEmpty() ? "" : (trans_status.equalsIgnoreCase("SUCCESSFUL") ? " and actcode = '000'" : " and actcode != '000'"))
                + (!uniqueTransId.trim().isEmpty() ? " and etzRef = :reference " : "")
                + " order by created desc";

        System.out.println("gip QUERY >>" + qry);
        List<Object[]> resp = new ArrayList<>();
        Session session = DbHibernate.getSession("40.9MYSQL");
        try {

            q = session.createNativeQuery(qry);

            if (!service.equalsIgnoreCase("search")) {
                q.setParameter("start_dt", (Object) start_dt)
                        .setParameter("end_dt", (Object) end_dt);
            } else {
                q.setParameter("searchKey", (Object) searchKey.trim());
            }
            if (!uniqueTransId.trim().isEmpty()) {
                q.setParameter("reference", (Object) uniqueTransId.trim());
            }
            resp = q.getResultList();
        } catch (Exception er) {
            log.error(er.getMessage(), er);

        } finally {
            if (session != null) {
                session.close();
            }
        }
        for (final Object[] record : resp) {
            final GIPTransaction gip = new GIPTransaction();
            try {
                gip.setEtzRef(this.checkNull(record[0]));
                gip.setSessionId(this.checkNull(record[7]));
                gip.setAmount(this.checkNull(record[1]));
                gip.setAccountDebit(this.checkNull(record[2]));
                gip.setAccountCredit(this.checkNull(record[3]));
                gip.setActCode(this.checkNull(record[4]));
                gip.setNameToCredit(this.checkNull(record[5]));
                gip.setDatetime(this.checkNull(record[6]));
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            recordsList.add(gip);
        }

        return recordsList;
    }

    public List<GIPTransaction> getGhQRTransactions(final String jsonString) {
        System.out.println("ghqr trx request received >> " + jsonString);
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
        String account = apiData.getAccountNumber();

        String qry = "";
        List<GIPTransaction> recordsList = new ArrayList<>();

        if (type_id.contains("[0]") || type_id.contains("[16]")) {
        } else {
            return recordsList;
        }
        if (service == null) {
            service = "";
        }
        if (uniqueTransId == null) {
            uniqueTransId = "";
        }
        if (trans_status == null) {
            trans_status = "";
        }
        if (account == null) {
            account = "";
        }
        if (service.equalsIgnoreCase("qrsearch") && searchKey.trim().isEmpty()) {
            return recordsList;
        }

        qry = "SELECT etzRef, truncate(amount/100,2) amount, accounttodebit accountDebit, accounttocredit accountSrc, actcode, nametocredit, created, sessionid  FROM telcodb.`gipqrtransaction` "
                + " WHERE 1=1 "
                + (!service.equalsIgnoreCase("qrsearch") ? " and created between :start_dt and :end_dt " : " and etzRef = :searchKey ")
                + (trans_status.equalsIgnoreCase("ALL") || trans_status.trim().isEmpty() ? "" : (trans_status.equalsIgnoreCase("SUCCESSFUL") ? " and actcode = '000'" : " and actcode != '000'"))
                + (!uniqueTransId.trim().isEmpty() ? " and etzRef = :reference " : "")
                + (!account.trim().isEmpty() ? " and (accounttodebit = :account or accounttocredit = :account) " : "")
                + " order by created desc";

        System.out.println("gip QUERY >>" + qry);
        List<Object[]> resp = new ArrayList<>();
        Session session = DbHibernate.getSession("40.9MYSQL");
        try {

            q = session.createNativeQuery(qry);

            if (!service.equalsIgnoreCase("qrsearch")) {
                q.setParameter("start_dt", (Object) start_dt)
                        .setParameter("end_dt", (Object) end_dt);
            } else {
                q.setParameter("searchKey", (Object) searchKey.trim());
            }
            if (!uniqueTransId.trim().isEmpty()) {
                q.setParameter("reference", (Object) uniqueTransId.trim());
            }
            if (!account.trim().isEmpty()) {
                q.setParameter("account", (Object) account.trim());
            }
            resp = q.getResultList();
        } catch (Exception er) {
            log.error(er.getMessage(), er);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        for (final Object[] record : resp) {
            final GIPTransaction gip = new GIPTransaction();
            try {
                gip.setEtzRef(this.checkNull(record[0]));
                gip.setSessionId(this.checkNull(record[7]));
                gip.setAmount(this.checkNull(record[1]));
                gip.setAccountDebit(this.checkNull(record[2]));
                gip.setAccountCredit(this.checkNull(record[3]));
                gip.setActCode(this.checkNull(record[4]));
                gip.setNameToCredit(this.checkNull(record[5]));
                gip.setDatetime(this.checkNull(record[6]));
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            recordsList.add(gip);
        }

        return recordsList;
    }
    
     public List<GIPTransaction> getGipStatusCheck(String jsonString) {

        log.info("Gip Status Check request received >>  " + jsonString);
        Gson j = new Gson();

        ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);
        String data = apiData.getUniqueTransId();
        String status = apiData.getStatus();

        String user_code = apiData.getUserCode();
        String type_id = apiData.getType_id();
        String username = apiData.getUserName();

        String userId = apiData.getUser_id();

        String ipAddress = apiData.getIpAddress();

        List<GIPTransaction> records = new ArrayList<>();

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



            String query = "SELECT etzRef, amount, datetime, trackingnum, functioncode, originebank, destbank, sessionid, channelcode, nametodebit, accounttodebit, nametocredit, accounttocredit, narration, actcode, aprvcode, reprocessed,trans_status, process_attempt, created, updated, mac, source, qrcode, resp_code"
                    + " from telcodb.giptransaction where 1=1 "
                    + "  AND etzref  in (:reference) order by created desc";
            // + "  AND unique_transid in (:reference)  AND TRANS_STATUS <>'00'  order by trans_date desc";
           

       //String qry = "SELECT trans_no,unique_transid,merchant_id, trans_amount as amount, trans_date, trans_channel, trans_status, trans_note, subscriber_id, issuer_code ,Payment_code ,mobile_no, sp_status FROM vasdb2.`t_paytrans` WHERE unique_transid like :searchKey  order by trans_date DESC";

            Session session = DbHibernate.getSession("40.9MYSQL");
            //Session session = DbHibernate.getSession("VASREPROCESS");

            try {

                org.hibernate.query.Query q = session.createNativeQuery(query);

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
           GIPTransaction gip = new GIPTransaction();
              try {
                gip.setEtzRef(checkNull(record[0]));
                //gip.setAmount(checkNull(record[1]));                              
                gip.setAmount(String.valueOf(BigDecimal.valueOf(Double.valueOf(checkNull(record[1])))));
                 //gip.setDatetime(checkNull(record[2]));                            
                gip.setTrackingnum(checkNull(record[3])); 
                gip.setFunctioncode(checkNull(record[4]));
                gip.setOriginebank(checkNull(record[5]));
                gip.setDestbank(checkNull(record[6]));
                gip.setSessionId(checkNull(record[7]));
                gip.setChannelcode(checkNull(record[8]));
                gip.setNametodebit(checkNull(record[9]));
                gip.setAccounttodebit(checkNull(record[10]));
                gip.setNametocredit(checkNull(record[11]));
                gip.setAccounttocredit(checkNull(record[12]));
                gip.setNarration(checkNull(record[13]));
                gip.setActCode(checkNull(record[14]));
                gip.setAprvcode(checkNull(record[15]));
                gip.setReprocessed(checkNull(record[16]));
                gip.setTrans_status(checkNull(record[17]));
                gip.setProcess_attempt(checkNull(record[18]));
                gip.setCreated(checkNull(record[19]));
                gip.setUpdated(checkNull(record[20]));
                gip.setMac(checkNull(record[21]));
                gip.setSource(checkNull(record[22]));
                gip.setQrcode(checkNull(record[23]));
                gip.setResp_code(checkNull(record[24]));
               
              
            } catch (Exception e) {
                log.error("error", e);
            }
            records.add(gip);
        }

        return records;
    }

    public String convertDate(final String dateTime) {
        String convertedDateTime = "N/A";
        final DateFormat srcDf = new SimpleDateFormat("yyMMddhhmmss");
        try {
            final Date date = srcDf.parse(dateTime);
            final DateFormat destDf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            convertedDateTime = destDf.format(date);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return convertedDateTime;
    }

    public String convertDate2(final String dateTime) {
        String convertedDateTime = "N/A";
        final DateFormat srcDf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        try {
            final Date date = srcDf.parse(dateTime);
            final DateFormat destDf = new SimpleDateFormat("yyMMddhhmmss");
            convertedDateTime = destDf.format(date);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return convertedDateTime;
    }

    protected String checkNull(final Object data) {
        if (data != null && !data.equals("")) {
            return data.toString();
        }
        return "NULL";
    }
}
