/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etzgh.xportal.controller;

import com.etzgh.xportal.cdi.UserSession;
import com.etzgh.xportal.dao.AppDao;
import com.etzgh.xportal.utility.DoHTTPRequest;
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
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;

/**
 *
 * @author sunkwa-arthur
 */
public class BalanceProxy extends HttpServlet {

    private static final long serialVersionUID = 5767791483998272545L;
    private static final Logger log = Logger.getLogger(BalanceProxy.class.getName());
    final static PortalSettings portalSettings = new PortalSettings();
    private static String balance_endpoint = "";

    static {
        balance_endpoint = StringUtils.substringBeforeLast(portalSettings.getSetting("balance_endpoint"), ":");
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {

        UserSession userSession;
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {

            try {
                userSession = (UserSession) request.getSession().getAttribute("userSession");
                String action = request.getParameter("action");

                String userType = userSession.getUser().getType_id();

                String userCode = userSession.getUser().getUser_code();
                String balanceType = request.getParameterMap().containsKey("type") ? request.getParameter("type") : "";

                boolean isAllowed = false;

                log.info("show: " + action);
                if (userType.contains("[0]")) {
                    isAllowed = true;
                    log.info("allowed: ");
                } else if (userType.contains("[25]")) {
                    String userRoles = new AppDao().getRoleParameters("[2000000000000064]", userCode);
                    log.info("usrRoles: " + userRoles);

                    List<String> roles = Arrays.asList(userRoles.split("~"));
                    isAllowed = ((roles.contains(balanceType) || roles.contains("ALL") || balanceType.isEmpty())
                            && !userRoles.isEmpty());

                    System.out.println("CHECK : " + roles + " - " + isAllowed + " - " + balanceType);
                }
                if (isAllowed) {

                    if (action.equalsIgnoreCase("fetchBuckets")) {

                        out.println(new Gson().toJson(new AppDao().getBalanceTypes(userType, userCode)));
                    } else if (action.equalsIgnoreCase("fetchBucketsBalance")) {

                        if (!balanceType.isEmpty()) {

                            String bucketBal = DoHTTPRequest.get2(balance_endpoint + "/" + balanceType);
                            JSONObject resp = new JSONObject(bucketBal);
                            String msg = resp.getString("response");
                            if (msg.equalsIgnoreCase("success")) {

                                out.println(new Gson().toJson(resp.getString("balance")));
                            } else {

                                out.println(new Gson().toJson("N/A"));
                            }
                        } else {
                            out.println(new Gson().toJson(new ArrayList<>()));
                        }
                    } else {
                        out.println(new Gson().toJson(new ArrayList<>()));
                    }

                } else {
                    out.println(new Gson().toJson(new ArrayList<>()));
                }

            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
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
