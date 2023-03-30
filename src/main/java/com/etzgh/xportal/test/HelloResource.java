package com.etzgh.xportal.test;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.apache.log4j.Logger;

@Path("/callback")
public class HelloResource {

    private static final Logger log = Logger.getLogger(HelloResource.class.getName());

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String sayHello() {
        return "Hello World from HelloResource";
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String test(String json) {
        log.info("CALLBACK ::: " + json);
        return "";
    }
}
