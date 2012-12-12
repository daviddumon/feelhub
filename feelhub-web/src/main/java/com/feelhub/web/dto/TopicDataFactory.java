package com.feelhub.web.dto;

import com.feelhub.application.TopicService;
import com.feelhub.domain.feeling.*;
import com.feelhub.domain.meta.Illustration;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.Topic;
import com.feelhub.repositories.Repositories;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

import java.util.List;

public class TopicDataFactory {

    @Inject
    public TopicDataFactory(final TopicService topicService) {
        this.topicService = topicService;
    }

    public TopicData getTopicData(final Topic realTopic, final FeelhubLanguage feelhubLanguage) {
        return getTopicData(realTopic, feelhubLanguage, null);
    }

    public List<TopicData> getTopicDatas(final Feeling feeling, final FeelhubLanguage feelhubLanguage) {
        final List<TopicData> topicDatas = Lists.newArrayList();
        for (final Sentiment sentiment : feeling.getSentiments()) {
            final Topic realTopic = topicService.lookUp(sentiment.getTopicId());
            final TopicData topicData = getTopicData(realTopic, feelhubLanguage, sentiment);

            topicDatas.add(topicData);
        }
        return topicDatas;
    }

    private TopicData getTopicData(final Topic topic, final FeelhubLanguage feelhubLanguage, final Sentiment sentiment) {
        final TopicData.Builder builder = new TopicData.Builder();
        builder.id(topic.getId());
        builder.name(topic.getDescription(feelhubLanguage));
        builder.type(topic.getType());
        //builder.subtypes(topic.getSubTypes());
        //builder.urls(topic.getUrls());
        if (sentiment != null) {
            builder.sentimentValue(sentiment.getSentimentValue());
        }
        final List<Illustration> illustrations = Repositories.illustrations().forTopicId(topic.getId());
        if (!illustrations.isEmpty()) {
            builder.illustration(illustrations.get(0));
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
