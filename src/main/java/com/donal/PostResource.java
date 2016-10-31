package com.donal;

import com.donal.util.FacebookUtil;
import com.restfb.FacebookClient;
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
    FacebookUtil facebookUtil;

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
    @Produces({MediaType.APPLICATION_JSON})
    public Response getPostsForPage(@QueryParam("date") String date,
                                    @QueryParam("hashtag") String hashtag,
                                    @Context HttpServletRequest request) {


        FacebookClient facebookClient = facebookUtil.getFacebookClientFromSession(request);

        return facebookService.getPosts(date, hashtag, facebookClient);
    }

}

