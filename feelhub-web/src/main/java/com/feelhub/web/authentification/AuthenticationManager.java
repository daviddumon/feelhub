package com.feelhub.web.authentification;

import com.feelhub.application.SessionService;
import com.feelhub.application.UserService;
import com.feelhub.domain.session.Session;
import com.feelhub.domain.user.User;
import com.feelhub.web.tools.CookieBuilder;
import com.feelhub.web.tools.CookieManager;
import com.feelhub.web.tools.FeelhubWebProperties;
import com.google.inject.Inject;
import org.joda.time.DateTime;
import org.restlet.data.Cookie;

import java.util.UUID;

public class AuthenticationManager {

	@Inject
	public AuthenticationManager(final UserService userService, final SessionService sessionService, FeelhubWebProperties properties, CookieManager cookieManager) {
		this.userService = userService;
		this.sessionService = sessionService;
		this.properties = properties;
		this.cookieManager = cookieManager;
	}

	public void authenticate(final UserDetails userDetails) {
		User user = userService.authentificate(userDetails.getEmail(), userDetails.getPassword());
		final Session session = sessionService.createSession(user, new DateTime().plusSeconds(lifeTime(userDetails.isRemember())));
		setCookiesInResponse(userDetails.isRemember(), user, session);
	}

	private void setCookiesInResponse(final boolean remember, final User user, final Session session) {
		final CookieBuilder cookieBuilder = cookieManager.cookieBuilder();
		cookieManager.setCookie(cookieBuilder.idCookie(user));
		cookieManager.setCookie(cookieBuilder.tokenCookie(session, remember));
	}

	private int lifeTime(final boolean remember) {
		if (remember) {
			return properties.sessionPermanentTime;
		} else {
			return properties.sessionbasetime;
		}
	}

	public boolean logout() {
		CookieBuilder cookieBuilder = cookieManager.cookieBuilder();
		final Cookie sessionCookie = cookieManager.getCookies().getFirst(CookieBuilder.SESSION);
		final Cookie id = cookieManager.getCookies().getFirst(CookieBuilder.ID);
		if (sessionCookie != null && id != null) {
			sessionService.deleteSession(UUID.fromString(sessionCookie.getValue()));
			cookieManager.setCookie(cookieBuilder.eraseIdCookie(id.getValue()));
			cookieManager.setCookie(cookieBuilder.eraseSessionCookie(sessionCookie.getValue()));
			return true;
		}
		return false;
	}

	private final UserService userService;
	private final SessionService sessionService;
	private FeelhubWebProperties properties;
	private CookieManager cookieManager;
}
