/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etzgh.xportal.controller;

import com.etzgh.xportal.cdi.UserSession;
import com.etzgh.xportal.dao.AppDao;
import com.etzgh.xportal.model.JsonResponse;
import com.etzgh.xportal.utility.DoHTTPRequest;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;

/**
 *
 * @author sunkwa-arthur
 */
public class VerificationProxy extends HttpServlet {

    private static final long serialVersionUID = 5767791483998272545L;
    private static final Logger log = Logger.getLogger(VerificationProxy.class.getName());
    final static PortalSettings portalSettings = new PortalSettings();
    private static String ghana_card_endpoint = "";

    static {
        ghana_card_endpoint = StringUtils.substringBeforeLast(new PortalSettings().getSetting("ghcard_endpoint"), ":");
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {

        UserSession userSession;
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {

            try {
                userSession = (UserSession) request.getSession().getAttribute("userSession");
                String service = request.getParameter("service");
                String ghanaCard = request.getParameterMap().containsKey("ghanaCardNumber") ? request.getParameter("ghanaCardNumber") : "";
                String base64Image = request.getParameterMap().containsKey("base64Image") ? request.getParameter("base64Image") : "";

                String userType = userSession.getUser().getType_id();

                String userCode = userSession.getUser().getUser_code();
//                String balanceType = request.getParameterMap().containsKey("type") ? request.getParameter("type") : "";

                boolean isAllowed = false;

                if (userType.contains("[74]")) {
                    String userRoles = new AppDao().getRoleParameters("[555]", userCode);
                    log.info("usrRoles: " + userRoles);

                    List<String> roles = Arrays.asList(userRoles.split("~"));
                    isAllowed = !roles.isEmpty();

                    System.out.println("CHECK : " + roles + " - " + isAllowed);
                }
                if (isAllowed) {

                    if (service.equalsIgnoreCase("verifyGhanaCard")) {

                        String[] formattedData = processImage(ghanaCard, base64Image);
                        if (formattedData[0].equals("00")) {
                            try {
                                String vReq = "{\n"
                                        + "    \"pinNumber\": \"" + formattedData[2] + "\",\n"
                                        + "    \"image\": \"" + formattedData[3] + "\",\n"
                                        + "    \"dataType\": \"" + formattedData[4] + "\",\n"
                                        + "    \"center\": \"BRANCHLESS\"\n"
                                        + "}";

//                                log.info("VERIFY REQ: "+ vReq);
                                String vResp = new DoHTTPRequest().post(ghana_card_endpoint, vReq);
//                                String vResp = new String(Files.readAllBytes(Paths.get("GhanaCard.json")));

                                JSONObject vRespObj = new JSONObject(vResp);

                                if (vRespObj.optString("code").equals("200")) {
                                    out.println(new Gson().toJson(new JsonResponse("00", "success", vRespObj.getJSONObject("personData").toString())));

                                } else {
                                    out.println(new Gson().toJson(new JsonResponse("01", vRespObj.optString("message", "Could not verify data"))));
                                }

                            } catch (Exception e) {
                                log.error(e.getMessage(), e);
                                out.println(new Gson().toJson(new JsonResponse("01", "An Error Occured")));
                            }
                        } else {
                            out.println(new Gson().toJson(new JsonResponse("01", formattedData[1])));
                        }
                    } else {
                        out.println(new Gson().toJson(new JsonResponse("01", "Invalid Service " + service)));
                    }

                } else {
                    out.println(new Gson().toJson(new JsonResponse("01", "Authorization Required")));
                }

            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }

    }

    public String[] processImage(String ghno, String base64Image) {
        String[] result = {"01", "An Error Occured Processing Image", "", "", ""};
        Pattern p = Pattern.compile("^GHA-\\d{9}-\\d{1}");

        try {
            boolean invalidImage = base64Image == null || base64Image.trim().isEmpty();
            boolean invalidGhno = ghno == null || ghno.trim().isEmpty() || !p.matcher(ghno.trim().toUpperCase()).find();
            if (invalidImage && invalidGhno) {
                result[1] = "Invalid Image and Ghana Card Number. Please try again.";
            } else if (invalidImage) {
                result[1] = "Invalid Image. Please retake picture";
            } else if (invalidGhno) {
                result[1] = "Invalid Ghana Card Number. Please try again";
            } else {
                ghno = ghno.trim().toUpperCase();
                String[] imageData = base64Image.split(",");
                result[0] = "00";
                result[1] = "Processed Image";
                result[2] = ghno;
                result[3] = imageData[1];
                result[4] = "JPG";
            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return result;
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
