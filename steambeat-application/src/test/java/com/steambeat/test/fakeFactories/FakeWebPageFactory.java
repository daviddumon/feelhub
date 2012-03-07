package com.steambeat.test.fakeFactories;

import com.steambeat.domain.analytics.Association;
import com.steambeat.domain.subject.webpage.*;
import com.steambeat.test.FakeUriScraper;

public class FakeWebPageFactory extends WebPageFactory {

    public FakeWebPageFactory() {
        super();
    }

    @Override
    public WebPage newWebPage(final Association association) {
        if (checkIfExists(association)) {
            throw new WebPageAlreadyExistsException(association.getId());
        }
        final WebPage webPage = new WebPage(association);
        webPage.setScraper(new FakeUriScraper());
        return webPage;
    }
}
