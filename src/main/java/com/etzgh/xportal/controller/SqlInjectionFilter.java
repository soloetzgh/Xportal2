/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etzgh.xportal.controller;

import com.etzgh.xportal.model.JsonResponse;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author sunkwa-arthur
 */
@WebFilter("/SqlInjectionFilter")
public class SqlInjectionFilter implements Filter {

    private static final String SQL_TYPES
            = "TABLE, TABLESPACE, PROCEDURE, FUNCTION, TRIGGER, KEY, VIEW, MATERIALIZED VIEW, LIBRARY"
            + "DATABASE LINK, DBLINK, INDEX, CONSTRAINT, TRIGGER, USER, SCHEMA, DATABASE, PLUGGABLE DATABASE, BUCKET, "
            + "CLUSTER, COMMENT, SYNONYM, TYPE, JAVA, SESSION, ROLE, PACKAGE, PACKAGE BODY, OPERATOR"
            + "SEQUENCE, RESTORE POINT, PFILE, CLASS, CURSOR, OBJECT, RULE, USER, DATASET, DATASTORE, "
            + "COLUMN, FIELD, OPERATOR";
    private static final String[] sqlRegexps = {
        "(?i)(.*)(\\b)+SELECT(\\b)+\\s.*(\\b)+FROM(\\b)+\\s.*(.*)",
        "(?i)(.*)(\\b)+INSERT(\\b)+\\s.*(\\b)+INTO(\\b)+\\s.*(.*)",
        "(?i)(.*)(\\b)+UPDATE(\\b)+\\s.*(.*)",
        "(?i)(.*)(\\b)+DELETE(\\b)+\\s.*(\\b)+FROM(\\b)+\\s.*(.*)",
        "(?i)(.*)(\\b)+UPSERT(\\b)+\\s.*(.*)",
        "(?i)(.*)(\\b)+SAVEPOINT(\\b)+\\s.*(.*)",
        "(?i)(.*)(\\b)+CALL(\\b)+\\s.*(.*)",
        "(?i)(.*)(\\b)+ROLLBACK(\\b)+\\s.*(.*)",
        "(?i)(.*)(\\b)+KILL(\\b)+\\s.*(.*)",
        "(?i)(.*)(\\b)+SLEEP(\\b)+\\s.*(.*)",
        "(?i)(.*)(\\b)+DROP(\\b)+\\s.*(.*)",
        "(?i)(.*)(\\b)+CREATE(\\b)+(\\s)*(" + SQL_TYPES.replaceAll(",", "|") + ")(\\b)+\\s.*(.*)",
        "(?i)(.*)(\\b)+ALTER(\\b)+(\\s)*(" + SQL_TYPES.replaceAll(",", "|") + ")(\\b)+\\s.*(.*)",
        "(?i)(.*)(\\b)+TRUNCATE(\\b)+(\\s)*(" + SQL_TYPES.replaceAll(",", "|") + ")(\\b)+\\s.*(.*)",
        "(?i)(.*)(\\b)+LOCK(\\b)+(\\s)*(" + SQL_TYPES.replaceAll(",", "|") + ")(\\b)+\\s.*(.*)",
        "(?i)(.*)(\\b)+UNLOCK(\\b)+(\\s)*(" + SQL_TYPES.replaceAll(",", "|") + ")(\\b)+\\s.*(.*)",
        "(?i)(.*)(\\b)+RELEASE(\\b)+(\\s)*(" + SQL_TYPES.replaceAll(",", "|") + ")(\\b)+\\s.*(.*)",
        "(?i)(.*)(\\b)+DESC(\\b)+(\\w)*\\s.*(.*)",
        "(?i)(.*)(\\b)+DESCRIBE(\\b)+(\\w)*\\s.*(.*)",
        "(.*)(/\\*|\\*/|;){1,}(.*)",
        "(.*)(-){2,}(.*)",};
    // pre-build the Pattern objects for faster validation

    private ServletContext context;

    private static List<Pattern> validationPatterns = getValidationPatterns();

    @Override
    public void init(FilterConfig fConfig) throws ServletException {
        validationPatterns = getValidationPatterns();
        this.context = fConfig.getServletContext();
        this.context.log("SqlInjectionFilter initialized");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        Enumeration<String> params = req.getParameterNames();
        Boolean validReq = true;
        String sqlVal = "";
        while (params.hasMoreElements() && validReq) {
            String name = params.nextElement();
            String value = request.getParameter(name);
            //this.context.log(req.getRemoteAddr() + "::Request Params::{" + name + "=" + value + "}");

            validReq = isValid(value);
            sqlVal = !validReq ? value : "";
            //log.info("VALIDATe: "+ value +" : "+validReq);
        }

        if (validReq) {
            chain.doFilter(request, response);
        } else {
            PrintWriter out = response.getWriter();
            out.println(new Gson().toJson(new JsonResponse("01", "INVALID REQUEST PASSED")));
            this.context.log("SQL injection detected " + sqlVal);
        }

//        Cookie[] cookies = req.getCookies();
//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//                this.context.log(req.getRemoteAddr() + "::Cookie::{" + cookie.getName() + "," + cookie.getValue() + "}");
//            }
//        }
        // pass the request along the filter chain
        //chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        //we can close resources here
    }

    public boolean isValid(String dataString) {
        return isSqlInjectionSafe(dataString);
    }

    private boolean isSqlInjectionSafe(String dataString) {
        if (isEmpty(dataString)) {
            return true;
        }

        if (dataString.contains("sleep")) {
            return false;
        }

        //log.info("patterns: "+ validationPatterns);
        return validationPatterns.stream().noneMatch((pattern) -> (matches(pattern, dataString)));
    }

    private boolean matches(Pattern pattern, String dataString) {
        Matcher matcher = pattern.matcher(dataString);
        return matcher.matches();
    }

    private static List<Pattern> getValidationPatterns() {
        List<Pattern> patterns = new ArrayList<>();
        for (String sqlExpression : sqlRegexps) {
            patterns.add(getPattern(sqlExpression));
        }
        return patterns;
    }

    private static Pattern getPattern(String regEx) {
        return Pattern.compile(regEx, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
    }

    private boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }
}
