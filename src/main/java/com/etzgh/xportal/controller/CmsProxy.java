/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etzgh.xportal.controller;

import com.etzgh.xportal.cdi.UserSession;
import com.etzgh.xportal.dao.CmsDao;
import com.etzgh.xportal.model.ApiPostData;
import com.etzgh.xportal.model.ECARDHOLDER;
import com.etzgh.xportal.model.E_CMS;
import com.etzgh.xportal.model.GVIVE;
import com.etzgh.xportal.model.JsonResponse;
import com.etzgh.xportal.model.NODES;
import com.etzgh.xportal.utility.DoHTTPRequest;
import com.etzgh.xportal.utility.GeneralUtility;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.apache.log4j.Logger;

/**
 *
 * @author sunkwa-arthur
 */
public class CmsProxy extends HttpServlet {

    private static final long serialVersionUID = -1268579599504543570L;
    private static final PortalSettings portalSettings = new PortalSettings();
    private static String id_verifier = "";
    private static final GeneralUtility utility = new GeneralUtility();

    private static final Logger log = Logger.getLogger(CmsProxy.class.getName());

    static {
        id_verifier = StringUtils.substringBeforeLast(portalSettings.getSetting("id_verifier"), ":");
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException, RollbackException, HeuristicMixedException, HeuristicRollbackException {
        UserSession userSession;
        userSession = (UserSession) request.getSession().getAttribute("userSession");
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            ApiPostData apiData = new ApiPostData();
            apiData.setIpAddress((String) request.getAttribute("ipAddress"));
            List<String> roleList = new ArrayList<>();
            apiData.setUserCode(userSession.getUser().getUser_code());
            apiData.setIpAddress((String) request.getAttribute("ipAddress"));
            apiData.setUser_id(userSession.getUser().getUser_id());
            String userType = userSession.getUser().getType_id();
            apiData.setUserName(userSession.getUser().getUsername());

            HashMap<String, Object> options = new HashMap<>();
            String firstName = (request.getParameterMap().containsKey("firstname")
                    ? request.getParameter("firstname").trim()
                    : "");
            String lastName = (request.getParameterMap().containsKey("lastname")
                    ? request.getParameter("lastname").trim()
                    : "");
            String branchCode = (request.getParameterMap().containsKey("branchcode")
                    ? request.getParameter("branchcode").trim()
                    : "");
            String idNumber = request.getParameterMap().containsKey("id_number") ? request.getParameter("id_number")
                    : "";
            String idType = request.getParameterMap().containsKey("id_type") ? request.getParameter("id_type") : "";
            options.put("firstname", firstName);
            options.put("lastname", lastName);
            options.put("branchcode", branchCode.isEmpty() ? "001" : branchCode);
            options.put("fax", !(idType.trim().isEmpty() && idNumber.trim().isEmpty()) ? idType + " " + idNumber : "");

            apiData.setOptions(options);
            apiData.setType_id(userType);

            String card_num = request.getParameter("card_number");

            apiData.setStartDate(request.getParameter("startDate"));
            apiData.setEndDate(request.getParameter("endDate"));
            apiData.setStatus(request.getParameter("status"));

            String from_source = request.getParameter("mobile_number");

            String service = request.getParameter("service");

            if (service == null) {
                service = "";
            }

            String action = request.getParameterMap().containsKey("action") ? request.getParameter("action") : "";

            String reason = request.getParameterMap().containsKey("reason") ? request.getParameter("reason") : "";

            apiData.setAction(action);

            if (from_source == null) {
                from_source = "";
            }
            if (card_num == null) {
                card_num = "";
            }

            Boolean allowed = (!userType.isEmpty() && (userType.contains("[0]") || (userType.contains("[6]")
                    && !utility.getRoleParameters("[2000000000000060]", apiData.getUserCode()).isEmpty())));

            System.out.println("ALLOWED 1: " + allowed + " " + userType);
            roleList = Arrays
                    .asList(utility.getRoleParameters("[50]", userSession.getUser().getUser_code()).split("~"));

            allowed = !roleList.isEmpty();

            System.out.println("ALLOWED: " + allowed + " ROLE: " + roleList);
            if (allowed) {
                apiData.setAppId(roleList);
                String bankCode = "000";
                if (apiData.getType_id().contains("[0]")) {
                } else if (apiData.getType_id().contains("[6]")) {
                    bankCode = utility.getRoleParameters("[2000000000000060]", apiData.getUserCode());
                }
                apiData.setBank_code(bankCode);
                apiData.setCard_num(card_num);
                apiData.setFrom_source(from_source);

                if (service.equalsIgnoreCase("search")) {

                    List<ECARDHOLDER> records = new CmsDao().getCardDetails(new Gson().toJson(apiData));

                    if (!records.isEmpty()) {
                        out.println(new Gson().toJson(new JsonResponse("00", "success", new Gson().toJson(records))));
                    } else {
                        out.println(new Gson().toJson(new JsonResponse("01", "No Data Found")));
                    }

                } else if (service.equalsIgnoreCase("modify")
                        && (roleList.contains("1") || roleList.contains("2") || roleList.contains("3"))) {

                    apiData.setOptionType(reason);
                    apiData.setStatus(request.getParameter("choice"));
                    if (action.equalsIgnoreCase("addaccount") && (firstName.isEmpty() || lastName.isEmpty())
                            && roleList.contains("2")) {
                        out.println(new Gson().toJson(new JsonResponse("01", "Firstname and Lastname is required")));
                    } else {
                        String resp = new CmsDao().modifyCardAccount(new Gson().toJson(apiData));
                        out.println(resp);
                    }

                } else if (service.equalsIgnoreCase("verifyID")) {
                    GVIVE requestVerify = new GVIVE();

                    if (!(idNumber.isEmpty() || idType.isEmpty())) {
                        String fname = "";
                        String mname = "";
                        String lname = "";

                        requestVerify.setIdType(idType);
                        requestVerify.setIdNumber(idNumber);
                        requestVerify.setFirstName(fname);
                        requestVerify.setMiddleName(mname);
                        requestVerify.setLastName(lname);
                        String jsonRequest = new Gson().toJson(requestVerify);
                        System.out.println("JSON REQUEST" + jsonRequest);
                        String statusResponse = new DoHTTPRequest().post(id_verifier, jsonRequest);

                        System.out.println("Response " + statusResponse);
                        out.println(new Gson().toJson(new JsonResponse("00", "success", statusResponse)));
                    } else {
                        out.println(new Gson().toJson(new JsonResponse("01", "Invalid National Id")));
                    }
                } else if (service.equalsIgnoreCase("requests")) {
                    List<E_CMS> records = new CmsDao().getCmsRequests(new Gson().toJson(apiData));

                    if (!records.isEmpty()) {
                        out.println(new Gson().toJson(new JsonResponse("00", "success", new Gson().toJson(records))));
                    } else {
                        out.println(new Gson().toJson(new JsonResponse("01", "No Data Found")));
                    }
                } else if (apiData.getAction().equalsIgnoreCase("branches")) {

                    List<NODES> records = new CmsDao().getSubissuers(new Gson().toJson(apiData));
                    out.println(new Gson().toJson(records));
                } else {

                    out.println(new Gson().toJson(new JsonResponse("01", "Service Unavailable")));
                }

            } else {
                out.println(new Gson().toJson(new JsonResponse("01", "Authorization Required")));
            }
        }
    }

