package com.steambeat.domain;

import com.google.common.collect.*;

import java.util.*;

@SuppressWarnings("unchecked")
public enum DomainEventBus {

    INSTANCE;

    public void spread(final DomainEvent event) {
        if (!stackOnSpread) {
            doSpread(event);
        } else {
            events.add(event);
        }
    }

    private void doSpread(final DomainEvent event) {
        for (final DomainEventListener listener : getListeners(event.getClass())) {
            listener.notify(event);
        }
    }

    private List<DomainEventListener> getListeners(final Class<? extends DomainEvent> eventType) {
        List<DomainEventListener> listenersForEvent = this.listeners.get(eventType);
        if (listenersForEvent == null) {
            listenersForEvent = Lists.newArrayList();
            this.listeners.put(eventType, listenersForEvent);
        }
        return listenersForEvent;
    }

    public void clear() {
        listeners.clear();
        events.clear();
    }

    public void stackOnSpread() {
        stackOnSpread = true;
    }

    public void notifyOnSpread() {
        stackOnSpread = false;
    }

    public void flush() {
        for (final DomainEvent event : events) {
            doSpread(event);
        }
        events.clear();
    }

    public <T extends DomainEvent> void register(final DomainEventListener<T> listener, final Class<T> eventType) {
        getListeners(eventType).add(listener);
    }

    private final Map<Class<? extends DomainEvent>, List<DomainEventListener>> listeners = Maps.newHashMap();
    private final List<DomainEvent> events = Lists.newArrayList();
    private boolean stackOnSpread = false;
}
