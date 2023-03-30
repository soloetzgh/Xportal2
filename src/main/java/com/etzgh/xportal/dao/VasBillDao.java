package com.etzgh.xportal.dao;

import com.etzgh.xportal.model.ApiPostData;
import com.etzgh.xportal.model.Bank;
import com.etzgh.xportal.model.MobileMoney;
import com.etzgh.xportal.model.MomoUpdate;
import com.etzgh.xportal.model.NODES;
import com.etzgh.xportal.model.VasBillMerchantCode;
import com.etzgh.xportal.model.VasBillTransaction;
import com.etzgh.xportal.utility.DbHibernate;
import com.etzgh.xportal.utility.GeneralUtility;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.joining;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class VasBillDao {

    Query q;

    private static final GeneralUtility utility = new GeneralUtility();
    private static final Logger log = Logger.getLogger(VasBillDao.class.getName());
    private static final Map<String, String> banksMap;
    private static final String ERROR = "ERROR";
    private static final Gson gson = new Gson();

    public String welcome(final String json) {
        return "WELCOME TO THE ETRANZACT XPORTAL WEBSERVICE";
    }

    public static Map<String, String> convertListAfterJava8(List<Bank> list) {
        Map<String, String> map = list.stream().collect(Collectors.toMap(Bank::getIssuer_code, bank -> bank.getIssuer_name()));
        return map;
    }

    static {
        banksMap = convertListAfterJava8(new AppDao().getBanks());
    }

    protected String checkNull(Object Data) {
        if (Data != null && !Data.equals("")) {
            return Data.toString();
        }
        return "";
    }

    public static void main(String[] args) {
        String r = "{\"startDate\":\"2021-05-01 00:00\",\"endDate\":\"2021-06-01 00:00\",\"merchant\":\"ALL\",\"product\":\"\",\"accountNumber\":\"\",\"status\":\"Successful\",\"service\":\"transactions\",\"action\":\"\",\"apiSecret\":\"\",\"apiCode\":\"\",\"userCode\":\"[2000000000000053]|BO~DSTV~GOTV~GOTVLITE~GOTVMAX~GOTVPLS\",\"admin\":\"\",\"type_desc\":\"\",\"role_id\":\"\",\"type_id\":\"[4]\",\"userName\":\"\",\"ClientId\":\"\",\"user_id\":\"10500000000000817\",\"pageNumber\":1,\"rowsPerPage\":1,\"uniqueTransId\":\"\",\"trans_code\":\"\",\"transType\":\"\",\"bank_code\":\"ALL\",\"subscriberId\":\"\",\"trans_status\":\"\",\"roleList\":[],\"channel\":\"ALL\",\"bank\":\"\",\"biller\":\"\",\"transId\":\"\",\"bankid\":\"\",\"from_source\":\"\",\"card_num\":\"\",\"optionType\":\"\",\"payType\":\"\",\"wcMerchant\":\"\",\"destination\":\"\",\"lineType\":\"\",\"trans_channel\":\"\",\"pan\":\"\",\"terminal_id\":\"\",\"card_account\":\"\",\"cop_issuercode\":\"\",\"cop_role_id\":\"\",\"cop_company_id\":\"\",\"mobile_no\":\"\",\"phone\":\"\",\"type\":\"\",\"amount\":\"\",\"amount2\":\"\",\"refundStatus\":\"\",\"partialReversal\":\"\",\"reversalReason\":\"\",\"ipAddress\":\"41.210.62.195\",\"userData\":\"\",\"appId\":[],\"company\":\"\",\"origin\":\"\",\"options\":{}}";

        new VasBillDao().getVasBillTransactions(r);
    }

    public List<VasBillTransaction> getVasBillTransactions(final String jsonString) {
        System.out.println("vasbill trx request received >> " + jsonString);
        final Gson j = new Gson();
        final ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);

        final String start_dt = apiData.getStartDate();
        final String end_dt = apiData.getEndDate();
        String transType = apiData.getMerchant();
        String trans_channel = apiData.getChannel();
        String trans_status = apiData.getStatus();
        String bankCode = apiData.getBank_code();
        String uniqueTransId = apiData.getUniqueTransId();
        String custAccount = apiData.getAccountNumber();
        String ucode = apiData.getUserCode();
        final String type_id = apiData.getType_id();

        final String cardScheme = apiData.getCardSchemeNumbers();
        String searchKey = apiData.getSearchKey();
        final String service = apiData.getService();
        String qry = "";
        String qry2 = "";
        List<String> transType2 = new ArrayList<>();
        String userRoles = "";

        List<VasBillTransaction> records = new ArrayList<>();
        List<VasBillMerchantCode> res = new ArrayList<>();
        List<VasBillTransaction> records1 = new ArrayList<>();
        List<VasBillMerchantCode> res1 = new ArrayList<>();
        List<String> roleList = new ArrayList<>();

        try {

            if (!type_id.isEmpty()) {

                if (type_id.contains("[0]") || type_id.contains("[6]")) {
                    if (transType == null || transType.trim().isEmpty()) {
                        transType = "ALL";
                    }
                    if (type_id.contains("[0]") && (bankCode.isEmpty() || bankCode.equals("000"))) {
                        bankCode = "ALL";
                    }

                    roleList.add(transType.toUpperCase());
                } else if (type_id.contains("[4]")) {

                    if (bankCode.isEmpty() || bankCode.equals("000")) {
                        bankCode = "ALL";
                    }
                    userRoles = utility.getRoleParameters("[2000000000000053]", ucode);
                    System.out.println("usrRoles: " + userRoles);

                    roleList = Arrays.asList(userRoles.split("~"));
                    if (transType.equalsIgnoreCase("ALL")) {

                    } else {
                        if (roleList.contains(transType)) {
                            roleList = new ArrayList<>(roleList);
                            roleList.add(transType);
                        }
                    }

                } else {
                    return records;
                }
            } else {
                return records;
            }

            if (uniqueTransId == null) {
                uniqueTransId = "";
            }

            if (service.equalsIgnoreCase("search") && searchKey.trim().isEmpty()) {
                return records;
            }

            if (service.equalsIgnoreCase("search")) {
                searchKey = searchKey.trim();
            }
            if (trans_channel == null || trans_channel.isEmpty()) {
                trans_channel = "ALL";
            }
            if (trans_status == null || trans_status.isEmpty()) {
                trans_status = "ALL";
            }
            if (custAccount == null || custAccount.isEmpty()) {
                custAccount = "";
            }

            if (bankCode.equalsIgnoreCase("ALL")) {
                qry = "(SELECT b.trans_no trans_no, b.unique_transid unique_transid, b.merchant_id merchant_id, b.trans_amount amount, b.trans_date trans_date, substring(b.unique_transid, 1, 2) trans_channel , b.trans_status trans_status, b.trans_note trans_note, b.subscriber_id subscriber_id, (case when substring(b.UNIQUE_TRANSID, 1, 2)='cs' then substring(b.UNIQUE_TRANSID, 7, 3)  when substring(b.UNIQUE_TRANSID, 1, 2)='ET' then substring(b.UNIQUE_TRANSID, 3, 3) when substring(b.UNIQUE_TRANSID, 1, 2)='05' then substring(b.UNIQUE_TRANSID, 3, 3) when substring(b.UNIQUE_TRANSID, 1, 2)='09' then (select merchant_name from fgdb.cop_fundgate_info where terminal_id = ifnull(a.terminal, c.terminal)) when (substring(b.UNIQUE_TRANSID, 1, 2)='ju' or substring(b.UNIQUE_TRANSID, 1, 6)='02MPAY') then 'mobile money'  end) issuer_code , b.Payment_code Payment_code , b.mobile_no mobile_no, b.sp_status FROM  vasdb2.`t_paytrans` b left  join  fgdb.FUNDGATE_RESPONSE_old a  on b.unique_transid=a.etzRef left join fgdb.FUNDGATE_RESPONSE c on b.unique_transid=c.etzRef where 1=1 "
                        + (service.equalsIgnoreCase("search") ? "  AND b.unique_transid = :searchKey " : " and b.TRANS_DATE between :start_dt and :end_dt ")
                        + (!roleList.contains("ALL") ? " and b.merchant_id in (:transType) " : "")
                        + (!uniqueTransId.trim().isEmpty() ? " and b.unique_transid =  :uniqueTransId " : "")
                        + (!custAccount.trim().isEmpty() ? " and b.subscriber_id = :custAccount " : "")
                        + (!trans_channel.equalsIgnoreCase("ALL") ? " and substring(b.unique_transid, 1, 2) = :trans_channel " : "")
                        + (!trans_status.equalsIgnoreCase("ALL") ? (trans_status.equalsIgnoreCase("SUCCESSFUL") ? " and b.trans_status = '00'" : " and b.trans_status <> '00'") : "")
                        + " order by b.TRANS_DATE desc) "
                        + " UNION DISTINCT "
                        + " (SELECT b.trans_no trans_no, b.unique_transid unique_transid, b.merchant_id merchant_id, b.trans_amount amount, b.trans_date trans_date, substring(b.unique_transid, 1, 2) trans_channel , b.trans_status trans_status, b.trans_note trans_note, b.subscriber_id subscriber_id, (case when substring(b.UNIQUE_TRANSID, 1, 2)='ET' then substring(b.UNIQUE_TRANSID, 3, 3) when substring(b.UNIQUE_TRANSID, 1, 2)='05' then substring(b.UNIQUE_TRANSID, 3, 3) when substring(b.UNIQUE_TRANSID, 1, 2)='09' then (select merchant_name from fgdb.cop_fundgate_info where terminal_id = ifnull(a.terminal, c.terminal)) when (substring(b.UNIQUE_TRANSID, 1, 2)='ju' or substring(b.UNIQUE_TRANSID, 1, 6)='02MPAY') then 'mobile money'  end) issuer_code , b.Payment_code Payment_code , b.mobile_no mobile_no, b.sp_status FROM  vasdb2.`t_paytrans` b left  join  fgdb.FUNDGATE_RESPONSE a  on b.unique_transid=a.etzRef left  join  fgdb.FUNDGATE_RESPONSE_old c  on b.unique_transid=c.etzRef where 1=1 "
                        + " and (case when substring(b.UNIQUE_TRANSID, 1, 2)='ET' then substring(b.UNIQUE_TRANSID, 3, 3) when substring(b.UNIQUE_TRANSID, 1, 2)='05' then substring(b.UNIQUE_TRANSID, 3, 3) when substring(b.UNIQUE_TRANSID, 1, 2)='09' then (select merchant_name from fgdb.cop_fundgate_info where terminal_id = ifnull(a.terminal, c.terminal)) when (substring(b.UNIQUE_TRANSID, 1, 2)='ju' or substring(b.UNIQUE_TRANSID, 1, 6)='02MPAY') then 'mobile money'  end) <> null "
                        + (service.equalsIgnoreCase("search") ? "  AND b.unique_transid = :searchKey " : " and b.TRANS_DATE between :start_dt and :end_dt ")
                        + (!uniqueTransId.trim().isEmpty() ? " and b.unique_transid =  :uniqueTransId " : "")
                        + (!custAccount.trim().isEmpty() ? " and b.subscriber_id = :custAccount " : "")
                        + (!trans_channel.equalsIgnoreCase("ALL") ? " and substring(b.unique_transid, 1, 2) = :trans_channel " : "")
                        + (!trans_status.equalsIgnoreCase("ALL") ? (trans_status.equalsIgnoreCase("SUCCESSFUL") ? " and b.trans_status = '00'" : " and b.trans_status <> '00'") : "")
                        + " order by b.TRANS_DATE desc) order by trans_date desc";
                log.info("Qry> " + qry);

                Session session = DbHibernate.getSession("40.9MYSQL");
                List<Object[]> resr = new ArrayList<>();

                try {
                    this.q = session.createNativeQuery(qry);

                    if (!service.equalsIgnoreCase("search")) {
                        this.q.setParameter("start_dt", (Object) start_dt)
                                .setParameter("end_dt", (Object) end_dt);
                    } else {
                        this.q.setParameter("searchKey", (Object) searchKey);
                    }

                    if (!trans_channel.equals("ALL")) {
                        this.q.setParameter("trans_channel", (Object) trans_channel);
                    }
                    if (qry.contains(":transType")) {
                        this.q.setParameter("transType", roleList);
                    }
                    if (!uniqueTransId.trim().isEmpty()) {
                        this.q.setParameter("uniqueTransId", (Object) uniqueTransId.trim());
                    }
                    if (!custAccount.trim().isEmpty()) {
                        this.q.setParameter("custAccount", (Object) custAccount.trim());
                    }

                    resr = q.getResultList();
                } catch (Exception t) {
                    log.error(ERROR, t);
                } finally {
                    if (session != null) {
                        try {
                            session.close();
                        } catch (Exception er) {
                            // Do Nothing
                        }
                    }
                }

                for (Object[] record : resr) {
                    VasBillTransaction vb = new VasBillTransaction();
                    try {
                        vb.setTrans_no(checkNull(record[0]));
                        vb.setUnique_transid(checkNull(record[1]));
                        vb.setMerchant_id(checkNull(record[2]));
                        vb.setAmount(BigDecimal.valueOf(Double.valueOf(checkNull(record[3]))));
                        vb.setTrans_date(checkNull(record[4]));
                        vb.setTrans_channel(checkNull(record[5]));
                        vb.setTrans_note(checkNull(record[7]));
                        vb.setTrans_status(checkNull(record[6]));
                        vb.setSubscriber_id(checkNull(record[8]));
                        vb.setIssuer_code(checkNull(record[9]));
                        vb.setPayment_code(checkNull(record[10]));
                        vb.setMobile_no(checkNull(record[11]));
                        vb.setAttempts(checkNull(record[12]));

                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                    }
                    records.add(vb);
                }

                final String sql_in = records.stream().filter(f -> f.getUnique_transid().startsWith("02") || f.getUnique_transid().startsWith("05") && !f.getUnique_transid().startsWith("02MPAY")).map(f -> "'" + f.getUnique_transid() + "'").collect(joining(","));

                if (!sql_in.isEmpty()) {

                    qry2 = "SELECT id,transid, substring(card_num,1,3) merchant_code FROM ecarddb.e_requestlog where transid in(" + sql_in + ")";

                    qry2 += " UNION SELECT id,transid, substring(card_num,1,3) merchant_code FROM ecarddb.e_requestlog_bk20210126 where transid in(" + sql_in + ")";

                    qry2 += " UNION SELECT id,transid, substring(card_num,1,3) merchant_code FROM ecarddb2.e_requestlog where transid in(" + sql_in + ")";

                    qry2 = "select * from(" + qry2 + ") as t1;";

                    Session session2 = DbHibernate.getSession("40.15MYSQL");
                    List<Object[]> result = new ArrayList<>();
                    try {
                        this.q = session2.createNativeQuery(qry2);
                        result = (List<Object[]>) this.q.getResultList();

                    } catch (Exception e) {
                        log.error(ERROR, e);

                    } finally {
                        if (session2 != null) {
                            try {
                                session2.close();
                            } catch (Exception e) {
                                log.error(e.getMessage(), e);
                            }
                        }
                    }
                    final HashMap<String, String> resp = mapBankResult(result);

                    final String trans = transType;
                    if (!records.isEmpty()) {
                        records.forEach(f -> {

                            if (f.getUnique_transid().startsWith("02") && !f.getUnique_transid().startsWith("02MPAY") || f.getUnique_transid().startsWith("05")) {

                                f.setIssuer_code(banksMap.getOrDefault(resp.getOrDefault(f.getUnique_transid(), ""), ""));
                            }
                        });
                    }

                } else {

                }
                records1.addAll(records);

            } else if (!bankCode.equals("ALL")) {
                qry2 = "SELECT id, transid, substring(card_num,1,3) merchant_code FROM ecarddb.e_requestlog where 1=1 "
                        + (service.equalsIgnoreCase("search") ? "  AND transid = :searchKey " : " and trans_date between :start_dt and :end_dt ")
                        + " and substring(transid, 1, 2) in ('02','05') and trans_code='P' and trans_descr like 'BILL:%' and substring(card_num , 1, 3)= :bankCode ";
                qry2 += " UNION SELECT id, transid, substring(card_num,1,3) merchant_code FROM ecarddb.e_requestlog_bk20210126 where 1=1 "
                        + (service.equalsIgnoreCase("search") ? "  AND transid = :searchKey " : " and trans_date between :start_dt and :end_dt ")
                        + " and substring(transid, 1, 2) in ('02','05') and trans_code='P' and trans_descr like 'BILL:%' and substring(card_num , 1, 3)= :bankCode ";
                qry2 += " UNION SELECT id, transid, substring(card_num,1,3) merchant_code FROM ecarddb2.e_requestlog where 1=1 "
                        + (service.equalsIgnoreCase("search") ? "  AND transid = :searchKey " : " and trans_date between :start_dt and :end_dt ")
                        + " and substring(transid, 1, 2) in ('02','05') and trans_code='P' and trans_descr like 'BILL:%' and substring(card_num , 1, 3)= :bankCode ";
                qry2 = "select * from (" + qry2 + ") as t1";
                Session session = DbHibernate.getSession("40.15MYSQL");

                List<Object[]> resr = new ArrayList<>();
                try {
                    this.q = session.createNativeQuery(qry2);

                    if (!service.equalsIgnoreCase("search")) {
                        this.q.setParameter("start_dt", (Object) start_dt)
                                .setParameter("end_dt", (Object) end_dt);
                    } else {
                        this.q.setParameter("searchKey", (Object) searchKey);
                    }
                    this.q.setParameter("bankCode", (Object) bankCode);
                    resr = (List<Object[]>) this.q.getResultList();
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
                for (Object[] record : resr) {
                    VasBillMerchantCode vb = new VasBillMerchantCode();
                    try {
                        vb.setId(Integer.parseInt(checkNull(record[0])));
                        vb.setTransid(checkNull(record[1]));
                        vb.setMerchant_code(checkNull(record[2]));

                    } catch (Exception gh) {
                        log.error(gh.getMessage(), gh);

                    }
                    res1.add(vb);
                }

                if (res1.size() > 0) {
                    final String sql_in = res1.stream().map(f -> "'" + f.getTransid() + "'").collect(joining(","));
                    if (!sql_in.isEmpty()) {
                        qry = "SELECT b.trans_no trans_no, b.unique_transid unique_transid, b.merchant_id merchant_id, b.trans_amount amount, b.trans_date trans_date, substring(b.unique_transid, 1, 2) trans_channel , b.trans_status trans_status, b.trans_note trans_note, b.subscriber_id subscriber_id, :bankCode as issuer_code , b.Payment_code Payment_code , b.mobile_no mobile_no, b.sp_status FROM  vasdb2.`t_paytrans` b left  join  fgdb.FUNDGATE_RESPONSE a  on b.unique_transid=a.etzRef where b.unique_transid in (" + sql_in + ") "
                                + (transType.equals("ALL") ? "" : " and b.merchant_id in (:transType) ")
                                + ((!uniqueTransId.trim().isEmpty()) ? " and b.unique_transid =  :uniqueTransId " : "")
                                + (!custAccount.trim().isEmpty() ? " and b.subscriber_id = :custAccount " : "")
                                + (trans_channel.equalsIgnoreCase("ALL") ? "" : " and substring(b.unique_transid, 1, 2) = :trans_channel ")
                                + (trans_status.equalsIgnoreCase("ALL") ? "" : (trans_status.equalsIgnoreCase("SUCCESSFUL") ? " and b.trans_status = '00'" : " and b.trans_status <> '00'"))
                                + " order by b.TRANS_DATE desc";

                        Session session2 = DbHibernate.getSession("40.9MYSQL");

                        try {
                            this.q = session2.createNativeQuery(qry).setParameter("bankCode", (Object) bankCode);
                            if (!trans_channel.equals("ALL")) {
                                this.q.setParameter("trans_channel", (Object) trans_channel);
                            }
                            if (!transType.equals("ALL")) {
                                this.q.setParameter("transType", (Object) transType);
                            }
                            if (!uniqueTransId.trim().isEmpty()) {
                                this.q.setParameter("uniqueTransId", (Object) uniqueTransId.trim());
                            }
                            if (!custAccount.trim().isEmpty()) {
                                this.q.setParameter("custAccount", (Object) custAccount.trim());
                            }

                            List<Object[]> re = this.q.getResultList();
                            for (Object[] record : re) {
                                VasBillTransaction vb = new VasBillTransaction();
                                try {
                                    vb.setTrans_no(checkNull(record[0]));
                                    vb.setUnique_transid(checkNull(record[1]));
                                    vb.setMerchant_id(checkNull(record[2]));
                                    vb.setAmount(BigDecimal.valueOf(Double.valueOf(checkNull(record[3]))));
                                    vb.setTrans_date(checkNull(record[4]));
                                    vb.setTrans_channel(checkNull(record[5]));
                                    vb.setTrans_note(checkNull(record[7]));
                                    vb.setTrans_status(checkNull(record[6]));
                                    vb.setSubscriber_id(checkNull(record[8]));
                                    vb.setIssuer_code(banksMap.getOrDefault(checkNull(record[9]), ""));
                                    vb.setPayment_code(checkNull(record[10]));
                                    vb.setMobile_no(checkNull(record[11]));
                                    vb.setAttempts(checkNull(record[12]));

                                } catch (Exception e) {
                                    log.error(e.getMessage(), e);
                                }
                                records.add(vb);
                            }

                        } catch (Exception fd) {
                            log.error(ERROR, fd);

                        } finally {
                            if (session != null) {
                                try {
                                    session.close();
                                } catch (Exception e) {
                                    log.error(e.getMessage(), e);
                                }
                            }
                        }
                        records1.addAll(records);
                    }
                }

                final String qry3 = "(SELECT b.trans_no trans_no, b.unique_transid unique_transid, b.merchant_id merchant_id, b.trans_amount amount, b.trans_date trans_date, substring(b.unique_transid, 1, 2) trans_channel , b.trans_status trans_status, b.trans_note trans_note, b.subscriber_id subscriber_id, (case when substring(b.UNIQUE_TRANSID, 1, 2)='cs' then substring(b.UNIQUE_TRANSID, 7, 3)  when substring(b.UNIQUE_TRANSID, 1, 2)='ET' then substring(b.UNIQUE_TRANSID, 3, 3) when substring(b.UNIQUE_TRANSID, 1, 2)='05' then substring(b.UNIQUE_TRANSID, 3, 3) when substring(b.UNIQUE_TRANSID, 1, 2)='09' then (select merchant_name from fgdb.cop_fundgate_info where terminal_id = a.terminal) when (substring(b.UNIQUE_TRANSID, 1, 2)='ju' or substring(b.UNIQUE_TRANSID, 1, 6)='02MPAY') then 'mobile money'  end) issuer_code , b.Payment_code Payment_code , b.mobile_no mobile_no, b.sp_status FROM  vasdb2.`t_paytrans` b left  join  fgdb.FUNDGATE_RESPONSE_old a  on b.unique_transid=a.etzRef where  (left(b.unique_transid,2) = '02' and left(b.unique_transid,6) = '02MPAY') and (case when substring(b.UNIQUE_TRANSID, 1, 2)='cs' then substring(b.UNIQUE_TRANSID, 7, 3)  when substring(b.UNIQUE_TRANSID, 1, 2)='ET' then substring(b.UNIQUE_TRANSID, 3, 3) when substring(b.UNIQUE_TRANSID, 1, 2)='05' then substring(b.UNIQUE_TRANSID, 3, 3) when substring(b.UNIQUE_TRANSID, 1, 2)='09' then (select merchant_name from fgdb.cop_fundgate_info where terminal_id = a.terminal) when (substring(b.UNIQUE_TRANSID, 1, 2)='ju' or substring(b.UNIQUE_TRANSID, 1, 6)='02MPAY') then 'mobile money'  end) = :bankCode   and b.TRANS_DATE between  :start_dt and :end_dt "
                        + (transType.equals("ALL") ? (type_id.equals("2000000000000044") ? " and b.merchant_id in('ADSL','VPP') " : "") : " and b.merchant_id in (:transType) ")
                        + (!uniqueTransId.trim().isEmpty() ? " and b.unique_transid =  :uniqueTransId " : "")
                        + (!custAccount.trim().isEmpty() ? " and b.subscriber_id = :custAccount " : "")
                        + (trans_channel.equals("ALL") ? "" : " and substring(b.unique_transid, 1, 2) = :trans_channel ")
                        + (trans_status.equalsIgnoreCase("ALL") ? "" : (trans_status.equalsIgnoreCase("SUCCESSFUL") ? " and b.trans_status = '00'" : " and b.trans_status <> '00'")) + " order by b.TRANS_DATE desc) "
                        + "UNION DISTINCT (SELECT b.trans_no trans_no, b.unique_transid unique_transid, b.merchant_id merchant_id, b.trans_amount amount, b.trans_date trans_date, substring(b.unique_transid, 1, 2) trans_channel , b.trans_status trans_status, b.trans_note trans_note, b.subscriber_id subscriber_id, (case when substring(b.UNIQUE_TRANSID, 1, 2)='cs' then substring(b.UNIQUE_TRANSID, 7, 3) when substring(b.UNIQUE_TRANSID, 1, 2)='ET' then substring(b.UNIQUE_TRANSID, 3, 3) when substring(b.UNIQUE_TRANSID, 1, 2)='05' then substring(b.UNIQUE_TRANSID, 3, 3) when substring(b.UNIQUE_TRANSID, 1, 2)='09' then (select merchant_name from fgdb.cop_fundgate_info where terminal_id = a.terminal) when (substring(b.UNIQUE_TRANSID, 1, 2)='ju' or substring(b.UNIQUE_TRANSID, 1, 6)='02MPAY') then 'mobile money'  end) issuer_code , b.Payment_code Payment_code , b.mobile_no mobile_no, b.sp_status FROM  vasdb2.`t_paytrans` b left  join  fgdb.FUNDGATE_RESPONSE a  on b.unique_transid=a.etzRef where (case when substring(b.UNIQUE_TRANSID, 1, 2)='cs' then substring(b.UNIQUE_TRANSID, 7, 3)  when substring(b.UNIQUE_TRANSID, 1, 2)='ET' then substring(b.UNIQUE_TRANSID, 3, 3) when substring(b.UNIQUE_TRANSID, 1, 2)='05' then substring(b.UNIQUE_TRANSID, 3, 3) when substring(b.UNIQUE_TRANSID, 1, 2)='09' then (select merchant_name from fgdb.cop_fundgate_info where terminal_id = a.terminal) when (substring(b.UNIQUE_TRANSID, 1, 2)='ju' or substring(b.UNIQUE_TRANSID, 1, 6)='02MPAY') then 'mobile money'  end) <> null and b.TRANS_DATE between :start_dt and :end_dt "
                        + (transType.equals("ALL") ? "" : " and b.merchant_id in (:transType) ")
                        + (!uniqueTransId.trim().isEmpty() ? " and b.unique_transid =  :uniqueTransId " : "")
                        + (!custAccount.trim().isEmpty() ? " and b.subscriber_id = :custAccount " : "")
                        + (trans_channel.equals("ALL") ? "" : " and substring(b.unique_transid, 1, 2) = :trans_channel ")
                        + (trans_status.equalsIgnoreCase("ALL") ? "" : (trans_status.equalsIgnoreCase("SUCCESSFUL") ? " and b.trans_status = '00'" : " and b.trans_status <> '00'")) + " order by b.TRANS_DATE desc) order by trans_date desc";

                Session session2 = DbHibernate.getSession("40.9MYSQL");
                List<Object[]> ret = new ArrayList<>();
                try {
                    this.q = session2.createNativeQuery(qry3)
                            .setParameter("start_dt", (Object) start_dt)
                            .setParameter("end_dt", (Object) end_dt)
                            .setParameter("bankCode", (Object) bankCode);
                    if (!trans_channel.equals("ALL")) {
                        this.q.setParameter("trans_channel", (Object) trans_channel);
                    }
                    if (!transType.equals("ALL")) {
                        this.q.setParameter("transType", (Object) transType);
                    }
                    if (!uniqueTransId.trim().isEmpty()) {
                        this.q.setParameter("uniqueTransId", (Object) uniqueTransId.trim());
                    }
                    if (!custAccount.trim().isEmpty()) {
                        this.q.setParameter("custAccount", (Object) custAccount.trim());
                    }
                    ret = this.q.getResultList();
                } catch (Exception gf) {
                    log.error(ERROR, gf);

                } finally {
                    if (session2 != null) {
                        try {
                            session2.close();
                        } catch (Exception e) {
                            log.error(e.getMessage(), e);
                        }
                    }
                }
                records = new ArrayList<>();
                for (Object[] record : ret) {
                    VasBillTransaction vb = new VasBillTransaction();
                    try {
                        vb.setTrans_no(checkNull(record[0]));
                        vb.setUnique_transid(checkNull(record[1]));
                        vb.setMerchant_id(checkNull(record[2]));
                        vb.setAmount(BigDecimal.valueOf(Double.valueOf(checkNull(record[3]))));
                        vb.setTrans_date(checkNull(record[4]));
                        vb.setTrans_channel(checkNull(record[5]));
                        vb.setTrans_note(checkNull(record[7]));
                        vb.setTrans_status(checkNull(record[6]));
                        vb.setSubscriber_id(checkNull(record[8]));
                        vb.setIssuer_code(checkNull(record[9]));
                        vb.setPayment_code(checkNull(record[10]));
                        vb.setMobile_no(checkNull(record[11]));
                        vb.setAttempts(checkNull(record[12]));

                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                    }
                    records.add(vb);
                }

                records1.addAll(records);

            }
        } catch (Exception l) {
            log.error(ERROR, l);

        }

        return records1;
    }

    public HashMap mapBankResult(List<Object[]> bankResult) {
        HashMap dMap = new HashMap();
        for (Object[] record : bankResult) {
            try {
                if (record[1] != null && record[2] != null) {
                    dMap.put(record[1].toString(), "" + record[2].toString());
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }

        return dMap;
    }

    public List<NODES> getMerchants(final String jsonString) {
        log.info("vasbillmerchants request received >> " + jsonString);
        final Gson j = new Gson();
        final ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);

        String mCode = apiData.getMerchant();
        String type_id = apiData.getType_id();
        String ucode = apiData.getUserCode();
        String userRoles = "";

        List<String> roleList = new ArrayList<>();
        List<NODES> recordsList = new ArrayList<>();
        String query = "";
        if (!type_id.isEmpty()) {
            if (type_id.contains("[0]") || type_id.contains("[6]")) {
                roleList.add("ALL");
            } else if (type_id.contains("[4]")) {
                userRoles = utility.getRoleParameters("[2000000000000053]", ucode);
                log.info("ucode: " + userRoles);
                if (!userRoles.isEmpty()) {
                    roleList = Pattern.compile("~").splitAsStream(userRoles).map(f -> f.split(":")[0]).collect(Collectors.toList());
                } else {
                    return recordsList;
                }
            } else {
                return recordsList;
            }
        } else {
            return recordsList;
        }

        Session session = DbHibernate.getSession("40.9MYSQL");

        log.info("ROLL ::: " + roleList);

        try {
            query = "SELECT alias id,  name from vasdb2.e_vas_node "
                    + (!roleList.contains("ALL") ? " where alias in (:alias) " : "")
                    + " order by alias asc ";
            this.q = session.createNativeQuery(query, NODES.class);
            if (!roleList.contains("ALL")) {
                q.setParameter("alias", (Object) roleList);
            }

            recordsList = q.getResultList();

            System.out.println("size: " + recordsList.size());

        } catch (Exception ek) {
            log.error(ERROR, ek);

        } finally {
            if (session != null) {
                session.close();
            }
        }

        return recordsList;
    }

    public List<VasBillTransaction> searchTransaction(final String jsonString) {
        System.out.println("vasbill search trx request received >> " + jsonString);
        final Gson j = new Gson();
        final ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);

        final String searchKey = apiData.getSearchKey();
        List<VasBillTransaction> records = new ArrayList<>();
        if (searchKey == null || searchKey.equalsIgnoreCase("")) {
            return null;
        }
        String qry = "";
        qry = "SELECT trans_no,unique_transid,merchant_id, trans_amount as amount, trans_date, trans_channel, trans_status, trans_note, subscriber_id, issuer_code ,Payment_code ,mobile_no, sp_status FROM vasdb2.`t_paytrans` WHERE unique_transid like :searchKey  order by trans_date DESC";

        Session session = DbHibernate.getSession("40.9MYSQL");
        List<Object[]> ret = new ArrayList<>();
        try {
            this.q = session.createNativeQuery(qry).setParameter("searchKey", (Object) ("%" + searchKey + "%"));
            ret = this.q.getResultList();
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
        for (Object[] record : ret) {
            VasBillTransaction vb = new VasBillTransaction();
            try {
                vb.setTrans_no(checkNull(record[0]));
                vb.setUnique_transid(checkNull(record[1]));
                vb.setMerchant_id(checkNull(record[2]));
                vb.setAmount(BigDecimal.valueOf(Double.valueOf(checkNull(record[3]))));
                vb.setTrans_date(checkNull(record[4]));
                vb.setTrans_channel(checkNull(record[5]));
                vb.setTrans_note(checkNull(record[7]));
                vb.setTrans_status(checkNull(record[6]));
                vb.setSubscriber_id(checkNull(record[8]));
                vb.setIssuer_code(checkNull(record[9]));
                vb.setPayment_code(checkNull(record[10]));
                vb.setMobile_no(checkNull(record[11]));
                vb.setAttempts(checkNull(record[12]));

            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            records.add(vb);
        }

        return records;
    }

    public List<VasBillTransaction> getBillStatusCheck(String jsonString) {

        log.info("Bill Status Check request received >>  " + jsonString);
        Gson j = new Gson();

        ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);
        String data = apiData.getUniqueTransId();
        String status = apiData.getStatus();

        String user_code = apiData.getUserCode();
        String type_id = apiData.getType_id();
        String username = apiData.getUserName();

        String userId = apiData.getUser_id();

        String ipAddress = apiData.getIpAddress();

        List<VasBillTransaction> records = new ArrayList<>();

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

            String query = "SELECT transid, merchant_id, merchant_code, trans_date, trans_period, trans_type, trans_channel, trans_amount, trans_status, trans_note, subscriber_id, mobile_no, card_subname, card_fullname, card_account, card_num, card_issuersubcodes, spnf_batchno, user_name, sp_status, trans_no, issuer_code, sub_code, payment_type, t_fullname, t_address, t_quantity, payment_code, cheque_no, cheque_bank, aut_username, unique_transid, int_status, process_status, status_description, response_date"
                    + " from vasdb2.t_paytrans where 1=1 "
                   // + "  AND unique_transid in (:reference) order by trans_date desc";
//             + "  AND unique_transid in (:reference)  AND trans_status <>'00'  order by trans_date desc";
                     + "  AND unique_transid in (:reference)  AND trans_status = '00' order by trans_date desc";
           

       //String qry = "SELECT trans_no,unique_transid,merchant_id, trans_amount as amount, trans_date, trans_channel, trans_status, trans_note, subscriber_id, issuer_code ,Payment_code ,mobile_no, sp_status FROM vasdb2.`t_paytrans` WHERE unique_transid like :searchKey  order by trans_date DESC";


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
           VasBillTransaction vb = new VasBillTransaction();
            try {
                vb.setTransid(checkNull(record[0]));
                vb.setMerchant_id(checkNull(record[1]));
                vb.setMerchant_code(checkNull(record[2]));
                vb.setTrans_date(checkNull(record[3]));
                vb.setTrans_period(checkNull(record[4]));
                vb.setTrans_type(checkNull(record[5]));
                vb.setTrans_channel(checkNull(record[6]));
                vb.setAmount(BigDecimal.valueOf(Double.valueOf(checkNull(record[7])))); 
                vb.setTrans_status(checkNull(record[8]));
                vb.setTrans_note(checkNull(record[9]));
                vb.setSubscriber_id(checkNull(record[10]));
                vb.setMobile_no(checkNull(record[11]));
                vb.setCard_subname(checkNull(record[12]));
                vb.setCard_fullname(checkNull(record[13]));
                vb.setCard_account(checkNull(record[14]));
                vb.setCard_num(checkNull(record[15]));
                vb.setCard_issuersubcodes(checkNull(record[16]));
                vb.setSpnf_batchno(checkNull(record[17]));
                vb.setUser_name(checkNull(record[18]));
                vb.setSp_status(checkNull(record[19]));
                vb.setTrans_no(checkNull(record[20]));
                vb.setIssuer_code(checkNull(record[21]));
                vb.setSub_code(checkNull(record[22]));
                vb.setPayment_type(checkNull(record[23]));
                vb.setT_fullname(checkNull(record[24]));
                vb.setT_address(checkNull(record[25]));
                vb.setT_quantity(checkNull(record[26]));
                vb.setPayment_code(checkNull(record[27]));
                vb.setCheque_no(checkNull(record[28]));
                vb.setCheque_bank(checkNull(record[29]));
                vb.setAut_username(checkNull(record[30]));
                vb.setUnique_transid(checkNull(record[31]));
                vb.setInt_status(checkNull(record[32]));
                vb.setProcess_status(checkNull(record[33]));
                vb.setStatus_description(checkNull(record[34]));
                vb.setResponse_date(checkNull(record[35]));
              
            } catch (Exception e) {
                log.error("error", e);
            }
            records.add(vb);
        }

        return records;
    }
    
    public List<VasBillTransaction> getAirtimeStatusCheck(String jsonString) {

        log.info("Airtime Status Check request received >>  " + jsonString);
        Gson j = new Gson();

        ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);
        String data = apiData.getUniqueTransId();
        String status = apiData.getStatus();

        String user_code = apiData.getUserCode();
        String type_id = apiData.getType_id();
        String username = apiData.getUserName();

        String userId = apiData.getUser_id();

        String ipAddress = apiData.getIpAddress();

        List<VasBillTransaction> records = new ArrayList<>();

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

            String query = "SELECT sequence,reference,unique_transid, amount, source_account, dest_account, linetype, source_balance, dest_balance, trans_date, response_code, response_message, source, response_date, original_amount,provider, voucher_type, original_transid, alias, attempts, check_digit, initial_response_code, process_mode, ip_address, total_attempts"
                    + " from vasdb2.t_provider_log where 1=1 "
                    + "  AND unique_transid in (:reference) order by trans_date desc";
             //+ "  AND unique_transid in (:reference)  AND response_code = '0'  order by trans_date desc";
           

       //String qry = "SELECT trans_no,unique_transid,merchant_id, trans_amount as amount, trans_date, trans_channel, trans_status, trans_note, subscriber_id, issuer_code ,Payment_code ,mobile_no, sp_status FROM vasdb2.`t_paytrans` WHERE unique_transid like :searchKey  order by trans_date DESC";

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
           VasBillTransaction vb = new VasBillTransaction();
              try {
                vb.setSequence(checkNull(record[0]));
                vb.setReference(checkNull(record[1]));
                vb.setUnique_transid(checkNull(record[2]));
                vb.setAmount(BigDecimal.valueOf(Double.valueOf(checkNull(record[3])))); 
                vb.setSource_account(checkNull(record[4]));
                vb.setDest_account(checkNull(record[5]));
                vb.setLinetype(checkNull(record[6]));
                vb.setSource_balance(checkNull(record[7]));
                vb.setDest_balance(checkNull(record[8]));
                vb.setTrans_date(checkNull(record[9]));
                vb.setResponse_code(checkNull(record[10]));
                vb.setResponse_message(checkNull(record[11]));
                vb.setSource(checkNull(record[12]));
                vb.setResponse_date(checkNull(record[13]));
                vb.setOriginal_amount(checkNull(record[14]));
                vb.setProvider(checkNull(record[15]));
                vb.setVoucher_type(checkNull(record[16]));
                vb.setOriginal_transid(checkNull(record[17]));
                vb.setAlias(checkNull(record[18]));
                vb.setAttempts(checkNull(record[19]));
                vb.setCheck_digit(checkNull(record[20]));
                vb.setInitial_response_code(checkNull(record[21]));
                vb.setProcess_mode(checkNull(record[22]));
                vb.setIp_address(checkNull(record[23]));
                vb.setAttempts(checkNull(record[24]));
              
              
            } catch (Exception e) {
                log.error("error", e);
            }
            records.add(vb);
        }

        return records;
    }
    
        public List<VasBillTransaction> getBillAndAirtimeStatusCheck(String jsonString) {

        log.info("Bill And Airtime Status Check request received >>  " + jsonString);
        Gson j = new Gson();

        ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);
        String data = apiData.getUniqueTransId();
        String status = apiData.getStatus();

        String user_code = apiData.getUserCode();
        String type_id = apiData.getType_id();
        String username = apiData.getUserName();

        String userId = apiData.getUser_id();

        String ipAddress = apiData.getIpAddress();

        List<VasBillTransaction> records = new ArrayList<>();

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



            String query = "SELECT vas_id, acct_no, alias, amount, client_ref, tran_dt, response_code, message, narration, node_ref, node_response, prod_id, vas_ref, subscriber, third_party_ref, channel_id, terminal_id, bank_code, line_type,line_mode, client, other_info"
                    + " from vasdb2.e_vas_trans where 1=1 "
                    + "  AND client_ref in (:reference) order by tran_dt desc";
            // + "  AND unique_transid in (:reference)  AND TRANS_STATUS <>'00'  order by trans_date desc";

       //String qry = "SELECT trans_no,unique_transid,merchant_id, trans_amount as amount, trans_date, trans_channel, trans_status, trans_note, subscriber_id, issuer_code ,Payment_code ,mobile_no, sp_status FROM vasdb2.`t_paytrans` WHERE unique_transid like :searchKey  order by trans_date DESC";

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
           VasBillTransaction vb = new VasBillTransaction();
              try {
                vb.setVas_id(checkNull(record[0]));
                vb.setAcct_no(checkNull(record[1]));
                vb.setAlias(checkNull(record[2]));
                vb.setAmount(BigDecimal.valueOf(Double.valueOf(checkNull(record[3])))); 
                vb.setClient_ref(checkNull(record[4]));
                vb.setTran_dt(checkNull(record[5]));
                vb.setResponse_code(checkNull(record[6]));
                vb.setMessage(checkNull(record[7]));
                vb.setNarration(checkNull(record[8]));
                vb.setNode_ref(checkNull(record[9]));
                vb.setNode_response(checkNull(record[10]));
                vb.setProd_id(checkNull(record[11]));
                vb.setVas_ref(checkNull(record[12]));
                vb.setSubscriber(checkNull(record[13]));
                vb.setThird_party_ref(checkNull(record[14]));
                vb.setChannel_id(checkNull(record[15]));
                vb.setTerminal_id(checkNull(record[16]));
                vb.setBank_code(checkNull(record[17]));
                vb.setLinetype(checkNull(record[18]));
                vb.setLine_mode(checkNull(record[19]));
                vb.setClient(checkNull(record[20]));
                vb.setOther_info(checkNull(record[21]));
               
              
            } catch (Exception e) {
                log.error("error", e);
            }
            records.add(vb);
        }

        return records;
    }

}
