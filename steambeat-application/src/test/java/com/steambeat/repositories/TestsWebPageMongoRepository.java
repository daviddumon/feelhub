package com.steambeat.repositories;

import com.mongodb.*;
import com.steambeat.domain.Repository;
import com.steambeat.domain.opinion.Feeling;
import com.steambeat.domain.subject.webpage.*;
import com.steambeat.test.testFactories.TestFactories;
import org.joda.time.DateTime;
import org.junit.*;

import java.net.*;
import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@SuppressWarnings("unchecked")
public class TestsWebPageMongoRepository extends TestWithMongoRepository {

    @Before
    public void before() {
        repo = Repositories.webPages();
    }

    @Test
    public void canPersist() throws UnknownHostException, MongoException, MalformedURLException {
        final WebPage webPage = new WebPage(new Association(new Uri("http://www.lemonde.fr"), TestFactories.canonicalUriFinder()));
        final DateTime webPageCreationDate = webPage.getCreationDate();
        webPage.createOpinion("my opinion", Feeling.good);

        repo.add(webPage);

        final DBCollection collection = mongo.getCollection("subject");
        final DBObject query = new BasicDBObject();
        query.put("_id", "http://www.lemonde.fr");
        final DBObject webPageFound = collection.findOne(query);
        assertThat(webPageFound, notNullValue());
        assertThat(webPageFound.get("_id"), is((Object) "http://www.lemonde.fr"));
        assertThat(webPageFound.get("creationDate"), is((Object) webPageCreationDate.getMillis()));
    }

    @Test
    public void canGet() {
        final DBCollection collection = mongo.getCollection("subject");
        final DBObject webPage = new BasicDBObject();
        webPage.put("_id", "lemonde.fr");
        webPage.put("__discriminator", "WebPage");
        collection.insert(webPage);

        final WebPage webPageFound = repo.get("lemonde.fr");

        assertThat(webPageFound, notNullValue());
    }

    @Test
    public void canGetAll() {
        final DBCollection collection = mongo.getCollection("subject");
        final DBObject webPage = new BasicDBObject();
        webPage.put("_id", "lemonde.fr");
        webPage.put("__discriminator", "WebPage");
        collection.insert(webPage);
        collection.insert(webPage);
        collection.insert(webPage);

        final List<WebPage> webPageList = repo.getAll();

        assertThat(webPageList, notNullValue());
        assertThat(webPageList.size(), is(3));
    }

    protected Repository<WebPage> repo;

}
