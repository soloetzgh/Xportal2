package com.etzgh.xportal.dao;

import com.etzgh.xportal.model.ApiPostData;
import com.etzgh.xportal.model.CscLog;
import com.etzgh.xportal.utility.DbHibernate;
import com.google.gson.Gson;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class CscDao {

    private static final Logger log = Logger.getLogger(CscDao.class.getName());

    public static void main(String[] args) {

        String req = "{\"product\":\"\",\"accountNumber\":\"\",\"service\":\"search\",\"action\":\"\",\"apiSecret\":\"\",\"apiCode\":\"\",\"userCode\":\"[17]|0060000246:0067520000010008~GCB:ALL,[2000000000000049]|DSTV~SCB:6AGAS,[2000000000000053]|DSTV,[2000000000000054]|2,[2500000000000048]|3,[2500000000000049]|38,[2500000000000050]|~75,[2500000000000051]|0060000244:0067510000010000\",\"admin\":\"\",\"type_desc\":\"\",\"role_id\":\"\",\"type_id\":\"[2],[3],[4],[9],[21],[31],[33],[34],[41],[42]\",\"searchKey\":\"qazwsxedcrfvtgb123123\",\"userName\":\"\",\"ClientId\":\"\",\"user_id\":\"10500000000000635\",\"pageNumber\":1,\"rowsPerPage\":1,\"trans_code\":\"\",\"subscriberId\":\"\",\"trans_status\":\"\",\"channel\":\"\",\"biller\":\"\",\"transId\":\"\",\"bankid\":\"\",\"card_num\":\"\",\"optionType\":\"\",\"payType\":\"\",\"wcMerchant\":\"\",\"lineType\":\"\",\"trans_channel\":\"\",\"pan\":\"\",\"terminal_id\":\"\",\"card_account\":\"\",\"cop_issuercode\":\"\",\"cop_role_id\":\"\",\"cop_company_id\":\"\",\"mobile_no\":\"\",\"phone\":\"\",\"type\":\"\",\"amount\":\"\",\"amount2\":\"\",\"refundStatus\":\"\",\"partialReversal\":\"\",\"reversalReason\":\"\",\"userData\":\"\",\"appId\":[],\"options\":{}}";
        new CscDao().getCscLogTrans(req);
    }

    public List<CscLog> getCscLogTrans(final String jsonString) {
        log.info("CscLog request received >> " + jsonString);
        final Gson j = new Gson();
        final ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);

        final String start_dt = apiData.getStartDate();
        final String end_dt = apiData.getEndDate();
        String reference = apiData.getUniqueTransId();
        String merchant = apiData.getMerchant();
        String status = apiData.getStatus();
        String source_frm = apiData.getFrom_source();
        String dest_account = apiData.getDestination();
        String payment_type = apiData.getPayType();
        String trans_type = apiData.getTransType();

        String bank = apiData.getBank();

        final String type_id = apiData.getType_id();

        final String service = apiData.getService();
        String searchKey = apiData.getSearchKey();
        List<CscLog> recordsList = new ArrayList<>();
        List<Object[]> records = new ArrayList<>();
        List<String> roleList = new ArrayList<>();
        List<String> bankList = new ArrayList<>();

        String str_source_frm = source_frm;
        if (reference == null) {
            reference = "";
        }
        if (str_source_frm == null) {
            str_source_frm = "";
        }
        if (merchant == null) {
            merchant = "";
        }
        if (searchKey == null) {
            searchKey = "";
        }
        if (payment_type == null) {
            payment_type = "";
        }
        if (service.equalsIgnoreCase("search") && searchKey.trim().isEmpty()) {
            return recordsList;
        }

        if (!type_id.isEmpty()) {
            if (type_id.contains("[0]")) {
                if (bank != null) {
                    System.out.println("BANK: " + bank + " :: " + !bank.equalsIgnoreCase("all"));
                    if (!bank.equalsIgnoreCase("all") && !bank.isEmpty()) {
                        bankList.add(bank.toUpperCase());
                    }

                    if (!merchant.equalsIgnoreCase("all")) {
                        roleList.add(merchant.toUpperCase());
                    }
                }

            } else if (type_id.contains("[42]")) {
                System.out.println("TRY");
                if (bank != null) {
                    System.out.println("BANK: " + bank + " :: " + !bank.equalsIgnoreCase("all"));
                    if (!bank.equalsIgnoreCase("all") && !bank.isEmpty()) {
                        bankList.add(bank.toUpperCase());
                    }

                    if (!merchant.equalsIgnoreCase("all")) {
                        roleList.add(merchant.toUpperCase());
                    }
                }
            } else {
                return recordsList;
            }
        } else {
            return recordsList;
        }

        String sql = "SELECT reference, source_account, dest_account, dest_type,amount, trans_status,is_reprocessed, reprocess_reference, is_reversed, status, status_desc, process_date,  trans_date from telcodb.csc_service where 1=1 "
                + (service.equalsIgnoreCase("search") ? " AND reference = :searchKey "
                : "  and trans_date BETWEEN :start_dt and :end_dt ")
                + (reference.trim().isEmpty() ? "" : " AND reference like :reference ")
                + (!bankList.isEmpty() && !bankList.contains("ALL")
                ? " AND dest_type ='bank' and left(dest_account,3) in (:bank) "
                : "")
                + (str_source_frm.trim().isEmpty() ? "" : " AND source_account like :source_account ")
                + (dest_account.trim().isEmpty() ? "" : " AND dest_account like :dest_account ")
                + (trans_type.trim().isEmpty() || trans_type.equalsIgnoreCase("ALL") ? ""
                : " AND dest_type = :trans_type ")
                + (status.equalsIgnoreCase("ALL") || status.isEmpty() ? ""
                : status.equalsIgnoreCase("SUCCESSFUL") ? " AND status = '00' "
                : status.equalsIgnoreCase("PENDING") ? " AND status = '58' " : "  AND status != '00' ")
                + " order by process_date desc";

        Session session = DbHibernate.getSession("40.15MYSQL");
        try {
            Query q = session.createNativeQuery(sql);

            if (!service.equalsIgnoreCase("search")) {
                q.setParameter("start_dt", (Object) start_dt)
                        .setParameter("end_dt", (Object) end_dt);
            } else {
                q.setParameter("searchKey", (Object) searchKey.trim());
            }
            if (!reference.trim().isEmpty()) {
                q.setParameter("reference", (Object) ("%" + reference.trim() + "%"));
            }

            if (!bankList.isEmpty() && !bankList.contains("ALL")) {

                q.setParameter("bank", (Object) bankList);
            }
            if (!str_source_frm.trim().isEmpty()) {
                q.setParameter("source_account", "%" + (Object) str_source_frm.trim());
            }
            if (!dest_account.trim().isEmpty()) {
                q.setParameter("dest_account", "%" + (Object) dest_account.trim());
            }
            if (!(trans_type.equalsIgnoreCase("all") || trans_type.isEmpty())) {
                q.setParameter("trans_type", "%" + (Object) trans_type);
            }
            records = (List<Object[]>) q.getResultList();

        } catch (Exception et) {
            log.error(et.getMessage(), et);
        } finally {
            if (session != null) {
                session.close();
            }
        }

        for (final Object[] record : records) {
            final CscLog cs = new CscLog();
            try {
                cs.setReference(checkNull(record[0]));
                cs.setSource_account(checkNull(record[1]));
                cs.setDest_account(checkNull(record[2]));
                cs.setDest_type(checkNull(record[3]));
                cs.setAmount(BigDecimal.valueOf(Double.valueOf(record[4].toString())));
                cs.setTrans_status(checkNull(record[5]));
                cs.setTrans_date(checkNull(record[12]));
                cs.setIs_reprocessed(checkNull(record[6]));
                cs.setReprocess_reference(checkNull(record[7]));
                cs.setIs_reversed(checkNull(record[8]));
                cs.setStatus(checkNull(record[9]));
                cs.setStatus_description(checkNull(record[10]));
                cs.setProcess_date(checkNull(record[11]));

            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }

            recordsList.add(cs);
        }

        return recordsList;
    }

    protected String checkNull(Object data) {

        if (data != null && !data.equals("")) {
            return data.toString();
        }
        return "NULL";
    }

}
