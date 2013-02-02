package com.feelhub.application.mail;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class FeelhubMailToMimeMessage {

    public FeelhubMailToMimeMessage(Session mailSession) {
        this.mailSession = mailSession;
    }

    public MimeMessage toMimeMessage(FeelhubMail mail) {
        final MimeMessage mimeMessage = new MimeMessage(mailSession);
        try {
            mimeMessage.setFrom(new InternetAddress(mail.from()));
            mimeMessage.setSubject(mail.subject());
            mimeMessage.setText(mail.content());
            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail.to()));
        } catch (MessagingException e) {
            throw new EmailException(e);
        }
        return mimeMessage;
    }

    private Session mailSession;
}
