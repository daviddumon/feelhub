package com.steambeat.test.testFactories;

import com.steambeat.domain.opinion.Feeling;
import com.steambeat.repositories.Repositories;
import com.steambeat.domain.subject.feed.*;

import java.util.UUID;

public class FeedFactoryForTest {

    public Feed newFeed(final String uri) {
        final Association association = new Association(new Uri(uri), TestFactories.canonicalUriFinder());
        final Feed feed = new Feed(association);
        Repositories.feeds().add(feed);
        return feed;
    }

    public Feed newFeed() {
        return newFeed("myfeed" + UUID.randomUUID().toString());
    }

    public Feed newFeedWithLotOfOpinions(final String uri, final int opinionsSize) {
        final Association association = new Association(new Uri(uri), TestFactories.canonicalUriFinder());
        final Feed feed = new Feed(association);
        for (int i = 0; i < opinionsSize; i++) {
            Repositories.opinions().add(feed.createOpinion("myopinion" + i, Feeling.good));
        }
        Repositories.feeds().add(feed);
        return feed;
    }
}

