package com.steambeat.domain.user;

import com.steambeat.domain.thesaurus.SteambeatLanguage;
import org.junit.*;
import org.junit.rules.ExpectedException;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class TestsUser {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void canSetEmailAsIdentifier() {
        final String email = "mymail@mail.com";
        final User user = new User();

        user.setEmail(email);

        assertThat(user.getEmail(), is(email));
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

        assertThat(user.getEmail(), is("mymail@mail.com"));
    }

    @Test
    public void trimEmail() {
        final User user = new User();

        user.setEmail("  mymail@mail.com ");

        assertThat(user.getEmail(), is("mymail@mail.com"));
    }

    @Test
    public void canSetPassword() {
        final User user = new User();

        user.setPassword("password");

        assertThat(user.getPassword(), notNullValue());
    }

    @Test
    public void canHashPassword() {
        final User user = new User();

        user.setPassword("password");

        assertThat(user.getPassword(), not("password"));
    }

    @Test
    public void canCheckAPasswordMatches() {
        final String password = "password";
        final User user = new User();
        user.setPassword(password);

        final boolean helloTest = user.checkPassword("hello");
        final boolean passwordTest = user.checkPassword(password);

        assertFalse(helloTest);
        assertTrue(passwordTest);
    }

    @Test
    public void canSetFullname() {
        final User user = new User();
        final String fullname = "John doe";

        user.setFullname(fullname);

        assertThat(user.getFullname(), is(fullname));
    }

    @Test
    public void canSetLanguageCode() {
        final User user = new User();
        final String language = "English";
        final String languageCode = SteambeatLanguage.forString(language).getCode();

        user.setLanguageCode(languageCode);

        assertThat(user.getLanguageCode(), is(languageCode));
    }

    @Test
    public void canActivate() {
        final User user = new User();

        assertThat(user.isActive(), is(false));

        user.activate();

        assertThat(user.isActive(), is(true));
    }

    @Test
    public void hasSecret() {
        final User user = new User();

        assertThat(user.getSecret(), notNullValue());
    }
}
