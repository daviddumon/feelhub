package com.feelhub.domain.eventbus;

import com.google.common.collect.Queues;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.common.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;

public class HybridEventBus {

    public HybridEventBus(Executor executor) {
        this.eventBus = new AsyncEventBus("async event bus", executor);
    }

    public void register(Object listener) {
        if (isImmediate(listener)) {
            //syncEventBus.register(listener);
        }
        eventBus.register(listener);
    }

    private boolean isImmediate(Object listener) {
        Class<?> clazz = listener.getClass();
        Set<? extends Class<?>> supers = TypeToken.of(clazz).getTypes().rawTypes();

        for (Method method : clazz.getMethods()) {
            for (Class<?> c : supers) {
                try {
                    Method m = c.getMethod(method.getName(), method.getParameterTypes());
                    if (m.isAnnotationPresent(Subscribe.class) && m.getAnnotation(Immediate.class) != null) {
                        return true;
                    }
                } catch (NoSuchMethodException ignored) {

                }
            }
        }
        return false;
    }

    public void post(Object event) {
        currentEvents.get().add(event);
    }

    public void propagate() {
        ConcurrentLinkedQueue<Object> events = currentEvents.get();
        while (!events.isEmpty()) {
            LOGGER.debug("Propagating {}", currentEvents.get().peek());
            eventBus.post(events.poll());
        }
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(HybridEventBus.class);
    private final ThreadLocal<ConcurrentLinkedQueue<Object>> currentEvents = new ThreadLocal<ConcurrentLinkedQueue<Object>>() {
        @Override
        protected ConcurrentLinkedQueue<Object> initialValue() {
            return Queues.newConcurrentLinkedQueue();
        }
    };
    private final EventBus eventBus;
}
