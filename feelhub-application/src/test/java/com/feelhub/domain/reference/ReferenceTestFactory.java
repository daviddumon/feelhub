package com.feelhub.domain.reference;

import com.feelhub.repositories.Repositories;

import java.util.UUID;

public class ReferenceTestFactory {

    public Reference newReference() {
        final UUID id = UUID.randomUUID();
        final Reference reference = new Reference(id);
        Repositories.references().add(reference);
        return reference;
    }
}
