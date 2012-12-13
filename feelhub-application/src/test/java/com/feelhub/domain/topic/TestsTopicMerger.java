package com.feelhub.domain.topic;

import com.feelhub.application.TagService;
import com.feelhub.domain.feeling.*;
import com.feelhub.domain.relation.Relation;
import com.feelhub.domain.statistics.*;
import com.feelhub.domain.tag.Tag;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.real.RealTopic;
import com.feelhub.domain.topic.web.*;
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

    @Test
    public void mergeNames() {
        final WebTopic webTopic = TestFactories.topics().newSimpleWebTopic(WebTopicType.Website);
        final WebTopic oldWebTopic = TestFactories.topics().newSimpleWebTopic(WebTopicType.Website);
        webTopic.addName(FeelhubLanguage.REFERENCE, "Webtopic-reference");
        webTopic.addName(FeelhubLanguage.fromCode("fr"), "Webtopic-fr");
        oldWebTopic.addName(FeelhubLanguage.REFERENCE, "Oldwebtopic-reference");
        oldWebTopic.addName(FeelhubLanguage.fromCode("de"), "Oldwebtopic-de");

        topicMerger.merge(webTopic.getId(), oldWebTopic.getId());

        assertThat(webTopic.getName(FeelhubLanguage.REFERENCE)).isEqualTo("Webtopic-reference");
        assertThat(webTopic.getName(FeelhubLanguage.fromCode("fr"))).isEqualTo("Webtopic-fr");
        assertThat(webTopic.getName(FeelhubLanguage.fromCode("de"))).isEqualTo("Oldwebtopic-de");
    }

    @Test
    public void mergeDescriptions() {
        final WebTopic webTopic = TestFactories.topics().newSimpleWebTopic(WebTopicType.Website);
        final WebTopic oldWebTopic = TestFactories.topics().newSimpleWebTopic(WebTopicType.Website);
        webTopic.addDescription(FeelhubLanguage.REFERENCE, "Webtopic-reference");
        webTopic.addDescription(FeelhubLanguage.fromCode("fr"), "Webtopic-fr");
        oldWebTopic.addDescription(FeelhubLanguage.REFERENCE, "Oldwebtopic-reference");
        oldWebTopic.addDescription(FeelhubLanguage.fromCode("de"), "Oldwebtopic-de");

        topicMerger.merge(webTopic.getId(), oldWebTopic.getId());

        assertThat(webTopic.getDescription(FeelhubLanguage.REFERENCE)).isEqualTo("Webtopic-reference");
        assertThat(webTopic.getDescription(FeelhubLanguage.fromCode("fr"))).isEqualTo("Webtopic-fr");
        assertThat(webTopic.getDescription(FeelhubLanguage.fromCode("de"))).isEqualTo("Oldwebtopic-de");
    }

    @Test
    public void mergeIllustrationLinksIfEmpty() {
        final WebTopic webTopic = TestFactories.topics().newSimpleWebTopic(WebTopicType.Website);
        final WebTopic oldWebTopic = TestFactories.topics().newSimpleWebTopic(WebTopicType.Website);
        oldWebTopic.setIllustrationLink("illustrationLink");

        topicMerger.merge(webTopic.getId(), oldWebTopic.getId());

        assertThat(webTopic.getIllustrationLink()).isEqualTo(oldWebTopic.getIllustrationLink());
    }

    @Test
    public void doNotMergeIllustrationLinksIfExists() {
        final WebTopic webTopic = TestFactories.topics().newSimpleWebTopic(WebTopicType.Website);
        final WebTopic oldWebTopic = TestFactories.topics().newSimpleWebTopic(WebTopicType.Website);
        webTopic.setIllustrationLink("weblink");
        oldWebTopic.setIllustrationLink("oldlink");

        topicMerger.merge(webTopic.getId(), oldWebTopic.getId());

        assertThat(webTopic.getIllustrationLink()).isEqualTo("weblink");
    }

    @Test
    public void mergeSubtypes() {
        final WebTopic webTopic = TestFactories.topics().newSimpleWebTopic(WebTopicType.Website);
        final WebTopic oldWebTopic = TestFactories.topics().newSimpleWebTopic(WebTopicType.Website);
        webTopic.addSubType("websub1");
        oldWebTopic.addSubType("oldsub1");
        oldWebTopic.addSubType("oldsub2");
        oldWebTopic.addSubType("websub1");

        topicMerger.merge(webTopic.getId(), oldWebTopic.getId());

        assertThat(webTopic.getSubTypes()).contains("websub1");
        assertThat(webTopic.getSubTypes()).contains("oldsub1");
        assertThat(webTopic.getSubTypes()).contains("oldsub2");
        assertThat(webTopic.getSubTypes().size()).isEqualTo(3);
    }

    @Test
    public void mergeUrls() {
        final WebTopic webTopic = TestFactories.topics().newSimpleWebTopic(WebTopicType.Website);
        final WebTopic oldWebTopic = TestFactories.topics().newSimpleWebTopic(WebTopicType.Website);
        webTopic.addUrl("weburl1");
        oldWebTopic.addUrl("oldurl1");
        oldWebTopic.addUrl("oldurl2");
        oldWebTopic.addUrl("weburl1");

        topicMerger.merge(webTopic.getId(), oldWebTopic.getId());

        assertThat(webTopic.getUrls()).contains("weburl1");
        assertThat(webTopic.getUrls()).contains("oldurl1");
        assertThat(webTopic.getUrls()).contains("oldurl2");
        assertThat(webTopic.getUrls().size()).isEqualTo(3);
    }

    private TopicMerger topicMerger;
}
