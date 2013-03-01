package com.feelhub.domain.topic;


import com.feelhub.domain.feeling.*;
import com.google.common.base.Predicate;
import com.google.common.collect.*;
import org.joda.time.DateTime;

import java.util.*;

public final class TopicSentimentScoreCalculator {

    int sentimentScore(final List<Sentiment> sentiments) {
        if (sentiments.isEmpty()) {
            return 0;
        } else if (sentiments.size() == 1) {
            return (int) sentimentScore(1d, sentiments.get(0).getSentimentValue());
        }
        return sentimentScore(scoresBySentimentValue(sentiments, firstSentiment(sentiments).getCreationDate(), offsetBetweenSentiments(sentiments)));
    }

    private Sentiment firstSentiment(final List<Sentiment> sentiments) {
        final Ordering<Sentiment> ordering = new Ordering<Sentiment>() {
            @Override
            public int compare(final Sentiment left, final Sentiment right) {
                return left.getCreationDate().compareTo(right.getCreationDate());
            }
        };
        return ordering.min(sentiments);
    }

    private long offsetBetweenSentiments(final List<Sentiment> sentiments) {
        return lastSentiment(sentiments).getCreationDate().getMillis() - firstSentiment(sentiments).getCreationDate().getMillis();
    }

    private Sentiment lastSentiment(final List<Sentiment> sentiments) {
        final Ordering<Sentiment> ordering = new Ordering<Sentiment>() {
            @Override
            public int compare(final Sentiment left, final Sentiment right) {
                return left.getCreationDate().compareTo(right.getCreationDate());
            }
        };
        return ordering.max(sentiments);
    }

    private Map<SentimentValue, Double> scoresBySentimentValue(final List<Sentiment> sentiments, final DateTime firstSentiment, final double offset) {
        final Map<SentimentValue, Double> result = Maps.newHashMap();
        for (final Map.Entry<SentimentValue, List<Sentiment>> entry : sentimentsBySentimentValue(sentiments).entrySet()) {
            result.put(entry.getKey(), scoreForSentiments(firstSentiment, offset, entry.getValue()));
        }
        return result;
    }

    private Map<SentimentValue, List<Sentiment>> sentimentsBySentimentValue(final List<Sentiment> sentiments) {
        final Map<SentimentValue, List<Sentiment>> result = Maps.newHashMap();
        for (final SentimentValue sentimentValue : SentimentValue.values()) {
            result.put(sentimentValue, Lists.newArrayList(Iterables.filter(sentiments, new Predicate<Sentiment>() {
                @Override
                public boolean apply(final Sentiment input) {
                    return input.getSentimentValue() == sentimentValue;
                }
            })));
        }
        return result;
    }

    private double scoreForSentiments(final DateTime firstSentiment, final double offset, final List<Sentiment> sentiments) {
        double score = 0;
        for (final Sentiment sentiment : sentiments) {
            score += (sentiment.getCreationDate().getMillis() - firstSentiment.getMillis()) / offset;
        }
        return score;
    }

    private int sentimentScore(final Map<SentimentValue, Double> scoreBySentimentValue) {
        final double totalScore = totalScore(scoreBySentimentValue);
        int finalResult = 0;
        for (final Map.Entry<SentimentValue, Double> entry : scoreBySentimentValue.entrySet()) {
            final SentimentValue key = entry.getKey();
            finalResult += sentimentScore(entry.getValue() / totalScore, key);
        }
        return finalResult;
    }

    private double sentimentScore(final double ponderatedScore, final SentimentValue sentimentValue) {
        return (ponderatedScore * 100) * sentimentValue.numericValue();
    }

    private double totalScore(final Map<SentimentValue, Double> result) {
        double total = 0;
        for (final Map.Entry<SentimentValue, Double> entry : result.entrySet()) {
            total += entry.getValue();
        }
        return total;
    }
}
