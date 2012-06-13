package com.steambeat.repositories;

import com.mongodb.*;
import com.steambeat.domain.Repository;
import com.steambeat.domain.user.User;
import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsUserMongoRepository extends TestWithMongoRepository {

    @Before
    public void before() {
        repository = Repositories.users();
    }

    @Test
    public void canPersistAnUser() {
        final User user = new User();
        user.setEmail("email@email.com");
        user.setPassword("password");

        repository.add(user);

        final DBObject userFound = getUserFromDB();
        assertThat(userFound, notNullValue());
        assertThat(userFound.get("_id"), is(user.getId()));
        assertThat(userFound.get("_id"), is((Object)user.getEmail()));
        assertThat(userFound.get("password"), is((Object) user.getPassword()));
    }

    @Test
    public void canGetAnUser() {
        final DBCollection collection = mongo.getCollection("user");
        final DBObject user = new BasicDBObject();
        user.put("_id", "email@email.com");
        collection.insert(user);

        final User userFound = repository.get("email@email.com");

        assertThat(userFound, notNullValue());
    }

    private DBObject getUserFromDB() {
        final DBCollection collection = mongo.getCollection("user");
        final DBObject query = new BasicDBObject();
        query.put("_id", "email@email.com");
        return collection.findOne(query);
    }

    private Repository<User> repository;
}
