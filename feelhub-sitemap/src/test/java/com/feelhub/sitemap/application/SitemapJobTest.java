package com.feelhub.sitemap.application;

import com.feelhub.sitemap.test.FakeSitemapsRepository;
import com.feelhub.sitemap.test.TestWithMongo;
import com.feelhub.test.TestFactories;
import org.junit.Before;
import org.junit.Test;
import org.mongolink.MongoSession;
import org.quartz.JobExecutionException;

import static org.fest.assertions.Assertions.assertThat;

public class SitemapJobTest extends TestWithMongo {

    private FakeSitemapsRepository sitemapRepository;

    @Before
    public void before() {
        sitemapRepository = new FakeSitemapsRepository();
        SitemapsRepository.initialize(sitemapRepository);
        MongoSession session = newSession();
        session.start();
        TestFactories.topics().newCompleteRealTopic();
        session.stop();
    }

    @Test
    public void canExecute() throws JobExecutionException {
        final SitemapJob sitemapJob = new SitemapJob(newSession());

        sitemapJob.execute(null);

        assertThat(sitemapRepository.getSitemapIndexes()).hasSize(1);
        assertThat(sitemapRepository.getSitemapIndexes().get(0).getSitemaps().get(0).getEntries()).hasSize(2);
    }

}
