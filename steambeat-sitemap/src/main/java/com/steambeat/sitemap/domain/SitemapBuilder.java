package com.steambeat.sitemap.domain;

import com.google.common.collect.Lists;

import java.util.List;

public class SitemapBuilder {

    public SitemapBuilder() {
        currentSitemap = 1;
        currentSitemapIndex = 1;
        sitemapIndexes.add(new SitemapIndex(currentSitemapIndex));
        getCurrentSitemapIndex().add(new Sitemap(currentSitemap));
    }

    public void build(final List<SitemapEntry> sitemapEntries) {
        for (SitemapEntry sitemapEntry : sitemapEntries) {
            try {
                getCurrentSitemapIndex().getLastSitemap().addEntry(sitemapEntry);
            } catch (SitemapCapacityException e) {
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

    private int currentSitemapIndex;
    private int currentSitemap;
    private List<SitemapIndex> sitemapIndexes = Lists.newArrayList();
}
