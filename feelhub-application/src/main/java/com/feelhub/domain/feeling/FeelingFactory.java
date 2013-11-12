package com.feelhub.domain.feeling;

import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.topic.Topic;
import com.feelhub.repositories.Repositories;

import java.util.UUID;

public class FeelingFactory {

    public Feeling createFeeling(final UUID userId, final UUID topicId) {
        final Feeling feeling = new Feeling(userId, topicId);
        DomainEventBus.INSTANCE.post(new FeelingCreatedEvent(feeling));
        final Topic currentTopic = Repositories.topics().getCurrentTopic(topicId);
        if (!currentTopic.getHasFeelings()) {
            DomainEventBus.INSTANCE.post(new FirstFeelingCreatedEvent(feeling));
        }
        return feeling;
    }
}
