package com.steambeat.application;

import com.steambeat.repositories.Repositories;
import com.steambeat.test.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.testFactories.TestFactories;
import org.junit.*;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

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

        userService.createUser(email, password);

        assertThat(Repositories.users().getAll().size(), is(1));
    }

    @Test
    public void cannotCreateAnUserIfEmailAlreadyUsed() {
        exception.expect(EmailAlreadyUsed.class);
        final String email = "mail@mail.com";
        final String password = "password";
        TestFactories.users().createUser(email);

        userService.createUser(email, password);
    }

    @Test
    public void lowerAndTrimBeforeChekingEmail() {
        exception.expect(EmailAlreadyUsed.class);
        final String email = "Mail@mail.com ";
        final String password = "password";
        TestFactories.users().createUser("mail@mail.com");

        userService.createUser(email, password);
    }

    private UserService userService;
}
