package com.feelhub.domain.topic;

import com.feelhub.application.TagService;
import com.feelhub.domain.feeling.*;
import com.feelhub.domain.media.Media;
import com.feelhub.domain.related.Related;
import com.feelhub.domain.statistics.*;
import com.feelhub.domain.tag.Tag;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.http.*;
import com.feelhub.domain.topic.http.uri.Uri;
import com.feelhub.domain.topic.real.RealTopic;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.google.inject.*;
import org.junit.*;

import static org.fest.assertions.Assertions.*;

public class TopicMergerTest {

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
        final Tag tag1 = TestFactories.tags().newTag(newRealTopic.getName(FeelhubLanguage.reference()), FeelhubLanguage.reference());
        tag1.addTopic(newRealTopic, FeelhubLanguage.reference());
        final RealTopic oldRealTopic = TestFactories.topics().newCompleteRealTopic("tag2");
        final Tag tag2 = TestFactories.tags().newTag(oldRealTopic.getName(FeelhubLanguage.reference()), FeelhubLanguage.reference());
        tag2.addTopic(oldRealTopic, FeelhubLanguage.reference());

        topicMerger.merge(newRealTopic.getId(), oldRealTopic.getId());

        for (final Tag tag : Repositories.tags().getAll()) {
            assertThat(tag.getTopicsIdFor(FeelhubLanguage.reference())).contains(newRealTopic.getId());
            assertThat(oldRealTopic.getId()).isNotIn(tag.getTopicsIdFor(FeelhubLanguage.reference()));
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
    public void mergeRelated() {
        final RealTopic newRealTopic = TestFactories.topics().newCompleteRealTopic("tag1");
        final RealTopic oldRealTopic = TestFactories.topics().newCompleteRealTopic("tag2");
        final RealTopic anotherRealTopic = TestFactories.topics().newCompleteRealTopic();
        final Related related1 = TestFactories.related().newRelated(oldRealTopic.getId(), anotherRealTopic.getId());
        final Related related2 = TestFactories.related().newRelated(anotherRealTopic.getId(), oldRealTopic.getId());

        topicMerger.merge(newRealTopic.getId(), oldRealTopic.getId());

        assertThat(related1.getFromId()).isEqualTo(newRealTopic.getId());
        assertThat(related1.getToId()).isEqualTo(anotherRealTopic.getId());
        assertThat(related2.getFromId()).isEqualTo(anotherRealTopic.getId());
        assertThat(related2.getToId()).isEqualTo(newRealTopic.getId());
    }

    @Test
    public void mergeMedias() {
        final HttpTopic oldTopic = TestFactories.topics().newMediaTopic();
        final HttpTopic newTopic = TestFactories.topics().newMediaTopic();
        final RealTopic anotherRealTopic = TestFactories.topics().newCompleteRealTopic();
        final Media media1 = TestFactories.medias().newMedia(oldTopic.getId(), anotherRealTopic.getId());
        final Media media2 = TestFactories.medias().newMedia(anotherRealTopic.getId(), oldTopic.getId());

        topicMerger.merge(newTopic.getId(), oldTopic.getId());

        assertThat(media1.getFromId()).isEqualTo(newTopic.getId());
        assertThat(media1.getToId()).isEqualTo(anotherRealTopic.getId());
        assertThat(media2.getFromId()).isEqualTo(anotherRealTopic.getId());
        assertThat(media2.getToId()).isEqualTo(newTopic.getId());
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
        final HttpTopic httpTopic = TestFactories.topics().newSimpleHttpTopic(HttpTopicType.Website);
        final HttpTopic oldHttpTopic = TestFactories.topics().newSimpleHttpTopic(HttpTopicType.Website);
        httpTopic.addName(FeelhubLanguage.REFERENCE, "Webtopic-reference");
        httpTopic.addName(FeelhubLanguage.fromCode("fr"), "Webtopic-fr");
        oldHttpTopic.addName(FeelhubLanguage.REFERENCE, "Oldwebtopic-reference");
        oldHttpTopic.addName(FeelhubLanguage.fromCode("de"), "Oldwebtopic-de");

        topicMerger.merge(httpTopic.getId(), oldHttpTopic.getId());

        assertThat(httpTopic.getName(FeelhubLanguage.REFERENCE)).isEqualTo("Webtopic-reference");
        assertThat(httpTopic.getName(FeelhubLanguage.fromCode("fr"))).isEqualTo("Webtopic-fr");
        assertThat(httpTopic.getName(FeelhubLanguage.fromCode("de"))).isEqualTo("Oldwebtopic-de");
    }

    @Test
    public void mergeDescriptions() {
        final HttpTopic httpTopic = TestFactories.topics().newSimpleHttpTopic(HttpTopicType.Website);
        final HttpTopic oldHttpTopic = TestFactories.topics().newSimpleHttpTopic(HttpTopicType.Website);
        httpTopic.addDescription(FeelhubLanguage.REFERENCE, "Webtopic-reference");
        httpTopic.addDescription(FeelhubLanguage.fromCode("fr"), "Webtopic-fr");
        oldHttpTopic.addDescription(FeelhubLanguage.REFERENCE, "Oldwebtopic-reference");
        oldHttpTopic.addDescription(FeelhubLanguage.fromCode("de"), "Oldwebtopic-de");

        topicMerger.merge(httpTopic.getId(), oldHttpTopic.getId());

        assertThat(httpTopic.getDescription(FeelhubLanguage.REFERENCE)).isEqualTo("Webtopic-reference");
        assertThat(httpTopic.getDescription(FeelhubLanguage.fromCode("fr"))).isEqualTo("Webtopic-fr");
        assertThat(httpTopic.getDescription(FeelhubLanguage.fromCode("de"))).isEqualTo("Oldwebtopic-de");
    }

    @Test
    public void mergeIllustrationLinksIfEmpty() {
        final HttpTopic httpTopic = TestFactories.topics().newSimpleHttpTopic(HttpTopicType.Website);
        final HttpTopic oldHttpTopic = TestFactories.topics().newSimpleHttpTopic(HttpTopicType.Website);
        oldHttpTopic.setIllustration("illustrationLink");

        topicMerger.merge(httpTopic.getId(), oldHttpTopic.getId());

        assertThat(httpTopic.getIllustration()).isEqualTo(oldHttpTopic.getIllustration());
    }

    @Test
    public void doNotMergeIllustrationLinksIfExists() {
        final HttpTopic httpTopic = TestFactories.topics().newSimpleHttpTopic(HttpTopicType.Website);
        final HttpTopic oldHttpTopic = TestFactories.topics().newSimpleHttpTopic(HttpTopicType.Website);
        httpTopic.setIllustration("weblink");
        oldHttpTopic.setIllustration("oldlink");

        topicMerger.merge(httpTopic.getId(), oldHttpTopic.getId());

        assertThat(httpTopic.getIllustration()).isEqualTo("weblink");
    }

    @Test
    public void mergeSubtypes() {
        final HttpTopic httpTopic = TestFactories.topics().newSimpleHttpTopic(HttpTopicType.Website);
        final HttpTopic oldHttpTopic = TestFactories.topics().newSimpleHttpTopic(HttpTopicType.Website);
        httpTopic.addSubType("websub1");
        oldHttpTopic.addSubType("oldsub1");
        oldHttpTopic.addSubType("oldsub2");
        oldHttpTopic.addSubType("websub1");

        topicMerger.merge(httpTopic.getId(), oldHttpTopic.getId());

        assertThat(httpTopic.getSubTypes()).contains("websub1");
        assertThat(httpTopic.getSubTypes()).contains("oldsub1");
        assertThat(httpTopic.getSubTypes()).contains("oldsub2");
        assertThat(httpTopic.getSubTypes().size()).isEqualTo(3);
    }

    @Test
    public void mergeUris() {
        final HttpTopic httpTopic = TestFactories.topics().newSimpleHttpTopic(HttpTopicType.Website);
        final HttpTopic oldHttpTopic = TestFactories.topics().newSimpleHttpTopic(HttpTopicType.Website);
        final Uri uri1 = new Uri("weburl1");
        httpTopic.addUri(uri1);
        final Uri uri2 = new Uri("oldurl1");
        oldHttpTopic.addUri(uri2);
        final Uri uri3 = new Uri("oldurl2");
        oldHttpTopic.addUri(uri3);
        oldHttpTopic.addUri(uri1);

        topicMerger.merge(httpTopic.getId(), oldHttpTopic.getId());

        assertThat(httpTopic.getUris()).contains(uri1);
        assertThat(httpTopic.getUris()).contains(uri2);
        assertThat(httpTopic.getUris()).contains(uri3);
        assertThat(httpTopic.getUris().size()).isEqualTo(3);
    }

    private TopicMerger topicMerger;
}
