/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etzgh.xportal.controller;

import com.etzgh.xportal.dao.AppDao;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.apache.log4j.Logger;

/**
 *
 * @author Eugene
 */
public class PasswordReset extends HttpServlet {

    private static final long serialVersionUID = 7053426636756566188L;

    private static final Logger log = Logger.getLogger(PasswordReset.class.getName());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        JSONObject respMsg = new JSONObject();
        try (PrintWriter out = response.getWriter()) {
            String userName = request.getParameter("username");
            final String requestOrigin = (String) request.getAttribute("Origin");
            respMsg.put("code", "01");
            if (userName.isEmpty()) {
                respMsg.put("message", "Missing Fields");
                out.println(respMsg.toString());
            } else {
                respMsg = new AppDao().resetPassword(userName, requestOrigin, false);

                out.println(respMsg.toString());

            }
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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }

}
