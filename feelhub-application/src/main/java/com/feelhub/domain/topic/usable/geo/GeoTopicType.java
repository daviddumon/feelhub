package com.feelhub.domain.topic.usable.geo;

import com.feelhub.domain.topic.TopicType;

public enum GeoTopicType implements TopicType {

    Place(false),
    Coords(true),
    Other(false);

    private GeoTopicType(final boolean tagUniqueness) {
        this.tagUniqueness = tagUniqueness;
    }

    @Override
    public boolean hasTagUniqueness() {
        return tagUniqueness;
    }

    private final boolean tagUniqueness;
}
