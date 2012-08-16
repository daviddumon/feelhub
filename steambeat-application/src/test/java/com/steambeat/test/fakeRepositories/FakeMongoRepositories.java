package com.steambeat.test.fakeRepositories;

import com.steambeat.domain.Repository;
import com.steambeat.domain.keyword.KeywordRepository;
import com.steambeat.domain.opinion.Opinion;
import com.steambeat.domain.relation.RelationRepository;
import com.steambeat.domain.session.Session;
import com.steambeat.domain.statistics.StatisticsRepository;
import com.steambeat.domain.reference.Reference;
import com.steambeat.domain.user.UserRepository;
import com.steambeat.repositories.Repositories;

public class FakeMongoRepositories extends Repositories {

    @Override
    protected KeywordRepository getKeywordRepository() {
        return keywordRepository;
    }

    @Override
    protected Repository<Reference> getReferenceRepository() {
        return referenceRepository;
    }

    @Override
    protected Repository<Session> getSessionRepository() {
        return sessionRepository;
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
    protected UserRepository getUserRepository() {
        return userRepository;
    }

    private final Repository<Opinion> opinionRepository = new FakeOpinionRepository();
    private final RelationRepository relationFakeRepository = new FakeRelationRepository();
    private final FakeStatisticsRepository statisticsRepository = new FakeStatisticsRepository();
    private final FakeUserRepository userRepository = new FakeUserRepository();
    private final FakeSessionRepository sessionRepository = new FakeSessionRepository();
    private final FakeReferenceRepository referenceRepository = new FakeReferenceRepository();
    private final FakeKeywordRepository keywordRepository = new FakeKeywordRepository();
}
