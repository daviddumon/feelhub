package com.feelhub.repositories.mapping;

import com.feelhub.domain.feeling.Sentiment;
import org.mongolink.domain.mapper.ComponentMap;

public class SentimentMapping extends ComponentMap<Sentiment> {

    public SentimentMapping() {
        super(Sentiment.class);
    }

    @Override
    protected void map() {
        property(element().getSentimentValue());
        property(element().getTopicId());
        property(element().getToken());
    }
}
