package com.steambeat.repositories;

import com.steambeat.domain.subject.Relation;
import org.mongolink.MongoSession;

public class RelationMongoRepository extends BaseMongoRepository<Relation> {

    public RelationMongoRepository(final MongoSession mongoSession) {
        super(mongoSession);
    }
}
