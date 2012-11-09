package com.feelhub.domain.feeling;

import com.feelhub.domain.eventbus.WithDomainEvent;
import com.feelhub.domain.topic.*;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.google.common.collect.Lists;
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
        feelingManager = new FeelingManager();
    }

    @Test
    public void canChangeSentimentsTopicsForAConcept() {
        final Topic topic1 = TestFactories.topics().newTopic();
        final Topic topic2 = TestFactories.topics().newTopic();
        final Feeling feeling1 = TestFactories.feelings().newFeelingWithoutSentiments();
        final Feeling feeling2 = TestFactories.feelings().newFeelingWithoutSentiments();
        feeling1.addSentiment(topic1, SentimentValue.good);
        feeling2.addSentiment(topic1, SentimentValue.good);
        feeling1.addSentiment(topic2, SentimentValue.bad);
        feeling2.addSentiment(topic2, SentimentValue.bad);
        final TopicPatch topicPatch = new TopicPatch(topic1.getId());
        topicPatch.addOldTopicId(topic2.getId());

        feelingManager.merge(topicPatch);

        final List<Sentiment> sentiments = Lists.newArrayList();
        sentiments.addAll(feeling1.getSentiments());
        sentiments.addAll(feeling2.getSentiments());
        for (final Sentiment sentiment : sentiments) {
            assertThat(sentiment.getTopicId(), is(topic1.getId()));
        }
    }

    private FeelingManager feelingManager;
}
