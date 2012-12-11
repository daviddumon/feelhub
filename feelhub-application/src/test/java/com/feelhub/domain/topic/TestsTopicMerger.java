package com.feelhub.domain.topic;

import com.feelhub.application.TagService;
import com.feelhub.domain.feeling.*;
import com.feelhub.domain.meta.Illustration;
import com.feelhub.domain.relation.Relation;
import com.feelhub.domain.statistics.*;
import com.feelhub.domain.tag.Tag;
import com.feelhub.domain.topic.usable.real.RealTopic;
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
        final RealTopic newRealTopic = TestFactories.topics().newCompleteRealTopic("tag1");
        final RealTopic oldRealTopic = TestFactories.topics().newCompleteRealTopic("tag2");

        topicMerger.merge(newRealTopic.getId(), oldRealTopic.getId());

        for (final Tag tag : Repositories.tags().getAll()) {
            assertThat(tag.getTopicIds()).contains(newRealTopic.getId());
            assertThat(oldRealTopic.getId()).isNotIn(tag.getTopicIds());
        }
    }

    @Test
    public void mergeIllustrations() {
        final RealTopic newRealTopic = TestFactories.topics().newCompleteRealTopic("tag1");
        final RealTopic oldRealTopic = TestFactories.topics().newCompleteRealTopic("tag2");
        TestFactories.illustrations().newIllustration(newRealTopic.getId());
        TestFactories.illustrations().newIllustration(oldRealTopic.getId());

        topicMerger.merge(newRealTopic.getId(), oldRealTopic.getId());

        for (final Illustration illustration : Repositories.illustrations().getAll()) {
            assertThat(illustration.getTopicId()).isEqualTo(newRealTopic.getId());
        }
    }

    @Test
    public void mergeFeelings() {
        final RealTopic newRealTopic = TestFactories.topics().newCompleteRealTopic("tag1");
        final RealTopic oldRealTopic = TestFactories.topics().newCompleteRealTopic("tag2");
        TestFactories.feelings().newFeelings(newRealTopic.getId(), 10);
        TestFactories.feelings().newFeelings(oldRealTopic.getId(), 10);

        topicMerger.merge(newRealTopic.getId(), oldRealTopic.getId());

        for (final Feeling feeling : Repositories.feelings().getAll()) {
            for (final Sentiment sentiment : feeling.getSentiments()) {
                assertThat(sentiment.getTopicId()).isEqualTo(newRealTopic.getId());
            }
        }
    }

    @Test
    public void mergeRelations() {
        final RealTopic newRealTopic = TestFactories.topics().newCompleteRealTopic("tag1");
        final RealTopic oldRealTopic = TestFactories.topics().newCompleteRealTopic("tag2");
        final RealTopic anotherRealTopic = TestFactories.topics().newCompleteRealTopic();
        final Relation relation1 = TestFactories.relations().newRelation(oldRealTopic.getId(), anotherRealTopic.getId());
        final Relation relation2 = TestFactories.relations().newRelation(anotherRealTopic.getId(), oldRealTopic.getId());

        topicMerger.merge(newRealTopic.getId(), oldRealTopic.getId());

        assertThat(relation1.getFromId()).isEqualTo(newRealTopic.getId());
        assertThat(relation1.getToId()).isEqualTo(anotherRealTopic.getId());
        assertThat(relation2.getFromId()).isEqualTo(anotherRealTopic.getId());
        assertThat(relation2.getToId()).isEqualTo(newRealTopic.getId());
    }

    @Test
    public void mergeStatistics() {
        final RealTopic newRealTopic = TestFactories.topics().newCompleteRealTopic("tag1");
        final RealTopic oldRealTopic = TestFactories.topics().newCompleteRealTopic("tag2");
        TestFactories.statistics().newStatisticsWithSentiments(oldRealTopic.getId(), Granularity.hour);

        topicMerger.merge(newRealTopic.getId(), oldRealTopic.getId());

        final Statistics statistics = Repositories.statistics().getAll().get(0);
        assertThat(statistics.getTopicId()).isEqualTo(newRealTopic.getId());
    }

    private TopicMerger topicMerger;
}