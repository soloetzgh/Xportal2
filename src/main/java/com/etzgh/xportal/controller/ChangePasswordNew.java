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
import com.etzgh.xportal.model.JsonResponse;
import com.etzgh.xportal.utility.PasswordGenerator;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.json.JSONObject;

/**
 *
 * @author Eugene
 */
public class ChangePasswordNew extends HttpServlet {

    private static final long serialVersionUID = 3027164229559718673L;

    private static final Logger log = Logger.getLogger(ChangePasswordNew.class.getName());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserSession userSession;
        userSession = (UserSession) request.getSession().getAttribute("userSession");
        response.setContentType("application/json");
        final String ip_address = (String) request.getAttribute("ipAddress");
        try (PrintWriter out = response.getWriter()) {
            String oldPassword = request.getParameter("oldPwd");
            String newPassword = request.getParameter("newPwd");
            String confirmPassword = request.getParameter("confPwd");
            String email = request.getParameter("email");

            String userId = userSession.getUser().getUser_id();
            String userName = userSession.getUser().getUsername();

            if (oldPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty() || email.isEmpty()) {

                out.println(new Gson().toJson(new JsonResponse("01", "Enter values for all fields")));
            } else {
                if (!newPassword.equals(confirmPassword)) {
                    out.println("New password and confirmation password are not the same");
                    out.println(new Gson().toJson(new JsonResponse("01", "New password and confirmation password are not the same")));
                } else {
                    if (!PasswordGenerator.validatePassword(newPassword)) {
                        String validText = "PASSWORD MUST BE AT LEAST 12 CHARACTERS CONTAIN AT LEAST ONE UPPERCASE LETTER, AT LEAST ONE NUMBER AND AT LEAST ONE OF THE FOLLOWING SYMBOLS [@#_*$%^?&.+=]".toLowerCase();

                        out.println(new Gson().toJson(new JsonResponse("01", validText)));
                    } else if (!PasswordGenerator.containDetails(newPassword, userSession.getUser())) {
                        out.println(new Gson().toJson(new JsonResponse("01", "Password must not contain logon Id or personal name(s)")));
                    } else {
                        final JSONObject res = new AppDao().changePassword(userId, oldPassword, newPassword, userName, email, true, "");
                        new Thread(() -> {
                            AuditTrail audit = new AuditTrail(userId, res.getString("message"), "authentication", null, "Password Change", ip_address);
                            new AuditDao().insertIntoAuditTrail(audit);
                        }).start();
                        out.println(res.toString());
                    }
                }

            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            log.error("error", ex);
        }
    }
}
