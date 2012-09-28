package com.steambeat.web.dto;

import com.steambeat.domain.illustration.Illustration;
import com.steambeat.domain.keyword.Keyword;
import com.steambeat.domain.thesaurus.SteambeatLanguage;

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

        public Builder language(final SteambeatLanguage language) {
            this.languageCode = language.getCode();
            return this;
        }

        private String referenceId = "";
        private String illustrationLink = "";
        private String keywordValue = "";
        private String languageCode = SteambeatLanguage.none().getCode();
    }

    private ReferenceData(final Builder builder) {
        this.referenceId = builder.referenceId;
        this.illustrationLink = builder.illustrationLink;
        this.keywordValue = builder.keywordValue;
        this.languageCode = builder.languageCode;
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

    private final String referenceId;
    private final String illustrationLink;
    private String keywordValue;
    private String languageCode;
}
