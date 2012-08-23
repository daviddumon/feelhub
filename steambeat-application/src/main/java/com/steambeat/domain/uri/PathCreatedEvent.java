package com.steambeat.domain.uri;

import com.steambeat.domain.eventbus.DomainEvent;

public class PathCreatedEvent extends DomainEvent {

    public PathCreatedEvent(final Path path) {
        super();
        this.path = path;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(date.toString());
        stringBuilder.append(" - ");
        stringBuilder.append("PathCreatedEvent ");
        stringBuilder.append(path.toString());
        return stringBuilder.toString();
    }

    public Path getPath() {
        return path;
    }

    private Path path;
}
