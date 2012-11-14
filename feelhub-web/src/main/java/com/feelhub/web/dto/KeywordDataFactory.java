package com.feelhub.web.dto;

import com.feelhub.application.KeywordService;
import com.feelhub.domain.feeling.*;
import com.feelhub.domain.illustration.Illustration;
import com.feelhub.domain.keyword.Keyword;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.repositories.Repositories;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

import java.util.*;

public class KeywordDataFactory {

    @Inject
    public KeywordDataFactory(final KeywordService keywordService) {
        this.keywordService = keywordService;
    }

    public KeywordData getKeywordData(final Keyword keyword) {
        final KeywordData.Builder builder = new KeywordData.Builder();
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

    public KeywordData getKeywordData(final UUID id, final Keyword keyword) {
        final KeywordData.Builder builder = new KeywordData.Builder();
        builder.keyword(keyword);
        builder.language(keyword.getLanguage());
        builder.topicId(id);
        final List<Illustration> illustrations = Repositories.illustrations().forTopicId(id);
        if (!illustrations.isEmpty()) {
            builder.illustration(illustrations.get(0));
        }
        return builder.build();
    }

    public KeywordData getKeywordData(final Keyword keyword, final Sentiment sentiment) {
        final KeywordData.Builder builder = new KeywordData.Builder();
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

    public List<KeywordData> getKeywordDatas(final Feeling feeling, final FeelhubLanguage feelhubLanguage) {
        final List<KeywordData> keywordDatas = Lists.newArrayList();
        for (final Sentiment sentiment : feeling.getSentiments()) {
            final Keyword keyword = keywordService.lookUp(sentiment.getTopicId(), feelhubLanguage);
            final KeywordData keywordData = getKeywordData(keyword, sentiment);
            keywordDatas.add(keywordData);
        }
        return keywordDatas;
    }

    private final KeywordService keywordService;
}
