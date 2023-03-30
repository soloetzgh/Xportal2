/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etzgh.xportal.controller;

import com.etzgh.xportal.utility.TokenUtil;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import org.apache.log4j.Logger;

/**
 *
 * @author sunkwa-arthur
 */
public class HttpSessionCollector implements HttpSessionListener {

    private static final Map<String, HttpSession> sessions = new HashMap<String, HttpSession>();

    private static final Multimap<String, String> map = LinkedHashMultimap.create();
    private static final PortalSettings portalSettings = new PortalSettings();

    private static final Logger log = Logger.getLogger(HttpSessionCollector.class.getName());
    private static final String ERROR = "ERROR";
    private static final Map<String, Integer> periodMap = new HashMap<>();

    static {
        periodMap.put("minute", (60));
        periodMap.put("hour", (60 * 60));
        periodMap.put("day", (60 * 60 * 24));
        periodMap.put("week", (60 * 60 * 24 * 7));
        periodMap.put("month", (60 * 60 * 24 * 7 * 4));
        periodMap.put("year", (60 * 60 * 24 * 7 * 4 * 12));
    }

    @Override
    public void sessionCreated(HttpSessionEvent event) {
//        log.info("TIMEOUT: " + sessionTimeout + 2);
        event.getSession().setMaxInactiveInterval(getSessionTimeout() + 1);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        try {
            log.info("Removing: " + event.getSession().getAttribute("userSessionUserId"));
            log.info("Removing: " + event.getSession().getId());
            sessions.remove(event.getSession().getId());

            map.remove(event.getSession().getAttribute("userSessionUserId"), event.getSession().getId());
            TokenUtil.removeSessionCache(event.getSession().getId());
        } catch (Exception e) {
            log.error(ERROR, e);
        }
    }

    public static void destroySession(String userId, String sessionId) {

        try {
            HttpSession session = sessions.get(sessionId);
            if (session != null) {
                session.invalidate();
                log.info("Invalidating out: " + sessionId);
            } else {
                log.info("Session Unavailable");
            }
        } catch (Exception e) {
            log.error(ERROR, e);

        }
    }

    public static boolean removeAllUserSessions(String userId) {
        boolean result = false;
        try {
            Iterator<Map.Entry<String, String>> i = map.entries().iterator();
            while (i.hasNext()) {
                Entry<String, String> data = i.next();
                if (data.getKey().equals(userId)) {
                    destroySession(userId, data.getValue());
                    i.remove();
                }
            }
        } catch (Exception l) {
            log.error(ERROR, l);

        }
        return result;
    }

    public static boolean removeOtherUserSessions(String userId, String sessionId) {
        boolean result = false;

        try {
            Iterator<Map.Entry<String, String>> i = map.entries().iterator();
            while (i.hasNext()) {
                Entry<String, String> data = i.next();
                if (data.getKey().equals(userId) && !data.getValue().equals(sessionId)) {
                    destroySession(data.getKey(), data.getValue());
                    i.remove();
                }
            }
        } catch (Exception e) {
            log.error(ERROR, e);

        }
        return result;
    }

    public static void saveSession(String key, String value, HttpSession httpSession) {
        try {
            log.info("SAVING SESSION: " + key + " => value:" + value + " : " + httpSession);
            map.put(key, value);
            sessions.put(value, httpSession);
        } catch (Exception e) {
            log.error(ERROR, e);

        }
    }

    public static boolean setSessionTimeout(int timeOut) {
        boolean success = true;

        try {
//            sessionTimeout = timeOut;
            Iterator<Map.Entry<String, String>> i = map.entries().iterator();
            while (i.hasNext()) {
                Entry<String, String> data = i.next();
                try {
                    HttpSession s = sessions.get(data.getValue());
                    if (s != null) {
                        log.info("Setting Max: " + timeOut);
                        s.setMaxInactiveInterval(timeOut);
                    }
                } catch (Exception t) {
                    log.error(ERROR, t);
                }
            }
        } catch (Exception e) {
            log.error(ERROR, e);
        }
        return success;
    }

    private int getSessionTimeout() {
        int sessionT = 15 * 60;
        try {
            String timeOut = new PortalSettings().getSetting("session_timeout");
//            log.info("TIMEOUT::: " + timeOut);
            String status = timeOut.split(":")[1];
//            log.info("ACTIVE TIMEOUT: " + status);
            if (status.equals("1")) {
                float data = Float.parseFloat((timeOut.split("-")[0]));
                String data1 = timeOut.split("-").length > 1 ? timeOut.split("-")[1].split(":")[0] : "minute";

//                log.info("CALC TIMEOUT: " + data + " " + data1);
                sessionT = (int) data * periodMap.getOrDefault(data1, 1);
            }
        } catch (Exception e) {
            log.error(ERROR, e);
            sessionT = 15 * 60;
        }
        return sessionT;
    }
}
