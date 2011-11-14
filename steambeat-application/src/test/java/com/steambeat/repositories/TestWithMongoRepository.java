package com.steambeat.repositories;

import com.mongodb.FakeDB;
import fr.bodysplash.mongolink.MongoSession;
import fr.bodysplash.mongolink.test.FakePersistentContext;
import org.junit.*;

import java.net.UnknownHostException;

public class TestWithMongoRepository {

    @Rule
    public FakePersistentContext fakePersistentContext = new FakePersistentContext("com.steambeat.repositories.mapping");

    @Before
    public void beforeMongoRepository() throws UnknownHostException {
        final MongoSession session = fakePersistentContext.getSession();
        mongo = (FakeDB) session.getDb();
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

    protected FakeDB mongo;
    private SessionProvider provider;
}
