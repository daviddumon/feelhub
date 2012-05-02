package com.steambeat.test.fakeRepositories;

import com.steambeat.domain.Repository;
import com.steambeat.domain.association.Association;
import com.steambeat.domain.opinion.Opinion;
import com.steambeat.domain.relation.Relation;
import com.steambeat.domain.statistics.StatisticsRepository;
import com.steambeat.repositories.Repositories;

public class FakeMongoRepositories extends Repositories {

    @Override
    protected FakeSubjectMongoRepository getSubjectRepository() {
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

    private final FakeSubjectMongoRepository subjectRepository = new FakeSubjectMongoRepository();
    private final Repository<Association> associationRepository = new FakeAssociationRepository();
    private final Repository<Opinion> opinionRepository = new FakeOpinionRepository();
    private final Repository<Relation> relationFakeRepository = new FakeRelationRepository();
    private final FakeStatisticsRepository statisticsRepository = new FakeStatisticsRepository();
}
