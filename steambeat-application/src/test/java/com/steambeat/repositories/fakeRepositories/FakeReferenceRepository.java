package com.steambeat.repositories.fakeRepositories;

import com.steambeat.domain.reference.*;

public class FakeReferenceRepository extends FakeRepository<Reference> implements ReferenceRepository {

    @Override
    public Reference getActive(Object id) {
        Reference reference = get(id);
        while (!reference.isActive() && !reference.getCurrentReferenceId().equals(id)) {
            id = reference.getCurrentReferenceId();
            reference = get(id);
        }
        return reference;
    }
}
