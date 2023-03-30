package com.etzgh.xportal.dao;

import com.etzgh.xportal.controller.PortalSettings;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.etzgh.xportal.model.ApiPostData;
import com.etzgh.xportal.model.AuditTrail;
import com.etzgh.xportal.model.Bank;
import com.etzgh.xportal.model.BulkUpload;
import com.etzgh.xportal.model.BulkUploadLog;
import com.etzgh.xportal.model.E_RequestLog;
import com.etzgh.xportal.model.E_Reversal;
import com.etzgh.xportal.model.MomoUpdate;
import com.etzgh.xportal.model.Reversal;
import com.etzgh.xportal.model.TMC;
import com.etzgh.xportal.utility.Config;
import com.etzgh.xportal.utility.CryptographerMin;
import com.etzgh.xportal.utility.DbHibernate;
import com.etzgh.xportal.utility.PasswordGenerator;
import com.etzgh.xportal.utility.SuperHttpClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.Iterator;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Predicate;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author sunkwa-arthur
 */
public class ReprocessDao {

    private static final Config config = new Config();

    private static final Map<String, String> bankDB = new HashMap<>();
    private static final Logger log = Logger.getLogger(ReprocessDao.class.getName());
    private static final String ERROR = "ERROR";
    private static final CryptographerMin crypto = new CryptographerMin();
    private static final Map<String, String> banksMap;
    private static final String SECRET_KEY = StringUtils.substringBeforeLast(new PortalSettings().getSetting("hash_key"), ":");
    private static final int batchSize;
    private static final int transactionPerBatch;

    public ReprocessDao() {

    }

    public static Map<String, String> convertListAfterJava8(List<Bank> list) {
        Map<String, String> map = list.stream().collect(Collectors.toMap(Bank::getIssuer_code, bank -> bank.getIssuer_name()));
        return map;
    }

    static {
        bankDB.put("004", "GCB");
        bankDB.put("021", "BOA");
        bankDB.put("920", "ABII");
        bankDB.put("905", "BESTPOINT");
        bankDB.put("005", "NIB");
        banksMap = convertListAfterJava8(new AppDao().getBanks());
        String b = config.getProperty("UPLOAD_INSERT_BATCHSIZE");
        String c = config.getProperty("UPLOAD_INSERT_BATCHSIZE");

        batchSize = Integer.valueOf(b.isEmpty() ? "100" : b);
        transactionPerBatch = Integer.valueOf(c.isEmpty() ? "100" : c);
    }

    public static void main(String[] args) {

        String str = "[\"02ZB90576206130\",\"02EJ835910F4079\",\"02WI25596441280\",\"02RS42603E77662\",\"02FM056078C9737\",\"02HG62608827592\",\"02LR83616940887\",\"02TJ55627422349\",\"02VE226303E8735\",\"02FT73632414468\",\"02SA13651482476\",\"09FG09050828416220145\",\"02VL93657A41513\",\"09FG09050828532464005\",\"02BT836639A5062\",\"02HL586661F0604\",\"02BY526678B2094\",\"02QU826826C8481\",\"02VB92683132058\",\"02GG82687EB4393\",\"02NM22691BF0809\",\"02RW87694C57714\",\"02VZ53702554198\",\"02VG327117D1664\",\"02EE197135E1526\",\"02OH97714C46428\",\"02MI67715CA0735\",\"02MB04716B39420\",\"02OY797278C6644\",\"02TL63728F52715\",\"02IG13735089459\",\"02FX197369A0877\",\"02WS28741A91153\",\"02NW507433C1275\",\"02PQ99746627528\",\"02RW72749579346\",\"09FG09050831126353541\",\"02RP66772FB8690\",\"02VV29777D69732\",\"02CE56782992007\",\"02XT20786578197\",\"02IB94793728870\",\"02YV92797E48738\",\"02DP117985A4079\",\"02ZU39804BA3235\",\"02MX75808115518\",\"02QS90810DF9922\",\"02ZK33811D48690\",\"09FG09050832132320164\",\"02ZH73817A83198\",\"02ST34819E35916\",\"02HH82824836332\",\"02SD74828021946\",\"02IZ228415D9836\",\"02EP388515B7305\",\"02BY88855A26630\",\"02ZT47858EE0904\",\"02PV588681A9781\",\"02TE38869A45573\",\"02ZG22871209153\",\"02TN42878BE1875\",\"02JF54880B87638\",\"02HU93883B94269\",\"02NT39893EE7952\",\"09FG09050833493544150\",\"02ML648977A8695\",\"02SW55899468415\",\"02WI649036F7660\",\"09FG09050834043452120\",\"02AE42921E76552\",\"02QF51922AF3626\",\"02IS62925AC0409\",\"02KT73933D78983\",\"09FG09050834560213046\",\"09FG09050835001246520\",\"02UT549574F6202\",\"09FG09050835241603115\",\"02BZ86966E97874\",\"02KL379701F6023\",\"02XW62976DD8945\",\"02VT17981BE0455\",\"02XS15985559176\",\"02SO469964E7760\",\"09FG09050836142313214\",\"09FG09050836161544161\",\"02QP64010C34246\",\"02IG87024285719\",\"02VO43026486345\",\"09FG09050837141601655\",\"02DS92049143334\",\"02JTXB45120F17094\",\"02NV63054A97208\",\"02LH62056B86251\",\"02RV67059901483\",\"02RX38060307772\",\"02ZG41066DC2017\",\"02OF74074B97219\",\"02TA860847B6239\",\"02HY21090F57853\",\"02EL42095944263\",\"02IX49096C45985\",\"02ZE27105AA0555\",\"02PE13116119042\",\"02BC93119408462\",\"02KV52137472907\",\"02JA821400A8420\",\"02TH32146378979\",\"02WR921548C2927\",\"02SI921594E1595\",\"02OK521611B6204\",\"02BR91165FC4078\",\"02FL881719F6676\",\"02VD211798F6782\",\"09FG09050840346331305\",\"02NT55203AD0243\",\"02FE66219812632\",\"02OV72240D32408\",\"02BH432447B1174\"]";
        ArrayList<String> strings = new Gson().fromJson(str, new TypeToken<ArrayList<String>>() {
        }.getType());
        log.info("D: " + strings);
        List<BulkUploadLog> logRecord = new ReprocessDao().handleSQLDuplicateErrorAndNoResultFound(strings, false, false);

//        logRecord.stream().forEach(f -> {
//            if (f.getCard_num() == null) {
//                f.setReversed("N/A");
//            } else if (f.getReversed().isEmpty()) {
//                f.setReversed(new ReprocessDao().logReprocess(f, "Eugene", "12323423", "XPortal.ReversalApp", "2343242",
//                        "initiator", ""));
//            } else {
//                f.setReversed("Duplicate");
//            }
//        });
    }

