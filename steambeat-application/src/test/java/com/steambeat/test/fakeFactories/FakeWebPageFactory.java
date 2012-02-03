package com.steambeat.test.fakeFactories;

import com.steambeat.domain.subject.webpage.*;
import com.steambeat.test.FakeWebPageScraper;

public class FakeWebPageFactory extends WebPageFactory {

    public FakeWebPageFactory() {
        super();
    }

    @Override
    public WebPage newWebPage(final Association association) {
        checkNotExists(association);
        return new WebPage(association, new FakeWebPageScraper());
    }
}
