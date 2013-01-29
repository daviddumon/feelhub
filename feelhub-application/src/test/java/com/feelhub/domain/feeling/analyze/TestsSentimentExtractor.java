package com.feelhub.domain.feeling.analyze;

import com.feelhub.domain.feeling.*;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.http.HttpTopic;
import com.feelhub.domain.topic.http.uri.*;
import com.feelhub.domain.topic.real.RealTopic;
import com.feelhub.domain.user.User;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.google.common.collect.Lists;
import com.google.inject.*;
import org.junit.*;

import java.util.List;

import static org.fest.assertions.Assertions.*;

public class TestsSentimentExtractor {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Before
    public void before() {
        final FakeUriResolver fakeUriResolver = new FakeUriResolver();
        canonicalUri = "http://www.urlcanonique.com";
        fakeUriResolver.thatFind(canonicalUri);
        injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(UriResolver.class).toInstance(fakeUriResolver);
            }
        });
        sentimentExtractor = injector.getInstance(SentimentExtractor.class);
        expectedSentiments.clear();
        user = TestFactories.users().createFakeActiveUser("mail@mail.com");
    }

    @Test
    public void canAnalyzeEmptyFeeling() {
        final RealTopic topic = TestFactories.topics().newCompleteRealTopic();
        expectedSentiments.add(new Sentiment(topic.getId(), SentimentValue.none));

        final List<Sentiment> sentiments = sentimentExtractor.analyze("", topic.getId(), user.getId(), FeelhubLanguage.reference());

        assertThat(sentiments).isEqualTo(expectedSentiments);
    }

    @Test
    public void createRealTopicWithoutTypes() {
        final RealTopic topic = TestFactories.topics().newCompleteRealTopic();

        final List<Sentiment> sentiments = sentimentExtractor.analyze("I like +#+oranges", topic.getId(), user.getId(), FeelhubLanguage.reference());

        assertThat(sentiments.size()).isEqualTo(2);
        assertThat(sentiments.get(0).getTopicId()).isEqualTo(topic.getId());
        assertThat(sentiments.get(0).getSentimentValue()).isEqualTo(SentimentValue.none);
        assertThat(sentiments.get(1).getTopicId()).isNull();
        assertThat(sentiments.get(1).getSentimentValue()).isEqualTo(SentimentValue.good);
    }

    @Test
    public void canCreateHttpTopics() {
        final RealTopic topic = TestFactories.topics().newCompleteRealTopic();

        final List<Sentiment> sentiments = sentimentExtractor.analyze("I like -#-www.google.fr+", topic.getId(), user.getId(), FeelhubLanguage.reference());

        assertThat(sentiments.size()).isEqualTo(2);
        assertThat(sentiments.get(0).getTopicId()).isEqualTo(topic.getId());
        assertThat(sentiments.get(0).getSentimentValue()).isEqualTo(SentimentValue.none);
        assertThat(sentiments.get(1).getTopicId()).isNotNull();
        assertThat(sentiments.get(1).getSentimentValue()).isEqualTo(SentimentValue.bad);
    }

    @Test
    public void canCreateSentimentWithTopicFromSemanticContext() {
        final RealTopic topic = TestFactories.topics().newCompleteRealTopic();
        final RealTopic anotherTopic = TestFactories.topics().newCompleteRealTopic();
        TestFactories.tags().newTag("value1", anotherTopic, FeelhubLanguage.reference());
        TestFactories.tags().newTag("value2", anotherTopic, FeelhubLanguage.fromCode("fr"));
        TestFactories.related().newRelated(topic.getId(), anotherTopic.getId());

        final List<Sentiment> sentiments = sentimentExtractor.analyze("I like +value1 and +value2", topic.getId(), user.getId(), FeelhubLanguage.reference());

        assertThat(sentiments.size()).isEqualTo(3);
        assertThat(sentiments.get(0).getTopicId()).isEqualTo(topic.getId());
        assertThat(sentiments.get(0).getSentimentValue()).isEqualTo(SentimentValue.none);
        assertThat(sentiments.get(1).getTopicId()).isEqualTo(anotherTopic.getId());
        assertThat(sentiments.get(1).getSentimentValue()).isEqualTo(SentimentValue.good);
        assertThat(sentiments.get(2).getTopicId()).isNull();
        assertThat(sentiments.get(2).getSentimentValue()).isEqualTo(SentimentValue.good);
        assertThat(sentiments.get(2).getToken()).isEqualTo("value2");
    }

    @Test
    public void canLookupHttpTopic() {
        final RealTopic topic = TestFactories.topics().newCompleteRealTopic();
        final HttpTopic httpTopic = TestFactories.topics().newCompleteHttpTopic();
        TestFactories.tags().newTag("http://www.fakeurl.com", httpTopic, FeelhubLanguage.none());

        final List<Sentiment> sentiments = sentimentExtractor.analyze("I like -http://www.fakeurl.com", topic.getId(), user.getId(), FeelhubLanguage.reference());

        assertThat(sentiments.size()).isEqualTo(2);
        assertThat(Repositories.topics().getAll().size()).isEqualTo(2);
    }

    private Injector injector;
    private SentimentExtractor sentimentExtractor;
    private List<Sentiment> expectedSentiments = Lists.newArrayList();
    private User user;
    private String canonicalUri;
}
