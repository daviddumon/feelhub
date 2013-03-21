package com.feelhub.sitemap.domain;

import com.google.common.collect.Lists;
import org.joda.time.DateTime;

import java.util.List;

public class SitemapIndexBuilder {

    public List<SitemapIndex> build(final List<SitemapEntry> entries) {
        entries.add(0, new SitemapEntry("", Frequency.hourly, 0.8, DateTime.now()));
        return sitemapIndexes(sitemaps(entries));
    }

    private List<SitemapIndex> sitemapIndexes(final List<Sitemap> sitemaps) {
        final List<List<Sitemap>> partitionnedSitemaps = Lists.partition(sitemaps, SitemapPreferences.sitemapIndexCapacity());
        final List<SitemapIndex> sitemapIndexes = Lists.newArrayList();
        int i = 0;
        for (final List<Sitemap> partition : partitionnedSitemaps) {
            final SitemapIndex sitemapIndex = new SitemapIndex(partition);
            sitemapIndex.setIndex(i++);
            sitemapIndexes.add(sitemapIndex);
        }
        return sitemapIndexes;
    }

    private List<Sitemap> sitemaps(final List<SitemapEntry> entries) {
        final List<List<SitemapEntry>> partitionnedEntries = Lists.partition(entries, SitemapPreferences.sitemapCapacity());
        final List<Sitemap> sitemaps = Lists.newArrayList();
        int i = 0;
        for (final List<SitemapEntry> partition : partitionnedEntries) {
            final Sitemap sitemap = new Sitemap(partition);
            sitemap.setIndex(i++);
            sitemaps.add(sitemap);
        }
        return sitemaps;
    }

}
