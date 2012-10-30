package com.feelhub.repositories;

import com.feelhub.domain.alchemy.*;
import com.feelhub.domain.illustration.IllustrationRepository;
import com.feelhub.domain.keyword.KeywordRepository;
import com.feelhub.domain.opinion.OpinionRepository;
import com.feelhub.domain.reference.ReferenceRepository;
import com.feelhub.domain.relation.RelationRepository;
import com.feelhub.domain.session.SessionRepository;
import com.feelhub.domain.statistics.StatisticsRepository;
import com.feelhub.domain.user.UserRepository;

public abstract class Repositories {

    public static void initialize(final Repositories repositories) {
        Repositories.soleInstance = repositories;
    }

    public static OpinionRepository opinions() {
        return Repositories.soleInstance.getOpinionRepository();
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

    public static ReferenceRepository references() {
        return Repositories.soleInstance.getReferenceRepository();
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

    protected abstract ReferenceRepository getReferenceRepository();

    protected abstract SessionRepository getSessionRepository();

    protected abstract RelationRepository getRelationRepository();

    protected abstract OpinionRepository getOpinionRepository();

    protected abstract StatisticsRepository getStatisticsRepository();

    protected abstract UserRepository getUserRepository();

    private static Repositories soleInstance;
}