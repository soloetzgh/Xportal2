package com.etzgh.xportal.utility;

import com.etzgh.xportal.cdi.UserSession;
import com.etzgh.xportal.controller.PortalSettings;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONObject;

@WebFilter("/OriginFilter")
public class OriginFilter implements Filter {

    private ServletContext context;
    private static final Config config = new Config();
    private static String appPath = "";
    private static int appPort = 0;
    private static String appOrigin = "";
//

    static {
        appOrigin = config.getProperty("PORTAL_ORIGIN");
        appPort = Integer.valueOf(config.getProperty("APPLICATION_PORT"));
        appPath = config.getProperty("APPLICATION_API_PATH");
    }

    private static final String[] ignorePath = {
        "/api/Logout",};

    @Override
    public void init(FilterConfig fConfig) throws ServletException {
        this.context = fConfig.getServletContext();
        this.context.log("AuthenticationFilter initialized");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String ipAddress = IpAddress.getClientIpAddress(req);
        request.setAttribute("ipAddress", ipAddress);
//        resp.setHeader("Access-Control-Expose-Headers", "Set-Cookie");

//        addSameSiteCookieAttribute(resp);
//        log.log(Level.INFO, "REQUEST IP: {0}", ipAddress);
        UserSession userSession = null;
        String uri = req.getRequestURI();
        String requestOrigin = appOrigin.isEmpty() ? req.getHeader("Host") : appOrigin;
        request.setAttribute("Origin", requestOrigin);
//        System.out.println("ORIGIN : " + requestOrigin);
//        System.out.println("ORIGIN : " + GeneralUtility.getRequestHeadersInfo(req));

        HttpSession session = req.getSession(false);

        String path = uri.substring(req.getContextPath().length());

//        log.info("Uri: " + uri);
//        log.info("PATH: " + path);
//        log.info("Context: " + req.getContextPath());
//        log.info("REQUESTURL: " + req.getRequestURL().toString());
//        log.info("Query String: " + req.getQueryString());
        if (session != null && session.getAttribute("userSession") != null) {
            userSession = (UserSession) session.getAttribute("userSession");
            userSession.getUser().getRequires2FA();
            userSession.getUser().getIsLoggedIn();
            userSession.getUser().getUser_code();
            userSession.getUser().getType_id();
        }

        if (userSession != null && isLoginRequired(path)) {
            JSONObject checkOrigin = GeneralUtility.checkOrigin(userSession.getUser(), requestOrigin);

            if (!checkOrigin.getString("code").equals("00")) {
                try (PrintWriter out = response.getWriter()) {
                    resp.setHeader("Content-Type", "application/json");
                    resp.setStatus(403);
                    out.println(checkOrigin.toString());
                }
            } else {
                chain.doFilter(request, response);
            }

        } else {
//            request.setAttribute("ipAddress", ipAddress);
            chain.doFilter(request, response);
        }

    }

    @Override
    public void destroy() {
        //close any resources here
    }

    private boolean isLoginRequired(String requestURL) {

        for (String loginRequiredURL : ignorePath) {
            if (requestURL == null) {
                requestURL = "";
            }
//            log.info("loginRequired: " + !requestURL.equals(loginRequiredURL));
            if (requestURL.equals(loginRequiredURL)) {
                return false;
            }
        }

        return true;
    }
}
