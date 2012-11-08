package com.feelhub.web.dto;

import com.feelhub.domain.feeling.SentimentValue;
import com.feelhub.domain.illustration.Illustration;
import com.feelhub.domain.keyword.Keyword;
import com.feelhub.domain.thesaurus.FeelhubLanguage;

import java.util.UUID;

public class ReferenceData {

    public static class Builder {

        public ReferenceData build() {
            return new ReferenceData(this);
        }

        public Builder referenceId(final UUID referenceId) {
            this.referenceId = referenceId.toString();
            return this;
        }

        public Builder illustration(final Illustration illustration) {
            this.illustrationLink = illustration.getLink();
            return this;
        }

        public Builder keyword(final Keyword keyword) {
            this.keywordValue = keyword.getValue();
            return this;
        }

        public Builder language(final FeelhubLanguage language) {
            this.languageCode = language.getCode();
            return this;
        }

        public Builder sentimentValue(final SentimentValue sentimentValue) {
            this.sentimentValue = sentimentValue;
            return this;
        }

        private String referenceId = "";
        private String illustrationLink = "";
        private String keywordValue = "";
        private String languageCode = FeelhubLanguage.none().getCode();
        private SentimentValue sentimentValue = SentimentValue.none;
    }

    private ReferenceData(final Builder builder) {
        this.referenceId = builder.referenceId;
        this.illustrationLink = builder.illustrationLink;
        this.keywordValue = builder.keywordValue;
        this.languageCode = builder.languageCode;
        this.sentimentValue = builder.sentimentValue;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public String getIllustrationLink() {
        return illustrationLink;
    }

    public String getKeywordValue() {
        return keywordValue;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public SentimentValue getSentimentValue() {
        return sentimentValue;
    }

    private final String referenceId;
    private final String illustrationLink;
    private final String keywordValue;
    private final String languageCode;
    private final SentimentValue sentimentValue;
}
