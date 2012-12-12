package com.feelhub.application;

import com.feelhub.domain.eventbus.*;
import com.feelhub.domain.user.*;
import com.feelhub.repositories.*;
import com.feelhub.repositories.fakeRepositories.*;
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
                bind(SessionProvider.class).to(FakeSessionProvider.class);
            }
        });
        activationService = injector.getInstance(ActivationService.class);
    }

    @Test
    public void canCreateActivation() {
        final User user = new User();
        DomainEventBus.INSTANCE.post(new UserCreatedEvent(user));

        assertThat(Repositories.activation().getAll()).hasSize(1);
        final Activation activation = Repositories.activation().getAll().get(0);
        assertThat(activation.getUserId()).isEqualTo(user.getId());
    }

    @Test
    public void doNotCreateIfUserAlreadyActivated() {
        final User user = new User();
        user.activate();

        DomainEventBus.INSTANCE.post(new UserCreatedEvent(user));

        assertThat(Repositories.activation().getAll()).hasSize(0);
    }

    @Test
    public void canPropagateSendMail() {
        bus.capture(ActivationCreatedEvent.class);
        final User user = new User();

        activationService.onUserCreated(new UserCreatedEvent(user));

        final ActivationCreatedEvent event = bus.lastEvent(ActivationCreatedEvent.class);
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
