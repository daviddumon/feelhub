package com.feelhub.domain.topic.world;

import com.feelhub.application.WorldService;
import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.feeling.*;
import com.feelhub.repositories.SessionProvider;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;

public class WorldListener {

    @Inject
    public WorldListener(final WorldService worldService) {
        this.worldService = worldService;
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    public void handle(final SentimentStatisticsEvent sentimentStatisticsEvent) {
        final WorldTopic worldTopic = worldService.lookUpOrCreateWorld();
        final Sentiment sentiment = new Sentiment(worldTopic.getId(), sentimentStatisticsEvent.getSentiment().getSentimentValue());
        final WorldStatisticsEvent worldStatisticsEvent = new WorldStatisticsEvent(sentiment);
        DomainEventBus.INSTANCE.post(worldStatisticsEvent);
    }

    private final WorldService worldService;
}
