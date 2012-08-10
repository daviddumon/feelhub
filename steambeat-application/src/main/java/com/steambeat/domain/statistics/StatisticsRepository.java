package com.steambeat.domain.statistics;

import com.steambeat.domain.Repository;
import com.steambeat.domain.topic.Topic;
import org.joda.time.Interval;

import java.util.List;

public interface StatisticsRepository extends Repository<Statistics> {

    List<Statistics> forTopic(Topic topic, Granularity granularity);

    List<Statistics> forTopic(Topic topic, Granularity granularity, Interval interval);
}
