package com.feelhub.domain.meta;

import com.feelhub.domain.BaseEntity;

import java.util.UUID;

public class Illustration extends BaseEntity {

    //do not delete mongolink constructor
    public Illustration() {
    }

    public Illustration(final UUID topicId, final String link) {
        this.id = UUID.randomUUID();
        this.topicId = topicId;
        this.link = link;
    }

    @Override
    public Object getId() {
        return id;
    }

    public String getLink() {
        return link;
    }

    public UUID getTopicId() {
        return topicId;
    }

    public void setTopicId(final UUID topicId) {
        this.topicId = topicId;
    }

    private UUID id;
    private String link;
    private UUID topicId;
}
