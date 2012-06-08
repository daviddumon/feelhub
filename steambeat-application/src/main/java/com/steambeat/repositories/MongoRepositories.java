package com.steambeat.repositories;

import com.steambeat.domain.Repository;
import com.steambeat.domain.association.AssociationRepository;
import com.steambeat.domain.opinion.Opinion;
import com.steambeat.domain.relation.RelationRepository;
import com.steambeat.domain.statistics.StatisticsRepository;
import com.steambeat.domain.user.User;

import javax.inject.Inject;

public class MongoRepositories extends Repositories {

    @Inject
    public MongoRepositories(final SessionProvider provider) {
        this.provider = provider;
    }

    @Override
    protected SubjectMongoRepository getSubjectRepository() {
        return new SubjectMongoRepository(provider.get());
    }

    @Override
    protected RelationRepository getRelationRepository() {
        return new RelationMongoRepository(provider.get());
    }

    @Override
    protected Repository<Opinion> getOpinionRepository() {
        return new OpinionMongoRepository(provider.get());
    }

    @Override
    protected StatisticsRepository getStatisticsRepository() {
        return new StatisticsMongoRepository(provider.get());
    }

    @Override
    protected AssociationRepository getAssociationRepository() {
        return new AssociationMongoRepository(provider.get());
    }

    @Override
    protected Repository<User> getUserRepository() {
        return new UserMongoRepository(provider.get());
    }

    private final SessionProvider provider;
}
