/**
 *
 * @author sunkwa-arthur
 */
package com.etzgh.xportal.dao;

import com.etzgh.xportal.model.ApiPostData;
import com.etzgh.xportal.model.CorporatePay;
import com.etzgh.xportal.model.RoleOption;
import com.etzgh.xportal.utility.DbHibernate;
import com.etzgh.xportal.utility.GeneralUtility;
import com.google.gson.Gson;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.apache.log4j.Logger;

public class CorporatePayDao {

    private static final Logger log = Logger.getLogger(CorporatePayDao.class.getName());

    Query q;
    Query q1;
    Query q2;

    Map cpMap = new TreeMap();

    final GeneralUtility utility = new GeneralUtility();

    public static void main(String[] args) {
        String a = "{\"startDate\":\"2011-02-10 00:00\",\"endDate\":\"2021-02-10 23:59\",\"product\":\"\",\"accountNumber\":\"\",\"status\":\"A\",\"service\":\"transactions\",\"action\":\"\",\"apiSecret\":\"\",\"apiCode\":\"\",\"userCode\":\"[1],[17]|0060000244:0067510000010000,[2000000000000048]|000,[2000000000000049]|SCB:6PHY,[2000000000000053]|GOTV,[2000000000000054]|GOTV~TELESOL,[2000000000000060]|000,[2000000000000062]|ALL,[2000000000000063]|KNUST,[2000000000000064]|SurflineTopup,[2500000000000049]|2,[2500000000000050]|38,[29],[71]|0060010112,[91]|ALL\",\"admin\":\"\",\"type_desc\":\"\",\"role_id\":\"\",\"type_id\":\"[0],[1],[2],[3],[4],[5],[6],[7],[8],[9],[10],[11],[21],[25],[26],[27],[28],[30],[31],[33],[41],[43]\",\"userName\":\"\",\"ClientId\":\"\",\"user_id\":\"9000000000000490\",\"pageNumber\":1,\"rowsPerPage\":1,\"trans_code\":\"\",\"transType\":\"\",\"subscriberId\":\"\",\"trans_status\":\"\",\"channel\":\"\",\"bank\":\"\",\"biller\":\"\",\"transId\":\"\",\"bankid\":\"\",\"from_source\":\"\",\"card_num\":\"\",\"optionType\":\"\",\"payType\":\"\",\"wcMerchant\":\"\",\"destination\":\"\",\"lineType\":\"\",\"trans_channel\":\"\",\"pan\":\"\",\"terminal_id\":\"\",\"card_account\":\"\",\"cop_issuercode\":\"\",\"cop_role_id\":\"\",\"cop_company_id\":\"ALL\",\"mobile_no\":\"\",\"phone\":\"\",\"type\":\"\",\"amount\":\"\",\"amount2\":\"\",\"refundStatus\":\"\",\"partialReversal\":\"\",\"reversalReason\":\"\",\"ipAddress\":\"\",\"userData\":\"\",\"appId\":[],\"options\":{}}";
        new CorporatePayDao().getCorporatePayReport(a);
    }

    public List<CorporatePay> getCorporatePayReport(String jsonString) {
        System.out.println("corporatepay report request received >> " + jsonString);
        Gson j = new Gson();
        ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);

        List<CorporatePay> fundgateMap = new ArrayList<>();
        List<CorporatePay> beneficiaryMap = new ArrayList<>();
        List<CorporatePay> pendingMap = new ArrayList<>();
        List<CorporatePay> CorporatePayLog = new ArrayList<>();
        List<Object[]> records1 = new ArrayList<>();
        List<Object[]> records2 = new ArrayList<>();
        List<Object[]> records3 = new ArrayList<>();
        List<String> copList = new ArrayList<>();

        String role = apiData.getCop_role_id();
        String issuerCode = apiData.getCop_issuercode();
        String company_Id = apiData.getCop_company_id();
        String type_id = apiData.getType_id();
        String paymentStatus = apiData.getStatus();
        String paymentReference = apiData.getUniqueTransId();
        String paymentId = apiData.getTransId();

        String start_dt = apiData.getStartDate();
        String end_dt = apiData.getEndDate();
        String batchId = apiData.getTrans_code();
        String ucode = apiData.getUserCode();
        boolean processFund = false;
        boolean processBen = false;
        boolean pending = false;
        boolean paymentReferenceSet = false;
        boolean paymentIdSet = false;
        boolean batchIdSet = false;
        boolean companyIdSet = false;
        boolean dateSet = false;
        String userRoles = "";
        if (paymentId == null) {
            paymentId = "";
        }
        if (batchId == null) {
            batchId = "";
        }
        if (company_Id == null) {
            company_Id = "";
        }

