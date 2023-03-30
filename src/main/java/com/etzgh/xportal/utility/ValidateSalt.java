/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etzgh.xportal.utility;

/**
 *
 * @author sunkwa-arthur
 */
import java.io.IOException;
import org.apache.log4j.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ValidateSalt implements Filter {

    private static final Logger log = Logger.getLogger(ValidateSalt.class.getName());

    private static final String[] ignorePath = {
        "/api/Authenticator", "/api/ValidateOtp"
    };

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // Assume its HTTP
        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpServletResponse httpRes = (HttpServletResponse) response;
        //String[] ignoreUrls = {"/etz/proxy"};
//        log.info("VALIDATE SALT URL : " + httpReq.getServletPath());

        // Get the salt sent with the request
//        log.info("METHOD: " + httpReq.getMethod() + " - " + httpReq.getContextPath() + " - " + httpReq.getRequestURI() + " - " + httpReq.getPathInfo() + httpReq.getServletPath());
        if (httpReq.getMethod().equalsIgnoreCase("post") && requiresSalt(httpReq.getServletPath())) {
//            log.info("METHOD: " + httpReq.getMethod() + " - " + httpReq.getContextPath() + " - " + httpReq.getRequestURI() + " - " + httpReq.getPathInfo() + " - " + httpReq.getServletPath());

            TokenUtil.processToken(httpReq, httpRes, true, chain);
        } else {
            chain.doFilter(httpReq, httpRes);
        }
    }

    private boolean requiresSalt(String requestURL) {

        for (String saltRequired : ignorePath) {
            if (requestURL == null) {
                requestURL = "";
            }
            //log.info("loginRequired: " + !requestURL.equals(saltRequired));
            if (requestURL.equals(saltRequired)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
