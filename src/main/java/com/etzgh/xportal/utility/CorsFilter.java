package com.etzgh.xportal.utility;

/**
 * @author sunkwa-arthur
 */
import com.etzgh.xportal.cdi.UserSession;
import java.io.IOException;
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
public class CorsFilter implements Filter {

    private ServletContext context;
    private static final Logger log = Logger.getLogger(CorsFilter.class.getName());

    private static final String[] ignoreExt = {
        ".css", ".js", ".html"
    };
    private static final String[] ignorePath = {
        "/api/UserProfile", "/api/PortalSettings", "/api/Authenticator", "/api/Logout"
    };
    private static final String[] ignoreQueryString = {
        "action=menuList", "action=portalSetting"
    };

    @Override
    public void init(FilterConfig fConfig) throws ServletException {
        this.context = fConfig.getServletContext();
        this.context.log("CorsFilter initialized");
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

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

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
