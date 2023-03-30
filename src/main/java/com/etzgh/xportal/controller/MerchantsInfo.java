package com.etzgh.xportal.controller;

import com.etzgh.xportal.cdi.UserSession;
import com.etzgh.xportal.dao.MobileAppDao;
import com.etzgh.xportal.model.MenuOptions;
import com.etzgh.xportal.model.VasGateMerchant;
import com.etzgh.xportal.service.UserProfileService;
import com.etzgh.xportal.utility.DoHTTPRequest;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
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
public class MerchantsInfo extends HttpServlet {

    private static final long serialVersionUID = 2779093570295166493L;

    private static final UserProfileService roleService = new UserProfileService();

    private static final Logger log = Logger.getLogger(MerchantsInfo.class.getName());

    private static final String BALANCEENDPOINT;

    static {
        BALANCEENDPOINT = StringUtils.substringBeforeLast(new PortalSettings().getSetting("balance_endpoint"), ":");
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserSession userSession;
        response.setContentType("text/html;charset=UTF-8");
        try {
            userSession = (UserSession) request.getSession().getAttribute("userSession");
            String action = request.getParameterMap().containsKey("action") ? request.getParameter("action") : "";
            String option = request.getParameterMap().containsKey("option") ? request.getParameter("option") : "";
            try (PrintWriter out = response.getWriter()) {
                if (action.equalsIgnoreCase("menuList")) {

                    List<MenuOptions> menuItems = roleService.getUserMenuList();
                    out.println(new Gson().toJson(menuItems));
                } else if (action.equalsIgnoreCase("portalSettings")) {
                    out.println(roleService.getPortalClientSettings());
                } else {

                    String userType = userSession.getUser().getType_id();
                    String userId = userSession.getUser().getUser_id();

                    String userCode = userSession.getUser().getUser_code();

                    Boolean isAdmin = userCode.contains("[1]");
                    Boolean isExtAdmin = userCode.contains("[2]");

                    if (action.equalsIgnoreCase("fetchBucketsBalance")) {

                        String balanceType = request.getParameterMap().containsKey("type")
                                ? request.getParameter("type")
                                : "";
                        if (!balanceType.isEmpty()) {
                            boolean isAllowed = false;

                            if (userType.contains("[0]")) {
                                isAllowed = true;
                            } else if (userType.contains("[25]")) {
                                String userRoles = roleService.getRoleParameters("[2000000000000064]", userCode);

                                String[] arr = userRoles.split("~");
                                isAllowed = Arrays.stream(arr).anyMatch(balanceType::equals) && !userRoles.isEmpty();
                            }
                            if (isAllowed) {
                                String bucketBal = DoHTTPRequest
                                        .get2(BALANCEENDPOINT + "/" + balanceType);
                                JSONObject resp = new JSONObject(bucketBal);
                                String msg = resp.getString("response");
                                if (msg.equalsIgnoreCase("success")) {

                                    out.println(resp.getString("balance"));
                                } else {

                                    out.println("N/A");
                                }
                            } else {
                                out.println(new Gson().toJson(new ArrayList<>()));
                            }
                        } else {
                            out.println(new Gson().toJson(new ArrayList<>()));
                        }
                    }

                    if (action.equalsIgnoreCase("providerList")) {
                        out.println(new Gson().toJson(roleService.getProviderList(userType, userCode)));
                    }
                    if (action.equalsIgnoreCase("senderIdList")) {
                        out.println(new Gson().toJson(roleService.getSenderList(userType, userCode)));
                    }

                    if (action.equalsIgnoreCase("vasgate")) {

                        if (userType.contains("[0]")) {
                            List<VasGateMerchant> merchants = new ArrayList<>();
                            out.println(new Gson().toJson(merchants));
                        } else {
                            if (userType.contains("[4]")) {
                                String userRoles = roleService.getRoleParameters("[2000000000000053]", userCode);
                                log.info("usrRoles: " + userRoles);

                                if (userRoles.contains("~")) {
                                    String[] arr = userRoles.split("~");
                                    userRoles = Arrays.stream(arr).collect(Collectors.joining("','"));
                                }
                                log.info("result: " + userRoles);

                                List<VasGateMerchant> merchants = new ArrayList<>();
                                out.println(new Gson().toJson(merchants));

                            } else {
                                out.println(new Gson().toJson(new ArrayList<>()));
                            }
                        }
                    }

                    if (action.equalsIgnoreCase("wcMerchants")) {

                        String wcMerchants = "";
                        out.println(new Gson().toJson(wcMerchants));
                    }
                    if (action.equalsIgnoreCase("mpayMerchants")) {

                        String wcMerchants = "";
                        out.println(new Gson().toJson(wcMerchants));
                    }
                    if (action.equalsIgnoreCase("typeIdList")) {
                        out.println(new Gson().toJson(roleService.getTypeIdList(isAdmin, isExtAdmin, userId)));
                    }

                    if (action.equalsIgnoreCase("typeIdListOptions") && !option.isEmpty()) {
                        if (isAdmin) {

                            out.println(new Gson().toJson(roleService.getRoleOptions(option)));
                        } else if (isExtAdmin && userCode.contains("[" + option + "]")) {
                            out.println(new Gson().toJson(roleService.getSpecificRoleOptions(option,
                                    roleService.getRoleParameters("[" + option + "]", userCode))));
                        }
                    }
                    if (action.equalsIgnoreCase("appIdList")) {
                        List<String> appId = new ArrayList<>();
                        if (userType.contains("[0]") || isAdmin) {
                            appId.add("ALL");
                        } else {
                            String userRoles = roleService.getRoleParameters("[2500000000000050]", userCode);
                            if (userRoles.contains("~")) {

                                appId = Arrays.asList(userRoles.split("~"));
                            }

                        }
                        log.info("result: " + appId.toString());
                        if (!appId.isEmpty()) {
                            out.println(new Gson().toJson(new MobileAppDao().getMobileAppIdList(appId)));
                        } else {
                            out.println(new Gson().toJson(appId));
                        }

                    }
                }
            }
        } catch (Exception e) {
            log.error("error", e);

        }

    }

    public static boolean isNumeric(String inputData) {
        return inputData.matches("[-+]?\\d+(\\.\\d+)?");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        setAccessControlHeaders(response);
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

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        setAccessControlHeaders(resp);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    private void setAccessControlHeaders(HttpServletResponse resp) {

        resp.setHeader("Access-Control-Allow-Methods", "GET");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    public Boolean testEtzUsers(String typeId, String desc) {
        Boolean result = false;
        if (desc.split(" ")[0].equals("ETZ") || typeId.equals("1") || typeId.equals("2")) {
            result = true;
        }
        return result;
    }
}
