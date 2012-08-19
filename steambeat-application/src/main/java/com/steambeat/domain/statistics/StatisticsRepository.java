package com.steambeat.domain.statistics;

import com.steambeat.domain.Repository;
import com.steambeat.domain.reference.Reference;
import org.joda.time.Interval;

import java.util.*;

public interface StatisticsRepository extends Repository<Statistics> {

    List<Statistics> forReferenceId(UUID reference);

    List<Statistics> forReference(Reference reference, Granularity granularity);

    List<Statistics> forReference(Reference reference, Granularity granularity, Interval interval);
}
