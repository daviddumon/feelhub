package com.steambeat.test.fakeFactories;

import com.steambeat.domain.analytics.Association;
import com.steambeat.domain.analytics.identifiers.uri.Uri;
import com.steambeat.domain.subject.webpage.*;
import com.steambeat.test.FakeUriScraper;

public class FakeWebPageFactory extends WebPageFactory {

    public FakeWebPageFactory() {
        super();
    }

    @Override
    public WebPage newWebPage(final Association association) {
        final FakeUriScraper fakeUriScraper = new FakeUriScraper();
        fakeUriScraper.scrap(Uri.empty());
        final WebPage webPage = new WebPage(association);
        webPage.update(fakeUriScraper);
        return webPage;
    }
}
