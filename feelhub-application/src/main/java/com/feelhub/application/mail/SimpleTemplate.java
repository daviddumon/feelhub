package com.feelhub.application.mail;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

public class SimpleTemplate {

    public SimpleTemplate(final String template) {
        this.template = template;
    }

    public String apply(final Map<String, Object> data) {
        final Writer writer = new StringWriter();
        try {
            final Template temp = new Template("name", new StringReader(template), configuration());
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

    private final String template;
}
