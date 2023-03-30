package com.etzgh.xportal.dao;

import com.etzgh.xportal.controller.PortalSettings;
import com.etzgh.xportal.model.ApiPostData;
import com.etzgh.xportal.model.AuditTrail;
import com.etzgh.xportal.model.NODES;
import com.etzgh.xportal.model.RoleOption;
import com.etzgh.xportal.utility.Config;
import com.etzgh.xportal.utility.DbHibernate;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author sunkwa-arthur
 */
public class AuditDao {

    private static final Gson gson = new Gson();
    private static final Config config = new Config();
    private final PortalSettings portalSettings = new PortalSettings();

    private static final String path = config.getProperty("APPLICATION_API_PATH");
    private static HashMap<String, String> userOptions = new HashMap<>();
    private static final Pattern typeIdPattern = Pattern.compile("\\[(.*?)\\]");
    private static final Logger log = Logger.getLogger(AuditDao.class.getName());

    private static final String[] translateList = {"modifyuserroles", "createuser"};
    private static final List<String> translateAuditList = new ArrayList<>(Arrays.asList(translateList));

    private static final String ERROR = "ERROR";

    public static void main(String[] args) {
        String r = "{\"startDate\":\"2021-02-09 00:00\",\"endDate\":\"2021-02-09 23:59\",\"merchant\":\"\",\"product\":\"\",\"accountNumber\":\"\",\"status\":\"\",\"service\":\"searchlog\",\"apiSecret\":\"\",\"apiCode\":\"\",\"userCode\":\"[1],[17]|0060000244:0067510000010000,[2000000000000048]|000,[2000000000000049]|SCB:6PHY,[2000000000000053]|GOTV,[2000000000000054]|GOTV~TELESOL,[2000000000000060]|000,[2000000000000062]|ALL,[2000000000000063]|KNUST,[2000000000000064]|SurflineTopup,[2500000000000049]|2,[2500000000000050]|38,[29],[71]|0060010112,[91]|ALL\",\"admin\":\"\",\"type_desc\":\"\",\"role_id\":\"\",\"type_id\":\"[0],[1],[2],[3],[4],[5],[6],[7],[8],[9],[10],[11],[21],[25],[26],[27],[28],[30],[31],[33],[41],[43]\",\"searchKey\":\"\",\"userName\":\"\",\"user_id\":\"9000000000000490\",\"pageNumber\":1,\"rowsPerPage\":1,\"uniqueTransId\":\"\",\"trans_code\":\"\",\"transType\":\"ALL\",\"bank_code\":\"\",\"subscriberId\":\"\",\"trans_status\":\"\",\"channel\":\"\",\"bank\":\"\",\"biller\":\"\",\"transId\":\"\",\"bankid\":\"\",\"from_source\":\"\",\"card_num\":\"\",\"optionType\":\"\",\"payType\":\"\",\"wcMerchant\":\"\",\"destination\":\"\",\"lineType\":\"\",\"trans_channel\":\"\",\"pan\":\"\",\"terminal_id\":\"\",\"card_account\":\"\",\"cop_issuercode\":\"\",\"cop_role_id\":\"\",\"cop_company_id\":\"\",\"mobile_no\":\"\",\"phone\":\"\",\"type\":\"\",\"amount\":\"\",\"amount2\":\"\",\"refundStatus\":\"\",\"partialReversal\":\"\",\"reversalReason\":\"\",\"userData\":\"\",\"appId\":[],\"options\":{}}";

        final String a = "[2000000000000048]|00000000000000000002:0069900596470004~00000000000000000017:0068860077580002,[2000000000000049]|ALL~SCB:6AGAS,[2000000000000053]|DBANK,[2000000000000054]|MTN,[2000000000000064]|ALL,[2500000000000050]|77,[2500000000000051],[50]|3";

        String res = translateAuditTrail(a);

        System.out.println("Res::: " + res);
    }

    static {
        userOptions = loadUserOptions();
    }

