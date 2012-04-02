package com.steambeat.sitemap.domain;

import com.google.common.collect.Lists;
import org.joda.time.DateTime;

import java.util.*;

public class Sitemap {

    public Sitemap(final int index) {
        if (index < 0) {
            throw new SitemapCreationException();
        }
        this.index = index;
        this.loc = "/sitemap_" + String.format("%05d", index) + ".xml";
        this.lastMod = getLastModFromSitemapEntries(SitemapEntryRepository.getSitemapEntriesFor(this));
    }

    private DateTime getLastModFromSitemapEntries(final List<SitemapEntry> sitemapEntries) {
        final List<DateTime> dates = Lists.newArrayList();
        for (final SitemapEntry sitemapEntry : sitemapEntries) {
            dates.add(sitemapEntry.getLastMod());
        }
        return dates.isEmpty() ? new DateTime() : Collections.max(dates);
    }

    public List<SitemapEntry> getEntries() {
        return SitemapEntryRepository.getSitemapEntriesFor(this);
    }

    public DateTime getLastMod() {
        return lastMod;
    }

    public String getLoc() {
        return loc;
    }

    public static int getCapacity() {
        return CAPACITY;
    }

    public static void setCapacity(final int capacity) {
        CAPACITY = capacity;
    }

    public int getIndex() {
        return index;
    }

    private final int index;
    private final DateTime lastMod;
    private final String loc;
    private static int CAPACITY = 1000;
}