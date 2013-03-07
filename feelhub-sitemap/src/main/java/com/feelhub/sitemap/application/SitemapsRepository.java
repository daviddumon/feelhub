package com.feelhub.sitemap.application;

import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.feelhub.sitemap.domain.SitemapIndex;

import java.io.InputStream;
import java.util.List;

public abstract class SitemapsRepository {

    public static SitemapsRepository instance() {
        return instance;
    }

    public static void initialize(SitemapsRepository instance) {
        SitemapsRepository.instance = instance;
    }

    public abstract void put(List<SitemapIndex> sitemapIndexes);

    public abstract InputStream get(String objectKey);

    private static SitemapsRepository instance;
}
