package com.donal.controller;

import com.donal.util.FacebookUtil;
import com.donal.util.OAuthServiceConfig;
import com.donal.util.OAuthServiceProvider;
import com.restfb.FacebookClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RequestMapping(value = "/social/facebook")

@Component
public class FacebookController<FacebookApi> {

    private static final String REDIRECT_URI = "http://localhost:8080/social/facebook/callback";

    private static final Logger LOGGER = LoggerFactory.getLogger(FacebookController.class);

    private static final String FACEBOOK = "facebook";

    @Autowired
    private ConnectionFactoryRegistry connectionFactoryRegistry;

    @Autowired
    private OAuth2Parameters oAuth2Parameters;

    @Autowired
    FacebookUtil facebookUtil;

    @Autowired
    @Qualifier("facebookServiceProvider")
    private OAuthServiceProvider<FacebookApi> facebookServiceProvider;

    @Autowired
    private OAuthServiceConfig facebookServiceConfig;


    @RequestMapping(value = "/signin", method = RequestMethod.GET)
    public ModelAndView signin(HttpServletRequest request,
                               HttpServletResponse response) throws Exception {

        FacebookConnectionFactory facebookConnectionFactory
                = (FacebookConnectionFactory) connectionFactoryRegistry.getConnectionFactory(FACEBOOK);

        OAuth2Operations oauthOperations = facebookConnectionFactory.getOAuthOperations();

        oAuth2Parameters.setState("received_facebook_token");

        String authorizeUrl = oauthOperations.buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, oAuth2Parameters);

        RedirectView redirectView = new RedirectView(authorizeUrl, true, true, true);

        return new ModelAndView(redirectView);
    }


    @RequestMapping(value = "/callback", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView loginCallback(@RequestParam("code") String code,
                                      @RequestParam("state") String state, HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {

        FacebookClient facebookClient = facebookUtil.getClient(code);

        HttpSession session = request.getSession();

        session.setAttribute("client", facebookClient);

        String url = "/home.html?authx=true";

        RedirectView redirectView = new RedirectView(url, true, true, true);

        return new ModelAndView(redirectView);
    }

    @RequestMapping(value = "/callback", params = "error_reason", method = RequestMethod.GET)
    @ResponseBody
    public void error(@RequestParam("error_reason") String errorReason,
                      @RequestParam("error") String error,
                      @RequestParam("error_description") String description,
                      HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        try {
            LOGGER.error(
                    "Error occurred while validating user, reason is : {}",
                    errorReason);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, description);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
