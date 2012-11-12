package com.feelhub.repositories;

import com.feelhub.domain.user.*;
import org.mongolink.MongoSession;

public class ActivationMongoRepository extends BaseMongoRepository<Activation> implements ActivationRepository {

    public ActivationMongoRepository(MongoSession mongoSession) {
        super(mongoSession);
    }
}
