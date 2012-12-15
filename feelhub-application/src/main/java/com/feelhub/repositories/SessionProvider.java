package com.feelhub.repositories;

import com.feelhub.tools.FeelhubApplicationProperties;
import org.mongolink.*;
import org.mongolink.domain.mapper.ContextBuilder;

public class SessionProvider {

    public SessionProvider() {
    }

    public void init() {
        final FeelhubApplicationProperties props = new FeelhubApplicationProperties();
        final ContextBuilder context = new ContextBuilder("com.feelhub.repositories.mapping");
        manager = MongoSessionManager.create(context, props.getDbSettings());
    }

    public MongoSession get() {
        if (sessionContainer.get() == null) {
            sessionContainer.set(manager.createSession());
        }
        return sessionContainer.get();
    }

    public void start() {
        get().start();
    }

    public void stop() {
        get().stop();
        sessionContainer.remove();
    }

    public void close() {
        manager.close();
    }

    private final ThreadLocal<MongoSession> sessionContainer = new ThreadLocal<MongoSession>() {
        @Override
        protected MongoSession initialValue() {
            return manager.createSession();
        }
    };
    protected volatile MongoSessionManager manager;
}
