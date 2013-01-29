package com.feelhub.repositories.mapping;

import com.feelhub.domain.media.Media;
import org.mongolink.domain.mapper.AggregateMap;

public class MediaMapping extends AggregateMap<Media> {

    public MediaMapping() {
        super(Media.class);
    }

    @Override
    protected void map() {
        id(element().getId()).natural();
        property(element().getFromId());
        property(element().getToId());
    }
}
