package com.steambeat.repositories;

import com.steambeat.domain.relation.Relation;
import org.mongolink.MongoSession;

public class RelationMongoRepository extends BaseMongoRepository<Relation> {

    public RelationMongoRepository(final MongoSession mongoSession) {
        super(mongoSession);
    }
}
