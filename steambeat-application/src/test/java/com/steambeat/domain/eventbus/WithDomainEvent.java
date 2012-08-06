package com.steambeat.domain.eventbus;

import com.google.common.collect.Maps;
import com.google.common.eventbus.*;
import org.junit.rules.ExternalResource;

import java.util.Map;

public class WithDomainEvent extends ExternalResource {

    @Override
    protected void before() throws Throwable {
        DomainEventBus.INSTANCE.setEventBus(new EventBus());
    }

    public <T extends DomainEvent> void capture(final Class<T> type) {
        DomainEventBus.INSTANCE.register(new SimpleEventListerner<T>() {

            @Override
            public void handle(final T event) {
                eventsCaptured.put(type, event);
            }
        });
    }

    private class SimpleEventListerner<T extends DomainEvent> {

        @Subscribe
        public void handle(final T event) {
        }
    }

    public <T extends DomainEvent> T lastEvent(final Class<T> type) {
        return (T) eventsCaptured.get(type);
    }

    private final Map<Class<? extends DomainEvent>, Object> eventsCaptured = Maps.newHashMap();
}
