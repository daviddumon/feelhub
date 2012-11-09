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

public abstract class Repositories {

    public static void initialize(final Repositories repositories) {
        Repositories.soleInstance = repositories;
    }

    public static FeelingRepository feelings() {
        return Repositories.soleInstance.getFeelingRepository();
    }

    public static RelationRepository relations() {
        return Repositories.soleInstance.getRelationRepository();
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
        return Repositories.soleInstance.getTopicRepository();
    }

    public static KeywordRepository keywords() {
        return Repositories.soleInstance.getKeywordRepository();
    }

    public static IllustrationRepository illustrations() {
        return Repositories.soleInstance.getIllustrationRepository();
    }

    public static AlchemyEntityRepository alchemyEntities() {
        return Repositories.soleInstance.getAlchemyEntityRepository();
    }

    public static AlchemyAnalysisRepository alchemyAnalysis() {
        return Repositories.soleInstance.getAlchemyAnalysisRepository();
    }

    protected abstract AlchemyAnalysisRepository getAlchemyAnalysisRepository();

    protected abstract AlchemyEntityRepository getAlchemyEntityRepository();

    protected abstract IllustrationRepository getIllustrationRepository();

    protected abstract KeywordRepository getKeywordRepository();

    protected abstract TopicRepository getTopicRepository();

    protected abstract SessionRepository getSessionRepository();

    protected abstract RelationRepository getRelationRepository();

    protected abstract FeelingRepository getFeelingRepository();

    protected abstract StatisticsRepository getStatisticsRepository();

    protected abstract UserRepository getUserRepository();

    private static Repositories soleInstance;
}