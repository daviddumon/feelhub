package com.feelhub.web.authentification;

import com.feelhub.domain.user.BadUserException;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TestsFeelhubAuthenticator{

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
}
