package com.steambeat.repositories;

import com.mongodb.*;
import com.steambeat.domain.keyword.*;
import com.steambeat.domain.thesaurus.Language;
import com.steambeat.test.*;
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

    @Test
    public void canGetForValueAndLanguage() {
        final String value = "value";
        final Language english = Language.forString("english");
        TestFactories.keywords().newKeyword(value, english);

        final Keyword keyword = repository.forValueAndLanguage(value, english);

        assertThat(keyword, notNullValue());
    }

    private DBObject getUserFromDB(final Object id) {
        final DBCollection collection = mongo.getCollection("keyword");
        final DBObject query = new BasicDBObject();
        query.put("_id", id);
        return collection.findOne(query);
    }

    private KeywordRepository repository;
}
