package com.feelhub.web.social;

import com.feelhub.tools.Clients;
import com.feelhub.web.tools.FeelhubWebProperties;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.gson.Gson;
import com.google.inject.Inject;
import org.restlet.Client;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Method;
import org.scribe.builder.ServiceBuilder;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

import javax.inject.Singleton;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

@Singleton
public class GoogleConnector {

    @Inject
    public GoogleConnector(final FeelhubWebProperties properties) {
        final String callback = properties.domain + "/social/google";
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
        final ListenableFuture<GoogleUser> getUser = executor.submit(fetchUserData(accessToken));
        return Futures.getUnchecked(getUser);
    }

    private Callable<GoogleUser> fetchUserData(final Token accessToken) {
        return new Callable<GoogleUser>() {
            @Override
            public GoogleUser call() throws Exception {
                final String url = String.format(USER_INFO_URL, accessToken.getToken());
                final Request request = new Request(Method.GET, url);
                final Response representation = getUserInfo(request);
                return new Gson().fromJson(representation.getEntityAsText(), GoogleUser.class);
            }

            private Response getUserInfo(final Request request) {

                final Client client = Clients.create();
                try {
                    return client.handle(request);
                } finally {
                    Clients.stop(client);
                }
            }
        };
    }

    private final OAuthService service;
    private final String USER_INFO_URL = "https://www.googleapis.com/oauth2/v1/userinfo?access_token=%s";
    private final ListeningExecutorService executor = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());
}
