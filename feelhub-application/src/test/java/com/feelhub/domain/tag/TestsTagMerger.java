package com.feelhub.domain.tag;

import com.feelhub.domain.feeling.*;
import com.feelhub.domain.meta.Illustration;
import com.feelhub.domain.relation.Relation;
import com.feelhub.domain.statistics.*;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.Topic;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.*;
import com.google.common.collect.Lists;
import com.google.inject.*;
import org.junit.*;

import java.util.List;

import static org.fest.assertions.Assertions.*;

public class TestsTagMerger {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Before
    public void before() {
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
            }
        });
        tagMerger = injector.getInstance(TagMerger.class);
    }

    @Test
    public void mergeKeywords() {
        final Topic good = TestFactories.topics().newTopic();
        final Topic bad = TestFactories.topics().newTopic();

        tagMerger.merge(createListOfKeyword(good, bad));

        for (final Tag tag : Repositories.keywords().getAll()) {
            assertThat(tag.getTopicId()).isEqualTo(good.getId());
        }
    }

    @Test
    public void mergeIllustrations() {
        final Topic good = TestFactories.topics().newTopic();
        final Topic bad = TestFactories.topics().newTopic();
        TestFactories.illustrations().newIllustration(good.getId());
        TestFactories.illustrations().newIllustration(bad.getId());

        tagMerger.merge(createListOfKeyword(good, bad));

        for (final Illustration illustration : Repositories.illustrations().getAll()) {
            assertThat(illustration.getTopicId()).isEqualTo(good.getId());
        }
    }

    @Test
    public void mergeFeelings() {
        final Topic good = TestFactories.topics().newTopic();
        final Topic bad = TestFactories.topics().newTopic();
        TestFactories.feelings().newFeelings(good.getId(), 10);
        TestFactories.feelings().newFeelings(bad.getId(), 10);

        tagMerger.merge(createListOfKeyword(good, bad));

        for (final Feeling feeling : Repositories.feelings().getAll()) {
            for (final Sentiment sentiment : feeling.getSentiments()) {
                assertThat(sentiment.getTopicId()).isEqualTo(good.getId());
            }
        }
    }

    @Test
    public void mergeRelations() {
        final Topic good = TestFactories.topics().newTopic();
        final Topic bad = TestFactories.topics().newTopic();
        final Topic topic3 = TestFactories.topics().newTopic();
        final Relation relation1 = TestFactories.relations().newRelation(bad.getId(), topic3.getId());
        final Relation relation2 = TestFactories.relations().newRelation(topic3.getId(), bad.getId());

        tagMerger.merge(createListOfKeyword(good, bad));

        assertThat(relation1.getFromId()).isEqualTo(good.getId());
        assertThat(relation1.getToId()).isEqualTo(topic3.getId());
        assertThat(relation2.getFromId()).isEqualTo(topic3.getId());
        assertThat(relation2.getToId()).isEqualTo(good.getId());
    }

    @Test
    public void mergeStatistics() {
        final Topic good = TestFactories.topics().newTopic();
        final Topic bad = TestFactories.topics().newTopic();
        TestFactories.statistics().newStatisticsWithSentiments(bad.getId(), Granularity.hour);

        tagMerger.merge(createListOfKeyword(good, bad));

        final Statistics statistics = Repositories.statistics().getAll().get(0);
        assertThat(statistics.getTopicId()).isEqualTo(good.getId());
    }

    private List<Tag> createListOfKeyword(final Topic good, final Topic bad) {
        final List<Tag> tags = Lists.newArrayList();
        final Tag first = TestFactories.tags().newWord("first", FeelhubLanguage.reference(), good.getId());
        tags.add(first);
        time.waitDays(1);
        tags.add(TestFactories.tags().newWord("second", FeelhubLanguage.none(), bad.getId()));
        tags.add(TestFactories.tags().newWord("third"));
        return tags;
    }

    private TagMerger tagMerger;
}
