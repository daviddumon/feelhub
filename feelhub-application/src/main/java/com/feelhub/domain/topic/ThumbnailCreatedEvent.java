package com.feelhub.domain.topic;

import com.feelhub.domain.eventbus.DomainEvent;

import java.math.BigDecimal;
import java.util.UUID;

public class ThumbnailCreatedEvent extends DomainEvent {

    @Override
    public String toString() {
        return "";
    }

    public UUID getTopicId() {
        return topicId;
    }

    public void setTopicId(final UUID topicId) {
        this.topicId = topicId;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(final Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }

    private UUID topicId;
    private Thumbnail thumbnail;
}
