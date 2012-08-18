package com.steambeat.domain.keyword;

import com.google.common.eventbus.*;
import com.steambeat.domain.eventbus.DomainEventBus;
import com.steambeat.domain.reference.ReferencesChangedEvent;
import com.steambeat.repositories.Repositories;

import java.util.*;

public class KeywordManager {

    public KeywordManager() {
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    @AllowConcurrentEvents
    public void handle(final ReferencesChangedEvent referencesChangedEvent) {
        final UUID newReference = referencesChangedEvent.getNewReference();
        for (UUID reference : referencesChangedEvent.getReferences()) {
            final List<Keyword> keywords = Repositories.keywords().forReferenceId(reference);
            for (Keyword keyword : keywords) {
                keyword.setReferenceId(newReference);
            }
        }
    }
}
