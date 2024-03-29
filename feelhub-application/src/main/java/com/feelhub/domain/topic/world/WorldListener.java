package com.feelhub.domain.topic.world;

import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.feeling.*;
import com.feelhub.domain.topic.TopicFactory;
import com.feelhub.repositories.Repositories;
import com.google.common.base.*;
import com.google.common.eventbus.Subscribe;

public class WorldListener {

    public WorldListener() {
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    public void handle(final FeelingCreatedEvent feelingCreatedEvent) {
        final WorldTopic worldTopic = lookUpOrCreateWorld();
        final Feeling worldFeeling = new Feeling(null, worldTopic.getId());
        worldFeeling.setFeelingValue(feelingCreatedEvent.getFeeling().getFeelingValue());
        final WorldStatisticsEvent worldStatisticsEvent = new WorldStatisticsEvent(worldFeeling);
        DomainEventBus.INSTANCE.post(worldStatisticsEvent);
    }

    WorldTopic lookUpOrCreateWorld() {
        return lookUp().or(worldSupplier());
    }

    private Supplier<WorldTopic> worldSupplier() {
        return new Supplier<WorldTopic>() {
            @Override
            public WorldTopic get() {
                return createWorld();
            }
        };
    }

    private Optional<WorldTopic> lookUp() {
        return Optional.fromNullable(Repositories.topics().getWorldTopic());
    }

    private WorldTopic createWorld() {
        final WorldTopic worldTopic = new TopicFactory().createWorldTopic();
        Repositories.topics().add(worldTopic);
        return worldTopic;
    }

}
