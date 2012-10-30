package com.feelhub.repositories;

import com.feelhub.domain.illustration.*;
import com.feelhub.domain.reference.Reference;
import com.feelhub.test.*;
import com.mongodb.*;
import org.junit.*;

import java.util.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsIllustrationMongoRepositories extends TestWithMongoRepository {

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Before
    public void before() {
        repository = Repositories.illustrations();
    }

    @Test
    public void canPersistAnIllustration() {
        final Illustration illustration = new Illustration(UUID.randomUUID(), "link");

        repository.add(illustration);

        final DBObject illustrationFound = getUserFromDB(illustration.getId());
        assertThat(illustrationFound, notNullValue());
        assertThat(illustrationFound.get("_id"), is(illustration.getId()));
        assertThat(illustrationFound.get("link"), is((Object) illustration.getLink()));
        assertThat(illustrationFound.get("referenceId"), is((Object) illustration.getReferenceId()));
        assertThat(illustrationFound.get("creationDate"), is((Object) illustration.getCreationDate().getMillis()));
        assertThat(illustrationFound.get("lastModificationDate"), is((Object) illustration.getLastModificationDate().getMillis()));
    }

    @Test
    public void canGetAnIllustration() {
        final DBCollection collection = mongo.getCollection("illustration");
        final DBObject illustration = new BasicDBObject();
        final UUID id = UUID.randomUUID();
        illustration.put("_id", id);
        collection.insert(illustration);

        final Illustration illustrationFound = repository.get(id);

        assertThat(illustrationFound, notNullValue());
    }

    @Test
    public void canGetForAReference() {
        final Reference reference = TestFactories.references().newReference();
        TestFactories.illustrations().newIllustration(reference, "link1");
        TestFactories.illustrations().newIllustration(reference, "link2");
        TestFactories.illustrations().newIllustration(reference, "link3");

        final List<Illustration> illustrations = repository.forReferenceId(reference.getId());

        assertThat(illustrations, notNullValue());
        assertThat(illustrations.size(), is(3));
    }

    private DBObject getUserFromDB(final Object id) {
        final DBCollection collection = mongo.getCollection("illustration");
        final DBObject query = new BasicDBObject();
        query.put("_id", id);
        return collection.findOne(query);
    }

    private IllustrationRepository repository;
}
