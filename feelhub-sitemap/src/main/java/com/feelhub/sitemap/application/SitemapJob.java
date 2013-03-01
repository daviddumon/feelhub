package com.feelhub.sitemap.application;

import com.feelhub.domain.topic.Topic;
import com.feelhub.domain.topic.real.RealTopic;
import com.feelhub.sitemap.domain.*;
import com.feelhub.sitemap.tools.SitemapProperties;
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
        final ContextBuilder context = new ContextBuilder("com.feelhub.repositories.mapping");
        final MongoSessionManager manager = MongoSessionManager.create(context, sitemapProperties.getDbSettings());
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
        createSitemapEntriesFromTopics(fetchtopics(), "topics");
        buildSitemapsAndIndexes();
        session.stop();
    }

    private void clearExistingSitemapsAndIndexes() {
        SitemapRepository.clear();
        SitemapIndexRepository.clear();
    }

    private void createSitemapEntriesFromTopics(final List<RealTopic> realTopics, final String uriToken) {
        for (final RealTopic realTopic : realTopics) {
            SitemapEntryRepository.add(new SitemapEntry("/" + uriToken + "/" + realTopic.getId().toString(), Frequency.hourly, 0.5));
        }
    }

    private List<RealTopic> fetchtopics() {
        final Criteria criteria = session.createCriteria(Topic.class);
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

    private final MongoSession session;
    private static DateTime lastBuildDate = new DateTime(1L);
    private DateTime queryDate;
    private static final Logger logger = Logger.getLogger(SitemapJob.class);
}
