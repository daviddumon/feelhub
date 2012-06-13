package com.steambeat.repositories;

import com.mongodb.*;
import com.steambeat.domain.Repository;
import com.steambeat.domain.session.Session;
import org.junit.*;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsSessionMongoRepository extends TestWithMongoRepository {

    @Before
    public void before() {
        repository = Repositories.sessions();
    }

    @Test
    public void canPersistSession() {
        final Session session = new Session();
        session.setEmail("mail@mail.com");
        final UUID token = UUID.randomUUID();
        session.setToken(token);

        repository.add(session);

        final DBObject sessionFound = getSessionFromDB();
        assertThat(sessionFound, notNullValue());
        assertThat(sessionFound.get("_id"), is(session.getId()));
        assertThat(sessionFound.get("_id"), is((Object) session.getEmail()));
        assertThat(sessionFound.get("token"), is((Object) session.getToken()));
    }

    @Test
    public void canGetASession() {
        final DBCollection collection = mongo.getCollection("session");
        final DBObject session = new BasicDBObject();
        session.put("_id", "email@email.com");
        collection.insert(session);

        final Session sessionFound = repository.get("email@email.com");

        assertThat(sessionFound, notNullValue());
    }

    private DBObject getSessionFromDB() {
        final DBCollection collection = mongo.getCollection("session");
        final DBObject query = new BasicDBObject();
        query.put("_id", "mail@mail.com");
        return collection.findOne(query);
    }

    private Repository<Session> repository;
}
