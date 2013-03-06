package com.feelhub.sitemap.converter;

import com.feelhub.sitemap.domain.SitemapIndex;
import com.feelhub.sitemap.domain.SitemapIndexUri;
import com.feelhub.sitemap.tools.SitemapProperties;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.log4j.Logger;

import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RobotsTxtStringProvider {
    public String toString(List<SitemapIndex> indexes) {
        HashMap<String, Object> map = Maps.newHashMap();
        map.put("root", new SitemapProperties().getRoot());
        map.put("indexes", indexes(indexes));
        return process(map);
    }

    private List<String> indexes(List<SitemapIndex> indexes) {
        List<String> result = Lists.newArrayList();
        int i = 0;
        for (SitemapIndex index : indexes) {
            result.add(SitemapIndexUri.getUri(i++));
        }
        return result;
    }

    public String process(final Map<String, Object> data) {
        final Writer writer = new StringWriter();
        try {
            final Template temp = new Template("name", new InputStreamReader(getClass().getResourceAsStream("/templates/robots.ftl")), configuration());
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

    private static final Logger logger = Logger.getLogger(RobotsTxtStringProvider.class);
}
