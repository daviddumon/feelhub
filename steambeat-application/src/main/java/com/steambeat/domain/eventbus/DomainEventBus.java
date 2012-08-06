package com.steambeat.domain.eventbus;

import com.google.common.collect.Lists;
import com.google.common.eventbus.*;

import java.util.List;
import java.util.concurrent.Executors;

public enum DomainEventBus {

    INSTANCE;

    private DomainEventBus() {
        eventBus = new AsyncEventBus(Executors.newFixedThreadPool(50));
    }

    public void setEventBus(final EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void register(final Object listener) {
        eventBus.register(listener);
        listeners.add(listener);
    }

    public void post(final DomainEvent event) {
        eventBus.post(event);
        events.add(event);
    }

    public List<DomainEvent> getEvents() {
        return events;
    }

    private final List<Object> listeners = Lists.newArrayList();
    private EventBus eventBus;
    private List<DomainEvent> events = Lists.newArrayList();
}