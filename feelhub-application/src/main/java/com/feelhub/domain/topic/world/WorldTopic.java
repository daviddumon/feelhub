package com.feelhub.domain.topic.world;

import com.feelhub.domain.topic.*;

import java.util.UUID;

public class WorldTopic extends Topic {

    //mongolink
    protected WorldTopic() {

    }

    public WorldTopic(final UUID id) {
        super(id);
    }

    @Override
    public TopicType getType() {
        return UnusableTopicTypes.None;
    }
}
