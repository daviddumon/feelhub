package com.feelhub.sitemap.domain;

import com.google.common.collect.Lists;
import org.joda.time.DateTime;

import java.util.*;

public class Sitemap {

    public Sitemap(List<SitemapEntry> entries) {
        this.loc = "/sitemap_" + String.format("%05d", 1) + ".xml";
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
        return loc;
    }

    private final String loc;
    private List<SitemapEntry> entries = Lists.newArrayList();
}