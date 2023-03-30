package com.etzgh.xportal.utility;

import java.net.URLEncoder;
import org.apache.log4j.Logger;

public class OnboardingUtility {

    final static String SMS_GATEWAY_URL = "http://";

    private static final Logger log = Logger.getLogger(OnboardingUtility.class.getName());

    public static void main(String[] args) {
        log.info(new OnboardingUtility().cryptAccountNumber("5011010213216"));
    }

    public String cryptPan(String pan, int encType) {
        String cryptedPan = "";
        int subIndex = 6;
        if ((pan.length() == 19) || (pan.length() == 25)) {
            subIndex = 9;
        }
        Cryptographer crypt = new Cryptographer();
        byte[] epinblock = null;
        String mmk = "01010101010101010101010101010101";
        if (encType == 1) {
            String padValue = "FFFFFF" + pan.substring(subIndex);
            try {
                crypt.getClass();
                epinblock = crypt.doCryto(padValue, mmk, 1);
                cryptedPan = pan.substring(0, subIndex) + Cryptographer.byte2hex(epinblock);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        } else {
            try {
                crypt.getClass();
                epinblock = crypt.doCryto(pan.substring(subIndex), mmk, 2);
                String decPan = Cryptographer.byte2hex(epinblock).substring(6);
                if (decPan.startsWith("FFFFFF")) {
                    decPan = decPan.substring(6);
                }
                cryptedPan = pan.substring(0, subIndex) + decPan;
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }

        }

        return cryptedPan;
    }

    /*This method takes the account number, shows the first 3, hash the next five and show the next*/
    public String cryptAccountNumber(String value) {

        String message = "";

        try {
            message = value.substring(0, 3) + "-----" + value.substring(8);
        } catch (Exception ex) {
            message = value;
            log.error(ex.getMessage(), ex);
        }
        return "GCB-" + message;
    }

    public String retrievePIN(String defaultPIN, String crdNum) {
        String pin = "";
        log.info("Retrieving PIN");
        log.info("defaultPIN: " + defaultPIN);
        log.info("CardNum: " + crdNum);
        try {
            String pp = new ecard.E_SECURE().f_decode(defaultPIN, crdNum);
            if (pp == null || pp.equalsIgnoreCase("null")) {
                pp = new ecard.E_SECURE().f_decode(defaultPIN);
            }
            if (pp != null) {
                pin = pp;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return pin;
    }

    public int sendSMS(String username, String password, String from, String to, String text, String terminalid, String id) {
        int resp = -1;
        try {
            String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
            data = data + "&password=" + URLEncoder.encode(password, "UTF-8");
            data = data + "&" + URLEncoder.encode("from", "UTF-8") + "=" + URLEncoder.encode(from, "UTF-8");
            data = data + "&" + URLEncoder.encode("to", "UTF-8") + "=" + URLEncoder.encode(to, "UTF-8");
            data = data + "&" + URLEncoder.encode("text", "UTF-8") + "=" + URLEncoder.encode(text, "UTF-8");
            data = data + "&" + URLEncoder.encode("terminalid", "UTF-8") + "=" + URLEncoder.encode(terminalid, "UTF-8");
            data = data + "&" + URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");
            log.info("SMS DATA >> " + data);
            resp = new DoHTTPRequest().get(SMS_GATEWAY_URL + "?" + data);

            log.info(" DBPINMailer >>>>>> " + resp);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            resp = -1;
        }
        return resp;
    }

}
