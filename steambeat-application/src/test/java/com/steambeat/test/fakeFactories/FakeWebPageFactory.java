package com.steambeat.test.fakeFactories;

import com.steambeat.domain.subject.webpage.WebPageFactory;
import com.steambeat.test.FakeHtmlParser;

public class FakeWebPageFactory extends WebPageFactory {

    public FakeWebPageFactory() {
        super(new FakeHtmlParser());
    }

}
