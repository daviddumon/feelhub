package com.feelhub.sitemap.test;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.google.common.collect.Lists;

import java.io.ByteArrayInputStream;
import java.util.List;

public class FakeAmazonS3 extends AmazonS3Client {
    private List<PutObjectRequest> putObjectRequests = Lists.newArrayList();
    private List<GetObjectRequest> getObjectRequests = Lists.newArrayList();

    @Override
    public PutObjectResult putObject(PutObjectRequest putObjectRequest) {
        getPutObjectRequests().add(putObjectRequest);
        return null;
    }

    @Override
    public S3Object getObject(GetObjectRequest getObjectRequest) {
        getObjectRequests.add(getObjectRequest);
        S3Object s3Object = new S3Object();
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
