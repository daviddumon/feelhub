package com.feelhub.sitemap.application;

import com.feelhub.domain.topic.Topic;
import com.feelhub.sitemap.domain.*;
import com.feelhub.sitemap.tools.SitemapProperties;
import com.google.common.collect.Lists;
import org.mongolink.*;
import org.mongolink.domain.mapper.ContextBuilder;
import org.slf4j.*;

import java.util.List;

public class SitemapsBuilderJob {

    public SitemapsBuilderJob() {
        final SitemapProperties sitemapProperties = new SitemapProperties();
        final ContextBuilder context = new ContextBuilder("com.feelhub.repositories.mapping");
        final MongoSessionManager manager = MongoSessionManager.create(context, sitemapProperties.getDbSettings());
        session = manager.createSession();
    }

    public SitemapsBuilderJob(final MongoSession mongoSession) {
        this.session = mongoSession;
    }

    public void execute() {
        logger.info("Beginning new sitemap job");
        SitemapsRepository.instance().put(sitemapIndexes());
    }

    private List<SitemapIndex> sitemapIndexes() {
        return new SitemapIndexBuilder().build(sitemapEntriesFor(new TopicsProvider().topics(session), "topic"));
    }

    private List<SitemapEntry> sitemapEntriesFor(final List<Topic> topics, final String uriToken) {
        final List<SitemapEntry> result = Lists.newArrayList();
        for (final Topic topic : topics) {
            result.add(new SitemapEntry("/" + uriToken + "/" + topic.getId().toString(), Frequency.hourly, 0.5));
        }
        return result;
    }

    private final MongoSession session;
    private static final Logger logger = LoggerFactory.getLogger(SitemapsBuilderJob.class);

}
