package com.steambeat.repositories;

import com.mongodb.*;
import com.steambeat.domain.Repository;
import com.steambeat.domain.topic.Topic;
import org.junit.*;

import java.net.*;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsTopicMongoRepository extends TestWithMongoRepository {

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
    }

    @Test
    public void canGetATopic() {
        final DBCollection collection = mongo.getCollection("topic");
        final DBObject topic = new BasicDBObject();
        final UUID id = UUID.randomUUID();
        topic.put("_id", id);
        collection.insert(topic);

        final Topic topicFound = (Topic) repo.get(id);

        assertThat(topicFound, notNullValue());
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

    private Repository<Topic> repo;
}
