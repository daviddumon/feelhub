package com.steambeat.repositories.mapping;

import com.steambeat.domain.reference.Reference;
import org.mongolink.domain.mapper.EntityMap;

public class ReferenceMapping extends EntityMap<Reference> {

    public ReferenceMapping() {
        super(Reference.class);
    }

    @Override
    protected void map() {
        id(element().getId()).natural();
        property(element().isActive());
        property(element().getCreationDate());
        property(element().getLastModificationDate());
    }
}
