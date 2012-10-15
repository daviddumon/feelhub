package com.steambeat.domain.opinion;

import com.google.common.collect.Lists;
import com.steambeat.domain.BaseEntity;
import com.steambeat.domain.eventbus.DomainEventBus;
import com.steambeat.domain.reference.Reference;
import org.joda.time.DateTime;

import java.util.*;

public class Opinion extends BaseEntity {

    public static class Builder {

        public Opinion build() {
            return new Opinion(this);
        }

        public Builder id(final UUID id) {
            this.id = id;
            return this;
        }

        public Builder text(final String text) {
            this.text = text;
            return this;
        }

        public Builder user(final String userId) {
            this.userId = userId;
            return this;
        }

        public Builder language(final String languageCode) {
            this.languageCode = languageCode;
            return this;
        }

        public Builder judgments(final List<Judgment> judgments) {
            this.judgments.addAll(judgments);
            return this;
        }

        private UUID id = null;
        private String text = "";
        private String userId = "";
        private String languageCode = "";
        private List<Judgment> judgments = Lists.newArrayList();
    }

    //do not delete mongolink constructor
    protected Opinion() {
    }

    private Opinion(final Builder builder) {
        this.id = builder.id;
        this.text = builder.text;
        this.languageCode = builder.languageCode;
        this.userId = builder.userId;
        this.judgments.addAll(builder.judgments);
        postEventForAllJudgments();
    }

    private void postEventForAllJudgments() {
        for (Judgment judgment : judgments) {
            DomainEventBus.INSTANCE.post(new JudgmentStatisticsEvent(judgment));
        }
    }

    public Opinion(final String text, final String userId) {
        this(text, userId, UUID.randomUUID());
    }

    public Opinion(final String text, final String userId, final UUID id) {
        this.id = id;
        this.text = text;
        this.userId = userId;
    }

    public void addJudgment(final Reference reference, final Feeling feeling) {
        final Judgment judgment = new Judgment(reference.getId(), feeling);
        judgments.add(judgment);
        reference.setLastModificationDate(new DateTime());
        DomainEventBus.INSTANCE.post(new JudgmentStatisticsEvent(judgment));
    }

    public void addJudgment(final Judgment judgment) {
        judgments.add(judgment);
        //judgment.getReference().setLastModificationDate(new DateTime());
        DomainEventBus.INSTANCE.post(new JudgmentStatisticsEvent(judgment));
    }

    public UUID getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public List<Judgment> getJudgments() {
        return judgments;
    }

    public void setLanguageCode(final String languageCode) {
        this.languageCode = languageCode;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public String getUserId() {
        return userId;
    }

    private UUID id;
    private String text;
    private final List<Judgment> judgments = Lists.newArrayList();
    private String languageCode;
    private String userId;
}
