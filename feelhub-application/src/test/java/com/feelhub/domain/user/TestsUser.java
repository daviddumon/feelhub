package com.feelhub.domain.user;

import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.fest.assertions.Assertions.*;


public class TestsUser {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void canCreate() {
        final User user = new UserFactory().createUser("email@email.com", "test", "Jb Dusse", "fr_fr");

        assertThat(user.getId()).isEqualTo("email@email.com");
    }

    @Test
    public void canSetEmailAsIdentifier() {
        final String email = "mymail@mail.com";
        final User user = new User("");

        user.setEmail(email);

        assertThat(user.getEmail()).isEqualTo(email);
    }

    @Test
    public void identifierIsARealEmail() {
        exception.expect(BadEmail.class);
        final String email = "notanemail";
        final User user = new User("");

        user.setEmail(email);
    }

    @Test
    public void lowercaseEmails() {
        final User user = new User("");

        user.setEmail("mYmail@mAIL.cOm");

        assertThat(user.getEmail()).isEqualTo("mymail@mail.com");
    }

    @Test
    public void trimEmail() {
        final User user = new User("");

        user.setEmail("  mymail@mail.com ");

        assertThat(user.getEmail()).isEqualTo("mymail@mail.com");
    }

    @Test
    public void canSetPassword() {
        final User user = new User("");

        user.setPassword("password");

        assertThat(user.getPassword()).isNotNull();
    }

    @Test
    public void canHashPassword() {
        final User user = new User("");

        user.setPassword("password");

        assertThat(user.getPassword()).isNotEqualTo("password");
    }

    @Test
    public void canCheckAPasswordMatches() {
        final String password = "password";
        final User user = new User("");
        user.setPassword(password);

        final boolean helloTest = user.checkPassword("hello");
        final boolean passwordTest = user.checkPassword(password);

        assertThat(helloTest).isFalse();
        assertThat(passwordTest).isTrue();
    }

    @Test
    public void canSetFullname() {
        final User user = new User("");
        final String fullname = "John doe";

        user.setFullname(fullname);

        assertThat(user.getFullname()).isEqualTo(fullname);
    }

    @Test
    public void canSetLanguageCode() {
        final User user = new User("");
        final String language = "English";
        final String languageCode = FeelhubLanguage.forString(language).getCode();

        user.setLanguageCode(languageCode);

        assertThat(user.getLanguageCode()).isEqualTo(languageCode);
    }

    @Test
    public void canActivate() {
        final User user = new User("");

        assertThat(user.isActive()).isEqualTo(false);

        user.activate();

        assertThat(user.isActive()).isEqualTo(true);
    }

    @Test
    public void hasSecret() {
        final User user = new User("");

        assertThat(user.getSecret()).isNotNull();
    }

    @Test
    public void canCreateFromFacebook() {
        final User user = new UserFactory().createFromFacebook("1234", "email@email.com", "first", "last", "FR…fr", "token");

        assertThat(user).isNotNull();
        assertThat(user.getEmail()).isEqualTo("email@email.com");
        assertThat(user.getFullname()).isEqualTo("first last");
        assertThat(user.getLanguageCode()).isEqualTo("fr…fr");
        assertThat(user.isActive()).isTrue();
        assertThat(user.getId()).isEqualTo("FB:1234");
        assertThat(user.getSocialToken(SocialNetwork.FACEBOOK)).isEqualTo(new SocialToken(SocialNetwork.FACEBOOK, "token"));
    }
}
