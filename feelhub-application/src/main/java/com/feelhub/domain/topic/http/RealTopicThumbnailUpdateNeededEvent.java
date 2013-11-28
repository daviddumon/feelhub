package com.feelhub.domain.topic.http;

import com.feelhub.domain.eventbus.DomainEvent;
import com.google.common.base.Objects;

import java.util.UUID;

public class RealTopicThumbnailUpdateNeededEvent extends DomainEvent {
    public RealTopicThumbnailUpdateNeededEvent(UUID topicId) {
        this.topicId = topicId;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("Topic id", topicId).toString();
    }

    public UUID topicId;
}
