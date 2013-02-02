package com.feelhub.web.mail;

import com.feelhub.application.mail.*;
import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.user.*;
import com.feelhub.web.WebReferenceBuilder;
import com.feelhub.application.mail.factory.MailFactory;
import com.google.common.eventbus.*;
import com.google.inject.Inject;
import org.restlet.Context;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class MailBuilder {

    @Inject
    public MailBuilder(final MailSender mailSender) {
        this.mailSender = mailSender;
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    public void onActivationCreated(ActivationCreatedEvent event) {
        sendValidationTo(event.getUser(), event.getActivation());
    }

    @Subscribe
    public void onUserCreated(final UserCreatedEvent event) {
        if (event.getUser().isActive()) {
           sendWelcomeMessageTo(event.getUser());
        }
    }

    public MimeMessage sendWelcomeMessageTo(User user) {
        return sendMail(MailFactory.welcome(user, context));
    }

    public MimeMessage sendValidationTo(final User user, final Activation activation) {
        return sendMail(MailFactory.validation(user, new WebReferenceBuilder(context).buildUri("/activation/" + activation.getId())));
    }

    private MimeMessage sendMail(FeelhubMail mail) {
        try {
            final MimeMessage mimeMessage = new FeelhubMailToMimeMessage(getMailSession()).toMimeMessage(mail);
            mailSender.send(mimeMessage);
            return mimeMessage;
        } catch (Exception e) {
            throw new EmailException(e);
        }
    }

    private Session getMailSession() {
        final Properties mailProperties = properties();
        return Session.getDefaultInstance(mailProperties, new CustomAuthenticator());
    }

    private Properties properties() {
        final Properties mailProperties = new Properties();
        mailProperties.put("mail.smtp.host", "smtp.sendgrid.net");
        mailProperties.put("mail.smtp.port", "465");
        mailProperties.put("mail.smtp.auth", "true");
        mailProperties.put("mail.smtp.socketFactory.port", "465");
        mailProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        return mailProperties;
    }

    public void setContext(final Context context) {
        this.context = context;
    }

    private MailSender mailSender;
    private Context context;
}