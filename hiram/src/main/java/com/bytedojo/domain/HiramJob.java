package com.bytedojo.domain;

import com.bytedojo.tools.HiramProperties;
import fr.bodysplash.mongolink.MongoSessionManager;
import fr.bodysplash.mongolink.domain.mapper.ContextBuilder;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.quartz.*;

import java.util.List;

public class HiramJob implements Job {

    public HiramJob() {
        logger.info("Starting new job");
        this.sitemapBuilder = new SitemapBuilder();
        final HiramProperties hiramProperties = new HiramProperties();
        ContextBuilder contextBuilder = new ContextBuilder("com.bytedojo.context");
        mongoSessionManager = MongoSessionManager.create(contextBuilder, hiramProperties.getDbSettings());
        this.mongoQuery = new MongoQuery(mongoSessionManager.createSession());
    }

    public HiramJob(SitemapBuilder sitemapBuilder, MongoQuery mongoQuery) {
        logger.info("Starting new job");
        this.sitemapBuilder = sitemapBuilder;
        this.mongoQuery = mongoQuery;
        final HiramProperties hiramProperties = new HiramProperties();
        ContextBuilder contextBuilder = new ContextBuilder("com.bytedojo.context");
        mongoSessionManager = MongoSessionManager.create(contextBuilder, hiramProperties.getDbSettings());
    }

    @Override
    public void execute(final JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("job started");
        if (lastBuildDate != null) {
            logger.info("last build date : " + lastBuildDate);
        }
        List uris = mongoQuery.execute(lastBuildDate);
        lastBuildDate = new DateTime();
        sitemapBuilder.build(lastUrisCount, uris);
        lastUrisCount += uris.size();
        mongoSessionManager.close();
        logger.info("job finished. Uris added : " + uris.size() + ". Total uris : " + lastUrisCount);
    }

    public DateTime getLastBuildDate() {
        return lastBuildDate;
    }

    public int getLastUrisCount() {
        return lastUrisCount;
    }

    public void setLastUrisCount(int value) {
        lastUrisCount = value;
    }

    private SitemapBuilder sitemapBuilder;
    private MongoQuery mongoQuery;
    private static int lastUrisCount;
    private static DateTime lastBuildDate = new DateTime(1L);
    private static final Logger logger = Logger.getLogger(HiramJob.class);
    private MongoSessionManager mongoSessionManager;
}
