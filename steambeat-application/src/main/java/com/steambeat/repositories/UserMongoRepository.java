package com.steambeat.repositories;

import com.steambeat.domain.user.User;
import org.mongolink.MongoSession;

public class UserMongoRepository extends BaseMongoRepository<User> {

    public UserMongoRepository(final MongoSession mongoSession) {
        super(mongoSession);
    }
}
