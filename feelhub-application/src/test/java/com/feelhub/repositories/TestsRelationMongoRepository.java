package com.feelhub.repositories;

import com.feelhub.domain.relation.*;
import com.feelhub.domain.topic.Topic;
import com.feelhub.test.*;
import com.mongodb.*;
import org.junit.*;

import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsRelationMongoRepository extends TestWithMongoRepository {

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Before
    public void before() {
        repo = Repositories.relations();
    }

    @Test
    public void canPersist() {
        final Topic left = TestFactories.topics().newTopic();
        final Topic right = TestFactories.topics().newTopic();
        final Relation relation = new Relation(left, right, 1.0);

        repo.add(relation);

        final DBCollection relations = mongo.getCollection("relation");
        final DBObject query = new BasicDBObject();
        query.put("_id", relation.getId());
        final DBObject relationFound = relations.findOne(query);
        assertThat(relationFound, notNullValue());
        assertThat(relationFound.get("_id").toString(), is(relation.getId().toString()));
        assertThat(relationFound.get("fromId").toString(), is(relation.getFromId().toString()));
        assertThat(relationFound.get("toId").toString(), is(relation.getToId().toString()));
        assertThat(relationFound.get("creationDate"), is((Object) relation.getCreationDate().getMillis()));
        assertThat(relationFound.get("weight"), is((Object) 1.0));
        assertThat(relationFound.get("creationDate"), is((Object) time.getNow().getMillis()));
        assertThat(relationFound.get("lastModificationDate"), is((Object) time.getNow().getMillis()));
    }

    @Test
    public void canGet() {
        final Topic left = TestFactories.topics().newTopic();
        final Topic right = TestFactories.topics().newTopic();
        final Relation relation = new Relation(left, right, 1.0);
        Repositories.relations().add(relation);

        final Relation relationFound = repo.get(relation.getId());

        assertThat(relationFound, notNullValue());
    }

    @Test
    public void canLookupForFromAndTo() {
        final Topic from = TestFactories.topics().newTopic();
        final Topic to = TestFactories.topics().newTopic();
        final Relation relation = TestFactories.relations().newRelation(from, to);

        final Relation relationFound = repo.lookUp(from.getId(), to.getId());

        assertThat(relationFound, notNullValue());
        assertThat(relation.getId(), is(relationFound.getId()));
    }

    @Test
    public void canGetAllRelationsRelatedToATopicId() {
        final Topic topic1 = TestFactories.topics().newTopic();
        final Topic topic2 = TestFactories.topics().newTopic();
        TestFactories.relations().newRelation(topic1, topic2);
        TestFactories.relations().newRelation(topic2, topic1);
        TestFactories.relations().newRelation(topic2, topic2);
        TestFactories.relations().newRelation(topic1, topic1);

        final List<Relation> relations = repo.forTopicId(topic1.getId());

        assertThat(relations.size(), is(3));
    }

    private RelationRepository repo;
}
