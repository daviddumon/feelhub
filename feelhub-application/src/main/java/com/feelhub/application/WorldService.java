package com.feelhub.application;

import com.feelhub.domain.topic.*;
import com.feelhub.domain.world.WorldNotFoundException;
import com.feelhub.repositories.Repositories;
import com.google.inject.Inject;

public class WorldService {

    @Inject
    public WorldService(final TopicFactory topicFactory) {
        this.topicFactory = topicFactory;
    }

    public Topic lookUpOrCreateWorld() {
        try {
            return lookUp();
        } catch (WorldNotFoundException e) {
            return createWorld();
        }
    }

    private Topic lookUp() {
        final Topic world = Repositories.topics().world();
        if (world == null) {
            throw new WorldNotFoundException();
        }
        return world;
    }

    private Topic createWorld() {
        final Topic world = topicFactory.createWorld();
        Repositories.topics().add(world);
        return world;
    }

    private TopicFactory topicFactory;
}
