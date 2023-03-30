/*
 * ETZ.Dev.Team 2019
 */
package com.etzgh.xportal.utility;

import com.fasterxml.jackson.databind.JsonNode;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.security.KeyStore;
import java.util.Base64;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class SuperHttpClient {

    private static Config props = new Config();
    static final int CONNECT_TIMEOUT = 30;
    static final int SOCKET_TIMEOUT = 30;
    static final boolean LOGGING_ENABLED = true;
    final static boolean ENABLE_DEBUGGING = false;
    static String multiChoiceAuth = "";
    static String justPayNotificationAuth = "";
    private static final Logger log = Logger.getLogger(SuperHttpClient.class.getName());

    public static void main(String[] args) {
        // SuperHttpClient.doGet("https:
    }

    public static String doGet(String url, String payload) {
        return doGet(url, CONNECT_TIMEOUT, SOCKET_TIMEOUT);
    }

    public static String doGet(String url, int connectTimeout, int socketTimeout) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        RequestConfig config = RequestConfig.custom().setConnectTimeout(connectTimeout * 1000)
                .setSocketTimeout(socketTimeout * 1000).build();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(config);

        log.info("connecting to url >>" + url);
        return send(httpClient, httpGet);
    }

    public static String doGet(String url) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        RequestConfig config = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT * 1000)
                .setSocketTimeout(SOCKET_TIMEOUT * 1000).build();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(config);

        log.info("connecting to url >>" + url);
        return send(httpClient, httpGet);
    }

    public static String doPost(String url, String payload) {
        return doPost(url, payload, CONNECT_TIMEOUT, SOCKET_TIMEOUT);
    }

    public static String doPostXML(String url, String payload) {
        return sendXmlRequest(url, payload, CONNECT_TIMEOUT, SOCKET_TIMEOUT);
    }

    public static String doVasRequest(String url, String payload) {
        return doVasRequest(url, payload, CONNECT_TIMEOUT, SOCKET_TIMEOUT);
    }

    public static String doJustPayNotificationRequest(String url, String payload) {
        return sendPushNotifRequest(justPayNotificationAuth, url, payload, CONNECT_TIMEOUT, SOCKET_TIMEOUT);
    }

    public static String doMCPNotificationRequest(String url, String payload) {
        return sendPushNotifRequest(multiChoiceAuth, url, payload, CONNECT_TIMEOUT, SOCKET_TIMEOUT);
    }

    public static String doPost(String url, String payload, int connectTimeout, int socketTimeout) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        RequestConfig config = RequestConfig.custom().setConnectTimeout(connectTimeout * 1000)
                .setSocketTimeout(socketTimeout * 1000).build();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(config);
        StringEntity st = new StringEntity(payload, "UTF-8");
        st.setChunked(true);
        httpPost.setEntity(st);
        httpPost.addHeader("Accept", "application/json");
        httpPost.addHeader("Content-Type", "application/json");

        log.info("connecting to url >>" + url);
        return send(httpClient, httpPost);
    }

    public static String sendXmlRequest(String url, String payload, int connectTimeout, int socketTimeout) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        RequestConfig config = RequestConfig.custom().setConnectTimeout(connectTimeout * 1000)
                .setSocketTimeout(socketTimeout * 1000).build();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(config);
        StringEntity st = new StringEntity(payload, "UTF-8");
        st.setChunked(true);
        httpPost.setEntity(st);
        httpPost.addHeader("Content-type", "text/xml");
        log.info("connecting to url >>" + url);
        return send(httpClient, httpPost);
    }

    public static String sendPushNotifRequest(String url, String authKey, String payload, int connectTimeout,
            int socketTimeout) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        RequestConfig config = RequestConfig.custom().setConnectTimeout(connectTimeout * 1000)
                .setSocketTimeout(socketTimeout * 1000).build();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(config);
        StringEntity st = new StringEntity(payload, "UTF-8");
        st.setChunked(true);
        httpPost.setEntity(st);
        httpPost.addHeader("Content-type", "application/json");
        httpPost.addHeader("Authorization", "key=" + authKey);

        log.info("connecting to url >>" + url);
        return send(httpClient, httpPost);
    }

    public static String doVasRequest(String url, String payload, int connectTimeout, int socketTimeout) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        RequestConfig config = RequestConfig.custom().setConnectTimeout(connectTimeout * 1000)
                .setSocketTimeout(socketTimeout * 1000).build();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(config);
        StringEntity st = new StringEntity(payload, "UTF-8");
        st.setChunked(true);
        httpPost.setEntity(st);

        httpPost.addHeader("CLIENT_APP", "SMSINTERFACE");
        log.info("connecting to url >>" + url);
        return send(httpClient, httpPost);
    }

    public static String doPostSSL(String url, String payload, int connectTimeout, int socketTimeout,
            String trustStoreLoc, String trustStorePass) {
        return doPostSSL(url, payload, connectTimeout, socketTimeout, trustStoreLoc, trustStorePass, null, null);
    }

    public static String doPostSSL(String url, String payload, String trustStoreLoc, String trustStorePass) {
        return doPostSSL(url, payload, CONNECT_TIMEOUT, SOCKET_TIMEOUT, trustStoreLoc, trustStorePass, null, null);
    }

    public static String doPostSSL(String url, String payload, int connectTimeout, int socketTimeout,
            String trustStoreLoc, String trustStorePass, String keystoreloc, String keyStorePass) {
        SSLConnectionSocketFactory sslsf = null;
        if (keystoreloc == null) {

            sslsf = getSocketFactory(trustStoreLoc, trustStorePass);
        } else {

            sslsf = getSocketFactory(trustStoreLoc, trustStorePass, keystoreloc, keyStorePass);
        }
        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
        RequestConfig config = RequestConfig.custom().setConnectTimeout(connectTimeout * 1000)
                .setSocketTimeout(socketTimeout * 1000).build();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(config);
        StringEntity st = new StringEntity(payload, "UTF-8");
        st.setChunked(true);
        httpPost.setEntity(st);

        log.info("connecting to url >>" + url);
        return send(httpClient, httpPost);
    }

    public static String doPostSSL(String url, String payload, String trustStoreLoc, String trustStorePass,
            String keystoreloc, String keyStorePass) {
        return doPostSSL(url, payload, CONNECT_TIMEOUT, SOCKET_TIMEOUT, trustStoreLoc, trustStorePass, keystoreloc,
                keyStorePass);
    }
    
        public static String doGetSSL(String url, int connectTimeout, int socketTimeout, String trustStoreLoc, String trustStorePass, String keystoreloc, String keyStorePass) {
        SSLConnectionSocketFactory sslsf = null;
       if (keystoreloc == null && trustStoreLoc == null) {
           //no cert needed
        } else if (keystoreloc != null && trustStoreLoc == null) {
          //1 way
            sslsf = getSocketFactory(trustStoreLoc, trustStorePass);   
        }
        else {
            //2 way
            sslsf = getSocketFactory(trustStoreLoc, trustStorePass, keystoreloc, keyStorePass);
        }
        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
        RequestConfig config = RequestConfig.custom().setConnectTimeout(connectTimeout * 1000).setSocketTimeout(socketTimeout * 1000).build();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(config);
        System.out.println("connecting to url >>" + url);
        //httpGet.addHeader("Content-type", "application/json");
        //httpGet.addHeader("Content-type", "text/xml");
        System.out.println("connecting to url >>" + url);
        return sendGRA(httpClient, httpGet);
    }
        
            public static String sendGRA(CloseableHttpClient httpClient, HttpRequestBase httpRequest) {
        
        String rspBody = "";
        
//        if (ENABLE_DEBUGGING) {
//            System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");
//            System.setProperty("org.apache.commons.logging.simplelog.showdatetime", "true");
//            System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http.wire", "DEBUG");
//        }

//        httpRequest.addHeader("Authorization", "Basic " + getBascicAuthStr("ETRANZACT", "ETRANZACT"));
        httpRequest.addHeader("Content-type", "application/json");
        //httpRequest.addHeader("Accept", "application/json");
        httpRequest.addHeader("Content-type", "application/json");
        httpRequest.addHeader("x-api-key", "4Cq1W6LirB6KgtdYMu02R3Q1LVwj0Z3w89ghJ3vS");
        long start = System.currentTimeMillis();
        try (CloseableHttpResponse response = httpClient.execute(httpRequest)) {
        	//XSSFWorkbook wb = new XSSFWorkbook();
            System.out.println("RESPONSE HEADERS >> " + response.toString());
            int status = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            //long len = entity.getContentLength();
//            String rspBody = EntityUtils.toString(entity);
 rspBody = EntityUtils.toString(entity);

//            String path = "/Users/GadEdem/Pictures/Elevy.json";
//String path = "C:\\Users\\yaw.owusu-koranteng\\Downloads\\ny\\Elevy.json";
//
//            FileWriter file = new FileWriter(path);
//            file.write(rspBody);
//            file.close();
//            JsonNode node = om.readTree(new File(path));
//            int colNum = 0;
//			XSSFSheet sheet = wb.createSheet(" ELevy ");
//			Row row = sheet.createRow(0);
//			Cell transactionStatusC = row.createCell(colNum++);
//			Cell transactionStatusTimestampC = row.createCell(colNum++);
//			Cell elevyIDC = row.createCell(colNum++);
//			Cell clientTransactionIDC = row.createCell(colNum++);
//			Cell serviceTypeC = row.createCell(colNum++);
//			Cell currencyC = row.createCell(colNum++);
//			Cell transferAmountC = row.createCell(colNum++);
//			Cell taxableAmountC = row.createCell(colNum++);
//			Cell elevyAmountC = row.createCell(colNum++);
//			Cell elevyRefundAmountC = row.createCell(colNum++);
//			transactionStatusC.setCellValue("transactionStatus");
//			transactionStatusTimestampC.setCellValue("transactionStatusTimestamp");
//			elevyIDC.setCellValue("elevyID");
//			clientTransactionIDC.setCellValue("clientTransactionID");
//			serviceTypeC.setCellValue("serviceType");
//			transactionStatusC.setCellValue("transactionStatus");
//			currencyC.setCellValue("currency");
//			transferAmountC.setCellValue("transferAmount");
//			taxableAmountC.setCellValue("taxableAmount");
//			elevyAmountC.setCellValue("elevyAmount");
//			elevyRefundAmountC.setCellValue("elevyRefundAmount");
//			JsonNode body = node.get("transactions");
//			int rowNum = 1;
//			colNum = 0;
//			int i = 0;
//			JsonNode rowNode;
//			while(i < body.size()) {
//				rowNode = body.get(i++);
//				Row bodyRow = sheet.createRow(rowNum++);
//				Cell transactionStatus = bodyRow.createCell(colNum++);
//				Cell transactionStatusTimestamp = bodyRow.createCell(colNum++);
//				Cell elevyID = bodyRow.createCell(colNum++);
//				Cell clientTransactionID = bodyRow.createCell(colNum++);
//				Cell serviceType = bodyRow.createCell(colNum++);
//				Cell currency = bodyRow.createCell(colNum++);
//				Cell transferAmount = bodyRow.createCell(colNum++);
//				Cell taxableAmount = bodyRow.createCell(colNum++);
//				Cell elevyAmount = bodyRow.createCell(colNum++);
//				Cell elevyRefundAmount = bodyRow.createCell(colNum++);
//				transactionStatus.setCellValue(rowNode.get("transactionStatus").asText());
//				transactionStatusTimestamp.setCellValue(rowNode.get("transactionStatusTimestamp").asText());
//				elevyID.setCellValue(rowNode.get("elevyID").asText());
//				clientTransactionID.setCellValue(rowNode.get("clientTransactionID").asText());
//				serviceType.setCellValue(rowNode.get("serviceType").asText());transactionStatus.setCellValue(rowNode.get("transactionStatus").asText());
//				currency.setCellValue(rowNode.get("currency").asText());
//				transferAmount.setCellValue(rowNode.get("transferAmount").asText());
//				taxableAmount.setCellValue(rowNode.get("taxableAmount").asText());
//				elevyAmount.setCellValue(rowNode.get("elevyAmount").asText());
//				elevyRefundAmount.setCellValue(rowNode.get("elevyRefundAmount").asText());
//				colNum = 0;
//			}
            
//            FileOutputStream outputStream = new FileOutputStream("/Users/GadEdem/Pictures/Elevy.xlsx");
 //FileOutputStream outputStream = new FileOutputStream("C:\\Users\\yaw.owusu-koranteng\\Downloads\\ny\\Elevy.xlsx");

//			wb.write(outputStream);
//			wb.close();
			//System.out.println(" Excel file generated");   
            System.out.println("RESPONSE BODY >> " + rspBody);
            System.out.println("TAT [CONNECT+DATA] >> " + (System.currentTimeMillis() - start));
            return rspBody;
        } catch (ConnectTimeoutException e) {
            System.out.println("CONNECT TIMEOUT >> Couldn't establish connection to the host server");
            System.out.println("Took more than " + (System.currentTimeMillis() - start) + "ms to connect to the server");
            e.printStackTrace(System.out);
        } catch (SocketTimeoutException e) {
            System.out.println("READ TIMEOUT >> Couldn't read data from the host server");
            System.out.println("Took more than " + (System.currentTimeMillis() - start) + "ms to read data from the server");
            e.printStackTrace(System.out);
        } catch (Exception e) {
            System.out.println("message " + e.getMessage());
             System.out.println("class " + e.getClass().getSimpleName());
            System.out.println("IOException Calling HTTPS server. Possibly server is down or not accepting the request");
            e.printStackTrace(System.out);
        }
        //return null;
        
        return rspBody;
    }

    public static String send(CloseableHttpClient httpClient, HttpRequestBase httpRequest) {
        if (ENABLE_DEBUGGING) {
            System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");
            System.setProperty("org.apache.commons.logging.simplelog.showdatetime", "true");
            System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http.wire", "DEBUG");
        }
        httpRequest.addHeader("Authorization", getBasicAuthStr("ETRANZACT", "ETRANZACT"));
        long start = System.currentTimeMillis();
        try (CloseableHttpResponse response = httpClient.execute(httpRequest)) {
            response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();

            String rspBody = EntityUtils.toString(entity);

            return rspBody;
        } catch (ConnectTimeoutException e) {
            log.info("CONNECT TIMEOUT >> Couldn't establish connection to the host server");
            log.info("Took more than " + (System.currentTimeMillis() - start) + "ms to connect to the server");
            e.printStackTrace(System.out);
        } catch (SocketTimeoutException e) {
            log.info("READ TIMEOUT >> Couldn't read data from the host server");
            log.info("Took more than " + (System.currentTimeMillis() - start) + "ms to read data from the server");
            e.printStackTrace(System.out);
        } catch (Exception e) {
            log.info("IOException Calling HTTPS server. Possibly server is down or not accepting the request");
            e.printStackTrace(System.out);
        }
        return null;
    }

    public static SSLConnectionSocketFactory getSocketFactory(String trustStoreLoc, String trustStorePass,
            String keystoreloc, String keyStorePass) {
        try {
            final KeyStore keyStore = KeyStore.getInstance("JKS");
            try (final InputStream is = new FileInputStream(keystoreloc)) {
                keyStore.load(is, keyStorePass.toCharArray());
            }
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(keyStore, keyStorePass.toCharArray());

            final KeyStore trustStore = KeyStore.getInstance("JKS");
            try (final InputStream is = new FileInputStream(trustStoreLoc)) {
                trustStore.load(is, trustStorePass.toCharArray());
            }
            final TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(trustStore);

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), new java.security.SecureRandom());

            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext,
                    new String[]{"TLSv1", "TLSv1.1", "TLSv1.2"}, null,
                    SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            return sslsf;
        } catch (java.security.KeyStoreException e) {
            log.info("Error while creating SSL Factory.");
            return null;
        } catch (Exception e) {
            log.info("Error while creating SSL Factory.");
            e.printStackTrace(System.out);
        }
        return null;
    }

    public static SSLConnectionSocketFactory getSocketFactory(String trustStoreLoc, String trustStorePass) {
        try {
            final KeyStore trustStore = KeyStore.getInstance("JKS");
            try (final InputStream is = new FileInputStream(trustStoreLoc)) {
                trustStore.load(is, trustStorePass.toCharArray());
            }
            final TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(trustStore);

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tmf.getTrustManagers(), new java.security.SecureRandom());

            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext,
                    new String[]{"TLSv1", "TLSv1.1", "TLSv1.2"}, null,
                    SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            return sslsf;
        } catch (java.security.KeyStoreException e) {
            log.info("Error while creating SSL Factory.");
            return null;
        } catch (Exception e) {
            log.info("Error while creating SSL Factory.");
            e.printStackTrace(System.out);
        }
        return null;
    }

    public static String getBasicAuthStr(String username, String password) {
        String authStr = username + ":" + password;
        authStr = "Basic " + Base64.getEncoder().encodeToString(authStr.getBytes());
        return authStr;
    }
}
