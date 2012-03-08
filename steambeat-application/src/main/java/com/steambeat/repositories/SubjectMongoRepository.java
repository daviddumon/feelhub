package com.steambeat.repositories;

import com.steambeat.domain.subject.Subject;
import org.mongolink.MongoSession;

public class SubjectMongoRepository extends BaseMongoRepository<Subject> {

    public SubjectMongoRepository(final MongoSession mongoSession) {
        super(mongoSession);
    }
}
