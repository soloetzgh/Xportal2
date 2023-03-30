package com.etzgh.xportal.dao;

import com.etzgh.xportal.model.Activity;
import com.etzgh.xportal.model.ApiPostData;
import com.etzgh.xportal.model.CocoaProfile;
import com.etzgh.xportal.model.NODES;
import com.etzgh.xportal.utility.DbHibernate;
import com.etzgh.xportal.utility.GeneralUtility;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class PbcDao {

    private static final Logger log = Logger.getLogger(PbcDao.class.getName());
    private static final GeneralUtility utility = new GeneralUtility();
    private static final Map<String, String> activityDescr = new HashMap<>();

    public static void main(String[] args) {

        String req = "{\"startDate\":\"2021-11-09 00:00\",\"endDate\":\"2021-11-09 23:59\",\"merchant\":\"ALL\",\"product\":\"ALL\",\"status\":\"ALL\",\"service\":\"transactions\",\"action\":\"\",\"apiSecret\":\"\",\"apiCode\":\"\",\"userCode\":\"[2500000000000055]|0244238465:HO\",\"admin\":\"\",\"type_desc\":\"\",\"role_id\":\"\",\"type_id\":\"[56]\",\"userName\":\"\",\"ClientId\":\"0244238465:HO\",\"user_id\":\"10500000000000635\",\"pageNumber\":1,\"rowsPerPage\":1,\"trans_code\":\"ALL\",\"transType\":\"\",\"bank_code\":\"005\",\"subscriberId\":\"\",\"trans_status\":\"\",\"roleList\":[],\"channel\":\"\",\"biller\":\"\",\"transId\":\"\",\"bankid\":\"\",\"from_source\":\"\",\"card_num\":\"\",\"optionType\":\"subordinate\",\"payType\":\"\",\"wcMerchant\":\"\",\"destination\":\"\",\"lineType\":\"ALL\",\"trans_channel\":\"\",\"pan\":\"\",\"terminal_id\":\"\",\"card_account\":\"\",\"cop_issuercode\":\"\",\"cop_role_id\":\"\",\"cop_company_id\":\"\",\"mobile_no\":\"\",\"phone\":\"\",\"type\":\"\",\"amount\":\"\",\"amount2\":\"\",\"refundStatus\":\"\",\"partialReversal\":\"\",\"reversalReason\":\"\",\"ipAddress\":\"\",\"userData\":\"\",\"appId\":[],\"company\":\"\",\"origin\":\"\",\"options\":{}}";
        log.info("LIST::: " + new PbcDao().getPBCTrans(req).size());
    }

    static {
        activityDescr.put("RGF", "Farmer Registration (RGF)");
        activityDescr.put("FPP", "Cocoa Purchase (FPP)");
//        activityDescr.put("ATR", "Airtime Purchase (ATR)");
        activityDescr.put("AFR", "Accept Fund Recall (AFR)");
//        activityDescr.put("PFM-MMO", "Cocoa Momo Payment (PFM-MMO)");
//        activityDescr.put("PFM-CCP", "Cocoa Pulse Payment (PFM-CCP)");
        activityDescr.put("RQF", "Fund Requests (RQF)");
        activityDescr.put("RQF", "Fund Requests (RQF)");
        activityDescr.put("WDC", "Withdraw Cash (RQF)");
        activityDescr.put("WDC-WBK", "Bank Withdrawal (WDC-WBK)");
//        activityDescr.put("WDC-WMM", "Momo Withdrawal (WDC-WMM)");
        activityDescr.put("UDL", "Update Delivery (UDL)");
        activityDescr.put("APP", "Authorize PC Purchase (APP)");
        activityDescr.put("RFD", "Recall Funds (RFD)");
        activityDescr.put("CAC", "Create Account (CAC)");
//        activityDescr.put("RGF", "Register Farmer (RGF)");

    }

    public List<Activity> getPBCTrans(final String jsonString) {
        log.info("PBC trx request received >> " + jsonString);
        final Gson j = new Gson();
        final ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);

        final String start_dt = apiData.getStartDate();
        final String end_dt = apiData.getEndDate();
        String reference = apiData.getUniqueTransId();

        String status = apiData.getStatus();
        String source_frm = apiData.getFrom_source();

        final String ucode = apiData.getUserCode();
        String query = "";
        String merchantCode = "";
        final String type_id = apiData.getType_id();
        String actCode = apiData.getProduct();
        final String service = apiData.getService();
        String searchKey = apiData.getSearchKey();
        String network = apiData.getLineType();
        String servicecode = apiData.getTrans_code();

        List<Activity> recordsList = new ArrayList<>();
        List<Object[]> records = new ArrayList<>();
        List<String> roleList = new ArrayList<>();
        List<String> bankList = new ArrayList<>();
        List<String> codeList = new ArrayList<>();

        String userRoles = "";

        final String dest = apiData.getSubscriberId();

        String str_source_frm = source_frm;
        String initCategory = "";

        String targetIdNo = apiData.getFrom_source();
        String rmIdNo = apiData.getMerchant();
        String doIdNo = apiData.getLineType();
        String pcIdNo = apiData.getTrans_code();
        String option = apiData.getOptionType();
        String clientId = apiData.getClientId();
        boolean subordinate = (option == null || !option.equalsIgnoreCase("initiator"));
        System.out.println("IS: " + subordinate);
        String initIdNo = "";

        String initRegion = "";
        String initDistrCode = "";
        String target = "";

        if (reference == null) {
            reference = "";
        }
        if (str_source_frm == null) {
            str_source_frm = "";
        }

        if (rmIdNo == null) {
            rmIdNo = "";
        }

        if (searchKey == null) {
            searchKey = "";
        }
        if (option == null) {
            option = "";
        }
        if (servicecode == null) {
            servicecode = "";
        }
        if (actCode == null) {
            actCode = "";
        }
        if (network == null) {
            network = "";
        }
        if (clientId == null) {
            clientId = "";
        }
        if (status == null || status.trim().isEmpty()) {
            status = "ALL";
        }

        if (service.equalsIgnoreCase("search") && searchKey.trim().isEmpty()) {
            return recordsList;
        }

        if (!type_id.contains("[0]")) {
            switch (clientId.split(":")[1]) {
                case "HO":
                case "DO":
                case "RM":
                case "PC":
                    target = clientId.replaceAll(":", "~");

                    break;
                default:
                    break;
            }
        }

        if (!rmIdNo.isEmpty() && !rmIdNo.equalsIgnoreCase("all")) {
            target = rmIdNo;
        }
        if (!doIdNo.isEmpty() && !doIdNo.equalsIgnoreCase("all")) {
            target = doIdNo;
        }
        if (!pcIdNo.isEmpty() && !pcIdNo.equalsIgnoreCase("all")) {
            target = pcIdNo;
        }

        if (!type_id.isEmpty()) {
            if (type_id.contains("[0]")) {

                if (!rmIdNo.equalsIgnoreCase("all") && !rmIdNo.trim().isEmpty()) {
                    roleList.add(rmIdNo.split("~")[0].toUpperCase());
                }
                if (!doIdNo.equalsIgnoreCase("all") && !doIdNo.trim().isEmpty()) {
                    roleList.add(doIdNo.split("~")[0].toUpperCase());
                }
                if (!pcIdNo.equalsIgnoreCase("all") && !pcIdNo.trim().isEmpty()) {
                    roleList.add(pcIdNo.split("~")[0].toUpperCase());
                }
                if (!(servicecode.trim().isEmpty() || servicecode.equalsIgnoreCase("ALL"))) {
                    codeList.add(servicecode.toUpperCase());
                }

            } else if (type_id.contains("[56]")) {

                userRoles = utility.getRoleParameters("[2500000000000055]", ucode);
                log.info("ucode: " + userRoles);

                if (!userRoles.isEmpty()) {

                    initCategory = target.split("~", -1)[1];
                    initIdNo = target.split("~", -1)[0];
                    if (!subordinate) {
                        System.out.println("TARGETR : " + target);
                        initCategory = target.split("~", -1)[1];
                        initIdNo = target.split("~", -1)[0];

                    }
                    if (rmIdNo.equalsIgnoreCase("ALL")) {

                    } else {
                        if (roleList.contains(rmIdNo)) {
                            roleList = new ArrayList<>(roleList);
                            roleList.add(rmIdNo);
                        }
                    }
                    if (servicecode.trim().isEmpty() || servicecode.equalsIgnoreCase("ALL")) {

                    } else {
                        if ((codeList.isEmpty() || codeList.contains("ALL")) || codeList.contains(servicecode)) {
                            codeList = new ArrayList<>(codeList);
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

        Session session = DbHibernate.getSession("PBCMYSQL");
        String distrCode = "";
        String societyCode = "";
        String region = "";
        try {
            Query q;
            List allroleList = new ArrayList<>();

            if (!target.isEmpty()) {
                String cqry = "SELECT distrCode, societyCode, region, names FROM telcodb.cocoa_profile where coy='pbc' and idNo = :idNo";
                q = session.createNativeQuery(cqry).setParameter("idNo", target.split("~")[0]);
                List<Object[]> rec = (List<Object[]>) q.getResultList();

                for (final Object[] record : rec) {
                    System.out.println("NAME: " + record[3]);
                    distrCode = checkNull(record[0]);
                    societyCode = checkNull(record[1]);
                    region = checkNull(record[2]);
                }
            }

            String qry = "SELECT a.activityCode, a.created, a.amount, a.xtrainfo2, a.targetIdno,a.targetName, a.initIdNo, a.initName, a.initDistrict, a.activityStatus, a.activityStatusMsg, a.activityRef, a.commentNote "
                    + " FROM telcodb.cocoa_activity a "
                    + " left join telcodb.cocoa_profile b on b.idno = a.initIdNo "
                    + " where 1=1 and a.coy ='PBC' "
                    + (status.equals("ALL") ? "" : (status.equals("SUCCESSFUL") ? " and a.activityStatus in ('0','00') " : " and a.activityStatus not in('0','00') "));

            if (subordinate) {
                System.out.println("CATEG: " + initCategory + " - " + distrCode + " - " + societyCode + " - " + region);
                qry += (initCategory.equalsIgnoreCase("RM") ? " and b.category not in ('HO') " : "")
                        + (initCategory.equalsIgnoreCase("RM") ? " and b.region = :region and a.initCatgry not in ('RM')" : "")
                        + (initCategory.equalsIgnoreCase("DO") ? " and b.distrCode = :distrCode and a.initCatgry not in ('DO') " : "")
                        + (initCategory.equalsIgnoreCase("PC") ? " and b.societyCode = :societyCode and a.initCatgry = 'FM' " : "");

            } else if (target.isEmpty()) {
            } else {
                qry += " and a.initIdNo = :initIdNo ";
            }

            String qry2 = "";

            qry += qry2;

            qry += (!(start_dt.trim().isEmpty() || end_dt.trim().isEmpty()) ? " and a.created between :startDate and :endDate " : " and a.activityRef = :activityRef ")
                    + (!(actCode.trim().isEmpty() || actCode.equalsIgnoreCase("ALL")) ? " and a.activityCode in(:actCode) " : "")
                    + " and a.activitycode not in('ATR','PFM-MMO','PFM-CCP','WDC-WMM','VBL','VPP','VFR') order by a.created desc ";

            q = session.createNativeQuery(qry);

//            log.info("qry: " + qry);
            if (!(start_dt.trim().isEmpty() || end_dt.trim().isEmpty())) {
                q.setParameter("startDate", start_dt);
                q.setParameter("endDate", end_dt);
            } else {
                q.setParameter("activityRef", searchKey.trim());
            }
            if (subordinate) {

            } else if (target.isEmpty()) {
            } else {
                System.out.println("TARGET : " + target);
                q.setParameter("initIdNo", target.split("~")[0]);
            }

            if (!(actCode.trim().isEmpty() || actCode.equalsIgnoreCase("ALL"))) {
                q.setParameter("actCode", actCode);
            }

            if (qry.contains(":pcIdNo")) {
                q.setParameter("pcIdNo", pcIdNo.split("~")[0]);

            }
            if (qry.contains(":doIdNo")) {
                q.setParameter("doIdNo", doIdNo.split("~")[0]);
            }
            if (qry.contains(":rmIdNo")) {
                q.setParameter("rmIdNo", rmIdNo.split("~")[0]);
            }

            if (qry.contains(":region")) {
                q.setParameter("region", region);

            }
            if (qry.contains(":societyCode")) {
                q.setParameter("societyCode", societyCode);
            }
            if (qry.contains(":distrCode")) {
                q.setParameter("distrCode", distrCode);
            }
            if (qry.contains(":tidNo")) {
                q.setParameter("tidNo", initIdNo);
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
            final Activity act = new Activity();
            try {
                act.setActivityReference(checkNull(record[11]));
                act.setActivity(activityDescr.getOrDefault(checkNull(record[0]), checkNull(record[0])));
                act.setInitIdNo(checkNull(record[6]));
                act.setInitName(checkNull(record[7]));
                act.setInitDistrict(checkNull(record[8]));
                act.setAmount(Double.valueOf(checkNull(record[2])));
                act.setTargetIdNo(checkNull(record[4]));
                act.setTargetName(checkNull(record[5]));
                act.setCommentNote(checkNull(record[5]));

                act.setStatus(checkNull(record[9]));
                act.setStatusMsg(checkNull(record[10]));
                act.setExtraInfo(checkNull(record[3]));
                act.setTransactionDate(checkNull(record[1]));

            } catch (Exception er) {
                log.error(er.getMessage(), er);
            }

            recordsList.add(act);
        }

        return recordsList;
    }

    public List<NODES> getPBCProfiles(final String jsonString) {
        log.info("pbc profiles request received >> " + jsonString);
        final Gson j = new Gson();
        final ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);

        String mCode = apiData.getMerchant();
        String idNo = apiData.getFrom_source();
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

                if (!merchant.equalsIgnoreCase("all")) {
                    if (!roleList.contains(merchant.toUpperCase())) {
                        roleList.add(merchant.toUpperCase());
                    }
                }

            } else if (type_id.contains("[56]")) {
                userRoles = utility.getRoleParameters("[2500000000000055]", ucode);
                log.info("ucode: " + userRoles);
                if (!userRoles.isEmpty()) {
                    roleList = Pattern.compile("~").splitAsStream(userRoles).filter(f -> f.split(":")[1].equalsIgnoreCase("ALL")).map(f -> f.split(":")[0]).distinct().collect(Collectors.toList());

                } else {
                    return recordsList;
                }
            } else {
                return recordsList;
            }
        } else {
            return recordsList;
        }

        Session session = DbHibernate.getSession("PBCMYSQL");

        try {

            String qry = "select concat_ws('~',idNo , category) id, CONCAT(names, ' - ', idno) name from telcodb.cocoa_profile where 1=1 and coy ='PBC' "
                    + (merchant.isEmpty() ? "" : " and category in (:category) ")
                    + (idNo.isEmpty() ? "" : " and prySuperId = :idNo ")
                    + " order by name asc;";

            log.info("query: " + qry);
            Query q = session.createNativeQuery(qry, NODES.class);

            if (!merchant.isEmpty()) {
                q.setParameter("category", (Object) merchant);
            }
            if (!idNo.isEmpty()) {
                q.setParameter("idNo", (Object) idNo.split("~")[0]);
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

    public List<NODES> getActivityTypes(final String jsonString) {
        log.info("pbc activity request received >> " + jsonString);
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

            } else if (type_id.contains("[56]")) {
                userRoles = utility.getRoleParameters("[2500000000000055]", ucode);
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

        try {

            for (Map.Entry<String, String> entry : activityDescr.entrySet()) {

                NODES act = new NODES();
                act.setId(entry.getKey());
                act.setName(entry.getValue());
                recordsList.add(act);
            }
        } catch (Exception ek) {
            log.error(ek.getMessage(), ek);

        }

        return recordsList;
    }

    public List<CocoaProfile> getDetailedProfiles(String jsonString) {

        log.info("pbc details view request received >> " + jsonString);
        Gson j = new Gson();
        ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);
        List<CocoaProfile> recordsList = new ArrayList<>();

        String card_num = apiData.getCard_num();
        String source_frm = apiData.getFrom_source();
        String ucode = apiData.getUserCode();
        String type_id = apiData.getType_id();
        String query = "";
        log.info("Card Num -> " + card_num);
        log.info("type -> " + type_id);
        String userRoles = "";

        List<String> cardList = new ArrayList<>();

        if (source_frm == null) {
            source_frm = "";
        }
        if (card_num == null) {
            card_num = "";
        }

        if (!type_id.isEmpty()) {

            if (type_id.contains("[0]")) {

            } else if (type_id.contains("[68]")) {
                userRoles = utility.getRoleParameters("[2500000000000060]", ucode);

                if (!source_frm.trim().isEmpty()) {
                    cardList.add(source_frm.trim());
                }
            } else {

                return recordsList;
            }
        } else {
//            log.info("r");
            return recordsList;
        }
        Map<String, String> balances = new HashMap<>();

        Session session = DbHibernate.getSession("PBCMYSQL");
        List<NODES> n = new ArrayList<>();
        try {

            String qry = "select a.category, a.idNo, a.msisdn, a.names, '0.00'  onlineBalance, b.names primarySuperior, c.names secondarySuperior, \n"
                    + "a.distrName districtName, a.societyName, a.region, a.sex, a.maxCreditLimit, a.maxWthdrLimit maxWithdrawalLimit,\n"
                    + " (case when a.activeStatus = 1 then 'YES' else 'NO' end) activeStatus, a.created from telcodb.cocoa_profile a left join telcodb.cocoa_profile b \n"
                    + " on a.prySuperId = b.idNo left join telcodb.cocoa_profile c on a.secSuperId = c.idNo where 1 = 1 "
                    + (!cardList.isEmpty() ? " and a.idNo = :phone " : "");
            Query qy = session.createNativeQuery(qry, CocoaProfile.class);
            if (!cardList.isEmpty()) {
                qy.setParameter("phone", cardList);
            }
            recordsList = qy.getResultList();

        } catch (Exception r) {
            log.error(r.getMessage(), r);
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
        if (!recordsList.isEmpty()) {

            List<String> numbers = recordsList.parallelStream().map(f -> f.getIdNo()).collect(Collectors.toList());
            Session session2 = DbHibernate.getSession("40.17MYSQL");
//            log.info("Numbers: " + numbers);
            try {
                String qry = "select card_account id, ifnull(online_Balance,0.00) name from ecarddb.e_cardholder where card_account in(:numbers) and card_num like '700920%'";
                Query qe = session2.createNativeQuery(qry, NODES.class).setParameter("numbers", numbers);

                n = qe.getResultList();

                balances = n.parallelStream().collect(Collectors.toMap(s -> s.getId(), s -> s.getName()));
//                System.out.println("Balances: " + balances);
            } catch (Exception er) {
                log.error(er.getMessage(), er);
            } finally {
                if (session2 != null) {
                    try {
                        session2.close();
                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                    }
                }

            }
            final Map<String, String> finalBalances = balances;
//            System.out.println("FINAL: " + finalBalances);
            recordsList.parallelStream().forEach(f -> {
                f.setOnlineBalance(Double.valueOf(finalBalances.getOrDefault(f.getIdNo(), "0.00")));
            });

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
