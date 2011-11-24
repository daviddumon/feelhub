package com.steambeat.test.fakeRepositories;

import com.steambeat.repositories.SessionProvider;
import fr.bodysplash.mongolink.*;
import fr.bodysplash.mongolink.domain.mapper.ContextBuilder;
import fr.bodysplash.mongolink.test.*;

public class FakeSessionProvider extends SessionProvider {

    @Override
    public MongoSession get() {
        ContextBuilder context = new ContextBuilder("com.steambeat.repositories.mapping");
        final MongoSessionManager manager = MongoSessionManager.create(context, Settings.defaultInstance()
                .withDbFactory(FakeDBFactory.class)
                .withCriteriaFactory(FakeCriteriaFactory.class));
        return manager.createSession();
    }

    @Override
    public void init() {
        
    }
}
