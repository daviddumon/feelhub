package com.feelhub.application.command.feeling;

import com.feelhub.application.command.Command;
import com.feelhub.domain.feeling.*;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.Topic;
import com.feelhub.domain.user.User;
import com.feelhub.repositories.Repositories;

import java.util.UUID;

public class CreateFeelingCommand implements Command<UUID> {

    private CreateFeelingCommand(final Builder builder) {
        this.text = builder.text;
        this.language = builder.language;
        this.userId = builder.userId;
        this.topicId = builder.topicId;
        this.feelingValue = builder.feelingValue;
    }

    @Override
    public UUID execute() {
        final Feeling feeling = buildFeeling();
        Repositories.feelings().add(feeling);
        return feeling.getId();
    }

    private Feeling buildFeeling() {
        final Feeling feeling = new FeelingFactory().createFeeling(userId, topicId);
        feeling.setFeelingValue(feelingValue);
        feeling.setText(text);
        feeling.setLanguageCode(language.getCode());
        return feeling;
    }

    public final String text;
    public final FeelhubLanguage language;
    public final UUID userId;
    public final FeelingValue feelingValue;
    public final UUID topicId;

    public static class Builder {

        public CreateFeelingCommand build() {
            return new CreateFeelingCommand(this);
        }

        public Builder text(final String text) {
            this.text = text;
            return this;
        }

        public Builder languageCode(final FeelhubLanguage languageCode) {
            this.language = languageCode;
            return this;
        }

        public Builder user(final User user) {
            this.userId = user.getId();
            return this;
        }

        public Builder topic(final Topic topic) {
            this.topicId = topic.getId();
            return this;
        }

        public Builder feelingValue(final FeelingValue feelingValue) {
            this.feelingValue = feelingValue;
            return this;
        }

        private String text = "";
        private FeelhubLanguage language = FeelhubLanguage.none();
        private UUID userId;
        private UUID topicId;
        private FeelingValue feelingValue;
    }
}
