/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etzgh.xportal.utility;

import com.etz.http.etc.Card;
import com.etz.http.etc.HttpHost;
import com.etz.http.etc.TransCode;
import com.etz.http.etc.XProcessor;
import com.etz.http.etc.XRequest;
import com.etz.http.etc.XResponse;
import com.etzgh.xportal.controller.PortalSettings;
import com.google.gson.Gson;
import java.util.Map;
import org.apache.log4j.Logger;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author samuel.onwona
 */
public class autoSwitchCall {

    private static final Logger log = Logger.getLogger(autoSwitchCall.class.getName());
    private static String ipAddress = "";
    private static int port = 0;
    private static String key = "";
    private static final PortalSettings portalSettings = new PortalSettings();
    private static final String ERROR = "ERROR";

    static {

        ipAddress = StringUtils.substringBeforeLast(portalSettings.getSetting("autoswitch_ip"), ":");
        port = Integer.parseInt(StringUtils.substringBeforeLast(portalSettings.getSetting("autoswitch_port"), ":"));
        key = StringUtils.substringBeforeLast(portalSettings.getSetting("autoswitch_key"), ":");

    }

    public static void main(String[] args) {
        log.info(new autoSwitchCall().autoRequest("09FG10061954523343551"));
    }

    public String autoRequest(String uniqueTransId) {
        String returnValue = "";
        int ret = -1;
        try {

            XProcessor processor = new XProcessor();
            XRequest request = new XRequest();
            HttpHost host = new HttpHost();

            log.info(String.format("Auto: {0} : {1} : {2}", new Object[]{ipAddress, key, port}));
            host.setServerAddress(ipAddress);
            host.setPort(port);
            host.setSecureKey(key);
//	      host.setSecureKey;

            Card card = new Card();
            card.setCardNumber("999ABC123");
            String expiryDate = "000000";
            card.setCardExpiration(expiryDate);
            request.setXmlString("<CBARequest>StatusQuery-" + uniqueTransId + "</CBARequest>");//custom request

            request.setCard(card);
            request.setReference(uniqueTransId);
            request.setTransAmount(0);
            request.setFee(0);
            request.setTransCode(TransCode.BANKSERVICE);
            Gson gson = new Gson();
            XResponse response = null;
            try {
                response = processor.process(host, request);
                if (response.getResponse() == 0) {
//                log.info("RESPONSE OBJECT::" + gson.toJson(response));
//                log.info("Custom:" + response.getCustomXml());
//                    ret = response.getResponse();

                    returnValue = response.getCustomXml();
                    Map jmap = (Map) gson.fromJson(response.getCustomXml(), Map.class);
                    String trackingNo = (String) jmap.get("TrackingNum");
                    log.info("Tracking No:: {0}" + trackingNo);

                } else {
                    returnValue = response.getCustomXml();
                }
                return returnValue;
            } catch (Exception e) {
                ret = -1;
                returnValue = ret + "==" + "null";
                log.error(ERROR, e);
            }
        } catch (Exception except) {
            log.info("XPORTAL AUTOSWITCH >>> {0}", except);
        }
        return returnValue;
    }

    public String[] statusCheck(String ref, String cardNumber, String channel) {
        String[] res = new String[3];
//        res[2] = id;
        try {
//            props.load(new FileInputStream(new File("C:\\cfg\\merchantpay.properties")));
//            String source = config.getProperty("CardNumber");
            String pin = "0000";
            String expiry = "00000";
//            log.info("CardPin:"+ pin);
            String reference = ref;
            Card card = new Card();
            card.setCardNumber(cardNumber);
            card.setCardExpiration(expiry);
//            card.setAccountType("CA");
            card.setCardPin(pin);

            XRequest request = new XRequest();
            HttpHost host = new HttpHost();
            request.setCard(card);
            request.setTransCode(TransCode.BANKSERVICE);
            request.setReference(reference);
            request.setXmlString("<CBARequest>SC:" + reference + "</CBARequest>");
//            request.setOtherReference("EUGENETEST001");
            request.setDescription("Status Check for " + reference);
            request.setChannelId(channel);
            request.setCurrency("566");

//            log.info("AutoSwitch R: ");
            XProcessor processor = new XProcessor();
            XResponse xResponse = processor.process(host, request);

//                log.info("AutoSwitch XProcessor " + (xResponse == null ? "" : xResponse.toString()));
            if (xResponse != null) {
                log.info("AutoSwitch Response Code: {0}" + xResponse.getResponse());
                log.info("Transaction Code: {0}" + xResponse.getCustomXml());
                log.info("Transaction Code: " + xResponse.getCustomXml());
                if (xResponse.getResponse() != 0) {
                    res[0] = ("" + xResponse.getResponse());
                    res[1] = (reference);
                    res[2] = xResponse.getCustomXml();
//            res[1] = (getErrorDesc(new StringBuilder().append("").append(xResponse.getResponse()).toString()) + " Ref:" + id);
                } else if (xResponse.getResponse() == 0) {
                    res[0] = ("" + xResponse.getResponse());
                    res[1] = (reference);
                    res[2] = xResponse.getCustomXml();
//            res[1] = (getErrorDesc(new StringBuilder().append("").append(xResponse.getResponse()).toString()) + " Ref:" + id);
                }
            } else {
                res[0] = "-1";
                res[1] = (reference);
            }

        } catch (Exception ef) {
            log.error(ERROR, ef);
        }
        return res;
    }

    public String getAPL(String phone) {
        String returnValue = "";

        String expiryDate = "0000";

        XProcessor processor = new XProcessor();

        HttpHost host = new HttpHost();
        host.setServerAddress(ipAddress);
        host.setPort(port);
        host.setSecureKey(key);

        Card card = new Card();
//        card.setCardNumber(String.format("021%s", accountNumber));
        card.setCardNumber("0050010000000161");

        card.setCardExpiration(expiryDate);

        XRequest request = new XRequest();
        request.setXmlString("<CBARequest>APL:" + phone + "</CBARequest>");
        request.setCard(card);
        request.setTransCode(TransCode.BANKSERVICE);

        XResponse response = null;
        try {
            response = processor.process(host, request);

            log.info("Response: {0}" + response.getResponse());
            log.info("Message: {0}" + response.getMessage());
            log.info("Custom XML: {0}" + response.getCustomXml());
            log.info("Reference: {0}" + response.getReference());

            if (response.getResponse() == 0) {
                returnValue = response.getCustomXml();
            }
        } catch (Exception e) {
            log.error(ERROR, e);
        }

        return returnValue;
    }

}
