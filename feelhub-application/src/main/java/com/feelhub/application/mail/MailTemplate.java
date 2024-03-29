package com.feelhub.application.mail;

import com.feelhub.application.mail.factory.ResourceUtils;
import com.google.common.collect.Maps;
import freemarker.template.*;

import java.io.*;
import java.util.*;

public class MailTemplate {

    public enum Format {
        HTML("mail/layout-html.ftl"), TEXT("mail/layout-text.ftl");

        Format(final String templatePath) {

            this.templatePath = templatePath;
        }

        public String template() {
            return ResourceUtils.resource(templatePath);
        }

        private String templatePath;
    }

    public MailTemplate(final String content, final Format format) {
        this.content = content;
        this.format = format;
    }

    public String apply(final Map<String, Object> data) {
        return apply(templatedMessage(), data);
    }

    private String templatedMessage() {
        final HashMap<String, Object> map = Maps.newHashMap();
        map.put("message", content);
        return apply(format.template(), map);
    }

    private String apply(final String template1, final Map<String, Object> data) {
        final Writer writer = new StringWriter();
        try {
            final Template temp = new Template("name", new StringReader(template1), configuration());
            temp.process(data, writer);
            writer.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return writer.toString();
    }

    private Configuration configuration() {
        final Configuration configuration = new Configuration();
        configuration.setTagSyntax(Configuration.AUTO_DETECT_TAG_SYNTAX);
        return configuration;
    }

    private final String content;
    private final Format format;
}
