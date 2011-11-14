package com.steambeat.repositories;

import com.steambeat.domain.Repository;
import com.steambeat.domain.subject.feed.Feed;
import fr.bodysplash.mongolink.MongoSession;

public class FeedMongoRepository extends BaseMongoRepository<Feed> implements Repository<Feed> {

    public FeedMongoRepository(final MongoSession mongoSession) {
        super(mongoSession);
    }
}
