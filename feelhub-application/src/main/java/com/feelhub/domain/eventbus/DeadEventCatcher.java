package com.feelhub.domain.eventbus;

import com.google.common.eventbus.*;

public class DeadEventCatcher {

    public DeadEventCatcher() {
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    public void handle(final DeadEvent deadEvent) {
        final FeelhubDeadEvent feelhubDeadEvent = new FeelhubDeadEvent(deadEvent);
        DomainEventBus.INSTANCE.getEvents().add(feelhubDeadEvent);
    }
}
