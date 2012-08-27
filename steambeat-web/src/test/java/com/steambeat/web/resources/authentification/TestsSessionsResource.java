package com.steambeat.web.resources.authentification;

import com.steambeat.domain.session.Session;
import com.steambeat.domain.user.User;
import com.steambeat.repositories.Repositories;
import com.steambeat.test.*;
import com.steambeat.repositories.fakeRepositories.WithFakeRepositories;
import com.steambeat.web.*;
import org.joda.time.Interval;
import org.junit.*;
import org.restlet.data.*;
import org.restlet.engine.util.CookieSeries;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class TestsSessionsResource {

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Test
    public void canRequestToken() {
        final User user = TestFactories.users().createActiveUser("mail@mail.com");
        final ClientResource sessions = restlet.newClientResource("/sessions");
        final Form parameters = new Form();
        parameters.add("email", user.getEmail());
        parameters.add("password", "password");

        sessions.post(parameters);

        assertThat(sessions.getStatus(), is(Status.SUCCESS_CREATED));
    }

    @Test
    public void sessionsNeedAnEmail() {
        final User user = TestFactories.users().createActiveUser("mail@mail.com");
        final ClientResource sessions = restlet.newClientResource("/sessions");
        final Form parameters = new Form();
        parameters.add("password", "password");

        sessions.post(parameters);

        assertThat(sessions.getStatus(), is(Status.CLIENT_ERROR_BAD_REQUEST));
    }

    @Test
    public void sessionsNeedAPassword() {
        final User user = TestFactories.users().createActiveUser("mail@mail.com");
        final ClientResource sessions = restlet.newClientResource("/sessions");
        final Form parameters = new Form();
        parameters.add("email", user.getEmail());

        sessions.post(parameters);

        assertThat(sessions.getStatus(), is(Status.CLIENT_ERROR_BAD_REQUEST));
    }

    @Test
    public void doNotCreateSessionForUnknownUser() {
        final ClientResource sessions = restlet.newClientResource("/sessions");
        final Form parameters = new Form();
        parameters.add("email", "mail@mail.com");
        parameters.add("password", "password");

        sessions.post(parameters);

        assertThat(sessions.getStatus(), is(Status.CLIENT_ERROR_FORBIDDEN));
    }

    @Test
    public void doNotCreateSessionIfBadPassword() {
        final User user = TestFactories.users().createActiveUser("mail@mail.com");
        final ClientResource sessions = restlet.newClientResource("/sessions");
        final Form parameters = new Form();
        parameters.add("email", user.getEmail());
        parameters.add("password", "password2");

        sessions.post(parameters);

        assertThat(sessions.getStatus(), is(Status.CLIENT_ERROR_UNAUTHORIZED));
    }

    @Test
    public void setCookieOnSuccessfulLogin() {
        final User user = TestFactories.users().createActiveUser("mail@mail.com");
        final ClientResource sessions = restlet.newClientResource("/sessions");
        final Form parameters = new Form();
        parameters.add("email", user.getEmail());
        parameters.add("password", "password");

        sessions.post(parameters);

        assertThat(sessions.getStatus(), is(Status.SUCCESS_CREATED));
        assertFalse(sessions.getResponse().getCookieSettings().isEmpty());
    }

    @Test
    public void doNotSetCookieIfErrorForbidden() {
        final User user = TestFactories.users().createActiveUser("mail@mail.com");
        final ClientResource sessions = restlet.newClientResource("/sessions");
        final Form parameters = new Form();
        parameters.add("email", "fake@mail.com");
        parameters.add("password", "password");

        sessions.post(parameters);

        assertThat(sessions.getStatus(), is(Status.CLIENT_ERROR_FORBIDDEN));
        assertTrue(sessions.getResponse().getCookieSettings().isEmpty());
    }

    @Test
    public void doNotSetCookieIfBadRequest() {
        final User user = TestFactories.users().createActiveUser("mail@mail.com");
        final ClientResource sessions = restlet.newClientResource("/sessions");
        final Form parameters = new Form();
        parameters.add("email", user.getEmail());

        sessions.post(parameters);

        assertThat(sessions.getStatus(), is(Status.CLIENT_ERROR_BAD_REQUEST));
        assertTrue(sessions.getResponse().getCookieSettings().isEmpty());
    }

    @Test
    public void setIdCookie() {
        final User user = TestFactories.users().createActiveUser("mail@mail.com");
        final ClientResource sessions = restlet.newClientResource("/sessions");
        final Form parameters = new Form();
        parameters.add("email", user.getEmail());
        parameters.add("password", "password");

        sessions.post(parameters);

        assertThat(sessions.getStatus(), is(Status.SUCCESS_CREATED));
        assertFalse(sessions.getResponse().getCookieSettings().isEmpty());
        final CookieSetting id = sessions.getResponse().getCookieSettings().getFirst("id");
        assertThat(id, notNullValue());
        assertThat(id.getComment(), is("id cookie"));
        assertThat(id.getName(), is("id"));
        assertTrue(id.isAccessRestricted());
        assertThat(id.isSecure(), is(Boolean.valueOf(restlet.getApplication().getContext().getAttributes().get("com.steambeat.cookie.secure").toString())));
        assertThat(id.getVersion(), is(0));
        assertThat(id.getValue(), is(user.getEmail()));
        assertThat(id.getDomain(), is(restlet.getApplication().getContext().getAttributes().get("com.steambeat.cookie.domain").toString()));
        assertThat(id.getMaxAge(), is(157680000));
        assertThat(id.getPath(), is("/"));
    }

    @Test
    public void setTokenCookie() {
        final User user = TestFactories.users().createActiveUser("mail@mail.com");
        final ClientResource sessions = restlet.newClientResource("/sessions");
        final Form parameters = new Form();
        parameters.add("email", user.getEmail());
        parameters.add("password", "password");

        sessions.post(parameters);

        final Session session = Repositories.sessions().getAll().get(0);
        assertThat(sessions.getStatus(), is(Status.SUCCESS_CREATED));
        assertFalse(sessions.getResponse().getCookieSettings().isEmpty());
        final CookieSetting sessionCookie = sessions.getResponse().getCookieSettings().getFirst("session");
        assertThat(sessionCookie, notNullValue());
        assertThat(sessionCookie.getComment(), is("session cookie"));
        assertThat(sessionCookie.getName(), is("session"));
        assertTrue(sessionCookie.isAccessRestricted());
        assertThat(sessionCookie.getVersion(), is(0));
        assertThat(sessionCookie.getValue(), is(session.getToken().toString()));
        assertThat(sessionCookie.isSecure(), is(Boolean.valueOf(restlet.getApplication().getContext().getAttributes().get("com.steambeat.cookie.secure").toString())));
        assertThat(sessionCookie.getDomain(), is(restlet.getApplication().getContext().getAttributes().get("com.steambeat.cookie.domain").toString()));
        assertThat(sessionCookie.getMaxAge(), is((int) new Interval(time.getNow(), session.getExpirationDate()).toDurationMillis() / 1000));
        assertThat(sessionCookie.getPath(), is("/"));
    }

    @Test
    public void deleteSessionWhenSessionIsDeletedWithCorrectParameters() {
        final User user = TestFactories.users().createActiveUser("mail@mail.com");
        final ClientResource sessions = restlet.newClientResource("/sessions");
        final Session session = TestFactories.sessions().createSessionFor(user);
        final CookieSeries cookies = getGoodCookies(user, session);

        sessions.delete(cookies);

        assertThat(Repositories.sessions().getAll().size(), is(0));
        assertThat(sessions.getStatus(), is(Status.SUCCESS_ACCEPTED));
    }

    @Test
    public void deleteCookieWhenSessionIsDeletedWithCorrectParameters() {
        final User user = TestFactories.users().createActiveUser("mail@mail.com");
        final ClientResource sessions = restlet.newClientResource("/sessions");
        final Session session = TestFactories.sessions().createSessionFor(user);
        final CookieSeries cookies = getGoodCookies(user, session);

        sessions.delete(cookies);

        assertFalse(sessions.getResponse().getCookieSettings().isEmpty());
    }

    @Test
    public void cannotDeleteWithoutGoodCookies() {
        final User user = TestFactories.users().createActiveUser("mail@mail.com");
        final ClientResource sessions = restlet.newClientResource("/sessions");
        final Session session = TestFactories.sessions().createSessionFor(user);

        sessions.delete(null);

        assertThat(sessions.getStatus(), is(Status.CLIENT_ERROR_BAD_REQUEST));
        assertThat(Repositories.sessions().getAll().size(), is(1));
    }

    private CookieSeries getGoodCookies(final User user, final Session session) {
        final Cookie id = new Cookie(1, "id", user.getEmail());
        final Cookie sessionCookie = new Cookie(1, "session", session.getToken().toString());
        final CookieSeries cookies = new CookieSeries();
        cookies.add(id);
        cookies.add(sessionCookie);
        return cookies;
    }
}
