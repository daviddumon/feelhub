package com.feelhub.domain.statistics;

import com.feelhub.domain.Repository;
import org.joda.time.Interval;

import java.util.*;

public interface StatisticsRepository extends Repository<Statistics> {

    List<Statistics> forTopicId(UUID topicId);

    List<Statistics> forTopicId(UUID topicId, Granularity granularity);

    List<Statistics> forTopicId(UUID topicId, Granularity granularity, Interval interval);
}
