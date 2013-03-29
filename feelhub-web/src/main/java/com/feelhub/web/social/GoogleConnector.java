package com.feelhub.web.social;

import com.feelhub.tools.Clients;
import com.feelhub.web.WebReferenceBuilder;
import com.feelhub.web.tools.FeelhubWebProperties;
import com.google.gson.Gson;
import com.google.inject.Inject;
import org.restlet.Client;
import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Method;
import org.scribe.builder.ServiceBuilder;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

public class GoogleConnector {


    @Inject
    public GoogleConnector(final FeelhubWebProperties properties) {
        final String callback = new WebReferenceBuilder(Context.getCurrent()).buildUri("/social/google");
        service = new ServiceBuilder().provider(GoogleApi.class).apiKey(properties.googleAppId).apiSecret(properties.googleAppSecret).callback(callback)
                .scope("https://www.googleapis.com/auth/userinfo.email https://www.googleapis.com/auth/userinfo.profile").build();
    }

    public String getUrl() {
        return service.getAuthorizationUrl(Token.empty());
    }

    public Token getAccesToken(final String googleCode) {
        return service.getAccessToken(Token.empty(), new Verifier(googleCode));
    }

    public GoogleUser getUser(final Token accessToken) {
        final String url = String.format(USER_INFO_URL, accessToken.getToken());
        Request request = new Request(Method.GET, url);
        Response representation = getUserInfo(request);
        return new Gson().fromJson(representation.getEntityAsText(), GoogleUser.class);
    }

    private Response getUserInfo(Request request) {

        final Client client = Clients.create();
        try {
            return client.handle(request);
        } finally {
            Clients.stop(client);
        }
    }

    private final OAuthService service;

    private final String USER_INFO_URL = "https://www.googleapis.com/oauth2/v1/userinfo?access_token=%s";
}
