package com.feelhub.application.mail;

public class FeelhubMail {

    public static String DEFAULT_FROM = "register@feelhub.com";

    public FeelhubMail(String to, String subject, String content) {
        this.to = to;
        this.subject = subject;
        this.content = content;
    }

    public String to() {
        return to;
    }

    public String subject() {
        return subject;
    }

    public String content() {
        return content;
    }

    public String from() {
        return from;
    }

    private String to;
    private String subject;
    private String content;
    private String from = "register@feelhub.com";

}
