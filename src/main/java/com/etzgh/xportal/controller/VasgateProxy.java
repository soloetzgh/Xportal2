package com.etzgh.xportal.controller;

import com.etzgh.xportal.cdi.UserSession;
import com.etzgh.xportal.dao.AppDao;
import com.etzgh.xportal.dao.MomoDao;
import com.etzgh.xportal.dao.VasBillDao;
import com.etzgh.xportal.model.ApiPostData;
import com.etzgh.xportal.model.Bank;
import com.etzgh.xportal.model.Channel;
import com.etzgh.xportal.model.JsonResponse;
import com.etzgh.xportal.model.MobileMoney;
import com.etzgh.xportal.model.NODES;
import com.etzgh.xportal.model.VasBillTransaction;
import com.etzgh.xportal.utility.GeneralUtility;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
 * @author sunkwa-arthur
 */
public class VasgateProxy extends HttpServlet {

    final static GeneralUtility utility = new GeneralUtility();

    final static AppDao ad = new AppDao();

    private static final Logger log = Logger.getLogger(VasgateProxy.class.getName());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserSession userSession;
        userSession = (UserSession) request.getSession().getAttribute("userSession");
        response.setContentType("application/json");

        try (PrintWriter out = response.getWriter()) {

            ApiPostData apiData = new ApiPostData();
            apiData.setStartDate(request.getParameter("startDate"));
            apiData.setEndDate(request.getParameter("endDate"));
            apiData.setMerchant(request.getParameterMap().containsKey("merchant") ? request.getParameter("merchant") : "ALL");
            apiData.setAccountNumber(request.getParameter("custAccount"));
            apiData.setBank_code(request.getParameterMap().containsKey("bank") ? request.getParameter("bank") : "000");
            apiData.setChannel(request.getParameter("channel"));
            apiData.setUniqueTransId(request.getParameter("uniqueTransId"));
            apiData.setStatus(request.getParameter("status"));
            apiData.setService(request.getParameter("service"));
            apiData.setSearchKey(request.getParameter("searchKey"));
            apiData.setUserCode(userSession.getUser().getUser_code());
            apiData.setIpAddress((String) request.getAttribute("ipAddress"));
            apiData.setUser_id(userSession.getUser().getUser_id());
            apiData.setType_id(userSession.getUser().getType_id());

            apiData.setCardSchemeNumbers(userSession.getUser().getCardSchemeNumbers());
            String action = request.getParameterMap().containsKey("action") ? request.getParameter("action") : "";

            if (!action.isEmpty()) {
                apiData.setService(action);
            }

            log.info(new Gson().toJson(apiData));

            Boolean allowed = (!apiData.getType_id().isEmpty() && (apiData.getType_id().contains("[0]")
                    || (apiData.getType_id().contains("[4]") && !utility.getRoleParameters("[2000000000000053]", apiData.getUserCode()).isEmpty())
                    || (apiData.getType_id().contains("[6]") && !utility.getRoleParameters("[2000000000000060]", apiData.getUserCode()).isEmpty())));
            if (allowed) {

                String bankCode = "000";
                if (apiData.getType_id().contains("[0]")) {
                } else if (apiData.getType_id().contains("[6]")) {
                    bankCode = utility.getRoleParameters("[2000000000000060]", apiData.getUserCode());
                    if (!bankCode.equals("000")) {
                        apiData.setBank_code(bankCode);
                    }

                }
                if (apiData.getService().equalsIgnoreCase("transactions") || apiData.getService().equalsIgnoreCase("search")) {

                    List<VasBillTransaction> records = new VasBillDao().getVasBillTransactions(new Gson().toJson(apiData));

                    if (records != null && records.size() > 0) {

                        out.println(new Gson().toJson(new JsonResponse("00", "success", new Gson().toJson(records))));
                    } else {
                        out.println(new Gson().toJson(new JsonResponse("01", "No Data Found")));
                    }
                    records = null;
                } else if (apiData.getService().equalsIgnoreCase("merchants")) {
                    log.info(apiData);

                    List<NODES> records = new VasBillDao().getMerchants(new Gson().toJson(apiData));

                    out.println(new Gson().toJson(records));
                } else if (apiData.getService().equalsIgnoreCase("banks")) {
                    log.info(apiData);

                    List<Bank> records = bankCode.equals("000") ? ad.getBanks() : new ArrayList<>();

                    out.println(new Gson().toJson(records));
                    records = null;
                } else if (apiData.getService().equalsIgnoreCase("channels")) {
                    log.info(apiData);

                    List<Channel> records = ad.getChannels();

                    out.println(new Gson().toJson(records));
                    records = null;
                }else if (apiData.getService().equalsIgnoreCase("billStatusCheck")) {
                    if (allowed) {
                        List<VasBillTransaction> records = new VasBillDao().getBillStatusCheck(new Gson().toJson(apiData));

                        if (records != null && records.size() > 0) {
                            out.println(
                                    new Gson().toJson(new JsonResponse("00", "success", new Gson().toJson(records))));
                        } else {
                            out.println(new Gson().toJson(new JsonResponse("01", "No Data Found")));
                        }

                    } else {
                        out.println(new Gson().toJson(new JsonResponse("01", "Authorization Required")));
                    }

                }else if (apiData.getService().equalsIgnoreCase("airtimeStatusCheck")) {
                    if (allowed) {
                        List<VasBillTransaction> records = new VasBillDao().getAirtimeStatusCheck(new Gson().toJson(apiData));

                        if (records != null && records.size() > 0) {
                            out.println(
                                    new Gson().toJson(new JsonResponse("00", "success", new Gson().toJson(records))));
                        } else {
                            out.println(new Gson().toJson(new JsonResponse("01", "No Data Found")));
                        }

                    } else {
                        out.println(new Gson().toJson(new JsonResponse("01", "Authorization Required")));
                    }

                }else if (apiData.getService().equalsIgnoreCase("billandairtimeStatusCheck")) {
                    if (allowed) {
                        List<VasBillTransaction> records = new VasBillDao().getBillAndAirtimeStatusCheck(new Gson().toJson(apiData));

                        if (records != null && records.size() > 0) {
                            out.println(
                                    new Gson().toJson(new JsonResponse("00", "success", new Gson().toJson(records))));
                        } else {
                            out.println(new Gson().toJson(new JsonResponse("01", "No Data Found")));
                        }

                    } else {
                        out.println(new Gson().toJson(new JsonResponse("01", "Authorization Required")));
                    }

                }
            } else {
                out.println(new Gson().toJson(new JsonResponse("01", "Authorization Required")));
            }

        }
    }

    public Map<String, String> convertListAfterJava8(List<Bank> list) {
        Map<String, String> map = list.stream().collect(Collectors.toMap(Bank::getIssuer_code, bank -> bank.getIssuer_name()));
        return map;
    }

    protected Boolean CheckNo(String num) {

        if (num != null) {
            boolean numeric = false;
            numeric = num.matches("-?\\d+(\\.\\d+)?");
            return numeric;
        }
        return false;
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
