package com.donal.util;

import com.restfb.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Service
public class FacebookUtil {

    @Value("${app.config.oauth.facebook.apikey}")
    private String apikey;


    @Value("${app.config.oauth.facebook.apisecret}")
    private String apiSecret;


    @Value("${app.config.oauth.facebook.callback}")
    private String callback;

    /**
     *
     * @param code
     * @return
     * @throws IOException
     */
    public FacebookClient getClient(String code) throws IOException {
        FacebookClient.AccessToken userToken = getFacebookUserToken(code, callback);

        return new DefaultFacebookClient(userToken.getAccessToken(), Version.VERSION_2_5);
    }

    /**
     * @param request
     * @return
     */
    public FacebookClient getFacebookClientFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession();

        return (FacebookClient) session.getAttribute("client");
    }

    /**
     *
     * @param code
     * @param redirectUrl
     * @return
     * @throws IOException
     */
    private FacebookClient.AccessToken getFacebookUserToken(String code, String redirectUrl) throws IOException {

        WebRequestor wr = new DefaultWebRequestor();
        WebRequestor.Response accessTokenResponse = wr.executeGet(
                "https://graph.facebook.com/oauth/access_token?client_id=" + apikey + "&redirect_uri=" + redirectUrl
                        + "&client_secret=" + apiSecret + "&code=" + code);

        DefaultFacebookClient.AccessToken userToken = DefaultFacebookClient.AccessToken.fromQueryString(accessTokenResponse.getBody());

        return
                new DefaultFacebookClient().obtainExtendedAccessToken(apikey, apiSecret, userToken.getAccessToken());

    }
}
