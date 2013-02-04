package com.feelhub.patch;

import com.feelhub.repositories.SessionProvider;
import org.mongolink.MongoSession;
import org.mongolink.MongoSessionManager;
import org.mongolink.Settings;
import org.mongolink.domain.mapper.ContextBuilder;

public class FakeSessionProvider extends SessionProvider {

    @Override
    public MongoSession get() {
        if (session == null) {
            session = manager.createSession();
        }
        return session;
    }

    @Override
    public void init() {
        ContextBuilder contextBuilder = new ContextBuilder("com.feelhub.repositories.mapping");
        manager = MongoSessionManager.create(contextBuilder, Settings.defaultInstance().withDbFactory(FongoDBFactory.class));
    }

    @Override
    public void close() {
        super.close();
    }

    MongoSession session;
}
