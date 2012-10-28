package com.steambeat.web.tools;

import com.steambeat.domain.session.Session;
import com.steambeat.domain.user.User;
import org.restlet.Context;
import org.restlet.data.CookieSetting;

public final class CookieBuilder {

	public static CookieBuilder create(Context context) {
		return new CookieBuilder(context);
	}

	private CookieBuilder(final Context context) {
		this.context = context;
	}

	public CookieSetting idCookie(User user) {
		final CookieSetting id = new CookieSetting();
		id.setName("id");
		id.setComment("id cookie");
		id.setAccessRestricted(true);
		id.setValue(user.getEmail());
		id.setSecure(Boolean.valueOf(context.getAttributes().get("com.steambeat.cookie.secure").toString()));
		id.setDomain(context.getAttributes().get("com.steambeat.cookie.domain").toString());
		id.setMaxAge(getIdCookieTime(context));
		id.setPath("/");
		return id;
	}

	private int getIdCookieTime(Context context) {
		return Integer.valueOf(context.getAttributes().get("com.steambeat.cookie.cookiepermanenttime").toString());
	}

	public CookieSetting tokenCookie(Session session, boolean remember) {
		final CookieSetting result = new CookieSetting();
		result.setName("session");
		result.setComment("session cookie");
		result.setSecure(Boolean.valueOf(context.getAttributes().get("com.steambeat.cookie.secure").toString()));
		result.setAccessRestricted(true);
		result.setValue(session.getToken().toString());
		result.setDomain(context.getAttributes().get("com.steambeat.cookie.domain").toString());
		result.setMaxAge(getSessionCookieTime(remember));
		result.setPath("/");
		return result;
	}

	private  int getSessionCookieTime(boolean remember) {
		if (remember) {
			return Integer.valueOf(context.getAttributes().get("com.steambeat.cookie.cookiepermanenttime").toString());
		}
		return Integer.valueOf(context.getAttributes().get("com.steambeat.cookie.cookiebasetime").toString());
	}

	public CookieSetting eraseIdCookie(final String value) {
		final CookieSetting id = new CookieSetting();
		id.setName("id");
		id.setComment("id cookie");
		id.setAccessRestricted(true);
		id.setValue(value);
		id.setSecure(Boolean.valueOf(context.getAttributes().get("com.steambeat.cookie.secure").toString()));
		id.setDomain(context.getAttributes().get("com.steambeat.cookie.domain").toString());
		id.setMaxAge(0);
		id.setPath("/");
		return id;
	}

	public CookieSetting eraseSessionCookie(final String value) {
		final CookieSetting session = new CookieSetting();
		session.setName("session");
		session.setComment("session cookie");
		session.setSecure(true);
		session.setAccessRestricted(true);
		session.setValue(value);
		session.setSecure(Boolean.valueOf(context.getAttributes().get("com.steambeat.cookie.secure").toString()));
		session.setDomain(context.getAttributes().get("com.steambeat.cookie.domain").toString());
		session.setMaxAge(0);
		session.setPath("/");
		return session;
	}

	private Context context;
}
