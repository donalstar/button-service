package com.donal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
@ApplicationPath("/api/*")
@Path("user")
public class UserResource {
    private FacebookService facebookService;

    @Autowired
    public UserResource(FacebookService taskService) {
        this.facebookService = taskService;
    }


    /**
     * Get user profile
     *
     * @return
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getUser(@Context HttpServletRequest request) {
        return facebookService.getUser(request);
    }

}

