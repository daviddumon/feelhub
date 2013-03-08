package com.feelhub.sitemap;

import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3Client;
import com.feelhub.sitemap.amazon.S3SitemapsRepository;
import com.feelhub.sitemap.application.*;
import org.quartz.JobExecutionException;

public class Main {
    public static void main(String args[]) throws JobExecutionException {
        SitemapsRepository.initialize(new S3SitemapsRepository(new AmazonS3Client(new ClasspathPropertiesFileCredentialsProvider())));
        new SitemapsBuilderJob().execute();
    }
}
