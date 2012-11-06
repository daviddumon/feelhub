package com.feelhub.web.tools;

import com.feelhub.domain.session.Session;
import com.feelhub.domain.user.User;
import org.restlet.data.CookieSetting;

public final class CookieBuilder {

    public CookieBuilder(FeelhubWebProperties properties) {
        this.properties = properties;
    }

    public CookieSetting idCookie(User user) {
        final CookieSetting id = baseIdCookie();
        id.setValue(user.getId());
        id.setMaxAge(properties.cookiePermanentTime);
        return id;
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
        id.setSecure(properties.secureMode);
        id.setDomain(properties.cookie);
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
            return properties.cookiePermanentTime;
        }
        return properties.cookieBaseTime;
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
        session.setSecure(properties.secureMode);
        session.setAccessRestricted(true);
        session.setDomain(properties.cookie);
        session.setPath("/");
        return session;
    }

    private FeelhubWebProperties properties;
    public static final String ID = "id";
    public static final String SESSION = "session";
}
