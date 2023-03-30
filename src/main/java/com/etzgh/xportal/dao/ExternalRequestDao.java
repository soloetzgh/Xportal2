package com.etzgh.xportal.dao;

import com.etzgh.xportal.model.ApiPostData;
import com.etzgh.xportal.model.ExternalRequests;
import com.etzgh.xportal.utility.DbHibernate;
import com.etzgh.xportal.utility.GeneralUtility;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class ExternalRequestDao {

    private static final GeneralUtility utility = new GeneralUtility();
    private static final Logger log = Logger.getLogger(ExternalRequestDao.class.getName());

    public String updateExternalRequest(String jsonString) {
        System.out.println("ExternalRequest received >> " + jsonString);
        Gson j = new Gson();
        ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);
        String response = "FAILED";
        Session session = DbHibernate.getSession("30.19MYSQL");

        Transaction userTxn = null;
        try {
//      
            //unncecessary but just to match old xportal code variables so i work less
            String mobile_no = apiData.getSubscriberId();
            String type_desc = apiData.getType_desc().trim();
            String type_id = apiData.getType_id();
            String user_code = apiData.getUserCode();

            String query = "";
            if (mobile_no == null || mobile_no.equalsIgnoreCase("")) {
                return null;
            }

            userTxn = session.beginTransaction();
            query = "update ussd_db.m_request_data set req_status='SUCCESSFUL' where  unique_ref =:unique_ref "
                    + (!user_code.equals("000") ? " and merchant_name = : merchant_name " : "");
            Query q = session.createNativeQuery(query)
                    .setParameter("unique_ref", mobile_no);

            int i = q.executeUpdate();

            userTxn.commit();

            if (i > 0) {
                response = "TREATED";
            } else {
                response = "NOT FOUND";
            }
        } catch (Exception ef) {

        }
        System.out.println("Update Records done");
        return response;
//return  null;
    }

    public List<ExternalRequests> fetchExternalRequests(String jsonString) {

        System.out.println("External Request received >> " + jsonString);
        Gson j = new Gson();
        ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);
//      
        String beginDate = apiData.getStartDate();
        String endDate = apiData.getEndDate();
        String transType = apiData.getTransType();
        String dest = apiData.getSubscriberId();

        String unique_ref = apiData.getUniqueTransId();
        String type_desc = apiData.getType_desc().trim();
        String type_id = apiData.getType_id();
        String merchant = apiData.getMerchant();

        String user_code = apiData.getUserCode();
        List<ExternalRequests> recordsList = new ArrayList<>();
        List<String> roleList = new ArrayList<>();

        if (dest == null) {
            dest = "";
        }
        if (transType == null) {
            transType = "";
        }
        if (unique_ref == null) {
            unique_ref = "";
        }
        String userRoles = "";

        if (!type_id.isEmpty()) {
            if (type_id.contains("[0]")) {
                if (!merchant.trim().isEmpty()) {
                    roleList.add(merchant.toUpperCase());
                }
            } else if (type_id.contains("[84]")) {

                userRoles = utility.getRoleParameters("[2000000000000057]", user_code);
                log.error("ucode: " + userRoles);

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
                    return recordsList;
                }

            } else {
                return recordsList;
            }
        } else {
            return recordsList;
        }
        Session session = DbHibernate.getSession("USSDDBMYSQL");

        String query = "";

        query = "SELECT id, unique_ref, merchant_name, msisdn, req_data, req_status, req_date from ussd_db.m_request_data WHERE req_date between :beginDate and :endDate "
                + (!dest.isEmpty() ? " AND msisdn = :mobile_no " : "")
                + (!transType.equalsIgnoreCase("ALL") && !transType.isEmpty() ? " AND req_status = :transType " : "")
                + (!unique_ref.isEmpty() ? " AND unique_ref like :unique_ref " : "")
                + (!roleList.contains("ALL") && !roleList.isEmpty() ? " AND merchant_name in (:merchant_name) " : "")
                + " order by req_date desc";

        Query q = session.createNativeQuery(query, ExternalRequests.class);
        q.setParameter("beginDate", beginDate);
        q.setParameter("endDate", endDate);
        if (!transType.equalsIgnoreCase("ALL") && !transType.equalsIgnoreCase("")) {
            q.setParameter("transType", transType);
        }
        if (!dest.isEmpty()) {
            q.setParameter("mobile_no", dest);
        }
        if (!unique_ref.isEmpty()) {
            q.setParameter("unique_ref", "%" + unique_ref + "%");
        }

        if (!roleList.contains("ALL") && !roleList.isEmpty()) {
            q.setParameter("merchant_name", roleList);
        }

        System.out.println("External Requests query :::" + query);
        recordsList = q.getResultList();

        System.out.println("ExtRequests Records Select done");
        return recordsList;
//return  null;
    }

}
