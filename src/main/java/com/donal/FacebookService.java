package com.donal;


import com.donal.model.PostData;
import com.donal.model.Result;
import com.donal.model.UserData;
import com.restfb.Connection;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.json.JsonObject;
import com.restfb.types.Post;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Service
public class FacebookService {
    static Logger logger = Logger.getLogger(FacebookService.class.getName());

    private static final int RESULTS_LIMIT = 50;

    /**
     * Get  user
     *
     * @param facebookClient
     * @return
     */
    public Response getUser(FacebookClient facebookClient) {

        if (facebookClient != null) {
            JsonObject me = facebookClient.fetchObject("me", JsonObject.class);

            UserData user = new UserData();

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
     * @param facebookClient
     * @return
     */
    public Response getPosts(String date, String hashtag, FacebookClient facebookClient) {

        logger.info("Get posts for date " + date + " hashtag " + hashtag);

        Date searchDate = null;

        if (date != null) {
            try {
                searchDate = getSearchDate(date);
            } catch (ParseException e) {
                return Response.status(
                        Response.Status.INTERNAL_SERVER_ERROR).entity("Invalid search date " + date).build();
            }
        }

        if (facebookClient != null) {
            Connection<Post> postsFeed;

            if (searchDate == null) {
                postsFeed = facebookClient.fetchConnection("me/feed", Post.class);
            } else {
                long time_secs = searchDate.getTime() / 1000;

                postsFeed = facebookClient.fetchConnection("me/feed", Post.class, Parameter.with("since", time_secs));
            }

            logger.info("posts feed " + postsFeed);

            Result result = parseFeedResults(postsFeed, hashtag);

            Response.ResponseBuilder builder = Response.ok(result);

            return builder.build();
        } else {
            return Response.status(
                    Response.Status.INTERNAL_SERVER_ERROR).entity("No Facebook client connection available").build();
        }
    }


    /**
     * @param postsFeed
     * @param hashtag
     * @return
     */
    private Result parseFeedResults(Connection<Post> postsFeed, String hashtag) {
        Result result = new Result();

        List<PostData> posts = new ArrayList<PostData>();

        int count = 0;

        for (Post post : postsFeed.getData()) {

            if (count < RESULTS_LIMIT) { // arbitrary test limit

                String message = post.getMessage();

                // if hashtag is non-null, only add results containing the hashtag
                if (hashtag == null) {
                    PostData postData = new PostData();
                    postData.setId(post.getId());

                    postData.setMessage(post.getMessage());
                    posts.add(postData);

                    count++;
                } else {
                    String searchClause = hashtag.startsWith("#") ? hashtag : "#" + hashtag;

                    if (message != null && message.contains(searchClause)) {
                        PostData postData = new PostData();
                        postData.setId(post.getId());


                        postData.setMessage(post.getMessage());
                        posts.add(postData);

                        count++;
                    }
                }
            }
        }

        result.setPosts(posts);

        return result;
    }


    /**
     * @param dateString
     * @return
     * @throws ParseException
     */
    private Date getSearchDate(String dateString) throws ParseException {
        Date searchDate = null;

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

        if (dateString != null) {

            searchDate = sdf.parse(dateString);

        } else {
            System.out.println("Date not set");
        }

        return searchDate;
    }
}
