package com.feelhub.domain.topic;


import com.feelhub.domain.feeling.Sentiment;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import org.joda.time.DateTime;

import java.util.List;

public final class TopicSentimentScoreCalculator {

    int getSentimentScore(List<Sentiment> sentiments, DateTime topicCreationDate) {
        if (sentiments.isEmpty()) {
            return 0;
        }
        int score = 0;
        //Sentiment lastSentiment = findLastSentiment(sentiments);
        for(Sentiment sentiment : sentiments) {
            score += sentiment.getSentimentValue().numericValue();
        }
        return (int) (((double) score / (double) sentiments.size()) * 100);
    }

}
