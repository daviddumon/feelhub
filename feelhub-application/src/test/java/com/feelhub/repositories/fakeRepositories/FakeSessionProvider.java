package com.feelhub.repositories.fakeRepositories;

import com.feelhub.repositories.SessionProvider;
import org.mongolink.MongoSession;
import org.mongolink.MongoSessionManager;
import org.mongolink.Settings;
import org.mongolink.domain.mapper.ContextBuilder;
import org.mongolink.test.FongoDbFactory;

public class FakeSessionProvider extends SessionProvider {

    public FakeSessionProvider() {
        final ContextBuilder context = new ContextBuilder("com.feelhub.repositories.mapping");
        sessionManager = MongoSessionManager.create(context, Settings.defaultInstance()
                .withDbFactory(FongoDbFactory.class));
    }

    @Override
    public MongoSession get() {
        return sessionManager.createSession();
    }

    private  MongoSessionManager sessionManager;
}
