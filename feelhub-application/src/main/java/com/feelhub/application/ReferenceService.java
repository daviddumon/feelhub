package com.feelhub.application;

import com.feelhub.domain.reference.*;
import com.feelhub.repositories.Repositories;
import com.google.inject.Inject;

import java.util.UUID;

public class ReferenceService {

    @Inject
    public ReferenceService(final ReferenceFactory referenceFactory) {
        this.referenceFactory = referenceFactory;
    }

    public Reference lookUp(final UUID id) {
        return Repositories.references().getActive(id);
    }

    public Reference newReference() {
        final Reference reference = referenceFactory.createReference();
        Repositories.references().add(reference);
        return reference;
    }

    private final ReferenceFactory referenceFactory;
}
