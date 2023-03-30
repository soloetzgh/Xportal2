/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etzgh.xportal.utility;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.log4j.Logger;

/**
 *
 * @author sunkwa-arthur
 */
public class DoHTTPRequest {

    private static final Logger log = Logger.getLogger(DoHTTPRequest.class.getName());

    public static void main(String[] args) {
    }

    public String post(String URL, String jsonData) {
        StringBuilder response = new StringBuilder();
        try {
            URL url = new URL(URL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("POST");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("CLIENT_APP", "SMSINTERFACE");
            con.setDoOutput(true);

            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.writeBytes(jsonData);
                wr.flush();
            }

            int responseCode = con.getResponseCode();

            try (
                    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
            }

            return response.toString();

        } catch (Exception ex) {
            log.error("error: " + URL + ": ", ex);
        }
        return response.toString();
    }

    public int get(String url) {
        int responseCode = -1;
        StringBuilder response = new StringBuilder();
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            responseCode = con.getResponseCode();
            con.setReadTimeout(30000);
            con.setReadTimeout(120000);

            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()))) {
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
            }

        } catch (Exception ex) {
            responseCode = 0;
            log.error("error: " + url + ": ", ex);
        }

        return responseCode;
    }

    public String get1(String url) {
        // int responseCode = -1;
        StringBuilder response = new StringBuilder();
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            //responseCode = con.getResponseCode();
            con.setReadTimeout(30000);
            con.setReadTimeout(120000);
            System.out.println("\nSending 'GET' request to URL : " + url);
            //System.out.println("Response Code : " + responseCode);
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()))) {
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
            }

        } catch (Exception ex) {
            // responseCode = 0;
            log.error("error: ", ex);

        }

//        System.out.println("RESPONSE MESSAGE >>" + response.toString());
        return response.toString();
    }

    public static String get2(String URL) {
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
            log.error("error: " + URL + ": ", ex);
        }
        return response.toString();
    }

}
