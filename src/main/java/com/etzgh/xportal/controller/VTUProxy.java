/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etzgh.xportal.controller;

import com.etzgh.xportal.cdi.UserSession;
import com.etzgh.xportal.dao.AppDao;
import com.etzgh.xportal.dao.VtuDao;
import com.etzgh.xportal.model.ApiPostData;
import com.etzgh.xportal.model.Bank;
import com.etzgh.xportal.model.JsonResponse;
import com.etzgh.xportal.model.VTU;
import com.etzgh.xportal.utility.GeneralUtility;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
public class VTUProxy extends HttpServlet {

    public final GeneralUtility utility = new GeneralUtility();

    private static final Logger log = Logger.getLogger(VTUProxy.class.getName());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserSession userSession;
        userSession = (UserSession) request.getSession().getAttribute("userSession");
        response.setContentType("application/json");

        try (PrintWriter out = response.getWriter()) {

            ApiPostData apiData = new ApiPostData();
            apiData.setStartDate(request.getParameter("startDate"));
            apiData.setEndDate(request.getParameter("endDate"));
            apiData.setBank_code(request.getParameter("bank"));
            apiData.setDestination(request.getParameter("destination"));
            apiData.setFrom_source(request.getParameter("from_source"));
            apiData.setStatus(request.getParameterMap().containsKey("status") ? request.getParameter("status") : "ALL");
            apiData.setLineType(
                    request.getParameterMap().containsKey("lineType") ? request.getParameter("lineType") : "ALL");
            apiData.setTransType(request.getParameter("transType"));
            apiData.setService(request.getParameter("service"));
            apiData.setSearchKey(request.getParameter("searchKey"));

            String type_id = userSession.getUser().getType_id();
            String ucode = userSession.getUser().getUser_code();
            apiData.setUserCode(ucode);
            apiData.setUser_id(userSession.getUser().getUser_id());
            apiData.setType_id(type_id);

            apiData.setCardSchemeNumbers(userSession.getUser().getCardSchemeNumbers());
            /*String telcoList = "GLO,MTN,TIGO,VODAFONE"; */

            String action = request.getParameterMap().containsKey("action") ? request.getParameter("action") : "";

            if (!action.isEmpty()) {
                apiData.setService(action);
            }

            Boolean allowed = (!type_id.isEmpty() && (type_id.contains("[0]")
                    || (type_id.contains("[7]") && !utility.getRoleParameters("[2000000000000062]", ucode).isEmpty())
                    || (type_id.contains("[6]") && !utility.getRoleParameters("[2000000000000060]", ucode).isEmpty())));
            if (allowed) {
                String bankCode = "000";
                if (apiData.getType_id().contains("[0]")) {
                } else if (apiData.getType_id().contains("[6]")) {
                    bankCode = utility.getRoleParameters("[2000000000000060]", apiData.getUserCode());
                    apiData.setBank_code(bankCode);

                }
                if (apiData.getService().equalsIgnoreCase("transactions")
                        || apiData.getService().equalsIgnoreCase("search")) {
                    System.out.println(apiData);

                    List<VTU> records = new VtuDao().getVtuTransactions(new Gson().toJson(apiData));

                    if (records != null && records.size() > 0) {
                        out.println(new Gson().toJson(new JsonResponse("00", "success", new Gson().toJson(records))));
                    } else {
                        out.println(new Gson().toJson(new JsonResponse("01", "No Data Found")));
                    }
                } else if (apiData.getService().equalsIgnoreCase("banks")) {

                    List<Bank> records = bankCode.equals("000") ? new AppDao().getBanks() : new ArrayList<>();

                    out.println(new Gson().toJson(records));
                } else if (apiData.getService().equalsIgnoreCase("telco")) {

                    List<Bank> records = new ArrayList<>();

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
            log.error(ex.getMessage(), ex);
        }
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
}
