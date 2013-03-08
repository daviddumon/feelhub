package com.feelhub.domain.eventbus;

import com.feelhub.domain.topic.http.HttpTopicCreatedEvent;
import com.feelhub.domain.topic.http.uri.ResolverResult;
import com.feelhub.domain.topic.real.RealTopicCreatedEvent;
import com.google.common.eventbus.Subscribe;
import com.google.common.util.concurrent.MoreExecutors;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.fest.assertions.Assertions.assertThat;

public class HybridEventBusTest {

    @Before
    public void setUp() throws Exception {
        eventBus = new HybridEventBus(MoreExecutors.sameThreadExecutor());

    }

    @Test
    public void canPropagateNow() {
        ImmediateEventListener listener = new ImmediateEventListener();
        eventBus.register(listener);

        eventBus.post(new HttpTopicCreatedEvent(UUID.randomUUID(), new ResolverResult()));

        assertThat(listener.handled).isTrue();
    }

    @Test
    public void doNotPropagateToAsyncNow() {
        AsyncEventListener listener = new AsyncEventListener();
        eventBus.register(listener);

        eventBus.post(new HttpTopicCreatedEvent(UUID.randomUUID(), new ResolverResult()));

        assertThat(listener.handled).isFalse();
    }

    @Test
    public void canProgagateToAsyncListeners() {
        AsyncEventListener listener = new AsyncEventListener();
        eventBus.register(listener);
        eventBus.post(new HttpTopicCreatedEvent(UUID.randomUUID(), new ResolverResult()));

        eventBus.propagateAsync();

        assertThat(listener.handled).isTrue();
    }

    @Test
    public void asynclyCreatedEventAreAlsoHandledBySyncListeners() {
        AsyncEventListener listener = new AsyncEventListener();
        eventBus.register(listener);
        eventBus.post(new HttpTopicCreatedEvent(UUID.randomUUID(), new ResolverResult()));
        ImmediateEventListener immediateListener = new ImmediateEventListener();
        eventBus.register(immediateListener);

        eventBus.propagateAsync();

        assertThat(immediateListener.handled).isTrue();
    }

    private HybridEventBus eventBus;

    private class ImmediateEventListener {

        @Subscribe
        @Immediate
        public void onEvent(HttpTopicCreatedEvent event) {
            handled = true;
        }

        @Subscribe
        @Immediate
        public void onEvent(RealTopicCreatedEvent event) {
            handled = true;
        }

        public boolean handled;
    }


    private class AsyncEventListener {

        @Subscribe
        public void onEvent(HttpTopicCreatedEvent event) {
            handled = true;
            eventBus.post(new RealTopicCreatedEvent(UUID.randomUUID()));
        }

        public boolean handled;
    }
}
