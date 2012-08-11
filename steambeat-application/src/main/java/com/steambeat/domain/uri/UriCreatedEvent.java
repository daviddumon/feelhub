package com.steambeat.domain.uri;

import com.steambeat.domain.eventbus.DomainEvent;
import org.joda.time.DateTime;

public class UriCreatedEvent implements DomainEvent {

    public UriCreatedEvent(final Uri uri) {
        this.uri = uri;
        this.date = new DateTime();
    }

    @Override
    public DateTime getDate() {
        return date;
    }

    public Uri getUri() {
        return uri;
    }

    private DateTime date;
    private Uri uri;
}
