package com.feelhub.repositories.mapping;

import com.feelhub.domain.related.Related;
import org.mongolink.domain.mapper.AggregateMap;

public class RelatedMapping extends AggregateMap<Related> {

    public RelatedMapping() {
        super(Related.class);
    }

    @Override
    public void map() {
        id().onProperty(element().getId()).natural();
        property().onProperty(element().getFromId());
        property().onProperty(element().getToId());
        property().onProperty(element().getWeight());
        property().onProperty(element().getCreationDate());
        property().onProperty(element().getLastModificationDate());
    }
}
