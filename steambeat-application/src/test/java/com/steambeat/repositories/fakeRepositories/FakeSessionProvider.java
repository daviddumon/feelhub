package com.steambeat.repositories.fakeRepositories;

import com.steambeat.repositories.SessionProvider;
import org.mongolink.*;
import org.mongolink.domain.mapper.ContextBuilder;
import org.mongolink.test.*;

public class FakeSessionProvider extends SessionProvider {

    @Override
    public MongoSession get() {
        final ContextBuilder context = new ContextBuilder("com.steambeat.repositories.mapping");
        manager = MongoSessionManager.create(context, Settings.defaultInstance()
                .withDbFactory(FakeDBFactory.class)
                .withCriteriaFactory(FakeCriteriaFactory.class));
        return manager.createSession();
    }

    @Override
    public void init() {

    }
}
