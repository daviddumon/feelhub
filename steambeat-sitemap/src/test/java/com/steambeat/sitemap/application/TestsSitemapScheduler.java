package com.steambeat.sitemap.application;

import com.steambeat.repositories.TestWithMongoRepository;
import com.steambeat.sitemap.domain.SitemapEntryRepository;
import com.steambeat.test.SystemTime;
import com.steambeat.test.testFactories.TestFactories;
import org.junit.*;
import org.quartz.*;

import javax.servlet.ServletException;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class TestsSitemapScheduler extends TestWithMongoRepository {

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Before
    public void before() {
        sitemapScheduler = new SitemapScheduler();
    }

    @Test
    public void canRunApplication() throws InterruptedException, SchedulerException, ServletException {
        final JobKey hiramJob = new JobKey("hiramJob");
        final TriggerKey triggerKey = new TriggerKey("hiramTrigger");

        sitemapScheduler.initialize();
        sitemapScheduler.run();
        Thread.sleep(1500);

        assertTrue(sitemapScheduler.getScheduler().isStarted());
        assertTrue(sitemapScheduler.getScheduler().checkExists(hiramJob));
        assertTrue(sitemapScheduler.getScheduler().checkExists(triggerKey));
    }

    @Test
    public void addRootOnlyFirstTime() throws InterruptedException, ServletException {
        sitemapScheduler.initialize();

        assertThat(SitemapEntryRepository.get(""), notNullValue());
    }

    @Test
    public void grabAllExistingSubjectsOnBoot() {
        TestFactories.subjects().newWebPage();
        TestFactories.subjects().newWebPage();
        TestFactories.subjects().newWebPage();

        sitemapScheduler.initialize();

        assertThat(SitemapEntryRepository.size(), is(4));
    }

    private SitemapScheduler sitemapScheduler;
}
