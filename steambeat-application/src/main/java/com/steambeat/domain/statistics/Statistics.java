package com.steambeat.domain.statistics;

import com.steambeat.domain.BaseEntity;
import com.steambeat.domain.opinion.*;
import com.steambeat.domain.subject.Subject;
import org.joda.time.DateTime;

import java.util.UUID;

public class Statistics extends BaseEntity {

    protected Statistics() {
    }

    public Statistics(final Subject subject, final Granularity granularity, final DateTime date) {
        this.id = UUID.randomUUID();
        this.date = date;
        subjectId = subject.getId();
        this.granularity = granularity;
    }

    public void incrementJudgmentCount(final Judgment judgment) {
        incrementJudgmentCountForFeeling(judgment.getFeeling());
    }

    private void incrementJudgmentCountForFeeling(final Feeling feeling) {
        switch (feeling) {
            case good:
                goodJudgments++;
                break;
            case bad:
                badJudgments++;
                break;
        }
    }

    @Override
    public UUID getId() {
        return id;
    }

    public UUID getSubjectId() {
        return subjectId;
    }

    public Granularity getGranularity() {
        return granularity;
    }

    public DateTime getDate() {
        return date;
    }

    public int getGoodJudgments() {
        return goodJudgments;
    }

    public int getBadJudgments() {
        return badJudgments;
    }

    private UUID id;
    private UUID subjectId;
    private Granularity granularity;
    private DateTime date;
    private int goodJudgments;
    private int badJudgments;
}
