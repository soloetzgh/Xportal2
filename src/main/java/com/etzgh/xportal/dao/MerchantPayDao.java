package com.etzgh.xportal.dao;

import com.etzgh.xportal.model.ApiPostData;
import com.etzgh.xportal.model.MerchantPay;
import com.etzgh.xportal.model.MerchantPaySettlement;
import com.etzgh.xportal.model.NODES;
import com.etzgh.xportal.utility.Config;
import com.etzgh.xportal.utility.DbHibernate;
import com.etzgh.xportal.utility.GeneralUtility;
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

public class MerchantPayDao {

    private static final Logger log = Logger.getLogger(MerchantPayDao.class.getName());
    private static final GeneralUtility utility = new GeneralUtility();
    private static final Config config = new Config();
    private static final List<String> responseExclusionList;

    static {
        String exclusion = config.getProperty("MPAY_RESPONSE_EXCLUSION");
        List<String> l = new ArrayList<>();
        if (!exclusion.trim().isEmpty()) {
            l = Arrays.asList(exclusion.trim().split("\\s*,\\s*"));
        }
        responseExclusionList = l;
    }

    public static void main(String[] args) {

        String req = "{\"startDate\":\"2021-03-06 00:00\",\"endDate\":\"2021-04-06 23:59\",\"merchant\":\"6TOTAL\",\"product\":\"ALL\",\"status\":\"ALL\",\"service\":\"transactions\",\"action\":\"\",\"apiSecret\":\"\",\"apiCode\":\"\",\"userCode\":\"[1],[17]|0060000290:0067620000000010,[2000000000000048]|00000000000000000002:0069900596470004,[2000000000000049],[2000000000000053]|ALL,[2000000000000054],[2000000000000060]|000,[2000000000000062]|ALL,[2000000000000063]|KNUST,[2000000000000064]|ALL,[2500000000000049]|2,[2500000000000050]|ALL,[2500000000000053]|3,[29],[50]|2,[71]|0230010002,[91]|ALL\",\"admin\":\"\",\"type_desc\":\"\",\"role_id\":\"\",\"type_id\":\"[0],[1],[2],[3],[4],[5],[6],[7],[8],[9],[10],[11],[20],[21],[24],[25],[26],[27],[28],[30],[31],[33],[35],[37],[39],[40],[41],[43],[44]\",\"userName\":\"\",\"ClientId\":\"\",\"user_id\":\"9000000000000490\",\"pageNumber\":1,\"rowsPerPage\":1,\"uniqueTransId\":\"02MPAY-0703626-B03C-710\",\"trans_code\":\"ALL\",\"transType\":\"\",\"subscriberId\":\"\",\"trans_status\":\"\",\"channel\":\"\",\"bank\":\"SCB\",\"biller\":\"\",\"transId\":\"\",\"bankid\":\"\",\"from_source\":\"\",\"card_num\":\"\",\"optionType\":\"\",\"payType\":\"\",\"wcMerchant\":\"\",\"destination\":\"\",\"lineType\":\"\",\"trans_channel\":\"\",\"pan\":\"\",\"terminal_id\":\"\",\"card_account\":\"\",\"cop_issuercode\":\"\",\"cop_role_id\":\"\",\"cop_company_id\":\"\",\"mobile_no\":\"\",\"phone\":\"\",\"type\":\"\",\"amount\":\"\",\"amount2\":\"\",\"refundStatus\":\"\",\"partialReversal\":\"\",\"reversalReason\":\"\",\"ipAddress\":\"\",\"userData\":\"\",\"appId\":[],\"company\":\"\",\"options\":{}}";
        System.out.println("LIST::: " + new MerchantPayDao().getMerchantPayTrans(req));
    }

    public List<MerchantPay> getMerchantPayTrans(final String jsonString) {
        log.error("MerchantPay trx request received >> " + jsonString);
        final Gson j = new Gson();
        final ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);

        final String start_dt = apiData.getStartDate();
        final String end_dt = apiData.getEndDate();
        String reference = apiData.getUniqueTransId();
        String merchant = apiData.getMerchant();
        String status = apiData.getStatus();
        String source_frm = apiData.getFrom_source();
        final String bank = apiData.getBank();
        final String ucode = apiData.getUserCode();
        final String cardScheme = apiData.getCardSchemeNumbers();
        String query = "";
        String merchantCode = "";
        final String merchantType = ucode.split(":")[0];
        final String type_id = apiData.getType_id();
        String product = apiData.getProduct();
        final String bankCode = apiData.getBank_code();
        final String service = apiData.getService();
        String searchKey = apiData.getSearchKey();
        String network = apiData.getLineType();
        String servicecode = apiData.getTrans_code();
        String clientId = apiData.getClientId();
        String settlementStatus = apiData.getTrans_status();

