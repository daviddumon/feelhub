package com.feelhub.application.command.user;

import com.feelhub.domain.session.EmailAlreadyUsed;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.user.User;
import com.feelhub.domain.user.activation.Activation;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class CreateUserCommandTest {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void canCreateAnUser() {
        final String email = "mail@mail.com";
        final String password = "password";
        final String fullname = "David John";
        final String language = "en";

        final UUID userId = new CreateUserCommand(email, password, fullname, language).execute();

        assertThat(Repositories.users().getAll().size(), is(1));
        final User user = Repositories.users().get(userId);
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

        new CreateUserCommand(email, password, fullname, language).execute();
    }

    @Test
    public void lowerAndTrimBeforeChekingEmail() {
        exception.expect(EmailAlreadyUsed.class);
        final String email = "Mail@mail.com ";
        final String password = "password";
        final String fullname = "David John";
        final String language = "English";
        TestFactories.users().createFakeUser("mail@mail.com");

        new CreateUserCommand(email, password, fullname, language).execute();
    }

    @Test
    public void createActivation() {

        final UUID userId = new CreateUserCommand("mail@mail.com", "password", "David John", "en").execute();

        assertThat(Repositories.activation().getAll().size(), is(1));
        final Activation activation = Repositories.activation().getAll().get(0);
        Assert.assertThat(activation.getUserId(), is(userId));
    }
}
