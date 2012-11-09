package com.feelhub.web.dto;

import com.feelhub.domain.feeling.Sentiment;
import com.feelhub.domain.illustration.Illustration;
import com.feelhub.domain.keyword.Keyword;
import com.feelhub.repositories.Repositories;

import java.util.*;

public class TopicDataFactory {

    public TopicData getTopicData(final Keyword keyword) {
        final TopicData.Builder builder = new TopicData.Builder();
        builder.keyword(keyword);
        builder.language(keyword.getLanguage());
        if (keyword.getTopicId() != null) {
            builder.topicId(keyword.getTopicId());
            final List<Illustration> illustrations = Repositories.illustrations().forTopicId(keyword.getTopicId());
            if (!illustrations.isEmpty()) {
                builder.illustration(illustrations.get(0));
            }
        }
        return builder.build();
    }

    public TopicData getTopicData(final UUID id, final Keyword keyword) {
        final TopicData.Builder builder = new TopicData.Builder();
        builder.keyword(keyword);
        builder.language(keyword.getLanguage());
        builder.topicId(id);
        final List<Illustration> illustrations = Repositories.illustrations().forTopicId(id);
        if (!illustrations.isEmpty()) {
            builder.illustration(illustrations.get(0));
        }
        return builder.build();
    }

    public TopicData getTopicDatas(final Keyword keyword, final Sentiment sentiment) {
        final TopicData.Builder builder = new TopicData.Builder();
        builder.keyword(keyword);
        builder.language(keyword.getLanguage());
        builder.sentimentValue(sentiment.getSentimentValue());
        builder.topicId(sentiment.getTopicId());
        final List<Illustration> illustrations = Repositories.illustrations().forTopicId(sentiment.getTopicId());
        if (!illustrations.isEmpty()) {
            builder.illustration(illustrations.get(0));
        }
        return builder.build();
    }
}
