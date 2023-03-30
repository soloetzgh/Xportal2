package com.etzgh.xportal.service;

import com.etzgh.xportal.dao.AppDao;
import com.etzgh.xportal.model.MenuOptions;
import com.etzgh.xportal.model.NODES;
import com.etzgh.xportal.model.RoleInfoData;
import com.etzgh.xportal.model.RoleOption;
import com.etzgh.xportal.model.User;
import com.etzgh.xportal.model.UserInfo;
import com.etzgh.xportal.model.UserManagement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.apache.log4j.Logger;

@Path("etz/gh/users/")
public class UserProfileService {

    private static AppDao appDao = new AppDao();
    private static final Logger log = Logger.getLogger(UserProfileService.class.getName());

    @GET
    public String welcome(String json) {
        return "WELCOME TO THE ETRANZACT XPORTAL WEBSERVICE";
    }

    @POST
    @Path("users")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<UserManagement> getUsers(String jsonString) {
        return appDao.getUsers(jsonString);
    }

    @POST
    @Path("status")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String changeUserStatus(String jsonString) {
        return appDao.changeUserStatus(jsonString);
    }

    @POST
    @Path("resetPasswordAdmin")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String resetPasswordAdmin(String jsonString) {
        return appDao.resetPasswordAdmin(jsonString);
    }

    @POST
    @Path("fetchUserRoles")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public RoleInfoData fetchUserRoles(String jsonString) {
        return appDao.fetchUserRoles(jsonString);
    }

    @POST
    @Path("createUser")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String createUser(String jsonString) {
        return appDao.createUser(jsonString);
    }

    @POST
    @Path("modifyUser")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String modifyUser(String jsonString) {
        return appDao.modifyUser(jsonString);
    }

    @POST
    @Path("modifyUserRoles")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String modifyUserRoles(String jsonString) {
        return appDao.modifyUserRoles(jsonString);
    }

    @POST
    @Path("checkUsername")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String checkUsers(String username) {
        return appDao.checkUsers(username);
    }

    @POST
    @Path("getAllUserRoles")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String getAllUserRoles(String userId) {
        return appDao.getAllUserRoles(userId);
    }

    @POST
    @Path("getUserRoleList")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String getUserRoleList(String userId) {
        return appDao.getUserRoleList(userId);
    }

    @POST
    @Path("getMenuList")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<MenuOptions> getMenuList() {
        return appDao.getMenuList();
    }

    @POST
    @Path("getUserMenuList")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<MenuOptions> getUserMenuList() {

        return appDao.getUserMenuList();
    }

    @POST
    @Path("getProviderList")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<NODES> getProviderList(String typeId, String userCode) {

        return appDao.getProviderList(typeId, userCode);
    }

    @POST
    @Path("getSenderList")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<NODES> getSenderList(String typeId, String userCode) {

        return appDao.getSenderList(typeId, userCode);
    }

    @POST
    @Path("getPortalClientSettings")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String getPortalClientSettings() {
        return appDao.getPortalClientSettings();
    }

    public User updateUserCode(User user) {
        try {
            appDao.updateLastLogin(user.getUser_id());
            user.setUser_code(getAllUserRoles(user.getUser_id()));
        } catch (Exception es) {
            log.error(es.getMessage(), es);
        }
        return user;
    }

    public ArrayList<String> validateRequest(UserInfo userData, int type) {

        ArrayList<String> errorMessages = new ArrayList<>();

        if (userData.getMobile() != null) {
            try {

                Long.valueOf(userData.getMobile());
                if (String.valueOf(userData.getMobile()).length() < 10) {
                    errorMessages.add("phoneNumber not complete");
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                errorMessages.add("phoneNumber should consist only numbers");
            }
        }

        try {
            if (!Pattern.compile("^(?!\\.)(?!\\-)(?!.*?\\.\\.)(?!.*?\\-\\-)(?!.*\\-$)[a-zA-Z .-]+$").matcher(userData.getFirstName()).matches()) {
                errorMessages.add("Invalid FirstName");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            errorMessages.add("Invalid FirstName");
        }
        try {
            if (!Pattern.compile("^(?!\\.)(?!\\-)(?!.*?\\.\\.)(?!.*?\\-\\-)(?!.*\\-$)[a-zA-Z .-]+$").matcher(userData.getLastName()).matches()) {
                errorMessages.add("Invalid LastName");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            errorMessages.add("Invalid LastName");
        }
        if (type != 2) {
            try {
                if (userData.getNewUsername().trim().isEmpty() || userData.getNewUsername() == null) {
                    errorMessages.add("Invalid Username");
                }
                if (!Pattern.compile("^(?!\\.)(?!\\-)(?!.*?\\.\\.)(?!.*?\\-\\-)(?!.*\\-$)[a-zA-Z .-]+$").matcher(userData.getNewUsername()).matches()) {
                    errorMessages.add("Invalid Username");
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                errorMessages.add("Invalid Username");
            }
        }

        return errorMessages;
    }

    public String getRoleParameters(String role, String rolesList) {
        String roleParam = "";
        if (!rolesList.isEmpty()) {
            try {
                int firstParam = rolesList.indexOf(role);
                int nextParam = rolesList.indexOf(",", firstParam);
                roleParam = rolesList.substring(firstParam, (nextParam == -1) ? rolesList.length() : nextParam).split("[|]")[1];
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        return roleParam;
    }

    public List<RoleOption> getSpecificRoleOptions(String role, String option) {
        return appDao.getSpecificRoleOptions(role, option);
    }

    public List<RoleOption> getRoleOptions(String role) {
        return appDao.getRoleOptions(role);
    }

    public List<NODES> getTypeIdList(boolean isAdmin, boolean isExtAdmin, String userId) {

        return appDao.getTypeIdList(isAdmin, isExtAdmin, userId);
    }

    public Boolean validateOtp(String userId, String otp) {
        return appDao.verify2FA(userId, otp);
    }
}
