/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etzgh.xportal.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.log4j.Logger;

/**
 *
 * @author sunkwa-arthur
 */
public class BinChecker {

    private static final Logger log = Logger.getLogger(BinChecker.class.getName());

    public String CheckBin(String BinNumber) {

        StringBuilder response = new StringBuilder();
        try {

            URL url = new URL("");

            HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();

            httpCon.setRequestMethod("GET");

            httpCon.setRequestProperty("Accept", "application/json");
            httpCon.setRequestProperty("Content-Type", "application/json");
            httpCon.setDoOutput(true);

            int responseCode = httpCon.getResponseCode();

            if (responseCode != 200) {

                log.info("\nSending POST request to URL : " + url);
                log.info("Response Code: " + responseCode + ". Connection failed");
                try (BufferedReader in = new BufferedReader(new InputStreamReader(httpCon.getErrorStream()))) {
                    String inputLine;

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    log.info("Output: \n" + response);
                }
            } else {
                log.info("\nSending POST request to URL : " + url);
                log.info("Response Code: " + responseCode);

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
        log.info(response.toString());
        return response.toString();
    }
}
