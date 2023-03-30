package com.etzgh.xportal.controller;

import com.etzgh.xportal.model.AnyBean;
import com.etzgh.xportal.model.Comment;
import java.util.Arrays;
import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/mt")
public class TestController implements Controller {

    Logger log = LoggerFactory.getLogger(TestController.class);

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String test() {
        return "Hello Any Test";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/json")
    public AnyBean json() {
        return new AnyBean();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/comment")
    public List<Comment> comments() {
        return Arrays.asList(
                new Comment("A", "a"),
                new Comment("B", "b"),
                new Comment("C", "c")
        );
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/callback")
    public String comments(String c) {
        log.info("c={}", c);
        return c;
    }
}
