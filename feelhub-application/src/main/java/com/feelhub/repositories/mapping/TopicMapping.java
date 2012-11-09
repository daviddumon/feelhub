package com.feelhub.repositories.mapping;

import com.feelhub.domain.topic.Topic;
import org.mongolink.domain.mapper.EntityMap;

public class TopicMapping extends EntityMap<Topic> {

    public TopicMapping() {
        super(Topic.class);
    }

    @Override
    protected void map() {
        id(element().getId()).natural();
        property(element().isActive());
        property(element().getCurrentTopicId());
        property(element().getCreationDate());
        property(element().getLastModificationDate());
    }
}