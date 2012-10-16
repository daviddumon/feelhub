package com.steambeat.application;

import com.google.inject.Inject;
import com.steambeat.domain.reference.*;
import com.steambeat.repositories.Repositories;

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
