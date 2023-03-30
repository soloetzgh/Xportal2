package com.etzgh.xportal.dao;

import com.etzgh.xportal.controller.HttpSessionCollector;
import com.etzgh.xportal.controller.PortalSettings;
import com.etzgh.xportal.model.ApiPostData;
import com.etzgh.xportal.model.AuditTrail;
import com.etzgh.xportal.model.Bank;
import com.etzgh.xportal.model.BucketBalanceType;
import com.etzgh.xportal.model.Channel;
import com.etzgh.xportal.model.MenuOptions;
import com.etzgh.xportal.model.NODES;
import com.etzgh.xportal.model.PeriodData;
import com.etzgh.xportal.model.PortalSettingsData;
import com.etzgh.xportal.model.RoleInfo;
import com.etzgh.xportal.model.RoleInfoData;
import com.etzgh.xportal.model.RoleOption;
import com.etzgh.xportal.model.User;
import com.etzgh.xportal.model.UserInfo;
import com.etzgh.xportal.model.UserManagement;
import com.etzgh.xportal.utility.Alert;
import com.etzgh.xportal.utility.Config;
import com.etzgh.xportal.utility.CryptographerMin;
import com.etzgh.xportal.utility.DbHibernate;
import com.etzgh.xportal.utility.GeneralUtility;
import com.etzgh.xportal.utility.HibernateUtil;
import com.etzgh.xportal.utility.PasswordGenerator;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.persistence.NoResultException;
import org.apache.commons.lang.RandomStringUtils;
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
public class AppDao {

    private static final Gson gson = new Gson();
    private static final Config config = new Config();

    private static final String path = config.getProperty("APPLICATION_API_PATH");

    private static final Logger log = Logger.getLogger(AppDao.class.getName());
    private static final Map<String, String> bankDB = new HashMap<>();

    // private static HashMap<String, String> userOptions = new HashMap<>();
    private static final Properties gipProp = new Properties();
    private static final String ERROR = "ERROR";

    public AppDao() {
    }

    static {

        bankDB.put("004", "GCB");
        bankDB.put("021", "BOA");
        bankDB.put("920", "ABII");
        bankDB.put("905", "BESTPOINT");
        bankDB.put("005", "NIB");

        try {
            InputStream is = new FileInputStream(new File("cfg" + File.separator + "etzgipcodemapping.properties"));
            gipProp.load(is);

        } catch (Exception e) {
            log.error(ERROR, e);
        }
    }

    public Session openConnection() {
        Session session = HibernateUtil.getSession();
        return session;
    }

    public void closeConnection(Session session) {
        if (session != null) {
            session.close();
        }
    }

    public static void main(String[] args) {

        new AppDao().fetchUserRoles(
                "{\"startDate\":\"\",\"endDate\":\"\",\"merchant\":\"\",\"product\":\"\",\"accountNumber\":\"\",\"status\":\"\",\"service\":\"fetchUserRoles\",\"action\":\"\",\"apiSecret\":\"\",\"apiCode\":\"\",\"userCode\":\"[2],[2000000000000049]|ALL~ETZ:6AKA,[2000000000000060]|004,[2500000000000050]|58,[2500000000000052]\",\"admin\":\"\",\"type_desc\":\"\",\"role_id\":\"\",\"type_id\":\"[4],[5],[6],[7],[9],[20],[24],[26],[39],[40],[43]\",\"searchKey\":\"\",\"userName\":\"\",\"ClientId\":\"10500000000000754\",\"user_id\":\"10500000000000754\",\"pageNumber\":1,\"rowsPerPage\":1,\"uniqueTransId\":\"\",\"trans_code\":\"\",\"transType\":\"\",\"subscriberId\":\"\",\"trans_status\":\"\",\"channel\":\"\",\"bank\":\"\",\"biller\":\"\",\"transId\":\"\",\"bankid\":\"\",\"from_source\":\"\",\"card_num\":\"\",\"optionType\":\"\",\"payType\":\"\",\"wcMerchant\":\"\",\"destination\":\"\",\"lineType\":\"\",\"trans_channel\":\"\",\"pan\":\"\",\"terminal_id\":\"\",\"card_account\":\"\",\"cop_issuercode\":\"\",\"cop_role_id\":\"\",\"cop_company_id\":\"\",\"mobile_no\":\"\",\"phone\":\"\",\"type\":\"\",\"amount\":\"\",\"amount2\":\"\",\"refundStatus\":\"\",\"partialReversal\":\"\",\"reversalReason\":\"\",\"ipAddress\":\"0:0:0:0:0:0:0:1\",\"userData\":\"\",\"appId\":[],\"company\":\"Etranzact\",\"options\":{}}");
    }

    public String getBranchCode(String bankCode, String branchCode) {
        String branch = "";
        try {
            if (!branchCode.equalsIgnoreCase("ALL")) {
                switch (bankCode) {
                    case "005":
                        branch = branchCode.substring(1);
                        break;
                    default:
                        branch = branchCode;
                        break;
                }
            } else {
                branch = branchCode;
            }
        } catch (Exception e) {
            log.error(ERROR, e);
        }
        return branch;
    }

