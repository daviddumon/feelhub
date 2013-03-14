package com.feelhub.sitemap.application;

import com.feelhub.sitemap.domain.SitemapIndex;

import java.io.InputStream;
import java.util.List;

public abstract class SitemapsRepository {

    public static SitemapsRepository instance() {
        return instance;
    }

    public static void initialize(final SitemapsRepository instance) {
        SitemapsRepository.instance = instance;
    }

    public abstract void put(List<SitemapIndex> sitemapIndexes);

    public abstract InputStream get(String objectKey);

    private static SitemapsRepository instance;
}
