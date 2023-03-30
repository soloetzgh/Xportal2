package com.etzgh.xportal.dao;

import com.etzgh.xportal.model.ApiPostData;
import com.etzgh.xportal.model.JustPayTransaction;
import com.etzgh.xportal.model.NODES;
import com.etzgh.xportal.utility.DbHibernate;
import com.etzgh.xportal.utility.GeneralUtility;
import com.google.gson.Gson;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.log4j.Logger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class JustpayDao {

    private static final GeneralUtility utility = new GeneralUtility();
    private static final Logger log = Logger.getLogger(JustpayDao.class.getName());

    public List<JustPayTransaction> getJustPayTransactions(final String jsonString) {
        log.info("JustPay trx request received >> {0}" + jsonString);
        final Gson j = new Gson();
        final ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);

        final String beginDate = apiData.getStartDate();
        final String endDate = apiData.getEndDate();
        String uniqueTransId = apiData.getUniqueTransId();
        String transType = apiData.getTransType();
        String trans_status = apiData.getTrans_status();
        String option = apiData.getTrans_code();
        String dest = apiData.getDestination();
        String source = apiData.getSubscriberId();
        final String type_id = apiData.getType_id();
        final String user_code = apiData.getUserCode();
        String merchant = apiData.getMerchant();
        final String searchKey = apiData.getSearchKey();
        final String service = apiData.getService();
        String shortcode = apiData.getPayType();
        List<String> shortCodeList = new ArrayList<>();

        List<JustPayTransaction> records = new ArrayList<>();
        List<String> roleList = new ArrayList<>();
        String userRoles = "";
        String query = "";
        String gloQuery = "";

        if (uniqueTransId == null) {
            uniqueTransId = "";
        }
        if (shortcode == null) {
            shortcode = "";
        }
        if (trans_status == null || trans_status.trim().isEmpty()) {
            trans_status = "ALL";
        }

        if (source == null) {
            source = "";
        }
        if (option == null) {
            option = "";
        }
        if (transType == null) {
            transType = "";
        }
        if (dest == null) {
            dest = "";
        }
        if (merchant == null) {
            merchant = "";
        }
        if (service.equalsIgnoreCase("search") && searchKey.trim().isEmpty()) {
            return records;
        }

        if (service.equalsIgnoreCase("search")) {
            uniqueTransId = searchKey;
        }
        if (!type_id.isEmpty()) {
            if (type_id.contains("[0]")) {
                if (!merchant.trim().isEmpty()) {
                    roleList.add(merchant.toUpperCase());
                }
            } else if (type_id.contains("[3]")) {
                userRoles = utility.getRoleParameters("[2000000000000054]", user_code);
                log.info("ucode: {0}" + userRoles);
                if (!userRoles.isEmpty()) {

                    roleList = Arrays.asList(userRoles.split("~"));
                    if (merchant.equalsIgnoreCase("ALL")) {

                    } else {
                        if (roleList.contains(merchant)) {
                            roleList = new ArrayList<>(roleList);
                            roleList.add(merchant);
                        }
                    }

                    log.info("result: {0}" + roleList);

                } else {
                    return records;
                }
            } else {
                return records;
            }
        } else {
            return records;
        }
        log.info("ROLES: {0}" + roleList);

        if (!shortcode.trim().isEmpty()) {
            shortCodeList.add(shortcode.trim());
            if (!shortcode.trim().endsWith("#")) {
                shortCodeList.add(shortcode.trim() + "#");
            }
        }

        query = "SELECT a.reference, a.provider,a.mmno, ifnull(a.fee, 0) as fee,a.destacct,a.amount,(case when a.mainoptions='transfer' then ifnull(a.value_code, 'null')  when a.mainoptions='airtime' then ifnull(d.response_code, 'null') else ifnull(c.trans_status, 'null') end) value_code, "
                + "(case when a.mainoptions = 'airtime' then d.response_message when a.mainoptions ='bill' then c.trans_note else a.value_msg end) value_msg,a.created_date,(case when b.respcode in('0','00') then 'SUCCESSFUL' when b.respcode ='58' then 'PENDING' else 'FAILED' end) callback_msg,a.mainoptions,a.vastype,b.clientid,b.flag from  telcodb.just_pay a left JOIN telcodb.mobilemoney b ON SUBSTRING_INDEX(a.reference,'#',1) = b.reference "
                + "left join vasdb2.t_paytrans c on a.reference=c.unique_transid left join vasdb2.t_provider_log d on a.reference=d.unique_transid  WHERE 1=1 "
                + (service.equalsIgnoreCase("search") ? "  AND a.reference LIKE :reference "
                : " and a.created_date between :beginDate  and :endDate ")
                + (!service.equalsIgnoreCase("search") && !uniqueTransId.trim().isEmpty()
                ? " AND a.reference LIKE :reference "
                : "")
                + ((!transType.equalsIgnoreCase("ALL") && !transType.equalsIgnoreCase(""))
                ? " AND a.provider LIKE :transType "
                : "")
                + ((!option.equalsIgnoreCase("") && !option.equalsIgnoreCase("ALL")) ? " AND a.mainoptions = :option "
                : "")
                + (trans_status.equalsIgnoreCase("ALL") ? ""
                : (trans_status.equalsIgnoreCase("0")
                ? " and (case when a.mainoptions='transfer' then ifnull(a.value_code, 'null')  when a.mainoptions='airtime' then ifnull(d.response_code, 'null') else ifnull(c.trans_status, 'null') end) in ('0','00') "
                : " and (case when a.mainoptions='transfer' then ifnull(a.value_code, 'null')  when a.mainoptions='airtime' then ifnull(d.response_code, 'null') else ifnull(c.trans_status, 'null') end)  not in ('0','00') "))
                + (!source.trim().isEmpty() ? " AND a.mmno LIKE :source " : "")
                + (!roleList.contains("ALL") && !roleList.isEmpty() ? " AND a.vastype in (:merchant) " : "")
                + (!dest.trim().isEmpty() ? " AND a.destacct LIKE :dest " : "")
                + (!shortCodeList.isEmpty() ? " and a.shortcode in (:shortcode) " : "")
                + gloQuery
                + " order by a.created_date desc";

        Session session = DbHibernate.getSession("40.9MYSQL");

        try {
            Query q = session.createNativeQuery(query);

            if (!service.equalsIgnoreCase("search")) {
                q.setParameter("beginDate", (Object) beginDate)
                        .setParameter("endDate", (Object) endDate);
            } else {
                q.setParameter("reference", (Object) uniqueTransId.trim());
            }

            if (!transType.equalsIgnoreCase("ALL") && !transType.equalsIgnoreCase("")) {
                q.setParameter("transType", (Object) ("%" + transType + "%"));
            }
            if (!source.trim().isEmpty()) {
                q.setParameter("source", (Object) ("%" + source.trim() + "%"));
            }
            if (!service.equalsIgnoreCase("search") && !uniqueTransId.trim().isEmpty()) {
                q.setParameter("reference", (Object) ("%" + uniqueTransId.trim() + "%"));
            }
            if (!dest.trim().isEmpty()) {
                q.setParameter("dest", (Object) ("%" + dest.trim() + "%"));
            }

            if (!roleList.contains("ALL") && !roleList.isEmpty()) {
                q.setParameter("merchant", roleList);
            }
            if (!option.equalsIgnoreCase("") && !option.equalsIgnoreCase("ALL")) {
                q.setParameter("option", (Object) option);
            }
            if (!shortCodeList.isEmpty()) {
                q.setParameter("shortcode", (Object) shortCodeList);
            }

            List<Object[]> resp = q.getResultList();

            for (Object[] record : resp) {
                JustPayTransaction jp = new JustPayTransaction();
                try {
                    jp.setReference(checkNull(record[0]));
                    jp.setProvider(checkNull(record[1]));
                    jp.setMmno(checkNull(record[2]));
                    jp.setFee(BigDecimal.valueOf(Double.valueOf(checkNull(record[3]))));
                    jp.setDestacct(checkNull(record[4]));
                    jp.setAmount(BigDecimal.valueOf(Double.valueOf(checkNull(record[5]))));
                    jp.setValue_code(checkNull(record[6]));
                    jp.setValue_msg(checkNull(record[7]));
                    jp.setCreated_date(checkNull(record[8]));
                    jp.setCallback_msg(checkNull(record[9]));
                    jp.setMainoptions(checkNull(record[10]));
                    jp.setVastype(checkNull(record[11]));
                    jp.setClientid(checkNull(record[12]));
                    jp.setFlag(checkNull(record[13]));

                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }

                records.add(jp);

            }

        } catch (Exception e) {
            log.error("Er", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }

        log.info("Just Pay Records Select done");
        return records;
    }

    public List<NODES> getMerchants(final String jsonString) {
        log.info("jp request received >> {0}" + jsonString);
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
            if (type_id.contains("[0]")) {
                roleList.add("ALL");
            } else if (type_id.contains("[3]")) {
                userRoles = utility.getRoleParameters("[2000000000000054]", ucode);
                log.info("ucode: {0}" + userRoles);
                if (!userRoles.isEmpty()) {
                    roleList = Pattern.compile("~").splitAsStream(userRoles).map(f -> f.split(":")[0])
                            .collect(Collectors.toList());
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
            query = "SELECT alias id,  name from vasdb2.e_vas_node "
                    + (!roleList.contains("ALL") ? " where alias in (:alias) " : "")
                    + " order by alias asc ";
            Query q = session.createNativeQuery(query, NODES.class);
            if (!roleList.contains("ALL")) {
                q.setParameter("alias", (Object) roleList);
            }

            recordsList = q.getResultList();

        } catch (Exception ek) {
            log.error("Erw", ek);
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return recordsList;
    }

    protected String checkNull(Object Data) {
        if (Data != null && !Data.equals("")) {
            return Data.toString();
        }
        return "";
    }
}