    public String login(String username, String password, String requestOrigin) {

        User user = new User();
        JSONObject json = new JSONObject();
        json.put("code", "00");
        String login_attempts = getSetting("login_attempt");

        int allowed_attempts = !login_attempts.equals("null") ? Integer.valueOf(login_attempts.split(":")[0]) : 3;
        Boolean userExists = false;
        String message = "Account Not Found";

        Session session = DbHibernate.getSession("APP_DB");
        Transaction tx = null;
        String otp = PasswordGenerator.generateOtp(Integer.valueOf(getSetting("otp_length").split(":")[0]));
        final Boolean otp_sms = getSetting("otp_sms").split(":")[0].equalsIgnoreCase("yes");
        final Boolean otp_email = getSetting("otp_email").split(":")[0].equalsIgnoreCase("yes");

        try {

            tx = session.beginTransaction();
            String crypt_password = CryptographerMin.doMd5Hash(username + password);

            String query = "select a.user_id, a.email, a.password, a.lastname,a.firstname, a.status_id, b.status_desc, a.username,a.first_logon, a.mobile,"
                    + "(case when temp_password = :password then 'true' else 'false' end) temp_password, "
                    + "(case when a.status_id = 2 then 'deactivated' when a.status_id = 3 then 'locked'  else 'active' end) status, "
                    + "(case when (a.password = :password or a.temp_password = :password) then 'true' else 'false' end) valid_password, a.login_attempts, "
                    + "(case when a.status_id = 5 then 'true' else 'false' end) password_expired, "
                    + "(case when require2fa=1 then 'true' else 'false' end) require2fa, a.company, a.user_code, a.branch_code "
                    + "from telcodb.support_user a left join telcodb.support_status b on b.status_id=a.status_id "
                    + " where a.username = binary :username";

            Query q = session.createNativeQuery(query)
                    .setParameter("username", username)
                    .setParameter("password", crypt_password);
            Object[] record = (Object[]) q.getSingleResult();

            userExists = true;

            json.put("userId", record[0].toString());
            int count = (Integer.valueOf(record[13].toString()));
            String account_status = record[11].toString();

            User tempUser = new User();
            String userRoles = getAllUserRoles(record[0].toString());
            userRoles = (userRoles == null ? "" : userRoles);
            tempUser.setType_id(getUserRoleList(record[0].toString()));
            tempUser.setUser_code(userRoles);

            JSONObject checkOrigin = GeneralUtility.checkOrigin(tempUser, requestOrigin);

            if (!checkOrigin.getString("code").equals("00")) {

                return checkOrigin.toString();
            }

            if (!(account_status.equals("locked") || account_status.equals("deactivated"))) {
                message = "Invalid Credentials";

                if (record[12].toString().equals("false")) {

                    json.put("userExists", userExists);
                    json.put("message", message);
                    json.put("extra_info",
                            ((count + 1) == allowed_attempts ? "Account Locked"
                                    : "Attempts: " + (count + 1) + ". Account would be locked after " + allowed_attempts
                                    + " unsuccessful attempts"));
                    count++;

                    String qry = "update telcodb.support_user set login_attempts = login_attempts + 1 , "
                            + " status_id = (case when login_attempts = :attempts then 3 else status_id end) "
                            + " where username = binary :username";
                    int i = session.createNativeQuery(qry)
                            .setParameter("username", username)
                            .setParameter("attempts", allowed_attempts)
                            .executeUpdate();
                    log.info("Executed : " + i);

                } else if (!(account_status.equals("deactivated"))) {

                    String query2 = "update telcodb.support_user set status_id = 1, login_attempts=0 ,last_login = now() "
                            + (record[12].toString().equalsIgnoreCase("false") ? " , temp_password = '' " : "")
                            + "  where user_id = :userId";
                    session.createNativeQuery(query2)
                            .setParameter("userId", record[0].toString())
                            .executeUpdate();

                    user.setUser_id(checkNull(record[0]));
                    user.setEmail(checkNull(record[1]));
                    user.setLastname(checkNull(record[3]));
                    user.setFirstname(checkNull(record[4]));
                    user.setType_id(getUserRoleList(checkNull(record[0])));

                    user.setStatus_id(checkNull(record[6]));

                    user.setUsername(username);
                    user.setFirst_logon(checkNull(record[8]));
                    user.setMobile(checkNull(record[9]));
                    user.setTemp_password(checkNull(record[10]));

                    user.setUser_code(userRoles);
                    user.setRequires2FA(checkNull(record[15]).equals("true"));
                    user.setCompany(checkNull(record[16]));
                    user.setBankCode(checkNull(record[17]));
                    user.setBranchCode(getBranchCode(checkNull(record[17]), checkNull(record[18])));
                    user.setAdmin(userRoles.contains("[1]") ? "ETZ" : (userRoles.contains("[2]") ? "EXT" : "NONE"));

                    log.info("Req:" + user.getRequires2FA() + " ~ " + record[15].toString());
                    if (user.getRequires2FA() == true) {

                        String oldOtp = (String) session.createNativeQuery(
                                "select token from telcodb.otp_expiry where user_id =:userId and expired=0 and type='auth' order by id desc limit 1;")
                                .setParameter("userId", record[0].toString()).getResultList().stream().findFirst()
                                .orElse(null);

                        log.info("OLD OTP: " + oldOtp == null ? "null" : oldOtp);

                        if (oldOtp == null) {
                            String otpQry = "insert into  telcodb.otp_expiry(user_id, token,type) values(:userId, :otp,'auth'); ";
                            session.createNativeQuery(otpQry)
                                    .setParameter("userId", record[0].toString())
                                    .setParameter("otp", otp)
                                    .executeUpdate();
                        } else {
                            otp = oldOtp;
                        }
                        final String pass = otp;
                        new Thread(() -> {
                            if (otp_email) {
                                new Alert().processEmail(record[1].toString(), user.getFirstname(), pass, "", "otp",
                                        requestOrigin);
                            }
                            if (otp_sms && (user.getMobile() != null && !user.getMobile().isEmpty())) {
                                new Alert().sendSMS(user.getMobile(), createMsg(user.getFirstname(), pass),
                                        "Etranzact");
                            }
                        }).start();
                        user.setIsLoggedIn(false);
                    } else {
                        user.setIsLoggedIn(true);
                    }

                    String nextRoute = getFirstRoute(user.getUser_code(), user.getTemp_password(), path);
                    user.setFirstRoute(nextRoute);

                    json.put("userData", new JSONObject(user).toString());
                    json.put("userExists", true);
                    json.put("extra_info", record[14].toString().equals("true") ? "Password expired" : "");
                    json.put("message", "Successful Login");

                } else {
                    json.put("userExists", true);
                    json.put("message", "Account " + account_status);
                    json.put("userId", record[0].toString());
                }
            } else {

                json.put("userExists", userExists);
                json.put("message", "Account " + account_status);
            }

            tx.commit();

        } catch (NoResultException ed) {

            json.put("userExists", userExists);

            json.put("message", message);
        } catch (Exception s) {
            log.error(ERROR, s);
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return json.toString();

    }

    public Boolean verify2FA(String userId, String otp) {
        Boolean valid = false;
        Session session = DbHibernate.getSession("APP_DB");
        Transaction tx = null;
        try {

            tx = session.beginTransaction();
            String query = "select * from telcodb.otp_expiry where user_id = :userid and token = :token and expired=0 AND NOW() <= DATE_ADD(created_at, INTERVAL 24 HOUR) limit 1;";
            Query q = session.createNativeQuery(query)
                    .setParameter("token", otp)
                    .setParameter("userid", userId);
            Object[] record = (Object[]) q.getSingleResult();
            if (record != null) {
                String query2 = "update telcodb.otp_expiry set expired = 1, used_at = now() where token = :otp and user_id =:userId";
                int i = session.createNativeQuery(query2)
                        .setParameter("otp", otp)
                        .setParameter("userId", userId)
                        .executeUpdate();
                log.info("Executed : " + i);
            }

            tx.commit();
            valid = true;
        } catch (Exception e) {
            log.error(ERROR, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return valid;
    }

    public JSONObject changePassword(String user_id, String old_password, String password, String username,
            String email, boolean clearAll, String sessionId) {
        String code = "01";
        String result = "An Error Occured";
        JSONObject resp = new JSONObject();

        try {
            String oldcrypt_password = CryptographerMin.doMd5Hash(username + old_password);
            String crypt_password = CryptographerMin.doMd5Hash(username + password);
            String hashedPassword = CryptographerMin.hashWithBCrypt(password);
            String pass_history = getSetting("password_history").split(":")[0];
            int passwordHistoryLimit = (pass_history.isEmpty() || pass_history.equals("null")) ? 10
                    : Integer.parseInt(pass_history);

            Session session = DbHibernate.getSession("APP_DB");
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                String query = "select username, (case when a.status_id = 2 then 'deactivated' when a.status_id = 3 then 'locked'  else 'active' end) status from telcodb.support_user a where (password = :password or temp_password = :password) and user_id = :userId";
                Query q = session.createNativeQuery(query)
                        .setParameter("password", oldcrypt_password)
                        .setParameter("userId", user_id);
                Object[] record = (Object[]) q.getSingleResult();

                if (record[1].toString().equals("active")) {

                    boolean passHistoryCheck = false;
                    String passHistory = "select password from telcodb.support_password_history where user_id =:user_id order by created desc limit :limit";
                    List<String> passwordHistory = session.createNativeQuery(passHistory)
                            .setParameter("user_id", user_id)
                            .setParameter("limit", passwordHistoryLimit)
                            .getResultList();

                    if (!passwordHistory.isEmpty()) {
                        boolean found = false;

                        for (String pass : passwordHistory) {
                            if (CryptographerMin.validateBCryptHash(password, pass)) {
                                found = true;

                                break;
                            }
                        }
                        passHistoryCheck = !found;
                    } else {
                        passHistoryCheck = true;
                    }

                    if (passHistoryCheck) {
                        String query2 = "update telcodb.support_user set password = :password, first_logon = '1', status_id = 1, last_login = now(), last_password_change=now(),  temp_password = '', email = :email  where user_id = :userId";
                        int i = session.createNativeQuery(query2)
                                .setParameter("password", crypt_password)
                                .setParameter("email", email)
                                .setParameter("userId", user_id)
                                .executeUpdate();

                        if (i > 0) {

                            String query4 = "insert into telcodb.support_password_history (user_id, password) values (:user_id,:password);";
                            int k = session.createNativeQuery(query4)
                                    .setParameter("user_id", user_id)
                                    .setParameter("password", hashedPassword)
                                    .executeUpdate();
                            if (k > 0) {
                                code = "00";
                                result = "Password Change Successful";
                                if (clearAll) {
                                    HttpSessionCollector.removeAllUserSessions(user_id);
                                } else {
                                    HttpSessionCollector.removeOtherUserSessions(user_id, sessionId);
                                }
                            }
                        }
                    } else {
                        result = "Password was recently used";
                    }
                } else {
                    result = "Your account is " + record[1].toString();
                }

                tx.commit();
            } catch (NoResultException ex) {
                result = "Invalid Credentials Entered";
            } catch (Exception ex) {
                log.error(ERROR, ex);
                result = "An error occured. Please try again";
            } finally {
                if (session != null) {
                    session.close();
                }
            }
        } catch (Exception it) {
            log.error(ERROR, it);
            result = "An error occured. Please try again";
        }

        resp.put("code", code);
        resp.put("message", result);
        return resp;
    }

    public JSONObject changePasswordWithToken(String token, String password, String requestOrigin) {

        JSONObject resp = new JSONObject();
        resp.put("code", "01");
        resp.put("message", "Password reset with link failed");
        try {
            String otp_lifetime = getSetting("otp_lifetime");
            String pass_history = getSetting("password_history").split(":")[0];
            int passwordHistoryLimit = (pass_history.isEmpty() || pass_history.equals("null")) ? 10
                    : Integer.parseInt(pass_history);

            if (otp_lifetime != null) {
                String data = (otp_lifetime.split("-")[0]);
                String data1 = otp_lifetime.split("-").length > 1 ? otp_lifetime.split("-")[1] : "default";
                String status = otp_lifetime.split(":")[1];
                if (status.equals("1")) {

                    if (data1.equals("default")) {
                        data = "1";
                        data1 = "hour";
                    }

                    switch (data1) {
                        case "minute":
                            data = String.valueOf(Integer.valueOf(data));
                            break;
                        case "hour":
                            data = String.valueOf(Integer.valueOf(data) * 60);
                            break;
                        case "day":
                            data = String.valueOf(Integer.valueOf(data) * 60 * 24);
                            break;
                        case "week":
                            data = String.valueOf(Integer.valueOf(data) * 60 * 24 * 7);
                            break;
                        case "month":
                            data = String.valueOf(Integer.valueOf(data) * 60 * 24 * 7 * 4);
                            break;
                        case "year":
                            data = String.valueOf(Integer.valueOf(data) * 60 * 24 * 7 * 4 * 12);
                            break;
                        default:

                            break;

                    }

                    String hashedPassword = CryptographerMin.hashWithBCrypt(password);

                    Session session = DbHibernate.getSession("APP_DB");
                    Transaction tx = null;
                    try {
                        tx = session.beginTransaction();
                        String query = "select a.user_id, a.username, (case when TIMESTAMPDIFF(MINUTE,b.created_at, now()) < :data then 'Reset link has expired' when b.expired != 0 then 'Reset link already used' else concat('Reset password for ', a.username) end) status_msg, "
                                + " (case when (TIMESTAMPDIFF(MINUTE,b.created_at, now()) >= :data or b.expired != 0) then '0' else '1' end) status, (case when a.status_id = 2 then 'deactivated' when a.status_id = 3 then 'locked'  else 'active' end) profile_status, a.firstname, a.lastname from telcodb.support_user a left join telcodb.otp_expiry b on a.user_id = b.user_id where b.token = :token and b.type='reset' limit 1 ";

                        Query q = session.createNativeQuery(query)
                                .setParameter("token", token)
                                .setParameter("data", data);
                        Object[] record = (Object[]) q.getSingleResult();
                        resp.put("user_id", record[0].toString());

                        String userId = record[0].toString();

                        User tempUser = new User();
                        String userRoles = getAllUserRoles(record[0].toString());
                        userRoles = (userRoles == null ? "" : userRoles);
                        tempUser.setType_id(getUserRoleList(record[0].toString()));
                        tempUser.setUser_code(userRoles);

                        JSONObject checkOrigin = GeneralUtility.checkOrigin(tempUser, requestOrigin);

                        if (!checkOrigin.getString("code").equals("00")) {

                            return checkOrigin;
                        }

                        User user = new User();
                        user.setUsername(record[1].toString());
                        user.setFirstname(record[5].toString());
                        user.setLastname(record[6].toString());

                        if (PasswordGenerator.containDetails(password, user)) {
                            resp.put("message", "Password must not contain logon Id or personal name(s)");
                        } else if (record[4].toString().equals("active")) {

                            if (record[3].toString().equals("1")) {

                                boolean passHistoryCheck = false;
                                String passHistory = "select password from telcodb.support_password_history where user_id =:user_id order by created desc limit :limit";
                                List<String> passwordHistory = session.createNativeQuery(passHistory)
                                        .setParameter("user_id", userId)
                                        .setParameter("limit", passwordHistoryLimit)
                                        .getResultList();

                                System.out.println("RECORDS ::: " + passwordHistory);

                                if (!passwordHistory.isEmpty()) {
                                    boolean found = false;

                                    for (String pass : passwordHistory) {
                                        if (CryptographerMin.validateBCryptHash(password, pass)) {
                                            found = true;

                                            break;
                                        }
                                    }
                                    passHistoryCheck = !found;
                                } else {
                                    passHistoryCheck = true;
                                }

                                if (passHistoryCheck) {

                                    String crypt_password = CryptographerMin.doMd5Hash(record[1].toString() + password);

                                    String query2 = "update telcodb.support_user set password = :password, status_id = 1, first_logon = '1', last_login = now(), last_password_change=now(),  temp_password = '' where user_id = :userId";
                                    int i = session.createNativeQuery(query2)
                                            .setParameter("password", crypt_password)
                                            .setParameter("userId", userId)
                                            .executeUpdate();
                                    if (i > 0) {

                                        String query3 = "update telcodb.otp_expiry set expired = 1, used_at = now() where token = :token";
                                        int j = session.createNativeQuery(query3)
                                                .setParameter("token", token)
                                                .executeUpdate();

                                        if (j > 0) {
                                            String query4 = "insert into telcodb.support_password_history (user_id, password) values (:user_id,:password);";
                                            int k = session.createNativeQuery(query4)
                                                    .setParameter("user_id", userId)
                                                    .setParameter("password", hashedPassword)
                                                    .executeUpdate();

                                            if (k > 0) {
                                                resp.put("code", "00");
                                                resp.put("message", "Password reset with link successful");
                                                HttpSessionCollector.removeAllUserSessions(record[0].toString());
                                            }
                                        }
                                    }
                                } else {
                                    resp.put("message", "Password was used recently");
                                }
                            } else {
                                resp.put("message", record[2].toString().equals("1"));
                            }
                        } else {
                            resp.put("message", "Your Account has been " + record[4].toString());

                        }

                        tx.commit();
                    } catch (NoResultException ex) {
                        resp.put("message", "Invalid Reset link");
                    } catch (Exception ex) {
                        log.error(ERROR, ex);
                        if (tx != null) {
                            try {
                                tx.rollback();
                            } catch (Exception e) {
                                log.error(ERROR, e);
                            }
                        }
                        resp.put("message", "An error occured. Please try again");
                    } finally {
                        if (session != null) {
                            session.close();
                        }
                    }
                }
            }
        } catch (Exception it) {
            log.error(ERROR, it);

            resp.put("message", "An error occured. Please try again");
        }

        return resp;
    }

    public String checkTokenValidity(String token) {
        JSONObject resp = new JSONObject();
        resp.put("message", "Invalid Token");
        resp.put("code", "01");
        try {
            String otp_lifetime = getSetting("otp_lifetime");

            if (otp_lifetime != null) {
                String data = (otp_lifetime.split("-")[0]);
                String data1 = otp_lifetime.split("-").length > 1 ? otp_lifetime.split("-")[1] : "default";
                String status = otp_lifetime.split(":")[1];
                if (status.equals("1")) {

                    if (data1.equals("default")) {
                        data = "1";
                        data1 = "hour";
                    }

                    switch (data1) {
                        case "minute":
                            data = String.valueOf(Integer.valueOf(data));
                            break;
                        case "hour":
                            data = String.valueOf(Integer.valueOf(data) * 60);
                            break;
                        case "day":
                            data = String.valueOf(Integer.valueOf(data) * 60 * 24);
                            break;
                        case "week":
                            data = String.valueOf(Integer.valueOf(data) * 60 * 24 * 7);
                            break;
                        case "month":
                            data = String.valueOf(Integer.valueOf(data) * 60 * 24 * 7 * 4);
                            break;
                        case "year":
                            data = String.valueOf(Integer.valueOf(data) * 60 * 24 * 7 * 4 * 12);
                            break;
                        default:
                            break;
                    }

                    Session session = DbHibernate.getSession("APP_DB");

                    try {
                        String query = "select a.user_id, a.username, (case when TIMESTAMPDIFF(MINUTE,b.created_at, now()) >= :data then 'Reset link has expired' when b.expired != 0 then 'Reset link already used' else concat('Reset password for ', a.username) end) status_msg, (case when (TIMESTAMPDIFF(MINUTE,b.created_at, now()) >= :data or b.expired != 0) then '0' else '1' end) status, "
                                + " a.firstname, a.lastname from telcodb.otp_expiry b left join telcodb.support_user a on a.user_id = b.user_id where b.token = :token and b.type='reset'";
                        Query q = session.createNativeQuery(query)
                                .setParameter("token", token)
                                .setParameter("data", data);
                        Object[] record = (Object[]) q.getSingleResult();
                        resp.put("code", record[3].toString().equals("1") ? "00" : "01");
                        resp.put("message", record[2].toString());
                        if (record[3].toString().equals("1")) {
                            resp.put("username", record[1].toString());
                            resp.put("firstname", record[4].toString());
                            resp.put("lastname", record[5].toString());
                        }

                    } catch (NoResultException ex) {
                        resp.put("message", "Invalid Reset Link");
                    } catch (Exception ex) {
                        log.error(ERROR, ex);
                        resp.put("message", "An error occured. Please try again");
                    } finally {
                        if (session != null) {
                            session.close();
                        }
                    }
                }
            }
        } catch (Exception it) {
            log.error(ERROR, it);
            resp.put("message", "An error occured. Please try again");
        }

        return resp.toString();
    }

    public JSONObject resetPassword(String username, String requestOrigin, boolean admin) {
        String result = "An Error Occured";
        String code = "01";

        JSONObject resp = new JSONObject();
        if (username.isEmpty()) {
            result = "Username is invalid";
        } else {
            String otp_lifetime = getSetting("otp_lifetime");

            if (otp_lifetime != null) {
                String data = (otp_lifetime.split("-")[0]);
                String data1 = otp_lifetime.split("-").length > 1 ? otp_lifetime.split("-")[1] : "default";
                String status = otp_lifetime.split(":")[1];
                if (status.equals("1")) {

                    if (data1.equals("default")) {
                        data = "1";
                        data1 = "hour";
                    }

                    switch (data1) {
                        case "minute":
                            data = String.valueOf(Integer.valueOf(data));
                            break;
                        case "hour":
                            data = String.valueOf(Integer.valueOf(data) * 60);
                            break;
                        case "day":
                            data = String.valueOf(Integer.valueOf(data) * 60 * 24);
                            break;
                        case "week":
                            data = String.valueOf(Integer.valueOf(data) * 60 * 24 * 7);
                            break;
                        case "month":
                            data = String.valueOf(Integer.valueOf(data) * 60 * 24 * 7 * 4);
                            break;
                        case "year":
                            data = String.valueOf(Integer.valueOf(data) * 60 * 24 * 7 * 4 * 12);
                            break;
                        default:
                            break;
                    }
                }

                try {

                    Session session = DbHibernate.getSession("APP_DB");
                    Transaction tx = null;
                    try {
                        tx = session.beginTransaction();
                        String query = "select user_id, firstname, email from telcodb.support_user where (username = :username or user_id =:username) ";
                        Query q = session.createNativeQuery(query);
                        q.setParameter("username", username);

                        Object[] record = (Object[]) q.getSingleResult();

                        if (record != null) {

                            if (!admin) {
                                User tempUser = new User();
                                String userRoles = getAllUserRoles(record[0].toString());
                                userRoles = (userRoles == null ? "" : userRoles);
                                tempUser.setType_id(getUserRoleList(record[0].toString()));
                                tempUser.setUser_code(userRoles);

                                JSONObject checkOrigin = GeneralUtility.checkOrigin(tempUser, requestOrigin);

                                if (!checkOrigin.getString("code").equals("00")) {

                                    return checkOrigin;
                                }
                            }

                            log.info("CHECK Origin::: " + requestOrigin);

                            if (!(record[2].toString().trim()).isEmpty()) {

                                String generatedToken = RandomStringUtils.random(25, true, true);

                                String oldOtp = (String) session.createNativeQuery(
                                        "select concat(token,'|',TIMESTAMPDIFF(MINUTE,created_at, now()) >= :data) from telcodb.otp_expiry where user_id =:userId and expired=0  and type='reset' order by id desc limit 1;")
                                        .setParameter("userId", record[0].toString())
                                        .setParameter("data", data)
                                        .getResultList().stream().findFirst()
                                        .orElse(null);

                                if (oldOtp == null) {

                                    String otpQry = "insert into telcodb.otp_expiry(user_id, token,type) values(:userId, :token,'reset'); ";
                                    session.createNativeQuery(otpQry)
                                            .setParameter("userId", record[0].toString())
                                            .setParameter("token", generatedToken)
                                            .executeUpdate();
                                } else if (oldOtp.split("[|]")[1].equalsIgnoreCase("1")) {
                                    String otpQry = "update telcodb.otp_expiry set expired = 1 where token =:token ";
                                    session.createNativeQuery(otpQry)
                                            .setParameter("token", oldOtp.split("[|]")[0])
                                            .executeUpdate();

                                    String otpQry1 = "insert into telcodb.otp_expiry(user_id, token,type) values(:userId, :token,'reset'); ";
                                    session.createNativeQuery(otpQry1)
                                            .setParameter("userId", record[0].toString())
                                            .setParameter("token", generatedToken)
                                            .executeUpdate();
                                } else {
                                    generatedToken = oldOtp.split("[|]")[0];
                                }

                                final String token = generatedToken;
                                new Thread(() -> {
                                    new Alert().processEmail(record[2].toString(), record[1].toString(), token, "",
                                            "reset", requestOrigin);
                                }).start();
                                code = "00";
                                result = "Password Reset link sent to: " + record[2].toString();

                            } else {
                                result = "Email not set for Account !!!";
                            }
                        } else {
                            result = "Account not found";
                        }
                        tx.commit();
                    } catch (NoResultException ex) {

                        result = "Account not found";
                    } catch (Exception ex) {
                        log.error(ERROR, ex);

                        result = "An error occured !!!";

                    } finally {
                        if (session != null) {
                            session.close();
                        }
                    }
                } catch (Exception kl) {
                    log.error(ERROR, kl);
                }
            }
        }

        resp.put("code", code);
        resp.put("message", result);

        return resp;

    }

    public String resetPasswordAdmin(String jsonString) {
        log.info("Reset User Password request received >> " + jsonString);

        ApiPostData apiData = gson.fromJson(jsonString, ApiPostData.class);
        String userId = apiData.getClientId();
        String user = apiData.getUser_id();
        String requestOrigin = apiData.getOrigin();

        String result = "An error occured !!!";
        try {

            JSONObject json = resetPassword(userId, requestOrigin, true);
            final String resetResultMessage = json.getString("message");
            result = resetResultMessage;
            new Thread(() -> {
                AuditTrail audit = new AuditTrail(user, resetResultMessage, "authentication", userId, "Password Reset",
                        apiData.getIpAddress());
                new AuditDao().insertIntoAuditTrail(audit);
            }).start();

        } catch (Exception kl) {
            log.error(ERROR, kl);
        }

        return result;
    }

    public String checkUsers(String username) {
        boolean userExists = false;
        String message = "";
        try {
            if (!username.trim().isEmpty()) {
                Session session = DbHibernate.getSession("APP_DB");

                try {
                    String query = "select username from telcodb.support_user where username = :username ";

                    Query q = session.createNativeQuery(query).setParameter("username", username);

                    List<String> res = (List<String>) q.getResultList();
                    if (res != null && res.size() > 0) {
                        userExists = true;
                    }

                    message = userExists ? "Username is Unavailable" : "Username is Available";
                } catch (Exception io) {
                    log.error(ERROR, io);
                } finally {
                    if (session != null) {
                        session.close();
                    }
                }
            }
        } catch (Exception ex) {
            log.error(ERROR, ex);
            message = "An Error occured";
        }

        return message;
    }

    public void updateLastLogin(String userId) {
        Boolean result = false;
        log.info("Update >> " + userId);
        Transaction tx = null;
        Session session = DbHibernate.getSession("APP_DB");
        try {

            tx = session.beginTransaction();
            String qry = "update telcodb.support_user set last_login = now() where user_id = :user_id";
            session.createNativeQuery(qry)
                    .setParameter("user_id", userId)
                    .executeUpdate();

            tx.commit();
            result = true;
        } catch (Exception ex) {
            log.error(ERROR, ex);
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }

    public String updateUserRole(String username, String role) {
        Transaction tx = null;
        Session session = DbHibernate.getSession("APP_DB");
        try {

            tx = session.beginTransaction();
            String query = "update telcodb.support_user set role_id = :roleId where username = :username";
            int i = session.createNativeQuery(query)
                    .setParameter("roleId", role)
                    .setParameter("username", username)
                    .executeUpdate();
            tx.commit();
            if (i > 0) {
                return "success";
            } else {
                return "user not found";
            }

        } catch (Exception ex) {
            return "An error occured. Please try again";
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }

    public List<UserManagement> getUsers(String jsonString) {

        log.info("Users request received >> " + jsonString);

        ApiPostData apiData = gson.fromJson(jsonString, ApiPostData.class);

        String username = apiData.getUserName();

        HashMap<String, Object> options = apiData.getOptions();

        String order = (String) options.getOrDefault("order", "");
        String sort_by = (String) options.getOrDefault("sort_by", "");
        String startDate = (String) options.getOrDefault("startDate", "");
        String endDate = (String) options.getOrDefault("endDate", "");
        String status = (String) options.getOrDefault("status", "");
        String secure = (String) options.getOrDefault("2fa", "");
        String type = (String) options.getOrDefault("type", "");
        String company = (String) options.getOrDefault("company", "");
        String userCategory = (String) options.getOrDefault("userType", "ALL");

        String between = !(startDate.isEmpty() || endDate.isEmpty()) ? " between :startDate and :endDate " : "";
        String betweenQry = "";
        switch (sort_by) {
            case "last_login":
                betweenQry = (!between.isEmpty() ? " and a.last_login " + between : "");
                sort_by = " order by a.last_login ";
                break;
            case "created":
                betweenQry = (!between.isEmpty() ? " and a.create_date " + between : "");
                sort_by = " order by a.create_date ";
                break;
            case "modified":
                betweenQry = (!between.isEmpty() ? " and a.modified_date " + between : "");
                sort_by = " order by a.modified_date ";
                break;
            case "last_password_change":
                betweenQry = (!between.isEmpty() ? " and a.last_password_change " + between : "");
                sort_by = " order by a.last_password_change ";
                break;
            case "disabled":
                betweenQry = (!between.isEmpty() ? " and a.deactivated_date " + between : "");
                sort_by = " order by a.deactivated_date ";
                break;
            default:
                betweenQry = (!between.isEmpty() ? " and a.last_login " + between : "");
                sort_by = " order by a.last_login ";
                break;
        }
        String statusQry = "";
        switch (status) {
            case "active":
                statusQry = " and b.status_id=1 ";
                break;

            case "disabled":
                statusQry = " and b.status_id=2 ";
                break;
            case "locked":
                statusQry = " and b.status_id=3 ";
                break;
            case "inactive":
                statusQry = " and b.status_id=4 ";
                break;
            case "password_expired":
                statusQry = " and b.status_id=5 ";
                break;
            default:
                break;
        }
        switch (userCategory) {
            case "ADMIN":
                userCategory = "1";
                break;
            case "EXTADMIN":
                userCategory = "2";
                break;

            default:
                userCategory = "ALL";
                break;

        }

        String query = "select distinct(a.user_id), a.email, a.lastname, a.firstname, a.username, a.company ,a.create_date, a.user_code, a.mobile,a.card_scheme cardScheme, "
                + " a.cardscheme_numbers cardSchemeNumbers, a.first_logon, a.type_id, a.role_id, a.admin, a.status_id, b.status_desc, (case when a.require2fa = 1 then 'true' else 'false' end) require2fa, a.branch_code, a.last_login, a.deactivated_date "
                + " from telcodb.support_user a left join telcodb.support_status b on a.status_id = b.status_id left join telcodb.support_user_access c on c.user_id = a.user_id where 1=1 "
                + statusQry
                + (secure.equalsIgnoreCase("all") || secure.isEmpty() ? ""
                : (secure.equalsIgnoreCase("yes") ? " and a.require2fa=1 " : " and a.require2fa != 1 "))
                + (username != null && !username.trim().isEmpty() ? (type.equalsIgnoreCase("singleuser")
                ? " and a.username = :username "
                : " and (a.username LIKE :username OR a.FIRSTNAME LIKE :username OR a.LASTNAME LIKE :username) ")
                : "")
                + betweenQry
                + (!company.isEmpty() ? " and a.company like :company " : "")
                + (!userCategory.equalsIgnoreCase("ALL") ? " and c.access_type = :userCategory " : "")
                + sort_by
                + (order.equalsIgnoreCase("asc") ? " asc " : " desc ");

        List<UserManagement> records = new ArrayList<>();
        Session session = DbHibernate.getSession("APP_DB");
        try {

            Query q = session.createNativeQuery(query, UserManagement.class);

            if (username != null && !username.trim().isEmpty()) {
                q.setParameter("username", type.equalsIgnoreCase("singleuser") ? username : ("%" + username + "%"));
            }
            if (!betweenQry.isEmpty()) {
                q.setParameter("startDate", startDate + ":00")
                        .setParameter("endDate", endDate + ":59");
            }

            if (!company.isEmpty()) {
                q.setParameter("company", "%" + company + "%");
            }
            if (!userCategory.equalsIgnoreCase("ALL")) {
                q.setParameter("userCategory", userCategory);
            }
            records = q.getResultList();
            log.info("User Records Select done");
        } catch (Exception se) {
            log.error(ERROR, se);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return records;
    }

    public String changeUserStatus(String jsonString) {
        Boolean result = false;
        log.info("Users request received >> " + jsonString);

        ApiPostData apiData = gson.fromJson(jsonString, ApiPostData.class);

        String userCode = apiData.getUserCode();
        String status = apiData.getStatus();
        String userId = apiData.getUser_id();
        String user_id = apiData.getClientId();

        if (!(userCode.contains("[1]") || userCode.contains("[2]"))) {
            return "AUTHORIZATION REQUIRED";
        }

        Transaction tx = null;
        Session session = DbHibernate.getSession("APP_DB");
        try {

            tx = session.beginTransaction();

            String qry = "update telcodb.support_user set status_id = :status, login_attempts = 0 where user_id = :user_id";
            int i = session.createNativeQuery(qry)
                    .setParameter("status", status.equalsIgnoreCase("activate") ? 1 : 2)
                    .setParameter("user_id", user_id)
                    .executeUpdate();
            if (i > 0) {
                String qry1 = "insert telcodb.support_audit_trail(ip_address,action,comment,user_id, user) values (:ip_address,'update', concat(:status, 'd user account'), :user_id, :user)";
                int k = session.createNativeQuery(qry1)
                        .setParameter("status", status)
                        .setParameter("user_id", user_id)
                        .setParameter("user", userId)
                        .setParameter("ip_address", apiData.getIpAddress())
                        .executeUpdate();

                if (k > 0) {
                    result = true;

                    if (!status.equalsIgnoreCase("activate")) {
                        new Thread(() -> {
                            HttpSessionCollector.removeAllUserSessions(user_id);
                        }).start();
                    }
                }
            }

            tx.commit();
        } catch (Exception ex) {
            log.error(ERROR, ex);
            if (tx != null && tx.isActive()) {
                try {
                    tx.rollback();
                } catch (Exception e) {
                    log.error(ERROR, e);
                }
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return result ? "SUCCESS" : "FAILED";
    }

    public RoleInfoData fetchUserRoles(String jsonString) {
        Boolean result = false;
        log.info("UsersRoles request received >> " + jsonString);

        ApiPostData apiData = gson.fromJson(jsonString, ApiPostData.class);
        String cardScheme = apiData.getCardSchemeNumbers();
        String userCode = apiData.getUserCode();
        String userType = apiData.getType_id();

        String userId = apiData.getUser_id();
        String user_id = apiData.getClientId();
        String company = apiData.getCompany();
        List<RoleInfo> records = new ArrayList<>();
        List<NODES> typelist = new ArrayList<>();
        List<String> typeIdList = new ArrayList<>();
        RoleInfoData roleInfoData = new RoleInfoData();

        Boolean isAdmin = false;
        Boolean isExtAdmin = false;
        if (userCode.contains("[1]") || userCode.contains("[2]")) {
            isAdmin = userCode.contains("[1]");
            isExtAdmin = userCode.contains("[2]");

        } else {
            return roleInfoData;
        }

        Session session = DbHibernate.getSession("APP_DB");
        try {

            if (isAdmin) {
                String query = "select type_id as id, type_desc as name from telcodb.support_type order by type_desc asc";
                Query q = session.createNativeQuery(query, NODES.class);
                typelist = q.getResultList();
            } else if (isExtAdmin) {
                String query = "select distinct(d.type_id) as id, d.type_desc as name "
                        + " from telcodb.support_user a left join telcodb.support_user_access c on a.user_id = c.user_id left join telcodb.support_type d"
                        + " on d.type_id = c.access_type where c.user_id = :userId and a.company =:company and d.type_id !=2 order by type_desc asc";

                Query q = session.createNativeQuery(query, NODES.class)
                        .setParameter("userId", userId)
                        .setParameter("company", company);

                typelist = q.getResultList();

                typeIdList = typelist.stream().map(f -> f.getId()).collect(Collectors.toList());

                System.out.println("TypeId List ::: " + typeIdList);
            }

            String qry = "SELECT ifnull(a.access_type,''), ifnull(a.access_control,'') access FROM telcodb.support_user_access a "
                    + "left join telcodb.support_type b on a.access_type = b.type_id where user_id = :user_id "
                    + (isExtAdmin ? " and a.access_type in (:typeIdList) " : "")
                    + " order by b.type_desc asc";
            Query q = session.createNativeQuery(qry)
                    .setParameter("user_id", user_id);

            if (isExtAdmin) {
                q.setParameter("typeIdList", typeIdList);
            }

            records = new ArrayList<>();
            List<Object[]> resp = q.getResultList();
//            System.out.println("RESP::: " + resp);

            for (Object[] record : resp) {
                RoleInfo role = new RoleInfo();
//                log.info("RoleId: " + record[0].toString() + " " + record[1].toString());
                try {
                    role.setId(record[0].toString());
                    role.setUserRole(record[1].toString());

                    role.setRoleOptions(userCode.contains("[2]")
                            ? getSpecificRoleOptions(record[0].toString(),
                                    getRoleParameters(record[0].toString(), userCode))
                            : getRoleOptions(record[0].toString()));
                } catch (Exception e) {
                    log.error(ERROR, e);
                }
                records.add(role);
            }

        } catch (Exception ex) {
            log.error(ERROR, ex);
        } finally {
            if (session != null) {
                session.close();
            }
        }

        roleInfoData.setRoleInfo(records);
        roleInfoData.setTypeIdList(typelist);
        return roleInfoData;
    }

    public List<RoleOption> getRoleOptions(String role) {

        String query = "";
        List<RoleOption> options = new ArrayList<>();

        Session session = DbHibernate.getSession("APP_DB");
        try {

            query = "select query , UPPER(dbIp)  from telcodb.support_user_optionsQuery where type_id = :typeId";

            List<Object[]> resp = session.createNativeQuery(query).setParameter("typeId", role).getResultList();
            String sql = "";
            String dpIp = "";
            for (Object[] record : resp) {
                sql = record[0].toString();
                dpIp = record[1].toString();
            }

            if (!(sql.isEmpty() && dpIp.isEmpty())) {

                Session dbsession = DbHibernate.getSession(dpIp);

                try {

                    options = dbsession.createNativeQuery(sql, RoleOption.class).getResultList();

                } catch (Exception et) {
                    log.error(ERROR, et);
                } finally {
                    if (dbsession != null) {
                        dbsession.close();
                    }
                }
            }
        } catch (Exception re) {
            log.error(ERROR, re);
        } finally {
            if (session != null) {
                session.close();

            }
        }

        return options;
    }

    public List<RoleOption> getSpecificRoleOptions(String role, String option) {

        List<String> optionList = Pattern.compile("~").splitAsStream(option).collect(Collectors.toList());

        String query = "";
        List<RoleOption> options = new ArrayList<>();
        query = "select query_2, dbIp  from telcodb.support_user_optionsQuery where type_id = :typeId";
        Session session = DbHibernate.getSession("APP_DB");
        Session dbsession = null;
        try {

            Query q = session.createNativeQuery(query).setParameter("typeId", role);
            List<Object[]> resp = q.getResultList();
            String sql = "";
            String dpIp = "";
            for (Object[] record : resp) {
                sql = record[0].toString();
                dpIp = record[1].toString();
            }

            if (!(sql.isEmpty() && dpIp.isEmpty())) {
                log.info("SQL >>> " + sql + " " + dpIp);

                switch (dpIp) {
                    case "APP_DB":

                        dbsession = session;
                        break;
                    default:
                        dbsession = DbHibernate.getSession(dpIp);
                        break;
                }
                q = dbsession.createNativeQuery(sql, RoleOption.class);

                if (!optionList.isEmpty() && sql.contains(":option")) {

                    q.setParameter("option", optionList);
                }

                options = q.getResultList();

            }
        } catch (Exception et) {
            log.error(ERROR, et);
        } finally {
            if (session != null) {
                session.close();
            }
            if (dbsession != null) {
                dbsession.close();
            }
        }

        return options;
    }

    public String getUserRoleList(String userId) {
        String message = "";

        Session session = DbHibernate.getSession("APP_DB");
        if (!(userId == null || userId.trim().isEmpty())) {
            try {

                String query = "select group_concat(distinct concat('[',a.id,']') order by a.id asc separator ',') useraccess "
                        + " from telcodb.support_menuitems a left join telcodb.support_menuitemstorole b on a.id=b.menu_id "
                        + "left join telcodb.support_user_access c on c.access_type= b.type_id where c.user_id = :userId ";

                Query q = session.createNativeQuery(query).setParameter("userId", userId);

                List<String> userRoles = q.getResultList();
                message = !userRoles.isEmpty() ? userRoles.get(0) : "";

                return message;

            } catch (Exception ex) {
                log.error(ERROR, ex);
                message = "An Error occured";
            } finally {
                if (session != null) {
                    session.close();
                }
            }
        }
        return message;

    }

    public List<MenuOptions> getMenuList() {
        List<MenuOptions> menuItems = new ArrayList<>();
        Session session = DbHibernate.getSession("APP_DB");
        try {

            String query = "select menu, showOnMenu, name, concat('[',id,']', (case when open = 1 then ',[0]' else '' end)) accessLevel, routePath, templateUrl, controller from telcodb.support_menuitems";

            Query q = session.createNativeQuery(query, MenuOptions.class);
            menuItems = q.getResultList();

        } catch (Exception ex) {
            log.error(ERROR, ex);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return menuItems;

    }

    public List<MenuOptions> getUserMenuList() {

        List<MenuOptions> menuItems = new ArrayList<>();
        Session session = DbHibernate.getSession("APP_DB");
        try {

            String query = "select name, menu, showOnMenu, accessLevel, open, group_concat(distinct openTo) openTo, routePath,"
                    + " templateUrl, controller, icon from (select distinct(a.name), d.name menu, a.showOnMenu, concat('[',a.id,']') accessLevel, "
                    + "(case when a.open = 1 then ifnull(b.type_id,0) else concat_ws('|',b.type_id,replace(a.openTo,',','~')) end) openTo, a.open, a.routePath, a.templateUrl, a.controller, d.icon "
                    + "from telcodb.support_menuitems a left join telcodb.support_menuitemstorole b on a.id = b.menu_id left join "
                    + "telcodb.support_user_access c on c.access_type= b.type_id left join telcodb.support_menu d on d.id = a.menu_id) as t1 group by routePath, name;";
            Query q = session.createNativeQuery(query, MenuOptions.class);
            menuItems = q.getResultList();

        } catch (Exception ex) {
            log.error(ERROR, ex);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return menuItems;
    }

    public String getAllUserRoles(String userId) {
        String message = "";
        if (!(userId == null || userId.trim().isEmpty())) {
            Session session = DbHibernate.getSession("APP_DB");
            try {

                String query = "select group_concat(access separator ',') useraccess from "
                        + "(SELECT concat('[',access_type,']',(case when GROUP_CONCAT(access_control SEPARATOR '~') != '' then '|' else '' end),GROUP_CONCAT(access_control SEPARATOR '~')) access "
                        + "FROM telcodb.support_user_access  where user_id = :userId GROUP BY access_type) as t1 group by null;";

                Query q = session.createNativeQuery(query).setParameter("userId", userId);

                List<String> userRoles = q.getResultList();
                message = (!userRoles.isEmpty() ? userRoles.get(0) : "");

                return message;

            } catch (Exception ex) {

                message = "An Error occured";
            } finally {
                if (session != null) {
                    session.close();
                }
            }
        }
        return message;
    }

    public String modifyUser(String jsonString) {
        JSONObject jsonResponse = new JSONObject();
        try {

            ApiPostData apiData = gson.fromJson(jsonString, ApiPostData.class);

            UserInfo userInfo = gson.fromJson(apiData.getUserData(), UserInfo.class);
            Boolean isExtAdmin = apiData.getUserCode().contains("[2]");
            log.info("Data: " + userInfo);
            if (userInfo != null) {

                boolean rollback = true;

                ArrayList<String> validationErrorMsgs = validateRequest(userInfo, 2);
                if (validationErrorMsgs.isEmpty()) {
                    Transaction tx = null;
                    Session session = DbHibernate.getSession("APP_DB");
                    try {

                        tx = session.beginTransaction();

                        String userId = userInfo.getUserId();
                        String userExistsQuery = "select count(*) from telcodb.support_user where user_id = :userId";
                        Query q = session.createNativeQuery(userExistsQuery).setParameter("userId", userId);
                        int count = ((BigInteger) q.getSingleResult()).intValue();
                        log.info("Count: " + count);
                        Boolean userExists = count > 0;
                        log.info("UserExists: " + userExists);
                        if (userExists) {

                            String modifyUser = "update telcodb.support_user set firstname =:firstname, lastname = :lastname, email =:email, "
                                    + "mobile = :mobile, user_code = :user_code, require2fa = :require2fa, company = :company, branch_code = :branch_code where user_id =:userId ";
                            log.info("QUERY TO Modify User >>" + modifyUser);
                            int n = session.createNativeQuery(modifyUser)
                                    .setParameter("firstname", userInfo.getFirstName())
                                    .setParameter("lastname", userInfo.getLastName())
                                    .setParameter("mobile", userInfo.getMobile())
                                    .setParameter("email", userInfo.getEmail())
                                    .setParameter("user_code",
                                            isExtAdmin ? apiData.getBank_code() : userInfo.getBankCode())
                                    .setParameter("branch_code", userInfo.getBranchCode())
                                    .setParameter("company", isExtAdmin ? apiData.getCompany() : userInfo.getCompany())
                                    .setParameter("require2fa",
                                            userInfo.getRequire2FA().equalsIgnoreCase("true") ? 1 : 0)
                                    .setParameter("userId", userId)
                                    .executeUpdate();
                            log.info("QUERY TO Modify User >>" + modifyUser);

                            String insertAudit = "insert telcodb.support_audit_trail(action,comment, extra_info ,user_id, user,ip_address) "
                                    + "select 'modifyUser' action , 'modified user account details' comment, userchange extra_info, :userId user_id, :user_id user, :ip_address ip_address from "
                                    + "(select (case when firstname != :firstname then concat('firstname -> old: ',firstname, ' new: ', :firstname) "
                                    + "when lastname != :lastname then concat('lastname -> old: ',lastname, ' new: ', :lastname) "
                                    + "when user_code != :user_code then concat('user_code -> old: ',user_code, ' new: ', :user_code) "
                                    + "when email != :email then concat('email -> old: ',email, ' new: ', :email) "
                                    + "when mobile != :mobile then concat('mobile -> old: ',mobile, ' new: ', :mobile) "
                                    + "when branch_code != :branch_code then concat('branch_code -> old: ',branch_code, ' new: ', :branch_code) "
                                    + "when require2fa != :require2fa then concat('require2fa -> old: ',require2fa, ' new: ', :require2fa) else '' end) userchange "
                                    + "from telcodb.support_user where user_id = :userId) as t2";
                            int y = session.createNativeQuery(insertAudit)
                                    .setParameter("userId", userId)
                                    .setParameter("firstname", userInfo.getFirstName())
                                    .setParameter("lastname", userInfo.getLastName())
                                    .setParameter("mobile", userInfo.getMobile())
                                    .setParameter("email", userInfo.getEmail())
                                    .setParameter("user_code", userInfo.getBankCode())
                                    .setParameter("branch_code", userInfo.getBranchCode())
                                    .setParameter("user_id", apiData.getUser_id())
                                    .setParameter("ip_address", apiData.getIpAddress())
                                    .setParameter("require2fa",
                                            userInfo.getRequire2FA().equalsIgnoreCase("true") ? 1 : 0)
                                    .executeUpdate();
                            rollback = false;
                            jsonResponse.put("message", "User Modified Successfully");

                        } else {

                            log.info("User Doesn't Exists");
                            jsonResponse.put("message", "User Doesn't Exists");
                        }
                        if (rollback) {
                            try {
                                tx.rollback();

                            } catch (Exception rt) {
                                log.error(ERROR, rt);
                            }
                        } else {
                            tx.commit();
                        }

                        log.info("User Modified Successfully");
                        jsonResponse.put("message", "User Modified Successfully");
                    } catch (Exception er) {
                        log.error(ERROR, er);
                        log.info("An Error Occured");
                        jsonResponse.put("message", "An Error Occured");
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
                } else {
                    log.info("Error: " + validationErrorMsgs);
                    jsonResponse.put("message", validationErrorMsgs);
                }
            }
        } catch (Exception ex) {
            log.error(ERROR, ex);
            jsonResponse.put("message", "An Error Occured");
        }

        return jsonResponse.toString();
    }

    public String modifyUserRoles(String jsonString) {
        JSONObject jsonResponse = new JSONObject();
        boolean rollback = true;
        try {

            ApiPostData apiData = gson.fromJson(jsonString, ApiPostData.class);

            UserInfo userInfo = gson.fromJson(apiData.getUserData(), UserInfo.class);
            log.info("Data: " + userInfo);
            String usercode = apiData.getUserCode();
            String type_id = apiData.getType_id();
            Boolean isExtAdmin = apiData.getUserCode().contains("[2]");
            if (userInfo != null) {
                Transaction tx = null;
                Session session = DbHibernate.getSession("APP_DB");
                try {
                    tx = session.beginTransaction();

                    String userId = userInfo.getUserId();
                    String userExistsQuery = "select count(*) from telcodb.support_user where user_id = :userId";
                    Query q = session.createNativeQuery(userExistsQuery).setParameter("userId", userId);
                    int count = ((BigInteger) q.getSingleResult()).intValue();
                    log.info("Count: " + count);
                    Boolean userExists = count > 0;
                    log.info("UserExists: " + userExists);
                    if (userExists) {

                        String dropUserData = "delete from telcodb.support_user_access where user_id =:userId ";
                        q = session.createNativeQuery(dropUserData);
                        q.setParameter("userId", userId);

                        int o = q.executeUpdate();

                        if (userInfo.getUserOptions() != null && !userInfo.getUserOptions().isEmpty()) {
                            userInfo.getUserOptions().stream()
                                    .filter(f -> validateRoleParams(type_id, usercode, f.getUserOption0(),
                                    f.getUserOption1(), f.getUserOption2()))
                                    .forEach(
                                            g -> {
                                                String insertRoles = "insert ignore into telcodb.support_user_access(user_id, access_type, access_control) values (:userId, :accessType, :accessControl)";
                                                int y = session.createNativeQuery(insertRoles)
                                                        .setParameter("userId", userId)
                                                        .setParameter("accessType",
                                                                (g.getUserOption0() == null ? "" : g.getUserOption0()))
                                                        .setParameter("accessControl",
                                                                g.getUserOption1() == null ? "" : g.getUserOption1())
                                                        .executeUpdate();
                                            });
                        }

                        String insertAudit = "insert telcodb.support_audit_trail(action,comment, extra_info ,user_id, user, ip_address) "
                                + "select 'modifyUserRoles' action , 'modified user account roles' comment, useraccess extra_info, :userId user_id, :user_id user, :ip_address ip_address from "
                                + "(select group_concat(access separator ',') useraccess from (SELECT concat('[',access_type,']', "
                                + "(case when GROUP_CONCAT(access_control SEPARATOR '~') != '' then '|' else '' end), GROUP_CONCAT(access_control SEPARATOR '~')) access "
                                + "FROM telcodb.support_user_access  where user_id = :userId GROUP BY access_type) as t1 group by null) as t2";
                        int y = session.createNativeQuery(insertAudit)
                                .setParameter("userId", userId)
                                .setParameter("user_id", apiData.getUser_id())
                                .setParameter("ip_address", apiData.getIpAddress())
                                .executeUpdate();

                        rollback = false;
                        jsonResponse.put("message", "User Modified Successfully");

                        rollback = false;

                        new Thread(() -> {
                            HttpSessionCollector.removeAllUserSessions(userId);
                        }).start();

                    } else {

                        log.info("User Doesn't Exists");
                        jsonResponse.put("message", "User Doesn't Exists");
                    }
                    if (rollback) {
                        try {
                            tx.rollback();

                        } catch (Exception rt) {
                            log.error(ERROR, rt);
                        }
                    } else {
                        tx.commit();
                    }

                } catch (Exception er) {
                    log.error(ERROR, er);
                    log.info("An Error Occured");
                    jsonResponse.put("message", "An Error Occured");
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
            }
        } catch (Exception ex) {
            log.error(ERROR, ex);
            jsonResponse.put("message", "An Error Occured");
        }

        return jsonResponse.toString();
    }

    public String createUser(String jsonString) {
        JSONObject jsonResponse = new JSONObject();
        try {

            ApiPostData apiData = gson.fromJson(jsonString, ApiPostData.class);

            final String requestOrigin = apiData.getOrigin();

            User tempUser = new User();
            String userRoles = getAllUserRoles(apiData.getUser_id());
            userRoles = (userRoles == null ? "" : userRoles);
            tempUser.setType_id(getUserRoleList(apiData.getUser_id()));
            tempUser.setUser_code(userRoles);

            JSONObject checkOrigin = GeneralUtility.checkOrigin(tempUser, requestOrigin);

            if (!checkOrigin.getString("code").equals("00")) {

                return checkOrigin.toString();
            }
            UserInfo userInfo = gson.fromJson(apiData.getUserData(), UserInfo.class);
            Boolean isExtAdmin = apiData.getUserCode().contains("[2]");
            String usercode = apiData.getUserCode();
            String type_id = apiData.getType_id();

            String generatedPassword = "";
            log.info("Data: " + userInfo);
            boolean rollback = true;
            if (userInfo != null) {

                ArrayList<String> validationErrorMsgs = validateRequest(userInfo, 1);
                if (validationErrorMsgs.isEmpty()) {
                    Transaction tx = null;
                    Session session = DbHibernate.getSession("APP_DB");
                    try {
                        tx = session.beginTransaction();
                        userInfo.setNewUsername(userInfo.getNewUsername().trim());
                        String userExistsQuery = "select count(*) from telcodb.support_user where username =:username";
                        Query q = session.createNativeQuery(userExistsQuery).setParameter("username",
                                userInfo.getNewUsername());
                        int count = ((BigInteger) q.getSingleResult()).intValue();
//                        log.info("Count: " + count);
                        Boolean userExists = count > 0;
                        log.info("UserExists: " + userExists);
                        final String name = userInfo.getFirstName();
                        final String username = userInfo.getNewUsername();
                        final String email = userInfo.getEmail();
                        if (!userExists) {

                            generatedPassword = PasswordGenerator.generatePassword();

                            String crypt_password = CryptographerMin
                                    .doMd5Hash(userInfo.getNewUsername() + generatedPassword);
                            String createUser = "insert telcodb.support_user(firstname,lastname,email, company, mobile,username, password, createdby, first_logon, create_date, status_id,require2fa,user_code, branch_code) "
                                    + " values  (:firstname,:lastname, :email, :company, :mobile, :username, :password, :adminId, '0', now(), 4, :require2fa, :user_code,:branch_code)";
                            log.info("QUERY TO Create User >>" + createUser);
                            int n = session.createNativeQuery(createUser)
                                    .setParameter("firstname", userInfo.getFirstName())
                                    .setParameter("lastname", userInfo.getLastName())
                                    .setParameter("username", userInfo.getNewUsername())
                                    .setParameter("mobile", userInfo.getMobile())
                                    .setParameter("email", userInfo.getEmail())
                                    .setParameter("company", isExtAdmin ? apiData.getCompany() : userInfo.getCompany())
                                    .setParameter("user_code",
                                            isExtAdmin ? (apiData.getBank_code().isEmpty() ? "000"
                                                    : apiData.getBank_code()) : userInfo.getBankCode())
                                    .setParameter("branch_code", userInfo.getBranchCode())
                                    .setParameter("adminId", apiData.getUser_id())
                                    .setParameter("password", crypt_password)
                                    .setParameter("require2fa",
                                            userInfo.getRequire2FA().equalsIgnoreCase("true") ? 1 : 0)
                                    .executeUpdate();

                            String getIdQuery = "select user_id from telcodb.support_user where username = :username limit 1";
                            q = session.createNativeQuery(getIdQuery)
                                    .setParameter("username", userInfo.getNewUsername());

                            String generatedId = ((BigInteger) q.getSingleResult()).toString();

                            if (generatedId != null) {
                                if (userInfo.getUserOptions() != null && !userInfo.getUserOptions().isEmpty()) {
                                    userInfo.getUserOptions().stream()
                                            .filter(f -> validateRoleParams(type_id, usercode, f.getUserOption0(),
                                            f.getUserOption1(), f.getUserOption2()))
                                            .forEach(
                                                    g -> {
                                                        String insertRoles = "insert ignore into telcodb.support_user_access(user_id, access_type, access_control) values (:userId, :accessType, :accessControl)";
                                                        session.createNativeQuery(insertRoles)
                                                                .setParameter("userId", generatedId)
                                                                .setParameter("accessType",
                                                                        g.getUserOption0() == null ? ""
                                                                        : g.getUserOption0())
                                                                .setParameter("accessControl",
                                                                        g.getUserOption1() == null ? ""
                                                                        : g.getUserOption1())
                                                                .executeUpdate();
                                                    });
                                }
                                jsonResponse.put("message", "User Created Successfully");
                                jsonResponse.put("password", generatedPassword);

                                String insertAudit = "insert telcodb.support_audit_trail(action,comment, extra_info ,user_id, user,ip_address) "
                                        + "select 'createUser' action , 'created user account with roles' comment, useraccess extra_info, :userId user_id, :user_id user, :ip_address ip_address from "
                                        + "(select group_concat(access separator ',') useraccess from (SELECT concat('[',access_type,']', "
                                        + "(case when GROUP_CONCAT(access_control SEPARATOR '~') != '' then '|' else '' end), GROUP_CONCAT(access_control SEPARATOR '~')) access "
                                        + "FROM telcodb.support_user_access  where user_id = :userId GROUP BY access_type) as t1 group by null) as t2";
                                int y = session.createNativeQuery(insertAudit)
                                        .setParameter("userId", generatedId)
                                        .setParameter("user_id", apiData.getUser_id())
                                        .setParameter("ip_address", apiData.getIpAddress())
                                        .executeUpdate();

                                rollback = false;

                            } else {

                                jsonResponse.put("message", "Couldn't generate userId");
                            }
                        } else {

                            jsonResponse.put("message", "User Exists");
                        }
                        if (rollback) {
                            try {
                                tx.rollback();

                            } catch (Exception rt) {
                                log.error(ERROR, rt);
                            }
                        } else {
                            tx.commit();
                            final String pass = generatedPassword;
                            new Thread(() -> {
                                new Alert().processEmail(email, username, pass, name, "setup", requestOrigin);
                            }).start();

                            jsonResponse.put("message", "User Created Successfully");
                            jsonResponse.put("username", userInfo.getNewUsername());
                            jsonResponse.put("password", generatedPassword);
                        }

                    } catch (Exception er) {
                        log.error(ERROR, er);

                        jsonResponse.put("message", "An Error Occured");
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
                } else {
                    log.info("Error: " + validationErrorMsgs);
                    jsonResponse.put("message", validationErrorMsgs);
                }
            }
        } catch (Exception ex) {
            log.error(ERROR, ex);
            jsonResponse.put("message", "An Error Occured");
        }

        return jsonResponse.toString();
    }

    public List<NODES> getTypeIdList(boolean isAdmin, boolean isExtAdmin, String userId) {
        List<NODES> result = new ArrayList<>();
        Session session = DbHibernate.getSession("APP_DB");
        try {

            if (isAdmin) {
                String query = "";
                query = "select type_id as id, type_desc as name from telcodb.support_type order by type_desc asc";

                Query q = session.createNativeQuery(query, NODES.class);
                result = q.getResultList();
            } else if (isExtAdmin) {
                String query = "";

                query = "select distinct(d.type_id) as id, d.type_desc as name "
                        + " from telcodb.support_user_access c left join telcodb.support_type d"
                        + " on d.type_id = c.access_type  where c.user_id = :userId and d.type_id not in (1,2) order by type_desc asc";

                Query q = session.createNativeQuery(query, NODES.class)
                        .setParameter("userId", userId);
                result = q.getResultList();
            }
        } catch (Exception k) {
            log.error(ERROR, k);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return result;
    }

    public List<PortalSettingsData> getPortalSettings(String jsonString) {

        log.info("PortalSettings request received >> " + jsonString);
        List<PortalSettingsData> records = new ArrayList<>();
        try {
            ApiPostData apiData = gson.fromJson(jsonString, ApiPostData.class);

            String name = apiData.getTransType();
            String userCode = apiData.getUserCode();
            String userType = apiData.getType_id();
            String username = apiData.getUserName();
            Boolean isAdmin = userCode.contains("[1]");

            if (isAdmin) {
                Session session = DbHibernate.getSession("APP_DB");
                try {
                    String query = "select id, name, description, current_value, key_name, status, type_1, type_2 from telcodb.support_portal_settings "
                            + (name != null && !name.equalsIgnoreCase("") ? " where name like :name  " : "")
                            + " order by name asc";
                    log.info("PortalSettings query : " + query);
                    Query q = session.createNativeQuery(query, PortalSettingsData.class);

                    if (name != null && !name.equalsIgnoreCase("")) {
                        q.setParameter("name", "%" + name + "%");
                    }
                    records = q.getResultList();
                    log.info("PortalSettings Select done");
                } catch (Exception e) {
                    log.error(ERROR, e);
                } finally {
                    if (session != null) {
                        session.close();
                    }
                }

            } else {
                log.info("DENIED ACCESS");
            }
        } catch (Exception w) {
            log.error(ERROR, w);
        }

        return records;
    }

    public String updatePortalSettings(String jsonString) {
        JSONObject jsonResponse = new JSONObject();
        log.info("updatePortalSettings request received >> " + jsonString);

        ApiPostData apiData = gson.fromJson(jsonString, ApiPostData.class);
        String userCode = apiData.getUserCode();

        String userId = apiData.getUser_id();

        Boolean isAdmin = userCode.contains("[1]");

        PortalSettingsData portalSettings = gson.fromJson(apiData.getUserData(), PortalSettingsData.class);

        if (!isAdmin) {

            jsonResponse.put("message", "AUTHORIZATION REQUIRED");

            return jsonResponse.toString();
        }

        Transaction tx = null;
        Session session = DbHibernate.getSession("APP_DB");
        String key_name = "";
        boolean rollback = true;
        try {

            tx = session.beginTransaction();

            String qry1 = "insert telcodb.support_audit_trail(ip_address,action,comment, user) select :ip_address ip_address,  'updatePortal' action, "
                    + "concat('Updated PortalSettings: ', updateSettings) comment , :user user from "
                    + "(select concat((case when name != :name then concat(key_name, ' name changed from ',name, ' to ' , :name) else '' end), "
                    + "(case when description != :description then concat(key_name, ' description changed from ',description, ' to ' , :description) else '' end), "
                    + "(case when (current_value != :current_value or type_2 != :type_2) then concat(key_name ,' duration changed from ',concat(current_value, ' ', type_2), "
                    + "' to ' , concat(:current_value, ' ', :type_2)) else '' end)) updateSettings "
                    + "from telcodb.support_portal_settings where id = :id ) as t1";

            int i = session.createNativeQuery(qry1)
                    .setParameter("id", portalSettings.getId())
                    .setParameter("name", portalSettings.getName())
                    .setParameter("description", portalSettings.getDescription())
                    .setParameter("current_value", portalSettings.getCurrent_value())
                    .setParameter("type_2", portalSettings.getType_2())
                    .setParameter("ip_address", apiData.getIpAddress())
                    .setParameter("user", userId)
                    .executeUpdate();

            if (i > 0) {

                String qry = "update telcodb.support_portal_settings set name = :name, description = :description, current_value = :current_value ,type_2 = :type_2  where id = :id ";
                int k = session.createNativeQuery(qry)
                        .setParameter("id", portalSettings.getId())
                        .setParameter("name", portalSettings.getName())
                        .setParameter("description", portalSettings.getDescription())
                        .setParameter("current_value", portalSettings.getCurrent_value())
                        .setParameter("type_2", portalSettings.getType_2() != null ? portalSettings.getType_2() : "")
                        .executeUpdate();

                key_name = (String) session
                        .createNativeQuery("select key_name from telcodb.support_portal_settings where id = :id")
                        .setParameter("id", portalSettings.getId()).getSingleResult();

                if (key_name.equalsIgnoreCase("session_timeout")) {
                    log.info("Setting Timeout:  " + portalSettings.getCurrent_value());
                    int timeout = 0;
                    float data = Float.parseFloat(portalSettings.getCurrent_value());
                    switch (portalSettings.getType_2()) {
                        case "minute":
                            timeout = (int) (data * 60);
                            break;
                        case "hour":
                            timeout = (int) (data * 60 * 60);
                            break;
                        case "day":
                            timeout = (int) (data * 60 * 60 * 24);
                            break;
                        case "week":
                            timeout = (int) (data * 60 * 60 * 24 * 7);
                            break;
                        case "month":
                            timeout = (int) (data * 60 * 60 * 24 * 7 * 4);
                            break;
                        case "year":
                            timeout = (int) (data * 60 * 60 * 24 * 7 * 4 * 12);
                            break;
                        default:
                            timeout = 60 * 15;
                            break;
                    }
                    HttpSessionCollector.setSessionTimeout(timeout);

                }
                rollback = false;
            }
            if (rollback) {
                try {
                    tx.rollback();

                } catch (Exception rt) {
                    log.error(ERROR, rt);
                }
            } else {
                tx.commit();
            }

            PortalSettings.refreshPortalSettings();
            jsonResponse.put("message", "PortalSetting Modified Successfully");

        } catch (Exception ex) {
            log.error(ERROR, ex);
            jsonResponse.put("message", "Could not Modify PortalSetting");
            try {
                if (tx != null) {
                    tx.rollback();
                }
            } catch (Exception xe) {
                log.info("could not rollback");
                log.error(ERROR, xe);
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return jsonResponse.toString();
    }

    public String getPortalClientSettings() {
        String result = "";
        Session session = DbHibernate.getSession("APP_DB");

        try {

            String qry = "select JSON_OBJECTAGG(setting_name, setting_value) from "
                    + "(select key_name setting_name, (case when type_1 ='time' then "
                    + "(current_value* (case when type_2 ='minute' then 60 when type_2='hour' then (60*60) "
                    + "when type_2 = 'day' then (60*60*24) when type_2 = 'week' then (60*60*24*7) "
                    + "when type_2 ='month' then (60*60*24*7*4) when type_2='year' then (60*60*24*7*4*12) else 0 end)) "
                    + "else current_value end) setting_value from telcodb.support_portal_settings where showSetting =1 "
                    + "order by key_name asc) as t1";

            result = (String) session.createNativeQuery(qry).getSingleResult();

        } catch (Exception ex) {
            log.error(ERROR, ex);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return result;
    }

    public String changePortalSettingsStatus(String jsonString) {

        log.info("changeStatus request received >> " + jsonString);
        Gson j = new Gson();
        JSONObject jsonResponse = new JSONObject();
        ApiPostData apiData = gson.fromJson(jsonString, ApiPostData.class);
        String userCode = apiData.getUserCode();

        String userId = apiData.getUser_id();
        String status = apiData.getStatus();
        String id = apiData.getClientId();

        Boolean isAdmin = userCode.contains("[1]");

        if (!isAdmin) {

            jsonResponse.put("message", "AUTHORIZATION REQUIRED");

            return jsonResponse.toString();
        }
        String current_setting = getSetting(id);
        log.info("current: " + current_setting);
        current_setting = current_setting.split(":")[0] + (status.equals("enable") ? ":1" : ":0");
        log.info("new setting: " + current_setting);
        Transaction tx = null;
        Session session = DbHibernate.getSession("APP_DB");
        boolean rollback = true;
        try {

            tx = session.beginTransaction();

            String qry1 = "insert telcodb.support_audit_trail(ip_address,action,comment, user) select :ipaddress ipaddress,  'updatePortal' action, "
                    + "concat('Updated PortalSetting: ', updateSettings) comment , :user user from "
                    + "(select concat(case when status != :status then concat((case when :status = '1' then 'enabled ' else 'disabled ' end), key_name) else '' end) updateSettings "
                    + " from telcodb.support_portal_settings where key_name = :id ) as t1";

            int i = session.createNativeQuery(qry1)
                    .setParameter("id", id)
                    .setParameter("status", status.equals("enable") ? "1" : "0")
                    .setParameter("user", userId)
                    .setParameter("ip_address", apiData.getIpAddress())
                    .executeUpdate();

            if (i > 0) {
                String qry = "update telcodb.support_portal_settings set status = :status where key_name = :id ";
                int k = session.createNativeQuery(qry)
                        .setParameter("status", status.equals("enable") ? "1" : "0")
                        .setParameter("id", id)
                        .executeUpdate();

                if (k > 0) {

                    rollback = false;
                    PortalSettings.addSettings(id, current_setting);
                }
            }
            if (rollback) {
                try {
                    tx.rollback();

                } catch (Exception rt) {
                    log.error(ERROR, rt);
                }
            } else {
                tx.commit();
            }
            jsonResponse.put("message", "PortalSetting Modified Successfully");
        } catch (Exception er) {
            log.error(ERROR, er);
            jsonResponse.put("message", "Could not Modify PortalSetting");
            try {
                if (tx != null) {
                    tx.rollback();
                }
            } catch (Exception xe) {
                log.info("could not rollback");
                log.error(ERROR, xe);
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return jsonResponse.toString();
    }

    public String runChangePassword(String jsonString) {
        Boolean result = false;
        Boolean run = true;
        JSONObject json = new JSONObject(jsonString);
        String data = json.optString("data", "60");
        String data1 = json.optString("data1", "day").split(":")[0];

        switch (data1) {
            case "minute":
                data = String.valueOf(Integer.valueOf(data));
                break;
            case "hour":
                data = String.valueOf(Integer.valueOf(data) * 60);
                break;
            case "day":
                data = String.valueOf(Integer.valueOf(data) * 60 * 24);
                break;
            case "week":
                data = String.valueOf(Integer.valueOf(data) * 60 * 24 * 7);
                break;
            case "month":
                data = String.valueOf(Integer.valueOf(data) * 60 * 24 * 7 * 4);
                break;
            case "year":
                data = String.valueOf(Integer.valueOf(data) * 60 * 24 * 7 * 4 * 12);
                break;
            default:
                run = false;
                break;

        }

        if (run) {
            Transaction tx = null;
            Session session = DbHibernate.getSession("APP_DB");
            try {

                tx = session.beginTransaction();

                String qry = "select user_id, username from telcodb.support_user where first_logon = '1' and status_id = 1 and TIMESTAMPDIFF(MINUTE,last_password_change, now()) > :data ";
                Query q = session.createNativeQuery(qry)
                        .setParameter("data", data);

                List<Object[]> resp = q.getResultList();

                resp.forEach((record) -> {

                    HttpSessionCollector.removeAllUserSessions(record[0].toString());
                });

                String qry1 = "update telcodb.support_user set first_logon = '0', status_id = 5 where first_logon = '1' and TIMESTAMPDIFF(MINUTE,last_password_change, now()) > :data ";
                int i = session.createNativeQuery(qry1)
                        .setParameter("data", data)
                        .executeUpdate();

                tx.commit();

                result = true;

            } catch (Exception er) {
                log.info("Error: " + er);
                log.error(ERROR, er);
                try {
                    if (tx != null) {
                        tx.rollback();
                    }
                } catch (Exception ex) {
                    log.info("An error occured");
                    log.error(ERROR, ex);
                }
            } finally {
                if (session != null) {
                    session.close();
                }
            }
        } else {
            result = false;
        }
        return result ? "SUCCESS" : "FAILED";
    }

    public String runInactiveAccount(String jsonString) {
        Boolean result = false;
        Boolean run = true;
        JSONObject json = new JSONObject(jsonString);
        String data = json.optString("data", "60");
        String data1 = json.optString("data1", "day").split(":")[0];

        switch (data1) {
            case "minute":
                data = String.valueOf(Integer.valueOf(data));
                break;
            case "hour":
                data = String.valueOf(Integer.valueOf(data) * 60);
                break;
            case "day":
                data = String.valueOf(Integer.valueOf(data) * 60 * 24);
                break;
            case "week":
                data = String.valueOf(Integer.valueOf(data) * 60 * 24 * 7);
                break;
            case "month":

                data = String.valueOf(Integer.valueOf(data) * 60 * 24 * 7 * 4);
                break;
            case "year":
                data = String.valueOf(Integer.valueOf(data) * 60 * 24 * 7 * 4 * 12);
                break;
            default:
                run = false;
                break;

        }

        if (run) {
            Transaction tx = null;
            Session session = DbHibernate.getSession("APP_DB");
            try {

                tx = session.beginTransaction();

                String qry1 = "update telcodb.support_user set status_id = 4 where status_id = 1 and (TIMESTAMPDIFF(MINUTE,last_login, now()) > :data or last_login is null)";
                int i = session.createNativeQuery(qry1)
                        .setParameter("data", data)
                        .executeUpdate();

                tx.commit();

                result = true;

            } catch (Exception er) {
                log.info("Error: " + er);
                log.error(ERROR, er);
                try {
                    if (tx != null) {
                        tx.rollback();
                    }
                } catch (Exception ex) {
                    log.info("An error occured");
                    log.error(ERROR, ex);
                }
            } finally {
                if (session != null) {
                    session.close();
                }
            }
        } else {
            result = false;
        }
        return result ? "SUCCESS" : "FAILED";
    }

    public String runExpireOtp() {
        Boolean result = false;
        Boolean run = true;
        String otp_lifetime = getSetting("otp_lifetime");

        if (otp_lifetime != null) {

            String data = (otp_lifetime.split("-")[0]);
            String data1 = otp_lifetime.split("-").length > 1 ? otp_lifetime.split("-")[1] : "default";
            String status = data1.split(":")[1];
            if (status.equals("1")) {

                if (data1.split(":")[0].equals("default")) {
                    data = "1";
                    data1 = "hour";
                }

                switch (data1.split(":")[0]) {
                    case "minute":
                        data = String.valueOf(Integer.valueOf(data));
                        break;
                    case "hour":
                        data = String.valueOf(Integer.valueOf(data) * 60);
                        break;
                    case "day":
                        data = String.valueOf(Integer.valueOf(data) * 60 * 24);
                        break;
                    case "week":
                        data = String.valueOf(Integer.valueOf(data) * 60 * 24 * 7);
                        break;
                    case "month":
                        data = String.valueOf(Integer.valueOf(data) * 60 * 24 * 7 * 4);
                        break;
                    case "year":
                        data = String.valueOf(Integer.valueOf(data) * 60 * 24 * 7 * 4 * 12);
                        break;
                    default:
                        run = false;
                        break;
                }

                if (run) {
                    Transaction tx = null;
                    Session session = DbHibernate.getSession("APP_DB");
                    try {

                        tx = session.beginTransaction();

                        String qry1 = "update telcodb.otp_expiry set expired = 1 where expired = 0 and (TIMESTAMPDIFF(MINUTE,created_at, now()) > :data)";
                        int i = session.createNativeQuery(qry1)
                                .setParameter("data", data)
                                .executeUpdate();

                        tx.commit();

                        result = true;

                    } catch (Exception er) {

                        log.error(ERROR, er);
                        try {
                            if (tx != null) {
                                tx.rollback();
                            }
                        } catch (Exception ex) {

                            log.error(ERROR, ex);
                        }
                    } finally {
                        if (session != null) {
                            session.close();
                        }
                    }
                } else {
                    result = false;
                }
            }
        }
        return result ? "SUCCESS" : "FAILED";
    }

    public List<Object[]> loadPortalSettings() {
        List<Object[]> resp = new ArrayList<>();
        Session session = DbHibernate.getSession("APP_DB");
        try {

            String qry = "select key_name, concat(current_value,(case when type_2 !='' then concat('-',type_2) else '' end), "
                    + "(concat(':',status))) from telcodb.support_portal_settings where status = 1";

            Query q = session.createNativeQuery(qry);

            resp = q.getResultList();
        } catch (Exception we) {
            log.error(ERROR, we);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return resp;
    }

    public String getFirstRoute(String typeId, String tempPassword, String path) {
        String nextRoute = "";
        String pathUrl = "";

        Session session = DbHibernate.getSession("APP_DB");

        try {
            log.info("Type: " + Arrays.toString(typeId.split(",")));
            String userType = (typeId.split(",")[0].replaceAll("[\\[\\](){}]", "")).split("[|]")[0];
            log.info("FirstRouter: " + userType);

            String qry = "select b.routePath url from telcodb.support_menuitemstorole a left join telcodb.support_menuitems b on a.menu_id = b.id "
                    + " where  a.type_id = :type_id order by b.id asc limit 1";

            Query q = session.createNativeQuery(qry);

            q.setParameter("type_id", userType);

            String record = (String) q.getSingleResult();
            log.info("Result: " + record);

            nextRoute = pathUrl + "/" + (record != null ? record : "unauthorized");
        } catch (NoResultException ex) {
            log.info("Doesn't exist");

            nextRoute = pathUrl + "/unauthorized";
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return nextRoute;
    }

    public List<NODES> getProviderList(String typeId, String userCode) {
        List<NODES> resp = new ArrayList<>();
        Session session = DbHibernate.getSession("APP_DB");
        try {

            String qry = "(select 'ALL' id, 'ALL' name) union  select alias id, alias name from telcodb.sms_providers where status = 1";

            Query q = session.createNativeQuery(qry, NODES.class);

            resp = q.getResultList();
        } catch (Exception we) {
            log.error(ERROR, we);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return resp;
    }

    public List<NODES> getSenderList(String type_id, String user_code) {
        List<NODES> resp = new ArrayList<>();

        String userRoles = "";

        if (!type_id.isEmpty()) {
            if (type_id.contains("[1]")) {
                userRoles = "ETZ";
            } else {
                userRoles = user_code.split("\\|")[1];
                user_code = user_code.split("\\|")[0].replaceAll("\\[|\\]", "");
                log.info("Usercode: " + user_code);
                log.info("usrRoles: " + userRoles);

                if (userRoles.contains("~")) {
                    String[] arr = userRoles.split("~");
                    userRoles = Arrays.stream(arr).collect(Collectors.joining("','"));
                }
                log.info("result: " + userRoles);

            }
        } else {
            return new ArrayList<>();
        }

        Session session = DbHibernate.getSession("APP_DB");
        try {

            String qry = "";

            if (userRoles.equals("ETZ")) {
                qry = "(select 'ALL' id, 'ALL' name) union (select b.name id,  b.name name from telcodb.sms_senderidtotype a left join "
                        + " telcodb.sms_senderids b on a.sender_id = b.id where status = 1 order by b.name asc) ";

            } else {

                qry = "select b.name id,  b.name name from telcodb.sms_senderidtotype a left join  "
                        + "telcodb.sms_senderids b on a.sender_id = b.id where status = 1 "
                        + " and a.type_id = :user_code "
                        + " order by b.name asc ";

            }

            Query q = session.createNativeQuery(qry, NODES.class);

            if (!userRoles.equals("ETZ")) {
                q.setParameter("user_code", user_code);
            }

            resp = q.getResultList();
        } catch (Exception we) {
            log.error(ERROR, we);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return resp;
    }

    public List<String> getInconId(String bankCode) {
        String inconId = "";
        List<String> userRoles = new ArrayList<>();
        Session session = DbHibernate.getSession("40.15MYSQL");
        try {

            String sql = "select group_concat(incon_id) as incon_id from ecarddb.e_tmcnode where issuer_code = :bankCode  group by issuer_code";

            Query q = session.createNativeQuery(sql).setParameter("bankCode", bankCode);
            userRoles = Arrays.asList(((String) q.getSingleResult()).split(","));

        } catch (Exception ex) {
            log.error(ERROR, ex);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return userRoles;
    }

    public List<String> getInconIdMap(String bankCode) {
        String inconId = "";
        List<String> userRoles = new ArrayList<>();
        Session session = DbHibernate.getSession("40.15MYSQL");
        try {

            String sql = "select group_concat(incon_id) as incon_id from ecarddb.e_tmcnode where issuer_code = :bankCode  group by issuer_code";

            Query q = session.createNativeQuery(sql).setParameter("bankCode", bankCode);
            userRoles = Arrays.asList(((String) q.getSingleResult()).split(","));

        } catch (Exception ex) {
            log.error(ERROR, ex);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return userRoles;
    }

    public List<BucketBalanceType> getBalanceTypes(String type_id, String user_code) {
        List<String> userRoles = new ArrayList<>();
        List<BucketBalanceType> balTypes = new ArrayList<>();

        if (!type_id.isEmpty()) {
            if (type_id.contains("[0]")) {
            } else if (type_id.contains("[25]")) {
                String roles = getRoleParameters("[2000000000000064]", user_code);
                System.out.println("usrRoles: " + userRoles);

                userRoles = Arrays.asList(roles.split("~"));

                System.out.println("result: " + userRoles);
            } else {
                return balTypes;
            }

        } else {
            return balTypes;
        }

        String qry = "select name, alias, minimum from telcodb.support_bucket_balance where status = 1 "
                + (type_id.contains("[0]") || userRoles.contains("ALL") ? "" : " and alias in(:userRoles) ");

        Session session = DbHibernate.getSession("APP_DB");
        try {

            Query q = session.createNativeQuery(qry, BucketBalanceType.class);

            if (!(type_id.contains("[0]") || userRoles.contains("ALL"))) {
                q.setParameter("userRoles", userRoles);

            }
            balTypes = q.getResultList();

        } catch (Exception e) {
            log.error(ERROR, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return balTypes;

    }

    public List<Bank> getBanks() {

        List<Bank> bankrecs = new ArrayList<>();

        Session session = DbHibernate.getSession("40.15MYSQL");
        try {
            Query q = session.createNativeQuery(
                    "select ifnull(issuer_acct, issuer_code) issuer_code, issuer_name  from ecarddb.e_issuer order by issuer_name asc");
            List<Object[]> bankrecords = (List<Object[]>) q.getResultList();

            for (Object[] record : bankrecords) {
                Bank b = new Bank();
                try {
                    b.setIssuer_code(checkNull(record[0]));
                    b.setIssuer_name(checkNull(record[1]));
                } catch (Exception et) {
                    log.error(ERROR, et);
                }
                bankrecs.add(b);
            }
        } catch (Exception e) {
            log.error(ERROR, e);
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (Exception e) {
                    log.error(ERROR, e);
                }
            }
        }

        return bankrecs;
    }

    public List<PeriodData> getPeriodValidation() {

        List<PeriodData> periodRecs = new ArrayList<>();

        Session session = DbHibernate.getSession("APP_DB");
        try {
            Query q = session.createNativeQuery(
                    "select id, name, UPPER(path) path, type, value from telcodb.period_restriction order by id asc", PeriodData.class);
            periodRecs = q.getResultList();

        } catch (Exception e) {
            log.error(ERROR, e);
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (Exception e) {
                    log.error(ERROR, e);
                }
            }
        }

        return periodRecs;
    }

    public List<Bank> getGipBanks() {

        List<Bank> bankrecs = new ArrayList<>();

        Session session = DbHibernate.getSession("40.15MYSQL");
        try {
            Query q = session.createNativeQuery(
                    "select ifnull(issuer_acct, issuer_code), issuer_name  from ecarddb.e_issuer order by issuer_name asc");
            List<Object[]> bankrecords = (List<Object[]>) q.getResultList();

            for (Object[] record : bankrecords) {
                Bank b = new Bank();
                try {
                    b.setIssuer_code(gipProp.getProperty("BANK_" + checkNull(record[0]), ""));
                    b.setIssuer_name(checkNull(record[1]));
                } catch (Exception et) {
                    log.error(ERROR, et);
                }
                bankrecs.add(b);
            }
        } catch (Exception e) {
            log.error(ERROR, e);
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (Exception e) {
                    log.error(ERROR, e);
                }
            }
        }

        return bankrecs;
    }

    public List<Bank> getBranches(String bankCode) {
        log.info("GET BRANCHES: " + bankCode);
        List<Bank> bankrecs = new ArrayList<>();

        if (bankCode == null || bankCode.trim().isEmpty() || bankCode.equalsIgnoreCase("ALL")
                || bankCode.equals("000")) {
            return bankrecs;
        }

        Session session = null;

        if (bankCode.equals("000")) {
            session = DbHibernate.getSession("40.15MYSQL");
        } else {
            session = DbHibernate.getSession(bankDB.getOrDefault(bankCode.trim(), null));
        }
        try {
            Query q = session.createNativeQuery("select sub_code, sub_name from e_subissuer where issuer_code = :bankCode and sub_name is not null and sub_name !='' order by sub_name asc").setParameter("bankCode", bankCode);
//            log.info("CHECKING" + q.toString());

            List<Object[]> bankrecords = (List<Object[]>) q.getResultList();

            for (Object[] record : bankrecords) {
                Bank b = new Bank();
                try {
                    b.setIssuer_code(checkNull(record[0]));
                    b.setIssuer_name(checkNull(record[1]));
                    bankrecs.add(b);
                } catch (Exception et) {
                    log.error(ERROR, et);
                }

            }
        } catch (Exception e) {
            log.error(ERROR, e);
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (Exception e) {
                    log.error(ERROR, e);
                }
            }
        }

        return bankrecs;
    }

    public List<Bank> getNetworks(String ucode) {

        List<Bank> bankrecs = new ArrayList<>();

        Session session = DbHibernate.getSession("APP_DB");
        try {
            Query q = session.createNativeQuery(
                    "select issuer_code, issuer_name  from ecarddb..e_issuer order by issuer_name asc");
            List<Object[]> bankrecords = (List<Object[]>) q.getResultList();

            for (Object[] record : bankrecords) {
                Bank b = new Bank();
                try {
                    b.setIssuer_code(checkNull(record[0]));
                    b.setIssuer_name(checkNull(record[1]));
                } catch (Exception e) {
                    log.error(ERROR, e);
                }
                bankrecs.add(b);
            }
        } catch (Exception e) {
            log.error(ERROR, e);
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (Exception e) {
                    log.error(ERROR, e);
                }
            }
        }
        return bankrecs;
    }

    public List<NODES> getBankNodes(String bankCode) {

        List<NODES> nodes = new ArrayList<>();

        Session session = DbHibernate.getSession("40.15MYSQL");
        try {
            System.out.println("BANK: " + bankCode);
            Query q = session.createNativeQuery("select incon_id id, incon_name name from ecarddb.e_tmcnode where 1=1 "
                    + (!bankCode.equals("000") ? " and issuer_code = :bankCode " : "")
                    + " order by incon_name asc");

            if (!bankCode.equals("000")) {
                q.setParameter("bankCode", bankCode);
            }
            List<Object[]> bankrecords = (List<Object[]>) q.getResultList();

            for (Object[] record : bankrecords) {
                NODES n = new NODES();
                try {
                    n.setId(checkNull(record[0]));
                    n.setName(checkNull(record[1]));
                } catch (Exception e) {
                    log.error(ERROR, e);
                }
                nodes.add(n);
            }
        } catch (Exception e) {
            log.error(ERROR, e);
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (Exception e) {
                    log.error(ERROR, e);
                }
            }
        }
        return nodes;
    }

    public List<Channel> getChannels() {

        List<Channel> channelList = new ArrayList<>();

        Session session = DbHibernate.getSession("40.4SYBASE");
        try {
            Query q = session.createNativeQuery(
                    "select channel_id, channel_name from ecarddb..e_channel order by channel_name asc");
            List<Object[]> chann = (List<Object[]>) q.getResultList();

            for (Object[] record : chann) {
                Channel c = new Channel();
                try {
                    c.setChannel_id(checkNull(record[0]));
                    c.setChannel_name(checkNull(record[1]));
                } catch (Exception e) {
                    log.error(ERROR, e);
                }
                channelList.add(c);
            }
        } catch (Exception e) {
            log.error(ERROR, e);
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (Exception e) {
                    log.error(ERROR, e);
                }
            }
        }
        return channelList;
    }

    public ArrayList<String> validateRequest(UserInfo userData, int type) {
        log.info("Type: " + type);
        ArrayList<String> errorMessages = new ArrayList<>();

        if (userData.getMobile() != null) {
            try {

                Long.valueOf(userData.getMobile());
                if (String.valueOf(userData.getMobile()).length() < 10) {
                    errorMessages.add("phoneNumber not complete");
                }
            } catch (Exception e) {
                log.error(ERROR, e);
                errorMessages.add("phoneNumber should consist only numbers");
            }
        }

        try {
            if (!Pattern.compile("^(?!\\.)(?!\\-)(?!.*?\\.\\.)(?!.*?\\-\\-)(?!.*\\-$)[a-zA-Z .-]+$")
                    .matcher(userData.getFirstName()).matches()) {
                errorMessages.add("Invalid FirstName");
            }
        } catch (Exception e) {
            log.error(ERROR, e);
            errorMessages.add("Invalid FirstName");
        }
        try {
            if (!Pattern.compile("^(?!\\.)(?!\\-)(?!.*?\\.\\.)(?!.*?\\-\\-)(?!.*\\-$)[a-zA-Z .-]+$")
                    .matcher(userData.getLastName()).matches()) {
                errorMessages.add("Invalid LastName");
            }
        } catch (Exception e) {
            log.error(ERROR, e);
            errorMessages.add("Invalid LastName");
        }
        if (type != 2) {
            try {
                if (userData.getNewUsername().trim().isEmpty() || userData.getNewUsername() == null) {
                    errorMessages.add("Invalid Username");
                }
                if (!Pattern.compile("^(?!\\.)(?!\\-)(?!.*?\\.\\.)(?!.*?\\-\\-)(?!.*\\-$)[a-zA-Z .-]+$")
                        .matcher(userData.getNewUsername()).matches()) {
                    errorMessages.add("Invalid Username");
                }
            } catch (Exception e) {
                log.error(ERROR, e);
                errorMessages.add("Invalid Username");
            }
        }

        return errorMessages;
    }

    public boolean validateRoleParams(String type_id, String usercode, String userOptions0, String userOptions1,
            String userOptions2) {
        boolean valid = false;

        if (!usercode.contains("[1]")) {

            List<String> roleList = Arrays.asList(getRoleParameters("[" + userOptions0 + "]", usercode).split("~"));
            System.out.println("RoleList: " + roleList);

            valid = roleList.contains(userOptions1) || roleList.contains("ALL") || roleList.contains("1");

        } else {
            valid = true;
        }

        System.out.println("TYPE ID::: " + type_id);
        System.out.println("usercode::: " + usercode);
        System.out.println("USEROPTION0::: " + userOptions0);
        System.out.println("USEROPTION1::: " + userOptions1);
        System.out.println("USEROPTION2::: " + userOptions2);
        System.out.println("Valid::: " + valid);

        return valid;
    }

    public String getRoleParameters(String role, String rolesList) {
        String roleParam = "";
        if (!rolesList.isEmpty()) {
            try {
                int firstParam = rolesList.indexOf(role);
                int nextParam = rolesList.indexOf(",", firstParam);
                roleParam = rolesList.substring(firstParam, (nextParam == -1) ? rolesList.length() : nextParam)
                        .split("[|]")[1];
            } catch (Exception e) {
                log.error(ERROR, e);
            }
        }
        return roleParam;
    }

    public String getSetting(String key) {
        String data = new PortalSettings().getSetting(key);
        return data;
    }

    protected String checkNull(Object Data) {
        if (Data != null && !Data.equals("")) {
            return Data.toString();
        }
        return "";
    }

    public String createMsg(String name, String pin) {
        String message = StringUtils.substringBeforeLast(getSetting("otp_sms_template"), ":");
        message = message.replace("<NAME>", name);
        message = message.replace("<OTP>", pin);
        return message;
    }

    public static HashMap<String, String> loadUserOptions() {
        HashMap<String, String> useroptions = new HashMap<>();

        String query = "select query, dbIp, type_id  from telcodb.support_user_optionsQuery order by type_id asc";
        Session session = DbHibernate.getSession("APP_DB");

        try {

            Query q = session.createNativeQuery(query);
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

                        q = dbsession.createNativeQuery(sql, RoleOption.class);

                        options = q.getResultList();

                        for (RoleOption role : options) {
                            useroptions.put(typeId + "-" + role.getAlias(), role.getName());
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

    public static HashMap<String, String> getMB2bMeerchants() {
        HashMap<String, String> merchantCodes = new HashMap<>();
        Session session = DbHibernate.getSession("MOBILEDBMYSQL");

        try {
            String query = "select UPPER(alias) merchant, merchant_code  from mobiledb.m_b2b_merchants where appid = 'DEFAULT';";
            Query q = session.createNativeQuery(query);
            List<Object[]> resp = q.getResultList();

            for (Object[] record : resp) {
                try {
                    merchantCodes.put(record[0].toString(), record[1].toString());

                } catch (Exception ey) {
                    log.error(ERROR, ey);
                }
            }
        } catch (Exception et) {
            log.error(ERROR, et);
        } finally {
            if (session != null) {
                session.close();
            }

        }

        return merchantCodes;

    }
}
