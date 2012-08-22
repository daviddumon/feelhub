package com.steambeat.domain.eventbus;

import com.google.common.eventbus.*;

import java.util.*;
import java.util.concurrent.Executors;

public enum DomainEventBus {

    INSTANCE;

    private DomainEventBus() {
        eventBus = new AsyncEventBus(Executors.newFixedThreadPool(50));
        //eventBus = new EventBus();
    }

    public void setEventBus(final EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void register(final Object listener) {
        eventBus.register(listener);
    }

    public void post(final DomainEvent event) {
        eventBus.post(event);
        events.add(event);
        Collections.sort(events);
    }

    public List<DomainEvent> getEvents() {
        return events;
    }

    private EventBus eventBus;
    private AutoDiscardingList<DomainEvent> events = new AutoDiscardingList<DomainEvent>(100);
}
