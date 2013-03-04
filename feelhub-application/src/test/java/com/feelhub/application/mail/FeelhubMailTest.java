package com.feelhub.application.mail;

import org.junit.Test;

import static org.fest.assertions.Assertions.*;

public class FeelhubMailTest {

    @Test
    public void canCreate() {
        final FeelhubMail mail = new FeelhubMail("to", "title", "content", "contentHtml");

        assertThat(mail.to()).isEqualTo("to");
        assertThat(mail.subject()).isEqualTo("title");
        assertThat(mail.textContent()).isEqualTo("content");
        assertThat(mail.htmlContent()).isEqualTo("contentHtml");
    }

    @Test
    public void defaultFromIsRegister() {
        final FeelhubMail mail = new FeelhubMail("to", "title", "content", "contentHtml");

        assertThat(mail.from()).isEqualTo("register@feelhub.com");
    }

}
