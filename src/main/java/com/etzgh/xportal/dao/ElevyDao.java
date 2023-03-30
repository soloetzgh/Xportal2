package com.etzgh.xportal.dao;

import com.etzgh.xportal.model.ApiPostData;
import com.etzgh.xportal.model.Bank;
import com.etzgh.xportal.model.Elevy;
import com.etzgh.xportal.model.GraElevyReport;
import com.etzgh.xportal.model.NlaLiquidationReport;
import com.etzgh.xportal.model.Sms;
import com.etzgh.xportal.utility.Config;
import com.etzgh.xportal.utility.DbHibernate;
import com.etzgh.xportal.utility.GeneralUtility;
import com.etzgh.xportal.utility.SuperHttpClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.json.JSONArray;
import org.json.JSONObject;

public class ElevyDao {

    private static final GeneralUtility utility = new GeneralUtility();
    private static final Logger log = Logger.getLogger(ElevyDao.class.getName());
    protected static final Map<String, String> banks;
    final List<String> CREDIT_SERVICES = Arrays.asList(new Config().getProperty("CREDIT_SERVICES").split("#"));
    final List<String> DEBIT_SERVICES = Arrays.asList(new Config().getProperty("DEBIT_SERVICES").split("#"));
    private static final Gson gson = new Gson();
    private static final Config config = new Config();

    public static void main(String[] args) {
        String json = "{\"startDate\":\"2021-08-13 15:00\",\"endDate\":\"2021-08-13 23:59\",\"merchant\":\"\",\"product\":\"\",\"accountNumber\":\"\",\"status\":\"ALL\",\"service\":\"transactions\",\"action\":\"\",\"apiSecret\":\"\",\"apiCode\":\"\",\"userCode\":\"[1],[17]|0060000290:0067620000000010,[2000000000000048]|00000000000000000002:0069900596470004,[2000000000000049],[2000000000000053]|ALL,[2000000000000054],[2000000000000060]|000,[2000000000000062]|ALL,[2000000000000063]|KNUST,[2000000000000064]|ALL,[22],[2500000000000048]|3,[2500000000000049]|2,[2500000000000050]|ALL,[2500000000000053]|3,[29],[51]|2,[71]|0230010002,[91]|ALL\",\"admin\":\"\",\"type_desc\":\"\",\"role_id\":\"\",\"type_id\":\"[0],[1],[2],[3],[4],[5],[6],[7],[8],[9],[10],[11],[20],[21],[24],[25],[26],[27],[28],[30],[31],[33],[34],[39],[40],[41],[43],[44]\",\"userName\":\"Eugene.Arthur\",\"ClientId\":\"\",\"user_id\":\"9000000000000490\",\"pageNumber\":1,\"rowsPerPage\":1,\"uniqueTransId\":\"\",\"trans_code\":\"\",\"transType\":\"D\",\"bank_code\":\"ALL\",\"subscriberId\":\"\",\"trans_status\":\"\",\"roleList\":[],\"channel\":\"09\",\"bank\":\"\",\"biller\":\"\",\"transId\":\"\",\"bankid\":\"\",\"from_source\":\"\",\"card_num\":\"\",\"optionType\":\"ALL\",\"payType\":\"\",\"wcMerchant\":\"\",\"destination\":\"\",\"lineType\":\"ALL\",\"trans_channel\":\"\",\"pan\":\"\",\"terminal_id\":\"\",\"card_account\":\"\",\"cop_issuercode\":\"\",\"cop_role_id\":\"\",\"cop_company_id\":\"\",\"mobile_no\":\"\",\"phone\":\"\",\"type\":\"\",\"amount\":\"\",\"amount2\":\"\",\"refundStatus\":\"\",\"partialReversal\":\"\",\"reversalReason\":\"\",\"ipAddress\":\"0:0:0:0:0:0:0:1\",\"userData\":\"\",\"appId\":[],\"company\":\"\",\"origin\":\"\",\"options\":{}}";
        new ElevyDao().getElevyTransactions(json);
        System.exit(0);
    }

    static {
        banks = convertListAfterJava8(new AppDao().getBanks());

    }

