package com.steambeat.sitemap.domain;

import com.google.common.collect.Lists;
import org.joda.time.DateTime;

import java.util.List;

public class SitemapIndex {

    public SitemapIndex(final int index) {
        name = "sitemap_index_" + String.format("%05d", index) + ".xml";
        lastModTime = new DateTime();
    }

    public String getPath() {
        return "http://www.steambeat.com/" + name;
    }

    public void add(final Sitemap sitemap) {
        if (sitemaps.size() >= SITEMAP_INDEX_CAPACITY) {
            throw new CapacityException();
        }
        sitemaps.add(sitemap);
        lastModTime = new DateTime();
    }

    public List<Sitemap> getSitemaps() {
        return sitemaps;
    }

    public Sitemap getLastSitemap() {
        return sitemaps.get(sitemaps.size() - 1);
    }

    public static void setSitemapIndexCapacity(final int capacity) {
        SITEMAP_INDEX_CAPACITY = capacity;
    }

    public DateTime getLastModTime() {
        return lastModTime;
    }

    private final String name;
    private final List<Sitemap> sitemaps = Lists.newArrayList();
    private DateTime lastModTime;
    private static int SITEMAP_INDEX_CAPACITY = 50000;
}
