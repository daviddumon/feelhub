package com.feelhub.web.dto;

import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.Topic;

public class TopicDataFactory {

    public TopicData topicData(final Topic topic, final FeelhubLanguage feelhubLanguage) {
        final TopicData.Builder builder = new TopicData.Builder();
        if (topic != null) {
            builder.id(topic.getId());
            builder.name(topic.getName(feelhubLanguage));
            builder.type(topic.getType());
            builder.thumbnail(topic.getThumbnail());
            builder.description(topic.getDescription(feelhubLanguage));
            builder.subtypes(topic.getSubTypes());
            builder.uris(topic.getUris());
            builder.happyFeelingCount(topic.getHappyFeelingCount());
            builder.sadFeelingCount(topic.getSadFeelingCount());
            builder.boredFeelingCount(topic.getBoredFeelingCount());
            builder.creationDate(topic.getCreationDate());
            builder.lastModificatioNDate(topic.getLastModificationDate());
        }
        return builder.build();
    }

    public TopicData simpleTopicData(final Topic topic, final FeelhubLanguage feelhubLanguage) {
        final TopicData.Builder builder = new TopicData.Builder();
        if (topic != null) {
            builder.id(topic.getId());
            builder.name(topic.getName(feelhubLanguage));
            builder.type(topic.getType());
            builder.thumbnail(topic.getThumbnail());
            builder.creationDate(topic.getCreationDate());
            builder.lastModificatioNDate(topic.getLastModificationDate());
        }
        return builder.build();
    }
}
