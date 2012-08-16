package com.steambeat.repositories;

import com.steambeat.domain.Repository;
import com.steambeat.domain.keyword.KeywordRepository;
import com.steambeat.domain.opinion.Opinion;
import com.steambeat.domain.relation.RelationRepository;
import com.steambeat.domain.session.Session;
import com.steambeat.domain.statistics.StatisticsRepository;
import com.steambeat.domain.reference.Reference;
import com.steambeat.domain.user.UserRepository;

import javax.inject.Inject;

public class MongoRepositories extends Repositories {

    @Inject
    public MongoRepositories(final SessionProvider provider) {
        this.provider = provider;
    }

    @Override
    protected KeywordRepository getKeywordRepository() {
        return new KeywordMongoRepository(provider.get());
    }

    @Override
    protected Repository<Reference> getReferenceRepository() {
        return new ReferenceMongoRepository(provider.get());
    }

    @Override
    protected Repository<Session> getSessionRepository() {
        return new SessionMongoRepository(provider.get());
    }

    @Override
    protected RelationRepository getRelationRepository() {
        return new RelationMongoRepository(provider.get());
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
    protected UserRepository getUserRepository() {
        return new UserMongoRepository(provider.get());
    }

    private final SessionProvider provider;
}