    public List<Elevy> getElevyTransactions(String jsonString) {

        log.info("Elevy trx request received >>  " + jsonString);
        Gson j = new Gson();

        ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);
        String start_dt = apiData.getStartDate();
        String end_dt = apiData.getEndDate();
        String bank = apiData.getBank_code();
        String account = apiData.getAccount();
        String reference = apiData.getUniqueTransId();
        String msisdn = apiData.getFrom_source();
        String network = apiData.getLineType();
        String paymenttype = apiData.getTransType();
        String mode = apiData.getChannel();
        String status = apiData.getStatus();
        String alert = apiData.getOptionType();

        String user_code = apiData.getUserCode();

        String type_id = apiData.getType_id();
        final String searchKey = apiData.getSearchKey();
        final String service = apiData.getService();
        List<String> roleList = new ArrayList<>();

        List<Elevy> records = new ArrayList<>();

        String userRoles = "";

        if (msisdn == null) {
            msisdn = "";
        }

        if (!msisdn.isEmpty() && msisdn.startsWith("0") && msisdn.length() == 10) {
            msisdn = "233" + msisdn.substring(1);
        }

        if (alert == null || alert.trim().isEmpty()) {
            alert = "ALL";
        }
        if (paymenttype == null || paymenttype.trim().isEmpty()) {
            paymenttype = "ALL";
        }
        if (network == null || network.trim().isEmpty()) {
            network = "ALL";
        }
        if (account == null || account.trim().isEmpty()) {
            account = "";
        }
        if (bank == null || bank.trim().isEmpty() || bank.trim().equals("000")) {
            bank = "ALL";
        }

        if (service.equalsIgnoreCase("search") && searchKey != null && searchKey.trim().isEmpty()) {
            return records;
        }

        if (service.equalsIgnoreCase("search")) {
            reference = searchKey;
        }
        if (reference == null) {
            reference = "";
        }

        if (!type_id.isEmpty()) {

            if (type_id.contains("[0]") || type_id.contains("[6]")) {

            } else if (type_id.contains("[5]")) {
                userRoles = utility.getRoleParameters("[91]", user_code);

                roleList = Arrays.asList(userRoles.split("~"));
                if (network.equalsIgnoreCase("ALL")) {

                } else {
                    if (roleList.contains(network)) {
                        roleList = new ArrayList<>(roleList);
                        roleList.add(network);
                    }
                }
            } else {
                return records;
            }
        } else {
            return records;
        }

        String query = "select reference, sender_etz_bank_code, sender_phone_number, sender_etz_card_number,sender_account_number, "
                + "receiver_etz_bank_code, receiver_account_number, service, service_provider, channel, receiver_institution_id, "
                + "sender_issuer_id, sender_tin, reserve_req_time,reserve_rsp_time, reserve_status_code, reserve_status_msg, "
                + "elevy_id, transfer_amount,taxable_amount_error,taxable_amount, elevy,elevy_error,elevy_error_diff,state, "
                + "state_history, flag,status,process_attempt,confirm_status_code,confirm_status_msg,cancel_status_code, "
                + "cancel_status_msg from elevy.elevy where 1=1 "
                + (service.equalsIgnoreCase("search") ? "  AND (reference = :reference or elevy_id = :reference) " : " and reserve_req_time between :start_dt  and :end_dt ")
                + (!reference.trim().isEmpty() ? " and (reference = :reference or elevy_id = :reference) " : "")
                + (!msisdn.trim().isEmpty() ? " and sender_phone_number = :msisdn " : "")
                + (status.equalsIgnoreCase("ALL") ? "" : (status.equalsIgnoreCase("successful") ? " and status ='00' " : (status.equalsIgnoreCase("failed") ? " and status in('06') " : " and status not in('06','00')")))
                + (!account.trim().isEmpty() ? " and (receiver_account_number = :account or sender_account_number = :account) " : "")
                + (!alert.equalsIgnoreCase("ALL") ? " and a.flag = :alert " : "")
                + (!paymenttype.equalsIgnoreCase("ALL") ? (paymenttype.equalsIgnoreCase("C") ? " and service in(:CREDITSERVICES) " : " and service in(:DEBITSERVICES) ") : "")
                + (!bank.equalsIgnoreCase("ALL") ? " and (substring_index(sender_etz_bank_code,'_',-1) = :bank or substring_index(receiver_etz_bank_code,'_',-1) = :bank) " : "")
                + " order by reserve_req_time desc";

        Session session = DbHibernate.getSession("30.19MYSQL");

