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

        public Builder languageCode(final FeelhubLanguage languageCode) {
            this.language = languageCode;
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

        public Builder topicId(final UUID topicId) {
            this.topicId = topicId;
            return this;
        }

        private String text = "";
        private FeelhubLanguage language = FeelhubLanguage.none();
        private UUID feelingId;
        private UUID userId;
        private UUID topicId;
    }

    private FeelingRequestEvent(final Builder builder) {
        this.text = builder.text;
        this.language = builder.language;
        this.feelingId = builder.feelingId;
        this.userId = builder.userId;
        this.topicId = builder.topicId;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public String getText() {
        return text;
    }

    public FeelhubLanguage getLanguage() {
        return language;
    }

    public UUID getFeelingId() {
        return feelingId;
    }

    public UUID getUserId() {
        return userId;
    }

    public UUID getTopicId() {
        return topicId;
    }

    private final String text;
    private final FeelhubLanguage language;
    private final UUID feelingId;
    private final UUID userId;
    private final UUID topicId;
}
