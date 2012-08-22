package com.steambeat.repositories;

import com.mongodb.*;
import com.steambeat.domain.Repository;
import com.steambeat.domain.reference.Reference;
import org.junit.*;

import java.net.*;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsReferenceMongoRepository extends TestWithMongoRepository {

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
        collection.insert(reference);

        final Reference referenceFound = (Reference) repo.get(id);

        assertThat(referenceFound, notNullValue());
    }

    //
    //@Test
    //public void bugCannotChangeDateOfSubject() {
    //    final UUID id = TestFactories.subjects().newWebPage().getId();
    //
    //    final Subject subject = Repositories.subjects().get(id);
    //    time.waitDays(1);
    //    subject.setLastModificationDate(time.getNow());
    //
    //    assertThat(Repositories.subjects().get(id).getLastModificationDate(), is(time.getNow()));
    //}

    private Repository<Reference> repo;
}
