package com.feelhub.domain.topic;

import com.feelhub.domain.BaseEntity;

import java.util.UUID;

public class Topic extends BaseEntity {

    //mongolink constructor do not delete!
    protected Topic() {
    }

    public Topic(final UUID id) {
        this.id = id;
        this.active = true;
    }

    @Override
    public UUID getId() {
        return id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(final boolean active) {
        this.active = active;
    }

    public UUID getCurrentTopicId() {
        return currentTopicId;
    }

    public void setCurrentTopicId(final UUID currentId) {
        this.currentTopicId = currentId;
    }

    private UUID id;
    private boolean active;
    private UUID currentTopicId;
}
