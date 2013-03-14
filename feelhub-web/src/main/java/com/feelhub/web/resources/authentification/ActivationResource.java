package com.feelhub.web.resources.authentification;

import com.feelhub.application.command.CommandBus;
import com.feelhub.application.command.user.activation.ConfirmActivationCommand;
import com.feelhub.web.WebReferenceBuilder;
import com.feelhub.web.representation.ModelAndView;
import com.google.common.util.concurrent.*;
import com.google.inject.Inject;
import org.restlet.data.Status;
import org.restlet.resource.*;

import java.util.UUID;

public class ActivationResource extends ServerResource {

    @Inject
    public ActivationResource(final CommandBus bus) {
        this.bus = bus;
    }

    @Get
    public ModelAndView confirm() {
        final UUID secret = UUID.fromString(getRequestAttributes().get("secret").toString());
        Futures.addCallback(bus.execute(new ConfirmActivationCommand(secret)), new FutureCallback<Object>() {
            @Override
            public void onSuccess(final Object result) {
            }

            @Override
            public void onFailure(final Throwable t) {
                setLocationRef(new WebReferenceBuilder(getContext()).buildUri("/"));
                setStatus(Status.REDIRECTION_PERMANENT);
            }
        });
        return ModelAndView.createNew("activation.ftl");
    }

    private final CommandBus bus;
}
