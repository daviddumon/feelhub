package com.feelhub.sitemap.domain;

import com.google.common.collect.Lists;

import java.util.List;

public class SitemapIndexBuilder {

    public List<SitemapIndex> build(List<SitemapEntry> entries) {
        entries.add(0, new SitemapEntry("", Frequency.hourly, 0.8));
        return sitemapIndexes(sitemaps(entries));
    }

    private List<SitemapIndex> sitemapIndexes(List<Sitemap> sitemaps) {
        List<List<Sitemap>> partitionnedSitemaps = Lists.partition(sitemaps, SitemapPreferences.sitemapIndexCapacity());
        List<SitemapIndex> sitemapIndexes = Lists.newArrayList();
        int i = 0;
        for (List<Sitemap> partition : partitionnedSitemaps) {
            SitemapIndex sitemapIndex = new SitemapIndex(partition);
            sitemapIndex.setIndex(i++);
            sitemapIndexes.add(sitemapIndex);
        }
        return sitemapIndexes;
    }

    private List<Sitemap> sitemaps(List<SitemapEntry> entries) {
        List<List<SitemapEntry>> partitionnedEntries = Lists.partition(entries, SitemapPreferences.sitemapCapacity());
        List<Sitemap> sitemaps = Lists.newArrayList();
        int i = 0;
        for (List<SitemapEntry> partition : partitionnedEntries) {
            Sitemap sitemap = new Sitemap(partition);
            sitemap.setIndex(i++);
            sitemaps.add(sitemap);
        }
        return sitemaps;
    }

}
