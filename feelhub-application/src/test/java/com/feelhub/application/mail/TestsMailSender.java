package com.feelhub.application.mail;


import com.feelhub.domain.admin.Api;
import com.feelhub.domain.admin.ApiCallEvent;
import com.feelhub.domain.eventbus.WithDomainEvent;
import org.junit.Rule;
import org.junit.Test;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import static org.fest.assertions.Assertions.assertThat;

public class TestsMailSender {

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Test
    public void incrementStatistic() {
        bus.capture(ApiCallEvent.class);
        MailSender mailSender = mailSender();

        mailSender.send(new FeelhubMail("charles@arpinum.fr", "subject", "content","content"));

        ApiCallEvent event = bus.lastEvent(ApiCallEvent.class);
        assertThat(event).isNotNull();
        assertThat(event.getApi()).isEqualTo(Api.SendGrid);
        assertThat(event.getIncrement()).isEqualTo(1);
    }

    private MailSender mailSender() {
        return new MailSender() {
            @Override
            protected void sendMimeMessage(MimeMessage mimeMessage) throws MessagingException {

            }
        };
    }
}
