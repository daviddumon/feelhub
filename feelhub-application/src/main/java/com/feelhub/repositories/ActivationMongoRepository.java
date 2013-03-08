package com.feelhub.repositories;

import com.feelhub.domain.user.activation.*;
import org.mongolink.MongoSession;

public class ActivationMongoRepository extends BaseMongoRepository<Activation> implements ActivationRepository {

    public ActivationMongoRepository(final MongoSession mongoSession) {
        super(mongoSession);
    }
}