        List<MerchantPay> recordsList = new ArrayList<>();
        List<Object[]> records = new ArrayList<>();
        List<String> roleList = new ArrayList<>();
        List<String> bankList = new ArrayList<>();
        List<String> codeList = new ArrayList<>();

        String userRoles = "";

        final String dest = apiData.getSubscriberId();
//        System.out.println("MERCHANT: " + merchant);
        String str_source_frm = source_frm;
        if (reference == null) {
            reference = "";
        }
        if (str_source_frm == null) {
            str_source_frm = "";
        }
        if (settlementStatus == null) {
            settlementStatus = "ALL";
        }
        if (!str_source_frm.isEmpty() && str_source_frm.startsWith("0") && str_source_frm.length() == 10) {
            str_source_frm = "233" + str_source_frm.substring(1);
        }
        if (merchant == null) {
            merchant = "";
        }

        if (searchKey == null) {
            searchKey = "";
        } else {
            searchKey = searchKey.trim();
        }
        if (servicecode == null) {
            servicecode = "";
        }
        if (product == null) {
            product = "";
        }
        if (network == null) {
            network = "";
        }
        if (clientId == null) {
            clientId = "";
        } else {
            clientId = clientId.trim();
        }
        if (status == null || status.trim().isEmpty()) {
            status = "ALL";
        }

        if (service.equalsIgnoreCase("search") && searchKey.isEmpty()) {
            return recordsList;
        }

