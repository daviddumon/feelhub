package com.feelhub.application;

import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.feeling.*;
import com.feelhub.domain.feeling.analyze.SentimentExtractor;
import com.feelhub.domain.relation.FeelingRelationBinder;
import com.feelhub.repositories.Repositories;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;

import java.util.List;

public class FeelingService {

    @Inject
    public FeelingService(final FeelingRelationBinder feelingRelationBinder, final SentimentExtractor sentimentExtractor) {
        this.feelingRelationBinder = feelingRelationBinder;
        this.sentimentExtractor = sentimentExtractor;
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    public void handle(final FeelingRequestEvent event) {
        final List<Sentiment> sentiments = sentimentExtractor.analyze(event.getText(), event.getTopicId(), event.getUserId(), event.getLanguage());
        final Feeling feeling = buildFeeling(event, sentiments);
        feelingRelationBinder.bind(feeling);
        Repositories.feelings().add(feeling);
    }

    private Feeling buildFeeling(final FeelingRequestEvent feelingRequestEvent, final List<Sentiment> sentiments) {
        final Feeling.Builder builder = new Feeling.Builder();
        builder.id(feelingRequestEvent.getFeelingId());
        builder.text(feelingRequestEvent.getText());
        builder.user(feelingRequestEvent.getUserId());
        builder.language(feelingRequestEvent.getLanguage().getCode());
        builder.sentiments(sentiments);
        return builder.build();
    }

    private final FeelingRelationBinder feelingRelationBinder;
    private SentimentExtractor sentimentExtractor;
}
