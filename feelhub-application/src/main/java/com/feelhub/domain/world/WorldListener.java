package com.feelhub.domain.world;

import com.feelhub.application.KeywordService;
import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.feeling.*;
import com.feelhub.domain.keyword.Keyword;
import com.feelhub.repositories.SessionProvider;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;

public class WorldListener {

    @Inject
    public WorldListener(final SessionProvider sessionProvider, final KeywordService keywordService) {
        this.sessionProvider = sessionProvider;
        this.keywordService = keywordService;
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    public void handle(final SentimentStatisticsEvent sentimentStatisticsEvent) {
        sessionProvider.start();
        final Keyword world = keywordService.lookUpOrCreateWorld();
        final Sentiment sentiment = new Sentiment(world.getReferenceId(), sentimentStatisticsEvent.getSentiment().getSentimentValue());
        final WorldStatisticsEvent worldStatisticsEvent = new WorldStatisticsEvent(sentiment);
        DomainEventBus.INSTANCE.post(worldStatisticsEvent);
        sessionProvider.stop();
    }

    private final SessionProvider sessionProvider;
    private final KeywordService keywordService;
}
