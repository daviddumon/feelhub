package com.feelhub.domain.user.activation;

import com.feelhub.domain.eventbus.WithDomainEvent;
import com.feelhub.domain.user.User;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import org.junit.*;

import static org.fest.assertions.Assertions.*;

public class ActivationTest {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Test
    public void canCreateActivation() {
        final User user = new User();
        final Activation activation = new Activation(user);

        assertThat(activation.getId()).isNotNull();
        assertThat(activation.getUserId()).isEqualTo(user.getId());
    }

    @Test
    public void canConfirm() {
        final User user = new User();
        Repositories.users().add(user);
        final Activation activation = new Activation(user);

        activation.confirm();

        assertThat(user.isActive()).isTrue();
        assertThat(bus.lastEvent(UserActivatedEvent.class)).isNotNull();
    }
}
