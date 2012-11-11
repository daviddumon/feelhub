package com.feelhub.domain.user;

import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import org.junit.Rule;
import org.junit.Test;

import static org.fest.assertions.Assertions.*;

public class TestsActivation {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Test
    public void canCreateActivation() {
        final Activation activation = new Activation(new User("idUser"));

        assertThat(activation.getId()).isNotNull();
        assertThat(activation.getUserId()).isEqualTo("idUser");
    }

    @Test
    public void canConfirm() {
        final User user = new User("idUser");
        Repositories.users().add(user);
        final Activation activation = new Activation(user);

        activation.confirm();

        assertThat(user.isActive()).isTrue();
    }
}
