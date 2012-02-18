package com.steambeat.test.testFactories;

import com.steambeat.domain.analytics.Association;
import com.steambeat.domain.analytics.identifiers.uri.Uri;
import com.steambeat.domain.subject.webpage.WebPage;
import com.steambeat.repositories.Repositories;

import java.util.UUID;

public class WebPageFactoryForTest {

    public WebPage newWebPage() {
        final WebPage webPage = new WebPage(new Association(new Uri("http://www.fake.com/" + UUID.randomUUID().toString()), UUID.randomUUID()));
        webPage.update();
        Repositories.webPages().add(webPage);
        return webPage;
    }
}

