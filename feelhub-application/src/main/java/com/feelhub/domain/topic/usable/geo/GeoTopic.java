package com.feelhub.domain.topic.usable.geo;

import com.feelhub.domain.topic.usable.UsableTopic;

import java.util.UUID;

public class GeoTopic extends UsableTopic {

    //mongolink
    protected GeoTopic() {

    }

    public GeoTopic(final UUID id, final GeoTopicType type) {
        super(id, type);
    }
}
