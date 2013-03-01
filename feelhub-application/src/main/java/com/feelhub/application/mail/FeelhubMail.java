package com.feelhub.application.mail;

public class FeelhubMail {

    public static String DEFAULT_FROM = "register@feelhub.com";

    public FeelhubMail(final String to, final String subject, final String textContent, final String htmlContent) {
        this.to = to;
        this.subject = subject;
        this.textContent = textContent;
        this.htmlContent = htmlContent;
    }

    public String to() {
        return to;
    }

    public String subject() {
        return subject;
    }

    public String from() {
        final String from = "register@feelhub.com";
        return from;
    }

    public String htmlContent() {
        return htmlContent;
    }

    public String textContent() {
        return textContent;
    }

    private final String to;
    private final String subject;
    private final String textContent;
    private final String htmlContent;

}
