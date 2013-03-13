package com.feelhub.sitemap.converter;

import com.feelhub.sitemap.domain.SitemapIndex;
import com.feelhub.sitemap.tools.SitemapProperties;
import com.google.common.collect.Maps;

import java.util.*;

public class RobotsTxtToStringConverter extends ToStringConverter {

    public RobotsTxtToStringConverter(List<SitemapIndex> indexes) {
        this.indexes = indexes;
    }

    @Override
    protected Map<String, Object> data() {
        Map<String, Object> map = Maps.newHashMap();
        map.put("prefix", new SitemapProperties().getRoot());
        map.put("indexes", indexes);
        return map;
    }

    @Override
    protected String template() {
        return "/templates/robots.ftl";
    }

    private List<SitemapIndex> indexes;
}