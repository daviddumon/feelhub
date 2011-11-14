package com.steambeat.test.fakeFactories;

import com.steambeat.domain.subject.feed.FeedFactory;
import com.steambeat.test.FakeHtmlParser;

public class FakeFeedFactory extends FeedFactory {

    public FakeFeedFactory() {
        super(new FakeHtmlParser());
    }

}
