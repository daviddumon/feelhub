package com.feelhub.web.mail;

import com.feelhub.application.mail.MailSender;
import com.feelhub.application.mail.factory.MailFactory;
import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.user.*;
import com.feelhub.domain.user.activation.ActivationCreatedEvent;
import com.feelhub.repositories.Repositories;
import com.feelhub.web.WebReferenceBuilder;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import org.restlet.Context;

public class MailBuilder {

    @Inject
    public MailBuilder(final MailSender mailSender) {
        this.mailSender = mailSender;
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    public void onActivationCreated(final ActivationCreatedEvent event) {
        User user = Repositories.users().get(event.userId);
        mailSender.send(MailFactory.validation(user, new WebReferenceBuilder(context).buildUri("/activation/" + event.activationId)));
    }

    @Subscribe
    public void onUserCreated(final UserCreatedEvent event) {
        if (event.getUser().isActive()) {
            mailSender.send(MailFactory.welcome(event.getUser()));
        }
    }

    public void setContext(final Context context) {
        this.context = context;
    }

    private final MailSender mailSender;
    private Context context;
}