package com.feelhub.domain.topic;

import com.feelhub.domain.Repository;

import java.util.UUID;

public interface TopicRepository extends Repository<Topic> {

    Topic world();

    Topic getCurrentTopic(final UUID id);
}