        if (!type_id.isEmpty()) {
            if (type_id.contains("[0]")) {
                copList.add(company_Id);
            } else if (type_id.contains("[28]")) {
                userRoles = utility.getRoleParameters("[2000000000000048]", ucode);
                System.out.println("usrRoles: " + userRoles);

                copList = Pattern.compile("~").splitAsStream(userRoles).map(f -> f.split(":")[0])
                        .collect(Collectors.toList());
                if (company_Id.equalsIgnoreCase("ALL")) {

                } else {
                    if (copList.contains(company_Id)) {

                        copList = new ArrayList<>(copList);
                        copList.add(company_Id);
                    }
                }
            } else {
                return CorporatePayLog;
            }
        } else {
            return CorporatePayLog;
        }

        String ql = "select distinct m.COMPANY_NAME as companyName, mi.ISSUER_NAME as initBank, '' as converted_amount, ";
        ql += "f.PAYMENT_ID as paymentId, f.BATCH_ID as batchId, f.REFERENCE as reference, f.BENEFICIARY_NAME as beneficiaryName, fi.ISSUER_NAME as beneficiaryBank, f.S_ACCT_NUMBER as accountNumber, ";
        ql += "f.TRANS_AMOUNT as amount, f.CREATED as authorized_date, f.DESCRIPTION as description, (case when f.HOST_ERROR_CODE ='0' then 'Successful' when f.HOST_ERROR_CODE ='7' then 'Failed' when f.HOST_ERROR_CODE ='-1' then 'Network Error' when f.HOST_ERROR_CODE='R' then  'Reversed' else 'N/A' end)  as status, f.REMARK as remark, CONCAT(u.FIRSTNAME, ' ', u.LASTNAME) as authorizer from";
        ql += " COP_FUNDGATE_LOG f, COP_MERCHANT m, COP_ISSUERBIN mi, COP_ISSUERBIN fi, COP_USERS u where f.COMPANY_ID=m.COMPANY_ID and m.ISSUER_CODE=mi.ISSUER_CODE and f.ISSUER_CODE=fi.ISSUER_CODE and ";
        ql += " f.AUTHORIZER_BY=u.USERNAME ";
        String ql2 = "select distinct m.COMPANY_NAME as companyName, bi.ISSUER_NAME as initBank, '' as converted_amount,  ";
        ql2 += "b.PAYMENT_ID as paymentId, b.BATCH_ID as batchId, b.UNIQUE_TRANSID as reference, CONCAT(b.FIRSTNAME, ' ', b.LASTNAME) as beneficiaryName, bb.ISSUER_NAME as beneficiaryBank, b.S_ACCT_NO as accountNumber, ";
        ql2 += "b.AMOUNT as amount, b.CREATED as authorized_date, p.PAYMENT_DESCRIPTION as description, 'Pending' as status, ' ' as remark, CONCAT(u.FIRSTNAME, ' ', u.LASTNAME) as authorizer ";
        ql2 += " from COP_BENEFICIARY b, COP_MERCHANT m, COP_ISSUERBIN bi, COP_ISSUERBIN bb, COP_PAYMENT p, COP_USERS u  where b.COMPANY_ID=m.COMPANY_ID and b.ISSUER_CODE=bi.ISSUER_CODE and b.BANK_CODE=bb.ISSUER_CODE and ";
        ql2 += " b.PAYMENT_ID=p.PAYMENT_ID and b.USERNAME=u.USERNAME ";
        String ql3 = "select distinct m.COMPANY_NAME as companyName, bi.ISSUER_NAME as initBank, '' as converted_amount,  ";
        ql3 += "b.PAYMENT_ID as paymentId, b.BATCH_ID as batchId, b.UNIQUE_TRANSID as reference, CONCAT(b.FIRSTNAME, ' ', b.LASTNAME) as beneficiaryName, bb.ISSUER_NAME as beneficiaryBank, b.S_ACCT_NO as accountNumber, ";
        ql3 += "b.AMOUNT as amount, b.CREATED as authorized_date, p.PAYMENT_DESCRIPTION as description, 'Pending' as status, ' ' as remark, CONCAT(u.FIRSTNAME, ' ', u.LASTNAME) as authorizer  ";
        ql3 += " from COP_BENEFICIARY b,  COP_MERCHANT m, COP_ISSUERBIN bi, COP_ISSUERBIN bb, COP_PAYMENT p, COP_USERS u, COP_FUNDGATE_LOG f where ";
        ql3 += "b.PAYMENT_THREAD_ID is null and b.PAYMENT_ID = f.PAYMENT_ID and b.COMPANY_ID=m.COMPANY_ID and b.ISSUER_CODE=bi.ISSUER_CODE and b.BANK_CODE=bb.ISSUER_CODE and ";
        ql3 += " b.PAYMENT_ID=p.PAYMENT_ID and b.USERNAME=u.USERNAME ";
        if (paymentReference != null && !paymentReference.trim().equals("")) {
            paymentReferenceSet = true;
            ql = ql + " and f.REFERENCE = :paymentReference";
            ql2 = ql2 + " and b.UNIQUE_TRANSID = :paymentReference";
            ql3 = ql3 + " and b.UNIQUE_TRANSID = :paymentReference";
        } else if (!paymentId.trim().isEmpty()) {
            paymentIdSet = true;
            ql = ql + " and f.PAYMENT_ID=:paymentId";
            ql2 = ql2 + " and b.PAYMENT_ID=:paymentId ";
            ql3 = ql3 + " and b.PAYMENT_ID=:paymentId";
        } else if (!batchId.trim().isEmpty()) {
            batchIdSet = true;
            ql = ql + " and f.BATCH_ID=:batchId";
            ql2 = ql2 + " and b.BATCH_ID= :batchId ";
            ql3 = ql3 + " and b.BATCH_ID=:batchId";
        } else {
            if (paymentId.isEmpty()) {
                dateSet = true;
                ql = ql + " and f.CREATED between :startDate and :endDate ";
                ql2 = ql2 + " and b.CREATED between :startDate and :endDate ";
                ql3 = ql3 + " and b.CREATED between :startDate and :endDate ";
            }
        }
        System.out.println("COP :" + copList);
        if (copList.contains("ALL")) {

            ql = ql + " and f.company_id in (select distinct m.company_id from cop_Merchant m ) ";
            ql2 = ql2 + " and b.merchant_id in (select distinct m.company_id from cop_Merchant m ) ";
            ql3 = ql3 + " and b.merchant_id in (select distinct m.company_id from cop_Merchant m ) ";
        } else if (!copList.isEmpty()) {
            companyIdSet = true;
            ql = ql + " and f.COMPANY_ID in (:companyId)";
            ql2 = ql2 + " and b.COMPANY_ID = (:companyId)";
            ql3 = ql3 + " and b.COMPANY_ID = (:companyId)";
        } else {
            return CorporatePayLog;
        }
        if (paymentStatus != null && paymentStatus.trim().equalsIgnoreCase("P")) {
            ql += " and f.HOST_ERROR_CODE in ('0','00','000') ";
            processFund = true;
            processBen = false;
            pending = false;
        } else if (paymentStatus != null && paymentStatus.trim().equalsIgnoreCase("F")) {
            ql += " and f.HOST_ERROR_CODE not in ('0','00','000', null,'tra') ";
            processFund = true;
            processBen = false;
            pending = false;
        } else if (paymentStatus != null && paymentStatus.trim().equalsIgnoreCase("E")) {
            ql += " and f.HOST_ERROR_CODE not in (null,'tra') ";
            processFund = true;
            processBen = false;
            pending = true;
        } else if (paymentStatus != null && paymentStatus.trim().equalsIgnoreCase("R")) {
            ql += " and f.HOST_ERROR_CODE = 'R' ";
            processFund = true;
            processBen = false;
            pending = false;
        } else {
            processFund = true;
            processBen = false;
            pending = true;
        }

