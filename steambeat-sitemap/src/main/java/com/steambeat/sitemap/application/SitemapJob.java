package com.steambeat.sitemap.application;

import com.steambeat.domain.subject.Subject;
import com.steambeat.domain.subject.concept.Concept;
import com.steambeat.domain.subject.webpage.WebPage;
import com.steambeat.sitemap.domain.*;
import com.steambeat.sitemap.tools.SitemapProperties;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.mongolink.*;
import org.mongolink.domain.criteria.*;
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
        queryDate = lastBuildDate;
        lastBuildDate = new DateTime();
        logger.info("Beginning new sitemap job");
        rebuildSitemapsAndIndexes();
    }

    private void rebuildSitemapsAndIndexes() {
        session.start();
        clearExistingSitemapsAndIndexes();
        createSitemapEntriesFromSubjects(fetchWebpages(), "webpages");
        createSitemapEntriesFromSubjects(fetchConcepts(), "concepts");
        buildSitemapsAndIndexes();
        session.stop();
    }

    private void clearExistingSitemapsAndIndexes() {
        SitemapRepository.clear();
        SitemapIndexRepository.clear();
    }

    private void createSitemapEntriesFromSubjects(final List<Subject> subjects, final String uriToken) {
        for (final Subject subject : subjects) {
            SitemapEntryRepository.add(new SitemapEntry("/" + uriToken + "/" + subject.getId().toString(), Frequency.hourly, 0.5));
        }
    }

    private List<Subject> fetchWebpages() {
        final Criteria criteria = session.createCriteria(WebPage.class);
        criteria.add(Restrictions.equals("__discriminator", "WebPage"));
        addDateRestriction(criteria);
        return criteria.list();
    }

    private List<Subject> fetchConcepts() {
        final Criteria criteria = session.createCriteria(Concept.class);
        criteria.add(Restrictions.equals("__discriminator", "Concept"));
        addDateRestriction(criteria);
        return criteria.list();
    }

    private void addDateRestriction(final Criteria criteria) {
        criteria.add(Restrictions.greaterThanOrEqualTo("lastModificationDate", queryDate));
    }

    private void buildSitemapsAndIndexes() {
        SitemapRepository.buildAllSitemaps();
        SitemapIndexRepository.buildAllSitemapIndexes();
    }

    public DateTime getLastBuildDate() {
        return lastBuildDate;
    }

    private MongoSessionManager manager;
    private final MongoSession session;
    private static DateTime lastBuildDate = new DateTime(1L);
    private DateTime queryDate;
    private static final Logger logger = Logger.getLogger(SitemapJob.class);
}
