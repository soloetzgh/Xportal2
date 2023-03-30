/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etzgh.xportal.controller;

import com.etzgh.xportal.cdi.UserSession;
import com.etzgh.xportal.dao.ExternalRequestDao;
import com.etzgh.xportal.dao.MerchantPayDao;
import com.etzgh.xportal.model.ApiPostData;
import com.etzgh.xportal.model.ExternalRequests;
import com.etzgh.xportal.model.JsonResponse;
import com.etzgh.xportal.model.NODES;
import com.etzgh.xportal.utility.GeneralUtility;
import com.google.common.base.Splitter;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author sunkwa-arthur
 */
public class ExtRequestProxy extends HttpServlet {

    private static List<Object[]> terminalrecords;
    private static final Logger log = Logger.getLogger(ExtRequestProxy.class.getName());

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

            apiData.setTransType(request.getParameter("transType"));
            apiData.setMerchant(request.getParameter("merchant"));

            apiData.setUniqueTransId(request.getParameter("unique_ref"));
            apiData.setService(request.getParameter("service"));
            apiData.setSubscriberId(request.getParameter("subscriberId"));
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
//            log.info(new Gson().toJson(apiData));
            boolean allowed = (!type_id.isEmpty() && (type_id.contains("[0]")
                    || (type_id.contains("[84]") && !utility.getRoleParameters("[2000000000000057]", ucode).isEmpty())));
            if (allowed) {
                if (apiData.getService().equalsIgnoreCase("requests")) {

                    List<ExternalRequests> records = new ExternalRequestDao().fetchExternalRequests(new Gson().toJson(apiData));

//                    if (records != null && records.size() > 0) {
//                        out.println(new Gson().toJson(new JsonResponse("00", "success", new Gson().toJson(records))));
//                    } else {
//                        out.println(new Gson().toJson(new JsonResponse("01", "No Data Found")));
//                    }

                    if (records != null && records.size() > 0) {
                        //BUILD JSON ARRAY

                        JSONArray jsonArray = new JSONArray();

                        records.forEach(f -> {
                            jsonArray.put(createJsonObject(f.getId(), f.getUnique_ref(), f.getMerchant_name(), f.getMsisdn(), f.getReq_data(), f.getReq_status(), f.getReq_date()));
                        });
                        out.println(new Gson().toJson(new JsonResponse("00", "success", jsonArray.toString())));

                    } else {
                        out.println(new Gson().toJson(new JsonResponse("01", "success", new Gson().toJson(""))));
                    }
                } else if (apiData.getService().equalsIgnoreCase("merchants")) {

                    List<NODES> records = new MerchantPayDao().getMerchants(new Gson().toJson(apiData));

                    out.println(new Gson().toJson(records));

                }
            } else {
                out.println(new Gson().toJson(new JsonResponse("01", "Authorization Required")));
            }

        }
    }

    public JSONObject createJsonObject(int Id, String Unique_ref, String Merchant_name, String Msisdn, String Req_data, String Req_status, String Req_date) {
        JSONObject json = new JSONObject();

        try {
            json.put("id", Id);
            json.put("unique_ref", Unique_ref);
            json.put("merchant_name", Merchant_name);
            json.put("msisdn", Msisdn);
            Map<String, String> mp
                    = Splitter.on("~ ")
                            .withKeyValueSeparator(":")
                            .split(Req_data);
            mp.entrySet().stream().map((entry) -> {
                return entry;
            }).forEachOrdered((entry) -> {
                json.put(entry.getKey(), entry.getValue());
            });

            json.put("req_status", Req_status);
            json.put("req_date", Req_date);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return json;
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
