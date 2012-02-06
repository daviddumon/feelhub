package com.steambeat.test.testFactories;

import com.steambeat.domain.opinion.Feeling;
import com.steambeat.domain.subject.webpage.*;
import com.steambeat.repositories.Repositories;
import com.steambeat.test.FakeUriScraper;

import java.util.UUID;

public class WebPageFactoryForTest {

    public WebPage newWebPage() {
        return newWebPage("http://www.fake.com/" + UUID.randomUUID().toString());
    }

    public WebPage newWebPage(final String address) {
        final Association association = new Association(new Uri(address), TestFactories.canonicalUriFinder());
        final WebPage webPage = new WebPage(association, new FakeUriScraper());
        Repositories.webPages().add(webPage);
        return webPage;
    }

    public WebPage newWebPageWithLotOfOpinions(final String address, final int opinionsSize) {
        final Association association = new Association(new Uri(address), TestFactories.canonicalUriFinder());
        final WebPage webPage = new WebPage(association, new FakeUriScraper());
        for (int i = 0; i < opinionsSize; i++) {
            Repositories.opinions().add(webPage.createOpinion("my opinion" + i, Feeling.good));
        }
        Repositories.webPages().add(webPage);
        return webPage;
    }
}

