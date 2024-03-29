package com.feelhub.sitemap.converter;

import freemarker.template.*;
import org.slf4j.*;

import java.io.*;
import java.util.Map;

public abstract class ToStringConverter {

    public String toString() {
        return process(data());
    }

    private String process(final Map<String, Object> data) {
        final Writer writer = new StringWriter();
        try {
            final Template temp = new Template("name", new InputStreamReader(getClass().getResourceAsStream(template())), configuration());
            temp.process(data, writer);
            writer.flush();
        } catch (Exception e) {
            logger.error("Freemarker exception", e);
        }
        return writer.toString();
    }

    private Configuration configuration() {
        final Configuration configuration = new Configuration();
        configuration.setTagSyntax(Configuration.AUTO_DETECT_TAG_SYNTAX);
        return configuration;
    }

    protected abstract String template();

    protected abstract Map<String, Object> data();

    private static final Logger logger = LoggerFactory.getLogger(ToStringConverter.class);

}
