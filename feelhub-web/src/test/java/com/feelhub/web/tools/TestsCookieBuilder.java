package com.feelhub.web.tools;

import com.feelhub.domain.session.Session;
import com.feelhub.domain.user.User;
import org.joda.time.DateTime;
import org.junit.Test;
import org.restlet.data.CookieSetting;

import static org.fest.assertions.Assertions.*;

public class TestsCookieBuilder {

    @Test
    public void canCreateIdCookie() {
        final FeelhubWebProperties properties = new FeelhubWebProperties();
        properties.secureMode = true;
        properties.cookie = ".test.localhost";
        properties.cookiePermanentTime = 10;

        final CookieSetting cookie = new CookieBuilder(properties).idCookie(new User("userId"));

        assertThat(cookie.getValue()).isEqualTo("userId");
        assertThat(cookie.getComment()).isEqualTo("id cookie");
        assertThat(cookie.getName()).isEqualTo("id");
        assertThat(cookie.isAccessRestricted()).isTrue();
        assertThat(cookie.isSecure()).isTrue();
        assertThat(cookie.getVersion()).isEqualTo(0);
        assertThat(cookie.getDomain()).isEqualTo(".test.localhost");
        assertThat(cookie.getMaxAge()).isEqualTo(10);
        assertThat(cookie.getPath()).isEqualTo("/");
    }

    @Test
    public void canCreateSessionCookie() {
        final FeelhubWebProperties properties = new FeelhubWebProperties();
        properties.secureMode = true;
        properties.cookie = ".test.localhost";
        properties.cookiePermanentTime = 10;
        properties.cookieBaseTime = 1;
        final Session session = new Session(DateTime.now());

        final CookieSetting cookie = new CookieBuilder(properties).tokenCookie(session, false);

        assertThat(cookie.getValue()).isEqualTo(session.getId().toString());
        assertThat(cookie.getComment()).isEqualTo("session cookie");
        assertThat(cookie.getName()).isEqualTo("session");
        assertThat(cookie.isAccessRestricted()).isTrue();
        assertThat(cookie.isSecure()).isTrue();
        assertThat(cookie.getVersion()).isEqualTo(0);
        assertThat(cookie.getDomain()).isEqualTo(".test.localhost");
        assertThat(cookie.getMaxAge()).isEqualTo(1);
        assertThat(cookie.getPath()).isEqualTo("/");
    }

    @Test
    public void canCreateSessionCookieWithRemember() {
        final FeelhubWebProperties properties = new FeelhubWebProperties();
        properties.secureMode = true;
        properties.cookie = ".test.localhost";
        properties.cookiePermanentTime = 10;
        properties.cookieBaseTime = 1;
        final Session session = new Session(DateTime.now());

        final CookieSetting cookie = new CookieBuilder(properties).tokenCookie(session, true);

        assertThat(cookie.getValue()).isEqualTo(session.getId().toString());
        assertThat(cookie.getComment()).isEqualTo("session cookie");
        assertThat(cookie.getName()).isEqualTo("session");
        assertThat(cookie.isAccessRestricted()).isTrue();
        assertThat(cookie.isSecure()).isTrue();
        assertThat(cookie.getVersion()).isEqualTo(0);
        assertThat(cookie.getDomain()).isEqualTo(".test.localhost");
        assertThat(cookie.getMaxAge()).isEqualTo(10);
        assertThat(cookie.getPath()).isEqualTo("/");
    }

    @Test
    public void canCreateEraseIdCookie() {
        final CookieSetting cookie = new CookieBuilder(new FeelhubWebProperties()).eraseIdCookie("userId");

        assertThat(cookie.getValue()).isEqualTo("userId");
        assertThat(cookie.getName()).isEqualTo("id");
        assertThat(cookie.getMaxAge()).isEqualTo(0);
    }

    @Test
    public void canCreateEraseSessionCookie() {
        final CookieSetting cookie = new CookieBuilder(new FeelhubWebProperties()).eraseSessionCookie("uuid");

        assertThat(cookie.getName()).isEqualTo("session");
        assertThat(cookie.getValue()).isEqualTo("uuid");
        assertThat(cookie.getMaxAge()).isEqualTo(0);
    }
}
