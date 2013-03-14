package com.feelhub.sitemap.converter;

import com.feelhub.sitemap.domain.SitemapIndex;
import com.feelhub.sitemap.tools.SitemapProperties;
import com.google.common.collect.Maps;

import java.util.Map;

public class SitemapIndexToStringConverter extends ToStringConverter {

    public SitemapIndexToStringConverter(final SitemapIndex sitemapIndex) {
        this.sitemapIndex = sitemapIndex;
    }

    @Override
    protected String template() {
        return "/templates/sitemap_index.ftl";
    }

    @Override
    protected Map<String, Object> data() {
        final Map<String, Object> map = Maps.newHashMap();
        map.put("prefix", new SitemapProperties().getRoot());
        map.put("sitemaps", sitemapIndex.getSitemaps());
        return map;
    }

    private final SitemapIndex sitemapIndex;
}
