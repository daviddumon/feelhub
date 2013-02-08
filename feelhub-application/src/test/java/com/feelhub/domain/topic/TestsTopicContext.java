package com.feelhub.domain.topic;

import com.feelhub.domain.tag.Tag;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.http.HttpTopic;
import com.feelhub.domain.topic.real.RealTopic;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.google.inject.*;
import org.junit.*;

import java.util.Map;

import static org.fest.assertions.Assertions.*;

public class TestsTopicContext {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Before
    public void before() {
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
            }
        });
        topicContext = injector.getInstance(TopicContext.class);
    }

    @Test
    public void canGetAContextForATopic() {
        final RealTopic topic1 = TestFactories.topics().newCompleteRealTopic("name1");
        final RealTopic topic2 = TestFactories.topics().newCompleteRealTopic("name2");
        final RealTopic topic3 = TestFactories.topics().newCompleteRealTopic("name3");
        final Tag value1 = TestFactories.tags().newTag("value1", topic2);
        final Tag value2 = TestFactories.tags().newTag("value2", topic2);
        final Tag value3 = TestFactories.tags().newTag("value3", topic3);
        TestFactories.related().newRelated(topic1.getId(), topic2.getId());
        TestFactories.related().newRelated(topic1.getId(), topic3.getId());

        final Map<Tag,Topic> tagTopicMap = topicContext.extractFor(topic1.getId(), FeelhubLanguage.reference());

        assertThat(tagTopicMap.size()).isEqualTo(3);
        assertThat(tagTopicMap.keySet()).contains(value1);
        assertThat(tagTopicMap.keySet()).contains(value2);
        assertThat(tagTopicMap.keySet()).contains(value3);
    }

    @Test
    public void mediaDoNotBelongToTopicContext() {
        final RealTopic topic1 = TestFactories.topics().newCompleteRealTopic("name1");
        final RealTopic topic2 = TestFactories.topics().newCompleteRealTopic("name2");
        final RealTopic topic3 = TestFactories.topics().newCompleteRealTopic("name3");
        final Tag value1 = TestFactories.tags().newTag("value1", topic2);
        final Tag value2 = TestFactories.tags().newTag("value2", topic2);
        TestFactories.tags().newTag("value3", topic3);
        TestFactories.related().newRelated(topic1.getId(), topic2.getId());
        TestFactories.medias().newMedia(topic1.getId(), topic3.getId());

        final Map<Tag, Topic> tagTopicMap = topicContext.extractFor(topic1.getId(), FeelhubLanguage.reference());

        assertThat(tagTopicMap.size()).isEqualTo(2);
        assertThat(tagTopicMap.keySet()).contains(value1);
        assertThat(tagTopicMap.keySet()).contains(value1);
    }

    @Test
    public void onlyGetTagsWithGoodLanguage() {
        final RealTopic topic1 = TestFactories.topics().newCompleteRealTopic("name1");
        final RealTopic topic2 = TestFactories.topics().newCompleteRealTopic("name2");
        final RealTopic topic3 = TestFactories.topics().newCompleteRealTopic("name3");
        TestFactories.tags().newTag("value1", topic2, FeelhubLanguage.reference());
        final Tag tag = TestFactories.tags().newTag("value2", topic2, FeelhubLanguage.fromCode("fr"));
        TestFactories.related().newRelated(topic1.getId(), topic2.getId());
        TestFactories.related().newRelated(topic1.getId(), topic3.getId());

        final Map<Tag, Topic> tagTopicMap = topicContext.extractFor(topic1.getId(), FeelhubLanguage.fromCode("fr"));

        assertThat(tagTopicMap.size()).isEqualTo(1);
        assertThat(tagTopicMap.keySet()).contains(tag);
    }

    @Test
    public void alwaysGetTagsWithoutLanguage() {
        final RealTopic topic1 = TestFactories.topics().newCompleteRealTopic("name1");
        final RealTopic topic2 = TestFactories.topics().newCompleteRealTopic("name2");
        final RealTopic topic3 = TestFactories.topics().newCompleteRealTopic("name3");
        TestFactories.tags().newTag("value1", topic2, FeelhubLanguage.reference());
        final Tag value2 = TestFactories.tags().newTag("value2", topic2, FeelhubLanguage.fromCode("fr"));
        final Tag value3 = TestFactories.tags().newTag("value3", topic3, FeelhubLanguage.none());
        TestFactories.related().newRelated(topic1.getId(), topic2.getId());
        TestFactories.related().newRelated(topic1.getId(), topic3.getId());

        final Map<Tag, Topic> tagTopicMap = topicContext.extractFor(topic1.getId(), FeelhubLanguage.fromCode("fr"));

        assertThat(tagTopicMap.size()).isEqualTo(2);
        assertThat(tagTopicMap.keySet()).contains(value2);
        assertThat(tagTopicMap.keySet()).contains(value3);
    }

    @Test
    public void contextIsOnlyAboutRealTopics() {
        final RealTopic topic1 = TestFactories.topics().newCompleteRealTopic("name1");
        final RealTopic topic2 = TestFactories.topics().newCompleteRealTopic("name2");
        final HttpTopic topic3 = TestFactories.topics().newCompleteHttpTopic();
        final Tag value1 = TestFactories.tags().newTag("value1", topic2);
        final Tag value2 = TestFactories.tags().newTag("value2", topic2);
        TestFactories.tags().newTag("value3", topic3);
        TestFactories.related().newRelated(topic1.getId(), topic2.getId());
        TestFactories.related().newRelated(topic1.getId(), topic3.getId());

        final Map<Tag, Topic> tagTopicMap = topicContext.extractFor(topic1.getId(), FeelhubLanguage.reference());

        assertThat(tagTopicMap.size()).isEqualTo(2);
        assertThat(tagTopicMap.keySet()).contains(value1);
        assertThat(tagTopicMap.keySet()).contains(value2);
    }

    private TopicContext topicContext;
}
