package com.steambeat.application;

import com.steambeat.domain.topic.Topic;
import com.steambeat.repositories.Repositories;

import java.util.UUID;

public class TopicService {

    public Topic lookUp(final UUID id) {
        return Repositories.topics().get(id);
    }
}
