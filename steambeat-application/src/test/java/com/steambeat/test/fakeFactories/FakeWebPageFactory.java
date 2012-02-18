package com.steambeat.test.fakeFactories;

import com.steambeat.domain.analytics.Association;
import com.steambeat.domain.subject.webpage.*;

public class FakeWebPageFactory extends WebPageFactory {

    public FakeWebPageFactory() {
        super();
    }

    @Override
    public WebPage newWebPage(final Association association) {
        final WebPage webPage = new WebPage(association);
        webPage.update();
        return webPage;
    }
}
