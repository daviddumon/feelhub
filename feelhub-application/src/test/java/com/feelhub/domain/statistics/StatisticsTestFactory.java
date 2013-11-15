package com.feelhub.domain.statistics;

import com.feelhub.domain.topic.Topic;
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

    public Statistics newStatisticsWithFeelings(final UUID topicid, final Granularity granularity) {
        final Statistics statistics = new Statistics(topicid, granularity, new DateTime());
        final Topic topic = Repositories.topics().get(topicid);
        statistics.incrementFeelingCount(TestFactories.feelings().happyFeeling(topic));
        statistics.incrementFeelingCount(TestFactories.feelings().happyFeeling(topic));
        statistics.incrementFeelingCount(TestFactories.feelings().happyFeeling(topic));
        statistics.incrementFeelingCount(TestFactories.feelings().sadFeeling(topic));
        statistics.incrementFeelingCount(TestFactories.feelings().sadFeeling(topic));
        statistics.incrementFeelingCount(TestFactories.feelings().boredFeeling(topic));
        Repositories.statistics().add(statistics);
        return statistics;
    }
}
