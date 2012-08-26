package com.steambeat.application;

import com.steambeat.domain.session.Session;
import com.steambeat.domain.user.User;
import com.steambeat.repositories.Repositories;
import com.steambeat.repositories.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.*;
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
        sessionService = new SessionService();
    }

    @Test
    public void canCreateASessionForAnUser() {
        final User user = TestFactories.users().createUser("mail@mail.com");

        final Session session = sessionService.getSessionFor(user);

        assertThat(session, notNullValue());
        assertThat(session.getEmail(), is(user.getEmail()));
        assertThat(session.getToken(), notNullValue());
    }

    @Test
    public void persistSession() {
        final User user = TestFactories.users().createUser("mail@mail.com");

        final Session session = sessionService.getSessionFor(user);

        assertThat(Repositories.sessions().getAll().size(), is(1));
    }

    @Test
    public void renewSessionWhenLogged() {
        final User user = TestFactories.users().createUser("mail@mail.com");
        sessionService.getSessionFor(user);
        time.waitHours(10);

        final Session session = sessionService.getSessionFor(user);

        assertFalse(session.isExpired());
        assertThat(Repositories.sessions().getAll().size(), is(1));
    }

    private SessionService sessionService;
}
