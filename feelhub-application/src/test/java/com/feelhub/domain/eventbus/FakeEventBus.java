package com.feelhub.domain.eventbus;

import com.google.common.collect.Maps;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.Map;

public class FakeEventBus extends HybridEventBus {

    public FakeEventBus() {
        super(MoreExecutors.sameThreadExecutor());
    }

    @Override
    public void post(final Object event) {
        eventsCaptured.put((Class<? extends DomainEvent>) event.getClass(), event);
        super.post(event);
        super.propagate();
    }

    <T extends DomainEvent> T lastEvent(final Class<T> type) {
        return (T) eventsCaptured.get(type);
    }

    public final Map<Class<? extends DomainEvent>, Object> eventsCaptured = Maps.newHashMap();
}