    public AuditDao() {

    }

    public static HashMap<String, String> loadUserOptions() {
        HashMap<String, String> useroptions = new HashMap<>();

        String query = "";
        Session session = DbHibernate.getSession("APP_DB");
        query = "SELECT concat('[',type_id,']'), type_desc FROM telcodb.support_type;";

        try {

            Query q = session.createNativeQuery(query);
            List<Object[]> typeIdResp = q.getResultList();

            for (Object[] record : typeIdResp) {
                useroptions.put(record[0].toString(), record[1].toString());
            }

            query = "select query, dbIp, type_id  from telcodb.support_user_optionsQuery order by type_id asc";
            q = session.createNativeQuery(query);
            List<Object[]> resp = q.getResultList();

            for (Object[] record : resp) {
                final String sql = record[0].toString();
                final String dpIp = record[1].toString();
                final String typeId = record[2].toString();
                Session dbsession = null;
                try {

                    if (!(sql.isEmpty() && dpIp.isEmpty())) {

                        switch (dpIp) {
                            case "APP_DB":

                                dbsession = DbHibernate.getSession("APP_DB");
                                break;
                            default:
                                dbsession = DbHibernate.getSession(dpIp);
                                break;
                        }
                        List<RoleOption> options = new ArrayList<>();
                        if (dbsession != null) {
                            q = dbsession.createNativeQuery(sql, RoleOption.class);

                            options = q.getResultList();

                            for (RoleOption role : options) {
                                useroptions.put("[" + typeId + "]" + "-" + role.getAlias(), role.getName());
                            }
                        }

                    }
                } catch (Exception ey) {
                    log.error(ERROR, ey);
                } finally {
                    if (dbsession != null) {
                        try {
                            dbsession.close();
                        } catch (Exception u) {
                            log.error(ERROR, u);
                        }
                    }
                }
            }
        } catch (Exception et) {
            log.error(ERROR, et);
        } finally {
            if (session != null) {
                session.close();
            }

        }

        return useroptions;
    }

