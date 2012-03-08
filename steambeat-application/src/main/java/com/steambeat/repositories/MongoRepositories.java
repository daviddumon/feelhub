package com.steambeat.repositories;

import com.steambeat.domain.Repository;
import com.steambeat.domain.analytics.Association;
import com.steambeat.domain.opinion.Opinion;
import com.steambeat.domain.statistics.StatisticsRepository;
import com.steambeat.domain.subject.*;

import javax.inject.Inject;

public class MongoRepositories extends Repositories {

    @Inject
    public MongoRepositories(final SessionProvider provider) {
        this.provider = provider;
    }

    @Override
    protected Repository<Subject> getSubjectRepository() {
        return new SubjectMongoRepository(provider.get());
    }

    @Override
    protected Repository<Relation> getRelationRepository() {
        return null;  // todo
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
