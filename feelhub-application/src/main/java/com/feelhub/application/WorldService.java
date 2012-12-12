package com.feelhub.application;

import com.feelhub.domain.topic.TopicFactory;
import com.feelhub.domain.topic.world.*;
import com.feelhub.repositories.Repositories;
import com.google.inject.Inject;

public class WorldService {

    @Inject
    public WorldService(final TopicFactory topicFactory) {
        this.topicFactory = topicFactory;
    }

    public WorldTopic lookUpOrCreateWorld() {
        try {
            return lookUp();
        } catch (WorldNotFoundException e) {
            return createWorld();
        }
    }

    private WorldTopic lookUp() {
        final WorldTopic worldTopic = Repositories.topics().getWorldTopic();
        if (worldTopic == null) {
            throw new WorldNotFoundException();
        }
        return worldTopic;
    }

    private WorldTopic createWorld() {
        final WorldTopic worldTopic = topicFactory.createWorldTopic();
        Repositories.topics().add(worldTopic);
        return worldTopic;
    }

    private TopicFactory topicFactory;
}
