package com.steambeat.sitemap.application;

import com.google.common.collect.Lists;
import com.steambeat.sitemap.domain.*;

import java.util.List;

public class SitemapBuilder {

    public SitemapBuilder() {
        currentSitemap = 1;
        final int currentSitemapIndex = 1;
        sitemapIndexes.add(new SitemapIndex(currentSitemapIndex));
        getCurrentSitemapIndex().add(new Sitemap(currentSitemap));
    }

    public void build(final List<SitemapEntry> sitemapEntries) {
        for (final SitemapEntry sitemapEntry : sitemapEntries) {
            try {
                getCurrentSitemapIndex().getLastSitemap().addEntry(sitemapEntry);
            } catch (CapacityException e) {
                changeCurrentSitemap();
                getCurrentSitemapIndex().getLastSitemap().addEntry(sitemapEntry);
            }
        }
    }

    private void changeCurrentSitemap() {
        getCurrentSitemapIndex().add(new Sitemap(++currentSitemap));
    }

    public SitemapIndex getCurrentSitemapIndex() {
        return sitemapIndexes.get(sitemapIndexes.size() - 1);
    }

    private int currentSitemap;
    private final List<SitemapIndex> sitemapIndexes = Lists.newArrayList();
}
