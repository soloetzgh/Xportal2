package com.etzgh.xportal.controller;

import com.etzgh.xportal.cdi.UserSession;
import com.etzgh.xportal.dao.AppDao;
import com.etzgh.xportal.dao.MobileAppDao;
import com.etzgh.xportal.model.AccountRequest;
import com.etzgh.xportal.model.ApiPostData;
import com.etzgh.xportal.model.Bank;
import com.etzgh.xportal.model.BankApplication;
import com.etzgh.xportal.model.CustomerFeedback;
import com.etzgh.xportal.model.ECARDHOLDER;
import com.etzgh.xportal.model.FraudReport;
import com.etzgh.xportal.model.JsonResponse;
import com.etzgh.xportal.model.MobileActivateLog;
import com.etzgh.xportal.model.MobileAppTransaction;
import com.etzgh.xportal.model.MobileAppUser;
import com.etzgh.xportal.model.MobileAppUserLog;
import com.etzgh.xportal.model.NODES;
import com.etzgh.xportal.utility.DoHTTPRequest;
import com.etzgh.xportal.utility.GeneralUtility;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;

/**
 * @author sunkwa-arthur
 */
public class MobileAppProxy extends HttpServlet {

    private static final long serialVersionUID = 2779093570295166493L;

    private static final GeneralUtility utility = new GeneralUtility();

    private static final Logger log = Logger.getLogger(MobileAppProxy.class.getName());

    public static final PortalSettings portalSettings = new PortalSettings();

    private static List<Bank> bankList = new ArrayList<>();
    private static final String ERROR = "ERROR";

