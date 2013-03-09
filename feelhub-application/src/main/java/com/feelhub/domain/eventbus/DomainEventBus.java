package com.feelhub.domain.eventbus;

public enum DomainEventBus {

    INSTANCE;

    public void propagate() {
        eventBus.propagate();
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

    private HybridEventBus eventBus;

}
