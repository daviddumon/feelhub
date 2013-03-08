package com.feelhub.domain.eventbus;

import org.junit.rules.ExternalResource;

public class WithDomainEvent extends ExternalResource {

    @Override
    protected void before() throws Throwable {
        fakeEventBus = new FakeEventBus();
        DomainEventBus.INSTANCE.setEventBus(fakeEventBus);
    }

    public <T extends DomainEvent> T lastEvent(final Class<T> type) {
        return fakeEventBus.lastEvent(type);
    }

    private FakeEventBus fakeEventBus;
}
