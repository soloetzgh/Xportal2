package com.etzgh.xportal.controller;

import com.etzgh.xportal.cdi.UserSession;
import com.etzgh.xportal.dao.AuditDao;
import com.etzgh.xportal.model.AuditTrail;
import com.etzgh.xportal.model.JsonResponse;
import com.etzgh.xportal.model.User;
import com.etzgh.xportal.service.UserProfileService;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import org.apache.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author sunkwa-arthur
 */
public class ValidateOtp extends HttpServlet {

    private static final long serialVersionUID = 2779093570295166493L;

    private static final UserProfileService roleService = new UserProfileService();

    private static final Logger log = Logger.getLogger(ValidateOtp.class.getName());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserSession userSession;
        response.setContentType("application/json;charset=UTF-8");
        final String ip_address = (String) request.getAttribute("ipAddress");
        try {
            userSession = (UserSession) request.getSession().getAttribute("userSession");
            final String userId = userSession.getUser().getUser_id();

            String otp = request.getParameterMap().containsKey("otp") ? request.getParameter("otp") : "";
            String resp = "Invalid OTP";
            String code = "01";
            try (PrintWriter out = response.getWriter()) {
                if (otp != null && !otp.isEmpty()) {

                    Boolean valid = roleService.validateOtp(userId, otp);
                    if (valid) {
                        User user = userSession.getUser();
                        user.setIsLoggedIn(true);
                        userSession.setUser(user);
                        request.getSession().setAttribute("userSession", userSession);
                    }
                    resp = valid ? "OTP verified successfully" : "Invalid OTP";
                    code = valid ? "00" : "01";
                }

                final String res = resp;
                new Thread(() -> {
                    AuditTrail audit = new AuditTrail(userId, res, "authentication", null, "OTP Verification",
                            ip_address);
                    new AuditDao().insertIntoAuditTrail(audit);
                }).start();

                out.println(new Gson().toJson(new JsonResponse(code, resp)));

            }
        } catch (Exception e) {
            log.error("VALIDATE OTP ERROR ::: {0}", e);
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
            log.error("Get", ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            log.error("Post", ex);
        }
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            setAccessControlHeaders(resp);
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception ex) {
            log.error("options", ex);
        }
    }

    private void setAccessControlHeaders(HttpServletResponse resp) {

        try {
            resp.setHeader("Access-Control-Allow-Methods", "GET");
        } catch (Exception ex) {
            log.error("accessControlHeaders", ex);
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
