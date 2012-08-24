package com.steambeat.domain.keyword;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.steambeat.domain.eventbus.DomainEventBus;
import com.steambeat.domain.reference.ReferencesChangedEvent;
import com.steambeat.repositories.*;

import java.util.*;

public class KeywordManager {

    @Inject
    public KeywordManager(final SessionProvider sessionProvider) {
        this.sessionProvider = sessionProvider;
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    public void handle(final ReferencesChangedEvent referencesChangedEvent) {
        sessionProvider.start();
        final UUID newReference = referencesChangedEvent.getNewReferenceId();
        for (UUID reference : referencesChangedEvent.getReferenceIds()) {
            final List<Keyword> keywords = Repositories.keywords().forReferenceId(reference);
            for (Keyword keyword : keywords) {
                keyword.setReferenceId(newReference);
            }
        }
        sessionProvider.stop();
    }

    private SessionProvider sessionProvider;
}
