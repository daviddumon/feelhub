package com.feelhub.application.command.user;

import com.feelhub.domain.eventbus.WithDomainEvent;
import com.feelhub.domain.user.*;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.util.UUID;

import static org.fest.assertions.Assertions.*;

public class CreateUserFromGoogleCommandTest {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Test
    public void returnsCorrectSocialNetwork() {
        final CreateUserFromGoogleCommand command = new CreateUserFromGoogleCommand("", "", "", "", "", "");

        final SocialNetwork socialNetwork = command.socialNetwork();

        assertThat(socialNetwork).isEqualTo(SocialNetwork.GOOGLE);
    }

    @Test
    public void canCreateUser() {
        final CreateUserFromGoogleCommand command = new CreateUserFromGoogleCommand("id", "jb@gmail.com", "jb", "dusse", "fr_fr", "token");

        final UUID id = command.execute();

        assertThat(id).isNotNull();
        final User user = Repositories.users().get(id);
        assertThat(user.getSocialAuth(SocialNetwork.GOOGLE)).isNotNull();
    }
}
