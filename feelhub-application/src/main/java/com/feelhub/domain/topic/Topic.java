package com.feelhub.domain.topic;

import com.feelhub.domain.BaseEntity;

import java.util.UUID;

public abstract class Topic extends BaseEntity {

    //mongolink
    protected Topic() {
    }

    public Topic(final UUID id) {
        this.id = id;
        this.currentId = id;
    }

    @Override
    public UUID getId() {
        return id;
    }

    public UUID getCurrentId() {
        return currentId;
    }

    public void changeCurrentId(final UUID currentId) {
        this.currentId = currentId;
        final TopicMerger topicMerger = new TopicMerger();
        topicMerger.merge(this.getCurrentId(), this.getId());
    }

    protected UUID id;
    protected UUID currentId;
}
