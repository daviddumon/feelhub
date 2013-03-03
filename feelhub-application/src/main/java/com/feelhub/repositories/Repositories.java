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
import com.feelhub.domain.user.*;
import com.feelhub.domain.user.activation.ActivationRepository;

public abstract class Repositories {

    public static void initialize(final Repositories repositories) {
        Repositories.soleInstance = repositories;
    }

    public static FeelingRepository feelings() {
        return Repositories.soleInstance.getFeelingRepository();
    }

    public static RelatedRepository related() {
        return Repositories.soleInstance.getRelatedRepository();
    }

    public static MediaRepository medias() {
        return Repositories.soleInstance.getMediaRepository();
    }

    public static StatisticsRepository statistics() {
        return Repositories.soleInstance.getStatisticsRepository();
    }

    public static UserRepository users() {
        return Repositories.soleInstance.getUserRepository();
    }

    public static SessionRepository sessions() {
        return Repositories.soleInstance.getSessionRepository();
    }

    public static TopicRepository topics() {
        return Repositories.soleInstance.getUsableTopicRepository();
    }

    public static TagRepository tags() {
        return Repositories.soleInstance.getKeywordRepository();
    }

    public static AlchemyEntityRepository alchemyEntities() {
        return Repositories.soleInstance.getAlchemyEntityRepository();
    }

    public static AlchemyAnalysisRepository alchemyAnalysis() {
        return Repositories.soleInstance.getAlchemyAnalysisRepository();
    }

    public static AdminStatisticsRepository adminStatistics() {
        return Repositories.soleInstance.getAdminStatisticsRepository();
    }

    public static ActivationRepository activation() {
        return Repositories.soleInstance.getActivationRepository();
    }

    protected abstract ActivationRepository getActivationRepository();

    protected abstract AlchemyAnalysisRepository getAlchemyAnalysisRepository();

    protected abstract AlchemyEntityRepository getAlchemyEntityRepository();

    protected abstract AdminStatisticsRepository getAdminStatisticsRepository();

    protected abstract TagRepository getKeywordRepository();

    protected abstract TopicRepository getUsableTopicRepository();

    protected abstract SessionRepository getSessionRepository();

    protected abstract RelatedRepository getRelatedRepository();

    protected abstract FeelingRepository getFeelingRepository();

    protected abstract StatisticsRepository getStatisticsRepository();

    protected abstract UserRepository getUserRepository();

    protected abstract MediaRepository getMediaRepository();

    private static Repositories soleInstance;

}