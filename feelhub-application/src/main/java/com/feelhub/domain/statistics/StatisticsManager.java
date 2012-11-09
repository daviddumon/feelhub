package com.feelhub.domain.statistics;

import com.feelhub.domain.topic.TopicPatch;
import com.feelhub.repositories.Repositories;

import java.util.*;

public class StatisticsManager {

    public void merge(final TopicPatch topicPatch) {
        for (final UUID oldTopicId : topicPatch.getOldTopicIds()) {
            for (final Granularity granularity : Granularity.values()) {
                mergeForGranularity(granularity, oldTopicId, topicPatch.getNewTopicId());
            }
        }
    }

    private void mergeForGranularity(final Granularity granularity, final UUID oldTopicId, final UUID newTopicId) {
        final List<Statistics> statisticsList = Repositories.statistics().forTopicId(oldTopicId, granularity);
        if (!statisticsList.isEmpty()) {
            for (final Statistics oldStat : statisticsList) {
                final List<Statistics> goodStatList = Repositories.statistics().forTopicId(newTopicId, granularity, granularity.intervalFor(oldStat.getDate()));
                if (goodStatList.isEmpty()) {
                    oldStat.setTopicId(newTopicId);
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
