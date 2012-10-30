package com.feelhub.domain.statistics;

import com.feelhub.domain.Repository;
import org.joda.time.Interval;

import java.util.*;

public interface StatisticsRepository extends Repository<Statistics> {

    List<Statistics> forReferenceId(UUID referenceId);

    List<Statistics> forReferenceId(UUID referenceId, Granularity granularity);

    List<Statistics> forReferenceId(UUID referenceId, Granularity granularity, Interval interval);
}
