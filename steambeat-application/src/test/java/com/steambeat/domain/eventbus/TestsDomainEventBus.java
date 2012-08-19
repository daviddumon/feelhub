package com.steambeat.domain.eventbus;

import com.google.common.eventbus.*;
import com.steambeat.domain.opinion.OpinionCreatedEvent;
import org.junit.*;

import static org.mockito.Mockito.*;

public class TestsDomainEventBus {

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Test
    public void canUseAsyncEventBus() {
        final SimpleEventListener simpleEventListener = mock(SimpleEventListener.class);
        final OpinionCreatedEvent opinionCreatedEvent = new OpinionCreatedEvent(null);
        DomainEventBus.INSTANCE.register(simpleEventListener);

        DomainEventBus.INSTANCE.post(opinionCreatedEvent);

        verify(simpleEventListener, times(1)).handle(opinionCreatedEvent);
    }

    @Test
    public void canSpreadEventToMultipleListeners() {
        final SimpleEventListener listener1 = mock(SimpleEventListener.class);
        final SimpleEventListener listener2 = mock(SimpleEventListener.class);
        final OpinionCreatedEvent opinionCreatedEvent = new OpinionCreatedEvent(null);
        DomainEventBus.INSTANCE.register(listener1);
        DomainEventBus.INSTANCE.register(listener2);

        DomainEventBus.INSTANCE.post(opinionCreatedEvent);

        verify(listener1, times(1)).handle(opinionCreatedEvent);
        verify(listener2, times(1)).handle(opinionCreatedEvent);
    }

    private class SimpleEventListener {

        @Subscribe
        @AllowConcurrentEvents
        public void handle(OpinionCreatedEvent opinionCreatedEvent) {

        }
    }
}
