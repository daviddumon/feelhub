package com.feelhub.application.command.session;

import com.feelhub.domain.session.Session;
import com.feelhub.domain.user.User;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.*;
import org.junit.*;

import static org.junit.Assert.*;

public class AuthenticateCommandTest {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Before
    public void before() {
        user = TestFactories.users().createFakeActiveUser("mail@mail.com");
    }

    @Test
    public void cannotAuthentificateWithoutSession() {
        final boolean result = new AuthenticateCommand(user.getId(), null).execute();

        assertFalse(result);
    }

    @Test
    public void canAuthentificateWithSession() {
        final Session session = TestFactories.sessions().createSessionFor(user);

        final boolean result = new AuthenticateCommand(user.getId(), session.getToken()).execute();

        assertTrue(result);
    }

    @Test
    public void mustHaveAGoodSessionForUser() {
        final User otherUser = TestFactories.users().createFakeActiveUser("othermail@mail.com");
        final Session session = TestFactories.sessions().createSessionFor(otherUser);

        final boolean result = new AuthenticateCommand(user.getId(), session.getToken()).execute();

        assertFalse(result);
    }

    @Test
    public void cannotAuthentificateForAnExpiredSession() {
        final Session session = TestFactories.sessions().createSessionFor(user);
        time.waitDays(1);

        final boolean result = new AuthenticateCommand(user.getId(), session.getToken()).execute();

        assertFalse(result);
    }

    private User user;
}
