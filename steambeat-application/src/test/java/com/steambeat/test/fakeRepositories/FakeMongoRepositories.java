package com.steambeat.test.fakeRepositories;

import com.steambeat.domain.Repository;
import com.steambeat.domain.association.AssociationRepository;
import com.steambeat.domain.opinion.Opinion;
import com.steambeat.domain.relation.RelationRepository;
import com.steambeat.domain.session.Session;
import com.steambeat.domain.statistics.StatisticsRepository;
import com.steambeat.domain.user.User;
import com.steambeat.repositories.Repositories;

public class FakeMongoRepositories extends Repositories {

    @Override
    protected Repository<Session> getSessionRepository() {
        return sessionRepository;
    }

    @Override
    protected FakeSubjectMongoRepository getSubjectRepository() {
        return subjectRepository;
    }

    @Override
    protected RelationRepository getRelationRepository() {
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
    protected AssociationRepository getAssociationRepository() {
        return associationRepository;
    }

    @Override
    protected Repository<User> getUserRepository() {
        return userRepository;
    }

    private final FakeSubjectMongoRepository subjectRepository = new FakeSubjectMongoRepository();
    private final FakeAssociationRepository associationRepository = new FakeAssociationRepository();
    private final Repository<Opinion> opinionRepository = new FakeOpinionRepository();
    private final RelationRepository relationFakeRepository = new FakeRelationRepository();
    private final FakeStatisticsRepository statisticsRepository = new FakeStatisticsRepository();
    private final FakeUserRepository userRepository = new FakeUserRepository();
    private final FakeSessionRepository sessionRepository = new FakeSessionRepository();
}
