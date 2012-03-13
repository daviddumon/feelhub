package com.steambeat.repositories;

import com.steambeat.domain.analytics.Relation;
import org.mongolink.MongoSession;

public class RelationMongoRepository extends BaseMongoRepository<Relation> {

    public RelationMongoRepository(final MongoSession mongoSession) {
        super(mongoSession);
    }
}
