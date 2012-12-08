package com.feelhub.domain.feeling;

import com.feelhub.domain.eventbus.WithDomainEvent;
import com.feelhub.domain.topic.TopicPatch;
import com.feelhub.domain.topic.usable.real.RealTopic;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.google.common.collect.Lists;
import com.google.inject.*;
import org.junit.*;

import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsFeelingManager {

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Before
    public void before() {
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
            }
        });
        feelingManager = injector.getInstance(FeelingManager.class);
    }

    @Test
    public void canChangeSentimentsTopicsForAConcept() {
        final RealTopic realTopic1 = TestFactories.topics().newCompleteRealTopic();
        final RealTopic realTopic2 = TestFactories.topics().newCompleteRealTopic();
        final Feeling feeling1 = TestFactories.feelings().newFeelingWithoutSentiments();
        final Feeling feeling2 = TestFactories.feelings().newFeelingWithoutSentiments();
        feeling1.addSentiment(realTopic1, SentimentValue.good);
        feeling2.addSentiment(realTopic1, SentimentValue.good);
        feeling1.addSentiment(realTopic2, SentimentValue.bad);
        feeling2.addSentiment(realTopic2, SentimentValue.bad);
        final TopicPatch topicPatch = new TopicPatch(realTopic1.getId());
        topicPatch.addOldTopicId(realTopic2.getId());

        feelingManager.merge(topicPatch);

        final List<Sentiment> sentiments = Lists.newArrayList();
        sentiments.addAll(feeling1.getSentiments());
        sentiments.addAll(feeling2.getSentiments());
        for (final Sentiment sentiment : sentiments) {
            assertThat(sentiment.getTopicId(), is(realTopic1.getId()));
        }
    }

    private FeelingManager feelingManager;
}
