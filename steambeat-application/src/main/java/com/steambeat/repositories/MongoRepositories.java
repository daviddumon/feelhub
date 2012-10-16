package com.steambeat.repositories;

import com.steambeat.domain.alchemy.*;
import com.steambeat.domain.illustration.IllustrationRepository;
import com.steambeat.domain.keyword.KeywordRepository;
import com.steambeat.domain.opinion.OpinionRepository;
import com.steambeat.domain.reference.ReferenceRepository;
import com.steambeat.domain.relation.RelationRepository;
import com.steambeat.domain.session.SessionRepository;
import com.steambeat.domain.statistics.StatisticsRepository;
import com.steambeat.domain.user.UserRepository;

import javax.inject.Inject;

public class MongoRepositories extends Repositories {

    @Inject
    public MongoRepositories(final SessionProvider provider) {
        this.provider = provider;
    }

    @Override
    protected AlchemyAnalysisRepository getAlchemyAnalysisRepository() {
        return new AlchemyAnalysisMongoRepository(provider.get());
    }

    @Override
    protected AlchemyEntityRepository getAlchemyEntityRepository() {
        return new AlchemyEntityMongoRepository(provider.get());
    }

    @Override
    protected IllustrationRepository getIllustrationRepository() {
        return new IllustrationMongoRepository(provider.get());
    }

    @Override
    protected KeywordRepository getKeywordRepository() {
        return new KeywordMongoRepository(provider.get());
    }

    @Override
    protected ReferenceRepository getReferenceRepository() {
        return new ReferenceMongoRepository(provider.get());
    }

    @Override
    protected SessionRepository getSessionRepository() {
        return new SessionMongoRepository(provider.get());
    }

    @Override
    protected RelationRepository getRelationRepository() {
        return new RelationMongoRepository(provider.get());
    }

    @Override
    protected OpinionRepository getOpinionRepository() {
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
