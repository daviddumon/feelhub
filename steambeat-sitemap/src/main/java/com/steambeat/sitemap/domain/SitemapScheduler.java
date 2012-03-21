//package com.steambeat.sitemap.domain;
//
//import com.steambeat.sitemap.fake.SitemapProperties;
//import org.joda.time.DateTime;
//import org.quartz.*;
//import org.quartz.impl.StdSchedulerFactory;
//
//import static org.quartz.JobBuilder.*;
//import static org.quartz.SimpleScheduleBuilder.*;
//import static org.quartz.TriggerBuilder.*;
//
//public class SitemapScheduler {
//
//    public SitemapScheduler() {
//        initialize();
//        createRootInSitemap();
//        run();
//    }
//
//    private void initialize() {
//        sitemapProperties = new SitemapProperties();
//        try {
//            scheduler = StdSchedulerFactory.getDefaultScheduler();
//            scheduler.start();
//        } catch (SchedulerException e) {
//        }
//    }
//
//    @SuppressWarnings("unchecked")
//    private void createRootInSitemap() {
//        //SitemapBuilder sitemapBuilder = new SitemapBuilder();
//        //List uris = Lists.newArrayList();
//        //Map uri = new HashMap();
//        //uri.put("value", "http://www.steambeat.com");
//        //uri.put("frequence", Frequency.always);
//        //uri.put("priority", 0.9);
//        //uris.add(uri);
//        //sitemapBuilder.build(0, uris);
//    }
//
//    private void run() {
//        JobDetail hiramJob = newJob(SitemapJob.class)
//                .withIdentity("hiramJob")
//                .build();
//
//        Trigger hiramTrigger = newTrigger()
//                .withIdentity("hiramTrigger")
//                .withSchedule(simpleSchedule()
//                        .withIntervalInSeconds(sitemapProperties.getDelay())
//                        .repeatForever())
//                .startAt(new DateTime().toDate())
//                .build();
//
//        try {
//            scheduler.scheduleJob(hiramJob, hiramTrigger);
//        } catch (SchedulerException e) {
//        }
//    }
//
//    public Scheduler getScheduler() {
//        return scheduler;
//    }
//
//    private SitemapProperties sitemapProperties;
//    private Scheduler scheduler;
//}
