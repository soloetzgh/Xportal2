/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etzgh.xportal.utility;

import com.etzgh.xportal.model.InfobibRequest;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author samuel.onwona
 */
public class InfoBib {

    /**
     * @param location
     * @return
     * @throws java.lang.Exception
     */
    private String sendMessageUrl = "";
    private String verifyPhoneURL = "";
    private String infobibAuth = "";
    private static final Config props = new Config();

    private static final Logger log = Logger.getLogger(InfoBib.class.getName());

    public String VerifyPhone(String Number) throws Exception {

        verifyPhoneURL = props.getProperty("verifyPhoneUrl");
        infobibAuth = "Basic " + props.getProperty("infobibAuth");
        InfobibRequest infobibreq = new InfobibRequest();

        String verifyResponse = null;
        try {

            infobibreq.setTo(Number);

            String jsonRequest = new Gson().toJson(infobibreq);

            verifyResponse = httpCon(jsonRequest, verifyPhoneURL);

        } catch (Exception e) {
            log.error("INFOBIB ERROR ::: ", e);

            verifyResponse = null;
        }
        return verifyResponse;
    }

    public int SendMsg(String number, String Message) throws IOException {
        String msgResponse = null;
        int result = 0;

        InfobibRequest infobibreq = new InfobibRequest();
        try {

            sendMessageUrl = props.getProperty("sendMessageUrl");
            infobibAuth = props.getProperty("infobibAuth");
            log.info("send: " + sendMessageUrl + " " + infobibAuth);
            infobibreq.setTo(number);
            infobibreq.setText(Message);
            infobibreq.setFrom("eTranzact");

            log.info("info: " + infobibreq.getFrom() + infobibreq.getText());
            String jsonRequest = new Gson().toJson(infobibreq);
            log.info("Json: " + jsonRequest);
            msgResponse = httpCon(jsonRequest, sendMessageUrl);

            JSONObject root = new JSONObject(msgResponse);
            JSONArray messagesArray = root.getJSONArray("messages");
            JSONObject messageDetails = messagesArray.getJSONObject(0);
            JSONObject singleDetail = messageDetails.getJSONObject("status");
            String msgResponse2 = singleDetail.getString("groupName");
            log.info("Response: " + msgResponse2);
            if (msgResponse2.equalsIgnoreCase("pending")) {
                result = 1;
            } else {
                result = 0;
            }

        } catch (Exception e) {
            log.info("message Error: ", e);
            result = 0;
        }

        return result;
    }

    public String httpCon(String json, String url) throws FileNotFoundException, IOException {
        StringBuilder response = new StringBuilder();

        try {
            URL obj = new URL(url);
            HttpURLConnection httpCon = (HttpURLConnection) obj.openConnection();

            httpCon.setRequestMethod("POST");

            httpCon.setRequestProperty("Accept", "application/json");
            httpCon.setRequestProperty("Content-Type", "application/json");
            httpCon.setDoOutput(true);
            httpCon.setRequestProperty("Authorization", infobibAuth);

            httpCon.setDoOutput(true);

            try (OutputStream os = httpCon.getOutputStream()) {
                os.write(json.getBytes());
                os.flush();
            }

            int responseCode = httpCon.getResponseCode();

            if (responseCode != 200) {

                try (BufferedReader in = new BufferedReader(new InputStreamReader(httpCon.getErrorStream()))) {
                    String inputLine;

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }

                }
            } else {

                try (BufferedReader in = new BufferedReader(new InputStreamReader(httpCon.getInputStream()))) {
                    String inputLine;

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                }
            }

        } catch (IOException ex) {
            log.info(ex.getMessage());
        }

        return response.toString();

    }

}
