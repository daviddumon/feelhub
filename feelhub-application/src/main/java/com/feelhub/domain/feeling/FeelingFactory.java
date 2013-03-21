package com.feelhub.domain.feeling;

import com.feelhub.domain.eventbus.DomainEventBus;

import java.util.UUID;

public class FeelingFactory {

    public Feeling createFeeling(final String text, final UUID userId) {
        final Feeling feeling = new Feeling(UUID.randomUUID(), text, userId);
        DomainEventBus.INSTANCE.post(new FeelingCreatedEvent(feeling.getId(), userId));
        return feeling;
    }
}
