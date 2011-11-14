package com.steambeat.domain.subject.feed;

import com.google.inject.Inject;
import com.steambeat.domain.DomainEventBus;
import com.steambeat.repositories.Repositories;
import com.steambeat.tools.HtmlParser;

public class FeedFactory {

    @Inject
    public FeedFactory(final HtmlParser parser) {
        this.parser = parser;
    }

    public Feed buildFeed(final Association association) {
        checkNotExists(association);
        final Feed result = new Feed(association);
        DomainEventBus.INSTANCE.spread(new FeedCreatedEvent(result));
        return result;
    }

    private void checkNotExists(final Association association) {
        if (Repositories.feeds().get(association.getCanonicalUri()) != null) {
            throw new FeedAlreadyExistsException(association.getCanonicalUri());
        }
    }

    private final HtmlParser parser;
}
