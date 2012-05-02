package com.steambeat.repositories;

import com.mongodb.*;
import com.steambeat.domain.association.Association;
import com.steambeat.domain.association.uri.Uri;
import com.steambeat.domain.relation.alchemy.readmodel.AlchemyJsonEntity;
import com.steambeat.domain.subject.*;
import com.steambeat.domain.subject.concept.*;
import com.steambeat.domain.subject.steam.Steam;
import com.steambeat.domain.subject.webpage.WebPage;
import com.steambeat.test.SystemTime;
import com.steambeat.test.testFactories.TestFactories;
import org.junit.*;

import java.net.*;
import java.util.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@SuppressWarnings("unchecked")
public class TestsSubjectMongoRepository extends TestWithMongoRepository {

    @Rule
    public SystemTime time = SystemTime.fixed();

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
        assertThat(webPageFound.get("lastModificationDate"), is((Object) webPage.getCreationDate().getMillis()));
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
        final Steam steam = new Steam(UUID.randomUUID());

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

    @Test
    public void bugCannotChangeDateOfSubject() {
        final UUID id = TestFactories.subjects().newWebPage().getId();

        final Subject subject = Repositories.subjects().get(id);
        time.waitDays(1);
        subject.setLastModificationDate(time.getNow());

        assertThat(Repositories.subjects().get(id).getLastModificationDate(), is(time.getNow()));
    }

    @Test
    public void canPersistConcept() {
        final List<AlchemyJsonEntity> entities = TestFactories.alchemy().entities(1);
        final ConceptFactory conceptFactory = new ConceptFactory();
        final Concept concept = conceptFactory.newConcept(entities.get(0));

        repo.add(concept);

        final DBCollection collection = mongo.getCollection("subject");
        final DBObject query = new BasicDBObject();
        query.put("_id", concept.getId());
        final DBObject conceptFound = collection.findOne(query);
        assertThat(conceptFound, notNullValue());
        assertThat(conceptFound.get("_id"), is((Object) concept.getId()));
        assertThat(conceptFound.get("type"), is((Object) concept.getType()));
        assertThat(conceptFound.get("text"), is((Object) concept.getText()));
        assertThat(conceptFound.get("language"), is((Object) concept.getLanguage()));
        assertThat(conceptFound.get("website"), is((Object) concept.getWebsite()));
        assertThat(conceptFound.get("geo"), is((Object) concept.getGeo()));
    }

    protected SubjectRepository repo;

}
