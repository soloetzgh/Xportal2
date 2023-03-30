/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etzgh.xportal.utility;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author sunkwa-arthur
 */
//@WebFilter("/LoadSalt")
public class LoadSalt implements Filter {

//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//            throws IOException, ServletException {
//
//        // Assume its HTTP
//        HttpServletRequest httpReq = (HttpServletRequest) request;
//        HttpServletResponse httpRes = (HttpServletResponse) response;
//
//        if (httpReq.getRequestURI().endsWith(".html") && !httpReq.getRequestURI().endsWith("index.html")) {
//
//            // Check the user session for the salt cache, if none is present we create one
//            Cache<String, Boolean> csrfPreventionSaltCache = (Cache<String, Boolean>) httpReq.getSession().getAttribute("csrfPreventionSaltCache");
//
//            if (csrfPreventionSaltCache == null) {
//                csrfPreventionSaltCache = CacheBuilder.newBuilder()
//                        .maximumSize(5000)
//                        .expireAfterWrite(10, TimeUnit.MINUTES)
//                        .expireAfterAccess(1, TimeUnit.SECONDS)
//                        .build();
//
//                httpReq.getSession().setAttribute("csrfPreventionSaltCache", csrfPreventionSaltCache);
//            }
//
//            // Generate the salt and store it in the users cache
//            String salt = RandomStringUtils.random(20, 0, 0, true, true, null, new SecureRandom());
//            csrfPreventionSaltCache.put(salt, Boolean.TRUE);
//
//            // Add the salt to the current request so it can be used
//            // by the page rendered in this request
//            httpReq.setAttribute("csrfPreventionSalt", salt);
//            httpRes.addHeader("XSRF-TOKEN", salt);
//        }
//
//        chain.doFilter(httpReq, httpRes);
//    }
//    
//    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // Assume its HTTP
        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpServletResponse httpRes = (HttpServletResponse) response;

        HttpSession session = httpReq.getSession(false);

//        log.info("LOAD SALT");
        if (httpReq.getRequestURI().endsWith(".html") && !httpReq.getRequestURI().endsWith("/index.html") && session != null) {

            TokenUtil.processToken(httpReq, httpRes, false, null);

        }

        chain.doFilter(httpReq, httpRes);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
//        log.info("FILTER LOAD");
    }

    @Override
    public void destroy() {
    }
}
