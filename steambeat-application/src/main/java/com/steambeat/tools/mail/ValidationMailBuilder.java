package com.steambeat.tools.mail;


import com.google.inject.Inject;
import com.steambeat.domain.user.User;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class ValidationMailBuilder {

    @Inject
    public ValidationMailBuilder(final MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public MimeMessage sendValidationTo(final User user) {
        try {
            final Session mailSession = getMailSession();
            final MimeMessage mimeMessage = getValidationMessage(mailSession, user);
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

    private MimeMessage getValidationMessage(final Session mailSession, final User user) {
        final MimeMessage mimeMessage = new MimeMessage(mailSession);
        try {
            mimeMessage.setFrom(new InternetAddress("register@steambeat.com"));
            mimeMessage.setSubject("Welcome to Steambeat !");
            mimeMessage.setText("Thank you for registering to Steambeat");
            mimeMessage.setRecipients(Message.RecipientType.TO,InternetAddress.parse(user.getEmail()));
        } catch (MessagingException e) {
            throw new EmailException();
        }
        return mimeMessage;
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

    private MailSender mailSender;
}
