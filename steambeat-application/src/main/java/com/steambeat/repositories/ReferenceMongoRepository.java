package com.steambeat.repositories;

import com.steambeat.domain.reference.Reference;
import org.mongolink.MongoSession;

import java.util.UUID;

public class ReferenceMongoRepository extends BaseMongoRepository<Reference> {

    public ReferenceMongoRepository(final MongoSession mongoSession) {
        super(mongoSession);
    }

    @Override
    public Reference get(final Object id) {
        Reference reference = session.get(id, getPersistentType());
        while (!reference.isActive()) {
            final UUID currentReferenceId = reference.getCurrentReferenceId();
            reference = session.get(currentReferenceId, getPersistentType());
        }
        return reference;
    }
}
