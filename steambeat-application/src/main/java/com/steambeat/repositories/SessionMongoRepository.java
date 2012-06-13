package com.steambeat.repositories;

import com.steambeat.domain.session.Session;
import org.mongolink.MongoSession;

public class SessionMongoRepository extends BaseMongoRepository<Session> {

    public SessionMongoRepository(final MongoSession mongoSession) {
        super(mongoSession);
    }
}
