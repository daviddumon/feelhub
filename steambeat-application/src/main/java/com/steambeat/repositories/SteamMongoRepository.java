package com.steambeat.repositories;

import com.steambeat.domain.Repository;
import com.steambeat.domain.subject.steam.Steam;
import org.mongolink.MongoSession;

public class SteamMongoRepository extends BaseMongoRepository<Steam> implements Repository<Steam> {

    public SteamMongoRepository(final MongoSession mongoSession) {
        super(mongoSession);
    }
}
