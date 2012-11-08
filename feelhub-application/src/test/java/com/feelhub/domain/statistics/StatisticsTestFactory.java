package com.feelhub.domain.statistics;

import com.feelhub.domain.feeling.*;
import com.feelhub.domain.reference.Reference;
import com.feelhub.repositories.Repositories;
import com.feelhub.test.TestFactories;
import org.joda.time.DateTime;

public class StatisticsTestFactory {

    public Statistics newStatistics(final Reference reference, final Granularity granularity) {
        final Statistics statistics = new Statistics(reference.getId(), granularity, new DateTime());
        Repositories.statistics().add(statistics);
        return statistics;
    }

    public Statistics newStatisticsWithSentiments(final Reference reference, final Granularity granularity) {
        final Statistics statistics = new Statistics(reference.getId(), granularity, new DateTime());
        statistics.incrementSentimentCount(new Sentiment(reference.getId(), SentimentValue.good));
        statistics.incrementSentimentCount(new Sentiment(reference.getId(), SentimentValue.good));
        statistics.incrementSentimentCount(new Sentiment(reference.getId(), SentimentValue.good));
        statistics.incrementSentimentCount(new Sentiment(reference.getId(), SentimentValue.bad));
        statistics.incrementSentimentCount(new Sentiment(reference.getId(), SentimentValue.bad));
        statistics.incrementSentimentCount(new Sentiment(reference.getId(), SentimentValue.neutral));
        new Sentiment(reference.getId(), SentimentValue.good);
        Repositories.statistics().add(statistics);
        return statistics;
    }

    public Statistics newStatistics() {
        final Reference reference = TestFactories.references().newReference();
        final Statistics statistics = new Statistics(reference.getId(), Granularity.day, new DateTime());
        Repositories.statistics().add(statistics);
        return statistics;
    }
}
