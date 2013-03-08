package com.feelhub.domain.user.activation;

import com.feelhub.domain.eventbus.WithDomainEvent;
import com.feelhub.domain.user.User;
import org.junit.*;

import static org.fest.assertions.Assertions.*;

public class ActivationFactoryTest {

    @Rule
    public WithDomainEvent events = new WithDomainEvent();

    @Test
    public void canPropagateEvent() {
        final User user = new User();

        Activation activation = new ActivationFactory().createForUser(user);

        final ActivationCreatedEvent event = events.lastEvent(ActivationCreatedEvent.class);
        assertThat(event).isNotNull();
        assertThat(event.userId).isEqualTo(user.getId());
        assertThat(event.activationId).isEqualTo(activation.getId());
    }
}
