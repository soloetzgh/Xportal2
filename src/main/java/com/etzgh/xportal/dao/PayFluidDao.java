package com.etzgh.xportal.dao;

import com.etzgh.xportal.model.ApiPostData;
import com.etzgh.xportal.model.NODES;
import com.etzgh.xportal.model.Payfluid;
import com.etzgh.xportal.utility.DbHibernate;
import com.etzgh.xportal.utility.GeneralUtility;
import com.google.gson.Gson;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import org.hibernate.Session;

public class PayFluidDao {

    private static final GeneralUtility utility = new GeneralUtility();
    private static final Logger log = Logger.getLogger(PayFluidDao.class.getName());
    Query q;

    public List<Payfluid> getPayfluidTransactions(String jsonString) {

        log.info("payfluid trx request received >> " + jsonString);
        Gson j = new Gson();
        List<Payfluid> recordsList = new ArrayList<>();
        ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);

        String start_dt = apiData.getStartDate();
        String end_dt = apiData.getEndDate();
        String etzRef = apiData.getUniqueTransId();
        String payLink = apiData.getBankid();
        String transType = apiData.getTransType();
        String clientRef = apiData.getTransId();
        String plusMerchant = apiData.getMerchant();
        String status = apiData.getStatus();
        List<String> trans_status = new ArrayList<>();
        String type_id = apiData.getType_id();
        String service = apiData.getService();
        String searchKey = apiData.getSearchKey();
        String ucode = apiData.getUserCode();
        String userRoles = "";
        List<String> roleList = new ArrayList<>();

        if (status.equalsIgnoreCase("successful")) {
            trans_status.add("0");
        } else if (status.equalsIgnoreCase("pending")) {
            trans_status.add("58");
            trans_status.add("66");
        } else if (status.equalsIgnoreCase("failed")) {
            trans_status.add("06");
            trans_status.add("6");
        } else {
            trans_status.add("all");
        }

        String qry = "";

        if (searchKey == null) {
            searchKey = "";
        }

        if (service.equalsIgnoreCase("search") && searchKey.trim().isEmpty()) {
            return recordsList;
        }

        if (!type_id.isEmpty()) {
            log.info("Type: " + type_id);
            if (type_id.contains("[0]")) {
                roleList.add(plusMerchant);
            } else if (type_id.contains("[11]")) {
                userRoles = utility.getRoleParameters("[2500000000000042]", ucode);
//                log.info("usrRoles: " + userRoles);
                roleList = Arrays.asList(userRoles.split("~"));

                if (plusMerchant.equalsIgnoreCase("ALL")) {

                } else {
                    if (roleList.contains(plusMerchant)) {
                        roleList = new ArrayList<>(roleList);
                        roleList.add(plusMerchant);
                    }
                }
            } else {
                return recordsList;
            }
        } else {
            return recordsList;
        }

