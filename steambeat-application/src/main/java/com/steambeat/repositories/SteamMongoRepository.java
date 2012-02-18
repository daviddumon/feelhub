package com.steambeat.repositories;

import com.steambeat.domain.Repository;
import com.steambeat.domain.subject.steam.Steam;
import fr.bodysplash.mongolink.MongoSession;

public class SteamMongoRepository extends BaseMongoRepository<Steam> implements Repository<Steam> {

    public SteamMongoRepository(final MongoSession mongoSession) {
        super(mongoSession);
    }
}
