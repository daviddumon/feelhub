package com.feelhub.sitemap.test;

import com.feelhub.sitemap.application.SitemapsRepository;
import com.feelhub.sitemap.domain.SitemapIndex;

import java.io.InputStream;
import java.util.List;

public class FakeSitemapsRepository extends SitemapsRepository {

    @Override
    public void put(List<SitemapIndex> sitemapIndexes) {
        this.sitemapIndexes = sitemapIndexes;
    }

    @Override
    public InputStream get(String objectKey) {
        return null;
    }

    public List<SitemapIndex> getSitemapIndexes() {
        return sitemapIndexes;
    }

    private List<SitemapIndex> sitemapIndexes;
}
