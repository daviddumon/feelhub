package com.feelhub.repositories;

import com.feelhub.domain.topic.*;
import com.feelhub.domain.topic.ftp.FtpTopic;
import com.feelhub.domain.topic.geo.GeoTopic;
import com.feelhub.domain.topic.http.HttpTopic;
import com.feelhub.domain.topic.real.RealTopic;
import com.feelhub.domain.topic.world.WorldTopic;
import com.feelhub.test.*;
import com.mongodb.*;
import org.junit.*;

import java.util.UUID;

import static org.fest.assertions.Assertions.*;

public class TopicMongoRepositoryTest extends TestWithMongoRepository {

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
        fakeTopic.setIllustration("illustration");
        fakeTopic.setThumbnail("tb");

        repo.add(fakeTopic);

        final DBObject topicFound = getTopic(id);
        assertThat(topicFound).isNotNull();
        assertThat(topicFound.get("_id")).isEqualTo(id);
        assertThat(topicFound.get("currentId")).isEqualTo(id);
        assertThat(topicFound.get("creationDate")).isEqualTo(fakeTopic.getCreationDate().getMillis());
        assertThat(topicFound.get("lastModificationDate")).isEqualTo(fakeTopic.getLastModificationDate().getMillis());
        assertThat(topicFound.get("illustration")).isEqualTo(fakeTopic.getIllustration());
        assertThat(topicFound.get("thumbnail")).isEqualTo(fakeTopic.getThumbnail());
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
        assertThat(topicFound.get("uris")).isNotNull();
        assertThat(topicFound.get("__discriminator")).isEqualTo("RealTopic");
    }

    @Test
    public void canPersistAnHttpTopic() {
        final HttpTopic httpTopic = TestFactories.topics().newCompleteHttpTopic();

        repo.add(httpTopic);

        final DBObject topicFound = getTopic(httpTopic.getId());
        assertThat(topicFound).isNotNull();
        assertThat(topicFound.get("typeValue").toString()).isEqualTo(httpTopic.getType().toString());
        assertThat(topicFound.get("userId")).isEqualTo(httpTopic.getUserId());
        assertThat(topicFound.get("names")).isNotNull();
        assertThat(topicFound.get("descriptions")).isNotNull();
        assertThat(topicFound.get("subTypes")).isNotNull();
        assertThat(topicFound.get("uris")).isNotNull();
        assertThat(topicFound.get("illustration")).isEqualTo(httpTopic.getIllustration());
        assertThat(topicFound.get("__discriminator")).isEqualTo("HttpTopic");
        assertThat(topicFound.get("mediaTypeValue").toString()).isEqualTo("text/html");
        assertThat(topicFound.get("openGraphType").toString()).isEqualTo("article");
    }

    @Test
    public void canPersistAnFtpTopic() {
        final FtpTopic ftpTopic = TestFactories.topics().newSimpleFtpTopic();

        repo.add(ftpTopic);

        final DBObject topicFound = getTopic(ftpTopic.getId());
        assertThat(topicFound).isNotNull();
        assertThat(topicFound.get("activationId")).isEqualTo(ftpTopic.getUserId());
        assertThat(topicFound.get("names")).isNotNull();
        assertThat(topicFound.get("descriptions")).isNotNull();
        assertThat(topicFound.get("subTypes")).isNotNull();
        assertThat(topicFound.get("uris")).isNotNull();
        assertThat(topicFound.get("illustration")).isEqualTo(ftpTopic.getIllustration());
        assertThat(topicFound.get("__discriminator")).isEqualTo("FtpTopic");
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
        assertThat(topicFound.get("uris")).isNotNull();
        assertThat(topicFound.get("illustration")).isEqualTo(geoTopic.getIllustration());
        assertThat(topicFound.get("__discriminator")).isEqualTo("GeoTopic");
    }

    @Test
    public void canGetAWorldTopic() {
        final DBCollection collection = getMongo().getCollection("topic");
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
    public void canGetAnHttpTopic() {
        final DBCollection collection = getMongo().getCollection("topic");
        final DBObject topic = new BasicDBObject();
        final UUID id = UUID.randomUUID();
        topic.put("_id", id);
        topic.put("__discriminator", "HttpTopic");
        collection.insert(topic);

        final HttpTopic topicFound = repo.getHttpTopic(id);

        assertThat(topicFound).isNotNull();
        assertThat(topicFound.getId()).isEqualTo(id);
    }

    @Test
    public void canGetAnFtpTopic() {
        final DBCollection collection = getMongo().getCollection("topic");
        final DBObject topic = new BasicDBObject();
        final UUID id = UUID.randomUUID();
        topic.put("_id", id);
        topic.put("__discriminator", "FtpTopic");
        collection.insert(topic);

        final FtpTopic topicFound = repo.getFtpTopic(id);

        assertThat(topicFound).isNotNull();
        assertThat(topicFound.getId()).isEqualTo(id);
    }

    @Test
    public void canGetARealTopic() {
        final DBCollection collection = getMongo().getCollection("topic");
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
        final DBCollection collection = getMongo().getCollection("topic");
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
        final DBCollection collection = getMongo().getCollection("topic");
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

        @Override
        public String getTypeValue() {
            return null;
        }
    }
}
