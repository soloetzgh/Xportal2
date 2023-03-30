package com.etzgh.xportal.controller;

import com.etzgh.xportal.cdi.UserSession;
import com.etzgh.xportal.dao.AppDao;
import com.etzgh.xportal.dao.ConsoleFilesDao;
import com.etzgh.xportal.model.ApiPostData;
import com.etzgh.xportal.model.Bank;
import com.etzgh.xportal.model.ConsoleFile;
import com.etzgh.xportal.model.JsonResponse;
import com.etzgh.xportal.utility.CryptographerMin;
import com.etzgh.xportal.utility.GeneralUtility;
import com.etzgh.xportal.utility.HashNumber;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author sunkwa-arthur
 */
@MultipartConfig
public class ConsoleFilesProxy extends HttpServlet {

    private static List<Object[]> merchantrecords;

    private static final GeneralUtility utility = new GeneralUtility();
    private static final AppDao ad = new AppDao();
    private static final Logger log = Logger.getLogger(ConsoleFilesProxy.class.getName());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserSession userSession;
        userSession = (UserSession) request.getSession().getAttribute("userSession");
        response.setContentType("application/json");

        try (OutputStream out = response.getOutputStream()) {

            ApiPostData apiData = new ApiPostData();
            apiData.setStartDate(request.getParameter("startDate"));
            apiData.setEndDate(request.getParameter("endDate"));
            apiData.setBank_code(request.getParameter("bank"));

            apiData.setUserCode(userSession.getUser().getUser_code());
            apiData.setUserName(userSession.getUser().getUsername());
            apiData.setIpAddress((String) request.getAttribute("ipAddress"));
            apiData.setUser_id(userSession.getUser().getUser_id());
            apiData.setType_id(userSession.getUser().getType_id());

            apiData.setService(request.getParameter("service"));
            String action = request.getParameterMap().containsKey("action") ? request.getParameter("action") : "";

            if (!action.isEmpty()) {
                apiData.setService(action);
            }

            Boolean allowed = (!apiData.getType_id().isEmpty() && (apiData.getType_id().contains("[0]")
                    || (apiData.getType_id().contains("[53]") && !utility.getRoleParameters("[123]", apiData.getUserCode()).isEmpty())
                    || (apiData.getType_id().contains("[6]") && !utility.getRoleParameters("[2000000000000060]", apiData.getUserCode()).isEmpty())));

            if (allowed) {

                String bankCode = "000";
                if (apiData.getType_id().contains("[0]")) {
                } else if (apiData.getType_id().contains("[6]")) {
                    bankCode = utility.getRoleParameters("[2000000000000060]", apiData.getUserCode());
                    apiData.setBank_code(bankCode);
                } else if (apiData.getType_id().contains("[53]")) {

                    bankCode = utility.getRoleParameters("[123]", apiData.getUserCode());
                    apiData.setBank_code(bankCode);
                }

                if (apiData.getService().equalsIgnoreCase("transactions")) {

                    List<ConsoleFile> records = new ConsoleFilesDao().getConsoleFiles(new Gson().toJson(apiData));

                    if (records != null && records.size() > 0) {
                        response.getOutputStream().println(new Gson().toJson(new JsonResponse("00", "success", new Gson().toJson(records))));
                    } else {
                        response.getOutputStream().println(new Gson().toJson(new JsonResponse("01", "No Data Found")));
                    }
                } else if (apiData.getService().equalsIgnoreCase("downloadFile")) {
                    String filePath = request.getParameterMap().containsKey("fileId") ? request.getParameter("fileId") : "";

                    try {
                        filePath = CryptographerMin.decodeDecryptToString(filePath);

                        File file = new File(filePath);

                        if (filePath.trim().isEmpty() || !file.exists()) {
                            response.getOutputStream().println(new Gson().toJson(new JsonResponse("01", "An error occured. File not found")));
                        } else {
                            String filename = file.getName();
                            response.setContentType("application/octet-stream");
                            response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");

                            try (FileInputStream in = new FileInputStream(file)) {
                                byte[] buffer = new byte[4096];
                                int length;
                                while ((length = in.read(buffer)) > 0) {
                                    out.write(buffer, 0, length);
                                }
                            }
                            out.flush();
                        }
                    } catch (Exception hf) {
                        log.error("", hf);
                        response.getOutputStream().println(new Gson().toJson(new JsonResponse("01", "An error occured")));
                    }
                } else if (apiData.getService().equalsIgnoreCase("uploadFile")) {

                    String[] resp = {"01", "Authorization Required"};
                    if (!utility.getRoleParameters("[124]", apiData.getUserCode()).isEmpty()) {
                        resp = new ConsoleFilesDao().saveConsoleFiles(new Gson().toJson(apiData), request.getPart("file"));
                    }
                    response.getOutputStream().println(new Gson().toJson(new JsonResponse(resp[0], resp[1])));
                } else if (apiData.getService().equalsIgnoreCase("banks")) {

                    List<Bank> records = bankCode.equals("000") ? ad.getBanks() : new ArrayList<>();

                    response.getOutputStream().println(new Gson().toJson(records));
                } else {
                    response.getOutputStream().println(new Gson().toJson(new JsonResponse("01", "Invalid Service")));
                }
            } else {
                response.getOutputStream().println(new Gson().toJson(new JsonResponse("01", "Authorization Required")));
            }

        }
    }

    protected String mapChannel(String channel) {
        channel = "" + channel;

        switch (channel) {
            case "01":
                return "WEBCONNECT";
            case "03":
                return "POS ";
            case "04":
                return "ATM";
            case "02":
                return "MOBILE";
            case "05":
                return "PAYOUTLET";
            case "07":
                return "JUSTPAY";
            case "08":
                return "CORPORATEPAY";
            case "09":
                return "FUNDGATE";
            default:
                return "NULL";
        }
    }

    protected String mapClientId(String client) {
        client = "" + client;
        if (client.equals("null") || client.equals("")) {
            return "NO RESP";
        }
        return client;
    }

    protected String mapNarration(String n) {
        n = "" + n;
        if (n.equals("null") || n.equals("")) {
            return "NO RESP";
        }
        return n;
    }

    protected String hashAccountNumber(String accNo) {
        int start = accNo.length() / 2;
        HashNumber hn = new HashNumber();

        return hn.hashStringValue(accNo, start / 2, start / 2);
    }

    public String mapSwitchCode(String code) {
        code = "" + code;
        if (code.equals("null") || code.equals("")) {
            return "";
        }

        return code;
    }

    protected String mapFlag(String flag) {
        flag = "" + flag;
        switch (flag) {
            case "0":
                return "COMPLETED";
            case "null":
            case "":
                return " ";
            case "7":
                return "REVERSING ";
            case "8":
                return "REVERSED ";
            default:
                return "PENDING";
        }

    }

    protected String mapBankCode(String bank) {
        if (!bank.equals("")) {
            return bank.substring(0, 3);
        } else {
            return "n/a";
        }
    }

    protected String mapMerchant(String cardno) {
        for (Object[] obj : merchantrecords) {
            if (obj[0].toString().trim().equals(cardno.trim())) {
                return obj[1].toString();
            }
        }
        return cardno;
    }

    protected Boolean checkMerchant(String cardno) {
        Boolean result = false;
        try {
            result = merchantrecords.stream().anyMatch((obj) -> (obj[0].toString().trim().equals(cardno.trim())));
        } catch (Exception e) {
            result = false;
            System.out.println("Momo Error ::: " + e);
        }
        return result;
    }

    protected String mapBank(String bank) {
        if (checkMerchant(bank)) {
            return mapMerchant(bank);
        }
        switch (bank.substring(0, 3)) {
            case "017":
                return "FIRST ATLANTIC BANK";
            case "006":
                return "UNITED BANK OF AFRICA ";
            case "004":
                return "GHANA COMMERCIAL BANK ";
            case "905":
                return "BEST POINT ";
            case "029":
                return "ENERGY BANK ";
            case "012":
                return "ZENITH BANK ";
            case "003":
                return "BARCLAYS BANK ";
            case "021":
                return "BOA BANK ";
            default:
                return "n/a";
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
