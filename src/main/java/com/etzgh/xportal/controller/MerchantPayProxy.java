/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etzgh.xportal.controller;

import com.etzgh.xportal.cdi.UserSession;
import com.etzgh.xportal.dao.MerchantPayDao;
import com.etzgh.xportal.model.ApiPostData;
import com.etzgh.xportal.model.Bank;
import com.etzgh.xportal.model.JsonResponse;
import com.etzgh.xportal.model.MerchantPay;
import com.etzgh.xportal.model.MerchantPaySettlement;
import com.etzgh.xportal.model.NODES;
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
public class MerchantPayProxy extends HttpServlet {

    private static final Logger log = Logger.getLogger(MerchantPayProxy.class.getName());

    private static final GeneralUtility utility = new GeneralUtility();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserSession userSession;
        userSession = (UserSession) request.getSession().getAttribute("userSession");
        response.setContentType("application/json");

        String[] networks = {"MTN", "GMONEY", "VODA", "TIGO", "AIRTEL"};

        List<Bank> networkList = new ArrayList<>();

        for (String n : networks) {
            Bank network = new Bank();
            network.setIssuer_name(n);
            network.setIssuer_code(n);
            networkList.add(network);
        }

        try (PrintWriter out = response.getWriter()) {

            ApiPostData apiData = new ApiPostData();
            String type_id = userSession.getUser().getType_id();
            String ucode = userSession.getUser().getUser_code();
            apiData.setStartDate(request.getParameter("startDate"));
            apiData.setEndDate(request.getParameter("endDate"));
            apiData.setMerchant(
                    request.getParameterMap().containsKey("merchant") ? request.getParameter("merchant") : "ALL");
            apiData.setProduct(
                    request.getParameterMap().containsKey("product") ? request.getParameter("product") : "ALL");
            apiData.setTrans_code(
                    request.getParameterMap().containsKey("servicecode") ? request.getParameter("servicecode") : "ALL");
            apiData.setUniqueTransId(request.getParameter("uniqueTransId"));
            apiData.setAccountNumber(request.getParameter("account_no"));
            apiData.setFrom_source(request.getParameter("from_source"));
            apiData.setLineType(request.getParameter("network"));
            apiData.setClientId(request.getParameter("client_id"));

            apiData.setStatus(request.getParameterMap().containsKey("status") ? request.getParameter("status") : "ALL");
            apiData.setTrans_status(request.getParameterMap().containsKey("trans_status") ? request.getParameter("trans_status") : "ALL");

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
            Boolean allowed = (!type_id.isEmpty() && (type_id.contains("[0]")
                    || (type_id.contains("[9]") && !utility.getRoleParameters("[2000000000000049]", ucode).isEmpty())));
            if (allowed) {
                if (apiData.getService().equalsIgnoreCase("transactions")
                        || apiData.getService().equalsIgnoreCase("search")) {

                    List<MerchantPay> records = new MerchantPayDao().getMerchantPayTrans(new Gson().toJson(apiData));

                    if (records != null && records.size() > 0) {
                        out.println(new Gson().toJson(new JsonResponse("00", "success", new Gson().toJson(records))));
                    } else {
                        out.println(new Gson().toJson(new JsonResponse("01", "No Data Found")));
                    }
                } else if (apiData.getService().equalsIgnoreCase("mpaysettlement")
                        || apiData.getService().equalsIgnoreCase("mpaysettlementsearch")) {

                    List<MerchantPaySettlement> records = new MerchantPayDao()
                            .getMerchantPaySettlementTrans(new Gson().toJson(apiData));

                    if (records != null && records.size() > 0) {
                        out.println(new Gson().toJson(new JsonResponse("00", "success", new Gson().toJson(records))));
                    } else {
                        out.println(new Gson().toJson(new JsonResponse("01", "No Data Found")));
                    }
                } else if (apiData.getService().equalsIgnoreCase("merchants")) {

                    List<NODES> records = new MerchantPayDao().getMerchants(new Gson().toJson(apiData));

                    out.println(new Gson().toJson(records));

                } else if (apiData.getService().equalsIgnoreCase("products")) {

                    List<NODES> records = new MerchantPayDao().getProducts(new Gson().toJson(apiData));

                    out.println(new Gson().toJson(records));

                } else if (apiData.getService().equalsIgnoreCase("servicecodes")) {

                    List<NODES> records = new MerchantPayDao().getServiceCodes(new Gson().toJson(apiData));

                    out.println(new Gson().toJson(records));

                } else if (apiData.getService().equalsIgnoreCase("networks")) {

                    out.println(new Gson().toJson(networkList));
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
