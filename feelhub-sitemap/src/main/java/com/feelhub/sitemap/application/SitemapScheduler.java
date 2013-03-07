package com.feelhub.sitemap.application;

import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3Client;
import com.feelhub.sitemap.amazon.S3SitemapsRepository;
import com.feelhub.sitemap.tools.SitemapProperties;
import org.joda.time.DateTime;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class SitemapScheduler {

    public void initialize() {
        initializeScheduler();
    }

    private void initializeScheduler() {
        sitemapProperties = new SitemapProperties();
        SitemapsRepository.initialize(new S3SitemapsRepository(new AmazonS3Client(new ClasspathPropertiesFileCredentialsProvider())));
        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
        } catch (SchedulerException e) {
        }
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