        ql = ql + " order by f.CREATED desc ";
        ql2 = ql2 + " order by b.CREATED desc ";
        ql3 = ql3 + " order by b.CREATED desc ";
        Session session = DbHibernate.getSession("30.25MYSQL");
        try {

            if (processFund) {

                q = (session.createNativeQuery(ql));
                if (companyIdSet) {
                    q.setParameter("companyId", copList);
                }
                if (paymentReferenceSet) {
                    q.setParameter("paymentReference", paymentReference);
                }
                if (paymentIdSet) {
                    q.setParameter("paymentId", paymentId);
                }
                if (batchIdSet) {
                    q.setParameter("batchId", batchId);
                }
                if (dateSet) {
                    q.setParameter("startDate", start_dt);
                    q.setParameter("endDate", end_dt);
                }
                records1 = q.getResultList();

            }
            if (processBen) {
                q = (session.createNativeQuery(ql2));
                if (companyIdSet) {
                    q.setParameter("companyId", copList);
                }
                if (paymentReferenceSet) {
                    q.setParameter("paymentReference", paymentReference);
                }
                if (paymentIdSet) {
                    q.setParameter("paymentId", paymentId);
                }
                if (dateSet) {
                    q.setParameter("startDate", start_dt);
                    q.setParameter("endDate", end_dt);
                }
                if (batchIdSet) {
                    q.setParameter("batchId", batchId);
                }

                records2 = q.getResultList();

            }
            if (pending) {
                q = (session.createNativeQuery(ql3));
                if (companyIdSet) {
                    q.setParameter("companyId", copList);
                }
                if (paymentReferenceSet) {
                    q.setParameter("paymentReference", paymentReference);
                }
                if (paymentIdSet) {
                    q.setParameter("paymentId", paymentId);
                }
                if (dateSet) {
                    q.setParameter("startDate", start_dt);
                    q.setParameter("endDate", end_dt);
                }
                if (batchIdSet) {
                    q.setParameter("batchId", batchId);
                }

                records3 = q.getResultList();

            }
        } catch (Exception rt) {
            log.error(rt.getMessage(), rt);
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
        }

