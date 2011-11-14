package com.steambeat.repositories;

import com.steambeat.domain.Repository;
import com.steambeat.domain.opinion.Opinion;
import com.steambeat.domain.statistics.StatisticsRepository;
import com.steambeat.domain.subject.feed.*;
import fr.bodysplash.mongolink.MongoSession;

import javax.inject.Inject;

public class MongoRepositories extends Repositories {

    @Inject
    public MongoRepositories(SessionProvider provider) {
        this.provider = provider;
    }

    @Override
    protected Repository<Opinion> getOpinionRepository() {
        return new OpinionMongoRepository(provider.get());
    }

    @Override
    protected StatisticsRepository getStatisticsRepository() {
        return new StatisticsMongoRepository(provider.get());
    }

    @Override
    protected Repository<Feed> getFeedRepository() {
        return new FeedMongoRepository(provider.get());
    }

    @Override
    protected Repository<Association> getAssociationRepository() {
        return new AssociationMongoRepository(provider.get());
    }

    public MongoSession getSession() {
        return provider.get();
    }

    private SessionProvider provider;
}
