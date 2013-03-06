package com.feelhub.sitemap.converter;

import com.feelhub.sitemap.domain.SitemapIndex;
import com.feelhub.sitemap.tools.SitemapProperties;
import com.google.common.collect.Maps;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.log4j.Logger;

import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class SitemapIndexStringConverter {
    public String toString(SitemapIndex sitemapIndex) {
        HashMap<String, Object> map = Maps.newHashMap();
        map.put("prefix", new SitemapProperties().getRoot());
        map.put("sitemaps", sitemapIndex.getSitemaps());
        return process(map);
    }

    public String process(final Map<String, Object> data) {
        final Writer writer = new StringWriter();
        try {
            final Template temp = new Template("name", new InputStreamReader(getClass().getResourceAsStream("/templates/sitemap_index.ftl")), configuration());
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

    private static final Logger logger = Logger.getLogger(RobotsTxtStringConverter.class);
}
