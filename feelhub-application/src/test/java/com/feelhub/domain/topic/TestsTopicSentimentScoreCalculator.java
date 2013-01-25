package com.feelhub.domain.topic;

import com.feelhub.domain.feeling.Sentiment;
import com.feelhub.domain.feeling.SentimentValue;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.SystemTime;
import com.google.common.collect.Lists;
import org.joda.time.DateTime;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class TestsTopicSentimentScoreCalculator {

    @Rule
    public SystemTime systemTime = SystemTime.fixed();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Test
    public void scoreIsZeroWhenNoSentiment() {
        TopicSentimentScoreCalculator calculator = new TopicSentimentScoreCalculator();

        int score = calculator.sentimentScore(Lists.<Sentiment>newArrayList());

        assertThat(score).isEqualTo(0);
    }

    @Test
    public void scoreIsMinus100WhenAllSentimentsAreBad() {
        TopicSentimentScoreCalculator calculator = new TopicSentimentScoreCalculator();
        List<Sentiment> sentiments = Lists.newArrayList(newSentiment(SentimentValue.bad, 11), newSentiment(SentimentValue.bad, 12));

        int score = calculator.sentimentScore(sentiments);

        assertThat(score).isEqualTo(-100);
    }

    @Test
    public void scoreIs100WhenAllSentimentsAreGood() {
        TopicSentimentScoreCalculator calculator = new TopicSentimentScoreCalculator();
        List<Sentiment> sentiments = Lists.newArrayList(newSentiment(SentimentValue.good, 11), newSentiment(SentimentValue.good, 12));

        int score = calculator.sentimentScore(sentiments);

        assertThat(score).isEqualTo(100);
    }

    @Test
    public void scoreIsPonderatedByCreationDate() {
        TopicSentimentScoreCalculator calculator = new TopicSentimentScoreCalculator();
        List<Sentiment> sentiments = Lists.newArrayList();
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

        int score = calculator.sentimentScore(sentiments);

        assertThat(score).isEqualTo(32);
    }

    private Sentiment newSentiment(SentimentValue sentimentValue, long time) {
        systemTime.set(new DateTime(time));
        return new Sentiment(sentimentValue, "token");
    }
}
