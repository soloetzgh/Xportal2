package com.etzgh.xportal.dao;

import com.etz.vasgate.util.Encryption;
import com.etzgh.xportal.model.ApiPostData;
import com.etzgh.xportal.model.BulkUpload;
import com.etzgh.xportal.model.EmiConfig;
import com.etzgh.xportal.model.EmiPsp;
import com.etzgh.xportal.model.EmiPspRequest;
import com.etzgh.xportal.utility.Config;
import com.etzgh.xportal.utility.DbHibernate;
import com.etzgh.xportal.utility.HashNumber;
import com.etzgh.xportal.utility.PasswordGenerator;
import com.etzgh.xportal.utility.XRandom;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.persistence.NoResultException;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.json.JSONObject;

/**
 *
 * @author sunkwa-arthur
 */
public class EmiDao {

    private static final Config config = new Config();

    private static final int batchSize;
    private static final int transactionPerBatch;
    private static final String secretKey = config.getProperty("SECRET_KEY");
    private static final String[] airtime = {"mtn", "airtel", "tigo", "vodafone", "glo"};
    private static final List<String> topuplist = Arrays.asList(airtime);

    private static final HashMap<String, String> merchantCodeMap = new HashMap<String, String>();

    private static final String merchantCodes = "";

    private static final String CLIENT = "XPORTAL";

    private static final Logger log = Logger.getLogger(ReprocessDao.class.getName());

    static {
        String b = config.getProperty("UPLOAD_INSERT_BATCHSIZE");
        String c = config.getProperty("UPLOAD_INSERT_BATCHSIZE");

        batchSize = Integer.valueOf(b.isEmpty() ? "100" : b);
        transactionPerBatch = Integer.valueOf(c.isEmpty() ? "100" : c);

    }

    public EmiDao() {

    }

    public String initiateEmiRequest(String rawJsonData, String username, String accountType, String bank) {

        String result = "false";

        try {
            long start = System.currentTimeMillis();
            log.info("rawJsonData:: " + rawJsonData);
            List<EmiPspRequest> bulkUploadList = new Gson().fromJson(rawJsonData, new TypeToken<List<EmiPspRequest>>() {
            }.getType());

            final String batchId = PasswordGenerator.generateUuid();

            if (bank.equals("000")) {
                bank = "006";
            }
            log.info("bank :::" + bank);

            EmiConfig bankData = config.getEmiConfig().getOrDefault(bank, new EmiConfig());

//            log.info(bankData);
            boolean loggedRequest = logBatchRequest(batchId, bank, username, bulkUploadList, bankData.getCHARGING_PARTY_ID(), accountType);
            if (loggedRequest) {
                log.info("REQUEST LOGGED SUCCESSFULLY");
                result = "true";
            } else {
                log.info("An Error Occured logging request");
                result = "false";
            }
            log.info("TAT:: " + (System.currentTimeMillis() - start));

        } catch (Exception rt) {
            log.error("error: ", rt);
        }

        return result;
    }

    public int updateEmiProcessStatus(String[] data) {

        int result = -1;
        Session session = DbHibernate.getSession("30.19MYSQL");

        Transaction tx = null;
        try {
            tx = session.beginTransaction();

            String query = "UPDATE `elevy`.`emiprocesslog` set requestStatus = 'PROCESSED', requestError = :error, requestErrorMsg = :msg, requestProcessDate = now() where requestId=:requestId";
            Query q = session.createNativeQuery(query);
            q.setParameter("error", data[0]);
            q.setParameter("requestId", data[1]);
            q.setParameter("msg", data[2]);

            result = q.executeUpdate();

            tx.commit();
        } catch (Exception ex) {
            log.error("Database Exception>> an error occured:", ex);
            try {
                if (tx != null) {
                    tx.rollback();
                }
            } catch (Exception e) {
                log.error("error: ", e);
            }

        } finally {
            if (session != null) {
                session.close();
            }
        }

        return result;
    }

