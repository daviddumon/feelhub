package com.steambeat.test.testFactories;

import com.steambeat.domain.analytics.Association;
import com.steambeat.domain.analytics.identifiers.uri.Uri;
import com.steambeat.domain.subject.webpage.WebPage;
import com.steambeat.repositories.Repositories;

import java.util.UUID;

public class WebPageFactoryForTest {

    public WebPage newWebPage() {
        final Association association = new Association(new Uri("http://www.fake.com/" + UUID.randomUUID().toString()), UUID.randomUUID());
        final WebPage webPage = new WebPage(association);
        webPage.update();
        Repositories.associations().add(association);
        Repositories.webPages().add(webPage);
        return webPage;
    }
}

