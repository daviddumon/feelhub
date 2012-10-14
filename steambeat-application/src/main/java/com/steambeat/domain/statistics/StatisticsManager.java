package com.steambeat.domain.statistics;

import com.steambeat.repositories.Repositories;

import java.util.*;

public class StatisticsManager {

    public void migrate(final UUID referenceId, final List<UUID> oldReferenceIds) {
        //for (final UUID referenceId : event.getReferenceIds()) {
        //    final List<Statistics> statistics = Repositories.statistics().forReferenceId(referenceId);
        //    if (!statistics.isEmpty()) {
        //        for (final Statistics stat : statistics) {
        //            stat.setReferenceId(event.getNewReferenceId());
        //        }
        //    }
        //}

        //todo : une stat migrée doit etre fondue dans les existantes
    }
}
