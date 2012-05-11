package com.steambeat.repositories;

import com.mongodb.*;
import com.steambeat.domain.association.*;
import com.steambeat.domain.association.tag.Tag;
import com.steambeat.domain.association.uri.Uri;
import com.steambeat.domain.thesaurus.Language;
import com.steambeat.test.testFactories.TestFactories;
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
        final Language french = Language.forString("french");
        final Association association = new Association(identifier, uuid, french);

        repo.add(association);

        final DBObject associationFound = getAssociationFromDB();
        assertThat(associationFound, notNullValue());
        assertThat(associationFound.get("identifier"), is((Object) identifier.toString()));
        assertThat(UUID.fromString(associationFound.get("subjectId").toString()), is((Object) uuid));
        assertThat(associationFound.get("language"), is((Object) french));
    }

    private DBObject getAssociationFromDB() {
        final DBCollection collection = mongo.getCollection("association");
        final DBObject query = new BasicDBObject();
        query.put("identifier", "http://www.lemonde.fr");
        return collection.findOne(query);
    }

    @Test
    public void canGet() {
        final DBCollection collection = mongo.getCollection("association");
        final DBObject association = new BasicDBObject();
        final UUID uuid = UUID.randomUUID();
        association.put("_id", uuid);
        collection.insert(association);

        final Association associationFound = repo.get(uuid);

        assertThat(associationFound, notNullValue());
    }

    @Test
    public void canGetForTag() {
        final Tag tag = new Tag("tag");
        TestFactories.associations().newAssociation(tag, UUID.randomUUID(), Language.forString("french"));

        final Association associationFound = repo.forIdentifier(tag);

        assertThat(associationFound, notNullValue());
    }

    @Test
    public void canGetForUri() {
        final Uri uri = new Uri("http://www.mongo.com");
        TestFactories.associations().newAssociation(uri, UUID.randomUUID(), Language.forString("french"));

        final Association associationFound = repo.forIdentifier(uri);

        assertThat(associationFound, notNullValue());
    }

    @Test
    @Ignore
    public void canUseRegexForTagMatching() {
        final Tag tag = new Tag("tag");
        TestFactories.associations().newAssociation(tag, UUID.randomUUID(), Language.forString("french"));

        final Association associationFound = repo.forIdentifier(new Tag("/tag/"));

        assertThat(associationFound, notNullValue());
    }

    private AssociationRepository repo;
}
