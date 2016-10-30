package com.donal;


import com.donal.model.PostData;
import com.donal.model.Result;
import com.donal.model.User;
import com.restfb.Connection;
import com.restfb.FacebookClient;
import com.restfb.json.JsonObject;
import com.restfb.types.Post;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class FacebookService {
    static Logger logger = Logger.getLogger(FacebookService.class.getName());

    /**
     * Get  user
     *
     * @param request
     * @return
     */
    public Response getUser(HttpServletRequest request) {

        FacebookClient facebookClient = getFacebookClientFromSession(request);

        if (facebookClient != null) {
            JsonObject me = facebookClient.fetchObject("me", JsonObject.class);

            User user = new User();

            logger.info("Got user name " + me.getString("name"));

            user.setName(me.getString("name"));

            Response.ResponseBuilder builder = Response.ok(user);

            return builder.build();
        } else {
            return Response.status(
                    Response.Status.INTERNAL_SERVER_ERROR).entity("No Facebook client connection available").build();
        }
    }

    /**
     *
     * @param pageId
     * @param request
     * @return
     */
    public Response getPosts(int pageId, HttpServletRequest request) {

        System.out.println("Get posts for page = " + pageId);

        FacebookClient facebookClient = getFacebookClientFromSession(request);

        if (facebookClient != null) {
            HttpSession session = request.getSession();

            Connection<Post> postsFeed = (Connection<Post>) session.getAttribute("postsFeed");

            if (postsFeed == null) {
                postsFeed = facebookClient.fetchConnection("me/feed", Post.class);

                session.setAttribute("postsFeed", postsFeed);
            }

            Result result = new Result();

            List<PostData> posts = new ArrayList<PostData>();

            int pageIndex = 0;

            for (List<Post> page : postsFeed) {
                if (pageIndex == pageId) {
                    for (Post post : page) {

                        PostData postData = new PostData();
                        postData.setId(post.getId());
                        postData.setMessage(post.getMessage());
                        posts.add(postData);
                    }
                }

                pageIndex++;
            }

            result.setPosts(posts);

            result.setPageId(pageId);

            result.setPageCount(pageIndex);

            Response.ResponseBuilder builder = Response.ok(result);

            return builder.build();
        }
        else {
            return Response.status(
                    Response.Status.INTERNAL_SERVER_ERROR).entity("No Facebook client connection available").build();
        }
    }

    /**
     *
     * @param request
     * @return
     */
    private FacebookClient getFacebookClientFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession();

        return (FacebookClient) session.getAttribute("client");
    }
}
