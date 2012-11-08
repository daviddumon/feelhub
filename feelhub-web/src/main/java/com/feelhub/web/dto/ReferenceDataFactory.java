package com.feelhub.web.dto;

import com.feelhub.domain.feeling.Sentiment;
import com.feelhub.domain.illustration.Illustration;
import com.feelhub.domain.keyword.Keyword;
import com.feelhub.repositories.Repositories;

import java.util.*;

public class ReferenceDataFactory {

    public ReferenceData getReferenceData(final Keyword keyword) {
        final ReferenceData.Builder builder = new ReferenceData.Builder();
        builder.keyword(keyword);
        builder.language(keyword.getLanguage());
        if (keyword.getReferenceId() != null) {
            builder.referenceId(keyword.getReferenceId());
            final List<Illustration> illustrations = Repositories.illustrations().forReferenceId(keyword.getReferenceId());
            if (!illustrations.isEmpty()) {
                builder.illustration(illustrations.get(0));
            }
        }
        return builder.build();
    }

    public ReferenceData getReferenceData(final UUID id, final Keyword keyword) {
        final ReferenceData.Builder builder = new ReferenceData.Builder();
        builder.keyword(keyword);
        builder.language(keyword.getLanguage());
        builder.referenceId(id);
        final List<Illustration> illustrations = Repositories.illustrations().forReferenceId(id);
        if (!illustrations.isEmpty()) {
            builder.illustration(illustrations.get(0));
        }
        return builder.build();
    }

    public ReferenceData getReferenceDatas(final Keyword keyword, final Sentiment sentiment) {
        final ReferenceData.Builder builder = new ReferenceData.Builder();
        builder.keyword(keyword);
        builder.language(keyword.getLanguage());
        builder.sentimentValue(sentiment.getSentimentValue());
        builder.referenceId(sentiment.getReferenceId());
        final List<Illustration> illustrations = Repositories.illustrations().forReferenceId(sentiment.getReferenceId());
        if (!illustrations.isEmpty()) {
            builder.illustration(illustrations.get(0));
        }
        return builder.build();
    }
}
