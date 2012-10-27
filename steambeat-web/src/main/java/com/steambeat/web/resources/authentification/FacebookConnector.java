package com.steambeat.web.resources.authentification;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.steambeat.web.ReferenceBuilder;
import org.restlet.Context;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.FacebookApi;
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


	private final OAuthService service;
}
