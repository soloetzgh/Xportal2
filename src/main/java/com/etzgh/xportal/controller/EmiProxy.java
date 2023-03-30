package com.etzgh.xportal.controller;

import com.etzgh.xportal.cdi.UserSession;
import com.etzgh.xportal.dao.EmiDao;
import com.etzgh.xportal.model.ApiPostData;
import com.etzgh.xportal.model.BulkUpload;
import com.etzgh.xportal.model.EmiPsp;
import com.etzgh.xportal.model.JsonResponse;
import com.etzgh.xportal.model.NODES;
import com.etzgh.xportal.utility.Config;
import com.etzgh.xportal.utility.GeneralUtility;
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
 *
 * @author Eugene Sunkwa-Arthur
 */
public class EmiProxy extends HttpServlet {

    private static final GeneralUtility utility = new GeneralUtility();
    private static final Logger log = Logger.getLogger(EmiProxy.class.getName());
    private static final List<NODES> emiAccountTypes = new ArrayList<>();

    static {
        final String types = new Config().getProperty("EMI_ACCOUNT_TYPES", "").trim();
        if (!types.isEmpty()) {

            final List<String> typeList = Arrays.asList(types.split("\\s*,\\s*"));
            log.info("EMI ACCOUNT TYPES::: " + typeList);

            for (final String f : typeList) {
                final NODES ac = new NODES();
                ac.setId(f);
                ac.setName(f.replaceAll("_", " "));
                emiAccountTypes.add(ac);
            }
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserSession userSession;
        userSession = (UserSession) request.getSession().getAttribute("userSession");
        response.setContentType("application/json");

        try (PrintWriter out = response.getWriter()) {

            ApiPostData apiData = new ApiPostData();
            String userName = userSession.getUser().getUsername();

            String userType = userSession.getUser().getType_id();
            String userCode = userSession.getUser().getUser_code();
            String bankCode = userSession.getUser().getBankCode();

            apiData.setBank_code(bankCode);

            List<String> roleList = new ArrayList<>();

            Boolean allowUser = false;

            if ((userType.contains("[69]") && !utility.getRoleParameters("[2500000000000061]", userCode).isEmpty())) {
                String userRoles = utility.getRoleParameters("[2500000000000061]", userCode);
                log.info("usrRoles: " + userRoles);
                roleList = Arrays.asList(userRoles.split("~"));
                allowUser = !roleList.isEmpty();
            }

            if (allowUser) {

                apiData.setService(request.getParameter("service"));
                apiData.setOptionType(request.getParameter("optionType"));
                apiData.setOtherInfo(request.getParameter("otherInfo"));
                apiData.setStatus(request.getParameter("status"));
                apiData.setStartDate(request.getParameter("startDate"));
                apiData.setEndDate(request.getParameter("endDate"));

                apiData.setFrom_source(request.getParameter("from_source"));
                apiData.setUserCode(userSession.getUser().getUser_code());

                apiData.setIpAddress((String) request.getAttribute("ipAddress"));
                apiData.setUser_id(userSession.getUser().getUser_id());

                String action = request.getParameterMap().containsKey("action") ? request.getParameter("action") : "";

                if (!action.isEmpty()) {
                    apiData.setService(action);
                }

                if (apiData.getService().equalsIgnoreCase("requests")) {

                    log.info(new Gson().toJson(apiData));
                    if (userType.contains("[0]")) {
                        apiData.setBank_code(request.getParameter("bank"));
                    }
                    if (roleList.contains("1") || roleList.contains("3") || roleList.contains("2")) {
                        List<EmiPsp> records = new EmiDao().getEmiRequests(new Gson().toJson(apiData));
                        if (records != null && records.size() > 0) {
                            out.println(new Gson().toJson(new JsonResponse("00", "success", new Gson().toJson(records))));
                        } else {
                            out.println(new Gson().toJson(new JsonResponse("01", "No Data Found")));
                        }
                    } else {
                        out.println(new Gson().toJson(new JsonResponse("01", "Authorization Required")));
                    }

                } else if (apiData.getService().equalsIgnoreCase("emiAccountTypes")) {
                    out.println(new Gson().toJson(emiAccountTypes));
                } else if (apiData.getService().equalsIgnoreCase("uploadFile")) {

                    if (apiData.getOtherInfo().length() > 1) {
                        if (roleList.contains("2") || roleList.contains("3") || roleList.contains("1")) {
                            String returnValue = new EmiDao().initiateEmiRequest(apiData.getOtherInfo(), userName, apiData.getOptionType(), bankCode);
                            log.info("EMI RSP >> " + returnValue);
                            if (returnValue.equals("true")) {
                                out.println(new Gson().toJson(new JsonResponse("00", "Request will be processed")));
                            } else {
                                out.println(new Gson().toJson(new JsonResponse("01", "An error occured processing your request")));
                            }
                        } else {
                            out.println(new Gson().toJson(new JsonResponse("01", "Authorization Required")));
                        }
                    } else {
                        log.info("No Requests To Process");
                        out.println(new Gson().toJson(new JsonResponse("01", "No Requests To Process")));
                    }

                } else if (apiData.getService().equalsIgnoreCase("initiatedRequests") || apiData.getService().equalsIgnoreCase("searchIniRequests")) {
                    if (roleList.contains("1") || roleList.contains("3")) {
                        List<BulkUpload> records = new EmiDao().getInitiatedBulkRequests(new Gson().toJson(apiData));
                        if (records != null && records.size() > 0) {
                            out.println(new Gson().toJson(new JsonResponse("00", "success", new Gson().toJson(records))));
                        } else {
                            out.println(new Gson().toJson(new JsonResponse("01", "No Data Found")));
                        }
                    } else {
                        out.println(new Gson().toJson(new JsonResponse("01", "Authorization Required")));
                    }

                } else {
                    out.println(new Gson().toJson(new JsonResponse("01", "Service not configured")));
                }
            } else {
                out.println(new Gson().toJson(new JsonResponse("01", "Authorization Required")));
            }

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
