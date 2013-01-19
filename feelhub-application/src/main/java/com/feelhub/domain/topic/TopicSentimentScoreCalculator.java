package com.feelhub.domain.topic;


import com.feelhub.domain.feeling.Sentiment;
import org.joda.time.DateTime;

import java.util.List;

public final class TopicSentimentScoreCalculator {

    int getSentimentScore(List<Sentiment> sentiments, DateTime topicCreationDate) {
        if (sentiments.isEmpty()) {
            return 0;
        }
        int score = 0;
        for(Sentiment sentiment : sentiments) {
            score += sentiment.getSentimentValue().numericValue();
        }
        return score / sentiments.size() * 100;
    }
}
