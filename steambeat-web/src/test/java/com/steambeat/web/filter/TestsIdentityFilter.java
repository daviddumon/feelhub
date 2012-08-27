package com.steambeat.web.filter;

import com.steambeat.application.*;
import com.steambeat.domain.session.Session;
import com.steambeat.domain.user.*;
import com.steambeat.repositories.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.TestFactories;
import com.steambeat.web.WebApplicationTester;
import org.junit.*;
import org.restlet.*;
import org.restlet.data.Cookie;
import org.restlet.engine.util.CookieSeries;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

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
        session = sessionService.getSessionFor(user);
        identityFilter = new IdentityFilter(new UserService(new UserFactory()), new SessionService());
        identityFilter.setContext(restlet.getApplication().getContext());
    }

    @Test
    public void setUserInRequest() {
        setGoodCookieInRequest();

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
        setBadNamedCookieInRequest();

        identityFilter.beforeHandle(request, new Response(request));

        assertThat(request.getAttributes().get("com.steambeat.user"), nullValue());
    }

    @Test
    public void setUserOnlyIfUserExists() {
        setCookieForNonExistingUser();

        identityFilter.beforeHandle(request, new Response(request));

        assertThat(request.getAttributes().get("com.steambeat.user"), nullValue());
    }

    @Test
    public void setSessionInContext() {
        setGoodCookieInRequest();

        identityFilter.beforeHandle(request, new Response(request));

        assertThat((Session)request.getAttributes().get("com.steambeat.session"), is(session));
    }

    private void setGoodCookieInRequest() {
        final Cookie cookie = new Cookie(1, "id", "mail@mail.com");
        final CookieSeries cookies = new CookieSeries();
        cookies.add(cookie);
        request.setCookies(cookies);
    }

    private void setBadNamedCookieInRequest() {
        final Cookie cookie = new Cookie(1, "bad", "mail@mail.com");
        final CookieSeries cookies = new CookieSeries();
        cookies.add(cookie);
        request.setCookies(cookies);
    }

    private void setCookieForNonExistingUser() {
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
