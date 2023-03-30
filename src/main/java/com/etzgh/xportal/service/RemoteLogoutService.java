package com.etzgh.xportal.service;

import com.etzgh.xportal.controller.HttpSessionCollector;
import com.etzgh.xportal.model.UserData;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("etz/gh/logoutUser/")
public class RemoteLogoutService {

    @GET
    public String welcome(String json) {
        return "WELCOME TO THE ETRANZACT XPORTAL WEBSERVICE";
    }

    @POST
    @Path("allSessions")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String logoutUser(UserData userData) {

        HttpSessionCollector.removeAllUserSessions(userData.getUserId());
        return "done";
    }

    @POST
    @Path("otherSessions")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String logoutOtherUserSessions(UserData userData) {

        HttpSessionCollector.removeOtherUserSessions(userData.getUserId(), userData.getSessionId());
        return "done";
    }
}
