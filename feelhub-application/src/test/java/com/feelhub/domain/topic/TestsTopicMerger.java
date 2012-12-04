package com.feelhub.domain.topic;

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
            }
        });
        topicMerger = injector.getInstance(TopicMerger.class);
    }

    @Test
    public void mergeTags() {
        final Topic newTopic = TestFactories.topics().newTopic();
        final Topic oldTopic = TestFactories.topics().newTopic();

        topicMerger.merge(newTopic, oldTopic);

        for (final Tag tag : Repositories.tags().getAll()) {
            assertThat(tag.getTopicIds()).contains(newTopic.getId());
            assertThat(oldTopic.getId()).isNotIn(tag.getTopicIds());
        }
    }

    //@Test
    //public void mergeIllustrations() {
    //    final Topic good = TestFactories.topics().newTopic();
    //    final Topic bad = TestFactories.topics().newTopic();
    //    TestFactories.illustrations().newIllustration(good.getId());
    //    TestFactories.illustrations().newIllustration(bad.getId());
    //
    //    topicMerger.merge(createListOfKeyword(good, bad));
    //
    //    for (final Illustration illustration : Repositories.illustrations().getAll()) {
    //        assertThat(illustration.getTopicId()).isEqualTo(good.getId());
    //    }
    //}
    //
    //@Test
    //public void mergeFeelings() {
    //    final Topic good = TestFactories.topics().newTopic();
    //    final Topic bad = TestFactories.topics().newTopic();
    //    TestFactories.feelings().newFeelings(good.getId(), 10);
    //    TestFactories.feelings().newFeelings(bad.getId(), 10);
    //
    //    topicMerger.merge(createListOfKeyword(good, bad));
    //
    //    for (final Feeling feeling : Repositories.feelings().getAll()) {
    //        for (final Sentiment sentiment : feeling.getSentiments()) {
    //            assertThat(sentiment.getTopicId()).isEqualTo(good.getId());
    //        }
    //    }
    //}
    //
    //@Test
    //public void mergeRelations() {
    //    final Topic good = TestFactories.topics().newTopic();
    //    final Topic bad = TestFactories.topics().newTopic();
    //    final Topic topic3 = TestFactories.topics().newTopic();
    //    final Relation relation1 = TestFactories.relations().newRelation(bad.getId(), topic3.getId());
    //    final Relation relation2 = TestFactories.relations().newRelation(topic3.getId(), bad.getId());
    //
    //    topicMerger.merge(createListOfKeyword(good, bad));
    //
    //    assertThat(relation1.getFromId()).isEqualTo(good.getId());
    //    assertThat(relation1.getToId()).isEqualTo(topic3.getId());
    //    assertThat(relation2.getFromId()).isEqualTo(topic3.getId());
    //    assertThat(relation2.getToId()).isEqualTo(good.getId());
    //}
    //
    //@Test
    //public void mergeStatistics() {
    //    final Topic good = TestFactories.topics().newTopic();
    //    final Topic bad = TestFactories.topics().newTopic();
    //    TestFactories.statistics().newStatisticsWithSentiments(bad.getId(), Granularity.hour);
    //
    //    topicMerger.merge(createListOfKeyword(good, bad));
    //
    //    final Statistics statistics = Repositories.statistics().getAll().get(0);
    //    assertThat(statistics.getTopicId()).isEqualTo(good.getId());
    //}
    //
    //private List<Tag> createListOfKeyword(final Topic good, final Topic bad) {
    //    final List<Tag> tags = Lists.newArrayList();
    //    final Tag first = TestFactories.tags().newTag("first", FeelhubLanguage.reference(), good.getId());
    //    tags.add(first);
    //    time.waitDays(1);
    //    tags.add(TestFactories.tags().newTag("second", FeelhubLanguage.none(), bad.getId()));
    //    tags.add(TestFactories.tags().newTag("third"));
    //    return tags;
    //}

    private TopicMerger topicMerger;
}
