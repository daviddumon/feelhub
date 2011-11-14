package com.steambeat.repositories;

import com.steambeat.domain.Repository;
import com.steambeat.domain.subject.feed.*;
import com.steambeat.test.testFactories.TestFactories;
import com.mongodb.*;
import org.joda.time.DateTime;
import org.junit.*;

import java.net.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@SuppressWarnings("unchecked")
public class TestsAssociationMongoReposiroty extends TestWithMongoRepository {

    @Before
    public void before() throws UnknownHostException {
        repo = Repositories.associations();
    }

    @Test
    public void canPersist() throws UnknownHostException, MongoException, MalformedURLException {
        final Association association =
                new Association(new Uri("www.lemonde.fr"), TestFactories.canonicalUriFinder().thatFind(new Uri("http://www.lemonde.fr")));
        final DateTime creationDate = association.getExpirationDate();

        repo.add(association);

        final DBCollection collection = mongo.getCollection("association");
        final DBObject query = new BasicDBObject();
        query.put("_id", "http://www.lemonde.fr");
        final DBObject uriFound = collection.findOne(query);
        assertThat(uriFound, notNullValue());
        assertThat(uriFound.get("_id"), is((Object) "http://www.lemonde.fr"));
        assertThat(uriFound.get("canonicalUri"), is((Object) association.getCanonicalUri()));
        assertThat(uriFound.get("expirationDate"), is((Object) creationDate.getMillis()));
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
