package com.feelhub.application;

import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.feeling.*;
import com.feelhub.repositories.Repositories;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;

public class FeelingService {

    @Inject
    public FeelingService(final FeelingRelationBinder feelingRelationBinder, final FeelingFactory feelingFactory) {
        this.feelingRelationBinder = feelingRelationBinder;
        this.feelingFactory = feelingFactory;
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    public void handle(final FeelingRequestEvent event) {
        final Feeling feeling = buildFeeling(event);
        feelingRelationBinder.bind(feeling);
        Repositories.feelings().add(feeling);
    }

    private Feeling buildFeeling(final FeelingRequestEvent feelingRequestEvent) {
        final Feeling feeling = feelingFactory.createFeeling(feelingRequestEvent.getFeelingId(), feelingRequestEvent.getText(), feelingRequestEvent.getUserId());
        feeling.setLanguageCode(feelingRequestEvent.getLanguage().getCode());
        for (Sentiment sentiment : feelingRequestEvent.getSentiments()) {
            feeling.addSentiment(sentiment);
        }
        return feeling;
    }

    private final FeelingRelationBinder feelingRelationBinder;
    private final FeelingFactory feelingFactory;
}
