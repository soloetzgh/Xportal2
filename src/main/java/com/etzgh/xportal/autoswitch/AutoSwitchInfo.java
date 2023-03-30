package com.etzgh.xportal.autoswitch;

import com.etz.http.etc.HttpHost;
import com.etzgh.xportal.utility.Config;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

public class AutoSwitchInfo {

    private static final Config config = new Config();
    private static String hostIp = "";
    private static String context = "";
    private static int port = 0;
    private static String key = "";
    private static String hostIp2 = "";
    private static String context2 = "";
    private static int port2 = 0;
    private static String key2 = "";
    private static final Logger log = Logger.getLogger(AutoSwitchInfo.class.getName());
    Map<String, String> switichInfo = new HashMap<>();

    static {
        try {
            hostIp = config.getProperty("AUTOSWITCH_HOST");
            port = Integer.parseInt(config.getProperty("AUTOSWITCH_PORT"));
            key = config.getProperty("AUTOSWITCH_KEY");
            context = config.getProperty("AUTOSWITCH_CONTEXT", "AutoSwitch");

            hostIp2 = config.getProperty("AUTOSWITCH_HOST_2");
            port2 = Integer.parseInt(config.getProperty("AUTOSWITCH_PORT_2"));
            key2 = config.getProperty("AUTOSWITCH_KEY_2");
            context2 = config.getProperty("AUTOSWITCH_CONTEXT_2", "AutoSwitch");
        } catch (Exception e) {
            log.error("error", e);
        }
    }

    public static HttpHost getHttpHost() {
        HttpHost httpHost = null;
        try {

            httpHost = new HttpHost();
            httpHost.setServerAddress(hostIp);
            httpHost.setPort(port);
            httpHost.setContext(context);
            httpHost.setSecureKey(key);
        } catch (Exception ex) {
            log.info("could not connect to AutoSwitch ", ex);
        }

        return httpHost;
    }

    public static HttpHost getHttpHost(String node) {
        HttpHost httpHost = null;
        try {

            httpHost = new HttpHost();
            if (node != null && node.equalsIgnoreCase("node1")) {
                httpHost.setServerAddress(hostIp);
                httpHost.setPort(port);
                httpHost.setContext(context);
                httpHost.setSecureKey(key);
            } else {
                httpHost.setServerAddress(hostIp2);
                httpHost.setPort(port2);
                httpHost.setContext(context2);
                httpHost.setSecureKey(key2);
            }
        } catch (Exception ex) {
            log.info("could not connect to AutoSwitch: " + node, ex);
        }

        return httpHost;
    }

    /*    public static HttpHost getHttpHost2() {
        HttpHost httpHost = null;
        try {

            httpHost = new HttpHost();
            httpHost.setServerAddress(hostIp);
            httpHost.setPort(port);
            httpHost.setContext(context);
            httpHost.setSecureKey(key);
        } catch (Exception ex) {
            log.info("could not connect to AutoSwitch ", ex);
        }

        return httpHost;
    } */
}
