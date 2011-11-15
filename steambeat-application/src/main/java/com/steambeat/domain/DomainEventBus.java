package com.steambeat.domain;

import com.google.common.collect.*;

import java.util.*;

public enum DomainEventBus {

    INSTANCE;

    public void spread(final DomainEvent event) {
        if (stackOnSpread == false) {
            doSpread(event);
        } else {
            events.add(event);
        }
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

    private void doSpread(final DomainEvent event) {
        for (final DomainEventListener listener : getListesteners(event.getClass())) {
            listener.notify(event);
        }
    }

    public <T extends DomainEvent> void register(final DomainEventListener<T> listener, final Class<T> eventType) {
        getListesteners(eventType).add(listener);
    }

    private List<DomainEventListener> getListesteners(final Class<? extends DomainEvent> eventType) {
        List<DomainEventListener> listeners = this.listeners.get(eventType);
        if (listeners == null) {
            listeners = Lists.newArrayList();
            this.listeners.put(eventType, listeners);
        }
        return listeners;
    }

    private final Map<Class<? extends DomainEvent>, List<DomainEventListener>> listeners = Maps.newHashMap();
    private boolean stackOnSpread = false;
    private final List<DomainEvent> events = Lists.newArrayList();
}
