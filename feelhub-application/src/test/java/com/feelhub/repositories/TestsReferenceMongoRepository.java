package com.feelhub.repositories;

import com.feelhub.domain.reference.*;
import com.feelhub.test.*;
import com.mongodb.*;
import org.junit.*;

import java.net.*;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsReferenceMongoRepository extends TestWithMongoRepository {

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Before
    public void before() {
        repo = Repositories.references();
    }

    @Test
    public void canPersistAReference() throws UnknownHostException, MongoException, MalformedURLException {
        final UUID id = UUID.randomUUID();
        final Reference reference = new Reference(id);

        repo.add(reference);

        final DBCollection collection = mongo.getCollection("reference");
        final DBObject query = new BasicDBObject();
        query.put("_id", reference.getId());
        final DBObject referenceFound = collection.findOne(query);
        assertThat(referenceFound, notNullValue());
        assertThat(referenceFound.get("_id"), is((Object) reference.getId()));
        assertThat(referenceFound.get("creationDate"), is((Object) reference.getCreationDate().getMillis()));
        assertThat(referenceFound.get("lastModificationDate"), is((Object) reference.getLastModificationDate().getMillis()));
        assertThat(referenceFound.get("active"), is((Object) true));
    }

    @Test
    public void canGetAReference() {
        final DBCollection collection = mongo.getCollection("reference");
        final DBObject reference = new BasicDBObject();
        final UUID id = UUID.randomUUID();
        reference.put("_id", id);
        reference.put("active", true);
        collection.insert(reference);

        final Reference referenceFound = repo.get(id);

        assertThat(referenceFound, notNullValue());
    }

    @Test
    public void bugCannotChangeDateOfReference() {
        final UUID id = TestFactories.references().newReference().getId();

        final Reference reference = repo.get(id);
        time.waitDays(1);
        reference.setLastModificationDate(time.getNow());

        assertThat(Repositories.references().get(id).getLastModificationDate(), is(time.getNow()));
    }

    @Test
    public void canPersistAnOldReference() throws UnknownHostException, MongoException, MalformedURLException {
        final UUID id = UUID.randomUUID();
        final Reference reference = new Reference(id);
        reference.setActive(false);
        final UUID newId = TestFactories.references().newReference().getId();
        reference.setCurrentReferenceId(newId);

        repo.add(reference);

        final DBCollection collection = mongo.getCollection("reference");
        final DBObject query = new BasicDBObject();
        query.put("_id", reference.getId());
        final DBObject referenceFound = collection.findOne(query);
        assertThat(referenceFound, notNullValue());
        assertThat(referenceFound.get("_id"), is((Object) reference.getId()));
        assertThat(referenceFound.get("creationDate"), is((Object) reference.getCreationDate().getMillis()));
        assertThat(referenceFound.get("lastModificationDate"), is((Object) reference.getLastModificationDate().getMillis()));
        assertThat(referenceFound.get("active"), is((Object) false));
        assertThat(referenceFound.get("currentReferenceId"), is((Object) newId));
    }

    @Test
    public void canGetActiveReference() {
        final DBCollection collection = mongo.getCollection("reference");
        final DBObject firstReference = new BasicDBObject();
        final UUID firstId = UUID.randomUUID();
        firstReference.put("_id", firstId);
        firstReference.put("active", true);
        collection.insert(firstReference);
        final DBObject secondReference = new BasicDBObject();
        final UUID secondId = UUID.randomUUID();
        secondReference.put("_id", secondId);
        secondReference.put("active", false);
        secondReference.put("currentReferenceId", firstId);
        collection.insert(secondReference);
        final DBObject thirdReference = new BasicDBObject();
        final UUID thirdId = UUID.randomUUID();
        thirdReference.put("_id", thirdId);
        thirdReference.put("active", false);
        thirdReference.put("currentReferenceId", secondId);
        collection.insert(thirdReference);

        final Reference referenceFound = repo.getActive(thirdId);

        assertThat(referenceFound, notNullValue());
        assertThat(referenceFound.getId(), is(firstId));
    }

    @Test
    public void canGetAnInactiveReferenceWithSameCurrentRef() {
        final DBCollection collection = mongo.getCollection("reference");
        final DBObject reference = new BasicDBObject();
        final UUID id = UUID.randomUUID();
        reference.put("_id", id);
        reference.put("active", false);
        reference.put("currentReferenceId", id);
        collection.insert(reference);

        final Reference referenceFound = repo.getActive(id);

        assertThat(referenceFound, notNullValue());
        assertThat(referenceFound.getId(), is(id));
    }

    private ReferenceRepository repo;
}
