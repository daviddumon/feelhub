package com.steambeat.repositories;

import com.mongodb.*;
import com.steambeat.domain.Repository;
import com.steambeat.domain.keyword.Keyword;
import com.steambeat.domain.thesaurus.Language;
import com.steambeat.test.SystemTime;
import org.junit.*;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsKeywordMongoRepository extends TestWithMongoRepository {

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Before
    public void before() {
        repository = Repositories.keywords();
    }

    @Test
    public void canPersistAKeyword() {
        final Keyword keyword = new Keyword("value", Language.forString("english"), UUID.randomUUID());

        repository.add(keyword);

        final DBObject keywordFound = getUserFromDB(keyword.getId());
        assertThat(keywordFound, notNullValue());
        assertThat(keywordFound.get("_id"), is(keyword.getId()));
        assertThat(keywordFound.get("value"), is((Object) keyword.getValue()));
        assertThat(keywordFound.get("language"), is((Object) keyword.getLanguage()));
        assertThat(keywordFound.get("topic"), is((Object) keyword.getTopic()));
        assertThat(keywordFound.get("creationDate"), is((Object) keyword.getCreationDate().getMillis()));
        assertThat(keywordFound.get("lastModificationDate"), is((Object) keyword.getLastModificationDate().getMillis()));
    }

    @Test
    public void canGetAKeyword() {
        final DBCollection collection = mongo.getCollection("keyword");
        final DBObject keyword = new BasicDBObject();
        final UUID id = UUID.randomUUID();
        keyword.put("_id", id);
        collection.insert(keyword);

        final Keyword keywordFound = repository.get(id);

        assertThat(keywordFound, notNullValue());
    }

    private DBObject getUserFromDB(final Object id) {
        final DBCollection collection = mongo.getCollection("keyword");
        final DBObject query = new BasicDBObject();
        query.put("_id", id);
        return collection.findOne(query);
    }

    //@Before
    //public void before() throws UnknownHostException {
    //    repo = Repositories.associations();
    //}
    //
    //@Test
    //public void canPersist() throws UnknownHostException, MongoException, MalformedURLException {
    //    final Identifier identifier = new Uri("www.lemonde.fr");
    //    final UUID uuid = UUID.randomUUID();
    //    final Language french = Language.forString("french");
    //    final Association association = new Association(identifier, uuid, french);
    //
    //    repo.add(association);
    //
    //    final DBObject associationFound = getAssociationFromDB();
    //    assertThat(associationFound, notNullValue());
    //    assertThat(associationFound.get("identifier"), is((Object) identifier.toString()));
    //    assertThat(UUID.fromString(associationFound.get("topicId").toString()), is((Object) uuid));
    //    assertThat(associationFound.get("language"), is((Object) french.getCode()));
    //}
    //
    //private DBObject getAssociationFromDB() {
    //    final DBCollection collection = mongo.getCollection("association");
    //    final DBObject query = new BasicDBObject();
    //    query.put("identifier", "http://www.lemonde.fr");
    //    return collection.findOne(query);
    //}
    //
    //@Test
    //public void canGet() {
    //    final DBCollection collection = mongo.getCollection("association");
    //    final DBObject association = new BasicDBObject();
    //    final UUID uuid = UUID.randomUUID();
    //    association.put("_id", uuid);
    //    collection.insert(association);
    //
    //    final Association associationFound = repo.get(uuid);
    //
    //    assertThat(associationFound, notNullValue());
    //}
    //
    //@Test
    //public void canGetForTag() {
    //    final Tag tag = new Tag("tag");
    //    TestFactories.associations().newAssociation(tag, UUID.randomUUID(), Language.forString("french"));
    //
    //    final Association associationFound = repo.forIdentifier(tag);
    //
    //    assertThat(associationFound, notNullValue());
    //}
    //
    //@Test
    //public void canGetForUri() {
    //    final Uri uri = new Uri("http://www.mongo.com");
    //    TestFactories.associations().newAssociation(uri, UUID.randomUUID(), Language.forString("french"));
    //
    //    final Association associationFound = repo.forIdentifier(uri);
    //
    //    assertThat(associationFound, notNullValue());
    //}
    //
    //@Test
    //public void canGetForIdentifierAndLanguage() {
    //    final Tag tag = new Tag("tag");
    //    final Language language = Language.forString("french");
    //    TestFactories.associations().newAssociation(tag, UUID.randomUUID(), language);
    //
    //    final Association associationFound = repo.forIdentifierAndLanguage(tag, language);
    //
    //    assertThat(associationFound, notNullValue());
    //}
    //
    //@Test
    //public void canGetIfNoLanguage() {
    //    final Tag tag = new Tag("tag");
    //    TestFactories.associations().newAssociation(tag, UUID.randomUUID(), Language.forString(""));
    //
    //    final Association associationFound = repo.forIdentifierAndLanguage(tag, Language.forString("french"));
    //
    //    assertThat(associationFound, notNullValue());
    //}
    //
    //private AssociationRepository repo;

    private Repository<Keyword> repository;
}
