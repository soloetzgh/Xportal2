/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etzgh.xportal.controller;

import com.etzgh.xportal.cdi.UserSession;
import com.etzgh.xportal.dao.AppDao;
import com.etzgh.xportal.dao.AuditDao;
import com.etzgh.xportal.model.AuditTrail;
import com.etzgh.xportal.model.User;
import com.etzgh.xportal.utility.EncryptRSAUtil;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.json.JSONObject;

/**
 *
 * @author sunkwa-arthur
 */
public class LoginAuthenticator extends HttpServlet {

    private static final long serialVersionUID = 4977827838928295460L;
    private static final String ERROR = "error";

    private static final Logger log = Logger.getLogger(LoginAuthenticator.class.getName());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserSession userSession = new UserSession();

        final String ip_address = (String) request.getAttribute("ipAddress");
        final String requestOrigin = (String) request.getAttribute("Origin");
        response.setHeader("Content-Type", "application/json");

        String msg = "";

        try (PrintWriter out = response.getWriter()) {

            String payload = request.getParameter("payload");
            String pvv = new PortalSettings().getSetting("priv_key").split(":")[0];

            JSONObject respMsg = new JSONObject();
            try {
                String hj = EncryptRSAUtil.decrypt(payload, pvv);

                JSONObject json = new JSONObject(hj);

                String username = json.optString("username");
                String password = json.optString("password");

                respMsg = new JSONObject(new AppDao().login(username, password, requestOrigin));
                if (respMsg.optBoolean("userExists", false)) {
                    Gson gson = new Gson();

                    User user = gson.fromJson(respMsg.optString("userData", ""), User.class);
                    if (respMsg.getString("message").toLowerCase().contains("successful")) {

                        userSession.setUser(user);

                        userSession.setCreated(new Date());

                        request.getSession().setAttribute("userSession", userSession);
                        request.getSession(false).setAttribute("userSessionUserId", user.getUser_id());

                        HttpSessionCollector.saveSession(user.getUser_id(), request.getSession(false).getId(), request.getSession(false));

                        msg = user.getIsLoggedIn() ? "Successful Login" : "OTP Required";
                        respMsg.put("message", msg);

                        respMsg.put("userData", new JSONObject(user).toString());

                    } else {
                        msg = respMsg.optString("message", "An error occured");
                    }
                    final String userId = respMsg.getString("userId");
                    final String message = msg;
                    new Thread(() -> {
                        AuditTrail audit = new AuditTrail(userId, message, "authentication", null, "Login Attempt", ip_address);
                        new AuditDao().insertIntoAuditTrail(audit);
                    }).start();
                }
            } catch (Exception er) {
                log.error(ERROR, er);
            }

            out.println(respMsg.toString());

        }
    }

    public static String checkFirstRole(String roles) {
        String oneRole = "";
        try {
            if (!roles.isEmpty()) {
                Object[] access = roles.split(",");
                log.info("objs: " + access[1]);
                Object[] accessTypes = access[1].toString().split("[|]");
                log.info("Data: " + accessTypes[0]);

                oneRole = accessTypes[0].toString();
            }
        } catch (Exception r) {
            log.error(ERROR, r);
        }
        return oneRole;

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        setAccessControlHeaders(response);
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            log.error(ERROR, ex);
        }
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        setAccessControlHeaders(resp);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    private void setAccessControlHeaders(HttpServletResponse resp) {

    }

}
