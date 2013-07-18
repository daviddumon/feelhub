package com.feelhub.web.resources.authentification;

import com.feelhub.application.command.CommandBus;
import com.feelhub.application.command.user.CreateUserCommand;
import com.feelhub.domain.session.EmailAlreadyUsed;
import com.feelhub.domain.user.BadEmail;
import com.google.common.base.Throwables;
import com.google.common.util.concurrent.*;
import com.google.inject.Inject;
import org.restlet.data.*;
import org.restlet.resource.*;

import java.util.UUID;

public class SignupResource extends ServerResource {

    @Inject
    public SignupResource(final CommandBus bus) {
        this.bus = bus;
    }

    @Post
    public void signup(final Form form) {
        if (!checkForm(form)) {
            setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
            return;
        }
        final ListenableFuture<UUID> result = bus.execute(createCommand(form));
        try {
            Futures.getUnchecked(result);
            setStatus(Status.SUCCESS_CREATED);
        } catch (Exception e) {
            if (EmailAlreadyUsed.class.isAssignableFrom(Throwables.getRootCause(e).getClass())) {
                setStatus(Status.CLIENT_ERROR_CONFLICT);
                return;
            }
            if (BadEmail.class.isAssignableFrom(Throwables.getRootCause(e).getClass())) {
                setStatus(Status.CLIENT_ERROR_PRECONDITION_FAILED);
                return;
            }
            Throwables.propagate(e);
        }
    }

    private CreateUserCommand createCommand(final Form form) {
        final String email = form.getFirstValue("email");
        final String password = form.getFirstValue("password");
        final String fullname = form.getFirstValue("fullname");
        final String language = form.getFirstValue("language");
        return new CreateUserCommand(email, password, fullname, language);
    }

    private boolean checkForm(final Form form) {
        return form.getQueryString().contains("email")
                && form.getQueryString().contains("password")
                && form.getQueryString().contains("fullname")
                && form.getQueryString().contains("language");
    }

    private final CommandBus bus;
}
