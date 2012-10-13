package com.steambeat.repositories;

import com.mongodb.*;
import com.steambeat.domain.session.*;
import com.steambeat.domain.user.User;
import com.steambeat.test.*;
import org.joda.time.DateTime;
import org.junit.*;

import java.util.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsSessionMongoRepository extends TestWithMongoRepository {

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Before
    public void before() {
        repository = Repositories.sessions();
    }

    @Test
    public void canPersistSession() {
        final Session session = new Session(new DateTime().plusHours(1));
        session.setEmail("mail@mail.com");

        repository.add(session);

        final DBObject sessionFound = getSessionFromDB(session.getId());
        assertThat(sessionFound, notNullValue());
        assertThat(sessionFound.get("_id"), is(session.getId()));
        assertThat(sessionFound.get("_id"), is((Object) session.getToken()));
        assertThat(sessionFound.get("email"), is((Object) session.getEmail()));
        assertThat(sessionFound.get("creationDate"), is((Object) time.getNow().getMillis()));
        assertThat(sessionFound.get("expirationDate"), is((Object) session.getExpirationDate().getMillis()));
        assertThat(sessionFound.get("lastModificationDate"), is((Object) time.getNow().getMillis()));
    }

    private DBObject getSessionFromDB(final Object token) {
        final DBCollection collection = mongo.getCollection("session");
        final DBObject query = new BasicDBObject();
        query.put("_id", token);
        return collection.findOne(query);
    }

    @Test
    public void canGetASession() {
        final DBCollection collection = mongo.getCollection("session");
        final DBObject session = new BasicDBObject();
        final UUID id = UUID.randomUUID();
        session.put("_id", id);
        collection.insert(session);

        final Session sessionFound = repository.get(id);

        assertThat(sessionFound, notNullValue());
    }

    @Test
    public void canGetForAUser() {
        final User user = TestFactories.users().createFakeActiveUser("mail@mail.com");
        TestFactories.sessions().createSessionFor(user);
        TestFactories.sessions().createSessionFor(user);
        TestFactories.sessions().createSessionFor(user);

        final List<Session> sessions = repository.forUser(user);

        assertThat(sessions.size(), is(3));
    }

    private SessionRepository repository;
}
