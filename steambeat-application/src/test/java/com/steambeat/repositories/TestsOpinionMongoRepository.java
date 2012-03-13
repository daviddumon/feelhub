package com.steambeat.repositories;

import com.mongodb.*;
import com.steambeat.domain.opinion.*;
import com.steambeat.domain.subject.webpage.WebPage;
import com.steambeat.test.SystemTime;
import com.steambeat.test.testFactories.TestFactories;
import org.junit.*;

import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsOpinionMongoRepository extends TestWithMongoRepository {

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Test
    public void canPersist() {
        final WebPage webPage = TestFactories.subjects().newWebPage();
        final Opinion opinion = new Opinion("yeah");
        opinion.addJudgment(webPage, Feeling.bad);

        Repositories.opinions().add(opinion);

        final DBCollection opinions = mongo.getCollection("opinion");
        final DBObject query = new BasicDBObject();
        query.put("_id", opinion.getId());
        final DBObject opinionFound = opinions.findOne(query);
        assertThat(opinionFound, notNullValue());
        assertThat(opinionFound.get("_id").toString(), is(opinion.getId()));
        assertThat(opinionFound.get("text").toString(), is(opinion.getText()));
        assertThat(opinionFound.get("creationDate"), is((Object) opinion.getCreationDate().getMillis()));
    }

    @Test
    public void canGet() {
        final Opinion opinion = TestFactories.opinions().newOpinion();

        final Opinion opinionFound = Repositories.opinions().get(opinion.getId());

        assertThat(opinionFound, is(opinion));
    }

    @Test
    public void canGetAll() {
        final Opinion opinion0 = TestFactories.opinions().newOpinion();
        final Opinion opinion1 = TestFactories.opinions().newOpinion();

        final List<Opinion> opinions = Repositories.opinions().getAll();

        assertThat(opinions.size(), is(2));
        assertThat(opinions.get(0), is(opinion0));
        assertThat(opinions.get(1), is(opinion1));
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
