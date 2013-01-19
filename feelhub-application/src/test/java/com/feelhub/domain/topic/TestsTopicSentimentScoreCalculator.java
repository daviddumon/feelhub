package com.feelhub.domain.topic;

import com.feelhub.domain.feeling.Sentiment;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.google.common.collect.Lists;
import org.joda.time.DateTime;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class TestsTopicSentimentScoreCalculator {
    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Test
    public void scoreIsZeroWhenNoSentiment() {
        TopicSentimentScoreCalculator calculator = new TopicSentimentScoreCalculator();

        int score = calculator.getSentimentScore(Lists.<Sentiment>newArrayList(), null);

        assertThat(score).isEqualTo(0);
    }

    @Test
    public void scoreIsMinus100WhenAllSentimentsAreBad() {
        TopicSentimentScoreCalculator calculator = new TopicSentimentScoreCalculator();
        List<Sentiment> sentiments = Lists.newArrayList(TestFactories.sentiments().newBadSentiment(), TestFactories.sentiments().newBadSentiment());

        int score = calculator.getSentimentScore(sentiments, new DateTime());

        assertThat(score).isEqualTo(-100);
    }

    @Test
    public void scoreIs100WhenAllSentimentsAreGood() {
        TopicSentimentScoreCalculator calculator = new TopicSentimentScoreCalculator();
        List<Sentiment> sentiments = Lists.newArrayList(TestFactories.sentiments().newGoodSentiment(), TestFactories.sentiments().newGoodSentiment());

        int score = calculator.getSentimentScore(sentiments, new DateTime());

        assertThat(score).isEqualTo(100);
    }

    @Test
    public void canHave() {
        TopicSentimentScoreCalculator calculator = new TopicSentimentScoreCalculator();
        List<Sentiment> sentiments = Lists.newArrayList(TestFactories.sentiments().newGoodSentiment(), TestFactories.sentiments().newGoodSentiment(), TestFactories.sentiments().newBadSentiment());

        int score = calculator.getSentimentScore(sentiments, new DateTime());

        assertThat(score).isEqualTo(33);
    }
}
