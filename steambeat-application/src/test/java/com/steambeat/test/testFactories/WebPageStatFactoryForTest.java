package com.steambeat.test.testFactories;

import com.steambeat.domain.statistics.*;
import com.steambeat.domain.subject.webpage.WebPage;
import com.steambeat.repositories.Repositories;
import org.joda.time.DateTime;

public class WebPageStatFactoryForTest {

    public Statistics newWebPageStat(final String address) {
        return newWebPageStat(address, Granularity.hour);
    }

    public Statistics newWebPageStat(final String address, final Granularity granularity) {
        final WebPage webPage = TestFactories.subjects().newWebPage();
        final Statistics statistics = new Statistics(webPage, granularity, new DateTime());
        Repositories.statistics().add(statistics);
        return statistics;
    }
}
