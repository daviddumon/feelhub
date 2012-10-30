package com.feelhub.sitemap.application;

import com.feelhub.sitemap.domain.*;
import com.feelhub.sitemap.tools.SitemapProperties;
import org.joda.time.DateTime;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.JobBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;
import static org.quartz.TriggerBuilder.*;

public class SitemapScheduler {

    public void initialize() {
        initializeScheduler();
        createRootInSitemap();
    }

    private void initializeScheduler() {
        sitemapProperties = new SitemapProperties();
        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
        } catch (SchedulerException e) {
        }
    }

    private void createRootInSitemap() {
        final SitemapEntry root = new SitemapEntry("", Frequency.always, 0.8);
        SitemapEntryRepository.add(root);
    }

    public void run() {
        final JobDetail hiramJob = newJob(sitemapJobClass)
                .withIdentity("sitemapJob")
                .build();

        final Trigger hiramTrigger = newTrigger()
                .withIdentity("sitemapTrigger")
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(sitemapProperties.getDelay())
                        .repeatForever())
                .startAt(new DateTime().toDate())
                .build();

        try {
            scheduler.scheduleJob(hiramJob, hiramTrigger);
        } catch (SchedulerException e) {
        }
    }

    public void setSitemapJobClass(final Class<? extends Job> sitemapJobClass) {
        this.sitemapJobClass = sitemapJobClass;
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    private Scheduler scheduler;
    private SitemapProperties sitemapProperties;
    private Class<? extends Job> sitemapJobClass = SitemapJob.class;
}
