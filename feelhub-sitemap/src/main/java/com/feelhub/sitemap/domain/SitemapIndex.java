package com.feelhub.sitemap.domain;

import java.util.List;

public class SitemapIndex {

    public SitemapIndex(List<Sitemap> sitemaps) {
        this.sitemaps = sitemaps;
    }

    public List<Sitemap> getSitemaps() {
        return sitemaps;
    }

    private List<Sitemap> sitemaps;
}
