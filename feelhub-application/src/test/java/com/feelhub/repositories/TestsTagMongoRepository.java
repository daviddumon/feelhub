package com.feelhub.repositories;

import com.feelhub.domain.tag.*;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.Topic;
import com.feelhub.test.*;
import com.mongodb.*;
import org.junit.*;

import java.util.*;

import static org.fest.assertions.Assertions.*;

public class TestsTagMongoRepository extends TestWithMongoRepository {

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Before
    public void before() {
        repository = Repositories.keywords();
    }

    @Test
    public void canPersistAKeyword() {
        final Tag word = new Tag("value", FeelhubLanguage.fromCountryName("english"), UUID.randomUUID());

        repository.add(word);

        final DBObject wordFound = getKeywordFromDB(word.getId());
        assertThat(wordFound).isNotNull();
        assertThat(wordFound.get("_id")).isEqualTo(word.getId());
        assertThat(wordFound.get("value")).isEqualTo(word.getValue());
        assertThat(wordFound.get("languageCode")).isEqualTo(word.getLanguageCode());
        assertThat(wordFound.get("topicId")).isEqualTo(word.getTopicId());
        assertThat(wordFound.get("creationDate")).isEqualTo(word.getCreationDate().getMillis());
        assertThat(wordFound.get("lastModificationDate")).isEqualTo(word.getLastModificationDate().getMillis());
    }

    @Test
    public void canGetAKeyword() {
        final DBCollection collection = mongo.getCollection("tag");
        final DBObject keyword = new BasicDBObject();
        final UUID id = UUID.randomUUID();
        keyword.put("_id", id);
        collection.insert(keyword);

        final Tag tagFound = repository.get(id);

        assertThat(tagFound).isNotNull();
    }

    @Test
    public void canGetForValueAndLanguage() {
        final String value = "value";
        final FeelhubLanguage english = FeelhubLanguage.fromCountryName("english");
        TestFactories.tags().newWord(value, english);

        final Tag tag = repository.forValueAndLanguage(value, english);

        assertThat(tag).isNotNull();
    }

    @Test
    public void canGetForATopic() {
        final Topic topic = TestFactories.topics().newTopic();
        TestFactories.tags().newWord("coucou", FeelhubLanguage.fromCode("fr"), topic.getId());
        TestFactories.tags().newWord("hello", FeelhubLanguage.fromCode("en"), topic.getId());
        TestFactories.tags().newWord("hola", FeelhubLanguage.fromCode("es"), topic.getId());

        final List<Tag> tags = repository.forTopicId(topic.getId());

        assertThat(tags).isNotNull();
        assertThat(tags.size()).isEqualTo(3);
    }

    @Test
    public void canGetForTopicIdAndLanguage() {
        final String value = "value";
        final FeelhubLanguage english = FeelhubLanguage.fromCountryName("english");
        final Tag word = TestFactories.tags().newWord(value, english);

        final Tag tag = repository.forTopicIdAndLanguage(word.getTopicId(), english);

        assertThat(tag).isNotNull();
    }

    private DBObject getKeywordFromDB(final Object id) {
        final DBCollection collection = mongo.getCollection("tag");
        final DBObject query = new BasicDBObject();
        query.put("_id", id);
        return collection.findOne(query);
    }

    private TagRepository repository;
}