        List<Object[]> resp = new ArrayList<>();
        try {

            Query q = session.createNativeQuery(query);

            if (!service.equalsIgnoreCase("search")) {
                q.setParameter("start_dt", (Object) start_dt)
                        .setParameter("end_dt", (Object) end_dt);
            }

            if (!msisdn.trim().isEmpty()) {
                q.setParameter("msisdn", msisdn.trim());
            }

            if (!bank.equalsIgnoreCase("ALL")) {
                q.setParameter("bank", bank);
            }
            if (!account.trim().isEmpty()) {
                q.setParameter("account", account);
            }

            if (!network.equalsIgnoreCase("ALL")) {
                q.setParameter("network", network);
            }

            if (query.contains(":CREDITSERVICES")) {
                q.setParameter("CREDITSERVICES", CREDIT_SERVICES);

            }
            if (query.contains(":DEBITSERVICES")) {
                q.setParameter("DEBITSERVICES", DEBIT_SERVICES);
            }
            if (query.contains(":reference")) {
                q.setParameter("reference", reference);
            }

            resp = q.getResultList();

        } catch (Exception e) {
            log.error("error", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        List<String> references = new ArrayList<>();
        for (Object[] record : resp) {
            Elevy elevy = new Elevy();
            try {
                elevy.setReference(checkNull(record[0]));
                references.add(checkNull(record[0]));
                elevy.setSender_etz_bank_code(checkNull(record[1]));
                elevy.setSender_phone_number(checkNull(record[2]));
                elevy.setSender_etz_card_number(checkNull(record[3]));

                elevy.setSender_account_number(checkNull(record[4]));
                elevy.setReceiver_etz_bank_code(checkNull(record[5]));
                elevy.setReceiver_account_number(checkNull(record[6]));
                String serviceType = checkNull(record[7]);

                elevy.setService(serviceType);
                elevy.setService_provider(checkNull(record[8]));
                elevy.setChannel(checkNull(record[9]));
                elevy.setReceiver_institution_id(checkNull(record[10]));
                elevy.setSender_issuer_id(checkNull(record[11]));
                elevy.setSender_tin(checkNull(record[12]));
                elevy.setReserve_req_time(checkNull(record[13]));
                elevy.setReserve_rsp_time(checkNull(record[14]));
                elevy.setReserve_status_code(checkNull(record[15]));
                elevy.setReserve_status_msg(checkNull(record[16]));
                elevy.setElevy_id(checkNull(record[17]));
                elevy.setTransfer_amount(Double.valueOf(checkNull(record[18])));
                elevy.setTaxable_amount_error(Double.valueOf(checkNull(record[19])));
                elevy.setTaxable_amount(Double.valueOf(checkNull(record[20])));
                elevy.setElevy(Double.valueOf(checkNull(record[21])));
                elevy.setElevy_error(Double.valueOf(checkNull(record[22])));
                elevy.setElevy_error_diff(Double.valueOf(checkNull(record[23])));
                elevy.setState(checkNull(record[24]));
                elevy.setState_history(checkNull(record[25]));
                elevy.setFlag(checkNull(record[26]));
                elevy.setStatus(checkNull(record[27]));
                elevy.setProcess_attempt(checkNull(record[28]));
                elevy.setConfirm_status_code(checkNull(record[29]));
                elevy.setConfirm_status_msg(checkNull(record[30]));
                elevy.setCancel_status_code(checkNull(record[31]));
                elevy.setCancel_status_msg(checkNull(record[32]));

            } catch (Exception e) {
                log.error("error", e);
            }
            records.add(elevy);
        }

        Map<String, Double> transactionFees = getTransactionFees(references);

        if (!transactionFees.isEmpty()) {
            records.stream().parallel().forEach(e -> {
                e.setTransfer_fee(transactionFees.getOrDefault(e.getReference(), 0.00));
            });
        }

        return records;
    }

    public Map<String, Double> getTransactionFees(List<String> references) {
        Map<String, Double> result = new HashMap<>();
        if (!references.isEmpty()) {

            String query = "select transid, ifnull(fee,0.00) from ecarddb.e_requestlog where transid in(:references) "
                    + " union "
                    + " select transid, ifnull(fee,0.00) from ecarddb2.e_requestlog where transid in (:references)";
            List<Object[]> resp = new ArrayList<>();

            Session session = DbHibernate.getSession("40.15MYSQL");
            try {

                Query q = session.createNativeQuery(query);
                q.setParameter("references", references);
                resp = q.getResultList();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            } finally {
                if (session != null) {
                    session.close();
                }
            }
            for (Object[] record : resp) {
                result.put(record[0].toString(), Double.valueOf(record[1].toString()));
            }
        }
        return result;
    }

    public List<Elevy> getElevyReport(String jsonString) {

        log.info("Elevy trx request received >>  " + jsonString);
        Gson j = new Gson();

        ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);
        String start_dt = apiData.getStartDate();
        String end_dt = apiData.getEndDate();
        String bank = apiData.getBank_code();
        String account = apiData.getAccount();
        String reference = apiData.getUniqueTransId();
        String msisdn = apiData.getFrom_source();
        String network = apiData.getLineType();
        String paymenttype = apiData.getTransType();
        String mode = apiData.getChannel();
        String status = apiData.getStatus();
        String alert = apiData.getOptionType();

        String user_code = apiData.getUserCode();

        String type_id = apiData.getType_id();
        final String searchKey = apiData.getSearchKey();
        final String service = apiData.getService();
        List<String> roleList = new ArrayList<>();

        List<Elevy> records = new ArrayList<>();

        String userRoles = "";

        if (msisdn == null) {
            msisdn = "";
        }

        if (!msisdn.isEmpty() && msisdn.startsWith("0") && msisdn.length() == 10) {
            msisdn = "233" + msisdn.substring(1);
        }

        if (alert == null || alert.trim().isEmpty()) {
            alert = "ALL";
        }
        if (paymenttype == null || paymenttype.trim().isEmpty()) {
            paymenttype = "ALL";
        }
        if (network == null || network.trim().isEmpty()) {
            network = "ALL";
        }
        if (account == null || account.trim().isEmpty()) {
            account = "";
        }
        if (bank == null || bank.trim().isEmpty() || bank.trim().equals("000")) {
            bank = "ALL";
        }

        if (service.equalsIgnoreCase("search") && searchKey != null && searchKey.trim().isEmpty()) {
            return records;
        }

        if (service.equalsIgnoreCase("search")) {
            reference = searchKey;
        }
        if (reference == null) {
            reference = "";
        }

        if (!type_id.isEmpty()) {

            if (type_id.contains("[0]") || type_id.contains("[6]")) {

            } else if (type_id.contains("[5]")) {
                userRoles = utility.getRoleParameters("[91]", user_code);

                roleList = Arrays.asList(userRoles.split("~"));
                if (network.equalsIgnoreCase("ALL")) {

                } else {
                    if (roleList.contains(network)) {
                        roleList = new ArrayList<>(roleList);
                        roleList.add(network);
                    }
                }
            } else {
                return records;
            }
        } else {
            return records;
        }

        String query = "select reference, sender_etz_bank_code, sender_phone_number, sender_etz_card_number,sender_account_number, "
                + "receiver_etz_bank_code, receiver_account_number, service, service_provider, channel, receiver_institution_id, "
                + "sender_issuer_id, sender_tin, reserve_req_time,reserve_rsp_time, reserve_status_code, reserve_status_msg, "
                + "elevy_id, transfer_amount,taxable_amount_error,taxable_amount, elevy,elevy_error,elevy_error_diff,state, "
                + "state_history, flag,status,process_attempt,confirm_status_code,confirm_status_msg,cancel_status_code, "
                + "cancel_status_msg from elevy.elevy where 1=1 "
                + (service.equalsIgnoreCase("search") ? "  AND (reference = :reference or elevy_id = :reference) " : " and reserve_req_time between :start_dt  and :end_dt ")
                + (!reference.trim().isEmpty() ? " and (reference = :reference or elevy_id = :reference) " : "")
                + (!msisdn.trim().isEmpty() ? " and sender_phone_number = :msisdn " : "")
                + (status.equalsIgnoreCase("ALL") ? "" : (status.equalsIgnoreCase("successful") ? " and status ='00' " : (status.equalsIgnoreCase("failed") ? " and status in('06') " : " and status not in('06','00')")))
                + (!account.trim().isEmpty() ? " and (receiver_account_number = :account or sender_account_number = :account) " : "")
                + (!alert.equalsIgnoreCase("ALL") ? " and a.flag = :alert " : "")
                + (!paymenttype.equalsIgnoreCase("ALL") ? (paymenttype.equalsIgnoreCase("C") ? " and service in(:CREDITSERVICES) " : " and service in(:DEBITSERVICES) ") : "")
                + (!bank.equalsIgnoreCase("ALL") ? " and (substring_index(sender_etz_bank_code,'_',-1) = :bank or substring_index(receiver_etz_bank_code,'_',-1) = :bank) " : "")
                + " order by reserve_req_time desc";

        Session session = DbHibernate.getSession("30.19MYSQL");

        try {

            Query q = session.createNativeQuery(query);

            if (!service.equalsIgnoreCase("search")) {
                q.setParameter("start_dt", (Object) start_dt)
                        .setParameter("end_dt", (Object) end_dt);
            } else {
                q.setParameter("reference", (Object) reference.trim());
            }

            if (!msisdn.trim().isEmpty()) {
                q.setParameter("msisdn", msisdn.trim());
            }

            if (!bank.equalsIgnoreCase("ALL")) {
                q.setParameter("bank", bank);
            }
            if (!account.trim().isEmpty()) {
                q.setParameter("account", account);
            }
            if (!paymenttype.equalsIgnoreCase("ALL")) {
                q.setParameter("paymenttype", paymenttype);
            }
            if (!network.equalsIgnoreCase("ALL")) {
                q.setParameter("network", network);
            }

            if (query.contains(":CREDITSERVICES")) {
                q.setParameter("CREDITSERVICES", CREDIT_SERVICES);

            }
            if (query.contains(":DEBITSERVICES")) {
                q.setParameter("DEBITSERVICES", DEBIT_SERVICES);
            }

            List<Object[]> resp = q.getResultList();

            for (Object[] record : resp) {
                Elevy elevy = new Elevy();
                try {
                    elevy.setReference(checkNull(record[0]));
                    elevy.setSender_etz_bank_code(checkNull(record[1]));
                    elevy.setSender_phone_number(checkNull(record[2]));
                    elevy.setSender_etz_card_number(checkNull(record[3]));

                    elevy.setSender_account_number(checkNull(record[4]));
                    elevy.setReceiver_etz_bank_code(checkNull(record[5]));
                    elevy.setReceiver_account_number(checkNull(record[6]));
                    elevy.setService(checkNull(record[7]));
                    elevy.setService_provider(checkNull(record[8]));
                    elevy.setChannel(checkNull(record[9]));
                    elevy.setReceiver_institution_id(checkNull(record[10]));
                    elevy.setSender_issuer_id(checkNull(record[11]));
                    elevy.setSender_tin(checkNull(record[12]));
                    elevy.setReserve_req_time(checkNull(record[13]));
                    elevy.setReserve_rsp_time(checkNull(record[14]));
                    elevy.setReserve_status_code(checkNull(record[15]));
                    elevy.setReserve_status_msg(checkNull(record[16]));
                    elevy.setElevy_id(checkNull(record[17]));
                    elevy.setTransfer_amount(Double.valueOf(checkNull(record[18])));
                    elevy.setTaxable_amount_error(Double.valueOf(checkNull(record[19])));
                    elevy.setTaxable_amount(Double.valueOf(checkNull(record[20])));
                    elevy.setElevy(Double.valueOf(checkNull(record[21])));
                    elevy.setElevy_error(Double.valueOf(checkNull(record[22])));
                    elevy.setElevy_error_diff(Double.valueOf(checkNull(record[23])));
                    elevy.setState(checkNull(record[24]));
                    elevy.setState_history(checkNull(record[25]));
                    elevy.setFlag(checkNull(record[26]));
                    elevy.setStatus(checkNull(record[27]));
                    elevy.setProcess_attempt(checkNull(record[28]));
                    elevy.setConfirm_status_code(checkNull(record[29]));
                    elevy.setConfirm_status_msg(checkNull(record[30]));
                    elevy.setCancel_status_code(checkNull(record[31]));
                    elevy.setCancel_status_msg(checkNull(record[32]));

                } catch (Exception e) {
                    log.error("error", e);
                }
                records.add(elevy);
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
    

    protected String checkNull(Object Data) {
        if (Data != null && !Data.equals("")) {
            return Data.toString();
        }
        return "";
    }

    public static Map<String, String> convertListAfterJava8(List<Bank> list) {
        Map<String, String> map = list.stream().collect(Collectors.toMap(Bank::getIssuer_code, bank -> bank.getIssuer_name()));
        return map;
    }

}
