package com.feelhub.repositories.fakeRepositories;

import com.feelhub.domain.alchemy.*;
import com.feelhub.domain.feeling.FeelingRepository;
import com.feelhub.domain.illustration.IllustrationRepository;
import com.feelhub.domain.keyword.KeywordRepository;
import com.feelhub.domain.relation.RelationRepository;
import com.feelhub.domain.session.SessionRepository;
import com.feelhub.domain.statistics.StatisticsRepository;
import com.feelhub.domain.topic.TopicRepository;
import com.feelhub.domain.user.UserRepository;
import com.feelhub.repositories.Repositories;

public class FakeMongoRepositories extends Repositories {

    @Override
    protected AlchemyAnalysisRepository getAlchemyAnalysisRepository() {
        return alchemyAnalysisRepository;
    }

    @Override
    protected AlchemyEntityRepository getAlchemyEntityRepository() {
        return alchemyEntityRepository;
    }

    @Override
    protected IllustrationRepository getIllustrationRepository() {
        return illustrationRepository;
    }

    @Override
    protected KeywordRepository getKeywordRepository() {
        return keywordRepository;
    }

    @Override
    protected TopicRepository getTopicRepository() {
        return topicRepository;
    }

    @Override
    protected SessionRepository getSessionRepository() {
        return sessionRepository;
    }

    @Override
    protected RelationRepository getRelationRepository() {
        return relationFakeRepository;
    }

    @Override
    protected FeelingRepository getFeelingRepository() {
        return feelingRepository;
    }

    @Override
    protected StatisticsRepository getStatisticsRepository() {
        return statisticsRepository;
    }

    @Override
    protected UserRepository getUserRepository() {
        return userRepository;
    }

    private final FakeFeelingRepository feelingRepository = new FakeFeelingRepository();
    private final RelationRepository relationFakeRepository = new FakeRelationRepository();
    private final FakeStatisticsRepository statisticsRepository = new FakeStatisticsRepository();
    private final FakeUserRepository userRepository = new FakeUserRepository();
    private final FakeSessionRepository sessionRepository = new FakeSessionRepository();
    private final FakeTopicRepository topicRepository = new FakeTopicRepository();
    private final FakeKeywordRepository keywordRepository = new FakeKeywordRepository();
    private final FakeIllustrationRepository illustrationRepository = new FakeIllustrationRepository();
    private final FakeAlchemyEntityRepository alchemyEntityRepository = new FakeAlchemyEntityRepository();
    private final AlchemyAnalysisRepository alchemyAnalysisRepository = new FakeAlchemyAnalysisRepository();
}
