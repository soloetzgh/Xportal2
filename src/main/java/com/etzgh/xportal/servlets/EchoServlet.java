package com.etzgh.xportal.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.*;

public class EchoServlet implements Servlet {

    public void init(ServletConfig config) throws ServletException {

    }

    public ServletConfig getServletConfig() {

        return null;
    }

    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        PrintWriter writer = res.getWriter();
        writer.write("ECHO World");
        writer.flush();
    }

    public String getServletInfo() {

        return null;
    }

    public void destroy() {

    }
}
