package com.steambeat.sitemap.domain;

import com.google.common.collect.Lists;

import java.util.List;

public enum RobotsFile {

    INSTANCE;

    public SitemapIndex newSitemapIndex() {
        final SitemapIndex sitemapIndex = new SitemapIndex(sitemapIndexes.size() + 1);
        sitemapIndexes.add(sitemapIndex);
        return sitemapIndex;
    }

    public List<SitemapIndex> getSitemapIndexes() {
        return sitemapIndexes;
    }

    public void clear() {
        sitemapIndexes = Lists.newArrayList();
    }

    public SitemapIndex getSitemapIndex(final int index) {
        if (index <= sitemapIndexes.size()) {
            return sitemapIndexes.get(index - 1);
        } else {
            return null;
        }
    }

    private List<SitemapIndex> sitemapIndexes = Lists.newArrayList();
}
