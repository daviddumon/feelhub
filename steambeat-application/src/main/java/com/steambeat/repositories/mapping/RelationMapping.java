package com.steambeat.repositories.mapping;

import com.steambeat.domain.analytics.Relation;
import org.mongolink.domain.mapper.EntityMap;

public class RelationMapping extends EntityMap<Relation> {

    public RelationMapping() {
        super(Relation.class);
    }

    @Override
    protected void map() {
        id(element().getId()).natural();
        property(element().getLeftId());
        property(element().getRightId());
    }
}
