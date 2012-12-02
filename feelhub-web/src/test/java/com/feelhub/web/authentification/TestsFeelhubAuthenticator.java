package com.feelhub.web.authentification;

import com.feelhub.domain.user.BadPasswordException;
import com.feelhub.domain.user.BadUserException;
import com.feelhub.domain.user.User;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TestsFeelhubAuthenticator {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void cannotAuthenticateIfAccountIsNotActive() {
        final User fakeUser = TestFactories.users().createFakeUser("mail@mail.com");

        exception.expect(BadUserException.class);
        new FeelhubAuthenticator().authenticate(new AuthRequest(fakeUser.getEmail(), "password", true));
    }

    @Test
    public void cannotAuthenticateUnknownUser() {
        exception.expect(BadUserException.class);
        new FeelhubAuthenticator().authenticate(new AuthRequest("asutesiutar", "", true));
    }

    @Test
    public void canCheckPassword() {
        final User user = TestFactories.users().createFakeActiveUser("test@test.com");
        user.setPassword("toto");

        exception.expect(BadPasswordException.class);
        new FeelhubAuthenticator().authenticate(new AuthRequest(user.getEmail() , "tata", true));
    }
}
