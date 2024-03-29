package com.feelhub.domain;

import com.feelhub.domain.alchemy.AlchemyAnalyzer;
import com.feelhub.domain.bing.BingSearch;
import com.feelhub.domain.cloudinary.Cloudinary;
import com.feelhub.domain.scraper.HttpTopicAnalyzer;
import com.feelhub.domain.topic.TopicLastModificationDateUpdater;
import com.feelhub.domain.topic.http.HttpTopicIndexer;
import com.feelhub.domain.topic.real.RealTopicIndexer;
import com.feelhub.domain.topic.world.WorldListener;
import com.google.inject.AbstractModule;

public class DomainWorkersModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(RealTopicIndexer.class).asEagerSingleton();
        bind(HttpTopicIndexer.class).asEagerSingleton();
        bind(BingSearch.class).asEagerSingleton();
        bind(Cloudinary.class).asEagerSingleton();
        bind(AlchemyAnalyzer.class).asEagerSingleton();
        bind(HttpTopicAnalyzer.class).asEagerSingleton();
        bind(WorldListener.class).asEagerSingleton();
        bind(TopicLastModificationDateUpdater.class).asEagerSingleton();
    }
}
