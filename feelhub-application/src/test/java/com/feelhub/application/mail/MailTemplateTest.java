package com.feelhub.application.mail;

import com.feelhub.application.mail.factory.ResourceUtils;
import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.Map;

import static org.fest.assertions.Assertions.*;

public class MailTemplateTest {

    @Test
    public void canGetFromText() {
        final Map<String, Object> data = Maps.newHashMap();
        data.put("withData", "toto");

        final String result = new MailTemplate(ResourceUtils.resource("template-test.ftl"), MailTemplate.Format.TEXT).apply(data);

        assertThat(result).startsWith("a simple template toto");
        assertThat(result).contains("Best regards,");
    }

    @Test
    public void canGetFromHtml() {
        final Map<String, Object> data = Maps.newHashMap();
        data.put("withData", "toto");

        final String result = new MailTemplate(ResourceUtils.resource("template-test.ftl"), MailTemplate.Format.HTML).apply(data);

        assertThat(result).contains("a simple template toto");
        assertThat(result).contains("<p>Best regards,</p>");
    }
}
