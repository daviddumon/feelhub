package com.feelhub.application.mail;

import org.junit.Test;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

import static org.fest.assertions.Assertions.*;

public class TestsFeelhubMailToMimeMessage {

    @Test
    public void canConvert() throws MessagingException, IOException {
        final Session session = null;
        final FeelhubMail mail = new FeelhubMail("to@example.fr", "the subject", "the content", "the html content");

        final MimeMessage mimeMessage = new FeelhubMailToMimeMessage(session).toMimeMessage(mail);

        assertThat(mimeMessage).isNotNull();
        assertThat(mimeMessage.getRecipients(Message.RecipientType.TO)).hasSize(1);
        assertThat(mimeMessage.getRecipients(Message.RecipientType.TO)[0].toString()).isEqualTo("to@example.fr");
        assertThat(mimeMessage.getFrom()).hasSize(1);
        assertThat(mimeMessage.getFrom()[0].toString()).isEqualTo("register@feelhub.com");
        assertThat(mimeMessage.getSubject()).isEqualTo("the subject");
        assertThat(mimeMessage.getContent()).isInstanceOf(Multipart.class);
        final Multipart part = (Multipart) mimeMessage.getContent();
        assertThat(part.getCount()).isEqualTo(2);
        final BodyPart textBodyPart = part.getBodyPart(0);
        assertThat(textBodyPart.getContent()).isEqualTo("the content");
        final BodyPart htmlBodyPart = part.getBodyPart(1);
        assertThat(htmlBodyPart.getContent()).isEqualTo("the html content");
    }

}
