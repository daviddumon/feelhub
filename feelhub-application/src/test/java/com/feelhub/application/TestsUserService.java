package com.feelhub.application;

import com.feelhub.domain.session.EmailAlreadyUsed;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.user.*;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
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
        userService = new UserService(new UserFactory());
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
        assertThat(user.getLanguageCode(), is(FeelhubLanguage.forString(language).getCode()));
        assertTrue(user.checkPassword(password));
    }

    @Test
    public void cannotCreateAnUserIfEmailAlreadyUsed() {
        exception.expect(EmailAlreadyUsed.class);
        final String email = "mail@mail.com";
        final String password = "password";
        final String fullname = "David John";
        final String language = "English";
        TestFactories.users().createFakeUser(email);

        userService.createUser(email, password, fullname, language);
    }

    @Test
    public void lowerAndTrimBeforeChekingEmail() {
        exception.expect(EmailAlreadyUsed.class);
        final String email = "Mail@mail.com ";
        final String password = "password";
        final String fullname = "David John";
        final String language = "English";
        TestFactories.users().createFakeUser("mail@mail.com");

        userService.createUser(email, password, fullname, language);
    }

    @Test
    public void cannotAuthenticateIfAccountIsNotActive() {
        exception.expect(BadUserException.class);
        final User user = TestFactories.users().createFakeUser("mail@mail.com");

        userService.authentificate("mail@mail.com", "password");
    }

    private UserService userService;
}