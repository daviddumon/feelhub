package com.feelhub.application.mail;

import com.feelhub.domain.admin.ApiCallEvent;
import com.feelhub.domain.eventbus.DomainEventBus;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailSender {

    public void send(final FeelhubMail message) {
        try {
            sendMimeMessage(new FeelhubMailToMimeMessage(getMailSession()).toMimeMessage(message));
            DomainEventBus.INSTANCE.post(ApiCallEvent.sendGrid());
        } catch (MessagingException e) {
            throw new EmailException(e);
        }
    }

    protected void sendMimeMessage(MimeMessage mimeMessage) throws MessagingException {
        Transport.send(mimeMessage);
    }

    private Session getMailSession() {
        return Session.getDefaultInstance(properties(), new CustomAuthenticator());
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
}
