package com.feelhub.domain.eventbus;

import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.SystemTime;
import org.junit.*;

public class TestsDomainEventBus {

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Test
    public void canUseAsyncEventBus() {
        //final SimpleEventListener simpleEventListener = mock(SimpleEventListener.class);
        //final FeelingCreatedEvent feelingCreatedEvent = new FeelingCreatedEvent(null);
        //DomainEventBus.INSTANCE.register(simpleEventListener);
        //
        //DomainEventBus.INSTANCE.post(feelingCreatedEvent);
        //
        //verify(simpleEventListener, times(1)).handle(feelingCreatedEvent);
    }

    @Test
    public void canSpreadEventToMultipleListeners() {
        //final SimpleEventListener listener1 = mock(SimpleEventListener.class);
        //final SimpleEventListener listener2 = mock(SimpleEventListener.class);
        //final FeelingCreatedEvent feelingCreatedEvent = new FeelingCreatedEvent(null);
        //DomainEventBus.INSTANCE.register(listener1);
        //DomainEventBus.INSTANCE.register(listener2);
        //
        //DomainEventBus.INSTANCE.post(feelingCreatedEvent);
        //
        //verify(listener1, times(1)).handle(feelingCreatedEvent);
        //verify(listener2, times(1)).handle(feelingCreatedEvent);
    }

    @Test
    @Ignore
    public void blockTheSizeOfTheEventsList() {
        //for (int i = 0; i < 1000; i++) {
        //    time.waitMinutes(1);
        //    DomainEventBus.INSTANCE.post(new FeelingRequestEvent(TestFactories.keywords().newKeyword("value" + i, FeelhubLanguage.reference())));
        //}
        //
        //final List<DomainEvent> events = DomainEventBus.INSTANCE.getEvents();
        //assertThat(events.size(), is(100));
        //assertThat(events.get(99).getDate(), is(time.getNow()));
    }

    //private class SimpleEventListener {
    //
    //    @Subscribe
    //    public void handle(final FeelingCreatedEvent feelingCreatedEvent) {
    //
    //    }
    //}
}
