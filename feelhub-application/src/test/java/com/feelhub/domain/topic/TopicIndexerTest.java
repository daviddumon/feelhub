package com.feelhub.domain.topic;

import com.feelhub.domain.tag.Tag;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.http.HttpTopic;
import com.feelhub.domain.topic.real.*;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import org.junit.*;

import java.util.UUID;

import static org.fest.assertions.Assertions.*;

public class TopicIndexerTest {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Test
    public void canIndexNonTranslatableTopic() {
        final String value = "value";
        final HttpTopic httpTopic = TestFactories.topics().newCompleteHttpTopic();

        new TopicIndexer(httpTopic).index(value, FeelhubLanguage.reference());

        assertThat(Repositories.tags().getAll().size()).isEqualTo(1);
        final Tag tag = Repositories.tags().getAll().get(0);
        assertThat(tag.getId()).isEqualTo(value);
        assertThat(tag.getTopicsIdFor(FeelhubLanguage.none())).contains(httpTopic.getId());
    }

    @Test
    public void canIndexTranslatableTopic() {
        final String value = "value";
        final RealTopic topic = TestFactories.topics().newCompleteRealTopic(value, RealTopicType.Anniversary);

        new TopicIndexer(topic).index(value, FeelhubLanguage.fromCode("de"));

        assertThat(Repositories.tags().getAll().size()).isEqualTo(1);
        final Tag tag = Repositories.tags().getAll().get(0);
        assertThat(tag.getId()).isEqualTo(value);
        assertThat(tag.getTopicsIdFor(FeelhubLanguage.fromCode("de"))).contains(topic.getId());
    }

    @Test
    public void correctlySetTopicIdInTag() {
        final String value = "value";
        final HttpTopic httpTopic = TestFactories.topics().newCompleteHttpTopic();

        new TopicIndexer(httpTopic).index(value, FeelhubLanguage.none());

        assertThat(Repositories.tags().getAll().get(0).getTopicsIdFor(FeelhubLanguage.none())).contains(httpTopic.getId());
    }

    @Test
    public void correctlySetTopicIdInTagWhenTagUniqueness() {
        final FakeTopic fakeTopic = new FakeTopic(UUID.randomUUID(), FakeUniqueTopicType.Unique);
        final String tag = "tag-fr";

        new TopicIndexer(fakeTopic).index(tag, FeelhubLanguage.none());

        assertThat(Repositories.tags().getAll().get(0).getTopicsIdFor(FeelhubLanguage.none())).contains(fakeTopic.getId());
    }

    @Test
    public void doNotAddDoubleUniqueId() {
        final FakeTopic newTopic = new FakeTopic(UUID.randomUUID(), FakeUniqueTopicType.Unique);
        Repositories.topics().add(newTopic);
        final String tag = "tag-fr";
        createTagForFakeUniqueTopic(tag);

        new TopicIndexer(newTopic).index(tag, FeelhubLanguage.none());

        assertThat(Repositories.tags().getAll().get(0).getTopicsIdFor(FeelhubLanguage.none()).size()).isEqualTo(1);
    }

    @Test
    public void canAddNotUniqueTopicToExistingTag() {
        final FakeTopic fakeTopic = new FakeTopic(UUID.randomUUID(), FakeUniqueTopicType.NotUnique);
        final String tag = "tag-fr";
        createTagForFakeNonUniqueTopic(tag);

        new TopicIndexer(fakeTopic).index(tag, FeelhubLanguage.none());

        assertThat(Repositories.tags().getAll().size()).isEqualTo(1);
        assertThat(Repositories.tags().getAll().get(0).getTopicsIdFor(FeelhubLanguage.none()).size()).isEqualTo(2);
    }

    @Test
    public void changeCurrentIdIfExistingUniqueTopicType() {
        final String value = "tag";
        final FakeTopic fakeTopic = createTagForFakeUniqueTopic(value);
        final FakeTopic anotherTopic = new FakeTopic(UUID.randomUUID(), FakeUniqueTopicType.Unique);
        Repositories.topics().add(anotherTopic);

        new TopicIndexer(anotherTopic).index(value, FeelhubLanguage.none());

        final Tag tag = Repositories.tags().getAll().get(0);
        assertThat(tag.getTopicsIdFor(FeelhubLanguage.none()).size()).isEqualTo(1);
        assertThat(tag.getTopicsIdFor(FeelhubLanguage.none())).contains(fakeTopic.getId());
        assertThat(anotherTopic.getCurrentId()).isEqualTo(fakeTopic.getId());
    }

    private FakeTopic createTagForFakeUniqueTopic(final String value) {
        final FakeTopic fakeTopic = new FakeTopic(UUID.randomUUID(), FakeUniqueTopicType.Unique);
        final Tag tag = new Tag(value);
        tag.addTopic(fakeTopic, FeelhubLanguage.none());
        Repositories.tags().add(tag);
        Repositories.topics().add(fakeTopic);
        return fakeTopic;
    }

    private FakeTopic createTagForFakeNonUniqueTopic(final String value) {
        final FakeTopic fakeTopic = new FakeTopic(UUID.randomUUID(), FakeUniqueTopicType.NotUnique);
        final Tag tag = new Tag(value);
        tag.addTopic(fakeTopic, FeelhubLanguage.none());
        Repositories.tags().add(tag);
        Repositories.topics().add(fakeTopic);
        return fakeTopic;
    }

    enum FakeUniqueTopicType implements TopicType {
        Unique(true),
        NotUnique(false);

        FakeUniqueTopicType(final boolean unique) {
            isUnique = unique;
        }

        @Override
        public boolean hasTagUniqueness() {
            return isUnique;
        }

        @Override
        public boolean isMedia() {
            return false;
        }

        @Override
        public boolean isTranslatable() {
            return false;
        }

        private final boolean isUnique;
    }

    class FakeTopic extends Topic {
        FakeTopic(final UUID id, final TopicType type) {
            super(id);
            this.type = type;
        }

        @Override
        public TopicType getType() {
            return type;
        }

        @Override
        public String getTypeValue() {
            return type.toString();
        }

        private final TopicType type;
    }
}
