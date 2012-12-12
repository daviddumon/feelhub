package com.feelhub.domain.topic.geo;

import com.feelhub.domain.topic.*;

import java.util.UUID;

public class GeoTopic extends Topic {

    //mongolink
    protected GeoTopic() {

    }

    @Override
    public TopicType getType() {
        return GeoTopicType.valueOf(typeValue);
    }

    public String getTypeValue() {
        return typeValue;
    }

    public void setType(final GeoTopicType type) {
        this.typeValue = type.toString();
    }

    public GeoTopic(final UUID id, final GeoTopicType type) {
        super(id);
        this.typeValue = type.toString();
    }

    private String typeValue;
}
