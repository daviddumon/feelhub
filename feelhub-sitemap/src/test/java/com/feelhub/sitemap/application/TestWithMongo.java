package com.feelhub.sitemap.application;

import com.feelhub.repositories.MongoRepositories;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.SessionProvider;
import com.mongodb.DB;
import org.junit.Before;
import org.junit.Rule;
import org.mongolink.MongoSession;
import org.mongolink.MongoSessionManager;
import org.mongolink.Settings;
import org.mongolink.domain.mapper.ContextBuilder;
import org.mongolink.test.FongoDbFactory;
import org.mongolink.test.MongolinkRule;

import java.net.UnknownHostException;

public class TestWithMongo {

    @Before
    public void beforeMongoRepository() throws UnknownHostException {
        ContextBuilder contextBuilder = new ContextBuilder("com.feelhub.repositories.mapping");
        sessionManager = MongoSessionManager.create(contextBuilder, Settings.defaultInstance().withDbFactory(FongoDbFactory.class));
    }

    public MongoSession newSession() {
        MongoSession session = sessionManager.createSession();
        final MongoRepositories mongoRepos = new MongoRepositories(IdentitySessionProvider(session));
        Repositories.initialize(mongoRepos);
        return session;
    }

    private SessionProvider IdentitySessionProvider(final MongoSession session) {
        return new SessionProvider() {
            @Override
            public MongoSession get() {
                return session;
            }
        };
    }

    private MongoSessionManager sessionManager;
}
