/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etzgh.xportal.controller;

/**
 *
 * @author sunkwa-arthur
 */
import com.etzgh.xportal.cdi.UserSession;
import com.etzgh.xportal.dao.CardTransDao;
import com.etzgh.xportal.dao.NlaLiquidationReportDao;
import com.etzgh.xportal.model.ApiPostData;
import com.etzgh.xportal.model.CardMasterReport;
import com.etzgh.xportal.model.CardTransReport;
import com.etzgh.xportal.model.CardTransactionsReport;
import com.etzgh.xportal.model.JsonResponse;
import com.etzgh.xportal.model.NlaLiquidationReport;
import com.etzgh.xportal.utility.CryptographerMin;
import com.etzgh.xportal.utility.GeneralUtility;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class CardTransReportProxy extends HttpServlet {

    private static final GeneralUtility utility = new GeneralUtility();
    private static final Logger log = Logger.getLogger(CardTransReportProxy.class.getName());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserSession userSession;
        userSession = (UserSession) request.getSession().getAttribute("userSession");
        response.setContentType("application/json");

        try (PrintWriter out = response.getWriter()) {

            ApiPostData apiData = new ApiPostData();
            apiData.setStartDate(request.getParameter("startDate"));
            apiData.setEndDate(request.getParameter("endDate"));

            apiData.setUniqueTransId(request.getParameter("uniqueTransId"));
            apiData.setSubscriberId(request.getParameter("mobile_no"));
            apiData.setService(request.getParameter("service"));
            apiData.setSearchKey(request.getParameter("searchKey"));
            apiData.setStatus(request.getParameterMap().containsKey("status") ? request.getParameter("status") : "ALL");
            apiData.setUserCode(userSession.getUser().getUser_code());
            apiData.setIpAddress((String) request.getAttribute("ipAddress"));
            apiData.setUser_id(userSession.getUser().getUser_id());
            apiData.setCardSchemeNumbers(userSession.getUser().getCardSchemeNumbers());
            apiData.setType_id(userSession.getUser().getType_id());
            apiData.setBank_code(userSession.getUser().getBankCode());
            apiData.setCard_num(request.getParameterMap().containsKey("cardnum") ? request.getParameter("cardnum") : "");
            
             apiData.setReference(request.getParameter("reference"));
             apiData.setExtReference(request.getParameter("extReference"));
             apiData.setDestacct(request.getParameter("destacct"));
             apiData.setTransNumber(request.getParameter("transNumber"));
             
             apiData.setEmail(request.getParameter("email"));
             apiData.setPhone(request.getParameter("phone"));
             apiData.setIdNumber(request.getParameter("idNumber"));
             apiData.setCan(request.getParameter("can"));

            System.out.println("Card number: " + apiData.getCard_num());
            String card_number = apiData.getCard_num();
            String mobile = apiData.getSubscriberId();

            if (card_number == null) {
                card_number = "";
            }
            if (mobile == null) {
                mobile = "";
            }

            Boolean allowed = (!apiData.getType_id().isEmpty() && (apiData.getType_id().contains("[0]")
                    || (apiData.getType_id().contains("[2]") && !utility.getRoleParameters("[17]", apiData.getUserCode()).isEmpty())
                    || (apiData.getType_id().contains("[28]") && !utility.getRoleParameters("[2000000000000048]", apiData.getUserCode()).isEmpty())
                    || (apiData.getType_id().contains("[6]") && !utility.getRoleParameters("[2000000000000060]", apiData.getUserCode()).isEmpty()
                    || (apiData.getType_id().contains("[68]"))
                    || (apiData.getType_id().contains("[88]"))
                    || (apiData.getType_id().contains("[89]")))));

            if (allowed) {
                if (apiData.getService().equalsIgnoreCase("cardTransRpt")) {

                    if ((!apiData.getType_id().contains("[2]") && !apiData.getType_id().contains("[68]")) && (card_number.trim().isEmpty() && mobile.trim().isEmpty())) {
                        out.println(new Gson().toJson(new JsonResponse("01", "Account number or Mobile number is required")));
                    } else {

                        CardTransactionsReport records = new CardTransDao().getCardTransReport(new Gson().toJson(apiData));
                        List<CardTransReport> li = records.getCardTransactionsReport();
                        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
                        if (!li.isEmpty()) {

                            String resp = gson.toJson(li);

                            String mac = CryptographerMin.encryptEncodeToHex("report=cardtrans&action=pdf&title=CARD TRANSACTIONS REPORT&start=" + apiData.getStartDate() + "&end=" + apiData.getEndDate() + "&card_number=" + records.getCardNumber() + "&card_name=" + records.getCardName(), resp);
                            response.addHeader("app-token", mac);
                            out.println(gson.toJson(new JsonResponse("00", "success", gson.toJson(records))));
                        } else {
                            out.println(gson.toJson(new JsonResponse("01", "No Data Found")));
                        }
                    }
                }else if (apiData.getService().equalsIgnoreCase("cardmastertransreport")) {
                    System.out.println("Card master transactions started");
                    if (allowed) {
                        List<CardMasterReport> records = new CardTransDao().getCardMasterTransaction(new Gson().toJson(apiData));

                        if (records != null && records.size() > 0) {
                            out.println(new Gson().toJson(new JsonResponse("00", "success", new Gson().toJson(records))));
                        } else {
                            out.println(new Gson().toJson(new JsonResponse("01", "No Data Found")));
                        }
                        records = null;
                    } else {
                        out.println(new Gson().toJson(new JsonResponse("01", "Authorization Required")));
                    }

                }else if (apiData.getService().equalsIgnoreCase("cardmasteronbordingreport")) {
                    System.out.println("Card master transactions started");
                    if (allowed) {
                        List<CardMasterReport> records = new CardTransDao().getCardMasterOnboarding(new Gson().toJson(apiData));

                        if (records != null && records.size() > 0) {
                            out.println(new Gson().toJson(new JsonResponse("00", "success", new Gson().toJson(records))));
                        } else {
                            out.println(new Gson().toJson(new JsonResponse("01", "No Data Found")));
                        }
                        records = null;
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
