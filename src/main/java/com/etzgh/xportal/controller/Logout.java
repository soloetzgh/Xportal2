/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etzgh.xportal.controller;

import com.etzgh.xportal.cdi.UserSession;
import com.etzgh.xportal.dao.AuditDao;
import com.etzgh.xportal.model.AuditTrail;
import com.etzgh.xportal.model.JsonResponse;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

/**
 *
 * @author sunkwa-arthur
 */
public class Logout extends HttpServlet {

    private static final long serialVersionUID = 7417428408523571720L;
    private static final Logger log = Logger.getLogger(Logout.class.getName());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json");
        UserSession userSession = (UserSession) request.getSession().getAttribute("userSession");
        if (userSession != null) {
            final String ip_address = (String) request.getAttribute("ipAddress");
            final String userId = userSession.getUser().getUser_id();

            userSession.setUser(null);

            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
                new Thread(() -> {
                    AuditTrail audit = new AuditTrail(userId, "Logout successful", "authentication", null, "User Logout", ip_address);
                    new AuditDao().insertIntoAuditTrail(audit);
                }).start();
            }
        }
        try (PrintWriter out = response.getWriter()) {
            out.println(new Gson().toJson(new JsonResponse("00", "Logout successful")));
        }

    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            processRequest(request, response);
        } catch (Exception ex) {
            log.error("error", ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
