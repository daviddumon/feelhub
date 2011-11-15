package com.steambeat.repositories;

import com.steambeat.domain.Repository;
import com.steambeat.domain.subject.webpage.WebPage;
import fr.bodysplash.mongolink.MongoSession;

public class WebPageMongoRepository extends BaseMongoRepository<WebPage> implements Repository<WebPage> {

    public WebPageMongoRepository(final MongoSession mongoSession) {
        super(mongoSession);
    }
}
