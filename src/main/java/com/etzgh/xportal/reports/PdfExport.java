/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etzgh.xportal.reports;

import com.etzgh.xportal.cdi.UserSession;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JsonDataSource;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.j2ee.servlets.ImageServlet;
import org.apache.log4j.Logger;
import org.json.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author sunkwa-arthur
 */
public class PdfExport extends HttpServlet {

    private static final long serialVersionUID = -6314605360057983217L;
    private static final Logger log = Logger.getLogger(PdfExport.class.getName());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext context = this.getServletConfig().getServletContext();

        StringBuilder jb = new StringBuilder();
        String line = null;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                jb.append(line);
            }
        } catch (Exception e) {
            /*report an error*/
            log.error(e.getMessage(), e);
        }

        log.info("REQUEST :: " + jb.toString());
        try {
            JSONObject jsonObject = HTTP.toJSONObject(jb.toString());
        } catch (JSONException e) {

            throw new IOException("Error parsing JSON request string");
        }
        UserSession userSession;
        userSession = (UserSession) request.getSession().getAttribute("userSession");
        String action = request.getParameter("action");

        ServletOutputStream servletOutputStream = response.getOutputStream();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {

            String startDate = request.getParameter("start");
            String endDate = request.getParameter("end");
            String report = request.getParameter("report");

            String reportTitle = (userSession.getUser().getType_id().length() > 10) ? (userSession.getUser().getFirstname() + ' ' + userSession.getUser().getLastname()) : "";

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("startDate", startDate);
            parameters.put("endDate", endDate);

            log.info("REPORT: " + report);

            JasperReport jasperReport = JasperCompileManager.compileReport(context.getRealPath("/reports/" + report + "template.jrxml"));

            JasperPrint jasperPrint = new JasperPrint();

            String fileName = report + "Report";
            if (action.equalsIgnoreCase("pdf")) {

                switch (report) {
                    case "cardtrans":
                        parameters.put("reportTitle", reportTitle);
                        String rawJsonData = "[{\"name\":\"Jerry\", \"value\":\"Jesus\"},"
                                + "{\"name\":\"Gideon\", \"value\": \"Loves\"},"
                                + "{\"name\":\"Eva\", \"value\": \"You\"}"
                                + "]";

                        ByteArrayInputStream jsonDataStream = new ByteArrayInputStream(rawJsonData.getBytes());

                        jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JsonDataSource(jsonDataStream));
                        request.getSession().setAttribute(ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, jasperPrint);
                        break;

                    default:

                        break;
                }
                response.sendRedirect(request.getContextPath() + "/reports/pdf");
            }
            if (action.equalsIgnoreCase("html")) {
                response.setContentType("text/html");
                HtmlExporter exporter = new HtmlExporter();

                exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                SimpleHtmlExporterOutput output = new SimpleHtmlExporterOutput(servletOutputStream);

                exporter.setExporterOutput(output);

                exporter.exportReport();
            }
            if (action.equalsIgnoreCase("xlsx2")) {
                response.sendRedirect(request.getContextPath() + "/reports/FundGate/xlsx");
            }
            if (action.equalsIgnoreCase("docx")) {
                response.sendRedirect(request.getContextPath() + "/reports/FundGate/docx");
            }
            if (action.equalsIgnoreCase("pptx")) {
                response.sendRedirect(request.getContextPath() + "/reports/FundGate/pptx");
            }

            response.setContentLength(baos.size());
            baos.writeTo(servletOutputStream);

        } catch (JRException ex) {
            servletOutputStream.println("An error occured generation report" + ex);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            servletOutputStream.flush();
            servletOutputStream.close();
            baos.close();
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
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
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }

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
