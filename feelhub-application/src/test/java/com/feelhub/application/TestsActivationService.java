package com.feelhub.application;

import com.feelhub.domain.eventbus.*;
import com.feelhub.domain.user.*;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.google.inject.*;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.util.UUID;

import static org.fest.assertions.Assertions.*;

public class TestsActivationService {

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    private ActivationService activationService;

    @Before
    public void avant() {
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
            }
        });
        activationService = injector.getInstance(ActivationService.class);
    }

    @Test
    public void canCreateActivation() {
        DomainEventBus.INSTANCE.post(new UserCreatedEvent(new User("id")));

        assertThat(Repositories.activation().getAll()).hasSize(1);
        final Activation activation = Repositories.activation().getAll().get(0);
        assertThat(activation.getUserId()).isEqualTo("id");
    }

    @Test
    public void canPropagateSendMail() {
        bus.capture(UserConfirmationMailEvent.class);
        final User user = new User("id");

        activationService.onUserCreated(new UserCreatedEvent(user));

        final UserConfirmationMailEvent event = bus.lastEvent(UserConfirmationMailEvent.class);
        assertThat(event).isNotNull();
        assertThat(event.getUser()).isEqualTo(user);
        assertThat(event.getActivation()).isNotNull();
    }

    @Test
    public void doesNotCreateActivationForActivatedUser() {
        activationService.onUserCreated(new UserCreatedEvent(TestFactories.users().createActiveUser("testt@test.com")));

        assertThat(Repositories.activation().getAll()).isEmpty();
    }

    @Test
    public void canConfirm() {
        final User user = TestFactories.users().createFakeUser("test@test.com");
        final Activation activation = new Activation(user);
        Repositories.activation().add(activation);

        activationService.confirm(activation.getId());

        assertThat(user.isActive());
        assertThat(Repositories.activation().getAll()).isEmpty();
    }

    @Test
    public void canThrowOnUnknownActivation() {
        exception.expect(ActivationNotFoundException.class);

        activationService.confirm(UUID.randomUUID());
    }
}
