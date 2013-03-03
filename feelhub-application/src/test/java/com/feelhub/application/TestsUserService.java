package com.feelhub.application;

import com.feelhub.domain.user.SocialAuth;
import com.feelhub.domain.user.SocialNetwork;
import com.feelhub.domain.user.User;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
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
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
            }
        });
        userService = injector.getInstance(UserService.class);
    }



    @Test
    public void canCreateForFacebook() {
        userService.findOrCreateForFacebook("123", "test@test.com", "jb", "dusse", "fr_fr", "token");

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

        userService.findOrCreateForFacebook("123", "test@test.com", "jb", "dusse", "fr_fr", "token");

        assertThat(Repositories.users().getAll().size(), is(1));
    }

    @Test
    public void canConnectFormerUserToFacebookAccount() {
        final User user = new User();
        user.setEmail("test@test.com");
        Repositories.users().add(user);

        userService.findOrCreateForFacebook("123", "test@test.com", "jb", "dusse", "fr_fr", "token");

        assertThat(Repositories.users().getAll().size(), is(1));
        assertThat(user.getSocialAuth(SocialNetwork.FACEBOOK), notNullValue());
        assertThat(user.getSocialAuth(SocialNetwork.FACEBOOK).getId(), is("123"));
    }

    private UserService userService;
}
