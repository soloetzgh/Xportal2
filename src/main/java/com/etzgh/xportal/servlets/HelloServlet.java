package com.etzgh.xportal.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.*;
import javax.servlet.http.HttpServlet;

public class HelloServlet extends HttpServlet {

    public void init(ServletConfig config) throws ServletException {

    }

    public ServletConfig getServletConfig() {

        return null;
    }

    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        PrintWriter writer = res.getWriter();
        writer.write("Hello World");
        writer.flush();
    }

    public String getServletInfo() {

        return null;
    }

    public void destroy() {

    }
}
