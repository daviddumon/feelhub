package com.steambeat.repositories;

import com.steambeat.domain.Repository;
import com.steambeat.domain.opinion.Feeling;
import com.steambeat.domain.subject.feed.*;
import com.steambeat.test.testFactories.TestFactories;
import com.mongodb.*;
import org.joda.time.DateTime;
import org.junit.*;

import java.net.*;
import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@SuppressWarnings("unchecked")
public class TestsFeedMongoRepository extends TestWithMongoRepository {

    @Before
    public void before() {
        repo = Repositories.feeds();
    }

    @Test
    public void canPersist() throws UnknownHostException, MongoException, MalformedURLException {
        final Feed feed = new Feed(new Association(new Uri("http://www.lemonde.fr"), TestFactories.canonicalUriFinder()));
        final DateTime feedCreationDate = feed.getCreationDate();
        feed.createOpinion("my opinion", Feeling.good);

        repo.add(feed);

        final DBCollection collection = mongo.getCollection("subject");
        final DBObject query = new BasicDBObject();
        query.put("_id", "http://www.lemonde.fr");
        final DBObject feedFound = collection.findOne(query);
        assertThat(feedFound, notNullValue());
        assertThat(feedFound.get("_id"), is((Object) "http://www.lemonde.fr"));
        assertThat(feedFound.get("creationDate"), is((Object) feedCreationDate.getMillis()));
    }

    @Test
    public void canGet() {
        final DBCollection collection = mongo.getCollection("subject");
        final DBObject feed = new BasicDBObject();
        feed.put("_id", "lemonde.fr");
        feed.put("__discriminator", "Feed");
        collection.insert(feed);

        final Feed feedFound = repo.get("lemonde.fr");

        assertThat(feedFound, notNullValue());
    }

    @Test
    public void canGetAll() {
        final DBCollection collection = mongo.getCollection("subject");
        final DBObject feed = new BasicDBObject();
        feed.put("_id", "lemonde.fr");
        feed.put("__discriminator", "Feed");
        collection.insert(feed);
        collection.insert(feed);
        collection.insert(feed);

        final List<Feed> feedList = repo.getAll();

        assertThat(feedList, notNullValue());
        assertThat(feedList.size(), is(3));
    }

    protected Repository<Feed> repo;

}
