package com.feelhub.domain.topic.unusable;

import com.feelhub.domain.topic.Topic;

import java.util.UUID;

public class WorldTopic extends Topic {

    //mongolink
    protected WorldTopic() {

    }

    public WorldTopic(final UUID id) {
        super(id);
    }
}
