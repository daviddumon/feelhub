package com.steambeat.sitemap.application;

import com.steambeat.sitemap.domain.SitemapEntryRepository;
import com.steambeat.sitemap.test.WithFakeData;
import com.steambeat.test.SystemTime;
import org.junit.*;
import org.quartz.*;

import javax.servlet.ServletException;
import java.io.File;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class TestsSitemapScheduler {

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Rule
    public WithFakeData withFakeData = new WithFakeData();

    @Test
    public void canRunApplication() throws InterruptedException, SchedulerException, ServletException {
        JobKey hiramJob = new JobKey("hiramJob");
        TriggerKey triggerKey = new TriggerKey("hiramTrigger");

        sitemapScheduler = new SitemapScheduler();
        sitemapScheduler.run();
        Thread.sleep(1500);

        assertTrue(sitemapScheduler.getScheduler().isStarted());
        assertTrue(sitemapScheduler.getScheduler().checkExists(hiramJob));
        assertTrue(sitemapScheduler.getScheduler().checkExists(triggerKey));
    }

    @Test
    public void addRootOnlyFirstTime() throws InterruptedException, ServletException {
        sitemapScheduler = new SitemapScheduler();

        assertThat(SitemapEntryRepository.get("http://www.steambeat.com"), notNullValue());
    }

    private SitemapScheduler sitemapScheduler;
    private String directoryName = "/hiram/sitemaps";
    private File directory = new File(directoryName);
}
