/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etzgh.xportal.controller;

import com.etzgh.xportal.cdi.UserSession;
import com.etzgh.xportal.dao.AppDao;
import com.etzgh.xportal.dao.CscDao;
import com.etzgh.xportal.model.ApiPostData;
import com.etzgh.xportal.model.Bank;
import com.etzgh.xportal.model.CscLog;
import com.etzgh.xportal.model.JsonResponse;
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
public class CscProxy extends HttpServlet {

    private static final Logger log = Logger.getLogger(CscProxy.class.getName());

    private static final AppDao ad = new AppDao();

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
            apiData.setMerchant(request.getParameter("merchant"));
            apiData.setUniqueTransId(request.getParameter("uniqueTransId"));

            apiData.setTransType(request.getParameter("trans_type"));
            apiData.setDestination(request.getParameter("dest_account"));
            apiData.setFrom_source(request.getParameter("source_account"));
            apiData.setStatus(request.getParameter("status"));

            apiData.setService(request.getParameter("service"));
            apiData.setBank(request.getParameter("bank"));
            apiData.setSearchKey(request.getParameter("searchKey"));
            apiData.setUserCode(ucode);
            apiData.setUser_id(userSession.getUser().getUser_id());
            apiData.setType_id(type_id);
            apiData.setBank_code(userSession.getUser().getBankCode());
            apiData.setCardSchemeNumbers(userSession.getUser().getCardSchemeNumbers());

            String action = request.getParameterMap().containsKey("action") ? request.getParameter("action") : "";

            if (!action.isEmpty()) {
                apiData.setService(action);
            }
            log.info(new Gson().toJson(apiData));
            Boolean allowed = (!type_id.isEmpty() && (type_id.contains("[0]") || (type_id.contains("[42]"))));
            if (allowed) {
                if (apiData.getService().equalsIgnoreCase("transactions") || apiData.getService().equalsIgnoreCase("search")) {

                    List<CscLog> records = new CscDao().getCscLogTrans(new Gson().toJson(apiData));

                    if (records != null && records.size() > 0) {
                        out.println(new Gson().toJson(new JsonResponse("00", "success", new Gson().toJson(records))));
                    } else {
                        out.println(new Gson().toJson(new JsonResponse("01", "No Data Found")));
                    }

                } else if (apiData.getService().equalsIgnoreCase("banks")) {

                    List<Bank> records = ad.getBanks();
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
