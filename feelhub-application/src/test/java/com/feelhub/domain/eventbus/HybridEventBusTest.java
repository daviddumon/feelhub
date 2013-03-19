package com.feelhub.domain.eventbus;

import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.http.HttpTopicCreatedEvent;
import com.feelhub.domain.topic.http.uri.ResolverResult;
import com.feelhub.domain.topic.real.RealTopicCreatedEvent;
import com.google.common.eventbus.Subscribe;
import com.google.common.util.concurrent.MoreExecutors;
import org.junit.*;

import java.util.UUID;

import static org.fest.assertions.Assertions.*;

public class HybridEventBusTest {

    @Before
    public void setUp() throws Exception {
        eventBus = new HybridEventBus(MoreExecutors.sameThreadExecutor());
    }


    @Test
    public void doNotPropagateToAsyncNow() {
        final AsyncEventListener listener = new AsyncEventListener();
        eventBus.register(listener);

        eventBus.post(new HttpTopicCreatedEvent(UUID.randomUUID(), new ResolverResult()));

        assertThat(listener.handled).isFalse();
    }

    @Test
    public void canProgagateToAsyncListeners() {
        final AsyncEventListener listener = new AsyncEventListener();
        eventBus.register(listener);
        eventBus.post(new HttpTopicCreatedEvent(UUID.randomUUID(), new ResolverResult()));

        eventBus.propagate();

        assertThat(listener.handled).isTrue();
    }

    @Test
    public void asynclyCreatedEventAreAlsoHandledBySyncListeners() {
        final AsyncEventListener listener = new AsyncEventListener();
        eventBus.register(listener);
        eventBus.post(new HttpTopicCreatedEvent(UUID.randomUUID(), new ResolverResult()));
        final ImmediateEventListener immediateListener = new ImmediateEventListener();
        eventBus.register(immediateListener);

        eventBus.propagate();

        assertThat(immediateListener.handled).isTrue();
    }

    private HybridEventBus eventBus;

    private class ImmediateEventListener {

        @Subscribe
        @Synchronize
        public void onEvent(final HttpTopicCreatedEvent event) {
            handled = true;
        }

        @Subscribe
        @Synchronize
        public void onEvent(final RealTopicCreatedEvent event) {
            handled = true;
        }

        public boolean handled;
    }


    private class AsyncEventListener {

        @Subscribe
        public void onEvent(final HttpTopicCreatedEvent event) {
            handled = true;
            eventBus.post(new RealTopicCreatedEvent(UUID.randomUUID(), FeelhubLanguage.reference()));
        }

        public boolean handled;
    }
}
