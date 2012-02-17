package com.steambeat.repositories;

import com.steambeat.domain.Repository;
import com.steambeat.domain.analytics.Association;
import com.steambeat.domain.opinion.Opinion;
import com.steambeat.domain.statistics.StatisticsRepository;
import com.steambeat.domain.subject.*;
import com.steambeat.domain.subject.concept.Concept;
import com.steambeat.domain.subject.webpage.*;

public abstract class Repositories {

    public static void initialize(final Repositories repositories) {
        Repositories.soleInstance = repositories;
    }

    public static Repository<Opinion> opinions() {
        return Repositories.soleInstance.getOpinionRepository();
    }

    public static Repository<WebPage> webPages() {
        return Repositories.soleInstance.getWebPageRepository();
    }

    public static Repository<Concept> concepts() {
        return Repositories.soleInstance.getConceptRepository();
    }

    public static Repository<Association> associations() {
        return Repositories.soleInstance.getAssociationRepository();
    }

    public static StatisticsRepository statistics() {
        return Repositories.soleInstance.getStatisticsRepository();
    }

    public static Repository<Subject> subjects() {
        return Repositories.soleInstance.getSubjectRepository();
    }

    public static Repository<Relation> relations() {
        return Repositories.soleInstance.getRelationRepository();
    }

    protected abstract Repository<Subject> getSubjectRepository();

    protected abstract Repository<Relation> getRelationRepository();

    protected abstract Repository<Opinion> getOpinionRepository();

    protected abstract StatisticsRepository getStatisticsRepository();

    protected abstract Repository<Concept> getConceptRepository();

    protected abstract Repository<Association> getAssociationRepository();

    protected abstract Repository<WebPage> getWebPageRepository();

    private static Repositories soleInstance;
}