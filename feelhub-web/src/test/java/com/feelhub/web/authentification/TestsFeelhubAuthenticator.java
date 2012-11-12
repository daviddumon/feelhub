package com.feelhub.web.authentification;

import com.feelhub.domain.user.*;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import org.junit.*;
import org.junit.rules.ExpectedException;

public class TestsFeelhubAuthenticator {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void cannotAuthenticateIfAccountIsNotActive() {
        TestFactories.users().createFakeUser("mail@mail.com");

        exception.expect(BadUserException.class);
        new FeelhubAuthenticator().authenticate(new AuthRequest("mail@mail.com", "password", true));
    }

    @Test
    public void cannotAuthenticateUnknownUser() {
        exception.expect(BadUserException.class);
        new FeelhubAuthenticator().authenticate(new AuthRequest("id", "", true));
    }

    @Test
    public void canCheckPassword() {
        final User user = TestFactories.users().createFakeActiveUser("test@test.com");
        user.setPassword("toto");

        exception.expect(BadPasswordException.class);
        new FeelhubAuthenticator().authenticate(new AuthRequest(user.getId(), "tata", true));
    }
}
