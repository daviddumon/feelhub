package com.feelhub.web.tools;

import com.google.inject.Inject;
import org.restlet.*;
import org.restlet.data.*;
import org.restlet.util.Series;

public class CookieManager {

    @Inject
    public CookieManager(final FeelhubWebProperties properties) {
        this.properties = properties;
    }

    public void setCookie(final CookieSetting cookie) {
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

    private final FeelhubWebProperties properties;
}
