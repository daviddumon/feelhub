package com.feelhub.repositories;

import com.feelhub.domain.alchemy.*;
import com.feelhub.domain.feeling.FeelingRepository;
import com.feelhub.domain.illustration.IllustrationRepository;
import com.feelhub.domain.keyword.KeywordRepository;
import com.feelhub.domain.relation.RelationRepository;
import com.feelhub.domain.session.SessionRepository;
import com.feelhub.domain.statistics.StatisticsRepository;
import com.feelhub.domain.topic.TopicRepository;
import com.feelhub.domain.user.UserRepository;

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
    protected TopicRepository getTopicRepository() {
        return new TopicMongoRepository(provider.get());
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
    protected FeelingRepository getFeelingRepository() {
        return new FeelingMongoRepository(provider.get());
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
