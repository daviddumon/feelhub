package com.feelhub.application.mail;

import org.junit.Test;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

import static org.fest.assertions.Assertions.assertThat;

public class TestsFeelhubMailToMimeMessage {

    @Test
    public void canConvert() throws MessagingException, IOException {
        Session session = null;
        FeelhubMail mail = new FeelhubMail("to@example.fr", "the subject", "the content");

        MimeMessage mimeMessage = new FeelhubMailToMimeMessage(session).toMimeMessage(mail);

        assertThat(mimeMessage).isNotNull();
        assertThat(mimeMessage.getRecipients(Message.RecipientType.TO)).hasSize(1);
        assertThat(mimeMessage.getRecipients(Message.RecipientType.TO)[0].toString()).isEqualTo("to@example.fr");
        assertThat(mimeMessage.getFrom()).hasSize(1);
        assertThat(mimeMessage.getFrom()[0].toString()).isEqualTo("register@feelhub.com");
        assertThat(mimeMessage.getSubject()).isEqualTo("the subject");
        assertThat(mimeMessage.getContent()).isEqualTo("the content");
    }


}
