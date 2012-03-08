package com.steambeat.test.fakeRepositories;

import com.steambeat.domain.Repository;
import com.steambeat.domain.analytics.Association;
import com.steambeat.domain.opinion.Opinion;
import com.steambeat.domain.statistics.StatisticsRepository;
import com.steambeat.domain.subject.*;
import com.steambeat.repositories.Repositories;

public class FakeMongoRepositories extends Repositories {

    @Override
    protected Repository<Subject> getSubjectRepository() {
        return subjectRepository;
    }

    @Override
    protected Repository<Relation> getRelationRepository() {
        return relationFakeRepository;
    }

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

    private final Repository<Subject> subjectRepository = new FakeRepository<Subject>();
    private final Repository<Association> associationRepository = new FakeAssociationRepository();
    private final Repository<Opinion> opinionRepository = new FakeOpinionRepository();
    private final FakeRepository<Relation> relationFakeRepository = new FakeRepository<Relation>();
    private final FakeStatisticsRepository statisticsRepository = new FakeStatisticsRepository();
}
