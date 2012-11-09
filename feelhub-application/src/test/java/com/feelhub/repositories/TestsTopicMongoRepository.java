package com.feelhub.repositories;

import com.feelhub.domain.topic.*;
import com.feelhub.test.*;
import com.mongodb.*;
import org.junit.*;

import java.net.*;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsTopicMongoRepository extends TestWithMongoRepository {

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Before
    public void before() {
        repo = Repositories.topics();
    }

    @Test
    public void canPersistATopic() throws UnknownHostException, MongoException, MalformedURLException {
        final UUID id = UUID.randomUUID();
        final Topic topic = new Topic(id);

        repo.add(topic);

        final DBCollection collection = mongo.getCollection("topic");
        final DBObject query = new BasicDBObject();
        query.put("_id", topic.getId());
        final DBObject topicFound = collection.findOne(query);
        assertThat(topicFound, notNullValue());
        assertThat(topicFound.get("_id"), is((Object) topic.getId()));
        assertThat(topicFound.get("creationDate"), is((Object) topic.getCreationDate().getMillis()));
        assertThat(topicFound.get("lastModificationDate"), is((Object) topic.getLastModificationDate().getMillis()));
        assertThat(topicFound.get("active"), is((Object) true));
    }

    @Test
    public void canGetATopic() {
        final DBCollection collection = mongo.getCollection("topic");
        final DBObject topic = new BasicDBObject();
        final UUID id = UUID.randomUUID();
        topic.put("_id", id);
        topic.put("active", true);
        collection.insert(topic);

        final Topic topicFound = repo.get(id);

        assertThat(topicFound, notNullValue());
    }

    @Test
    public void bugCannotChangeDateOfTopic() {
        final UUID id = TestFactories.topics().newTopic().getId();

        final Topic topic = repo.get(id);
        time.waitDays(1);
        topic.setLastModificationDate(time.getNow());

        assertThat(Repositories.topics().get(id).getLastModificationDate(), is(time.getNow()));
    }

    @Test
    public void canPersistAnOldTopic() throws UnknownHostException, MongoException, MalformedURLException {
        final UUID id = UUID.randomUUID();
        final Topic topic = new Topic(id);
        topic.setActive(false);
        final UUID newId = TestFactories.topics().newTopic().getId();
        topic.setCurrentTopicId(newId);

        repo.add(topic);

        final DBCollection collection = mongo.getCollection("topic");
        final DBObject query = new BasicDBObject();
        query.put("_id", topic.getId());
        final DBObject topicFound = collection.findOne(query);
        assertThat(topicFound, notNullValue());
        assertThat(topicFound.get("_id"), is((Object) topic.getId()));
        assertThat(topicFound.get("creationDate"), is((Object) topic.getCreationDate().getMillis()));
        assertThat(topicFound.get("lastModificationDate"), is((Object) topic.getLastModificationDate().getMillis()));
        assertThat(topicFound.get("active"), is((Object) false));
        assertThat(topicFound.get("currentTopicId"), is((Object) newId));
    }

    @Test
    public void canGetActiveTopic() {
        final DBCollection collection = mongo.getCollection("topic");
        final DBObject firstTopic = new BasicDBObject();
        final UUID firstId = UUID.randomUUID();
        firstTopic.put("_id", firstId);
        firstTopic.put("active", true);
        collection.insert(firstTopic);
        final DBObject secondTopic = new BasicDBObject();
        final UUID secondId = UUID.randomUUID();
        secondTopic.put("_id", secondId);
        secondTopic.put("active", false);
        secondTopic.put("currentTopicId", firstId);
        collection.insert(secondTopic);
        final DBObject thirdTopic = new BasicDBObject();
        final UUID thirdId = UUID.randomUUID();
        thirdTopic.put("_id", thirdId);
        thirdTopic.put("active", false);
        thirdTopic.put("currentTopicId", secondId);
        collection.insert(thirdTopic);

        final Topic topicFound = repo.getActive(thirdId);

        assertThat(topicFound, notNullValue());
        assertThat(topicFound.getId(), is(firstId));
    }

    @Test
    public void canGetAnInactiveTopicWithSameCurrentTopic() {
        final DBCollection collection = mongo.getCollection("topic");
        final DBObject topic = new BasicDBObject();
        final UUID id = UUID.randomUUID();
        topic.put("_id", id);
        topic.put("active", false);
        topic.put("currentTopicId", id);
        collection.insert(topic);

        final Topic topicFound = repo.getActive(id);

        assertThat(topicFound, notNullValue());
        assertThat(topicFound.getId(), is(id));
    }

    private TopicRepository repo;
}
