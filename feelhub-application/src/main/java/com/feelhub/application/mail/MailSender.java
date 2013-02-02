package com.feelhub.application.mail;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;

public class MailSender {

    public void send(final Message message) {
        try {
            Transport.send(message);
        } catch (MessagingException e) {
            throw new EmailException(e);
        }
    }
}
