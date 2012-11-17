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

        public Builder userLanguageCode(final FeelhubLanguage userLanguageCode) {
            this.userLanguage = userLanguageCode;
            return this;
        }

        public Builder languageCode(final FeelhubLanguage languageCode) {
            this.language = languageCode;
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
        private FeelhubLanguage userLanguage = FeelhubLanguage.none();
        private FeelhubLanguage language = FeelhubLanguage.none();
        private String keywordValue = "";
        private UUID feelingId;
        private String userId = "";
    }

    private FeelingRequestEvent(final Builder builder) {
        this.text = builder.text;
        this.sentimentValue = builder.sentimentValue;
        this.userLanguage = builder.userLanguage;
        this.language = builder.language;
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

    public FeelhubLanguage getUserLanguage() {
        return userLanguage;
    }

    public FeelhubLanguage getLanguage() {
        return language;
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
    private final FeelhubLanguage userLanguage;
    private final FeelhubLanguage language;
    private final String keywordValue;
    private final UUID feelingId;
    private final String userId;
}
