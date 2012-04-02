package com.steambeat.sitemap.application;

import com.steambeat.repositories.TestWithMongoRepository;
import com.steambeat.sitemap.domain.*;
import com.steambeat.sitemap.test.WithFakeData;
import com.steambeat.test.SystemTime;
import com.steambeat.test.testFactories.TestFactories;
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
        TestFactories.subjects().newWebPage();
        TestFactories.subjects().newWebPage();
        TestFactories.subjects().newWebPage();
        final SitemapJob sitemapJob = new SitemapJob(getProvider().get());

        sitemapJob.execute(null);

        assertThat(SitemapEntryRepository.size(), is(3));
    }

    @Test
    public void canFetchWebpages() throws JobExecutionException {
        data.clear();
        TestFactories.subjects().newWebPage();
        TestFactories.subjects().newWebPage();
        final SitemapJob sitemapJob = new SitemapJob(getProvider().get());

        sitemapJob.execute(null);

        assertThat(SitemapEntryRepository.size(), is(2));
    }

    @Test
    public void canFetchConcepts() throws JobExecutionException {
        data.clear();
        TestFactories.subjects().newConcept();
        final SitemapJob sitemapJob = new SitemapJob(getProvider().get());

        sitemapJob.execute(null);

        assertThat(SitemapEntryRepository.size(), is(1));
    }

    @Test
    public void cleanExistingSitemapsAndIndexesBefore() throws JobExecutionException {
        data.clear();
        TestFactories.subjects().newWebPage();
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
        TestFactories.subjects().newWebPage();
        TestFactories.subjects().newWebPage();
        TestFactories.subjects().newWebPage();
        time.waitDays(1);
        sitemapJob.execute(null);
        data.clear();
        TestFactories.subjects().newWebPage();

        sitemapJob.execute(null);

        assertThat(SitemapEntryRepository.size(), is(1));
    }
}
