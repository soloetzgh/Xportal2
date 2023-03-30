/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etzgh.xportal.controller;

import com.etzgh.xportal.cdi.UserSession;
import com.etzgh.xportal.dao.AppDao;
import com.etzgh.xportal.dao.NlaLiquidationReportDao;
import com.etzgh.xportal.dao.VasgateReprocessDao;
import com.etzgh.xportal.model.ApiPostData;
import com.etzgh.xportal.model.Channel;
import com.etzgh.xportal.model.JsonResponse;
import com.etzgh.xportal.model.NODES;
import com.etzgh.xportal.model.NlaLiquidationReport;
import com.etzgh.xportal.service.UserProfileService;
import com.etzgh.xportal.utility.GeneralUtility;
import com.etzgh.xportal.utility.HashNumber;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author yaw.owusu-koranteng
 */
public class NlaLiquidationReportProxy extends HttpServlet {

    private static List<Object[]> noderecords;

    private static final GeneralUtility utility = new GeneralUtility();

    private static final VasgateReprocessDao vasreprocess = new VasgateReprocessDao();
    private static final AppDao ad = new AppDao();
    private static final Logger log = Logger.getLogger(NlaLiquidationReportProxy.class.getName());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException {
        UserSession userSession;
        userSession = (UserSession) request.getSession().getAttribute("userSession");
        response.setContentType("application/json");

        String url = ((HttpServletRequest) request).getRequestURL().toString();

        try (PrintWriter out = response.getWriter()) {

            ApiPostData apiData = new ApiPostData();

            String userType = userSession.getUser().getType_id();
            List<String> roleList = new ArrayList<>();

            apiData.setStartDate(request.getParameter("startDate"));
            apiData.setEndDate(request.getParameter("endDate"));
            apiData.setPan(request.getParameter("pan"));
            apiData.setFrom_source(request.getParameter("from_source"));
            apiData.setStatus(request.getParameter("status"));
            apiData.setUniqueTransId(request.getParameter("trans_data"));
            apiData.setTransType(request.getParameter("transtype"));
            apiData.setOptionType(request.getParameter("optionType"));
            apiData.setChannel(request.getParameter("channel"));
            apiData.setService(request.getParameter("service"));
            apiData.setSearchKey(request.getParameter("searchKey"));
            apiData.setUserCode(userSession.getUser().getUser_code());
            apiData.setIpAddress((String) request.getAttribute("ipAddress"));
            apiData.setUniqueTransId(request.getParameter("uniqueTransId"));
            apiData.setUser_id(userSession.getUser().getUser_id());
            apiData.setType_id(userSession.getUser().getType_id());

            apiData.setCardSchemeNumbers(userSession.getUser().getCardSchemeNumbers());
            apiData.setUserName(userSession.getUser().getUsername());

            apiData.setStatus(request.getParameter("status"));
            apiData.setAccount(request.getParameter("account"));
            apiData.setClient(request.getParameter("client"));
            apiData.setVastype(request.getParameter("vastype"));
            apiData.setAmount(request.getParameter("amount"));
            apiData.setMobile_no(request.getParameter("mobile"));

            apiData.setTicketNumber(request.getParameter("ticketNumber"));
            apiData.setEtzReference(request.getParameter("etzReference"));
            apiData.setNlaReference(request.getParameter("nlaReference"));
            apiData.setNewCustomerId(request.getParameter("newCustomerId"));

            apiData.setData(request.getParameter("data"));
            System.out.println("Printing Data:" + request.getParameter("data"));

            String UserCode = apiData.getUserCode();
            String UserType = userSession.getUser().getType_id();
            String UserId = userSession.getUser().getUser_id();

            apiData.setDestination(request.getParameter("destination"));

            String action = request.getParameterMap().containsKey("action") ? request.getParameter("action") : "";

            if (!action.isEmpty()) {
                apiData.setService(action);
            }

            System.out.println(new Gson().toJson(apiData));

            Boolean allowUpdate = false;
            log.info("userType: " + userType);

            if (userType.contains("[0]") || userType.contains("[67]") || userType.contains("[71]") || userType.contains("[72]")) {
                String userRoles = new UserProfileService().getRoleParameters("[2500000000000062]",
                        userSession.getUser().getUser_code());
                log.info("usrRoles: " + userRoles);
                roleList = Arrays.asList(userRoles.split("~"));
                log.info("roleList: " + roleList);
                allowUpdate = !roleList.isEmpty();
            }

            if (allowUpdate) {

                if (apiData.getService().equalsIgnoreCase("channels")) {
                    List<Channel> channels = ad.getChannels();
                    out.println(new Gson().toJson(channels));
                    channels = null;
                } else if (apiData.getService().equalsIgnoreCase("nlaliquidationreport")) {
                    System.out.println("NLA Proxy started");
                    if (allowUpdate) {
                        List<NlaLiquidationReport> records = new NlaLiquidationReportDao().getNlaLiquidationReport(new Gson().toJson(apiData));

                        if (records != null && records.size() > 0) {
                            out.println(new Gson().toJson(new JsonResponse("00", "success", new Gson().toJson(records))));
                        } else {
                            out.println(new Gson().toJson(new JsonResponse("01", "No Data Found")));
                        }
                        records = null;
                    } else {
                        out.println(new Gson().toJson(new JsonResponse("01", "Authorization Required")));
                    }

                } else if (apiData.getService().equalsIgnoreCase("nlatransactions")) {
                    System.out.println("NLA transactions started");
                    if (allowUpdate) {
                        List<NlaLiquidationReport> records = new NlaLiquidationReportDao().getNlaTransaction(new Gson().toJson(apiData));

                        if (records != null && records.size() > 0) {
                            out.println(new Gson().toJson(new JsonResponse("00", "success", new Gson().toJson(records))));
                        } else {
                            out.println(new Gson().toJson(new JsonResponse("01", "No Data Found")));
                        }
                        records = null;
                    } else {
                        out.println(new Gson().toJson(new JsonResponse("01", "Authorization Required")));
                    }

                } else if (apiData.getService().equalsIgnoreCase("initiateReversal")) {

                    if (roleList.contains("2")) {

                        System.out.println("NLA initiate reversal now started" + request);
                        String reference = request.getParameter("transid");
                        System.out.println("Reference" + reference);
                        String transid = request.getParameter("transid");
                        System.out.println("transid" + transid);

                        String records = new NlaLiquidationReportDao().callInitiateReversal(new Gson().toJson(apiData));

                        if (records.length() > 0) {
                            out.println(new Gson().toJson(new JsonResponse("00", "success", new Gson().toJson(records))));
                        } else {
                            out.println(new Gson().toJson(new JsonResponse("01", "No Data Found")));
                        }
                    } else {
                        out.println(new Gson().toJson(new JsonResponse("01", "Authorization Required")));
                    }

                } else if (apiData.getService().equalsIgnoreCase("nlatransactionsauthorize")) {
                    System.out.println("NLA Proxy authorise started");
                    if (allowUpdate) {
                        List<NlaLiquidationReport> records = new NlaLiquidationReportDao().getNlaTransactionsAuthorizeReport(new Gson().toJson(apiData));

                        if (records != null && records.size() > 0) {
                            out.println(new Gson().toJson(new JsonResponse("00", "success", new Gson().toJson(records))));
                        } else {
                            out.println(new Gson().toJson(new JsonResponse("01", "No Data Found")));
                        }
                        records = null;
                    } else {
                        out.println(new Gson().toJson(new JsonResponse("01", "Authorization Required")));
                    }

                } else if (apiData.getService().equalsIgnoreCase("authorizeReversal")) {
                    if (roleList.contains("3") || roleList.contains("1")) {

                        System.out.println("NLA authorize reversal now started" + request);

                        String records = new NlaLiquidationReportDao().callAuthorizeReversal(new Gson().toJson(apiData));

                        if (records.length() > 0) {
                            out.println(new Gson().toJson(new JsonResponse("00", "success", new Gson().toJson(records))));
                        } else {
                            out.println(new Gson().toJson(new JsonResponse("01", "No Data Found")));
                        }

                    } else {
                        out.println(new Gson().toJson(new JsonResponse("01", "Authorization Required")));
                    }
                } else if (apiData.getService().equalsIgnoreCase("denyReversal")) {
                    if (roleList.contains("3") || roleList.contains("1")) {
//                            String id = request.getParameter("id");
//                            String reference = request.getParameter("transid");
//                            List<E_RequestLog> records = new ReversalDao().authorizeBulkReversal(reference, "3", apiData.getUserName(), apiData.getUser_id(), apiData.getIpAddress(), apiData.getOptionType(), apiData.getTransType());
//                            if (records != null && records.size() > 0) {
//
//                                out.println(new Gson().toJson(new JsonResponse("00", "success", new Gson().toJson(records))));
//                            } else {
//                                out.println(new Gson().toJson(new JsonResponse("01", "Reversal Failed")));
//                            }
                        System.out.println("NLA authorize reversal now started" + request);
                        //String reference = request.getParameter("transid");
                        //System.out.println("Reference" + reference);
                        //String transid = request.getParameter("transid");
                        //System.out.println("transid" + transid);
//                         String subscriberId = request.getParameter("subscriber_id");
//                         System.out.println("subscriberId"+subscriberId);
                        //List<E_RequestLog> records = new ReversalDao().initiateBulkReversal(reference, apiData.getUserName(), apiData.getUser_id(), apiData.getIpAddress());
//                         List<NlaLiquidationReport> records = new NlaLiquidationReportDao().callInitiateReversal(new Gson().toJson(apiData));
                        String records = new NlaLiquidationReportDao().callDenyReversal(new Gson().toJson(apiData));

//                        
                        if (records.length() > 0) {
                            out.println(new Gson().toJson(new JsonResponse("00", "success", new Gson().toJson(records))));
                        } else {
                            out.println(new Gson().toJson(new JsonResponse("01", "No Data Found")));
                        }
                    } else {
                        out.println(new Gson().toJson(new JsonResponse("01", "Authorization Required")));
                    }
                }
//                else if (apiData.getService().equalsIgnoreCase("tmcupdatelog") || apiData.getService().equalsIgnoreCase("updatetmcsearch")) {
//                    if (viewUpdate) {
//                        List<TmcUpdate> records = tmc.getTmcUpdateLog(new Gson().toJson(apiData));
//
//                        if (records != null && records.size() > 0) {
//                            out.println(new Gson().toJson(new JsonResponse("00", "success", new Gson().toJson(records))));
//                        } else {
//                            out.println(new Gson().toJson(new JsonResponse("01", "No Data Found")));
//                        }
//                        records = null;
//                    } else {
//                        out.println(new Gson().toJson(new JsonResponse("01", "Authorization Required")));
//                    }
//                }
//                else if (apiData.getService().equalsIgnoreCase("searchReversal")) {
//                    final String URL = baseURL + "trx/find";
//                    System.out.println(apiData);
//                    String tmcResponse = new DoHTTPRequest().post(URL, new Gson().toJson(apiData));
//                    if (tmcResponse != null) {
//                        out.println(new Gson().toJson(new JsonResponse("00", "success", tmcResponse)));
//                    } else {
//                        //01 = no record found
//                        out.println(new Gson().toJson(new JsonResponse("01", "success", tmcResponse)));
//                    }
//                } else if (apiData.getService().equalsIgnoreCase("count")) {
//                    final String URL = baseURL + "count";
//                    System.out.println(apiData);
//                    String tmcResponse = new DoHTTPRequest().post(URL, new Gson().toJson(apiData));
//
//                    out.println(new Gson().toJson(new JsonResponse("00", "success", tmcResponse)));
//                }
            } else {
                out.println(new Gson().toJson(new JsonResponse("01", "Authorization Required")));

            }
        }
    }

