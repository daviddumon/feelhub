package com.feelhub.application.command.user;

import com.feelhub.domain.user.*;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import org.junit.*;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class CreateUserFromFacebookCommandTest {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void canCreateForFacebook() {
        new CreateUserFromFacebookCommand("123", "test@test.com", "jb", "dusse", "fr_fr", "token").execute();

        Assert.assertThat(Repositories.users().getAll().size(), is(1));
        final User user = Repositories.users().getAll().get(0);
        assertThat(user, notNullValue());
        assertThat(user.getSocialAuth(SocialNetwork.FACEBOOK).getId(), is("123"));
    }

    @Test
    public void dontCreateTwiceForFacebook() {
        final User user = new User();
        user.addSocialAuth(new SocialAuth(SocialNetwork.FACEBOOK, "123", "token"));
        Repositories.users().add(user);

        new CreateUserFromFacebookCommand("123", "test@test.com", "jb", "dusse", "fr_fr", "token").execute();

        assertThat(Repositories.users().getAll().size(), is(1));
    }

    @Test
    public void canConnectFormerUserToFacebookAccount() {
        final User user = new User();
        user.setEmail("test@test.com");
        Repositories.users().add(user);

        new CreateUserFromFacebookCommand("123", "test@test.com", "jb", "dusse", "fr_fr", "token").execute();

        assertThat(Repositories.users().getAll().size(), is(1));
        assertThat(user.getSocialAuth(SocialNetwork.FACEBOOK), notNullValue());
        assertThat(user.getSocialAuth(SocialNetwork.FACEBOOK).getId(), is("123"));
    }
}
