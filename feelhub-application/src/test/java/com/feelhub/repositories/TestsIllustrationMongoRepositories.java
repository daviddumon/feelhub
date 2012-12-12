package com.feelhub.repositories;

import com.feelhub.domain.meta.*;
import com.feelhub.domain.topic.real.RealTopic;
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
        assertThat(illustrationFound.get("topicId"), is((Object) illustration.getTopicId()));
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
    public void canGetForATopic() {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        TestFactories.illustrations().newIllustration(realTopic.getId(), "link1");
        TestFactories.illustrations().newIllustration(realTopic.getId(), "link2");
        TestFactories.illustrations().newIllustration(realTopic.getId(), "link3");

        final List<Illustration> illustrations = repository.forTopicId(realTopic.getId());

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
