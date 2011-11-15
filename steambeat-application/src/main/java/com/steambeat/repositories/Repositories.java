package com.steambeat.repositories;

import com.steambeat.domain.Repository;
import com.steambeat.domain.opinion.Opinion;
import com.steambeat.domain.statistics.StatisticsRepository;
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

    public static Repository<Association> associations() {
        return Repositories.soleInstance.getAssociationRepository();
    }

    public static StatisticsRepository statistics() {
        return Repositories.soleInstance.getStatisticsRepository();
    }

    protected abstract Repository<Opinion> getOpinionRepository();

    protected abstract StatisticsRepository getStatisticsRepository();

    protected abstract Repository<Association> getAssociationRepository();

    protected abstract Repository<WebPage> getWebPageRepository();

    private static Repositories soleInstance;
}