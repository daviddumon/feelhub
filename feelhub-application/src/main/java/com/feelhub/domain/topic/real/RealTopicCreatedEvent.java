package com.feelhub.domain.topic.real;

import com.feelhub.domain.eventbus.DomainEvent;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.google.common.base.Objects;

import java.util.UUID;

public class RealTopicCreatedEvent extends DomainEvent {

    public RealTopicCreatedEvent(final UUID topicId, final FeelhubLanguage feelhubLanguage) {
        this.topicId = topicId;
        this.feelhubLanguage = feelhubLanguage;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("Event id", topicId).toString();
    }

    public final UUID topicId;
    public final FeelhubLanguage feelhubLanguage;
}
