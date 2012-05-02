package com.steambeat.repositories;

import com.steambeat.domain.relation.*;
import com.steambeat.domain.subject.Subject;
import org.mongolink.MongoSession;

public class RelationMongoRepository extends BaseMongoRepository<Relation> implements RelationRepository {

    public RelationMongoRepository(final MongoSession mongoSession) {
        super(mongoSession);
    }

    @Override
    public Relation lookUp(final Subject from, final Subject to) {
        return null;
    }
}
