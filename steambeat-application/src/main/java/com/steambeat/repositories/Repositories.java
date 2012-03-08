package com.steambeat.repositories;

import com.steambeat.domain.Repository;
import com.steambeat.domain.analytics.Association;
import com.steambeat.domain.opinion.Opinion;
import com.steambeat.domain.statistics.StatisticsRepository;
import com.steambeat.domain.subject.*;

public abstract class Repositories {

    public static void initialize(final Repositories repositories) {
        Repositories.soleInstance = repositories;
    }

    public static Repository<Subject> subjects() {
        return Repositories.soleInstance.getSubjectRepository();
    }

    public static Repository<Opinion> opinions() {
        return Repositories.soleInstance.getOpinionRepository();
    }

    public static Repository<Association> associations() {
        return Repositories.soleInstance.getAssociationRepository();
    }

    public static Repository<Relation> relations() {
        return Repositories.soleInstance.getRelationRepository();
    }

    public static StatisticsRepository statistics() {
        return Repositories.soleInstance.getStatisticsRepository();
    }

    protected abstract Repository<Subject> getSubjectRepository();

    protected abstract Repository<Relation> getRelationRepository();

    protected abstract Repository<Opinion> getOpinionRepository();

    protected abstract StatisticsRepository getStatisticsRepository();

    protected abstract Repository<Association> getAssociationRepository();

    private static Repositories soleInstance;
}