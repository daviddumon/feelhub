package com.feelhub.domain.keyword.world;

import com.feelhub.application.WorldService;
import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.feeling.*;
import com.feelhub.domain.keyword.Keyword;
import com.feelhub.repositories.SessionProvider;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;

public class WorldListener {

    @Inject
    public WorldListener(final SessionProvider sessionProvider, final WorldService worldService) {
        this.sessionProvider = sessionProvider;
        this.worldService = worldService;
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    public void handle(final SentimentStatisticsEvent sentimentStatisticsEvent) {
        sessionProvider.start();
        final Keyword world = worldService.lookUpOrCreateWorld();
        final Sentiment sentiment = new Sentiment(world.getTopicId(), sentimentStatisticsEvent.getSentiment().getSentimentValue());
        final WorldStatisticsEvent worldStatisticsEvent = new WorldStatisticsEvent(sentiment);
        DomainEventBus.INSTANCE.post(worldStatisticsEvent);
        sessionProvider.stop();
    }

    private final SessionProvider sessionProvider;
    private WorldService worldService;
}
