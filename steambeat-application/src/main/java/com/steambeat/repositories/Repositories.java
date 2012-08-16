package com.steambeat.repositories;

import com.steambeat.domain.Repository;
import com.steambeat.domain.keyword.KeywordRepository;
import com.steambeat.domain.opinion.Opinion;
import com.steambeat.domain.relation.RelationRepository;
import com.steambeat.domain.session.Session;
import com.steambeat.domain.statistics.StatisticsRepository;
import com.steambeat.domain.reference.Reference;
import com.steambeat.domain.user.UserRepository;

public abstract class Repositories {

    public static void initialize(final Repositories repositories) {
        Repositories.soleInstance = repositories;
    }

    public static Repository<Opinion> opinions() {
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

    public static Repository<Session> sessions() {
        return Repositories.soleInstance.getSessionRepository();
    }

    public static Repository<Reference> references() {
        return Repositories.soleInstance.getReferenceRepository();
    }

    public static KeywordRepository keywords() {
        return Repositories.soleInstance.getKeywordRepository();
    }

    protected abstract KeywordRepository getKeywordRepository();

    protected abstract Repository<Reference> getReferenceRepository();

    protected abstract Repository<Session> getSessionRepository();

    protected abstract RelationRepository getRelationRepository();

    protected abstract Repository<Opinion> getOpinionRepository();

    protected abstract StatisticsRepository getStatisticsRepository();

    protected abstract UserRepository getUserRepository();

    private static Repositories soleInstance;
}