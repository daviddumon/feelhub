package com.steambeat.domain.eventbus;

import com.google.common.eventbus.*;

public class DeadEventCatcher {

    public DeadEventCatcher() {
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    public void handle(DeadEvent deadEvent) {
        final SteambeatDeadEvent steambeatDeadEvent = new SteambeatDeadEvent(deadEvent);
        DomainEventBus.INSTANCE.getEvents().add(steambeatDeadEvent);
    }
}
