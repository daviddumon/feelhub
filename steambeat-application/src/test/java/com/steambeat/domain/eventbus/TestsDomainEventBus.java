package com.steambeat.domain.eventbus;

import com.google.common.eventbus.*;
import com.steambeat.domain.opinion.OpinionPostedEvent;
import org.junit.*;

import static org.mockito.Mockito.*;

public class TestsDomainEventBus {

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Test
    public void canUseAsyncEventBus() {
        final SimpleEventListener simpleEventListener = mock(SimpleEventListener.class);
        final OpinionPostedEvent opinionPostedEvent = new OpinionPostedEvent(null);
        DomainEventBus.INSTANCE.register(simpleEventListener);

        DomainEventBus.INSTANCE.post(opinionPostedEvent);

        verify(simpleEventListener, times(1)).handle(opinionPostedEvent);
    }

    @Test
    public void canSpreadEventToMultipleListeners() {
        final SimpleEventListener listener1 = mock(SimpleEventListener.class);
        final SimpleEventListener listener2 = mock(SimpleEventListener.class);
        final OpinionPostedEvent opinionPostedEvent = new OpinionPostedEvent(null);
        DomainEventBus.INSTANCE.register(listener1);
        DomainEventBus.INSTANCE.register(listener2);

        DomainEventBus.INSTANCE.post(opinionPostedEvent);

        verify(listener1, times(1)).handle(opinionPostedEvent);
        verify(listener2, times(1)).handle(opinionPostedEvent);
    }

    private class SimpleEventListener {

        @Subscribe
        @AllowConcurrentEvents
        public void handle(OpinionPostedEvent opinionPostedEvent) {

        }
    }
}
