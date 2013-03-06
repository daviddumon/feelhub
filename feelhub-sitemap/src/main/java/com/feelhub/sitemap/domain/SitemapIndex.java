package com.feelhub.sitemap.domain;

import java.util.List;

public class SitemapIndex {

    public SitemapIndex(List<Sitemap> sitemaps) {
        this.sitemaps = sitemaps;
    }

    public List<Sitemap> getSitemaps() {
        return sitemaps;
    }

    public String getLoc() {
        return "/sitemap_index_" + String.format("%05d", index) + ".xml";
    }

    public void setIndex(int index) {
        this.index = index;
    }

    private List<Sitemap> sitemaps;

    private int index;

}
