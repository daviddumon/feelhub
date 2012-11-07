package com.feelhub.repositories;

import com.feelhub.domain.user.*;
import com.feelhub.test.SystemTime;
import com.mongodb.*;
import org.junit.*;

import java.util.UUID;

import static org.fest.assertions.Assertions.*;

public class TestsUserMongoRepository extends TestWithMongoRepository {

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Before
    public void before() {
        repository = Repositories.users();
    }

    @Test
    public void canPersistAnUser() {
        final String id = UUID.randomUUID().toString();
        final User user = new User(id);
        user.setEmail("email@email.com");
        user.setPassword("password");
        user.setFullname("fullname");
        user.setLanguageCode("en");

        repository.add(user);

        final DBObject userFound = getUserFromDB(id);
        assertThat(userFound).isNotNull();
        assertThat(userFound.get("_id").toString()).isEqualTo(user.getId());
        assertThat(userFound.get("email").toString()).isEqualTo(user.getEmail());
        assertThat(userFound.get("password")).isEqualTo(((Object) user.getPassword()));
        assertThat(userFound.get("fullname")).isEqualTo((Object) user.getFullname());
        assertThat(userFound.get("languageCode")).isEqualTo((Object) user.getLanguageCode());
        assertThat(userFound.get("active")).isEqualTo((Object) user.isActive());
        assertThat(userFound.get("secret")).isEqualTo((Object) user.getSecret());
        assertThat(userFound.get("creationDate")).isEqualTo((Object) time.getNow().getMillis());
        assertThat(userFound.get("lastModificationDate")).isEqualTo((Object) time.getNow().getMillis());
    }

    @Test
    public void canGetAnUser() {
        final DBCollection collection = mongo.getCollection("user");
        final DBObject user = new BasicDBObject();
        user.put("_id", "test");
        collection.insert(user);

        final User userFound = repository.get("test");

        assertThat(userFound).isNotNull();
    }

    @Test
    public void canGetByEmail() {
        final DBCollection collection = mongo.getCollection("user");
        final DBObject user = new BasicDBObject();
        user.put("_id", "test");
        user.put("email", "jb@test.com");
        collection.insert(user);

        final User userFound = repository.forEmail("jb@test.com");

        assertThat(userFound).isNotNull();
    }

    private DBObject getUserFromDB(final String id) {
        final DBCollection collection = mongo.getCollection("user");
        final DBObject query = new BasicDBObject();
        query.put("_id", id);
        return collection.findOne(query);
    }

    private UserRepository repository;
}
