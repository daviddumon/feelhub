package com.steambeat.repositories;

import com.mongodb.*;
import com.steambeat.domain.Repository;
import com.steambeat.domain.subject.Subject;
import com.steambeat.domain.subject.steam.Steam;
import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsSteamMongoRepository extends TestWithMongoRepository {

    @Before
    public void before() {
        repo = Repositories.subjects();
    }

    @Test
    public void canPersist() {
        final Steam steam = new Steam();

        repo.add(steam);

        final DBCollection collection = mongo.getCollection("subject");
        final DBObject query = new BasicDBObject();
        query.put("_id", steam.getId());
        final DBObject steamFound = collection.findOne(query);
        assertThat(steamFound, notNullValue());
        assertThat(steamFound.get("_id"), is((Object) steam.getId()));
    }

    @Test
    public void canGet() {
        final DBCollection collection = mongo.getCollection("subject");
        final DBObject steam = new BasicDBObject();
        steam.put("_id", "steam");
        steam.put("__discriminator", "Steam");
        collection.insert(steam);

        final Subject steamFound = repo.get("steam");

        assertThat(steamFound, notNullValue());
    }

    private Repository<Subject> repo;
}
