package com.steambeat.domain.reference;

import java.util.UUID;

public class ReferenceFactory {

    public Reference createReference() {
        final UUID id = UUID.randomUUID();
        final Reference reference = new Reference(id);
        return reference;
    }
}
