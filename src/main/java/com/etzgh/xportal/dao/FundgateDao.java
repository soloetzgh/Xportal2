package com.etzgh.xportal.dao;

import com.etzgh.xportal.model.ApiPostData;
import com.etzgh.xportal.model.FundGateMerchant;
import com.etzgh.xportal.model.FundGateReport;
import com.etzgh.xportal.model.FundGateTransaction;
import com.etzgh.xportal.model.FundgateBalance;
import com.etzgh.xportal.model.FundgateResponse;
import com.etzgh.xportal.model.MomoUpdate;
import com.etzgh.xportal.model.NODES;
import com.etzgh.xportal.utility.DbHibernate;
import com.etzgh.xportal.utility.GeneralUtility;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.joining;
import javax.persistence.NoResultException;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class FundgateDao {

    /* private static final List<Object[]> terminalrecords; */
    private static final GeneralUtility utility = new GeneralUtility();
    private static final Gson gson = new Gson();
    private static final Map<String, String> productMap = new HashMap<>();
    private static final Logger log = Logger.getLogger(FundgateDao.class.getName());

    private static final List<FundGateMerchant> merchants = new ArrayList<>();

    public FundgateDao() {

    }

    static {
        productMap.put("V3.1_MMD", "Momo Debit");
        productMap.put("FT", "Funds Transfer");
        productMap.put("VT", "Airtime");
        productMap.put("PB", "Bill Payment");

    }

    public static void main(String[] args) {
        String request = "{\"service\":\"balance\",\"action\":\"\",\"apiSecret\":\"\",\"apiCode\":\"\",\"userCode\":\"[17]|0060000244:0067510000010000,[2500000000000056]|2,[2500000000000058]|2\",\"admin\":\"\",\"type_desc\":\"\",\"role_id\":\"\",\"type_id\":\"[2],[21],[57],[58],[59],[60],[61]\",\"userName\":\"\",\"user_id\":\"10500000000000635\",\"pageNumber\":1,\"rowsPerPage\":1,\"trans_code\":\"\",\"transType\":\"\",\"bank_code\":\"021\",\"subscriberId\":\"\",\"roleList\":[],\"channel\":\"\",\"bank\":\"\",\"biller\":\"\",\"transId\":\"\",\"bankid\":\"\",\"from_source\":\"\",\"card_num\":\"\",\"payType\":\"\",\"wcMerchant\":\"\",\"destination\":\"\",\"lineType\":\"\",\"trans_channel\":\"\",\"pan\":\"\",\"terminal_id\":\"\",\"card_account\":\"\",\"cop_issuercode\":\"\",\"cop_role_id\":\"\",\"cop_company_id\":\"\",\"mobile_no\":\"\",\"phone\":\"\",\"type\":\"\",\"amount\":\"\",\"amount2\":\"\",\"refundStatus\":\"\",\"partialReversal\":\"\",\"reversalReason\":\"\",\"ipAddress\":\"\",\"userData\":\"\",\"appId\":[],\"company\":\"\",\"origin\":\"\",\"options\":{}}";
        log.error("Response: {0}" + new FundgateDao().getBalance(request));
    }

    public List<FundGateTransaction> getFundGateTransactions(final String jsonString) {
        log.error("fundgate trx request received >> {0}" + jsonString);
        final Gson j = new Gson();
        final ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);

        final String start_dt = apiData.getStartDate();
        final String end_dt = apiData.getEndDate();
        String mCode = apiData.getMerchant();
        String product = apiData.getProduct();
        String st = apiData.getStatus();
        String trans_status = apiData.getTrans_status();
        String accountNo = apiData.getAccountNumber();
        final String option = apiData.getOptionType();
        String type_id = apiData.getType_id();
        String ucode = apiData.getUserCode();
        String searchKey = apiData.getSearchKey();
        String service = apiData.getService();
        String lineType = apiData.getLineType();

        String query = "";
        String userRoles = "";
        List<FundGateTransaction> recordsList = new ArrayList<>();
        List<Object[]> records = new ArrayList<>();
        List<String> roleList = new ArrayList<>();

        if (mCode == null || mCode.isEmpty()) {
            mCode = "ALL";
        }
        if (searchKey == null) {
            searchKey = "";
        }
        if (accountNo == null) {
            accountNo = "";
        }
        if (st == null) {
            st = "ALL";
        }
        if (trans_status == null) {
            trans_status = "ALL";
        }
        if (product == null || product.isEmpty()) {
            product = "ALL";
        }
        if (lineType == null || lineType.isEmpty()) {
            lineType = "ALL";
        }

        if (service.equalsIgnoreCase("search") && searchKey.trim().isEmpty()) {
            return recordsList;
        }

        if (!type_id.isEmpty()) {
            mCode = mCode.split(":", -1)[0];
            if (type_id.contains("[0]")) {
                roleList.add(mCode.toUpperCase());
            } else if (type_id.contains("[2]")) {
                userRoles = utility.getRoleParameters("[17]", ucode);
                log.info("ucode: {0}" + userRoles);
                if (!userRoles.isEmpty()) {
                    roleList = Pattern.compile("~").splitAsStream(userRoles).map(f -> f.split(":")[0])
                            .collect(Collectors.toList());
                    if (mCode.equalsIgnoreCase("ALL")) {

                    } else {
                        if (roleList.contains(mCode)) {
                            roleList = new ArrayList<>();
                            roleList.add(mCode);
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

        query = "SELECT DISTINCT (B.ETZREF), A.DESTINATION, A.CLIENTREF, A.AMOUNT,  B.RESPMESSAGE, B.CREATED AS CREATED2, A.LINETYPE ,  B.ACTION, C.MERCHANT_NAME TERMINAL, ifnull(A.SENDERNAME,''), ifnull(B.RESPCODE,'null')   "
                + (option.equals("new") ? " FROM fgdb.FUNDGATE_REQUEST A inner join fgdb.FUNDGATE_RESPONSE B "
                : " FROM fgdb.FUNDGATE_REQUEST_OLD A inner join fgdb.FUNDGATE_RESPONSE_OLD B ")
                + " on (a.terminal = b.terminal and a.clientref = b.clientref and a.action=b.action) left join fgdb.cop_fundgate_info c on a.terminal = c.terminal_id  where 1=1 "
                + (service.equalsIgnoreCase("search")
                ? (searchKey.trim().toLowerCase().startsWith("09fg") ? " AND  B.ETZREF = :reference "
                : " and A.CLIENTREF = :reference ")
                : "  and b.CREATED BETWEEN :start_dt and :end_dt ")
                + (!accountNo.isEmpty() ? " AND A.DESTINATION LIKE :accountNo " : "")
                + (!roleList.contains("ALL") ? " AND A.TERMINAL in (:mCode) " : "")
                + (st.equalsIgnoreCase("FAILED") ? " AND B.RESPCODE  in ('06') " : "")
                + (st.equalsIgnoreCase("PENDING") ? " AND B.RESPCODE = '31' " : "")
                + (st.equalsIgnoreCase("SUCCESSFUL") ? " AND B.RESPCODE = '0' " : "")
                + (product.equalsIgnoreCase("ALL") ? "" : " AND B.ACTION = :product ")
                + (lineType.equalsIgnoreCase("ALL") ? "" : " AND A.LINETYPE = :lineType ")
                + " AND A.AMOUNT <> 0.0 "
                + " AND B.ACTION not in ('BE','TS')  ORDER BY B.CREATED DESC ";
        Session session = DbHibernate.getSession("40.9MYSQL");
        try {
            log.info(query);
            Query q = session.createNativeQuery(query);

            if (!service.equalsIgnoreCase("search")) {
                q.setParameter("start_dt", (Object) start_dt)
                        .setParameter("end_dt", (Object) end_dt);
            } else {
                q.setParameter("reference", (Object) searchKey.trim());
            }
            if (!accountNo.trim().isEmpty()) {
                q.setParameter("accountNo", (Object) ("%" + accountNo.trim() + "%"));
            }
            if (!roleList.contains("ALL")) {
                q.setParameter("mCode", roleList);
            }
            if (!product.equalsIgnoreCase("ALL")) {
                q.setParameter("product", (Object) product);
            }
            if (!lineType.equalsIgnoreCase("ALL")) {
                q.setParameter("lineType", (Object) lineType);
            }

            records = (List<Object[]>) q.getResultList();

        } catch (Exception et) {
            log.error(et.getMessage(), et);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        List<String> sql_in = new ArrayList<>();
        List<String> sql_in2 = new ArrayList<>();
        List<String> sql_in3 = new ArrayList<>();
        List<String> sql_in4 = new ArrayList<>();

        for (final Object[] record : records) {
            final FundGateTransaction fg = new FundGateTransaction();
            try {
                fg.setEtzRef(checkNull(record[0]));
                fg.setDestination(checkNull(record[1]));
                fg.setClientRef(checkNull(record[2]));
                fg.setAmount(BigDecimal.valueOf(checkNullNumber(record[3])));
                fg.setRespMessage(checkNull(record[4]).replaceAll("(?i)gip#", ""));
                fg.setCreated(checkNull(record[5]));
                fg.setLineType(checkNull(record[6]));
                String act = checkNull(record[7]);
                fg.setAction(checkNull(record[7]));
                fg.setTerminal(checkNull(record[8]));
                fg.setSenderName(checkNull(record[9]));
                fg.setBeneficiaryName(checkNull(record[9]));
                fg.setRespCode(checkNull(record[10]));
                fg.setTransId("");
                sql_in.add("R" + fg.getEtzRef());
                if (fg.getAction().trim().equalsIgnoreCase("FT") || fg.getAction().trim().equalsIgnoreCase("V3.1_MMD")) {
                    sql_in2.add(fg.getEtzRef());
                } else if (fg.getAction().trim().equalsIgnoreCase("VT")) {
                    sql_in3.add(fg.getEtzRef());
                } else if (fg.getAction().trim().equalsIgnoreCase("PB")) {
                    sql_in4.add(fg.getEtzRef());
                }
            } catch (Exception y) {

            }

            recordsList.add(fg);
        }

//        log.info("SIZE: " + recordsList.size() + " -- " + recordsList.toString());
        HashMap<String, String> etransResp = getEtransaction(sql_in);

        HashMap<String, String> momoResp = getMomo(sql_in2, trans_status);

        HashMap<String, String> airtimeResp = getAirtime(sql_in3, trans_status);

        HashMap<String, String> billResp = getBill(sql_in4, trans_status);

        String billerResponse = "";

        switch (trans_status.toLowerCase()) {
            case "successful":
                billerResponse = "00";
                break;
            case "failed":
                billerResponse = "06";
                break;
            case "pending":
                billerResponse = "pending";
                break;
            default:
                break;
        }

        Iterator<FundGateTransaction> fd = recordsList.iterator();
        while (fd.hasNext()) {
            FundGateTransaction f = fd.next();

            try {
                if (f.getAction().equalsIgnoreCase("VT")) {
                    String resp = airtimeResp.getOrDefault(f.getEtzRef(), "");
                    f.setBillerRespCode(checkNull(resp.split("~").length > 0 ? resp.split("~")[0] : ""));
                    f.setBillerRespMessage(checkNull(resp.split("~").length > 1 ? resp.split("~")[1] : ""));
                }
                if (f.getAction().equalsIgnoreCase("PB")) {
                    String resp = billResp.getOrDefault(f.getEtzRef(), "");
                    f.setBillerRespCode(checkNull(resp.split("~").length > 0 ? resp.split("~")[0] : ""));
                    f.setBillerRespMessage(checkNull(resp.split("~").length > 1 ? resp.split("~")[1] : ""));
                }
                if (f.getAction().trim().equalsIgnoreCase("FT") || f.getAction().trim().equalsIgnoreCase("V3.1_MMD")) {
                    f.setLineType(getBiller(f.getLineType()));
                    String resp = momoResp.getOrDefault(f.getEtzRef(), "");
                    if (!resp.isEmpty()) {

                        f.setBillerRespCode(checkNull(resp.split("~").length > 0 ? resp.split("~")[0] : ""));
                        f.setBillerRespMessage(checkNull(resp.split("~").length > 1 ? resp.split("~")[1] : "")
                                .replaceAll("(?i)gip#", ""));
                        f.setTransId(checkNull(resp.split("~").length > 2 ? resp.split("~")[2] : "")
                                .replaceAll("(?i)gip#", ""));
                        if (f.getAction().trim().equalsIgnoreCase("FT")) {
                            f.setAmount(BigDecimal
                                    .valueOf(checkNullNumber(resp.split("~").length > 3 ? resp.split("~")[3] : "0.0")));
                        }
                    }
                }
                f.setBillerTransId(f.getTransId());

                f.setAction(productMap.getOrDefault(f.getAction(), f.getAction()));

                f.setReversal(etransResp.containsKey("R" + f.getEtzRef()) ? "R" : "");

//                log.info("SIZE: " + recordsList.size() + " -- " + recordsList.toString() + " -- " + billerResponse);
                if (billerResponse.isEmpty()) {
                } else if (billerResponse.equalsIgnoreCase("pending")) {
                    if (!((f.getRespCode() != null && !f.getRespCode().equals("06"))
                            && (f.getBillerRespCode() == null || (f.getBillerRespCode().equals("05")
                            || f.getBillerRespCode().equals("58") || f.getBillerRespCode().equals("66"))))) {
                        fd.remove();
                    }
                } else if (!(f.getBillerRespCode() != null && f.getBillerRespCode().equals(billerResponse))) {
                    fd.remove();
                }
            } catch (Exception et) {
                log.error("Fundgate error", et);
            }
        }

//        recordsList.forEach(f -> {
//            try {
//                if (f.getAction().equalsIgnoreCase("VT")) {
//                    String resp = airtimeResp.getOrDefault(f.getEtzRef(), "");
//                    f.setBillerRespCode(checkNull(resp.split("~").length > 0 ? resp.split("~")[0] : ""));
//                    f.setBillerRespMessage(checkNull(resp.split("~").length > 1 ? resp.split("~")[1] : ""));
//                }
//                if (f.getAction().equalsIgnoreCase("PB")) {
//                    String resp = billResp.getOrDefault(f.getEtzRef(), "");
//                    f.setBillerRespCode(checkNull(resp.split("~").length > 0 ? resp.split("~")[0] : ""));
//                    f.setBillerRespMessage(checkNull(resp.split("~").length > 1 ? resp.split("~")[1] : ""));
//                }
//                if (f.getAction().trim().equalsIgnoreCase("FT") || f.getAction().trim().equalsIgnoreCase("V3.1_MMD")) {
//                    f.setLineType(getBiller(f.getLineType()));
//                    String resp = momoResp.getOrDefault(f.getEtzRef(), "");
//                    if (!resp.isEmpty()) {
//
//                        f.setBillerRespCode(checkNull(resp.split("~").length > 0 ? resp.split("~")[0] : ""));
//                        f.setBillerRespMessage(checkNull(resp.split("~").length > 1 ? resp.split("~")[1] : "")
//                                .replaceAll("(?i)gip#", ""));
//                        f.setTransId(checkNull(resp.split("~").length > 2 ? resp.split("~")[2] : "")
//                                .replaceAll("(?i)gip#", ""));
//                        if (f.getAction().trim().equalsIgnoreCase("FT")) {
//                            f.setAmount(BigDecimal
//                                    .valueOf(checkNullNumber(resp.split("~").length > 3 ? resp.split("~")[3] : "0.0")));
//                        }
//                    }
//                }
//                f.setReversal(etransResp.containsKey("R" + f.getEtzRef()) ? "R" : "");
//            } catch (Exception et) {
//                log.error("Fundgate error", et);
//            }
//        });
//        if (!billerResponse.isEmpty()) {
//            final String r = billerResponse;
//
//            recordsList = recordsList.parallelStream().filter(f -> (r.equalsIgnoreCase("pending")
//                    ? ((f.getRespCode() != null && !f.getRespCode().equals("06"))
//                    && (f.getBillerRespCode() == null || (f.getBillerRespCode().equals("05")
//                    || f.getBillerRespCode().equals("58") || f.getBillerRespCode().equals("66"))))
//                    : (f.getBillerRespCode() != null && f.getBillerRespCode().equals(r)))).collect(Collectors.toList());
//
//        }
        return recordsList;
    }

    public List<FundGateTransaction> getFundGateInvestigation(final String jsonString) {
        log.error("fundgate investigation trx request received >> {0}" + jsonString);
        final Gson j = new Gson();
        final ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);

        final String start_dt = apiData.getStartDate();
        final String end_dt = apiData.getEndDate();
        final String ucode = apiData.getUserCode();
        final String type_id = apiData.getType_id();

        String userRoles = "";
        String mCode = "";

        final String cardScheme = apiData.getCardSchemeNumbers();
        String query = "";
        List<FundGateTransaction> recordsList = new ArrayList<>();

        if (!type_id.isEmpty()) {
            if (type_id.contains("[0]")) {
            } else if (type_id.contains("[2]")) {
                userRoles = utility.getRoleParameters("[17]", ucode);

                if (userRoles.contains("~")) {
                    String[] arr = userRoles.split("~");
                    userRoles = Arrays.stream(arr).collect(Collectors.joining("','"));
                }

            } else {
                return recordsList;
            }
        } else {
            return recordsList;
        }

        Session session = DbHibernate.getSession("40.9MYSQL");
        List<Object[]> records = new ArrayList<>();
        List<String> sql_in = new ArrayList<>();
        List<String> sql_in2 = new ArrayList<>();
        List<String> sql_in3 = new ArrayList<>();
        List<String> sql_in4 = new ArrayList<>();
        try {
            try {
                query = "SELECT DISTINCT(B.ETZREF), A.DESTINATION, A.CLIENTREF, A.AMOUNT,  B.RESPMESSAGE, B.CREATED AS CREATED2, A.LINETYPE ,  B.ACTION, C.MERCHANT_NAME TERMINAL, ifnull(A.SENDERNAME,''), ifnull(B.RESPCODE,'null')   FROM fgdb.FUNDGATE_REQUEST A inner join fgdb.FUNDGATE_RESPONSE B on a.terminal = b.terminal and a.clientref = b.clientref and a.action=b.action) left join fgdb.cop_fundgate_info c on a.terminal = c.terminal_id  where B.ACTION = 'FT' and B.RESPMESSAGE LIKE '%-1::%' OR B.RESPMESSAGE LIKE '%31::%' OR B.RESPMESSAGE LIKE '%99::%' and  (b.CREATED BETWEEN :start_dt and :end_dt) ORDER BY B.CREATED DESC";

                Query q = session.createNativeQuery(query);
                q.setParameter("start_dt", (Object) start_dt);
                q.setParameter("end_dt", (Object) end_dt);

                records = (List<Object[]>) q.getResultList();
            } catch (Exception ey) {
                log.error(ey.getMessage(), ey);
            } finally {
                if (session != null) {
                    session.close();
                }

            }
            for (final Object[] record : records) {
                final FundGateTransaction fg = new FundGateTransaction();
                fg.setEtzRef(checkNull(record[0]));
                fg.setDestination(checkNull(record[1]));
                fg.setClientRef(checkNull(record[2]));
                fg.setAmount(BigDecimal.valueOf(Double.valueOf(record[3].toString())));
                fg.setRespMessage(checkNull(record[4]).replaceAll("(?i)gip#", ""));
                fg.setCreated(checkNull(record[5]));
                fg.setLineType(checkNull(record[6]));
                fg.setAction(checkNull(record[7]));
                fg.setTerminal(checkNull(record[8]));
                fg.setSenderName(checkNull(record[9]));
                fg.setBeneficiaryName(checkNull(record[9]));
                fg.setRespCode(checkNull(record[10]));
                fg.setTransId("");
                sql_in.add("R" + fg.getEtzRef());
                if (fg.getAction().trim().equalsIgnoreCase("FT") || fg.getAction().trim().equalsIgnoreCase("V3.1_MMD")) {
                    sql_in2.add(fg.getEtzRef());
                } else if (fg.getAction().trim().equalsIgnoreCase("VT")) {
                    sql_in3.add(fg.getEtzRef());
                } else if (fg.getAction().trim().equalsIgnoreCase("PB")) {
                    sql_in4.add(fg.getEtzRef());
                }

                recordsList.add(fg);
            }

            HashMap<String, String> etransResp = getEtransaction(sql_in);

            HashMap<String, String> momoResp = getMomo(sql_in2, "");

            HashMap<String, String> airtimeResp = getAirtime(sql_in3, "");

            HashMap<String, String> billResp = getBill(sql_in4, "");

            recordsList.forEach(f -> {
                try {
                    if (f.getAction().equalsIgnoreCase("VT")) {
                        String resp = airtimeResp.getOrDefault(f.getEtzRef(), "");
                        f.setBillerRespCode(checkNull(resp.split("~").length > 0 ? resp.split("~")[0] : ""));
                        f.setBillerRespMessage(checkNull(resp.split("~").length > 1 ? resp.split("~")[1] : ""));
                    }
                    if (f.getAction().equalsIgnoreCase("PB")) {
                        String resp = billResp.getOrDefault(f.getEtzRef(), "");
                        f.setBillerRespCode(checkNull(resp.split("~").length > 0 ? resp.split("~")[0] : ""));
                        f.setBillerRespMessage(checkNull(resp.split("~").length > 1 ? resp.split("~")[1] : ""));
                    }
                    if (f.getAction().trim().equalsIgnoreCase("FT")
                            || f.getAction().trim().equalsIgnoreCase("V3.1_MMD")) {
                        f.setLineType(getBiller(f.getLineType()));
                        String resp = momoResp.getOrDefault(f.getEtzRef(), "");
                        if (!resp.isEmpty()) {

                            f.setBillerRespCode(checkNull(resp.split("~").length > 0 ? resp.split("~")[0] : ""));
                            f.setBillerRespMessage(checkNull(resp.split("~").length > 1 ? resp.split("~")[1] : "")
                                    .replaceAll("(?i)gip#", ""));
                            f.setTransId(checkNull(resp.split("~").length > 2 ? resp.split("~")[2] : "")
                                    .replaceAll("(?i)gip#", ""));
                            if (f.getAction().trim().equalsIgnoreCase("FT")) {
                                f.setAmount(BigDecimal.valueOf(
                                        checkNullNumber(resp.split("~").length > 3 ? resp.split("~")[3] : "0.0")));
                            }
                        }
                    }
                    f.setReversal(etransResp.containsKey("R" + f.getEtzRef()) ? "R" : "");
                } catch (Exception et) {

                }
            });
        } catch (Exception er) {
            log.error(er.getMessage(), er);
        }
        log.error("Records: {0}" + recordsList);
        return recordsList;
    }

    public String getBalance(final String jsonString) {
        log.error("fundgate balance trx request received >> {0}" + jsonString);
        final Gson j = new Gson();
        final ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);

        String mCode = apiData.getMerchant();
        final String ucode = apiData.getUserCode();
        final String type_id = apiData.getType_id();
        String userRoles = "";
        List<String> roleList = new ArrayList<>();
        String balance = "0.0";

        String query = "";
        if (!type_id.isEmpty()) {
            if (type_id.contains("[0]")) {
                if (mCode.equalsIgnoreCase("ALL")) {
                    return balance;
                } else {
                    roleList.add(mCode.split(":")[1]);
                }
            } else if (type_id.contains("[2]")) {
                userRoles = utility.getRoleParameters("[17]", ucode);
                if (!userRoles.isEmpty()) {
                    roleList = Pattern.compile("~").splitAsStream(userRoles).map(f -> f.split(":")[1])
                            .collect(Collectors.toList());
                    if (mCode.equalsIgnoreCase("ALL")) {

                    } else {
                        mCode = mCode.split(":")[1];
                        if (roleList.contains(mCode)) {
                            roleList = new ArrayList<>();
                            roleList.add(mCode);
                        }
                    }

                } else {
                    return balance;
                }
            } else {
                return balance;
            }
        } else {
            return balance;
        }

        Session session = DbHibernate.getSession("40.17MYSQL");

        try {
            log.error("ucode: {0}" + roleList);

            query = "SELECT ifnull(sum(online_balance),0), '' e from ecarddb.e_cardholder where card_num in (:cardnum)";

            Query q = session.createNativeQuery(query);
            q.setParameter("cardnum", (Object) roleList);

            Object[] record = (Object[]) q.getSingleResult();
            balance = record[0].toString();
        } catch (NoResultException ex) {
            balance = "0";
        } catch (Exception ey) {
            log.error(ey.getMessage(), ey);
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return balance;
    }

    public List<NODES> getMerchants(final String jsonString) {
        log.error("search trx fundgate request received >> {0}" + jsonString);
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
            } else if (type_id.contains("[2]")) {
                userRoles = utility.getRoleParameters("[17]", ucode);
                log.info("ucode: " + userRoles);
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
            query = "SELECT concat(terminal_id,':',card_num) id, merchant_name name from fgdb.cop_fundgate_info "
                    + (!roleList.contains("ALL") ? " where terminal_id in (:terminalId) " : "")
                    + " order by merchant_name asc ";
            Query q = session.createNativeQuery(query, NODES.class);
            if (!roleList.contains("ALL")) {
                q.setParameter("terminalId", (Object) roleList);
            }

            recordsList = q.getResultList();

        } catch (Exception ek) {
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return recordsList;
    }

    public List<NODES> getMerchantsAccount(final String jsonString) {
        log.error("search trx fundgate request received >> {0}" + jsonString);
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
            } else if (type_id.contains("[2]")) {
                userRoles = utility.getRoleParameters("[17]", ucode);
                log.info("ucode: " + userRoles);
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
            query = "SELECT card_num id, merchant_name name from fgdb.cop_fundgate_info "
                    + (!roleList.contains("ALL") ? " where terminal_id in (:terminalId) " : "")
                    + " order by merchant_name asc ";
            Query q = session.createNativeQuery(query, NODES.class);
            if (!roleList.contains("ALL")) {
                q.setParameter("terminalId", (Object) roleList);
            }

            recordsList = q.getResultList();

        } catch (Exception ek) {
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return recordsList;
    }

    public List<FundGateReport> getFundGateReport(final String jsonString) {
        log.error("fundgate report request received >> {0}" + jsonString);
        final Gson j = new Gson();
        final ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);

        final String start_dt = apiData.getStartDate();
        final String end_dt = apiData.getEndDate();
        String transType = apiData.getProduct();
        String uniqueTransid = apiData.getUniqueTransId();
        final String ucode = apiData.getUserCode();
        String type_id = apiData.getType_id();
        String mCode = apiData.getMerchant();
        String service = apiData.getService();
        String searchKey = apiData.getSearchKey();

        String sql = "";
        String userRoles = "";

        List<FundGateReport> recordsList = new ArrayList<>();

        List<String> roleList = new ArrayList<>();

        List<Object[]> records = new ArrayList<>();

        if (mCode == null || mCode.isEmpty()) {
            mCode = "ALL";
        }
        if (uniqueTransid == null) {
            uniqueTransid = "";
        }
        if (service == null) {
            service = "";
        }

        if (service.equalsIgnoreCase("searchReport") && searchKey.trim().isEmpty()) {
            return recordsList;
        }
        if (service.equalsIgnoreCase("searchReport")) {
            uniqueTransid = searchKey;
        }

        if (!type_id.isEmpty()) {
            if (type_id.contains("[0]")) {
                roleList.add(mCode.toUpperCase());
            } else if (type_id.contains("[2]")) {
                userRoles = utility.getRoleParameters("[17]", ucode);
                log.info("ucode: {0}" + userRoles);
                if (!userRoles.isEmpty()) {
                    roleList = Pattern.compile("~").splitAsStream(userRoles).map(f -> f.split(":")[1])
                            .collect(Collectors.toList());
                    if (mCode.equalsIgnoreCase("ALL")) {

                    } else {
                        if (roleList.contains(mCode)) {
                            roleList = new ArrayList<>(roleList);
                            roleList.add(mCode);
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
        log.error("ROLES: {0}" + roleList);
        Session session2 = DbHibernate.getSession("40.17MYSQL");

        switch (transType.toUpperCase()) {
            case "ALL":
                transType = "";
                break;
            case "I":
                transType = " and (trans_code in ('I'))";
                break;
            case "C":
                transType = " and (trans_code in ('C'))";
                break;
            case "T":
                transType = " and (trans_code in ('T', 'D'))";
                break;
            case "VTU":
                transType = " and (trans_descr like '%VTU Payment%' OR trans_descr like '%;VT%') ";
                break;
            default:
                transType = "";
                break;

        }

        try {
            Query q;

            sql = "Select unique_transid, trans_amount, trans_date  from ecarddb.e_transaction where 1=1 "
                    + (!service.equalsIgnoreCase("searchReport") ? " and trans_date between :start_dt and :end_dt "
                    : " and unique_transid like :uniqueTransid ")
                    + (!roleList.contains("ALL") ? " and (card_num in (:ucode) or merchant_code in (:ucode)) " : "")
                    + transType
                    + " order by trans_date desc";
            q = session2.createNativeQuery(sql);

            if (!service.equalsIgnoreCase("searchReport")) {
                q.setParameter("start_dt", (Object) start_dt).setParameter("end_dt", (Object) end_dt);
            } else {
                q.setParameter("uniqueTransid", (Object) (uniqueTransid.trim() + "%"));
            }

            if (!uniqueTransid.trim().isEmpty()) {
                q.setParameter("uniqueTransid", (Object) (uniqueTransid.trim() + "%"));
            }

            if (!roleList.contains("ALL")) {
                q.setParameter("ucode", (Object) roleList);
            }

            records = (List<Object[]>) q.getResultList();

        } catch (Exception ek) {
            log.error(ek.getMessage(), ek);
        } finally {
            if (session2 != null) {
                session2.close();
            }
        }

        records.stream().map(record -> {
            final FundGateReport fg = new FundGateReport();
            try {
                fg.setUnique_transid(checkNull(record[0]));
                fg.setTrans_amount(BigDecimal.valueOf(checkNullNumber(record[1])));
                fg.setTrans_date(checkNull(record[2]));
            } catch (Exception eu) {
            }
            return fg;
        }).forEachOrdered(fg -> {
            recordsList.add(fg);
        });

        log.info("FundGate Records Select done");
        return recordsList;
    }

    public List<FundgateBalance> getFundgateBalances(String jsonString) {

        List<FundgateBalance> recordsList = new ArrayList<>();
        Gson j = new Gson();
        ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);
        String type_id = apiData.getType_id();

        if (!(type_id.contains("[0]") || type_id.contains("[1]"))) {
            return recordsList;
        }

        List<Object[]> records = new ArrayList<>();
        List<Object[]> records2 = new ArrayList<>();

        String qry = "select terminal_id, merchant_name, card_num from fgdb.cop_fundgate_info where card_num is not null order by merchant_name asc";
        Session session = DbHibernate.getSession("40.9MYSQL");
        Query q = session.createNativeQuery(qry);
        records = (List<Object[]>) q.getResultList();

        String sql_in = records.stream().map(f -> "'" + f[2].toString() + "'").collect(joining(","));
        List<String> cardList = new ArrayList<>();

        Map<String, String> fgMap = new HashMap<>();

        records.stream().forEach(f -> {
            fgMap.put(f[2].toString(), f[1].toString());
            cardList.add(f[2].toString());
        });

        String qry2 = "select card_num, ifnull(online_balance, 0.00) from ecarddb.e_cardholder where card_num in (:cardList) and card_account not like '%1111111111%' and (company not like 'SusuTxT' OR company is null)";
        Session session2 = DbHibernate.getSession("40.17MYSQL");
        q = session2.createNativeQuery(qry2)
                .setParameter("cardList", cardList);

        records2 = (List<Object[]>) q.getResultList();

        records2.stream().map((record) -> {
            FundgateBalance fg = new FundgateBalance();
            fg.setTitle(fgMap.getOrDefault(record[0].toString(), ""));

            fg.setAmount(BigDecimal.valueOf(checkNullNumber(record[1])));
            return fg;
        }).forEachOrdered((fg) -> {
            recordsList.add(fg);
        });

        return recordsList;
    }

    public List<FundgateResponse> getFundGateReferenceCheck(String jsonString) {

        log.info("Fundgate Reference Check received >>  " + jsonString);
        Gson j = new Gson();

        ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);
//        String data = apiData.getUniqueTransId();
        String data = apiData.getClientRef();
        String status = apiData.getStatus();

        String user_code = apiData.getUserCode();
        String type_id = apiData.getType_id();
        String username = apiData.getUserName();

        String userId = apiData.getUser_id();

        String ipAddress = apiData.getIpAddress();

        List<FundgateResponse> records = new ArrayList<>();

        List<String> roleList = new ArrayList<>();
        List<Object[]> resp = new ArrayList<>();
        List<String> references = new ArrayList<>();

        String userRoles = "";

        if (!type_id.isEmpty()) {

            if (type_id.contains("[0]")) {

                userRoles = utility.getRoleParameters("[2500000000000053]", user_code);
                if (userRoles.isEmpty()) {
                    return records;
                }
            } else {
                return records;
            }
        } else {
            return records;
        }

        String narration = "";
        String respcode = "";
        String fgRespcode = "";

        List<MomoUpdate> recordsList = gson.fromJson(data, new TypeToken<List<MomoUpdate>>() {
        }.getType());

//        final String _respcode = respcode;
//        final String _fgRespcode = fgRespcode;
//        final String _narration = narration;
//        final String _status = status.substring(0, 1).toUpperCase() + status.substring(1);
        recordsList.stream().forEach(f -> {
            if (f.getReference() != null && !f.getReference().trim().isEmpty()) {
                references.add(f.getReference().trim());

                //updateMomo(f.getReference().trim(), f.getClientid().trim(), _narration, _respcode, _fgRespcode, _status, username, userId, ipAddress);
            }
        });

        System.out.println("Client References" + references);

        if (!references.isEmpty()) {

//            String query = "select a.reference , a.client , a.msisdn , (case when a.paymenttype='D' then 'DEBIT' when a.paymenttype = 'C' then 'CREDIT' else a.paymenttype end) as paymenttype, a.amount , a.trnxdate , (case when left(a.reference,5) = '01ESA' then 'WEBCONNECT' when  left(a.reference,2) in ('02','09') then 'TRANSFER' else  ifnull(UPPER(b.mainoptions),'') end) mainoptions, (case when a.respcode ='00' then 'SUCCESSFUL' when a.respcode in ('58','66') then 'PENDING' when a.respcode ='06' then 'FAILED' else a.respcode end) respcode, a.respcode as respcode2, a.cardno, a.cardno as cardno2, "
//                    + "a.cardno as code, (case when left(a.channel,2) in ('09','89') then 'FUNDGATE' when left(a.channel,2) = '03' then 'POS' WHEN left(a.channel,2) = '01' then 'WEBCONNECT' WHEN left(a.channel,2) = '07' then 'JUSTPAY' WHEN left(a.channel,2) = '02' then 'MOBILE' WHEN left(a.channel,2) = '08' then 'CORPORATEPAY' WHEN left(a.channel,2) = '04' then 'ATM' else left(a.channel, 2) end) channel, ifnull(a.clientid,''), (case when a.respcode in('0','00') then 'SUCCESSFUL' else ifnull(a.narration,'') end) narration,a.switchcode , "
//                    + "(case when a.flag='0' then 'COMPLETED' when a.flag='7' then 'REVERSING' when a.flag='8' then 'REVERSED' else ifnull(a.flag, '') end) flag, ifnull(a.clientcode,'') clientcode  from telcodb.mobilemoney a left join telcodb.just_pay b on a.reference = b.reference where 1=1 "
//                    + "  AND a.reference in (:reference)  order by a.trnxdate desc";
            String query = "select respId, action, terminal, etzRef, respMessage, clientRef, created, respcode"
                    + " from fgdb.fundgate_response where 1=1 "
                    + "  AND clientRef in (:reference)  order by created desc";

            Session session = DbHibernate.getSession("40.9MYSQL");
            //Session session = DbHibernate.getSession("VASREPROCESS");

            try {

                Query q = session.createNativeQuery(query);

                q.setParameter("reference", references);

                resp = q.getResultList();

            } catch (Exception e) {
                log.error("error", e);
            } finally {
                if (session != null) {
                    session.close();
                }
            }
        }

        for (Object[] record : resp) {
            FundgateResponse fgr = new FundgateResponse();
            try {
                fgr.setRespId(checkNull(record[0]));
                fgr.setAction(checkNull(record[1]));
                fgr.setTerminal(checkNull(record[2]));
                fgr.setEtzRef(checkNull(record[3]));
                fgr.setRespMessage(checkNull(record[4]));
                fgr.setClientRef(checkNull(record[5]));
                fgr.setCreated(checkNull(record[6]));
                fgr.setRespCode(checkNull(record[7]));

            } catch (Exception e) {
                log.error("error", e);
            }
            records.add(fgr);
        }

        return records;
    }

    public List<Object[]> getTerminals() {

        Session session = DbHibernate.getSession("40.9MYSQL");
        List<Object[]> recs = new ArrayList<>();
        try {
            Query q = session.createNativeQuery(
                    "select terminal_id, merchant_name from fgdb.cop_fundgate_info order by merchant_name");
            recs = (List<Object[]>) q.getResultList();
        } catch (Exception ey) {
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return recs;
    }

    protected List<FundGateTransaction> mapNarration(List<FundGateTransaction> recordList,
            HashMap<String, String> transResp) {

        recordList.forEach(f -> {
            if (transResp.containsKey(f.getEtzRef())) {
                f.setBillerRespMessage(transResp.get(f.getEtzRef()).split("~")[0]);
                f.setTransId(transResp.get(f.getEtzRef()).split("~")[1]);
            }
        });
        return recordList;
    }

    protected String checkNull(Object Data) {
        if (Data != null && !Data.equals("")) {
            return Data.toString();
        }
        return "NULL";
    }

    protected double checkNullNumber(Object Data) {
        if (Data != null && !Data.equals("")) {
            return Double.valueOf(Data.toString());
        }
        return 0.0;
    }

    /*
     * protected String mapTerminal(final String terminal_id) {
     * for (final Object[] obj : this.terminalrecords) {
     * if (obj[0].toString().trim().equals(terminal_id.trim())) {
     * return obj[1].toString();
     * }
     * }
     * return terminal_id;
     * }
     */
    protected String convertResp(String resp) {
        if (!resp.isEmpty()) {
            try {
                resp = resp.replaceAll(" Balance.*?GHC.", "");
            } catch (Exception ex) {
                log.error("XPORTAL ERROR Convert Fundgate Resp Err ::: {0}", ex);
            }
        }
        return resp;
    }

    public HashMap<String, String> getEtransaction(List<String> refs) {

        HashMap<String, String> dMap = new HashMap<>();
        if (!refs.isEmpty()) {
            final String qry2 = "SELECT unique_transid, trans_code FROM ecarddb.e_transaction where unique_transid in(:refs)";
            Session session = DbHibernate.getSession("40.17MYSQL");

            try {
                Query q = session.createNativeQuery(qry2).setParameter("refs", refs);
                List<Object[]> resp = (List<Object[]>) q.getResultList();

                resp.stream().filter(record -> (record[0] != null && record[1] != null)).forEachOrdered(record -> {
                    dMap.put(record[0].toString(), "" + record[1].toString());
                });
            } catch (Exception er) {
                log.error(er.getMessage(), er);
            } finally {
                if (session != null) {
                    session.close();
                }
            }
        }
        return dMap;
    }

    public HashMap<String, String> getAirtime(List<String> refs, String status) {

        HashMap<String, String> dMap = new HashMap<>();
        if (!refs.isEmpty()) {
            final String qry2 = "SELECT unique_transid, concat(ifnull((case when response_code ='0' then '00' else response_code end),'N/A') ,'~', ifnull((case when response_code in ('0','00') then 'SUCCESSFUL' else response_message end),'N/A')) description from vasdb2.t_provider_log where unique_transid in(:refs) ";

            Session session = DbHibernate.getSession("40.9MYSQL");

            try {
                Query q = session.createNativeQuery(qry2).setParameter("refs", refs);
                List<Object[]> resp = (List<Object[]>) q.getResultList();

                resp.stream().filter(record -> (record[0] != null && record[1] != null)).forEachOrdered(record -> {
                    dMap.put(record[0].toString(), "" + record[1].toString());
                });
            } catch (Exception er) {
                log.error(er.getMessage(), er);
            } finally {
                if (session != null) {
                    session.close();
                }
            }
        }
        return dMap;
    }

    public HashMap<String, String> getBill(List<String> refs, String status) {

        HashMap<String, String> dMap = new HashMap<>();
        if (!refs.isEmpty()) {
            final String qry2 = "SELECT unique_transid, concat(ifnull(trans_status,'N/A'),'~', ifnull((case when trans_status in('0','00') then 'SUCCESSFUL' else trans_note end),'N/A')) description FROM vasdb2.t_paytrans where unique_transid in(:refs) ";

            Session session = DbHibernate.getSession("40.9MYSQL");

            try {
                Query q = session.createNativeQuery(qry2).setParameter("refs", refs);
                List<Object[]> resp = (List<Object[]>) q.getResultList();

                resp.stream().filter(record -> (record[0] != null && record[1] != null)).forEachOrdered(record -> {
                    dMap.put(record[0].toString(), "" + record[1].toString());
                });
            } catch (Exception er) {
                log.error(er.getMessage(), er);
            } finally {
                if (session != null) {
                    session.close();
                }
            }
        }
        return dMap;
    }

    public HashMap<String, String> getMomo(List<String> refs, String status) {

        HashMap<String, String> dMap = new HashMap<>();
        if (!refs.isEmpty()) {
            final String qry2 = "SELECT reference, concat(ifnull(respcode, 'N/A'),'~',ifnull((case when respcode in ('0','00') then 'SUCCESSFUL' else narration end), 'N/A') ,'~', ifnull(clientId,'N/A'), '~', ifnull(amount,0.00)) na FROM telcodb.mobilemoney where reference in(:refs) ";

            Session session = DbHibernate.getSession("40.9MYSQL");

            try {
                Query q = session.createNativeQuery(qry2).setParameter("refs", refs);
                List<Object[]> resp = (List<Object[]>) q.getResultList();

                resp.stream().filter(record -> (record[0] != null && record[1] != null)).forEachOrdered(record -> {
                    dMap.put(record[0].toString(), "" + record[1].toString());
                });
            } catch (Exception er) {
                log.error(er.getMessage(), er);
            } finally {
                if (session != null) {
                    session.close();
                }
            }
        }
        return dMap;
    }

    protected String getBiller(String billerCode) {
        switch (billerCode) {
            case "686":
                return "MTN";
            case "844":
                return "TIGO";
            case "863":
                return "VODA";
            case "285":
                return "AIRTEL";
            default:
                break;
        }
        return billerCode;
    }

}
