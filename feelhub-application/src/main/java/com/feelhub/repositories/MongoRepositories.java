package com.feelhub.repositories;

import com.feelhub.domain.admin.AdminStatisticsRepository;
import com.feelhub.domain.alchemy.*;
import com.feelhub.domain.feeling.FeelingRepository;
import com.feelhub.domain.media.MediaRepository;
import com.feelhub.domain.related.RelatedRepository;
import com.feelhub.domain.session.SessionRepository;
import com.feelhub.domain.statistics.StatisticsRepository;
import com.feelhub.domain.tag.TagRepository;
import com.feelhub.domain.topic.TopicRepository;
import com.feelhub.domain.user.UserRepository;
import com.feelhub.domain.user.activation.ActivationRepository;

import javax.inject.Inject;

public class MongoRepositories extends Repositories {

    @Inject
    public MongoRepositories(final SessionProvider provider) {
        this.provider = provider;
    }

    @Override
    protected ActivationRepository getActivationRepository() {
        return new ActivationMongoRepository(provider.get());
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
    protected AdminStatisticsRepository getAdminStatisticsRepository() {
        return new AdminStatisticsMongoRepository(provider.get());
    }

    @Override
    protected TagRepository getKeywordRepository() {
        return new TagMongoRepository(provider.get());
    }

    @Override
    protected TopicRepository getUsableTopicRepository() {
        return new TopicMongoRepository(provider.get());
    }

    @Override
    protected SessionRepository getSessionRepository() {
        return new SessionMongoRepository(provider.get());
    }

    @Override
    protected RelatedRepository getRelatedRepository() {
        return new RelatedMongoRepository(provider.get());
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

    @Override
    protected MediaRepository getMediaRepository() {
        return new MediaMongoRepository(provider.get());
    }

    private final SessionProvider provider;
}
