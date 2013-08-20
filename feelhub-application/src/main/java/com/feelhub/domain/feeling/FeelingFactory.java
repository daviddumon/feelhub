package com.feelhub.domain.feeling;

import com.feelhub.domain.eventbus.DomainEventBus;

import java.util.UUID;

public class FeelingFactory {

    public Feeling createFeeling(final UUID userId, final UUID topicId) {
        final Feeling feeling = new Feeling(userId, topicId);
        DomainEventBus.INSTANCE.post(new FeelingCreatedEvent(feeling));
        return feeling;
    }
}
