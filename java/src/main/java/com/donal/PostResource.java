package com.donal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
@ApplicationPath("/api/*")
@Path("post")
public class PostResource {
    private FacebookService facebookService;

    @Autowired
    public PostResource(FacebookService facebookService) {
        this.facebookService = facebookService;
    }

    /**
     * Get posts
     *
     * @return
     */
    @GET
    @Path("/{page_id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getPostsForPage(@PathParam("page_id") int pageId, @Context HttpServletRequest request) {
        System.out.println("Get posts for page " + pageId);
        return facebookService.getPosts(pageId, request);
    }

}

