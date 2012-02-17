package com.steambeat.repositories;

import com.mongodb.*;
import com.steambeat.domain.Repository;
import com.steambeat.domain.analytics.Association;
import com.steambeat.domain.analytics.identifiers.Identifier;
import com.steambeat.domain.analytics.identifiers.uri.Uri;
import org.junit.*;

import java.net.*;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@SuppressWarnings("unchecked")
public class TestsAssociationMongoRepository extends TestWithMongoRepository {

    @Before
    public void before() throws UnknownHostException {
        repo = Repositories.associations();
    }

    @Test
    public void canPersist() throws UnknownHostException, MongoException, MalformedURLException {
        final Identifier identifier = new Uri("www.lemonde.fr");
        final UUID uuid = UUID.randomUUID();
        final Association association = new Association(identifier, uuid);

        repo.add(association);

        final DBObject uriFound = getAssociationFromDB();
        assertThat(uriFound, notNullValue());
        assertThat(uriFound.get("_id"), is((Object) identifier.toString()));
        assertThat(uriFound.get("subjectId"), is((Object) uuid));
    }

    private DBObject getAssociationFromDB() {
        final DBCollection collection = mongo.getCollection("association");
        final DBObject query = new BasicDBObject();
        query.put("_id", "http://www.lemonde.fr");
        return collection.findOne(query);
    }

    @Test
    public void canGet() {
        final DBCollection collection = mongo.getCollection("association");
        final DBObject uri = new BasicDBObject();
        uri.put("_id", "liberation.fr");
        collection.insert(uri);

        final Association associationFound = repo.get("liberation.fr");

        assertThat(associationFound, notNullValue());
    }

    private Repository<Association> repo;
}
