package com.steambeat.web.mail;

import com.google.common.eventbus.*;
import com.google.inject.Inject;
import com.steambeat.domain.eventbus.DomainEventBus;
import com.steambeat.domain.user.*;
import com.steambeat.web.ReferenceBuilder;
import com.steambeat.web.representation.SteambeatTemplateRepresentation;
import org.restlet.Context;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.util.Properties;

public class MailBuilder {

    @Inject
    public MailBuilder(final MailSender mailSender) {
        this.mailSender = mailSender;
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    @AllowConcurrentEvents
    public void handle(final UserCreatedEvent event) {
        sendValidationTo(event.getUser());
    }

    public MimeMessage sendValidationTo(final User user) {
        this.user = user;
        try {
            final Session mailSession = getMailSession();
            final MimeMessage mimeMessage = getValidationMessage(mailSession);
            mailSender.send(mimeMessage);
            return mimeMessage;
        } catch (Exception e) {
            throw new EmailException();
        }
    }

    private Session getMailSession() {
        final Properties mailProperties = getProperties();
        return Session.getDefaultInstance(mailProperties, new CustomAuthenticator());
    }

    private MimeMessage getValidationMessage(final Session mailSession) {
        final MimeMessage mimeMessage = new MimeMessage(mailSession);
        try {
            mimeMessage.setFrom(new InternetAddress("register@steambeat.com"));
            mimeMessage.setSubject("Welcome to Steambeat !");
            setContent(mimeMessage);
            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getEmail()));
        } catch (MessagingException e) {
            throw new EmailException();
        }
        return mimeMessage;
    }

    private void setContent(final MimeMessage mimeMessage) {
        try {
            final SteambeatTemplateRepresentation content = SteambeatTemplateRepresentation.createNew("mail/welcome.ftl", context)
                    .with("name", user.getFullname())
                    .with("activation_link", new ReferenceBuilder(context).buildUri("/activation/" + user.getSecret()));
            mimeMessage.setText(content.getText());
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new EmailException();
        } catch (IOException e) {
            e.printStackTrace();
            throw new EmailException();
        }
    }

    private Properties getProperties() {
        final Properties mailProperties = new Properties();
        mailProperties.put("mail.smtp.host", "mail.gandi.net");
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
    private User user;
    private Context context;
}
