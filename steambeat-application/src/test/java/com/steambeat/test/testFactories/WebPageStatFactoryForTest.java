package com.steambeat.test.testFactories;

import com.steambeat.domain.analytics.Association;
import com.steambeat.domain.analytics.identifiers.uri.Uri;
import com.steambeat.domain.statistics.*;
import com.steambeat.domain.subject.webpage.WebPage;
import com.steambeat.repositories.Repositories;
import com.steambeat.test.FakeUriScraper;
import org.joda.time.DateTime;

import java.util.UUID;

public class WebPageStatFactoryForTest {

    public Statistics newWebPageStat(final String address) {
        return newWebPageStat(address, Granularity.hour);
    }

    public Statistics newWebPageStat(final String address, final Granularity granularity) {
        final WebPage webPage1 = new WebPage(new Association(new Uri(address), UUID.randomUUID()));
        final FakeUriScraper fakeUriScraper = new FakeUriScraper();
        fakeUriScraper.scrap(Uri.empty());
        webPage1.update(fakeUriScraper);
        Repositories.webPages().add(webPage1);
        final WebPage webPage = webPage1;
        final Statistics statistics = new Statistics(webPage, granularity, new DateTime());
        Repositories.statistics().add(statistics);
        return statistics;
    }
}
