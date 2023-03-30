/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etzgh.xportal.utility;

import com.etzgh.xportal.controller.PortalSettings;
import com.etzgh.xportal.model.User;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.apache.log4j.Logger;

/**
 *
 * @author sunkwa-arthur
 */
public class GeneralUtility {

    //CMMENT
    private static final Config config = new Config();
    private static final String appPath;
    private static final int appPort;
    private static final PortalSettings portalSettings = new PortalSettings();

    private static final Logger log = Logger.getLogger(GeneralUtility.class.getName());

    static {
        appPort = Integer.valueOf(config.getProperty("APPLICATION_PORT"));
        appPath = config.getProperty("APPLICATION_API_PATH");
    }

    public static void main(String[] args) {
        String req = "{\"merchant\":\"ALL\",\"product\":\"\",\"service\":\"channels\",\"action\":\"\",\"apiSecret\":\"\",\"apiCode\":\"\",\"userCode\":\"[2000000000000060]|021,[2500000000000050]|58,[2500000000000056]|3,[2500000000000057],[2500000000000058]|3,[2500000000000059]|3,[51]|2\",\"admin\":\"\",\"type_desc\":\"\",\"role_id\":\"\",\"type_id\":\"[4],[5],[6],[7],[20],[24],[39],[40],[54],[55],[57],[58],[59],[60],[61],[62],[66]\",\"userName\":\"\",\"ClientId\":\"\",\"user_id\":\"10500000000000796\",\"pageNumber\":1,\"rowsPerPage\":1,\"trans_code\":\"\",\"transType\":\"\",\"bank_code\":\"000\",\"subscriberId\":\"\",\"trans_status\":\"\",\"roleList\":[],\"bank\":\"\",\"biller\":\"\",\"transId\":\"\",\"bankid\":\"\",\"from_source\":\"\",\"card_num\":\"\",\"optionType\":\"\",\"payType\":\"\",\"wcMerchant\":\"\",\"destination\":\"\",\"lineType\":\"\",\"trans_channel\":\"\",\"pan\":\"\",\"terminal_id\":\"\",\"card_account\":\"\",\"cop_issuercode\":\"\",\"cop_role_id\":\"\",\"cop_company_id\":\"\",\"mobile_no\":\"\",\"phone\":\"\",\"type\":\"\",\"amount\":\"\",\"amount2\":\"\",\"refundStatus\":\"\",\"partialReversal\":\"\",\"reversalReason\":\"\",\"ipAddress\":\"192.168.5.25\",\"userData\":\"\",\"appId\":[],\"company\":\"\",\"origin\":\"\",\"original_respcode\":\"\",\"updated_respcode\":\"\",\"initiated_by\":\"\",\"initiated_date\":\"\",\"authorised_by\":\"\",\"authorised_date\":\"\",\"originalReference\":\"\",\"client\":\"\",\"vastype\":\"\",\"account\":\"\",\"data\":\"\",\"otherInfo\":\"\",\"branch\":\"\",\"ticketNumber\":\"\",\"nlaReference\":\"\",\"etzReference\":\"\",\"newCustomerId\":\"\",\"options\":{}}";
        System.out.println("CHECK: " + new GeneralUtility().getRoleParameters("[2500000000000055]","[1],[17]|0060000290:0067620000000010,[2000000000000048],[2000000000000049]|SCB:6AGAS,[2000000000000053]|DSTV,[2000000000000054]|ADSL~TELESOL,[2000000000000060]|000,[2000000000000062]|ALL,[2000000000000063]|KNUST,[2000000000000064]|ALL,[22],[2500000000000049]|2,[2500000000000050]|ALL,[2500000000000053]|1,[2500000000000055]|0503737419:PC,[2500000000000058]|1,[2500000000000059]|1,[2500000000000061]|1,[29],[50]|2,[51]|2,[71]|0230010002,[91]|ALL"));
    }

    public String getRoleParameters(String role, String rolesList) {
        String roleParam = "";
        if (!rolesList.isEmpty()) {
            try {
                int firstParam = rolesList.indexOf(role);
                if (firstParam > -1) {
                    int nextParam = rolesList.indexOf(",", firstParam);
                    roleParam = rolesList.substring(firstParam, (nextParam == -1) ? rolesList.length() : nextParam)
                            .split("[|]",-1)[1];
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
        return roleParam;
    }

    public static BigDecimal stripDecimalZeros(BigDecimal value) {
        if (value == null) {
            return null;
        }

        BigDecimal striped = (value.scale() > 0) ? value.stripTrailingZeros() : value;

        return (striped.scale() < 0) ? striped.setScale(0) : striped;
    }

    public static Map<String, String> getRequestHeadersInfo(HttpServletRequest request) {

        Map<String, String> map = new HashMap<>();

        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }

        return map;
    }

    public static Map<String, String> getResponseHeadersInfo(HttpServletResponse response) {

        Map<String, String> map = new HashMap<>();

        Collection<String> headerNames = response.getHeaderNames();
        for (String header : headerNames) {
            String value = response.getHeader(header);
            map.put(header, value);
        }

        return map;
    }

    public static JSONObject checkOrigin(User user, String requestOrigin) {
        JSONObject allowed = new JSONObject();
        allowed.put("code", "00");
        String userCode = "";
        String typeId = "";
        if (user != null) {
            userCode = user.getUser_code();
            typeId = user.getType_id();
        }

        if (requestOrigin == null) {
            allowed.put("code", "01");
            allowed.put("message", "You are not allowed to access this url");

        } else if (typeId == null || userCode == null) {
        } else if (typeId.contains("[6]")) {
            String bankCode = new GeneralUtility().getRoleParameters("[2000000000000060]", userCode);
            String bankOrigin = StringUtils.substringBeforeLast(portalSettings.getSetting("bank_origin"), ":");

            String ip = requestOrigin.split("//")[0];

            if (bankCode.isEmpty()) {
                allowed.put("code", "01");
                allowed.put("message",
                        "You are not allowed to access this portal. Kindly access the portal using  http://"
                        + bankOrigin + "/" + appPath);
            } else {
                List<String> bankList = Arrays.asList(StringUtils
                        .substringBeforeLast(portalSettings.getSetting("strict_bank_origin"), ":").split(","));

                if (!bankOrigin.equals(ip) && bankList.contains(bankCode)) {
                    allowed.put("code", "01");
                    allowed.put("message",
                            "You are not allowed to access this portal. Kindly access the portal using  http://"
                            + bankOrigin + "/" + appPath);
                }
            }
        }
        return allowed;
    }
}
