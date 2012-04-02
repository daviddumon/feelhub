package com.steambeat.test.testFactories;

import com.steambeat.domain.statistics.*;
import com.steambeat.domain.subject.Subject;
import com.steambeat.domain.subject.webpage.WebPage;
import com.steambeat.repositories.Repositories;
import org.joda.time.DateTime;

public class StatisticsFactoryForTest {

    public Statistics newStatistics(final Subject subject, final Granularity granularity) {
        final Statistics statistics = new Statistics(subject, granularity, new DateTime());
        Repositories.statistics().add(statistics);
        return statistics;
    }

    public Statistics newStatistics() {
        final WebPage webPage = TestFactories.subjects().newWebPage();
        final Statistics statistics = new Statistics(webPage, Granularity.day, new DateTime());
        Repositories.statistics().add(statistics);
        return statistics;
    }
}
