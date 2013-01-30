package com.feelhub.web.mail;

import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.user.*;
import com.feelhub.web.WebReferenceBuilder;
import com.feelhub.web.representation.FeelhubTemplateRepresentation;
import com.google.common.eventbus.*;
import com.google.inject.Inject;
import org.restlet.Context;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.util.Properties;

public class MailBuilder {

    @Inject
    public MailBuilder(final MailSender mailSender) {
        this.mailSender = mailSender;
        //DomainEventBus.INSTANCE.register(this);
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
        try {
            final Session mailSession = getMailSession();
            final MimeMessage mimeMessage = getWelcomeMessage(mailSession, user);
            mailSender.send(mimeMessage);
            return mimeMessage;
        } catch (Exception e) {
            throw new EmailException();
        }
    }

    public MimeMessage sendValidationTo(final User user, final Activation activation) {
        try {
            final Session mailSession = getMailSession();
            final MimeMessage mimeMessage = getValidationMessage(mailSession, user, activation);
            mailSender.send(mimeMessage);
            return mimeMessage;
        } catch (Exception e) {
            throw new EmailException();
        }
    }

    private Session getMailSession() {
        final Properties mailProperties = properties();
        return Session.getDefaultInstance(mailProperties, new CustomAuthenticator());
    }

    private MimeMessage getValidationMessage(final Session mailSession, final User user, final Activation activation) {
        final MimeMessage mimeMessage = new MimeMessage(mailSession);
        try {
            mimeMessage.setFrom(new InternetAddress("register@feelhub.com"));
            mimeMessage.setSubject("Welcome to Feelhub !");
            setContent(mimeMessage, user, activation);
            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getEmail()));
        } catch (MessagingException e) {
            throw new EmailException();
        }
        return mimeMessage;
    }

    private MimeMessage getWelcomeMessage(final Session mailSession, final User user) {
        final MimeMessage mimeMessage = new MimeMessage(mailSession);
        try {
            mimeMessage.setFrom(new InternetAddress("register@feelhub.com"));
            mimeMessage.setSubject("Welcome to Feelhub !");
            setContent(mimeMessage);
            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getEmail()));
        } catch (MessagingException e) {
            throw new EmailException();
        }
        return mimeMessage;
    }

    private void setContent(MimeMessage mimeMessage) {
        try {
            final FeelhubTemplateRepresentation content = FeelhubTemplateRepresentation.createNew("mail/welcome.ftl", context);
            mimeMessage.setText(content.getText());
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new EmailException();
        } catch (IOException e) {
            e.printStackTrace();
            throw new EmailException();
        }
    }

    private void setContent(final MimeMessage mimeMessage, final User user, final Activation activation) {
        try {
            final FeelhubTemplateRepresentation content = FeelhubTemplateRepresentation.createNew("mail/activation.ftl", context)
                    .with("name", user.getFullname())
                    .with("activation_link", new WebReferenceBuilder(context).buildUri("/activation/" + activation.getId()));
            mimeMessage.setText(content.getText());
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new EmailException();
        } catch (IOException e) {
            e.printStackTrace();
            throw new EmailException();
        }
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