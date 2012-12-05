package com.feelhub.domain.topic;

import com.feelhub.application.TagService;
import com.feelhub.domain.feeling.*;
import com.feelhub.domain.meta.Illustration;
import com.feelhub.domain.relation.Relation;
import com.feelhub.domain.statistics.*;
import com.feelhub.domain.tag.Tag;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.google.inject.*;
import org.junit.*;

import static org.fest.assertions.Assertions.*;

public class TestsTopicMerger {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Before
    public void before() {
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(TagService.class).asEagerSingleton();
            }
        });
        topicMerger = injector.getInstance(TopicMerger.class);
    }

    @Test
    public void mergeTags() {
        final Topic newTopic = TestFactories.topics().newTopic("tag1");
        final Topic oldTopic = TestFactories.topics().newTopic("tag2");

        topicMerger.merge(newTopic.getId(), oldTopic.getId());

        for (final Tag tag : Repositories.tags().getAll()) {
            assertThat(tag.getTopicIds()).contains(newTopic.getId());
            assertThat(oldTopic.getId()).isNotIn(tag.getTopicIds());
        }
    }

    @Test
    public void mergeIllustrations() {
        final Topic newTopic = TestFactories.topics().newTopic("tag1");
        final Topic oldTopic = TestFactories.topics().newTopic("tag2");
        TestFactories.illustrations().newIllustration(newTopic.getId());
        TestFactories.illustrations().newIllustration(oldTopic.getId());

        topicMerger.merge(newTopic.getId(), oldTopic.getId());

        for (final Illustration illustration : Repositories.illustrations().getAll()) {
            assertThat(illustration.getTopicId()).isEqualTo(newTopic.getId());
        }
    }

    @Test
    public void mergeFeelings() {
        final Topic newTopic = TestFactories.topics().newTopic("tag1");
        final Topic oldTopic = TestFactories.topics().newTopic("tag2");
        TestFactories.feelings().newFeelings(newTopic.getId(), 10);
        TestFactories.feelings().newFeelings(oldTopic.getId(), 10);

        topicMerger.merge(newTopic.getId(), oldTopic.getId());

        for (final Feeling feeling : Repositories.feelings().getAll()) {
            for (final Sentiment sentiment : feeling.getSentiments()) {
                assertThat(sentiment.getTopicId()).isEqualTo(newTopic.getId());
            }
        }
    }

    @Test
    public void mergeRelations() {
        final Topic newTopic = TestFactories.topics().newTopic("tag1");
        final Topic oldTopic = TestFactories.topics().newTopic("tag2");
        final Topic anotherTopic = TestFactories.topics().newTopic();
        final Relation relation1 = TestFactories.relations().newRelation(oldTopic.getId(), anotherTopic.getId());
        final Relation relation2 = TestFactories.relations().newRelation(anotherTopic.getId(), oldTopic.getId());

        topicMerger.merge(newTopic.getId(), oldTopic.getId());

        assertThat(relation1.getFromId()).isEqualTo(newTopic.getId());
        assertThat(relation1.getToId()).isEqualTo(anotherTopic.getId());
        assertThat(relation2.getFromId()).isEqualTo(anotherTopic.getId());
        assertThat(relation2.getToId()).isEqualTo(newTopic.getId());
    }

    @Test
    public void mergeStatistics() {
        final Topic newTopic = TestFactories.topics().newTopic("tag1");
        final Topic oldTopic = TestFactories.topics().newTopic("tag2");
        TestFactories.statistics().newStatisticsWithSentiments(oldTopic.getId(), Granularity.hour);

        topicMerger.merge(newTopic.getId(), oldTopic.getId());

        final Statistics statistics = Repositories.statistics().getAll().get(0);
        assertThat(statistics.getTopicId()).isEqualTo(newTopic.getId());
    }

    private TopicMerger topicMerger;
}
