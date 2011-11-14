package com.steambeat.repositories.mapping;

import com.steambeat.domain.subject.feed.Association;
import fr.bodysplash.mongolink.domain.mapper.EntityMap;

public class AssociationMapping extends EntityMap<Association> {

    public AssociationMapping() {
        super(Association.class);
    }

    @Override
    protected void map() {
        id(element().getUri()).natural();
        property(element().getCanonicalUri());
        property(element().getExpirationDate());
    }
}
