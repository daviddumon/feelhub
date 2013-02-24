package com.feelhub.domain.eventbus;

import com.google.common.collect.Lists;
import com.google.common.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public enum DomainEventBus {

    INSTANCE;

    public void progateOnPost() {
        propagateOnPost = true;
    }

    public void propagate() {
        ArrayList<DomainEvent> eventsToPost = Lists.newArrayList(currentEvents.get());
        for (DomainEvent domainEvent : eventsToPost) {
            eventBus.post(domainEvent);
        }
        currentEvents.get().removeAll(eventsToPost);
        if(!currentEvents.get().isEmpty()) {
            propagate();
        }
    }

    private DomainEventBus() {
        eventBus = new EventBus();
    }

    public void setEventBus(final EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void register(final Object listener) {
        eventBus.register(listener);
    }

    public void post(final DomainEvent event) {
        currentEvents.get().add(event);
        if(propagateOnPost) {
            propagate();
        }
    }

    public List<DomainEvent> getEvents() {
        return Lists.newArrayList();
    }

    private EventBus eventBus;
    private final ThreadLocal<List<DomainEvent>> currentEvents = new ThreadLocal<List<DomainEvent>>() {
        @Override
        protected List<DomainEvent> initialValue() {
            return Lists.newArrayList();
        }
    };
    private boolean propagateOnPost;
}
