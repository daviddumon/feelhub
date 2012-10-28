package com.steambeat.web.resources.authentification;

import com.google.common.base.Strings;
import com.google.inject.Inject;
import com.steambeat.application.SessionService;
import com.steambeat.application.UserService;
import com.steambeat.domain.session.Session;
import com.steambeat.domain.user.BadPasswordException;
import com.steambeat.domain.user.User;
import com.steambeat.web.ReferenceBuilder;
import com.steambeat.web.tools.CookieBuilder;
import org.joda.time.DateTime;
import org.restlet.data.Cookie;
import org.restlet.data.CookieSetting;
import org.restlet.data.Form;
import org.restlet.data.Status;
import org.restlet.resource.Delete;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import java.util.UUID;

public class SessionsResource extends ServerResource {

	@Inject
	public SessionsResource(final UserService userService, final SessionService sessionService) {
		this.userService = userService;
		this.sessionService = sessionService;
	}

	@Post
	public void login(final Form form) {
		if (!checkForm(form)) {
			setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
			return;
		}
		final String email = form.getFirstValue("email");
		final String password = form.getFirstValue("password");
		boolean remember = toBoolean(form.getFirstValue("remember"));
		try {
			user = userService.authentificate(email, password);
			session = sessionService.createSession(user, getSessionTime(remember));
			setCookiesInResponse(remember);
			setStatus(Status.SUCCESS_CREATED);
			setLocationRef(new ReferenceBuilder(getContext()).buildUri("/"));
		} catch (BadPasswordException badPasswordException) {
			setStatus(Status.CLIENT_ERROR_UNAUTHORIZED);
		}

	}

	private boolean toBoolean(final String value) {
		return !Strings.isNullOrEmpty(value) && value.equalsIgnoreCase("on");
	}

	private boolean checkForm(final Form form) {
		return form.getQueryString().contains("email") && form.getQueryString().contains("password");
	}

	private DateTime getSessionTime(boolean remember) {
		if (remember) {
			final String expirationTime = getContext().getAttributes().get("com.steambeat.session.sessionpermanenttime").toString();
			return new DateTime().plusSeconds(Integer.valueOf(expirationTime));
		} else {
			final String expirationTime = getContext().getAttributes().get("com.steambeat.session.sessionbasetime").toString();
			return new DateTime().plusSeconds(Integer.valueOf(expirationTime));
		}
	}

	private void setCookiesInResponse(final boolean remember) {
		final CookieBuilder cookieBuilder = CookieBuilder.create(getContext());
		setCookie(cookieBuilder.idCookie(user));
		setCookie(cookieBuilder.tokenCookie(session, remember));
	}

	@Delete
	public void logout() {
		CookieBuilder cookieBuilder = CookieBuilder.create(getContext());
		final Cookie sessionCookie = getRequest().getCookies().getFirst(CookieBuilder.SESSION);
		final Cookie id = getRequest().getCookies().getFirst(CookieBuilder.ID);
		if (sessionCookie != null && id != null) {
			sessionService.deleteSession(UUID.fromString(sessionCookie.getValue()));
			setCookie(cookieBuilder.eraseIdCookie(id.getValue()));
			setCookie(cookieBuilder.eraseSessionCookie(sessionCookie.getValue()));
			setStatus(Status.SUCCESS_ACCEPTED);
		} else {
			setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
		}
	}

	private void setCookie(final CookieSetting cookie) {
		getResponse().getCookieSettings().add(cookie);
	}

	private final UserService userService;
	private final SessionService sessionService;
	private User user;
	private Session session;
}
