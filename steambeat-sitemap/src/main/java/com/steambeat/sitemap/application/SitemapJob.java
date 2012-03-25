package com.steambeat.sitemap.application;

import com.steambeat.domain.subject.Subject;
import com.steambeat.sitemap.domain.*;
import com.steambeat.sitemap.tools.SitemapProperties;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.mongolink.*;
import org.mongolink.domain.criteria.Criteria;
import org.mongolink.domain.mapper.ContextBuilder;
import org.quartz.*;

import java.util.List;

public class SitemapJob implements Job {

    public SitemapJob() {
        final SitemapProperties sitemapProperties = new SitemapProperties();
        final ContextBuilder context = new ContextBuilder("com.steambeat.repositories.mapping");
        manager = MongoSessionManager.create(context, sitemapProperties.getDbSettings());
        session = manager.createSession();
    }

    public SitemapJob(final MongoSession mongoSession) {
        this.session = mongoSession;
    }

    @Override
    public void execute(final JobExecutionContext jobExecutionContext) throws JobExecutionException {
        lastBuildDate = new DateTime();
        logger.info("Beginning new sitemap job");
        session.start();
        List<Subject> list = fetchData();
        persistSubjects(list);
        session.stop();
    }

    private List<Subject> fetchData() {
        final Criteria criteria = session.createCriteria(Subject.class);
        return criteria.list();
    }

    private void persistSubjects(final List<Subject> list) {
        for (Subject subject : list) {
            SitemapEntryRepository.add(new SitemapEntry(subject.getId().toString(), Frequency.hourly, 0.5));
        }
        SitemapRepository.buildAllSitemaps();
        SitemapIndexRepository.buildAllSitemapIndexes();
    }

    public DateTime getLastBuildDate() {
        return lastBuildDate;
    }

    private MongoSessionManager manager;
    private final MongoSession session;
    private static DateTime lastBuildDate = new DateTime(1L);
    private static final Logger logger = Logger.getLogger(SitemapJob.class);
}
