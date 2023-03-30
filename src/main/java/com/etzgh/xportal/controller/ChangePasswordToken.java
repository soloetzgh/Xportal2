/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etzgh.xportal.controller;

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
public class ChangePasswordToken extends HttpServlet {

    private static final long serialVersionUID = 3027164229559718673L;

    private static final Logger log = Logger.getLogger(ChangePasswordNew.class.getName());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json;charset=UTF-8");
        final String ip_address = (String) request.getAttribute("ipAddress");
        final String requestOrigin = (String) request.getAttribute("Origin");
        try (PrintWriter out = response.getWriter()) {
            String token = request.getParameter("token");
            String newPassword = request.getParameter("newPwd");
            String confirmPassword = request.getParameter("confPwd");
            String action = request.getParameter("action");

            if (action.equals("resetAccount")) {
                if (newPassword.isEmpty() || confirmPassword.isEmpty()) {

                    out.println(new Gson().toJson(new JsonResponse("01", "Enter values for all fields")));

                } else if (!newPassword.equals(confirmPassword)) {

                    out.println(new Gson()
                            .toJson(new JsonResponse("01", "New password and confirmation password are not the same")));

                } else if (!PasswordGenerator.validatePassword(newPassword)) {
                    String validText = "PASSWORD MUST BE AT LEAST 12 CHARACTERS CONTAIN AT LEAST ONE UPPERCASE LETTER, AT LEAST ONE NUMBER AND AT LEAST ONE OF THE FOLLOWING SYMBOLS [@#_*$%^?&.+=]"
                            .toLowerCase();

                    out.println(new Gson().toJson(new JsonResponse("01", validText)));

                } else {

                    final JSONObject resp = new AppDao().changePasswordWithToken(token, confirmPassword, requestOrigin);
                    if (!resp.optString("user_id", "").isEmpty()) {
                        new Thread(() -> {
                            AuditTrail audit = new AuditTrail(resp.getString("user_id"), resp.getString("message"),
                                    "authentication", null, "Password Change", ip_address);
                            new AuditDao().insertIntoAuditTrail(audit);
                        }).start();
                    }
                    out.println(resp.toString());
                }

            } else if (action.equalsIgnoreCase("checkTokenValidity")) {
                out.println(new AppDao().checkTokenValidity(token));
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
