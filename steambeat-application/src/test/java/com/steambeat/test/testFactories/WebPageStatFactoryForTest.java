package com.steambeat.test.testFactories;

import com.steambeat.domain.statistics.*;
import com.steambeat.domain.subject.Subject;
import com.steambeat.repositories.Repositories;
import org.joda.time.DateTime;

public class WebPageStatFactoryForTest {

    public Statistics newWebPageStat(final Subject subject, Granularity granularity) {
        final Statistics statistics = new Statistics(subject, granularity, new DateTime());
        Repositories.statistics().add(statistics);
        return statistics;
    }
}
