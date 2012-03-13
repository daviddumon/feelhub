package com.steambeat.repositories;

import com.mongodb.*;
import com.steambeat.domain.Repository;
import com.steambeat.domain.analytics.Association;
import com.steambeat.domain.analytics.identifiers.uri.Uri;
import com.steambeat.domain.subject.Subject;
import com.steambeat.domain.subject.webpage.WebPage;
import org.junit.*;

import java.net.*;
import java.util.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@SuppressWarnings("unchecked")
public class TestsSubjectMongoRepository extends TestWithMongoRepository {

    @Before
    public void before() {
        repo = Repositories.subjects();
    }

    @Test
    public void canPersistWebPage() throws UnknownHostException, MongoException, MalformedURLException {
        final Association association = new Association(new Uri("http://www.fake.com/" + UUID.randomUUID().toString()), UUID.randomUUID());
        final WebPage webPage = new WebPage(association);

        repo.add(webPage);

        final DBCollection collection = mongo.getCollection("subject");
        final DBObject query = new BasicDBObject();
        query.put("_id", webPage.getId());
        final DBObject webPageFound = collection.findOne(query);
        assertThat(webPageFound, notNullValue());
        assertThat(webPageFound.get("_id"), is((Object) webPage.getId()));
        assertThat(webPageFound.get("creationDate"), is((Object) webPage.getCreationDate().getMillis()));
        assertThat(webPageFound.get("semanticDescription"), is((Object) webPage.getSemanticDescription()));
        assertThat(webPageFound.get("uri"), is((Object) webPage.getUri()));
    }

    @Test
    public void canGetWebPage() {
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
