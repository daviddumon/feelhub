package com.bytedojo.domain;

import com.bytedojo.tools.HiramProperties;
import com.google.common.collect.Lists;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.restlet.Application;

import java.util.*;

import static org.quartz.JobBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;
import static org.quartz.TriggerBuilder.*;

public class HiramScheduler {

    public HiramScheduler() {
        initialize();
        createRootInSitemap();
        run();
    }

    private void initialize() {
        logger.info("application started");
        hiramProperties = new HiramProperties();
        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
        } catch (SchedulerException e) {
            logger.error(e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void createRootInSitemap() {
        SitemapBuilder sitemapBuilder = new SitemapBuilder();
        List uris = Lists.newArrayList();
        Map uri = new HashMap();
        uri.put("value", "http://www.kikiyoo.com");
        uri.put("frequence", Frequence.always);
        uri.put("priority", 0.9);
        uris.add(uri);
        sitemapBuilder.build(0, uris);
    }

    private void run() {
        JobDetail hiramJob = newJob(HiramJob.class)
                .withIdentity("hiramJob")
                .build();

        Trigger hiramTrigger = newTrigger()
                .withIdentity("hiramTrigger")
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(hiramProperties.getDelay())
                        .repeatForever())
                .startAt(new DateTime().toDate())
                .build();

        try {
            scheduler.scheduleJob(hiramJob, hiramTrigger);
        } catch (SchedulerException e) {
            logger.error(e.getMessage());
        }
    }

    public org.quartz.Scheduler getScheduler() {
        return scheduler;
    }

    private HiramProperties hiramProperties;
    private org.quartz.Scheduler scheduler;
    private static final Logger logger = Logger.getLogger(Application.class);
}