    protected String mapResponse(String code, String resp) {

        String RespMsg = "";
        resp = resp.substring(0, 3);
        if (code == null || resp == null) {
            return "No response";
        }
        if (code.equals("58") && (resp.equals("686") || resp.equals("844") || resp.equals("863") || resp.equals("285"))) {
            return "MobileMoney Debit Successful";
        }

        if (code.equals("00")) {
            return "SUCCESSFUL";
        }
        if (code.equals("-1")) {
            return "TIME OUT";
        }
        if (code.equals("55")) {
            return "INCORRECT PIN";
        }
        if (code.equals("51")) {
            return "INSUFFICIENT FUNDS";
        }
        if (code.equals("91")) {
            return "Issuer or switch inoperative";
        }
        if (code.equals("34")) {
            return "Suspected fraud, pick-up";
        }
        if (code.equals("58")) {
            return "Transaction not permitted on terminal";
        }

        return "FAILED";
    }

    protected String mapResponseCode(String Responsecode) {
        if (Responsecode != null && !Responsecode.equals("")) {
            return Responsecode;
        }
        return "No response";
    }

    protected Boolean nohash(String Usrcode, String UserId) {

        System.out.println("USR: " + Usrcode.contains("[6]"));
        System.out.println("USRQ: " + Usrcode.contains("[0]"));

        return Usrcode.contains("[6]") || Usrcode.contains("[0]");
    }

