package com.feelhub.repositories.mapping;

import com.feelhub.domain.tag.Tag;
import org.mongolink.domain.mapper.AggregateMap;

public class TagMapping extends AggregateMap<Tag> {

    public TagMapping() {
        super(Tag.class);
    }

    @Override
    public void map() {
        id().onProperty(element().getId()).natural();
        collection().onProperty(element().getTopicIds());
    }
}
