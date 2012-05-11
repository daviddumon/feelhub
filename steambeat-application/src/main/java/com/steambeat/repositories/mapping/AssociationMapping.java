package com.steambeat.repositories.mapping;

import com.steambeat.domain.association.Association;
import org.mongolink.domain.mapper.EntityMap;

public class AssociationMapping extends EntityMap<Association> {

    public AssociationMapping() {
        super(Association.class);
    }

    @Override
    protected void map() {
        id(element().getId()).natural();
        property(element().getIdentifier());
        property(element().getSubjectId());
        property(element().getLanguage());
    }
}
