/**
 *
 * @author sunkwa-arthur
 */
package com.etzgh.xportal.dao;

import com.etzgh.xportal.model.ApiPostData;
import com.etzgh.xportal.model.MobileInvestigation;
import com.etzgh.xportal.model.MobileInvestigationDetails;
import com.etzgh.xportal.model.NODES;
import com.etzgh.xportal.utility.DbHibernate;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class MobileInvestigationDao {

    private static final Logger log = Logger.getLogger(MobileInvestigationDao.class.getName());

    public static void main(String args[]) {
        String r = "{\"merchant\":\"\",\"product\":\"\",\"accountNumber\":\"\",\"status\":\"\",\"service\":\"search\",\"action\":\"\",\"apiSecret\":\"\",\"apiCode\":\"\",\"userCode\":\"[2000000000000060]|905,[2500000000000050]|77\",\"admin\":\"\",\"type_desc\":\"\",\"role_id\":\"\",\"type_id\":\"[4],[5],[6],[7],[20],[24],[39],[40]\",\"searchKey\":\"02RS42603E77662\",\"userName\":\"\",\"ClientId\":\"\",\"user_id\":\"10500000000000754\",\"pageNumber\":1,\"rowsPerPage\":1,\"trans_code\":\"\",\"transType\":\"ALL\",\"bank_code\":\"905\",\"trans_status\":\"\",\"channel\":\"\",\"bank\":\"\",\"biller\":\"\",\"bankid\":\"\",\"from_source\":\"\",\"card_num\":\"\",\"optionType\":\"\",\"payType\":\"\",\"wcMerchant\":\"\",\"destination\":\"\",\"lineType\":\"\",\"trans_channel\":\"\",\"pan\":\"\",\"terminal_id\":\"\",\"card_account\":\"\",\"cop_issuercode\":\"\",\"cop_role_id\":\"\",\"cop_company_id\":\"\",\"mobile_no\":\"\",\"phone\":\"\",\"type\":\"\",\"amount\":\"\",\"amount2\":\"\",\"refundStatus\":\"\",\"partialReversal\":\"\",\"reversalReason\":\"\",\"ipAddress\":\"\",\"userData\":\"\",\"appId\":[],\"company\":\"\",\"options\":{}}";

        new MobileInvestigationDao().getMobileInvestigations(r);

    }

    public List<MobileInvestigation> getMobileInvestigations(String jsonString) {
        log.info("mobileinvestigations request received >> " + jsonString);
        Gson j = new Gson();
        ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);

        String start_dt = apiData.getStartDate();
        String end_dt = apiData.getEndDate();
        String bankCode = apiData.getBank_code();
        String type = apiData.getTransType();

        String str_mobile = apiData.getSubscriberId();

        String type_id = apiData.getType_id();
        String service = apiData.getService();
        String searchKey = apiData.getSearchKey();
        List<MobileInvestigation> recordsList = new ArrayList<>();

        String qry = "";

        if (type == null) {
            type = "ALL";
        }
        if (str_mobile == null) {
            str_mobile = "";
        }

        if (!type_id.isEmpty()) {
            log.info("TY: " + type_id + " : " + bankCode);
            if (type_id.contains("[0]")) {
                bankCode = bankCode.equals("000") ? "ALL" : bankCode;
            } else if (type_id.contains("[24]") || type_id.contains("[6]")) {
                log.info("bank: " + bankCode);
            } else {
                return recordsList;
            }
        } else {
            return recordsList;
        }

        if (str_mobile.length() == 12 && str_mobile.startsWith("233")) {
        } else if (str_mobile.length() == 10 && str_mobile.startsWith("0")) {
            str_mobile = "233" + str_mobile.substring(1);
        }

        if (searchKey == null) {
            searchKey = "";
        }
        if (service.equalsIgnoreCase("search") && searchKey.trim().isEmpty()) {
            return recordsList;
        }

        if (str_mobile.isEmpty() && !service.equalsIgnoreCase("search")) {
            return recordsList;
        }

        log.info("bank >  " + bankCode);
        log.info("type >  " + type);

        qry = "select a.id id , a.mobile_no mobile_no, a.destination_no destination_no, a.channel channel, a.message message, "
                + "a.ticket_id ticket_id, a.unique_transid unique_transid, a.checkvalue checkvalue, b.appid, "
                + "(case when (b.appid ='eTzMobile' and b.token in('T','TAM')) then 'momo' when (b.appid ='eTzMobile' and b.token in('VL')) "
                + "then 'topup' when (b.appid != 'eTzMobile' and b.token in('T','TAM','TAMMC', 'TAMMD')) then 'momo' when "
                + "(b.appid != 'eTzMobile' and b.token in('VL')) then 'topup' else 'other' end ) type, a.msg_date msg_date "
                + "from mobiledb.m_incoming_messages a left join mobiledb.m_requests b on  a.unique_transid=b.unique_transid left join "
                + "mobiledb.m_mobileapp_properties c on c.appid=b.appid where 1=1 "
                + (service.equalsIgnoreCase("search") ? " and a.unique_transid = :searchKey " : "  and a.msg_date between  :start_dt  and :end_dt ")
                + (!bankCode.equalsIgnoreCase("ALL") ? " and c.bank_code = :bank " : "")
                + (!type.equalsIgnoreCase("ALL") ? " and b.token = :type " : "")
                + (!str_mobile.trim().isEmpty() ? " and a.mobile_no = :str_mobile " : "")
                + " order by a.msg_date desc";

        Session session = DbHibernate.getSession("MOBILEDBMYSQL");
        List<Object[]> records = new ArrayList<>();
        try {
            Query q = session.createNativeQuery(qry);

            if (!service.equalsIgnoreCase("search")) {
                q.setParameter("start_dt", start_dt)
                        .setParameter("end_dt", end_dt);
            } else {
                q.setParameter("searchKey", (Object) searchKey.trim());
            }

            if (!bankCode.equals("ALL")) {
                q.setParameter("bank", bankCode);
            }
            if (!type.equals("ALL")) {
                q.setParameter("type", type);
            }
            if (!str_mobile.trim().isEmpty()) {
                q.setParameter("str_mobile", str_mobile.trim());
            }

            log.info("MobileInvestigations QUERY >> " + qry);
            records = q.getResultList();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }

        for (final Object[] record : records) {
            final MobileInvestigation mi = new MobileInvestigation();
            try {
                mi.setId(checkNull(record[0]));
                mi.setMobile_no(checkNull(record[1]));
                mi.setDestination_no(checkNull(record[2]));
                mi.setChannel(checkNull(record[3]));
                mi.setMessage(checkNull(record[4]));
                mi.setTicket_id(checkNull(record[5]));
                mi.setUnique_transid(checkNull(record[6]));
                mi.setCheckvalue(checkNull(record[7]));
                mi.setAppid(checkNull(record[8]));
                mi.setType(checkNull(record[9]));
                mi.setMsg_date(checkNull(record[10]));

            } catch (Exception y) {
                log.error(y.getMessage(), y);
            }
            recordsList.add(mi);
        }
        log.info("Mobile Investigation RECORDS SELECT DONE");

        return recordsList;
    }

    public List<MobileInvestigationDetails> getDetailedMobileInvestigations(String jsonString) {

        log.info("detailed mobileinvestigations request received >> " + jsonString);
        Gson j = new Gson();
        ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);
        String type = apiData.getTransType();
        String message_id = apiData.getTransId();

        String uniqueTransId = apiData.getUniqueTransId();
        String bank = apiData.getClientId();
        String ucode = apiData.getUserCode();

        String cardScheme = apiData.getCardSchemeNumbers();
        String qry = "";
        List<MobileInvestigationDetails> recordsList = new ArrayList<>();
        Query q;

        qry = "select id , message_id, appid, response_code, 'null' momo_resp, 'null' vt_resp, 'null' momo_no, 'null' vt_no, 'null' momo_ref, 'null' amount, response_message, mobile_no, sender_id, token, created from mobiledb.m_outgoing_messages where message_id = :message_id";
        log.info(qry);
        log.info("Detailed MobileInvestigations QUERY >>" + qry);

        Session session = DbHibernate.getSession("MOBILEDBMYSQL");
        List<Object[]> records = new ArrayList<>();
        try {
            q = session.createNativeQuery(qry)
                    .setParameter("message_id", message_id);

            records = q.getResultList();
        } catch (Exception et) {
            log.error(et.getMessage(), et);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        for (final Object[] record : records) {
            final MobileInvestigationDetails mi = new MobileInvestigationDetails();
            try {
                mi.setId(checkNull(record[0]));
                mi.setMessage_id(checkNull(record[1]));
                mi.setAppid(checkNull(record[2]));
                mi.setResponse_code(checkNull(record[3]));
                mi.setMomo_resp(checkNull(record[4]));
                mi.setVt_resp(checkNull(record[5]));
                mi.setMomo_no(checkNull(record[6]));
                mi.setVt_no(checkNull(record[7]));
                mi.setMomo_ref(checkNull(record[8]));
                mi.setAmount(checkNull(record[9]));
                mi.setResponse_message(checkNull(record[10]));
                mi.setMobile_no(checkNull(record[11]));
                mi.setSender_id(checkNull(record[12]));
                mi.setToken(checkNull(record[13]));
                mi.setCreated(checkNull(record[14]));

            } catch (Exception y) {
                log.error(y.getMessage(), y);
            }
            recordsList.add(mi);
        }

        if (!recordsList.isEmpty()) {
            if (type.equalsIgnoreCase("momo")) {
                String query = "select respcode, (case when PAYMENTTYPE ='D' then substring(cardno,4) else msisdn end) msisdn, clientid, amount from telcodb.mobilemoney where reference = :uniqueTransId ";
                log.info("Detailed Momo QUERY >>" + query);
                Session ses = DbHibernate.getSession("40.9MYSQL");
                List<Object[]> momorecord = new ArrayList<>();
                try {
                    q = ses.createNativeQuery(query)
                            .setParameter("uniqueTransId", uniqueTransId);
                    momorecord = q.getResultList();

                } catch (Exception y) {
                    log.error(y.getMessage(), y);
                } finally {
                    if (ses != null) {
                        ses.close();
                    }
                }

                final List<Object[]> momorecordf = momorecord;
                recordsList.forEach((MobileInvestigationDetails f) -> {
                    for (Object[] obj : momorecordf) {

                        f.setMomo_resp((obj[0].toString().trim()));
                        f.setMomo_no((obj[1].toString().trim()));
                        f.setMomo_ref((obj[2].toString().trim()));
                        f.setAmount((obj[3].toString().trim()));
                    }
                });
            } else if (type.equalsIgnoreCase("topup")) {
                String query = "select response_code, dest_account, amount from vasdb2.t_provider_log where unique_transid = :uniqueTransId ";
                log.info("Detailed Vasgate QUERY >>" + query);
                Session ses = DbHibernate.getSession("40.9MYSQL");
                List<Object[]> vgrecord = new ArrayList<>();
                try {
                    q = ses.createNativeQuery(query)
                            .setParameter("uniqueTransId", uniqueTransId);
                    vgrecord = q.getResultList();

                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                } finally {
                    if (ses != null) {
                        ses.close();
                    }
                }
                final List<Object[]> vgrecordf = vgrecord;
                recordsList.forEach((MobileInvestigationDetails f) -> {
                    for (Object[] obj : vgrecordf) {

                        f.setVt_resp((obj[0].toString().trim()));
                        f.setVt_no((obj[1].toString().trim()));
                        f.setAmount((obj[2].toString().trim()));
                    }
                });
            } else if (type.equalsIgnoreCase("bill")) {
                String query = "select trans_status, subscriber_id, trans_amount from vasdb2.t_paytrans where unique_transid = :uniqueTransId ";
                log.info("Detailed Vasgate Bill QUERY >>" + query);
                Session ses = DbHibernate.getSession("40.9MYSQL");
                List<Object[]> vgrecord = new ArrayList<>();
                try {
                    q = ses.createNativeQuery(query)
                            .setParameter("uniqueTransId", uniqueTransId);
                    vgrecord = q.getResultList();

                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                } finally {
                    if (ses != null) {
                        ses.close();
                    }
                }
                final List<Object[]> vgrecordf = vgrecord;
                recordsList.forEach((MobileInvestigationDetails f) -> {
                    for (Object[] obj : vgrecordf) {

                        f.setVt_resp((obj[0].toString().trim()));
                        f.setVt_no((obj[1].toString().trim()));
                        f.setAmount((obj[2].toString().trim()));
                    }
                });
            }
        }

        return recordsList;

    }

    public List<NODES> getMobileAppIdList() {

        List<NODES> appIdList = new ArrayList<>();
        String qry = "";

        qry = "SELECT appid name, id FROM mobiledb.m_mobileapp_properties where 1=1 "
                + " order by name asc;";

        Session session = DbHibernate.getSession("MOBILEDBMYSQL");
        try {
            Query q = session.createNativeQuery(qry, NODES.class);

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

    protected String checkNull(Object Data) {
        if (Data != null && !Data.toString().trim().isEmpty()) {
            return Data.toString().trim();
        }
        return "";
    }

}
