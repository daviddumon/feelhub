package com.feelhub.web.mail;

import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.user.UserConfirmationMailEvent;
import com.feelhub.web.mail.mandrill.MandrillMailSender;
import com.feelhub.web.mail.mandrill.MandrillMessage;
import com.feelhub.web.mail.mandrill.MandrillRecipient;
import com.feelhub.web.mail.mandrill.MandrillTemplateRequest;
import com.feelhub.web.tools.FeelhubWebProperties;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import org.apache.log4j.Logger;

public class MailWatcher {

    @Inject
    public MailWatcher(final MandrillMailSender mailSender, FeelhubWebProperties properties) {
        this.mailSender = mailSender;
        this.properties = properties;
        DomainEventBus.INSTANCE.register(this);
        Logger.getLogger(MailWatcher.class).info("Waiting for mail to send");
    }

    @Subscribe
    public void onUserConfirmation(UserConfirmationMailEvent event) {
        final MandrillTemplateRequest request = new MandrillTemplateRequest();
        request.template_name = "Activation";
        request.message = buildConfirmationMessage(event);
        mailSender.send(request);
    }

    private MandrillMessage buildConfirmationMessage(final UserConfirmationMailEvent event) {
        final MandrillMessage message = new MandrillMessage();
        message.subject = "Welcome to Feelhub !";
        final MandrillRecipient recipient = new MandrillRecipient();
        recipient.email = event.getUser().getEmail();
        recipient.name = event.getUser().getFullname();
        message.to.add(recipient);
        message.addMergeVar("ACTIVATION", String.format("%s/activation/%s", properties.domain, event.getActivation().getId()));
        message.addTag("activation");
        return message;
    }

    private MandrillMailSender mailSender;
    private FeelhubWebProperties properties;
}
