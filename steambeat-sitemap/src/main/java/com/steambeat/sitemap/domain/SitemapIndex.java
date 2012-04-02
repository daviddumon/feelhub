package com.steambeat.sitemap.domain;

import java.util.List;

public class SitemapIndex {

    public SitemapIndex(final int index) {
        if (index < 0) {
            throw new SitemapIndexCreationException();
        }
        this.index = index;
        this.loc = "/sitemap_index_" + String.format("%05d", index) + ".xml";
    }

    public List<Sitemap> getSitemaps() {
        return SitemapRepository.getSitemapsFor(this);
    }

    public String getLoc() {
        return loc;
    }

    public int getIndex() {
        return index;
    }

    public static int getCapacity() {
        return CAPACITY;
    }

    public static void setCapacity(final int capacity) {
        SitemapIndex.CAPACITY = capacity;
    }

    private final int index;
    private final String loc;
    private static int CAPACITY = 1000;
}
