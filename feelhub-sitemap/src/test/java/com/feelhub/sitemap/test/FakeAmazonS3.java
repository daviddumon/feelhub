package com.feelhub.sitemap.test;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.google.common.collect.Lists;

import java.io.ByteArrayInputStream;
import java.util.List;

public class FakeAmazonS3 extends AmazonS3Client {
    private final List<PutObjectRequest> putObjectRequests = Lists.newArrayList();
    private final List<GetObjectRequest> getObjectRequests = Lists.newArrayList();

    @Override
    public PutObjectResult putObject(final PutObjectRequest putObjectRequest) {
        getPutObjectRequests().add(putObjectRequest);
        return null;
    }

    @Override
    public S3Object getObject(final GetObjectRequest getObjectRequest) {
        getObjectRequests.add(getObjectRequest);
        final S3Object s3Object = new S3Object();
        s3Object.setObjectContent(new ByteArrayInputStream("toto".getBytes()));
        return s3Object;
    }

    public List<PutObjectRequest> getPutObjectRequests() {
        return putObjectRequests;
    }

    public List<GetObjectRequest> getGetObjectRequests() {
        return getObjectRequests;
    }
}
