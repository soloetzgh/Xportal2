package com.etzgh.xportal.controller;

import com.etzgh.xportal.cdi.UserSession;
import com.etzgh.xportal.dao.AppDao;
import com.etzgh.xportal.model.ApiPostData;
import com.etzgh.xportal.model.JsonResponse;
import com.etzgh.xportal.model.PortalSettingsData;
import com.etzgh.xportal.model.RoleInfoData;
import com.etzgh.xportal.model.UserManagement;
import com.etzgh.xportal.service.PortalSettingService;
import com.etzgh.xportal.service.UserProfileService;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author sunkwa-arthur
 */
public class AdminProxy extends HttpServlet {

    private static final long serialVersionUID = -2550330452223099798L;

    private static final UserProfileService userprofileService = new UserProfileService();
    private static final PortalSettingService portalSettingService = new PortalSettingService();
    private static final Logger log = Logger.getLogger(AdminProxy.class.getName());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserSession userSession = (UserSession) request.getSession().getAttribute("userSession");
        final String requestOrigin = (String) request.getAttribute("Origin");

        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {

            ApiPostData apiData = new ApiPostData();

            apiData.setService(request.getParameter("service"));
            apiData.setOrigin(requestOrigin);

            apiData.setType_id(userSession.getUser().getType_id());
            apiData.setBank_code(userSession.getUser().getBankCode());
            apiData.setUserCode(userSession.getUser().getUser_code());
            apiData.setIpAddress((String) request.getAttribute("ipAddress"));
            apiData.setUser_id(userSession.getUser().getUser_id());
            apiData.setCardSchemeNumbers(userSession.getUser().getCardSchemeNumbers());

            apiData.setOrigin((String) request.getAttribute("Origin"));

            apiData.setCompany(userSession.getUser().getCompany());

            String action = request.getParameterMap().containsKey("action") ? request.getParameter("action") : "";

            if (!action.isEmpty()) {
                apiData.setService(action);
            }

            Boolean isAdmin = apiData.getUserCode().contains("[1]");
            Boolean isExtAdmin = apiData.getUserCode().contains("[2]");

            if (isAdmin || isExtAdmin) {
                if (apiData.getService().equalsIgnoreCase("users")) {
                    String username = request.getParameter("username");

                    String specific = request.getParameterMap().containsKey("init") ? "true" : "false";
                    String order = request.getParameterMap().containsKey("order") ? request.getParameter("username") : "";
                    String sort_by = request.getParameterMap().containsKey("sort_by") ? request.getParameter("sort_by") : "";
                    String status = request.getParameterMap().containsKey("status") ? request.getParameter("status") : "";
                    String startDate = request.getParameterMap().containsKey("startDate") ? request.getParameter("startDate") : "";
                    String endDate = request.getParameterMap().containsKey("endDate") ? request.getParameter("endDate") : "";
                    String secure = request.getParameterMap().containsKey("2fa") ? request.getParameter("2fa") : "";
                    String type = request.getParameterMap().containsKey("type") ? request.getParameter("type") : "";
                    String company = request.getParameterMap().containsKey("company") ? request.getParameter("company") : "";
                    String userType = request.getParameterMap().containsKey("userType") ? request.getParameter("userType") : "ALL";

                    System.out.println("TYPE :: " + request.getParameterMap().containsKey("type") + " :: " + request.getParameter("type"));

                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("order", order);
                    hashMap.put("sort_by", sort_by);
                    hashMap.put("status", status);
                    hashMap.put("startDate", startDate);
                    hashMap.put("endDate", endDate);
                    hashMap.put("2fa", secure);
                    hashMap.put("type", type);
                    hashMap.put("userType", userType);

                    hashMap.put("company", isExtAdmin ? apiData.getCompany() : company);

                    apiData.setOptions(hashMap);

                    apiData.setUserName(username);
                    apiData.setType(specific);

                    log.info(apiData);

                    List<UserManagement> records = userprofileService.getUsers(new Gson().toJson(apiData));

                    if (records != null && records.size() > 0) {

                        out.println(new Gson().toJson(new JsonResponse("00", "success", new Gson().toJson(records))));
                    } else {
                        out.println(new Gson().toJson(new JsonResponse("01", "No Data Found")));
                    }

                } else if (apiData.getService().equalsIgnoreCase("banks") && isAdmin) {

                    out.println(new Gson().toJson(new AppDao().getBanks()));

                } else if (apiData.getService().equalsIgnoreCase("branches") && (isAdmin || isExtAdmin)) {
                    String bankCode = request.getParameter("bankCode");

                    if (isExtAdmin) {
                        bankCode = apiData.getBank_code();
                    }

                    out.println(new Gson().toJson(new AppDao().getBranches(bankCode)));

                } else if (apiData.getService().equalsIgnoreCase("typeIdList")) {

                    out.println(new Gson().toJson(new AppDao().getTypeIdList(isAdmin, isExtAdmin, apiData.getUser_id())));

                } else if (apiData.getService().equalsIgnoreCase("checkUsername")) {
                    String username = request.getParameter("username");
                    apiData.setUserName(username);
                    log.info(apiData);
                    String respMsg = userprofileService.checkUsers(username);

                    out.println(new Gson().toJson(new JsonResponse("00", "success", respMsg)));

                } else if (apiData.getService().equalsIgnoreCase("createUser")) {
                    String requestData = request.getParameter("createUserData");
                    log.info("request : " + requestData);
                    String respMsg = "";
                    if (requestData != null) {
                        apiData.setUserData(requestData);
                        respMsg = userprofileService.createUser(new Gson().toJson(apiData));
                    }
                    out.println(new Gson().toJson(new JsonResponse("00", "success", respMsg)));

                } else if (apiData.getService().equalsIgnoreCase("modifyUser")) {
                    String requestData = request.getParameter("modifyUserData");
                    log.info("request : " + requestData);
                    String respMsg = "";
                    if (requestData != null) {
                        apiData.setUserData(requestData);
                        respMsg = userprofileService.modifyUser(new Gson().toJson(apiData));
                    }
                    out.println(new Gson().toJson(new JsonResponse("00", "success", respMsg)));

                } else if (apiData.getService().equalsIgnoreCase("modifyUserRoles")) {
                    String requestData = request.getParameter("modifyUserRoles");
                    log.info("request : " + requestData);
                    String respMsg = "";
                    if (requestData != null) {
                        apiData.setUserData(requestData);
                        respMsg = userprofileService.modifyUserRoles(new Gson().toJson(apiData));
                    }
                    out.println(new Gson().toJson(new JsonResponse("00", "success", respMsg)));

                } else if (apiData.getService().equalsIgnoreCase("toggleStatus")) {
                    apiData.setStatus(request.getParameter("status"));
                    apiData.setClientId(request.getParameter("userId"));
                    log.info(apiData);

                    String respMsg = userprofileService.changeUserStatus(new Gson().toJson(apiData));

                    out.println(new Gson().toJson(new JsonResponse("00", "success", respMsg)));
                } else if (apiData.getService().equalsIgnoreCase("resetPassword")) {

                    apiData.setClientId(request.getParameter("userId"));
                    log.info(apiData);

                    String respMsg = userprofileService.resetPasswordAdmin(new Gson().toJson(apiData));

                    out.println(new Gson().toJson(new JsonResponse("00", "success", respMsg)));
                } else if (apiData.getService().equalsIgnoreCase("fetchUserRoles")) {

                    apiData.setClientId(request.getParameter("userId"));

                    log.info(apiData);

                    RoleInfoData respMsg = userprofileService.fetchUserRoles(new Gson().toJson(apiData));

                    out.println(new Gson().toJson(new JsonResponse("00", "success", new Gson().toJson(respMsg))));
                } else if (apiData.getService().equalsIgnoreCase("portalSettings")) {

                    apiData.setTransType(request.getParameter("setting_name"));
                    log.info(apiData);
                    List<PortalSettingsData> respMsg = portalSettingService.getPortalSettings(new Gson().toJson(apiData));

                    out.println(new Gson().toJson(new JsonResponse("00", "success", new Gson().toJson(respMsg))));
                } else if (apiData.getService().equalsIgnoreCase("portalSettingsUpdate")) {
                    apiData.setUserData(request.getParameter("modifyPortalSettings"));
                    log.info(apiData);

                    String respMsg = portalSettingService.updatePortalSettings(new Gson().toJson(apiData));

                    out.println(new Gson().toJson(new JsonResponse("00", "success", respMsg)));
                } else if (apiData.getService().equalsIgnoreCase("changePortalSettingsStatus")) {
                    apiData.setClientId(request.getParameter("id"));
                    apiData.setStatus(request.getParameter("status"));
                    log.info(apiData);

                    String respMsg = portalSettingService.changePortalSettingsStatus(new Gson().toJson(apiData));

                    out.println(new Gson().toJson(new JsonResponse("00", "success", respMsg)));
                }
            } else {
                out.println(new Gson().toJson(new JsonResponse("01", "Authorization Required")));
            }

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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            log.error("error", ex);
        }
    }
}
