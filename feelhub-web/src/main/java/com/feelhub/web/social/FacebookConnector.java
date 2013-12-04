package com.feelhub.web.social;

import com.feelhub.web.tools.FeelhubWebProperties;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.restfb.DefaultFacebookClient;
import com.restfb.types.User;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.FacebookApi;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

@Singleton
public class FacebookConnector {

    @Inject
    public FacebookConnector(final FeelhubWebProperties properties) {
        final String callback = properties.domain + "/social/facebook";
        service = new ServiceBuilder().provider(FacebookApi.class).apiKey(properties.facebookAppId).apiSecret(properties.facebookAppSecret).callback(callback)
                .build();
    }

    public String getUrl() {
        return service.getAuthorizationUrl(null) + "&scope=email,publish_actions";
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
