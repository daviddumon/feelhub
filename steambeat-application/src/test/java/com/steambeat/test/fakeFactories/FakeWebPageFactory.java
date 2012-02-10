package com.steambeat.test.fakeFactories;

import com.steambeat.domain.subject.webpage.*;
import com.steambeat.test.FakeUriScraper;

public class FakeWebPageFactory extends WebPageFactory {

    public FakeWebPageFactory() {
        super();
    }

    @Override
    public WebPage newWebPage(final Association association) {
        checkNotExists(association);
        final FakeUriScraper fakeUriScraper = new FakeUriScraper(Uri.empty());
        fakeUriScraper.scrap();
        final WebPage webPage = new WebPage(association);
        webPage.update(fakeUriScraper);
        return webPage;
    }
}
