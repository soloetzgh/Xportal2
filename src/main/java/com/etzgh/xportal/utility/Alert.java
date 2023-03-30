/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etzgh.xportal.utility;

import com.etzgh.xportal.controller.PortalSettings;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.log4j.Logger;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;

/**
 *
 * @author sunkwa-arthur
 */
public class Alert {

    private static final PortalSettings portalSettings = new PortalSettings();
    private static final Logger log = Logger.getLogger(Alert.class.getName());
    private static final DoHTTPRequest doHttpRequest = new DoHTTPRequest();
    private static final Config config = new Config();
    private static String appPath = config.getProperty("APPLICATION_API_PATH");

    public String sendSMS(String to, String text, String header) {
        String TERMINAL_ID = StringUtils.substringBeforeLast(portalSettings.getSetting("sms_terminal"), ":");
        String SMS_GATEWAY_URL = StringUtils.substringBeforeLast(portalSettings.getSetting("sms_url"), ":");
        // String FROM =
        // StringUtils.substringBeforeLast(portalSettings.getSetting("sms_senderid"),
        // ":");

        JSONObject req = new JSONObject();
        String resp = "";
        if (!(to.length() < 10 && to.length() > 12)) {
            req.put("from", header);
            req.put("to", to);

            req.put("terminalid", TERMINAL_ID);

            req.put("text", text);
            req.put("type", "0");

            resp = post(SMS_GATEWAY_URL, req.toString());
        } else {
            log.info("Invalid Number");
            resp = "Invalid";
        }

        return resp;
    }

    public static String get(String URL) {
        StringBuilder response = new StringBuilder();

        try {
            URL url = new URL(URL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);
            int responseCode = con.getResponseCode();

            log.info("Response Code : " + responseCode);

            try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
            }

            return response.toString();

        } catch (Exception ex) {
            log.info(ex.getMessage());
        }
        return response.toString();
    }

    public static boolean doGet(String url) throws Exception {
        boolean success = false;
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");

        int responseCode = con.getResponseCode();
        BufferedReader in = null;
        if (responseCode == 200) {
            in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            success = true;
        } else {
            in = new BufferedReader(
                    new InputStreamReader(con.getErrorStream()));
        }

        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return success;
    }

    public String post(String URL, String jsonData) {
        StringBuilder response = new StringBuilder();
        try {
            URL url = new URL(URL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("POST");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);

            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.writeBytes(jsonData);
                wr.flush();
            }

            int responseCode = con.getResponseCode();

            log.info("Response Code : {0}" + responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return response.toString();

        } catch (Exception ex) {
            log.info(ex.getMessage());
        }
        return response.toString();
    }

    public static void main(String[] args) {
        new Alert().processEmail("eugenearthur53@gmail.com", "Eugene.Arthur", "weret", "Eugene", "reset", "");
    }

    public void processEmail(final String destinationMail, final String username, final String password, String name,
            String type, String endpoint) {
        try {
            String path = portalSettings.getSetting("email_server");
            String email_server = path.substring(0, path.lastIndexOf(":"));
            final JSONObject email = new JSONObject();
            final JSONObject credentials = new JSONObject();
            credentials.put("name", (Object) name);
            credentials.put("username", (Object) username);
            credentials.put("password", (Object) password);
            credentials.put("endpoint", endpoint + "/" + appPath + "/");
            String subject = "";
            String htmlMsg = "";
            switch (type) {
                case "reset":
                    subject = "XPORTAL PASSWORD RESET";
                    htmlMsg = "xportalpasswordreset";
                    break;
                case "otp":
                    subject = "XPORTAL OTP";
                    htmlMsg = "xportalotp";
                    break;
                case "setup":
                    subject = "XPORTAL ACCOUNT";
                    htmlMsg = "xportalaccount";
                    break;
                default:
                    break;
            }
            email.put("subject", (Object) subject);
            email.put("sender", (Object) "no-reply@etranzact.com.gh");
            email.put("recepient", (Object) destinationMail);
            email.put("credentials", (Object) credentials.toString());
            email.put("htmlMessage", (Object) htmlMsg);
            email.put("message", (Object) "Email");

            doHttpRequest.post(email_server, email.toString());
        } catch (Exception et) {
            log.info("ERROR: {0}", et);
        }
    }
}
