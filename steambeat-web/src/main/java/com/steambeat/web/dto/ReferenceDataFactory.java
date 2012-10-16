package com.steambeat.web.dto;

import com.steambeat.domain.illustration.Illustration;
import com.steambeat.domain.keyword.Keyword;
import com.steambeat.domain.opinion.Judgment;
import com.steambeat.repositories.Repositories;

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

    public ReferenceData getReferenceDatas(final Keyword keyword, final Judgment judgment) {
        final ReferenceData.Builder builder = new ReferenceData.Builder();
        builder.keyword(keyword);
        builder.language(keyword.getLanguage());
        builder.feeling(judgment.getFeeling());
        builder.referenceId(judgment.getReferenceId());
        final List<Illustration> illustrations = Repositories.illustrations().forReferenceId(judgment.getReferenceId());
        if (!illustrations.isEmpty()) {
            builder.illustration(illustrations.get(0));
        }
        return builder.build();
    }
}
