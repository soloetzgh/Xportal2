/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etzgh.xportal.utility;

import com.etzgh.xportal.model.JsonResponse;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author sunkwa-arthur
 */
public class TokenUtil {

    private static final Map<String, Cache<String, Boolean>> sessionTokenMap = new HashMap<>();
    private static final Logger log = Logger.getLogger(TokenUtil.class.getName());
//    private static final MultiValuedMap sessionMap = new ArrayListValuedHashMap();
    private static final ListMultimap<String, String> sessionMap = ArrayListMultimap.create();

    public static void processToken(HttpServletRequest httpReq, HttpServletResponse httpRes, boolean validate, FilterChain chain) throws ServletException, IOException {
        if (!validate) {
            // Check the user session for the salt cache, if none is present we create one

            Cache<String, Boolean> csrfPreventionSaltCache = sessionTokenMap.getOrDefault(httpReq.getSession().getId(), null);

            String salt = RandomStringUtils.random(20, 0, 0, true, true, null, new SecureRandom());

            if (csrfPreventionSaltCache == null) {
                csrfPreventionSaltCache = CacheBuilder.newBuilder()
                        // .expireAfterWrite(10, TimeUnit.MINUTES)
                        // .maximumSize(10000)
                        .concurrencyLevel(10)
                        .expireAfterAccess(10, TimeUnit.MINUTES)
                        .build();
            }

            // Generate the salt and store it in the users cache
            csrfPreventionSaltCache.put(salt, Boolean.TRUE);
//            log.info("SESSION: " + httpReq.getSession().getId());
            sessionTokenMap.put(httpReq.getSession().getId(), csrfPreventionSaltCache);

            // Add the salt to the current request so it can be used
            // by the page rendered in this request
            Cookie ck = new Cookie("XSRF-TOKEN", salt);
            ck.setPath("/");
            httpRes.addCookie(ck);
            httpRes.addHeader("XSRF-TOKEN", salt);
        } else {
            String salt = httpReq.getHeader("X-XSRF-TOKEN");
//            log.info("GOT TOKENT " + salt);

            // Validate that the salt is in the cache
//            log.info("SESSION: " + httpReq.getSession().getId());
//            log.info(sessionTokenMap.toString());
            Cache<String, Boolean> csrfPreventionSaltCache = sessionTokenMap.getOrDefault(httpReq.getSession().getId(), null);

            if (csrfPreventionSaltCache != null
                    && salt != null
                    //                    && csrfPreventionSaltCache.getIfPresent(salt) != null) {
                    && csrfPreventionSaltCache.asMap().remove(salt) != null) {

                csrfPreventionSaltCache.put(salt, Boolean.TRUE);
//                log.info("SESSION: " + httpReq.getSession().getId());
                sessionTokenMap.put(httpReq.getSession().getId(), csrfPreventionSaltCache);
                // If the salt is in the cache, we move on
                Cookie ck = new Cookie("XSRF-TOKEN", salt);
                ck.setPath("/");
                httpRes.addCookie(ck);
                httpRes.addHeader("XSRF-TOKEN", salt);
                chain.doFilter(httpReq, httpRes);
            } else {
                // Otherwise we throw an exception aborting the request flow
                httpRes.setStatus(403);
                PrintWriter out = httpRes.getWriter();

                out.println(new Gson().toJson(new JsonResponse("01", "Potential CSRF detected !!")));
//                throw new ServletException("Potential CSRF detected !! Inform a scary sysadmin ASAP. ");
            }
        }
    }

//  
    public static void processToken2(HttpServletRequest httpReq, HttpServletResponse httpRes, boolean validate, FilterChain chain) throws ServletException, IOException {
        if (!validate) {
            // Check the user session for the salt cache, if none is present we create one
            String salt = RandomStringUtils.random(20, 0, 0, true, true, null, new SecureRandom());

            // Generate the salt and store it in the users cache
            sessionMap.put(httpReq.getSession().getId(), salt);
            log.info("SESSION: " + httpReq.getSession().getId() + " - " + salt);

            // Add the salt to the current request so it can be used
            // by the page rendered in this request
            Cookie ck = new Cookie("XSRF-TOKEN", salt);
            ck.setPath("/");
            httpRes.addCookie(ck);
            httpRes.addHeader("XSRF-TOKEN", salt);
        } else {
            String salt = httpReq.getHeader("X-XSRF-TOKEN");
//            log.info("GOT TOKENT " + salt);

            // Validate that the salt is in the cache
//            log.info("SESSION: " + httpReq.getSession().getId());
//            log.info(sessionTokenMap.toString());
            boolean valid = sessionMap.containsEntry(httpReq.getSession().getId(), salt);

            if (valid) {
                sessionMap.remove(httpReq.getSession().getId(), salt);
                // If the salt is in the cache, we move on
                chain.doFilter(httpReq, httpRes);
            } else {
                // Otherwise we throw an exception aborting the request flow
                httpRes.setStatus(403);
                PrintWriter out = httpRes.getWriter();

                out.println(new Gson().toJson(new JsonResponse("01", "Potential CSRF detected !!")));
//                throw new ServletException("Potential CSRF detected !! Inform a scary sysadmin ASAP. ");
            }
        }
    }

    public static void removeSessionCache(String id) {
        sessionTokenMap.remove(id);
        sessionMap.removeAll(id);
    }

}
