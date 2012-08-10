package com.steambeat.test.fakeRepositories;

import com.steambeat.domain.Repository;
import com.steambeat.domain.association.AssociationRepository;
import com.steambeat.domain.keyword.Keyword;
import com.steambeat.domain.opinion.Opinion;
import com.steambeat.domain.relation.RelationRepository;
import com.steambeat.domain.session.Session;
import com.steambeat.domain.statistics.StatisticsRepository;
import com.steambeat.domain.topic.Topic;
import com.steambeat.domain.user.UserRepository;
import com.steambeat.repositories.Repositories;

public class FakeMongoRepositories extends Repositories {

    @Override
    protected Repository<Keyword> getKeywordRepository() {
        return keywordRepository;
    }

    @Override
    protected Repository<Topic> getTopicRepository() {
        return topicRepository;
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
    protected AssociationRepository getAssociationRepository() {
        return associationRepository;
    }

    @Override
    protected UserRepository getUserRepository() {
        return userRepository;
    }

    private final FakeAssociationRepository associationRepository = new FakeAssociationRepository();
    private final Repository<Opinion> opinionRepository = new FakeOpinionRepository();
    private final RelationRepository relationFakeRepository = new FakeRelationRepository();
    private final FakeStatisticsRepository statisticsRepository = new FakeStatisticsRepository();
    private final FakeUserRepository userRepository = new FakeUserRepository();
    private final FakeSessionRepository sessionRepository = new FakeSessionRepository();
    private final FakeTopicRepository topicRepository = new FakeTopicRepository();
    private final Repository<Keyword> keywordRepository = new FakeKeywordRepository();
}
