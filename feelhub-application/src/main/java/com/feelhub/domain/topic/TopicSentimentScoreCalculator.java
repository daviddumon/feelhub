package com.feelhub.domain.topic;


import com.feelhub.domain.feeling.*;
import com.google.common.base.Predicate;
import com.google.common.collect.*;
import org.joda.time.DateTime;

import java.util.*;

public final class TopicSentimentScoreCalculator {

    int sentimentScore(List<Sentiment> sentiments) {
        if (sentiments.isEmpty()) {
            return 0;
        } else if (sentiments.size() == 1) {
            return (int) sentimentScore(1d, sentiments.get(0).getSentimentValue());
        }
        return sentimentScore(scoresBySentimentValue(sentiments, firstSentiment(sentiments).getCreationDate(), offsetBetweenSentiments(sentiments)));
    }

    private Sentiment firstSentiment(List<Sentiment> sentiments) {
        Ordering<Sentiment> ordering = new Ordering<Sentiment>() {
            @Override
            public int compare(Sentiment left, Sentiment right) {
                return left.getCreationDate().compareTo(right.getCreationDate());
            }
        };
        return ordering.min(sentiments);
    }

    private long offsetBetweenSentiments(List<Sentiment> sentiments) {
        return lastSentiment(sentiments).getCreationDate().getMillis() - firstSentiment(sentiments).getCreationDate().getMillis();
    }

    private Sentiment lastSentiment(List<Sentiment> sentiments) {
        Ordering<Sentiment> ordering = new Ordering<Sentiment>() {
            @Override
            public int compare(Sentiment left, Sentiment right) {
                return left.getCreationDate().compareTo(right.getCreationDate());
            }
        };
        return ordering.max(sentiments);
    }

    private Map<SentimentValue, Double> scoresBySentimentValue(List<Sentiment> sentiments, DateTime firstSentiment, double offset) {
        Map<SentimentValue, Double> result = Maps.newHashMap();
        for (Map.Entry<SentimentValue, List<Sentiment>> entry : sentimentsBySentimentValue(sentiments).entrySet()) {
            result.put(entry.getKey(), scoreForSentiments(firstSentiment, offset, entry.getValue()));
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

    private double scoreForSentiments(DateTime firstSentiment, double offset, List<Sentiment> sentiments) {
        double score = 0;
        for (Sentiment sentiment : sentiments) {
            score += (sentiment.getCreationDate().getMillis() - firstSentiment.getMillis()) / offset;
        }
        return score;
    }

    private int sentimentScore(Map<SentimentValue, Double> scoreBySentimentValue) {
        double totalScore = totalScore(scoreBySentimentValue);
        int finalResult = 0;
        for (Map.Entry<SentimentValue, Double> entry : scoreBySentimentValue.entrySet()) {
            SentimentValue key = entry.getKey();
            finalResult += sentimentScore(entry.getValue() / totalScore, key);
        }
        return finalResult;
    }

    private double sentimentScore(double ponderatedScore, SentimentValue sentimentValue) {
        return (ponderatedScore * 100) * sentimentValue.numericValue();
    }

    private double totalScore(Map<SentimentValue, Double> result) {
        double total = 0;
        for (Map.Entry<SentimentValue, Double> entry : result.entrySet()) {
            total += entry.getValue();
        }
        return total;
    }
}