        if (!type_id.isEmpty()) {
            if (type_id.contains("[0]")) {
                if (bank != null) {

                    if (!bank.equalsIgnoreCase("all") && !bank.trim().isEmpty()) {
                        bankList.add(bank.toUpperCase());
                    }

                    if (!merchant.equalsIgnoreCase("all") && !merchant.trim().isEmpty()) {
                        roleList.add(merchant.toUpperCase());
                    }
                    if (!(servicecode.trim().isEmpty() || servicecode.equalsIgnoreCase("ALL"))) {
                        codeList.add(servicecode.toUpperCase());
                    }
                }

            } else if (type_id.contains("[9]")) {

                userRoles = utility.getRoleParameters("[2000000000000049]", ucode);
                log.error("ucode: " + userRoles);

                if (!userRoles.isEmpty()) {
                    bankList = Pattern.compile("~").splitAsStream(userRoles).filter(f -> f.split(":")[0].equalsIgnoreCase("all") || f.split(":")[1].equalsIgnoreCase("ALL")).map(f -> f.split(":")[0]).distinct().collect(Collectors.toList());
                    roleList = Pattern.compile("~").splitAsStream(userRoles).filter(f -> !f.split(":")[0].equalsIgnoreCase("ALL") && !f.split(":")[1].equalsIgnoreCase("ALL")).map(f -> f.split(":")[1]).distinct().collect(Collectors.toList());
                    codeList = Pattern.compile("~").splitAsStream(userRoles).filter(f -> f.split(":").length == 3).map(f -> f.split(":")[2]).distinct().collect(Collectors.toList());

                    if (merchant.equalsIgnoreCase("ALL")) {

                    } else {
//                        System.out.println("ROLELIST::: " + roleList + " - ");
                        if (roleList.contains(merchant) || userRoles.split(":")[1].equalsIgnoreCase("ALL")) {
                            roleList = new ArrayList<>();
                            roleList.add(merchant);
                        }
                    }
                    if (servicecode.trim().isEmpty() || servicecode.equalsIgnoreCase("ALL")) {

                    } else {
                        if ((codeList.isEmpty() || codeList.contains("ALL")) || codeList.contains(servicecode)) {
                            codeList = new ArrayList<>();
                            codeList.add(servicecode);
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

        Session session = DbHibernate.getSession("USSDDBMYSQL");
        Map<String, String> momo = new HashMap<>();

        try {
            Query q;
            List allroleList = new ArrayList<>();

            if (!bankList.isEmpty()) {

                String bankSql = "select group_concat(payment_alias) from ussd_db.etz_general_merchants where 1=1 and bank in (:bank) ";

                q = session.createNativeQuery(bankSql);
                q.setParameter("bank", bankList);

                allroleList = Arrays.asList(((String) q.getSingleResult()).split(","));

            }

            //if transid is passed
            if (!clientId.isEmpty() || !searchKey.isEmpty()) {
                if (!clientId.isEmpty()) {
                    momo = getMobileMoney(clientId);
                } else {
                    momo = getMobileMoney(searchKey);
                }
//                log.info("SIZE "+ momo.size());
                if (momo.size() > 0) {
                    searchKey = new ArrayList<>(momo.keySet()).get(0);
                } else {
                    return recordsList;
                }
            }

            String sql = "SELECT a.etz_ref_id, '' destacct, a.channel, a.mobile_no, a.merchant,  ifnull(a.fee, 0.00) fee, ifnull(a.trans_amount,0.00) trans_amount, a.3rdParty_paymt_ref transid, (case when a.channel ='card' then UPPER(a.callback_msg) else a.trans_status end) trans_status, a.status, '' value_msg, \n"
                    + " a.trans_date, '' callback_msg, 'BILL' mainoptions, '' flag, '' provider, a.reference, a.product trans_type, a.name, a.extra_info,a.network, a.narration, a.bank, (case when b.status = '0' then 'SUCCESSFUL' when b.status is null then b.status when b.status ='06' then 'FAILED' else '' end)  settlement_status, b.settlementResponse settlement_response, b.otherRef settlement_ref  FROM ussd_db.etz_mpay_report a left join ussd_db.gcb_mpay_settlement1 b  on a.etz_ref_id = b.unique_transid where 1=1 "
                    + (!type_id.contains("[0]") && responseExclusionList != null && !responseExclusionList.isEmpty() ? " and a.status not in (:mpay_exclusion_list) " : "")
                    + (service.equalsIgnoreCase("search") ? " AND a.etz_ref_id = :searchKey) " : "  and a.trans_date BETWEEN :start_dt and :end_dt ")
                    + (reference.trim().isEmpty() ? "" : " AND etz_ref_id = :reference ")
                    + (!roleList.isEmpty() && !roleList.contains("all") ? " and a.payment_alias in (:vastype) " : (allroleList.isEmpty() ? "" : " AND a.payment_alias in (:allroleList) "))
                    + (str_source_frm.trim().isEmpty() ? "" : " AND a.mobile_no like :dest ")
                    + (!status.equalsIgnoreCase("all") ? (!status.equalsIgnoreCase("pending") ? (status.equalsIgnoreCase("successful") ? " AND a.status = '00' " : " and a.status not in ('00','58') ") : " and a.status in ('58') ") : "")
                    + (!settlementStatus.equalsIgnoreCase("all") ? (!settlementStatus.equalsIgnoreCase("pending") ? (settlementStatus.equalsIgnoreCase("successful") ? " AND b.status = '0' " : " and b.id is not null and b.status not in ('0','') ") : " and b.is is not null and b.status in ('') ") : "")
                    + (!product.trim().isEmpty() && !product.trim().equalsIgnoreCase("all") ? " AND a.product = :product " : "")
                    + (!codeList.isEmpty() ? " AND a.location_code in (:servicecode) " : "")
                    + " group by a.etz_ref_id order by a.trans_date desc";

            q = session.createNativeQuery(sql);
//            System.out.println("QUERY: "+ sql);
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
            } else if (!allroleList.isEmpty()) {
                q.setParameter("allroleList", allroleList);
            }

            if (!str_source_frm.trim().isEmpty()) {
                q.setParameter("dest", "%" + (Object) str_source_frm.trim());
            }

            if (!codeList.isEmpty()) {
                q.setParameter("servicecode", (Object) codeList);
            }
            if (!product.trim().isEmpty() && !product.trim().equalsIgnoreCase("all")) {
                q.setParameter("product", (Object) product);
            }
            if (!type_id.contains("[0]") && responseExclusionList != null && !responseExclusionList.isEmpty()) {
                q.setParameter("mpay_exclusion_list", responseExclusionList);
            }

            records = (List<Object[]>) q.getResultList();

        } catch (Exception et) {
            log.error("error", et);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        List<String> momoRefs = new ArrayList<>();

        for (final Object[] record : records) {
            final MerchantPay mp = new MerchantPay();
            try {

                mp.setEtz_reference(checkNull(record[0]));
                mp.setMobile_no(checkNull(record[3]));
                mp.setPayment_type(checkNull(record[2]));
                if (mp.getPayment_type().equalsIgnoreCase("momo") || mp.getPayment_type().equalsIgnoreCase("gmoney")) {
                    momoRefs.add(mp.getEtz_reference());
                }
                mp.setNetwork(checkNull(record[20]));
                mp.setTransid(checkNull(record[7]));
                mp.setMerchant(checkNull(record[4]));
                mp.setReference(checkNull(record[16]));
                mp.setTrans_status(checkNull(record[9]));
                mp.setStatus(checkNull(record[8]));
                mp.setAmount(BigDecimal.valueOf(Double.valueOf(record[6].toString())));
                mp.setFee(BigDecimal.valueOf(Double.valueOf(record[5].toString())));
                mp.setProduct(checkNull(record[17]));
                mp.setStatus_msg(checkNull(record[21]));
                mp.setName(checkNull(record[18]));
                mp.setExtra_info(checkNull(record[19]));
                mp.setBank(checkNull(record[22]));
                if (mp.getBank().equalsIgnoreCase("GCB")) {
                    mp.setSettlementStatus(checkNull(record[23]));
                    mp.setSettlementMessage(checkNull(record[24]));
                    mp.setSettlementRef(checkNull(record[25]));
                }

                mp.setTrans_date(checkNull(record[11]));
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }

            recordsList.add(mp);
        }

        if (!momoRefs.isEmpty()) {
            if (momo.isEmpty()) {
                momo = getMobileMoney(momoRefs);
            }
            final Map<String, String> m = momo;
            recordsList.parallelStream().filter(f -> f.getPayment_type().equalsIgnoreCase("momo") || f.getPayment_type().equalsIgnoreCase("gmoney")).forEach(f -> {
                String d = m.getOrDefault(f.getEtz_reference(), "");
                if (!d.isEmpty()) {

                    try {
                        String[] data = d.split("-", -1);
                        f.setNetwork(data[0]);
                        f.setTransid(data[1]);
                        f.setStatus(data[2]);
                        f.setStatus_msg((data[4].equals("8") || data[4].equals("7") ? "REVERSED" : data[3]));
                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                    }
                }
            });

        }

        return recordsList;
    }

    public Map<String, String> getMobileMoney(List<String> momoRefs) {
        Map<String, String> result = new HashMap<>();
        List<Object[]> records = new ArrayList<>();
        Session session = DbHibernate.getSession("40.9MYSQL");
        try {
            String sql = "select reference, concat_ws('-', client,ifnull(clientid,'N/A'),ifnull(respcode,'N/A'),ifnull(narration,'N/A'), ifnull(flag,'')) from telcodb.mobilemoney where reference in (:reference) and paymenttype ='D'";
            Query q = session.createNativeQuery(sql);
            q.setParameter("reference", momoRefs);

            records = (List<Object[]>) q.getResultList();

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        for (final Object[] record : records) {
            try {
                result.put(record[0].toString(), record[1].toString());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }

        }

        return result;
    }

    public Map<String, String> getMobileMoney(String reference) {
        Map<String, String> result = new HashMap<>();
        List<Object[]> records = new ArrayList<>();
        Session session = DbHibernate.getSession("40.9MYSQL");
        try {
            String sql = "select reference, concat_ws('-', client,ifnull(clientid,'N/A'),ifnull(respcode,'N/A'),ifnull(narration,'N/A'), ifnull(flag,'')) from telcodb.mobilemoney where (clientid = :reference or reference = :reference) and paymenttype ='D'";
            Query q = session.createNativeQuery(sql);
            q.setParameter("reference", reference);

            records = (List<Object[]>) q.getResultList();

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        for (final Object[] record : records) {
            try {
                result.put(record[0].toString(), record[1].toString());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }

        }

        return result;
    }

    public List<MerchantPaySettlement> getMerchantPaySettlementTrans(final String jsonString) {
        log.info("MerchantPay trx request received >> " + jsonString);
        final Gson j = new Gson();
        final ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);

        final String start_dt = apiData.getStartDate();
        final String end_dt = apiData.getEndDate();
        String reference = apiData.getUniqueTransId();
        String merchant = apiData.getMerchant();
        String status = apiData.getStatus();
        final String source_frm = apiData.getFrom_source();
        String bank = apiData.getBank();
        final String ucode = apiData.getUserCode();
        String query = "";

        final String type_id = apiData.getType_id();

        final String service = apiData.getService();
        String searchKey = apiData.getSearchKey();
        List<MerchantPaySettlement> recordsList = new ArrayList<>();
        List<Object[]> records = new ArrayList<>();
        List<String> roleList = new ArrayList<>();
        List<String> bankList = new ArrayList<>();

        String userRoles = "";

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
        if (bank == null) {
            bank = "ALL";
        }
        switch (status.toLowerCase()) {
            case "successful":
                status = "0";
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

        if (!type_id.isEmpty()) {
            if (type_id.contains("[0]")) {
                System.out.println("BANAK: " + bank + " :: " + !bank.equalsIgnoreCase("all"));
                if (!bank.equalsIgnoreCase("all")) {
                    bankList.add(bank.toUpperCase());
                }

                if (!merchant.equalsIgnoreCase("all")) {
                    roleList.add(merchant.toUpperCase());
                }

            } else if (type_id.contains("[9]")) {

                userRoles = utility.getRoleParameters("[2000000000000049]", ucode);
                log.info("ucode: " + userRoles);

                if (!userRoles.isEmpty()) {
                    bankList = Pattern.compile("~").splitAsStream(userRoles).filter(f -> f.split(":")[0].equalsIgnoreCase("all") || f.split(":")[1].equalsIgnoreCase("ALL")).map(f -> f.split(":")[0]).distinct().collect(Collectors.toList());
                    roleList = Pattern.compile("~").splitAsStream(userRoles).filter(f -> !f.split(":")[0].equalsIgnoreCase("ALL") && !f.split(":")[1].equalsIgnoreCase("ALL")).map(f -> f.split(":")[1]).distinct().collect(Collectors.toList());
                    if (merchant.equalsIgnoreCase("ALL")) {

                    } else {
                        if (roleList.contains(merchant)) {
                            roleList = new ArrayList<>(roleList);
                            roleList.add(merchant);
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

        String sql = "SELECT a.unique_transid, b.merchant_name, a.account_number, a.settlement_ref, a.settle_amount, a.status, "
                + "a.attempts, a.date FROM ussd_db.mpay_settlement a left join ussd_db.etz_general_merchants b on a.merchant = b.payment_alias where 1=1 "
                + (service.equalsIgnoreCase("mpaysettlementsearch") ? " AND (a.unique_transid = :searchKey or a.settlement_ref =:searchKey) " : "  and a.date BETWEEN :start_dt and :end_dt ")
                + (reference.trim().isEmpty() ? "" : " and (a.settlement_ref = :reference or a.unique_transid = :reference ) ")
                + (!bankList.isEmpty() && roleList.isEmpty() ? "  and a.merchant in (select payment_alias from ussd_db.etz_general_merchants where 1=1 "
                + (!bankList.contains("ALL") ? " and a.bank in (:bank)) " : "") : "")
                + (!roleList.isEmpty() && bankList.isEmpty() ? " and b.payment_alias in(:vastype) " : "")
                + (!roleList.isEmpty() && !bankList.isEmpty() ? " and (a.merchant in (select payment_alias from ussd_db.etz_general_merchants where 1=1 "
                + (!bankList.contains("ALL") ? " and bank in (:bank) " : "") + ")" + " or a.merchant in (:vastype)) " : "")
                + (!status.equalsIgnoreCase("all") && !status.equals("") ? " and a.status = :status " : "")
                + " group by a.unique_transid order by a.date desc";

        Session session = DbHibernate.getSession("USSDDBMYSQL");
        try {
            Query q = session.createNativeQuery(sql);

            if (!service.equalsIgnoreCase("mpaysettlementsearch")) {
                q.setParameter("start_dt", (Object) start_dt)
                        .setParameter("end_dt", (Object) end_dt);
            } else {
                q.setParameter("searchKey", (Object) searchKey.trim());
            }
            if (!reference.trim().isEmpty()) {
                q.setParameter("reference", (Object) ("%" + reference.trim() + "%"));
            }

            if (!roleList.isEmpty() && !roleList.contains("ALL")) {
                q.setParameter("vastype", (Object) roleList);
            }
            if (!bankList.isEmpty() && !bankList.contains("ALL")) {
                q.setParameter("bank", (Object) bankList);
            }
            if (!str_source_frm.trim().isEmpty()) {
                q.setParameter("dest", "%" + (Object) str_source_frm.trim());
            }

            records = (List<Object[]>) q.getResultList();

        } catch (Exception et) {
            log.error("error", et);
        } finally {
            if (session != null) {
                session.close();
            }
        }

        for (final Object[] record : records) {
            final MerchantPaySettlement mp = new MerchantPaySettlement();
            try {

                mp.setUnique_transid(checkNull(record[0]));
                mp.setMerchant_name(checkNull(record[1]));
                mp.setAccount_number(checkNull(record[2]));
                mp.setSettlement_ref(checkNull(record[3]));
                mp.setSettle_amount(BigDecimal.valueOf(Double.valueOf(record[4].toString())));
                mp.setStatus(checkNull(record[5]));
                mp.setStatus_msg(translateStatus(checkNull(record[5])));
                mp.setAttempts(checkNull(record[6]));
                mp.setDate(checkNull(record[7]));
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }

            recordsList.add(mp);
        }

        return recordsList;
    }

    public List<MerchantPaySettlement> getGCBMerchantPaySettlementTrans(final String jsonString) {
        log.info("GCBMerchantPay trx request received >> " + jsonString);
        final Gson j = new Gson();
        final ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);

        final String start_dt = apiData.getStartDate();
        final String end_dt = apiData.getEndDate();
        String reference = apiData.getUniqueTransId();
        String merchant = apiData.getMerchant();
        String status = apiData.getStatus();
        final String source_frm = apiData.getFrom_source();
        String bank = apiData.getBank();
        final String ucode = apiData.getUserCode();
        String query = "";

        final String type_id = apiData.getType_id();

        final String service = apiData.getService();
        String searchKey = apiData.getSearchKey();
        List<MerchantPaySettlement> recordsList = new ArrayList<>();
        List<Object[]> records = new ArrayList<>();
        List<String> roleList = new ArrayList<>();
        List<String> bankList = new ArrayList<>();

        String userRoles = "";

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
        if (bank == null) {
            bank = "ALL";
        }
        switch (status.toLowerCase()) {
            case "successful":
                status = "0";
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

        if (!type_id.isEmpty()) {
            if (type_id.contains("[0]")) {

                if (!bank.equalsIgnoreCase("all")) {
                    bankList.add(bank.toUpperCase());
                }

                if (!merchant.equalsIgnoreCase("all")) {
                    roleList.add(merchant.toUpperCase());
                }

            } else if (type_id.contains("[9]")) {

                userRoles = utility.getRoleParameters("[2000000000000049]", ucode);
                log.info("ucode: " + userRoles);

                if (!userRoles.isEmpty()) {
                    bankList = Pattern.compile("~").splitAsStream(userRoles).filter(f -> f.split(":")[0].equalsIgnoreCase("all") || f.split(":")[1].equalsIgnoreCase("ALL")).map(f -> f.split(":")[0]).distinct().collect(Collectors.toList());
                    roleList = Pattern.compile("~").splitAsStream(userRoles).filter(f -> !f.split(":")[0].equalsIgnoreCase("ALL") && !f.split(":")[1].equalsIgnoreCase("ALL")).map(f -> f.split(":")[1]).distinct().collect(Collectors.toList());
                    if (merchant.equalsIgnoreCase("ALL")) {

                    } else {
                        if (roleList.contains(merchant)) {
                            roleList = new ArrayList<>(roleList);
                            roleList.add(merchant);
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

        String sql = "SELECT a.unique_transid, b.merchant_name, a.account_number, a.settlement_ref, a.settle_amount, a.status, "
                + "a.attempts, a.date FROM ussd_db.mpay_settlement a left join ussd_db.etz_general_merchants b on a.merchant = b.payment_alias where 1=1  "
                + (service.equalsIgnoreCase("mpaysettlementsearch") ? " AND (a.unique_transid = :searchKey or a.settlement_ref =:searchKey) " : "  and a.date BETWEEN :start_dt and :end_dt ")
                + (reference.trim().isEmpty() ? "" : " and (a.settlement_ref = :reference or a.unique_transid = :reference ) ")
                + (!bankList.isEmpty() && roleList.isEmpty() ? "  and a.merchant in (select payment_alias from ussd_db.etz_general_merchants where 1=1 "
                + (!bankList.contains("ALL") ? " and a.bank in (:bank)) " : "") : "")
                + (!roleList.isEmpty() && bankList.isEmpty() ? " and b.payment_alias in(:vastype) " : "")
                + (!roleList.isEmpty() && !bankList.isEmpty() ? " and (a.merchant in (select payment_alias from ussd_db.etz_general_merchants where 1=1 "
                + (!bankList.contains("ALL") ? " and bank in (:bank) " : "") + ")" + " or a.merchant in (:vastype)) " : "")
                + (!status.equalsIgnoreCase("all") && !status.equals("") ? " and a.status = :status " : "")
                + " group by a.unique_transid order by a.date desc";

        Session session = DbHibernate.getSession("USSDDBMYSQL");
        try {
            Query q = session.createNativeQuery(sql);

            if (!service.equalsIgnoreCase("mpaysettlementsearch")) {
                q.setParameter("start_dt", (Object) start_dt)
                        .setParameter("end_dt", (Object) end_dt);
            } else {
                q.setParameter("searchKey", (Object) searchKey.trim());
            }
            if (!reference.trim().isEmpty()) {
                q.setParameter("reference", (Object) ("%" + reference.trim() + "%"));
            }

            if (!roleList.isEmpty() && !roleList.contains("ALL")) {
                q.setParameter("vastype", (Object) roleList);
            }
            if (!bankList.isEmpty() && !bankList.contains("ALL")) {
                q.setParameter("bank", (Object) bankList);
            }
            if (!str_source_frm.trim().isEmpty()) {
                q.setParameter("dest", "%" + (Object) str_source_frm.trim());
            }

            if (!responseExclusionList.isEmpty()) {
                q.setParameter("mpay_exclusion_list", responseExclusionList);
            }

            {
                records = (List<Object[]>) q.getResultList();
            }

        } catch (Exception et) {
            log.error("error", et);
        } finally {
            if (session != null) {
                session.close();
            }
        }

        for (final Object[] record : records) {
            final MerchantPaySettlement mp = new MerchantPaySettlement();
            try {

                mp.setUnique_transid(checkNull(record[0]));
                mp.setMerchant_name(checkNull(record[1]));
                mp.setAccount_number(checkNull(record[2]));
                mp.setSettlement_ref(checkNull(record[3]));
                mp.setSettle_amount(BigDecimal.valueOf(Double.valueOf(record[4].toString())));
                mp.setStatus(checkNull(record[5]));
                mp.setStatus_msg(translateStatus(checkNull(record[5])));
                mp.setAttempts(checkNull(record[6]));
                mp.setDate(checkNull(record[7]));
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }

            recordsList.add(mp);
        }

        return recordsList;
    }

    public List<NODES> getMerchants(final String jsonString) {
        log.info("mpay merchants request received >> " + jsonString);
        final Gson j = new Gson();
        final ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);

        String mCode = apiData.getMerchant();
        String bank = apiData.getBank();
        String merchant = apiData.getMerchant();
        String type_id = apiData.getType_id();
        String ucode = apiData.getUserCode();
        String userRoles = "";

        List<String> bankList = new ArrayList<>();
        List<String> roleList = new ArrayList<>();

        List<NODES> recordsList = new ArrayList<>();
        String query = "";
        if (merchant == null) {
            merchant = "";
        }
        if (!merchant.equalsIgnoreCase("all")) {
            roleList.add(merchant.toUpperCase());
        }

        if (!type_id.isEmpty()) {
            if (type_id.contains("[0]")) {
                if (bank != null && !bank.equalsIgnoreCase("all")) {
                    bankList.add(bank.toUpperCase());
                }
                if (!merchant.equalsIgnoreCase("all")) {
                    if (!roleList.contains(merchant.toUpperCase())) {
                        roleList.add(merchant.toUpperCase());
                    }
                }

            } else if (type_id.contains("[9]")) {
                userRoles = utility.getRoleParameters("[2000000000000049]", ucode);
//                log.info("ucode: " + userRoles);
                if (!userRoles.isEmpty()) {
                    bankList = Pattern.compile("~").splitAsStream(userRoles).filter(f -> f.split(":")[0].equalsIgnoreCase("all") || f.split(":")[1].equalsIgnoreCase("ALL")).map(f -> f.split(":")[0]).distinct().collect(Collectors.toList());

                    roleList = Pattern.compile("~").splitAsStream(userRoles).filter(f -> !f.split(":")[0].equalsIgnoreCase("all") && !f.split(":")[1].equalsIgnoreCase("ALL")).map(f -> f.split(":")[1]).distinct().collect(Collectors.toList());

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

        try {

            System.out.println("MERCHANT::: " + merchant + " ::: " + !merchant.isEmpty() + " : " + roleList + " : " + bankList);
            if (!merchant.isEmpty() && !merchant.equalsIgnoreCase("all")) {
                query = "select a.report name, a.report id from ussd_db.mpay_report a  where a.vastype in (:merchant) and report !='' and report is not null  group by a.report asc";
            } else {
                query = "SELECT distinct(payment_alias) id, merchant_name name from ussd_db.etz_general_merchants where 1=1 and bank in('GCB','SCB','ETZ','BOA') "
                        + ((!bankList.isEmpty() && roleList.isEmpty()) ? " and bank in (:bank) " : "")
                        + ((!roleList.isEmpty() && bankList.isEmpty()) ? " and payment_alias in (:merchant) " : "")
                        + ((!roleList.isEmpty() && !bankList.isEmpty() && !merchant.equalsIgnoreCase("all")) ? " and (bank in (:bank) or payment_alias in (:merchant)) " : "")
                        + " group by payment_alias order by merchant_name asc ";
            }

            Query q = session.createNativeQuery(query, NODES.class);

            if (query.contains(":merchant")) {
                q.setParameter("merchant", (Object) roleList);
            }
            if (query.contains(":bank")) {
                q.setParameter("bank", (Object) bankList);
            }

            recordsList = q.getResultList();

        } catch (Exception ek) {
            log.error("error", ek);

        } finally {
            if (session != null) {
                session.close();
            }
        }

        return recordsList;
    }

    public List<NODES> getProducts(final String jsonString) {
        log.info("mpay merchant products request received >> " + jsonString);
        final Gson j = new Gson();
        final ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);

        String mCode = apiData.getMerchant();
        String bank = apiData.getBank();
        String merchant = apiData.getMerchant();
        String type_id = apiData.getType_id();
        String ucode = apiData.getUserCode();
        String userRoles = "";

        List<String> bankList = new ArrayList<>();
        List<String> roleList = new ArrayList<>();

        List<NODES> recordsList = new ArrayList<>();
        String query = "";
        if (merchant == null) {
            merchant = "";
        }
        if (!merchant.equalsIgnoreCase("all")) {
            roleList.add(merchant.toUpperCase());
        }

        if (!type_id.isEmpty()) {
            if (type_id.contains("[0]")) {
                if (bank != null && !bank.equalsIgnoreCase("all")) {
                    bankList.add(bank.toUpperCase());
                }
                if (!merchant.equalsIgnoreCase("all")) {
                    if (!roleList.contains(merchant.toUpperCase())) {
                        roleList.add(merchant.toUpperCase());
                    }
                }

            } else if (type_id.contains("[9]")) {
                userRoles = utility.getRoleParameters("[2000000000000049]", ucode);
                log.info("ucode: " + userRoles);
                if (!userRoles.isEmpty()) {

                    roleList = Pattern.compile("~").splitAsStream(userRoles).filter(f -> !f.split(":")[0].equalsIgnoreCase("all") && !f.split(":")[1].equalsIgnoreCase("ALL")).map(f -> f.split(":")[1]).distinct().collect(Collectors.toList());

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

        try {

            log.info("MERCHANT::: " + merchant + " ::: " + !merchant.isEmpty() + " : " + roleList);
            if (!roleList.isEmpty() && !roleList.contains("ALL")) {
                query = "select a.product name, a.product id from ussd_db.etz_mpay_report a  where a.payment_alias in (:merchant) and a.product !='' and a.product is not null group by a.product asc";

                Query q = session.createNativeQuery(query, NODES.class);

                if (query.contains(":merchant")) {
                    q.setParameter("merchant", (Object) roleList);
                }

                recordsList = q.getResultList();
            }

        } catch (Exception ek) {
            log.error(ek.getMessage(), ek);

        } finally {
            if (session != null) {
                session.close();
            }
        }

        return recordsList;
    }

    public List<NODES> getServiceCodes(final String jsonString) {
        log.info("mpay merchant service code request received >> " + jsonString);
        final Gson j = new Gson();
        final ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);

        String mCode = apiData.getMerchant();
        String bank = apiData.getBank();
        String merchant = apiData.getMerchant();
        String type_id = apiData.getType_id();
        String ucode = apiData.getUserCode();
        String userRoles = "";

        List<String> bankList = new ArrayList<>();
        List<String> roleList = new ArrayList<>();
        List<String> codeList = new ArrayList<>();

        List<NODES> recordsList = new ArrayList<>();
        String query = "";
        if (merchant == null) {
            merchant = "";
        }
        if (!merchant.equalsIgnoreCase("all")) {
            roleList.add(merchant.toUpperCase());
        }

        if (!type_id.isEmpty()) {
            if (type_id.contains("[0]")) {
                if (bank != null && !bank.equalsIgnoreCase("all")) {
                    bankList.add(bank.toUpperCase());
                }
                if (!merchant.equalsIgnoreCase("all")) {
                    if (!roleList.contains(merchant.toUpperCase())) {
                        roleList.add(merchant.toUpperCase());
                    }
                }

            } else if (type_id.contains("[9]")) {
                userRoles = utility.getRoleParameters("[2000000000000049]", ucode);

                if (!userRoles.isEmpty()) {

                    roleList = Pattern.compile("~").splitAsStream(userRoles).filter(f -> !f.split(":")[1].equalsIgnoreCase("ALL")).map(f -> f.split(":")[1]).distinct().collect(Collectors.toList());

                    codeList = Pattern.compile("~").splitAsStream(userRoles).filter(f -> f.split(":").length == 3).map(f -> f.split(":")[2]).distinct().collect(Collectors.toList());

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

        try {

            if (!roleList.isEmpty()) {
                query = "select b.customer_name name, b.customer_account id from ussd_db.etz_general_merchants a left join ussd_db.etz_general_merchants_customers b  on a.payment_alias = b.merchant_alias where a.payment_alias in (:merchant) and a.merchant_type = '2' "
                        + (!codeList.isEmpty() && !codeList.contains("ALL") ? " and b.customer_account = :account " : "")
                        + " order by b.customer_name asc ";

                Query q = session.createNativeQuery(query, NODES.class);

                if (query.contains(":merchant")) {
                    q.setParameter("merchant", (Object) roleList);
                }
                if (!codeList.isEmpty() && !codeList.contains("ALL")) {
                    q.setParameter("account", (Object) codeList);
                }

                recordsList = q.getResultList();
            }

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

    public String translateStatus(String status) {
        String message = "";

        switch (status) {
            case "0":
                message = "SUCCESSFUL";
                break;
            case "P":
                message = "PENDING";
                break;
            case "X":
                message = "FAILED";
                break;
            default:
                message = "N/A";
                break;
        }

        return message;

    }

}
