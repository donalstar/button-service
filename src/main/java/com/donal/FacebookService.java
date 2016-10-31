package com.donal;


import com.donal.model.PostData;
import com.donal.model.Result;
import com.donal.model.UserData;
import com.restfb.Connection;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.json.JsonObject;
import com.restfb.types.Post;
import com.restfb.types.User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
     * @param request
     * @return
     */
    public Response getUser(HttpServletRequest request) {

        FacebookClient facebookClient = getFacebookClientFromSession(request);

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
     * @param request
     * @return
     */
    public Response getPosts(String date, String hashtag, HttpServletRequest request) {

        logger.info("Get posts for date " + date + " hashtag " + hashtag);

        HttpSession session = request.getSession();

        Date searchDate = null;

        if (date != null) {
            try {
                searchDate = getSearchDate(date);
            } catch (ParseException e) {
                return Response.status(
                        Response.Status.INTERNAL_SERVER_ERROR).entity("Invalid search date " + date).build();
            }
        }

        FacebookClient facebookClient = getFacebookClientFromSession(request);

        if (facebookClient != null) {

            Connection<Post> postsFeed;

            if (searchDate == null) {
                postsFeed = facebookClient.fetchConnection("me/feed", Post.class);
            } else {
                long time_secs = searchDate.getTime() / 1000;

                postsFeed = facebookClient.fetchConnection("me/feed", Post.class, Parameter.with("since", time_secs));
            }

            session.setAttribute("postsFeed", postsFeed);

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

        System.out.println("Count of posts " + postsFeed.getData().size());

        int count = 0;

        for (List<Post> page : postsFeed) {

            for (Post post : page) {

                if (count < RESULTS_LIMIT) { // arbitrary test limit

                    String message = post.getMessage();

                    // if hashtag is non-null, only add results containing the hashtag
                    if (hashtag == null) {
                        PostData postData = new PostData();
                        postData.setId(post.getId());


                        postData.setMessage(post.getMessage());
                        posts.add(postData);

                        count++;
                    }
                    else {
                        String searchClause = hashtag.startsWith("#") ? hashtag : "#" + hashtag;

                        if (message!= null && message.contains(searchClause)) {
                            System.out.println(("Matched hashtag " + message));
                            System.out.println("name " + post.getName());


                            PostData postData = new PostData();
                            postData.setId(post.getId());


                            postData.setMessage(post.getMessage());
                            posts.add(postData);

                            count++;
                        }
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


    private void testGetFriendPosts(FacebookClient facebookClient) {

        System.out.println("Get friend posts");

        Connection<User> myFriends = facebookClient.fetchConnection("me/friends", User.class);


        for (List<User> userPage : myFriends) {
            System.out.println("Friend page - count = " + userPage.size());

            for (User user : userPage) {

                System.out.println("User " + user.getName());

            }

        }


    }

    /**
     * @param request
     * @return
     */
    private FacebookClient getFacebookClientFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession();

        return (FacebookClient) session.getAttribute("client");
    }
}
