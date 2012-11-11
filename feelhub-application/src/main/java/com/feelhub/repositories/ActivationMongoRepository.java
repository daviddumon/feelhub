package com.feelhub.repositories;

import com.feelhub.domain.user.Activation;
import com.feelhub.domain.user.ActivationRepository;
import org.mongolink.MongoSession;

public class ActivationMongoRepository extends BaseMongoRepository<Activation> implements ActivationRepository {

    public ActivationMongoRepository(MongoSession mongoSession) {
        super(mongoSession);
    }
}
