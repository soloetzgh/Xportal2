/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etzgh.xportal.controller;

import com.etzgh.xportal.cdi.UserSession;
import com.etzgh.xportal.dao.FundgateDao;
import com.etzgh.xportal.dao.VasBillDao;
import com.etzgh.xportal.model.ApiPostData;
import com.etzgh.xportal.model.FundGateReport;
import com.etzgh.xportal.model.FundGateTransaction;
import com.etzgh.xportal.model.FundgateBalance;
import com.etzgh.xportal.model.FundgateResponse;
import com.etzgh.xportal.model.JsonResponse;
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
public class FundgateProxy extends HttpServlet {

    private static List<Object[]> terminalrecords;
    private static final Logger log = Logger.getLogger(FundgateProxy.class.getName());

    private static final GeneralUtility utility = new GeneralUtility();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
            apiData.setProduct(request.getParameter("product"));
            apiData.setAccountNumber(request.getParameter("account_no"));
            apiData.setClientId(request.getParameter("client_ref"));
            apiData.setStatus(request.getParameter("status"));
            apiData.setTrans_status(request.getParameter("trans_status"));
            apiData.setService(request.getParameter("service"));
            apiData.setOptionType(request.getParameter("record"));
            apiData.setSearchKey(request.getParameter("searchKey"));
            apiData.setUniqueTransId(request.getParameter("uniqueTransid"));
            apiData.setClientRef(request.getParameter("clientRef"));
            apiData.setLineType(request.getParameter("lineType"));

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
            boolean allowed = (!type_id.isEmpty() && (type_id.contains("[0]")
                    || (type_id.contains("[2]") && !utility.getRoleParameters("[17]", ucode).isEmpty())));
            if (allowed) {
                if (apiData.getService().equalsIgnoreCase("transactions")) {

                    List<FundGateTransaction> records = new FundgateDao()
                            .getFundGateTransactions(new Gson().toJson(apiData));

                    if (records != null && records.size() > 0) {
                        out.println(new Gson().toJson(new JsonResponse("00", "success", new Gson().toJson(records))));
                    } else {
                        out.println(new Gson().toJson(new JsonResponse("01", "No Data Found")));
                    }
                } else if (apiData.getService().equalsIgnoreCase("balance")) {

                    String balance = new FundgateDao().getBalance(new Gson().toJson(apiData));

                    out.println(balance);

                } else if (apiData.getService().equalsIgnoreCase("fgbalance")) {

                    List<FundgateBalance> records = new FundgateDao().getFundgateBalances(new Gson().toJson(apiData));

                    out.println(new Gson().toJson(new JsonResponse("00", "success", new Gson().toJson(records))));

                } else if (apiData.getService().equalsIgnoreCase("merchants")) {

                    List<NODES> records = new FundgateDao().getMerchants(new Gson().toJson(apiData));

                    out.println(new Gson().toJson(records));

                } else if (apiData.getService().equalsIgnoreCase("billers")) {
//                    log.info(apiData);
                    List<NODES> records = new ArrayList<>();
                    records.add(new NODES("844", "AirtelTigo Cash"));
                    records.add(new NODES("686", "Mtn Mobilemoney"));
                    records.add(new NODES("863", "Vodafone Cash"));
                    records.addAll(new VasBillDao().getMerchants(new Gson().toJson(apiData)));

                    out.println(new Gson().toJson(records));
                } else if (apiData.getService().equalsIgnoreCase("merchantsAccount")) {

                    List<NODES> records = new FundgateDao().getMerchantsAccount(new Gson().toJson(apiData));

                    out.println(new Gson().toJson(records));

                } else if (apiData.getService().equalsIgnoreCase("investigation")) {

                    List<FundGateTransaction> records = new FundgateDao()
                            .getFundGateInvestigation(new Gson().toJson(apiData));

                    if (records != null && records.size() > 0) {

                        out.println(new Gson().toJson(new JsonResponse("00", "success", new Gson().toJson(records))));
                    } else {
                        out.println(new Gson().toJson(new JsonResponse("01", "No Data Found")));
                    }
                } else if (apiData.getService().equalsIgnoreCase("search")) {

                    List<FundGateTransaction> records = new FundgateDao()
                            .getFundGateTransactions(new Gson().toJson(apiData));
                    if (records != null && records.size() > 0) {
                        out.println(new Gson().toJson(new JsonResponse("00", "success", new Gson().toJson(records))));
                    } else {
                        out.println(new Gson().toJson(new JsonResponse("01", "No Data Found")));
                    }

                } else if (apiData.getService().equalsIgnoreCase("report")) {

                    List<FundGateReport> records = new FundgateDao().getFundGateReport(new Gson().toJson(apiData));

                    if (records != null && records.size() > 0) {
                        out.println(new Gson().toJson(new JsonResponse("00", "success", new Gson().toJson(records))));
                    } else {
                        out.println(new Gson().toJson(new JsonResponse("01", "No Data Found")));
                    }
                } else if (apiData.getService().equalsIgnoreCase("searchReport")) {

                    List<FundGateReport> records = new FundgateDao().getFundGateReport(new Gson().toJson(apiData));

                    if (records != null && records.size() > 0) {
                        out.println(new Gson().toJson(new JsonResponse("00", "success", new Gson().toJson(records))));
                    } else {
                        out.println(new Gson().toJson(new JsonResponse("01", "No Data Found")));
                    }

                } else if (apiData.getService().equalsIgnoreCase("cardtransactions")) {

                    List<FundGateTransaction> records = new FundgateDao()
                            .getFundGateTransactions(new Gson().toJson(apiData));
                    terminalrecords = new FundgateDao().getTerminals();

                    if (records != null && records.size() > 0) {

                        out.println(new Gson().toJson(new JsonResponse("00", "success", new Gson().toJson(records))));
                    } else {
                        out.println(new Gson().toJson(new JsonResponse("01", "No Data Found")));
                    }
                } else if (apiData.getService().equalsIgnoreCase("fundgateReferenceCheck")) {

                    List<FundgateResponse> records = new FundgateDao()
                            .getFundGateReferenceCheck(new Gson().toJson(apiData));
                    //terminalrecords = new FundgateDao().getTerminals();

                    if (records != null && records.size() > 0) {

                        out.println(new Gson().toJson(new JsonResponse("00", "success", new Gson().toJson(records))));
                    } else {
                        out.println(new Gson().toJson(new JsonResponse("01", "No Data Found")));
                    }
                }
            } else {
                out.println(new Gson().toJson(new JsonResponse("01", "Authorization Required")));
            }

        }
    }

    protected String mapTerminal(String terminal_id) {
        for (Object[] obj : terminalrecords) {
            if (obj[0].toString().trim().equals(terminal_id.trim())) {
                return obj[1].toString();
            }
        }
        return terminal_id;
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
