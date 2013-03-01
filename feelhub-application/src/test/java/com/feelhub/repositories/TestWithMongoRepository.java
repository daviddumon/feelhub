package com.feelhub.repositories;

import com.mongodb.DB;
import org.junit.*;
import org.mongolink.MongoSession;
import org.mongolink.test.MongolinkRule;

import java.net.UnknownHostException;

public class TestWithMongoRepository {

    public DB getMongo() {
        return session.getDb();
    }

    @Rule
    public MongolinkRule mongolink = MongolinkRule.withPackage("com.feelhub.repositories.mapping");

    @Before
    public void beforeMongoRepository() throws UnknownHostException {
        session = mongolink.getCurrentSession();
        provider = IdentitySessionProvider(session);
        final MongoRepositories mongoRepos = new MongoRepositories(provider);
        Repositories.initialize(mongoRepos);
    }


    private SessionProvider IdentitySessionProvider(final MongoSession session) {
        return new SessionProvider() {
            @Override
            public MongoSession get() {
                return session;
            }
        };
    }

    public SessionProvider getProvider() {
        return provider;
    }

    private SessionProvider provider;
    private MongoSession session;
}
