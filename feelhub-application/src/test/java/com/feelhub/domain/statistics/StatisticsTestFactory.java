package com.feelhub.domain.statistics;

import com.feelhub.domain.feeling.*;
import com.feelhub.domain.topic.real.RealTopic;
import com.feelhub.repositories.Repositories;
import com.feelhub.test.TestFactories;
import org.joda.time.DateTime;

import java.util.UUID;

public class StatisticsTestFactory {

    public Statistics newStatistics() {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        final Statistics statistics = new Statistics(realTopic.getId(), Granularity.day, new DateTime());
        Repositories.statistics().add(statistics);
        return statistics;
    }

    public Statistics newStatistics(final UUID topicId, final Granularity granularity) {
        final Statistics statistics = new Statistics(topicId, granularity, new DateTime());
        Repositories.statistics().add(statistics);
        return statistics;
    }

    public Statistics newStatisticsWithSentiments(final UUID topicid, final Granularity granularity) {
        final Statistics statistics = new Statistics(topicid, granularity, new DateTime());
        statistics.incrementSentimentCount(new Sentiment(topicid, SentimentValue.good));
        statistics.incrementSentimentCount(new Sentiment(topicid, SentimentValue.good));
        statistics.incrementSentimentCount(new Sentiment(topicid, SentimentValue.good));
        statistics.incrementSentimentCount(new Sentiment(topicid, SentimentValue.bad));
        statistics.incrementSentimentCount(new Sentiment(topicid, SentimentValue.bad));
        statistics.incrementSentimentCount(new Sentiment(topicid, SentimentValue.neutral));
        new Sentiment(topicid, SentimentValue.good);
        Repositories.statistics().add(statistics);
        return statistics;
    }
}
