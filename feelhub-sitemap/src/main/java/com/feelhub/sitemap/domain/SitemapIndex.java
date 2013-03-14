package com.feelhub.sitemap.domain;

import java.util.List;

public class SitemapIndex {

    public SitemapIndex(final List<Sitemap> sitemaps) {
        this.sitemaps = sitemaps;
    }

    public List<Sitemap> getSitemaps() {
        return sitemaps;
    }

    public String getLoc() {
        return "/" + getName();
    }

    public String getName() {
        return "sitemap_index_" + String.format("%05d", index) + ".xml";
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(final int index) {
        this.index = index;
    }

    private final List<Sitemap> sitemaps;

    private int index;

}
