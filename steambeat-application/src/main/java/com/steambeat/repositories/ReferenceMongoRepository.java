package com.steambeat.repositories;

import com.steambeat.domain.reference.Reference;
import org.mongolink.MongoSession;

public class ReferenceMongoRepository extends BaseMongoRepository<Reference> {

    public ReferenceMongoRepository(final MongoSession mongoSession) {
        super(mongoSession);
    }
}
