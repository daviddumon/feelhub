package com.feelhub.sitemap.test;

import com.feelhub.repositories.*;
import org.junit.*;
import org.mongolink.*;
import org.mongolink.domain.mapper.ContextBuilder;
import org.mongolink.test.FongoDbFactory;

import java.net.UnknownHostException;

public class TestWithMongo {

    @Before
    public void beforeMongo() throws UnknownHostException {
        ContextBuilder contextBuilder = new ContextBuilder("com.feelhub.repositories.mapping");
        sessionManager = MongoSessionManager.create(contextBuilder, Settings.defaultInstance().withDbFactory(FongoDbFactory.class));
    }

    @After
    public void afterMongo() {
        FongoDbFactory.clean();
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
