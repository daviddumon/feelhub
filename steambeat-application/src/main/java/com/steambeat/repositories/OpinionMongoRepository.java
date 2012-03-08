package com.steambeat.repositories;

import com.steambeat.domain.opinion.Opinion;
import org.mongolink.MongoSession;

public class OpinionMongoRepository extends BaseMongoRepository<Opinion> {

    public OpinionMongoRepository(final MongoSession mongoSession) {
        super(mongoSession);
    }
}
