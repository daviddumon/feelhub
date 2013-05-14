package com.feelhub.web.resources.authentification;

import com.feelhub.application.command.CommandBus;
import com.feelhub.application.command.user.activation.ConfirmActivationCommand;
import com.feelhub.domain.feeling.SentimentValue;
import com.feelhub.web.WebReferenceBuilder;
import com.feelhub.web.dto.FeelhubMessage;
import com.feelhub.web.representation.ModelAndView;
import com.feelhub.web.resources.HomeResource;
import com.feelhub.web.resources.api.ApiFeelingSearch;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.*;
import com.google.inject.Inject;
import org.restlet.data.Status;
import org.restlet.resource.Get;

import java.util.UUID;

public class ActivationResource extends HomeResource {

    @Inject
    public ActivationResource(final CommandBus bus, final ApiFeelingSearch apiFeelingSearch) {
        super(apiFeelingSearch);
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
                setLocationRef(new WebReferenceBuilder(getContext()).buildUri("/error"));
                setStatus(Status.REDIRECTION_PERMANENT);
            }
        });
        final ModelAndView modelAndView = super.represent();
        return modelAndView.with("messages", Lists.newArrayList(getActivationMessage()));
    }

    private FeelhubMessage getActivationMessage() {
        final FeelhubMessage feelhubMessage = new FeelhubMessage();
        feelhubMessage.setFeeling(SentimentValue.good.toString());
        feelhubMessage.setText("Your account has been activated! Enjoy Feelhub!");
        feelhubMessage.setSecondTimer(3);
        return feelhubMessage;
    }

    private final CommandBus bus;
}
