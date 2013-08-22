package com.feelhub.domain.topic;

import com.feelhub.domain.feeling.*;
import com.google.common.base.Predicate;
import com.google.common.collect.*;
import org.joda.time.DateTime;

import java.util.*;

public final class TopicFeelingScoreCalculator {

    int feelingScore(final List<Feeling> feelings) {
        if (feelings.isEmpty()) {
            return 0;
        } else if (feelings.size() == 1) {
            return (int) feelingScore(1d, feelings.get(0).getFeelingValue());
        }
        return feelingScore(scoresByFeelingValue(feelings, firstFeeling(feelings).getCreationDate(), offsetBetweenFeelings(feelings)));
    }

    private Feeling firstFeeling(final List<Feeling> feelings) {

        final Ordering<Feeling> ordering = new Ordering<Feeling>() {

            @Override
            public int compare(final Feeling left, final Feeling right) {
                return left.getCreationDate().compareTo(right.getCreationDate());
            }
        };

        return ordering.min(feelings);
    }

    private long offsetBetweenFeelings(final List<Feeling> feelings) {
        return lastFeeling(feelings).getCreationDate().getMillis() - firstFeeling(feelings).getCreationDate().getMillis();
    }

    private Feeling lastFeeling(final List<Feeling> feelings) {
        final Ordering<Feeling> ordering = new Ordering<Feeling>() {

            @Override
            public int compare(final Feeling left, final Feeling right) {
                return left.getCreationDate().compareTo(right.getCreationDate());
            }
        };
        return ordering.max(feelings);
    }

    private Map<FeelingValue, Double> scoresByFeelingValue(final List<Feeling> feelings, final DateTime firstFeeling, final double offset) {
        final Map<FeelingValue, Double> result = Maps.newHashMap();
        for (final Map.Entry<FeelingValue, List<Feeling>> entry : feelingsByFeelingValue(feelings).entrySet()) {
            result.put(entry.getKey(), scoreForFeelings(firstFeeling, offset, entry.getValue()));
        }
        return result;
    }

    private Map<FeelingValue, List<Feeling>> feelingsByFeelingValue(final List<Feeling> feelings) {
        final Map<FeelingValue, List<Feeling>> result = Maps.newHashMap();
        for (final FeelingValue feelingValue : FeelingValue.values()) {
            result.put(feelingValue, Lists.newArrayList(Iterables.filter(feelings, new Predicate<Feeling>() {

                @Override
                public boolean apply(final Feeling input) {
                    return input.getFeelingValue() == feelingValue;
                }
            })));
        }
        return result;
    }

    private double scoreForFeelings(final DateTime firstFeeling, final double offset, final List<Feeling> feelings) {
        double score = 0;
        for (final Feeling feeling : feelings) {
            score += (feeling.getCreationDate().getMillis() - firstFeeling.getMillis()) / offset;
        }
        return score;
    }

    private int feelingScore(final Map<FeelingValue, Double> scoreByFeelingValue) {
        final double totalScore = totalScore(scoreByFeelingValue);
        int finalResult = 0;
        for (final Map.Entry<FeelingValue, Double> entry : scoreByFeelingValue.entrySet()) {
            final FeelingValue key = entry.getKey();
            finalResult += feelingScore(entry.getValue() / totalScore, key);
        }
        return finalResult;
    }

    private double feelingScore(final double ponderatedScore, final FeelingValue feelingValue) {
        return (ponderatedScore * 100) * feelingValue.numericValue();
    }

    private double totalScore(final Map<FeelingValue, Double> result) {
        double total = 0;
        for (final Map.Entry<FeelingValue, Double> entry : result.entrySet()) {
            total += entry.getValue();
        }
        return total;
    }
}
