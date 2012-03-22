package com.steambeat.sitemap.domain;

import com.google.common.collect.Lists;
import org.joda.time.DateTime;

import java.util.List;

public class Sitemap {

    public Sitemap(final int index) {
        name = "sitemap_" + String.format("%05d", index) + ".xml";
        lastModTime = new DateTime();
    }

    public String getPath() {
        return "http://www.steambeat.com/" + name;
    }

    public void addEntry(final SitemapEntry sitemapEntry) {
        if (sitemapEntries.size() >= SITEMAP_CAPACITY) {
            throw new CapacityException();
        }
        sitemapEntries.add(sitemapEntry);
        lastModTime = new DateTime();
    }

    public List<SitemapEntry> getEntries() {
        return sitemapEntries;
    }

    public DateTime getLastModTime() {
        return lastModTime;
    }

    public static void setSitemapCapacity(final int capacity) {
        SITEMAP_CAPACITY = capacity;
    }

    private final String name;
    private final List<SitemapEntry> sitemapEntries = Lists.newArrayList();
    private DateTime lastModTime;
    private static int SITEMAP_CAPACITY = 50000;
}
