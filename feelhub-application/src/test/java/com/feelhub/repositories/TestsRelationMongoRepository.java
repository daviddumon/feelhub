package com.feelhub.repositories;

import com.feelhub.domain.relation.*;
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
    public void canPersist() {
        final RealTopic left = TestFactories.topics().newCompleteRealTopic();
        final RealTopic right = TestFactories.topics().newCompleteRealTopic();
        final Relation relation = new Relation(left, right, 1.0);

        repo.add(relation);

        final DBCollection relations = mongo.getCollection("relation");
        final DBObject query = new BasicDBObject();
        query.put("_id", relation.getId());
        final DBObject relationFound = relations.findOne(query);
        assertThat(relationFound).isNotNull();
        assertThat(relationFound.get("_id").toString()).isEqualTo(relation.getId().toString());
        assertThat(relationFound.get("fromId").toString()).isEqualTo(relation.getFromId().toString());
        assertThat(relationFound.get("toId").toString()).isEqualTo(relation.getToId().toString());
        assertThat(relationFound.get("creationDate")).isEqualTo(relation.getCreationDate().getMillis());
        assertThat(relationFound.get("weight")).isEqualTo(1.0);
        assertThat(relationFound.get("lastModificationDate")).isEqualTo(relation.getLastModificationDate().getMillis());
    }

    @Test
    public void canGet() {
        final RealTopic left = TestFactories.topics().newCompleteRealTopic();
        final RealTopic right = TestFactories.topics().newCompleteRealTopic();
        final Relation relation = new Relation(left, right, 1.0);
        Repositories.relations().add(relation);

        final Relation relationFound = repo.get(relation.getId());

        assertThat(relationFound).isNotNull();
    }

    @Test
    public void canLookupForFromAndTo() {
        final RealTopic from = TestFactories.topics().newCompleteRealTopic();
        final RealTopic to = TestFactories.topics().newCompleteRealTopic();
        final Relation relation = TestFactories.relations().newRelation(from.getId(), to.getId());

        final Relation relationFound = repo.lookUp(from.getId(), to.getId());

        assertThat(relationFound).isNotNull();
        assertThat(relation.getId()).isEqualTo(relationFound.getId());
    }

    @Test
    public void canGetAllRelationsContainingATopicId() {
        final RealTopic realTopic1 = TestFactories.topics().newCompleteRealTopic();
        final RealTopic realTopic2 = TestFactories.topics().newCompleteRealTopic();
        TestFactories.relations().newRelation(realTopic1.getId(), realTopic2.getId());
        TestFactories.relations().newRelation(realTopic2.getId(), realTopic1.getId());

        final List<Relation> relations = repo.containingTopicId(realTopic1.getId());

        assertThat(relations.size()).isEqualTo(2);
    }

    @Test
    public void canGetAllRelationsForATopicId() {
        final RealTopic realTopic1 = TestFactories.topics().newCompleteRealTopic();
        final RealTopic realTopic2 = TestFactories.topics().newCompleteRealTopic();
        TestFactories.relations().newRelation(realTopic1.getId(), realTopic2.getId());
        TestFactories.relations().newRelation(realTopic2.getId(), realTopic1.getId());

        final List<Relation> relations = repo.forTopicId(realTopic1.getId());

        assertThat(relations.size()).isEqualTo(1);
    }

    private RelationRepository repo;
}
