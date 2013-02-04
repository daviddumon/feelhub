package com.feelhub.application.mail;

import javax.mail.*;
import javax.mail.internet.*;

public class FeelhubMailToMimeMessage {

    public FeelhubMailToMimeMessage(Session mailSession) {
        this.mailSession = mailSession;
    }

    public MimeMessage toMimeMessage(FeelhubMail mail) {
        final MimeMessage mimeMessage = new MimeMessage(mailSession);
        try {
            mimeMessage.setFrom(new InternetAddress(mail.from()));
            mimeMessage.setSubject(mail.subject());
            mimeMessage.setContent(multipart(mail.textContent(), mail.htmlContent()));
            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail.to()));
        } catch (MessagingException e) {
            throw new EmailException(e);
        }
        return mimeMessage;
    }

    private MimeMultipart multipart(final String plainText, final String html) throws MessagingException {
        final MimeMultipart multipart = new MimeMultipart("alternative");
        multipart.addBodyPart(textPart(plainText));
        multipart.addBodyPart(htmlPart(html));
        return multipart;
    }

    private BodyPart textPart(final String contenu) throws MessagingException {
        final BodyPart bodyPart = new MimeBodyPart();
        bodyPart.setContent(contenu, "text/plain; charset=UTF-8");
        return bodyPart;
    }

    private BodyPart htmlPart(final String contenu) throws MessagingException {
        final BodyPart bodyPart = new MimeBodyPart();
        bodyPart.setContent(contenu, "text/html; charset=UTF-8");
        return bodyPart;
    }

    private Session mailSession;
}
