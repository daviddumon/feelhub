package com.feelhub.repositories;

import com.feelhub.domain.keyword.*;
import com.feelhub.domain.keyword.uri.Uri;
import com.feelhub.domain.keyword.word.Word;
import com.feelhub.domain.keyword.world.World;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.Topic;
import com.feelhub.test.*;
import com.mongodb.*;
import org.junit.*;

import java.util.*;

import static org.fest.assertions.Assertions.*;

public class TestsKeywordMongoRepository extends TestWithMongoRepository {

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Before
    public void before() {
        repository = Repositories.keywords();
    }

    @Test
    public void canPersistAWord() {
        final Word word = new Word("value", FeelhubLanguage.fromCountryName("english"), UUID.randomUUID());

        repository.add(word);

        final DBObject wordFound = getKeywordFromDB(word.getId());
        assertThat(wordFound).isNotNull();
        assertThat(wordFound.get("_id")).isEqualTo(word.getId());
        assertThat(wordFound.get("value")).isEqualTo(word.getValue());
        assertThat(wordFound.get("languageCode")).isEqualTo(word.getLanguageCode());
        assertThat(wordFound.get("topicId")).isEqualTo(word.getTopicId());
        assertThat(wordFound.get("creationDate")).isEqualTo(word.getCreationDate().getMillis());
        assertThat(wordFound.get("lastModificationDate")).isEqualTo(word.getLastModificationDate().getMillis());
        assertThat((Boolean) wordFound.get("translationNeeded")).isFalse();
    }

    @Test
    public void canPersistAnUri() {
        final Uri uri = new Uri("value", UUID.randomUUID());

        repository.add(uri);

        final DBObject uriFound = getKeywordFromDB(uri.getId());
        assertThat(uriFound).isNotNull();
        assertThat(uriFound.get("_id")).isEqualTo(uri.getId());
        assertThat(uriFound.get("value")).isEqualTo(uri.getValue());
        assertThat(uriFound.get("languageCode")).isEqualTo(FeelhubLanguage.none().getCode());
        assertThat(uriFound.get("topicId")).isEqualTo(uri.getTopicId());
        assertThat(uriFound.get("creationDate")).isEqualTo(uri.getCreationDate().getMillis());
        assertThat(uriFound.get("lastModificationDate")).isEqualTo(uri.getLastModificationDate().getMillis());
    }

    @Test
    public void canPersistWorld() {
        final World world = new World(UUID.randomUUID());

        repository.add(world);

        final DBObject worldFound = getKeywordFromDB(world.getId());
        assertThat(worldFound).isNotNull();
        assertThat(worldFound.get("_id")).isEqualTo(world.getId());
        assertThat(worldFound.get("value")).isEqualTo(world.getValue());
        assertThat(worldFound.get("topicId")).isEqualTo(world.getTopicId());
        assertThat(worldFound.get("creationDate")).isEqualTo(world.getCreationDate().getMillis());
        assertThat(worldFound.get("lastModificationDate")).isEqualTo(world.getLastModificationDate().getMillis());
    }

    @Test
    public void canGetAWord() {
        final DBCollection collection = mongo.getCollection("keyword");
        final DBObject keyword = new BasicDBObject();
        final UUID id = UUID.randomUUID();
        keyword.put("_id", id);
        keyword.put("__discriminator", "Word");
        collection.insert(keyword);

        final Keyword keywordFound = repository.get(id);

        assertThat(keywordFound).isNotNull();
    }

    @Test
    public void canGetAnUri() {
        final DBCollection collection = mongo.getCollection("keyword");
        final DBObject keyword = new BasicDBObject();
        final UUID id = UUID.randomUUID();
        keyword.put("_id", id);
        keyword.put("__discriminator", "Uri");
        collection.insert(keyword);

        final Keyword keywordFound = repository.get(id);

        assertThat(keywordFound).isNotNull();
    }

    @Test
    public void canGetWorld() {
        TestFactories.keywords().newWord();
        final World world = TestFactories.keywords().newWorld();

        final World worldFound = repository.world();

        assertThat(worldFound).isNotNull();
        assertThat(worldFound.getTopicId()).isEqualTo(world.getTopicId());
    }

    @Test
    public void canGetForValueAndLanguage() {
        final String value = "value";
        final FeelhubLanguage english = FeelhubLanguage.fromCountryName("english");
        TestFactories.keywords().newWord(value, english);

        final Keyword keyword = repository.forValueAndLanguage(value, english);

        assertThat(keyword).isNotNull();
    }

    @Test
    public void canGetForATopic() {
        final Topic topic = TestFactories.topics().newTopic();
        TestFactories.keywords().newWord("coucou", FeelhubLanguage.fromCode("fr"), topic.getId());
        TestFactories.keywords().newWord("hello", FeelhubLanguage.fromCode("en"), topic.getId());
        TestFactories.keywords().newWord("hola", FeelhubLanguage.fromCode("es"), topic.getId());

        final List<Keyword> keywords = repository.forTopicId(topic.getId());

        assertThat(keywords).isNotNull();
        assertThat(keywords.size()).isEqualTo(3);
    }

    @Test
    public void canGetForTopicIdAndLanguage() {
        final String value = "value";
        final FeelhubLanguage english = FeelhubLanguage.fromCountryName("english");
        final Word word = TestFactories.keywords().newWord(value, english);

        final Keyword keyword = repository.forTopicIdAndLanguage(word.getTopicId(), english);

        assertThat(keyword).isNotNull();
    }

    private DBObject getKeywordFromDB(final Object id) {
        final DBCollection collection = mongo.getCollection("keyword");
        final DBObject query = new BasicDBObject();
        query.put("_id", id);
        return collection.findOne(query);
    }

    private KeywordRepository repository;
}
