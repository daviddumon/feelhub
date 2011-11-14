package com.steambeat.repositories;

import com.steambeat.domain.Repository;
import com.steambeat.domain.opinion.Opinion;
import com.steambeat.domain.statistics.StatisticsRepository;
import com.steambeat.domain.subject.feed.*;

public abstract class Repositories {

    public static void initialize(final Repositories repositories) {
        Repositories.soleInstance = repositories;
    }

    public static Repository<Opinion> opinions() {
        return Repositories.soleInstance.getOpinionRepository();
    }

    public static Repository<Feed> feeds() {
        return Repositories.soleInstance.getFeedRepository();
    }

    public static Repository<Association> associations() {
        return Repositories.soleInstance.getAssociationRepository();
    }

    public static StatisticsRepository statistics() {
        return Repositories.soleInstance.getStatisticsRepository();
    }

    protected abstract Repository<Opinion> getOpinionRepository();

    protected abstract StatisticsRepository getStatisticsRepository();

    protected abstract Repository<Association> getAssociationRepository();

    protected abstract Repository<Feed> getFeedRepository();

    private static Repositories soleInstance;
}