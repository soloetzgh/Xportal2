/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etzgh.xportal.controller;

import com.etzgh.xportal.cdi.UserSession;
import com.etzgh.xportal.dao.PbcDao;
import com.etzgh.xportal.model.Activity;
import com.etzgh.xportal.model.ApiPostData;
import com.etzgh.xportal.model.Bank;
import com.etzgh.xportal.model.CocoaProfile;
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
public class PbcProxy extends HttpServlet {

    final static GeneralUtility utility = new GeneralUtility();

    private static final Logger log = Logger.getLogger(PbcProxy.class.getName());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserSession userSession;
        userSession = (UserSession) request.getSession().getAttribute("userSession");
        response.setContentType("application/json");

        List<Bank> categoryList = new ArrayList<>();

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
            apiData.setOptionType(request.getParameter("option"));
            apiData.setFrom_source(request.getParameter("from_source"));
            apiData.setLineType(request.getParameterMap().containsKey("dm") ? request.getParameter("dm") : "ALL");

            apiData.setStatus(request.getParameterMap().containsKey("status") ? request.getParameter("status") : "ALL");

            apiData.setService(request.getParameter("service"));
            apiData.setBank(request.getParameter("bank"));
            apiData.setSearchKey(request.getParameter("searchKey"));
            apiData.setUserCode(ucode);
            apiData.setUser_id(userSession.getUser().getUser_id());
            apiData.setType_id(type_id);
            apiData.setBank_code(userSession.getUser().getBankCode());
            apiData.setCardSchemeNumbers(userSession.getUser().getCardSchemeNumbers());

            String action = request.getParameterMap().containsKey("action") ? request.getParameter("action") : "";
            String idNo = request.getParameterMap().containsKey("idNo") ? request.getParameter("idNo") : "";

            if (!action.isEmpty()) {
                apiData.setService(action);
            }
            Boolean allowed = (!type_id.isEmpty() && (type_id.contains("[0]")
                    || (type_id.contains("[56]") && !utility.getRoleParameters("[2500000000000055]", ucode).isEmpty())
                    || (type_id.contains("[68]"))));
            if (allowed) {
                if (type_id.contains("[56]")) {

                    apiData.setClientId(utility.getRoleParameters("[2500000000000055]", ucode));

                }
                if (apiData.getService().equalsIgnoreCase("transactions")
                        || apiData.getService().equalsIgnoreCase("search")) {

                    List<Activity> records = new PbcDao().getPBCTrans(new Gson().toJson(apiData));

                    if (records != null && records.size() > 0) {
                        out.println(new Gson().toJson(new JsonResponse("00", "success", new Gson().toJson(records))));
                    } else {
                        out.println(new Gson().toJson(new JsonResponse("01", "No Data Found")));
                    }
                } else if (apiData.getService().equalsIgnoreCase("cardholderEnquiry")
                        || apiData.getService().equalsIgnoreCase("cardholderEnquirySearch")) {

                    List<CocoaProfile> records = new PbcDao().getDetailedProfiles(new Gson().toJson(apiData));

                    if (records != null && records.size() > 0) {
                        out.println(new Gson().toJson(new JsonResponse("00", "success", new Gson().toJson(records))));
                    } else {
                        out.println(new Gson().toJson(new JsonResponse("01", "No Data Found")));
                    }
                } else if (apiData.getService().equalsIgnoreCase("getProfiles")) {

                    List<NODES> records = new PbcDao().getPBCProfiles(new Gson().toJson(apiData));

                    out.println(new Gson().toJson(records));

                } else if (apiData.getService().contains("PbcProfiles")) {

                    List<NODES> records = new ArrayList<>();

                    boolean allowRmProfiles = false;
                    boolean allowDoProfiles = false;
                    boolean allowPcProfiles = false;
                    String roleItem = apiData.getService().split("PbcProfiles")[0].toUpperCase();

                    if (type_id.contains("[56]")) {

                        String role = utility.getRoleParameters("[2500000000000055]", ucode).split(":",-1)[1]
                                .toUpperCase();

                        switch (role) {
                            case "HO":
                                allowRmProfiles = roleItem.equalsIgnoreCase("RM");
                                allowDoProfiles = roleItem.equalsIgnoreCase("DO") && !idNo.isEmpty();
                                allowPcProfiles = roleItem.equalsIgnoreCase("PC") && !idNo.isEmpty();
                                if (!idNo.isEmpty()) {
                                    apiData.setFrom_source(idNo);
                                }
                                break;
                            case "RM":

                                allowRmProfiles = false;
                                allowDoProfiles = roleItem.equalsIgnoreCase("DO");
                                if (allowDoProfiles) {
                                    apiData.setFrom_source(
                                            utility.getRoleParameters("[2500000000000055]", ucode).split(":")[0]);
                                }
                                allowPcProfiles = roleItem.equalsIgnoreCase("PC") && !idNo.isEmpty();
                                if (allowPcProfiles) {
                                    apiData.setFrom_source(idNo);
                                }
                                break;
                            case "DO":

                                allowRmProfiles = false;
                                allowDoProfiles = false;
                                idNo = utility.getRoleParameters("[2500000000000055]", ucode).split(":")[0];
                                allowPcProfiles = roleItem.equalsIgnoreCase("PC");
                                apiData.setFrom_source(idNo);
                                break;
                            case "PC":
                                allowRmProfiles = false;
                                allowDoProfiles = false;
                                allowPcProfiles = false;
                                break;
                            default:
                                break;
                        }
                    } else if (type_id.contains("[0]")) {

                    }

                    System.out.println("ROLE ITEM: " + roleItem);
                    apiData.setMerchant(roleItem);

                    if (allowRmProfiles) {
                        System.out.println("ALLOWED RM");

                        records = new PbcDao().getPBCProfiles(new Gson().toJson(apiData));
                    }
                    if (allowDoProfiles) {
                        System.out.println("ALLOWED DO");
                        records = new PbcDao().getPBCProfiles(new Gson().toJson(apiData));
                    }
                    if (allowPcProfiles) {
                        System.out.println("ALLOWED PC");

                        records = new PbcDao().getPBCProfiles(new Gson().toJson(apiData));
                    }

                    out.println(new Gson().toJson(records));

                } else if (apiData.getService().equalsIgnoreCase("activityTypes")) {

                    List<NODES> records = new PbcDao().getActivityTypes(new Gson().toJson(apiData));

                    out.println(new Gson().toJson(records));

                } else if (apiData.getService().equalsIgnoreCase("categories")) {

                    out.println(new Gson().toJson(categoryList));
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
