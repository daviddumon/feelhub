package com.steambeat.test.testFactories;

import com.steambeat.domain.subject.feed.Feed;
import com.steambeat.repositories.Repositories;
import com.steambeat.domain.statistics.*;
import org.joda.time.DateTime;

public class FeedStatFactoryForTest {

    public Statistics newFeedStat(final String uri) {
        return newFeedStat(uri, Granularity.hour);
    }

    public Statistics newFeedStat(String uri, Granularity granularity) {
        final Feed feed = TestFactories.feeds().newFeed(uri);
        final Statistics statistics = new Statistics(feed, granularity, new DateTime());
        Repositories.statistics().add(statistics);
        return statistics;
    }
}
