package com.feelhub.domain.keyword;

import com.feelhub.domain.feeling.*;
import com.feelhub.domain.illustration.Illustration;
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

public class TestsKeywordMerger {

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
        keywordMerger = injector.getInstance(KeywordMerger.class);
    }

    @Test
    public void mergeKeywords() {
        final Topic good = TestFactories.topics().newTopic();
        final Topic bad = TestFactories.topics().newTopic();

        keywordMerger.merge(createListOfKeyword(good, bad));

        for (final Keyword keyword : Repositories.keywords().getAll()) {
            assertThat(keyword.getTopicId()).isEqualTo(good.getId());
        }
    }

    @Test
    public void mergeTopics() {
        final Topic good = TestFactories.topics().newTopic();
        final Topic bad = TestFactories.topics().newTopic();

        keywordMerger.merge(createListOfKeyword(good, bad));

        final List<Topic> topics = Repositories.topics().getAll();
        assertThat(topics.get(0).isActive()).isTrue();
        assertThat(topics.get(1).isActive()).isFalse();
        assertThat(topics.get(2).isActive()).isFalse();
    }

    @Test
    public void mergeIllustrations() {
        final Topic good = TestFactories.topics().newTopic();
        final Topic bad = TestFactories.topics().newTopic();
        TestFactories.illustrations().newIllustration(good);
        TestFactories.illustrations().newIllustration(bad);

        keywordMerger.merge(createListOfKeyword(good, bad));

        for (final Illustration illustration : Repositories.illustrations().getAll()) {
            assertThat(illustration.getTopicId()).isEqualTo(good.getId());
        }
    }

    @Test
    public void mergeFeelings() {
        final Topic good = TestFactories.topics().newTopic();
        final Topic bad = TestFactories.topics().newTopic();
        TestFactories.feelings().newFeelings(good, 10);
        TestFactories.feelings().newFeelings(bad, 10);

        keywordMerger.merge(createListOfKeyword(good, bad));

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
        final Relation relation1 = TestFactories.relations().newRelation(bad, topic3);
        final Relation relation2 = TestFactories.relations().newRelation(topic3, bad);

        keywordMerger.merge(createListOfKeyword(good, bad));

        assertThat(relation1.getFromId()).isEqualTo(good.getId());
        assertThat(relation1.getToId()).isEqualTo(topic3.getId());
        assertThat(relation2.getFromId()).isEqualTo(topic3.getId());
        assertThat(relation2.getToId()).isEqualTo(good.getId());
    }

    @Test
    public void mergeStatistics() {
        final Topic good = TestFactories.topics().newTopic();
        final Topic bad = TestFactories.topics().newTopic();
        TestFactories.statistics().newStatisticsWithSentiments(bad, Granularity.hour);

        keywordMerger.merge(createListOfKeyword(good, bad));

        final Statistics statistics = Repositories.statistics().getAll().get(0);
        assertThat(statistics.getTopicId()).isEqualTo(good.getId());
    }

    private List<Keyword> createListOfKeyword(final Topic good, final Topic bad) {
        final List<Keyword> keywords = Lists.newArrayList();
        final Keyword first = TestFactories.keywords().newWord("first", FeelhubLanguage.reference(), good);
        keywords.add(first);
        time.waitDays(1);
        keywords.add(TestFactories.keywords().newWord("second", FeelhubLanguage.none(), bad));
        keywords.add(TestFactories.keywords().newWord("third"));
        return keywords;
    }

    private KeywordMerger keywordMerger;
}
