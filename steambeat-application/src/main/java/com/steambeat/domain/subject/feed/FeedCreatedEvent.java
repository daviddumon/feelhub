package com.steambeat.domain.subject.feed;

import com.steambeat.domain.DomainEvent;
import org.joda.time.DateTime;

public class FeedCreatedEvent implements DomainEvent {

    public FeedCreatedEvent(Feed result) {
        this.feed = result;
    }

    @Override
    public DateTime getDate() {
        return date;
    }

    public Feed getFeed() {
        return feed;
    }

    private final Feed feed;
    private final DateTime date = new DateTime();
}
