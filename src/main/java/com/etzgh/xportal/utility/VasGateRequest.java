package com.etzgh.xportal.utility;

import java.io.File;
import java.io.FileInputStream;
import org.apache.log4j.Logger;
import org.json.JSONObject;

/**
 *
 * @author sunkwa-arthur
 */
public class VasGateRequest {

    final Config props = new Config();
    JSONObject vasReq = new JSONObject();
    private static final Logger log = Logger.getLogger(VasGateRequest.class.getName());

    public String queryAccount(String merchant, String account) {

        log.info("Query: " + merchant + " " + account);
        JSONObject verifyResponse = new JSONObject();
        try {
            String env = props.getProperty("vasEnv");
            String vasgateURL = "";
            if (env.equals("production")) {
                vasgateURL = props.getProperty("produrl");
            } else {
                vasgateURL = props.getProperty("demourl");
            }

            if (!(merchant.isEmpty() || merchant.equals("")) && !(account.isEmpty() || account.equals(""))) {
                vasReq.put("apiId", "xportal");
                vasReq.put("apiSecret", "EAE87AA45B443279747E158C6FA5FD2C9DDD49B8BCB2726FEE89F76D679B88BD5599E3E59643EA233454C66");
                vasReq.put("product", merchant);
                vasReq.put("account", account);
                vasReq.put("amount", "1.0");
                vasReq.put("action", "query");
                vasReq.put("reference", new XRandom().generateUniqueId());
                log.info("VasRequest ::: " + vasReq.toString());

                JSONObject resp = new JSONObject(new DoHTTPRequest().post(vasgateURL, vasReq.toString()));
                log.info("RESPONSE ::: " + resp.toString());
                verifyResponse.put("response", "success");

                verifyResponse.put("vasresponse", resp.toString());

                verifyResponse.put("vasresponse", resp.toString());
            } else {
                verifyResponse.put("response", "error");
            }
        } catch (Exception ex) {
            log.info("VASGATE PROCESS ERROR ::: " + ex);
            verifyResponse.put("response", "error");
        }
        return verifyResponse.toString();
    }

    public String processAccount(String request) {

        log.info("Process: " + request);
        JSONObject verifyResponse = new JSONObject();
        try {
            String env = props.getProperty("vasEnv");
            String vasgateURL = "";
            if (env.equals("production")) {
                vasgateURL = props.getProperty("produrl");
            } else {
                vasgateURL = props.getProperty("demourl");
            }
            JSONObject processRequest = new JSONObject(request);

            if (processRequest.has("product") && processRequest.has("reference") && processRequest.has("amount") && processRequest.has("account")) {
                vasReq.put("apiId", "xportal");
                vasReq.put("apiSecret", "EAE87AA45B443279747E158C6FA5FD2C9DDD49B8BCB2726FEE89F76D679B88BD5599E3E59643EA233454C66");
                vasReq.put("product", processRequest.getString("product"));
                vasReq.put("account", processRequest.getString("account"));
                vasReq.put("amount", processRequest.getString("amount"));
                vasReq.put("action", "process");
                vasReq.put("reference", processRequest.getString("reference"));
                if (processRequest.has("otherinfo")) {
                    vasReq.put("otherInfo", processRequest.getString("otherinfo"));
                }
                log.info("VasRequest ::: " + vasReq.toString());

                JSONObject resp = new JSONObject(new DoHTTPRequest().post(vasgateURL, vasReq.toString()));
                log.info("RESPONSE ::: " + resp.toString());
                verifyResponse.put("response", "success");

                verifyResponse.put("vasresponse", resp.toString());

            } else {
                verifyResponse.put("response", "error");
            }
        } catch (Exception ex) {
            log.info("VASGATE PROCESS ERROR ::: " + ex);
            verifyResponse.put("response", "error");
        }
        return verifyResponse.toString();
    }

}
