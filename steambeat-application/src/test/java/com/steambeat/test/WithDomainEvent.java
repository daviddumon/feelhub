package com.steambeat.test;

import com.google.common.collect.Maps;
import com.steambeat.domain.*;
import org.junit.rules.ExternalResource;

import java.util.Map;

public class WithDomainEvent extends ExternalResource {

    @Override
    protected void before() throws Throwable {
        DomainEventBus.INSTANCE.stackOnSpread();
    }

    @Override
    protected void after() {
        DomainEventBus.INSTANCE.clear();
        DomainEventBus.INSTANCE.notifyOnSpread();
    }

    public <T extends DomainEvent> void capture(final Class<T> type) {
        DomainEventBus.INSTANCE.register(new DomainEventListener<T>() {
            @Override
            public void notify(final T event) {
                eventsCaptured.put(type, event);
            }
        }, type);
    }

    public <T extends DomainEvent> T lastEvent(final Class<T> type) {
        return (T) eventsCaptured.get(type);
    }

    private final Map<Class<? extends DomainEvent>, Object> eventsCaptured = Maps.newHashMap();
}
