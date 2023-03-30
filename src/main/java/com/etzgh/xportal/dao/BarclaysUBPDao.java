package com.etzgh.xportal.dao;

import com.etzgh.xportal.model.ApiPostData;
import com.etzgh.xportal.model.BarclaysUBPTransaction;
import com.etzgh.xportal.utility.DbHibernate;
import com.google.gson.Gson;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.apache.log4j.Logger;

public class BarclaysUBPDao {

    private static final Logger log = Logger.getLogger(BarclaysUBPDao.class.getName());

    public List<BarclaysUBPTransaction> getBarclaysUBPTransactions(String jsonString) {

        System.out.println("barclays trx request received >> " + jsonString);
        List<BarclaysUBPTransaction> recordsList = new ArrayList<>();
        Gson j = new Gson();
        ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);

        String start_dt = apiData.getStartDate();
        String end_dt = apiData.getEndDate();
        String biller = apiData.getTransType();
        String unique = apiData.getUniqueTransId();
        String transId = apiData.getTransId();
        String status = apiData.getStatus();
        String type_id = apiData.getType_id();
        String searchKey = apiData.getSearchKey();
        String service = apiData.getService();
        String query = "";

        if (!type_id.isEmpty()) {
            if (type_id.contains("[0]") || type_id.contains("[10]")) {
            } else {
                return recordsList;
            }
        } else {
            return recordsList;
        }

        if (searchKey == null) {
            searchKey = "";
        }

        if (transId == null) {
            transId = "";
        }
        if (unique == null) {
            unique = "";
        }
        if (biller == null) {
            biller = "";
        }
        if (status == null) {
            status = "";
        }

        if (service.equalsIgnoreCase("search") && searchKey.trim().isEmpty()) {
            return recordsList;
        }
        query = "select extTransid, uniqueTransid, transAmount as amount, utilityCode, utilityRef, resultCode, result, message, "
                + " transDate, isReversal from telcodb.barclaystrans where 1=1 "
                + (service.equalsIgnoreCase("search") ? " and uniqueTransid like :searchKey "
                : " and transDate between :start_dt and :end_dt ")
                + (!unique.trim().isEmpty() ? " and uniqueTransid = :uniqueTransid " : "")
                + (!transId.trim().isEmpty() ? " and extTransid = :transId " : "")
                + (!biller.trim().isEmpty() && !biller.equalsIgnoreCase("ALL") ? " and utilityCode = :biller " : "")
                + (!status.trim().isEmpty() && !status.equalsIgnoreCase("ALL")
                ? (status.equalsIgnoreCase("successful") ? " and resultCode = '000'"
                : " and resultCode <>'000'")
                : "")
                + " and left(uniqueTransid,5) not in('01ESA') order by transDate desc";

        Session session = DbHibernate.getSession("40.9MYSQL");
        List<Object[]> records = new ArrayList<>();

        try {
            Query q = session.createNativeQuery(query);

            if (!service.equalsIgnoreCase("search")) {
                q.setParameter("start_dt", (Object) start_dt)
                        .setParameter("end_dt", (Object) end_dt);
            } else {
                q.setParameter("searchKey", (Object) searchKey.trim());
            }

            if (!unique.trim().isEmpty()) {
                q.setParameter("uniqueTransid", unique.trim());
            }
            if (!biller.trim().isEmpty() && !biller.equalsIgnoreCase("ALL")) {
                q.setParameter("biller", biller.trim());
            }
            if (!transId.trim().isEmpty()) {
                q.setParameter("transId", transId.trim());
            }

            records = q.getResultList();

        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
        }

        for (Object[] record : records) {
            BarclaysUBPTransaction bar = new BarclaysUBPTransaction();
            try {
                bar.setExTransid(checkNull(record[0]));
                bar.setUniqueTransid(checkNull(record[1]));
                bar.setAmount(BigDecimal.valueOf(Double.valueOf(record[2].toString())));
                bar.setUtilityCode(checkNull(record[3]));
                bar.setUtilityRef(checkNull(record[4]));
                bar.setResultCode(checkNull(record[5]));
                bar.setResult(checkNull(record[6]));
                bar.setMessage(checkNull(record[7]));
                bar.setIsReversal(checkNull(record[9]));
                bar.setTransDate(checkNull(record[8]));
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            recordsList.add(bar);
        }

        return recordsList;
    }

    protected String checkNull(Object Data) {
        if (Data != null && !Data.equals("")) {
            return Data.toString();
        }
        return "";
    }

    public String mapBarUBPCode(String code) {
        if (code.equals("000")) {
            return "Transaction Successful";
        } else if (code.equals("001")) {
            return "Transaction Unsuccessful";
        } else if (code.equals("010")) {
            return "Invalid Payment Method";
        } else if (code.equals("011")) {
            return "Invalid Utility code";
        } else if (code.equals("012")) {
            return "Duplicate transid detected";
        } else if (code.equals("013")) {
            return "Internal Error Occurred, please contact the service provider";
        } else if (code.equals("014")) {
            return "Request Timeout";
        } else if (code.equals("015")) {
            return "No transaction found";
        } else if (code.equals("016")) {
            return "invalid details passed - school fees";
        } else if (code.equals("017")) {
            return "invalid IP Address";
        } else if (code.equals("099")) {
            return "Server error occurred/invalid entry";
        } else if (code.isEmpty()) {
            return "No response";
        } else if (code.equals("null")) {
            return "No response";
        }
        return code;
    }
}
