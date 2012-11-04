package com.feelhub.web.tools;

import com.google.inject.Inject;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Cookie;
import org.restlet.data.CookieSetting;
import org.restlet.util.Series;

public class CookieManager {

	@Inject
	public CookieManager(FeelhubWebProperties properties) {
		this.properties = properties;
	}

	public void setCookie(CookieSetting cookie) {
		Response.getCurrent().getCookieSettings().add(cookie);
	}

	public CookieBuilder cookieBuilder() {
		return new CookieBuilder(properties);
	}

	public Series<Cookie> getCookies() {
		return Request.getCurrent().getCookies();
	}

	public Cookie getCookie(final String id) {
		return Request.getCurrent().getCookies().getFirst(id);
	}

	private FeelhubWebProperties properties;
}
