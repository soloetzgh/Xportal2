package com.etzgh.xportal.service;

import com.etzgh.xportal.dao.AppDao;
import com.etzgh.xportal.model.PortalSettingsData;
import com.etzgh.xportal.utility.Config;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.apache.log4j.Logger;
import org.json.JSONObject;

@Path("etz/gh/settings/")
public class PortalSettingService {

    private static final Config config = new Config();
    private static final Logger log = Logger.getLogger(PortalSettingService.class.getName());

    @GET
    public String welcome(String json) {
        return "WELCOME TO THE ETRANZACT XPORTAL WEBSERVICE";
    }

    @POST
    @Path("portalSettings")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<PortalSettingsData> getPortalSettings(String jsonString) {
        return new AppDao().getPortalSettings(jsonString);
    }

    @POST
    @Path("updatePortalSettings")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String updatePortalSettings(String jsonString) {
        return new AppDao().updatePortalSettings(jsonString);
    }

    @POST
    @Path("changePortalSettingsStatus")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String changePortalSettingsStatus(String jsonString) {
        return new AppDao().changePortalSettingsStatus(jsonString);
    }

    @POST
    @Path("runChangePassword")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String runChangePassword(String jsonString) {
        return new AppDao().runChangePassword(jsonString);
    }

    @POST
    @Path("runInactiveAccount")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String runInactiveAccount(String jsonString) {
        return new AppDao().runInactiveAccount(jsonString);
    }

    public void changePasswordSchedule() {

        String change_password = new AppDao().getSetting("change_password");

        if (change_password != null) {
            String data = (change_password.split("-")[0]);
            String data1 = change_password.split("-").length > 1 ? change_password.split("-")[1] : "default";
            String status = change_password.split(":")[1];
            if (status.equals("1")) {

                if (data1.equals("default")) {

                    data = "60";
                    data1 = "day";
                }
                JSONObject jsonString = new JSONObject();
                jsonString.put("data", data);
                jsonString.put("data1", data1);

                new AppDao().runChangePassword(jsonString.toString());
            } else {
                log.info("change password duration disabled");
            }

        } else {
            log.info("CHANGE PASSWORD DURATION NOT SET");
        }
    }

    public void runInactiveAccountSchedule() {

        String inactive_account = new AppDao().getSetting("inactive_account");

        if (inactive_account != null) {

            String data = (inactive_account.split("-")[0]);
            String data1 = inactive_account.split("-").length > 1 ? inactive_account.split("-")[1] : "default";
            String status = inactive_account.split(":")[1];
            if (status.equals("1")) {

                if (data1.equals("default")) {

                    data = "30";
                    data1 = "day";
                }
                JSONObject jsonString = new JSONObject();
                jsonString.put("data", data);
                jsonString.put("data1", data1);

                new AppDao().runInactiveAccount(jsonString.toString());
            } else {
                log.info("INACTIVE ACCOUNT duration disabled");
            }

        } else {
            log.info("INACTIVE ACCOUNT DURATION NOT SET");
        }
    }

    public void runExpireOtpSchedule() {
        new AppDao().runExpireOtp();
    }

}
