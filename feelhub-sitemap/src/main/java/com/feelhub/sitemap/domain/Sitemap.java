package com.feelhub.sitemap.domain;

import com.google.common.collect.Lists;
import org.joda.time.DateTime;

import java.util.Collections;
import java.util.List;

public class Sitemap {

    public Sitemap(List<SitemapEntry> entries) {
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

    public String getLoc() {
        return "/sitemap_" + String.format("%05d", 1) + ".xml";
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    private List<SitemapEntry> entries = Lists.newArrayList();
    private int index;
}