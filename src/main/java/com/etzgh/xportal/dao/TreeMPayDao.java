package com.etzgh.xportal.dao;

import com.etzgh.xportal.model.ApiPostData;
import com.etzgh.xportal.model.MerchantPay;
import com.etzgh.xportal.model.NODES;
import com.etzgh.xportal.model.TreeSettlement;
import com.etzgh.xportal.utility.DbHibernate;
import com.etzgh.xportal.utility.GeneralUtility;
import com.etzgh.xportal.utility.HashNumber;
import com.google.gson.Gson;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class TreeMPayDao {

    private static final Logger log = Logger.getLogger(TreeMPayDao.class.getName());
    private static final GeneralUtility utility = new GeneralUtility();
    Query q;

    public static void main(String[] args) {
        String r = "{\"startDate\":\"2021-04-10 00:00\",\"endDate\":\"2021-04-20 23:59\",\"merchant\":\"ALL\",\"product\":\"\",\"status\":\"ALL\",\"service\":\"transactions\",\"action\":\"\",\"apiSecret\":\"\",\"apiCode\":\"\",\"userCode\":\"[2500000000000045]|ALL\",\"admin\":\"\",\"type_desc\":\"\",\"role_id\":\"\",\"type_id\":\"[47],[48]\",\"userName\":\"\",\"ClientId\":\"\",\"user_id\":\"10500000000000754\",\"pageNumber\":1,\"rowsPerPage\":1,\"uniqueTransId\":\"\",\"trans_code\":\"\",\"transType\":\"\",\"subscriberId\":\"\",\"trans_status\":\"\",\"channel\":\"\",\"bank\":\"\",\"biller\":\"\",\"transId\":\"\",\"bankid\":\"\",\"from_source\":\"\",\"card_num\":\"\",\"optionType\":\"\",\"payType\":\"\",\"wcMerchant\":\"\",\"destination\":\"\",\"lineType\":\"\",\"trans_channel\":\"\",\"pan\":\"\",\"terminal_id\":\"\",\"card_account\":\"\",\"cop_issuercode\":\"\",\"cop_role_id\":\"\",\"cop_company_id\":\"\",\"mobile_no\":\"\",\"phone\":\"\",\"type\":\"\",\"amount\":\"\",\"amount2\":\"\",\"refundStatus\":\"\",\"partialReversal\":\"\",\"reversalReason\":\"\",\"ipAddress\":\"\",\"userData\":\"\",\"appId\":[],\"company\":\"\",\"options\":{}}";
        new TreeMPayDao().getMerchantPayTrans(r);
    }

    public List<MerchantPay> getMerchantPayTrans(final String jsonString) {

        System.out.println("Tree MerchantPay trx request received >> " + jsonString);
        final Gson j = new Gson();
        final ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);

        final String start_dt = apiData.getStartDate();
        final String end_dt = apiData.getEndDate();
        String reference = apiData.getUniqueTransId();
        String merchant = apiData.getMerchant();
        final String status = apiData.getStatus();
        final String source_frm = apiData.getFrom_source();
        final String ucode = apiData.getUserCode();
        List<MerchantPay> recordsList = new ArrayList<>();
        String query = "";
        final String type_id = apiData.getType_id();
        final String service = apiData.getService();
        String searchKey = apiData.getSearchKey();
        List<String> roleList = new ArrayList<>();
        List<Object[]> records = new ArrayList<>();
        String userRoles = "";

        String str_source_frm = source_frm;
        if (reference == null) {
            reference = "";
        }
        if (str_source_frm == null) {
            str_source_frm = "";
        }

        if (service.equalsIgnoreCase("search") && searchKey.trim().isEmpty()) {
            return recordsList;
        }

        if (!type_id.isEmpty()) {
            if (type_id.contains("[0]")) {
                roleList.add(merchant.toUpperCase());
            } else if (type_id.contains("[47]")) {
                userRoles = utility.getRoleParameters("[2500000000000045]", ucode);
                log.info("ucode: " + userRoles);
                if (!userRoles.isEmpty()) {

                    roleList = Arrays.asList(userRoles.split("~"));

                    if (roleList.contains("ALL")) {
                        roleList = new ArrayList<>();
                        roleList.add(merchant);
                    } else if (roleList.contains(merchant)) {

                        roleList = new ArrayList<>();
                        roleList.add(merchant);
                    }

                    log.info("result: " + roleList);

                } else {
                    return recordsList;
                }
            } else {
                return recordsList;
            }
        } else {
            return recordsList;
        }

        query = "SELECT etz_ref_id, '' destacct, channel, mobile_no, merchant,  ifnull(fee, 0.00) fee, ifnull(trans_amount,0.00) trans_amount, 3rdParty_paymt_ref transid, (case when channel ='card' then UPPER(callback_msg) else trans_status end) trans_status, a.status, '' value_msg, \n"
                + " trans_date, '' callback_msg, 'BILL' mainoptions, '' flag, '' provider, reference, product trans_type, a.name, extra_info, a.product mmda FROM ussd_db.etz_mpay_report a where 1=1 and a.payment_alias ='6TREE' "
                + (service.equalsIgnoreCase("search") ? " AND etz_ref_id = :searchKey " : " and trans_date BETWEEN :start_dt and :end_dt ")
                + (reference.trim().isEmpty() ? "" : " AND etz_ref_id = :reference ")
                + (!roleList.isEmpty() && !roleList.contains("ALL") ? " and product in (:vastype) " : "")
                + (str_source_frm.trim().isEmpty() ? "" : " AND mobile_no like :dest ")
                + (!status.equalsIgnoreCase("all") ? (status.equalsIgnoreCase("successful") ? " AND a.status = '00' " : " and a.status <> '00' ") : "")
                + " order by trans_date desc";

        Session session = DbHibernate.getSession("USSDDBMYSQL");
        try {
            log.info("TreeMerchant query " + query);

            q = session.createNativeQuery(query);

            if (!service.equalsIgnoreCase("search")) {
                q.setParameter("start_dt", (Object) start_dt)
                        .setParameter("end_dt", (Object) end_dt);
            } else {
                q.setParameter("searchKey", (Object) searchKey.trim());
            }
            if (!reference.trim().isEmpty()) {
                q.setParameter("reference", (Object) reference.trim());
            }

            if (!roleList.isEmpty() && !roleList.contains("ALL")) {
                q.setParameter("vastype", (Object) roleList);
            }

            if (!str_source_frm.trim().isEmpty()) {
                q.setParameter("dest", "%" + (Object) str_source_frm.trim());
            }

            records = (List<Object[]>) q.getResultList();

        } catch (Exception et) {
            log.error(et.getMessage(), et);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        Map<String, String> merchants = getMerchants();
        System.out.println("MerchantPay Records Select done");
        for (final Object[] record : records) {
            final MerchantPay mp = new MerchantPay();
            try {

                mp.setEtz_reference(checkNull(record[0]));
                mp.setMobile_no(checkNull(record[3]));
                mp.setPayment_type(checkNull(record[2]));
                mp.setTransid(checkNull(record[7]));
                mp.setMerchant(checkNull(record[20]));
                mp.setReference(checkNull(record[16]));
                mp.setTrans_status(checkNull(record[9]));
                mp.setStatus(checkNull(record[8]));
                mp.setAmount(BigDecimal.valueOf(Double.valueOf(record[6].toString())));

                mp.setName(merchants.getOrDefault(checkNull(record[18]), "N/A"));

                mp.setTrans_date(checkNull(record[11]));
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }

            recordsList.add(mp);
        }

        return recordsList;

    }

    public List<TreeSettlement> getTreeSettlementTrans(String jsonString) {

        System.out.println("TreeSettlement trx request received >> " + jsonString);
        Gson j = new Gson();
        ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);

        String start_dt = apiData.getStartDate();
        String end_dt = apiData.getEndDate();
        String reference = apiData.getUniqueTransId();
        String merchant = apiData.getMerchant();
        String status = apiData.getStatus();
        String ucode = apiData.getUserCode();
        final String service = apiData.getService();
        String searchKey = apiData.getSearchKey();
        List<TreeSettlement> recordsList = new ArrayList<>();
        String query = "";
        String type_id = apiData.getType_id();
        List<String> roleList = new ArrayList<>();
        List<Object[]> records = new ArrayList<>();
        if (reference == null) {
            reference = "";
        }

        switch (status.toLowerCase()) {
            case "successful":
                status = "00";
                break;
            case "pending":
                status = "P";
                break;
            case "failed":
                status = "X";
                break;
            default:
                status = "";
                break;
        }

        if (service.equalsIgnoreCase("mpaysettlementsearch") && searchKey.trim().isEmpty()) {
            return recordsList;
        }

        String userRoles = "";

        if (!type_id.isEmpty()) {
            if (type_id.contains("[0]")) {
                roleList.add(merchant.toUpperCase());
            } else if (type_id.contains("[47]")) {
                userRoles = utility.getRoleParameters("[2500000000000045]", ucode);
                log.info("ucode: " + userRoles);
                if (!userRoles.isEmpty()) {

                    roleList = Arrays.asList(userRoles.split("~"));

                    if (roleList.contains("ALL")) {
                        roleList = new ArrayList<>();
                        roleList.add(merchant);
                    } else if (roleList.contains(merchant)) {

                        roleList = new ArrayList<>();
                        roleList.add(merchant);
                    }

                } else {
                    return recordsList;
                }
            } else {
                return recordsList;
            }
        } else {
            return recordsList;
        }

        query = "select a.unique_transid, a.phone_number, b.name, a.settle_amount, a.trans_date, "
                + "a.settlement_ref, a.account_number, a.status, a.attempts, a.date as settlement_date "
                + "from tree_db.mmda_settlement a left join tree_db.mmda_info b on a.mmda = b.alias where 1=1 "
                + (service.equalsIgnoreCase("mpaysettlementsearch") ? " AND (settlement_ref = :searchKey or unique_transid = :searchKey) " : "  and date BETWEEN :start_dt and :end_dt ")
                + (!roleList.isEmpty() && !roleList.contains("ALL") ? " and mmda in (:merchant) " : "")
                + (!status.equalsIgnoreCase("all") && !status.equals("") ? " and status = :status " : "")
                + (!reference.trim().isEmpty() ? " and (settlement_ref = :reference or unique_transid = :reference) " : "")
                + " order by date desc";

        System.out.println("TreeSettlement query " + query);
        Session session = DbHibernate.getSession("40.9MYSQL");
        try {
            q = session.createNativeQuery(query);

            if (!service.equalsIgnoreCase("mpaysettlementsearch")) {
                q.setParameter("start_dt", (Object) start_dt)
                        .setParameter("end_dt", (Object) end_dt);
            } else {
                q.setParameter("searchKey", (Object) searchKey.trim());
            }

            if (!status.equalsIgnoreCase("all") && !status.equals("")) {
                q.setParameter("status", status);
            }
            if (!roleList.isEmpty() && !roleList.contains("ALL")) {
                q.setParameter("merchant", roleList);
            }
            if (!reference.trim().isEmpty()) {
                q.setParameter("reference", reference);
            }

            records = (List<Object[]>) q.getResultList();
            System.out.println("TreeSettlement Records Select done");

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }

        for (final Object[] record : records) {
            final TreeSettlement mp = new TreeSettlement();
            try {

                mp.setUnique_transid(checkNull(record[0]));
                mp.setPhone_number(checkNull(record[1]));
                mp.setMmda(checkNull(record[2]));
                mp.setSettle_amount(BigDecimal.valueOf(Double.valueOf(record[3].toString())));
                mp.setTrans_date(checkNull(record[4]));
                mp.setSettlement_ref(checkNull(record[5]));
                mp.setAccount_number(hashAccount(checkNull(record[6])));
                mp.setStatus(checkNull(record[7]));
                mp.setAttempts(Integer.parseInt(record[8].toString()));
                mp.setSettlment_date(checkNull(record[9]));
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }

            recordsList.add(mp);
        }

        return recordsList;

    }

    public List<NODES> getMerchants(final String jsonString) {
        log.info("tree merchants request received >> " + jsonString);
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
            } else if (type_id.contains("[47]")) {
                userRoles = utility.getRoleParameters("[2500000000000045]", ucode);
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

        try {

            query = "SELECT alias id, name FROM tree_db.`mmda_info` "
                    + (!roleList.contains("ALL") ? " where alias in (:alias) " : "")
                    + " order by name asc ";
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

    public Map<String, String> getMerchants() {

        Session session = DbHibernate.getSession("40.9MYSQL");
        List<Object[]> records = new ArrayList<>();
        Map<String, String> m = new HashMap<>();
        try {

            String query = "SELECT ifnull(alias id,''), name FROM tree_db.`mmda_info` order by name asc";
            q = session.createNativeQuery(query);

            records = (List<Object[]>) q.getResultList();
            for (final Object[] record : records) {
                try {
                    m.put(record[0].toString(), record[1].toString());
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }

        } catch (Exception ek) {
            log.error(ek.getMessage(), ek);

        } finally {
            if (session != null) {
                session.close();
            }
        }
        return m;
    }

    protected String checkNull(Object data) {

        if (data != null && !data.equals("")) {
            return data.toString();
        }
        return "NULL";
    }

    protected String hashAccount(String accNo) {

        if (accNo == null || accNo.equalsIgnoreCase("null") || accNo.trim().isEmpty()) {
        } else {
            int start = accNo.length() / 2;
            HashNumber hn = new HashNumber();

            accNo = hn.hashStringValue(accNo, start / 2, start / 2);
        }
        return accNo;
    }
}
