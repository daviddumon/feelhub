package com.steambeat.repositories;

import com.mongodb.*;
import com.steambeat.domain.analytics.Association;
import com.steambeat.domain.analytics.identifiers.uri.Uri;
import com.steambeat.domain.subject.*;
import com.steambeat.domain.subject.steam.Steam;
import com.steambeat.domain.subject.webpage.WebPage;
import com.steambeat.test.testFactories.TestFactories;
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
        webPage.put("_id", id);
        webPage.put("__discriminator", "WebPage");
        collection.insert(webPage);

        final WebPage webPageFound = (WebPage) repo.get(id);

        assertThat(webPageFound, notNullValue());
    }

    @Test
    public void canGetAll() {
        final DBCollection collection = mongo.getCollection("subject");
        final DBObject webPage = new BasicDBObject();
        final UUID id = UUID.randomUUID();
        webPage.put("_id", id);
        webPage.put("__discriminator", "WebPage");
        collection.insert(webPage);
        collection.insert(webPage);
        collection.insert(webPage);

        final List<Subject> webPageList = repo.getAll();

        assertThat(webPageList, notNullValue());
        assertThat(webPageList.size(), is(3));
    }

    @Test
    public void canPersistSteam() {
        final Steam steam = new Steam();

        repo.add(steam);

        final DBCollection collection = mongo.getCollection("subject");
        final DBObject query = new BasicDBObject();
        query.put("_id", steam.getId());
        final DBObject steamFound = collection.findOne(query);
        assertThat(steamFound, notNullValue());
        assertThat(steamFound.get("_id"), is((Object) steam.getId()));
        assertThat(steamFound.get("creationDate"), is((Object) steam.getCreationDate().getMillis()));
    }

    @Test
    public void canGetSteam() {
        final DBCollection collection = mongo.getCollection("subject");
        final DBObject steam = new BasicDBObject();
        final UUID id = UUID.randomUUID();
        steam.put("_id", id);
        steam.put("__discriminator", "Steam");
        collection.insert(steam);

        final Steam steamFound = repo.getSteam();

        assertThat(steamFound, notNullValue());
    }

    @Test
    public void canGetSteamWhenAlreadySubjets() {
        TestFactories.subjects().newWebPage();
        TestFactories.subjects().newWebPage();
        final DBCollection collection = mongo.getCollection("subject");
        final DBObject steam = new BasicDBObject();
        final UUID id = UUID.randomUUID();
        steam.put("_id", id);
        steam.put("__discriminator", "Steam");
        collection.insert(steam);

        final Steam steamFound = repo.getSteam();

        assertThat(steamFound, notNullValue());
    }

    protected SubjectRepository repo;

}
