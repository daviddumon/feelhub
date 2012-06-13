package com.steambeat.web.resources;

import com.steambeat.domain.session.Session;
import com.steambeat.domain.user.User;
import com.steambeat.repositories.Repositories;
import com.steambeat.test.SystemTime;
import com.steambeat.test.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.testFactories.TestFactories;
import com.steambeat.web.*;
import org.joda.time.Interval;
import org.junit.*;
import org.restlet.data.*;

import static org.hamcrest.MatcherAssert.assertThat;
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
        final User user = TestFactories.users().createUser("mail@mail.com");
        final ClientResource sessions = restlet.newClientResource("/sessions");
        final Form parameters = new Form();
        parameters.add("email", user.getEmail());
        parameters.add("password", "password");

        sessions.post(parameters);

        assertThat(sessions.getStatus(), is(Status.SUCCESS_CREATED));
    }

    @Test
    public void sessionsNeedAnEmail() {
        final User user = TestFactories.users().createUser("mail@mail.com");
        final ClientResource sessions = restlet.newClientResource("/sessions");
        final Form parameters = new Form();
        parameters.add("password", "password");

        sessions.post(parameters);

        assertThat(sessions.getStatus(), is(Status.CLIENT_ERROR_BAD_REQUEST));
    }

    @Test
    public void sessionsNeedAPassword() {
        final User user = TestFactories.users().createUser("mail@mail.com");
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
        final User user = TestFactories.users().createUser("mail@mail.com");
        final ClientResource sessions = restlet.newClientResource("/sessions");
        final Form parameters = new Form();
        parameters.add("email", user.getEmail());
        parameters.add("password", "password2");

        sessions.post(parameters);

        assertThat(sessions.getStatus(), is(Status.CLIENT_ERROR_FORBIDDEN));
    }

    @Test
    public void setCookieOnSuccessfulLogin() {
        final User user = TestFactories.users().createUser("mail@mail.com");
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
        final User user = TestFactories.users().createUser("mail@mail.com");
        final ClientResource sessions = restlet.newClientResource("/sessions");
        final Form parameters = new Form();
        parameters.add("email", user.getEmail());
        parameters.add("password", "password2");

        sessions.post(parameters);

        assertThat(sessions.getStatus(), is(Status.CLIENT_ERROR_FORBIDDEN));
        assertTrue(sessions.getResponse().getCookieSettings().isEmpty());
    }

    @Test
    public void doNotSetCookieIfBadRequest() {
        final User user = TestFactories.users().createUser("mail@mail.com");
        final ClientResource sessions = restlet.newClientResource("/sessions");
        final Form parameters = new Form();
        parameters.add("email", user.getEmail());

        sessions.post(parameters);

        assertThat(sessions.getStatus(), is(Status.CLIENT_ERROR_BAD_REQUEST));
        assertTrue(sessions.getResponse().getCookieSettings().isEmpty());
    }

    @Test
    public void setIdCookie() {
        final User user = TestFactories.users().createUser("mail@mail.com");
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
        assertTrue(id.isSecure());
        assertTrue(id.isAccessRestricted());
        assertThat(id.getVersion(), is(0));
        assertThat(id.getValue(), is(user.getEmail()));
        assertThat(id.getDomain(), is(restlet.getApplication().getContext().getAttributes().get("com.steambeat.cookie").toString()));
        assertThat(id.getMaxAge(), is(157680000));
        assertThat(id.getPath(), is("/"));
    }

    @Test
    public void setTokenCookie() {
        final User user = TestFactories.users().createUser("mail@mail.com");
        final ClientResource sessions = restlet.newClientResource("/sessions");
        final Form parameters = new Form();
        parameters.add("email", user.getEmail());
        parameters.add("password", "password");

        sessions.post(parameters);

        final Session session = Repositories.sessions().getAll().get(0);
        assertThat(sessions.getStatus(), is(Status.SUCCESS_CREATED));
        assertFalse(sessions.getResponse().getCookieSettings().isEmpty());
        final CookieSetting id = sessions.getResponse().getCookieSettings().getFirst("token");
        assertThat(id, notNullValue());
        assertThat(id.getComment(), is("token cookie"));
        assertThat(id.getName(), is("token"));
        assertTrue(id.isSecure());
        assertTrue(id.isAccessRestricted());
        assertThat(id.getVersion(), is(0));
        assertThat(id.getValue(), is(session.getToken().toString()));
        assertThat(id.getDomain(), is(restlet.getApplication().getContext().getAttributes().get("com.steambeat.cookie").toString()));
        assertThat(id.getMaxAge(), is((int)new Interval(time.getNow(), session.getExpirationDate()).toDurationMillis()/1000));
        assertThat(id.getPath(), is("/"));
    }
}
