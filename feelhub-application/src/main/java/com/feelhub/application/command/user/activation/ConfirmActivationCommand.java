package com.feelhub.application.command.user.activation;

import com.feelhub.application.command.Command;
import com.feelhub.domain.user.activation.Activation;
import com.feelhub.domain.user.activation.ActivationNotFoundException;
import com.feelhub.repositories.Repositories;

import java.util.UUID;

public class ConfirmActivationCommand implements Command<Object> {

    public ConfirmActivationCommand(final UUID activationId) {
        this.activationId = activationId;
    }

    @Override
    public Object execute() {
        final Activation activation = Repositories.activation().get(activationId);
        if (activation == null) {
            throw new ActivationNotFoundException();
        }
        activation.confirm();
        Repositories.activation().delete(activation);
        return true;
    }

    public final UUID activationId;
}
