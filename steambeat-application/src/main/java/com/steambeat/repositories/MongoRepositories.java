package com.steambeat.repositories;

import com.steambeat.domain.Repository;
import com.steambeat.domain.analytics.Relation;
import com.steambeat.domain.association.Association;
import com.steambeat.domain.opinion.Opinion;
import com.steambeat.domain.statistics.StatisticsRepository;

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
    protected Repository<Relation> getRelationRepository() {
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
    protected Repository<Association> getAssociationRepository() {
        return new AssociationMongoRepository(provider.get());
    }

    private final SessionProvider provider;
}
