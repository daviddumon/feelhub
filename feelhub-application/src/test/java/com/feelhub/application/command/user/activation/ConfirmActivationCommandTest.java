package com.feelhub.application.command.user.activation;

import com.feelhub.domain.user.User;
import com.feelhub.domain.user.activation.*;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.util.UUID;

import static org.fest.assertions.Assertions.*;

public class ConfirmActivationCommandTest {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void canConfirm() {
        final User user = TestFactories.users().createFakeUser("test@test.com");
        final Activation activation = new Activation(user);
        Repositories.activation().add(activation);

        new ConfirmActivationCommand(activation.getId()).execute();

        assertThat(user.isActive());
        assertThat(Repositories.activation().getAll()).isEmpty();
    }

    @Test
    public void canThrowOnUnknownActivation() {
        exception.expect(ActivationNotFoundException.class);

        new ConfirmActivationCommand(UUID.randomUUID()).execute();
    }
}
