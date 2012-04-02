package com.steambeat.sitemap.application;

import com.steambeat.sitemap.domain.*;
import com.steambeat.sitemap.tools.SitemapProperties;
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
        grabExistingSubjects();
    }

    private void initializeScheduler() {
        sitemapProperties = new SitemapProperties();
        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
        } catch (SchedulerException e) {
        }
    }

    private void grabExistingSubjects() {
        try {
            final Job sitemapJob = sitemapJobClass.newInstance();
            sitemapJob.execute(null);
        } catch (JobExecutionException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
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

    public void setSitemapJobClass(Class<? extends Job> sitemapJobClass) {
        this.sitemapJobClass = sitemapJobClass;
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    private Scheduler scheduler;
    private SitemapProperties sitemapProperties;
    private Class<? extends Job> sitemapJobClass = SitemapJob.class;
}
