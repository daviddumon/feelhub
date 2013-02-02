package com.feelhub.application.mail;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import java.util.Properties;

public class MailSender {

    public void send(final FeelhubMail message) {
        try {
            Transport.send(new FeelhubMailToMimeMessage(getMailSession()).toMimeMessage(message));
        } catch (MessagingException e) {
            throw new EmailException(e);
        }
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
