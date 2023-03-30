/*
 * ETZ.Dev.Team 2019
 */
package com.etzgh.elevy;

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
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class SuperHttpClient {

    static final int CONNECT_TIMEOUT = 30;
    static final int SOCKET_TIMEOUT = 30;
    static final boolean LOGGING_ENABLED = true;
    static final boolean ENABLE_DEBUGGING = false;
    private static final Logger log = Logger.getLogger(SuperHttpClient.class.getName());

    public static void main(String[] args) {

    }

    public static String doGet(String url, String payload, String apiKey) {
        return doGet(url, CONNECT_TIMEOUT, SOCKET_TIMEOUT, apiKey);
    }

    public static String doGet(String url, int connectTimeout, int socketTimeout, String apiKey) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        RequestConfig config = RequestConfig.custom().setConnectTimeout(connectTimeout * 1000)
                .setSocketTimeout(socketTimeout * 1000).build();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(config);

        System.out.println("connecting to url >>" + url);
        return send(httpClient, httpGet, apiKey);
    }

    public static String doGetSSL(String url, String payload, String trustStoreLoc, String trustStorePass,
            String apiKey) {
        return doGetSSL(url, payload, CONNECT_TIMEOUT, SOCKET_TIMEOUT, trustStoreLoc, trustStorePass, null, null,
                apiKey);
    }

    public static String doPost(String url, String payload, String apiKey) {
        return doPost(url, payload, CONNECT_TIMEOUT, SOCKET_TIMEOUT, apiKey);
    }

    public static String doPost(String url, String payload, int connectTimeout, int socketTimeout, String apiKey) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        RequestConfig config = RequestConfig.custom().setConnectTimeout(connectTimeout * 1000)
                .setSocketTimeout(socketTimeout * 1000).build();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(config);
        StringEntity st = new StringEntity(payload, "UTF-8");
        st.setChunked(true);
        httpPost.setEntity(st);

        System.out.println("connecting to url >>" + url);
        return send(httpClient, httpPost, apiKey);
    }

    public static String doPostSSL(String url, String payload, int connectTimeout, int socketTimeout,
            String trustStoreLoc, String trustStorePass, String apiKey) {
        return doPostSSL(url, payload, connectTimeout, socketTimeout, trustStoreLoc, trustStorePass, null, null,
                apiKey);
    }

    public static String doPostSSL(String url, String payload, String trustStoreLoc, String trustStorePass,
            String apiKey) {
        return doPostSSL(url, payload, CONNECT_TIMEOUT, SOCKET_TIMEOUT, trustStoreLoc, trustStorePass, null, null,
                apiKey);
    }

    public static String doGetSSL(String url, String payload, int connectTimeout, int socketTimeout,
            String trustStoreLoc, String trustStorePass, String keystoreloc, String keyStorePass, String apiKey) {
        SSLConnectionSocketFactory sslsf = null;
        if (keystoreloc == null && trustStoreLoc == null) {

        } else if (keystoreloc != null && trustStoreLoc == null) {

            sslsf = getSocketFactory(trustStoreLoc, trustStorePass);
        } else {

            sslsf = getSocketFactory(trustStoreLoc, trustStorePass, keystoreloc, keyStorePass);
        }
        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
        RequestConfig config = RequestConfig.custom().setConnectTimeout(connectTimeout * 1000)
                .setSocketTimeout(socketTimeout * 1000).build();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(config);
        System.out.println("connecting to url >>" + url);

        System.out.println("connecting to url >>" + url);
        return send(httpClient, httpGet, apiKey);
    }

    public static String doPostSSL(String url, String payload, int connectTimeout, int socketTimeout,
            String trustStoreLoc, String trustStorePass, String keystoreloc, String keyStorePass, String apiKey) {
        SSLConnectionSocketFactory sslsf = null;
        if (keystoreloc == null && trustStoreLoc == null) {
            // Do nothing 
        } else if (keystoreloc != null && trustStoreLoc == null) {

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
        System.out.println("connecting to url >>" + url);
        return send(httpClient, httpPost, apiKey);
    }

    public static String doPostSSL(String url, String payload, String trustStoreLoc, String trustStorePass,
            String keystoreloc, String keyStorePass, String apiKey) {
        return doPostSSL(url, payload, CONNECT_TIMEOUT, SOCKET_TIMEOUT, trustStoreLoc, trustStorePass, keystoreloc,
                keyStorePass, apiKey);
    }

    public static String send(CloseableHttpClient httpClient, HttpRequestBase httpRequest, String apiKey) {

        httpRequest.addHeader("Content-type", "application/json");

        httpRequest.addHeader("Content-type", "application/json");
        httpRequest.addHeader("x-api-key", apiKey);
        long start = System.currentTimeMillis();
        try (CloseableHttpResponse response = httpClient.execute(httpRequest)) {
            System.out.println("RESPONSE HEADERS >> " + response.toString());
            HttpEntity entity = response.getEntity();

            String rspBody = EntityUtils.toString(entity);
            System.out.println("RESPONSE BODY >> " + rspBody);
            System.out.println("TAT [CONNECT+DATA] >> " + (System.currentTimeMillis() - start));
            return rspBody;
        } catch (ConnectTimeoutException e) {
            System.out.println("CONNECT TIMEOUT >> Couldn't establish connection to the host server");
            System.out
                    .println("Took more than " + (System.currentTimeMillis() - start) + "ms to connect to the server");
            log.error(e.getMessage(), e);
        } catch (SocketTimeoutException e) {
            System.out.println("READ TIMEOUT >> Couldn't read data from the host server");
            System.out.println(
                    "Took more than " + (System.currentTimeMillis() - start) + "ms to read data from the server");
            log.error(e.getMessage(), e);
        } catch (Exception e) {
            System.out.println("message " + e.getMessage());
            System.out.println("class " + e.getClass().getSimpleName());
            System.out
                    .println("IOException Calling HTTPS server. Possibly server is down or not accepting the request");
            log.error(e.getMessage(), e);
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

            SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), new java.security.SecureRandom());

            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, new String[]{"TLSv1.2"},
                    null, new NoopHostnameVerifier());
            return sslsf;
        } catch (java.security.KeyStoreException e) {
            System.out.println("Error while creating SSL Factory.");
            return null;
        } catch (Exception e) {
            System.out.println("Error while creating SSL Factory.");
            log.error(e.getMessage(), e);
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
            System.out.println("Error while creating SSL Factory.");
            return null;
        } catch (Exception e) {
            System.out.println("Error while creating SSL Factory.");
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public static String getBascicAuthStr(String username, String password) {
        String authStr = username + ":" + password;
        authStr = Base64.getEncoder().encodeToString(authStr.getBytes());
        return authStr;
    }
}
