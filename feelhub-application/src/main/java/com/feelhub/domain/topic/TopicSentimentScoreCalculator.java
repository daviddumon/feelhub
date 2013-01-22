package com.feelhub.domain.topic;


import com.feelhub.domain.feeling.*;
import com.google.common.base.Predicate;
import com.google.common.collect.*;
import org.joda.time.DateTime;

import java.util.*;

public final class TopicSentimentScoreCalculator {

    int sentimentScore(List<Sentiment> sentiments, DateTime topicCreationDate) {
        if (sentiments.isEmpty()) {
            return 0;
        }
        Sentiment lastSentiment = findLastSentiment(sentiments);
        long offsetBetweenLastSentimentAndTopicCreation = Math.max(lastSentiment.getCreationDate().getMillis() - topicCreationDate.getMillis(), 1);
        return sentimentScore(scoresBySentimentValue(sentiments, topicCreationDate, offsetBetweenLastSentimentAndTopicCreation));
    }

    private Sentiment findLastSentiment(List<Sentiment> sentiments) {
        Ordering<Sentiment> ordering = new Ordering<Sentiment>() {
            @Override
            public int compare(Sentiment left, Sentiment right) {
                return left.getCreationDate().compareTo(right.getCreationDate());
            }
        };
        return ordering.max(sentiments);
    }

    private Map<SentimentValue, Double> scoresBySentimentValue(List<Sentiment> sentiments, DateTime topicCreationDate, double millisecondsUntilLastSentiment) {
        Map<SentimentValue, Double> result = Maps.newHashMap();
        for (Map.Entry<SentimentValue, List<Sentiment>> entry : sentimentsBySentimentValue(sentiments).entrySet()) {
            result.put(entry.getKey(), scoreForSentiments(topicCreationDate, millisecondsUntilLastSentiment, entry.getValue()));
        }
        return result;
    }

    private Map<SentimentValue, List<Sentiment>> sentimentsBySentimentValue(List<Sentiment> sentiments) {
        Map<SentimentValue, List<Sentiment>> result = Maps.newHashMap();
        for (final SentimentValue sentimentValue : SentimentValue.values()) {
            result.put(sentimentValue, Lists.newArrayList(Iterables.filter(sentiments, new Predicate<Sentiment>() {
                @Override
                public boolean apply(Sentiment input) {
                    return input.getSentimentValue() == sentimentValue;
                }
            })));
        }
        return result;
    }

    private double scoreForSentiments(DateTime topicCreationDate, double millisecondsUntilLastSentiment, List<Sentiment> value) {
        double score = 0;
        for (Sentiment sentiment : value) {
            score += (sentiment.getCreationDate().getMillis() - topicCreationDate.getMillis()) / millisecondsUntilLastSentiment;
        }
        return score;
    }

    private int sentimentScore(Map<SentimentValue, Double> scoreBySentimentValue) {
        double totalScore = totalScore(scoreBySentimentValue);
        int finalResult = 0;
        for (Map.Entry<SentimentValue, Double> entry : scoreBySentimentValue.entrySet()) {
            finalResult += ((entry.getValue() / totalScore) * 100) * entry.getKey().numericValue();
        }
        return finalResult;
    }

    private double totalScore(Map<SentimentValue, Double> result) {
        double total = 0;
        for (Map.Entry<SentimentValue, Double> entry : result.entrySet()) {
            total += entry.getValue();
        }
        return total;
    }
}
