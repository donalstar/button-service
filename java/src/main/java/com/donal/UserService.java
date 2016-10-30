package com.donal;


import com.donal.model.User;
import com.restfb.FacebookClient;
import com.restfb.json.JsonObject;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;

@Service
public class UserService {
    static Logger logger = Logger.getLogger(UserService.class.getName());

    /**
     * Get  user
     */
    public Response getUser(HttpServletRequest request) {
        Response response;

        HttpSession session = request.getSession();

        FacebookClient facebookClient = (FacebookClient) session.getAttribute("client");

        JsonObject me = facebookClient.fetchObject("me", JsonObject.class);

        User user = new User();

        System.out.println("Got user name " + me.getString("name"));
        user.setName(me.getString("name"));

        Response.ResponseBuilder builder = Response.ok(user);

        response = builder.build();

        System.out.println("Returning " + response);

        return response;
    }
//
//    public Response getPosts(String code) {
//
//        FacebookClient facebookClient = null;
//        try {
//            facebookClient = getClient(code);
//
//            Connection<Post> myFeed = facebookClient.fetchConnection("me/feed", Post.class);
//
//            for (List<Post> myFeedConnectionPage : myFeed)
//                for (Post post : myFeedConnectionPage)
//                    System.out.println("Post: " + post);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        UserData user = new UserData();
//
//        user.setId(1);
//        user.setName("posts");
//
//
//        Response.ResponseBuilder builder = Response.ok(user);
//
//        Response response = builder.build();
//
//        return response;
//    }
//
//    private FacebookClient getClient(String code) throws IOException {
//        FacebookClient.AccessToken userToken = getFacebookUserToken(code, REDIRECT_URI);
//
//        System.out.println("Got user token " + userToken);
//
//        return new DefaultFacebookClient(userToken.getAccessToken(), Version.VERSION_2_5);
//
//
//    }

//    private FacebookClient.AccessToken getFacebookUserToken1(String code, String redirectUrl) throws IOException {
//        String appId = APP_ID;
//        String secretKey = APP_SECRET;
//
//        WebRequestor wr = new DefaultWebRequestor();
//        WebRequestor.Response accessTokenResponse = wr.executeGet(
//                "https://graph.facebook.com/oauth/access_token?client_id=" + appId + "&redirect_uri=" + redirectUrl
//                        + "&client_secret=" + secretKey + "&code=" + code);
//
//        DefaultFacebookClient.AccessToken userToken = DefaultFacebookClient.AccessToken.fromQueryString(accessTokenResponse.getBody());
//
//        return
//                new DefaultFacebookClient().obtainExtendedAccessToken(APP_ID,
//                        APP_SECRET, userToken.getAccessToken());
//
//
//    }


}
