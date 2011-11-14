package com.steambeat.domain.statistics;

import com.steambeat.domain.Repository;
import com.steambeat.domain.subject.Subject;
import org.joda.time.Interval;

import java.util.List;

public interface StatisticsRepository extends Repository<Statistics> {

    List<Statistics> forFeed(Subject subject, Granularity granularity);

    List<Statistics> forFeed(Subject subject, Granularity granularity, Interval interval);
}
