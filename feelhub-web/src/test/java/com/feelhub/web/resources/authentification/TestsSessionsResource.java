package com.feelhub.web.resources.authentification;

import com.feelhub.domain.session.Session;
import com.feelhub.domain.user.User;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.*;
import com.feelhub.web.*;
import org.joda.time.DateTime;
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
    public void isMapped() {
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
        TestFactories.users().createActiveUser("mail@mail.com");
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
    public void setCookieOnSuccessLogin() {
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
        TestFactories.users().createActiveUser("mail@mail.com");
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
        assertThat(id.isSecure(), is(false));
        assertThat(id.getVersion(), is(0));
        assertThat(id.getValue(), is(user.getEmail()));
        assertThat(id.getDomain(), is(".test.localhost"));
        assertThat(id.getMaxAge(), is(10));
        assertThat(id.getPath(), is("/"));
    }

    @Test
    public void setSessionCookie() {
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
        assertThat(sessionCookie.isSecure(), is(false));
        assertThat(sessionCookie.getDomain(), is(".test.localhost"));
        assertThat(sessionCookie.getMaxAge(), is(1));
        assertThat(sessionCookie.getPath(), is("/"));
    }

    @Test
    public void setSessionCookieWithRememberOn() {
        final User user = TestFactories.users().createActiveUser("mail@mail.com");
        final ClientResource sessions = restlet.newClientResource("/sessions");
        final Form parameters = new Form();
        parameters.add("email", user.getEmail());
        parameters.add("password", "password");
        parameters.add("remember", "on");

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
        assertThat(sessionCookie.isSecure(), is(false));
        assertThat(sessionCookie.getDomain(), is(".test.localhost"));
        assertThat(sessionCookie.getMaxAge(), is(Integer.valueOf(10)));
        assertThat(sessionCookie.getPath(), is("/"));
        assertThat(session.getExpirationDate(), is(new DateTime().plusSeconds(10)));
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
