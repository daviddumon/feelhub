package com.feelhub.repositories.mapping;

import com.feelhub.domain.relation.*;
import com.feelhub.domain.relation.media.Media;
import com.feelhub.domain.relation.related.Related;
import org.mongolink.domain.mapper.*;

public class RelationMapping extends AggregateMap<Relation> {

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

        subclass(new SubclassMap<Related>(Related.class) {

            @Override
            protected void map() {
            }
        });

        subclass(new SubclassMap<Media>(Media.class) {

            @Override
            protected void map() {
            }
        });
    }
}
