package com.etzgh.xportal.controller;

import com.etzgh.xportal.cdi.UserSession;
import com.etzgh.xportal.dao.AppDao;
import com.etzgh.xportal.dao.BankCollectionDao;
import com.etzgh.xportal.model.ApiPostData;
import com.etzgh.xportal.model.Bank;
import com.etzgh.xportal.model.EcgCollection;
import com.etzgh.xportal.model.JsonResponse;
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
public class BankCollectionsProxy extends HttpServlet {

    private static final long serialVersionUID = -5918577684178283996L;

    private static final Logger log = Logger.getLogger(BankCollectionsProxy.class.getName());
    final static AppDao ad = new AppDao();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserSession userSession;
        userSession = (UserSession) request.getSession().getAttribute("userSession");
        response.setContentType("application/json");

        try (PrintWriter out = response.getWriter()) {

            ApiPostData apiData = new ApiPostData();

            apiData.setStartDate(request.getParameter("startDate"));
            apiData.setEndDate(request.getParameter("endDate"));
            apiData.setUniqueTransId(request.getParameter("uniqueTransId"));
            apiData.setService(request.getParameter("service"));
            apiData.setSubscriberId(request.getParameter("from_source"));
            apiData.setUserCode(userSession.getUser().getUser_code());
            apiData.setUser_id(userSession.getUser().getUser_id());
            apiData.setType_id(userSession.getUser().getType_id());

            apiData.setMerchant(request.getParameter("merchant"));
            apiData.setBank_code(request.getParameter("bank_code"));
            apiData.setCardSchemeNumbers(userSession.getUser().getCardSchemeNumbers());
            System.out.println(new Gson().toJson(apiData));

            String action = request.getParameterMap().containsKey("action") ? request.getParameter("action") : "";

            if (!action.isEmpty()) {
                apiData.setService(action);
            }

            if (apiData.getService().equalsIgnoreCase("transactions") || apiData.getService().equalsIgnoreCase("search")) {

                List<EcgCollection> records = new BankCollectionDao().getBankCollections(new Gson().toJson(apiData));

                if (records != null && records.size() > 0) {
                    out.println(new Gson().toJson(new JsonResponse("00", "success", new Gson().toJson(records))));
                } else {
                    out.println(new Gson().toJson(new JsonResponse("01", "No Data Found")));
                }

            } else if (apiData.getService().equalsIgnoreCase("banks")) {

                List<Bank> records = ad.getBanks();

                out.println(new Gson().toJson(records));
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
