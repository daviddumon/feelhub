package com.feelhub.web.mail;

import com.feelhub.application.mail.MailSender;

import javax.mail.Message;

public class FakeMailSender extends MailSender {

    @Override
    public void send(final Message message) {

    }
}