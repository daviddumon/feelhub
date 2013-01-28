package com.feelhub.repositories;

import com.feelhub.domain.relation.*;
import com.feelhub.domain.relation.media.Media;
import com.feelhub.domain.relation.related.Related;
import com.feelhub.domain.topic.real.RealTopic;
import com.feelhub.test.*;
import com.mongodb.*;
import org.junit.*;

import java.util.List;

import static org.fest.assertions.Assertions.*;

public class TestsRelationMongoRepository extends TestWithMongoRepository {

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Before
    public void before() {
        repo = Repositories.relations();
    }

    @Test
    public void canPersistARelatedRelation() {
        final RealTopic left = TestFactories.topics().newCompleteRealTopic();
        final RealTopic right = TestFactories.topics().newCompleteRealTopic();
        final Related related = new Related(left.getId(), right.getId(), 1.0);

        repo.add(related);

        final DBCollection relations = mongo.getCollection("relation");
        final DBObject query = new BasicDBObject();
        query.put("_id", related.getId());
        final DBObject relationFound = relations.findOne(query);
        assertThat(relationFound).isNotNull();
        assertThat(relationFound.get("_id").toString()).isEqualTo(related.getId().toString());
        assertThat(relationFound.get("fromId").toString()).isEqualTo(related.getFromId().toString());
        assertThat(relationFound.get("toId").toString()).isEqualTo(related.getToId().toString());
        assertThat(relationFound.get("creationDate")).isEqualTo(related.getCreationDate().getMillis());
        assertThat(relationFound.get("weight")).isEqualTo(1.0);
        assertThat(relationFound.get("lastModificationDate")).isEqualTo(related.getLastModificationDate().getMillis());
        assertThat(relationFound.get("__discriminator")).isEqualTo("Related");
    }

    @Test
    public void canGetARelated() {
        final RealTopic left = TestFactories.topics().newCompleteRealTopic();
        final RealTopic right = TestFactories.topics().newCompleteRealTopic();
        final Related related = new Related(left.getId(), right.getId(), 1.0);
        Repositories.relations().add(related);

        final Relation relationFound = repo.get(related.getId());

        assertThat(relationFound).isNotNull();
    }

    @Test
    public void canPersistAMediaRelation() {
        final RealTopic left = TestFactories.topics().newCompleteRealTopic();
        final RealTopic right = TestFactories.topics().newCompleteRealTopic();
        final Media media = new Media(left.getId(), right.getId());

        repo.add(media);

        final DBCollection relations = mongo.getCollection("relation");
        final DBObject query = new BasicDBObject();
        query.put("_id", media.getId());
        final DBObject relationFound = relations.findOne(query);
        assertThat(relationFound).isNotNull();
        assertThat(relationFound.get("_id").toString()).isEqualTo(media.getId().toString());
        assertThat(relationFound.get("fromId").toString()).isEqualTo(media.getFromId().toString());
        assertThat(relationFound.get("toId").toString()).isEqualTo(media.getToId().toString());
        assertThat(relationFound.get("creationDate")).isEqualTo(media.getCreationDate().getMillis());
        assertThat(relationFound.get("weight")).isEqualTo(0.0);
        assertThat(relationFound.get("lastModificationDate")).isEqualTo(media.getLastModificationDate().getMillis());
        assertThat(relationFound.get("__discriminator")).isEqualTo("Media");
    }

    @Test
    public void canGetAMedia() {
        final RealTopic left = TestFactories.topics().newCompleteRealTopic();
        final RealTopic right = TestFactories.topics().newCompleteRealTopic();
        final Media media = new Media(left.getId(), right.getId());
        Repositories.relations().add(media);

        final Relation relationFound = repo.get(media.getId());

        assertThat(relationFound).isNotNull();
    }

    @Test
    public void canLookupRelatedForFromAndTo() {
        final RealTopic from = TestFactories.topics().newCompleteRealTopic();
        final RealTopic to = TestFactories.topics().newCompleteRealTopic();
        final Relation relation = TestFactories.relations().newRelated(from.getId(), to.getId());

        final Relation relationFound = repo.lookUpRelated(from.getId(), to.getId());

        assertThat(relationFound).isNotNull();
        assertThat(relation.getId()).isEqualTo(relationFound.getId());
    }

    @Test
    public void lookupRelatedDoNotReturnMedia() {
        final RealTopic from = TestFactories.topics().newCompleteRealTopic();
        final RealTopic to = TestFactories.topics().newCompleteRealTopic();
        TestFactories.relations().newMedia(from.getId(), to.getId());

        final Relation relationFound = repo.lookUpRelated(from.getId(), to.getId());

        assertThat(relationFound).isNull();
    }

    @Test
    public void canLookupMediaForFromAndTo() {
        final RealTopic from = TestFactories.topics().newCompleteRealTopic();
        final RealTopic to = TestFactories.topics().newCompleteRealTopic();
        final Relation relation = TestFactories.relations().newMedia(from.getId(), to.getId());

        final Relation relationFound = repo.lookUpMedia(from.getId(), to.getId());

        assertThat(relationFound).isNotNull();
        assertThat(relation.getId()).isEqualTo(relationFound.getId());
    }

    @Test
    public void lookupMediaDoNotReturnRelated() {
        final RealTopic from = TestFactories.topics().newCompleteRealTopic();
        final RealTopic to = TestFactories.topics().newCompleteRealTopic();
        TestFactories.relations().newRelated(from.getId(), to.getId());

        final Relation relationFound = repo.lookUpMedia(from.getId(), to.getId());

        assertThat(relationFound).isNull();
    }

    @Test
    public void canGetAllRelationsContainingATopicId() {
        final RealTopic realTopic1 = TestFactories.topics().newCompleteRealTopic();
        final RealTopic realTopic2 = TestFactories.topics().newCompleteRealTopic();
        TestFactories.relations().newRelated(realTopic1.getId(), realTopic2.getId());
        TestFactories.relations().newRelated(realTopic2.getId(), realTopic1.getId());

        final List<Relation> relations = repo.containingTopicId(realTopic1.getId());

        assertThat(relations.size()).isEqualTo(2);
    }

    @Test
    public void canGetAllRelatedForATopicId() {
        final RealTopic realTopic1 = TestFactories.topics().newCompleteRealTopic();
        final RealTopic realTopic2 = TestFactories.topics().newCompleteRealTopic();
        TestFactories.relations().newRelated(realTopic1.getId(), realTopic2.getId());
        TestFactories.relations().newRelated(realTopic2.getId(), realTopic1.getId());
        TestFactories.relations().newMedia(realTopic1.getId(), realTopic2.getId());

        final List<Related> related = repo.relatedForTopicId(realTopic1.getId());

        assertThat(related.size()).isEqualTo(1);
    }

    private RelationRepository repo;
}
