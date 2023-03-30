package com.etzgh.xportal.utility;

/**
 * @author sunkwa-arthur
 */
import com.etzgh.xportal.cdi.UserSession;
import com.etzgh.xportal.controller.PortalSettings;
import com.etzgh.xportal.model.JsonResponse;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
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

@WebFilter("/AuthenticationFilter")
public class AuthenticationFilter implements Filter {

    private ServletContext context;
    private static final Logger log = Logger.getLogger(AuthenticationFilter.class.getName());
    private static final Config config = new Config();
    private static final Map<String, Integer> periodMap = new HashMap<>();

    private static final String[] ignoreExt = {
        ".css", ".js", ".html"
    };
    private static final String[] ignorePath = {
        "/api/UserProfile", "/api/PortalSettings", "/api/Authenticator", "/api/Logout", "/api/ValidateOtp", "/api/ExportPdf"
    };
    private static final String[] ignoreQueryString = {
        "action=menuList", "action=portalSetting"
    };
    private static final String ERROR = "ERROR";

    static {
        periodMap.put("minute", (60));
        periodMap.put("hour", (60 * 60));
        periodMap.put("day", (60 * 60 * 24));
        periodMap.put("week", (60 * 60 * 24 * 7));
        periodMap.put("month", (60 * 60 * 24 * 7 * 4));
        periodMap.put("year", (60 * 60 * 24 * 7 * 4 * 12));

    }

    @Override
    public void init(FilterConfig fConfig) throws ServletException {
        this.context = fConfig.getServletContext();
        this.context.log("AuthenticationFilter initialized");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        UserSession userSession = null;
        String uri = req.getRequestURI();
        Boolean requires2fa = false;
        Boolean isLoggedIn = false;

        HttpSession session = req.getSession(false);

        String path = uri.substring(req.getContextPath().length());

        if (session != null && session.getAttribute("userSession") != null) {
            userSession = (UserSession) session.getAttribute("userSession");
            requires2fa = userSession.getUser().getRequires2FA();
            isLoggedIn = userSession.getUser().getIsLoggedIn();
        }

        if (session == null && isLoginRequired(path)) {
            try (PrintWriter out = response.getWriter()) {
                resp.setHeader("Content-Type", "application/json");
                out.println(new Gson().toJson(new JsonResponse("01", "Session Expired")));
            }

        } else if (requires2fa && !isLoggedIn && isLoginRequired(path)) {

            try (PrintWriter out = response.getWriter()) {
                out.println(new Gson().toJson(new JsonResponse("01", "OTP Required")));
            }
        } else {
            if (session != null) {
                session.setMaxInactiveInterval(getSessionTimeout());
            }
            chain.doFilter(request, response);
        }

    }

    @Override
    public void destroy() {
    }

    private int getSessionTimeout() {
        int sessionTimeout = 15 * 60;
        try {
            String timeOut = new PortalSettings().getSetting("session_timeout");
//            log.info("TIMEOUT::: " + timeOut);
            String status = timeOut.split(":")[1];
//            log.info("ACTIVE TIMEOUT: " + status);
            if (status.equals("1")) {
                float data = Float.parseFloat((timeOut.split("-")[0]));
                String data1 = timeOut.split("-").length > 1 ? timeOut.split("-")[1].split(":")[0] : "minute";

//                log.info("CALC TIMEOUT: " + data + " " + data1);
                sessionTimeout = (int) data * periodMap.getOrDefault(data1, 1);
            }
        } catch (Exception e) {
            log.error(ERROR, e);
            sessionTimeout = 15 * 60;
        }
        return sessionTimeout;
    }

    private boolean isLoginRequired(String requestURL) {

        for (String loginRequiredURL : ignorePath) {
            if (requestURL == null) {
                requestURL = "";
            }

            if (requestURL.equals(loginRequiredURL)) {
                return false;
            }
        }

        return true;
    }

    private boolean allowQueryString(String queryString) {

        for (String qs : ignoreQueryString) {
            if (queryString == null) {
                return true;
            }
            log.info("Check: " + qs + " === " + queryString.equals(qs));
            if (queryString.equals(qs)) {
                return true;
            }
        }

        return false;
    }
}
