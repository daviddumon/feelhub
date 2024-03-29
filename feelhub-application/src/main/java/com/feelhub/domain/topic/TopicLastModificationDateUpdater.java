package com.feelhub.domain.topic;

import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.feeling.*;
import com.feelhub.repositories.Repositories;
import com.google.common.eventbus.*;

public class TopicLastModificationDateUpdater {

    public TopicLastModificationDateUpdater() {
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    @AllowConcurrentEvents
    public void onFeelingCreated(final FeelingCreatedEvent event) {
        final Feeling feeling = Repositories.feelings().get(event.getFeeling().getId());
        Repositories.topics().get(feeling.getTopicId()).setLastModificationDate(feeling.getCreationDate());
    }
}
