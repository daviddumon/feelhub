package com.feelhub.web.mail;

import com.feelhub.application.mail.*;
import com.google.common.collect.Lists;

import java.util.List;

public class FakeMailSender extends MailSender {

    @Override
    public void send(final FeelhubMail message) {
        messages.add(message);
    }

    public List<FeelhubMail> messages() {
        return messages;
    }

    private List<FeelhubMail> messages = Lists.newArrayList();
}