    public boolean logBatchRequest(String batchId, String bank, String username, List<EmiPspRequest> bulkUploadList, String chargingPartyId, String accountType) {
        boolean logged = false;
        Session session = DbHibernate.getSession("30.19MYSQL");
        final Transaction txn;
        try {

            txn = session.beginTransaction();

            String qry2 = "insert into elevy.emiprocesslog(chargingPartyId,accountNumber,tinOrGhanaCard,phoneNumber,requestStatus, requestDate, accountType, requestId,batchId,bank, requestBy, accountMask,identityMask)"
                    + " values (?,?,?,?,?,now(),?,?,?,?,?,?,?)";

            session.doWork(connection -> {
                connection.setAutoCommit(false);
                try (PreparedStatement pstmt = connection.prepareStatement(qry2)) {
                    int j = 1;
                    for (EmiPspRequest bu : bulkUploadList) {
                        bu.setRequestId(PasswordGenerator.generateUuid());
                        bu.setAccountType(accountType);
                        bu.setChargingPartyID(chargingPartyId);
                        pstmt.setString(1, bu.getChargingPartyID());
                        pstmt.setString(2, bu.getAccountNumber());
                        pstmt.setString(3, bu.getTINorGhanaCard());
                        pstmt.setString(4, bu.getPhoneNumber());
                        pstmt.setString(5, "PENDING");
                        pstmt.setString(6, bu.getAccountType());
                        pstmt.setString(7, bu.getRequestId());
                        pstmt.setString(8, batchId);
                        pstmt.setString(9, bank);
                        pstmt.setString(10, username);
//                        pstmt.setString(11, HashNumber.maskString(bu.getAccountNumber(), 4, 3, '*'));
//                        pstmt.setString(12, HashNumber.maskString(bu.getTINorGhanaCard(), 7, 3, '*'));,
                        pstmt.setString(11, hashAccountNumber(bu.getAccountNumber()));

                        pstmt.setString(12, hashAccountNumber(bu.getTINorGhanaCard()));

                        pstmt.addBatch();

                        if (j % batchSize == 0) {
                            pstmt.executeBatch();
                        }
                        j++;
                    }
                    pstmt.executeBatch();
                } catch (Exception e) {
                    log.error("error", e);
                    txn.rollback();
                }
            });

            txn.commit();
            logged = true;
        } catch (Exception er) {
            log.error("error", er);
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return logged;
    }

    protected String hashAccountNumber(String accNo) {
        int start = accNo.length() / 2;
        HashNumber hn = new HashNumber();

        return hn.hashStringValue(accNo, start / 2, start / 2, "*");
    }

    public boolean authorizeBatchRequest(String batchId, String username, String action) {
        boolean updated = false;
        Session session = DbHibernate.getSession("30.19MYSQL");
        final Transaction txn;
        try {

            txn = session.beginTransaction();
            String qry = "update bulk_process_request set status = :status, date_authorized = now(), authorized_by = :username  where batch_id = :batchId and status = 'PENDING';";

            Query q = session.createNativeQuery(qry);
            q.setParameter("batchId", batchId);
            q.setParameter("username", username);
            q.setParameter("status", action.equalsIgnoreCase("AUTHORIZE") ? "AUTHORIZED" : "DENIED");

            int i = q.executeUpdate();

            txn.commit();
            updated = i > 0;
        } catch (Exception er) {
            log.error("error", er);
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return updated;
    }

    public List<BulkUpload> getInitiatedBulkRequests(String jsonString) {
        log.info("process BulkRequests trx request received >> " + jsonString);
        Gson j = new Gson();
        ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);

        String startDate = apiData.getStartDate();
        String endDate = apiData.getEndDate();
        String batchId = apiData.getSearchKey();
        String service = apiData.getService();
        String status = apiData.getStatus();

        String query = "";

        List<BulkUpload> records = new ArrayList<>();

        if (service.equalsIgnoreCase("searchIniRequests") && StringUtils.isBlank(batchId)) {
            return records;
        }
        query = "select batch_id, chargingPartyId,accountNumber,tinOrGhanaCard, phoneNumber, requestDate, requestStatus, requestError, requestErrorMsg, requestProcessDate total, initiated_by, date_initiated, status from  bulk_process_request where 1=1 "
                + (service.equalsIgnoreCase("searchIniRequests") ? " and  batch_id = :batch_id " : " and date_Initiated between :start_dt and :end_dt ")
                + (!(StringUtils.isBlank(status) || status.equalsIgnoreCase("ALL")) ? " and status = :status " : "")
                + (!StringUtils.isBlank(batchId) ? " and batch_id = :batch_id " : "");

        Session session = DbHibernate.getSession("30.19MYSQL");
        try {

            Query q = session.createNativeQuery(query);

            if (service.equalsIgnoreCase("searchIniRequests")) {
                q.setParameter("batch_id", batchId);
            } else {
                q.setParameter("start_dt", startDate);
                q.setParameter("end_dt", endDate);
            }
            records = new ArrayList<>();
            List<Object[]> resp = q.getResultList();

            for (Object[] record : resp) {
                BulkUpload b = new BulkUpload();
                try {
//                    b.setBatch_id(checkNull(record[0]));
                    b.setAmount(checkNullDouble(record[1]));
//                    b.setTotal(checkNullNum(record[2]));
//                    b.setInitiated_by(checkNull(record[3]));
//                    b.setInitiated_date(checkNull(record[4]));
                    b.setStatus(checkNull(record[5]));
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
                records.add(b);

            }
        } catch (Exception e) {
            log.error("error", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return records;
    }

    public List<EmiPspRequest> getPendingEmiProcessRequests() {

        String query = "";

        List<EmiPspRequest> records = new ArrayList<>();

        Session session = DbHibernate.getSession("30.19MYSQL");
        Transaction tx = null;
        try {

            query = "select chargingPartyId, accountNumber, tinOrGhanaCard, phoneNumber, "
                    + " accountType, requestId, bank from elevy.emiprocesslog where requestStatus = 'PENDING'  "
                    + " and requestDate > date_sub(now(), interval 12 HOUR) "
                    + " order by requestDate asc limit 50";
            tx = session.beginTransaction();
            Query q = session.createNativeQuery(query);

            List<Object[]> resp = q.getResultList();
            List<String> refList = new ArrayList<>();

            for (Object[] record : resp) {
                EmiPspRequest b = new EmiPspRequest();
                try {
                    b.setChargingPartyID(checkNull(record[0]));
                    b.setAccountNumber(checkNull(record[1]));
                    b.setTINorGhanaCard(checkNull(record[2]));

                    b.setAccountType(checkNull(record[4]));
                    b.setRequestId(checkNull(record[5]));
                    b.setBank(checkNull(record[6]));

                    refList.add(b.getRequestId());

                } catch (Exception et) {
                    log.error("", et);
                }
                records.add(b);
            }

            if (!records.isEmpty()) {

                query = "update elevy.emiprocesslog  set requestStatus = 'PROCESSING', requestProcessDate = now()  where requestStatus ='PENDING' and requestId in (:requestId)";
                q = session.createNativeQuery(query);
                q.setParameter("requestId", refList);

                int i = q.executeUpdate();

                log.info("UPDATED EMI REQUESTS :: " + i);

            }
            tx.commit();
        } catch (Exception e) {
            records = new ArrayList<>();
            log.error("error", e);
            if (tx != null) {
                tx.rollback();
            }

        } finally {
            if (session != null) {
                session.close();
            }
        }
        return records;
    }

    protected int checkNullNum(Object Data) {
        if (Data != null && !Data.toString().trim().isEmpty()) {
            return Integer.valueOf(Data.toString());
        }
        return 0;
    }

    protected double checkNullDouble(Object Data) {
        if (Data != null && !Data.toString().trim().isEmpty()) {
            return Double.valueOf(Data.toString());
        }
        return 0.00;
    }

    public String createVasRequest(JSONObject req, String type) {
        JSONObject vasRequest = new JSONObject();
        if (type.equalsIgnoreCase("process")) {
            vasRequest.put("reference", req.getString("unique_transid"));
            vasRequest.put("product", req.getString("provider"));
            vasRequest.put("amount", req.getString("amount"));
            vasRequest.put("otherinfo", req.getString("otherinfo"));
            vasRequest.put("account", req.getString("dest_account"));

        } else {
            vasRequest.put("reference", new XRandom().generateUniqueId());
            vasRequest.put("product", req.getString("provider"));
            vasRequest.put("amount", req.getString("amount"));
            vasRequest.put("account", req.getString("dest_account"));
            vasRequest.put("otherinfo", req.getString("otherinfo"));
        }
        return vasRequest.toString();
    }

    public int handleSQLDuplicateErrorAndNoResultFound(String reference) {
        Session session = DbHibernate.getSession("30.19MYSQL");

        try {

            String qry = "select * from ecarddb.e_reprocess where reference like :reference and status='pending' or (status='authorized' and settled = '0' and reference like :reference)";

            Query q = session.createNativeQuery(qry);
            q.setParameter("reference", reference + "%");

            Object[] record = (Object[]) q.getSingleResult();

            if (record != null) {
                log.info("Pending Request or awaiting authorization");
                return 1;
            }
        } catch (NoResultException ex) {
            log.info("no result");
            return 0;
        } catch (Exception ex) {
            log.info("error: " + ex);
            if (ex.getMessage().contains("more than one elements")) {

                return 3;
            }
            return 2;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        log.info("no result");
        return 0;

    }

    public List<EmiPsp> getEmiRequests(String jsonString) throws ParseException {

        List<EmiPsp> recordsList = new ArrayList<>();
        log.info("Get EMIREQUESTS  received >> " + jsonString);
        Gson j = new Gson();
        ApiPostData apidata = j.fromJson(jsonString, ApiPostData.class);
        String start_dt = apidata.getStartDate();
        String end_dt = apidata.getEndDate();
        String mobile = apidata.getFrom_source();
        String transtype = apidata.getOptionType();
        String bank = apidata.getBank_code();
        String status = apidata.getStatus();

        if (transtype == null) {
            transtype = "";
        }

        if (mobile == null) {
            mobile = "";
        }

        if (status == null) {
            status = "";
        }
        if (bank == null) {
            bank = "";
        }

        String query = "select chargingPartyId, accountMask, identityMask, phoneNumber, "
                + " accountType, requestId, requestStatus, requestDate from elevy.emiprocesslog where 1=1 "
                + " and requestDate between :start_dt and :end_dt "
                + (!transtype.equals("ALL") && !transtype.isEmpty() ? " and accountType =  :transtype " : "")
                + (!mobile.isEmpty() ? " and phoneNumber = :mobile " : "")
                + (status.isEmpty() || status.equalsIgnoreCase("ALL") ? "" : " and requestStatus = :status ")
                + (bank.isEmpty() || bank.equalsIgnoreCase("ALL") ? "" : " and bank = :bank ")
                + " order by requestDate desc ";

        Session session = DbHibernate.getSession("30.19MYSQL");
        List<Object[]> resp = new ArrayList<>();
        try {
            Query q = session.createNativeQuery(query)
                    .setParameter("start_dt", start_dt)
                    .setParameter("end_dt", end_dt);

            if (!transtype.equals("ALL") && !transtype.isEmpty()) {
                q.setParameter("transtype", transtype);
            }
            if (!mobile.isEmpty()) {
                q.setParameter("mobile", mobile);
            }
            if (!(status.isEmpty() || status.equalsIgnoreCase("ALL"))) {
                q.setParameter("status", status);
            }
            if (!(bank.isEmpty() || bank.equalsIgnoreCase("ALL"))) {
                q.setParameter("bank", bank);
            }
            resp = q.getResultList();
        } catch (Exception e) {
            log.error("error", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }

        for (Object[] record : resp) {
            EmiPsp b = new EmiPsp();
            try {
                b.setChargingPartyID(checkNull(record[0]));
                b.setAccountNumber(checkNull(record[1]));
                b.setTINorGhanaCard(checkNull(record[2]));
                b.setPhoneNumber(checkNull(record[3]));
                b.setAccountType(checkNull(record[4]));
                b.setRequestId(checkNull(record[5]));
                b.setRequestStatus(checkNull(record[6]));
                b.setRequestDate(checkNull(record[7]));

            } catch (Exception et) {
                log.error("", et);
            }
            recordsList.add(b);
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

    public String generateMac(String destacct, String reference, BigDecimal amount, String otherInfo) {
        String mac = Encryption.getMD5(reference + "" + amount + "" + destacct + otherInfo + CLIENT);

        return mac;
    }

    public boolean verifyMac(String destacct, String reference, BigDecimal amount, String otherInfo, String mac) {
        return Encryption.getMD5(reference + "" + amount + "" + destacct + otherInfo + CLIENT).equalsIgnoreCase(mac);
    }
}
