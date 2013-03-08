package com.feelhub.domain.eventbus;

import com.google.common.collect.Lists;

import java.util.List;

public enum DomainEventBus {

    INSTANCE;

    public void propagateSync() {
        eventBus.propagateSync();
    }

    public void propagate() {
        eventBus.propagateAsync();
    }

    private DomainEventBus() {
    }

    public void setEventBus(final HybridEventBus eventBus) {
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

    private HybridEventBus eventBus;

}
