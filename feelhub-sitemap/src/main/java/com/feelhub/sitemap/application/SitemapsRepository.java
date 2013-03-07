package com.feelhub.sitemap.application;

import com.feelhub.sitemap.domain.SitemapIndex;

import java.util.List;

public abstract class SitemapsRepository {

    public static SitemapsRepository instance() {
        return instance;
    }

    public static void initialize(SitemapsRepository instance) {
        SitemapsRepository.instance = instance;
    }

    public abstract void put(List<SitemapIndex> sitemapIndexes);

    private static SitemapsRepository instance;
}