    static {
        bankList = new AppDao().getBanks();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserSession userSession;

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF8");
        try {
            userSession = (UserSession) request.getSession().getAttribute("userSession");
            String action = request.getParameterMap().containsKey("action") ? request.getParameter("action") : "";
            String appId = request.getParameterMap().containsKey("appId") ? request.getParameter("appId") : "ALL";

            List<String> appIdList = new ArrayList<>();
            List<String> roleList = new ArrayList<>();
            String[] ussdList = {"searchLog", "searchLogWithPhone", "search", "searchRegister", "modifyAccount", "NMOTP", "appIdList",
                "searchMobileAppLog", "getAccountName", "modifyMobileApp", "getBankTopics", "sendNotification", "getUserAccounts"};

            try (OutputStream out = response.getOutputStream()) {

                ApiPostData apiData = new ApiPostData();
                String userType = userSession.getUser().getType_id();

                apiData.setService(request.getParameter("service"));
                apiData.setUserCode(userSession.getUser().getUser_code());
                apiData.setIpAddress((String) request.getAttribute("ipAddress"));

                apiData.setUser_id(userSession.getUser().getUser_id());
                apiData.setUserName(userSession.getUser().getUsername());
                apiData.setType_id(userSession.getUser().getType_id());
                apiData.setBranch(userSession.getUser().getBranchCode());
                apiData.setStartDate(request.getParameter("startDate"));
                apiData.setEndDate(request.getParameter("endDate"));
                apiData.setTransType(request.getParameter("type"));
                apiData.setStatus(request.getParameter("status"));
                apiData.setMobile_no(request.getParameter("mobile_no"));
                apiData.setCard_num(request.getParameter("card_num"));
                apiData.setSubscriberId(request.getParameter("new_mobile_no"));
                apiData.setPayType(request.getParameter("enhancement_type"));
                apiData.setSearchKey(request.getParameter("searchKey"));

                apiData.setUniqueTransId(request.getParameter("uniqueTransId"));

                apiData.setApiCode(request.getParameter("otp"));
                String bankCode = request.getParameterMap().containsKey("bank_code") ? request.getParameter("bank_code")
                        : "";

                String reason = request.getParameterMap().containsKey("reason") ? request.getParameter("reason") : "";

                Boolean allowUser = false;
                apiData.setAction(action);

                if (apiData.getService() == null) {
                    apiData.setService(action);
                }

                if (apiData.getType_id().contains("[0]")) {
                } else if (apiData.getType_id().contains("[6]")) {
                    bankCode = utility.getRoleParameters("[2000000000000060]", apiData.getUserCode());
                } else {
                    bankCode = userSession.getUser().getBankCode();
                }

                apiData.setBank_code(bankCode);

                if (userType.contains("[0]")) {
                    allowUser = true;

                    appIdList.add(appId);
                    apiData.setAppId(appIdList);
                } else if (userType.contains("[39]")
                        && Arrays.stream(ussdList).anyMatch(apiData.getService()::equals)) {
                    String userRoles = utility.getRoleParameters("[2500000000000050]",
                            userSession.getUser().getUser_code());
                    log.info("usrRoles: " + userRoles);

                    if (appId.equalsIgnoreCase("all")) {
                        allowUser = true;
                        apiData.setAppId(Arrays.asList(userRoles.split("~")));
                    } else {
                        if (Arrays.asList(userRoles.split("~")).contains(appId)) {
                            allowUser = true;
                            appIdList.add(appId);
                            apiData.setAppId(appIdList);
                        }
                    }
                }
                log.info("DATA: " + apiData);
                roleList = Arrays
                        .asList(utility.getRoleParameters("[51]", userSession.getUser().getUser_code()).split("~"));
                String uRole = "";
                if (action.equals("addaccount") && apiData.getService().equalsIgnoreCase("processAccountRequest")
                        && userType.contains("[61]")) {
                    uRole = "[2500000000000058]";
                } else if ((apiData.getService().equalsIgnoreCase("accountRequests")
                        || apiData.getService().equalsIgnoreCase("processAccountRequest"))
                        && userType.contains("[55]")) {
                    uRole = "[2500000000000059]";
                } else if (apiData.getService().equalsIgnoreCase("mobileAppFeedback") && userType.contains("[57]")) {
                    uRole = "[2500000000000058]";
                } else if (apiData.getService().equalsIgnoreCase("mobileAppFraud") && userType.contains("[63]")) {
                    uRole = "[2500000000000058]";
                } else if ((apiData.getService().equalsIgnoreCase("bankApplications")
                        || apiData.getService().equalsIgnoreCase("processBankApplicationRequest"))
                        && userType.contains("[58]")) {
                    uRole = "[2500000000000056]";
                } else if (apiData.getService().equalsIgnoreCase("mobileAppTransactions")
                        && userType.contains("[59]")) {
                    uRole = "[2500000000000058]";
                } else if ((apiData.getService().equalsIgnoreCase("mobileAppUsers") || apiData.getService().equalsIgnoreCase("mobileAppUsersSearch")
                        || apiData.getService().equalsIgnoreCase("getUserAccounts") || apiData.getService().equalsIgnoreCase("modifyMobileApp"))
                        && userType.contains("[60]")) {
                    uRole = "[2500000000000058]";
                } else if ((apiData.getService().equalsIgnoreCase("mobileAppUserLog")
                        || apiData.getService().equalsIgnoreCase("modifyMobileApp")) && userType.contains("[61]")) {
                    uRole = "[2500000000000058]";
                } else if ((apiData.getService().equalsIgnoreCase("getBankTopics")
                        || apiData.getService().equalsIgnoreCase("sendNotification")) && userType.contains("[60]")) {
                    uRole = "[2500000000000058]";
                }
                if (!uRole.isEmpty()) {
                    allowUser = true;
                    roleList = Arrays
                            .asList(utility.getRoleParameters(uRole, userSession.getUser().getUser_code()).split("~"));
                }
                log.info("ROLES: " + roleList + " -- " + allowUser);
                allowUser = allowUser || !roleList.isEmpty();
                if (allowUser) {
                    apiData.setChannel(request.getParameter("appId"));

                    if (roleList.contains("2")) {
                        apiData.setRole_id("2");
                    } else if (roleList.contains("1") || roleList.contains("3")) {
                        apiData.setRole_id("3");
                    }

                    if (apiData.getService().equalsIgnoreCase("searchLog") || apiData.getService().equalsIgnoreCase("searchLogWithPhone")) {
                        apiData.setTrans_code(request.getParameter("sort_by"));

                        List<MobileActivateLog> records = new MobileAppDao()
                                .getMobileAppActivateLog(new Gson().toJson(apiData));

                        if (records != null && records.size() > 0) {
                            response.getOutputStream().println(
                                    new Gson().toJson(new JsonResponse("00", "success", new Gson().toJson(records))));
                        } else {
                            response.getOutputStream()
                                    .println(new Gson().toJson(new JsonResponse("01", "No Data Found")));
                        }

                    } else if (apiData.getService().equalsIgnoreCase("search")) {

                        List<ECARDHOLDER> records = new MobileAppDao().getMobileAppActivate(new Gson().toJson(apiData));

                        if (records != null && records.size() > 0) {
                            response.getOutputStream().println(
                                    new Gson().toJson(new JsonResponse("00", "success", new Gson().toJson(records))));
                        } else {
                            response.getOutputStream()
                                    .println(new Gson().toJson(new JsonResponse("01", "No Data Found")));
                        }
                    } else if (apiData.getService().equalsIgnoreCase("searchRegister")) {

                        List<ECARDHOLDER> records = new MobileAppDao().getMobileAppActivate(new Gson().toJson(apiData));

                        if (records != null && records.size() > 0) {
                            response.getOutputStream().println(
                                    new Gson().toJson(new JsonResponse("00", "success", new Gson().toJson(records))));
                        } else {
                            response.getOutputStream()
                                    .println(new Gson().toJson(new JsonResponse("01", "No Data Found")));
                        }
                    } else if (apiData.getService().equalsIgnoreCase("modifyAccount")
                            && (apiData.getRole_id().equals("1") || apiData.getRole_id().equals("2")
                            || apiData.getRole_id().equals("3"))) {
                        apiData.setClientId(request.getParameter("account_id"));
                        apiData.setLineType(request.getParameter("appId"));
                        apiData.setProduct(request.getParameterMap().containsKey("account_change")
                                ? request.getParameter("account_change")
                                : "");
                        apiData.setOptionType(reason);
                        apiData.setStatus(request.getParameter("choice"));
                        apiData.setRoleList(roleList);

                        String resp = new MobileAppDao().modifyCardAccount(new Gson().toJson(apiData));
                        response.getOutputStream().println(resp);

                    } else if (apiData.getService().equalsIgnoreCase("accountRequests")) {
                        apiData.setTrans_code(request.getParameter("sort_by"));

                        List<AccountRequest> records = new MobileAppDao()
                                .getMobileAppAccountRequestsLog(new Gson().toJson(apiData));

                        if (records != null && records.size() > 0) {
                            response.getOutputStream().println(
                                    new Gson().toJson(new JsonResponse("00", "success", new Gson().toJson(records))));
                        } else {
                            response.getOutputStream()
                                    .println(new Gson().toJson(new JsonResponse("01", "No Data Found")));
                        }

                    } else if (apiData.getService().equalsIgnoreCase("mobileAppFeedback")) {

                        List<CustomerFeedback> records = new MobileAppDao()
                                .getMobileAppFeedbackLog(new Gson().toJson(apiData));

                        if (records != null && records.size() > 0) {
                            response.getOutputStream()
                                    .write(new Gson()
                                            .toJson(new JsonResponse("00", "success", new Gson().toJson(records)))
                                            .getBytes("UTF-8"));
                        } else {
                            response.getOutputStream()
                                    .println(new Gson().toJson(new JsonResponse("01", "No Data Found")));
                        }

                    } else if (apiData.getService().equalsIgnoreCase("mobileAppFraud")) {

                        List<FraudReport> records = new MobileAppDao()
                                .getMobileAppFraudReport(new Gson().toJson(apiData));

                        if (records != null && records.size() > 0) {
                            response.getOutputStream().println(
                                    new Gson().toJson(new JsonResponse("00", "success", new Gson().toJson(records))));
                        } else {
                            response.getOutputStream()
                                    .println(new Gson().toJson(new JsonResponse("01", "No Data Found")));
                        }

                    } else if (apiData.getService().equalsIgnoreCase("mobileAppTransactions")
                            || apiData.getService().equalsIgnoreCase("mobileAppTransactionsSearch")) {
                        apiData.setTrans_code(request.getParameter("trans_code"));

                        List<MobileAppTransaction> records = new MobileAppDao()
                                .getMobileAppTransactions(new Gson().toJson(apiData));

                        if (records != null && records.size() > 0) {

                            response.getOutputStream()
                                    .write(new Gson()
                                            .toJson(new JsonResponse("00", "success", new Gson().toJson(records)))
                                            .getBytes("UTF-8"));
                        } else {
                            response.getOutputStream()
                                    .println(new Gson().toJson(new JsonResponse("01", "No Data Found")));
                        }

                    } else if (apiData.getService().equalsIgnoreCase("bankApplications")) {

                        List<BankApplication> records = new MobileAppDao()
                                .getBankApplicationsLog(new Gson().toJson(apiData));

                        if (records != null && records.size() > 0) {
                            response.getOutputStream().println(
                                    new Gson().toJson(new JsonResponse("00", "success", new Gson().toJson(records))));
                        } else {
                            response.getOutputStream()
                                    .println(new Gson().toJson(new JsonResponse("01", "No Data Found")));
                        }

                    } else if (apiData.getService().equalsIgnoreCase("fraudReport")) {
                        apiData.setTrans_code(request.getParameter("trans_code"));

                        List<FraudReport> records = new MobileAppDao()
                                .getMobileAppFraudReport(new Gson().toJson(apiData));

                        if (records != null && records.size() > 0) {
                            response.getOutputStream().println(
                                    new Gson().toJson(new JsonResponse("00", "success", new Gson().toJson(records))));
                        } else {
                            response.getOutputStream()
                                    .println(new Gson().toJson(new JsonResponse("01", "No Data Found")));
                        }

                    } else if (apiData.getService().equalsIgnoreCase("downloadBankApplicationImage")) {
                        apiData.setUniqueTransId(request.getParameter("reference"));

                        String filePath = new MobileAppDao().getBankApplicationImage(new Gson().toJson(apiData));

                        File file = new File(filePath);

                        if (filePath.trim().isEmpty() || !file.exists()) {
                            response.getOutputStream().println(
                                    new Gson().toJson(new JsonResponse("01", "An error occured. Image not found")));
                        } else {
                            String filename = file.getName();
                            response.setContentType("application/octet-stream");
                            response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");

                            try (FileInputStream in = new FileInputStream(file)) {
                                byte[] buffer = new byte[4096];
                                int length;
                                while ((length = in.read(buffer)) > 0) {
                                    out.write(buffer, 0, length);
                                }
                            }
                            out.flush();
                        }

                    } else if (apiData.getService().equalsIgnoreCase("sendNotification")) {
                        String type = request.getParameter("type");
                        String destination = request.getParameter("destination");
                        String title = request.getParameter("title");
                        String body = request.getParameter("body");

                        String notif = "{\n"
                                + "    \"action\": \"" + type + "\",\n"
                                + "    \"bankCode\": \"" + apiData.getBank_code() + "\",\n"
                                + (type.equalsIgnoreCase("sendtotopic")
                                ? "    \"topicName\": \"" + destination
                                + "\",\n  \"mobile\": \"233240000000\",\n"
                                : " \"mobile\": \"" + destination + "\",\n ")
                                + "    \"title\": \"" + title + "\",\n"
                                + "    \"body\": \"" + body + "\"\n"
                                + "}";
                        String url = StringUtils.substringBeforeLast(portalSettings.getSetting("m_auth_url"), ":");

                        String notifResp = new DoHTTPRequest().post(url, notif);

                        JSONObject json = new JSONObject(notifResp);

                        if (json.optString("error", "6").equals("0")) {
                            response.getOutputStream().println(new Gson().toJson(new JsonResponse("00",
                                    "Notification sent successfully", json.optString("message", "SUCCESSFUL"))));
                        } else {
                            response.getOutputStream().println(new Gson().toJson(new JsonResponse("01",
                                    json.optString("message", "An error occured sending notification"))));
                        }

                    } else if (apiData.getService().equalsIgnoreCase("getBankTopics")) {
                        List<NODES> topicList = new ArrayList<>();
                        if (apiData.getBank_code() == null || apiData.getBank_code().trim().isEmpty()
                                || apiData.getBank_code().equalsIgnoreCase("undefined")) {
                            // Do Nothing
                        } else {
                            String notif = "{\n"
                                    + "    \"action\": \"GETTOPICS\",\n"
                                    + "    \"mobile\": \"233241234567\",\n"
                                    + "    \"bankCode\": \"" + apiData.getBank_code() + "\"\n"
                                    + "}";
                            String url = StringUtils.substringBeforeLast(portalSettings.getSetting("m_auth_url"), ":");

                            String notifResp = new DoHTTPRequest().post(url, notif);

                            JSONObject json = new JSONObject(notifResp);

                            if (json.optString("error", "6").equals("0")) {

                                String[] topics = json.optString("topics", "").replace("\"", "")
                                        .replaceAll("\\[(.*?)\\]", "$1").split(",");

                                for (String n : topics) {
                                    NODES topic = new NODES();
                                    topic.setId(n);
                                    topic.setName(n);
                                    topicList.add(topic);
                                }

                            }

                        }
                        response.getOutputStream().println(new Gson().toJson(topicList));

                    } else if (apiData.getService().equalsIgnoreCase("transCategory")) {

                        List<NODES> records = new MobileAppDao().getCatList();

                        response.getOutputStream().println(new Gson().toJson(records));

                    } else if (apiData.getService().equalsIgnoreCase("products")) {

                        List<NODES> records = MobileAppDao.getTransactionTypes(request.getParameter("merchant"));

                        response.getOutputStream().println(new Gson().toJson(records));

                    } else if (apiData.getService().equalsIgnoreCase("mobileAppUsers") || apiData.getService().equalsIgnoreCase("mobileAppUsersSearch")) {

                        List<MobileAppUser> records = new MobileAppDao().getMobileAppUsers(new Gson().toJson(apiData));

                        if (records != null && records.size() > 0) {
                            response.getOutputStream().println(
                                    new Gson().toJson(new JsonResponse("00", "success", new Gson().toJson(records))));
                        } else {
                            response.getOutputStream()
                                    .println(new Gson().toJson(new JsonResponse("01", "No Data Found")));
                        }

                    } else if (apiData.getService().equalsIgnoreCase("searchMobileAppLog")) {
                        apiData.setTrans_code(request.getParameter("sort_by"));

                        List<MobileAppUserLog> records = new MobileAppDao()
                                .getMobileAppUserLog(new Gson().toJson(apiData));

                        if (records != null && records.size() > 0) {
                            response.getOutputStream().println(
                                    new Gson().toJson(new JsonResponse("00", "success", new Gson().toJson(records))));
                        } else {
                            response.getOutputStream()
                                    .println(new Gson().toJson(new JsonResponse("01", "No Data Found")));
                        }

                    } else if (apiData.getService().equalsIgnoreCase("modifyMobileApp")
                            && (apiData.getRole_id().equals("1") || apiData.getRole_id().equals("2")
                            || apiData.getRole_id().equals("3"))) {
                        apiData.setClientId(request.getParameter("customer_id"));

                        apiData.setOptionType(reason);
                        apiData.setStatus(request.getParameter("choice"));
                        apiData.setRoleList(roleList);
                        apiData.setAction(action.replace("User", ""));

                        String resp = new MobileAppDao().modifyMobileAppUser(new Gson().toJson(apiData));
                        response.getOutputStream().println(resp);

                    } else if (apiData.getService().equalsIgnoreCase("processAccountRequest")
                            && (apiData.getRole_id().equals("1") || apiData.getRole_id().equals("2")
                            || apiData.getRole_id().equals("3"))) {
                        apiData.setClientId(request.getParameter("account"));
                        apiData.setUniqueTransId(request.getParameter("reference"));

                        apiData.setOptionType(reason);
                        apiData.setStatus(request.getParameter("choice"));
                        apiData.setRoleList(roleList);

                        String resp = new MobileAppDao().processAccountRequest(new Gson().toJson(apiData)).toString();
                        response.getOutputStream().println(resp);

                    } else if (apiData.getService().equalsIgnoreCase("processBankApplicationRequest")
                            && (apiData.getRole_id().equals("1") || apiData.getRole_id().equals("2")
                            || apiData.getRole_id().equals("3"))) {
                        apiData.setClientId(request.getParameter("reference"));
                        apiData.setLineType(request.getParameter("appId"));

                        apiData.setOptionType(reason);
                        apiData.setStatus(request.getParameter("choice"));
                        apiData.setRoleList(roleList);

                        apiData.setAction("bankapplication");

                        String resp = new MobileAppDao().processBankApplicationRequest(apiData).toString();
                        response.getOutputStream().println(resp);

                    } else if (apiData.getService().equalsIgnoreCase("NMOTP") && (apiData.getRole_id().equals("1")
                            || apiData.getRole_id().equals("2") || apiData.getRole_id().equals("3"))) {
                        apiData.setAccountNumber(request.getParameter("account_number"));
                        apiData.setClientId(request.getParameter("account_id"));
                        apiData.setLineType(request.getParameter("appId"));
                        String resp = new MobileAppDao().sendNumberModificationOtp(new Gson().toJson(apiData))
                                .toString();
                        response.getOutputStream().println(resp);

                    } else if (action.equalsIgnoreCase("appIdList")) {

                        if (!appId.isEmpty()) {
                            response.getOutputStream().println(
                                    new Gson().toJson(new MobileAppDao().getMobileAppIdList(apiData.getAppId())));
                        } else {
                            response.getOutputStream().println(new Gson().toJson(appId));
                        }
                    } else if (action.equalsIgnoreCase("nm_otp")) {

                        if (!appId.isEmpty()) {
                            response.getOutputStream().println(new Gson().toJson(new MobileAppDao().useNMOtpList()));
                        } else {
                            response.getOutputStream().println(new Gson().toJson(appId));
                        }
                    } else if (apiData.getService().equalsIgnoreCase("banks")) {

                        List<Bank> records = userType.contains("[0]") ? bankList : new ArrayList<>();

                        response.getOutputStream().println(new Gson().toJson(records));
                    } else if (apiData.getService().equalsIgnoreCase("getAccountName")) {
                        apiData.setClientId(request.getParameter("account_id"));
                        apiData.setLineType(request.getParameter("appId"));
                        String name = new MobileAppDao().getNameFromBank(apiData);

                        if (name != null) {
                            response.getOutputStream()
                                    .println(new Gson().toJson(new JsonResponse("00", "success", name)));
                        } else {
                            response.getOutputStream().println(new Gson()
                                    .toJson(new JsonResponse("01", "Could not retrieve account name from bank")));
                        }

                    } else if (apiData.getService().equalsIgnoreCase("getUserAccounts")) {
                        apiData.setLineType(request.getParameter("appId"));

                        List<NODES> records = new MobileAppDao().getUserAccounts(apiData);

                        response.getOutputStream().println(new Gson().toJson(records));

                    } else {

                        response.getOutputStream().println(
                                new Gson().toJson(new JsonResponse("01", "Invalid Service: " + apiData.getService())));

                    }

                } else {
                    response.getOutputStream()
                            .println(new Gson().toJson(new JsonResponse("01", "Authorization Required")));

                }
            }
        } catch (Exception e) {
            log.error(ERROR, e);

        }

    }

    public static boolean isNumeric(String inputData) {
        return inputData.matches("[-+]?\\d+(\\.\\d+)?");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            processRequest(request, response);
        } catch (Exception ex) {
            log.error("error", ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            log.error("error", ex);
        }
    }

}
