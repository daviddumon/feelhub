package com.steambeat.web.filter;

import com.steambeat.application.UserService;
import com.steambeat.domain.user.UserFactory;
import com.steambeat.test.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.testFactories.TestFactories;
import com.steambeat.web.WebApplicationTester;
import org.junit.*;
import org.restlet.*;
import org.restlet.data.Cookie;
import org.restlet.engine.util.CookieSeries;

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
        TestFactories.users().createUser("mail@mail.com", "full name");
        identityFilter = new IdentityFilter(new UserService(new UserFactory()));
        identityFilter.setContext(restlet.getApplication().getContext());
    }

    @Test
    public void setNameInContextFromCookie() {
        setGoodCookieInRequest();

        identityFilter.beforeHandle(request, new Response(request));

        final Context context = identityFilter.getContext();
        assertThat(context.getAttributes().get("com.steambeat.identity").toString(), is("full name"));
    }

    @Test
    public void setEmptyNameIfNoCookie() {
        identityFilter.beforeHandle(request, new Response(request));

        final Context context = identityFilter.getContext();
        assertTrue(context.getAttributes().get("com.steambeat.identity").toString().isEmpty());
    }

    @Test
    public void setEmptyNameIfBadNamedCookie() {
        setBadNamedCookieInRequest();

        identityFilter.beforeHandle(request, new Response(request));

        final Context context = identityFilter.getContext();
        assertTrue(context.getAttributes().get("com.steambeat.identity").toString().isEmpty());
    }

    @Test
    public void setIdentityOnlyIfUserExists() {
        setCookieIfNotExistingUserInRequest();

        identityFilter.beforeHandle(request, new Response(request));

        final Context context = identityFilter.getContext();
        assertTrue(context.getAttributes().get("com.steambeat.identity").toString().isEmpty());
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

    private void setCookieIfNotExistingUserInRequest() {
        final Cookie cookie = new Cookie(1, "id", "john@doe.com");
        final CookieSeries cookies = new CookieSeries();
        cookies.add(cookie);
        request.setCookies(cookies);
    }

    private Request request;
    private IdentityFilter identityFilter;
}
