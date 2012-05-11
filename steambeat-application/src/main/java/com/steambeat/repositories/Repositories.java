package com.steambeat.repositories;

import com.steambeat.domain.Repository;
import com.steambeat.domain.association.AssociationRepository;
import com.steambeat.domain.opinion.Opinion;
import com.steambeat.domain.relation.RelationRepository;
import com.steambeat.domain.statistics.StatisticsRepository;
import com.steambeat.domain.subject.SubjectRepository;

public abstract class Repositories {

    public static void initialize(final Repositories repositories) {
        Repositories.soleInstance = repositories;
    }

    public static SubjectRepository subjects() {
        return Repositories.soleInstance.getSubjectRepository();
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

    protected abstract SubjectRepository getSubjectRepository();

    protected abstract RelationRepository getRelationRepository();

    protected abstract Repository<Opinion> getOpinionRepository();

    protected abstract StatisticsRepository getStatisticsRepository();

    protected abstract AssociationRepository getAssociationRepository();

    private static Repositories soleInstance;
}