    protected String hashAccountNumber(String accNo, String UsrCode, String UserId) {
        if (accNo != null && !accNo.equals("") && !accNo.equals("null") && !accNo.equals("Null") && !accNo.equals("NULL")) {
            if (!nohash(UsrCode, UserId)) {
                int start = accNo.length() / 2;
                HashNumber hn = new HashNumber();

                return hn.hashStringValue(accNo, start / 2, start / 2);
            } else {
                return accNo;
            }
        }
        return accNo;
    }

    protected String mapTranstype(String b) {
        String transtype = "";

        switch (b.substring(0, 2)) {

            case "00":
                transtype = "Purchase";
                break;
            case "01":
                transtype = "Cash Withdrawal";
                break;

            case "31":
                transtype = "Balance Enquiry";
                break;

            case "38":
                transtype = "Mini Statement";
                break;
            case "42":
                transtype = "Funds Transfer";
                break;

            case "53":
                transtype = "Payments";
                break;

            case "13":
                transtype = "Payments";
                break;

            case "92":
                transtype = "Pin Change";
                break;

            case "95":
                transtype = "Account Query";
                break;

            default:
                transtype = "";
                break;
        }
        return transtype;
    }

    protected String mapSrc_node(String id) {

        for (Object[] obj : noderecords) {
            if (obj[0].toString().trim().equals(id.trim())) {
                return obj[1].toString();
            }

        }

        return id;
    }

    protected String mapTarget_node(String id) {
        for (Object[] obj : noderecords) {
            if (obj[0].toString().trim().equals(id.trim())) {
                return obj[1].toString();
            }

        }
        return id;
    }

    protected String mapResponseMsg(String msg, String resp) {
        String RespMsg = "";
        resp = resp.substring(0, 3);
        if (msg.equals("58") && (resp.equals("686") || resp.equals("844") || resp.equals("863") || resp.equals("285"))) {
            RespMsg = "MobileMoney Debit Successful";
        }
        return RespMsg;
    }

    public Map<String, String> convertListAfterJava8(List<NODES> list) {
        Map<String, String> map = list.stream().collect(Collectors.toMap(NODES::getId, node -> node.getName()));
        return map;
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
