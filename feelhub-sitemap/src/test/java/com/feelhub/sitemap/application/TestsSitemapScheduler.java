package com.feelhub.sitemap.application;

import com.feelhub.repositories.TestWithMongoRepository;
import com.feelhub.sitemap.domain.SitemapEntryRepository;
import com.feelhub.sitemap.test.FakeSitemapJob;
import com.feelhub.test.SystemTime;
import com.google.inject.*;
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
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
            }
        });
        sitemapScheduler = injector.getInstance(SitemapScheduler.class);
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

    @Test
    public void addRootOnlyFirstTime() throws InterruptedException, ServletException {
        sitemapScheduler.initialize();

        assertThat(SitemapEntryRepository.get(""), notNullValue());
    }

    private SitemapScheduler sitemapScheduler;
}
