package com.feelhub.domain.topic;

import com.feelhub.domain.feeling.*;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.SystemTime;
import com.google.common.collect.Lists;
import org.joda.time.DateTime;
import org.junit.*;

import java.util.*;

import static org.fest.assertions.Assertions.*;

public class TestsTopicSentimentScoreCalculator {

    @Rule
    public SystemTime systemTime = SystemTime.fixed();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Test
    public void scoreIsZeroWhenNoSentiment() {
        final TopicSentimentScoreCalculator calculator = new TopicSentimentScoreCalculator();

        final int score = calculator.sentimentScore(Lists.<Sentiment>newArrayList());

        assertThat(score).isEqualTo(0);
    }

    @Test
    public void scoreIsMinus100WhenAllSentimentsAreBad() {
        final TopicSentimentScoreCalculator calculator = new TopicSentimentScoreCalculator();
        final List<Sentiment> sentiments = Lists.newArrayList(newSentiment(SentimentValue.bad, 11), newSentiment(SentimentValue.bad, 12));

        final int score = calculator.sentimentScore(sentiments);

        assertThat(score).isEqualTo(-100);
    }

    @Test
    public void scoreIs100WhenAllSentimentsAreGood() {
        final TopicSentimentScoreCalculator calculator = new TopicSentimentScoreCalculator();
        final List<Sentiment> sentiments = Lists.newArrayList(newSentiment(SentimentValue.good, 11), newSentiment(SentimentValue.good, 12));

        final int score = calculator.sentimentScore(sentiments);

        assertThat(score).isEqualTo(100);
    }

    @Test
    public void scoreIsPonderatedByCreationDate() {
        final TopicSentimentScoreCalculator calculator = new TopicSentimentScoreCalculator();
        final List<Sentiment> sentiments = Lists.newArrayList();
        sentiments.add(newSentiment(SentimentValue.bad, 11));
        sentiments.add(newSentiment(SentimentValue.neutral, 12));
        sentiments.add(newSentiment(SentimentValue.bad, 13));
        sentiments.add(newSentiment(SentimentValue.bad, 14));
        sentiments.add(newSentiment(SentimentValue.neutral, 15));
        sentiments.add(newSentiment(SentimentValue.good, 16));
        sentiments.add(newSentiment(SentimentValue.good, 17));
        sentiments.add(newSentiment(SentimentValue.good, 18));
        sentiments.add(newSentiment(SentimentValue.bad, 19));
        sentiments.add(newSentiment(SentimentValue.good, 20));

        final int score = calculator.sentimentScore(sentiments);

        assertThat(score).isIn(Lists.newArrayList(31, 32));
    }

    private Sentiment newSentiment(final SentimentValue sentimentValue, final long time) {
        systemTime.set(new DateTime(time));
        return new Sentiment(UUID.randomUUID(), sentimentValue);
    }
}
