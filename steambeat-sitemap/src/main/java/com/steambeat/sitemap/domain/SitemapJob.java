package com.steambeat.sitemap.domain;

import com.steambeat.sitemap.tools.SitemapProperties;
import fr.bodysplash.mongolink.MongoSessionManager;
import fr.bodysplash.mongolink.domain.mapper.ContextBuilder;
import org.joda.time.DateTime;
import org.quartz.*;

import java.util.List;

public class SitemapJob implements Job {

    public SitemapJob() {
        this.sitemapBuilder = new SitemapBuilder();
        final SitemapProperties sitemapProperties = new SitemapProperties();
        ContextBuilder contextBuilder = new ContextBuilder("com.steambeat.repositories.mapping");
        mongoSessionManager = MongoSessionManager.create(contextBuilder, sitemapProperties.getDbSettings());
        this.mongoQuery = new MongoQuery(mongoSessionManager.createSession());
    }

    public SitemapJob(SitemapBuilder sitemapBuilder, MongoQuery mongoQuery) {
        this.sitemapBuilder = sitemapBuilder;
        this.mongoQuery = mongoQuery;
        final SitemapProperties sitemapProperties = new SitemapProperties();
        ContextBuilder contextBuilder = new ContextBuilder("com.steambeat.repositories.mapping");
        mongoSessionManager = MongoSessionManager.create(contextBuilder, sitemapProperties.getDbSettings());
    }

    @Override
    public void execute(final JobExecutionContext jobExecutionContext) throws JobExecutionException {
        List uris = mongoQuery.execute(lastBuildDate);
        lastBuildDate = new DateTime();
        sitemapBuilder.build(lastUrisCount, uris);
        lastUrisCount += uris.size();
        mongoSessionManager.close();
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
    private MongoSessionManager mongoSessionManager;
}
