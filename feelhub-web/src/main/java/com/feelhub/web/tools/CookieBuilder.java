package com.feelhub.web.tools;

import com.feelhub.domain.session.Session;
import com.feelhub.domain.user.User;
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
        final CookieSetting id = baseIdCookie();
        id.setValue(user.getEmail());
        id.setMaxAge(getIdCookieTime(context));
        return id;
    }

    private int getIdCookieTime(Context context) {
        return Integer.valueOf(context.getAttributes().get("com.feelhub.cookie.cookiepermanenttime").toString());
    }

    public CookieSetting eraseIdCookie(final String value) {
        final CookieSetting id = baseIdCookie();
        id.setValue(value);
        id.setMaxAge(0);
        return id;
    }

    private CookieSetting baseIdCookie() {
        final CookieSetting id = new CookieSetting();
        id.setName(ID);
        id.setComment("id cookie");
        id.setAccessRestricted(true);
        id.setSecure(Boolean.valueOf(context.getAttributes().get("com.feelhub.cookie.secure").toString()));
        id.setDomain(context.getAttributes().get("com.feelhub.cookie.domain").toString());
        id.setPath("/");
        return id;
    }

    public CookieSetting tokenCookie(Session session, boolean remember) {
        final CookieSetting result = baseSessionCookie();
        result.setMaxAge(getSessionCookieTime(remember));
        result.setValue(session.getToken().toString());
        return result;
    }

    private int getSessionCookieTime(boolean remember) {
        if (remember) {
            return Integer.valueOf(context.getAttributes().get("com.feelhub.cookie.cookiepermanenttime").toString());
        }
        return Integer.valueOf(context.getAttributes().get("com.feelhub.cookie.cookiebasetime").toString());
    }

    public CookieSetting eraseSessionCookie(final String value) {
        final CookieSetting session = baseSessionCookie();
        session.setMaxAge(0);
        session.setValue(value);
        return session;
    }

    private CookieSetting baseSessionCookie() {
        final CookieSetting session = new CookieSetting();
        session.setName(SESSION);
        session.setComment("session cookie");
        session.setSecure(Boolean.valueOf(context.getAttributes().get("com.feelhub.cookie.secure").toString()));
        session.setAccessRestricted(true);
        session.setDomain(context.getAttributes().get("com.feelhub.cookie.domain").toString());
        session.setPath("/");
        return session;
    }

    private Context context;
    public static final String ID = "id";
    public static final String SESSION = "session";
}
