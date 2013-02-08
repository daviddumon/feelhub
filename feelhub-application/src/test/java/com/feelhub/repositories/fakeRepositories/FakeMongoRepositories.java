package com.feelhub.repositories.fakeRepositories;

import com.feelhub.domain.admin.AdminStatisticsRepository;
import com.feelhub.domain.alchemy.*;
import com.feelhub.domain.feeling.FeelingRepository;
import com.feelhub.domain.media.MediaRepository;
import com.feelhub.domain.related.RelatedRepository;
import com.feelhub.domain.session.SessionRepository;
import com.feelhub.domain.statistics.StatisticsRepository;
import com.feelhub.domain.tag.TagRepository;
import com.feelhub.domain.topic.TopicRepository;
import com.feelhub.domain.user.*;
import com.feelhub.repositories.Repositories;

public class FakeMongoRepositories extends Repositories {

    @Override
    protected ActivationRepository getActivationRepository() {
        return activationRepository;
    }

    @Override
    protected AlchemyAnalysisRepository getAlchemyAnalysisRepository() {
        return alchemyAnalysisRepository;
    }

    @Override
    protected AlchemyEntityRepository getAlchemyEntityRepository() {
        return alchemyEntityRepository;
    }

    @Override
    protected AdminStatisticsRepository getAdminStatisticsRepository() {
        return adminStatisticsRepository;
    }

    @Override
    protected TagRepository getKeywordRepository() {
        return keywordRepository;
    }

    @Override
    protected TopicRepository getUsableTopicRepository() {
        return topicRepository;
    }

    @Override
    protected SessionRepository getSessionRepository() {
        return sessionRepository;
    }

    @Override
    protected RelatedRepository getRelatedRepository() {
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

    @Override
    protected MediaRepository getMediaRepository() {
        return mediaRepository;
    }

    private final FakeFeelingRepository feelingRepository = new FakeFeelingRepository();
    private final RelatedRepository relationFakeRepository = new FakeRelatedRepository();
    private final MediaRepository mediaRepository = new FakeMediaRepository();
    private final FakeStatisticsRepository statisticsRepository = new FakeStatisticsRepository();
    private final FakeUserRepository userRepository = new FakeUserRepository();
    private final FakeSessionRepository sessionRepository = new FakeSessionRepository();
    private final FakeTopicRepository topicRepository = new FakeTopicRepository();
    private final TagRepository keywordRepository = new FakeTagRepository();
    private final FakeAlchemyEntityRepository alchemyEntityRepository = new FakeAlchemyEntityRepository();
    private final FakeActivationRepository activationRepository = new FakeActivationRepository();
    private final AlchemyAnalysisRepository alchemyAnalysisRepository = new FakeAlchemyAnalysisRepository();
    private final AdminStatisticsRepository adminStatisticsRepository = new FakeAdminStatisticsRepository();


}
