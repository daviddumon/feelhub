package com.feelhub.repositories;

import com.feelhub.domain.topic.*;
import com.feelhub.test.*;
import com.mongodb.*;
import org.junit.*;

import java.util.UUID;

import static org.fest.assertions.Assertions.*;

public class TestsTopicMongoRepository extends TestWithMongoRepository {

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Before
    public void before() {
        repo = Repositories.topics();
    }

    @Test
    public void canPersistATopic() {
        final Topic topic = TestFactories.topics().newTopic();

        repo.add(topic);

        final DBCollection collection = mongo.getCollection("topic");
        final DBObject query = new BasicDBObject();
        query.put("_id", topic.getId());
        final DBObject topicFound = collection.findOne(query);
        assertThat(topicFound).isNotNull();
        assertThat(topicFound.get("_id")).isEqualTo(topic.getId());
        assertThat(TopicType.valueOf(topicFound.get("type").toString())).isEqualTo(topic.getType());
        assertThat(topicFound.get("subTypes")).isNotNull();
        assertThat(topicFound.get("urls")).isNotNull();
        assertThat(topicFound.get("descriptions")).isNotNull();
        assertThat(topicFound.get("creationDate")).isEqualTo(topic.getCreationDate().getMillis());
        assertThat(topicFound.get("lastModificationDate")).isEqualTo(topic.getLastModificationDate().getMillis());
    }

    @Test
    public void canGetATopic() {
        final DBCollection collection = mongo.getCollection("topic");
        final DBObject topic = new BasicDBObject();
        final UUID id = UUID.randomUUID();
        topic.put("_id", id);
        collection.insert(topic);

        final Topic topicFound = repo.get(id);

        assertThat(topicFound).isNotNull();
    }

    @Test
    public void bugCannotChangeDateOfTopic() {
        final UUID id = TestFactories.topics().newTopic().getId();

        final Topic topic = repo.get(id);
        time.waitDays(1);
        topic.setLastModificationDate(time.getNow());

        assertThat(Repositories.topics().get(id).getLastModificationDate()).isEqualTo(time.getNow());
    }

    @Test
    public void canGetWorld() {
        final Topic topic = TestFactories.topics().newWorld();

        final Topic world = repo.world();

        assertThat(world).isNotNull();
    }

    private TopicRepository repo;
}
