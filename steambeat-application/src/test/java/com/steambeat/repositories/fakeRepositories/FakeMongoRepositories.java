package com.steambeat.repositories.fakeRepositories;

import com.steambeat.domain.Repository;
import com.steambeat.domain.alchemy.AlchemyRepository;
import com.steambeat.domain.illustration.IllustrationRepository;
import com.steambeat.domain.keyword.KeywordRepository;
import com.steambeat.domain.opinion.OpinionRepository;
import com.steambeat.domain.reference.Reference;
import com.steambeat.domain.relation.RelationRepository;
import com.steambeat.domain.session.SessionRepository;
import com.steambeat.domain.statistics.StatisticsRepository;
import com.steambeat.domain.user.UserRepository;
import com.steambeat.repositories.Repositories;

public class FakeMongoRepositories extends Repositories {

    @Override
    protected AlchemyRepository getAlchemyRepository() {
        return alchemyRepository;
    }

    @Override
    protected IllustrationRepository getIllustrationRepository() {
        return illustrationRepository;
    }

    @Override
    protected KeywordRepository getKeywordRepository() {
        return keywordRepository;
    }

    @Override
    protected Repository<Reference> getReferenceRepository() {
        return referenceRepository;
    }

    @Override
    protected SessionRepository getSessionRepository() {
        return sessionRepository;
    }

    @Override
    protected RelationRepository getRelationRepository() {
        return relationFakeRepository;
    }

    @Override
    protected OpinionRepository getOpinionRepository() {
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

    private final FakeOpinionRepository opinionRepository = new FakeOpinionRepository();
    private final RelationRepository relationFakeRepository = new FakeRelationRepository();
    private final FakeStatisticsRepository statisticsRepository = new FakeStatisticsRepository();
    private final FakeUserRepository userRepository = new FakeUserRepository();
    private final FakeSessionRepository sessionRepository = new FakeSessionRepository();
    private final FakeReferenceRepository referenceRepository = new FakeReferenceRepository();
    private final FakeKeywordRepository keywordRepository = new FakeKeywordRepository();
    private final FakeIllustrationRepository illustrationRepository = new FakeIllustrationRepository();
    private final AlchemyRepository alchemyRepository = new FakeAlchemyRepository();
}
