package com.steambeat.repositories;

import com.steambeat.domain.Repository;
import com.steambeat.domain.analytics.Association;
import org.mongolink.MongoSession;

public class AssociationMongoRepository extends BaseMongoRepository<Association> implements Repository<Association> {

    public AssociationMongoRepository(final MongoSession mongoSession) {
        super(mongoSession);
    }
}
