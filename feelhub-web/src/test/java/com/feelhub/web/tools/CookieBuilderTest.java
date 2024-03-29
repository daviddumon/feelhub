package com.feelhub.web.tools;

import com.feelhub.domain.feeling.FeelingValue;
import com.feelhub.domain.session.Session;
import com.feelhub.domain.user.User;
import com.feelhub.web.dto.FeelhubMessage;
import org.joda.time.DateTime;
import org.junit.Test;
import org.restlet.data.CookieSetting;

import static org.fest.assertions.Assertions.*;

public class CookieBuilderTest {

    @Test
    public void canCreateIdCookie() {
        final FeelhubWebProperties properties = new FeelhubWebProperties();
        properties.secureMode = true;
        properties.cookie = ".test.localhost";
        properties.cookiePermanentTime = 10;
        final User user = new User();

        final CookieSetting cookie = new CookieBuilder(properties).idCookie(user);

        assertThat(cookie.getValue()).isEqualTo(user.getId().toString());
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
        final Session session = new Session(DateTime.now(), new User());

        final CookieSetting cookie = new CookieBuilder(properties).tokenCookie(session.getToken(), false);

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
        final Session session = new Session(DateTime.now(), new User());

        final CookieSetting cookie = new CookieBuilder(properties).tokenCookie(session.getToken(), true);

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
        final CookieSetting cookie = new CookieBuilder(new FeelhubWebProperties()).eraseIdCookie("activationId");

        assertThat(cookie.getValue()).isEqualTo("activationId");
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

    @Test
    public void canCreateMessageCookie() {
        final FeelhubMessage feelhubMessage = new FeelhubMessage();
        feelhubMessage.setFeeling(FeelingValue.happy.toString());
        feelhubMessage.setSecondTimer(3);
        feelhubMessage.setText("This is good!");
        final CookieSetting cookie = new CookieBuilder(new FeelhubWebProperties()).messageCookie(feelhubMessage);

        assertThat(cookie.getName()).isEqualTo("message");
        assertThat(((CookieSetting) cookie).isAccessRestricted()).isFalse();
        assertThat(cookie.getValue()).isEqualTo(feelhubMessage.toJSON());
        assertThat(cookie.getMaxAge()).isEqualTo(-1);
    }
}
