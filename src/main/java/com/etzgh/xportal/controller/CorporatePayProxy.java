package com.etzgh.xportal.controller;

import com.etzgh.xportal.cdi.UserSession;
import com.etzgh.xportal.dao.CorporatePayDao;
import com.etzgh.xportal.model.ApiPostData;
import com.etzgh.xportal.model.CorporatePay;
import com.etzgh.xportal.model.JsonResponse;
import com.etzgh.xportal.model.RoleOption;
import com.etzgh.xportal.utility.CryptographerMin;
import com.etzgh.xportal.utility.GeneralUtility;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class CorporatePayProxy extends HttpServlet {

    private static final Logger log = Logger.getLogger(CorporatePayProxy.class.getName());

    private static final GeneralUtility utility = new GeneralUtility();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserSession userSession;
        userSession = (UserSession) request.getSession().getAttribute("userSession");
        response.setContentType("application/json");

        try (PrintWriter out = response.getWriter()) {

            ApiPostData apiData = new ApiPostData();
            apiData.setStartDate(request.getParameter("startDate"));
            apiData.setEndDate(request.getParameter("endDate"));
            apiData.setMerchant(request.getParameter("merchant"));
            apiData.setUniqueTransId(request.getParameter("uniqueTransId"));
            apiData.setStatus(request.getParameter("status"));
            apiData.setService(request.getParameter("service"));

            apiData.setTransId(request.getParameter("paymentId"));
            apiData.setTrans_code(request.getParameter("batchId"));
            apiData.setSearchKey(request.getParameter("searchKey"));
            apiData.setUserCode(userSession.getUser().getUser_code());
            apiData.setIpAddress((String) request.getAttribute("ipAddress"));
            apiData.setUser_id(userSession.getUser().getUser_id());
            apiData.setCardSchemeNumbers(userSession.getUser().getCardSchemeNumbers());
            apiData.setType_id(userSession.getUser().getType_id());
            apiData.setBank_code(userSession.getUser().getBankCode());
            apiData.setCop_company_id(request.getParameter("companyId"));

            String action = request.getParameterMap().containsKey("action") ? request.getParameter("action") : "";

            if (!action.isEmpty()) {
                apiData.setService(action);
            }

            log.info(new Gson().toJson(apiData));

            Boolean allowed = (!apiData.getType_id().isEmpty() && (apiData.getType_id().contains("[0]") || (apiData.getType_id().contains("[28]") && !utility.getRoleParameters("[2000000000000048]", apiData.getUserCode()).isEmpty())));

            log.info("IS ALLOWD: {0}" + allowed);
            if (allowed) {
                if (apiData.getService().equalsIgnoreCase("transactions")) {

                    List<CorporatePay> records = new CorporatePayDao().getCorporatePayReport(new Gson().toJson(apiData));

                    if (records != null && records.size() > 0) {

                        String resp = new GsonBuilder().disableHtmlEscaping().create().toJson(records);

                        String mac = CryptographerMin.encryptEncodeToHex("report=corporatepay&action=pdf&title=CORPORATEPAY REPORT&startDate=" + apiData.getStartDate() + "&endDate=" + apiData.getEndDate(), resp);

                        response.addHeader("app-token", mac);

                        out.println(new Gson().toJson(new JsonResponse("00", "success", resp)));
                    } else {
                        out.println(new Gson().toJson(new JsonResponse("01", "No Data Found")));
                    }

                } else if (apiData.getService().equalsIgnoreCase("merchants")) {
                    List<RoleOption> merchants = new CorporatePayDao().getMerchants(new Gson().toJson(apiData));
                    out.println(new Gson().toJson(merchants));
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

    public String currencyFormat(BigDecimal n) {

        return NumberFormat.getNumberInstance().format(n);
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
