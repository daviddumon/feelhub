package com.feelhub.repositories;

import com.feelhub.domain.media.*;
import com.feelhub.domain.topic.http.HttpTopic;
import com.feelhub.domain.topic.real.RealTopic;
import com.feelhub.test.*;
import com.mongodb.*;
import org.junit.*;

import java.util.List;

import static org.fest.assertions.Assertions.*;

public class TestsMediaMongoRepository extends TestWithMongoRepository {

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Before
    public void before() {
        repository = Repositories.medias();
    }

    @Test
    public void canPersistAMediaRelation() {
        final RealTopic left = TestFactories.topics().newCompleteRealTopic();
        final RealTopic right = TestFactories.topics().newCompleteRealTopic();
        final Media media = new Media(left.getId(), right.getId());

        repository.add(media);

        final DBCollection relations = mongo.getCollection("media");
        final DBObject query = new BasicDBObject();
        query.put("_id", media.getId());
        final DBObject mediaFound = relations.findOne(query);
        assertThat(mediaFound).isNotNull();
        assertThat(mediaFound.get("_id").toString()).isEqualTo(media.getId().toString());
        assertThat(mediaFound.get("fromId").toString()).isEqualTo(media.getFromId().toString());
        assertThat(mediaFound.get("toId").toString()).isEqualTo(media.getToId().toString());
    }

    @Test
    public void canGetAMedia() {
        final RealTopic left = TestFactories.topics().newCompleteRealTopic();
        final RealTopic right = TestFactories.topics().newCompleteRealTopic();
        final Media media = new Media(left.getId(), right.getId());
        Repositories.medias().add(media);

        final Media mediaFound = repository.get(media.getId());

        assertThat(mediaFound).isNotNull();
    }

    @Test
    public void canLookupMediaForFromAndTo() {
        final RealTopic from = TestFactories.topics().newCompleteRealTopic();
        final RealTopic to = TestFactories.topics().newCompleteRealTopic();
        final Media media = TestFactories.medias().newMedia(from.getId(), to.getId());

        final Media mediaFound = repository.lookUp(from.getId(), to.getId());

        assertThat(mediaFound).isNotNull();
        assertThat(media.getId()).isEqualTo(mediaFound.getId());
    }

    @Test
    public void canGetAllMediasContainingATopicId() {
        final HttpTopic httpTopic1 = TestFactories.topics().newMediaTopic();
        final HttpTopic httpTopic2 = TestFactories.topics().newMediaTopic();
        TestFactories.medias().newMedia(httpTopic1.getId(), httpTopic2.getId());
        TestFactories.medias().newMedia(httpTopic2.getId(), httpTopic1.getId());

        final List<Media> mediaList = repository.containingTopicId(httpTopic1.getId());

        assertThat(mediaList.size()).isEqualTo(2);
    }

    private MediaRepository repository;
}
