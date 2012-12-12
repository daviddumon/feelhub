package com.feelhub.repositories;

import com.feelhub.domain.topic.*;
import com.feelhub.domain.topic.geo.GeoTopic;
import com.feelhub.domain.topic.real.RealTopic;
import com.feelhub.domain.topic.web.WebTopic;
import com.feelhub.domain.topic.world.WorldTopic;
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
        final UUID id = UUID.randomUUID();
        final FakeTopic fakeTopic = new FakeTopic(id);

        repo.add(fakeTopic);

        final DBObject topicFound = getTopic(id);
        assertThat(topicFound).isNotNull();
        assertThat(topicFound.get("_id")).isEqualTo(id);
        assertThat(topicFound.get("currentId")).isEqualTo(id);
        assertThat(topicFound.get("creationDate")).isEqualTo(fakeTopic.getCreationDate().getMillis());
        assertThat(topicFound.get("lastModificationDate")).isEqualTo(fakeTopic.getLastModificationDate().getMillis());
    }

    @Test
    public void canPersistWorldTopic() {
        final UUID id = UUID.randomUUID();
        final WorldTopic worldTopic = new WorldTopic(id);

        repo.add(worldTopic);

        final DBObject topicFound = getTopic(id);
        assertThat(topicFound).isNotNull();
        assertThat(topicFound.get("_id")).isEqualTo(id);
        assertThat(topicFound.get("__discriminator")).isEqualTo("WorldTopic");
    }

    @Test
    public void canPersistARealTopic() {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();

        repo.add(realTopic);

        final DBObject topicFound = getTopic(realTopic.getId());
        assertThat(topicFound).isNotNull();
        assertThat(topicFound.get("typeValue").toString()).isEqualTo(realTopic.getType().toString());
        assertThat(topicFound.get("userId")).isEqualTo(realTopic.getUserId());
        assertThat(topicFound.get("names")).isNotNull();
        assertThat(topicFound.get("descriptions")).isNotNull();
        assertThat(topicFound.get("subTypes")).isNotNull();
        assertThat(topicFound.get("urls")).isNotNull();
        assertThat(topicFound.get("illustrationLink")).isEqualTo(realTopic.getIllustrationLink());
        assertThat(topicFound.get("__discriminator")).isEqualTo("RealTopic");
    }

    @Test
    public void canPersistAWebTopic() {
        final WebTopic webTopic = TestFactories.topics().newCompleteWebTopic();

        repo.add(webTopic);

        final DBObject topicFound = getTopic(webTopic.getId());
        assertThat(topicFound).isNotNull();
        assertThat(topicFound.get("typeValue").toString()).isEqualTo(webTopic.getType().toString());
        assertThat(topicFound.get("userId")).isEqualTo(webTopic.getUserId());
        assertThat(topicFound.get("names")).isNotNull();
        assertThat(topicFound.get("descriptions")).isNotNull();
        assertThat(topicFound.get("subTypes")).isNotNull();
        assertThat(topicFound.get("urls")).isNotNull();
        assertThat(topicFound.get("illustrationLink")).isEqualTo(webTopic.getIllustrationLink());
        assertThat(topicFound.get("__discriminator")).isEqualTo("WebTopic");
    }

    @Test
    public void canPersistAGeoTopic() {
        final GeoTopic geoTopic = TestFactories.topics().newCompleteGeoTopic();

        repo.add(geoTopic);

        final DBObject topicFound = getTopic(geoTopic.getId());
        assertThat(topicFound).isNotNull();
        assertThat(topicFound.get("typeValue").toString()).isEqualTo(geoTopic.getType().toString());
        assertThat(topicFound.get("userId")).isEqualTo(geoTopic.getUserId());
        assertThat(topicFound.get("names")).isNotNull();
        assertThat(topicFound.get("descriptions")).isNotNull();
        assertThat(topicFound.get("subTypes")).isNotNull();
        assertThat(topicFound.get("urls")).isNotNull();
        assertThat(topicFound.get("illustrationLink")).isEqualTo(geoTopic.getIllustrationLink());
        assertThat(topicFound.get("__discriminator")).isEqualTo("GeoTopic");
    }

    @Test
    public void canGetAWorldTopic() {
        final DBCollection collection = mongo.getCollection("topic");
        final DBObject topic = new BasicDBObject();
        final UUID id = UUID.randomUUID();
        topic.put("_id", id);
        topic.put("__discriminator", "WorldTopic");
        collection.insert(topic);

        final WorldTopic topicFound = repo.getWorldTopic();

        assertThat(topicFound).isNotNull();
        assertThat(topicFound.getId()).isEqualTo(id);
    }

    @Test
    public void canGetAWebTopic() {
        final DBCollection collection = mongo.getCollection("topic");
        final DBObject topic = new BasicDBObject();
        final UUID id = UUID.randomUUID();
        topic.put("_id", id);
        topic.put("__discriminator", "WebTopic");
        collection.insert(topic);

        final WebTopic topicFound = repo.getWebTopic(id);

        assertThat(topicFound).isNotNull();
        assertThat(topicFound.getId()).isEqualTo(id);
    }

    @Test
    public void canGetARealTopic() {
        final DBCollection collection = mongo.getCollection("topic");
        final DBObject topic = new BasicDBObject();
        final UUID id = UUID.randomUUID();
        topic.put("_id", id);
        topic.put("__discriminator", "RealTopic");
        collection.insert(topic);

        final RealTopic topicFound = repo.getRealTopic(id);

        assertThat(topicFound).isNotNull();
        assertThat(topicFound.getId()).isEqualTo(id);
    }

    @Test
    public void canGetAGeoTopic() {
        final DBCollection collection = mongo.getCollection("topic");
        final DBObject topic = new BasicDBObject();
        final UUID id = UUID.randomUUID();
        topic.put("_id", id);
        topic.put("__discriminator", "GeoTopic");
        collection.insert(topic);

        final GeoTopic topicFound = repo.getGeoTopic(id);

        assertThat(topicFound).isNotNull();
        assertThat(topicFound.getId()).isEqualTo(id);
    }

    @Test
    public void bugCannotChangeDateOfTopic() {
        final UUID id = TestFactories.topics().newCompleteRealTopic().getId();
        final RealTopic realTopic = (RealTopic) repo.get(id);
        time.waitDays(1);

        realTopic.setLastModificationDate(time.getNow());

        assertThat(Repositories.topics().get(id).getLastModificationDate()).isEqualTo(time.getNow());
    }

    @Test
    public void canGetCurrentTopic() {
        final RealTopic realTopic1 = TestFactories.topics().newCompleteRealTopic();
        final RealTopic realTopic2 = TestFactories.topics().newCompleteRealTopic();
        final RealTopic realTopic3 = TestFactories.topics().newCompleteRealTopic();
        realTopic1.changeCurrentId(realTopic2.getId());
        realTopic2.changeCurrentId(realTopic3.getId());

        final Topic currentRealTopic = repo.getCurrentTopic(realTopic1.getId());

        assertThat(currentRealTopic).isEqualTo(realTopic3);
    }

    private DBObject getTopic(final UUID id) {
        final DBCollection collection = mongo.getCollection("topic");
        final DBObject query = new BasicDBObject();
        query.put("_id", id);
        return collection.findOne(query);
    }

    private TopicRepository repo;

    class FakeTopic extends Topic {
        FakeTopic(final UUID id) {
            super(id);
        }

        @Override
        public TopicType getType() {
            return null;
        }
    }
}
