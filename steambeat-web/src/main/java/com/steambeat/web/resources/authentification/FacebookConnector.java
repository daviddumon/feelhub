package com.steambeat.web.resources.authentification;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.restfb.DefaultFacebookClient;
import com.restfb.types.User;
import com.steambeat.web.ReferenceBuilder;
import org.restlet.Context;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.FacebookApi;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

public class FacebookConnector {

	@Inject
	public FacebookConnector(@Named("facebook.appId") String appId, @Named("facebook.appSecret") String secret) {
		final String callback = new ReferenceBuilder(Context.getCurrent()).buildUri("/facebooklogin");
		service = new ServiceBuilder().provider(FacebookApi.class).apiKey(appId).apiSecret(secret).callback(callback)
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
