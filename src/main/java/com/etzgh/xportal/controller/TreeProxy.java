/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etzgh.xportal.controller;

import com.etzgh.xportal.cdi.UserSession;
import com.etzgh.xportal.dao.TreeMPayDao;
import com.etzgh.xportal.model.ApiPostData;
import com.etzgh.xportal.model.JsonResponse;
import com.etzgh.xportal.model.MerchantPay;
import com.etzgh.xportal.model.NODES;
import com.etzgh.xportal.model.TreeSettlement;
import com.etzgh.xportal.utility.GeneralUtility;
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
public class TreeProxy extends HttpServlet {

    private static final Logger log = Logger.getLogger(TreeProxy.class.getName());

    private static final GeneralUtility utility = new GeneralUtility();

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
            apiData.setMerchant(request.getParameterMap().containsKey("merchant") ? request.getParameter("merchant") : "ALL");

            apiData.setUniqueTransId(request.getParameter("uniqueTransId"));
            apiData.setAccountNumber(request.getParameter("account_no"));
            apiData.setFrom_source(request.getParameter("from_source"));
            apiData.setStatus(request.getParameterMap().containsKey("status") ? request.getParameter("status") : "ALL");

            apiData.setService(request.getParameter("service"));

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
            String userRoles = "";
            Boolean allowed = (!type_id.isEmpty() && (type_id.contains("[0]") || (type_id.contains("[47]") && !utility.getRoleParameters("[2500000000000045]", ucode).isEmpty())));
            if (allowed) {
                if (apiData.getService().equalsIgnoreCase("transactions") || apiData.getService().equalsIgnoreCase("search")) {

                    List<MerchantPay> records = new TreeMPayDao().getMerchantPayTrans(new Gson().toJson(apiData));

                    if (records != null && records.size() > 0) {
                        out.println(new Gson().toJson(new JsonResponse("00", "success", new Gson().toJson(records))));
                    } else {
                        out.println(new Gson().toJson(new JsonResponse("01", "No Data Found")));
                    }
                } else if (apiData.getService().equalsIgnoreCase("mpaysettlement") || apiData.getService().equalsIgnoreCase("mpaysettlementsearch")) {

                    List<TreeSettlement> records = new TreeMPayDao().getTreeSettlementTrans(new Gson().toJson(apiData));

                    if (records != null && records.size() > 0) {
                        out.println(new Gson().toJson(new JsonResponse("00", "success", new Gson().toJson(records))));
                    } else {
                        out.println(new Gson().toJson(new JsonResponse("01", "No Data Found")));
                    }
                } else if (apiData.getService().equalsIgnoreCase("merchants")) {

                    List<NODES> records = new TreeMPayDao().getMerchants(new Gson().toJson(apiData));

                    out.println(new Gson().toJson(records));

                }
            } else {
                out.println(new Gson().toJson(new JsonResponse("01", "Authorization Required")));
            }

        }
    }

    protected String getBiller(String billerCode) {
        switch (billerCode) {
            case "686":
                return "MTN";
            case "844":
                return "TIGO";
            case "863":
                return "VODA";
            case "285":
                return "AIRTEL";
            default:
                break;
        }
        return billerCode;
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
