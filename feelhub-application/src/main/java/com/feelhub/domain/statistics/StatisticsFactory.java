package com.feelhub.domain.statistics;

import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.feeling.*;
import com.feelhub.domain.topic.Topic;
import com.feelhub.domain.topic.world.WorldStatisticsEvent;
import com.feelhub.repositories.Repositories;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import org.joda.time.DateTime;

import java.util.List;

public class StatisticsFactory {

    @Inject
    public StatisticsFactory() {
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    public void handle(final FeelingCreatedEvent event) {
        feelingOn(event.getFeeling(), event.date);
    }

    @Subscribe
    public void handle(final WorldStatisticsEvent event) {
        feelingOn(event.getFeeling(), event.date);
    }

    private void feelingOn(final Feeling feeling, final DateTime date) {
        for (final Granularity granularity : Granularity.values()) {
            dealWith(granularity, feeling, date);
        }
    }

    private void dealWith(final Granularity granularity, final Feeling feeling, final DateTime date) {
        dealWithTopic(granularity, feeling, date);
    }

    private void dealWithTopic(final Granularity granularity, final Feeling feeling, final DateTime date) {
        final Statistics stat = getOrCreateStat(granularity, feeling, date);
        stat.incrementFeelingCount(feeling);
        if (granularity.equals(Granularity.all)) {
            final Topic topic = Repositories.topics().getCurrentTopic(feeling.getTopicId());
            topic.increasesFeelingCount(feeling);
        }
    }

    private synchronized Statistics getOrCreateStat(final Granularity granularity, final Feeling feeling, final DateTime date) {
        final List<Statistics> statistics = Repositories.statistics().forTopicId(feeling.getTopicId(), granularity, granularity.intervalFor(date));
        final Statistics stat;
        if (statistics.isEmpty()) {
            stat = new Statistics(feeling.getTopicId(), granularity, date);
            Repositories.statistics().add(stat);
        } else {
            stat = statistics.get(0);
        }
        return stat;
    }

}
