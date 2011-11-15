package com.steambeat.test.fakeRepositories;

import com.steambeat.domain.Repository;
import com.steambeat.domain.opinion.Opinion;
import com.steambeat.domain.statistics.StatisticsRepository;
import com.steambeat.domain.subject.webpage.*;
import com.steambeat.repositories.Repositories;

public class FakeRepositories extends Repositories {

    @Override
    protected Repository<Opinion> getOpinionRepository() {
        return opinionRepository;
    }

    @Override
    protected StatisticsRepository getStatisticsRepository() {
        return statisticsRepository;
    }

    @Override
    protected Repository<Association> getAssociationRepository() {
        return associationRepository;
    }

    @Override
    protected Repository<WebPage> getWebPageRepository() {
        return webPageRepository;
    }

    private final Repository<WebPage> webPageRepository = new FakeWebPageRepository();
    private final Repository<Association> associationRepository = new FakeAssociationRepository();
    private final FakeStatisticsRepository statisticsRepository = new FakeStatisticsRepository();
    private final Repository<Opinion> opinionRepository = new FakeOpinionRepository();
}
