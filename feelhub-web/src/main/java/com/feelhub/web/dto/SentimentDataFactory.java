package com.feelhub.web.dto;

import com.feelhub.application.TopicService;
import com.feelhub.domain.feeling.*;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.Topic;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

import java.util.List;

public class SentimentDataFactory {

    @Inject
    public SentimentDataFactory(final TopicService topicService) {
        this.topicService = topicService;
    }

    public List<SentimentData> sentimentDatas(final Feeling feeling, final FeelhubLanguage feelhubLanguage) {
        final List<SentimentData> sentimentDatas = Lists.newArrayList();
        for (final Sentiment sentiment : feeling.getSentiments()) {
            sentimentDatas.add(sentimentData(feelhubLanguage, sentiment));
        }
        return sentimentDatas;
    }

    private SentimentData sentimentData(FeelhubLanguage feelhubLanguage, Sentiment sentiment) {
        if (sentiment.getTopicId() != null) {
            final Topic topic = topicService.lookUpCurrent(sentiment.getTopicId());
            return sentimentData(topic, feelhubLanguage, sentiment);
        }
        return sentimentData(sentiment);
    }

    private SentimentData sentimentData(final Topic topic, final FeelhubLanguage feelhubLanguage, final Sentiment sentiment) {
        final SentimentData.Builder builder = new SentimentData.Builder();
        builder.id(topic.getId());
        builder.name(topic.getName(feelhubLanguage));
        builder.type(topic.getType());
        builder.illustration(topic.getIllustration());
        builder.sentimentValue(sentiment.getSentimentValue());
        return builder.build();
    }

    private SentimentData sentimentData(final Sentiment sentiment) {
        final SentimentData.Builder builder = new SentimentData.Builder();
        builder.name(sentiment.getToken());
        builder.sentimentValue(sentiment.getSentimentValue());
        return builder.build();
    }

    private final TopicService topicService;
}
