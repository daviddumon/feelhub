package com.steambeat.domain.eventbus;

import com.google.common.eventbus.Subscribe;
import com.steambeat.domain.keyword.KeywordCreatedEvent;
import com.steambeat.domain.opinion.OpinionCreatedEvent;
import com.steambeat.domain.thesaurus.SteambeatLanguage;
import com.steambeat.repositories.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.*;
import org.junit.*;

import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

public class TestsDomainEventBus {

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public SystemTime time = SystemTime.fixed();

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

    @Test
    @Ignore
    public void blockTheSizeOfTheEventsList() {
        for (int i = 0; i < 1000; i++) {
            time.waitMinutes(1);
            DomainEventBus.INSTANCE.post(new KeywordCreatedEvent(TestFactories.keywords().newKeyword("value" + i, SteambeatLanguage.reference())));
        }

        final List<DomainEvent> events = DomainEventBus.INSTANCE.getEvents();
        assertThat(events.size(), is(100));
        assertThat(events.get(99).getDate(), is(time.getNow()));
    }

    private class SimpleEventListener {

        @Subscribe
        public void handle(final OpinionCreatedEvent opinionCreatedEvent) {

        }
    }
}
