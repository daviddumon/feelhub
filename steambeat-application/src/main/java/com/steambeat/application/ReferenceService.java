package com.steambeat.application;

import com.steambeat.domain.reference.Reference;
import com.steambeat.repositories.Repositories;

import java.util.UUID;

public class ReferenceService {

    public Reference lookUp(final UUID id) {
        return Repositories.references().get(id);
    }
}
