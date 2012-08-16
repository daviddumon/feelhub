package com.steambeat.domain.subject.uri;

import com.steambeat.domain.eventbus.DomainEvent;

public class UriCreatedEvent extends DomainEvent {

    public UriCreatedEvent(final Uri uri) {
        super();
        this.uri = uri;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(date.toString());
        stringBuilder.append(" - ");
        stringBuilder.append("Uri ");
        stringBuilder.append(uri.toString());
        stringBuilder.append(" created");
        return stringBuilder.toString();
    }

    public Uri getUri() {
        return uri;
    }

    private Uri uri;
}
