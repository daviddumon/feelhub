package com.steambeat.repositories;

import com.mongodb.*;
import com.steambeat.domain.relation.*;
import com.steambeat.domain.reference.Reference;
import com.steambeat.test.TestFactories;
import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsRelationMongoRepository extends TestWithMongoRepository {

    @Before
    public void before() {
        repo = Repositories.relations();
    }

    @Test
    public void canPersist() {
        final Reference left = TestFactories.references().newReference();
        final Reference right = TestFactories.references().newReference();
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
    }

    @Test
    public void canGet() {
        final Reference left = TestFactories.references().newReference();
        final Reference right = TestFactories.references().newReference();
        final Relation relation = new Relation(left, right, 1.0);
        Repositories.relations().add(relation);

        final Relation relationFound = repo.get(relation.getId());

        assertThat(relationFound, notNullValue());
    }

    @Test
    public void canLookupForFromAndTo() {
        final Reference from = TestFactories.references().newReference();
        final Reference to = TestFactories.references().newReference();
        final Relation relation = TestFactories.relations().newRelation(from, to);

        final Relation relationFound = repo.lookUp(from, to);

        assertThat(relationFound, notNullValue());
        assertThat(relation.getId(), is(relationFound.getId()));
    }

    private RelationRepository repo;
}
