package com.steambeat.domain.user;

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
}
