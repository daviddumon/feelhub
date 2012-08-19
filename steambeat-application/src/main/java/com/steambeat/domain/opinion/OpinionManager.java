package com.steambeat.domain.opinion;

import com.google.common.eventbus.*;
import com.steambeat.domain.eventbus.DomainEventBus;
import com.steambeat.domain.reference.ReferencesChangedEvent;
import com.steambeat.repositories.Repositories;

import java.util.*;

public class OpinionManager {

    public OpinionManager() {
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    @AllowConcurrentEvents
    public void handle(final ReferencesChangedEvent event) {
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
    }
}
