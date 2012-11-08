package com.feelhub.domain.feeling;

import com.feelhub.domain.eventbus.DomainEvent;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.user.User;

import java.util.UUID;

public class FeelingRequestEvent extends DomainEvent {

    public static class Builder {

        public FeelingRequestEvent build() {
            return new FeelingRequestEvent(this);
        }

        public Builder text(final String text) {
            this.text = text;
            return this;
        }

        public Builder sentimentValue(final SentimentValue sentimentValue) {
            this.sentimentValue = sentimentValue;
            return this;
        }

        public Builder userLanguageCode(final String userLanguageCode) {
            this.userLanguageCode = userLanguageCode;
            return this;
        }

        public Builder languageCode(final String languageCode) {
            this.languageCode = languageCode;
            return this;
        }

        public Builder keywordValue(final String keywordValue) {
            this.keywordValue = keywordValue;
            return this;
        }

        public Builder feelingId(final UUID feelingId) {
            this.feelingId = feelingId;
            return this;
        }

        public Builder user(final User user) {
            this.userId = user.getId();
            return this;
        }

        private String text = "";
        private SentimentValue sentimentValue = SentimentValue.none;
        private String userLanguageCode = FeelhubLanguage.none().getCode();
        private String languageCode = FeelhubLanguage.none().getCode();
        private String keywordValue = "";
        private UUID feelingId;
        private String userId = "";
    }

    private FeelingRequestEvent(final Builder builder) {
        this.text = builder.text;
        this.sentimentValue = builder.sentimentValue;
        this.userLanguageCode = builder.userLanguageCode;
        this.languageCode = builder.languageCode;
        this.keywordValue = builder.keywordValue;
        this.feelingId = builder.feelingId;
        this.userId = builder.userId;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public SentimentValue getSentimentValue() {
        return sentimentValue;
    }

    public String getText() {
        return text;
    }

    public String getUserLanguageCode() {
        return userLanguageCode;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public String getKeywordValue() {
        return keywordValue;
    }

    public UUID getFeelingId() {
        return feelingId;
    }

    public String getUserId() {
        return userId;
    }

    private final String text;
    private final SentimentValue sentimentValue;
    private final String userLanguageCode;
    private final String languageCode;
    private final String keywordValue;
    private final UUID feelingId;
    private final String userId;
}