        qry = "select a.txn_ref, a.client_ref, a.pay_mthd, a.pay_scheme, a.payee_name, a.pay_descr, a.payee_email, b.mobile payee_phone, a.pay_amt, a.fee, a.pay_merchnm, "
                + "a.pay_status, a.pay_statusmsg, a.created, a.paygw_authref,  a.pay_instr from payfluid.txn_paid a left join payfluid.txn_pendn b on (a.client_ref = b.client_ref and a.pay_merchid = b.client_id) where right(a.txn_ref,3) not in('_vc') "
                + (!service.equalsIgnoreCase("search") ? " and a.created between :start_dt and :end_dt " : " and (a.paygw_authref =:searchKey or a.txn_ref = :searchKey or a.client_ref=:searchKey or a.pay_ref=:searchKey) ")
                + (!roleList.contains("ALL") ? " AND a.pay_merchid in (:merchant) " : "")
                + (!etzRef.isEmpty() ? " and a.txn_ref = :txn_ref " : "")
                + (!clientRef.isEmpty() ? " and a.client_ref = :client_ref " : "")
                + (!transType.equals("ALL") ? " and a.pay_scheme = :pay_scheme " : "")
                + (!payLink.isEmpty() ? " and a.pay_ref = :pay_ref " : "")
                + (!trans_status.isEmpty() && !trans_status.contains("all") ? " and a.pay_status in (:trans_status) " : "")
                + " order by a.created desc";
        System.out.println("Query" + qry);
        Session session = DbHibernate.getSession("30.39MYSQL");
        List<Object[]> resp = new ArrayList<>();
        try {
            q = session.createNativeQuery(qry);

            if (!service.equalsIgnoreCase("search")) {
                q.setParameter("start_dt", (Object) start_dt)
                        .setParameter("end_dt", (Object) end_dt);
            } else {
                q.setParameter("searchKey", (Object) searchKey.trim());
            }

            if (!roleList.contains("ALL")) {
                q.setParameter("merchant", roleList);
            }
            if (!etzRef.isEmpty()) {
                q.setParameter("txn_ref", etzRef.trim());
            }
            if (!clientRef.isEmpty()) {
                q.setParameter("client_ref", clientRef.trim());
            }
            if (!transType.equals("ALL")) {
                q.setParameter("pay_scheme", transType.trim());
            }
            if (!payLink.isEmpty()) {
                q.setParameter("pay_ref", payLink.trim());
            }
            if (!trans_status.isEmpty() && !trans_status.contains("all")) {
                q.setParameter("trans_status", trans_status);

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
            Payfluid pf = new Payfluid();
            try {
                pf.setTxn_ref(checkNull(record[0]));
                pf.setClient_ref(checkNull(record[1]));
                pf.setPaygw_authref(checkNull(record[14]));
                pf.setPay_mthd(checkNull(record[2]));
                pf.setPay_scheme(checkNull(record[3]));
                pf.setPay_instr(checkNull(record[15]));
                pf.setPayee_name(checkNull(record[4]));
                pf.setPay_descr(checkNull(record[5]));
                pf.setPayee_email(checkNull(record[6]));
                pf.setPayee_phone(checkNull(record[7]));
                pf.setPay_amt(Double.valueOf(checkNull(record[8])));
                pf.setFee(Double.valueOf(checkNull(record[9])));
                pf.setPay_merchnm(checkNull(record[10]));
                pf.setPay_status(checkNull(record[11]));
                pf.setPay_statusmsg(checkNull(record[12]));
                pf.setCreated(checkNull(record[13]));

            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            recordsList.add(pf);
        }

        String momoRefs = recordsList.stream().filter(f -> f.getPay_mthd().equalsIgnoreCase("wallets")).map(f -> "'" + f.getPaygw_authref() + "'")
                .collect(Collectors.joining(","));

        HashMap map = getMobileMoneyRecord(momoRefs);

        recordsList.forEach(f -> {
            try {
                if (f.getPay_mthd().equalsIgnoreCase("wallets")) {
                    f.setPay_instr(map.getOrDefault(checkNull(f.getPaygw_authref()) + "", checkNull(f.getPay_instr())).toString().split("[|]")[0]);
                    f.setPay_transid(map.getOrDefault(checkNull(f.getPaygw_authref()) + "", "|N/A").toString().split("[|]")[1]);
                } else {
                    f.setPay_transid("");
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        });

        return recordsList;

    }

    public HashMap getMobileMoneyRecord(String momoRefs) {
        HashMap dMap = new HashMap();
        List<Object[]> channelRs = new ArrayList<>();
        if (!momoRefs.isEmpty()) {

            String channelQry = "select  reference a , concat(msisdn, '|',(case when clientId != '' then ifnull(clientid,'N/A') else 'N/A' end)) b from  telcodb.mobilemoney where  reference  in (" + momoRefs + ")";

            try {
                Session session = DbHibernate.getSession("40.9MYSQL");
                try {
                    q = session.createNativeQuery(channelQry);

                    channelRs = q.getResultList();

                } catch (Exception e) {
                    log.info("PAYFLUID MOMO ERROR2 ::: ", e);
                } finally {
                    if (session != null) {
                        session.close();
                    }
                }
                for (Object[] record : channelRs) {

                    dMap.put(record[0].toString(), "" + record[1].toString());

                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        return dMap;
    }

    public List<NODES> getMerchants(final String jsonString) {
        log.info("payfluid merchants request received >> " + jsonString);
        final Gson j = new Gson();
        final ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);

        String type_id = apiData.getType_id();
        String ucode = apiData.getUserCode();
        String userRoles = "";

        List<String> roleList = new ArrayList<>();

        List<NODES> recordsList = new ArrayList<>();
        String query = "";
        if (!type_id.isEmpty()) {
            if (type_id.contains("[0]")) {
                roleList.add("ALL");
            } else if (type_id.contains("[11]")) {
                userRoles = utility.getRoleParameters("[2500000000000042]", ucode);
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

        Session session = DbHibernate.getSession("30.39MYSQL");

        try {

            query = "SELECT mName name , mIdNo id from payfluid.merch "
                    + (!roleList.contains("ALL") ? " where mIdNo in (:alias) " : "")
                    + " order by mName asc ";
            q = session.createNativeQuery(query, NODES.class);
            if (!roleList.contains("ALL")) {
                q.setParameter("alias", (Object) roleList);
            }

            recordsList = q.getResultList();

        } catch (Exception ek) {
            log.error(ek.getMessage(), ek);

        } finally {
            if (session != null) {
                session.close();
            }
        }

        return recordsList;
    }

    protected String checkNull(Object data) {

        if (data != null && !data.equals("")) {
            return data.toString();
        }
        return "NULL";
    }

    protected Date convertDate(String data) throws ParseException {
        if (!data.equals("NULL")) {
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(data);
            return date;
        }
        return null;
    }

}
