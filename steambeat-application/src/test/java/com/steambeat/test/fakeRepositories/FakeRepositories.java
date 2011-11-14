package com.steambeat.test.fakeRepositories;

import com.steambeat.domain.Repository;
import com.steambeat.domain.opinion.Opinion;
import com.steambeat.domain.statistics.StatisticsRepository;
import com.steambeat.repositories.Repositories;
import com.steambeat.domain.subject.feed.*;

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
    protected Repository<Feed> getFeedRepository() {
        return feedRepository;
    }

    private Repository<Feed> feedRepository = new FakeFeedRepository();
    private Repository<Association> associationRepository = new FakeAssociationRepository();
    private FakeStatisticsRepository statisticsRepository = new FakeStatisticsRepository();
    private Repository<Opinion> opinionRepository = new FakeOpinionRepository();
}
