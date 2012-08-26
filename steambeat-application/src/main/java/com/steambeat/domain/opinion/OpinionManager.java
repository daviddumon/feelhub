package com.steambeat.domain.opinion;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.steambeat.domain.eventbus.DomainEventBus;
import com.steambeat.domain.reference.ReferencesChangedEvent;
import com.steambeat.repositories.*;

import java.util.*;

public class OpinionManager {

    @Inject
    public OpinionManager(final SessionProvider sessionProvider) {
        this.sessionProvider = sessionProvider;
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    public void handle(final ReferencesChangedEvent event) {
        sessionProvider.start();
        for (final UUID referenceId : event.getReferenceIds()) {
            final List<Opinion> opinionsForReferenceId = Repositories.opinions().forReferenceId(referenceId);
            if (!opinionsForReferenceId.isEmpty()) {
                for (final Opinion opinion : opinionsForReferenceId) {
                    for (final Judgment judgment : opinion.getJudgments()) {
                        if (judgment.getReferenceId().equals(referenceId)) {
                            judgment.setReferenceId(event.getNewReferenceId());
                        }
                    }
                }
            }
        }
        sessionProvider.stop();
    }

    private final SessionProvider sessionProvider;
}
