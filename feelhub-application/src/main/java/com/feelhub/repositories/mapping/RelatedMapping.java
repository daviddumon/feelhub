package com.feelhub.repositories.mapping;

import com.feelhub.domain.related.Related;
import org.mongolink.domain.mapper.AggregateMap;

public class RelatedMapping extends AggregateMap<Related> {

    public RelatedMapping() {
        super(Related.class);
    }

    @Override
    protected void map() {
        id(element().getId()).natural();
        property(element().getFromId());
        property(element().getToId());
        property(element().getWeight());
    }
}
