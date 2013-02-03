package com.feelhub.application.mail;

public class FeelhubMail {

    public static String DEFAULT_FROM = "register@feelhub.com";

    public FeelhubMail(String to, String subject, String textContent, String htmlContent) {
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
        return from;
    }

    public String htmlContent() {
        return htmlContent;
    }

    public String textContent() {
        return textContent;
    }

    private String to;
    private String subject;
    private final String textContent;
    private final String htmlContent;
    private String from = "register@feelhub.com";

}
