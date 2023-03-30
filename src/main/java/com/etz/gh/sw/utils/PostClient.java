package com.etz.gh.sw.utils;

import com.etz.gh.sw.dto.Headers;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import org.apache.log4j.Logger;

public class PostClient {

    private static final Logger log = Logger.getLogger(PostClient.class.getName());
    private static final String ERRORMSG = "error";

    public static String doPost(String method, String url, Headers headers, String postData) throws Exception {
        String result = "";
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod(method);
            con.setRequestProperty("Content-Type", "application/json");
            if (headers.getSignature() != null) {
                con.setRequestProperty("signature", headers.getSignature());
            }
            if (headers.getHash() != null) {
                con.setRequestProperty("hash", headers.getHash());
            }

            if (headers.getUserId() != null) {
                con.setRequestProperty("userId", headers.getUserId());
            }

            if (headers.getPin() != null) {
                con.setRequestProperty("pin", headers.getPin());
            }

            con.setDoOutput(true);
            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.writeBytes(postData);
                wr.flush();
            }

            int responseCode = con.getResponseCode();

            BufferedReader in = null;

            System.out.println("response headers:: " + con.getHeaderFields());

            System.out.println();

            if (responseCode == 200) {
                in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {
                in = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }

            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();

            result = response.toString();

            return result;
        } catch (Exception e) {
            log.error(ERRORMSG, e);
        }

        return result;
    }

    public static HashMap<String, String> doPost2(String method, String url, Headers headers, String postData) {
        HashMap<String, String> res = new HashMap<>();
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod(method);
            con.setRequestProperty("Content-Type", "application/json");
            if (headers.getSignature() != null) {
                con.setRequestProperty("signature", headers.getSignature());
            }
            if (headers.getHash() != null) {
                con.setRequestProperty("hash", headers.getHash());
            }

            if (headers.getUserId() != null) {
                con.setRequestProperty("userId", headers.getUserId());
            }

            if (headers.getPin() != null) {
                con.setRequestProperty("pin", headers.getPin());
            }

            con.setDoOutput(true);
            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.writeBytes(postData);
                wr.flush();
            }

            int responseCode = con.getResponseCode();

            BufferedReader in = null;
            res.put("safe_tokens", con.getHeaderField("safe_tokens"));
            System.out.println("response headers:: " + con.getHeaderField("safe_tokens"));

            System.out.println();

            if (responseCode == 200) {
                in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {
                in = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }

            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();

            res.put("response", response.toString());

        } catch (Exception e) {
            log.error(ERRORMSG, e);
        }

        return res;
    }

    public static String doGet(String url, String authorization, String apiKey) throws Exception {
        String result = "";

        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("GET");
            con.setRequestProperty("access-key", apiKey);
            if (!authorization.isEmpty()) {
                con.setRequestProperty("Authorization", "Bearer " + authorization);
            }

            con.setDoOutput(true);

            int responseCode = con.getResponseCode();
            BufferedReader in = null;

            if (responseCode == 200) {
                in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));

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

            result = response.toString();

            return result;
        } catch (Exception e) {
            log.error(ERRORMSG, e);
        }

        return result;
    }

    public static String makePostRequest(String method, String url, Headers headers, String payload) {
        String result = "";

        try {
            result = PostClient.doPost(method, url, headers, payload);

            return result;
        } catch (Exception e) {
            log.error(ERRORMSG, e);
            return null;
        }
    }

    public static String makeGetRequest(String url, String signature, String apiKey) {
        String result = "";
        System.out.println("URL:: " + url);
        try {
            result = PostClient.doGet(url, signature, apiKey);

            return result;
        } catch (Exception e) {
            log.error(ERRORMSG, e);
            return null;
        }
    }

}