    public static <T> void remove(List<T> list, Predicate<T> predicate) {
        Iterator<T> itr = list.iterator();

        while (itr.hasNext()) {
            T t = itr.next();
            if (predicate.test(t)) {
                itr.remove();
            }
        }
    }

    public static List<BulkUpload> callMobileMoneyTable(List<String> references) {

        List<BulkUpload> respo = new ArrayList<>();

        String query = "select reference, paymenttype, flag transaction_status, switchcode extra_info, cardno destination, channel, "
                + " ifnull(amount,0.00) amount, client source_type, trnxdate transaction_date, msisdn source, "
                + " left(cardno,3) destination_type, 'MOBILEMONEY' transaction_type, '' status from telcodb.mobilemoney where 1=1"
                + " AND paymenttype = 'D' AND reference in (:reference)"
                + " order by paymenttype desc";

        Session session = DbHibernate.getSession("40.9MYSQL");

        try {

            Query q = session.createNativeQuery(query, BulkUpload.class);

            q.setParameter("reference", references);

            respo = q.getResultList();

        } catch (Exception e) {
            log.error(ERROR, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return respo;
    }

    public List<BulkUpload> getInitiatorTransactions(String jsonString) {
        log.info("Reprocess trx request received >>" + jsonString);
        Gson j = new Gson();
        ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);

        String trans_data = apiData.getUniqueTransId();
        String searchKey = apiData.getSearchKey();

        String service = apiData.getService();
        List<String> references = new ArrayList<>();

        if (trans_data == null) {
            trans_data = "";
        }
        if (searchKey == null) {
            searchKey = "";
        }
        List<BulkUpload> records = new ArrayList<>();

        if (service.equals("bulkTransactions")) {

            List<MomoUpdate> l = j.fromJson(trans_data, new TypeToken<List<MomoUpdate>>() {
            }.getType());

            references = l.stream().distinct().filter(f -> f.getReference() != null && !f.getReference().trim().isEmpty()).map(f -> f.getReference().trim()).collect(Collectors.toList());

        } else {
            if (service.equals("transactions") && !trans_data.trim().isEmpty()) {
                references.add(trans_data.trim());
            } else if (service.equals("search") && !searchKey.trim().isEmpty()) {
                references.add(searchKey.trim());
            }
        }

        if (references.isEmpty()) {
            return records;
        }
        try {
//            log.info("references: " + references);
//            List<BulkUpload> respFromTable = callMobileMoneyTable(references);
            records = callMobileMoneyTable(references);

//            for (Object[] record : respFromTable) {
//
//                BulkUpload mmr = new BulkUpload();
//                try {
//
//                    mmr.setReference(checkNull(record[0]));
//                    mmr.setTransaction_type("MOBILEMONEY");
////                    mmr.setExtra_info(checkNull(record[1]));
//                    mmr.setExtra_info(checkNull(record[3]));
//                    mmr.setSource(checkNull(record[9]));
//                    mmr.setDestination_type(banksMap.getOrDefault(checkNull(record[10]), checkNull(record[10])));
//                    mmr.setDestination(checkNull(record[4]));
//                    mmr.setChannel(checkNull(record[5]));
//                    mmr.setAmount(Double.valueOf(record[6].toString()));
//                    mmr.setSource_type(checkNull(record[7]));
//                    mmr.setTransaction_date(checkNull(record[8]));
//
//                } catch (Exception e) {
//                    log.error(ERROR, e);
//                }
//                records.add(mmr);
//            }
        } catch (Exception e) {
            log.error(ERROR, e);
        }

        return records;
    }

    public List<BulkUploadLog> getAuthorizerTransactions(String jsonString) {
        log.info("REPROCESS AUTHORIZER trx request received >> " + jsonString);
        Gson j = new Gson();
        ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);

        String beginDate = apiData.getStartDate();
        String endDate = apiData.getEndDate();
        String trans_data = apiData.getUniqueTransId();
        String status = apiData.getStatus();
        String searchKey = apiData.getSearchKey();

        if (trans_data == null) {
            trans_data = "";
        }
        if (status == null) {
            status = "";
        }
        if (searchKey == null) {
            searchKey = "";
        }
        if (!searchKey.isEmpty()) {
            trans_data = searchKey;
        }

        String query = "select * from telcodb.bulk_process_log where 1=1 "
                + (!trans_data.isEmpty() ? " and reference = :reference" : "")
                + (searchKey.isEmpty() ? " and initiated_date between :beginDate and :endDate " : "")
                + (status.isEmpty() || status.equalsIgnoreCase("all") ? "" : " and status =:status ")
                + " order by initiated_date desc";

        Session session = DbHibernate.getSession("40.9MYSQL");
        List<BulkUploadLog> records = new ArrayList<>();
        try {
            Query q = session.createNativeQuery(query, BulkUploadLog.class);
            if (searchKey.isEmpty()) {
                q.setParameter("beginDate", beginDate).setParameter("endDate", endDate);
            }
            if (!trans_data.isEmpty()) {
                q.setParameter("reference", trans_data);
            }
            if (!(status.isEmpty() || status.equalsIgnoreCase("all"))) {
                q.setParameter("status", status);
            }
            records = q.getResultList();
        } catch (Exception e) {
            log.error(ERROR, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return records;
    }

    public List<E_Reversal> getReversedTransactions(String jsonString) {
        log.info("REVERSED trx request received >> " + jsonString);
        Gson j = new Gson();
        ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);

        String beginDate = apiData.getStartDate();
        String endDate = apiData.getEndDate();
        String trans_data = apiData.getUniqueTransId();
        String appname = apiData.getChannel();

        log.info("Trans data >>> " + trans_data);
        if (trans_data == null) {
            trans_data = "";
        }

        String query = "";

        List<E_Reversal> records = new ArrayList<>();
        query = "select * from ecarddb.e_reversal where transid like :transid "
                + (!appname.equals("ALL") ? " and appname = :appname " : "")
                + " and trans_date between :beginDate  and :endDate  and (reversed IS NOT NULL or reversed != '') order by date_authorized desc";

        Session session = DbHibernate.getSession("40.9MYSQL");
        try {
            Query q = session.createNativeQuery(query, E_Reversal.class)
                    .setParameter("beginDate", beginDate)
                    .setParameter("endDate", endDate);

            if (!appname.equalsIgnoreCase("ALL")) {
                q.setParameter("appname", appname);
            }
            q.setParameter("transid", "%" + trans_data + "%");
            records = q.getResultList();
        } catch (Exception e) {
            log.error(ERROR, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return records;
    }

    public E_RequestLog searchReversalTransactions(int id, String reference) {

        log.info("Reversal search request received REFERENCE >> " + reference);

        String query = "";

        log.info("Reference >>> " + reference);
        E_RequestLog records = new E_RequestLog();
        Session session = DbHibernate.getSession("40.9MYSQL");
        try {

            query = "select trans_data transid, pan card_num,  card_acc_name trans_descr, amount trans_amount, acct_id2 merchant_code, trans_data unique_transid, trans_date, '' channelid, trans_seq  from ecarddb.e_tmcrequest  "
                    + "where trans_data = :trans_data and  (right(target,3) = 'CBA' or target ='MTNMOBILEMONEY') and not exists (select mti from ecarddb.e_tmcrequest where mti in('0420','0900') and trans_data = :trans_data);";

            Query q = session.createNativeQuery(query);
            q.setParameter("trans_data", reference);
            Object[] record = (Object[]) q.getSingleResult();

            E_RequestLog ereq = new E_RequestLog();

            ereq.setCard_num(checkNull(record[1]));
            ereq.setTrans_descr(checkNull(record[2]));
            ereq.setTrans_amount(BigDecimal.valueOf(Double.valueOf(checkNull(record[3]))));
            ereq.setMerchant_code(checkNull(record[4]));
            ereq.setTransid(checkNull(record[5]));
            ereq.setTrans_date(((Timestamp) record[6]));
            ereq.setChannelid(checkNull(record[7]));
            ereq.setTrans_code("");
            ereq.setRequest_id(checkNull(record[8]));

            records = ereq;

        } catch (Exception e) {
            log.error(ERROR, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return records;
    }

    public List<BulkUploadLog> initiateBulkReprocess(String reference, String userName, String userId, String ipAddress) {

        ArrayList<String> references = new Gson().fromJson(reference, new TypeToken<ArrayList<String>>() {
        }.getType());
        List<BulkUploadLog> logRecord = handleSQLDuplicateErrorAndNoResultFound(references, true, false);

        logRecord = logBulkReprocess(logRecord, userName, userId, "XPortal.ReprocessApp", ipAddress, "initiator", "");
//        logRecord.parallelStream().forEach(f -> {
//            if (f.getStatus() == null || f.getStatus().isEmpty()) {
//                f.setStatus(logReprocess(f, userName, userId, "XPortal.ReprocessApp", ipAddress, "initiator", ""));
//            }
//        });

        return logRecord;
    }

    public List<BulkUploadLog> authorizeBulkReprocess(String reference, String role, String userName, String userId,
            String ipAddress, String choice, String transType) {

        ArrayList<String> references = new Gson().fromJson(reference, new TypeToken<ArrayList<String>>() {
        }.getType());

        List<String> uniqueReferences = references.stream().distinct().collect(Collectors.toList());
        List<BulkUploadLog> logRecord = handleSQLDuplicateErrorAndNoResultFound(uniqueReferences, true, true);

        logRecord = logBulkReprocess(logRecord, userName, userId, "XPortal.ReprocessApp", ipAddress, "authorizer", choice);

        return logRecord;
    }

    public List<BulkUploadLog> handleSQLDuplicateErrorAndNoResultFound(List<String> references, boolean check, boolean override) {

        Session session = DbHibernate.getSession("40.9MYSQL");
        final String TRANSQUERY_ENDPOINT = StringUtils.substringBeforeLast(new PortalSettings().getSetting("transquery_endpoint"), ":");

        List<BulkUploadLog> records = new ArrayList<>();
        try {

            if (!references.isEmpty()) {
                List<BulkUpload> records_ = callMobileMoneyTable(references);
//                BeanUtils.copyProperties(records, records_);
//                log.info("DATA");
                log.info(records_.toString());
//                String query = "select id,transid,card_num,trans_date,trans_amount,trans_code,merchant_code,response_code,response_time,trans_descr,client_id,request_id,fee,currency,channelid,stan,reversed,otherfee, 'node1' srcname "
//                        + " from ecarddb.e_requestlog where transid in (select trans_data from ecarddb.e_tmcrequest a left join ecarddb.e_reversal b on a.trans_data = b.transid  "
//                        + " where a.trans_data in(:trans_data) and (right(a.target,3) = 'CBA' or a.target ='MTNMOBILEMONEY') and a.trans_data not in (select trans_data from ecarddb.e_tmcrequest "
//                        + " where ((mti in('0420') and response_code ='00') or (mti in('0900') and response_code ='00')) and trans_data in(:trans_data))) order by trans_date desc ";
//                String query2 = "select id,transid,card_num,trans_date,trans_amount,trans_code,merchant_code,response_code,response_time,trans_descr,client_id,request_id,fee,currency,channelid,stan,reversed,otherfee, 'node2' srcname "
//                        + " from ecarddb2.e_requestlog where transid in (select trans_data from ecarddb2.e_tmcrequest a left join ecarddb.e_reversal b on a.trans_data = b.transid  "
//                        + " where a.trans_data in(:trans_data) and (right(a.target,3) = 'CBA' or a.target ='MTNMOBILEMONEY') and a.trans_data not in (select trans_data from ecarddb2.e_tmcrequest "
//                        + " where ((mti in('0420') and response_code ='00') or (mti in('0900') and response_code ='00')) and trans_data in(:trans_data))) order by trans_date desc ";
                if (!records_.isEmpty()) {
                    String query = "select * from telcodb.bulk_process_log where reference in (:references)";
                    Query q = session.createNativeQuery(query, BulkUploadLog.class)
                            .setParameter("references", references);
                    List<BulkUploadLog> records2 = q.getResultList();

                    final Map<String, BulkUploadLog> result = records2.parallelStream().collect(
                            Collectors.toMap(BulkUploadLog::getReference, bu -> bu));

                    records_.parallelStream().forEach(f -> {
                        try {
                            BulkUploadLog bulk = new BulkUploadLog();
                            BeanUtils.copyProperties(bulk, f);
                            boolean add = true;

                            BulkUploadLog b = result.getOrDefault(f.getReference(), new BulkUploadLog());
                            log.info("CHECKING " + f.getReference() + " -- " + b.toString());
                            if (b.getReference() != null) {
//                                bulk.setId(b.getId());
                                BeanUtils.copyProperties(bulk, b);

                                if (b.getStatus().equalsIgnoreCase("PENDING")) {
                                    bulk.setStatus(override ? "SUCCESS" : "DUPLICATE");
                                } else if (b.getProcess_status().equalsIgnoreCase("PROCESSING")) {
                                    bulk.setStatus(b.getProcess_status());
                                }
                            }
                            if (check) {
                                //call status checker
                                String url = TRANSQUERY_ENDPOINT + "?reference=" + bulk.getReference() + "&transtype=REVERSAL";
                                log.info("CALLING: " + url);
                                String scResponse = SuperHttpClient.doGet(url);
                                log.info("RESPONSE: " + bulk.getReference() + " - " + scResponse);

                                if (scResponse != null && !scResponse.trim().isEmpty()) {
                                    JSONObject sc = new JSONObject(scResponse);
                                    String resp = sc.optString("status", "31");
                                    if (resp.equals("31")) {
                                        bulk.setStatus("NOT_FOUND");

                                    } else if (resp.equals("06")) {
                                        bulk.setStatus("REVERSED");
                                    }
                                } else {
                                    bulk.setStatus("SC_ERROR");
                                }
                            }
                            if (add) {
                                records.add(bulk);
                            }
                        } catch (Exception e) {
                            log.error(ERROR, e);
                        }
                    });
                }
            }

        } catch (Exception ex) {
            log.error(ERROR, ex);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return records;
    }

    public List<BulkUploadLog> getPendingProcess() {

        Session session = DbHibernate.getSession("40.9MYSQL");
        Transaction tx = null;
        List<BulkUploadLog> records = new ArrayList<>();
        try {

            String query = "select * from telcodb.bulk_process_log where status = 'authorized' and process_status is null and date_authorized > date_sub(now(), interval 1 month) order by id desc limit 100";
            tx = session.beginTransaction();
            Query q = session.createNativeQuery(query, BulkUploadLog.class);
            records = q.getResultList();

            if (!records.isEmpty()) {
                List<String> ids = records.stream().map(f -> f.getId()).collect(Collectors.toList());
                query = "update telcodb.bulk_process_log set process_status='PROCESSING' where id in(:ids)";
                q = session.createNativeQuery(query)
                        .setParameter("ids", ids);

                int r = q.executeUpdate();
                log.info("Updated " + r + " bulk_process_log to pending");
            }
            tx.commit();

        } catch (Exception ex) {
            log.error(ERROR, ex);
            records = new ArrayList<>();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return records;
    }

    public Map<String, String> getElevyRecords(List<String> references) {

        Session session = DbHibernate.getSession("30.19MYSQL");
        List<Object[]> records = new ArrayList<>();
        Map<String, String> trans = new HashMap<>();
        try {

            String query = "select reference, sender_etz_bank_code, sender_etz_card_number, sender_account_number, elevy from elevy.elevy where status ='00' and reference in(:references) ";

            Query q = session.createNativeQuery(query)
                    .setParameter("references", references);
            records = q.getResultList();
            for (Object[] record : records) {

            }

        } catch (Exception ex) {
            log.error(ERROR, ex);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return trans;
    }

    public String logReprocess(BulkUploadLog bulkUpload, String userName, String userId, String appName,
            String ipAddress, String userType, String choice) {
        String result = "";
        Session session = DbHibernate.getSession("40.9MYSQL");

        Transaction tx = null;
        final AuditTrail auditLog = new AuditTrail();
        try {
            int audit = 0;
            tx = session.beginTransaction();
            log.info(bulkUpload.toString());
            String action = "";
            auditLog.setUser(userId);
            auditLog.setIp_address(ipAddress);
            auditLog.setExtra_info("amount involved: GHS " + bulkUpload.getAmount());

            String resp = "-1";
            if (userType.equals("authorizer")) {
                choice = (choice.equals("authorize") ? "authorized" : "denied");

                action = "processReprocess";
                auditLog.setAction(action);
                auditLog.setComment(choice + " process for transaction: " + bulkUpload.getReference());

                String query2 = "UPDATE `telcodb`.`bulk_process_log` set authorized_date= now(), status=:choice, authorized_by = :userName where id = :id and status  = 'PENDING'";
                Query q = session.createNativeQuery(query2);
                q.setParameter("userName", userName);
                q.setParameter("choice", choice.toUpperCase());
                q.setParameter("id", bulkUpload.getId());

                int j = q.executeUpdate();

                resp = j > 0 ? "0" : resp;

                log.info("Record updated Successfully in bulk_process_log after processing: " + j + " --- " + resp + " -- " + bulkUpload.getId() + " -- " + q.getQueryString());

            } else {
                action = "initiateReprocess";
                auditLog.setAction(action);
                auditLog.setComment("initiated process for transaction: " + bulkUpload.getReference());
//                e_Reversal.setDate_initiated(new Date());
                bulkUpload.setInitiated_by(userName);
                bulkUpload.setStatus("PENDING");
                session.persist(bulkUpload);
                session.flush();
                log.info("Record Saved in bulk_process_log Successfully");
                resp = "0";
            }

            tx.commit();

            result = resp.equals("0") ? "SUCCESS" : "ERROR";
        } catch (Exception ex) {
            try {
                if (tx != null) {
                    tx.rollback();
                }
            } catch (Exception e) {
                log.error(ERROR, e);
            }
            String msg = ex.getMessage();
            log.info(msg);

            if (msg.contains("ConstraintViolationException")) {
                log.info("Database Exception>> Duplicate Record");
                result = "DUPLICATE";
            } else {
                log.error("Database Exception>> an error occured:", ex);
            }
            result = result.isEmpty() ? "ERROR" : result;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        auditLog.setComment(auditLog.getComment() + ":" + result);
        new Thread(() -> {
            new AuditDao().insertIntoAuditTrail(auditLog);
        }).start();
        return result;
    }

    public List<BulkUploadLog> logBulkReprocess(List<BulkUploadLog> bulkUploadList, String userName, String userId, String appName,
            String ipAddress, String userType, String choice) {

        final String batchId = PasswordGenerator.generateUuid();

        ExecutorService executor = Executors.newCachedThreadPool();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = formatter.format(date);
        try {

            List<Callable<String>> callables = new ArrayList<>();

            for (BulkUploadLog bulk : bulkUploadList) {
                bulk.setProcess_type("REPROCESS");
//                log.info("{} => transaction picked :: {}" + bulk.getReference());
                if (bulk.getStatus() == null || bulk.getStatus().isEmpty() || (bulk.getStatus().equalsIgnoreCase("SUCCESS") && userType.equals("authorizer"))) {

                    Callable<String> task = () -> {

                        String result = "";
                        Session session = DbHibernate.getSession("40.9MYSQL");
                        Transaction tx = null;
                        final AuditTrail auditLog = new AuditTrail();
                        try {
                            tx = session.beginTransaction();

//            log.info(bulkUpload.toString());
                            String action = "";
                            auditLog.setUser(userId);
                            auditLog.setIp_address(ipAddress);
                            auditLog.setExtra_info("amount involved: GHS " + bulk.getAmount());
                            String choice2 = (choice.equals("authorize") ? "authorized" : "denied");
                            String resp = "-1";
                            if (userType.equals("authorizer")) {

                                action = "processReprocess";
                                auditLog.setAction(action);
                                auditLog.setComment(choice2 + " process for transaction: " + bulk.getReference());

                                String query2 = "UPDATE `telcodb`.`bulk_process_log` set authorized_date= now(), status=:choice, authorized_by = :userName where id = :id and status  ='PENDING'";
                                Query q = session.createNativeQuery(query2);
                                q.setParameter("userName", userName);
                                q.setParameter("choice", choice2.toUpperCase());
                                q.setParameter("id", bulk.getId());

                                int j = q.executeUpdate();

                                resp = j > 0 ? "0" : resp;

                                log.info("Record updated Successfully in bulk_process_log after processing: " + j + " --- " + resp + " -- " + bulk.getId() + " -- " + q.getQueryString());

                            } else {
                                bulk.setBatch_id(batchId);
                                action = "initiateReprocess";
                                auditLog.setAction(action);
                                auditLog.setComment("initiated process for transaction: " + bulk.getReference());
//                e_Reversal.setDate_initiated(new Date());
                                bulk.setInitiated_by(userName);
                                bulk.setStatus("PENDING");
                                bulk.setInitiated_date(strDate);
                                session.persist(bulk);
                                session.flush();
                                log.info("Record Saved in bulk_process_log Successfully");
                                resp = "0";
                            }

                            tx.commit();

                            if (userType.equals("authorizer") && resp.equals("0")) {
                                result = choice2.toUpperCase();
                            } else {
                                result = resp.equals("0") ? "SUCCESS" : "ERROR";
                            }

                        } catch (Exception ex) {
                            try {
                                if (tx != null) {
                                    tx.rollback();
                                }
                            } catch (Exception e) {
                                log.error(ERROR, e);
                            }
                            String msg = ex.getMessage();
                            log.info(msg);

                            if (msg.contains("ConstraintViolationException")) {
                                log.info("Database Exception>> Duplicate Record");
                                result = "DUPLICATE";
                            } else {
                                log.error("Database Exception>> an error occured:", ex);
                            }
                            result = result.isEmpty() ? "ERROR" : result;
                        } finally {
                            if (session != null) {
                                session.close();
                            }
                        }
                        if (userType.equals("authorizer")) {
                            bulk.setStatus(result);
                        } else if (!userType.equals("authorizer") && bulk.getStatus().equals("PENDING")) {
                            bulk.setStatus(result);
                        }
                        auditLog.setComment(auditLog.getComment() + ":" + result);
                        new Thread(() -> {
                            new AuditDao().insertIntoAuditTrail(auditLog);
                        }).start();

                        return result;

                    };
                    callables.add(task);
                }
            }
            List<Future<String>> futures = executor.invokeAll(callables);
            for (Future<String> future : futures) {
                try {
                    log.info("Job completed :: status => {}" + future.get());
                } catch (ExecutionException e) {
                    log.error("Error retrieving results from bulk_process call", e);
                }
            }
        } catch (Exception e) {
            log.error(ERROR, e);
        }
        return bulkUploadList;
    }

    public int updateProcessStatus(BulkUploadLog transaction, String status, String type, String sc_response) {

        int result = -1;
        Session session = DbHibernate.getSession("40.9MYSQL");

        Transaction tx = null;
        try {
            tx = session.beginTransaction();
//            switch(transaction.getProcess_category()){
            String query = "";
            if (type.equals("REVERSAL") || type.equals("REPROCESS")) {
                if (type.equals("REVERSAL")) {
                    query = "UPDATE telcodb.bulk_process_log set reversed = :status where reference= :transid and reversed  = '0'";
                } else {
                    query = "UPDATE telcodb.bulk_process_log set reprocess = :status where reference= :transid and reprocess  = '0'";
                }
                Query q = session.createNativeQuery(query);
                q.setParameter("status", status);
                q.setParameter("transid", transaction.getReference());

                result = q.executeUpdate();
            } else if (type.equals("SC")) {
                ///transaction successful
                query = "UPDATE telcodb.bulk_process_log set process_status = 'PROCESSED', status_check_response ='SUCCESSFUL' where reference= :transid and process_status  = 'PROCESSING'";

                Query q = session.createNativeQuery(query);
                q.setParameter("status", status);
                q.setParameter("transid", transaction.getReference());

            }

            tx.commit();
        } catch (Exception ex) {
            log.error("Database Exception>> an error occured:", ex);
            try {
                if (tx != null) {
                    tx.rollback();
                }
            } catch (Exception e) {
                log.error(ERROR, e);
            }

        } finally {
            if (session != null) {
                session.close();
            }
        }

        return result;
    }

    public List<TMC> searchTmcTransactions(String jsonString) throws ParseException {

        log.info("TMC search request received >> " + jsonString);
        Gson j = new Gson();
        ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class
        );

        String trans_data = apiData.getSearchKey();
        String query = "";

        if (trans_data == null) {
            trans_data = "";
        }

        if (trans_data.equals("")) {
            return new ArrayList<>();
        }

        query = "select mti, trans_data, acct_id1, acct_id2, pro_code as transtype, trans_date, pro_code, pan, amount, "
                + "track2, response_code, response_code as response,  terminal_id, stan, card_acc_name, fee, reference, "
                + "src_node, target_node, response_time from ecarddb.e_tmcrequest where trans_data = :trans_data and right(target,3) = 'CBA' and mti='0420' order by trans_date desc ";
        query += " union select mti, trans_data, acct_id1, acct_id2, pro_code as transtype, trans_date, pro_code, pan, amount, "
                + "track2, response_code, response_code as response,  terminal_id, stan, card_acc_name, fee, reference, "
                + "src_node, target_node, response_time from ecarddb.e_tmcrequest where trans_data = :trans_data and right(target,3) = 'CBA' and mti='0420' order by trans_date desc ";
        log.info("Query >> " + query);
        List<TMC> recordsList = new ArrayList<>();
        List<Object[]> records = new ArrayList<>();
        Session session = DbHibernate.getSession("40.9MYSQL");
        try {
            Query q = session.createNativeQuery(query).setParameter("trans_data", trans_data);
            records = q.getResultList();
        } catch (Exception e) {
            log.error(ERROR, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }

        for (Object[] record : records) {
            TMC tmc = new TMC();
            try {
                tmc.setMti(checkNull(record[0]));
                tmc.setTrans_data(checkNull(record[1]));
                tmc.setSource_account(checkNull(record[2]));
                tmc.setDestination_account(checkNull(record[3]));
                tmc.setTranstype(checkNull(record[4]));
                tmc.setTrans_date(convertDate(checkNull(record[5])));
                tmc.setPro_code(checkNull(record[6]));
                tmc.setPan(checkNull(record[7]));
                tmc.setAmount(BigDecimal.valueOf(Double.valueOf(checkNull(record[8]))));
                tmc.setTrack2(checkNull(record[9]));
                tmc.setResponse_code(checkNull(record[10]));
                tmc.setResponse(checkNull(record[11]));
                tmc.setTerminal_id(checkNull(record[12]));
                tmc.setStan(checkNull(record[13]));
                tmc.setCard_acc_name(checkNull(record[14]));
                tmc.setFee(BigDecimal.valueOf(Double.valueOf(checkNull(record[15]))));
                tmc.setReference(checkNull(record[16]));
                tmc.setSrc_node(checkNull(record[17]));
                tmc.setTarget_node(checkNull(record[18]));
                tmc.setResponse_time(convertDate(checkNull(record[19])));
            } catch (Exception e) {
                log.error(ERROR, e);
            }
            recordsList.add(tmc);
        }

        return recordsList;
    }

    public String insertETransaction(E_RequestLog e_RequestLog) {
        Session session = DbHibernate.getSession("40.6SYBASE");
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            String query = "INSERT INTO ECARDDB..E_TRANSACTION (TRANSID, CARD_NUM, TRANS_NO, TRANS_DATE, TRANS_DESCR, TRANS_AMOUNT, TRANS_TYPE, TRANS_CODE, MERCHANT_CODE, \n"
                    + "CLOSED, EXTERNAL_TRANSID, UNIQUE_TRANSID,SERVICEID,CHANNELID) SELECT 123456*CONVERT(decimal,(11114111111116769 + floor(9999999998769099 * RAND(convert(varbinary, newid())))), 112) + \n"
                    + "CONVERT(decimal,(11114111111116769 + floor(999999999876909 * RAND(convert(varbinary, newid())))), 112) transid, :card_num card_num, "
                    + " left(CONVERT(varchar,(99999411111 + floor(11114111111 * RAND(convert(varbinary, newid())))), 112), 12) trans_no, \n"
                    + "CONVERT(date, getdate()) trans_date, :trans_descr trans_descr, :trans_amount trans_amount, '1' trans_type, 'I' trans_code, \n"
                    + ":merchant_code merchant_code, '1' closed, '' external_transid,  :unique_transid unique_transid , '' service_id, :channelId channelid ";

            Query q = session.createNativeQuery(query);
            q.setParameter("card_num", e_RequestLog.getMerchant_code().substring(0, 10))
                    .setParameter("trans_descr", e_RequestLog.getTrans_descr() + "-RVSL")
                    .setParameter("merchant_code", e_RequestLog.getCard_num())
                    .setParameter("channelId", e_RequestLog.getChannelid())
                    .setParameter("trans_amount", e_RequestLog.getTrans_amount())
                    .setParameter("unique_transid", e_RequestLog.getTransid() + "rvsl");

            int i = q.executeUpdate();

            tx.commit();

            return i > 0 ? "success" : "failed";
        } catch (Exception ex) {
            log.error(ERROR, ex);
            String msg = ex.getMessage();
            log.info("EXCEPTION MESSAGE " + msg);
            if (msg.contains("ConstraintViolationException")) {
                log.info("Database Exception>> Duplicate Record");
                return "Duplicate Record in e_transaction";
            }
            log.info("Database Exception>> an error occured");
            return "An error occured. Please try again";
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public String reverseTransaction() {
        return null;
    }

    public List<Reversal> getPendingReversals(String jsonString) {

        log.info("PENDING Reversal trx request received >> " + jsonString);
        Gson j = new Gson();
        ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class
        );

        String beginDate = apiData.getStartDate();
        String endDate = apiData.getEndDate();
        String bankCode = apiData.getBank_code();

        String qry = "";

        List<Reversal> records = new ArrayList<>();

        String dbType = config.getProperty(bankDB.getOrDefault(bankCode.trim(), null) + "_DB_TYPE");

        if (dbType.equals("sybase")) {

            qry = "Select A.DE41 CHN, A.DE0 MTI, A.DE43 NARRATION, A.DE102 CUSTACCT, A.DE103 MERCHACCT, SUBSTRING(A.UNIQUE_TRANSID,CHARINDEX(':',A.UNIQUE_TRANSID)+1,LEN(A.UNIQUE_TRANSID)) REFERENCE,\n"
                    + "B.DE39 RESP, A.CREATED DATE,  convert(DECIMAL(10,2), convert(DECIMAL(10,2),A.DE4)/100) AMOUNT , convert(DECIMAL(10,2), convert(DECIMAL(10,2),right(A.DE28,8))/100) FEE,A.DE37 SYSTEMREF, convert(DECIMAL(10,2), convert(DECIMAL(10,2),right(A.DE31,8))/100) ELEVY  FROM  E_HOSTREQUESTLOG A LEFT JOIN\n"
                    + " E_HOSTRESPONSELOG B ON A.DE37 = B.DE37 WHERE A.DE43 like '%B2W MoMo%' and A.created between :start_dt and :end_dt and B.DE39 = '00' and SUBSTRING(A.UNIQUE_TRANSID, CHARINDEX(':', A.UNIQUE_TRANSID) + 1, LEN(A.UNIQUE_TRANSID)) not in "
                    + "(select SUBSTRING(UNIQUE_TRANSID, CHARINDEX(':', UNIQUE_TRANSID) + 1, LEN(UNIQUE_TRANSID))  from e_hostrequestlog where\n"
                    + " DE43 like '%B2W MoMo%' and created between :start_dt and :end_dt and DE0 = '0420')";
        } else {
            qry = "Select A.DE41 CHN,A.DE0 MTI,A.DE43 NARRATION,A.DE102 `CUST ACCT`,A.DE103 `MERCH ACCT`, A.UNIQUE_TRANSID `REFERENCE`,B.DE39 RESP,A.CREATED DATE, "
                    + "truncate(A.DE4/100,2) `AMOUNT`, truncate(right(A.DE28,8)/100,2) FEE ,A.DE37 `SYSTEM REF`,  truncate(right(A.DE31,8)/100,2) ELEVY FROM E_HOSTREQUESTLOG A LEFT JOIN E_HOSTRESPONSELOG B "
                    + "ON A.DE37 = B.DE37 WHERE A.DE43 like '%B2W MoMo%' and A. created between :start_dt and :end_dt and B.DE39 ='00' and substring_index(UNIQUE_TRANSID,':',-1) "
                    + "not in(select substring_index(UNIQUE_TRANSID,':',-1) UNIQUE_TRANSID from e_hostrequestlog where DE43 like '%B2W MoMo%' and created between :start_dt and :end_dt and DE0 ='0420') ";
        }

        List<String> references = new ArrayList<>();
        Session session = DbHibernate.getSession(bankDB.getOrDefault(bankCode.trim(), null));
        try {

            Query q = session.createNativeQuery(qry);
            q.setParameter("start_dt", beginDate)
                    .setParameter("end_dt", endDate);
            List<Object[]> resp = q.getResultList();

            for (Object[] record : resp) {
                Reversal ereq = new Reversal();
                ereq.setChn(checkNull(record[0]));
                ereq.setMti(checkNull(record[1]));
                ereq.setNarration(checkNull(record[2]));
                ereq.setCustAccount(checkNull(record[3]));
                ereq.setMerchAccount(checkNull(record[4]));
                ereq.setReference(checkNull(record[5]));
                references.add(checkNull(record[5]));
                ereq.setResp(checkNull(record[6]));
                ereq.setDate(checkNull(record[7]));
                ereq.setAmount(Double.valueOf(checkNull(record[8])));
                ereq.setFee(Double.valueOf(checkNull(record[9])));
                ereq.setElevy(Double.valueOf(checkNull(record[11])));
                ereq.setSystemRef(checkNull(record[10]));

                records.add(ereq);

            }
        } catch (Exception e) {
            log.error(ERROR, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }

        if (!references.isEmpty()) {

            String query = "select reference, client, ifnull(respcode,''), ifnull(flag,'') from telcodb.mobilemoney "
                    + " where reference in (:reference) and paymenttype ='C'";

            Session session2 = DbHibernate.getSession("40.9MYSQL");

            List<Object[]> result = new ArrayList<>();
            try {

                Query q = session2.createNativeQuery(query);

                q.setParameter("reference", references);
                result = q.getResultList();

            } catch (Exception e) {
                log.error("error", e);
            } finally {
                if (session2 != null) {
                    session2.close();
                }
            }

            if (!result.isEmpty()) {

                final HashMap<String, String> resp = mapBankResult(result);

                records = records.parallelStream().map(f -> {

                    String[] data = resp.getOrDefault(f.getReference(), "").split("~", -1);
                    if (data.length > 1) {
                        f.setNetwork(data[0]);
                        f.setProcessed((data[2].equals("7") || data[2].equals("8")) || data[1].equals("00"));
                    } else {
                        f.setNetwork("N/A");
                    }
                    return f;
                }).filter(trans -> !trans.isProcessed()).collect(Collectors.toList());

                List<Reversal> notFoundRecords = records.parallelStream()
                        .filter(trans -> trans.getNetwork().equals("N/A")).collect(Collectors.toList());
                log.info("NOT FOUND TRANSLIST ::: " + notFoundRecords);
            }
        } else {
            log.info("RESULT IS EMPTY");

        }

        return records;

    }

    protected String checkNull(Object Data) {
        if (Data != null && !Data.equals("")) {
            return Data.toString();
        }
        return "";
    }

    protected Date convertDate(String Data) throws ParseException {
        if (!Data.equals("NULL")) {
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(Data);

            return date;
        }
        return null;
    }

    public HashMap mapBankResult(List<Object[]> bankResult) {
        HashMap dMap = new HashMap();
        for (Object[] record : bankResult) {
            try {
                if (record[1] != null && record[2] != null) {
                    dMap.put(record[0].toString(),
                            record[1].toString() + "~" + record[2].toString() + "~" + record[3].toString());
                }
            } catch (Exception e) {
                log.error(ERROR, e);
            }
        }

        return dMap;
    }
}