        if (processFund) {
            fundgateMap = convertList(records1);
            CorporatePayLog.addAll(fundgateMap);
        }
        if (processBen) {
            beneficiaryMap = convertList(records2);
            CorporatePayLog.addAll(beneficiaryMap);
        }
        if (pending) {
            pendingMap = convertList(records3);
            CorporatePayLog.addAll(pendingMap);
        }

        return CorporatePayLog;
    }

    protected String checkNull(Object Data) {
        if (Data != null && !Data.equals("")) {
            return Data.toString();
        }
        return "NULL";
    }

    protected int checkNullNum(Object Data) {
        int r = 0;

        if (Data != null && !Data.toString().trim().isEmpty()) {
            try {
                r = Integer.parseInt(Data.toString());
            } catch (NumberFormatException p) {
                r = 0;
            }
        }
        return r;
    }

    public List<RoleOption> getMerchants(final String jsonString) {
        System.out.println("Cpay request received >> " + jsonString);
        final Gson j = new Gson();
        final ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);

        String type_id = apiData.getType_id();
        String ucode = apiData.getUserCode();
        String userRoles = "";

        List<String> roleList = new ArrayList<>();
        List<RoleOption> recordsList = new ArrayList<>();
        String query = "";
        if (!type_id.isEmpty()) {
            if (type_id.contains("[0]")) {
                roleList.add("ALL");
            } else if (type_id.contains("[28]")) {
                userRoles = utility.getRoleParameters("[2000000000000048]", ucode);
                System.out.println("ucode: " + userRoles);
                if (!userRoles.isEmpty()) {
                    roleList = Pattern.compile("~").splitAsStream(userRoles).map(f -> f.split(":")[0])
                            .collect(Collectors.toList());

                    if (roleList.isEmpty()) {
                        return recordsList;
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

        Session session = DbHibernate.getSession("30.25MYSQL");

        try {

            query = "select distinct m.COMPANY_ID alias, m.COMPANY_NAME name  from corporatedb.COP_MERCHANT m where 1=1 "
                    + (!roleList.contains("ALL") ? " and m.COMPANY_ID in (:company_id) " : "")
                    + " order by m.COMPANY_NAME asc";

            q = session.createNativeQuery(query, RoleOption.class);
            if (!roleList.contains("ALL")) {
                q.setParameter("company_id", (Object) roleList);
            }

            recordsList = q.getResultList();

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return recordsList;
    }

    public String currencyFormat(BigDecimal n) {

        return NumberFormat.getNumberInstance().format(n);
    }

    public List<CorporatePay> convertList(List<Object[]> records) {
        List<CorporatePay> result = new ArrayList<>();

        for (Object[] record : records) {
            CorporatePay cp = new CorporatePay();
            try {
                cp.setCompanyName(checkNull(record[0]));
                cp.setInitBank(checkNull(record[1]));
                cp.setConverted_amount(currencyFormat(BigDecimal.valueOf(checkNullNum(record[2]))));
                cp.setPaymentId(checkNull(record[3]));
                cp.setBatchId(checkNull(record[4]));
                cp.setReference(checkNull(record[5]));
                cp.setBeneficiaryName(checkNull(record[6]));
                cp.setBeneficiaryBank(checkNull(record[7]));
                cp.setAccountNumber(checkNull(record[8]));
                cp.setAmount(stripDecimalZeros(BigDecimal.valueOf(Double.valueOf(checkNull(record[9])))));
                cp.setAuthorized_date(checkNull(record[10]));
                cp.setDescription(checkNull(record[11]));
                cp.setStatus(checkNull(record[12]));
                cp.setRemark(checkNull(record[13]));
                cp.setAuthorizer(checkNull(record[14]));

            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            result.add(cp);
        }

        return result;
    }

    public static BigDecimal stripDecimalZeros(BigDecimal value) {
        if (value == null) {
            return null;
        }

        BigDecimal striped = (value.scale() > 0) ? value.stripTrailingZeros() : value;

        return (striped.scale() < 0) ? striped.setScale(0) : striped;
    }

}
