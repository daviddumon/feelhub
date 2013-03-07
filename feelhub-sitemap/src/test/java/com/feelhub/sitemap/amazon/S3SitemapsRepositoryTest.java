package com.feelhub.sitemap.amazon;

import com.amazonaws.services.s3.model.PutObjectRequest;
import com.feelhub.sitemap.amazon.S3SitemapsRepository;
import com.feelhub.sitemap.converter.RobotsTxtToStringConverter;
import com.feelhub.sitemap.converter.SitemapIndexToStringConverter;
import com.feelhub.sitemap.converter.SitemapToStringConverter;
import com.feelhub.sitemap.domain.Sitemap;
import com.feelhub.sitemap.domain.SitemapEntry;
import com.feelhub.sitemap.domain.SitemapIndex;
import com.feelhub.sitemap.test.FakeAmazonS3;
import com.feelhub.test.SystemTime;
import com.google.common.collect.Lists;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;

import static org.fest.assertions.Assertions.assertThat;

public class S3SitemapsRepositoryTest {

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Before
    public void setUp() throws Exception {
        s3 = new FakeAmazonS3();
        s3SitemapsRepository = new S3SitemapsRepository(s3);
    }

    @Test
    public void canPutRobotsTxt() throws IOException {
        s3SitemapsRepository.put(sitemapsIndexes());

        PutObjectRequest putObjectRequest = s3.getPutObjectRequests().get(0);
        assertThat(putObjectRequest.getKey()).isEqualTo("robots.txt");
        assertThat(putObjectRequest.getBucketName()).isEqualTo(S3SitemapsRepository.BUCKET_NAME);
        assertThat(toString(putObjectRequest.getInputStream())).isEqualTo(robotsTxtInString());
        assertThat(putObjectRequest.getMetadata().getContentLength()).isEqualTo(robotsTxtInString().length());
    }

    private String robotsTxtInString() {
        return new RobotsTxtToStringConverter(sitemapsIndexes()).toString();
    }

    @Test
    public void canPutSitemapIndexes() throws IOException {
        s3SitemapsRepository.put(sitemapsIndexes());

        PutObjectRequest putObjectRequest = s3.getPutObjectRequests().get(1);
        assertThat(putObjectRequest.getKey()).isEqualTo("sitemap_index_01234.xml");
        assertThat(putObjectRequest.getBucketName()).isEqualTo(S3SitemapsRepository.BUCKET_NAME);
        assertThat(toString(putObjectRequest.getInputStream())).isEqualTo(sitemapIndexInString());
        assertThat(putObjectRequest.getMetadata().getContentLength()).isEqualTo(sitemapIndexInString().length());
    }

    private String sitemapIndexInString() {
        return new SitemapIndexToStringConverter(sitemapsIndexes().get(0)).toString();
    }

    @Test
    public void canPutSitemap() throws IOException {
        s3SitemapsRepository.put(sitemapsIndexes());

        PutObjectRequest putObjectRequest = s3.getPutObjectRequests().get(2);
        assertThat(putObjectRequest.getKey()).isEqualTo("sitemap_08765.xml");
        assertThat(putObjectRequest.getBucketName()).isEqualTo(S3SitemapsRepository.BUCKET_NAME);
        assertThat(toString(putObjectRequest.getInputStream())).isEqualTo(sitemapInString());
        assertThat(putObjectRequest.getMetadata().getContentLength()).isEqualTo(sitemapInString().length());
    }

    private String sitemapInString() {
        return new SitemapToStringConverter(sitemapsIndexes().get(0).getSitemaps().get(0)).toString();
    }

    private String toString(InputStream inputStream) throws IOException {
        StringWriter writer = new StringWriter();
        IOUtils.copy(inputStream, writer);
        return writer.toString();
    }

    private ArrayList<SitemapIndex> sitemapsIndexes() {
        Sitemap sitemap = new Sitemap(Lists.<SitemapEntry>newArrayList());
        sitemap.setIndex(8765);
        SitemapIndex sitemapIndex = new SitemapIndex(Lists.<Sitemap>newArrayList(sitemap));
        sitemapIndex.setIndex(1234);
        return Lists.newArrayList(sitemapIndex);
    }

    private FakeAmazonS3 s3;
    private S3SitemapsRepository s3SitemapsRepository;
}
