package com.steambeat.test.testFactories;

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
        final WebPage webPage = new WebPage(association);
        final FakeUriScraper fakeUriScraper = new FakeUriScraper(Uri.empty());
        fakeUriScraper.scrap();
        webPage.update(fakeUriScraper);
        Repositories.webPages().add(webPage);
        return webPage;
    }
}

