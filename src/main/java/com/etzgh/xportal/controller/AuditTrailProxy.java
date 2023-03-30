/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etzgh.xportal.controller;

import com.etzgh.xportal.cdi.UserSession;
import com.etzgh.xportal.dao.AuditDao;
import com.etzgh.xportal.model.ApiPostData;
import com.etzgh.xportal.model.AuditTrail;
import com.etzgh.xportal.model.JsonResponse;
import com.etzgh.xportal.model.NODES;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
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
public class AuditTrailProxy extends HttpServlet {

    private static final Logger log = Logger.getLogger(AuditTrailProxy.class.getName());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserSession userSession;
        userSession = (UserSession) request.getSession().getAttribute("userSession");
        response.setContentType("application/json");

        try (PrintWriter out = response.getWriter()) {

            ApiPostData apiData = new ApiPostData();
            String type_id = userSession.getUser().getType_id();
            String ucode = userSession.getUser().getUser_code();
            apiData.setStartDate(request.getParameter("startDate"));
            apiData.setEndDate(request.getParameter("endDate"));
            apiData.setClientId(request.getParameter("userId"));
            apiData.setAction(request.getParameter("action"));
            apiData.setSearchKey(request.getParameter("detail"));
            apiData.setTerminal_id(request.getParameter("ipaddress"));

            apiData.setService(request.getParameter("service"));
            apiData.setTransType(request.getParameter("action_type"));

            apiData.setUserCode(ucode);
            apiData.setUser_id(userSession.getUser().getUser_id());
            apiData.setType_id(type_id);

            apiData.setCardSchemeNumbers(userSession.getUser().getCardSchemeNumbers());

            String action = request.getParameterMap().containsKey("action") ? request.getParameter("action") : "";

            if (!action.isEmpty()) {
                apiData.setService(action);
            }
            log.info(new Gson().toJson(apiData));

            Boolean isAdmin = apiData.getUserCode().contains("[1]") || apiData.getType_id().contains("[43]");

            if (isAdmin) {
                System.out.println("ALLOWED");
                if (apiData.getService().equalsIgnoreCase("searchlog")) {

                    List<AuditTrail> records = new AuditDao().getAuditTrail(new Gson().toJson(apiData));

                    if (records != null && records.size() > 0) {
                        out.println(new Gson().toJson(new JsonResponse("00", "success", new Gson().toJson(records))));
                    } else {
                        out.println(new Gson().toJson(new JsonResponse("01", "No Data Found")));
                    }
                } else if (apiData.getService().equalsIgnoreCase("actions")) {
                    List<NODES> records = new AuditDao().getAuditActions();

                    out.println(new Gson().toJson(records));
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