    public static String translateAuditTrail(String audit) {
        List<String> arr = new ArrayList<>();
        try {
            List<String> rolesList = Arrays.asList(audit.split(","));

            rolesList.stream().forEach(role -> {

                String[] rI = role.split("[|]");

                String roleId = rI[0];
                String r = userOptions.getOrDefault(roleId, roleId);

                String roleList = rI[1];
                List<String> roles = Arrays.asList(roleList.split("~"));
                List<String> arr2 = new ArrayList<>();
                roles.stream().forEach(roleCode -> {

                    arr2.add(userOptions.getOrDefault(roleId + "-" + roleCode, roleCode));
                });
                arr.add(r + ":(" + String.join(",", arr2) + ")");
            });

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return String.join(", ", arr);
    }

    public int insertIntoAuditTrail(AuditTrail auditTrail) {

        String action = auditTrail.getAction();

        String comment = auditTrail.getComment();

        String extra_info = auditTrail.getExtra_info();

        String user = auditTrail.getUser();

        String user_id = auditTrail.getUser_id();

        String ip_address = auditTrail.getIp_address();

//        log.info("VVV : " + auditTrail.toString() + " -- " + extra_info);
        int success = 0;
        Session session = DbHibernate.getSession("APP_DB");
        Transaction tx = null;
        try {

            tx = session.beginTransaction();

            String insertAudit
                    = "insert telcodb.support_audit_trail(action,comment, extra_info ,user_id, user,ip_address) values(:action,:comment, :extra_info ,:user_id, :user,:ip_address)";
            success = session.createNativeQuery(insertAudit)
                    .setParameter("action", action)
                    .setParameter("comment", comment)
                    .setParameter("extra_info", extra_info)
                    .setParameter("user_id", user_id)
                    .setParameter("user", user)
                    .setParameter("ip_address", ip_address)
                    .executeUpdate();
            tx.commit();

            log.info("Logged for audit successfully");
        } catch (Exception er) {
            log.error(ERROR, er);
            log.info("An Error Occured logging audit");
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return success;
    }

    public List<AuditTrail> getAuditTrail(String jsonString) {

        log.info("AUDIT request received >> " + jsonString);
        Gson j = new Gson();
        ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);

        String action = apiData.getTransType();
        String userId = apiData.getClientId();
        String start_date = apiData.getStartDate();
        String end_date = apiData.getEndDate();
        String ipaddress = apiData.getTerminal_id();
        String detail = apiData.getSearchKey();

        if (action == null) {
            action = "";
        }
        if (userId == null) {
            userId = "";
        }
        if (start_date == null) {
            start_date = "";
        }
        if (end_date == null) {
            end_date = "";
        }

        if (ipaddress == null) {
            ipaddress = "";
        }
        if (detail == null) {
            detail = "";
        }
        List<AuditTrail> auditTrail = new ArrayList<>();
        List<Object[]> records = new ArrayList<>();

        Session session = DbHibernate.getSession("APP_DB");
        try {

            String queryAudit
                    = "select a.action,a.comment,a.extra_info, b.username as user_id, c.username as user,a.ip_address,a.date from  telcodb.support_audit_trail a left join telcodb.support_user b on a.user_id = b.user_id left join telcodb.support_user c on a.user=c.user_id  where 1=1 "
                    + (!action.isEmpty() && !action.equalsIgnoreCase("all") ? " and a.action = :action " : "")
                    + (!start_date.isEmpty() && !end_date.isEmpty() ? " and a.date between :start_date and :end_date " : "")
                    + (!userId.isEmpty() ? " and (b.username = :userId or c.username =:userId) " : "")
                    + (!detail.isEmpty() ? " and (a.comment like :detail or a.extra_info like :detail) " : "")
                    + (!ipaddress.isEmpty() ? " and a.ip_address =:ipaddress " : "")
                    + " order by a.date desc";
            Query q = session.createNativeQuery(queryAudit);

            if (!action.isEmpty() && !action.equalsIgnoreCase("all")) {
                q.setParameter("action", action);
            }
            if (!start_date.isEmpty() && !end_date.isEmpty()) {
                q.setParameter("start_date", start_date).setParameter("end_date", end_date);
            }
            if (!userId.isEmpty()) {
                q.setParameter("userId", userId);
            }
            if (!ipaddress.isEmpty()) {
                q.setParameter("ipaddress", ipaddress);
            }
            if (!detail.isEmpty()) {
                q.setParameter("detail", detail);
            }

            records = q.getResultList();

            log.info("Audit Trail Records Success");
        } catch (Exception er) {
            log.error(ERROR, er);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        for (Object[] record : records) {
            AuditTrail at = new AuditTrail();
            try {
                at.setAction(checkNull(record[0]));
                at.setComment(checkNull(record[1]));

                if (translateAuditList.contains(at.getAction().toLowerCase())) {
                    at.setExtra_info(translateAuditTrail(checkNull(record[2])));
                } else {
                    at.setExtra_info(checkNull(record[2]));
                }
                at.setUser_id(checkNull(record[3]));
                at.setUser(checkNull(record[4]));
                at.setIp_address(checkNull(record[5]));
                at.setDate(checkNull(record[6]));
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            auditTrail.add(at);
        }

        return auditTrail;
    }

    public List<NODES> getAuditActions() {
        List<NODES> actions = new ArrayList<>();

        Session session = DbHibernate.getSession("APP_DB");
        try {

            String sql = "SELECT action id, UPPER(action) name FROM telcodb.support_audit_trail group by action order by action asc";

            actions = session.createNativeQuery(sql, NODES.class).getResultList();

        } catch (Exception er) {
            log.error(ERROR, er);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return actions;
    }

    protected String checkNull(Object Data) {
        if (Data != null && !Data.equals("")) {
            return Data.toString();
        }
        return "";
    }

}
