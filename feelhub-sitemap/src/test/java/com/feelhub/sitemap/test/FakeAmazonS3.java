package com.feelhub.sitemap.test;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.google.common.collect.Lists;

import java.util.List;

public class FakeAmazonS3 extends AmazonS3Client {
    private List<PutObjectRequest> putObjectRequests = Lists.newArrayList();

    @Override
    public PutObjectResult putObject(PutObjectRequest putObjectRequest) {
        getPutObjectRequests().add(putObjectRequest);
        return null;
    }

    public List<PutObjectRequest> getPutObjectRequests() {
        return putObjectRequests;
    }
}
