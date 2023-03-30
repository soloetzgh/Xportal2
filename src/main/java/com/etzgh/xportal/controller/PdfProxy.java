package com.etzgh.xportal.controller;

import com.etzgh.xportal.model.CardTransReport;
import com.etzgh.xportal.model.CorporatePay;
import com.etzgh.xportal.model.FundGateTransaction;
import com.etzgh.xportal.model.JsonResponse;
import com.etzgh.xportal.utility.CryptographerMin;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static net.sf.dynamicreports.report.builder.DynamicReports.report;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.log4j.Logger;

/**
 *
 * @author sunkwa-arthur
 */
public class PdfProxy extends HttpServlet {

    private static final Logger log = Logger.getLogger(PdfProxy.class.getName());

    static String reportsPath = "";

    static {
        reportsPath = new File("reports" + File.separator).getAbsolutePath();
    }

    public static Map<String, String> splitParams(String query) {
        Map<String, String> query_pairs = new LinkedHashMap<>();

        String[] pairs = query.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            query_pairs.put(pair.substring(0, idx), pair.substring(idx + 1));
        }
        return query_pairs;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/pdf");

        String extra_info = request.getHeader("app-token");

        String info = CryptographerMin.decodeDecryptToString(extra_info);

        Map<String, String> params = splitParams(info);

        String rawJsonData = request.getParameterMap().containsKey("data") ? request.getParameter("data") : "";

        OutputStream servletOutputStream = response.getOutputStream();

        String mac = params.getOrDefault("mac", "");
        String action = params.getOrDefault("action", "");

        System.out.println("DECODING ::: " + rawJsonData);

        String newMac = CryptographerMin.generateMac(rawJsonData);

        if (newMac.equalsIgnoreCase(mac)) {

            if (!rawJsonData.isEmpty()) {
                InputStream is = null;
                try {

                    BigDecimal totalAmt = BigDecimal.ZERO, totalSuccess = BigDecimal.ZERO, totalFail = BigDecimal.ZERO, totalRev = BigDecimal.ZERO;

                    String startDate = params.getOrDefault("start", "");
                    String endDate = params.getOrDefault("end", "");
                    String report = params.getOrDefault("report", "");

                    String reportTitle = params.getOrDefault("title", "");
                    Map<String, Object> parameters = new HashMap<>();
                    parameters.put("startDate", startDate);
                    parameters.put("endDate", endDate);

                    parameters.put("context", reportsPath + File.separator);

                    is = new FileInputStream(new File("reports" + File.separator + report + "template.jrxml"));

                    JRBeanCollectionDataSource jrb = null;

                    String fileName = "";
                    if (action.equalsIgnoreCase("pdf")) {
                        switch (report) {
                            case "cardtrans":
                                fileName = "Card Transactions Report";

                                List<CardTransReport> records = new Gson().fromJson(rawJsonData, new TypeToken<List<CardTransReport>>() {
                                }.getType());

                                jrb = new JRBeanCollectionDataSource(records);

                                parameters.put("card_name", params.getOrDefault("card_name", ""));
                                parameters.put("card_number", params.getOrDefault("card_number", ""));
                                parameters.put("reportTitle", reportTitle);
                                break;
                            case "cpaycardtrans":
                                fileName = "Card Transactions Report";
                                List<CardTransReport> records1 = new Gson().fromJson(rawJsonData, new TypeToken<List<CardTransReport>>() {
                                }.getType());

                                parameters.put("reportTitle", reportTitle);
                                jrb = new JRBeanCollectionDataSource(records1);
                                break;

                            case "corporatepay":
                                fileName = "CorporatePay Report";
                                List<CorporatePay> records2 = new Gson().fromJson(rawJsonData, new TypeToken<List<CorporatePay>>() {
                                }.getType());

                                for (CorporatePay f : records2) {
                                    if (f.getStatus() != null) {
                                        if (f.getStatus().equalsIgnoreCase("successful")) {
                                            totalSuccess = totalSuccess.add(f.getAmount());
                                        }
                                        if (f.getStatus().equalsIgnoreCase("failed")) {
                                            totalFail = totalFail.add(f.getAmount());
                                        }
                                        if (f.getStatus().equalsIgnoreCase("reversed")) {
                                            totalRev = totalRev.add(f.getAmount());
                                        }
                                    }

                                }
                                totalAmt = totalAmt.add(totalRev);
                                totalAmt = totalAmt.add(totalFail);
                                totalAmt = totalAmt.add(totalSuccess);
                                parameters.put("totalSuccessful", totalSuccess.toString());
                                parameters.put("totalAmount", totalAmt.toString());
                                parameters.put("totalReversed", totalRev);
                                parameters.put("totalFailed", totalFail);
                                parameters.put("reportTitle", reportTitle);

                                jrb = new JRBeanCollectionDataSource(records2);

                                break;

                            case "fundgate":
                                fileName = "Card Transactions Report";
                                List<FundGateTransaction> records3 = new Gson().fromJson(rawJsonData, new TypeToken<List<FundGateTransaction>>() {
                                }.getType());

                                parameters.put("reportTitle", reportTitle);
                                jrb = new JRBeanCollectionDataSource(records3);
                                break;

                            default:

                                break;
                        }
                    }

                    try {
                        response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".pdf");
                        report().setTemplateDesign(is)
                                .setDataSource(jrb)
                                .setParameters(parameters).toPdf(servletOutputStream);
                    } catch (DRException e) {
                        log.error("error", e);
                        response.getOutputStream().println(new Gson().toJson(new JsonResponse("01", "An Error Occured")));

                    } finally {
                        servletOutputStream.close();
                    }

                } catch (Exception e) {
                    log.error("error", e);
                } finally {
                    if (is != null) {
                        is.close();
                    }
                }
            } else {
                log.info("DATA IS NULL");
            }
        } else {
            log.info("mac check failed");
            response.getOutputStream().println(new Gson().toJson(new JsonResponse("01", "Security Check Failed")));
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

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
