package com.steambeat.repositories;

import com.mongodb.*;
import com.steambeat.domain.opinion.Opinion;
import com.steambeat.test.SystemTime;
import com.steambeat.test.testFactories.TestFactories;
import org.hamcrest.Matchers;
import org.junit.*;

import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsOpinionMongoRepository extends TestWithMongoRepository {

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Test
    public void canPersist() {
        final Opinion opinion = TestFactories.opinions().newOpinion();

        Repositories.opinions().add(opinion);

        final DBCollection opinions = mongo.getCollection("opinion");
        final DBObject query = new BasicDBObject();
        query.put("_id", opinion.getId());
        final DBObject opinionFound = opinions.findOne(query);
        assertThat(opinionFound, notNullValue());
        assertThat(opinionFound.get("_id").toString(), Matchers.is(opinion.getId()));
        assertThat(opinionFound.get("text").toString(), Matchers.is(opinion.getText()));
        assertThat(opinionFound.get("feeling").toString(), Matchers.is(opinion.getFeeling().toString()));
        assertThat(opinionFound.get("creationDate"), is((Object) opinion.getCreationDate().getMillis()));
        assertThat(opinionFound.get("subjectId"), is((Object) opinion.getSubject().getId()));
    }

    @Test
    public void canGet() {
        final Opinion opinion = TestFactories.opinions().newOpinion();
        Repositories.opinions().add(opinion);

        final Opinion opinionFound = Repositories.opinions().get(opinion.getId());

        assertThat(opinionFound, Matchers.is(opinion));
    }

    @Test
    public void canGetAll() {
        final Opinion opinion = TestFactories.opinions().newOpinion();

        final List<Opinion> opinions = Repositories.opinions().getAll();

        assertThat(opinions.size(), is(1));
        assertThat(opinions.get(0), Matchers.is(opinion));
    }

    @Test
    public void canPersistMapOfJudgments() {
        final Opinion opinion = TestFactories.opinions().newOpinion();

        Repositories.opinions().add(opinion);

        final DBCollection opinions = mongo.getCollection("opinion");
        final DBObject query = new BasicDBObject();
        query.put("_id", opinion.getId());
        final DBObject opinionFound = opinions.findOne(query);
        assertThat(opinionFound.get("judgments"), notNullValue());
    }
}