    protected boolean VerifyId(String idType, String idNumber) {
        boolean result = false;
        System.out.println("GVIVE VERIFICATION");
        GVIVE requestVerify = new GVIVE();

        try {
            System.out.println("Id: " + idType + " Number: " + idNumber);
            if (!idType.equals("") && !idNumber.equals("")) {
                String fname = "";
                String mname = "";
                String lname = "";
                try {
                    System.out.println("Type: " + idType + "\nIdNo: " + idNumber);
                    fname = "";
                    mname = "";
                    lname = "";
                    requestVerify.setIdType(idType);
                    requestVerify.setIdNumber(idNumber);
                    requestVerify.setFirstName(fname);
                    requestVerify.setMiddleName(mname);
                    requestVerify.setLastName(lname);
                    String jsonRequest = new Gson().toJson(requestVerify);
                    System.out.println("JSON REQUEST" + jsonRequest);
                    String statusResponse = new DoHTTPRequest().post(id_verifier, jsonRequest);

                    JSONObject Some = new JSONObject(statusResponse);

                    result = !((Some.optString("message")).split("_")[0].contains("No Person Found")
                            || (Some.optString("message")).split("_")[0].contains("Incorrect Id Type")
                            || (Some.getString("message")).split("_")[0].contains("HTTP error code"));
                } catch (ArrayIndexOutOfBoundsException | JsonSyntaxException ex) {
                    result = false;
                }

                System.out.println("Result : " + result);
            } else {
                result = false;
            }
        } catch (ArrayIndexOutOfBoundsException | JsonSyntaxException e) {
            result = false;
        }
        return result;
    }

    protected boolean validateName(String firstName, String lastName) {
        try {
            return !(firstName.trim().isEmpty() || firstName.toLowerCase().contains("missing id type and")
                    || firstName.toLowerCase().contains("number")
                    || firstName.toLowerCase().contains("money_card") || firstName.toLowerCase().contains("mobile")
                    || firstName.toLowerCase().contains("invalid")
                    || lastName.toLowerCase().contains("missing id type and")
                    || lastName.toLowerCase().contains("number")
                    || lastName.toLowerCase().contains("money_card") || lastName.toLowerCase().contains("mobile")
                    || lastName.toLowerCase().contains("invalid"));
        } catch (Exception df) {
            return false;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
