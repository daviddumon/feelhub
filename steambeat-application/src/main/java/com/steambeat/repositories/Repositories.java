package com.steambeat.repositories;

import com.steambeat.domain.Repository;
import com.steambeat.domain.association.AssociationRepository;
import com.steambeat.domain.keyword.Keyword;
import com.steambeat.domain.opinion.Opinion;
import com.steambeat.domain.relation.RelationRepository;
import com.steambeat.domain.session.Session;
import com.steambeat.domain.statistics.StatisticsRepository;
import com.steambeat.domain.topic.Topic;
import com.steambeat.domain.user.UserRepository;

public abstract class Repositories {

    public static void initialize(final Repositories repositories) {
        Repositories.soleInstance = repositories;
    }

    public static Repository<Opinion> opinions() {
        return Repositories.soleInstance.getOpinionRepository();
    }

    public static AssociationRepository associations() {
        return Repositories.soleInstance.getAssociationRepository();
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

    public static Repository<Topic> topics() {
        return Repositories.soleInstance.getTopicRepository();
    }

    public static Repository<Keyword> keywords() {
        return Repositories.soleInstance.getKeywordRepository();
    }

    protected abstract Repository<Keyword> getKeywordRepository();

    protected abstract Repository<Topic> getTopicRepository();

    protected abstract Repository<Session> getSessionRepository();

    protected abstract RelationRepository getRelationRepository();

    protected abstract Repository<Opinion> getOpinionRepository();

    protected abstract StatisticsRepository getStatisticsRepository();

    protected abstract AssociationRepository getAssociationRepository();

    protected abstract UserRepository getUserRepository();

    private static Repositories soleInstance;
}