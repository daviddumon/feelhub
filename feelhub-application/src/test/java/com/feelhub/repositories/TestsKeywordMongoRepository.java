package com.feelhub.repositories;

import com.feelhub.domain.keyword.*;
import com.feelhub.domain.reference.Reference;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.test.*;
import com.mongodb.*;
import org.junit.*;

import java.util.*;

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
        final Keyword keyword = new Keyword("value", FeelhubLanguage.forString("english"), UUID.randomUUID());

        repository.add(keyword);

        final DBObject keywordFound = getUserFromDB(keyword.getId());
        assertThat(keywordFound, notNullValue());
        assertThat(keywordFound.get("_id"), is(keyword.getId()));
        assertThat(keywordFound.get("value"), is((Object) keyword.getValue()));
        assertThat(keywordFound.get("languageCode"), is((Object) keyword.getLanguageCode()));
        assertThat(keywordFound.get("referenceId"), is((Object) keyword.getReferenceId()));
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

    @Test
    public void canGetForValueAndLanguage() {
        final String value = "value";
        final FeelhubLanguage english = FeelhubLanguage.forString("english");
        TestFactories.keywords().newKeyword(value, english);

        final Keyword keyword = repository.forValueAndLanguage(value, english);

        assertThat(keyword, notNullValue());
    }

    @Test
    public void canGetWorld() {
        final String value = "";
        TestFactories.keywords().newKeyword(value, FeelhubLanguage.none());

        final Keyword keyword = repository.forValueAndLanguage(value, FeelhubLanguage.none());

        assertThat(keyword, notNullValue());
    }

    @Test
    public void canGetForAReference() {
        final Reference reference = TestFactories.references().newReference();
        TestFactories.keywords().newKeyword("coucou", FeelhubLanguage.forString("fr"), reference);
        TestFactories.keywords().newKeyword("hello", FeelhubLanguage.forString("en"), reference);
        TestFactories.keywords().newKeyword("hola", FeelhubLanguage.forString("es"), reference);

        final List<Keyword> keywords = repository.forReferenceId(reference.getId());

        assertThat(keywords, notNullValue());
        assertThat(keywords.size(), is(3));
    }

    private DBObject getUserFromDB(final Object id) {
        final DBCollection collection = mongo.getCollection("keyword");
        final DBObject query = new BasicDBObject();
        query.put("_id", id);
        return collection.findOne(query);
    }

    private KeywordRepository repository;
}
