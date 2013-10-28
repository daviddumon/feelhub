package com.feelhub.repositories.mapping;

import com.feelhub.domain.topic.Thumbnail;
import org.mongolink.domain.mapper.ComponentMap;

public class ThumbnailMapping extends ComponentMap<Thumbnail> {

    public ThumbnailMapping() {
        super(Thumbnail.class);
    }

    @Override
    public void map() {
        property().onProperty(element().getOrigin());
        property().onProperty(element().getCloudinary());
    }
}
