package com.steambeat.sitemap.domain;

import com.google.common.base.Predicate;
import com.google.common.collect.*;

import javax.annotation.Nullable;
import java.util.*;

public class SitemapEntryRepository {

    public static List<SitemapEntry> getSitemapEntriesFor(final Sitemap sitemap) {
        final int index = sitemap.getIndex();
        final List<List<SitemapEntry>> lists = Lists.partition(sitemapEntries, Sitemap.getCapacity());
        return index < lists.size() ? lists.get(index) : new ArrayList<SitemapEntry>();
    }

    public static List<SitemapEntry> getSitemapEntries() {
        return sitemapEntries;
    }

    public static int size() {
        return sitemapEntries.size();
    }

    public static void add(final SitemapEntry entry) {
        if (sitemapEntries.contains(entry)) {
            final SitemapEntry sitemapEntry = sitemapEntries.get(sitemapEntries.indexOf(entry));
            sitemapEntry.setLastMod(entry.getLastMod());
        } else {
            sitemapEntries.add(entry);
        }
    }

    public static void clear() {
        sitemapEntries.clear();
    }

    public static SitemapEntry get(final String loc) {
        try {
            return Iterables.find(sitemapEntries, byLoc(loc));
        } catch (Exception e) {
            return null;
        }
    }

    private static Predicate<SitemapEntry> byLoc(final String loc) {

        return new Predicate<SitemapEntry>() {

            @Override
            public boolean apply(@Nullable final SitemapEntry input) {
                return input.getLoc().equals(loc);
            }
        };
    }

    private static List<SitemapEntry> sitemapEntries = Lists.newArrayList();
}
