package com.steambeat.domain;

import com.google.common.eventbus.*;

public interface DomainEventListener<T extends DomainEvent> {

    @Subscribe
    @AllowConcurrentEvents
    public void handle(final T event);
}
