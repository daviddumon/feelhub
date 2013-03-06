package com.feelhub.sitemap.application;

import com.feelhub.test.TestFactories;
import org.junit.Before;
import org.junit.Test;
import org.mongolink.MongoSession;
import org.quartz.JobExecutionException;

public class SitemapJobTest extends TestWithMongo {

    @Before
    public void before() {
        MongoSession session = newSession();
        session.start();
        TestFactories.topics().newCompleteRealTopic();
        session.stop();
    }

    @Test
    public void canExecute() throws JobExecutionException {
        final SitemapJob sitemapJob = new SitemapJob(newSession());

        sitemapJob.execute(null);

        //assertThat(SitemapEntryRepository.size(), is(3));
    }

}
