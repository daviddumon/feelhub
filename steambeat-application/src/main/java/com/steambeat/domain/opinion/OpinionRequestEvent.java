package com.steambeat.domain.opinion;

import com.steambeat.domain.eventbus.DomainEvent;
import com.steambeat.domain.thesaurus.SteambeatLanguage;
import com.steambeat.domain.user.User;

public class OpinionRequestEvent extends DomainEvent {

    public static class Builder {

        public OpinionRequestEvent build() {
            return new OpinionRequestEvent(this);
        }

        public Builder text(final String text) {
            this.text = text;
            return this;
        }

        public Builder feeling(final Feeling feeling) {
            this.feeling = feeling;
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

        public Builder opinionId(final String opinionId) {
            this.opinionId = opinionId;
            return this;
        }

        public Builder user(final User user) {
            this.userId = user.getId();
            return this;
        }

        private String text = "";
        private Feeling feeling = Feeling.none;
        private String userLanguageCode = SteambeatLanguage.none().getCode();
        private String languageCode = SteambeatLanguage.none().getCode();
        private String keywordValue = "";
        private String opinionId = "";
        private String userId = "";
    }

    private OpinionRequestEvent(final Builder builder) {
        this.text = builder.text;
        this.feeling = builder.feeling;
        this.userLanguageCode = builder.userLanguageCode;
        this.languageCode = builder.languageCode;
        this.keywordValue = builder.keywordValue;
        this.opinionId = builder.opinionId;
        this.userId = builder.userId;
    }

    @Override
    public String toString() {
        return "";
    }

    public Feeling getFeeling() {
        return feeling;
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

    public String getOpinionId() {
        return opinionId;
    }

    public String getUserId() {
        return userId;
    }

    private final String text;
    private final Feeling feeling;
    private String userLanguageCode;
    private String languageCode;
    private String keywordValue;
    private String opinionId;
    private String userId;
}
