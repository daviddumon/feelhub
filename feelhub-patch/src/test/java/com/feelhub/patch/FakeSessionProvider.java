package com.feelhub.patch;

import com.feelhub.repositories.SessionProvider;
import org.mongolink.*;
import org.mongolink.domain.mapper.ContextBuilder;
import org.mongolink.test.FongoDbFactory;

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
        final ContextBuilder contextBuilder = new ContextBuilder("com.feelhub.repositories.mapping");
        manager = MongoSessionManager.create(contextBuilder, Settings.defaultInstance().withDbFactory(FongoDbFactory.class));
    }

    @Override
    public void close() {
        super.close();
    }

    MongoSession session;
}
