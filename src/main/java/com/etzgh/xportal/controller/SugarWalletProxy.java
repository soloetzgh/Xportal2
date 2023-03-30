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
import com.etzgh.xportal.model.ApiPostData;
import com.etzgh.xportal.model.JsonResponse;
import com.etzgh.xportal.model.SugarWallet;
import com.etzgh.xportal.utility.DoHTTPRequest;
import com.etzgh.xportal.utility.GeneralUtility;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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

/**
 *
 * @author sunkwa-arthur
 */
public class SugarWalletProxy extends HttpServlet {

    private static final GeneralUtility utility = new GeneralUtility();
    private static final Logger log = Logger.getLogger(SugarWalletProxy.class.getName());
    public static final PortalSettings portalSettings = new PortalSettings();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserSession userSession;

        userSession = (UserSession) request.getSession().getAttribute("userSession");
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {

            ApiPostData apiData = new ApiPostData();

            String start_date = request.getParameterMap().containsKey("startDate") ? request.getParameter("startDate") : "";
            String end_date
                    = request.getParameterMap().containsKey("endDate") ? request.getParameter("endDate") : "";
            String identifier
                    = request.getParameterMap().containsKey("identifier") ? request.getParameter("identifier") : "";
            String client_trace_no
                    = request.getParameterMap().containsKey("client_trace_no") ? request.getParameter("client_trace_no") : "";
            String dr_cr
                    = request.getParameterMap().containsKey("dr_cr") ? request.getParameter("dr_cr") : "";
            String status_code
                    = request.getParameterMap().containsKey("status_code") ? request.getParameter("status_code") : "";
            String trace_id
                    = request.getParameterMap().containsKey("trace_id") ? request.getParameter("trace_id") : "";
            String app_id
                    = request.getParameterMap().containsKey("app_id") ? request.getParameter("app_id") : "4";

            String req = "{\n"
                    + "    \"startDate\": \"" + start_date + "\",\n"
                    + "    \"endDate\": \"" + end_date + "\",\n"
                    + "    \"trace_id\": \"" + trace_id + "\",\n"
                    + "    \"identifier\": \"" + identifier + "\",\n"
                    + "    \"dr_cr\": \"" + dr_cr + "\",\n"
                    + "    \"status_code\": \"" + status_code + "\",\n"
                    + "    \"client_trace_no\": \"" + client_trace_no + "\",\n"
                    + "    \"appId\":\"" + app_id + "\"\n"
                    + "}";

            apiData.setService(request.getParameter("service"));
            apiData.setUserCode(userSession.getUser().getUser_code());
            apiData.setIpAddress((String) request.getAttribute("ipAddress"));
            apiData.setUser_id(userSession.getUser().getUser_id());
            apiData.setType_desc(userSession.getUser().getType_desc());
            apiData.setType_id(userSession.getUser().getType_id());

            String action = request.getParameterMap().containsKey("action") ? request.getParameter("action") : "";

            if (!action.isEmpty()) {
                apiData.setService(action);
            }
            log.info(new Gson().toJson(apiData));

            Boolean allowed = (!apiData.getType_id().isEmpty() && (apiData.getType_id().contains("[0]") || (apiData.getType_id().contains("[3]") && !utility.getRoleParameters("[2000000000000054]", apiData.getUserCode()).isEmpty())));
            if (allowed) {
                if (apiData.getService().equalsIgnoreCase("transactions")) {

                    log.info(apiData);
                    List<SugarWallet> records = new ArrayList<>();
                    String swUrl = StringUtils.substringBeforeLast(portalSettings.getSetting("sw_url"), ":");
                    System.out.println("URL: " + swUrl + " " + req);
                    String result = new DoHTTPRequest().post(swUrl, req);

                    System.out.println("RESP: " + result);

                    records = new Gson().fromJson(result, new TypeToken<List<SugarWallet>>() {
                    }.getType());

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

    public static String convertToJson(String data) {
        String result = "";
        data = data.substring(1, data.length() - 1);
        System.out.println("RR: " + data.split(", ")[0]);
        List<String> csv = new ArrayList<>();
        csv = Arrays.asList(data.split(", "));

        if (csv != null && !csv.isEmpty()) {

            csv.removeIf(e -> e.trim().isEmpty());

            if (csv.size() <= 1) {
                return "[]";
            }

            String[] columns = csv.get(0).replace("\"", "").replaceAll("\\[(.*?)\\]", "$1").split(",");

            StringBuilder json = new StringBuilder("[");
            csv.subList(1, csv.size())
                    .stream()
                    .map(e -> e.replace("\"", "").replaceAll("\\[(.*?)\\]", "$1").split(","))
                    .filter(e -> e.length == columns.length)
                    .forEach(row -> {

                        json.append("{");

                        for (int i = 0; i < columns.length; i++) {
                            json.append("\"")
                                    .append(columns[i])
                                    .append("\":\"")
                                    .append(row[i])
                                    .append("\",");
                        }
                        json.replace(json.lastIndexOf(","), json.length(), "");

                        json.append("},");

                    });

            json.replace(json.lastIndexOf(","), json.length(), "");

            json.append("]");

            result = json.toString();
        }
        return result;
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
