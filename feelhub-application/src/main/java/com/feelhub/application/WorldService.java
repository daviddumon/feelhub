package com.feelhub.application;

import com.feelhub.domain.keyword.KeywordFactory;
import com.feelhub.domain.keyword.world.*;
import com.feelhub.domain.topic.*;
import com.feelhub.repositories.Repositories;
import com.google.inject.Inject;

public class WorldService {

    @Inject
    public WorldService(final TopicFactory topicFactory, final KeywordFactory keywordFactory) {
        this.topicFactory = topicFactory;
        this.keywordFactory = keywordFactory;
    }

    public World lookUpOrCreateWorld() {
        try {
            return lookUp();
        } catch (WorldNotFound e) {
            return createWorld();
        }
    }

    private World lookUp() {
        final World world = Repositories.keywords().world();
        if (world == null) {
            throw new WorldNotFound();
        }
        return world;
    }

    private World createWorld() {
        final Topic topic = topicFactory.createTopic();
        final World world = keywordFactory.createWorld(topic.getId());
        Repositories.keywords().add(world);
        return world;
    }

    private TopicFactory topicFactory;
    private KeywordFactory keywordFactory;
}
