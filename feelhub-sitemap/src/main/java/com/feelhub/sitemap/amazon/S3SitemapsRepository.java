package com.feelhub.sitemap.amazon;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.StorageClass;
import com.feelhub.sitemap.application.SitemapsRepository;
import com.feelhub.sitemap.converter.RobotsTxtToStringConverter;
import com.feelhub.sitemap.converter.SitemapIndexToStringConverter;
import com.feelhub.sitemap.converter.SitemapToStringConverter;
import com.feelhub.sitemap.domain.Sitemap;
import com.feelhub.sitemap.domain.SitemapIndex;

import java.io.ByteArrayInputStream;
import java.util.List;

public class S3SitemapsRepository extends SitemapsRepository {

    public static final String BUCKET_NAME = "feelhub-sitemaps";

    public S3SitemapsRepository(AmazonS3 s3) {
        this.s3 = s3;
    }

    @Override
    public void put(List<SitemapIndex> sitemapIndexes) {
        putObject(new RobotsTxtToStringConverter(sitemapIndexes).toString(), "robots.txt");
        for (SitemapIndex sitemapIndex : sitemapIndexes) {
            putObject(new SitemapIndexToStringConverter(sitemapIndex).toString(), sitemapIndex.getName());
            for (Sitemap sitemap : sitemapIndex.getSitemaps()) {
                 putObject(new SitemapToStringConverter(sitemap).toString(), sitemap.getName());
            }
        }
    }

    private void putObject(String content, String objectName) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(BUCKET_NAME, objectName, new ByteArrayInputStream(content.getBytes()), objectMetadata(content));
        putObjectRequest.setStorageClass(StorageClass.ReducedRedundancy);
        s3.putObject(putObjectRequest);
    }

    private ObjectMetadata objectMetadata(String content) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(content.length());
        return metadata;
    }

    private AmazonS3 s3;
}
