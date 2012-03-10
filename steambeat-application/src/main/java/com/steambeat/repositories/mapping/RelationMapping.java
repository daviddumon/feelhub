package com.steambeat.repositories.mapping;

import com.steambeat.domain.subject.Relation;
import org.mongolink.domain.mapper.EntityMap;

public class RelationMapping extends EntityMap<Relation> {

    public RelationMapping() {
        super(Relation.class);
    }

    @Override
    protected void map() {
        id(element().getId()).auto();
        property(element().getLeftId());
        property(element().getRightId());
    }
}
