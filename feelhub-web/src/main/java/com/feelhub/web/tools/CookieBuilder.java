package com.feelhub.web.tools;

import com.feelhub.domain.user.User;
import org.restlet.data.CookieSetting;

import java.util.UUID;

public final class CookieBuilder {

    public CookieBuilder(final FeelhubWebProperties properties) {
        this.properties = properties;
    }

    public CookieSetting idCookie(final User user) {
        final CookieSetting id = baseIdCookie();
        id.setValue(user.getId().toString());
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
        return newCookie(ID, "id cookie");
    }

    public CookieSetting tokenCookie(final UUID sessionToken, final boolean remember) {
        final CookieSetting result = baseSessionCookie();
        result.setMaxAge(getSessionCookieTime(remember));
        result.setValue(sessionToken.toString());
        return result;
    }

    private CookieSetting baseSessionCookie() {
        return newCookie(SESSION, "session cookie");
    }

    private int getSessionCookieTime(final boolean remember) {
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

    private CookieSetting newCookie(final String name, final String comment) {
        final CookieSetting session = new CookieSetting();
        session.setName(name);
        session.setComment(comment);
        session.setAccessRestricted(true);
        session.setSecure(properties.secureMode);
        session.setDomain(properties.cookie);
        session.setPath("/");
        return session;
    }

    private final FeelhubWebProperties properties;
    public static final String ID = "id";
    public static final String SESSION = "session";
}
