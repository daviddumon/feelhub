package com.steambeat.domain.opinion;

import com.google.common.eventbus.*;
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
    @AllowConcurrentEvents
    public void handle(final ReferencesChangedEvent event) {
        sessionProvider.start();
        for (UUID referenceId : event.getReferenceIds()) {
            final List<Opinion> opinionsForReferenceId = Repositories.opinions().forReferenceId(referenceId);
            if (!opinionsForReferenceId.isEmpty()) {
                for (Opinion opinion : opinionsForReferenceId) {
                    for (Judgment judgment : opinion.getJudgments()) {
                        if (judgment.getReferenceId().equals(referenceId)) {
                            judgment.setReferenceId(event.getNewReferenceId());
                        }
                    }
                }
            }
        }
        sessionProvider.stop();
    }

    private SessionProvider sessionProvider;
}
