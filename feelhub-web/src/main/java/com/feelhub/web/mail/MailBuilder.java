package com.feelhub.web.mail;

import com.feelhub.application.mail.*;
import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.user.*;
import com.feelhub.web.WebReferenceBuilder;
import com.feelhub.application.mail.factory.MailFactory;
import com.google.common.eventbus.*;
import com.google.inject.Inject;
import org.restlet.Context;

public class MailBuilder {

    @Inject
    public MailBuilder(final MailSender mailSender) {
        this.mailSender = mailSender;
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    public void onActivationCreated(ActivationCreatedEvent event) {
        mailSender.send(MailFactory.validation(event.getUser(), new WebReferenceBuilder(context).buildUri("/activation/" + event.getActivation().getId())));
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

    private MailSender mailSender;
    private Context context;
}