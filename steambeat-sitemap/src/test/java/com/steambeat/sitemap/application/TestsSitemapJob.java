package com.steambeat.sitemap.application;

import com.mongodb.FakeDB;
import com.steambeat.repositories.TestWithMongoRepository;
import com.steambeat.sitemap.domain.SitemapEntryRepository;
import com.steambeat.test.SystemTime;
import com.steambeat.test.testFactories.TestFactories;
import org.junit.*;
import org.quartz.JobExecutionException;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsSitemapJob extends TestWithMongoRepository {

    @Rule
    public SystemTime time = SystemTime.fixed();

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
        TestFactories.subjects().newWebPage();
        TestFactories.subjects().newWebPage();
        TestFactories.subjects().newWebPage();
        final SitemapJob sitemapJob = new SitemapJob(getProvider().get());

        sitemapJob.execute(null);

        assertThat(SitemapEntryRepository.size(), is(3));
    }

    private FakeDB mongo;
}
