/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etzgh.xportal.controller;

import com.etzgh.xportal.model.AppCredential;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author sunkwa-arthur
 */
@WebServlet(name = "AppRouter", urlPatterns = {"/AppRouter"})
public class AppRouter extends HttpServlet {

    private static final long serialVersionUID = -2478217562110587963L;
    private static final Logger log = Logger.getLogger(AppRouter.class.getName());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        AppCredential appUser = (AppCredential) request.getSession().getAttribute("appUser");

        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            String extra = request.getParameter("extra");
            if (extra == null || extra.equals("")) {
                extra = "";
            }
            if (appUser != null) {

                String role = appUser.getRole();
                if (role.equalsIgnoreCase("0")) {
                    response.sendRedirect(request.getContextPath() + "/#!/AppUserLogin");
                } else if (role.equalsIgnoreCase("1") && extra.equals("view_rvsl")) {
                    response.sendRedirect(request.getContextPath() + "/#!/ReversalTransactions");
                } else if (role.equalsIgnoreCase("1")) {
                    response.sendRedirect(request.getContextPath() + "/#!/Administrator");
                } else if (role.equalsIgnoreCase("2")) {
                    response.sendRedirect(request.getContextPath() + "/#!/InitiateReversal");
                } else if (role.equalsIgnoreCase("3")) {
                    response.sendRedirect(request.getContextPath() + "/#!/AppUserLogin");
                } else if (role.equalsIgnoreCase("4")) {
                    response.sendRedirect(request.getContextPath() + "/#!/AuthoriseReversal");
                } else if (role.equalsIgnoreCase("5")) {
                    response.sendRedirect(request.getContextPath() + "/#!/AppUserLogin");
                }
            } else {
                response.sendRedirect(request.getContextPath() + "/#!/AppUserLogin");
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            log.error("error", ex);
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
