package com.feelhub.sitemap.application;

import com.feelhub.repositories.TestWithMongoRepository;
import com.feelhub.sitemap.domain.*;
import com.feelhub.sitemap.test.WithFakeData;
import com.feelhub.test.*;
import org.junit.*;
import org.quartz.JobExecutionException;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@Ignore
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
        TestFactories.topics().newCompleteRealTopic();
        TestFactories.topics().newCompleteRealTopic();
        TestFactories.topics().newCompleteRealTopic();
        final SitemapJob sitemapJob = new SitemapJob(getProvider().get());

        sitemapJob.execute(null);

        assertThat(SitemapEntryRepository.size(), is(3));
    }

    @Test
    public void canFetchtopics() throws JobExecutionException {
        data.clear();
        TestFactories.topics().newCompleteRealTopic();
        TestFactories.topics().newCompleteRealTopic();
        final SitemapJob sitemapJob = new SitemapJob(getProvider().get());

        sitemapJob.execute(null);

        assertThat(SitemapEntryRepository.size(), is(2));
    }

    @Test
    public void canFetchTopics() throws JobExecutionException {
        data.clear();
        TestFactories.topics().newCompleteRealTopic();
        final SitemapJob sitemapJob = new SitemapJob(getProvider().get());

        sitemapJob.execute(null);

        assertThat(SitemapEntryRepository.size(), is(1));
    }

    @Test
    public void cleanExistingSitemapsAndIndexesBefore() throws JobExecutionException {
        data.clear();
        TestFactories.topics().newCompleteRealTopic();
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
        TestFactories.topics().newCompleteRealTopic();
        TestFactories.topics().newCompleteRealTopic();
        TestFactories.topics().newCompleteRealTopic();
        time.waitDays(1);
        sitemapJob.execute(null);
        data.clear();
        TestFactories.topics().newCompleteRealTopic();

        sitemapJob.execute(null);

        assertThat(SitemapEntryRepository.size(), is(1));
    }
}
