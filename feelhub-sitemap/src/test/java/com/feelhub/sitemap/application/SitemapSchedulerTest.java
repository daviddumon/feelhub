package com.feelhub.sitemap.application;

import com.feelhub.repositories.TestWithMongoRepository;
import com.feelhub.sitemap.test.FakeSitemapJob;
import com.feelhub.test.SystemTime;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.TriggerKey;

import javax.servlet.ServletException;

import static org.junit.Assert.assertTrue;

public class SitemapSchedulerTest extends TestWithMongoRepository {

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Before
    public void before() {
        sitemapScheduler = new SitemapScheduler();
        sitemapScheduler.setSitemapJobClass(FakeSitemapJob.class);
    }

    @Test
    public void canRunApplication() throws InterruptedException, SchedulerException, ServletException {
        final JobKey hiramJob = new JobKey("sitemapJob");
        final TriggerKey triggerKey = new TriggerKey("sitemapTrigger");

        sitemapScheduler.initialize();
        sitemapScheduler.run();
        Thread.sleep(1500);

        assertTrue(sitemapScheduler.getScheduler().isStarted());
        assertTrue(sitemapScheduler.getScheduler().checkExists(hiramJob));
        assertTrue(sitemapScheduler.getScheduler().checkExists(triggerKey));
    }

    private SitemapScheduler sitemapScheduler;
}
