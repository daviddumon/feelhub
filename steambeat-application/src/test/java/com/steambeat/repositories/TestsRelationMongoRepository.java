package com.steambeat.repositories;

import com.mongodb.*;
import com.steambeat.domain.Repository;
import com.steambeat.domain.analytics.Relation;
import com.steambeat.domain.subject.webpage.WebPage;
import com.steambeat.test.testFactories.TestFactories;
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
        final WebPage left = TestFactories.subjects().newWebPage();
        final WebPage right = TestFactories.subjects().newWebPage();
        final Relation relation = new Relation(left, right);

        repo.add(relation);

        final DBCollection relations = mongo.getCollection("relation");
        final DBObject query = new BasicDBObject();
        query.put("_id", relation.getId());
        final DBObject relationFound = relations.findOne(query);
        assertThat(relationFound, notNullValue());
        assertThat(relationFound.get("_id").toString(), is(relation.getId().toString()));
        assertThat(relationFound.get("leftId").toString(), is(relation.getLeftId().toString()));
        assertThat(relationFound.get("rightId").toString(), is(relation.getRightId().toString()));
        assertThat(relationFound.get("creationDate"), is((Object) relation.getCreationDate().getMillis()));
        assertThat(relationFound.get("weight"), is((Object) 1));
    }

    @Test
    public void canGet() {
        final WebPage left = TestFactories.subjects().newWebPage();
        final WebPage right = TestFactories.subjects().newWebPage();
        final Relation relation = new Relation(left, right);
        Repositories.relations().add(relation);

        final Relation relationFound = repo.get(relation.getId());

        assertThat(relationFound, notNullValue());
    }

    private Repository<Relation> repo;
}
