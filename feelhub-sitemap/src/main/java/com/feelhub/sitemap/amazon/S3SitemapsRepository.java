package com.feelhub.sitemap.amazon;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.feelhub.sitemap.application.SitemapsRepository;
import com.feelhub.sitemap.converter.*;
import com.feelhub.sitemap.domain.*;

import java.io.*;
import java.util.List;

public class S3SitemapsRepository extends SitemapsRepository {

    public static final String BUCKET_NAME = "feelhub-sitemaps";

    public S3SitemapsRepository(final AmazonS3 s3) {
        this.s3 = s3;
    }

    @Override
    public void put(final List<SitemapIndex> sitemapIndexes) {
        putObject(new RobotsTxtToStringConverter(sitemapIndexes).toString(), "robots.txt");
        for (final SitemapIndex sitemapIndex : sitemapIndexes) {
            putObject(new SitemapIndexToStringConverter(sitemapIndex).toString(), sitemapIndex.getName());
            for (final Sitemap sitemap : sitemapIndex.getSitemaps()) {
                putObject(new SitemapToStringConverter(sitemap).toString(), sitemap.getName());
            }
        }
    }

    @Override
    public InputStream get(final String objectKey) {
        return s3.getObject(new GetObjectRequest(BUCKET_NAME, objectKey)).getObjectContent();
    }

    private void putObject(final String content, final String objectName) {
        final PutObjectRequest putObjectRequest = new PutObjectRequest(BUCKET_NAME, objectName, new ByteArrayInputStream(content.getBytes()), objectMetadata(content));
        putObjectRequest.setStorageClass(StorageClass.ReducedRedundancy);
        s3.putObject(putObjectRequest);
    }

    private ObjectMetadata objectMetadata(final String content) {
        final ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(content.length());
        return metadata;
    }

    private final AmazonS3 s3;
}
