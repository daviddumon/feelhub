package com.feelhub.web.dto;

import com.feelhub.domain.feeling.SentimentValue;
import com.feelhub.domain.illustration.Illustration;
import com.feelhub.domain.keyword.Keyword;
import com.feelhub.domain.thesaurus.FeelhubLanguage;

import java.util.UUID;

public class KeywordData {

    public static class Builder {

        public KeywordData build() {
            return new KeywordData(this);
        }

        public Builder topicId(final UUID topicId) {
            this.topicId = topicId.toString();
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

        private String topicId = "";
        private String illustrationLink = "";
        private String keywordValue = "";
        private String languageCode = FeelhubLanguage.none().getCode();
        private SentimentValue sentimentValue = SentimentValue.none;
    }

    private KeywordData(final Builder builder) {
        this.topicId = builder.topicId;
        this.illustrationLink = builder.illustrationLink;
        this.keywordValue = builder.keywordValue;
        this.languageCode = builder.languageCode;
        this.sentimentValue = builder.sentimentValue;
    }

    public String getTopicId() {
        return topicId;
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

    private final String topicId;
    private final String illustrationLink;
    private final String keywordValue;
    private final String languageCode;
    private final SentimentValue sentimentValue;
}
