package com.etzgh.xportal.controller;

import com.etzgh.xportal.cdi.UserSession;
import com.etzgh.xportal.dao.AppDao;
import com.etzgh.xportal.dao.ReversalDao;
import com.etzgh.xportal.model.ApiPostData;
import com.etzgh.xportal.model.Bank;
import com.etzgh.xportal.model.JsonResponse;
import com.etzgh.xportal.model.Reversal;
import com.etzgh.xportal.service.UserProfileService;
import com.etzgh.xportal.utility.GeneralUtility;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 * @author sunkwa-arthur
 */
public class BTWInvestigationProxy extends HttpServlet {

    private static final long serialVersionUID = 2779093570295166493L;

    private static final UserProfileService roleService = new UserProfileService();
    final static GeneralUtility utility = new GeneralUtility();

    private static final Logger log = Logger.getLogger(BTWInvestigationProxy.class.getName());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserSession userSession;
        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            try {
                userSession = (UserSession) request.getSession().getAttribute("userSession");
                String action = request.getParameterMap().containsKey("action") ? request.getParameter("action") : "";

                ApiPostData apiData = new ApiPostData();
                String userType = userSession.getUser().getType_id();
                List<String> roleList = new ArrayList<>();

                apiData.setUserCode(userSession.getUser().getUser_code());
                apiData.setIpAddress((String) request.getAttribute("ipAddress"));

                apiData.setUser_id(userSession.getUser().getUser_id());
                apiData.setUserName(userSession.getUser().getUsername());
                apiData.setType_id(userSession.getUser().getType_id());
                apiData.setService(request.getParameter("service"));
                apiData.setStartDate(request.getParameter("startDate"));
                apiData.setEndDate(request.getParameter("endDate"));
                apiData.setBank_code(request.getParameterMap().containsKey("bank") ? request.getParameter("bank") : "000");

                if (!action.isEmpty()) {
                    apiData.setService(action);
                }

                Boolean allowUser = false;

                if (userType.contains("[33]") || userType.contains("[31]")) {
                    String userRoles = roleService.getRoleParameters("[2500000000000049]", userSession.getUser().getUser_code());
                    log.info("usrRoles: " + userRoles);
                    roleList = Arrays.asList(userRoles.split("~"));
                    allowUser = !roleList.isEmpty();
                }

                String bankCode = "000";
                if (apiData.getType_id().contains("[0]")) {
                    allowUser = true;
                } else if (apiData.getType_id().contains("[6]")) {
                    bankCode = utility.getRoleParameters("[2000000000000060]", apiData.getUserCode());
                    if (!bankCode.equals("000")) {
                        apiData.setBank_code(bankCode);
                    }

                }

                if (allowUser) {
                    if (apiData.getService().equalsIgnoreCase("bankToWalletInvestigation")) {

                        List<Reversal> records = new ReversalDao().getPendingReversals(new Gson().toJson(apiData));

                        if (records != null && records.size() > 0) {

                            out.println(new Gson().toJson(new JsonResponse("00", "success", new Gson().toJson(records))));
                        } else {
                            out.println(new Gson().toJson(new JsonResponse("01", "No Data Found")));
                        }

                    } else if (apiData.getService().equalsIgnoreCase("banks")) {
                        log.info(apiData);

                        List<Bank> records = bankCode.equals("000") ? new AppDao().getBanks() : new ArrayList<>();

                        out.println(new Gson().toJson(records));
                        records = null;
                    } else {
                        out.println(new Gson().toJson(new JsonResponse("01", "Invalid Service")));
                    }
                } else {
                    out.println(new Gson().toJson(new JsonResponse("01", "Authorization Required")));
                }

            } catch (Exception e) {
                log.error("error: ", e);
                out.println(new Gson().toJson(new JsonResponse("01", "An Error Occured")));
            }
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
            log.error(ex.getMessage(), ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }

}
