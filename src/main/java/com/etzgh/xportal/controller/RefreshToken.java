package com.etzgh.xportal.controller;

import com.etzgh.xportal.cdi.UserSession;
import com.etzgh.xportal.model.JsonResponse;
import com.etzgh.xportal.utility.TokenUtil;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

/**
 * @author sunkwa-arthur
 */
// @WebServlet(name = "MerchantsInfo", urlPatterns = {"/MerchantsInfo"})
public class RefreshToken extends HttpServlet {

    private static final long serialVersionUID = 2779093570295166493L;

    final Properties props = new Properties();
    private static final Logger log = Logger.getLogger(RefreshToken.class.getName());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            UserSession userSession = (UserSession) request.getSession().getAttribute("userSession");

            try (PrintWriter out = response.getWriter()) {

                // Assume its HTTP
                HttpServletRequest httpReq = (HttpServletRequest) request;
                HttpServletResponse httpRes = (HttpServletResponse) response;

                HttpSession session = httpReq.getSession(false);

                // log.info("LOAD SALT");
                if (userSession != null) {

                    TokenUtil.processToken(httpReq, httpRes, false, null);

                    out.println(new Gson().toJson(new JsonResponse("00", "Refresh Successful")));
                } else {
                    out.println(new Gson().toJson(new JsonResponse("01", "Session Expired")));
                }

            }
        } catch (Exception e) {
            log.info("XPORTAL ERROR ::: ", e);
        }

    }

    public static boolean isNumeric(String inputData) {
        return inputData.matches("[-+]?\\d+(\\.\\d+)?");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            setAccessControlHeaders(response);
            processRequest(request, response);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    // for Preflight
    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            setAccessControlHeaders(resp);
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    private void setAccessControlHeaders(HttpServletResponse resp) {
        // resp.setHeader("Access-Control-Allow-Origin", "*");
        try {
            resp.setHeader("Access-Control-Allow-Methods", "GET");
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    public Boolean testEtzUsers(String typeId, String desc) {
        Boolean result = false;
        if (desc.split(" ")[0].equals("ETZ") || typeId.equals("1") || typeId.equals("2")) {
            result = true;
        }
        return result;
    }
}
