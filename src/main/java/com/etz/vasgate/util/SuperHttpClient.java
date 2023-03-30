/*
 * ETZ.Dev.Team 2019
 */
package com.etz.vasgate.util;

import java.io.FileInputStream;
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

public class SuperHttpClient {

//    private static final Properties props = new Properties();
    private static final Logger log = Logger.getLogger(SuperHttpClient.class.getName());
    private static final String CONNECT_URL = "connecting to url >>";
    private static final String ERROR = "ERROR";

    static final int CONNECT_TIMEOUT = 30;
    static final int SOCKET_TIMEOUT = 30;
    static final boolean LOGGING_ENABLED = true;
    static final boolean ENABLE_DEBUGGING = false;
    static String multiChoiceAuth = "";
    static String justPayNotificationAuth = "";

    public static String doGet(String url, String payload) {
        return doGet(url, CONNECT_TIMEOUT, SOCKET_TIMEOUT);
    }

    public static String doGet(String url, int connectTimeout, int socketTimeout) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        RequestConfig config = RequestConfig.custom().setConnectTimeout(connectTimeout * 1000)
                .setSocketTimeout(socketTimeout * 1000).build();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(config);

        log.info(CONNECT_URL + url);
        return send(httpClient, httpGet);
    }

    public static String doGet(String url) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        RequestConfig config = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT * 1000)
                .setSocketTimeout(SOCKET_TIMEOUT * 1000).build();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(config);

        log.info(CONNECT_URL + url);
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

        log.info(CONNECT_URL + url);
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
        log.info(CONNECT_URL + url);
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

        log.info(CONNECT_URL + url);
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
        log.info(CONNECT_URL + url);
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

        log.info(CONNECT_URL + url);
        return send(httpClient, httpPost);
    }

    public static String doPostSSL(String url, String payload, String trustStoreLoc, String trustStorePass,
            String keystoreloc, String keyStorePass) {
        return doPostSSL(url, payload, CONNECT_TIMEOUT, SOCKET_TIMEOUT, trustStoreLoc, trustStorePass, keystoreloc,
                keyStorePass);
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
            HttpEntity entity = response.getEntity();

            String rspBody = EntityUtils.toString(entity);

            return rspBody;
        } catch (ConnectTimeoutException e) {
            log.info("CONNECT TIMEOUT >> Couldn't establish connection to the host server");
            log.info("Took more than " + (System.currentTimeMillis() - start) + "ms to connect to the server");
            log.error(ERROR, e);
        } catch (SocketTimeoutException e) {
            log.info("READ TIMEOUT >> Couldn't read data from the host server");
            log.info("Took more than " + (System.currentTimeMillis() - start) + "ms to read data from the server");
            log.error(ERROR, e);
        } catch (Exception e) {
            log.info("IOException Calling HTTPS server. Possibly server is down or not accepting the request");
            log.error(ERROR, e);
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
        } catch (Exception e) {
            log.error(ERROR, e);
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
            log.info(ERROR);
            return null;
        } catch (Exception e) {
            log.info(ERROR);
            log.error(ERROR, e);
        }
        return null;
    }

    public static String getBasicAuthStr(String username, String password) {
        String authStr = username + ":" + password;
        authStr = "Basic " + Base64.getEncoder().encodeToString(authStr.getBytes());
        return authStr;
    }
}
