/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etzgh.xportal.controller;

import com.etzgh.xportal.cdi.UserSession;
import com.etzgh.xportal.dao.GipDao;
import com.etzgh.xportal.dao.VasBillDao;
import com.etzgh.xportal.model.ApiPostData;
import com.etzgh.xportal.model.GIPTransaction;
import com.etzgh.xportal.model.JsonResponse;
import com.etzgh.xportal.model.VasBillTransaction;
import com.etzgh.xportal.utility.autoSwitchCall;
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
public class GipProxy extends HttpServlet {

    private static final Logger log = Logger.getLogger(GipProxy.class.getName());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserSession userSession;
        userSession = (UserSession) request.getSession().getAttribute("userSession");
        response.setContentType("application/json");

        try (PrintWriter out = response.getWriter()) {

            ApiPostData apiData = new ApiPostData();
            apiData.setStartDate(request.getParameter("startDate"));
            apiData.setEndDate(request.getParameter("endDate"));
            apiData.setStatus(request.getParameter("status"));
            apiData.setUniqueTransId(request.getParameter("uniqueTransId"));
            apiData.setService(request.getParameter("service"));
            apiData.setSearchKey(request.getParameter("searchKey"));
            apiData.setAccountNumber(request.getParameter("account"));
            apiData.setUserCode(userSession.getUser().getUser_code());
            apiData.setIpAddress((String) request.getAttribute("ipAddress"));
            apiData.setUser_id(userSession.getUser().getUser_id());
            apiData.setType_id(userSession.getUser().getType_id());
            apiData.setCardSchemeNumbers(userSession.getUser().getCardSchemeNumbers());

            Boolean allowed = (!apiData.getType_id().isEmpty() && (apiData.getType_id().contains("[0]") || (apiData.getType_id().contains("[2]"))));
            if (allowed) {
                if (apiData.getService().equalsIgnoreCase("transactions") || apiData.getService().equalsIgnoreCase("search")) {

                    List<GIPTransaction> records = new GipDao().getGipTransactions(new Gson().toJson(apiData));
                    if (records != null && records.size() > 0) {
                        out.println(new Gson().toJson(new JsonResponse("00", "success", new Gson().toJson(records))));
                    } else {
                        out.println(new Gson().toJson(new JsonResponse("01", "No Data Found")));
                    }
                } else if (apiData.getService().equalsIgnoreCase("qrtransactions") || apiData.getService().equalsIgnoreCase("qrsearch")) {

                    List<GIPTransaction> records = new GipDao().getGhQRTransactions(new Gson().toJson(apiData));
                    if (records != null && records.size() > 0) {
                        out.println(new Gson().toJson(new JsonResponse("00", "success", new Gson().toJson(records))));
                    } else {
                        out.println(new Gson().toJson(new JsonResponse("01", "No Data Found")));
                    }
                } else if (apiData.getService().equalsIgnoreCase("gipcheck")) {

                    System.out.println(apiData);

                    String gipResponse = new autoSwitchCall().autoRequest(apiData.getUniqueTransId());

                    if (gipResponse != null) {
                        out.println(new Gson().toJson(new JsonResponse("00", "success", gipResponse)));
                    } else {

                        out.println(new Gson().toJson(new JsonResponse("01", "success", gipResponse)));
                    }
                }else if (apiData.getService().equalsIgnoreCase("gipStatusCheck")) {
                    if (allowed) {
                        List<GIPTransaction> records = new GipDao().getGipStatusCheck(new Gson().toJson(apiData));

                        if (records != null && records.size() > 0) {
                            out.println(
                                    new Gson().toJson(new JsonResponse("00", "success", new Gson().toJson(records))));
                        } else {
                            out.println(new Gson().toJson(new JsonResponse("01", "No Data Found")));
                        }

                    } else {
                        out.println(new Gson().toJson(new JsonResponse("01", "Authorization Required")));
                    }

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

}
