package com.feelhub.repositories;

import com.feelhub.domain.related.*;
import com.feelhub.domain.topic.real.RealTopic;
import com.feelhub.test.*;
import com.mongodb.*;
import org.junit.*;

import java.util.List;

import static org.fest.assertions.Assertions.*;

public class TestsRelatedMongoRepository extends TestWithMongoRepository {

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Before
    public void before() {
        repo = Repositories.related();
    }

    @Test
    public void canPersistARelated() {
        final RealTopic left = TestFactories.topics().newCompleteRealTopic();
        final RealTopic right = TestFactories.topics().newCompleteRealTopic();
        final Related related = new Related(left.getId(), right.getId(), 1.0);

        repo.add(related);

        final DBCollection relations = mongo.getCollection("related");
        final DBObject query = new BasicDBObject();
        query.put("_id", related.getId());
        final DBObject relationFound = relations.findOne(query);
        assertThat(relationFound).isNotNull();
        assertThat(relationFound.get("_id").toString()).isEqualTo(related.getId().toString());
        assertThat(relationFound.get("fromId").toString()).isEqualTo(related.getFromId().toString());
        assertThat(relationFound.get("toId").toString()).isEqualTo(related.getToId().toString());
        assertThat(relationFound.get("weight")).isEqualTo(1.0);
    }

    @Test
    public void canGetARelated() {
        final RealTopic left = TestFactories.topics().newCompleteRealTopic();
        final RealTopic right = TestFactories.topics().newCompleteRealTopic();
        final Related related = new Related(left.getId(), right.getId(), 1.0);
        Repositories.related().add(related);

        final Related relatedFound = repo.get(related.getId());

        assertThat(relatedFound).isNotNull();
    }

    @Test
    public void canLookupRelatedForFromAndTo() {
        final RealTopic from = TestFactories.topics().newCompleteRealTopic();
        final RealTopic to = TestFactories.topics().newCompleteRealTopic();
        final Related related = TestFactories.related().newRelated(from.getId(), to.getId());

        final Related relatedFound = repo.lookUp(from.getId(), to.getId());

        assertThat(relatedFound).isNotNull();
        assertThat(related.getId()).isEqualTo(relatedFound.getId());
    }

    @Test
    public void canGetAllRelatedContainingATopicId() {
        final RealTopic realTopic1 = TestFactories.topics().newCompleteRealTopic();
        final RealTopic realTopic2 = TestFactories.topics().newCompleteRealTopic();
        TestFactories.related().newRelated(realTopic1.getId(), realTopic2.getId());
        TestFactories.related().newRelated(realTopic2.getId(), realTopic1.getId());

        final List<Related> relatedList = repo.containingTopicId(realTopic1.getId());

        assertThat(relatedList.size()).isEqualTo(2);
    }

    @Test
    public void canGetAllRelatedForATopicId() {
        final RealTopic realTopic1 = TestFactories.topics().newCompleteRealTopic();
        final RealTopic realTopic2 = TestFactories.topics().newCompleteRealTopic();
        TestFactories.related().newRelated(realTopic1.getId(), realTopic2.getId());
        TestFactories.related().newRelated(realTopic2.getId(), realTopic1.getId());

        final List<Related> related = repo.forTopicId(realTopic1.getId());

        assertThat(related.size()).isEqualTo(1);
    }

    private RelatedRepository repo;
}
