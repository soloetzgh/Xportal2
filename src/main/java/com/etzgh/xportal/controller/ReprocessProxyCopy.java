//package com.etzgh.xportal.controller;
//
//import com.etzgh.xportal.cdi.UserSession;
//import com.etzgh.xportal.dao.ReprocessDao;
//import com.etzgh.xportal.model.ApiPostData;
//import com.etzgh.xportal.model.BulkUpload;
//import com.etzgh.xportal.model.JsonResponse;
//import com.etzgh.xportal.model.Reprocess;
//import com.etzgh.xportal.model.ReprocessLog;
//import com.etzgh.xportal.utility.GeneralUtility;
//import com.google.gson.Gson;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import org.apache.log4j.Logger;
//import org.json.JSONObject;
//
///**
// *
// * @author Eugene Sunkwa-Arthur
// */
//public class ReprocessProxyCopy extends HttpServlet {
//
//    private static final GeneralUtility utility = new GeneralUtility();
//    private static final Logger log = Logger.getLogger(ReprocessProxyCopy.class.getName());
//
//    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        UserSession userSession;
//        userSession = (UserSession) request.getSession().getAttribute("userSession");
//        response.setContentType("application/json");
//
//        try (PrintWriter out = response.getWriter()) {
//
//            String userName = userSession.getUser().getUsername();
//
//            String userType = userSession.getUser().getType_id();
//            List<String> roleList = new ArrayList<>();
//
//            Boolean allowUser = false;
//
//            if (userType.contains("[32]") || userType.contains("[34]")) {
//                String userRoles = utility.getRoleParameters("[2500000000000048]", userSession.getUser().getUser_code());
//                log.info("usrRoles: " + userRoles);
//                roleList = Arrays.asList(userRoles.split("~"));
//                allowUser = !roleList.isEmpty();
//            }
//
//            if (allowUser) {
//                ApiPostData apiData = new ApiPostData();
//                apiData.setStartDate(request.getParameter("startDate"));
//                apiData.setEndDate(request.getParameter("endDate"));
//                String transType = request.getParameter("transtype");
//                apiData.setService(request.getParameter("service"));
//
//                apiData.setSearchKey(request.getParameter("searchKey"));
//                apiData.setFrom_source(request.getParameter("source"));
//                apiData.setTransType(transType);
//                apiData.setDestination(request.getParameter("destination"));
//                apiData.setStatus(request.getParameter("status"));
//                apiData.setUniqueTransId(request.getParameter("unique_transid"));
//                apiData.setUserCode(userSession.getUser().getUser_code());
//                apiData.setIpAddress((String) request.getAttribute("ipAddress"));
//                apiData.setUser_id(userSession.getUser().getUser_id());
//                apiData.setCardSchemeNumbers(userSession.getUser().getCardSchemeNumbers());
//
//                if (apiData.getService().equalsIgnoreCase("transactions")) {
//
//                    log.info(new Gson().toJson(apiData));
//
//                    if (roleList.contains("2")) {
//                        List<Reprocess> records = new ReprocessDao().getFailedTransactionsForReprocess(new Gson().toJson(apiData));
//                        if (records != null && records.size() > 0) {
//                            out.println(new Gson().toJson(new JsonResponse("00", "success", new Gson().toJson(records))));
//                        } else {
//                            out.println(new Gson().toJson(new JsonResponse("01", "No Data Found")));
//                        }
//                    } else if (roleList.contains("1") || roleList.contains("3")) {
//                        List<ReprocessLog> records = new ReprocessDao().getInitiatedTransactions(new Gson().toJson(apiData));
//                        if (records != null && records.size() > 0) {
//                            out.println(new Gson().toJson(new JsonResponse("00", "success", new Gson().toJson(records))));
//                        } else {
//                            out.println(new Gson().toJson(new JsonResponse("01", "No Data Found")));
//                        }
//                    } else {
//                        out.println(new Gson().toJson(new JsonResponse("01", "Authorization Required")));
//                    }
//
//                } else if (apiData.getService().equalsIgnoreCase("reprocessedtrans")) {
//                    log.info(new Gson().toJson(apiData));
//
//                    if (roleList.contains("1") || roleList.contains("3")) {
//                        List<ReprocessLog> records = new ReprocessDao().getReprocessedTransactions(new Gson().toJson(apiData));
//                        if (records != null && records.size() > 0) {
//                            out.println(new Gson().toJson(new JsonResponse("00", "success", new Gson().toJson(records))));
//                        } else {
//                            out.println(new Gson().toJson(new JsonResponse("01", "No Data Found")));
//                        }
//                    } else {
//                        out.println(new Gson().toJson(new JsonResponse("01", "Authorization Required")));
//                    }
//                } else if (apiData.getService().equalsIgnoreCase("reprocess")) {
//                    String references = request.getParameter("references");
//                    JSONObject transaction = new JSONObject(references);
//                    if (transaction.length() > 1) {
//                        if (roleList.contains("2")) {
//                            String returnValue = new ReprocessDao().initiateReprocessing(transaction.toString(), userName, "unverified");
//                            log.info("REP INITIATOR RSP >> " + returnValue);
//                            out.println(new Gson().toJson(new JsonResponse("00", "success", returnValue)));
//                        } else if (roleList.contains("3") || roleList.contains("1")) {
//                            String action = request.getParameter("action").isEmpty() ? "" : request.getParameter("action");
//                            String returnValue = new ReprocessDao().authorizeReprocessing(transaction.toString(), userName, action);
//                            log.info("REP AUTHORIZER RSP >> " + returnValue);
//                            out.println(new Gson().toJson(new JsonResponse("00", "success", returnValue)));
//                        } else {
//                            out.println(new Gson().toJson(new JsonResponse("01", "Authorization Required")));
//                        }
//                    } else {
//                        log.info("No References To Reprocess");
//                        out.println(new Gson().toJson(new JsonResponse("01", "No Reference")));
//                    }
//
//                } else if (apiData.getService().equalsIgnoreCase("continuereprocess")) {
//                    String references = request.getParameter("references");
//
//                    JSONObject transaction = new JSONObject(references);
//
//                    if (transaction.length() > 1) {
//                        if (roleList.contains("3")) {
//                            String returnValue = new ReprocessDao().initiateReprocessing(transaction.toString(), userName, "verified");
//                            log.info("REP INITIATOR RSP >> " + returnValue);
//                            out.println(new Gson().toJson(new JsonResponse("00", "success", returnValue)));
//                        } else {
//                            out.println(new Gson().toJson(new JsonResponse("01", "Authorization Required")));
//                        }
//                    } else {
//                        log.info("No References To Reprocess");
//                        out.println(new Gson().toJson(new JsonResponse("01", "NO REFERENCE")));
//                    }
//
//                } else if (apiData.getService().equalsIgnoreCase("uploadFile")) {
//                    String data = request.getParameter("data");
//
//                    if (roleList.contains("2")) {
//                        String returnValue = new ReprocessDao().initiateReprocessing(data, userName, "verified");
//                        log.info("REP INITIATOR RSP >> " + returnValue);
//                        out.println(new Gson().toJson(new JsonResponse("00", "success", returnValue)));
//                    } else {
//                        out.println(new Gson().toJson(new JsonResponse("01", "Authorization Required")));
//                    }
//
//                } else if (apiData.getService().equalsIgnoreCase("initiatedRequests") || apiData.getService().equalsIgnoreCase("searchIniRequests")) {
//                    if (roleList.contains("1") || roleList.contains("3")) {
//                        List<BulkUpload> records = new ReprocessDao().getInitiatedBulkRequests(new Gson().toJson(apiData));
//                        if (records != null && records.size() > 0) {
//                            out.println(new Gson().toJson(new JsonResponse("00", "success", new Gson().toJson(records))));
//                        } else {
//                            out.println(new Gson().toJson(new JsonResponse("01", "No Data Found")));
//                        }
//                    } else {
//                        out.println(new Gson().toJson(new JsonResponse("01", "Authorization Required")));
//                    }
//
//                } else if (apiData.getService().equalsIgnoreCase("processBulkRequest")) {
//                    if (roleList.contains("1") || roleList.contains("3")) {
//                        String action = request.getParameter("action");
//                        boolean returnValue = new ReprocessDao().authorizeBatchRequest(apiData.getUniqueTransId(), userName, action);
//
//                        out.println(new Gson().toJson(new JsonResponse("00", returnValue ? "Processed Successfully" : "Processing Failed")));
//
//                    } else {
//                        out.println(new Gson().toJson(new JsonResponse("01", "Authorization Required")));
//                    }
//
//                } else {
//                    out.println(new Gson().toJson(new JsonResponse("01", "Service not configured")));
//                }
//            } else {
//                out.println(new Gson().toJson(new JsonResponse("01", "Authorization Required")));
//            }
//
//        } catch (Exception ex) {
//            log.error(ex.getMessage(), ex);
//        }
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        try {
//            processRequest(request, response);
//        } catch (Exception ex) {
//           log.error("",ex);
//        }
//    }
//
//}
