package com.feelhub.domain.eventbus;

import com.feelhub.domain.feeling.*;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.SystemTime;
import com.google.common.eventbus.Subscribe;
import org.junit.*;

import java.util.UUID;

import static org.mockito.Mockito.*;

public class DomainEventBusTest {

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Test
    public void canUseAsyncEventBus() {
        final SimpleEventListener simpleEventListener = mock(SimpleEventListener.class);
        final SentimentAddedEvent sentimentAddedEvent = new SentimentAddedEvent(new Sentiment(UUID.randomUUID(), SentimentValue.bad));
        DomainEventBus.INSTANCE.register(simpleEventListener);

        DomainEventBus.INSTANCE.post(sentimentAddedEvent);

        verify(simpleEventListener, times(1)).handle(sentimentAddedEvent);
    }

    @Test
    public void canSpreadEventToMultipleListeners() {
        final SimpleEventListener listener1 = mock(SimpleEventListener.class);
        final SimpleEventListener listener2 = mock(SimpleEventListener.class);
        final SentimentAddedEvent feelingCreatedEvent = new SentimentAddedEvent(new Sentiment(UUID.randomUUID(), SentimentValue.bad));
        DomainEventBus.INSTANCE.register(listener1);
        DomainEventBus.INSTANCE.register(listener2);

        DomainEventBus.INSTANCE.post(feelingCreatedEvent);

        verify(listener1, times(1)).handle(feelingCreatedEvent);
        verify(listener2, times(1)).handle(feelingCreatedEvent);
    }

    private class SimpleEventListener {

        @Subscribe
        public void handle(final SentimentAddedEvent sentimentAddedEvent) {

        }
    }
}
