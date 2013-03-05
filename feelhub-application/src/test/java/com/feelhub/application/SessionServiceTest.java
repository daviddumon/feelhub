package com.feelhub.application;

import com.feelhub.domain.session.Session;
import com.feelhub.domain.user.User;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.SystemTime;
import com.feelhub.test.TestFactories;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class SessionServiceTest {

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
