package com.steambeat.repositories;

import com.mongodb.*;
import com.steambeat.domain.Repository;
import com.steambeat.domain.subject.Subject;
import com.steambeat.domain.subject.webpage.WebPage;
import com.steambeat.test.testFactories.TestFactories;
import org.joda.time.DateTime;
import org.junit.*;

import java.net.*;
import java.util.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@SuppressWarnings("unchecked")
public class TestsWebPageMongoRepository extends TestWithMongoRepository {

    @Before
    public void before() {
        repo = Repositories.subjects();
    }

    @Test
    public void canPersist() throws UnknownHostException, MongoException, MalformedURLException {
        final WebPage webPage = TestFactories.webPages().newWebPage();
        final DateTime webPageCreationDate = webPage.getCreationDate();

        repo.add(webPage);

        final DBCollection collection = mongo.getCollection("subject");
        final DBObject query = new BasicDBObject();
        query.put("_id", webPage.getId());
        final DBObject webPageFound = collection.findOne(query);
        assertThat(webPageFound, notNullValue());
        assertThat(webPageFound.get("_id"), is((Object) webPage.getId()));
        assertThat(webPageFound.get("creationDate"), is((Object) webPageCreationDate.getMillis()));
        assertThat(webPageFound.get("semanticDescription"), is((Object) webPage.getSemanticDescription()));
    }

    @Test
    public void canGet() {
        final DBCollection collection = mongo.getCollection("subject");
        final DBObject webPage = new BasicDBObject();
        final UUID id = UUID.randomUUID();
        webPage.put("_id", id.toString());
        webPage.put("__discriminator", "WebPage");
        collection.insert(webPage);

        final WebPage webPageFound = (WebPage) repo.get(id.toString());

        assertThat(webPageFound, notNullValue());
    }

    @Test
    public void canGetAll() {
        final DBCollection collection = mongo.getCollection("subject");
        final DBObject webPage = new BasicDBObject();
        final UUID id = UUID.randomUUID();
        webPage.put("_id", id.toString());
        webPage.put("__discriminator", "WebPage");
        collection.insert(webPage);
        collection.insert(webPage);
        collection.insert(webPage);

        final List<Subject> webPageList = repo.getAll();

        assertThat(webPageList, notNullValue());
        assertThat(webPageList.size(), is(3));
    }

    protected Repository<Subject> repo;

}
