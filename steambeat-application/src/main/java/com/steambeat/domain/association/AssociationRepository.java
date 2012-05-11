package com.steambeat.domain.association;

import com.steambeat.domain.Repository;

public interface AssociationRepository extends Repository<Association> {

    @SuppressWarnings("unchecked")
    public Association forIdentifier(final Identifier identifier);
}
