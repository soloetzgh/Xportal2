/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etzgh.xportal.controller;

/**
 *
 * @author sunkwa-arthur
 */
import com.etzgh.xportal.utility.TokenUtil;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ValidateSalt implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // Assume its HTTP
        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpServletResponse httpRes = (HttpServletResponse) response;
        //String[] ignoreUrls = {"/etz/proxy"};

        // Get the salt sent with the request
        //log.info("METHOD: " + httpReq.getMethod() + " - " + httpReq.getContextPath() + " - " + httpReq.getRequestURI() + " - " + httpReq.getPathInfo() + httpReq.getServletPath());
        if (httpReq.getMethod().equalsIgnoreCase("post") && (httpReq.getServletPath().startsWith("/etz/proxy") || httpReq.getServletPath().startsWith("/chgpwd"))) {
            //log.info("METHOD: " + httpReq.getMethod() + " - " + httpReq.getContextPath() + " - " + httpReq.getRequestURI() + " - " + httpReq.getPathInfo() +" - "+ httpReq.getServletPath());

            TokenUtil.processToken(httpReq, httpRes, true, chain);

        } else {
            chain.doFilter(httpReq, httpRes);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
