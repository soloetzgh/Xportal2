/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etzgh.xportal.controller;

import com.etzgh.xportal.cdi.UserSession;
import com.etzgh.xportal.model.JsonResponse;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author Eugene
 */
public class UserProfile extends HttpServlet {

    private static final long serialVersionUID = 5453185101186112309L;

    private static final Logger log = Logger.getLogger(UserProfile.class.getName());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserSession userSession;
        userSession = (UserSession) request.getSession().getAttribute("userSession");
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            if (userSession != null) {
                out.println(new Gson().toJson(userSession.getUser()));
            } else {
                out.println(new Gson().toJson(new JsonResponse("01", "Session Expired")));
            }

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
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }

}
