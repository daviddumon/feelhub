package com.feelhub.sitemap.domain;

import com.google.common.collect.Lists;
import org.joda.time.DateTime;

import java.util.*;

public class Sitemap {

    public Sitemap(final List<SitemapEntry> entries) {
        this.entries = entries;
    }

    public List<SitemapEntry> getEntries() {
        return entries;
    }

    public DateTime getLastMod() {
        return lastModFromEntries();
    }

    private DateTime lastModFromEntries() {
        final List<DateTime> dates = Lists.newArrayList();
        for (final SitemapEntry sitemapEntry : entries) {
            dates.add(sitemapEntry.getLastMod());
        }
        return dates.isEmpty() ? new DateTime() : Collections.max(dates);
    }

    public String getName() {
        return "sitemap_" + String.format("%05d", index) + ".xml";
    }

    public String getLoc() {
        return "/" + getName();
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(final int index) {
        this.index = index;
    }

    private List<SitemapEntry> entries = Lists.newArrayList();
    private int index;
}