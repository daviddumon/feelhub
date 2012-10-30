package com.feelhub.repositories;

import com.feelhub.domain.reference.*;
import org.mongolink.MongoSession;

public class ReferenceMongoRepository extends BaseMongoRepository<Reference> implements ReferenceRepository {

    public ReferenceMongoRepository(final MongoSession mongoSession) {
        super(mongoSession);
    }

    @Override
    public Reference getActive(Object id) {
        Reference reference = session.get(id, getPersistentType());
        while (!reference.isActive() && !reference.getCurrentReferenceId().equals(id)) {
            id = reference.getCurrentReferenceId();
            reference = session.get(id, getPersistentType());
        }
        return reference;
    }
}
