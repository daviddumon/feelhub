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

public class SitemapIndexStringConverter extends FreemarkerConverter {

    public SitemapIndexStringConverter(SitemapIndex sitemapIndex) {
        this.sitemapIndex = sitemapIndex;
    }

    @Override
    protected String template() {
        return "/templates/sitemap_index.ftl";
    }

    @Override
    protected Map<String, Object> data() {
        HashMap<String, Object> map = Maps.newHashMap();
        map.put("prefix", new SitemapProperties().getRoot());
        map.put("sitemaps", sitemapIndex.getSitemaps());
        return map;
    }

    private SitemapIndex sitemapIndex;
}
