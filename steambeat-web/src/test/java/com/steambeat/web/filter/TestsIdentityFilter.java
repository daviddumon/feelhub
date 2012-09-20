package com.steambeat.web.filter;

import com.steambeat.application.*;
import com.steambeat.domain.session.Session;
import com.steambeat.domain.user.*;
import com.steambeat.repositories.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.TestFactories;
import com.steambeat.web.WebApplicationTester;
import org.joda.time.DateTime;
import org.junit.*;
import org.restlet.*;
import org.restlet.data.Cookie;
import org.restlet.engine.util.CookieSeries;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsIdentityFilter {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Before
    public void before() {
        request = new Request();
        user = TestFactories.users().createUser("mail@mail.com", "full name");
        final SessionService sessionService = new SessionService();
        session = sessionService.createSession(user, new DateTime().plusHours(1));
        identityFilter = new IdentityFilter(new UserService(new UserFactory()), new SessionService());
        identityFilter.setContext(restlet.getApplication().getContext());
    }

    @Test
    public void setUserInRequest() {
        requestWithGoodId();

        identityFilter.beforeHandle(request, new Response(request));

        assertThat((User) request.getAttributes().get("com.steambeat.user"), is(user));
    }

    @Test
    public void setNullUserInEmpty() {
        identityFilter.beforeHandle(request, new Response(request));

        assertThat(request.getAttributes().get("com.steambeat.user"), nullValue());
    }

    @Test
    public void setNullUserIfBadNamedCookie() {
        requestWithBadCookie();

        identityFilter.beforeHandle(request, new Response(request));

        assertThat(request.getAttributes().get("com.steambeat.user"), nullValue());
    }

    @Test
    public void setUserOnlyIfUserExists() {
        requestWithBadId();

        identityFilter.beforeHandle(request, new Response(request));

        assertThat(request.getAttributes().get("com.steambeat.user"), nullValue());
    }

    @Test
    public void canAuthentificate() {
        requestWithGoodIdAndSession();

        identityFilter.beforeHandle(request, new Response(request));

        assertThat((Boolean) request.getAttributes().get("com.steambeat.authentificated"), is(true));
    }

    @Test
    public void notAuthentificatedIfNoSession() {
        requestWithGoodId();

        identityFilter.beforeHandle(request, new Response(request));

        assertThat((Boolean) request.getAttributes().get("com.steambeat.authentificated"), is(false));
    }

    @Test
    public void notAuthentificatedIfBadSession() {
        requestWithGoodIdAndBadSession();

        identityFilter.beforeHandle(request, new Response(request));

        assertThat((Boolean) request.getAttributes().get("com.steambeat.authentificated"), is(false));
    }

    private void requestWithGoodId() {
        final Cookie cookie = new Cookie(1, "id", "mail@mail.com");
        final CookieSeries cookies = new CookieSeries();
        cookies.add(cookie);
        request.setCookies(cookies);
    }

    private void requestWithGoodIdAndSession() {
        final Cookie cookie = new Cookie(1, "id", "mail@mail.com");
        final Cookie sessionCookie = new Cookie(1, "session", session.getToken().toString());
        final CookieSeries cookies = new CookieSeries();
        cookies.add(cookie);
        cookies.add(sessionCookie);
        request.setCookies(cookies);
    }

    private void requestWithGoodIdAndBadSession() {
        final Cookie cookie = new Cookie(1, "id", "mail@mail.com");
        final Cookie sessionCookie = new Cookie(1, "session", UUID.randomUUID().toString());
        final CookieSeries cookies = new CookieSeries();
        cookies.add(cookie);
        cookies.add(sessionCookie);
        request.setCookies(cookies);
    }

    private void requestWithBadCookie() {
        final Cookie cookie = new Cookie(1, "bad", "mail@mail.com");
        final CookieSeries cookies = new CookieSeries();
        cookies.add(cookie);
        request.setCookies(cookies);
    }

    private void requestWithBadId() {
        final Cookie cookie = new Cookie(1, "id", "john@doe.com");
        final CookieSeries cookies = new CookieSeries();
        cookies.add(cookie);
        request.setCookies(cookies);
    }

    private Request request;
    private IdentityFilter identityFilter;
    private User user;
    private Session session;
}
