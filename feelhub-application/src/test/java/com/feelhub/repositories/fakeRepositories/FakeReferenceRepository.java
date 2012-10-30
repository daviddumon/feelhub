package com.feelhub.repositories.fakeRepositories;

import com.feelhub.domain.reference.*;

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
