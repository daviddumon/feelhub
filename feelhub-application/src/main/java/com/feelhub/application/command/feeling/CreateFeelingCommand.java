package com.feelhub.application.command.feeling;

import com.feelhub.application.command.Command;
import com.feelhub.domain.feeling.*;
import com.feelhub.domain.related.RelatedBuilder;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.user.User;
import com.feelhub.repositories.Repositories;

import java.util.*;

public class CreateFeelingCommand implements Command<UUID> {

    private CreateFeelingCommand(final Builder builder) {
        this.text = builder.text;
        this.language = builder.language;
        this.userId = builder.userId;
        this.sentiments = builder.sentiments;
    }

    @Override
    public UUID execute() {
        final Feeling feeling = buildFeeling();
        new FeelingRelationBinder(new RelatedBuilder()).bind(feeling);
        Repositories.feelings().add(feeling);
        return feeling.getId();
    }

    private Feeling buildFeeling() {
        final Feeling feeling = new FeelingFactory().createFeeling(text, userId);
        feeling.setLanguageCode(language.getCode());
        for (final Sentiment sentiment : sentiments) {
            feeling.addSentiment(sentiment);
        }
        return feeling;
    }

    public final String text;
    public final FeelhubLanguage language;
    public final UUID userId;
    public final List<Sentiment> sentiments;

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

        public Builder feelingId(final UUID feelingId) {
            this.feelingId = feelingId;
            return this;
        }

        public Builder user(final User user) {
            this.userId = user.getId();
            return this;
        }

        public Builder sentiments(final List<Sentiment> sentiments) {
            this.sentiments = sentiments;
            return this;
        }

        private String text = "";
        private FeelhubLanguage language = FeelhubLanguage.none();
        private UUID feelingId;
        private UUID userId;
        private List<Sentiment> sentiments;
    }
}
