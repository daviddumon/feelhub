package com.feelhub.application.mail;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class TestsFeelhubMail {

    @Test
    public void canCreate() {
        FeelhubMail mail = new FeelhubMail("to", "title", "content");

        assertThat(mail.to()).isEqualTo("to");
        assertThat(mail.subject()).isEqualTo("title");
        assertThat(mail.content()).isEqualTo("content");
    }

    @Test
    public void defaultFromIsRegister() {
        FeelhubMail mail = new FeelhubMail("to", "title", "content");

        assertThat(mail.from()).isEqualTo("register@feelhub.com");
    }

}
