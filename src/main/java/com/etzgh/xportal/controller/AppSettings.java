package com.etzgh.xportal.controller;

import com.etzgh.xportal.model.JsonResponse;
import com.etzgh.xportal.model.MenuOptions;
import com.etzgh.xportal.service.UserProfileService;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 * @author sunkwa-arthur
 */
public class AppSettings extends HttpServlet {

    private static final long serialVersionUID = 2779093570295166493L;

    private final UserProfileService roleService = new UserProfileService();

    private static final Logger log = Logger.getLogger(AppSettings.class.getName());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        String action = request.getParameterMap().containsKey("action") ? request.getParameter("action") : "";
        try (PrintWriter out = response.getWriter()) {
            if (action.equalsIgnoreCase("menuList")) {

                List<MenuOptions> menuItems = roleService.getUserMenuList();
                out.println(new Gson().toJson(menuItems));
            } else if (action.equalsIgnoreCase("config")) {
                out.println(roleService.getPortalClientSettings());
            } else if (action.equalsIgnoreCase("refresh")) {
                PortalSettings.refreshPortalSettings();
                out.println(new Gson().toJson(new JsonResponse("00", "Success")));
            }

        } catch (Exception e) {

            log.error("PORTAL ERROR ::: ", e);
        }

    }

    public static boolean isNumeric(String inputData) {
        return inputData.matches("[-+]?\\d+(\\.\\d+)?");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        setAccessControlHeaders(response);
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

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        setAccessControlHeaders(resp);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    private void setAccessControlHeaders(HttpServletResponse resp) {

        resp.setHeader("Access-Control-Allow-Methods", "GET");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    public Boolean testEtzUsers(String typeId, String desc) {
        Boolean result = false;
        if (desc.split(" ")[0].equals("ETZ") || typeId.equals("1") || typeId.equals("2")) {
            result = true;
        }
        return result;
    }
}
