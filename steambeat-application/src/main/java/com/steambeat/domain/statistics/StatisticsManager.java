package com.steambeat.domain.statistics;

import com.google.common.eventbus.*;
import com.steambeat.domain.eventbus.DomainEventBus;
import com.steambeat.domain.reference.ReferencesChangedEvent;
import com.steambeat.repositories.Repositories;

import java.util.*;

public class StatisticsManager {

    public StatisticsManager() {
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    @AllowConcurrentEvents
    public void handle(final ReferencesChangedEvent event) {
        for (UUID referenceId : event.getReferenceIds()) {
            final List<Statistics> statistics = Repositories.statistics().forReferenceId(referenceId);
            if (!statistics.isEmpty()) {
                for (Statistics stat : statistics) {
                    stat.setReferenceId(event.getNewReferenceId());
                }
            }
        }
    }
}
