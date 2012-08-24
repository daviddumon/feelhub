package com.steambeat.domain.statistics;

import com.google.common.eventbus.*;
import com.steambeat.domain.eventbus.DomainEventBus;
import com.steambeat.domain.reference.ReferencesChangedEvent;
import com.steambeat.repositories.*;

import javax.inject.Inject;
import java.util.*;

public class StatisticsManager {

    @Inject
    public StatisticsManager(final SessionProvider sessionProvider) {
        this.sessionProvider = sessionProvider;
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    public void handle(final ReferencesChangedEvent event) {
        sessionProvider.start();
        for (UUID referenceId : event.getReferenceIds()) {
            final List<Statistics> statistics = Repositories.statistics().forReferenceId(referenceId);
            if (!statistics.isEmpty()) {
                for (Statistics stat : statistics) {
                    stat.setReferenceId(event.getNewReferenceId());
                }
            }
        }
        sessionProvider.stop();
    }

    private SessionProvider sessionProvider;
}
