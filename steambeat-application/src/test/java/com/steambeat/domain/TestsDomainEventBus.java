package com.steambeat.domain;

import com.steambeat.domain.opinion.OpinionPostedEvent;
import com.steambeat.domain.subject.feed.FeedCreatedEvent;
import com.steambeat.test.WithDomainEvent;
import org.junit.*;
import org.mockito.internal.verification.VerificationModeFactory;

import static org.mockito.Mockito.*;

public class TestsDomainEventBus {

    @Rule
    public WithDomainEvent e = new WithDomainEvent();

    @Test
    public void canSpreadEventToMultipleListeners() {
        DomainEventBus.INSTANCE.notifyOnSpread();

        final DomainEventListener<OpinionPostedEvent> listener1 = mock(DomainEventListener.class);
        final DomainEventListener<OpinionPostedEvent> listener2 = mock(DomainEventListener.class);
        DomainEventBus.INSTANCE.register(listener1, OpinionPostedEvent.class);
        DomainEventBus.INSTANCE.register(listener2, OpinionPostedEvent.class);
        final OpinionPostedEvent event = new OpinionPostedEvent(null, null);

        DomainEventBus.INSTANCE.spread(event);

        verify(listener1).notify(event);
        verify(listener2).notify(event);
    }

    @Test
    public void canRegisterForOneEventType() {
        final DomainEventListener<OpinionPostedEvent> listener = mock(DomainEventListener.class);
        final DomainEventListener<FeedCreatedEvent> listener2 = mock(DomainEventListener.class);
        DomainEventBus.INSTANCE.register(listener, OpinionPostedEvent.class);
        DomainEventBus.INSTANCE.register(listener2, FeedCreatedEvent.class);
        final OpinionPostedEvent event = new OpinionPostedEvent(null, null);
        DomainEventBus.INSTANCE.spread(event);

        DomainEventBus.INSTANCE.flush();

        verify(listener).notify(event);
        verify(listener2, VerificationModeFactory.times(0)).notify(any(FeedCreatedEvent.class));
    }
}
