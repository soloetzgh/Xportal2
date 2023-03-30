package com.etzgh.xportal.utility;

import com.etzgh.xportal.controller.PortalSettings;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;

public class MessageAlert {

    final static Config config = new Config();

    private static String SMS_GATEWAY_URL = "";
    private static final String FROM = config.getProperty("SENDER_ID");
    private static String TERMINAL_ID = "";
    private static final Logger log = Logger.getLogger(MessageAlert.class.getName());
    private static final PortalSettings portalSettings = new PortalSettings();

    static {
        SMS_GATEWAY_URL = StringUtils.substringBeforeLast(portalSettings.getSetting("sms_url"), ":");
        TERMINAL_ID = StringUtils.substringBeforeLast(portalSettings.getSetting("sms_terminal"), ":");
    }

    public String sendSMS(String to, String text, String header) {

        JSONObject req = new JSONObject();
        String resp = "";
        if (!(to.length() < 10 && to.length() > 12)) {
            req.put("from", FROM);
            req.put("to", to);
            req.put("terminalid", TERMINAL_ID);
            req.put("text", text);
            req.put("type", "0");

            resp = post(SMS_GATEWAY_URL, req.toString());
        } else {
            log.info("Invalid Number");
            resp = "Invalid";
        }
        log.info("RESPONSE ::: " + resp);

        return resp;
    }

    public String checkStatus(String reference) {
        String response = get(SMS_GATEWAY_URL + "/status/reference/" + reference);
        log.info("RESPONSE ::: " + response);
        return response;

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
        StringBuffer response = new StringBuffer();

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

            log.info("Response Code : " + responseCode);

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

}
