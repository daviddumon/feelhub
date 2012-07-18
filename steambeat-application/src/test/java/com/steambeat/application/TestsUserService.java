package com.steambeat.application;

import com.steambeat.domain.user.User;
import com.steambeat.repositories.Repositories;
import com.steambeat.test.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.testFactories.TestFactories;
import org.junit.*;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class TestsUserService {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        userService = new UserService();
    }

    @Test
    public void canCreateAnUser() {
        final String email = "mail@mail.com";
        final String password = "password";
        final String fullname = "David John";
        final String language = "English";

        userService.createUser(email, password, fullname, language);

        assertThat(Repositories.users().getAll().size(), is(1));
        final User user = Repositories.users().getAll().get(0);
        assertThat(user.getFullname(), is(fullname));
        assertThat(user.getEmail(), is(email));
        assertThat(user.getLanguage(), is(language));
        assertTrue(user.checkPassword(password));
    }

    @Test
    public void cannotCreateAnUserIfEmailAlreadyUsed() {
        exception.expect(EmailAlreadyUsed.class);
        final String email = "mail@mail.com";
        final String password = "password";
        final String fullname = "David John";
        final String language = "English";
        TestFactories.users().createUser(email);

        userService.createUser(email, password, fullname, language);
    }

    @Test
    public void lowerAndTrimBeforeChekingEmail() {
        exception.expect(EmailAlreadyUsed.class);
        final String email = "Mail@mail.com ";
        final String password = "password";
        final String fullname = "David John";
        final String language = "English";
        TestFactories.users().createUser("mail@mail.com");

        userService.createUser(email, password, fullname, language);
    }

    @Test
    public void cannotAuthenticateIfAccountNotActive() {
        exception.expect(BadUserException.class);
        final User user = TestFactories.users().createUser("mail@mail.com");

        userService.authentificate("mail@mail.com", "password");
    }

    private UserService userService;
}
