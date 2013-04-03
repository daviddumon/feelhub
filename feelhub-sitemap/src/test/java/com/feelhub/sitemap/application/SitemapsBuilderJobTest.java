package com.feelhub.sitemap.application;

import com.feelhub.domain.topic.real.RealTopic;
import com.feelhub.sitemap.domain.*;
import com.feelhub.sitemap.test.*;
import com.feelhub.test.TestFactories;
import org.joda.time.DateTime;
import org.junit.*;
import org.mongolink.MongoSession;

import java.util.List;

import static org.fest.assertions.Assertions.*;

public class SitemapsBuilderJobTest extends TestWithMongo {

    private FakeSitemapsRepository sitemapRepository;

    @Before
    public void before() {
        sitemapRepository = new FakeSitemapsRepository();
        SitemapsRepository.initialize(sitemapRepository);
        final MongoSession session = newSession();
        session.start();
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        realTopic.setLastModificationDate(DateTime.parse("01"));
        session.stop();
    }

    @Test
    @Ignore("ne passes pas sur la ci")
    public void canExecute() {
        final SitemapsBuilderJob sitemapsBuilderJob = new SitemapsBuilderJob(newSession());

        sitemapsBuilderJob.execute();

        final List<SitemapIndex> indexes = sitemapRepository.getSitemapIndexes();
        assertThat(indexes).hasSize(1);
        final List<SitemapEntry> sitemapEntries = indexes.get(0).getSitemaps().get(0).getEntries();
        assertThat(sitemapEntries).hasSize(2);
        assertThat(sitemapEntries.get(1).getLastMod()).isEqualTo(DateTime.parse("01"));
    }
}
