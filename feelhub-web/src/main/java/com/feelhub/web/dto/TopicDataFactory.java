package com.feelhub.web.dto;

import com.feelhub.application.TopicService;
import com.feelhub.domain.feeling.*;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.Topic;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

import java.util.List;

public class TopicDataFactory {

    @Inject
    public TopicDataFactory(final TopicService topicService) {
        this.topicService = topicService;
    }

    public TopicData getTopicData(final Topic topic, final FeelhubLanguage feelhubLanguage) {
        return getTopicData(topic, feelhubLanguage, null);
    }

    public List<TopicData> getTopicDatas(final Feeling feeling, final FeelhubLanguage feelhubLanguage) {
        final List<TopicData> topicDatas = Lists.newArrayList();
        for (final Sentiment sentiment : feeling.getSentiments()) {
            if (sentiment.getTopicId() != null) {
                final Topic topic = topicService.lookUpCurrent(sentiment.getTopicId());
                topicDatas.add(getTopicData(topic, feelhubLanguage, sentiment));
            } else {
                topicDatas.add(getTopicData(null, feelhubLanguage, sentiment));
            }
        }
        return topicDatas;
    }

    private TopicData getTopicData(final Topic topic, final FeelhubLanguage feelhubLanguage, final Sentiment sentiment) {
        final TopicData.Builder builder = new TopicData.Builder();
        if (topic != null) {
            builder.id(topic.getId());
            builder.name(topic.getName(feelhubLanguage));
            builder.type(topic.getType());
            builder.illustration(topic.getIllustration());
            builder.description(topic.getDescription(feelhubLanguage));
            builder.subtypes(topic.getSubTypes());
            builder.uris(topic.getUris());
        } else {
            builder.name(sentiment.getToken());
        }
        if (sentiment != null) {
            builder.sentimentValue(sentiment.getSentimentValue());
        }
        return builder.build();
    }

    public TopicData getTopicData(final String name) {
        final TopicData.Builder builder = new TopicData.Builder();
        builder.name(name);
        return builder.build();
    }

    private final TopicService topicService;
}
