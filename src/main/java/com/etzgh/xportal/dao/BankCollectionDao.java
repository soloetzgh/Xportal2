package com.etzgh.xportal.dao;

import com.etzgh.xportal.model.ApiPostData;
import com.etzgh.xportal.model.EcgCollection;
import com.etzgh.xportal.utility.DbHibernate;
import com.etzgh.xportal.utility.GeneralUtility;
import com.google.gson.Gson;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.persistence.Query;
import org.hibernate.Session;
import org.apache.log4j.Logger;

public class BankCollectionDao {

    private final static GeneralUtility utility = new GeneralUtility();

    private static final Logger log = Logger.getLogger(BankCollectionDao.class.getName());

    public List<EcgCollection> getBankCollections(String jsonString) {
        System.out.println("bankcollection trx request received >> " + jsonString);
        final Gson j = new Gson();
        final ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);

        final String start_dt = apiData.getStartDate();
        final String end_dt = apiData.getEndDate();

        final String ucode = apiData.getUserCode();
        String uniqueTransid = apiData.getUniqueTransId();
        String subscriber_id = apiData.getSubscriberId();
        String merchant = apiData.getMerchant();

        String bankCode = apiData.getBank_code();
        final String type_id = apiData.getType_id();
        String searchKey = apiData.getSearchKey();
        String service = apiData.getService();
        String query = "";
        final List<EcgCollection> recordsList = new ArrayList<>();

        List<String> roleList = new ArrayList<>();
        String userRoles = "";

        if (uniqueTransid == null) {
            uniqueTransid = "";
        }
        if (subscriber_id == null) {
            subscriber_id = "";
        }
        if (merchant == null) {
            merchant = "";
        }
        if (bankCode == null) {
            bankCode = "";
        }

        if (searchKey == null) {
            searchKey = "";
        }

        if (service.equalsIgnoreCase("search") && searchKey.trim().isEmpty()) {
            return recordsList;
        }

        if (!type_id.isEmpty()) {
            if (type_id.contains("[0]")) {
                roleList.add(bankCode.toUpperCase());
            } else if (type_id.contains("[46]")) {
                userRoles = utility.getRoleParameters("[2500000000000041]", ucode);
                if (!userRoles.isEmpty()) {
                    roleList = Pattern.compile("~").splitAsStream(userRoles).map(f -> f.split(":")[0])
                            .collect(Collectors.toList());
                    if (bankCode.equalsIgnoreCase("ALL") || bankCode.equalsIgnoreCase("000")) {

                    } else {
                        if (roleList.contains(bankCode)) {
                            roleList = new ArrayList<>(roleList);
                            roleList.add(bankCode);
                        }
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

        query = "select trans_no trans_id, subscriber_id dest_account, full_name dest_name, trans_channel, trans_amount, 'GHS' as currency,trans_status, "
                + "trans_descr, trans_date from telcodb..t_billtrans a left join ecarddb..e_merchant b on a.merchant_code = b.merchant_code "
                + " where 1=1 "
                + (!roleList.contains("ALL") ? " and b.issuer_code in (:bank_code) " : "")
                + (!merchant.equalsIgnoreCase("all") ? " and b.merchant_name like :merchant " : "")
                + (service.equalsIgnoreCase("search") ? " and trans_no = :uniqueTransid "
                : " and trans_date between :start_dt and :end_dt ")
                + ((!uniqueTransid.isEmpty()) ? " and trans_no = :uniqueTransid " : "")
                + ((!subscriber_id.isEmpty()) ? " and subscriber_id = :subscriber_id " : "")
                + " order by TRANS_DATE desc";
        Session session = DbHibernate.getSession("40.4SYBASE");
        List<Object[]> records = new ArrayList<>();
        try {
            Query q = session.createNativeQuery(query);
            if (!service.equalsIgnoreCase("search")) {
                q.setParameter("start_dt", (Object) start_dt)
                        .setParameter("end_dt", (Object) end_dt);
            } else {
                q.setParameter("uniqueTransid", (Object) searchKey.trim());
            }
            if (!merchant.equalsIgnoreCase("all")) {
                q.setParameter("merchant", "%" + merchant + "%");
            }
            if (!uniqueTransid.equals("")) {
                q.setParameter("uniqueTransid", (Object) uniqueTransid);
            }
            if (!subscriber_id.isEmpty()) {
                q.setParameter("subscriber_id", (Object) subscriber_id);
            }
            if (!roleList.contains("ALL")) {
                q.setParameter("bank_code", (Object) roleList);
            }

            System.out.println("Query >> " + query);
            records = q.getResultList();

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        records.stream().map((record) -> {
            final EcgCollection ecg = new EcgCollection();
            try {
                ecg.setTrans_id(checkNull(record[0]));
                ecg.setDest_account(checkNull(record[1]));
                ecg.setDest_name(checkNull(record[2]));
                ecg.setTrans_channel(checkNull(record[3]));
                ecg.setTrans_amount(BigDecimal.valueOf(Double.valueOf(checkNull(record[4]))));
                ecg.setCurrency(checkNull(record[5]));
                ecg.setTrans_status(checkNull(record[6]));
                ecg.setTrans_descr(checkNull(record[7]));
                ecg.setTrans_date(checkNull(record[8]));
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            return ecg;
        }).forEachOrdered((ecg) -> {
            recordsList.add(ecg);
        });

        return recordsList;
    }

    protected String checkNull(final Object data) {
        if (data != null && !data.equals("")) {
            return data.toString();
        }
        return "NULL";
    }
}
