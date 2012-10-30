package com.feelhub.domain.statistics;

import com.feelhub.domain.reference.ReferencePatch;
import com.feelhub.repositories.Repositories;

import java.util.*;

public class StatisticsManager {

    public void merge(final ReferencePatch referencePatch) {
        for (final UUID oldReferenceId : referencePatch.getOldReferenceIds()) {
            for (final Granularity granularity : Granularity.values()) {
                mergeForGranularity(granularity, oldReferenceId, referencePatch.getNewReferenceId());
            }
        }
    }

    private void mergeForGranularity(final Granularity granularity, final UUID oldReferenceId, final UUID newReferenceId) {
        final List<Statistics> statisticsList = Repositories.statistics().forReferenceId(oldReferenceId, granularity);
        if (!statisticsList.isEmpty()) {
            for (final Statistics oldStat : statisticsList) {
                final List<Statistics> goodStatList = Repositories.statistics().forReferenceId(newReferenceId, granularity, granularity.intervalFor(oldStat.getDate()));
                if (goodStatList.isEmpty()) {
                    oldStat.setReferenceId(newReferenceId);
                } else {
                    final Statistics goodStat = goodStatList.get(0);
                    goodStat.addGood(oldStat.getGood());
                    goodStat.addBad(oldStat.getBad());
                    goodStat.addNeutral(oldStat.getNeutral());
                    Repositories.statistics().delete(oldStat);
                }
            }
        }
    }
}
