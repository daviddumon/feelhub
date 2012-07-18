package com.steambeat.web.mail;

import javax.mail.*;

public class MailSender {

    public void send(final Message message) {
        try {
            Transport.send(message);
        } catch (MessagingException e) {
            throw new EmailException();
        }
    }
}
