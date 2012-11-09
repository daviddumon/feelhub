package com.feelhub.domain.statistics;

import com.feelhub.domain.feeling.*;
import com.feelhub.domain.topic.Topic;
import com.feelhub.repositories.Repositories;
import com.feelhub.test.TestFactories;
import org.joda.time.DateTime;

public class StatisticsTestFactory {

    public Statistics newStatistics(final Topic topic, final Granularity granularity) {
        final Statistics statistics = new Statistics(topic.getId(), granularity, new DateTime());
        Repositories.statistics().add(statistics);
        return statistics;
    }

    public Statistics newStatisticsWithSentiments(final Topic topic, final Granularity granularity) {
        final Statistics statistics = new Statistics(topic.getId(), granularity, new DateTime());
        statistics.incrementSentimentCount(new Sentiment(topic.getId(), SentimentValue.good));
        statistics.incrementSentimentCount(new Sentiment(topic.getId(), SentimentValue.good));
        statistics.incrementSentimentCount(new Sentiment(topic.getId(), SentimentValue.good));
        statistics.incrementSentimentCount(new Sentiment(topic.getId(), SentimentValue.bad));
        statistics.incrementSentimentCount(new Sentiment(topic.getId(), SentimentValue.bad));
        statistics.incrementSentimentCount(new Sentiment(topic.getId(), SentimentValue.neutral));
        new Sentiment(topic.getId(), SentimentValue.good);
        Repositories.statistics().add(statistics);
        return statistics;
    }

    public Statistics newStatistics() {
        final Topic topic = TestFactories.topics().newTopic();
        final Statistics statistics = new Statistics(topic.getId(), Granularity.day, new DateTime());
        Repositories.statistics().add(statistics);
        return statistics;
    }
}
