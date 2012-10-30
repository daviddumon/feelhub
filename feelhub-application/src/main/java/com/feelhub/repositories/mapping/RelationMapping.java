package com.feelhub.repositories.mapping;

import com.feelhub.domain.relation.Relation;
import org.mongolink.domain.mapper.EntityMap;

public class RelationMapping extends EntityMap<Relation> {

    public RelationMapping() {
        super(Relation.class);
    }

    @Override
    protected void map() {
        id(element().getId()).natural();
        property(element().getFromId());
        property(element().getToId());
        property(element().getWeight());
        property(element().getCreationDate());
        property(element().getLastModificationDate());
    }
}
