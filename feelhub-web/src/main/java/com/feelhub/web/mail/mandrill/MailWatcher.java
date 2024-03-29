package com.feelhub.web.mail.mandrill;

import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.user.*;
import com.feelhub.domain.user.activation.ActivationCreatedEvent;
import com.feelhub.repositories.Repositories;
import com.feelhub.web.tools.FeelhubWebProperties;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import org.slf4j.LoggerFactory;

public class MailWatcher {

    @Inject
    public MailWatcher(final MandrillMailSender mailSender, final FeelhubWebProperties properties) {
        this.mailSender = mailSender;
        this.properties = properties;
        DomainEventBus.INSTANCE.register(this);
        LoggerFactory.getLogger(MailWatcher.class).info("Waiting for mail to send");
    }

    @Subscribe
    public void onActivationCreated(final ActivationCreatedEvent event) {
        final MandrillTemplateRequest request = createTemplateRequest(Repositories.users().get(event.userId), "activation", "Welcome to Feelhub !");
        request.message.addMergeVar("ACTIVATION", String.format("%s/activation/%s", properties.domain, event.activationId));
        mailSender.send(request);
    }

    @Subscribe
    public void onUserCreated(final UserCreatedEvent event) {
        if (event.user.isActive()) {
            mailSender.send(createTemplateRequest(event.user, "Welcome", "Welcome to Feelhub !"));
        }
    }

    private MandrillTemplateRequest createTemplateRequest(final User user, final String template, final String title) {
        final MandrillTemplateRequest request = new MandrillTemplateRequest();
        request.template_name = template;
        final MandrillMessage message = new MandrillMessage();
        message.subject = title;
        final MandrillRecipient recipient = new MandrillRecipient();
        recipient.email = user.getEmail();
        recipient.name = user.getFullname();
        message.to.add(recipient);
        message.addTag(template.toLowerCase());
        request.message = message;
        return request;
    }

    private final MandrillMailSender mailSender;
    private final FeelhubWebProperties properties;
}
