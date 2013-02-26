package com.feelhub.domain.eventbus;

import com.google.common.collect.Maps;
import com.google.common.eventbus.EventBus;

import java.util.Map;

public class FakeEventBus extends EventBus {

    @Override
    public void post(Object event) {
        eventsCaptured.put((Class<? extends DomainEvent>) event.getClass(), event);
        super.post(event);
    }

    <T extends DomainEvent> T lastEvent(Class<T> type) {
        return (T) eventsCaptured.get(type);
    }

    public final Map<Class<? extends DomainEvent>, Object> eventsCaptured = Maps.newHashMap();
}
