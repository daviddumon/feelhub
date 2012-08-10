package com.steambeat.repositories;

import com.steambeat.domain.keyword.Keyword;
import org.mongolink.MongoSession;

public class KeywordMongoRepository extends BaseMongoRepository<Keyword> {

    public KeywordMongoRepository(final MongoSession mongoSession) {
        super(mongoSession);
    }
}
