package com.etzgh.xportal.utility;

import org.apache.log4j.Logger;
import org.json.JSONObject;

/**
 *
 * @author sunkwa-arthur
 */
public class VerifyNumber {

    final Config props = new Config();
    private String verifyPhoneURL = "";
    private static final Logger log = Logger.getLogger(VerifyNumber.class.getName());

    public String verifyPhone(String number) {
        String verifyResponse = "";
        String phone = "";
        JSONObject response = new JSONObject();
        phone = new FormatPhoneNumber().formatNumber(number);
        if (!phone.equalsIgnoreCase("null")) {
            try {
                verifyPhoneURL = props.getProperty("verifyPhoneUrl");

                String jsonRequest = new JSONObject().put("number", number).toString();
                verifyResponse = new DoHTTPRequest().post(verifyPhoneURL, jsonRequest);

            } catch (Exception ex) {
                log.info("NUMBER LOOKUP ERROR ::: " + ex);
                response.put("response", "error");
                verifyResponse = response.toString();
            }
        }

        return verifyResponse;
    }
}
