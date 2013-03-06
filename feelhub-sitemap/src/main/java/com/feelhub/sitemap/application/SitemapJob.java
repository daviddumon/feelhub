package com.feelhub.sitemap.application;

import com.feelhub.domain.topic.real.RealTopic;
import com.feelhub.sitemap.domain.Frequency;
import com.feelhub.sitemap.domain.SitemapEntry;
import com.feelhub.sitemap.domain.SitemapIndexBuilder;
import com.feelhub.sitemap.tools.SitemapProperties;
import com.google.common.collect.Lists;
import org.apache.log4j.Logger;
import org.mongolink.MongoSession;
import org.mongolink.MongoSessionManager;
import org.mongolink.domain.mapper.ContextBuilder;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

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
        logger.info("Beginning new sitemap job");
        new SitemapIndexBuilder().build(sitemapEntriesFor(new TopicsProvider().topics(session), "topics"));
    }

    private List<SitemapEntry> sitemapEntriesFor(final List<RealTopic> realTopics, final String uriToken) {
        List<SitemapEntry> result = Lists.newArrayList();
        for (RealTopic realTopic : realTopics) {
            result.add(new SitemapEntry("/" + uriToken + "/" + realTopic.getId().toString(), Frequency.hourly, 0.5));
        }
        return result;
    }

    private final MongoSession session;
    private static final Logger logger = Logger.getLogger(SitemapJob.class);

}
