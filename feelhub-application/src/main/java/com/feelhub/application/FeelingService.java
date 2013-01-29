package com.feelhub.application;

import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.feeling.*;
import com.feelhub.domain.feeling.analyze.SentimentExtractor;
import com.feelhub.domain.feeling.FeelingRelationBinder;
import com.feelhub.repositories.Repositories;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;

import java.util.List;

public class FeelingService {

    @Inject
    public FeelingService(final FeelingRelationBinder feelingRelationBinder, final SentimentExtractor sentimentExtractor, final FeelingFactory feelingFactory) {
        this.feelingRelationBinder = feelingRelationBinder;
        this.sentimentExtractor = sentimentExtractor;
        this.feelingFactory = feelingFactory;
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    public void handle(final FeelingRequestEvent event) {
        final List<Sentiment> sentiments = sentimentExtractor.analyze(event.getText(), event.getTopicId(), event.getUserId(), event.getLanguage());
        final Feeling feeling = buildFeeling(event, sentiments);
        //feelingRelationBinder.bind(feeling);
        Repositories.feelings().add(feeling);
    }

    private Feeling buildFeeling(final FeelingRequestEvent feelingRequestEvent, final List<Sentiment> sentiments) {
        final Feeling feeling = feelingFactory.createFeeling(feelingRequestEvent.getFeelingId(), feelingRequestEvent.getText(), feelingRequestEvent.getUserId());
        feeling.setLanguageCode(feelingRequestEvent.getLanguage().getCode());
        for (Sentiment sentiment : sentiments) {
            feeling.addSentiment(sentiment);
        }
        return feeling;
    }

    private final FeelingRelationBinder feelingRelationBinder;
    private SentimentExtractor sentimentExtractor;
    private FeelingFactory feelingFactory;
}
