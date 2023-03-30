package com.etzgh.xportal.controller;

import com.etzgh.xportal.cdi.UserSession;
import com.etzgh.xportal.dao.ReversalDao;
import com.etzgh.xportal.model.ApiPostData;
import com.etzgh.xportal.model.E_RequestLog;
import com.etzgh.xportal.model.E_Reversal;
import com.etzgh.xportal.model.JsonResponse;
import com.etzgh.xportal.model.TMC;
import com.etzgh.xportal.service.UserProfileService;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 * @author sunkwa-arthur
 */
public class ReversalProxy extends HttpServlet {

    private static final long serialVersionUID = 2779093570295166493L;

    private static final Logger log = Logger.getLogger(ReversalProxy.class.getName());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserSession userSession;
        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            try {
                userSession = (UserSession) request.getSession().getAttribute("userSession");

                ApiPostData apiData = new ApiPostData();
                String userType = userSession.getUser().getType_id();
                List<String> roleList = new ArrayList<>();

                apiData.setUserCode(userSession.getUser().getUser_code());
                apiData.setIpAddress((String) request.getAttribute("ipAddress"));

                apiData.setUser_id(userSession.getUser().getUser_id());
                apiData.setUserName(userSession.getUser().getUsername());
                apiData.setType_id(userSession.getUser().getType_id());
                apiData.setService(request.getParameter("service"));
                apiData.setStartDate(request.getParameter("startDate"));
                apiData.setEndDate(request.getParameter("endDate"));
                apiData.setUniqueTransId(request.getParameter("trans_data"));
                apiData.setChannel(request.getParameter("appname"));
                apiData.setStatus(request.getParameter("status"));
                apiData.setOptionType(request.getParameter("choice"));

                apiData.setTransType(request.getParameter("transtype"));
                apiData.setSearchKey(request.getParameter("searchKey"));

                Boolean allowUser = false;

                if (userType.contains("[33]") || userType.contains("[31]")) {
                    String userRoles = new UserProfileService().getRoleParameters("[2500000000000049]",
                            userSession.getUser().getUser_code());
                    log.info("usrRoles: " + userRoles);
                    roleList = Arrays.asList(userRoles.split("~"));
                    allowUser = !roleList.isEmpty();
                }

                if (allowUser) {
                    if (apiData.getService().equalsIgnoreCase("search")
                            || apiData.getService().equalsIgnoreCase("transactions")
                            || apiData.getService().equalsIgnoreCase("bulkTransactions")) {

                        if (roleList.contains("2")) {
                            List<E_RequestLog> records = new ReversalDao()
                                    .getInitiatorTransactions(new Gson().toJson(apiData));
                            if (records != null && records.size() > 0) {
                                out.println(new Gson()
                                        .toJson(new JsonResponse("00", "success", new Gson().toJson(records))));
                            } else {
                                out.println(new Gson().toJson(new JsonResponse("01", "No Data Found")));
                            }
                        } else if (roleList.contains("1")) {
                            List<E_Reversal> records = new ReversalDao()
                                    .getReversedTransactions(new Gson().toJson(apiData));
                            if (records != null && records.size() > 0) {

                                out.println(new Gson()
                                        .toJson(new JsonResponse("00", "success", new Gson().toJson(records))));
                            } else {
                                out.println(new Gson().toJson(new JsonResponse("01", "No Data Found")));
                            }
                        } else if (roleList.contains("3")) {
                            List<E_Reversal> records = new ReversalDao()
                                    .getAuthorizerTransactions(new Gson().toJson(apiData));
                            if (records != null && records.size() > 0) {

                                out.println(new Gson()
                                        .toJson(new JsonResponse("00", "success", new Gson().toJson(records))));
                            } else {
                                out.println(new Gson().toJson(new JsonResponse("01", "No Data Found")));
                            }
                        } else {
                            out.println(new Gson().toJson(new JsonResponse("01", "Authorization Required")));
                        }
                    } else if (apiData.getService().equalsIgnoreCase("initiateReversal")) {
                        if (roleList.contains("2")) {

                            String reference = request.getParameter("transid");

                            List<E_RequestLog> records = new ReversalDao().initiateBulkReversal(reference,
                                    apiData.getUserName(), apiData.getUser_id(), apiData.getIpAddress());

                            if (records != null && records.size() > 0) {

                                out.println(new Gson()
                                        .toJson(new JsonResponse("00", "success", new Gson().toJson(records))));
                            } else {
                                out.println(new Gson().toJson(new JsonResponse("01", "No Data Found")));
                            }
                        } else {
                            out.println(new Gson().toJson(new JsonResponse("01", "Authorization Required")));
                        }

                    } else if (apiData.getService().equalsIgnoreCase("authorizeReversal")) {
                        if (roleList.contains("3") || roleList.contains("1")) {

                            String reference = request.getParameter("transid");
                            List<E_Reversal> records = new ReversalDao().authorizeBulkReversal(reference, "3",
                                    apiData.getUserName(), apiData.getUser_id(), apiData.getIpAddress(),
                                    apiData.getOptionType(), apiData.getTransType());
                            if (records != null && records.size() > 0) {

                                out.println(new Gson()
                                        .toJson(new JsonResponse("00", "success", new Gson().toJson(records))));
                            } else {
                                out.println(new Gson().toJson(new JsonResponse("01", "Reversal Failed")));
                            }
                        } else {
                            out.println(new Gson().toJson(new JsonResponse("01", "Authorization Required")));
                        }
                    } else if (apiData.getService().equalsIgnoreCase("searchReversal")) {
                        if (roleList.contains("3") || roleList.contains("1") || roleList.contains("2")) {

                            List<TMC> records = new ReversalDao().searchTmcTransactions(new Gson().toJson(apiData));

                            if (records != null && records.size() > 0) {

                                out.println(new Gson()
                                        .toJson(new JsonResponse("00", "success", new Gson().toJson(records))));
                            } else {
                                out.println(new Gson().toJson(new JsonResponse("01", "No Data Found")));
                            }
                        } else {
                            out.println(new Gson().toJson(new JsonResponse("01", "Authorization Required")));
                        }
                    } else {
                        out.println(new Gson().toJson(new JsonResponse("01", "Invalid Service")));
                    }
                } else {
                    out.println(new Gson().toJson(new JsonResponse("01", "Authorization Required")));
                }

            } catch (Exception e) {
                log.error("error: ", e);
                out.println(new Gson().toJson(new JsonResponse("01", "An Error Occured")));
            }
        }

    }

    public static boolean isNumeric(String inputData) {
        return inputData.matches("[-+]?\\d+(\\.\\d+)?");
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }

}
