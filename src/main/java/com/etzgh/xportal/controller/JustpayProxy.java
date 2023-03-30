package com.etzgh.xportal.controller;

import com.etzgh.xportal.cdi.UserSession;
import com.etzgh.xportal.dao.JustpayDao;
import com.etzgh.xportal.model.ApiPostData;
import com.etzgh.xportal.model.JsonResponse;
import com.etzgh.xportal.model.JustPayTransaction;
import com.etzgh.xportal.model.NODES;
import com.etzgh.xportal.utility.GeneralUtility;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
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
public class JustpayProxy extends HttpServlet {

    private static final GeneralUtility utility = new GeneralUtility();

    private static final Logger log = Logger.getLogger(JustpayProxy.class.getName());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserSession userSession;

        userSession = (UserSession) request.getSession().getAttribute("userSession");
        response.setContentType("application/json");

        try (PrintWriter out = response.getWriter()) {

            ApiPostData apiData = new ApiPostData();

            apiData.setStartDate(request.getParameter("startDate"));
            apiData.setEndDate(request.getParameter("endDate"));
            apiData.setUniqueTransId(request.getParameter("uniqueTransId"));
            apiData.setTrans_code(request.getParameter("trans_code"));
            apiData.setTransType(request.getParameter("transType"));
            apiData.setSubscriberId(request.getParameter("source"));
            apiData.setService(request.getParameter("service"));
            apiData.setDestination(request.getParameter("destination"));
            apiData.setTrans_status(request.getParameter("trans_status"));
            apiData.setPayType(request.getParameter("shortcode"));
            apiData.setSearchKey(request.getParameter("searchKey"));
            apiData.setProduct(request.getParameter("product"));
            apiData.setMerchant(request.getParameter("merchant"));
            apiData.setUserCode(userSession.getUser().getUser_code());
            apiData.setIpAddress((String) request.getAttribute("ipAddress"));
            apiData.setUser_id(userSession.getUser().getUser_id());
            apiData.setType_desc(userSession.getUser().getType_desc());
            apiData.setType_id(userSession.getUser().getType_id());
            apiData.setBank_code(userSession.getUser().getBankCode());

            String action = request.getParameterMap().containsKey("action") ? request.getParameter("action") : "";

            if (!action.isEmpty()) {
                apiData.setService(action);
            }
            log.info(new Gson().toJson(apiData));

            Boolean allowed = (!apiData.getType_id().isEmpty() && (apiData.getType_id().contains("[0]") || (apiData.getType_id().contains("[3]") && !utility.getRoleParameters("[2000000000000054]", apiData.getUserCode()).isEmpty())));
            if (allowed) {
                if (apiData.getService().equalsIgnoreCase("transactions")) {

                    log.info(apiData);

                    List<JustPayTransaction> records = new JustpayDao().getJustPayTransactions(new Gson().toJson(apiData));
                    if (records != null && records.size() > 0) {
                        out.println(new Gson().toJson(new JsonResponse("00", "success", new Gson().toJson(records))));
                    } else {
                        out.println(new Gson().toJson(new JsonResponse("01", "No Data Found")));
                    }

                } else if (apiData.getService().equalsIgnoreCase("search")) {

                    List<JustPayTransaction> records = new JustpayDao().getJustPayTransactions(new Gson().toJson(apiData));

                    if (records != null && records.size() > 0) {
                        out.println(new Gson().toJson(new JsonResponse("00", "success", new Gson().toJson(records))));
                    } else {
                        out.println(new Gson().toJson(new JsonResponse("01", "No Data Found")));
                    }

                } else if (apiData.getService().equalsIgnoreCase("merchants")) {
                    log.info(apiData);

                    List<NODES> records = new JustpayDao().getMerchants(new Gson().toJson(apiData));

                    out.println(new Gson().toJson(records));

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
