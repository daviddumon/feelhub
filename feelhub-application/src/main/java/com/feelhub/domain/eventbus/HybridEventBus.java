package com.feelhub.domain.eventbus;

import com.google.common.collect.Queues;
import com.google.common.eventbus.*;
import com.google.common.reflect.TypeToken;
import com.google.common.util.concurrent.*;
import org.slf4j.*;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.*;

public class HybridEventBus {

    public HybridEventBus(final ExecutorService executor) {
        this.asyncEventBus = new AsyncEventBus("async event bus", executor);
        this.syncableEventBus = new AsyncEventBus("async event bus", new ListenableSyncExecutor(executor));
    }

    public void register(final Object listener) {
        if (isSynchronize(listener)) {
            syncableEventBus.register(listener);
        } else {
            asyncEventBus.register(listener);
        }
    }

    private boolean isSynchronize(final Object listener) {
        final Class<?> clazz = listener.getClass();
        final Set<? extends Class<?>> supers = TypeToken.of(clazz).getTypes().rawTypes();

        for (final Method method : clazz.getMethods()) {
            for (final Class<?> c : supers) {
                try {
                    final Method m = c.getMethod(method.getName(), method.getParameterTypes());
                    if (m.isAnnotationPresent(Subscribe.class) && m.getAnnotation(Synchronize.class) != null) {
                        return true;
                    }
                } catch (NoSuchMethodException ignored) {

                }
            }
        }
        return false;
    }

    public void post(final Object event) {
        currentEvents.get().add(event);
    }

    public void propagate() {
        propagateSyncable();
        propagateAsync();
    }

    private void propagateSyncable() {
        final ConcurrentLinkedQueue<Object> events = currentEvents.get();
        if (!events.isEmpty()) {
            LOGGER.debug("Propagating events to syncable listeners");
        }
        for (final Object event : events) {
            LOGGER.debug("Propagating {}", event);
            syncableEventBus.post(event);
        }
    }

    private void propagateAsync() {
        if (!currentEvents.get().isEmpty()) {
            LOGGER.debug("Propagating events to async listeners");
        }
        while (!currentEvents.get().isEmpty()) {
            LOGGER.debug("Propagating {}", currentEvents.get().peek());
            asyncEventBus.post(currentEvents.get().poll());
        }
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(HybridEventBus.class);
    private final ThreadLocal<ConcurrentLinkedQueue<Object>> currentEvents = new ThreadLocal<ConcurrentLinkedQueue<Object>>() {
        @Override
        protected ConcurrentLinkedQueue<Object> initialValue() {
            return Queues.newConcurrentLinkedQueue();
        }
    };

    private final EventBus asyncEventBus;
    private final EventBus syncableEventBus;


    private class ListenableSyncExecutor extends AbstractListeningExecutorService implements ListeningExecutorService {

        public ListenableSyncExecutor(final ExecutorService executor) {
            this.delegate = executor;
        }

        @Override
        public boolean awaitTermination(final long timeout, final TimeUnit unit)
                throws InterruptedException {
            return delegate.awaitTermination(timeout, unit);
        }

        @Override
        public boolean isShutdown() {
            return delegate.isShutdown();
        }

        @Override
        public boolean isTerminated() {
            return delegate.isTerminated();
        }

        @Override
        public void shutdown() {
            delegate.shutdown();
        }

        @Override
        public List<Runnable> shutdownNow() {
            return delegate.shutdownNow();
        }

        @Override
        public void execute(final Runnable command) {
            LOGGER.debug("Starting new syncable thread");
            final ListenableFutureTask<Object> listenableTask = ListenableFutureTask.create(command, null);
            delegate.execute(listenableTask);
            Futures.getUnchecked(listenableTask);
            LOGGER.debug("Syncable thread returned");
        }

        private final ExecutorService delegate;
    }
}
