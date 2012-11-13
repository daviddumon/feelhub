package com.feelhub.application;

import com.feelhub.domain.session.Session;
import com.feelhub.domain.user.User;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.*;
import com.google.inject.*;
import org.joda.time.DateTime;
import org.junit.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class TestsSessionService {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Before
    public void before() {
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
            }
        });
        sessionService = injector.getInstance(SessionService.class);
        user = TestFactories.users().createFakeActiveUser("mail@mail.com");
    }

    @Test
    public void canCreateASessionForAnUser() {
        final Session session = sessionService.createSession(user, new DateTime().plusHours(1));

        assertThat(session, notNullValue());
        assertThat(session.getUserId(), is(user.getId()));
        assertThat(session.getToken(), notNullValue());
    }

    @Test
    public void persistSession() {
        sessionService.createSession(user, new DateTime().plusHours(1));

        assertThat(Repositories.sessions().getAll().size(), is(1));
    }

    @Test
    public void canDeteleSession() {
        final Session session = TestFactories.sessions().createSessionFor(user);

        sessionService.deleteSession(session.getToken());

        assertThat(Repositories.sessions().getAll().size(), is(0));
    }

    @Test
    public void cannotAuthentificateWithoutSession() {
        final boolean result = sessionService.authentificate(user, null);

        assertFalse(result);
    }

    @Test
    public void canAuthentificateWithSession() {
        final Session session = TestFactories.sessions().createSessionFor(user);

        final boolean result = sessionService.authentificate(user, session.getToken());

        assertTrue(result);
    }

    @Test
    public void mustHaveAGoodSessionForUser() {
        final User otherUser = TestFactories.users().createFakeActiveUser("othermail@mail.com");
        final Session session = TestFactories.sessions().createSessionFor(otherUser);

        final boolean result = sessionService.authentificate(user, session.getToken());

        assertFalse(result);
    }

    @Test
    public void cannotAuthentificateForAnExpiredSession() {
        final Session session = TestFactories.sessions().createSessionFor(user);
        time.waitDays(1);

        final boolean result = sessionService.authentificate(user, session.getToken());

        assertFalse(result);
    }

    private SessionService sessionService;
    private User user;
}
