package com.feelhub.domain.feeling.analyze;

import com.feelhub.domain.feeling.*;
import com.feelhub.domain.topic.http.uri.*;
import com.feelhub.domain.topic.real.RealTopic;
import com.feelhub.domain.user.User;
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

        final List<Sentiment> sentiments = sentimentExtractor.analyze("", topic.getId(), user.getId());

        assertThat(sentiments).isEqualTo(expectedSentiments);
    }

    @Test
    public void createRealTopicWithoutTypes() {
        final RealTopic topic = TestFactories.topics().newCompleteRealTopic();

        final List<Sentiment> sentiments = sentimentExtractor.analyze("I like +#+oranges", topic.getId(), user.getId());

        assertThat(sentiments.size()).isEqualTo(2);
        assertThat(sentiments.get(0).getTopicId()).isEqualTo(topic.getId());
        assertThat(sentiments.get(0).getSentimentValue()).isEqualTo(SentimentValue.none);
        assertThat(sentiments.get(1).getTopicId()).isNull();
        assertThat(sentiments.get(1).getSentimentValue()).isEqualTo(SentimentValue.good);
    }

    @Test
    public void canCreateHttpTopics() {
        final RealTopic topic = TestFactories.topics().newCompleteRealTopic();

        final List<Sentiment> sentiments = sentimentExtractor.analyze("I like -#-www.google.fr+", topic.getId(), user.getId());

        assertThat(sentiments.size()).isEqualTo(2);
        assertThat(sentiments.get(0).getTopicId()).isEqualTo(topic.getId());
        assertThat(sentiments.get(0).getSentimentValue()).isEqualTo(SentimentValue.none);
        assertThat(sentiments.get(1).getTopicId()).isNotNull();
        assertThat(sentiments.get(1).getSentimentValue()).isEqualTo(SentimentValue.bad);
    }

    private Injector injector;
    private SentimentExtractor sentimentExtractor;
    private List<Sentiment> expectedSentiments = Lists.newArrayList();
    private User user;
    private String canonicalUri;
}
