package com.steambeat.repositories;

import com.steambeat.tools.SteambeatApplicationProperties;
import fr.bodysplash.mongolink.*;
import fr.bodysplash.mongolink.domain.mapper.ContextBuilder;

public class SessionProvider {

    public SessionProvider() {
    }

    public void init() {
        final SteambeatApplicationProperties props = new SteambeatApplicationProperties();
        final ContextBuilder context = new ContextBuilder("com.steambeat.repositories.mapping");
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
    }

    public void close() {
        manager.close();
    }

    private final ThreadLocal<MongoSession> sessionContainer = new ThreadLocal<MongoSession>() {

    };
    private MongoSessionManager manager;
}
