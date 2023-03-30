package com.etzgh.xportal.dao;

import com.etzgh.xportal.model.ApiPostData;
import com.etzgh.xportal.model.VTU;
import com.etzgh.xportal.utility.DbHibernate;
import com.etzgh.xportal.utility.GeneralUtility;
import com.google.gson.Gson;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class VtuDao {

    private final GeneralUtility utility = new GeneralUtility();

    private static HashMap<String, String> bankNames = new HashMap<>();
    private static final Logger log = Logger.getLogger(VtuDao.class.getName());

    public static void main(String[] args) {
        String r = "{\"startDate\":\"2021-03-26 12:00\",\"endDate\":\"2021-03-27 23:59\",\"merchant\":\"\",\"product\":\"\",\"accountNumber\":\"\",\"status\":\"FAILED\",\"service\":\"transactions\",\"action\":\"\",\"apiSecret\":\"\",\"apiCode\":\"\",\"userCode\":\"[1],[17]|0060000290:0067620000000010,[2000000000000048]|00000000000000000002:0069900596470004,[2000000000000049],[2000000000000053]|ALL,[2000000000000054],[2000000000000060]|000,[2000000000000062]|ALL,[2000000000000063]|KNUST,[2000000000000064]|ALL,[2500000000000049]|2,[2500000000000050]|ALL,[2500000000000053]|3,[29],[50]|2,[71]|0230010002,[91]|ALL\",\"admin\":\"\",\"type_desc\":\"\",\"role_id\":\"\",\"type_id\":\"[0],[1],[2],[3],[4],[5],[6],[7],[8],[9],[10],[11],[20],[21],[25],[26],[27],[28],[30],[31],[33],[35],[37],[39],[40],[41],[43],[44]\",\"userName\":\"\",\"ClientId\":\"\",\"user_id\":\"9000000000000490\",\"pageNumber\":1,\"rowsPerPage\":1,\"uniqueTransId\":\"\",\"trans_code\":\"\",\"transType\":\"ALL\",\"bank_code\":\"920\",\"subscriberId\":\"\",\"trans_status\":\"\",\"channel\":\"\",\"bank\":\"\",\"biller\":\"\",\"transId\":\"\",\"bankid\":\"\",\"from_source\":\"\",\"card_num\":\"\",\"optionType\":\"\",\"payType\":\"\",\"wcMerchant\":\"\",\"destination\":\"233244149698\",\"lineType\":\"ALL\",\"trans_channel\":\"\",\"pan\":\"\",\"terminal_id\":\"\",\"card_account\":\"\",\"cop_issuercode\":\"\",\"cop_role_id\":\"\",\"cop_company_id\":\"\",\"mobile_no\":\"\",\"phone\":\"\",\"type\":\"\",\"amount\":\"\",\"amount2\":\"\",\"refundStatus\":\"\",\"partialReversal\":\"\",\"reversalReason\":\"\",\"ipAddress\":\"\",\"userData\":\"\",\"appId\":[],\"company\":\"\",\"options\":{}}";
        new VtuDao().getVtuTransactions(r);
    }

    public List<VTU> getVtuTransactions(String jsonString) {
        System.out.println("VTU trx request received >> " + jsonString);
        Gson j = new Gson();
        ApiPostData apiData = (ApiPostData) j.fromJson(jsonString, ApiPostData.class);
        String start_dt = apiData.getStartDate();
        String end_dt = apiData.getEndDate();
        String bankName = apiData.getBank();
        String str_dest = apiData.getDestination();
        String str_source_frm = apiData.getFrom_source();
        String status = apiData.getStatus();
        String transType = apiData.getTransType();

        String lineType = apiData.getLineType();
        String type_id = apiData.getType_id();
        String user_code = apiData.getUserCode();
        String bank_code = apiData.getBank_code();
        String service = apiData.getService();
        String searchKey = apiData.getSearchKey();
        List<String> roleList = new ArrayList<>();
        List<VTU> recordsList = new ArrayList<>();

        String userRoles = "";
        if (str_dest == null) {
            str_dest = "";
        }
        if (str_source_frm == null) {
            str_source_frm = "";
        }
        if (bankName == null) {
            bankName = "";
        }

        if (transType == null || transType.trim().isEmpty()) {
            transType = "ALL";
        }

        if (service.equalsIgnoreCase("search") && searchKey.trim().isEmpty()) {
            return recordsList;
        }

        if (!type_id.isEmpty()) {

            bankName = !bank_code.equals("000") ? bank_code : bankName;
            if (type_id.contains("[0]") || type_id.contains("[6]")) {
                roleList.add(lineType.toUpperCase());
            } else if (type_id.contains("[7]")) {
                userRoles = utility.getRoleParameters("[2000000000000062]", user_code);
                System.out.println("usrRoles: " + userRoles);
                roleList = Arrays.asList(userRoles.split("~"));
                if (lineType.equalsIgnoreCase("ALL")) {

                } else {
                    if (roleList.contains(lineType)) {
                        roleList = new ArrayList<>(roleList);
                        roleList.add(lineType);
                    }
                }

                bankName = !bank_code.equals("000") ? bank_code : bankName;
            } else {
                return recordsList;
            }
        } else {
            return recordsList;
        }

        String query = "";

        query = "select t2.amount, t2.source, t2.dest_account, t2.linetype, t2.trans_date, t2.response_code, t2.response_message, t2.provider, t2.response_date, t2.voucher_type, t2.id, t2.unique_transid, "
                + "(case when left(t2.unique_transid,4) = '09FG' then ifnull(d.MERCHANT_NAME,'N/A') when left(t2.unique_transid,5) ='justx' then 'mobilemoney' when left(t2.unique_transid,2) ='02' then 'bank' else 'N/A' end) bank "
                + "from vasdb2.t_provider_log t2 left join fgdb.FUNDGATE_REQUEST c on t2.unique_transid=c.etzRef left join fgdb.cop_fundgate_info d "
                + "on d.TERMINAL_ID= c.terminal where 1=1 "
                + (!service.equalsIgnoreCase("search") ? " and t2.trans_date between :start_dt and :end_dt " : " and t2.unique_transid IN(:searchKey) ")
                + (transType.equalsIgnoreCase("ALL") ? "" : (transType.equals("bank") ? " and t2.unique_transid not like '02SC%' and left(t2.unique_transid,2)='02'" : " and t2.unique_transid like :transType "))
                + (!roleList.contains("ALL") ? " and provider in (:lineType) " : "")
                + (!bankName.equalsIgnoreCase("ALL") && !bankName.trim().isEmpty() ? " and left(t2.unique_transid,2) ='02'" : "")
                + (!str_source_frm.trim().isEmpty() ? " and t2.source = :str_source_frm " : "")
                + (!str_dest.trim().isEmpty() ? " and t2.dest_account = :str_dest " : "")
                + (!status.equalsIgnoreCase("ALL") ? (status.equalsIgnoreCase("SUCCESSFUL") ? " and t2.response_code ='0'" : " and t2.response_code not in('0')") : "")
                + "  order by t2.trans_date desc";

        System.out.println("vtu query " + query);
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

            if (!roleList.contains("ALL")) {
                q.setParameter("lineType", roleList);
            }
            if (!str_dest.trim().isEmpty()) {
                q.setParameter("str_dest", str_dest.trim());
            }
            if (!str_source_frm.trim().isEmpty()) {
                q.setParameter("str_source_frm", str_source_frm.trim());
            }
            if (!transType.equalsIgnoreCase("ALL") && !transType.equals("bank")) {
                q.setParameter("transType", transType.trim() + "%");
            }
            records = q.getResultList();

        } catch (Exception et) {
            log.error(et.getMessage(), et);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        List<String> bankRefs = new ArrayList<>();

        for (final Object[] record : records) {
            final VTU vt = new VTU();
            try {
                String ref = checkNull(record[11]);

                vt.setUnique_transid(ref);

                if (ref.startsWith("02")) {
                    bankRefs.add(ref);
                }
                vt.setLinetype(checkNull(record[3]));
                vt.setProvider(checkNull(record[7]));
                vt.setSource(checkNull(record[1]));
                vt.setDest_account(checkNull(record[2]));
                vt.setAmount(BigDecimal.valueOf(Double.valueOf(record[0].toString())));
                vt.setBank(checkNull(record[12]));
                vt.setResponse_code(checkNull(record[5]));
                vt.setResponse_message(checkNull(record[6]));
                vt.setTrans_date(checkNull(record[4]));
                vt.setResponse_date(checkNull(record[8]));
                vt.setVoucher_type(checkNull(record[9]));

            } catch (Exception y) {
                log.error(y.getMessage(), y);
            }
            recordsList.add(vt);
        }

        if (!bankRefs.isEmpty()) {
            try {
                HashMap bankRecords = getBanks(bankRefs, bank_code);

                recordsList = recordsList.stream()
                        .map(s -> {
                            if (s.getUnique_transid().startsWith("02")) {
                                s.setBank(bankRecords.getOrDefault(s.getUnique_transid(), "bank").toString());
                            }
                            return s;
                        }).collect(Collectors.toList());

            } catch (Exception e) {
                log.error(e.getMessage(), e);
                System.out.println("VTU Mapping Banks Error: " + e);
            }
        }
        if (!bankName.equalsIgnoreCase("all") && !bankName.trim().isEmpty() && bankRefs.isEmpty()) {
            recordsList = new ArrayList<>();

        }

        System.out.println("VTU Records Select done");
        return recordsList;
    }

    public HashMap getBanks(List<String> references, String bankCode) {
        HashMap dMap = new HashMap();
        List<Object[]> channelRs = new ArrayList<>();

        try {
            if (!references.isEmpty()) {
                String qry2 = "SELECT a.transid, b.issuer_name FROM ecarddb.e_requestlog a left join ecarddb.e_issuer b on left(a.card_num,3) = b.issuer_code "
                        + " where a.transid in (:references) "
                        + (!bankCode.equals("000") && !bankCode.equals("ALL") ? " and left(a.card_num,3) = :bankCode " : "");
                qry2 += " UNION SELECT a.transid, b.issuer_name FROM ecarddb2.e_requestlog a left join ecarddb.e_issuer b on left(a.card_num,3) = b.issuer_code "
                        + " where a.transid in (:references) "
                        + (!bankCode.equals("000") && !bankCode.equals("ALL") ? " and left(a.card_num,3) = :bankCode " : "");
                qry2 += " UNION SELECT a.transid, b.issuer_name FROM ecarddb2.e_requestlog a left join ecarddb.e_issuer b on left(a.card_num,3) = b.issuer_code "
                        + " where a.transid in (:references) "
                        + (!bankCode.equals("000") && !bankCode.equals("ALL") ? " and left(a.card_num,3) = :bankCode " : "");

                qry2 = "select * from(" + qry2 + ") as t1;";
                Session session = DbHibernate.getSession("40.15MYSQL");
                try {
                    Query q = session.createNativeQuery(qry2).setParameter("references", references);
                    if (!bankCode.equals("000") && !bankCode.equals("ALL")) {
                        q.setParameter("bankCode", bankCode);
                    }

                    channelRs = q.getResultList();
                } catch (Exception et) {
                    log.error(et.getMessage(), et);
                } finally {
                    if (session != null) {
                        session.close();
                    }
                }

                for (Object[] record : channelRs) {
                    try {
                        dMap.put(record[0].toString(), "" + record[1].toString());
                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                    }
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return dMap;
    }

    public static HashMap getBankNames() {
        HashMap dMap = new HashMap();
        List<Object[]> bankNames2 = new ArrayList<>();

        try {
            String query = "select ifnull(issuer_acct, issuer_code),issuer_name from ecarddb.e_issuer order by issuer_name";

            Session session = DbHibernate.getSession("40.15MYSQL");
            try {
                Query q = session.createNativeQuery(query);

                bankNames2 = q.getResultList();
            } catch (Exception et) {
                log.info("error", et);
            } finally {
                if (session != null) {
                    session.close();
                }
            }

            for (Object[] record : bankNames2) {
                try {
                    dMap.put(record[0].toString(), "" + record[1].toString());
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return dMap;
    }

    protected String checkNull(Object Data) {
        if (Data != null && !Data.toString().trim().isEmpty()) {
            return Data.toString().trim();
        }
        return "";
    }

}
