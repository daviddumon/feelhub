package com.feelhub.domain.eventbus;

import com.google.common.collect.Lists;
import com.google.common.eventbus.EventBus;

import java.util.List;

public enum DomainEventBus {

    INSTANCE;

    private DomainEventBus() {
        eventBus = new EventBus();
    }

    public void setEventBus(final EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void register(final Object listener) {
        eventBus.register(listener);
    }

    public void post(final DomainEvent event) {
        eventBus.post(event);
    }

    public List<DomainEvent> getEvents() {
        return Lists.newArrayList();
    }

    private EventBus eventBus;
}
