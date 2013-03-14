package com.feelhub.sitemap.converter;

import com.feelhub.sitemap.domain.Sitemap;
import com.feelhub.sitemap.tools.SitemapProperties;
import com.google.common.collect.Maps;

import java.util.Map;

public class SitemapToStringConverter extends ToStringConverter {

    public SitemapToStringConverter(final Sitemap sitemap) {
        this.sitemap = sitemap;
    }

    @Override
    protected String template() {
        return "/templates/sitemap.ftl";
    }

    @Override
    protected Map<String, Object> data() {
        final Map<String, Object> map = Maps.newHashMap();
        map.put("prefix", new SitemapProperties().getRoot());
        map.put("entries", sitemap.getEntries());
        return map;
    }

    private final Sitemap sitemap;
}
