package com.etzgh.xportal.controller;

/**
 *
 * @author sunkwa-arthur
 */
import com.etzgh.xportal.cdi.UserSession;
import com.etzgh.xportal.dao.AppDao;
import com.etzgh.xportal.dao.MobileInvestigationDao;
import com.etzgh.xportal.model.ApiPostData;
import com.etzgh.xportal.model.Bank;
import com.etzgh.xportal.model.JsonResponse;
import com.etzgh.xportal.model.MobileInvestigation;
import com.etzgh.xportal.model.MobileInvestigationDetails;
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

public class MobileInvestProxy extends HttpServlet {

    private static final Logger log = Logger.getLogger(MobileInvestProxy.class.getName());

    public final static GeneralUtility utility = new GeneralUtility();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserSession userSession;
        userSession = (UserSession) request.getSession().getAttribute("userSession");
        response.setContentType("application/json");

        try (PrintWriter out = response.getWriter()) {
            if (userSession == null) {
                out.println(new Gson().toJson(new JsonResponse("01", "Session Expired")));
            } else {
                ApiPostData apiData = new ApiPostData();
                apiData.setStartDate(request.getParameter("startDate"));
                apiData.setEndDate(request.getParameter("endDate"));
                apiData.setSubscriberId(request.getParameter("mobile_no"));
                apiData.setUniqueTransId(request.getParameter("unique_transid"));
                apiData.setBank_code(
                        request.getParameterMap().containsKey("bank") ? request.getParameter("bank") : "ALL");
                apiData.setTransType(
                        request.getParameterMap().containsKey("type") ? request.getParameter("type") : "ALL");

                apiData.setTransId(request.getParameter("trans_id"));
                apiData.setService(request.getParameter("service"));
                apiData.setSearchKey(request.getParameter("searchKey"));
                apiData.setUserCode(userSession.getUser().getUser_code());
                apiData.setUser_id(userSession.getUser().getUser_id());

                apiData.setType_id(userSession.getUser().getType_id());
                apiData.setCardSchemeNumbers(userSession.getUser().getCardSchemeNumbers());

                String action = request.getParameterMap().containsKey("action") ? request.getParameter("action") : "";

                if (!action.isEmpty()) {
                    apiData.setService(action);
                }

                Boolean allowed = (!apiData.getType_id().isEmpty()
                        && (apiData.getType_id().contains("[0]") || (apiData.getType_id().contains("[6]")
                        && !utility.getRoleParameters("[2000000000000060]", apiData.getUserCode()).isEmpty())));
                if (allowed) {

                    if (apiData.getType_id().contains("[0]")) {

                    } else if (apiData.getType_id().contains("[6]")) {
                        String bankCode = utility.getRoleParameters("[2000000000000060]", apiData.getUserCode());
                        apiData.setBank_code(bankCode);
                    }

                    if (apiData.getService().equalsIgnoreCase("mobileinvest")
                            || apiData.getService().equalsIgnoreCase("search")) {

                        System.out.println(apiData);

                        List<MobileInvestigation> records = new MobileInvestigationDao()
                                .getMobileInvestigations(new Gson().toJson(apiData));

                        if (records != null && records.size() > 0) {
                            out.println(
                                    new Gson().toJson(new JsonResponse("00", "success", new Gson().toJson(records))));
                        } else {
                            out.println(new Gson().toJson(new JsonResponse("01", "No Data Found")));
                        }
                    } else if (apiData.getService().equalsIgnoreCase("mobileinvestdetails")) {
                        apiData.setClientId(request.getParameter("appId"));
                        List<MobileInvestigationDetails> records = new MobileInvestigationDao()
                                .getDetailedMobileInvestigations(new Gson().toJson(apiData));

                        if (records != null && records.size() > 0) {
                            out.println(
                                    new Gson().toJson(new JsonResponse("00", "success", new Gson().toJson(records))));
                        } else {
                            out.println(new Gson().toJson(new JsonResponse("01", "No Data Found")));
                        }
                    } else if (apiData.getService().equalsIgnoreCase("banks")) {

                        List<Bank> records = apiData.getType_id().contains("[0]") ? new AppDao().getBanks()
                                : new ArrayList<>();

                        out.println(new Gson().toJson(records));
                    }
                }
            }
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
