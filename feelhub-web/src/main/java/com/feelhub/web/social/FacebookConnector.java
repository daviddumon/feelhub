package com.feelhub.web.social;

import com.feelhub.web.ReferenceBuilder;
import com.feelhub.web.tools.FeelhubWebProperties;
import com.google.inject.Inject;
import com.restfb.DefaultFacebookClient;
import com.restfb.types.User;
import org.restlet.Context;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.FacebookApi;
import org.scribe.model.*;
import org.scribe.oauth.OAuthService;

public class FacebookConnector {

    @Inject
    public FacebookConnector(final FeelhubWebProperties properties) {
        final String callback = new ReferenceBuilder(Context.getCurrent()).buildUri("/social/facebook");
        service = new ServiceBuilder().provider(FacebookApi.class).apiKey(properties.facebookAppId).apiSecret(properties.facebookAppSecret).callback(callback)
                .build();
    }

    public String getUrl() {
        return service.getAuthorizationUrl(null);
    }

    public Token getAccesToken(final String facebookCode) {
        return service.getAccessToken(null, new Verifier(facebookCode));
    }

    public User getUser(final Token token) {
        final DefaultFacebookClient facebookClient = new DefaultFacebookClient(token.getToken());
        return facebookClient.fetchObject("me", User.class);
    }


    private final OAuthService service;
}
