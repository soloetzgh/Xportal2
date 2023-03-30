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
import com.etzgh.xportal.dao.AppDao;
import com.etzgh.xportal.model.JsonResponse;
import com.etzgh.xportal.model.PeriodData;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.log4j.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ValidatePeriod implements Filter {

    private static final Logger log = Logger.getLogger(ValidatePeriod.class.getName());
    private static final Map<String, Integer> periodMap = new HashMap<>();
    private static Map<String, PeriodData> periodRestriction = new HashMap<>();

    private static final String[] ignorePath = {
        "/api/Authenticator", "/api/ValidateOtp"
    };

    public static Map<String, PeriodData> convertListAfterJava8(List<PeriodData> list) {
        Map<String, PeriodData> map = list.stream().collect(Collectors.toMap((PeriodData::getPath), period -> period));
        return map;
    }

    static {
        periodMap.put("hour", (1000 * 60 * 60));
        periodMap.put("day", (1000 * 60 * 60 * 24));
        periodMap.put("week", (1000 * 60 * 60 * 24 * 7));
        periodMap.put("month", (1000 * 60 * 60 * 24 * 7 * 4));
        periodMap.put("year", (1000 * 60 * 60 * 24 * 7 * 4 * 12));

        periodRestriction = convertListAfterJava8(new AppDao().getPeriodValidation());

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // Assume its HTTP
        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpServletResponse httpRes = (HttpServletResponse) response;
        //String[] ignoreUrls = {"/etz/proxy"};
//        log.info("VALIDATE SALT URL : " + periodRestriction.toString());
        if (periodRestriction.isEmpty()) {
            chain.doFilter(httpReq, httpRes);
        } else {
            httpRes.setContentType("application/json");
            // Get the salt sent with the request
//            log.info("METHOD: " + httpReq.getMethod() + " - " + httpReq.getContextPath() + " - " + httpReq.getRequestURI() + " - " + httpReq.getPathInfo() + httpReq.getServletPath());
            String startDate = (request.getParameterMap().containsKey("startDate") ? request.getParameter("startDate") : "").trim();
            String endDate = (request.getParameterMap().containsKey("endDate") ? request.getParameter("endDate") : "").trim();
            PeriodData period = periodRestriction.getOrDefault(httpReq.getServletPath().substring(5).toUpperCase(), null);
//            System.out.println("PATH: " + httpReq.getServletPath().substring(5));

            if (period != null && (!startDate.isEmpty() || !endDate.isEmpty())) {
                //validate period
                PrintWriter out = httpRes.getWriter();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

                try {
                    Date start = sdf.parse(startDate);
                    Date end = sdf.parse(endDate);
                    long time_difference = end.getTime() - start.getTime();
                    int periodType = periodMap.getOrDefault(period.getType().toLowerCase(), 1);
                    int periodValue = Integer.valueOf(period.getValue());
                    int diff = (int) (time_difference / periodType);
                    if (diff > periodValue) {
                        out.println(new Gson().toJson(new JsonResponse("01", "Max Search Period Exceeded. Please Search with " + periodValue + " " + period.getType().toLowerCase() + " intervals")));
                    } else {
                        chain.doFilter(httpReq, httpRes);
                    }

                } catch (Exception e) {
                    log.error("VALIDATE PERIOD ERROR", e);
                    out.println(new Gson().toJson(new JsonResponse("01", "Invalid Date Range")));

                }

            } else {
                chain.doFilter(httpReq, httpRes);
            }
        }

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
