package com.steambeat.repositories;

import com.steambeat.domain.opinion.Opinion;
import fr.bodysplash.mongolink.MongoSession;

public class OpinionMongoRepository extends BaseMongoRepository<Opinion> {

    public OpinionMongoRepository(final MongoSession mongoSession) {
        super(mongoSession);
    }
}
