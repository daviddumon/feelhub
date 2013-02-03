package com.feelhub.application.mail;

import com.feelhub.application.mail.factory.ResourceUtils;
import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.Map;

import static org.fest.assertions.Assertions.assertThat;

public class TestsMailTemplate {

    @Test
    public void canGetFromText() {
        Map<String, Object> data = Maps.newHashMap();
        data.put("withData", "toto");

        String result = new MailTemplate(ResourceUtils.resource("template-test.ftl"), MailTemplate.Format.TEXT).apply(data);

        assertThat(result).startsWith("a simple template toto");
        assertThat(result).contains("Best regards,");
    }

    @Test
    public void canGetFromHtml() {
        Map<String, Object> data = Maps.newHashMap();
        data.put("withData", "toto");

        String result = new MailTemplate(ResourceUtils.resource("template-test.ftl"), MailTemplate.Format.HTML).apply(data);

        assertThat(result).contains("a simple template toto");
        assertThat(result).contains("<p>Best regards,</p>");
    }
}
