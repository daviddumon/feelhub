package com.feelhub.application;

import com.feelhub.domain.session.EmailAlreadyUsed;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.user.*;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.google.inject.*;
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
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
            }
        });
        userService = injector.getInstance(UserService.class);
    }

    @Test
    public void canCreateAnUser() {
        final String email = "mail@mail.com";
        final String password = "password";
        final String fullname = "David John";
        final String language = "en";

        userService.createUser(email, password, fullname, language);

        assertThat(Repositories.users().getAll().size(), is(1));
        final User user = Repositories.users().getAll().get(0);
        assertThat(user.getFullname(), is(fullname));
        assertThat(user.getEmail(), is(email));
        assertThat(user.getLanguageCode(), is(FeelhubLanguage.fromCode("en").getCode()));
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
    public void canCreateForFacebook() {
        userService.findOrCreateForFacebook("123", "test@test.com", "jb", "dusse", "fr_fr", "token");

        final User user = Repositories.users().get(UserIds.facebook("123"));
        assertThat(user, notNullValue());
        assertThat(user.getId(), is(UserIds.facebook("123")));
    }

    @Test
    public void dontCreateTwiceForFacebook() {
        final User user = new User(UserIds.facebook("123"));
        Repositories.users().add(user);

        userService.findOrCreateForFacebook("123", "test@test.com", "jb", "dusse", "fr_fr", "token");

        assertThat(Repositories.users().getAll().size(), is(1));
    }

    private UserService userService;
}
