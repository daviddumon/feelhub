package com.feelhub.domain.user;

import com.feelhub.domain.eventbus.WithDomainEvent;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import org.junit.*;
import org.junit.rules.ExpectedException;

import static org.fest.assertions.Assertions.*;


public class TestsUser {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Test
    public void canCreate() {
        bus.capture(UserCreatedEvent.class);
        final User user = new UserFactory().createUser("email@email.com", "test", "Jb Dusse", "FR_fr");

        assertThat(user.getId()).isNotNull();
        final UserCreatedEvent event = bus.lastEvent(UserCreatedEvent.class);
        assertThat(event.getUser()).isEqualTo(user);
    }

    @Test
    public void canSetEmailAsIdentifier() {
        final String email = "mymail@mail.com";
        final User user = new User();

        user.setEmail(email);

        assertThat(user.getEmail()).isEqualTo(email);
    }

    @Test
    public void identifierIsARealEmail() {
        exception.expect(BadEmail.class);
        final String email = "notanemail";
        final User user = new User();

        user.setEmail(email);
    }

    @Test
    public void lowercaseEmails() {
        final User user = new User();

        user.setEmail("mYmail@mAIL.cOm");

        assertThat(user.getEmail()).isEqualTo("mymail@mail.com");
    }

    @Test
    public void trimEmail() {
        final User user = new User();

        user.setEmail("  mymail@mail.com ");

        assertThat(user.getEmail()).isEqualTo("mymail@mail.com");
    }

    @Test
    public void canSetPassword() {
        final User user = new User();

        user.setPassword("password");

        assertThat(user.getPassword()).isNotNull();
    }

    @Test
    public void canHashPassword() {
        final User user = new User();

        user.setPassword("password");

        assertThat(user.getPassword()).isNotEqualTo("password");
    }

    @Test
    public void canCheckAPasswordMatches() {
        final String password = "password";
        final User user = new User();
        user.setPassword(password);

        final boolean helloTest = user.checkPassword("hello");
        final boolean passwordTest = user.checkPassword(password);

        assertThat(helloTest).isFalse();
        assertThat(passwordTest).isTrue();
    }

    @Test
    public void canSetFullname() {
        final User user = new User();
        final String fullname = "John doe";

        user.setFullname(fullname);

        assertThat(user.getFullname()).isEqualTo(fullname);
    }

    @Test
    public void canSetLanguage() {
        final User user = new User();

        final FeelhubLanguage language = FeelhubLanguage.fromCountryName("English");

        user.setLanguage(language);

        assertThat(user.getLanguageCode()).isEqualTo("en");
        assertThat(user.getLanguage()).isEqualTo(FeelhubLanguage.fromCode("en"));
    }

    @Test
    public void canActivate() {
        final User user = new User();

        assertThat(user.isActive()).isEqualTo(false);

        user.activate();

        assertThat(user.isActive()).isEqualTo(true);
    }

    @Test
    public void canCreateFromFacebook() {
        bus.capture(UserCreatedEvent.class);
        final User user = new UserFactory().createFromFacebook("1234", "email@email.com", "first", "last", "fr_FR", "token");

        assertThat(user).isNotNull();
        assertThat(user.getEmail()).isEqualTo("email@email.com");
        assertThat(user.getFullname()).isEqualTo("first last");
        assertThat(user.getLanguageCode()).isEqualTo("fr");
        assertThat(user.isActive()).isTrue();
        assertThat(user.getSocialAuth(SocialNetwork.FACEBOOK)).isEqualTo(new SocialAuth(SocialNetwork.FACEBOOK, "1234", "token"));
        assertThat(user.getSocialAuth(SocialNetwork.FACEBOOK).getToken()).isEqualTo("token");
        final UserCreatedEvent event = bus.lastEvent(UserCreatedEvent.class);
        assertThat(event).isNotNull();
        assertThat(event.getUser()).isEqualTo(user);
    }
}
