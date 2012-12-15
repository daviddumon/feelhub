package com.feelhub.domain.statistics;

import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.feeling.*;
import com.feelhub.domain.topic.world.WorldStatisticsEvent;
import com.feelhub.repositories.*;
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
    public void handle(final SentimentStatisticsEvent event) {
        sentimentOn(event.getSentiment(), event.getDate());
    }

    @Subscribe
    public void handle(final WorldStatisticsEvent event) {
        sentimentOn(event.getSentiment(), event.getDate());
    }

    private void sentimentOn(final Sentiment sentiment, final DateTime date) {
        for (final Granularity granularity : Granularity.values()) {
            dealWith(granularity, sentiment, date);
        }
    }

    private void dealWith(final Granularity granularity, final Sentiment sentiment, final DateTime date) {
        dealWithTopic(granularity, sentiment, date);
    }

    private void dealWithTopic(final Granularity granularity, final Sentiment sentiment, final DateTime date) {
        final Statistics stat = getOrCreateStat(granularity, sentiment, date);
        stat.incrementSentimentCount(sentiment);
    }

    private synchronized Statistics getOrCreateStat(final Granularity granularity, final Sentiment sentiment, final DateTime date) {
        final List<Statistics> statistics = Repositories.statistics().forTopicId(sentiment.getTopicId(), granularity, granularity.intervalFor(date));
        final Statistics stat;
        if (statistics.isEmpty()) {
            stat = new Statistics(sentiment.getTopicId(), granularity, date);
            Repositories.statistics().add(stat);
        } else {
            stat = statistics.get(0);
        }
        return stat;
    }

}
