package com.feelhub.repositories;

import com.feelhub.domain.tag.Tag;
import com.feelhub.domain.tag.TagRepository;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.real.RealTopic;
import com.feelhub.test.SystemTime;
import com.feelhub.test.TestFactories;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static org.fest.assertions.Assertions.*;

public class TagMongoRepositoryTest extends TestWithMongoRepository {

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Before
    public void before() {
        repository = Repositories.tags();
    }

    @Test
    public void canPersistATag() {
        final Tag tag = new Tag("value");
        tag.addTopic(TestFactories.topics().newCompleteHttpTopic(), FeelhubLanguage.none());

        repository.add(tag);

        final DBObject tagFound = getTagFromDB(tag.getId());
        assertThat(tagFound).isNotNull();
        assertThat(tagFound.get("_id")).isEqualTo(tag.getId());
        assertThat(tagFound.get("topicIds")).isNotNull();
    }

    @Test
    public void canGetATag() {
        final DBCollection collection = getMongo().getCollection("tag");
        final DBObject tag = new BasicDBObject();
        tag.put("_id", "value");
        collection.insert(tag);

        final Tag tagFound = repository.get("value");

        assertThat(tagFound).isNotNull();
    }

    @Test
    @Ignore("do not work with mongolink")
    public void canGetTagsWithTopicId() {
        final Tag tag = TestFactories.tags().newTag();
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        tag.addTopic(realTopic, FeelhubLanguage.reference());
        tag.addTopic(TestFactories.topics().newCompleteRealTopic(), FeelhubLanguage.reference());
        final Tag anotherTag = TestFactories.tags().newTag();
        anotherTag.addTopic(TestFactories.topics().newCompleteRealTopic(), FeelhubLanguage.reference());

        final List<Tag> tags = repository.forTopicId(realTopic.getId());

        assertThat(tags).isNotNull();
        assertThat(tags.size()).isEqualTo(1);
        assertThat(tags.get(0)).isEqualTo(tag);
    }

    private DBObject getTagFromDB(final Object id) {
        final DBCollection collection = getMongo().getCollection("tag");
        final DBObject query = new BasicDBObject();
        query.put("_id", id);
        return collection.findOne(query);
    }

    private TagRepository repository;
}
