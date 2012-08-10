package com.steambeat.sitemap.application;

import com.steambeat.repositories.TestWithMongoRepository;
import com.steambeat.sitemap.domain.*;
import com.steambeat.sitemap.test.WithFakeData;
import com.steambeat.test.*;
import org.junit.*;
import org.quartz.JobExecutionException;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsSitemapJob extends TestWithMongoRepository {

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Rule
    public WithFakeData data = new WithFakeData();

    @Test
    public void canSetLastBuildDate() throws JobExecutionException {
        final SitemapJob sitemapJob = new SitemapJob(getProvider().get());

        sitemapJob.execute(null);

        assertThat(sitemapJob.getLastBuildDate(), notNullValue());
        assertThat(sitemapJob.getLastBuildDate(), is(time.getNow()));
    }

    @Test
    public void lastBuildDateIsStatic() throws JobExecutionException {
        final SitemapJob sitemapJob = new SitemapJob(getProvider().get());
        sitemapJob.execute(null);

        final SitemapJob sitemapJob2 = new SitemapJob(getProvider().get());

        assertThat(sitemapJob.getLastBuildDate(), is(sitemapJob2.getLastBuildDate()));
    }

    @Test
    public void canExecute() throws JobExecutionException {
        data.clear();
        TestFactories.topics().newTopic();
        TestFactories.topics().newTopic();
        TestFactories.topics().newTopic();
        final SitemapJob sitemapJob = new SitemapJob(getProvider().get());

        sitemapJob.execute(null);

        assertThat(SitemapEntryRepository.size(), is(3));
    }

    @Test
    public void canFetchtopics() throws JobExecutionException {
        data.clear();
        TestFactories.topics().newTopic();
        TestFactories.topics().newTopic();
        final SitemapJob sitemapJob = new SitemapJob(getProvider().get());

        sitemapJob.execute(null);

        assertThat(SitemapEntryRepository.size(), is(2));
    }

    @Test
    public void canFetchTopics() throws JobExecutionException {
        data.clear();
        TestFactories.topics().newTopic();
        final SitemapJob sitemapJob = new SitemapJob(getProvider().get());

        sitemapJob.execute(null);

        assertThat(SitemapEntryRepository.size(), is(1));
    }

    @Test
    public void cleanExistingSitemapsAndIndexesBefore() throws JobExecutionException {
        data.clear();
        TestFactories.topics().newTopic();
        final SitemapJob sitemapJob = new SitemapJob(getProvider().get());

        sitemapJob.execute(null);
        sitemapJob.execute(null);

        assertThat(SitemapRepository.size(), is(1));
        assertThat(SitemapIndexRepository.size(), is(1));
    }

    @Test
    public void canUseLastBuildDateForFetching() throws JobExecutionException {
        data.clear();
        final SitemapJob sitemapJob = new SitemapJob(getProvider().get());
        TestFactories.topics().newTopic();
        TestFactories.topics().newTopic();
        TestFactories.topics().newTopic();
        time.waitDays(1);
        sitemapJob.execute(null);
        data.clear();
        TestFactories.topics().newTopic();

        sitemapJob.execute(null);

        assertThat(SitemapEntryRepository.size(), is(1));
    }
}
