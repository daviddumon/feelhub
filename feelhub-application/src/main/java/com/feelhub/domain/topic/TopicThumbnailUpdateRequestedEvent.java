package com.feelhub.domain.topic;

import com.feelhub.domain.eventbus.DomainEvent;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.google.common.base.Objects;

import java.util.UUID;

public class TopicThumbnailUpdateRequestedEvent extends DomainEvent {
    public TopicThumbnailUpdateRequestedEvent(UUID topicId, FeelhubLanguage feelhubLanguage) {
        this.topicId = topicId;
        this.feelhubLanguage = feelhubLanguage;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("Topic id", topicId).toString();
    }

    public UUID topicId;
    public FeelhubLanguage feelhubLanguage;
}
